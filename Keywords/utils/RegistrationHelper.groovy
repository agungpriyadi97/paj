package utils

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

import internal.GlobalVariable
import java.util.Random

public class RegistrationHelper {

    @Keyword
    def registerNewAccount() {
        String timestamp = String.valueOf(System.currentTimeMillis())
        String email = "testqa_${timestamp}@mailinator.com"
        String firstName = generateRandomName()
        String lastName = generateRandomName()
        String mobile = generateRandomMobile()
        String password = "P@ssw0rd123!"

        println "=== Registrasi ==="
        println "Email: ${email}"
        println "First Name: ${firstName}"
        println "Last Name: ${lastName}"
        println "Mobile: ${mobile}"
        println "Password: ${password}"

        WebUI.navigateToUrl('https://d-speedshop-pastiadajalan.gtechdigital.id/register')
        WebUI.delay(2)

        WebUI.setText(findTestObject('register_txtEmail'), email)
        WebUI.click(findTestObject('register_btnSendCode'))
        WebUI.delay(5)

        String inbox = email.split('@')[0]
        String verificationCode = getVerificationCodeFromMailinator(inbox)
        if (verificationCode == null) throw new Exception("Gagal ambil kode verifikasi")

        WebUI.setText(findTestObject('register_txtValidationCode'), verificationCode)
        WebUI.setText(findTestObject('register_txtPassword'), password)
        WebUI.setText(findTestObject('register_txtMobile'), mobile)
        WebUI.setText(findTestObject('register_txtFirstName'), firstName)
        WebUI.setText(findTestObject('register_txtLastName'), lastName)
        WebUI.check(findTestObject('register_chkConsent'))
        WebUI.click(findTestObject('register_btnSignUp'))

        WebUI.delay(3)
        println "✅ Registrasi sukses"
        return [email, password]
    }

    @Keyword
    def getVerificationCodeFromMailinator(String inbox) {
        try {
            WebUI.navigateToUrl("https://www.mailinator.com/v4/public/inboxes.jsp?to=${inbox}")
            WebUI.delay(3)

            WebUI.waitForElementVisible(findTestObject('mailinator_firstEmailRow'), 30)
            WebUI.click(findTestObject('mailinator_firstEmailRow'))

            WebUI.delay(3)
            WebUI.switchToDefaultContent()
            WebUI.waitForElementVisible(findTestObject('mailinator_iframeEmailBody'), 20)
            WebUI.switchToFrame(findTestObject('mailinator_iframeEmailBody'), 15)

            String emailText = ""
            if (WebUI.verifyElementPresent(findTestObject('mailinator_otpCodeDiv'), 10, FailureHandling.OPTIONAL)) {
                emailText = WebUI.getText(findTestObject('mailinator_otpCodeDiv'))
            } else {
                emailText = WebUI.getText(findTestObject('mailinator_emailBodyText'))
            }

            def matcher = (emailText =~ /\b\d{6}\b/)
            if (matcher.find()) return matcher.group()
            return null
        } catch (Exception e) {
            println "❌ Error: ${e.message}"
            return null
        } finally {
            WebUI.switchToDefaultContent()
        }
    }

    private String generateRandomName() {
        String[] names = ["John", "Jane", "Michael", "Sarah", "David", "Lisa", "Andi", "Siti", "Budi", "Dewi"]
        Random rand = new Random()
        return names[rand.nextInt(names.length)] + (System.currentTimeMillis() % 1000)
    }

    private String generateRandomMobile() {
        Random rand = new Random()
        String prefix = "0812"
        String number = String.format("%08d", rand.nextInt(100000000))
        return prefix + number
    }
}