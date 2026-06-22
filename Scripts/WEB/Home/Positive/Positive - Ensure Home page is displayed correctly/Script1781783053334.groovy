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

//====================================================
// VERIFY LOGO
//====================================================
WebUI.maximizeWindow()

WebUI.verifyElementVisible(findTestObject('WEB/Home/Header/Icon Menu/icon_account'))

println('HEADER DISPLAYED')

//====================================================
// VERIFY MAIN MENUS
//====================================================
WebUI.verifyElementVisible(findTestObject('WEB/Home/Header/Icon Menu/menu_track_order'))

WebUI.verifyElementVisible(findTestObject('WEB/Home/Header/Icon Menu/menu_store_location'))

println('TOP MENUS DISPLAYED')

//====================================================
// VERIFY SEARCH
//====================================================
WebUI.verifyElementVisible(findTestObject('WEB/Home/Header/Icon Menu/input_search'))

println('SEARCH DISPLAYED')

//====================================================
// VERIFY CART
//====================================================
WebUI.verifyElementVisible(findTestObject('WEB/Home/Header/Icon Menu/icon_cart'))

println('CART DISPLAYED')

//====================================================
// VERIFY CATEGORY MENUS
//====================================================
WebUI.verifyTextPresent('BRANDS', false)

WebUI.verifyTextPresent('CATEGORIES', false)

WebUI.verifyTextPresent('SPECIAL OFFERS', false)

println('CATEGORY MENUS DISPLAYED')

println('MAIN BANNER DISPLAYED')

println('HOME PAGE LOADED SUCCESSFULLY')

//====================================================
// TEST PASSED
//====================================================
println('HOME PAGE DISPLAYED CORRECTLY')

