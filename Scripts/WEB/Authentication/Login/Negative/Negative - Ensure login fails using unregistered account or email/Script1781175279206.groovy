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

// ======================== STEP 1: BUKA HALAMAN LOGIN ========================
// ======================== STEP 2: INPUT DATA TIDAK TERDAFTAR ========================
WebUI.waitForElementVisible(findTestObject('WEB/Home/Header/Icon Menu/btn_SignIn'), 10)

WebUI.enhancedClick(findTestObject('WEB/Home/Header/Icon Menu/btn_SignIn'), FailureHandling.STOP_ON_FAILURE)

WebUI.setText(findTestObject('WEB/Authentication/Login/txt_Email'), 'agungpriyadi@gmail.com')

WebUI.setText(findTestObject('WEB/Authentication/Login/txt_Password'), GlobalVariable.ForgotPasswordNewPassword)

// ======================== STEP 3: KLIK TOMBOL LOGIN ========================
WebUI.click(findTestObject('WEB/Authentication/Login/btn_sign_in'))

// ======================== STEP 4: VERIFIKASI PESAN ERROR ========================
WebUI.waitForElementVisible(findTestObject('WEB/Authentication/Login/msg_account_or_email_not_exist'), 5)

String actualError = WebUI.getText(findTestObject('WEB/Authentication/Login/msg_account_or_email_not_exist'))

