package utils

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.util.Map
import java.util.HashMap
import java.util.Random
import java.util.concurrent.ThreadLocalRandom

public class AddressHelper {
	
		// ========================= DELIVERY ADDRESS =========================
		@Keyword
		def addNewDeliveryAddress() {
			WebUI.waitForElementClickable(findTestObject('WEB/Address/Add Address Delivery/btn_add_new_address'), 10)
			WebUI.click(findTestObject('WEB/Address/Add Address Delivery/btn_add_new_address'))
			WebUI.delay(1)
		}
		
		@Keyword
		def fillAddressForm(Map<String, String> addressData) {
			WebUI.setText(findTestObject('WEB/Address/Add Address Delivery/input_first_name'), addressData.firstName)
			WebUI.setText(findTestObject('WEB/Address/Add Address Delivery/input_last_name'), addressData.lastName)
			WebUI.setText(findTestObject('WEB/Address/Add Address Delivery/input_mobile_phone'), addressData.mobilePhone)
			WebUI.setText(findTestObject('WEB/Address/Add Address Delivery/input_email'), addressData.email)
			WebUI.setText(findTestObject('WEB/Address/Add Address Delivery/input_address'), addressData.address)
			WebUI.clearText(findTestObject('WEB/Address/Add Address Delivery/input_zip_code'))
			WebUI.setText(findTestObject('WEB/Address/Add Address Delivery/input_zip_code'), addressData.zipCode)
			WebUI.waitForElementClickable(findTestObject('WEB/Address/Add Address Delivery/btn_delivery_save'), 10)
			WebUI.click(findTestObject('WEB/Address/Add Address Delivery/btn_delivery_save'))
			WebUI.waitForPageLoad(10)
		}
	
		@Keyword
		def fillAddressFormWithRandomData() {
			long timestamp = System.currentTimeMillis()
			Random random = new Random()
			Map<String, String> address = new HashMap<>()
			address.firstName = "Agung"
			address.lastName = "Priyadi" + timestamp
			address.mobilePhone = generateRandomPhoneNumber()
			address.email = "agung" + timestamp + "@yopmail.com"
			address.address = "Jl. Contoh No. " + random.nextInt(999)
			address.zipCode = "100000"
			fillAddressForm(address)
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