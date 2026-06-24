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
// VERIFY SUCCESS PAGE
//====================================================
WebUI.waitForElementVisible(findTestObject('WEB/Checkout/Checkout Success/lbl_OrderSuccess'), 30)

WebUI.verifyElementVisible(findTestObject('WEB/Checkout/Checkout Success/lbl_OrderSuccess'))

WebUI.verifyTextPresent('Your order has been placed successfully.', false)

println('SUCCESS PAGE DISPLAYED')

//====================================================
// OPEN MAILINATOR
//====================================================
String email = GlobalVariable.Email

String inboxName = email.replace('@mailinator.com', '')

WebUI.executeJavaScript('window.open(\'https://www.mailinator.com/\')', null)

WebUI.delay(3)

WebUI.switchToWindowIndex(2)

println('MAILINATOR OPENED')

//====================================================
// SEARCH INBOX
//====================================================
WebUI.setText(findTestObject('WEB/Mailinator/searchInput'), inboxName)

WebUI.click(findTestObject('WEB/Mailinator/goButton'))

WebUI.delay(5)

//====================================================
// WAIT ORDER CONFIRMATION EMAIL
//====================================================
boolean emailReceived = false

for (int i = 1; i <= 12; i++) {
    WebUI.refresh()

    WebUI.delay(5)

    try {
        WebUI.verifyElementVisible(findTestObject('WEB/Mailinator/latestOrderConfirmationEmail'))

        emailReceived = true

        break
    }
    catch (Exception e) {
        println('WAITING ORDER CONFIRMATION EMAIL...')
    } 
}

assert emailReceived

println('ORDER CONFIRMATION EMAIL RECEIVED')

//====================================================
// OPEN EMAIL
//====================================================
WebUI.click(findTestObject('WEB/Mailinator/latestOrderConfirmationEmail'))

WebUI.delay(5)

println('EMAIL OPENED')

//====================================================
// VERIFY EMAIL SUBJECT
//====================================================
WebUI.verifyTextPresent('Your Nataroe order confirmation', false)

println('EMAIL SUBJECT VERIFIED')

//====================================================
// TEST PASSED
//====================================================
println('CUSTOMER RECEIVED ORDER CONFIRMATION EMAIL')

