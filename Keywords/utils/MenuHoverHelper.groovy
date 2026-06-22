package utils

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebElement
import org.openqa.selenium.JavascriptExecutor
import java.util.Arrays

public class MenuHoverHelper {

    @Keyword
    def hoverAndClickSubMenu(TestObject parentMenu, TestObject childItem) {
        // 1. Hover ke menu utama dengan JavaScript
        WebElement parentElement = WebUiCommonHelper.findWebElement(parentMenu, 10)
        String hoverJS = "var evObj = document.createEvent('MouseEvents');" +
            "evObj.initMouseEvent('mouseover', true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);" +
            "arguments[0].dispatchEvent(evObj);"
        WebUI.executeJavaScript(hoverJS, Arrays.asList(parentElement))
        
        // 2. Tunggu submenu muncul (delay singkat)
        WebUI.delay(1)
        
        // 3. Klik submenu dengan JavaScript (hindari ElementNotInteractableException)
        WebUI.waitForElementPresent(childItem, 10)
        WebElement childElement = WebUiCommonHelper.findWebElement(childItem, 5)
        JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getWebDriver()
        js.executeScript("arguments[0].click();", childElement)
    }
}