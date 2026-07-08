import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import java.util.Arrays as Arrays

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

WebUI.delay(10)

println('PRODUCT ADDED TO CART')

WebUI.delay(3)

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
WebUI.waitForElementClickable(findTestObject('WEB/Cart/btn_Checkout'), 30)

WebUI.scrollToElement(findTestObject('WEB/Cart/btn_Checkout'), 10)

WebUI.enhancedClick(findTestObject('WEB/Cart/btn_Checkout'))

WebUI.waitForPageLoad(20)

println('CHECKOUT PAGE OPENED')

//====================================================
// WAIT PAYMENT SECTION
//====================================================
boolean paymentReady = false

for (int i = 1; i <= 20; i++) {
    String bodyText = WebUI.executeJavaScript('return document.body.innerText;', null)

    println('WAIT PAYMENT ATTEMPT : ' + i)

    if ((bodyText.contains('Midtrans') || bodyText.contains('Virtual Account')) || bodyText.contains('Pay With')) {
        paymentReady = true

        break
    }
    
    WebUI.delay(3)
}

assert paymentReady

println('PAYMENT SECTION READY')

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
TestObject vaObj = new TestObject('vaObj')

vaObj.addProperty('xpath', ConditionType.EQUALS, '//*[contains(text(),\'Virtual Account\')]')

WebUI.waitForElementPresent(vaObj, 30)

WebUI.enhancedClick(vaObj)

println('VIRTUAL ACCOUNT SELECTED')

//====================================================
// BCA
//====================================================
TestObject bcaObj = new TestObject('bcaObj')

bcaObj.addProperty('xpath', ConditionType.EQUALS, '//*[contains(text(),\'BCA\')]')

WebUI.waitForElementPresent(bcaObj, 30)

WebUI.enhancedClick(bcaObj)

println('BCA SELECTED')

//====================================================
// ACCEPT TERMS
//====================================================
WebUI.waitForElementVisible(findTestObject('WEB/Checkout/OrderSummary/checkbox'), 20)

WebUI.enhancedClick(findTestObject('WEB/Checkout/OrderSummary/checkbox'))

println('TERMS ACCEPTED')

//====================================================
// FINAL CHECKOUT
//====================================================
WebUI.scrollToElement(findTestObject('WEB/Cart/btn_Checkout'), 10)

WebUI.enhancedClick(findTestObject('WEB/Cart/btn_Checkout'))

println('CHECKOUT BUTTON CLICKED')

//====================================================
// HANDLE WINDOW
//====================================================
WebUI.delay(15)

try {
    WebUI.switchToWindowIndex(1)

    println('SWITCH TO TAB 1')
}
catch (Exception e) {
    println('SUCCESS PAGE OPENED IN SAME TAB')
} 

//====================================================
// SUCCESS PAGE
//====================================================
WebUI.waitForElementVisible(findTestObject('WEB/Checkout/Checkout Success/lbl_OrderSuccess'), 60)

WebUI.verifyElementVisible(findTestObject('WEB/Checkout/Checkout Success/lbl_OrderSuccess'))

println('SUCCESS PAGE DISPLAYED')

//====================================================
// VERIFY ORDER NUMBER
//====================================================
String orderNumber = WebUI.getText(findTestObject('WEB/Checkout/Checkout Success/lbl_OrderNumber')).trim()

println('RAW ORDER NUMBER : ' + orderNumber)

String cleanOrderNumber = orderNumber.replaceAll('[^0-9]', '')

assert cleanOrderNumber.length() > 0

println('ORDER NUMBER : ' + cleanOrderNumber)

println('ORDER NUMBER GENERATED SUCCESSFULLY')

//====================================================
// TEST PASSED
//====================================================
println('ORDER NUMBER VERIFIED SUCCESSFULLY')

