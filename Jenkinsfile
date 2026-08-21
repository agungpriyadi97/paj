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
                'Both',
                'Chrome (headless)',
                'Firefox (headless)'
            ],
            description: 'Pilih Browser (Both = Menjalankan Chrome lalu Firefox)'
        )

        choice(
            name: 'PROFILE',
            choices: [
                'Development',
                'QA',
                'UAT',
                'Production',
                'default'
            ],
            description: 'Execution Profile'
        )

        string(
            name: 'ENV',
            defaultValue: 'staging',
            description: 'Target Environment dari Telegram (dev, qa, uat, prod, staging)'
        )

        string(
            name: 'SUITE',
            defaultValue: 'Test Suites/WEB/Web_Test_Suite_Collection/Regression_pasti_ada_jalan_Web',
            description: 'Nama Test Suite / Collection dari Telegram'
        )

        string(
            name: 'TEST_PATH',
            defaultValue: '',
            description: '''
Kosong = Gunakan parameter SUITE / Default Test Suite Collection

Contoh override manual:
-testSuiteCollectionPath=Test Suites/WEB/Web_Test_Suite_Collection/Regression_pasti_ada_jalan_Web
'''
        )
    }

    environment {
        PROJECT_NAME = 'Pasti Ada Jalan'
        PROJECT_FILE = 'pasti-ada-jalan.prj'
        PROJECT_FOLDER = 'Pasti-ada-jalan'
        ONEDRIVE_ATTACHMENTS = 'C:\\Users\\AgungPriyadi\\OneDrive - (G)Tech Digital\\Attachments'
        USERPROFILE = 'C:\\Users\\AgungPriyadi'
        DEFAULT_TEST = 'Test Suites/WEB/Web_Test_Suite_Collection/Regression_pasti_ada_jalan_Web'
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
                    taskkill /F /IM katalon.exe /T 2>nul || exit 0
                    taskkill /F /IM katalonc.exe /T 2>nul || exit 0
                    taskkill /F /IM java.exe /T 2>nul || exit 0
                    taskkill /F /IM javaw.exe /T 2>nul || exit 0
                    taskkill /F /IM chromedriver.exe /T 2>nul || exit 0
                    taskkill /F /IM geckodriver.exe /T 2>nul || exit 0
                    taskkill /F /IM chrome.exe /T 2>nul || exit 0
                    taskkill /F /IM firefox.exe /T 2>nul || exit 0
                    timeout /t 2 /nobreak >nul 2>&1 || exit 0
                    if exist "C:\\Users\\AgungPriyadi\\.katalon\\packages\\KS-11.1.3\\config\\.metadata\\.lock" del /f /q "C:\\Users\\AgungPriyadi\\.katalon\\packages\\KS-11.1.3\\config\\.metadata\\.lock" 2>nul || exit 0
                    '''

                    bat '''
                    powershell -NoProfile -ExecutionPolicy Bypass -Command "\
                        $ErrorActionPreference = 'SilentlyContinue'; \
                        if (Test-Path 'Reports') { Remove-Item -Path 'Reports' -Recurse -Force -ErrorAction SilentlyContinue }; \
                        if (Test-Path 'Reports_Archive') { Remove-Item -Path 'Reports_Archive' -Recurse -Force -ErrorAction SilentlyContinue }; \
                        if (Test-Path 'Screenshot') { Remove-Item -Path 'Screenshot' -Recurse -Force -ErrorAction SilentlyContinue }; \
                        if (Test-Path 'summary.json') { Remove-Item 'summary.json' -Force -ErrorAction SilentlyContinue }; \
                        if (Test-Path 'test_results.json') { Remove-Item 'test_results.json' -Force -ErrorAction SilentlyContinue }; \
                        if (Test-Path 'failed_tests.json') { Remove-Item 'failed_tests.json' -Force -ErrorAction SilentlyContinue }; \
                        if (Test-Path 'error_log.txt') { Remove-Item 'error_log.txt' -Force -ErrorAction SilentlyContinue }; \
                        if (Test-Path 'Failure_Report.zip') { Remove-Item 'Failure_Report.zip' -Force -ErrorAction SilentlyContinue }; \
                    "
                    '''

                    // Mapping Execution Profile
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

                    // Mapping Target Test Suite / Collection
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
                    expression { params.BROWSER == 'Chrome' }
                    expression { params.BROWSER == 'Both' }
                }
            }
            steps {
                script {
                    echo "--- STARTING CHROME EXECUTION ---"
                    def cmd = "\"${env.KATALON_EXE}\" -clean -noSplash -runMode=console -projectPath=\"%WORKSPACE%\\${env.PROJECT_FILE}\" -retry=0 -apiKey=\"${env.KATALON_API_KEY}\" -orgID=\"${env.KATALON_ORG_ID}\" ${env.ARG_TYPE}=\"${env.FINAL_PATH}\" -executionProfile=\"${env.TARGET_PROFILE}\" -browserType=\"Chrome (headless)\" --config -webui.autoUpdateDrivers=true -webui.chrome.args=\"--disable-blink-features=AutomationControlled --disable-dev-shm-usage --disable-gpu --no-sandbox --window-size=1920,1080\""
                    
                    def exitCode = bat(script: cmd, returnStatus: true)
                    echo "Chrome Finished with Exit Code: ${exitCode}"
                    if (exitCode != 0) {
                        currentBuild.result = 'UNSTABLE'
                    }

                    // Salin HTML Chrome ke OneDrive & Arsipkan
                    bat(script: """
                    powershell -NoProfile -ExecutionPolicy Bypass -Command "\
                        \$ErrorActionPreference = 'SilentlyContinue'; \
                        try { \
                            \$dateStr = (Get-Date).ToString('dd-MM-yyyy'); \
                            \$dest = Join-Path (Join-Path 'C:\\Users\\AgungPriyadi\\OneDrive - (G)Tech Digital\\Attachments\\${env.PROJECT_FOLDER}' \$dateStr) 'Chrome Headless'; \
                            if (-not (Test-Path \$dest)) { New-Item -ItemType Directory -Path \$dest -Force | Out-Null }; \
                            if (Test-Path 'Reports') { \
                                Get-ChildItem -Path 'Reports' -Filter '*.html' -Recurse -ErrorAction SilentlyContinue | ForEach-Object { \
                                    \$curr = \$_.Directory; \
                                    \$modName = ''; \
                                    while (\$curr -and \$curr.Name -ne 'Reports' -and \$curr.FullName -ne \$env:WORKSPACE) { \
                                        if (\$curr.Name -notmatch '^\\d{8}_\\d{6}\$' -and \$curr.Name -ne 'Test Suites') { \$modName = \$curr.Name; break }; \
                                        \$curr = \$curr.Parent \
                                    }; \
                                    if (-not \$modName) { \$modName = if ('${params.SUITE}') { (Split-Path '${params.SUITE}' -Leaf) } else { 'Test_Report' } }; \
                                    \$destFile = Join-Path \$dest (\$modName + '.html'); \
                                    Copy-Item -Path \$_.FullName -Destination \$destFile -Force -ErrorAction SilentlyContinue; \
                                    Write-Host ('Copied Chrome HTML: ' + \$modName + '.html') \
                                }; \
                                New-Item -ItemType Directory -Path 'Reports_Archive\\Chrome' -Force | Out-Null; \
                                Copy-Item -Path 'Reports\\*' -Destination 'Reports_Archive\\Chrome' -Recurse -Force -ErrorAction SilentlyContinue; \
                                Remove-Item -Path 'Reports' -Recurse -Force -ErrorAction SilentlyContinue; \
                            } \
                        } catch { Write-Host ('Handled Chrome Copy Notice: ' + \$_.Exception.Message) } \
                    "
                    """, returnStatus: true)

                    bat(script: '''
                    taskkill /F /IM katalonc.exe /T 2>nul || exit 0
                    taskkill /F /IM java.exe /T 2>nul || exit 0
                    taskkill /F /IM javaw.exe /T 2>nul || exit 0
                    taskkill /F /IM chromedriver.exe /T 2>nul || exit 0
                    taskkill /F /IM chrome.exe /T 2>nul || exit 0
                    timeout /t 2 /nobreak >nul 2>&1 || exit 0
                    if exist "C:\\Users\\AgungPriyadi\\.katalon\\packages\\KS-11.1.3\\config\\.metadata\\.lock" del /f /q "C:\\Users\\AgungPriyadi\\.katalon\\packages\\KS-11.1.3\\config\\.metadata\\.lock" 2>nul || exit 0
                    ''', returnStatus: true)
                }
            }
        }

        stage('Run Firefox') {
            when {
                anyOf {
                    expression { params.BROWSER == 'Firefox (headless)' }
                    expression { params.BROWSER == 'Firefox' }
                    expression { params.BROWSER == 'Both' }
                }
            }
            steps {
                script {
                    echo "--- STARTING FIREFOX EXECUTION ---"
                    bat(script: '''
                    if exist "C:\\Users\\AgungPriyadi\\.katalon\\packages\\KS-11.1.3\\config\\.metadata\\.lock" del /f /q "C:\\Users\\AgungPriyadi\\.katalon\\packages\\KS-11.1.3\\config\\.metadata\\.lock" 2>nul || exit 0
                    ''', returnStatus: true)

                    def cmd = "\"${env.KATALON_EXE}\" -clean -noSplash -runMode=console -projectPath=\"%WORKSPACE%\\${env.PROJECT_FILE}\" -retry=0 -apiKey=\"${env.KATALON_API_KEY}\" -orgID=\"${env.KATALON_ORG_ID}\" ${env.ARG_TYPE}=\"${env.FINAL_PATH}\" -executionProfile=\"${env.TARGET_PROFILE}\" -browserType=\"Firefox (headless)\" --config -webui.autoUpdateDrivers=true"
                    
                    def exitCode = bat(script: cmd, returnStatus: true)
                    echo "Firefox Finished with Exit Code: ${exitCode}"
                    if (exitCode != 0) {
                        currentBuild.result = 'UNSTABLE'
                    }

                    // Salin HTML Firefox ke OneDrive & Arsipkan
                    bat(script: """
                    powershell -NoProfile -ExecutionPolicy Bypass -Command "\
                        \$ErrorActionPreference = 'SilentlyContinue'; \
                        try { \
                            \$dateStr = (Get-Date).ToString('dd-MM-yyyy'); \
                            \$dest = Join-Path (Join-Path 'C:\\Users\\AgungPriyadi\\OneDrive - (G)Tech Digital\\Attachments\\${env.PROJECT_FOLDER}' \$dateStr) 'Firefox Headless'; \
                            if (-not (Test-Path \$dest)) { New-Item -ItemType Directory -Path \$dest -Force | Out-Null }; \
                            if (Test-Path 'Reports') { \
                                Get-ChildItem -Path 'Reports' -Filter '*.html' -Recurse -ErrorAction SilentlyContinue | ForEach-Object { \
                                    \$curr = \$_.Directory; \
                                    \$modName = ''; \
                                    while (\$curr -and \$curr.Name -ne 'Reports' -and \$curr.FullName -ne \$env:WORKSPACE) { \
                                        if (\$curr.Name -notmatch '^\\d{8}_\\d{6}\$' -and \$curr.Name -ne 'Test Suites') { \$modName = \$curr.Name; break }; \
                                        \$curr = \$curr.Parent \
                                    }; \
                                    if (-not \$modName) { \$modName = if ('${params.SUITE}') { (Split-Path '${params.SUITE}' -Leaf) } else { 'Test_Report' } }; \
                                    \$destFile = Join-Path \$dest (\$modName + '.html'); \
                                    Copy-Item -Path \$_.FullName -Destination \$destFile -Force -ErrorAction SilentlyContinue; \
                                    Write-Host ('Copied Firefox HTML: ' + \$modName + '.html') \
                                }; \
                                New-Item -ItemType Directory -Path 'Reports_Archive\\Firefox' -Force | Out-Null; \
                                Copy-Item -Path 'Reports\\*' -Destination 'Reports_Archive\\Firefox' -Recurse -Force -ErrorAction SilentlyContinue; \
                                Remove-Item -Path 'Reports' -Recurse -Force -ErrorAction SilentlyContinue; \
                            } \
                        } catch { Write-Host ('Handled Firefox Copy Notice: ' + \$_.Exception.Message) } \
                    "
                    """, returnStatus: true)

                    bat(script: '''
                    taskkill /F /IM katalonc.exe /T 2>nul || exit 0
                    taskkill /F /IM java.exe /T 2>nul || exit 0
                    taskkill /F /IM javaw.exe /T 2>nul || exit 0
                    taskkill /F /IM geckodriver.exe /T 2>nul || exit 0
                    taskkill /F /IM firefox.exe /T 2>nul || exit 0
                    timeout /t 2 /nobreak >nul 2>&1 || exit 0
                    if exist "C:\\Users\\AgungPriyadi\\.katalon\\packages\\KS-11.1.3\\config\\.metadata\\.lock" del /f /q "C:\\Users\\AgungPriyadi\\.katalon\\packages\\KS-11.1.3\\config\\.metadata\\.lock" 2>nul || exit 0
                    ''', returnStatus: true)
                }
            }
        }
    }

    post {

        always {
            script {
                // Kembalikan semua file Reports dari Archive
                bat '''
                powershell -NoProfile -ExecutionPolicy Bypass -Command "\
                    $ErrorActionPreference = 'SilentlyContinue'; \
                    if (Test-Path 'Reports_Archive') { \
                        New-Item -ItemType Directory -Path 'Reports' -Force | Out-Null; \
                        Copy-Item -Path 'Reports_Archive\\*' -Destination 'Reports' -Recurse -Force -ErrorAction SilentlyContinue \
                    } \
                "
                '''

                archiveArtifacts(
                    artifacts: 'Reports/**, Screenshot/**, failure_*.html',
                    allowEmptyArchive: true
                )

                junit(
                    allowEmptyResults: true,
                    testResults: 'Reports/**/JUnit_Report.xml, Reports/**/*.xml'
                )

                bat """
                powershell -NoProfile -ExecutionPolicy Bypass -Command "\
                    \$ErrorActionPreference = 'SilentlyContinue'; \
                    \$p=0; \$f=0; \$s=0; \
                    \$xmlFiles = Get-ChildItem -Path 'Reports' -Filter 'JUnit_Report.xml' -Recurse -ErrorAction SilentlyContinue; \
                    if (-not \$xmlFiles) { \$xmlFiles = Get-ChildItem -Path 'Reports' -Filter '*.xml' -Recurse -ErrorAction SilentlyContinue | Where-Object { \$_.Name -ne 'TESTS-TestSuites.xml' } }; \
                    if (\$xmlFiles) { \
                        \$xmlFiles | ForEach-Object { \
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
                        } \
                    }; \
                    \$finalStatus = if (\$f -gt 0) { 'FAILURE' } else { 'SUCCESS' }; \
                    \$body = @{ \
                        job = '${env.JOB_NAME}'; \
                        projectName = '${env.PROJECT_NAME}'; \
                        buildNumber = [int]${env.BUILD_NUMBER}; \
                        status = \$finalStatus; \
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

                bat """
                powershell -NoProfile -ExecutionPolicy Bypass -Command "\
                    \$ErrorActionPreference = 'SilentlyContinue'; \
                    \$errs = @(); \
                    \$tcList = @(); \
                    \$i = 1; \
                    \$xmlFiles = Get-ChildItem -Path 'Reports' -Filter '*.xml' -Recurse -ErrorAction SilentlyContinue | Where-Object { \$_.Name -ne 'TESTS-TestSuites.xml' }; \
                    if (\$xmlFiles) { \
                        \$xmlFiles | ForEach-Object { \
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
                        } \
                    }; \
                    if (\$tcList.Count -gt 0) { \
                        \$tempZip = Join-Path \$env:TEMP 'PastiAdaJalan_Failures'; \
                        if (Test-Path \$tempZip) { Remove-Item \$tempZip -Recurse -Force -ErrorAction SilentlyContinue }; \
                        New-Item -ItemType Directory -Path (Join-Path \$tempZip 'Reports') -Force | Out-Null; \
                        Get-ChildItem -Path 'Reports' -Recurse -File -ErrorAction SilentlyContinue | Where-Object { \$_.Extension -in '.html', '.xml', '.log', '.properties' } | ForEach-Object { \
                            \$rel = \$_.FullName.Substring((Get-Item 'Reports').FullName.Length); \
                            \$targetFile = Join-Path (Join-Path \$tempZip 'Reports') \$rel; \
                            \$targetDir = Split-Path \$targetFile -Parent; \
                            if (-not (Test-Path \$targetDir)) { New-Item -ItemType Directory -Path \$targetDir -Force | Out-Null }; \
                            Copy-Item -Path \$_.FullName -Destination \$targetFile -Force -ErrorAction SilentlyContinue; \
                        }; \
                        if (Test-Path 'Screenshot') { \
                            New-Item -ItemType Directory -Path (Join-Path \$tempZip 'Screenshot') -Force | Out-Null; \
                            Get-ChildItem -Path 'Screenshot' -Filter '*.png' -Recurse -ErrorAction SilentlyContinue | Sort-Object LastWriteTime -Descending | Select-Object -First 10 | ForEach-Object { \
                                Copy-Item -Path \$_.FullName -Destination (Join-Path \$tempZip 'Screenshot') -Force -ErrorAction SilentlyContinue; \
                            }; \
                        }; \
                        if (Test-Path 'Failure_Report.zip') { Remove-Item 'Failure_Report.zip' -Force -ErrorAction SilentlyContinue }; \
                        Compress-Archive -Path (Join-Path \$tempZip '*') -DestinationPath 'Failure_Report.zip' -CompressionLevel Optimal -Force -ErrorAction SilentlyContinue; \
                        Remove-Item \$tempZip -Recurse -Force -ErrorAction SilentlyContinue; \
                        Set-Content -Path 'error_log.txt' -Value (\$errs -join ([Environment]::NewLine + '---' + [Environment]::NewLine)); \
                        \$jsonPayload = @{ projectName = '${env.PROJECT_NAME}'; jobName = '${env.JOB_NAME}'; buildUrl = '${env.BUILD_URL}'; buildNumber = [int]${env.BUILD_NUMBER}; testCases = \$tcList } | ConvertTo-Json -Depth 5; \
                        Set-Content -Path 'failed_tests.json' -Value \$jsonPayload; \
                        if (Test-Path 'Failure_Report.zip') { \
                            curl.exe -X POST \"http://localhost:5678/webhook/jenkins-report\" -F \"chat_id=8122375919\" -F \"file=@Failure_Report.zip\" -F \"error_log=@error_log.txt\"; \
                        } else { \
                            curl.exe -X POST \"http://localhost:5678/webhook/jenkins-report\" -F \"chat_id=8122375919\" -F \"error_log=@error_log.txt\"; \
                        }; \
                        if ((Get-Content 'failed_tests.json' | ConvertFrom-Json).testCases.Count -gt 0) { \
                            Invoke-RestMethod -Uri '${env.N8N_SHEETS_WEBHOOK}' -Method Post -ContentType 'application/json' -InFile 'failed_tests.json' \
                        }; \
                    } \
                "
                """
            }

            echo ""
            echo "======================================"
            echo "Automation Finished"
            echo "======================================"
        }

        unstable {
            echo "Automation UNSTABLE - Failure reporting handled in always block."
        }

        failure {
            echo "Automation FAILED - Failure reporting handled in always block."
        }
    }

}
