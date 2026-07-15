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

WebUI.mouseOver(findTestObject('WEB/Home/Header/Menu/menu_categories/menu_categories'))

WebUI.verifyElementPresent(findTestObject('WEB/Home/Header/Menu/menu_categories/lnk_Tumblers'), 10)

WebUI.verifyElementVisible(findTestObject('WEB/Home/Header/Menu/menu_categories/lnk_Tumblers'))

WebUI.verifyElementClickable(findTestObject('WEB/Home/Header/Menu/menu_categories/lnk_Tumblers'))

WebUI.click(findTestObject('WEB/Home/Header/Menu/menu_categories/lnk_Tumblers'))

WebUI.waitForPageLoad(10)

not_run: WebUI.scrollToElement(findTestObject('WEB/Product/PLP/Tumblers/Filter/Size/chk_64oz'), 5)

//====================================================
// VERIFY SIZE COPYWRITING
//====================================================
not_run: WebUI.verifyElementText(findTestObject('WEB/Product/PLP/Tumblers/Filter/Size/chk_64oz'), '64oz')

not_run: WebUI.verifyElementText(findTestObject('WEB/Product/PLP/Tumblers/Filter/Size/chk_OneSize'), 'One Size')

WebUI.scrollToElement(findTestObject('WEB/Product/PLP/Tumblers/Filter/Color/chk_Beige'), 5)

//====================================================
// VERIFY COLOR COPYWRITING
//====================================================
WebUI.verifyElementVisible(findTestObject('WEB/Product/PLP/Tumblers/Filter/Color/chk_Beige'), FailureHandling.STOP_ON_FAILURE)

WebUI.verifyElementVisible(findTestObject('WEB/Product/PLP/Tumblers/Filter/Color/chk_Black'), FailureHandling.STOP_ON_FAILURE)

WebUI.verifyElementVisible(findTestObject('WEB/Product/PLP/Tumblers/Filter/Color/lbl_HotPink'), FailureHandling.STOP_ON_FAILURE)

WebUI.verifyElementVisible(findTestObject('WEB/Product/PLP/Tumblers/Filter/Color/lbl_LightBlue'), FailureHandling.STOP_ON_FAILURE)

WebUI.verifyElementVisible(findTestObject('WEB/Product/PLP/Tumblers/Filter/Color/lbl_LightGreen'), FailureHandling.STOP_ON_FAILURE)

WebUI.verifyElementVisible(findTestObject('WEB/Product/PLP/Tumblers/Filter/Color/lbl_LightPink'), FailureHandling.STOP_ON_FAILURE)

WebUI.verifyElementVisible(findTestObject('WEB/Product/PLP/Tumblers/Filter/Color/lbl_NavyBlue'), FailureHandling.STOP_ON_FAILURE)

WebUI.verifyElementVisible(findTestObject('WEB/Product/PLP/Tumblers/Filter/Color/lbl_StainlessSteel'), FailureHandling.STOP_ON_FAILURE)

WebUI.verifyElementText(findTestObject('WEB/Product/PLP/Tumblers/Filter/Color/chk_White'), 'White')

println('PLP SIZE & COLOR COPYWRITING VERIFIED')

