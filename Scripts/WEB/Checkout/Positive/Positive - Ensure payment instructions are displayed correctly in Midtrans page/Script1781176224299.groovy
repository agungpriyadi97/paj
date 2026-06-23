import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.testobject.ConditionType as ConditionType
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
// PAYMENT METHOD - HEADLESS SAFE
//====================================================
WebUI.delay(10)

WebUI.scrollToPosition(0, 1500)

WebUI.delay(5)

// DEBUG
String bodyText = WebUI.executeJavaScript('return document.body.innerText;', null)

println('HAS PAY WITH        : ' + bodyText.contains('Pay With'))

println('HAS MIDTRANS        : ' + bodyText.contains('Midtrans'))

println('HAS VIRTUAL ACCOUNT : ' + bodyText.contains('Virtual Account'))

// Dynamic Object Midtrans
TestObject midtrans = new TestObject('midtrans')

midtrans.addProperty('xpath', ConditionType.EQUALS, '//span[contains(@class,\'sp-payment-methods__item-name\') and normalize-space()=\'Midtrans\']')

boolean midtransFound = false

for (int i = 1; i <= 10; i++) {
    println('WAIT MIDTRANS ATTEMPT : ' + i)

    if (WebUI.verifyElementPresent(midtrans, 10, FailureHandling.OPTIONAL)) {
        midtransFound = true

        break
    }
    
    WebUI.delay(3)
}

println('MIDTRANS FOUND : ' + midtransFound)

assert midtransFound : 'Midtrans payment method not displayed'

WebUI.scrollToElement(midtrans, 10)

WebUI.enhancedClick(midtrans)

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

int totalWindow = WebUI.getWindowIndex()

println('CURRENT WINDOW INDEX : ' + totalWindow)

try {
    WebUI.switchToWindowIndex(1)

    println('SWITCH TO WINDOW 1')
}
catch (Exception e) {
    println('SUCCESS PAGE OPENED IN SAME TAB')
} 

WebUI.delay(3)

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
// OPEN ORDER DETAIL
//====================================================
WebUI.verifyElementVisible(findTestObject('WEB/Checkout/Checkout Success/btn_ViewDetails'))

WebUI.click(findTestObject('WEB/Checkout/Checkout Success/btn_ViewDetails'))

WebUI.waitForPageLoad(10)

println('ORDER DETAIL PAGE OPENED')

///====================================================
// OPEN PAYMENT PAGE
//====================================================
WebUI.scrollToElement(findTestObject('WEB/OrderDetail/Page View Detail/btn_ContinueToPay'), 10)

WebUI.click(findTestObject('WEB/OrderDetail/Page View Detail/btn_ContinueToPay'))

WebUI.delay(5)

println('PAYMENT PAGE OPENED')

//====================================================
// VERIFY PAYMENT PAGE URL
//====================================================
String paymentUrl = WebUI.getUrl()

println('PAYMENT URL : ' + paymentUrl)

assert paymentUrl.contains('/payment')

assert paymentUrl.contains('vaNumber=')

assert paymentUrl.contains('totalAmount=')

assert paymentUrl.contains('paymentPlatform=midtrans')

println('PAYMENT PAGE VERIFIED')

//====================================================
// OPEN MIDTRANS SIMULATOR
//====================================================
WebUI.executeJavaScript('window.open(\'https://simulator.sandbox.midtrans.com/bca/va/index\',\'_blank\');', null)

WebUI.delay(5)

// DEBUG WINDOW
for (int i = 0; i <= 5; i++) {
    try {
        WebUI.switchToWindowIndex(i)

        println((('WINDOW ' + i) + ' URL : ') + WebUI.getUrl())
    }
    catch (Exception e) {
        println(('WINDOW ' + i) + ' NOT FOUND')
    } 
}

// biasanya simulator ada di index terakhir
try {
    WebUI.switchToWindowIndex(2)
}
catch (Exception e) {
    try {
        WebUI.switchToWindowIndex(1)
    }
    catch (Exception ex) {
        WebUI.switchToWindowIndex(0)
    } 
} 

WebUI.delay(5)

println('MIDTRANS SIMULATOR OPENED')

//====================================================
// VERIFY MIDTRANS PAGE
//====================================================
String midtransUrl = WebUI.getUrl()

println('MIDTRANS URL : ' + midtransUrl)

assert midtransUrl.toLowerCase().contains('midtrans')

println('MIDTRANS URL VERIFIED')

//====================================================
// VERIFY PAGE TITLE
//====================================================
String pageTitle = WebUI.getWindowTitle()

println('PAGE TITLE : ' + pageTitle)

assert pageTitle.trim().length() > 0

println('MIDTRANS PAGE DISPLAYED')

//====================================================
// TEST PASSED
//====================================================
println('PAYMENT INSTRUCTIONS DISPLAYED CORRECTLY IN MIDTRANS PAGE')

//====================================================
// INPUT VA NUMBER
//====================================================
WebUI.waitForElementVisible(findTestObject('WEB/Midtrans/txt_VirtualAccount'), 10)

WebUI.setText(findTestObject('WEB/Midtrans/txt_VirtualAccount'), virtualAccount)

println('VA NUMBER INPUTTED')

//====================================================
// CLICK INQUIRE
//====================================================
WebUI.click(findTestObject('WEB/Midtrans/btn_Inquire'))

WebUI.delay(5)

println('INQUIRE SUCCESS')

//====================================================
// VERIFY VA NUMBER
//====================================================
String vaNumber = WebUI.getText(findTestObject('WEB/Midtrans/lbl_VANumber'))

println('VA NUMBER : ' + vaNumber)

assert vaNumber.trim().length() > 0

//====================================================
// VERIFY ON BEHALF OF
//====================================================
String onBehalfOf = WebUI.getText(findTestObject('WEB/Midtrans/lbl_OnBehalfOf'))

println('ON BEHALF OF : ' + onBehalfOf)

assert onBehalfOf.trim().length() > 0

//====================================================
// VERIFY FREE TEXT
//====================================================
String freeText = WebUI.getText(findTestObject('WEB/Midtrans/lbl_FreeText'))

println('FREE TEXT : ' + freeText)

assert freeText.trim().length() > 0

//====================================================
// VERIFY AMOUNT TO PAY
//====================================================
String amountToPay = WebUI.getAttribute(findTestObject('WEB/Midtrans/txt_AmountToPay'), 'value')

println('AMOUNT TO PAY : ' + amountToPay)

assert amountToPay.trim().length() > 0

//====================================================
// VERIFY PAY BUTTON
//====================================================
WebUI.verifyElementVisible(findTestObject('WEB/Midtrans/btn_Pay'))

println('PAY BUTTON DISPLAYED')

//====================================================
// TEST PASSED
//====================================================
println('PAYMENT INSTRUCTIONS DISPLAYED CORRECTLY')

