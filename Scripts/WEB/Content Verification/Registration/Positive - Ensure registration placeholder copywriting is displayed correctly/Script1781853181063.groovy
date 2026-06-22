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
// OPEN REGISTER PAGE
//====================================================
WebUI.maximizeWindow()

WebUI.waitForElementVisible(findTestObject('WEB/Home/Header/Icon Menu/btn_SignIn'), 10)

WebUI.click(findTestObject('WEB/Home/Header/Icon Menu/btn_SignIn'))

WebUI.waitForElementVisible(findTestObject('WEB/Authentication/Login/lnk_Register'), 10)

WebUI.click(findTestObject('WEB/Authentication/Login/lnk_Register'))

WebUI.waitForPageLoad(10)

//====================================================
// VERIFY PLACEHOLDER COPYWRITING
//====================================================
// Email
WebUI.verifyElementAttributeValue(findTestObject('WEB/Authentication/Registration/emailInput'), 'placeholder', 'Email', 
    5)

// Verification Code
WebUI.verifyElementAttributeValue(findTestObject('WEB/Authentication/Registration/verificationCodeInput'), 'placeholder', 
    'Validation Code', 5)

// Password
WebUI.verifyElementAttributeValue(findTestObject('WEB/Authentication/Registration/passwordInput'), 'placeholder', 'Password', 
    5)

// Mobile
WebUI.verifyElementAttributeValue(findTestObject('WEB/Authentication/Registration/mobileInput'), 'placeholder', 'Your mobile phone', 
    5)

// First Name
WebUI.verifyElementAttributeValue(findTestObject('WEB/Authentication/Registration/firstNameInput'), 'placeholder', 'First Name', 
    5)

// Last Name
WebUI.verifyElementAttributeValue(findTestObject('WEB/Authentication/Registration/lastNameInput'), 'placeholder', 'Last Name', 
    5)

println('REGISTRATION PLACEHOLDER COPYWRITING VERIFIED')

