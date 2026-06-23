import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

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
// CHECKOUT
//====================================================
WebUI.scrollToElement(findTestObject('WEB/Cart/btn_Checkout'), 10)

WebUI.enhancedClick(findTestObject('WEB/Cart/btn_Checkout'))

WebUI.waitForPageLoad(20)

println('CHECKOUT PAGE OPENED')

WebUI.delay(5)

WebUI.scrollToPosition(0, 3000)

WebUI.delay(3)

//====================================================
// GET PAGE TEXT BEFORE PROMO
//====================================================
String bodyTextBefore = WebUI.executeJavaScript('return document.body.innerText;', null)

println(bodyTextBefore)

//====================================================
// GET ALL PRICE BEFORE PROMO
//====================================================
def pricesBefore = bodyTextBefore =~ 'Rp\\s*([\\d\\.]+)'

List<Long> valuesBefore = []

while (pricesBefore.find()) {
    valuesBefore.add(pricesBefore.group(1).replace('.', '').toLong())
}

println('VALUES BEFORE : ' + valuesBefore)

assert valuesBefore.size() > 0

Long totalBeforePromo = valuesBefore.max()

println('TOTAL BEFORE PROMO : ' + totalBeforePromo)

//====================================================
// APPLY PROMO
//====================================================
WebUI.waitForElementVisible(findTestObject('WEB/Checkout/Promotion/txt_PromoCode'), 20)

WebUI.setText(findTestObject('WEB/Checkout/Promotion/txt_PromoCode'), 'agung')

WebUI.enhancedClick(findTestObject('WEB/Checkout/Promotion/btn_ApplyPromo'))

println('PROMO APPLIED')

WebUI.delay(5)

//====================================================
// GET PAGE TEXT AFTER PROMO
//====================================================
String bodyTextAfter = WebUI.executeJavaScript('return document.body.innerText;', null)

println(bodyTextAfter)

//====================================================
// GET ALL PRICE AFTER PROMO
//====================================================
def pricesAfter = bodyTextAfter =~ 'Rp\\s*([\\d\\.]+)'

List<Long> valuesAfter = []

while (pricesAfter.find()) {
    valuesAfter.add(pricesAfter.group(1).replace('.', '').toLong())
}

println('VALUES AFTER : ' + valuesAfter)

assert valuesAfter.size() > 0

Long totalAfterPromo = valuesAfter.max()

println('TOTAL AFTER PROMO : ' + totalAfterPromo)

//====================================================
// VERIFY PROMO APPLIED
//====================================================
assert totalAfterPromo < totalBeforePromo

println('PROMOTION APPLIED SUCCESSFULLY')

//====================================================
// TEST PASSED
//====================================================
println('PROMO CODE SUCCESSFULLY APPLIED')

