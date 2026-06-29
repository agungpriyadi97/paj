package myorder

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.util.Arrays

import org.openqa.selenium.WebElement

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

class MyOrderKeyword {

    @Keyword
    def openFirstUnpaidOrder() {

        WebUI.waitForElementClickable(
            findTestObject('WEB/MyOrder/tab_Unpaid'),
            20
        )

        WebUI.enhancedClick(
            findTestObject('WEB/MyOrder/tab_Unpaid')
        )

        println("UNPAID TAB CLICKED")

        WebUI.delay(2)

        //-------------------------------------------------
        // Wait Active Tab
        //-------------------------------------------------

        boolean active=false

        for(int i=1;i<=10;i++){

            String tabName = WebUI.executeJavaScript("""

                var tab=document.querySelector(
                    '[role="tab"][aria-selected="true"]'
                );

                return tab ? tab.innerText.trim() : "";

            """, null)

            println("ACTIVE TAB : " + tabName)

            if(tabName.equalsIgnoreCase("Unpaid")){

                active=true

                break
            }

            WebUI.delay(1)
        }

        assert active : "Unpaid tab is not active."

        //-------------------------------------------------
        // View Detail
        //-------------------------------------------------

        TestObject btn = new TestObject()

        btn.addProperty(
            "xpath",
            ConditionType.EQUALS,

            "//div[contains(@class,'el-tab-pane')" +
            " and not(contains(@style,'display: none'))]" +

            "//button[.//span[normalize-space()='View Details']]"
        )

        WebUI.waitForElementPresent(btn,30)

        List<WebElement> buttons =
            WebUI.findWebElements(btn,30)

        assert buttons.size()>0 :
            "View Details button not found."

        println("TOTAL VIEW DETAIL : " + buttons.size())

        WebElement firstButton = buttons.get(0)

        WebUI.executeJavaScript(
            "arguments[0].scrollIntoView({block:'center'});",
            Arrays.asList(firstButton)
        )

        WebUI.delay(1)

        try{

            firstButton.click()

        }catch(Exception e){

            WebUI.executeJavaScript(
                "arguments[0].click();",
                Arrays.asList(firstButton)
            )
        }

        println("VIEW DETAILS CLICKED")

        //-------------------------------------------------
        // Wait Detail Page
        //-------------------------------------------------

        boolean loaded=false

        for(int i=1;i<=20;i++){

            if(WebUI.verifyElementPresent(

                findTestObject(
                    'WEB/OrderDetail/Page View Detail/btn_Cancel'
                ),

                2,

                FailureHandling.OPTIONAL

            )){

                loaded=true

                break
            }

            WebUI.delay(1)
        }

        assert loaded :
            "Order Detail page failed to load."

        println("ORDER DETAIL PAGE OPENED")
    }
	
	@Keyword
	def cancelOrder() {
	
		WebUI.scrollToElement(
			findTestObject('WEB/OrderDetail/Page View Detail/btn_Cancel'),
			10
		)
	
		WebUI.waitForElementClickable(
			findTestObject('WEB/OrderDetail/Page View Detail/btn_Cancel'),
			20
		)
	
		try {
	
			WebUI.enhancedClick(
				findTestObject('WEB/OrderDetail/Page View Detail/btn_Cancel')
			)
	
		} catch(Exception e){
	
			WebUI.executeJavaScript(
				"arguments[0].click();",
				Arrays.asList(
					WebUI.findWebElement(
						findTestObject('WEB/OrderDetail/Page View Detail/btn_Cancel')
					)
				)
			)
		}
	
		println("CANCEL POPUP OPENED")
	
		WebUI.waitForElementVisible(
			findTestObject('WEB/OrderDetail/CancelReason/ddl_Reason'),
			20
		)
	
		WebUI.enhancedClick(
			findTestObject('WEB/OrderDetail/CancelReason/ddl_Reason')
		)
	
		WebUI.enhancedClick(
			findTestObject('WEB/OrderDetail/CancelReason/opt_WrongColorVariant')
		)
	
		println("CANCEL REASON SELECTED")
	
		WebUI.enhancedClick(
			findTestObject('WEB/OrderDetail/CancelReason/btn_Confirm')
		)
	
		println("CONFIRM BUTTON CLICKED")
	
		WebUI.waitForElementClickable(
			findTestObject('WEB/OrderDetail/CancelReason/btn_OK'),
			20
		)
	
		WebUI.enhancedClick(
			findTestObject('WEB/OrderDetail/CancelReason/btn_OK')
		)
	
		println("OK BUTTON CLICKED")
	
		WebUI.waitForElementVisible(
			findTestObject('WEB/OrderDetail/CancelReason/msg_success'),
			20
		)
	
		println("ORDER CANCELLED SUCCESSFULLY")
	}
	
	@Keyword
	def verifyCancelReason() {
	
		WebUI.scrollToElement(
			findTestObject('WEB/OrderDetail/Page View Detail/btn_Cancel'),
			10
		)
	
		WebUI.waitForElementClickable(
			findTestObject('WEB/OrderDetail/Page View Detail/btn_Cancel'),
			20
		)
	
		WebUI.enhancedClick(
			findTestObject('WEB/OrderDetail/Page View Detail/btn_Cancel')
		)
	
		WebUI.waitForElementVisible(
			findTestObject('WEB/OrderDetail/CancelReason/lbl_Cancel'),
			20
		)
	
		WebUI.verifyElementText(
			findTestObject('WEB/OrderDetail/CancelReason/lbl_Cancel'),
			"Cancel"
		)
	
		WebUI.verifyElementText(
			findTestObject('WEB/OrderDetail/CancelReason/lbl_Reason'),
			"Reason"
		)
	
		WebUI.verifyElementAttributeValue(
			findTestObject('WEB/OrderDetail/CancelReason/txt_Select'),
			"placeholder",
			"Select",
			10
		)
	
		WebUI.enhancedClick(
			findTestObject('WEB/OrderDetail/CancelReason/ddl_Reason')
		)
	
		WebUI.verifyElementText(
			findTestObject('WEB/OrderDetail/CancelReason/opt_WrongColorVariant'),
			"Choose the wrong color/variant and would like to reorder the correct one."
		)
	
		println("CANCEL REASON COPYWRITING VERIFIED")
	}

}

