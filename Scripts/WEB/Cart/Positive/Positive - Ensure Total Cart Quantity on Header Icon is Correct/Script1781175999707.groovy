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
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

//====================================================
// LOGIN
//====================================================
WebUI.callTestCase(
findTestCase('WEB/Authentication/Login/Positive/Positive - Ensure user can login with valid account and password'),
[:],
FailureHandling.STOP_ON_FAILURE
)

println('LOGIN SUCCESS')

WebUI.delay(5)

//====================================================
// OPEN PDP DIRECTLY
//====================================================
WebUI.navigateToUrl(
'https://d-speedshop-pastiadajalan.gtechdigital.id/pdp/SP250526661250'
)

WebUI.waitForPageLoad(30)

println('PDP PAGE OPENED')

//====================================================
// ADD TO CART
//====================================================
WebUI.waitForElementPresent(
findTestObject('WEB/Product/PDP/btn_AddToCart'),
30
)

WebUI.waitForElementVisible(
findTestObject('WEB/Product/PDP/btn_AddToCart'),
30
)

WebUI.waitForElementClickable(
findTestObject('WEB/Product/PDP/btn_AddToCart'),
30
)

WebUI.scrollToElement(
findTestObject('WEB/Product/PDP/btn_AddToCart'),
10
)

WebUI.enhancedClick(
findTestObject('WEB/Product/PDP/btn_AddToCart')
)

WebUI.delay(3)

println('PRODUCT ADDED TO CART')

//====================================================
// OPEN CART
//====================================================
WebUI.waitForElementClickable(
findTestObject('WEB/Home/Header/Icon Menu/icon_cart'),
20
)

WebUI.enhancedClick(
findTestObject('WEB/Home/Header/Icon Menu/icon_cart')
)

WebUI.waitForPageLoad(30)

println('SHOPPING CART OPENED')

//====================================================
// VERIFY CART PAGE
//====================================================
WebUI.waitForElementVisible(
findTestObject('WEB/Cart/lbl_ShoppingCartTitle'),
30
)

WebUI.waitForElementVisible(
findTestObject('WEB/Cart/lbl_ProductName'),
30
)

println('SHOPPING CART PAGE DISPLAYED')

//====================================================
// DYNAMIC OBJECTS
//====================================================
TestObject qtyField = new TestObject('qtyField')

qtyField.addProperty(
'xpath',
ConditionType.EQUALS,
'//input[@role="spinbutton"]'
)

TestObject btnPlus = new TestObject('btnPlus')

btnPlus.addProperty(
'xpath',
ConditionType.EQUALS,
'//span[contains(@class,"el-input-number__increase")]'
)

//====================================================
// VERIFY QTY FIELD
//====================================================
boolean qtyFound = false

for(int i=1;i<=5;i++) {


if(
	WebUI.verifyElementPresent(
		qtyField,
		5,
		FailureHandling.OPTIONAL
	)
){
	qtyFound = true
	break
}

WebUI.delay(2)


}

assert qtyFound : 'Quantity field not displayed'

//====================================================
// GET QTY BEFORE
//====================================================
String qtyBeforeText = WebUI.executeJavaScript(
"""
var qtyInput =
document.querySelector(
'input[role="spinbutton"]'
);

if(!qtyInput){
return '0';
}

return qtyInput.getAttribute('aria-valuenow')
|| qtyInput.value
|| '0';
""",
null
)

int qtyBefore = qtyBeforeText.toInteger()

println('QTY BEFORE : ' + qtyBefore)

//====================================================
// TOTAL BEFORE
//====================================================
String totalBefore =
WebUI.getText(
findTestObject('WEB/Cart/lbl_Total')
)

println('TOTAL BEFORE : ' + totalBefore)

//====================================================
// CLICK PLUS
//====================================================
WebUI.waitForElementClickable(
btnPlus,
20
)

WebUI.scrollToElement(
btnPlus,
10
)

WebUI.enhancedClick(
btnPlus
)

println('PLUS BUTTON CLICKED')

//====================================================
// WAIT QTY UPDATED
//====================================================
boolean qtyUpdated = false

int qtyAfter = qtyBefore

for(int i=1;i<=10;i++) {


WebUI.delay(1)

String currentQty = WebUI.executeJavaScript(
"""
var qtyInput =
document.querySelector(
'input[role="spinbutton"]'
);

if(!qtyInput){
	return '0';
}

return qtyInput.getAttribute('aria-valuenow')
	|| qtyInput.value
	|| '0';
""",
null
)

qtyAfter = currentQty.toInteger()

println('CURRENT QTY : ' + qtyAfter)

if(qtyAfter > qtyBefore){

	qtyUpdated = true
	break
}


}

assert qtyUpdated : 'Quantity was not increased'

println('QTY UPDATED SUCCESSFULLY')

println('QTY AFTER : ' + qtyAfter)

//====================================================
// VERIFY TOTAL UPDATED
//====================================================
WebUI.delay(2)

String totalAfter =
WebUI.getText(
findTestObject('WEB/Cart/lbl_Total')
)

println('TOTAL AFTER : ' + totalAfter)

assert totalAfter != totalBefore

println('ORDER SUMMARY UPDATED')

//====================================================
// TEST PASSED
//====================================================
println('======================================')
println('QTY BEFORE : ' + qtyBefore)
println('QTY AFTER  : ' + qtyAfter)
println('TOTAL BEFORE : ' + totalBefore)
println('TOTAL AFTER  : ' + totalAfter)
println('USER CAN UPDATE PRODUCT QUANTITY SUCCESSFULLY')
println('TEST CASE PASSED')
println('======================================')
