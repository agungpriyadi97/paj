import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.testobject.ConditionType as ConditionType
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
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

//====================================================
// CHECKOUT ORDER
//====================================================
WebUI.callTestCase(
	findTestCase(
		'WEB/Checkout/Positive/Positive - Ensure user can checkout order successfully'
	),
	[:],
	FailureHandling.STOP_ON_FAILURE
)

println("CHECKOUT SUCCESS")

//====================================================
// WAIT SUCCESS PAGE
//====================================================
WebUI.waitForPageLoad(30)

WebUI.waitForElementVisible(
	findTestObject('WEB/Checkout/Checkout Success/lbl_OrderSuccess'),
	30
)

WebUI.verifyElementVisible(
	findTestObject('WEB/Checkout/Checkout Success/lbl_OrderSuccess')
)

println("CHECKOUT SUCCESS PAGE DISPLAYED")

//====================================================
// VERIFY PAYMENT INFORMATION
//====================================================
WebUI.verifyElementVisible(
	findTestObject('WEB/Checkout/Checkout Success/lbl_OrderStatus')
)

WebUI.verifyElementVisible(
	findTestObject('WEB/Checkout/Checkout Success/lbl_OrderNumber')
)

WebUI.verifyElementVisible(
	findTestObject('WEB/Checkout/Checkout Success/lbl_PaymentMethod')
)

WebUI.verifyElementVisible(
	findTestObject('WEB/Checkout/Checkout Success/lbl_TotalPrice')
)

println("ORDER INFORMATION VERIFIED")

//====================================================
// GET ORDER NUMBER
//====================================================
String orderNumber =
	WebUI.getText(
		findTestObject('WEB/Checkout/Checkout Success/lbl_OrderNumber')
	).trim()

assert orderNumber != ""

GlobalVariable.OrderNumber= orderNumber

println("ORDER NUMBER : " + orderNumber)

//====================================================
// GET PAYMENT METHOD
//====================================================
String paymentMethod =
	WebUI.getText(
		findTestObject('WEB/Checkout/Checkout Success/lbl_PaymentMethod')
	).trim()

assert paymentMethod != ""

GlobalVariable.PaymentMethod = paymentMethod

println("PAYMENT METHOD : " + paymentMethod)

//====================================================
// GET TOTAL PRICE
//====================================================
String totalPrice =
	WebUI.getText(
		findTestObject('WEB/Checkout/Checkout Success/lbl_TotalPrice')
	).trim()

assert totalPrice != ""

GlobalVariable.TotalPrice = totalPrice

println("TOTAL PRICE : " + totalPrice)

//====================================================
// GET VIRTUAL ACCOUNT
//====================================================
TestObject lblVA = new TestObject("lblVA")

lblVA.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//span[normalize-space()='Virtual Bank Account']/following::span[1]"
)

WebUI.waitForElementVisible(
	lblVA,
	20
)

String virtualAccount =
	WebUI.getText(lblVA)
		.replaceAll("\\s+","")
		.trim()

assert virtualAccount != ""

GlobalVariable.VirtualAccount = virtualAccount

println("VIRTUAL ACCOUNT : " + virtualAccount)

//====================================================
// VERIFY VA FORMAT
//====================================================
assert virtualAccount.matches("\\d+")

println("VIRTUAL ACCOUNT FORMAT VERIFIED")

//====================================================
// TEST PASSED
//====================================================
println("====================================")
println("PAYMENT INSTRUCTION VERIFIED")
println("ORDER NUMBER   : " + GlobalVariable.OrderNumber)
println("PAYMENT METHOD : " + GlobalVariable.PaymentMethod)
println("TOTAL PRICE    : " + GlobalVariable.TotalPrice)
println("VIRTUAL ACCOUNT: " + GlobalVariable.VirtualAccount)
println("====================================")