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
WebUI.scrollToElement(findTestObject('WEB/Address/Shipping Address/Add New Shipping Address/btn_Change_ShippingAddress'), 
    10)

WebUI.click(findTestObject('WEB/Address/Shipping Address/Add New Shipping Address/btn_Change_ShippingAddress'))

WebUI.delay(2)

println('CHANGE BILLING ADDRESS CLICKED')

//====================================================
// DELETE BILLING ADDRESS
//====================================================
WebUI.click(findTestObject('WEB/Address/Shipping Address/Delete Shipping Address/btn_Delete_ShippingAddress'))

println('DELETE BUTTON CLICKED')

//====================================================
// CONFIRM DELETE
//====================================================
WebUI.verifyElementVisible(findTestObject('WEB/Address/Shipping Address/Delete Shipping Address/popup_DeleteConfirmation'))

WebUI.click(findTestObject('WEB/Address/Shipping Address/Delete Shipping Address/btn_Yes'))

println('DELETE CONFIRMED')

WebUI.delay(5)

//====================================================
// VERIFY DELETE SUCCESS
//====================================================
WebUI.verifyTextNotPresent('QA Automation', false)

println('BILLING ADDRESS DELETED SUCCESSFULLY')

//====================================================
// TEST PASSED
//====================================================
println('USER CAN DELETE BILLING ADDRESS SUCCESSFULLY')

