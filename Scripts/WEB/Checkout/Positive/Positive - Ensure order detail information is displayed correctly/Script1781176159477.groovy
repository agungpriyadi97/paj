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
// Tunggu section payment muncul
WebUI.waitForElementVisible(findTestObject('WEB/Checkout/Payment Method/rdo_Midtrans'), 20)

WebUI.scrollToElement(findTestObject('WEB/Checkout/Payment Method/rdo_Midtrans'), 10)

// Pilih Midtrans jika belum terpilih
WebUI.click(findTestObject('WEB/Checkout/Payment Method/rdo_Midtrans'))

println('MIDTRANS SELECTED')

WebUI.click(findTestObject('WEB/Checkout/Payment Method/lbl_VirtualAccount'))

// Pilih BCA Virtual Account
WebUI.waitForElementVisible(findTestObject('WEB/Checkout/Payment Method/Virtual Account/rdo_BCA'), 10)

WebUI.click(findTestObject('WEB/Checkout/Payment Method/Virtual Account/rdo_BCA'))

println('BCA VIRTUAL ACCOUNT SELECTED')

// Verify BCA Selected
WebUI.verifyElementPresent(findTestObject('WEB/Checkout/Payment Method/Virtual Account/rdo_BCA'), 10)

println('PAYMENT METHOD VERIFIED')

WebUI.verifyElementVisible(findTestObject('WEB/Checkout/OrderSummary/checkbox'))

WebUI.click(findTestObject('WEB/Checkout/OrderSummary/checkbox'))

//====================================================
// FINAL CHECKOUT
//====================================================
WebUI.verifyElementVisible(findTestObject('WEB/Cart/btn_Checkout'), FailureHandling.STOP_ON_FAILURE)

WebUI.scrollToElement(findTestObject('WEB/Cart/btn_Checkout'), 10)

WebUI.click(findTestObject('WEB/Cart/btn_Checkout'))

WebUI.delay(10)

// pindah ke tab/window terakhir
WebUI.switchToWindowIndex(1)

WebUI.delay(3)

println('SWITCH TO SUCCESS PAGE')

//====================================================
// VERIFY CHECKOUT SUCCESS PAGE
//====================================================
WebUI.waitForElementVisible(findTestObject('WEB/Checkout/Checkout Success/lbl_OrderSuccess'), 30)

WebUI.verifyElementVisible(findTestObject('WEB/Checkout/Checkout Success/lbl_OrderSuccess'))

WebUI.verifyTextPresent('Your order has been placed successfully.', false)

println('SUCCESS PAGE DISPLAYED')

//====================================================
// VERIFY ORDER STATUS
//====================================================
String orderStatus = WebUI.getText(findTestObject('WEB/Checkout/Checkout Success/lbl_OrderStatus'))

println('ORDER STATUS : ' + orderStatus)

assert orderStatus != ''

//====================================================
// VERIFY ORDER NUMBER
//====================================================
String orderNumber = WebUI.getText(findTestObject('WEB/Checkout/Checkout Success/lbl_OrderNumber'))

println('ORDER NUMBER : ' + orderNumber)

assert orderNumber != ''

assert orderNumber.matches('\\d+')

//====================================================
// VERIFY PAYMENT METHOD
//====================================================
String paymentMethod = WebUI.getText(findTestObject('WEB/Checkout/Checkout Success/lbl_PaymentMethod'))

println('PAYMENT METHOD : ' + paymentMethod)

assert paymentMethod != ''

//====================================================
// VERIFY TOTAL PRICE
//====================================================
String totalPrice = WebUI.getText(findTestObject('WEB/Checkout/Checkout Success/lbl_TotalPrice'))

println('TOTAL PRICE : ' + totalPrice)

assert totalPrice.contains('Rp')

//====================================================
// VERIFY VIRTUAL ACCOUNT
//====================================================
String virtualAccount = WebUI.getText(findTestObject('WEB/Checkout/Checkout Success/lbl_VirtualAccount'))

println('VIRTUAL ACCOUNT : ' + virtualAccount)

assert virtualAccount != ''

//====================================================
// VERIFY BUTTON VIEW DETAILS
//====================================================
WebUI.verifyElementVisible(findTestObject('WEB/Checkout/Checkout Success/btn_ViewDetails'))

println('VIEW DETAILS BUTTON DISPLAYED')

//====================================================
// FINAL RESULT
//====================================================
println('CHECKOUT SUCCESS VERIFIED')

println('ORDER CREATED SUCCESSFULLY')

println('TEST CASE PASSED')

//====================================================
// OPEN ORDER DETAIL
//====================================================
WebUI.click(findTestObject('WEB/Checkout/Checkout Success/btn_ViewDetails'))

WebUI.waitForPageLoad(10)

WebUI.verifyElementVisible(findTestObject('WEB/OrderDetail/Page View Detail/lbl_UnpaidStatus'))

//====================================================
// VERIFY ORDER STATUS
//====================================================
String detailOrderStatus = WebUI.getText(findTestObject('WEB/OrderDetail/Page View Detail/lbl_UnpaidStatus'))

assert detailOrderStatus.trim() != ''

println('ORDER STATUS : ' + detailOrderStatus)

//====================================================
// VERIFY ORDER INFO
//====================================================
String detailOrderNumber = WebUI.getText(findTestObject('WEB/OrderDetail/Page View Detail/lbl_OrderNumber'))

String detailCreatedTime = WebUI.getText(findTestObject('WEB/OrderDetail/Page View Detail/lbl_CreatedTime'))

String detailPaymentMethod = WebUI.getText(findTestObject('WEB/OrderDetail/Page View Detail/lbl_PaymentMethod'))

assert detailOrderNumber.trim() != ''

assert detailCreatedTime.trim() != ''

assert detailPaymentMethod.trim() != ''

println('ORDER NUMBER  : ' + detailOrderNumber)

println('CREATED TIME  : ' + detailCreatedTime)

println('PAYMENT METHOD: ' + detailPaymentMethod)

println('ORDER INFO DISPLAYED')

//====================================================
// VERIFY DELIVERY INFO
//====================================================
String detailDeliveryAddress = WebUI.getText(findTestObject('WEB/OrderDetail/Page View Detail/lbl_DeliveryAddress'))

String detailBillingAddress = WebUI.getText(findTestObject('WEB/OrderDetail/Page View Detail/lbl_BillingAddress'))

assert detailDeliveryAddress.trim() != ''

assert detailBillingAddress.trim() != ''

println('DELIVERY ADDRESS : ' + detailDeliveryAddress)

println('BILLING ADDRESS  : ' + detailBillingAddress)

println('DELIVERY INFO DISPLAYED')

//====================================================
// VERIFY PRODUCT INFO
//====================================================
String detailProductName = WebUI.getText(findTestObject('WEB/OrderDetail/Page View Detail/lbl_ProductName'))

String detailProductSKU = WebUI.getText(findTestObject('WEB/OrderDetail/Page View Detail/lbl_ProductSKU'))

String detailProductPrice = WebUI.getText(findTestObject('WEB/OrderDetail/Page View Detail/lbl_ProductPrice'))

assert detailProductName.trim() != ''

assert detailProductSKU.trim() != ''

assert detailProductPrice.contains('Rp')

println('PRODUCT NAME  : ' + detailProductName)

println('PRODUCT SKU   : ' + detailProductSKU)

println('PRODUCT PRICE : ' + detailProductPrice)

println('PRODUCT INFO DISPLAYED')

//====================================================
// VERIFY ORDER SUMMARY
//====================================================
String detailSubtotal = WebUI.getText(findTestObject('WEB/OrderDetail/Page View Detail/lbl_Subtotal'))

String detailShippingFee = WebUI.getText(findTestObject('WEB/OrderDetail/Page View Detail/lbl_ShippingFee'))

String detailInsuranceFee = WebUI.getText(findTestObject('WEB/OrderDetail/Page View Detail/lbl_InsuranceFee'))

String detailTotalPrice = WebUI.getText(findTestObject('WEB/OrderDetail/Page View Detail/lbl_TotalPrice'))

assert detailSubtotal.contains('Rp')

assert detailShippingFee.contains('Rp')

assert detailInsuranceFee.contains('Rp')

assert detailTotalPrice.contains('Rp')

println('SUBTOTAL      : ' + detailSubtotal)

println('SHIPPING FEE  : ' + detailShippingFee)

println('INSURANCE FEE : ' + detailInsuranceFee)

println('TOTAL PRICE   : ' + detailTotalPrice)

println('ORDER SUMMARY DISPLAYED')

//====================================================
// TEST PASSED
//====================================================
println('ORDER DETAIL INFORMATION DISPLAYED CORRECTLY')

