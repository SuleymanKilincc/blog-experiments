# Blind-test preparation, run by the person who has the source code.
# Compiles the program without debug information, obfuscates every class, field and method
# name (the entry class included), and leaves only obf.jar in -Out. The source, the original
# jar and the name mapping stay in -Private, which the reverse engineer must not open.
# Nothing it prints contains a class, method or file name.
#
#   powershell -ExecutionPolicy Bypass -File prepare-blind.ps1 -Source <src dir> -Out <dir> [-Private <dir>] [-Tests <dir>]
param(
    [Parameter(Mandatory = $true)][string]$Source,
    [Parameter(Mandatory = $true)][string]$Out,
    [string]$Private = (Join-Path $env:LOCALAPPDATA 'blind-test-private'),
    [string]$Tests = ''
)
$ErrorActionPreference = 'Stop'

$tools = Join-Path (Split-Path -Parent (Split-Path -Parent (Split-Path -Parent $MyInvocation.MyCommand.Path))) 'tools'
$proguard = Join-Path $tools 'proguard-7.10.0\lib\proguard.jar'
if (-not (Test-Path $proguard)) { throw "ProGuard not found; run tools\get-tools.ps1 first." }
# java prints its settings on stderr; going through cmd keeps PowerShell from treating that as an error
$javaHome = (cmd /c "java -XshowSettings:properties -version 2>&1" | Select-String 'java.home').ToString().Split('=')[1].Trim()
$bin = Join-Path $javaHome 'bin'

if (-not (Test-Path $Source)) { throw "Source folder not found." }
Remove-Item -Recurse -Force $Private, $Out -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Force "$Private\classes", $Out | Out-Null

# 1) Compile without debug information, like a released program
$sources = Get-ChildItem -Path $Source -Recurse -Filter *.java
if ($sources.Count -eq 0) { throw "No .java files in the source folder." }
$lines = ($sources | Get-Content | Measure-Object -Line).Lines
$sources.FullName | ForEach-Object { '"' + ($_ -replace '\\', '/') + '"' } | Set-Content -Encoding ascii "$Private\sources.txt"
& "$bin\javac.exe" -g:none --release 21 -encoding UTF-8 -d "$Private\classes" "@$Private\sources.txt"
if ($LASTEXITCODE -ne 0) { throw "Compilation failed." }

# 2) Find the class with main() (its name is not printed)
$mains = $sources | Where-Object { Select-String -Path $_.FullName -Pattern 'public\s+static\s+void\s+main\s*\(' -Quiet }
if ($mains.Count -ne 1) { throw "Expected exactly one file with a main method, found $($mains.Count)." }
$package = Select-String -Path $mains[0].FullName -Pattern '^\s*package\s+([\w.]+)\s*;' | Select-Object -First 1
$mainClass = [IO.Path]::GetFileNameWithoutExtension($mains[0].Name)
if ($package) { $mainClass = $package.Matches[0].Groups[1].Value + '.' + $mainClass }
"Main-Class: $mainClass`r`n" | Set-Content -Encoding ascii -NoNewline "$Private\manifest.txt"
& "$bin\jar.exe" --create --file "$Private\orig.jar" --manifest "$Private\manifest.txt" -C "$Private\classes" .

# 3) ProGuard: rename every class, field and method (keeping only the name "main"), flatten packages
$config = @"
-injars '$Private\orig.jar'
-outjars '$Out\obf.jar'
-libraryjars '$javaHome\jmods\java.base.jmod'(!**.jar;!module-info.class)
-dontshrink
-dontoptimize
-dontwarn
-repackageclasses ''
-adaptresourcefilecontents META-INF/MANIFEST.MF
-keepclassmembers class * { public static void main(java.lang.String[]); }
-keepclassmembers enum * { public static **[] values(); public static ** valueOf(java.lang.String); }
-printmapping '$Private\mapping.txt'
"@
# ProGuard rejects a byte-order mark, so the config is written as UTF-8 without one
[IO.File]::WriteAllText("$Private\pg.pro", $config, (New-Object Text.UTF8Encoding($false)))
& "$bin\java.exe" -jar $proguard "@$Private\pg.pro" *> "$Private\pg.log"
if ($LASTEXITCODE -ne 0) { throw "ProGuard failed (see pg.log in the private folder)." }

# 4) Check the obfuscated program still behaves like the original (stdout and exit code;
#    stack traces are excluded because they contain the obfuscated class names)
$same = 0; $total = 0
if ($Tests -ne '' -and (Test-Path $Tests)) {
    foreach ($t in Get-ChildItem -Path $Tests -File) {
        $total++
        $a = cmd /c "`"$bin\java.exe`" -jar `"$Private\orig.jar`" < `"$($t.FullName)`" 2>nul & echo EXIT=%ERRORLEVEL%" | Out-String
        $b = cmd /c "`"$bin\java.exe`" -jar `"$Out\obf.jar`" < `"$($t.FullName)`" 2>nul & echo EXIT=%ERRORLEVEL%" | Out-String
        if ($a -eq $b) { $same++ }
    }
}

Write-Host ""
Write-Host "Done."
Write-Host "  Java files : $($sources.Count)"
Write-Host "  Lines      : $lines"
if ($total -gt 0) { Write-Host "  Check      : obfuscated program matched the original on $same / $total test inputs" }
else { Write-Host "  Check      : no test inputs given" }
Write-Host "  For the reverse engineer: $Out\obf.jar"
