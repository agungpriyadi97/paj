import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.testobject.TestObject as TestObject
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
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import java.util.Arrays as Arrays

WebUI.callTestCase(findTestCase('WEB/Checkout/Positive/Positive - Ensure user can checkout order successfully'), [:], FailureHandling.STOP_ON_FAILURE)

println('CHECKOUT COMPLETED')

//====================================================
// OPEN MAILINATOR INBOX
//====================================================
String email = GlobalVariable.ForgotPasswordEmail

String inboxName = email.replace('@mailinator.com', '')

println('EMAIL : ' + email)

println('INBOX : ' + inboxName)

WebUI.navigateToUrl(
	'https://www.mailinator.com/v4/public/inboxes.jsp?to=' + inboxName
)

WebUI.waitForPageLoad(30)

WebUI.delay(10)

println('MAILINATOR INBOX OPENED')

//====================================================
// WAIT ORDER CONFIRMATION EMAIL
//====================================================
boolean emailReceived = false

for (int i = 1; i <= 15; i++) {

	println('WAIT EMAIL ATTEMPT : ' + i)

	WebUI.refresh()

	WebUI.delay(5)

	if (
		WebUI.verifyElementPresent(
			findTestObject('WEB/Mailinator/latestOrderConfirmationEmail'),
			5,
			FailureHandling.OPTIONAL
		)
	) {

		emailReceived = true

		break
	}
}

assert emailReceived

println('ORDER CONFIRMATION EMAIL RECEIVED')

//====================================================
// OPEN EMAIL
//====================================================
WebUI.click(
	findTestObject('WEB/Mailinator/latestOrderConfirmationEmail')
)

WebUI.delay(10)

println('EMAIL OPENED')

//====================================================
// VERIFY EMAIL CONTENT
//====================================================
String emailText = WebUI.executeJavaScript(
	'return document.body.innerText;',
	null
)

println(emailText)

assert (
	emailText.contains('order confirmation') ||
	emailText.contains('Order Confirmation') ||
	emailText.contains('Thank you for your order') ||
	emailText.contains('Your Nataroe order')
)

println('EMAIL CONTENT VERIFIED')

//====================================================
// FINAL RESULT
//====================================================
println('======================================')
println('CUSTOMER RECEIVED ORDER CONFIRMATION EMAIL')
println('TEST CASE PASSED')
println('======================================')