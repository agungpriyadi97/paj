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

String email = 'agungpriyadi99@mailinator.com'

WebUI.waitForElementVisible(findTestObject('WEB/Home/Header/Icon Menu/menu_track_order'), 15)

WebUI.click(findTestObject('WEB/Home/Header/Icon Menu/menu_track_order'))

println('TRACK ORDER PAGE OPENED')

// Input Email
WebUI.setText(findTestObject('WEB/TrackOrder/Page Track Order/txt_Email'), email)

println('EMAIL FILLED')

// Verification Code dikosongkan
WebUI.setText(findTestObject('WEB/TrackOrder/Page Track Order/txt_VerificationCode'), '')

println('VERIFICATION CODE EMPTY')

// Verify Continue Disabled
WebUI.verifyElementHasAttribute(findTestObject('WEB/TrackOrder/Page Track Order/btn_Continue'), 'disabled', 10)

println('CONTINUE BUTTON DISABLED')

// Alternatif
assert !(WebUI.verifyElementClickable(findTestObject('WEB/TrackOrder/btn_Continue'), FailureHandling.OPTIONAL))

println('TEST PASSED')

