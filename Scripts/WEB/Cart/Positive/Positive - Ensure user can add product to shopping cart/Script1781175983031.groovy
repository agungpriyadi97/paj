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
// VERIFY SHOPPING CART PAGE
//====================================================

WebUI.verifyElementVisible(
	findTestObject('WEB/Cart/lbl_ShoppingCartTitle')
)

println('SHOPPING CART PAGE DISPLAYED')

//====================================================
// VERIFY PRODUCT DISPLAYED
//====================================================

WebUI.verifyElementVisible(
	findTestObject('WEB/Cart/img_Product')
)

WebUI.verifyElementVisible(
	findTestObject('WEB/Cart/lbl_ProductName')
)

WebUI.verifyElementVisible(
	findTestObject('WEB/Cart/lbl_ProductSKU')
)

println('PRODUCT DISPLAYED IN CART')

//====================================================
// VERIFY PRICE
//====================================================

WebUI.verifyElementVisible(
	findTestObject('WEB/Cart/lbl_SellingPrice')
)

println('PRODUCT PRICE DISPLAYED')

//====================================================
// VERIFY QTY
//====================================================

String qty =
	WebUI.getAttribute(
		findTestObject('WEB/Cart/txt_QuantityValue'),
		'value'
	)

println('QTY : ' + qty)

assert qty.toInteger() > 0

//====================================================
// VERIFY ORDER SUMMARY
//====================================================

WebUI.verifyElementVisible(
	findTestObject('WEB/Cart/lbl_OrderSummary')
)

WebUI.verifyElementVisible(
	findTestObject('WEB/Cart/lbl_Subtotal')
)

WebUI.verifyElementVisible(
	findTestObject('WEB/Cart/lbl_Total')
)
assert qty.toInteger() > 0
println('ORDER SUMMARY DISPLAYED')

//====================================================
// TEST PASSED
//====================================================

println('USER CAN ADD PRODUCT TO SHOPPING CART SUCCESSFULLY')