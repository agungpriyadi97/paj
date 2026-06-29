pipeline {

    agent any

    options {
        timeout(time: 12, unit: 'HOURS')
        timestamps()
    }

    // Build Manual
    // Kalau ingin otomatis tinggal aktifkan cron di bawah
    /*
    triggers {
        cron('0 8 * * 5')
    }
    */

    parameters {

        choice(
            name: 'BROWSER',
            choices: [
                'Chrome (headless)',
                'Firefox (headless)',
                'Both'
            ],
            description: 'Browser yang akan dijalankan'
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
Kosong = Regression Default

Contoh:
-testSuitePath=Test Suites/WEB/Login/Login
-testSuiteCollectionPath=Test Suites/WEB/Regression
        )

    }

    environment {

        // ======== GANTI INI SAJA JIKA PINDAH PROJECT ========

        PROJECT_FILE = 'pasti-ada-jalan.prj'

        DEFAULT_TEST =
        'Test Suites/WEB/Web_Test_Suite_Collection/Regression_PAJ_Web'

        KATALON_EXE =
        'C:\\Users\\AgungPriyadi\\.katalon\\packages\\KS-11.1.3\\katalonc.exe'

        KATALON_API_KEY = credentials('katalon-api-key')

        // ================================================

    }

    stages {

        stage('Checkout') {

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

                        env.ARG_TYPE = params.TEST_PATH.split('=')[0]
                        env.FINAL_PATH = params.TEST_PATH.split('=')[1]

                    } else {

                        env.ARG_TYPE = '-testSuiteCollectionPath'
                        env.FINAL_PATH = env.DEFAULT_TEST

                    }

                    echo "Project  : ${PROJECT_FILE}"
                    echo "Profile  : ${params.PROFILE}"
                    echo "Browser  : ${params.BROWSER}"
                    echo "Arg Type : ${env.ARG_TYPE}"
                    echo "Test     : ${env.FINAL_PATH}"

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

                catchError(buildResult: 'SUCCESS',
                           stageResult: 'UNSTABLE') {

                    bat """

                    "%KATALON_EXE%" ^
                    -noSplash ^
                    -runMode=console ^
                    -projectPath="%WORKSPACE%\\${PROJECT_FILE}" ^
                    -retry=0 ^
                    -apiKey="%KATALON_API_KEY%" ^
                    ${env.ARG_TYPE}="${env.FINAL_PATH}" ^
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

                catchError(buildResult: 'SUCCESS',
                           stageResult: 'UNSTABLE') {

                    bat """

                    "%KATALON_EXE%" ^
                    -noSplash ^
                    -runMode=console ^
                    -projectPath="%WORKSPACE%\\${PROJECT_FILE}" ^
                    -retry=0 ^
                    -apiKey="%KATALON_API_KEY%" ^
                    ${env.ARG_TYPE}="${env.FINAL_PATH}" ^
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

                artifacts: '''
                Reports/**,
                Screenshot/**,
                failure_*.html
                ''',
                allowEmptyArchive: true

            )

            junit(

                allowEmptyResults: true,
                testResults: 'Reports/**/*.xml'

            )

        }

        success {

            echo '==================================='
            echo 'Automation SUCCESS'
            echo '==================================='

        }

        unstable {

            echo '==================================='
            echo 'Automation UNSTABLE'
            echo 'Chrome / Firefox gagal sebagian'
            echo '==================================='

        }

        failure {

            echo '==================================='
            echo 'Automation FAILED'
            echo '==================================='

        }

    }

}
```
