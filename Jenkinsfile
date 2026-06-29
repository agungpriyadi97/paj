pipeline {

    agent any

    options {
        timeout(time: 12, unit: 'HOURS')
        timestamps()
    }

    parameters {

        choice(
            name: 'BROWSER',
            choices: [
                'Chrome (headless)',
                'Firefox (headless)',
                'Both'
            ],
            description: 'Pilih browser yang akan dijalankan'
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
            description: 'Kosongkan untuk menjalankan Regression default'
        )
    }

    environment {

        PROJECT_FILE = 'pasti-ada-jalan.prj'

        DEFAULT_TEST = 'Test Suites/WEB/Web_Test_Suite_Collection/Regression_pasti_ada_jalan_Web'

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

                        def splitValue = params.TEST_PATH.split("=")

                        env.ARG_TYPE = splitValue[0]
                        env.FINAL_PATH = splitValue[1]

                    } else {

                        env.ARG_TYPE = "-testSuiteCollectionPath"
                        env.FINAL_PATH = env.DEFAULT_TEST

                    }

                    echo "======================================="
                    echo "PROJECT  : ${env.PROJECT_FILE}"
                    echo "PROFILE  : ${params.PROFILE}"
                    echo "BROWSER  : ${params.BROWSER}"
                    echo "ARG TYPE : ${env.ARG_TYPE}"
                    echo "TEST PATH: ${env.FINAL_PATH}"
                    echo "======================================="
                }
            }
        }

        stage('Run Chrome') {

            when {
                anyOf {
                    expression { params.BROWSER == 'Chrome (headless)' }
                    expression { params.BROWSER == 'Both' }
                }
            }

            steps {

                catchError(buildResult: 'SUCCESS', stageResult: 'UNSTABLE') {

                    bat """
"%KATALON_EXE%" ^
-noSplash ^
-runMode=console ^
-projectPath="%WORKSPACE%\\%PROJECT_FILE%" ^
-retry=0 ^
-apiKey="%KATALON_API_KEY%" ^
%ARG_TYPE%="%FINAL_PATH%" ^
-executionProfile="${params.PROFILE}" ^
-browserType="Chrome (headless)" ^
-reportFolder="Reports\\Chrome_Reports" ^
-reportFileName="Chrome_Report" ^
--config ^
-webui.autoUpdateDrivers=true ^
-webui.chrome.args="--disable-blink-features=AutomationControlled --disable-dev-shm-usage --no-sandbox --disable-gpu --window-size=1920,1080"
"""
                }
            }
        }

        stage('Run Firefox') {

            when {
                anyOf {
                    expression { params.BROWSER == 'Firefox (headless)' }
                    expression { params.BROWSER == 'Both' }
                }
            }

            steps {

                catchError(buildResult: 'SUCCESS', stageResult: 'UNSTABLE') {

                    bat """
"%KATALON_EXE%" ^
-noSplash ^
-runMode=console ^
-projectPath="%WORKSPACE%\\%PROJECT_FILE%" ^
-retry=0 ^
-apiKey="%KATALON_API_KEY%" ^
%ARG_TYPE%="%FINAL_PATH%" ^
-executionProfile="${params.PROFILE}" ^
-browserType="Firefox (headless)" ^
-reportFolder="Reports\\Firefox_Reports" ^
-reportFileName="Firefox_Report" ^
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

            echo "======================================="
            echo "Pipeline Finished"
            echo "======================================="
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
