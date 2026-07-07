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
// LOGIN
//====================================================
WebUI.callTestCase(findTestCase('WEB/Authentication/Login/Positive/Positive - Ensure user can login with valid account and password'), 
    [:], FailureHandling.STOP_ON_FAILURE)

println('LOGIN SUCCESS')

WebUI.delay(5)

//====================================================
// OPEN PDP DIRECTLY
//====================================================
WebUI.navigateToUrl('https://d-speedshop-pastiadajalan.gtechdigital.id/pdp/SP260706006287')

WebUI.waitForPageLoad(30)

println('PDP PAGE OPENED')

//====================================================
// ADD TO CART
//====================================================
WebUI.waitForElementPresent(findTestObject('WEB/Product/PDP/btn_AddToCart'), 30)

WebUI.waitForElementVisible(findTestObject('WEB/Product/PDP/btn_AddToCart'), 30)

WebUI.waitForElementClickable(findTestObject('WEB/Product/PDP/btn_AddToCart'), 30)

WebUI.scrollToElement(findTestObject('WEB/Product/PDP/btn_AddToCart'), 10)

WebUI.enhancedClick(findTestObject('WEB/Product/PDP/btn_AddToCart'))

WebUI.delay(3)

println('PRODUCT ADDED TO CART')

//====================================================
// OPEN CART
//====================================================
WebUI.waitForElementClickable(findTestObject('WEB/Home/Header/Icon Menu/icon_cart'), 20)

WebUI.enhancedClick(findTestObject('WEB/Home/Header/Icon Menu/icon_cart'))

WebUI.waitForPageLoad(30)

println('SHOPPING CART OPENED')

//====================================================
// VERIFY SHOPPING CART PAGE
//====================================================
WebUI.verifyElementVisible(findTestObject('WEB/Cart/lbl_ShoppingCartTitle'))

println('SHOPPING CART PAGE DISPLAYED')

//====================================================
// VERIFY PRODUCT DISPLAYED
//====================================================
WebUI.verifyElementVisible(findTestObject('WEB/Cart/img_Product'))

WebUI.verifyElementVisible(findTestObject('WEB/Cart/lbl_ProductName'))

WebUI.verifyElementVisible(findTestObject('WEB/Cart/lbl_ProductSKU'))

println('PRODUCT DISPLAYED IN CART')

//====================================================
// VERIFY PRICE
//====================================================
WebUI.verifyElementVisible(findTestObject('WEB/Cart/lbl_SellingPrice'))

println('PRODUCT PRICE DISPLAYED')

//====================================================
// VERIFY QTY (HEADLESS SAFE)
//====================================================
String qty = WebUI.executeJavaScript('\nvar qtyInput =\ndocument.querySelector(\n\'input[role="spinbutton"]\'\n);\n\nif(!qtyInput){\nreturn \'0\';\n}\n\nreturn qtyInput.getAttribute(\'aria-valuenow\')\n|| qtyInput.value\n|| \'0\';\n', 
    null)

println('QTY : ' + qty)

assert qty.isInteger()

assert qty.toInteger() > 0

println('QTY VERIFIED')

//====================================================
// VERIFY ORDER SUMMARY
//====================================================
WebUI.verifyElementVisible(findTestObject('WEB/Cart/lbl_OrderSummary'))

WebUI.verifyElementVisible(findTestObject('WEB/Cart/lbl_Subtotal'))

WebUI.verifyElementVisible(findTestObject('WEB/Cart/lbl_Total'))

println('ORDER SUMMARY DISPLAYED')

//====================================================
// TEST PASSED
//====================================================
println('======================================')

println('SHOPPING CART PAGE DISPLAYED')

println('PRODUCT DISPLAYED IN CART')

println('PRODUCT PRICE DISPLAYED')

println('QTY VERIFIED : ' + qty)

println('ORDER SUMMARY DISPLAYED')

println('USER CAN ADD PRODUCT TO SHOPPING CART SUCCESSFULLY')

println('TEST CASE PASSED')

println('======================================')

