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
// OPEN TUMBLERS CATEGORY
//====================================================
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
// GUEST CHECKOUT
//====================================================
String guestEmail = 'agungpriyadi99@mailinator.com'

WebUI.waitForElementVisible(findTestObject('Object Repository/WEB/Checkout/GuestCheckout/txt_Email'), 20)

WebUI.setText(findTestObject('Object Repository/WEB/Checkout/GuestCheckout/txt_Email'), guestEmail)

println('GUEST EMAIL : ' + guestEmail)

WebUI.click(findTestObject('Object Repository/WEB/Checkout/GuestCheckout/btn_Continue'))

WebUI.waitForPageLoad(10)

println('CONTINUE AS GUEST SUCCESS')

//====================================================
// SHIPPING ADDRESS
//====================================================
WebUI.waitForPageLoad(10)

WebUI.setText(findTestObject('WEB/Checkout/Address Checkout/Shipping Address/Add New Shipping Address/Old Object Add/txt_FirstName (1)'), 
    'Agung')

WebUI.setText(findTestObject('WEB/Checkout/Address Checkout/Shipping Address/Add New Shipping Address/Old Object Add/txt_LastName (1)'), 
    'Priyadi')

WebUI.setText(findTestObject('WEB/Checkout/Address Checkout/Shipping Address/Add New Shipping Address/Old Object Add/txt_MobilePhone (1)'), 
    '081234567890')

WebUI.setText(findTestObject('WEB/Checkout/Address Checkout/Shipping Address/Add New Shipping Address/Old Object Add/txt_Address (1)'), 
    'Guest Address ' + System.currentTimeMillis())

println('SHIPPING ADDRESS FILLED')

WebUI.click(findTestObject('WEB/Checkout/Address Checkout/Shipping Address/Add New Shipping Address/Old Object Add/ddl_Province (1)'))

WebUI.click(findTestObject('WEB/Checkout/Address Checkout/Shipping Address/Add New Shipping Address/Old Object Add/opt_Province_Banten'))

println('PROVINCE SELECTED')

WebUI.delay(2)

WebUI.click(findTestObject('WEB/Checkout/GuestCheckout/ddl_City'))

WebUI.click(findTestObject('WEB/Checkout/Address Checkout/Shipping Address/Add New Shipping Address/Old Object Add/opt_City_KotaTangerang'))

println('CITY SELECTED')

WebUI.delay(2)

WebUI.click(findTestObject('WEB/Checkout/GuestCheckout/ddl_District'))

WebUI.click(findTestObject('WEB/Checkout/Address Checkout/Shipping Address/Add New Shipping Address/Old Object Add/opt_District_Larangan'))

println('DISTRICT SELECTED')

WebUI.setText(findTestObject('WEB/Checkout/Address Checkout/Shipping Address/Add New Shipping Address/Old Object Add/txt_PostalCode (1)'), 
    '15810')

println('POSTAL CODE FILLED')

WebUI.click(findTestObject('WEB/Checkout/Address Checkout/Shipping Address/Add New Shipping Address/Old Object Add/btn_Save (1)'))

WebUI.delay(5)

println('ADDRESS SAVED')

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
// VERIFY SUCCESS PAGE
//====================================================
WebUI.waitForElementVisible(findTestObject('WEB/Checkout/Checkout Success/lbl_OrderSuccess'), 30)

WebUI.verifyElementVisible(findTestObject('WEB/Checkout/Checkout Success/lbl_OrderSuccess'))

WebUI.verifyTextPresent('Your order has been placed successfully.', false)

println('SUCCESS PAGE DISPLAYED')

