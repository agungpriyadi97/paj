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
// VERIFY TRACK ORDER MENU
//====================================================
WebUI.verifyElementPresent(findTestObject('WEB/Home/Header/Icon Menu/menu_track_order'), 10)

WebUI.verifyElementVisible(findTestObject('WEB/Home/Header/Icon Menu/menu_track_order'))

WebUI.verifyElementClickable(findTestObject('WEB/Home/Header/Icon Menu/menu_track_order'))

println('TRACK ORDER MENU DISPLAYED')

//====================================================
// OPEN TRACK ORDER PAGE
//====================================================
WebUI.click(findTestObject('WEB/Home/Header/Icon Menu/menu_track_order'))

WebUI.waitForPageLoad(10)

println('TRACK ORDER PAGE OPENED')

//====================================================
// VERIFY URL
//====================================================
String currentUrl = WebUI.getUrl()

println('CURRENT URL : ' + currentUrl)

assert currentUrl.toLowerCase().contains('track')

println('TRACK ORDER PAGE VERIFIED')

//====================================================
// TEST PASSED
//====================================================
println('USER CAN ACCESS TRACK ORDER PAGE SUCCESSFULLY')

