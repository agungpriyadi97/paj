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
// OPEN TRACK ORDER PAGE
//====================================================
WebUI.click(findTestObject('WEB/Home/Header/Icon Menu/menu_track_order'))

WebUI.waitForPageLoad(10)

//====================================================
// VERIFY COPYWRITING
//====================================================
// Title
WebUI.verifyElementText(findTestObject('WEB/TrackOrder/Page Track Order/lbl_TrackYourOrder'), 'Track Your Order')

// Description
WebUI.verifyElementText(findTestObject('WEB/TrackOrder/Page Track Order/lbl_Description'), 'Please enter the email address you filled in when you placed the order.')

// Email Label
WebUI.verifyElementText(findTestObject('WEB/TrackOrder/Page Track Order/lbl_Email'), 'Email')

// Verification Code Label
WebUI.verifyElementText(findTestObject('WEB/TrackOrder/Page Track Order/lbl_VerificationCode'), 'Verification Code')

// Send Button
WebUI.verifyTextPresent('Send', false)

// Continue Button
WebUI.verifyTextPresent('Continue', false)

println('TRACK ORDER COPYWRITING VERIFIED')

