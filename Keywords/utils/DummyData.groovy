package utils

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
import java.util.Random

class DummyData {

	Random random = new Random()

	//=========================================
	// REGISTRATION
	//=========================================

	@Keyword
	Map generateRegistrationData() {

		String timestamp = System.currentTimeMillis().toString()

		return [
			email    : "qa${timestamp}@yopmail.com",
			account  : "qa${timestamp}",
			password : "Laskar123456@"
		]
	}

	//=========================================
	// ADDRESS
	//=========================================

	@Keyword
	Map generateAddressData() {

		List<String> firstNames = [
			"Agung",
			"Aldi",
			"Rizky",
			"Fajar",
			"Dimas",
			"Andi",
			"Bayu",
			"Yoga",
			"Rian",
			"Budi",
			"Arif",
			"Rama",
			"Reza",
			"Nanda",
			"Ilham"
		]

		List<String> lastNames = [
			"Pratama",
			"Kurniawan",
			"Saputra",
			"Nugraha",
			"Wijaya",
			"Permana",
			"Santoso",
			"Ramadhan",
			"Setiawan",
			"Putra",
			"Hidayat",
			"Firmansyah",
			"Mahendra",
			"Gunawan",
			"Prasetyo"
		]

		List<String> streets = [
			"Jl. Melati",
			"Jl. Mawar",
			"Jl. Kenanga",
			"Jl. Anggrek",
			"Jl. Dahlia",
			"Jl. Cempaka",
			"Jl. Mangga",
			"Jl. Durian",
			"Jl. Flamboyan",
			"Jl. Merdeka"
		]

		String firstName = firstNames[random.nextInt(firstNames.size())]
		String lastName = lastNames[random.nextInt(lastNames.size())]

		String mobilePhone = "08"

		for(int i=0;i<10;i++) {
			mobilePhone += random.nextInt(10)
		}

		String address = streets[random.nextInt(streets.size())] +
				" No. " + (random.nextInt(200) + 1)

		return [
			firstName  : firstName,
			lastName   : lastName,
			mobilePhone: mobilePhone,
			address    : address,
			postalCode : "15810"
		]
	}
}