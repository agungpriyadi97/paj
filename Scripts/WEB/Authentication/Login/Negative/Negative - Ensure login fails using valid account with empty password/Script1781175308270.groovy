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
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration

WebUI.comment('=== TC_Login_ValidAccountEmptyPassword: Login gagal menggunakan akun valid dengan password kosong ===')

// ==================== STEP 1: Navigasi ke halaman Sign In ====================
// Browser baru terbuka di halaman Home, klik tombol Sign In di header
WebUI.waitForElementVisible(findTestObject('WEB/Home/Header/Icon Menu/btn_SignIn'), 10)

WebUI.enhancedClick(findTestObject('WEB/Home/Header/Icon Menu/btn_SignIn'), FailureHandling.STOP_ON_FAILURE)

// Pastikan halaman Sign In terbuka
WebUI.waitForElementVisible(findTestObject('WEB/Authentication/Login/txt_Email'), 10)

// ==================== STEP 2: Test case negatif - akun valid, password kosong ====================
// 1. Isi field Email dengan email yang valid (terdaftar)
WebUI.setText(findTestObject('WEB/Authentication/Login/txt_Email'), 'registered_user@example.com')

// 2. Pastikan field Password dalam keadaan kosong
WebUI.clearText(findTestObject('WEB/Authentication/Login/txt_Password'))

// 3. Klik tombol Sign In
WebUI.click(findTestObject('WEB/Authentication/Login/btn_sign_in'))

// 4. Verifikasi login GAGAL:
//    a. Muncul pesan error lbl_PasswordRequired (karena password kosong)
//    b. Tombol Sign In masih ada (tidak redirect ke halaman sukses)
WebUI.waitForElementVisible(findTestObject('WEB/Authentication/Login/lbl_PasswordRequired'), 5)

// Verifikasi tombol Sign In masih ada (berarti belum redirect)
WebUI.verifyElementPresent(findTestObject('WEB/Authentication/Login/btn_sign_in'), 3)

// 5. Screenshot untuk bukti
WebUI.takeScreenshot(RunConfiguration.getReportFolder() + '/TC_Login_ValidAccountEmptyPassword.png')

KeywordUtil.markPassed('Validasi berhasil: Login gagal menggunakan akun valid dengan password kosong')

