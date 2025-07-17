package qaTeam.seleniumTestFrameWork;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import qaTeam.seleniumTestFrameWork.PageObjects.CartPage;
import qaTeam.seleniumTestFrameWork.PageObjects.ProductCatalogue;
import qaTeam.seleniumTestFrameWork.TestComponents.BaseTest;
import qaTeam.seleniumTestFrameWork.TestComponents.Retry;

/**
 * This test class validates negative scenarios like login failure and verifying
 * an incorrect product in the cart.
 */
public class ErrorValidations extends BaseTest {

	/**
	 * This test validates error message when incorrect credentials are used. <br>
	 * Expected: Displays "Incorrect email or password." toast message. <br>
	 * Group: ErrorHandling | Retry enabled.
	 *
	 * @throws IOException when login or properties fail to load
	 */

	@Test(groups = { "ErrorHandling" }, retryAnalyzer = Retry.class)
	public void LoginErrorValidation() throws IOException {
		// Attempt login with wrong password
		landingPage.loginApplication("testid1demo@gmail.com", "Test@124");

		// Assert the expected error message appears
		Assert.assertEquals(landingPage.getErrorMessage(), "Incorrect email or password.");
	}

	/**
	 * This test adds a valid product to cart and tries to verify presence of an
	 * incorrect (non-added) product to ensure false match fails.
	 *
	 * @throws InterruptedException if waitForWebElementToDisappear uses sleep
	 *                              fallback
	 */

	@Test
	public void productErrorValidation() throws InterruptedException {
		// Step 1: Login with valid credentials
		landingPage.loginApplication("testid1demo@gmail.com", "Test@123");

		// Step 2: Add correct product to cart
		ProductCatalogue productCatalogue = new ProductCatalogue(driver);
		productCatalogue.addProductToCart(productName);
		productCatalogue.goToCartPage();

		// Step 3: Validate that a wrong product is NOT present in cart
		CartPage cartPage = new CartPage(driver);
		boolean match = cartPage.verifyProductDisplay("wrong item"); // Deliberate mismatch
		Assert.assertFalse(match); // Expecting no match
	}
}
