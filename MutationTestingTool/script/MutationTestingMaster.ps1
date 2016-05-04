# Name: MutantTestingMaster.ps1
# Purpose: Execute MutationInsertion.jar to create mutants and then run ExecuteMutantTest.ps1 to test generated mutants.

Write-Host "Input your MutationTestingTool directory path."
Write-Host "NOTE: For example, path to MutationTestingTool is path/to/parent/MutationTestingTool"
$ToolPath = Read-Host -Prompt 'Enter Path: '
Write-Host "Your path is => $ToolPath"
Write-Host ""

Write-Host "Input your mutant directory path where you have original program:"
Write-Host "NOTE: Please enter path like this: path\\to\\mutant\\directory\\geo-master"
$MutantPath = Read-Host -Prompt 'Enter Path: '
Write-Host "Your mutant directory is => $MutantPath"
Write-Host ""

Write-Host "*** Starting mutation insertion ***"
Write-Host "Please wait while it completes..."
Write-Host "It is creating mutant projects in the same directory..."
# start jar file for mutation insertion
Start-Process `"$ToolPath\jar\MutationInsertion.jar`" `"$MutantPath`" -wait

Write-Host "Check insertion report at parent direcory of $MutantPath in MutationInsertionReport.txt file."
Write-Host "*** Mutation insertion complete. ***"
Write-Host ""

Write-Host "*** Starting mutation testing... ***"
# start testing script
Invoke-Expression "& `"$ToolPath\script\ExecuteMutantTest.ps1`""

# End of script