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

String keyword = 'Hydro Flask'

//====================================================
// VERIFY SEARCH MENU
//====================================================
WebUI.verifyElementVisible(findTestObject('WEB/Home/Header/Search/txt_Search'))

WebUI.click(findTestObject('WEB/Home/Header/Search/txt_Search'), FailureHandling.OPTIONAL)

WebUI.setText(findTestObject('WEB/Home/Header/Search/txt_Search'), 'Hydro Flask')

println('SEARCH MENU DISPLAYED')

String actualText = WebUI.getAttribute(findTestObject('WEB/Home/Header/Search/txt_Search'), 'value')

assert actualText == keyword

println('KEYWORD ENTERED SUCCESSFULLY')

//====================================================
// CLEAR SEARCH FIELD
//====================================================
WebUI.clearText(findTestObject('WEB/Home/Header/Search/txt_Search'))

String clearedText = WebUI.getAttribute(findTestObject('WEB/Home/Header/Search/txt_Search'), 'value')

assert clearedText == ''

println('SEARCH FIELD CLEARED SUCCESSFULLY')

//====================================================
// TEST PASSED
//====================================================
println('SEARCH MENU IS DISPLAYED AND ACCESSIBLE')

