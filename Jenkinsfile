pipeline {

    agent any

    options {
        timestamps()
        disableConcurrentBuilds()
    }

    parameters {
        choice(
            name: 'BROWSER',
            choices: [
                'Chrome (headless)',
                'Firefox (headless)',
                'Both'
            ],
            description: 'Pilih Browser (Abaikan jika memanggil Test Suite Collection)'
        )

        choice(
            name: 'PROFILE',
            choices: [
                'Development',
                'QA',
                'UAT',
                'Production'
            ],
            description: 'Execution Profile'
        )

        string(
            name: 'ENV',
            defaultValue: 'staging',
            description: 'Target Environment dari Telegram (staging, uat, prod)'
        )

        string(
            name: 'SUITE',
            defaultValue: 'Test Suites/WEB/Checkout/Post Payment Validation',
            description: 'Nama Test Suite dari Telegram'
        )

        string(
            name: 'TEST_PATH',
            defaultValue: '',
            description: '''
Kosong = Gunakan parameter SUITE / Default Test Suite

Contoh override manual:
-testSuitePath=Test Suites/WEB/Checkout/Post Payment Validation
'''
        )
    }

    environment {
        PROJECT_NAME = 'Pasti Ada Jalan'
        PROJECT_FILE = 'pasti-ada-jalan.prj'
        PROJECT_FOLDER = 'Pasti-ada-jalan'
        ONEDRIVE_ATTACHMENTS = 'C:\\Users\\AgungPriyadi\\OneDrive - (G)Tech Digital\\Attachments'
        USERPROFILE = 'C:\\Users\\AgungPriyadi'
        DEFAULT_TEST = 'Test Suites/WEB/Checkout/Post Payment Validation'
        KATALON_EXE = 'C:\\Users\\AgungPriyadi\\.katalon\\packages\\KS-11.1.3\\katalonc.exe'
        KATALON_API_KEY = credentials('katalon-api-key')
        KATALON_ORG_ID = '2078893'
        N8N_SHEETS_WEBHOOK = 'http://localhost:5678/webhook/79204498-fdea-41d3-a8a2-21b002f8b724'
    }

    stages {

        stage('Notify Start') {
            steps {
                script {
                    bat 'curl -X POST "http://localhost:5678/webhook/jenkins" -H "Content-Type: application/json" -d "{\\"job\\":\\"' + env.JOB_NAME + '\\",\\"projectName\\":\\"' + env.PROJECT_NAME + '\\",\\"buildNumber\\":' + env.BUILD_NUMBER + ',\\"browser\\":\\"' + params.BROWSER + '\\",\\"profile\\":\\"' + params.PROFILE + '\\",\\"status\\":\\"RUNNING\\",\\"phase\\":\\"STARTED\\"}"'
                }
            }
        }

        stage('Checkout Source') {
            steps {
                checkout scm
            }
        }

        stage('Prepare') {
            steps {
                script {
                    bat '''
                    taskkill /F /IM katalonc.exe /T 2>nul || exit 0
                    taskkill /F /IM java.exe /T 2>nul || exit 0
                    taskkill /F /IM chromedriver.exe /T 2>nul || exit 0
                    if exist "C:\\Users\\AgungPriyadi\\.katalon\\packages\\KS-11.1.3\\config\\.metadata\\.lock" del /f /q "C:\\Users\\AgungPriyadi\\.katalon\\packages\\KS-11.1.3\\config\\.metadata\\.lock" 2>nul || exit 0
                    
                    :: Bersihkan folder eksekusi lama agar hasil test case tidak terakumulasi
                    if exist Reports rmdir /s /q Reports
                    if exist Screenshot rmdir /s /q Screenshot
                    if exist summary.json del /f /q summary.json
                    if exist test_results.json del /f /q test_results.json
                    if exist error_log.txt del /f /q error_log.txt
                    if exist failed_tests.json del /f /q failed_tests.json
                    if exist Failure_Report.zip del /f /q Failure_Report.zip
                    '''

                    if (params.ENV?.trim()) {
                        def envInput = params.ENV.toLowerCase()
                        if (envInput == 'prod' || envInput == 'production') {
                            env.TARGET_PROFILE = 'Production'
                        } else if (envInput == 'uat') {
                            env.TARGET_PROFILE = 'UAT'
                        } else if (envInput == 'qa') {
                            env.TARGET_PROFILE = 'QA'
                        } else {
                            env.TARGET_PROFILE = 'Development'
                        }
                    } else {
                        env.TARGET_PROFILE = params.PROFILE ?: 'Development'
                    }

                    if (params.TEST_PATH?.trim()) {
                        def value = params.TEST_PATH.split("=")
                        env.ARG_TYPE = value[0]
                        env.FINAL_PATH = value[1]
                    } else if (params.SUITE?.trim()) {
                        def suiteInput = params.SUITE.trim()
                        if (suiteInput.startsWith("Test Suites/")) {
                            env.FINAL_PATH = suiteInput
                        } else if (suiteInput.toLowerCase() == 'regression') {
                            env.FINAL_PATH = env.DEFAULT_TEST
                        } else {
                            env.FINAL_PATH = "Test Suites/${suiteInput}"
                        }
                    } else {
                        env.FINAL_PATH = env.DEFAULT_TEST
                    }

                    if (!params.TEST_PATH?.trim()) {
                        if (env.FINAL_PATH.contains("Collection") || env.FINAL_PATH.contains("Web_Test_Suite_Collection")) {
                            env.ARG_TYPE = "-testSuiteCollectionPath"
                        } else {
                            env.ARG_TYPE = "-testSuitePath"
                        }
                    }

                    if (env.ARG_TYPE == "-testSuiteCollectionPath") {
                        env.EXTRA_ARGS = ""
                    } else {
                        env.EXTRA_ARGS = "-executionProfile=\"${env.TARGET_PROFILE}\" -browserType=\"Chrome (headless)\""
                    }

                    echo "====================================="
                    echo "PROJECT : ${env.PROJECT_FILE}"
                    echo "PROFILE : ${env.TARGET_PROFILE}"
                    echo "BROWSER : ${params.BROWSER}"
                    echo "ARGTYPE : ${env.ARG_TYPE}"
                    echo "PATH    : ${env.FINAL_PATH}"
                    echo "ORG ID  : ${env.KATALON_ORG_ID}"
                    echo "====================================="
                }
            }
        }

        stage('Run Chrome') {
            when {
                anyOf {
                    expression { params.BROWSER == 'Chrome (headless)' }
                    expression { params.BROWSER == 'Both' }
                    expression { env.ARG_TYPE == '-testSuiteCollectionPath' }
                }
            }
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'UNSTABLE') {
                    bat """
"${env.KATALON_EXE}" ^
-noSplash ^
-runMode=console ^
-projectPath="%WORKSPACE%\\${env.PROJECT_FILE}" ^
-retry=0 ^
-apiKey="${env.KATALON_API_KEY}" ^
-orgID="${env.KATALON_ORG_ID}" ^
${env.ARG_TYPE}="${env.FINAL_PATH}" ^
${env.EXTRA_ARGS} ^
--config ^
-webui.autoUpdateDrivers=true ^
-webui.chrome.args="--disable-blink-features=AutomationControlled --disable-dev-shm-usage --disable-gpu --no-sandbox --window-size=1920,1080"
"""
                }
            }
        }

        stage('Run Firefox') {
            when {
                allOf {
                    expression { env.ARG_TYPE == '-testSuitePath' }
                    anyOf {
                        expression { params.BROWSER == 'Firefox (headless)' }
                        expression { params.BROWSER == 'Both' }
                    }
                }
            }
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'UNSTABLE') {
                    bat """
"${env.KATALON_EXE}" ^
-noSplash ^
-runMode=console ^
-projectPath="%WORKSPACE%\\${env.PROJECT_FILE}" ^
-retry=0 ^
-apiKey="${env.KATALON_API_KEY}" ^
-orgID="${env.KATALON_ORG_ID}" ^
${env.ARG_TYPE}="${env.FINAL_PATH}" ^
-executionProfile="${env.TARGET_PROFILE}" ^
-browserType="Firefox (headless)" ^
--config ^
-webui.autoUpdateDrivers=true
"""
                }
            }
        }
    }

    post {

        always {
            archiveArtifacts(
                artifacts: 'Reports/**, Screenshot/**, failure_*.html',
                allowEmptyArchive: true
            )

            junit(
                allowEmptyResults: true,
                testResults: 'Reports/**/*.xml'
            )

            script {
                def currentStatus = currentBuild.currentResult ?: 'UNKNOWN'
                
                bat """
                powershell -NoProfile -ExecutionPolicy Bypass -Command "\
                    try { \
                        \$dateStr = (Get-Date).ToString('dd-MM-yyyy'); \
                        \$browser = if ('${params.BROWSER}' -like '*Firefox*') { 'Firefox Headless' } else { 'Chrome Headless' }; \
                        \$targetBase = 'C:\\Users\\AgungPriyadi\\OneDrive - (G)Tech Digital\\Attachments\\Pasti-ada-jalan'; \
                        if (-not (Test-Path \$targetBase)) { \
                            \$found = Get-ChildItem -Path 'C:\\Users\\AgungPriyadi' -Filter 'Pasti-ada-jalan' -Recurse -Directory -ErrorAction SilentlyContinue | Select-Object -First 1; \
                            if (\$found) { \$targetBase = \$found.FullName } \
                        }; \
                        \$dest = Join-Path (Join-Path \$targetBase \$dateStr) \$browser; \
                        if (-not (Test-Path \$dest)) { New-Item -ItemType Directory -Path \$dest -Force | Out-Null }; \
                        \$reports = Get-ChildItem -Path 'Reports' -Filter '*.html' -Recurse -ErrorAction SilentlyContinue; \
                        if (\$reports) { \
                            \$reports | ForEach-Object { \
                                \$curr = \$_.Directory; \
                                \$modName = ''; \
                                while (\$curr -and \$curr.Name -ne 'Reports' -and \$curr.FullName -ne \$env:WORKSPACE) { \
                                    if (\$curr.Name -notmatch '^\\d{8}_\\d{6}\$' -and \$curr.Name -ne 'Test Suites') { \$modName = \$curr.Name; break }; \
                                    \$curr = \$curr.Parent \
                                }; \
                                if (-not \$modName) { \$modName = if ('${params.SUITE}') { (Split-Path '${params.SUITE}' -Leaf) } else { 'Test_Report' } }; \
                                \$safeName = (\$modName -replace '[^a-zA-Z0-9_\\- ]', '_').Trim(); \
                                \$destFile = Join-Path \$dest (\$safeName + '.html'); \
                                Copy-Item -Path \$_.FullName -Destination \$destFile -Force; \
                                Write-Host ('Copied HTML: ' + \$safeName + '.html') \
                            } \
                        } \
                    } catch { Write-Host ('Error copy OneDrive: ' + \$_.Exception.Message) }; \
                    \$p=0; \$f=0; \$s=0; \
                    \$latestFolder = Get-ChildItem -Path 'Reports' -Directory -Recurse | Where-Object { \$_.Name -match '^\\d{8}_\\d{6}\$' } | Sort-Object LastWriteTime -Descending | Select-Object -First 1; \
                    \$searchRoot = if (\$latestFolder) { \$latestFolder.FullName } else { 'Reports' }; \
                    Get-ChildItem -Path \$searchRoot -Filter '*.xml' -Recurse -ErrorAction SilentlyContinue | ForEach-Object { \
                        [xml]\$x = Get-Content \$_.FullName; \
                        foreach(\$ts in \$x.SelectNodes('//testsuite')){ \
                            \$t=[int]\$ts.tests; \
                            \$fail=[int]\$ts.failures + [int]\$ts.errors; \
                            \$skip=[int]\$ts.skipped; \
                            \$pass=\$t - (\$fail + \$skip); \
                            if(\$pass -gt 0){\$p+=\$pass}; \
                            \$f+=\$fail; \
                            \$s+=\$skip \
                        } \
                    }; \
                    \$body = @{ \
                        job = '${env.JOB_NAME}'; \
                        projectName = '${env.PROJECT_NAME}'; \
                        buildNumber = [int]${env.BUILD_NUMBER}; \
                        status = '${currentStatus}'; \
                        phase = 'COMPLETED'; \
                        passed = \$p; \
                        failed = \$f; \
                        skipped = \$s \
                    } | ConvertTo-Json; \
                    Set-Content -Path 'summary.json' -Value \$body; \
                    Invoke-RestMethod -Uri 'http://localhost:5678/webhook/jenkins' -Method Post -ContentType 'application/json' -Body \$body; \
                    Write-Host 'SUCCESS: Finished Webhook sent to n8n' \
                "
                """
            }

            echo ""
            echo "======================================"
            echo "Automation Finished"
            echo "======================================"
        }

        success {
            echo "Automation SUCCESS"
        }

        unstable {
            echo "Automation UNSTABLE - Preparing Compact Zip, AI Error Log & Sending to Google Sheets..."
            script {
                bat """
                powershell -NoProfile -ExecutionPolicy Bypass -Command "\
                    try { \
                        \$tempZip = Join-Path \$env:TEMP 'PastiAdaJalan_Failures'; \
                        if (Test-Path \$tempZip) { Remove-Item \$tempZip -Recurse -Force -ErrorAction SilentlyContinue }; \
                        New-Item -ItemType Directory -Path (Join-Path \$tempZip 'Reports') -Force | Out-Null; \
                        Get-ChildItem -Path 'Reports' -Recurse -File -ErrorAction SilentlyContinue | Where-Object { \$_.Extension -in '.html', '.xml', '.log', '.properties' } | ForEach-Object { \
                            \$rel = \$_.FullName.Substring((Get-Item 'Reports').FullName.Length); \
                            \$targetFile = Join-Path (Join-Path \$tempZip 'Reports') \$rel; \
                            \$targetDir = Split-Path \$targetFile -Parent; \
                            if (-not (Test-Path \$targetDir)) { New-Item -ItemType Directory -Path \$targetDir -Force | Out-Null }; \
                            Copy-Item -Path \$_.FullName -Destination \$targetFile -Force; \
                        }; \
                        if (Test-Path 'Screenshot') { \
                            New-Item -ItemType Directory -Path (Join-Path \$tempZip 'Screenshot') -Force | Out-Null; \
                            Get-ChildItem -Path 'Screenshot' -Filter '*.png' -Recurse -ErrorAction SilentlyContinue | Sort-Object LastWriteTime -Descending | Select-Object -First 10 | ForEach-Object { \
                                Copy-Item -Path \$_.FullName -Destination (Join-Path \$tempZip 'Screenshot') -Force; \
                            }; \
                        }; \
                        if (Test-Path 'Failure_Report.zip') { Remove-Item 'Failure_Report.zip' -Force -ErrorAction SilentlyContinue }; \
                        Compress-Archive -Path (Join-Path \$tempZip '*') -DestinationPath 'Failure_Report.zip' -CompressionLevel Optimal -Force -ErrorAction SilentlyContinue; \
                        Remove-Item \$tempZip -Recurse -Force -ErrorAction SilentlyContinue; \
                    } catch { Write-Host ('Error creating zip: ' + \$_.Exception.Message) }; \
                    \$errs = @(); \
                    \$tcList = @(); \
                    \$i = 1; \
                    \$latestFolder = Get-ChildItem -Path 'Reports' -Directory -Recurse | Where-Object { \$_.Name -match '^\\d{8}_\\d{6}\$' } | Sort-Object LastWriteTime -Descending | Select-Object -First 1; \
                    \$searchRoot = if (\$latestFolder) { \$latestFolder.FullName } else { 'Reports' }; \
                    Get-ChildItem -Path \$searchRoot -Filter '*.xml' -Recurse -ErrorAction SilentlyContinue | ForEach-Object { \
                        [xml]\$x = Get-Content \$_.FullName; \
                        foreach(\$ts in \$x.SelectNodes('//testsuite')){ \
                            \$tsName = \$ts.name; \
                            foreach(\$tc in \$ts.SelectNodes('.//testcase[failure or error]')){ \
                                \$node = if(\$tc.failure){\$tc.failure}else{\$tc.error}; \
                                \$msg = \$node.message; \
                                if([string]::IsNullOrWhiteSpace(\$msg)){ \$msg = \$node.innerText }; \
                                if([string]::IsNullOrWhiteSpace(\$msg)){ \$msg = \$tc.'system-err' }; \
                                if([string]::IsNullOrWhiteSpace(\$msg)){ \$msg = 'No detailed error message found in XML.' }; \
                                \$errs += ('[Test Case]: ' + \$tc.name + [Environment]::NewLine + '[Error]: ' + \$msg); \
                                \$tcList += @{ number = [string]\$i; testSuiteName = \$tsName; testCaseName = [string]\$tc.name; status = 'failed'; errorMessage = [string]\$msg; reportUrl = '${env.BUILD_URL}' }; \
                                \$i++ \
                            } \
                        } \
                    }; \
                    if (\$errs.Count -eq 0) { \$errs += 'No detailed XML stacktrace found.' }; \
                    Set-Content -Path 'error_log.txt' -Value (\$errs -join ([Environment]::NewLine + '---' + [Environment]::NewLine)); \
                    \$jsonPayload = @{ projectName = '${env.PROJECT_NAME}'; jobName = '${env.JOB_NAME}'; buildUrl = '${env.BUILD_URL}'; buildNumber = [int]${env.BUILD_NUMBER}; testCases = \$tcList } | ConvertTo-Json -Depth 5; \
                    Set-Content -Path 'failed_tests.json' -Value \$jsonPayload; \
                "

                curl.exe -X POST "http://localhost:5678/webhook/jenkins-report" -F "chat_id=8122375919" -F "file=@Failure_Report.zip" -F "error_log=@error_log.txt"

                powershell -NoProfile -ExecutionPolicy Bypass -Command "Invoke-RestMethod -Uri '${env.N8N_SHEETS_WEBHOOK}' -Method Post -ContentType 'application/json' -InFile 'failed_tests.json'"
                """
            }
        }

        failure {
            echo "Automation FAILED - Preparing Compact Zip, AI Error Log & Sending to Google Sheets..."
            script {
                bat """
                powershell -NoProfile -ExecutionPolicy Bypass -Command "\
                    try { \
                        \$tempZip = Join-Path \$env:TEMP 'PastiAdaJalan_Failures'; \
                        if (Test-Path \$tempZip) { Remove-Item \$tempZip -Recurse -Force -ErrorAction SilentlyContinue }; \
                        New-Item -ItemType Directory -Path (Join-Path \$tempZip 'Reports') -Force | Out-Null; \
                        Get-ChildItem -Path 'Reports' -Recurse -File -ErrorAction SilentlyContinue | Where-Object { \$_.Extension -in '.html', '.xml', '.log', '.properties' } | ForEach-Object { \
                            \$rel = \$_.FullName.Substring((Get-Item 'Reports').FullName.Length); \
                            \$targetFile = Join-Path (Join-Path \$tempZip 'Reports') \$rel; \
                            \$targetDir = Split-Path \$targetFile -Parent; \
                            if (-not (Test-Path \$targetDir)) { New-Item -ItemType Directory -Path \$targetDir -Force | Out-Null }; \
                            Copy-Item -Path \$_.FullName -Destination \$targetFile -Force; \
                        }; \
                        if (Test-Path 'Screenshot') { \
                            New-Item -ItemType Directory -Path (Join-Path \$tempZip 'Screenshot') -Force | Out-Null; \
                            Get-ChildItem -Path 'Screenshot' -Filter '*.png' -Recurse -ErrorAction SilentlyContinue | Sort-Object LastWriteTime -Descending | Select-Object -First 10 | ForEach-Object { \
                                Copy-Item -Path \$_.FullName -Destination (Join-Path \$tempZip 'Screenshot') -Force; \
                            }; \
                        }; \
                        if (Test-Path 'Failure_Report.zip') { Remove-Item 'Failure_Report.zip' -Force -ErrorAction SilentlyContinue }; \
                        Compress-Archive -Path (Join-Path \$tempZip '*') -DestinationPath 'Failure_Report.zip' -CompressionLevel Optimal -Force -ErrorAction SilentlyContinue; \
                        Remove-Item \$tempZip -Recurse -Force -ErrorAction SilentlyContinue; \
                    } catch { Write-Host ('Error creating zip: ' + \$_.Exception.Message) }; \
                    \$errs = @(); \
                    \$tcList = @(); \
                    \$i = 1; \
                    \$latestFolder = Get-ChildItem -Path 'Reports' -Directory -Recurse | Where-Object { \$_.Name -match '^\\d{8}_\\d{6}\$' } | Sort-Object LastWriteTime -Descending | Select-Object -First 1; \
                    \$searchRoot = if (\$latestFolder) { \$latestFolder.FullName } else { 'Reports' }; \
                    Get-ChildItem -Path \$searchRoot -Filter '*.xml' -Recurse -ErrorAction SilentlyContinue | ForEach-Object { \
                        [xml]\$x = Get-Content \$_.FullName; \
                        foreach(\$ts in \$x.SelectNodes('//testsuite')){ \
                            \$tsName = \$ts.name; \
                            foreach(\$tc in \$ts.SelectNodes('.//testcase[failure or error]')){ \
                                \$node = if(\$tc.failure){\$tc.failure}else{\$tc.error}; \
                                \$msg = \$node.message; \
                                if([string]::IsNullOrWhiteSpace(\$msg)){ \$msg = \$node.innerText }; \
                                if([string]::IsNullOrWhiteSpace(\$msg)){ \$msg = \$tc.'system-err' }; \
                                if([string]::IsNullOrWhiteSpace(\$msg)){ \$msg = 'No detailed error message found in XML.' }; \
                                \$errs += ('[Test Case]: ' + \$tc.name + [Environment]::NewLine + '[Error]: ' + \$msg); \
                                \$tcList += @{ number = [string]\$i; testSuiteName = \$tsName; testCaseName = [string]\$tc.name; status = 'failed'; errorMessage = [string]\$msg; reportUrl = '${env.BUILD_URL}' }; \
                                \$i++ \
                            } \
                        } \
                    }; \
                    if (\$errs.Count -eq 0) { \$errs += 'No detailed XML stacktrace found.' }; \
                    Set-Content -Path 'error_log.txt' -Value (\$errs -join ([Environment]::NewLine + '---' + [Environment]::NewLine)); \
                    \$jsonPayload = @{ projectName = '${env.PROJECT_NAME}'; jobName = '${env.JOB_NAME}'; buildUrl = '${env.BUILD_URL}'; buildNumber = [int]${env.BUILD_NUMBER}; testCases = \$tcList } | ConvertTo-Json -Depth 5; \
                    Set-Content -Path 'failed_tests.json' -Value \$jsonPayload; \
                "

                curl.exe -X POST "http://localhost:5678/webhook/jenkins-report" -F "chat_id=8122375919" -F "file=@Failure_Report.zip" -F "error_log=@error_log.txt"

                powershell -NoProfile -ExecutionPolicy Bypass -Command "Invoke-RestMethod -Uri '${env.N8N_SHEETS_WEBHOOK}' -Method Post -ContentType 'application/json' -InFile 'failed_tests.json'"
                """
            }
        }

    }

}
