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

//====================================================
// OPEN MY ORDER
//====================================================
WebUI.click(findTestObject('WEB/Home/Header/Icon Menu/icon_account'))

WebUI.click(findTestObject('WEB/Home/Header/DropdownAccount/my_orders'))

WebUI.waitForPageLoad(10)

//====================================================
// OPEN ORDER DETAIL
//====================================================
WebUI.click(findTestObject('WEB/Checkout/Checkout Success/btn_ViewDetails'))

WebUI.waitForPageLoad(10)

//====================================================
// OPEN CANCEL POPUP
//====================================================
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
WebUI.verifyElementText(findTestObject('WEB/OrderDetail/CancelReason/opt_WrongColorVariant'), 'Choose the wrong color/variant and would like to reorder the correct one.')

println('CANCEL REASON COPYWRITING VERIFIED')

