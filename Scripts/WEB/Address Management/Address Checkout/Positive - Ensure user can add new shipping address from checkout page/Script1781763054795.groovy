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
WebUI.scrollToElement(findTestObject('WEB/Cart/btn_Checkout'), 10)

WebUI.click(findTestObject('WEB/Cart/btn_Checkout'))

WebUI.waitForPageLoad(10)

println('CHECKOUT PAGE OPENED')

WebUI.scrollToElement(findTestObject('WEB/Checkout/Address Checkout/Shipping Address/Add New Shipping Address/btn_Change_ShippingAddress'), 
    10)

WebUI.click(findTestObject('WEB/Checkout/Address Checkout/Shipping Address/Add New Shipping Address/btn_Change_ShippingAddress'))

WebUI.delay(2)

println('CHANGE SHIPPING ADDRESS CLICKED')

WebUI.click(findTestObject('WEB/Checkout/Address Checkout/Shipping Address/Add New Shipping Address/btn_AddNewAddress'))

WebUI.delay(2)

println('ADD NEW ADDRESS SELECTED')

//====================================================
// INPUT SHIPPING ADDRESS
//====================================================
String firstName = 'QA'

String lastName = 'Automation'

String mobilePhone = '08' + System.currentTimeMillis().toString().substring(5, 13)

String address = 'Shipping Address ' + System.currentTimeMillis()

WebUI.setText(findTestObject('WEB/Address/Shipping Address/Add New Shipping Address/txt_FirstName'), firstName)

WebUI.setText(findTestObject('WEB/Address/Shipping Address/Add New Shipping Address/txt_LastName'), lastName)

WebUI.setText(findTestObject('WEB/Address/Shipping Address/Add New Shipping Address/txt_MobilePhone'), mobilePhone)

WebUI.setText(findTestObject('WEB/Address/Shipping Address/Add New Shipping Address/txt_Address'), address)

println('SHIPPING ADDRESS FILLED')

//====================================================
// PROVINCE
//====================================================
WebUI.scrollToElement(findTestObject('WEB/Checkout/Address Checkout/Shipping Address/Add New Shipping Address/New Object Add/ddl_Province'), 
    5)

WebUI.waitForElementVisible(findTestObject('WEB/Checkout/Address Checkout/Shipping Address/Add New Shipping Address/New Object Add/ddl_Province'), 
    10)

WebUI.click(findTestObject('WEB/Checkout/Address Checkout/Shipping Address/Add New Shipping Address/New Object Add/ddl_Province'))

WebUI.click(findTestObject('WEB/Checkout/Address Checkout/Shipping Address/Add New Shipping Address/Old Object Add/opt_Province_Banten'))

println('PROVINCE SELECTED')

WebUI.delay(2)

//====================================================
// CITY
//====================================================
WebUI.scrollToElement(findTestObject('WEB/Checkout/Address Checkout/Shipping Address/Add New Shipping Address/New Object Add/ddl_City'), 
    5)

WebUI.waitForElementVisible(findTestObject('WEB/Checkout/Address Checkout/Shipping Address/Add New Shipping Address/New Object Add/ddl_City'), 
    10)

WebUI.click(findTestObject('WEB/Checkout/Address Checkout/Shipping Address/Add New Shipping Address/New Object Add/ddl_City'))

WebUI.click(findTestObject('WEB/Address/Shipping Address/Add New Shipping Address/opt_City_KabTangerang'))

println('CITY SELECTED')

WebUI.delay(2)

//====================================================
// DISTRICT
//====================================================
WebUI.scrollToElement(findTestObject('WEB/Checkout/Address Checkout/Shipping Address/Add New Shipping Address/New Object Add/ddl_District'), 
    5)

WebUI.waitForElementVisible(findTestObject('WEB/Checkout/Address Checkout/Shipping Address/Add New Shipping Address/New Object Add/ddl_District'), 
    10)

WebUI.click(findTestObject('WEB/Checkout/Address Checkout/Shipping Address/Add New Shipping Address/New Object Add/ddl_District'))

WebUI.click(findTestObject('WEB/Address/Shipping Address/Add New Shipping Address/opt_District_Balaraja'))

println('DISTRICT SELECTED')

WebUI.delay(2)

//====================================================
// POSTAL CODE
//====================================================
WebUI.setText(findTestObject('WEB/Address/Shipping Address/Add New Shipping Address/txt_PostalCode'), '15810')

println('POSTAL CODE FILLED')

//====================================================
// SAVE
//====================================================
WebUI.scrollToElement(findTestObject('WEB/Checkout/Address Checkout/Shipping Address/Add New Shipping Address/New Object Add/btn_Save'), 
    10)

WebUI.enhancedClick(findTestObject('WEB/Checkout/Address Checkout/Shipping Address/Add New Shipping Address/New Object Add/btn_Save'))

WebUI.delay(5)

WebUI.scrollToElement(findTestObject('WEB/Checkout/Address Checkout/Shipping Address/Add New Shipping Address/btn_Change_ShippingAddress'), 
    10)

println('NEW SHIPPING ADDRESS SAVED')

//====================================================
// VERIFY
//====================================================
WebUI.verifyTextPresent(firstName, false)

WebUI.verifyTextPresent(lastName, false)

WebUI.verifyTextPresent(address, false)

println('NEW SHIPPING ADDRESS DISPLAYED')

//====================================================
// TEST PASSED
//====================================================
println('USER CAN ADD NEW SHIPPING ADDRESS SUCCESSFULLY')

