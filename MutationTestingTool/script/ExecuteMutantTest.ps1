# Name: MutantTestScript.ps1
# Purpose: Execute test suit for each of the mutants of a maven project and compute mutation score.

# Get directory path for mutants
# For testing
# $MutantPath = "C:\Users\Fenil\Documents\Eclipse projects\mutants"
Write-Host "Input your mutant directory path"
Write-Host "NOTE: If geo-master is at path\to\mutant-directory\geo-master, then enter path\to\mutant-directory"
$MutantPath = Read-Host -Prompt 'Enter Path: '
Write-Host "Your mutant direcotry is => $MutantPath"

# cd to mutants directory
cd $MutantPath

# Get all the mutant project directories
# One direcoty is the original project and all others, post-fixed with number, are mutants.
$MutantProjects = (Get-ChildItem | ?{ $_.PSIsContainer } | Select-Object FullName).FullName
$TotalNumberOfMutants = $MutantProjects.length - 1
Write-Host "Total number of mutants: $TotalNumberOfMutants"

# Keep track of killed mutants
$NumberOfKilledMutants = 0
$NumberOfLiveMutants = 0
$CurrentMutantStatus = 0
$FirstProject = 'true'

# Execute test site for each of the project
ForEach($MutantProject In $MutantProjects){
	
	If($FirstProject -eq 'true'){
		$FirstProject = 'false'
		continue
	}
	# time-linit of 180 seconds(3 minutes) to complete tests.
	# if it doesn't complete in 180 sec, then it's considered 'not killed' by test suite.
	$timeoutSeconds = 180
	$code = {
		param($path)
		cd $path
		mvn test
	}
	
	Write-Host "*** Testing $MutantProject with 'mvn test' command ***"
	$j = Start-Job -ScriptBlock $code -Arg $MutantProject
	if (Wait-Job $j -Timeout $timeoutSeconds){
		# testing completes within 180 seconds
		$CurrentMutantStatus = (Receive-Job $j | Select-String "Tests run: 92, Failures: 0, Errors: 0, Skipped: 0" | Measure-Object).Count
	}else{
		# testing timed-out
		$CurrentMutantStatus = 1
	}
	Remove-Job -force $j
	
	If( $CurrentMutantStatus -eq 0 ){
		Write-Host "Mutant in project '$MutantProject' was killed."
		Write-Host "For more information, see reports at '$MutantProject\target\surefire-reports'."
	}elseif( $CurrentMutantStatus -eq 1 ){
		Write-Host "Mutant in project '$MutantProject' was NOT killed."
		Write-Host "For more information, see reports at '$MutantProject\target\surefire-reports'."
	}
	Write-Host ""
	
	$NumberOfLiveMutants = $NumberOfLiveMutants + $CurrentMutantStatus
}

$NumberOfKilledMutants = $TotalNumberOfMutants - $NumberOfLiveMutants
$MutationScore = $NumberOfKilledMutants / $TotalNumberOfMutants

Write-Host ""
Write-Host "--- Summary of Mutation testing ---"
Write-Host "Total Number of mutants: $TotalNumberOfMutants"
Write-Host "Total Number of killed mutants: $NumberOfKilledMutants"
Write-Host "Total Number of live mutants: $NumberOfLiveMutants"
Write-Host "Mutation score for this project is: $MutationScore"

# End of script