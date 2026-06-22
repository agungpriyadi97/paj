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

WebUI.verifyElementVisible(findTestObject('WEB/OrderDetail/Page View Detail/lbl_OrderStatus'))

//====================================================
// VERIFY ORDER STATUS
//====================================================
String detailOrderStatus = WebUI.getText(findTestObject('WEB/OrderDetail/Page View Detail/lbl_OrderStatus'))

String orderStatus = WebUI.getText(
	findTestObject('WEB/OrderDetail/Page View Detail/lbl_OrderStatus')
).trim()

assert orderStatus != ''