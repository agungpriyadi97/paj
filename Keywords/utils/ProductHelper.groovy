package utils

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.WebElement

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

class ProductHelper {

	@Keyword
	def addAnyAvailableProduct() {

		// WAIT PRODUCT LIST
		WebUI.waitForElementVisible(
			findTestObject('Home Page/Search/list_product'),
			15)

		// GET PRODUCT LIST
		List<WebElement> products =
				WebUiCommonHelper.findWebElements(
				findTestObject('Home Page/Search/list_product'),
				15)

		int totalProduct = products.size()

		WebUI.comment("Total product found : " + totalProduct)

		boolean productFound = false

		// LOOP PRODUCT
		for (int i = 1; i <= totalProduct; i++) {

			try {

				WebUI.comment("Checking product index : " + i)

				// DYNAMIC PRODUCT
				TestObject dynamicProduct = new TestObject()

				dynamicProduct.addProperty(
					'xpath',
					ConditionType.EQUALS,
					"(//div[contains(@class,'sp-plp-card')])[" + i + "]")

				// WAIT PRODUCT
				WebUI.waitForElementClickable(
					dynamicProduct,
					10)

				// SCROLL
				WebUI.scrollToElement(
					dynamicProduct,
					5)

				// CLICK PRODUCT
				WebUI.click(dynamicProduct)

				// WAIT PDP LOAD
				WebUI.waitForPageLoad(10)

				// CHECK ADD TO CART BUTTON
				boolean addToCartExist =
						WebUI.verifyElementVisible(
						findTestObject(
						'Product and Cart/Add to cart/button_Add to Cart'),
						FailureHandling.OPTIONAL)

				if (addToCartExist) {

					WebUI.comment('Available product found')

					// CLICK ADD TO CART
					WebUI.click(
						findTestObject(
						'Product and Cart/Add to cart/button_Add to Cart'))

					productFound = true

					break

				} else {

					WebUI.comment('Product sold out')

					WebUI.back()

					WebUI.waitForPageLoad(10)

					WebUI.waitForElementVisible(
						findTestObject(
						'Home Page/Search/list_product'),
						10)
				}

			} catch (Exception e) {

				WebUI.comment(
					'Failed product index : '
					+ i
					+ ' | '
					+ e.getMessage())

				WebUI.back()

				WebUI.waitForPageLoad(10)
			}
		}

		// FINAL ASSERTION
		assert productFound :
				'Tidak ada product available'
	}
}