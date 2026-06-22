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

WebUI.comment('=== TC_Login_InvalidEmailFormat: Login gagal menggunakan format email tidak valid ===')

// ==================== STEP 1: Navigasi ke halaman Sign In ====================
// Browser baru terbuka di halaman Home, klik tombol Sign In di header
WebUI.waitForElementVisible(findTestObject('WEB/Home/Header/Icon Menu/btn_SignIn'), 10)

WebUI.enhancedClick(findTestObject('WEB/Home/Header/Icon Menu/btn_SignIn'), FailureHandling.STOP_ON_FAILURE)

// Pastikan halaman Sign In terbuka
WebUI.waitForElementVisible(findTestObject('WEB/Authentication/Login/txt_Email'), 10)

// ==================== STEP 2: Test case negatif - format email tidak valid ====================
// 1. Isi field Email dengan format yang tidak valid (contoh: tanpa '@', tanpa domain, dll)
//    Beberapa contoh format email tidak valid:
//    - "userexample.com" (tanpa @)
//    - "user@" (tanpa domain)
//    - "@example.com" (tanpa local-part)
//    - "user@.com" (domain tidak lengkap)
WebUI.setText(findTestObject('WEB/Authentication/Login/txt_Email'), 'userinvalidemail.com')

// 2. Isi field Password dengan nilai apapun (asumsi tidak kosong)
WebUI.setText(findTestObject('WEB/Authentication/Login/txt_Password'), 'anyPassword123')

// 3. Klik tombol Sign In
WebUI.click(findTestObject('WEB/Authentication/Login/btn_sign_in'))

// 4. Verifikasi login GAGAL:
//    a. Muncul pesan error (lbl_LoginFailed atau pesan validasi format email)
//    b. Tombol Sign In masih ada (tidak redirect)
// Opsi 1: Jika ada pesan error spesifik untuk format email (misal lbl_EmailInvalid)
// WebUI.waitForElementVisible(findTestObject('WEB/Authentication/Login/lbl_EmailInvalid'), 5)
// WebUI.verifyElementText(findTestObject('WEB/Authentication/Login/lbl_EmailInvalid'), 'Please enter a valid email address')
// Opsi 2: Menggunakan lbl_LoginFailed (jika validasi dilakukan oleh backend)
boolean errorDisplayed = WebUI.verifyElementPresent(findTestObject('WEB/Authentication/Login/lbl_LoginFailed'), 5, FailureHandling.OPTIONAL)

if (errorDisplayed) {
    KeywordUtil.logInfo('Pesan error login gagal muncul: ' + WebUI.getText(findTestObject('WEB/Authentication/Login/lbl_LoginFailed'))) // Jika tidak ada pesan error, minimal pastikan halaman tidak redirect (masih di halaman login)
} else {
    KeywordUtil.logInfo('Tidak ada pesan error spesifik, verifikasi bahwa tombol Sign In masih ada')
}

// Verifikasi tombol Sign In masih ada (berarti login gagal dan tidak redirect)
WebUI.verifyElementPresent(findTestObject('WEB/Authentication/Login/btn_sign_in'), 3)

// 5. Screenshot untuk bukti
WebUI.takeScreenshot(RunConfiguration.getReportFolder() + '/TC_Login_InvalidEmailFormat.png')

KeywordUtil.markPassed('Validasi berhasil: Login gagal menggunakan format email tidak valid')

