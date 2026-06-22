package utils

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver

import internal.GlobalVariable

public class WindowHelper {
	/**
	 * Klik elemen yang membuka tab baru, lalu verifikasi URL tab baru,
	 * tutup tab baru, dan kembali ke tab awal.
	 * @param element TestObject yang akan diklik
	 * @param expectedUrlPattern pola URL yang diharapkan (bisa regex atau string awal)
	 * @return true jika URL cocok, false jika tidak
	 */
	@Keyword
	def clickAndVerifyNewTab(TestObject element, String expectedUrlPattern) {
		String originalWindow = DriverFactory.getWebDriver().getWindowHandle()
		
		// Klik tombol
		WebUI.click(element)
		WebUI.delay(2) // tunggu tab terbuka
		
		// Dapatkan semua window handles
		Set<String> windowHandles = DriverFactory.getWebDriver().getWindowHandles()
		
		// Beralih ke tab baru (biasanya handle terakhir)
		String newWindow = null
		for (String handle : windowHandles) {
			if (!handle.equals(originalWindow)) {
				newWindow = handle
				DriverFactory.getWebDriver().switchTo().window(newWindow)
				break
			}
		}
		
		if (newWindow == null) {
			throw new Exception("Tidak ada tab baru yang terbuka setelah klik")
		}
		
		// Verifikasi URL
		String currentUrl = WebUI.getUrl()
		boolean isMatch = currentUrl.matches(expectedUrlPattern) || currentUrl.startsWith(expectedUrlPattern)
		
		// Tutup tab baru dan kembali ke tab awal
		DriverFactory.getWebDriver().close()
		DriverFactory.getWebDriver().switchTo().window(originalWindow)
		
		return isMatch
	}
}
