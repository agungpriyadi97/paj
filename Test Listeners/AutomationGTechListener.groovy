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

class AutomationListener {

private static boolean browserOpenedByListener = false

@BeforeTestCase
def beforeTestCase(TestCaseContext testCaseContext) {

	KeywordUtil.logInfo("START TEST CASE : ${testCaseContext.getTestCaseId()}")

	boolean isBrowserActive = false

	try {

		isBrowserActive = (DriverFactory.getWebDriver() != null)

		if(isBrowserActive) {
			WebUI.getUrl()
		}

	} catch(Exception e) {

		isBrowserActive = false
		KeywordUtil.logInfo("Browser detected as inactive : ${e.getMessage()}")
	}

	if(!isBrowserActive && !browserOpenedByListener) {

		try {

			KeywordUtil.logInfo("Opening new browser...")

			WebUI.openBrowser('')
			WebUI.setViewPortSize(1920, 1080)

			if(GlobalVariable.URL == null || GlobalVariable.URL.isEmpty()) {

				KeywordUtil.markFailed(
					"GlobalVariable.URL is not set. Please configure it in Profiles."
				)

				return
			}

			WebUI.navigateToUrl(GlobalVariable.URL)

			acceptCookieConsent()

			browserOpenedByListener = true

			def width =
				WebUI.executeJavaScript(
					"return window.innerWidth",
					null
				)

			def height =
				WebUI.executeJavaScript(
					"return window.innerHeight",
					null
				)

			KeywordUtil.logInfo(
				"Viewport Size : ${width} x ${height}"
			)

			WebUI.takeScreenshot(
				RunConfiguration.getReportFolder() +
				"/START_PAGE.png"
			)

		} catch(Exception e) {

			KeywordUtil.markFailed(
				"Failed to open browser or navigate to URL : ${e.getMessage()}"
			)

			throw e
		}

	} else if(isBrowserActive) {

		KeywordUtil.logInfo(
			"Browser already opened, reusing existing session."
		)

		try {

			if(WebUI.getUrl() != GlobalVariable.URL) {

				WebUI.navigateToUrl(GlobalVariable.URL)

				acceptCookieConsent()
			}

		} catch(Exception e) {

			KeywordUtil.logWarning(
				"Could not verify current URL : ${e.getMessage()}"
			)
		}

	} else {

		KeywordUtil.logInfo(
			"Browser was opened by listener in a previous test case but is now closed. Reopening..."
		)

		browserOpenedByListener = false

		beforeTestCase(testCaseContext)
	}
}

private void acceptCookieConsent() {

	try {

		boolean cookieVisible =
			WebUI.verifyElementPresent(
				findTestObject(
					'WEB/Common/Cookie Consent/btn_AcceptAllCookies'
				),
				5,
				FailureHandling.OPTIONAL
			)

		if(cookieVisible) {

			WebUI.click(
				findTestObject(
					'WEB/Common/Cookie Consent/btn_AcceptAllCookies'
				)
			)

			KeywordUtil.logInfo(
				"Cookie consent accepted"
			)
		}

	} catch(Exception e) {

		KeywordUtil.logInfo(
			"Cookie popup not displayed"
		)
	}
}

@AfterTestCase
def afterTestCase(TestCaseContext testCaseContext) {

	try {

		if(DriverFactory.getWebDriver() != null) {

			String tcName =
				testCaseContext.getTestCaseId()
				.replaceAll("[^a-zA-Z0-9]", "_")

			String screenshotPath =
				RunConfiguration.getReportFolder() +
				"/${tcName}_${testCaseContext.getTestCaseStatus()}.png"

			WebUI.takeScreenshot(screenshotPath)

			KeywordUtil.logInfo(
				"Screenshot saved : ${screenshotPath}"
			)

			String tcId =
				testCaseContext.getTestCaseId()

			if(!tcId.contains("Forgot password verification email")) {

				WebUI.closeBrowser()

				browserOpenedByListener = false

			} else {

				KeywordUtil.logInfo(
					"Browser kept open for this test case."
				)
			}

		} else {

			KeywordUtil.logInfo("No browser to close.")

			browserOpenedByListener = false
		}

	} catch(Exception e) {

		KeywordUtil.markWarning(
			"Listener Error : ${e.getMessage()}"
		)

		browserOpenedByListener = false
	}
}

}
