import java.io.File
import java.util.Arrays
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.model.FailureHandling

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import internal.GlobalVariable

class AutomationGTechListener {

    private static boolean browserOpenedByListener = false

    @BeforeTestCase
    def beforeTestCase(TestCaseContext testCaseContext) {

        KeywordUtil.logInfo("================================================")
        KeywordUtil.logInfo("START TEST CASE : ${testCaseContext.getTestCaseId()}")
        KeywordUtil.logInfo("================================================")

        boolean isBrowserActive = false

        try {
            isBrowserActive = (DriverFactory.getWebDriver() != null)

            if (isBrowserActive) {
                WebUI.getUrl()
            }

        } catch (Exception e) {
            isBrowserActive = false
            KeywordUtil.logInfo("Browser detected as inactive : ${e.getMessage()}")
        }

        if (!isBrowserActive && !browserOpenedByListener) {

            try {
                KeywordUtil.logInfo("Opening new browser...")

                WebUI.openBrowser('')
                WebUI.setViewPortSize(1920, 1080)

                if (GlobalVariable.URL == null || GlobalVariable.URL.trim().isEmpty()) {
                    KeywordUtil.markFailedAndStop("GlobalVariable.URL is not set. Please configure it in Profiles.")
                }

                WebUI.navigateToUrl(GlobalVariable.URL)
                
                // Menjalankan handler Cookie Consent GTech
                acceptCookieConsent()
                
                WebUI.waitForPageLoad(30)

                browserOpenedByListener = true

                def width = WebUI.executeJavaScript("return window.innerWidth", null)
                def height = WebUI.executeJavaScript("return window.innerHeight", null)

                KeywordUtil.logInfo("Viewport Size : ${width} x ${height}")

                saveStartPageScreenshot()

            } catch (Exception e) {
                KeywordUtil.markFailed("Failed to open browser or navigate to URL : ${e.getMessage()}")
                throw e
            }

        } else if (isBrowserActive) {

            KeywordUtil.logInfo("Browser already opened. Reusing existing session.")

            try {
                if (WebUI.getUrl() != GlobalVariable.URL) {
                    WebUI.navigateToUrl(GlobalVariable.URL)
                    
                    // Menjalankan handler Cookie Consent GTech
                    acceptCookieConsent()
                }

            } catch (Exception e) {
                KeywordUtil.logWarning("Could not verify current URL : ${e.getMessage()}")
            }

        } else {

            KeywordUtil.logInfo("Browser closed unexpectedly. Re-opening browser...")

            browserOpenedByListener = false

            WebUI.openBrowser('')
            WebUI.setViewPortSize(1920, 1080)

            if (GlobalVariable.URL == null || GlobalVariable.URL.trim().isEmpty()) {
                KeywordUtil.markFailedAndStop("GlobalVariable.URL is not set. Please configure it in Profiles.")
            }

            WebUI.navigateToUrl(GlobalVariable.URL)
            
            // Menjalankan handler Cookie Consent GTech
            acceptCookieConsent()
            
            WebUI.waitForPageLoad(30)

            browserOpenedByListener = true

            saveStartPageScreenshot()

            try {
                def width = WebUI.executeJavaScript("return window.innerWidth", null)
                def height = WebUI.executeJavaScript("return window.innerHeight", null)

                KeywordUtil.logInfo("Viewport Size : ${width} x ${height}")

            } catch (Exception ignored) {
            }
        }
    }

    private void acceptCookieConsent() {
        try {
            boolean cookieVisible = WebUI.verifyElementPresent(
                    findTestObject('WEB/Common/Cookie Consent/btn_AcceptAllCookies'),
                    5,
                    FailureHandling.OPTIONAL
            )

            if (cookieVisible) {
                WebUI.click(findTestObject('WEB/Common/Cookie Consent/btn_AcceptAllCookies'))
                KeywordUtil.logInfo("Cookie consent accepted")
            }

        } catch (Exception e) {
            KeywordUtil.logInfo("Cookie popup not displayed")
        }
    }

    @AfterTestCase
    def afterTestCase(TestCaseContext testCaseContext) {

        try {

            if (DriverFactory.getWebDriver() != null) {

                // ============================================
                // Parsing Test Case ID untuk Subfolder Modul
                // ============================================
                String rawId = testCaseContext.getTestCaseId()
                String cleanPath = rawId.replace("Test Cases/", "") 
                String[] parts = cleanPath.split("/")
                
                String modulePath = "Root"
                String tcName = cleanPath
                
                // Jika test case berada di dalam struktur folder
                if (parts.length > 1) {
                    // Menggabungkan array folder menggunakan "/" agar menjadi path direktori
                    modulePath = String.join("/", Arrays.copyOfRange(parts, 0, parts.length - 1))
                    // Nama test case diambil dari elemen paling belakang
                    tcName = parts[parts.length - 1]
                }

                // Hanya membersihkan karakter nama test case-nya saja
                tcName = tcName.replaceAll("[^a-zA-Z0-9_\\-]", "_")
                String status = testCaseContext.getTestCaseStatus()

                // ============================================
                // Screenshot GitLab Artifact (Masuk ke Subfolder)
                // ============================================

                // Membuat path folder utama + path modul
                String screenshotFolder = RunConfiguration.getProjectDir() + "/Screenshot/" + modulePath
                
                // Buat direktori subfoldernya jika belum ada
                new File(screenshotFolder).mkdirs() 

                String artifactScreenshot = screenshotFolder + "/" + tcName + "_" + status + ".png"

                try {
                    WebUI.takeScreenshot(artifactScreenshot)
                    KeywordUtil.logInfo("Artifact Screenshot : ${artifactScreenshot}")
                } catch (Exception ignored) {
                }

                // ============================================
                // Screenshot Katalon Report
                // ============================================

                String reportFolder = RunConfiguration.getReportFolder()

                if (reportFolder != null) {
                    // Membuat subfolder di dalam folder Report juga agar rapi
                    String reportSubFolder = reportFolder + "/Screenshot/" + modulePath
                    new File(reportSubFolder).mkdirs()
                    
                    String reportScreenshot = reportSubFolder + "/" + tcName + "_" + status + ".png"

                    try {
                        WebUI.takeScreenshot(reportScreenshot)
                        KeywordUtil.logInfo("Report Screenshot : ${reportScreenshot}")
                    } catch (Exception ignored) {
                    }
                }

                // ============================================
                // Close Browser
                // ============================================

                String tcId = testCaseContext.getTestCaseId()

                if (!tcId.contains("Forgot password verification email")) {
                    WebUI.closeBrowser()
                    browserOpenedByListener = false
                } else {
                    KeywordUtil.logInfo("Browser kept open for this test case.")
                }

            } else {
                KeywordUtil.logInfo("No browser to close.")
                browserOpenedByListener = false
            }

        } catch (Exception e) {
            KeywordUtil.markWarning("Listener Error : ${e.getMessage()}")
            browserOpenedByListener = false
        }
    }

    private void saveStartPageScreenshot() {

        String screenshotFolder = RunConfiguration.getProjectDir() + "/Screenshot"
        new File(screenshotFolder).mkdirs()

        try {
            WebUI.takeScreenshot(screenshotFolder + "/START_PAGE.png")
        } catch (Exception ignored) {
        }

        String reportFolder = RunConfiguration.getReportFolder()

        if (reportFolder != null) {
            try {
                WebUI.takeScreenshot(reportFolder + "/START_PAGE.png")
            } catch (Exception ignored) {
            }
        }
    }
}