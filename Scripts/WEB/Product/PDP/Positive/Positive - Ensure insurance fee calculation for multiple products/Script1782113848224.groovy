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
WebUI.waitForElementVisible(findTestObject('WEB/Cart/btn_Checkout'), 10)

WebUI.scrollToElement(findTestObject('WEB/Cart/btn_Checkout'), 10)

WebUI.click(findTestObject('WEB/Cart/btn_Checkout'))

WebUI.waitForPageLoad(10)

println('CHECKOUT PAGE OPENED')

//====================================================
// GET SUBTOTAL
//====================================================
String subtotalText = WebUI.getText(findTestObject('WEB/Checkout/OrderSummary/lbl_Subtotal'))

//====================================================
// GET SHIPPING
//====================================================
String shippingText = WebUI.getText(findTestObject('WEB/Checkout/OrderSummary/lbl_ShippingFee'))

//====================================================
// GET INSURANCE
//====================================================
String insuranceText = WebUI.getText(findTestObject('WEB/Checkout/OrderSummary/lbl_InsuranceFee'))

//====================================================
// GET TOTAL
//====================================================
String totalText = WebUI.getText(findTestObject('WEB/Checkout/OrderSummary/lbl_Total'))

//====================================================
// CONVERT TO NUMBER
//====================================================
Long subtotal = ((subtotalText.replace('Rp', '').replace('.', '').replace(',', '').trim()) as Long)

Long shipping = ((shippingText.replace('Rp', '').replace('.', '').replace(',', '').trim()) as Long)

Long insurance = ((insuranceText.replace('Rp', '').replace('.', '').replace(',', '').trim()) as Long)

Long total = ((totalText.replace('Rp', '').replace('.', '').replace(',', '').trim()) as Long)

//====================================================
// VERIFY INSURANCE FORMULA
//====================================================
Long expectedInsurance = Math.round(subtotal * 0.0025)

println('EXPECTED INSURANCE : ' + expectedInsurance)

println('ACTUAL INSURANCE   : ' + insurance)

assert insurance == expectedInsurance

println('INSURANCE FORMULA CORRECT')

//====================================================
// VERIFY TOTAL FORMULA
//====================================================
Long expectedTotal = (subtotal + shipping) + insurance

println('EXPECTED TOTAL : ' + expectedTotal)

println('ACTUAL TOTAL   : ' + total)

assert total == expectedTotal

println('TOTAL FORMULA CORRECT')

