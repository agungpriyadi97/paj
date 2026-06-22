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

import internal.GlobalVariable

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebElement
import org.openqa.selenium.JavascriptExecutor
import com.kms.katalon.core.webui.driver.DriverFactory
import java.util.Random
import java.util.concurrent.ThreadLocalRandom
public class EditAddressHelper {

	// ========================= EDIT DELIVERY ADDRESS =========================
	@Keyword
	def editLastDeliveryAddressWithRandomData() {
		// Klik tombol edit pada alamat terakhir
		WebUI.click(findTestObject('WEB/Address/Edit Address Delivery/btn_edit_first_address'))
		WebUI.delay(2) // tunggu form edit terbuka
		
		// Generate data random
		long timestamp = System.currentTimeMillis()
		Random random = new Random()
		String newFirstName = "Agung"
		String newLastName = "Priyadi_Edit_" + timestamp
		String newMobile = generateRandomPhoneNumber()
		String newEmail = "edit_" + timestamp + "@yopmail.com"
		String newAddress = "Jl. Edit No. " + random.nextInt(999)
		String newZip = "100000"
		
		// Isi form edit
		WebUI.setText(findTestObject('WEB/Address/Edit Address Delivery/input_first_name'), newFirstName)
		WebUI.setText(findTestObject('WEB/Address/Edit Address Delivery/input_last_name'), newLastName)
		WebUI.setText(findTestObject('WEB/Address/Edit Address Delivery/input_mobile'), newMobile)
		WebUI.setText(findTestObject('WEB/Address/Edit Address Delivery/input_email'), newEmail)
		WebUI.setText(findTestObject('WEB/Address/Edit Address Delivery/input_address'), newAddress)
		WebUI.setText(findTestObject('WEB/Address/Edit Address Delivery/input_zip'), newZip) // tidak perlu clearText
		
		// Simpan perubahan
		WebUI.click(findTestObject('WEB/Address/Edit Address Delivery/btn_save'))
		WebUI.waitForPageLoad(10)
	}
	
	// ========================= HELPER RANDOM GENERATORS =========================
	@Keyword
	def generateRandomPhoneNumber() {
		String[] prefixes = ["09", "08", "07", "03", "05"]
		Random random = new Random()
		String prefix = prefixes[random.nextInt(prefixes.length)]
		int random8Digits = ThreadLocalRandom.current().nextInt(10000000, 99999999)
		return prefix + random8Digits
	}
	
	// ========================= EDIT DELIVERY ADDRESS =========================
	@Keyword
	def editLastBillingAddressWithRandomData() {
		// Klik tombol edit pada alamat terakhir
		WebUI.click(findTestObject('WEB/Address/Billing address/btn_edit_billing_address'))
		WebUI.delay(2) // tunggu form edit terbuka
		
		// Generate data random
		long timestamp = System.currentTimeMillis()
		Random random = new Random()
		String newFirstName = "Agung"
		String newLastName = "Priyadi_Edit_" + timestamp
		String newMobile = generateRandomPhoneNumber()
		String newEmail = "edit_" + timestamp + "@yopmail.com"
		String newAddress = "Jl. Edit No. " + random.nextInt(999)
		String newZip = "100000"
		
		// Isi form edit
		WebUI.setText(findTestObject('WEB/Address/Billing address/input_billing_first_name'), newFirstName)
		WebUI.setText(findTestObject('WEB/Address/Billing address/input_billing_last_name'), newLastName)
		WebUI.setText(findTestObject('WEB/Address/Billing address/input_billing_mobile'), newMobile)
		WebUI.setText(findTestObject('WEB/Address/Billing address/input_billing_email'), newEmail)
		WebUI.setText(findTestObject('WEB/Address/Billing address/input_billing_address'), newAddress)
		WebUI.setText(findTestObject('WEB/Address/Billing address/input_billing_zip'), newZip) // tidak perlu clearText
		
		// Simpan perubahan
		WebUI.click(findTestObject('WEB/Address/Billing address/btn_billing_save'))
		WebUI.waitForPageLoad(10)
	}
}