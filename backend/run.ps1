param (
    [string[]]$services
)

$jobs = New-Object -TypeName System.Collections.ArrayList
$jobDirs = @{}
$failedToStart = @()

function Start-BuildJob($dir) {
    Write-Host "Starting build for '$dir' in background..." -ForegroundColor Cyan
    $resolvedPath = Resolve-Path $dir
    $job = Start-Job -ScriptBlock {
        param($dirPath)
        Push-Location $dirPath
        if (Test-Path "./gradlew") {
            Write-Host "Building: $dirPath"
            ./gradlew jibDockerBuild
            if ($LASTEXITCODE -ne 0) {
                Write-Error "Failed to build in $dirPath"
                Exit 1
            }
        } else {
            Write-Warning "No gradlew found in $dirPath"
        }
        Pop-Location
    } -ArgumentList $resolvedPath

    if ($job -ne $null) {
        $jobs.Add($job)
        $jobDirs[$job.Id] = $resolvedPath
    } else {
        Write-Warning "Failed to start job for '$dir'"
        $failedToStart += $dir
    }
}

# Discover target directories
if ($services.Count -gt 0) {
    foreach ($service in $services) {
        if (Test-Path $service) {
            Start-BuildJob $service
        } else {
            Write-Warning "Directory '$service' not found"
        }
    }
} else {
    Write-Host "No services specified. Searching for all subdirectories with 'gradlew'..."
    Get-ChildItem -Directory | ForEach-Object {
        if (Test-Path "$($_.FullName)/gradlew") {
            Start-BuildJob $_.FullName
        }
    }
}

# Wait for all jobs to complete
Write-Host "`nWaiting for all builds to finish..." -ForegroundColor Yellow
Wait-Job $jobs

$successful = @()
$failed = @()

foreach ($job in $jobs) {
    $jobDir = $jobDirs[$job.Id]

    if ($job.State -eq "Completed" -and $job.ChildJobs[0].JobStateInfo.State -eq "Completed") {
        $successful += $jobDir
    } else {
        $failed += $jobDir
    }
    Remove-Job $job
}

# Print summary
Write-Host "`nBuild Summary:"
if ($successful.Count -gt 0) {
    Write-Host "Successful builds:" -ForegroundColor Green
    $successful | ForEach-Object { Write-Host " - $_" }
}
if ($failed.Count -gt 0) {
    Write-Host "Failed builds:" -ForegroundColor Red
    $failed | ForEach-Object { Write-Host " - $_" }
}
if ($failedToStart.Count -gt 0) {
    Write-Host "Failed to start jobs:" -ForegroundColor Red
    $failedToStart | ForEach-Object { Write-Host " - $_" }
}

# Exit if any build failed
if ($failed.Count -gt 0 -or $failedToStart.Count -gt 0) {
    Write-Error "`nSome builds failed. Docker Compose will not start."
    Exit 1
}

# Start Docker Compose
Write-Host "`nAll builds complete. Starting Docker Compose..." -ForegroundColor Green
docker compose up --build -d
