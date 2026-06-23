import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import org.openqa.selenium.Keys as Keys
import internal.GlobalVariable as GlobalVariable

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
WebUI.waitForElementClickable(findTestObject('WEB/Cart/btn_Checkout'), 20)

WebUI.scrollToElement(findTestObject('WEB/Cart/btn_Checkout'), 10)

WebUI.enhancedClick(findTestObject('WEB/Cart/btn_Checkout'))

WebUI.waitForPageLoad(30)

println('CHECKOUT PAGE OPENED')

//====================================================
// WAIT PAYMENT SECTION
//====================================================
WebUI.delay(5)

WebUI.scrollToPosition(0, 1500)

//====================================================
// MIDTRANS
//====================================================
WebUI.waitForElementPresent(findTestObject('WEB/Checkout/Payment Method/rdo_Midtrans'), 30)

WebUI.scrollToElement(findTestObject('WEB/Checkout/Payment Method/rdo_Midtrans'), 10)

WebUI.enhancedClick(findTestObject('WEB/Checkout/Payment Method/rdo_Midtrans'))

println('MIDTRANS SELECTED')

//====================================================
// VIRTUAL ACCOUNT
//====================================================
WebUI.waitForElementPresent(findTestObject('WEB/Checkout/Payment Method/lbl_VirtualAccount'), 20)

WebUI.enhancedClick(findTestObject('WEB/Checkout/Payment Method/lbl_VirtualAccount'))

println('VIRTUAL ACCOUNT OPENED')

//====================================================
// BCA
//====================================================
WebUI.waitForElementPresent(findTestObject('WEB/Checkout/Payment Method/Virtual Account/rdo_BCA'), 20)

WebUI.scrollToElement(findTestObject('WEB/Checkout/Payment Method/Virtual Account/rdo_BCA'), 10)

WebUI.enhancedClick(findTestObject('WEB/Checkout/Payment Method/Virtual Account/rdo_BCA'))

println('BCA VA SELECTED')

//====================================================
// ACCEPT POLICY
//====================================================
WebUI.waitForElementPresent(findTestObject('WEB/Checkout/OrderSummary/checkbox'), 20)

WebUI.enhancedClick(findTestObject('WEB/Checkout/OrderSummary/checkbox'))

println('POLICY CHECKED')

//====================================================
// FINAL CHECKOUT
//====================================================
WebUI.scrollToElement(findTestObject('WEB/Cart/btn_Checkout'), 10)

WebUI.enhancedClick(findTestObject('WEB/Cart/btn_Checkout'))

println('CHECKOUT BUTTON CLICKED')

WebUI.delay(10)

