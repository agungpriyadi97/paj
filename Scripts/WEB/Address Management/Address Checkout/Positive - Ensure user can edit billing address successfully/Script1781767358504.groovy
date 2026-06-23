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
WebUI.scrollToElement(findTestObject('WEB/Cart/btn_Checkout'), 10)

WebUI.click(findTestObject('WEB/Cart/btn_Checkout'))

WebUI.waitForPageLoad(10)

println('CHECKOUT PAGE OPENED')

WebUI.scrollToElement(findTestObject('WEB/Checkout/Address Checkout/Billing Address/Add New Billing Address/chk_BillingSameAsShipping'), 
    10)

WebUI.click(findTestObject('WEB/Checkout/Address Checkout/Billing Address/Add New Billing Address/chk_BillingSameAsShipping'))

WebUI.delay(2)

println('CHANGE SHIPPING ADDRESS CLICKED')

WebUI.click(findTestObject('WEB/Checkout/Address Checkout/Billing Address/Edit Shipping Address/btn_EditAddress'))

WebUI.delay(2)

println('ADD NEW ADDRESS SELECTED')

//====================================================
// RANDOM ADDRESS & POSTAL CODE
//====================================================
def jakartaAddresses = [[('address') : 'Jakarta Selatan', ('postalCode') : '12190'], [('address') : 'Jakarta Barat', ('postalCode') : '11510']
    , [('address') : 'Jakarta Timur', ('postalCode') : '13410'], [('address') : 'Jakarta Utara', ('postalCode') : '14450']
    , [('address') : 'Jakarta Pusat', ('postalCode') : '10110']]

def randomData = jakartaAddresses[new Random().nextInt(jakartaAddresses.size())]

String address = (randomData.address + ' ') + System.currentTimeMillis()

String postalCode = randomData.postalCode

println('ADDRESS : ' + address)

println('POSTAL CODE : ' + postalCode)

//====================================================
// INPUT SHIPPING ADDRESS
//====================================================
WebUI.clearText(findTestObject('WEB/Checkout/Address Checkout/Billing Address/Edit Shipping Address/txt_Address'))

WebUI.setText(findTestObject('WEB/Checkout/Address Checkout/Billing Address/Edit Shipping Address/txt_Address'), address)

println('SHIPPING ADDRESS FILLED')

WebUI.delay(1)

//====================================================
// INPUT POSTAL CODE
//====================================================
WebUI.clearText(findTestObject('WEB/Checkout/Address Checkout/Billing Address/Edit Shipping Address/txt_PostalCode'))

WebUI.setText(findTestObject('WEB/Checkout/Address Checkout/Billing Address/Edit Shipping Address/txt_PostalCode'), postalCode)

println('POSTAL CODE FILLED')

//====================================================
// SAVE
//====================================================
WebUI.scrollToElement(findTestObject('WEB/Checkout/Address Checkout/Billing Address/Edit Shipping Address/btn_Save'), 10)

WebUI.enhancedClick(findTestObject('WEB/Checkout/Address Checkout/Billing Address/Edit Shipping Address/btn_Save'))

WebUI.delay(5)

println('BILLING ADDRESS SAVED')

WebUI.scrollToElement(findTestObject('WEB/Checkout/Address Checkout/Billing Address/Add New Billing Address/chk_BillingSameAsShipping'), 
    10)

//====================================================
// VERIFY
//====================================================
WebUI.verifyTextPresent(address, false)

println('UPDATED BILLING ADDRESS DISPLAYED')

//====================================================
// TEST PASSED
//====================================================
println('USER CAN EDIT BILLING ADDRESS SUCCESSFULLY')

