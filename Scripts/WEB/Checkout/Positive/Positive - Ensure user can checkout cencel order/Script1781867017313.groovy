import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
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
import com.kms.katalon.core.testobject.ConditionType as ConditionType

//====================================================
// DYNAMIC DROPDOWN OPTION
//====================================================
//====================================================
// LOGIN
//====================================================
WebUI.callTestCase(findTestCase('WEB/Authentication/Login/Positive/Positive - Ensure user can login with valid account and password'), 
    [:], FailureHandling.STOP_ON_FAILURE)

//====================================================
// OPEN TUMBLERS CATEGORY
//====================================================
WebUI.mouseOver(findTestObject('WEB/Home/Header/Menu/menu_categories/menu_categories'))

WebUI.verifyElementPresent(findTestObject('WEB/Home/Header/Menu/menu_categories/lnk_Tumblers'), 10)

WebUI.verifyElementVisible(findTestObject('WEB/Home/Header/Menu/menu_categories/lnk_Tumblers'))

WebUI.verifyElementClickable(findTestObject('WEB/Home/Header/Menu/menu_categories/lnk_Tumblers'))

WebUI.click(findTestObject('WEB/Home/Header/Menu/menu_categories/lnk_Tumblers'))

WebUI.waitForPageLoad(10)

println('TUMBLERS PAGE OPENED')

//====================================================
// OPEN FIRST PRODUCT
//====================================================
WebUI.waitForElementClickable(findTestObject('WEB/Product/PLP/Product/card_FirstProduct'), 10)

WebUI.click(findTestObject('WEB/Product/PLP/Product/card_FirstProduct'))

WebUI.waitForPageLoad(10)

println('PRODUCT DETAIL PAGE OPENED')

//====================================================
// ADD TO CART
//====================================================
WebUI.scrollToElement(findTestObject('WEB/Product/PDP/btn_AddToCart'), 10)

WebUI.click(findTestObject('WEB/Product/PDP/btn_AddToCart'))

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

WebUI.delay(10)

WebUI.scrollToPosition(0, 1500)

WebUI.delay(5)

//====================================================
// DEBUG PAYMENT SECTION
//====================================================
String bodyText = WebUI.executeJavaScript(
	"return document.body.innerText;",
	null
)

println("HAS PAY WITH        : " + bodyText.contains("Pay With"))
println("HAS MIDTRANS        : " + bodyText.contains("Midtrans"))
println("HAS VIRTUAL ACCOUNT : " + bodyText.contains("Virtual Account"))

TestObject midtrans = new TestObject('midtrans')

midtrans.addProperty(
    'xpath',
    ConditionType.EQUALS,
    "//span[contains(@class,'sp-payment-methods__item-name') and normalize-space()='Midtrans']"
)
//====================================================
// PAYMENT METHOD
//====================================================

WebUI.delay(5)

WebUI.scrollToPosition(0, 1200)

boolean midtransFound = false

for (int i = 1; i <= 10; i++) {

	println("WAIT MIDTRANS ATTEMPT : " + i)

	if (
		WebUI.verifyElementPresent(
			midtrans,
			10,
			FailureHandling.OPTIONAL
		)
	) {

		midtransFound = true

		break
	}

	WebUI.delay(3)
}

println("MIDTRANS FOUND : " + midtransFound)

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

