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

WebUI.mouseOver(findTestObject('WEB/Home/Header/Icon Menu/icon_account'))

WebUI.click(findTestObject('WEB/Home/Header/Icon Menu/icon_account'))

WebUI.waitForElementVisible(findTestObject('WEB/Home/Header/DropdownAccount/my_orders'), 10)

WebUI.waitForElementPresent(findTestObject('WEB/Home/Header/DropdownAccount/my_orders'), 5)

WebUI.mouseOver(findTestObject('WEB/Home/Header/DropdownAccount/my_orders'))

WebUI.click(findTestObject('WEB/Home/Header/DropdownAccount/my_orders'))

//====================================================
// OPEN ORDER DETAIL
//====================================================
WebUI.click(findTestObject('WEB/MyOrder/btn_ViewDetails'))

WebUI.waitForPageLoad(10)

WebUI.verifyElementVisible(findTestObject('WEB/OrderDetail/Page View Detail/lbl_OrderStatus'))

//====================================================
// VERIFY ORDER STATUS
//====================================================
String detailOrderStatus = WebUI.getText(findTestObject('WEB/OrderDetail/Page View Detail/lbl_OrderStatus'))

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

