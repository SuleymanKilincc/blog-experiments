# Final step of the blind test, run by the person holding the hidden tests: runs every test
# input through the original program and through the reconstruction, and compares stdout and
# exit code byte for byte. Prints only counts and the positions of any differing tests.
#
#   powershell -ExecutionPolicy Bypass -File compare-hidden-tests.ps1 -Tests <dir> -Reconstruction <classes dir> [-Private <dir>] [-MainClass StoreApp]
param(
    [Parameter(Mandatory = $true)][string]$Tests,
    [Parameter(Mandatory = $true)][string]$Reconstruction,
    [string]$Private = (Join-Path $env:LOCALAPPDATA 'blind-test-private'),
    [string]$MainClass = 'StoreApp'
)
$ErrorActionPreference = 'Stop'
# java prints its settings on stderr; going through cmd keeps PowerShell from treating that as an error
$javaHome = (cmd /c "java -XshowSettings:properties -version 2>&1" | Select-String 'java.home').ToString().Split('=')[1].Trim()
$java = Join-Path $javaHome 'bin\java.exe'

if (-not (Test-Path "$Private\orig.jar")) { throw "Original program not found; run prepare-blind.ps1 first." }
if (-not (Test-Path $Reconstruction)) { throw "Reconstruction classes not found." }

$files = Get-ChildItem -Path $Tests -File | Sort-Object Name
$same = 0; $different = @(); $i = 0
foreach ($t in $files) {
    $i++
    $a = cmd /c "`"$java`" -jar `"$Private\orig.jar`" < `"$($t.FullName)`" 2>nul & echo EXIT=%ERRORLEVEL%" | Out-String
    $b = cmd /c "`"$java`" -cp `"$Reconstruction`" $MainClass < `"$($t.FullName)`" 2>nul & echo EXIT=%ERRORLEVEL%" | Out-String
    if ($a -eq $b) { $same++ } else { $different += $i }
}

Write-Host ""
Write-Host "Result: the reconstruction matched the original on $same / $($files.Count) test inputs."
if ($different.Count -gt 0) { Write-Host "Differing tests (by position): $($different -join ', ')" }
