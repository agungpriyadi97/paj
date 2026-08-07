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

WebUI.comment('=== TC_Login_IncorrectPassword: Login gagal menggunakan account dan password yang benar ===')

// ==================== STEP 1: Navigasi ke halaman Sign In ====================
// Browser baru terbuka di halaman Home, klik tombol Sign In di header
WebUI.waitForElementVisible(findTestObject('WEB/Home/Header/Icon Menu/btn_SignIn'), 10)

WebUI.enhancedClick(findTestObject('WEB/Home/Header/Icon Menu/btn_SignIn'), FailureHandling.STOP_ON_FAILURE)

// Pastikan halaman Sign In terbuka
WebUI.waitForElementVisible(findTestObject('WEB/Authentication/Login/txt_Email'), 10)

// ==================== STEP 2: Test case negatif - password salah ====================
// 1. Isi field Email dengan email yang valid (terdaftar)
WebUI.setText(findTestObject('WEB/Authentication/Login/txt_Email'), GlobalVariable.ForgotPasswordEmail)

// 2. Isi field Password dengan nilai yang salah (tidak sesuai)
WebUI.setText(findTestObject('WEB/Authentication/Login/txt_Password'), GlobalVariable.ForgotPasswordNewPassword)

// 3. Klik tombol Sign In
WebUI.click(findTestObject('WEB/Authentication/Login/btn_sign_in'))

WebUI.waitForPageLoad(20)

