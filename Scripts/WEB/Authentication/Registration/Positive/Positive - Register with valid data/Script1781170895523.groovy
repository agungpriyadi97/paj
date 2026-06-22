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
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import java.util.Random as Random

//====================================================
// TEST DATA
//====================================================
long ts = System.currentTimeMillis()

String inboxName = 'qa' + ts

String email = inboxName + '@mailinator.com'

String password = 'Testing123'

String mobile = '08' + ts.toString().takeRight(10)

String firstName = 'Agung'

String lastName = 'Priyadi'

println('EMAIL  : ' + email)

println('MOBILE : ' + mobile)

WebUI.waitForElementVisible(findTestObject('WEB/Home/Header/Icon Menu/btn_SignIn'), 10)

WebUI.enhancedClick(findTestObject('WEB/Home/Header/Icon Menu/btn_SignIn'), FailureHandling.STOP_ON_FAILURE)

WebUI.waitForElementVisible(findTestObject('WEB/Authentication/Login/lnk_Register'), 10)

WebUI.enhancedClick(findTestObject('WEB/Authentication/Login/lnk_Register'), FailureHandling.STOP_ON_FAILURE)

//====================================================
// INPUT EMAIL
//====================================================
WebUI.waitForElementVisible(findTestObject('WEB/Authentication/Registration/emailInput'), 20)

WebUI.setText(findTestObject('WEB/Authentication/Registration/emailInput'), email)

//====================================================
// SEND OTP
//====================================================
WebUI.click(findTestObject('WEB/Authentication/Registration/getCodeButton'))

WebUI.waitForPageLoad(10)

WebUI.delay(2)

println('OTP SENT')

//====================================================
// OPEN MAILINATOR
//====================================================
WebUI.executeJavaScript('window.open(\'https://www.mailinator.com/\')', null)

WebUI.switchToWindowIndex(1)

WebUI.waitForPageLoad(20)

//====================================================
// SEARCH INBOX
//====================================================
WebUI.waitForElementVisible(findTestObject('WEB/Mailinator/searchInput'), 20)

WebUI.setText(findTestObject('WEB/Mailinator/searchInput'), inboxName)

WebUI.click(findTestObject('WEB/Mailinator/goButton'))

//====================================================
// WAIT EMAIL
//====================================================
boolean emailFound = false

for (int i = 1; i <= 12; i++) {
    WebUI.delay(5)

    try {
        WebUI.refresh()

        WebUI.click(findTestObject('WEB/Mailinator/latestEmail'))

        emailFound = true

        println('EMAIL FOUND')

        break
    }
    catch (Exception e) {
        println('WAIT EMAIL...')
    } 
}

assert emailFound

//====================================================
// GET OTP
//====================================================
String otp = ''

WebDriver driver = DriverFactory.getWebDriver()

for (int i = 1; i <= 10; i++) {
    WebUI.delay(2)

    String source = driver.getPageSource()

    def matcher = source =~ '(?:Verification Code|Kode Verifikasi)\\s*:\\s*(\\d{6})'

    if (matcher.find()) {
        otp = matcher.group(1)

        break
    }
}

println('OTP = ' + otp)

assert otp != ''

//====================================================
// BACK TO REGISTER PAGE
//====================================================
WebUI.switchToWindowIndex(0)

//====================================================
// INPUT OTP
//====================================================
WebUI.setText(findTestObject('WEB/Authentication/Registration/verificationCodeInput'), otp)

//====================================================
// INPUT PASSWORD
//====================================================
WebUI.setText(findTestObject('WEB/Authentication/Registration/passwordInput'), password)

//====================================================
// INPUT MOBILE
//====================================================
WebUI.setText(findTestObject('WEB/Authentication/Registration/mobileInput'), mobile)

//====================================================
// INPUT FIRST NAME
//====================================================
WebUI.setText(findTestObject('WEB/Authentication/Registration/firstNameInput'), firstName)

//====================================================
// INPUT LAST NAME
//====================================================
WebUI.setText(findTestObject('WEB/Authentication/Registration/lastNameInput'), lastName)

//====================================================
// CHECK CONSENT
//====================================================
WebUI.click(findTestObject('WEB/Authentication/Registration/consentCheckBox'))

//====================================================
// SIGN UP
//====================================================
WebUI.click(findTestObject('WEB/Authentication/Registration/signUpButton'))

WebUI.delay(3)

//====================================================
// SAVE REGISTERED EMAIL
//====================================================
GlobalVariable.RegisteredEmail = email

println('===================================')

println('REGISTER SUCCESS')

println('REGISTERED EMAIL : ' + GlobalVariable.RegisteredEmail)

println('MOBILE           : ' + mobile)

println('===================================')

