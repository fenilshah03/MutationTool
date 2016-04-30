# Name: MutantTestScript.ps1
# Purpose: Execute JUnit test suit for the given mutants of a maven project and compute mutation score.

# Get directory path for mutants
# $MutantPath = Read-Host -Prompt 'Input your mutant directory path:'
# Write-Host "Your mutant direcotry is => $MutantPath"

$MutantPath = "C:\Users\Fenil\Documents\Eclipse projects\mutants"

# cd to mutants directory
cd $MutantPath

# Get all the mutant project directories
# One direcoty is the original project and all others, post-fixed with number, are mutants.
$MutantProjects = (Get-ChildItem | ?{ $_.PSIsContainer } | Select-Object FullName).FullName
$TotalNumberOfMutants = $MutantProjects.length - 1
Write-Host "Total number of mutants: $TotalNumberOfMutants"

# Keep track of killed mutants
$NumberOfKilledMutants = 0
$NumberOfLiveMutants = -1
$CurrentMutantStatus = 0

# Execute test site for each of the project
ForEach($MutantProject In $MutantProjects){
	# cd to mutant project direcotry
	cd $MutantProject
	
	Write-Host "*** Testing $MutantProject with 'mvn test' command ***"
	$CurrentMutantStatus = (mvn test | Select-String "Tests run: 92, Failures: 0, Errors: 0, Skipped: 0" | Measure-Object).Count
	
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