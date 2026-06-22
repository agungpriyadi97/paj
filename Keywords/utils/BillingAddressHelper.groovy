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
import java.util.Map
import java.util.HashMap
import java.util.Random
import java.util.concurrent.ThreadLocalRandom
import internal.GlobalVariable

public class BillingAddressHelper {
	
		// ========================= BILLING ADDRESS - ADD NEW =========================
		/**
		 * Mengisi form billing address dengan data dari Map
		 * Asumsi: form sudah terbuka (setelah klik "Thêm địa chỉ mới")
		 */
		@Keyword
		def fillBillingAddressForm(Map<String, String> addressData) {
			WebUI.setText(findTestObject('WEB/Address/Billing Add New Address/input_first_name'), addressData.firstName)
			WebUI.setText(findTestObject('WEB/Address/Billing Add New Address/input_last_name'), addressData.lastName)
			WebUI.setText(findTestObject('WEB/Address/Billing Add New Address/input_mobile_phone'), addressData.mobilePhone)
			WebUI.setText(findTestObject('WEB/Address/Billing Add New Address/input_email'), addressData.email)
			WebUI.setText(findTestObject('WEB/Address/Billing Add New Address/input_address'), addressData.address)
			WebUI.clearText(findTestObject('WEB/Address/Billing Add New Address/input_zip_code'))
			WebUI.setText(findTestObject('WEB/Address/Billing Add New Address/input_zip_code'), addressData.zipCode)
			WebUI.waitForElementClickable(findTestObject('WEB/Address/Billing Add New Address/btn_billing_save'), 10)
			WebUI.click(findTestObject('WEB/Address/Billing Add New Address/btn_billing_save'))
			WebUI.waitForPageLoad(10)
		}
	
		/**
		 * Mengisi form billing address dengan data random (form sudah terbuka)
		 */
		@Keyword
		def fillBillingAddressFormWithRandomData() {
			long timestamp = System.currentTimeMillis()
			Random random = new Random()
			Map<String, String> address = new HashMap<>()
			address.firstName = "Agung"
			address.lastName = "Priyadi" + timestamp
			address.mobilePhone = generateRandomPhoneNumber()
			address.email = "agung" + timestamp + "@yopmail.com"
			address.address = "Jl. Billing No. " + random.nextInt(999)
			address.zipCode = "100000"
			fillBillingAddressForm(address)
		}
	
		/**
		 * Method sederhana: klik tombol "Thêm địa chỉ mới" → delay 6 detik → isi form dengan data random
		 * Tidak ada logika pengecekan, persis seperti cara manual yang berhasil.
		 */
		@Keyword
		def addNewBillingAddressSimple() {
			// Klik tombol "Thêm địa chỉ mới" di section billing
			WebUI.click(findTestObject('WEB/Address/Billing Add New Address/btn_add_new_billing'))
			// Delay 6 detik (sama seperti cara manual)
			WebUI.delay(6)
			// Isi form dengan data random
			fillBillingAddressFormWithRandomData()
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
	
		@Keyword
		def generateRandomEmail() {
			long timestamp = System.currentTimeMillis()
			return "user_" + timestamp + "@yopmail.com"
		}
	}