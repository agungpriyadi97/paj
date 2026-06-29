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
// CHECKOUT ORDER
//====================================================
WebUI.callTestCase(findTestCase('WEB/Checkout/Positive/Positive - Ensure user can checkout order successfully'), [:], FailureHandling.STOP_ON_FAILURE)

println('CHECKOUT SUCCESS')

//====================================================
// OPEN ORDER DETAIL
//====================================================
WebUI.waitForElementVisible(findTestObject('WEB/Checkout/Checkout Success/btn_ViewDetails'), 30)

WebUI.enhancedClick(findTestObject('WEB/Checkout/Checkout Success/btn_ViewDetails'))

WebUI.waitForPageLoad(30)

println('ORDER DETAIL OPENED')

//====================================================
// CONTINUE TO PAY
//====================================================
WebUI.waitForElementClickable(findTestObject('WEB/OrderDetail/Page View Detail/btn_ContinueToPay'), 30)

WebUI.scrollToElement(findTestObject('WEB/OrderDetail/Page View Detail/btn_ContinueToPay'), 10)

try {
    WebUI.enhancedClick(findTestObject('WEB/OrderDetail/Page View Detail/btn_ContinueToPay'))
}
catch (Exception e) {
    WebUI.executeJavaScript('arguments[0].click();', java.util.Arrays.asList(WebUI.findWebElement(findTestObject('WEB/OrderDetail/Page View Detail/btn_ContinueToPay'))))
} 

println('PAYMENT PAGE OPENED')

WebUI.waitForPageLoad(30)

//====================================================
// GET VA NUMBER
//====================================================
TestObject lblVA = new TestObject()

lblVA.addProperty('xpath', com.kms.katalon.core.testobject.ConditionType.EQUALS, '//span[normalize-space()=\'Virtual Bank Account\']/following::span[1]')

WebUI.waitForElementVisible(lblVA, 20)

String virtualAccount = WebUI.getText(lblVA).replaceAll('\\s+', '').trim()

assert virtualAccount != ''

println('VA : ' + virtualAccount)

//====================================================
// OPEN MIDTRANS SIMULATOR
//====================================================
WebUI.executeJavaScript('window.open(\'https://simulator.sandbox.midtrans.com/bca/va/index\');', null)

WebUI.delay(5)

// pindah ke tab terakhir
WebUI.switchToWindowIndex(2)

println('MIDTRANS OPENED')

//====================================================
// INPUT VA
//====================================================
WebUI.waitForElementVisible(findTestObject('WEB/Midtrans/txt_VirtualAccount'), 20)

WebUI.setText(findTestObject('WEB/Midtrans/txt_VirtualAccount'), virtualAccount)

println('VA INPUTTED')

//====================================================
// INQUIRE
//====================================================
WebUI.click(findTestObject('WEB/Midtrans/btn_Inquire'))

WebUI.waitForElementVisible(findTestObject('WEB/Midtrans/btn_Pay'), 20)

println('INQUIRE SUCCESS')

//====================================================
// VERIFY PAYMENT DETAIL
//====================================================
WebUI.verifyElementVisible(findTestObject('WEB/Midtrans/lbl_VANumber'))

WebUI.verifyElementVisible(findTestObject('WEB/Midtrans/lbl_OnBehalfOf'))

WebUI.verifyElementVisible(findTestObject('WEB/Midtrans/lbl_FreeText'))

WebUI.verifyElementVisible(findTestObject('WEB/Midtrans/txt_AmountToPay'))

println('PAYMENT DETAIL VERIFIED')

//====================================================
// PAY
//====================================================
WebUI.click(findTestObject('WEB/Midtrans/btn_Pay'))

println('PAY BUTTON CLICKED')

//====================================================
// VERIFY PAYMENT SUCCESS
//====================================================
WebUI.waitForElementVisible(findTestObject('WEB/Midtrans/lbl_PaymentSuccess'), 30)

WebUI.verifyTextPresent('successful', false)

println('PAYMENT SUCCESS')

//====================================================
// BACK TO PAYMENT PAGE
//====================================================
WebUI.switchToWindowIndex(1)

WebUI.waitForPageLoad(30)

println('BACK TO PAYMENT PAGE')

//====================================================
// DYNAMIC ORDER STATUS
//====================================================
TestObject lblOrderStatus = new TestObject()

lblOrderStatus.addProperty('xpath', com.kms.katalon.core.testobject.ConditionType.EQUALS, '//span[normalize-space()=\'Order Status\']/following::span[1]')

//====================================================
// WAIT UNTIL STATUS = PAID
//====================================================
boolean paymentSuccess = false

String currentStatus = ''

for (int i = 1; i <= 20; i++) {
    WebUI.refresh()

    WebUI.waitForPageLoad(10)

    WebUI.waitForElementVisible(lblOrderStatus, 10)

    currentStatus = WebUI.getText(lblOrderStatus).trim()

    println((('CHECK STATUS (' + i) + ') : ') + currentStatus)

    if (currentStatus.equalsIgnoreCase('Paid')) {
        paymentSuccess = true

        break
    }
    
    WebUI.delay(3)
}

assert paymentSuccess

println('ORDER STATUS : ' + currentStatus)

//====================================================
// VERIFY OTHER PAYMENT INFO
//====================================================
WebUI.verifyElementVisible(lblOrderStatus)

WebUI.verifyElementVisible(findTestObject('WEB/Checkout/Checkout Success/lbl_OrderNumber'))

WebUI.verifyElementVisible(findTestObject('WEB/Checkout/Checkout Success/lbl_PaymentMethod'))

WebUI.verifyElementVisible(findTestObject('WEB/Checkout/Checkout Success/lbl_TotalPrice'))

WebUI.verifyElementVisible(findTestObject('WEB/Checkout/Checkout Success/lbl_VirtualAccount'))

println('PAYMENT INFORMATION VERIFIED')

//====================================================
// TEST PASSED
//====================================================
println('========================================')

println('USER CAN COMPLETE PAYMENT SUCCESSFULLY')

println('========================================')

