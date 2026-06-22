package utils

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.model.FailureHandling
import groovy.json.JsonSlurper

public class MailinatorHelper {
	
		@Keyword
		def getVerificationCodeFromCurrentTab(String inbox) {
			try {
				WebUI.delay(3) // beri waktu halaman stabil
				
				// Pastikan di halaman inbox yang benar
				String currentUrl = WebUI.getUrl()
				if (!currentUrl.contains("/inboxes")) {
					WebUI.navigateToUrl("https://www.mailinator.com/v4/public/inboxes.jsp?to=${inbox}")
					WebUI.delay(3)
				}
				
				// Klik email pertama
				TestObject firstEmail = new TestObject().addProperty('xpath', ConditionType.EQUALS,
					"(//table[contains(@class, 'table-striped')]//tr)[1]//td[contains(@onclick, 'showTheMessage')]")
				WebUI.waitForElementVisible(firstEmail, 30)
				WebUI.click(firstEmail)
				
				// Pindah ke iframe HTML
				WebUI.delay(3)
				WebUI.switchToDefaultContent()
				TestObject emailFrame = new TestObject().addProperty('id', ConditionType.EQUALS, 'html_msg_body')
				WebUI.waitForElementVisible(emailFrame, 20)
				WebUI.switchToFrame(emailFrame, 15)
				
				// Tunggu div OTP muncul (height:56px)
				TestObject otpDiv = new TestObject().addProperty('xpath', ConditionType.EQUALS,
					"//div[contains(@style, 'height: 56px')]")
				WebUI.waitForElementVisible(otpDiv, 15)
				String otpText = WebUI.getText(otpDiv)
				
				def matcher = (otpText =~ /\d{6}/)
				if (matcher.find()) {
					println "✅ Kode: ${matcher.group()}"
					return matcher.group()
				}
				return null
			} catch (Exception e) {
				println "Error: ${e.message}"
				return null
			}
		}
		
		/**
		 * Mengambil body HTML email terbaru dari inbox Mailinator via API
		 * @param inbox Nama inbox
		 * @return Body email dalam format HTML atau null
		 */
		@Keyword
		def getEmailBodyFromApi(String inbox) {
			String apiUrl = "https://api.mailinator.com/api/v2/domains/public/inboxes/${inbox}/messages"
			try {
				def response = WS.sendRequest({
					setRestUrl(apiUrl)
					setRestRequestMethod('GET')
				} as com.kms.katalon.core.testobject.RequestObject)
				
				if (response.getStatusCode() != 200) return null
				def json = new groovy.json.JsonSlurper().parseText(response.getResponseBodyContent())
				if (json.messages && json.messages.size() > 0) {
					def parts = json.messages[0].data?.parts
					def htmlPart = parts?.find { it.headers?.get('Content-Type')?.contains('text/html') }
					return htmlPart?.body ?: ""
				}
				return null
			} catch (Exception e) {
				println("❌ Error getEmailBodyFromApi: ${e.message}")
				return null
			}
		}
	}
	
	

//public class MailinatorHelper {
//
//    @Keyword
//    def getVerificationCodeFromCurrentTab(String inbox) {
//        try {
//            // Pastikan sudah di halaman Mailinator dengan inbox tertentu
//            // Jika belum, arahkan ke URL inbox
//            String currentUrl = WebUI.getUrl()
//            if (!currentUrl.contains("mailinator.com")) {
//                WebUI.navigateToUrl("https://www.mailinator.com/v4/public/inboxes.jsp?to=${inbox}")
//                WebUI.delay(3)
//            }
//            
//            // Cek apakah sudah di halaman inbox (ada tabel email)
//            TestObject emailTable = new TestObject().addProperty('xpath', ConditionType.EQUALS, "//table[contains(@class, 'table-striped')]")
//            if (!WebUI.verifyElementPresent(emailTable, 5, FailureHandling.OPTIONAL)) {
//                // Mungkin masih di halaman depan, coba input inbox lagi
//                TestObject inboxField = null
//                List<String> possibleIds = ['search', 'inbox_field', 'addOverlay']
//                for (String id : possibleIds) {
//                    inboxField = new TestObject().addProperty('id', ConditionType.EQUALS, id)
//                    if (WebUI.verifyElementPresent(inboxField, 2, FailureHandling.OPTIONAL)) {
//                        break
//                    }
//                }
//                if (inboxField != null) {
//                    WebUI.setText(inboxField, inbox)
//                    TestObject goButton = new TestObject().addProperty('xpath', ConditionType.EQUALS,
//                        "//button[contains(@class,'primary-btn') and contains(text(),'GO')] | //button[contains(text(),'GO')]")
//                    WebUI.click(goButton)
//                    WebUI.delay(3)
//                }
//            }
//            
//            // Klik email pertama
//            TestObject firstEmail = new TestObject().addProperty('xpath', ConditionType.EQUALS,
//                "(//table[contains(@class, 'table-striped')]//tr)[1]//td[contains(@onclick, 'showTheMessage')]")
//            WebUI.waitForElementVisible(firstEmail, 30)
//            WebUI.click(firstEmail)
//            
//            // Buka tab JSON
//            WebUI.delay(2)
//            WebUI.switchToDefaultContent()
//            TestObject jsonTab = new TestObject().addProperty('id', ConditionType.EQUALS, 'pills-json-tab')
//            WebUI.waitForElementClickable(jsonTab, 20)
//            WebUI.click(jsonTab)
//            
//            // Ambil JSON
//            WebUI.delay(1)
//            TestObject jsonContent = new TestObject().addProperty('id', ConditionType.EQUALS, 'pills-json-content')
//            WebUI.waitForElementVisible(jsonContent, 20)
//            String jsonText = WebUI.getText(jsonContent)
//            
//            def json = new JsonSlurper().parseText(jsonText)
//            if (json.parts == null || json.parts.isEmpty()) {
//                println("❌ Tidak ada parts dalam JSON")
//                return null
//            }
//            String emailBodyHtml = json.parts[0].body
//            
//            def matcher = (emailBodyHtml =~ /\b(\d{6})\b/)
//            if (!matcher.find()) {
//                matcher = (emailBodyHtml =~ /(\d{6})/)
//            }
//            
//            if (matcher.find()) {
//                String code = matcher.group(1)
//                println("✅ Kode verifikasi ditemukan: " + code)
//                return code
//            } else {
//                println("❌ Tidak ditemukan kode 6 digit dalam email")
//                return null
//            }
//        } catch (Exception e) {
//            println("❌ Error: " + e.getMessage())
//            return null
//        }
//    }
//    
//    @Keyword
//    def clearInbox(String inbox) {
//        println("Clear inbox not needed for public Mailinator")
//    }
//}