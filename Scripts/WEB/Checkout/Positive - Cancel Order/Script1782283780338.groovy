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
import java.util.Arrays as Arrays
import com.kms.katalon.core.testobject.ConditionType as ConditionType

WebUI.callTestCase(findTestCase('WEB/Authentication/Login/Positive/Positive - Ensure user can login with valid account and password'), 
    [:], FailureHandling.STOP_ON_FAILURE)

WebUI.mouseOver(findTestObject('WEB/Home/Header/Icon Menu/icon_account'))

WebUI.click(findTestObject('WEB/Home/Header/Icon Menu/icon_account'))

WebUI.waitForElementVisible(findTestObject('WEB/Home/Header/DropdownAccount/my_orders'), 10)

WebUI.waitForElementPresent(findTestObject('WEB/Home/Header/DropdownAccount/my_orders'), 5)

WebUI.mouseOver(findTestObject('WEB/Home/Header/DropdownAccount/my_orders'))

WebUI.click(findTestObject('WEB/Home/Header/DropdownAccount/my_orders'))

WebUI.waitForPageLoad(10)

println('ORDER DETAIL PAGE OPENED')

WebUI.waitForElementClickable(findTestObject('WEB/MyOrder/tab_Unpaid'), 10)

WebUI.click(findTestObject('WEB/MyOrder/tab_Unpaid'), FailureHandling.STOP_ON_FAILURE)

WebUI.delay(10)

//====================================================
// OPEN ORDER DETAIL
//====================================================
TestObject btnViewDetails = new TestObject('btnViewDetails')

btnViewDetails.addProperty('xpath', ConditionType.EQUALS, '(//button[.//span[normalize-space()=\'View Details\']])[1]')

WebUI.waitForElementPresent(btnViewDetails, 30)

WebUI.waitForElementVisible(btnViewDetails, 30)

def viewDetailElement = WebUI.findWebElement(btnViewDetails, 30)

WebUI.executeJavaScript('arguments[0].scrollIntoView({block:\'center\'});', Arrays.asList(viewDetailElement))

WebUI.delay(2)

// LANGSUNG JS CLICK
WebUI.executeJavaScript('arguments[0].click();', Arrays.asList(viewDetailElement))

println('VIEW DETAILS CLICKED')

WebUI.delay(3)

WebUI.waitForPageLoad(20)

println('ORDER DETAIL PAGE OPENED')

WebUI.scrollToElement(findTestObject('WEB/OrderDetail/Page View Detail/btn_Cancel'), 10)

WebUI.waitForElementClickable(findTestObject('WEB/OrderDetail/Page View Detail/btn_Cancel'), 10)

WebUI.click(findTestObject('WEB/OrderDetail/Page View Detail/btn_Cancel'))

WebUI.delay(2)

WebUI.verifyElementText(findTestObject('WEB/OrderDetail/CancelReason/lbl_Cancel'), 'Cancel')

// Reason Label
WebUI.verifyElementText(findTestObject('WEB/OrderDetail/CancelReason/lbl_Reason'), 'Reason')

// Select Placeholder
WebUI.verifyElementAttributeValue(findTestObject('WEB/OrderDetail/CancelReason/txt_Select'), 'placeholder', 'Select', 5)

// Open Dropdown
WebUI.click(findTestObject('WEB/OrderDetail/CancelReason/ddl_Reason'))

// Verify Cancel Reason Option
WebUI.click(findTestObject('WEB/OrderDetail/CancelReason/opt_WrongColorVariant'), FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('WEB/OrderDetail/CancelReason/btn_Confirm'))

WebUI.click(findTestObject('WEB/OrderDetail/CancelReason/btn_OK'))

WebUI.waitForElementVisible(findTestObject('WEB/OrderDetail/CancelReason/msg_success'), 2)

