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

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.Keys as Keys

String keyword = 'Hydro Flask'

//====================================================
// VERIFY SEARCH BAR
//====================================================
WebUI.verifyElementVisible(
	findTestObject('WEB/Home/Header/Search/txt_Search')
)

println('SEARCH BAR DISPLAYED')

//====================================================
// INPUT SEARCH KEYWORD
//====================================================
WebUI.setText(
	findTestObject('WEB/Home/Header/Search/txt_Search'),
	keyword
)

WebUI.sendKeys(
	findTestObject('WEB/Home/Header/Search/txt_Search'),
	Keys.chord(Keys.ENTER)
)

println('SEARCH EXECUTED')

//====================================================
// VERIFY SEARCH RESULT PAGE
//====================================================
WebUI.waitForPageLoad(10)

WebUI.verifyElementVisible(
	findTestObject('WEB/Home/Header/Search/lbl_SearchResult')
)

println('SEARCH RESULT PAGE DISPLAYED')

//====================================================
// VERIFY PRODUCT RESULT DISPLAYED
//====================================================
WebUI.verifyElementVisible(
	findTestObject('WEB/Home/Header/Search/card_Product')
)

println('PRODUCT RESULT DISPLAYED')

//====================================================
// VERIFY FIRST PRODUCT NAME
//====================================================
String productName = WebUI.getText(
	findTestObject('WEB/Home/Header/Search/lbl_FirstProductName')
)

println('FOUND PRODUCT : ' + productName)

assert productName.toLowerCase().contains('hydro')

println('SEARCH RESULT MATCHED')

//====================================================
// TEST PASSED
//====================================================
println('USER CAN SEARCH PRODUCT SUCCESSFULLY')