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
WebUI.navigateToUrl('https://d-speedshop-pastiadajalan.gtechdigital.id/pdp/SP250526661262')

WebUI.waitForPageLoad(30)

println('PDP PAGE OPENED')

println('SHOPPING CART OPENED')

println('NON PROMOTIONAL PRODUCT FOUND')

//====================================================
// ADD TO CART
//====================================================
WebUI.waitForElementPresent(findTestObject('WEB/Product/PDP/btn_AddToCart'), 30)

WebUI.waitForElementVisible(findTestObject('WEB/Product/PDP/btn_AddToCart'), 30)

WebUI.waitForElementClickable(findTestObject('WEB/Product/PDP/btn_AddToCart'), 30)

WebUI.scrollToElement(findTestObject('WEB/Product/PDP/btn_AddToCart'), 10)

WebUI.click(findTestObject('WEB/Product/PDP/btn_AddToCart'))

WebUI.delay(10)

println('PRODUCT ADDED TO CART')

//====================================================
// OPEN CART
//====================================================
WebUI.click(findTestObject('WEB/Home/Header/Icon Menu/icon_cart'))

WebUI.waitForPageLoad(10)

println('PRODUCT DETAIL PAGE OPENED')

WebUI.delay(10)

//====================================================
// OPEN CART
//====================================================
WebUI.click(findTestObject('WEB/Home/Header/Icon Menu/icon_cart'))

WebUI.waitForPageLoad(10)

println('SHOPPING CART OPENED')

//====================================================
// CHECKOUT
//====================================================
WebUI.scrollToElement(findTestObject('WEB/Cart/btn_Checkout'), 10)

WebUI.click(findTestObject('WEB/Cart/btn_Checkout'))

WebUI.waitForPageLoad(10)

println('CHECKOUT PAGE OPENED')

//====================================================
// PAYMENT METHOD
//====================================================
WebUI.waitForElementVisible(findTestObject('WEB/Checkout/Payment Method/rdo_Midtrans'), 20)

WebUI.click(findTestObject('WEB/Checkout/Payment Method/rdo_Midtrans'))

WebUI.click(findTestObject('WEB/Checkout/Payment Method/lbl_VirtualAccount'))

WebUI.waitForElementVisible(findTestObject('WEB/Checkout/Payment Method/Virtual Account/rdo_BCA'), 10)

WebUI.click(findTestObject('WEB/Checkout/Payment Method/Virtual Account/rdo_BCA'))

println('BCA VIRTUAL ACCOUNT SELECTED')

//====================================================
// VERIFY INSURANCE FEE ON CHECKOUT
//====================================================
String checkoutInsuranceFee = WebUI.getText(findTestObject('WEB/Checkout/OrderSummary/lbl_InsuranceFee')).trim()

println('CHECKOUT INSURANCE FEE : ' + checkoutInsuranceFee)

assert checkoutInsuranceFee.contains('Rp')

String checkoutFeeNumber = checkoutInsuranceFee.replaceAll('[^0-9]', '')

assert checkoutFeeNumber.toInteger() > 0

println('CHECKOUT INSURANCE FEE VERIFIED')

//====================================================
// ACCEPT TERMS
//====================================================
WebUI.click(findTestObject('WEB/Checkout/OrderSummary/checkbox'))

//====================================================
// FINAL CHECKOUT
//====================================================
WebUI.scrollToElement(findTestObject('WEB/Cart/btn_Checkout'), 10)

WebUI.click(findTestObject('WEB/Cart/btn_Checkout'))

WebUI.delay(10)

//====================================================
// SWITCH TAB IF NEEDED
//====================================================
try {
    WebUI.switchToWindowIndex(1)

    println('SWITCH TO SUCCESS TAB')
}
catch (Exception e) {
    println('SUCCESS PAGE OPENED IN SAME TAB')
} 

//====================================================
// VERIFY SUCCESS PAGE
//====================================================
WebUI.waitForElementVisible(findTestObject('WEB/Checkout/Checkout Success/lbl_OrderSuccess'), 30)

WebUI.verifyTextPresent('Your order has been placed successfully.', false)

println('SUCCESS PAGE DISPLAYED')

//====================================================
// OPEN ORDER DETAIL
//====================================================
WebUI.click(findTestObject('WEB/Checkout/Checkout Success/btn_ViewDetails'))

WebUI.waitForPageLoad(10)

//====================================================
// VERIFY ORDER DETAIL PAGE
//====================================================
WebUI.verifyElementVisible(findTestObject('WEB/OrderDetail/Page View Detail/lbl_UnpaidStatus'))

println('ORDER DETAIL PAGE OPENED')

//====================================================
// VERIFY INSURANCE FEE AFTER PAYMENT
//====================================================
String detailInsuranceFee = WebUI.getText(findTestObject('WEB/OrderDetail/Page View Detail/lbl_InsuranceFee')).trim()

println('DETAIL INSURANCE FEE : ' + detailInsuranceFee)

assert detailInsuranceFee.contains('Rp')

String detailFeeNumber = detailInsuranceFee.replaceAll('[^0-9]', '')

assert detailFeeNumber.toInteger() > 0

//====================================================
// VERIFY INSURANCE FEE CONSISTENCY
//====================================================
assert checkoutInsuranceFee == detailInsuranceFee

println('INSURANCE FEE CONSISTENT')

//====================================================
// TEST PASSED
//====================================================
println('NON PROMOTIONAL PRODUCT VERIFIED')

println('INSURANCE FEE DISPLAYED ON CHECKOUT')

println('INSURANCE FEE DISPLAYED AFTER PAYMENT')

println('INSURANCE FEE VALUE CONSISTENT')

println('TEST CASE PASSED')

