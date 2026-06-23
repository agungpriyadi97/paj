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

//====================================================
// DYNAMIC DROPDOWN OPTION
//====================================================
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
// HANDLE TAB / SAME TAB
//====================================================
WebUI.delay(15)

try {
    WebUI.switchToWindowIndex(1)

    println('SWITCH TO WINDOW 1')
}
catch (Exception e) {
    println('SUCCESS PAGE OPENED IN SAME TAB')
} 

//====================================================
// WAIT SUCCESS PAGE
//====================================================
WebUI.waitForElementVisible(findTestObject('WEB/Checkout/Checkout Success/lbl_OrderSuccess'), 60)

WebUI.verifyElementVisible(findTestObject('WEB/Checkout/Checkout Success/lbl_OrderSuccess'))

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
WebUI.scrollToElement(findTestObject('WEB/OrderDetail/Page View Detail/btn_Cancel'), 10)

WebUI.scrollToElement(findTestObject('WEB/OrderDetail/Page View Detail/btn_Cancel'), 10)

WebUI.waitForElementClickable(findTestObject('WEB/OrderDetail/Page View Detail/btn_Cancel'), 10)

WebUI.click(findTestObject('WEB/OrderDetail/Page View Detail/btn_Cancel'))

WebUI.delay(2)

WebUI.verifyElementText(findTestObject('WEB/OrderDetail/CancelReason/lbl_Cancel'), 'Cancel')

// Reason Label
WebUI.verifyElementText(findTestObject('WEB/OrderDetail/CancelReason/lbl_Reason'), 'Reason')

// Select Placeholder
WebUI.verifyElementAttributeValue(findTestObject('WEB/OrderDetail/CancelReason/txt_Select'), 'placeholder', 'Select', 5)

// Open Dropdown
WebUI.click(findTestObject('WEB/OrderDetail/CancelReason/ddl_Reason'))

// Verify Cancel Reason Option
WebUI.click(findTestObject('WEB/OrderDetail/CancelReason/opt_WrongColorVariant'), FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('WEB/OrderDetail/CancelReason/btn_Confirm'))

WebUI.click(findTestObject('WEB/OrderDetail/CancelReason/btn_OK'))

WebUI.waitForElementVisible(findTestObject('WEB/OrderDetail/CancelReason/msg_success'), 2)

