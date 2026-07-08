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
// OPEN PDP DIRECTLY
//====================================================
//====================================================
// ADD TO CART
//====================================================
//====================================================
// OPEN CART
//====================================================
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil

WebUI.callTestCase(findTestCase('WEB/Authentication/Login/Positive/Positive - Ensure user can login with valid account and password'), 
    [:], FailureHandling.STOP_ON_FAILURE)

println('LOGIN SUCCESS')

WebUI.delay(5)

WebUI.navigateToUrl('https://d-speedshop-pastiadajalan.gtechdigital.id/pdp/SP260706006287')

WebUI.waitForPageLoad(30)

println('PDP PAGE OPENED')

WebUI.waitForElementPresent(findTestObject('WEB/Product/PDP/btn_AddToCart'), 30)

WebUI.waitForElementVisible(findTestObject('WEB/Product/PDP/btn_AddToCart'), 30)

WebUI.waitForElementClickable(findTestObject('WEB/Product/PDP/btn_AddToCart'), 30)

WebUI.scrollToElement(findTestObject('WEB/Product/PDP/btn_AddToCart'), 10)

WebUI.enhancedClick(findTestObject('WEB/Product/PDP/btn_AddToCart'))

WebUI.delay(10)

println('PRODUCT ADDED TO CART')

WebUI.delay(3)

WebUI.waitForElementClickable(findTestObject('WEB/Home/Header/Icon Menu/icon_cart'), 20)

WebUI.enhancedClick(findTestObject('WEB/Home/Header/Icon Menu/icon_cart'))

WebUI.waitForPageLoad(30)

println('SHOPPING CART OPENED')

//====================================================
// VERIFY SHOPPING CART PAGE
//====================================================
WebUI.waitForElementVisible(findTestObject('WEB/Cart/lbl_ShoppingCartTitle'), 30)

println('SHOPPING CART PAGE DISPLAYED')

//====================================================
// CHECK STOCK MESSAGE
//====================================================
String pageText = WebUI.executeJavaScript('return document.body.innerText;', null)

if (pageText.contains('Only 1 left in stock')) {
    KeywordUtil.markWarning('Stock only 1. Quantity update cannot be tested.')

    return null
}

//====================================================
// GET QTY BEFORE
//====================================================
String qtyBeforeText = WebUI.executeJavaScript('\nvar qtyInput =\ndocument.querySelector(\n\'input[role="spinbutton"]\'\n);\n\nif(qtyInput){\n\treturn qtyInput.getAttribute(\'aria-valuenow\')\n\t\t|| qtyInput.value\n\t\t|| \'0\';\n}\n\nreturn \'0\';\n', 
    null)

int qtyBefore = qtyBeforeText.toInteger()

println('QTY BEFORE : ' + qtyBefore)

//====================================================
// GET TOTAL BEFORE
//====================================================
String totalBefore = WebUI.getText(findTestObject('WEB/Cart/lbl_Total'))

println('TOTAL BEFORE : ' + totalBefore)

//====================================================
// CLICK PLUS BUTTON
//====================================================
WebUI.waitForElementClickable(findTestObject('WEB/Cart/btn_QtyPlus'), 20)

WebUI.scrollToElement(findTestObject('WEB/Cart/btn_QtyPlus'), 10)

WebUI.enhancedClick(findTestObject('WEB/Cart/btn_QtyPlus'))

println('PLUS BUTTON CLICKED')

//====================================================
// WAIT QTY CHANGED
//====================================================
boolean qtyUpdated = false

int qtyAfter = qtyBefore

for (int i = 1; i <= 10; i++) {
    WebUI.delay(2)

    String currentQty = WebUI.executeJavaScript('\nvar qtyInput =\ndocument.querySelector(\n\'input[role="spinbutton"]\'\n);\n\nif(qtyInput){\n\treturn qtyInput.getAttribute(\'aria-valuenow\')\n\t\t|| qtyInput.value\n\t\t|| \'0\';\n}\n\nreturn \'0\';\n', 
        null)

    qtyAfter = currentQty.toInteger()

    println((('ATTEMPT ' + i) + ' QTY : ') + qtyAfter)

    if (qtyAfter > qtyBefore) {
        qtyUpdated = true

        break
    }
}

assert qtyUpdated : 'Quantity was not increased'

//====================================================
// VERIFY QTY UPDATED
//====================================================
println('QTY AFTER : ' + qtyAfter)

assert qtyAfter > qtyBefore

println('QTY UPDATED SUCCESSFULLY')

//====================================================
// VERIFY TOTAL UPDATED
//====================================================
String totalAfter = WebUI.getText(findTestObject('WEB/Cart/lbl_Total'))

println('TOTAL AFTER : ' + totalAfter)

assert totalAfter != totalBefore

println('ORDER SUMMARY UPDATED')

//====================================================
// TEST PASSED
//====================================================
println('======================================')

println('USER CAN UPDATE PRODUCT QUANTITY SUCCESSFULLY')

println('QTY BEFORE : ' + qtyBefore)

println('QTY AFTER : ' + qtyAfter)

println('======================================')

