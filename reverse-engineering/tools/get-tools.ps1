# Downloads the two tools the experiments use into reverse-engineering/tools/ and checks
# their SHA-256 against the files the experiments were run with.
#   ProGuard 7.10.0 (obfuscator)  https://github.com/Guardsquare/proguard
#   CFR 0.152 (decompiler)        https://www.benf.org/other/cfr/
$ErrorActionPreference = 'Stop'
$here = Split-Path -Parent $MyInvocation.MyCommand.Path

$tools = @(
    @{ Name = 'proguard-7.10.0.zip'
       Url  = 'https://github.com/Guardsquare/proguard/releases/download/v7.10.0/proguard-7.10.0.zip'
       Sha  = '5aa88c434766a057a2ead3412acc01022bb444f9f1bfbae690a1d48b2006b242' },
    @{ Name = 'cfr-0.152.jar'
       Url  = 'https://repo1.maven.org/maven2/org/benf/cfr/0.152/cfr-0.152.jar'
       Sha  = 'f686e8f3ded377d7bc87d216a90e9e9512df4156e75b06c655a16648ae8765b2' }
)

foreach ($t in $tools) {
    $path = Join-Path $here $t.Name
    if (-not (Test-Path $path)) {
        Write-Host "Downloading $($t.Name)..."
        Invoke-WebRequest -Uri $t.Url -OutFile $path -UseBasicParsing
    }
    $actual = (Get-FileHash -Algorithm SHA256 $path).Hash.ToLower()
    if ($actual -ne $t.Sha) { throw "Checksum mismatch for $($t.Name): $actual" }
    Write-Host "OK  $($t.Name)"
}

$pg = Join-Path $here 'proguard-7.10.0'
if (-not (Test-Path $pg)) { Expand-Archive -Path (Join-Path $here 'proguard-7.10.0.zip') -DestinationPath $here }
Write-Host "Tools ready in $here"
