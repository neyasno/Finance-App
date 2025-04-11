param (
    [string[]]$services
)

function Build-Service($dir) {
    Write-Host "Building service in '$dir'..." -ForegroundColor Cyan
    Push-Location $dir
    if (Test-Path "./gradlew") {
        ./gradlew jibDockerBuild
        if ($LASTEXITCODE -ne 0) {
            Write-Error "Failed to build service in '$dir'"
            Exit 1
        }
    } else {
        Write-Warning "No gradlew found in '$dir', skipping"
    }
    Pop-Location
}

if ($services.Count -gt 0) {
    foreach ($service in $services) {
        if (Test-Path $service) {
            Build-Service $service
        } else {
            Write-Warning "Directory '$service' not found"
        }
    }
} else {
    Write-Host "No services specified. Searching for all subdirectories with 'gradlew'..."
    Get-ChildItem -Directory | ForEach-Object {
        if (Test-Path "$($_.FullName)/gradlew") {
            Build-Service $_.FullName
        }
    }
}

Write-Host "`Starting Docker Compose..." -ForegroundColor Green
docker compose up --build -d
