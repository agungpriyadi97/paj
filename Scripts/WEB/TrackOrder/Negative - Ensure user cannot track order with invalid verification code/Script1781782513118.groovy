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

WebUI.waitForElementVisible(findTestObject('WEB/Home/Header/Icon Menu/menu_track_order'), 15)

WebUI.click(findTestObject('WEB/Home/Header/Icon Menu/menu_track_order'))

println('TRACK ORDER PAGE OPENED')

WebUI.setText(findTestObject('WEB/TrackOrder/Page Track Order/txt_Email'), GlobalVariable.ForgotPasswordEmail)

WebUI.click(findTestObject('WEB/TrackOrder/Page Track Order/btn_Send'))

WebUI.delay(2)

WebUI.setText(findTestObject('WEB/TrackOrder/Page Track Order/txt_VerificationCode'), '004669')

WebUI.click(findTestObject('WEB/TrackOrder/Page Track Order/btn_Continue'))

WebUI.waitForElementVisible(findTestObject('WEB/TrackOrder/Page Track Order/txt_VerificationCodeError'), 10)

WebUI.verifyElementVisible(findTestObject('WEB/TrackOrder/Page Track Order/txt_VerificationCodeError'))

WebUI.verifyElementText(findTestObject('WEB/TrackOrder/Page Track Order/txt_VerificationCodeError'), 'Verification code error.')

WebUI.delay(2)

