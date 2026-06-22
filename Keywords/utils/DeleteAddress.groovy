package utils

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import com.kms.katalon.core.testobject.ConditionType
import internal.GlobalVariable

public class DeleteAddress {

    // ========================= DELETE DELIVERY ADDRESS =========================
    
    /**
     * Menghapus alamat delivery yang PALING BAWAH (terakhir ditambahkan)
     */
    @Keyword
    def deleteLastDeliveryAddress() {
        String xpathDelete = "(//section[.//span[text()='Địa chỉ giao hàng']]//div[@class='address-operation']//span[text()='Xoá'])[last()]"
        TestObject deleteBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS, xpathDelete)
        WebUI.click(deleteBtn)
        
        // Tunggu pop-up muncul
        WebUI.delay(1)
        
        // Tombol konfirmasi "Đồng ý" - XPath dinamis
        String xpathConfirm = "//div[@class='el-message-box__btns']//button[contains(@class, 'el-button--primary')]//span[normalize-space()='Đồng ý']"
        TestObject confirmBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS, xpathConfirm)
        WebUI.waitForElementClickable(confirmBtn, 10)
        WebUI.click(confirmBtn)
        
        WebUI.waitForPageLoad(10)
    }
    
    /**
     * Menghapus alamat delivery berdasarkan email unik
     * @param email alamat email yang muncul di card alamat
     */
    @Keyword
    def deleteAddressByEmail(String email) {
        String xpathDelete = "//div[@class='address-center']//p[contains(text(), '${email}')]/ancestor::div[@class='address-center']/following-sibling::div[@class='address-operation']//span[text()='Xoá']"
        TestObject deleteBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS, xpathDelete)
        WebUI.click(deleteBtn)
        
        WebUI.delay(1)
        String xpathConfirm = "//div[@class='el-message-box__btns']//button[contains(@class, 'el-button--primary')]//span[normalize-space()='Đồng ý']"
        TestObject confirmBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS, xpathConfirm)
        WebUI.waitForElementClickable(confirmBtn, 10)
        WebUI.click(confirmBtn)
        
        WebUI.waitForPageLoad(10)
    }

    // ========================= DELETE BILLING ADDRESS =========================
    
    @Keyword
    def deleteLastBillingAddress() {
        String xpathDelete = "(//section[.//span[text()='Địa chỉ thanh toán']]//div[@class='address-operation']//span[text()='Xoá'])[last()]"
        TestObject deleteBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS, xpathDelete)
        WebUI.click(deleteBtn)
        
        WebUI.delay(1)
        String xpathConfirm = "//div[@class='el-message-box__btns']//button[contains(@class, 'el-button--primary')]//span[normalize-space()='Đồng ý']"
        TestObject confirmBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS, xpathConfirm)
        WebUI.waitForElementClickable(confirmBtn, 10)
        WebUI.click(confirmBtn)
        
        WebUI.waitForPageLoad(10)
    }
    
    @Keyword
    def deleteBillingAddressByEmail(String email) {
        String xpathDelete = "//section[.//span[text()='Địa chỉ thanh toán']]//div[@class='address-center']//p[contains(text(), '${email}')]/ancestor::div[@class='address-center']/following-sibling::div[@class='address-operation']//span[text()='Xoá']"
        TestObject deleteBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS, xpathDelete)
        WebUI.click(deleteBtn)
        
        WebUI.delay(1)
        String xpathConfirm = "//div[@class='el-message-box__btns']//button[contains(@class, 'el-button--primary')]//span[normalize-space()='Đồng ý']"
        TestObject confirmBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS, xpathConfirm)
        WebUI.waitForElementClickable(confirmBtn, 10)
        WebUI.click(confirmBtn)
        
        WebUI.waitForPageLoad(10)
    }
}