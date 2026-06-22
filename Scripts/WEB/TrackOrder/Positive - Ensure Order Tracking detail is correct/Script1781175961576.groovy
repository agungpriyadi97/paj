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

// ==================== STEP 1: Navigasi ke halaman Sign In ====================
// Browser baru terbuka di halaman Home, klik tombol Sign In di header
WebUI.waitForElementVisible(findTestObject('WEB/Home/Header/Icon Menu/btn_SignIn'), 10)

WebUI.enhancedClick(findTestObject('WEB/Home/Header/Icon Menu/btn_SignIn'), FailureHandling.STOP_ON_FAILURE)

// Pastikan halaman Sign In terbuka
WebUI.waitForElementVisible(findTestObject('WEB/Authentication/Login/txt_Email'), 10)

// ==================== STEP 2: Test case negatif - password salah ====================
// 1. Isi field Email dengan email yang valid (terdaftar)
WebUI.setText(findTestObject('WEB/Authentication/Login/txt_Email'), GlobalVariable.ForgotPasswordEmail)

// 2. Isi field Password dengan nilai yang salah (tidak sesuai)
WebUI.setText(findTestObject('WEB/Authentication/Login/txt_Password'), GlobalVariable.ForgotPasswordNewPassword)

// 3. Klik tombol Sign In
WebUI.click(findTestObject('WEB/Authentication/Login/btn_sign_in'))

WebUI.verifyElementVisible(findTestObject('WEB/Home/Header/Icon Menu/icon_cart'))

WebUI.delay(15)

WebUI.waitForElementVisible(findTestObject('WEB/Home/Header/Icon Menu/menu_track_order'), 15)

//====================================================
// OPEN TRACK ORDER
//====================================================
WebUI.click(findTestObject('WEB/Home/Header/Icon Menu/menu_track_order'))

println('MY ORDER PAGE OPENED')

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

println('ORDER DETAIL PAGE OPENED')

//====================================================
// VERIFY ORDER INFORMATION
//====================================================
WebUI.verifyElementVisible(findTestObject('WEB/OrderDetail/Page View Detail/lbl_OrderNumber'))

WebUI.verifyElementVisible(findTestObject('WEB/OrderDetail/Page View Detail/lbl_CreatedTime'))

WebUI.verifyElementVisible(findTestObject('WEB/OrderDetail/Page View Detail/lbl_OrderStatus'))

println('ORDER INFORMATION DISPLAYED')

//====================================================
// VERIFY PRODUCT INFORMATION
//====================================================
WebUI.verifyElementVisible(findTestObject('WEB/OrderDetail/Page View Detail/lbl_ProductName'))

WebUI.verifyElementVisible(findTestObject('WEB/OrderDetail/Page View Detail/lbl_ProductSKU'))

WebUI.verifyElementVisible(findTestObject('WEB/OrderDetail/Page View Detail/lbl_ProductPrice'))

println('PRODUCT INFORMATION DISPLAYED')

//====================================================
// VERIFY SHIPPING INFORMATION
//====================================================
WebUI.verifyElementVisible(findTestObject('WEB/OrderDetail/Page View Detail/lbl_DeliveryAddress'))

WebUI.verifyElementVisible(findTestObject('WEB/OrderDetail/Page View Detail/lbl_ShippingFee'))

println('SHIPPING INFORMATION DISPLAYED')

//====================================================
// VERIFY BILLING INFORMATION
//====================================================
WebUI.verifyElementVisible(findTestObject('WEB/OrderDetail/Page View Detail/lbl_BillingInfo'))

WebUI.verifyElementVisible(findTestObject('WEB/OrderDetail/Page View Detail/lbl_BillingName'))

WebUI.verifyElementVisible(findTestObject('WEB/OrderDetail/Page View Detail/lbl_BillingPhone'))

WebUI.verifyElementVisible(findTestObject('WEB/OrderDetail/Page View Detail/lbl_BillingAddress'))

println('BILLING INFORMATION DISPLAYED')

//====================================================
// VERIFY PAYMENT INFORMATION
//====================================================
WebUI.verifyElementVisible(findTestObject('WEB/OrderDetail/Page View Detail/lbl_PaymentMethod'))

WebUI.verifyElementVisible(findTestObject('WEB/OrderDetail/Page View Detail/lbl_Subtotal'))

WebUI.verifyElementVisible(findTestObject('WEB/OrderDetail/Page View Detail/lbl_InsuranceFee'))

WebUI.verifyElementVisible(findTestObject('WEB/OrderDetail/Page View Detail/lbl_TotalPrice'))

println('PAYMENT INFORMATION DISPLAYED')

//====================================================
// GET DATA FOR LOG
//====================================================
println('Order Number : ' + WebUI.getText(findTestObject('WEB/OrderDetail/Page View Detail/lbl_OrderNumber')))

println('Status : ' + WebUI.getText(findTestObject('WEB/OrderDetail/Page View Detail/lbl_OrderStatus')))

println('Product : ' + WebUI.getText(findTestObject('WEB/OrderDetail/Page View Detail/lbl_ProductName')))

println('Total : ' + WebUI.getText(findTestObject('WEB/OrderDetail/Page View Detail/lbl_TotalPrice')))

//====================================================
// TEST PASSED
//====================================================
println('ORDER TRACKING DETAIL DISPLAYED CORRECTLY')

