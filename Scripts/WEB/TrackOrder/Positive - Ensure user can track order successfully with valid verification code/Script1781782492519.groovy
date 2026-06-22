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
// TEST DATA
//====================================================
String email = 'agungpriyadi99@mailinator.com'

String inboxName = email.replace('@mailinator.com', '')

//====================================================
// OPEN TRACK ORDER
//====================================================
WebUI.waitForElementVisible(findTestObject('WEB/Home/Header/Icon Menu/menu_track_order'), 15)

WebUI.click(findTestObject('WEB/Home/Header/Icon Menu/menu_track_order'))

println('TRACK ORDER PAGE OPENED')

//====================================================
// SEND OTP
//====================================================
WebUI.waitForElementVisible(findTestObject('WEB/TrackOrder/Page Track Order/txt_Email'), 10)

WebUI.setText(findTestObject('WEB/TrackOrder/Page Track Order/txt_Email'), email)

WebUI.click(findTestObject('WEB/TrackOrder/Page Track Order/btn_Send'))

WebUI.verifyTextPresent('The verification code has been sent', false)

println('OTP SENT')

//====================================================
// OPEN MAILINATOR
//====================================================
WebUI.executeJavaScript('window.open();', null)

WebUI.switchToWindowIndex(1)

WebUI.navigateToUrl('https://www.mailinator.com')

WebUI.waitForPageLoad(10)

println('MAILINATOR OPENED')

//====================================================
// SEARCH INBOX
//====================================================
WebUI.setText(findTestObject('WEB/Mailinator/searchInput'), inboxName)

WebUI.click(findTestObject('WEB/Mailinator/goButton'))

WebUI.delay(5)

//====================================================
// OPEN LATEST EMAIL
//====================================================

WebUI.refresh()

WebUI.waitForElementVisible(
	findTestObject('WEB/TrackOrder/Mailinator/lbl_LatestEmailSubject'),
	30
)

WebUI.click(
	findTestObject('WEB/TrackOrder/Mailinator/lbl_LatestEmailSubject')
)

WebUI.delay(5)

println('LATEST EMAIL OPENED')

WebUI.switchToFrame(findTestObject('WEB/Mailinator/iframe_EmailBody'), 10)

//====================================================
// GET OTP
//====================================================
String otp = WebUI.getText(
    findTestObject('Object Repository/WEB/TrackOrder/Mailinator/lbl_OTP')
).trim()

println("OTP : " + otp)

//====================================================
// BACK TO TRACK ORDER
//====================================================
WebUI.switchToDefaultContent()

WebUI.closeWindowIndex(1)

WebUI.switchToWindowIndex(0)

//====================================================
// INPUT OTP
//====================================================
WebUI.waitForElementVisible(findTestObject('WEB/TrackOrder/Page Track Order/txt_VerificationCode'), 10)

WebUI.setText(findTestObject('WEB/TrackOrder/Page Track Order/txt_VerificationCode'), otp)

WebUI.click(findTestObject('WEB/TrackOrder/Page Track Order/btn_Continue'))

WebUI.delay(10)

WebUI.waitForElementVisible(findTestObject('Object Repository/WEB/TrackOrder/Page Track Order/lbl_TrackYourOrder'), 10)

println('TRACK ORDER SUCCESS')

