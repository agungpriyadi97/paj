import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

WebUI.waitForElementVisible(findTestObject('WEB/Home/Header/Icon Menu/menu_store_location'), 10)

WebUI.waitForElementPresent(findTestObject('WEB/Home/Header/Icon Menu/menu_store_location'), 5)

WebUI.click(findTestObject('WEB/Home/Header/Icon Menu/menu_store_location'))

WebUI.waitForPageLoad(10)

//====================================================
// VERIFY COPYWRITING
//====================================================
// Title
WebUI.verifyElementText(findTestObject('WEB/Home/Header/Menu/StoreLocation/lbl_StoreLocationTitle'), 'Store Location')

// Placeholder Select
WebUI.verifyElementAttributeValue(findTestObject('WEB/Home/Header/Menu/StoreLocation/txt_SelectPlaceholder'), 'placeholder', 
    'Select', 5)

// Store(s)
WebUI.verifyElementText(findTestObject('WEB/Home/Header/Menu/StoreLocation/lbl_StoreCount'), 'Store(s)')

println('STORE LOCATION COPYWRITING VERIFIED')

