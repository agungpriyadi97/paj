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
WebUI.waitForElementPresent(findTestObject('WEB/Product/PDP/btn_AddToCart'), 30)

WebUI.waitForElementVisible(findTestObject('WEB/Product/PDP/btn_AddToCart'), 30)

WebUI.waitForElementClickable(findTestObject('WEB/Product/PDP/btn_AddToCart'), 30)

WebUI.scrollToElement(findTestObject('WEB/Product/PDP/btn_AddToCart'), 10)

WebUI.click(findTestObject('WEB/Product/PDP/btn_AddToCart'))

println('PRODUCT ADDED TO CART')

WebUI.delay(10)

//====================================================
// OPEN CART
//====================================================
WebUI.click(findTestObject('WEB/Home/Header/Icon Menu/icon_cart'))

WebUI.waitForPageLoad(10)

println('SHOPPING CART OPENED')

//====================================================
//====================================================
// INCREASE QTY (MINIMUM > 1 PCS)
//====================================================
String currentQty = WebUI.getAttribute(
	findTestObject('WEB/Cart/txt_QuantityValue'),
	'aria-valuenow'
)

int qty = currentQty.toInteger()

println('CURRENT QTY : ' + qty)

// Jika qty masih 1, tambah 1x
if (qty == 1) {

	WebUI.click(
		findTestObject('WEB/Cart/btn_QtyPlus')
	)

	WebUI.delay(2)

	qty = WebUI.getAttribute(
		findTestObject('WEB/Cart/txt_QuantityValue'),
		'aria-valuenow'
	).toInteger()
}

assert qty > 1

println('FINAL QTY : ' + qty)

//====================================================
// CHECKOUT
//====================================================
WebUI.scrollToElement(findTestObject('WEB/Cart/btn_Checkout'), 10)

WebUI.click(findTestObject('WEB/Cart/btn_Checkout'))

WebUI.waitForPageLoad(10)

println('CHECKOUT PAGE OPENED')

//====================================================
// GET TOTAL BEFORE PROMO
//====================================================
String totalBeforePromoText = WebUI.getText(findTestObject('WEB/Checkout/OrderSummary/lbl_Total'))

Long totalBeforePromo = ((totalBeforePromoText.replace('Rp', '').replace('.', '').replace(',', '').trim()) as Long)

println('TOTAL BEFORE PROMO : ' + totalBeforePromo)

//====================================================
// APPLY PROMO CODE
//====================================================
WebUI.setText(findTestObject('WEB/Checkout/Promotion/txt_PromoCode'), 'agung')

WebUI.click(findTestObject('WEB/Checkout/Promotion/btn_ApplyPromo'))

WebUI.delay(5)

println('PROMO CODE APPLIED')

//====================================================
// VERIFY DISCOUNT
//====================================================
String discountText = WebUI.getText(findTestObject('WEB/Checkout/OrderSummary/lbl_CouponDiscount'))

println('DISCOUNT : ' + discountText)

assert discountText.contains('Rp')

//====================================================
// GET TOTAL AFTER PROMO
//====================================================
String totalAfterPromoText = WebUI.getText(findTestObject('WEB/Checkout/OrderSummary/lbl_Total'))

Long totalAfterPromo = ((totalAfterPromoText.replace('Rp', '').replace('.', '').replace(',', '').trim()) as Long)

println('TOTAL AFTER PROMO : ' + totalAfterPromo)

//====================================================
// VERIFY TOTAL REDUCED
//====================================================
assert totalAfterPromo < totalBeforePromo

println('PROMOTION APPLIED SUCCESSFULLY')

//====================================================
// TEST PASSED
//====================================================
println('PROMO CODE SUCCESSFULLY APPLIED')

