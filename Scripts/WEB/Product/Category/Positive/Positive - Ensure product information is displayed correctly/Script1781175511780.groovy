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
// VERIFY PRODUCT CARD
//====================================================
WebUI.verifyElementVisible(findTestObject('WEB/Product/PLP/Product/card_FirstProduct'))

println('PRODUCT CARD DISPLAYED')

//====================================================
// VERIFY PRODUCT IMAGE
//====================================================
WebUI.verifyElementVisible(findTestObject('WEB/Product/PLP/Product/img_FirstProduct'))

println('PRODUCT IMAGE DISPLAYED')

//====================================================
// VERIFY PRODUCT NAME
//====================================================
WebUI.verifyElementVisible(findTestObject('WEB/Product/PLP/Product/lbl_FirstProductName'))

String productName = WebUI.getText(findTestObject('WEB/Product/PLP/Product/lbl_FirstProductName'))

println('PRODUCT NAME : ' + productName)

assert productName.trim() != ''

//====================================================
// VERIFY PRODUCT PRICE
//====================================================
WebUI.verifyElementVisible(findTestObject('WEB/Product/PLP/Product/lbl_FirstProductSellingPrice'))

String sellingPrice = WebUI.getText(findTestObject('WEB/Product/PLP/Product/lbl_FirstProductSellingPrice'))

println('SELLING PRICE : ' + sellingPrice)

assert sellingPrice.contains('Rp')

//====================================================
// VERIFY PRODUCT INFORMATION
//====================================================
println('PRODUCT INFORMATION DISPLAYED CORRECTLY')

