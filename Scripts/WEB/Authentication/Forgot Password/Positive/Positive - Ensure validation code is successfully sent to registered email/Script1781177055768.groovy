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

String email = GlobalVariable.ForgotPasswordEmail

String inboxName = email.replace('@mailinator.com', '')

WebUI.waitForElementVisible(findTestObject('WEB/Home/Header/Icon Menu/btn_SignIn'), 10)

WebUI.enhancedClick(findTestObject('WEB/Home/Header/Icon Menu/btn_SignIn'), FailureHandling.STOP_ON_FAILURE)

WebUI.waitForElementVisible(findTestObject('WEB/Authentication/Login/lnk_ForgotPassword'), 10)

WebUI.enhancedClick(findTestObject('WEB/Authentication/Login/lnk_ForgotPassword'), FailureHandling.STOP_ON_FAILURE)

//====================================================
// INPUT EMAIL
//====================================================
WebUI.setText(findTestObject('WEB/Authentication/ForgotPassword/txt_Email'), email)

//====================================================
// SEND CODE
//====================================================
WebUI.click(findTestObject('WEB/Authentication/ForgotPassword/btn_SendValidationCode'))

println('SEND CODE SUCCESS')

WebUI.delay(3)

//====================================================
// OPEN MAILINATOR
//====================================================
WebUI.executeJavaScript('window.open(\'https://www.mailinator.com/\')', null)

WebUI.switchToWindowIndex(1)

//====================================================
// SEARCH INBOX
//====================================================
WebUI.setText(findTestObject('WEB/Mailinator/searchInput'), inboxName)

WebUI.click(findTestObject('WEB/Mailinator/goButton'))

//====================================================
// VERIFY EMAIL RECEIVED
//====================================================
boolean emailFound = false

for (int i = 1; i <= 12; i++) {
    WebUI.delay(5)

    try {
        WebUI.refresh()

        WebUI.verifyElementVisible(findTestObject('WEB/Mailinator/latestEmailForgotPassword'))

        emailFound = true

        break
    }
    catch (Exception e) {
        println('WAIT EMAIL...')
    } 
}

assert emailFound

println('FORGOT PASSWORD EMAIL RECEIVED')

