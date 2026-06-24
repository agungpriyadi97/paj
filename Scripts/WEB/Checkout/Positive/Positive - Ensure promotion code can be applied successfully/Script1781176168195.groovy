import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
//====================================================
// LOGIN
//====================================================
WebUI.callTestCase(findTestCase('WEB/Authentication/Login/Positive/Positive - Ensure user can login with valid account and password'), 
    [:], FailureHandling.STOP_ON_FAILURE)

println('LOGIN SUCCESS')

WebUI.delay(5)

//====================================================
// OPEN PDP DIRECTLY
//====================================================
WebUI.navigateToUrl('https://d-speedshop-pastiadajalan.gtechdigital.id/pdp/SP250526661250')

WebUI.waitForPageLoad(30)

println('PDP PAGE OPENED')

//====================================================
// ADD TO CART
//====================================================
WebUI.waitForElementPresent(findTestObject('WEB/Product/PDP/btn_AddToCart'), 30)

WebUI.waitForElementVisible(findTestObject('WEB/Product/PDP/btn_AddToCart'), 30)

WebUI.waitForElementClickable(findTestObject('WEB/Product/PDP/btn_AddToCart'), 30)

WebUI.scrollToElement(findTestObject('WEB/Product/PDP/btn_AddToCart'), 10)

WebUI.enhancedClick(findTestObject('WEB/Product/PDP/btn_AddToCart'))

WebUI.delay(10)

println('PRODUCT ADDED TO CART')

WebUI.delay(3)

//====================================================
// OPEN CART
//====================================================
WebUI.waitForElementClickable(findTestObject('WEB/Home/Header/Icon Menu/icon_cart'), 20)

WebUI.enhancedClick(findTestObject('WEB/Home/Header/Icon Menu/icon_cart'))

WebUI.waitForPageLoad(30)

println('SHOPPING CART OPENED')

//====================================================
// UPDATE QTY TO 2
//====================================================
WebUI.delay(3)

String qtyText =
WebUI.executeJavaScript(
"""
var qtyInput =
document.querySelector(
'input[role="spinbutton"]'
);

if(qtyInput){
	return qtyInput.getAttribute('aria-valuenow')
		|| qtyInput.value
		|| '1';
}

return '1';
""",
null
)

int qty =
qtyText.toInteger()

println(
	'CURRENT QTY : ' +
	qty
)

if(qty < 2){

	WebUI.click(
		findTestObject(
			'WEB/Cart/btn_QtyPlus'
		)
	)

	WebUI.delay(5)

	println(
		'QTY UPDATED TO 2'
	)
}

//====================================================
// CHECKOUT
//====================================================
WebUI.scrollToElement(
	findTestObject('WEB/Cart/btn_Checkout'),
	10
)

WebUI.enhancedClick(
	findTestObject('WEB/Cart/btn_Checkout')
)

WebUI.waitForPageLoad(30)

println('CHECKOUT PAGE OPENED')

WebUI.delay(5)

WebUI.scrollToPosition(0, 3000)

WebUI.delay(3)

//====================================================
// TOTAL OBJECT
//====================================================
TestObject totalObj = new TestObject('totalObj')

totalObj.addProperty(
	'xpath',
	ConditionType.EQUALS,
	"//ul[contains(@class,'price-detail')]//li[contains(@class,'total')]/span[last()]"
)

//====================================================
// TOTAL BEFORE PROMO
//====================================================
WebUI.waitForElementVisible(
	totalObj,
	20
)

String totalBeforeText =
	WebUI.getText(totalObj)

println('TOTAL BEFORE TEXT : ' + totalBeforeText)

Long totalBeforePromo =
	totalBeforeText
		.replace('Rp', '')
		.replace('.', '')
		.replace(',', '')
		.trim()
		.toLong()

println('TOTAL BEFORE PROMO : ' + totalBeforePromo)

//====================================================
// APPLY PROMO
//====================================================
WebUI.waitForElementVisible(
	findTestObject('WEB/Checkout/Promotion/txt_PromoCode'),
	20
)

WebUI.setText(
	findTestObject('WEB/Checkout/Promotion/txt_PromoCode'),
	'agung'
)

WebUI.enhancedClick(
	findTestObject('WEB/Checkout/Promotion/btn_ApplyPromo')
)

println('PROMO APPLIED')

WebUI.delay(5)

//====================================================
// VERIFY COUPON DISCOUNT APPEARS
//====================================================
String pageText =
	WebUI.executeJavaScript(
		"return document.body.innerText;",
		null
	)

assert pageText.contains('Coupon Discount')

println('COUPON DISCOUNT DISPLAYED')

//====================================================
// TOTAL AFTER PROMO
//====================================================
WebUI.waitForElementVisible(
	totalObj,
	20
)

String totalAfterText =
	WebUI.getText(totalObj)

println('TOTAL AFTER TEXT : ' + totalAfterText)

Long totalAfterPromo =
	totalAfterText
		.replace('Rp', '')
		.replace('.', '')
		.replace(',', '')
		.trim()
		.toLong()

println('TOTAL AFTER PROMO : ' + totalAfterPromo)

//====================================================
// VERIFY PROMO APPLIED
//====================================================
assert totalAfterPromo < totalBeforePromo

println('PROMOTION APPLIED SUCCESSFULLY')

//====================================================
// FINAL RESULT
//====================================================
println('======================================')
println('TOTAL BEFORE : ' + totalBeforePromo)
println('TOTAL AFTER  : ' + totalAfterPromo)
println('PROMO CODE SUCCESSFULLY APPLIED')
println('TEST CASE PASSED')
println('======================================')