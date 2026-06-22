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
// OPEN HOME PAGE
//====================================================
WebUI.maximizeWindow()

WebUI.waitForPageLoad(10)

println('HOME PAGE OPENED')

//====================================================
// VERIFY TRACK ORDER MENU
//====================================================
WebUI.verifyElementVisible(findTestObject('WEB/Home/Header/Icon Menu/menu_track_order'))

WebUI.verifyElementClickable(findTestObject('WEB/Home/Header/Icon Menu/menu_track_order'))

println('TRACK ORDER MENU DISPLAYED')

//====================================================
// OPEN TRACK ORDER
//====================================================
WebUI.click(findTestObject('WEB/Home/Header/Icon Menu/menu_track_order'))

WebUI.waitForPageLoad(10)

println('TRACK ORDER PAGE OPENED')

//====================================================
// VERIFY TRACK ORDER PAGE
//====================================================
WebUI.verifyElementVisible(findTestObject('WEB/TrackOrder/Page Track Order/lbl_TrackYourOrder'))

WebUI.verifyElementVisible(findTestObject('WEB/TrackOrder/Page Track Order/txt_Email'))

WebUI.verifyElementVisible(findTestObject('WEB/TrackOrder/Page Track Order/txt_VerificationCode'))

WebUI.verifyElementVisible(findTestObject('WEB/TrackOrder/Page Track Order/btn_Send'))

WebUI.verifyElementVisible(findTestObject('WEB/TrackOrder/Page Track Order/btn_Continue'))

println('TRACK ORDER FORM DISPLAYED')

//====================================================
// VERIFY URL
//====================================================
String currentUrl = WebUI.getUrl()

println('URL : ' + currentUrl)

assert currentUrl.contains('/order/track')

//====================================================
// TEST PASSED
//====================================================
println('GUEST USER CAN ACCESS ORDER TRACKING PAGE')

