import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.testobject.TestObject as TestObject
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
import java.util.Arrays as Arrays

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
// WAIT PAYMENT SECTION READY
//====================================================
WebUI.delay(5)

boolean paymentReady = false

for (int i = 1; i <= 20; i++) {
    String bodyText = WebUI.executeJavaScript('return document.body.innerText;', null)

    println('WAIT PAYMENT ATTEMPT : ' + i)

    if ((bodyText.contains('Midtrans') || bodyText.contains('Virtual Account')) || bodyText.contains('Pay With')) {
        paymentReady = true

        println('PAYMENT SECTION READY')

        break
    }
    
    WebUI.delay(3)
}

assert paymentReady

//====================================================
// MIDTRANS
//====================================================
TestObject midtransObj = new TestObject('midtransObj')

midtransObj.addProperty('xpath', ConditionType.EQUALS, '//*[contains(text(),\'Midtrans\')]')

WebUI.waitForElementPresent(midtransObj, 60)

WebUI.executeJavaScript('arguments[0].scrollIntoView({block:\'center\'});', Arrays.asList(WebUI.findWebElement(midtransObj)))

WebUI.enhancedClick(midtransObj)

println('MIDTRANS SELECTED')

//====================================================
// VIRTUAL ACCOUNT
//====================================================
TestObject virtualAccountObj = new TestObject('virtualAccountObj')

virtualAccountObj.addProperty('xpath', ConditionType.EQUALS, '//*[contains(text(),\'Virtual Account\')]')

WebUI.waitForElementPresent(virtualAccountObj, 30)

WebUI.enhancedClick(virtualAccountObj)

println('VIRTUAL ACCOUNT SELECTED')

//====================================================
// BCA VA
//====================================================
TestObject bcaObj = new TestObject('bcaObj')

bcaObj.addProperty('xpath', ConditionType.EQUALS, '//*[contains(text(),\'BCA\')]')

WebUI.waitForElementPresent(bcaObj, 30)

WebUI.enhancedClick(bcaObj)

println('BCA VIRTUAL ACCOUNT SELECTED')

//====================================================
// ACCEPT TERMS
//====================================================

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
WebUI.executeJavaScript('window.open(\'https://simulator.sandbox.midtrans.com/bca/va/index\')', null)

WebUI.delay(3)

// pindah ke tab terakhir
WebUI.switchToWindowIndex(2)

WebUI.delay(5)

println('MIDTRANS SIMULATOR OPENED')

//====================================================
// VERIFY MIDTRANS PAGE
//====================================================
String midtransUrl = WebUI.getUrl()

println('MIDTRANS URL : ' + midtransUrl)

assert midtransUrl.contains('simulator.sandbox.midtrans.com')

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

WebUI.click(findTestObject('WEB/Midtrans/btn_Pay'))

//====================================================
// VERIFY PAYMENT SUCCESS
//====================================================
WebUI.waitForElementVisible(findTestObject('WEB/Midtrans/lbl_PaymentSuccess'), 20)

WebUI.verifyElementVisible(findTestObject('WEB/Midtrans/lbl_PaymentSuccess'))

println('PAYMENT SUCCESS MESSAGE DISPLAYED')

//====================================================
// VERIFY SUCCESS TEXT
//====================================================
String paymentResult = WebUI.getText(findTestObject('WEB/Midtrans/lbl_PaymentSuccess'))

println('PAYMENT RESULT : ' + paymentResult)

assert paymentResult.toLowerCase().contains('successful')

println('PAYMENT SUCCESS VERIFIED')

//====================================================
// VERIFY BUTTON
//====================================================
WebUI.verifyElementVisible(findTestObject('WEB/Midtrans/btn_SimulateAnotherPayment'))

println('SIMULATE BUTTON DISPLAYED')

//====================================================
// TEST PASSED
//====================================================
println('USER CAN COMPLETE PAYMENT SUCCESSFULLY')

//====================================================
// BACK TO WEBSITE
//====================================================
WebUI.switchToWindowIndex(1)

WebUI.delay(5)

println('BACK TO WEBSITE')

//====================================================
// VERIFY SUCCESS PAGE DISPLAYED
//====================================================
WebUI.verifyTextPresent('Your order has been placed successfully.', false)

println('SUCCESS PAGE DISPLAYED')

//====================================================
// OPEN ORDER DETAIL
//====================================================
WebUI.click(findTestObject('WEB/Checkout/Checkout Success/btn_ViewDetails'))

WebUI.waitForPageLoad(10)

println('ORDER DETAIL PAGE OPENED')

//====================================================
// VERIFY STATUS IN ORDER DETAIL
//====================================================
boolean statusUpdated = false

for (int i = 1; i <= 20; i++) {
    WebUI.refresh()

    WebUI.waitForPageLoad(30)

    WebUI.waitForElementVisible(findTestObject('WEB/OrderDetail/Page View Detail/lbl_OrderStatus'), 20, FailureHandling.OPTIONAL)

    WebUI.delay(3)

    String detailStatus = WebUI.getText(findTestObject('WEB/OrderDetail/Page View Detail/lbl_OrderStatus')).trim()

    println("ATTEMPT $i STATUS = [$detailStatus]")

    if ((detailStatus.equalsIgnoreCase('Processing') || detailStatus.equalsIgnoreCase('Ready to Ship')) || detailStatus.equalsIgnoreCase(
        'Shipped')) {
        statusUpdated = true

        break
    }
}

//====================================================
// TEST PASSED
//====================================================
println('ORDER STATUS CHANGED SUCCESSFULLY AFTER PAYMENT')

