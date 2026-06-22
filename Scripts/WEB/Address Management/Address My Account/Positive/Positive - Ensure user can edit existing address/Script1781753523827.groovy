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
// OPEN SHIPPING ADDRESS EDIT
//====================================================
WebUI.click(findTestObject('WEB/Address/Shipping Address/Add New Shipping Address/btn_Change_ShippingAddress'))

WebUI.delay(2)

WebUI.click(findTestObject('WEB/Address/Shipping Address/Edit Address/btn_Edit_ShippingAddress'))

WebUI.delay(2)

//====================================================
// UPDATE ADDRESS
//====================================================
String newAddress = 'Updated Address ' + System.currentTimeMillis()

WebUI.clearText(findTestObject('WEB/Checkout/Address Checkout/Shipping Address/Add New Shipping Address/Old Object Add/txt_Address (1)'))

WebUI.setText(findTestObject('WEB/Checkout/Address Checkout/Shipping Address/Add New Shipping Address/Old Object Add/txt_Address (1)'), newAddress)

println('NEW ADDRESS : ' + newAddress)

//====================================================
// SAVE
//====================================================
WebUI.click(findTestObject('WEB/Address/Shipping Address/Edit Address/btn_Save_Address'))

WebUI.delay(5)

println('ADDRESS SAVED')

//====================================================
// VERIFY
//====================================================
WebUI.verifyTextPresent(newAddress, false)

println('ADDRESS UPDATED SUCCESSFULLY')

//====================================================
// TEST PASSED
//====================================================
println('USER CAN EDIT EXISTING ADDRESS SUCCESSFULLY')

