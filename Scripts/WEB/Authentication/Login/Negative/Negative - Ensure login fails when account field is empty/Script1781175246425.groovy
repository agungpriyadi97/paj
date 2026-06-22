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

WebUI.comment('=== TC_Login_EmptyEmail: Login gagal ketika field Email kosong ===')

WebUI.waitForElementVisible(findTestObject('WEB/Home/Header/Icon Menu/btn_SignIn'), 10)

WebUI.enhancedClick(findTestObject('WEB/Home/Header/Icon Menu/btn_SignIn'), FailureHandling.STOP_ON_FAILURE)

// 1. Pastikan field Email kosong
WebUI.clearText(findTestObject('WEB/Authentication/Login/txt_Email'))

// 2. Isi field Password dengan nilai apapun (asalkan tidak kosong)
WebUI.setText(findTestObject('WEB/Authentication/Login/txt_Password'), 'dummyPassword123')

// 3. Klik tombol Sign In
WebUI.click(findTestObject('WEB/Authentication/Login/btn_sign_in'))

// 4. Verifikasi login GAGAL:
//    a. Halaman masih menampilkan tombol Sign In (berarti tidak redirect)
//    b. Atau muncul pesan error (lbl_LoginFailed) jika ada
WebUI.waitForElementPresent(findTestObject('WEB/Authentication/Login/btn_sign_in'), 5)

WebUI.verifyElementPresent(findTestObject('WEB/Authentication/Login/btn_sign_in'), 3)

// Opsional: cek apakah pesan error login gagal muncul (jika backend memvalidasi)
// Jika sistem memiliki validasi frontend untuk email wajib, gunakan label tersebut.
// Contoh: WebUI.verifyElementPresent(findTestObject('WEB/Authentication/Login/lbl_EmailRequired'), 3)
// Alternatif: cek bahwa lbl_LoginFailed muncul (misal server response "Email tidak boleh kosong")
boolean errorDisplayed = WebUI.verifyElementPresent(findTestObject('WEB/Authentication/Login/lbl_LoginFailed'), 3, com.kms.katalon.core.model.FailureHandling.OPTIONAL)

if (errorDisplayed) {
    KeywordUtil.logInfo('Pesan error login gagal muncul: ' + WebUI.getText(findTestObject('WEB/Authentication/Login/lbl_LoginFailed')))
} else {
    KeywordUtil.logInfo('Tidak ada pesan lbl_LoginFailed; namun tombol Sign In masih ada, artinya login gagal.')
}

// 5. Screenshot untuk bukti
WebUI.takeScreenshot(RunConfiguration.getReportFolder() + '/TC_Login_EmptyEmail.png')

KeywordUtil.markPassed('Validasi berhasil: Login gagal ketika field Email kosong')

