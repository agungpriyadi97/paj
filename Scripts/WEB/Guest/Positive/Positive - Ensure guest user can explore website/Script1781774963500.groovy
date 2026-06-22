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
// VERIFY HOME PAGE
//====================================================
WebUI.verifyElementVisible(findTestObject('WEB/Home/Header/Menu/menu_brands/menu_brands'))

WebUI.verifyElementVisible(findTestObject('WEB/Home/Header/Menu/menu_categories/menu_categories'))

WebUI.verifyElementVisible(findTestObject('WEB/Home/Header/Menu/menu_special_offers/menu_special_offers'))

WebUI.verifyElementVisible(findTestObject('WEB/Home/Header/Menu/menu_wholesales/menu_wholesales'))

println('MAIN MENU DISPLAYED')

//====================================================
// VERIFY BRANDS MENU
//====================================================
WebUI.mouseOver(findTestObject('WEB/Home/Header/Menu/menu_brands/menu_brands'))

WebUI.verifyElementVisible(findTestObject('WEB/Home/Header/Menu/menu_brands/lnk_24Bottles'))

WebUI.verifyElementVisible(findTestObject('WEB/Home/Header/Menu/menu_brands/lnk_HydroFlask'))

println('BRANDS MENU ACCESSIBLE')

//====================================================
// VERIFY CATEGORIES MENU
//====================================================
WebUI.mouseOver(findTestObject('WEB/Home/Header/Menu/menu_categories/menu_categories'))

WebUI.verifyElementVisible(findTestObject('WEB/Home/Header/Menu/menu_categories/lnk_HomeAndLiving'))

WebUI.verifyElementVisible(findTestObject('WEB/Home/Header/Menu/menu_categories/lnk_Tumblers'))

WebUI.verifyElementVisible(findTestObject('WEB/Home/Header/Menu/menu_categories/lnk_Fashion'))

println('CATEGORIES MENU ACCESSIBLE')

//====================================================
// VERIFY SPECIAL OFFERS
//====================================================
WebUI.click(findTestObject('WEB/Home/Header/Menu/menu_special_offers/menu_special_offers'))

WebUI.waitForPageLoad(10)

println('SPECIAL OFFERS ACCESSIBLE')

//====================================================
// VERIFY WHOLESALES
//====================================================
WebUI.click(findTestObject('WEB/Home/Header/Menu/menu_wholesales/menu_wholesales'))

WebUI.waitForPageLoad(10)

println('WHOLESALES ACCESSIBLE')

//====================================================
// TEST PASSED
//====================================================
println('GUEST USER CAN EXPLORE WEBSITE SUCCESSFULLY')

