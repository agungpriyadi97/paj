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

WebUI.delay(2)

WebUI.waitForElementVisible(findTestObject('WEB/Home/Header/DropdownAccount/my_address'), 10)

WebUI.click(findTestObject('WEB/Home/Header/DropdownAccount/my_address'))

//====================================================
// CHANGE BILLING ADDRESS
//====================================================
WebUI.scrollToElement(findTestObject('WEB/Address/Billing Address/Add New Billing Address/btn_Change_BillingAddress'), 10)

WebUI.waitForElementVisible(findTestObject('WEB/Address/Billing Address/Add New Billing Address/btn_Change_BillingAddress'), 
    10)

WebUI.waitForElementClickable(findTestObject('WEB/Address/Billing Address/Add New Billing Address/btn_Change_BillingAddress'), 
    10)

WebUI.click(findTestObject('WEB/Address/Billing Address/Add New Billing Address/btn_Change_BillingAddress'))

WebUI.delay(2)

println('CHANGE BILLING ADDRESS CLICKED')

//====================================================
// ADD NEW BILLING ADDRESS
//====================================================
WebUI.waitForElementVisible(findTestObject('WEB/Address/Billing Address/Add New Billing Address/rdo_AddNewAddress'), 10)

WebUI.click(findTestObject('WEB/Address/Billing Address/Add New Billing Address/rdo_AddNewAddress'))

WebUI.delay(2)

println('ADD NEW ADDRESS SELECTED')

//====================================================
// INPUT BILLING ADDRESS
//====================================================
String firstName = 'QA'

String lastName = 'Automation'

String mobilePhone = '08' + System.currentTimeMillis().toString().substring(5, 13)

String address = 'Billing Address ' + System.currentTimeMillis()

WebUI.scrollToElement(findTestObject('WEB/Address/Billing Address/Add New Billing Address/txt_FirstName'), 10)

WebUI.setText(findTestObject('WEB/Address/Billing Address/Add New Billing Address/txt_FirstName'), firstName)

WebUI.setText(findTestObject('WEB/Address/Billing Address/Add New Billing Address/txt_LastName'), lastName)

WebUI.setText(findTestObject('WEB/Address/Billing Address/Add New Billing Address/txt_MobilePhone'), mobilePhone)

WebUI.setText(findTestObject('WEB/Address/Billing Address/Add New Billing Address/txt_Address'), address)

println('BILLING ADDRESS FILLED')

//====================================================
// SELECT PROVINCE
//====================================================
WebUI.scrollToElement(findTestObject('WEB/Address/Billing Address/Add New Billing Address/ddl_Province'), 10)

WebUI.waitForElementVisible(findTestObject('WEB/Address/Billing Address/Add New Billing Address/ddl_Province'), 10)

WebUI.click(findTestObject('WEB/Address/Billing Address/Add New Billing Address/ddl_Province'))

WebUI.click(findTestObject('WEB/Address/Billing Address/Add New Billing Address/opt_Province_Banten'))

println('PROVINCE SELECTED')

WebUI.delay(2)

//====================================================
// SELECT CITY
//====================================================
WebUI.click(findTestObject('WEB/Address/Billing Address/Add New Billing Address/ddl_City'))

WebUI.waitForElementVisible(findTestObject('WEB/Address/Billing Address/Add New Billing Address/opt_City_KabTangerang'), 
    10)

WebUI.click(findTestObject('WEB/Address/Billing Address/Add New Billing Address/opt_City_KabTangerang'))

println('CITY SELECTED')

WebUI.delay(2)

//====================================================
// SELECT DISTRICT
//====================================================
WebUI.click(findTestObject('WEB/Address/Billing Address/Add New Billing Address/ddl_District'))

WebUI.waitForElementVisible(findTestObject('WEB/Address/Billing Address/Add New Billing Address/opt_District_Balaraja'), 
    10)

WebUI.click(findTestObject('WEB/Address/Billing Address/Add New Billing Address/opt_District_Balaraja'))

println('DISTRICT SELECTED')

WebUI.delay(2)

//====================================================
// POSTAL CODE
//====================================================
WebUI.setText(findTestObject('WEB/Address/Billing Address/Add New Billing Address/txt_PostalCode'), '15810')

println('POSTAL CODE FILLED')

//====================================================
// SAVE
//====================================================
WebUI.click(findTestObject('WEB/Address/Billing Address/Add New Billing Address/btn_Save'))

WebUI.delay(5)

WebUI.scrollToElement(findTestObject('WEB/Address/Billing Address/Add New Billing Address/btn_Change_BillingAddress'), 0)

println('NEW BILLING ADDRESS SAVED')

//====================================================
// VERIFY NEW BILLING ADDRESS DISPLAYED
//====================================================
WebUI.verifyTextPresent(firstName, false)

WebUI.verifyTextPresent(lastName, false)

WebUI.verifyTextPresent(address, false)

println('NEW BILLING ADDRESS DISPLAYED')

//====================================================
// TEST PASSED
//====================================================
println('USER CAN ADD NEW BILLING ADDRESS SUCCESSFULLY')

