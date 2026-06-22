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

WebUI.delay(2)

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
// BILLING ADDRESS
//====================================================
WebUI.scrollToElement(findTestObject('WEB/Checkout/Address Checkout/Billing Address/Add New Billing Address/chk_BillingSameAsShipping'), 
    10)

WebUI.click(findTestObject('WEB/Checkout/Address Checkout/Billing Address/Add New Billing Address/chk_BillingSameAsShipping'))

WebUI.delay(2)

println('BILLING SAME AS SHIPPING UNCHECKED')

//====================================================
// ADD NEW BILLING ADDRESS
//====================================================
WebUI.scrollToElement(findTestObject('WEB/Checkout/Address Checkout/Billing Address/Add New Billing Address/rdo_AddNewAddress'), 
    10)

WebUI.click(findTestObject('WEB/Checkout/Address Checkout/Billing Address/Add New Billing Address/rdo_AddNewAddress'))

WebUI.delay(2)

println('ADD NEW BILLING ADDRESS SELECTED')

//====================================================
// INPUT BILLING ADDRESS
//====================================================
String firstName = 'QA'

String lastName = 'Automation'

String mobilePhone = '08' + System.currentTimeMillis().toString().substring(5, 13)

String address = 'Billing Address ' + System.currentTimeMillis()

WebUI.setText(findTestObject('WEB/Checkout/Address Checkout/Billing Address/Add New Billing Address/txt_FirstName'), firstName)

WebUI.setText(findTestObject('WEB/Checkout/Address Checkout/Billing Address/Add New Billing Address/txt_LastName'), lastName)

WebUI.setText(findTestObject('WEB/Checkout/Address Checkout/Billing Address/Add New Billing Address/txt_MobilePhone'), mobilePhone)

WebUI.setText(findTestObject('WEB/Checkout/Address Checkout/Billing Address/Add New Billing Address/txt_Address'), address)

println('BILLING ADDRESS FILLED')

//====================================================
// PROVINCE
//====================================================
WebUI.scrollToElement(findTestObject('WEB/Checkout/Address Checkout/Billing Address/Add New Billing Address/ddl_Province'), 
    5)

WebUI.click(findTestObject('WEB/Checkout/Address Checkout/Billing Address/Add New Billing Address/ddl_Province'))

WebUI.click(findTestObject('WEB/Checkout/Address Checkout/Billing Address/Add New Billing Address/opt_Province_Banten'))

println('PROVINCE SELECTED')

WebUI.delay(2)

//====================================================
// CITY
//====================================================
WebUI.click(findTestObject('WEB/Checkout/Address Checkout/Billing Address/Add New Billing Address/ddl_City'))

WebUI.click(findTestObject('WEB/Checkout/Address Checkout/Billing Address/Add New Billing Address/opt_City_KabTangerang'))

println('CITY SELECTED')

WebUI.delay(2)

//====================================================
// DISTRICT
//====================================================
WebUI.click(findTestObject('WEB/Checkout/Address Checkout/Billing Address/Add New Billing Address/ddl_District'))

WebUI.click(findTestObject('WEB/Checkout/Address Checkout/Billing Address/Add New Billing Address/opt_District_Balaraja'))

println('DISTRICT SELECTED')

WebUI.delay(2)

//====================================================
// POSTAL CODE
//====================================================
WebUI.setText(findTestObject('WEB/Checkout/Address Checkout/Billing Address/Add New Billing Address/txt_PostalCode'), '15810')

println('POSTAL CODE FILLED')

//====================================================
// SAVE
//====================================================
WebUI.scrollToElement(findTestObject('WEB/Checkout/Address Checkout/Billing Address/Add New Billing Address/btn_Save'), 
    10)

WebUI.click(findTestObject('WEB/Checkout/Address Checkout/Billing Address/Add New Billing Address/btn_Save'))

WebUI.delay(5)

WebUI.scrollToElement(findTestObject('WEB/Checkout/Address Checkout/Billing Address/Add New Billing Address/chk_BillingSameAsShipping'), 
    10)

println('NEW BILLING ADDRESS SAVED')

//====================================================
// VERIFY
//====================================================
WebUI.verifyTextPresent(firstName, false)

WebUI.verifyTextPresent(lastName, false)

WebUI.verifyTextPresent(address, false)

println('NEW BILLING ADDRESS DISPLAYED')

//====================================================
// TEST PASSED
//====================================================
println('USER CAN ADD NEW BILLING ADDRESS FROM CHECKOUT PAGE SUCCESSFULLY')

