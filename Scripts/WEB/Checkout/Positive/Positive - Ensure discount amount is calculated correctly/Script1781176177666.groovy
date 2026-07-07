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

println('PRODUCT ADDED TO CART')

WebUI.delay(5)

//====================================================
// OPEN CART
//====================================================
WebUI.waitForElementClickable(findTestObject('WEB/Home/Header/Icon Menu/icon_cart'), 20)

WebUI.enhancedClick(findTestObject('WEB/Home/Header/Icon Menu/icon_cart'))

WebUI.waitForPageLoad(30)

println('SHOPPING CART OPENED')

//====================================================
// CHECKOUT
//====================================================
WebUI.scrollToElement(findTestObject('WEB/Cart/btn_Checkout'), 10)

WebUI.enhancedClick(findTestObject('WEB/Cart/btn_Checkout'))

WebUI.waitForPageLoad(30)

println('CHECKOUT PAGE OPENED')

WebUI.delay(5)

//====================================================
// GET SUBTOTAL BEFORE PROMO
//====================================================
String subtotalText = WebUI.getText(findTestObject('WEB/Checkout/OrderSummary/lbl_Subtotal'))

Long subtotal = subtotalText.replace('Rp', '').replace('.', '').replace(',', '').trim().toLong()

println('SUBTOTAL : ' + subtotal)

//====================================================
// APPLY PROMO
//====================================================
WebUI.waitForElementVisible(findTestObject('WEB/Checkout/Promotion/txt_PromoCode'), 20)

WebUI.setText(findTestObject('WEB/Checkout/Promotion/txt_PromoCode'), 'agung')

WebUI.enhancedClick(findTestObject('WEB/Checkout/Promotion/btn_ApplyPromo'))

println('PROMO APPLIED')

WebUI.delay(5)

//====================================================
// VERIFY COUPON DISCOUNT DISPLAYED
//====================================================
WebUI.waitForElementVisible(findTestObject('WEB/Checkout/OrderSummary/lbl_CouponDiscount'), 20)

String discountText = WebUI.getText(findTestObject('WEB/Checkout/OrderSummary/lbl_CouponDiscount'))

println('DISCOUNT TEXT : ' + discountText)

assert discountText.contains('Rp')

//====================================================
// GET ACTUAL DISCOUNT
//====================================================
Long actualDiscount = discountText.replace('-Rp', '').replace('Rp', '').replace('.', '').replace(',', '').trim().toLong()

println('ACTUAL DISCOUNT : ' + actualDiscount)

//====================================================
// EXPECTED DISCOUNT = 10% SUBTOTAL
//====================================================
Long expectedDiscount = (subtotal * 10) / 100

println('EXPECTED DISCOUNT : ' + expectedDiscount)

assert actualDiscount == expectedDiscount

println('DISCOUNT VERIFIED')

//====================================================
// SHIPPING FEE
//====================================================
Long shippingFee = WebUI.getText(findTestObject('WEB/Checkout/OrderSummary/lbl_ShippingFee')).replace('Rp', '').replace(
    '.', '').replace(',', '').trim().toLong()

println('SHIPPING FEE : ' + shippingFee)

//====================================================
// INSURANCE FEE
//====================================================
Long insuranceFee = WebUI.getText(findTestObject('WEB/Checkout/OrderSummary/lbl_InsuranceFee')).replace('Rp', '').replace(
    '.', '').replace(',', '').trim().toLong()

println('INSURANCE FEE : ' + insuranceFee)

//====================================================
// ACTUAL TOTAL
//====================================================
Long actualTotal = WebUI.getText(findTestObject('WEB/Checkout/OrderSummary/lbl_Total')).replace('Rp', '').replace('.', '').replace(
    ',', '').trim().toLong()

println('ACTUAL TOTAL : ' + actualTotal)

//====================================================
// EXPECTED TOTAL
//====================================================
Long expectedTotal = ((subtotal - actualDiscount) + shippingFee) + insuranceFee

println('EXPECTED TOTAL : ' + expectedTotal)

assert actualTotal == expectedTotal

println('TOTAL VERIFIED')

//====================================================
// PASSED
//====================================================
println('=====================================')

println('SUBTOTAL         : ' + subtotal)

println('DISCOUNT         : ' + actualDiscount)

println('SHIPPING FEE     : ' + shippingFee)

println('INSURANCE FEE    : ' + insuranceFee)

println('EXPECTED TOTAL   : ' + expectedTotal)

println('ACTUAL TOTAL     : ' + actualTotal)

println('TEST CASE PASSED')

println('=====================================')

