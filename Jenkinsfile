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

        PROJECT_FILE = 'pasti-ada-jalan.prj'

        USERPROFILE = 'C:\\Users\\AgungPriyadi'

        DEFAULT_TEST = 'Test Suites/WEB/Checkout/Post Payment Validation'

        KATALON_EXE = 'C:\\Users\\AgungPriyadi\\.katalon\\packages\\KS-11.1.3\\katalonc.exe'

        KATALON_API_KEY = credentials('katalon-api-key')

        KATALON_ORG_ID = '2078893'

        // 🌟 URL Webhook n8n untuk pencatatan otomatis ke Google Sheets
        N8N_SHEETS_WEBHOOK = 'http://localhost:5678/webhook/79204498-fdea-41d3-a8a2-21b002f8b724'
    }

    stages {

        // --- NOTIFIKASI MULAI ---
        stage('Notify Start') {
            steps {
                script {
                    bat 'curl -X POST "http://localhost:5678/webhook/jenkins" -H "Content-Type: application/json" -d "{\\"job\\":\\"' + env.JOB_NAME + '\\",\\"buildNumber\\":' + env.BUILD_NUMBER + ',\\"browser\\":\\"' + params.BROWSER + '\\",\\"profile\\":\\"' + params.PROFILE + '\\",\\"status\\":\\"RUNNING\\",\\"phase\\":\\"STARTED\\"}"'
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
                    if exist "C:\\Users\\AgungPriyadi\\.katalon\\packages\\KS-11.1.3\\config\\.metadata\\.lock" del /f /q "C:\\Users\\AgungPriyadi\\.katalon\\packages\\KS-11.1.3\\config\\.metadata\\.lock" 2>nul || exit 0
                    if exist Reports rmdir /s /q Reports
                    if exist Screenshot rmdir /s /q Screenshot
                    if exist summary.json del /f /q summary.json
                    if exist error_log.txt del /f /q error_log.txt
                    if exist failed_tests.json del /f /q failed_tests.json
                    '''

                    // 1. Penanganan Mapping ENV Telegram ke Execution Profile Katalon
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

                    // 2. Penanganan Mapping SUITE / TEST_PATH & Deteksi Otomatis Collection vs Suite
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

                    // Deteksi otomatis tipe argumen Katalon CLI
                    if (!params.TEST_PATH?.trim()) {
                        if (env.FINAL_PATH.contains("Collection") || env.FINAL_PATH.contains("Web_Test_Suite_Collection")) {
                            env.ARG_TYPE = "-testSuiteCollectionPath"
                        } else {
                            env.ARG_TYPE = "-testSuitePath"
                        }
                    }

                    // Menyusun argumen ekstra
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

            // --- NOTIFIKASI SELESAI + RINGKASAN TEST CASE ---
            script {
                def currentStatus = currentBuild.currentResult ?: 'UNKNOWN'
                
                bat """
                powershell -Command "\$p=0;\$f=0;\$s=0; Get-ChildItem -Path 'Reports' -Filter '*.xml' -Recurse -ErrorAction SilentlyContinue | ForEach-Object { [xml]\$x = Get-Content \$_.FullName; foreach(\$ts in \$x.SelectNodes('//testsuite')){ \$t=[int]\$ts.tests; \$fail=[int]\$ts.failures + [int]\$ts.errors; \$skip=[int]\$ts.skipped; \$pass=\$t - (\$fail + \$skip); if(\$pass -gt 0){\$p+=\$pass}; \$f+=\$fail; \$s+=\$skip } }; \$json = '{\\"job\\":\\"${env.JOB_NAME}\\",\\"buildNumber\\":${env.BUILD_NUMBER},\\"status\\":\\"${currentStatus}\\",\\"phase\\":\\"COMPLETED\\",\\"passed\\":' + \$p + ',\\"failed\\":' + \$f + ',\\"skipped\\":' + \$s + '}'; Set-Content -Path 'summary.json' -Value \$json"
                curl -X POST "http://localhost:5678/webhook/jenkins" -H "Content-Type: application/json" -d @summary.json
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
            echo "Automation UNSTABLE - Preparing Zip, AI Error Log & Sending to Google Sheets..."
            script {
                bat """
                powershell -Command "if (Test-Path 'Failure_Report.zip') { Remove-Item 'Failure_Report.zip' }; \$f = @(); if (Test-Path 'Reports') { \$f += 'Reports' }; if (Test-Path 'Screenshot') { \$f += 'Screenshot' }; if (\$f.Count -gt 0) { Compress-Archive -Path \$f -DestinationPath 'Failure_Report.zip' -Force }; \$errs = @(); \$tcList = @(); \$i = 1; Get-ChildItem -Path 'Reports' -Filter '*.xml' -Recurse -ErrorAction SilentlyContinue | ForEach-Object { [xml]\$x = Get-Content \$_.FullName; foreach(\$tc in \$x.SelectNodes('//testcase[failure or error]')){ \$node = if(\$tc.failure){\$tc.failure}else{\$tc.error}; \$msg = \$node.message; if([string]::IsNullOrWhiteSpace(\$msg)){ \$msg = \$node.innerText }; if([string]::IsNullOrWhiteSpace(\$msg)){ \$msg = \$tc.'system-err' }; if([string]::IsNullOrWhiteSpace(\$msg)){ \$msg = 'No detailed error message found in XML.' }; \$errs += ('[Test Case]: ' + \$tc.name + [Environment]::NewLine + '[Error]: ' + \$msg); \$tcList += @{ number = [string]\$i; testSuiteName = '${env.FINAL_PATH}'.Replace('Test Suites/', ''); testCaseName = [string]\$tc.name; status = 'failed'; errorMessage = [string]\$msg; reportUrl = '${env.BUILD_URL}' }; \$i++ } }; if (\$errs.Count -eq 0) { \$errs += 'No detailed XML stacktrace found.' }; Set-Content -Path 'error_log.txt' -Value (\$errs -join ([Environment]::NewLine + '---' + [Environment]::NewLine)); \$jsonPayload = @{ testCases = \$tcList } | ConvertTo-Json -Depth 5; Set-Content -Path 'failed_tests.json' -Value \$jsonPayload"
                
                powershell -Command "curl.exe -X POST 'http://localhost:5678/webhook/jenkins-report' -F 'chat_id=8122375919' -F 'file=@Failure_Report.zip' -F 'error_log=@error_log.txt'"
                
                powershell -Command "curl.exe -X POST '${env.N8N_SHEETS_WEBHOOK}' -H 'Content-Type: application/json' -d @failed_tests.json"
                """
            }
        }

        failure {
            echo "Automation FAILED - Preparing Zip, AI Error Log & Sending to Google Sheets..."
            script {
                bat """
                powershell -Command "if (Test-Path 'Failure_Report.zip') { Remove-Item 'Failure_Report.zip' }; \$f = @(); if (Test-Path 'Reports') { \$f += 'Reports' }; if (Test-Path 'Screenshot') { \$f += 'Screenshot' }; if (\$f.Count -gt 0) { Compress-Archive -Path \$f -DestinationPath 'Failure_Report.zip' -Force }; \$errs = @(); \$tcList = @(); \$i = 1; Get-ChildItem -Path 'Reports' -Filter '*.xml' -Recurse -ErrorAction SilentlyContinue | ForEach-Object { [xml]\$x = Get-Content \$_.FullName; foreach(\$tc in \$x.SelectNodes('//testcase[failure or error]')){ \$node = if(\$tc.failure){\$tc.failure}else{\$tc.error}; \$msg = \$node.message; if([string]::IsNullOrWhiteSpace(\$msg)){ \$msg = \$node.innerText }; if([string]::IsNullOrWhiteSpace(\$msg)){ \$msg = \$tc.'system-err' }; if([string]::IsNullOrWhiteSpace(\$msg)){ \$msg = 'No detailed error message found in XML.' }; \$errs += ('[Test Case]: ' + \$tc.name + [Environment]::NewLine + '[Error]: ' + \$msg); \$tcList += @{ number = [string]\$i; testSuiteName = '${env.FINAL_PATH}'.Replace('Test Suites/', ''); testCaseName = [string]\$tc.name; status = 'failed'; errorMessage = [string]\$msg; reportUrl = '${env.BUILD_URL}' }; \$i++ } }; if (\$errs.Count -eq 0) { \$errs += 'No detailed XML stacktrace found.' }; Set-Content -Path 'error_log.txt' -Value (\$errs -join ([Environment]::NewLine + '---' + [Environment]::NewLine)); \$jsonPayload = @{ testCases = \$tcList } | ConvertTo-Json -Depth 5; Set-Content -Path 'failed_tests.json' -Value \$jsonPayload"
                
                powershell -Command "curl.exe -X POST 'http://localhost:5678/webhook/jenkins-report' -F 'chat_id=8122375919' -F 'file=@Failure_Report.zip' -F 'error_log=@error_log.txt'"
                
                powershell -Command "curl.exe -X POST '${env.N8N_SHEETS_WEBHOOK}' -H 'Content-Type: application/json' -d @failed_tests.json"
                """
            }
        }

    }

}
