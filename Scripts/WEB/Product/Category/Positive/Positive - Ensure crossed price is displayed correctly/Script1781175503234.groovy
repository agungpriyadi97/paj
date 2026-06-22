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
// OPEN HOME & LIVING PAGE
//====================================================
WebUI.navigateToUrl('https://d-speedshop-pastiadajalan.gtechdigital.id/home-and-living')

WebUI.waitForPageLoad(10)

//====================================================
// GET ORIGINAL PRICE
//====================================================
String originalPrice = WebUI.getText(findTestObject('WEB/Product/PLP/Product/lbl_OriginalPrice'))

println('ORIGINAL PRICE : ' + originalPrice)

//====================================================
// GET SELLING PRICE
//====================================================
String sellingPrice = WebUI.getText(findTestObject('WEB/Product/PLP/Product/lbl_SellingPrice'))

println('SELLING PRICE : ' + sellingPrice)

//====================================================
// CONVERT TO NUMBER
//====================================================
Long originalAmount = ((originalPrice.replace('Rp', '').replace('.', '').replace(',', '').trim()) as Long)

Long sellingAmount = ((sellingPrice.replace('Rp', '').replace('.', '').replace(',', '').trim()) as Long)

//====================================================
// VERIFY
//====================================================
assert originalAmount > sellingAmount

println('CROSSED PRICE DISPLAYED CORRECTLY')

