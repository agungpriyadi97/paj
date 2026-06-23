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

WebUI.callTestCase(findTestCase('WEB/Authentication/Login/Positive/Positive - Ensure user can login with valid account and password'), 
    [:], FailureHandling.STOP_ON_FAILURE)

println('LOGIN SUCCESS')

WebUI.delay(5)

//====================================================
// OPEN PDP DIRECTLY
//====================================================
WebUI.navigateToUrl('https://d-speedshop-pastiadajalan.gtechdigital.id/pdp/SP250526661250')

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

WebUI.delay(10)

println('PRODUCT ADDED TO CART')

WebUI.delay(3)

WebUI.waitForElementClickable(findTestObject('WEB/Home/Header/Icon Menu/icon_cart'), 20)

WebUI.enhancedClick(findTestObject('WEB/Home/Header/Icon Menu/icon_cart'))

WebUI.waitForPageLoad(30)

println('SHOPPING CART OPENED')

//====================================================
// INCREASE QTY TO 2 PCS
//====================================================
String currentQty = WebUI.getAttribute(findTestObject('WEB/Cart/txt_QuantityValue'), 'aria-valuenow')

println('CURRENT QTY : ' + currentQty)

Integer qty = currentQty.toInteger()

if (qty < 2) {
    WebUI.click(findTestObject('WEB/Cart/btn_QtyPlus'))

    WebUI.delay(2)

    currentQty = WebUI.getAttribute(findTestObject('WEB/Cart/txt_QuantityValue'), 'aria-valuenow')

    qty = currentQty.toInteger()
}

assert qty >= 2

println('CURRENT QTY : ' + qty)

println('MINIMUM QTY REQUIREMENT MET')

println('QTY UPDATED TO : ' + currentQty)

//====================================================
// CHECKOUT
//====================================================
WebUI.scrollToElement(findTestObject('WEB/Cart/btn_Checkout'), 10)

WebUI.click(findTestObject('WEB/Cart/btn_Checkout'))

WebUI.waitForPageLoad(10)

//====================================================
// GET SUBTOTAL BEFORE PROMO
//====================================================
Long subtotal = WebUI.getText(findTestObject('WEB/Checkout/OrderSummary/lbl_Subtotal')).replace('Rp', '').replace('.', '').replace(
    ',', '').trim().toLong()

println('SUBTOTAL : ' + subtotal)

//====================================================
// APPLY PROMO CODE
//====================================================
WebUI.setText(findTestObject('WEB/Checkout/Promotion/txt_PromoCode'), 'agung')

WebUI.click(findTestObject('WEB/Checkout/Promotion/btn_ApplyPromo'))

WebUI.delay(5)

println('PROMO CODE APPLIED')

//====================================================
// GET ACTUAL DISCOUNT
//====================================================
String discountText = WebUI.getText(findTestObject('WEB/Checkout/OrderSummary/lbl_CouponDiscount'))

println('DISCOUNT TEXT : ' + discountText)

Long actualDiscount = discountText.replace('-Rp', '').replace('Rp', '').replace('.', '').replace(',', '').trim().toLong()

println('ACTUAL DISCOUNT : ' + actualDiscount)

//====================================================
// CALCULATE EXPECTED DISCOUNT (10%)
//====================================================
Long expectedDiscount = (subtotal * 10) / 100

println('EXPECTED DISCOUNT : ' + expectedDiscount)

//====================================================
// VERIFY DISCOUNT AMOUNT
//====================================================
assert actualDiscount == expectedDiscount

println('DISCOUNT CALCULATION VERIFIED')

//====================================================
// VERIFY TOTAL FORMULA
//====================================================
Long shippingFee = WebUI.getText(findTestObject('WEB/Checkout/OrderSummary/lbl_ShippingFee')).replace('Rp', '').replace(
    '.', '').replace(',', '').trim().toLong()

Long insuranceFee = WebUI.getText(findTestObject('WEB/Checkout/OrderSummary/lbl_InsuranceFee')).replace('Rp', '').replace(
    '.', '').replace(',', '').trim().toLong()

Long actualTotal = WebUI.getText(findTestObject('WEB/Checkout/OrderSummary/lbl_Total')).replace('Rp', '').replace('.', '').replace(
    ',', '').trim().toLong()

Long expectedTotal = ((subtotal - actualDiscount) + shippingFee) + insuranceFee

println('EXPECTED TOTAL : ' + expectedTotal)

println('ACTUAL TOTAL   : ' + actualTotal)

assert actualTotal == expectedTotal

println('TOTAL CALCULATION VERIFIED')

//====================================================
// TEST PASSED
//====================================================
println('PROMO CODE APPLIED SUCCESSFULLY')

println('DISCOUNT AMOUNT CALCULATED CORRECTLY')

println('TEST CASE PASSED')

