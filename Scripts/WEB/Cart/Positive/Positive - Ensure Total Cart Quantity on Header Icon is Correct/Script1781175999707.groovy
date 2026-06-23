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
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType

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

WebUI.delay(10)

println('TUMBLERS PAGE OPENED')

//====================================================
// OPEN FIRST PRODUCT
//====================================================
WebUI.waitForElementClickable(
findTestObject('WEB/Product/PLP/Product/card_FirstProduct'),
20
)

WebUI.click(
findTestObject('WEB/Product/PLP/Product/card_FirstProduct')
)

WebUI.waitForPageLoad(20)

println('PRODUCT DETAIL PAGE OPENED')

//====================================================
// ADD TO CART
//====================================================
WebUI.waitForElementVisible(
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
WebUI.click(
findTestObject('WEB/Home/Header/Icon Menu/icon_cart')
)

WebUI.waitForPageLoad(20)

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
// DYNAMIC OBJECT - QTY FIELD
//====================================================
TestObject qtyField = new TestObject('qtyField')

qtyField.addProperty(
'xpath',
ConditionType.EQUALS,
"//input[@role='spinbutton']"
)

//====================================================
// DYNAMIC OBJECT - PLUS BUTTON
//====================================================
TestObject btnPlus = new TestObject('btnPlus')

btnPlus.addProperty(
'xpath',
ConditionType.EQUALS,
"//span[contains(@class,'el-input-number__increase')]"
)

//====================================================
// WAIT QTY FIELD
//====================================================
boolean qtyFound = false

for(int i=1; i<=5; i++) {

if(
	WebUI.verifyElementPresent(
		qtyField,
		5,
		FailureHandling.OPTIONAL
	)
) {

	qtyFound = true

	break
}

println("WAIT QTY FIELD ATTEMPT : " + i)

WebUI.delay(2)

}

assert qtyFound : 'Quantity field not displayed'

//====================================================
// GET QTY BEFORE
//====================================================
int qtyBefore = Integer.parseInt(
WebUI.getAttribute(
qtyField,
'aria-valuenow'
)
)

println('QTY BEFORE : ' + qtyBefore)

//====================================================
// GET TOTAL BEFORE
//====================================================
String totalBefore = WebUI.getText(
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

WebUI.enhancedClick(
btnPlus
)

println('PLUS BUTTON CLICKED')

//====================================================
// WAIT QTY UPDATED
//====================================================
WebUI.delay(3)

int qtyAfter = Integer.parseInt(
WebUI.getAttribute(
qtyField,
'aria-valuenow'
)
)

println('QTY AFTER : ' + qtyAfter)

//====================================================
// VERIFY QTY
//====================================================
assert qtyAfter == qtyBefore + 1

println('QTY UPDATED SUCCESSFULLY')

//====================================================
// VERIFY TOTAL
//====================================================
String totalAfter = WebUI.getText(
findTestObject('WEB/Cart/lbl_Total')
)

println('TOTAL AFTER : ' + totalAfter)

assert totalAfter != totalBefore

println('ORDER SUMMARY UPDATED')

//====================================================
// TEST PASSED
//====================================================
println('USER CAN UPDATE PRODUCT QUANTITY SUCCESSFULLY')