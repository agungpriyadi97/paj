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
                'Chrome (headless)'
            ],
            description: 'Pilih Browser'
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
            name: 'TEST_PATH',
            defaultValue: '',
            description: '''
Kosong = Default Test Suite (Registration)

Contoh override:
-testSuitePath=Test Suites/WEB/Authentication/Registration
'''
        )
    }

    environment {

        PROJECT_FILE = 'pasti-ada-jalan.prj'

        // Set default ke Test Suite Registration
        DEFAULT_TEST = 'Test Suites/WEB/Authentication/Registration'

        KATALON_EXE = 'C:\\Users\\AgungPriyadi\\.katalon\\packages\\KS-11.1.3\\katalonc.exe'

        KATALON_API_KEY = credentials('katalon-api-key')
    }

    stages {

        stage('Checkout Source') {

            steps {

                checkout scm

            }

        }

        stage('Prepare') {

            steps {

                script {

                    bat '''
                    if exist Reports rmdir /s /q Reports
                    if exist Screenshot rmdir /s /q Screenshot
                    '''

                    if (params.TEST_PATH?.trim()) {

                        def value = params.TEST_PATH.split("=")

                        env.ARG_TYPE = value[0]
                        env.FINAL_PATH = value[1]

                    } else {

                        // Gunakan -testSuitePath untuk Test Suite tunggal
                        env.ARG_TYPE = "-testSuitePath"
                        env.FINAL_PATH = env.DEFAULT_TEST

                    }

                    echo "====================================="
                    echo "PROJECT : ${env.PROJECT_FILE}"
                    echo "PROFILE : ${params.PROFILE}"
                    echo "BROWSER : ${params.BROWSER}"
                    echo "ARGTYPE : ${env.ARG_TYPE}"
                    echo "PATH    : ${env.FINAL_PATH}"
                    echo "====================================="

                }

            }

        }

        stage('Run Chrome') {

            steps {

                catchError(buildResult: 'SUCCESS', stageResult: 'UNSTABLE') {

                    bat """
"${env.KATALON_EXE}" ^
-noSplash ^
-runMode=console ^
-projectPath="%WORKSPACE%\\${env.PROJECT_FILE}" ^
-retry=0 ^
-apiKey="${env.KATALON_API_KEY}" ^
${env.ARG_TYPE}="${env.FINAL_PATH}" ^
-executionProfile="${params.PROFILE}" ^
-browserType="Chrome (headless)" ^
-reportFolder="Reports\\Chrome_Reports" ^
-reportFileName="Chrome_Report" ^
--config ^
-webui.autoUpdateDrivers=true ^
-webui.chrome.args="--disable-blink-features=AutomationControlled --disable-dev-shm-usage --disable-gpu --no-sandbox --window-size=1920,1080"
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

            // --- Kirim Webhook ke n8n ---
            script {
                def currentStatus = currentBuild.currentResult ?: 'UNKNOWN'
                bat 'curl -X POST "https://agungpriyadi97.app.n8n.cloud/webhook/jenkins" -H "Content-Type: application/json" -d "{\\"job\\":\\"' + env.JOB_NAME + '\\",\\"buildNumber\\":' + env.BUILD_NUMBER + ',\\"status\\":\\"' + currentStatus + '\\",\\"phase\\":\\"COMPLETED\\"}"'
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

            echo "Automation UNSTABLE"

        }

        failure {

            echo "Automation FAILED"

        }

    }

}
