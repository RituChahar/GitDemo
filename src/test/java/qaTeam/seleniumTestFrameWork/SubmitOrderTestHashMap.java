package qaTeam.seleniumTestFrameWork;

import java.io.IOException;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import qaTeam.seleniumTestFrameWork.PageObjects.*;
import qaTeam.seleniumTestFrameWork.TestComponents.BaseTest;

/**
 * This class contains data-driven test cases for placing an order and verifying
 * it appears in order history. The test uses hardcoded HashMaps as test data
 * (instead of external JSON).
 */
public class SubmitOrderTestHashMap extends BaseTest {

	/**
	 * Test Case: Submit Order This test logs in, adds a product to the cart,
	 * completes the checkout process, and verifies the confirmation message on the
	 * Thank You page.
	 *
	 * @param input - HashMap containing 'email', 'password', and 'productName'
	 */
	@Test(dataProvider = "getData", groups = { "pruchase" })
	public void submitOrder(HashMap<String, String> input) throws IOException, InterruptedException {

		// Step 1: Login using credentials from test data
		landingPage.loginApplication(input.get("email"), input.get("password"));

		// Step 2: Add specified product to cart
		ProductCatalogue productCatalogue = new ProductCatalogue(driver);
		productCatalogue.addProductToCart(input.get("productName"));
		productCatalogue.goToCartPage();

		// Step 3: Verify product exists in cart
		CartPage cartPage = new CartPage(driver);
		Boolean match = cartPage.verifyProductDisplay(input.get("productName"));
		Assert.assertTrue(match, "Product not found in cart.");
		cartPage.goToCheckout();

		// Step 4: Fill payment details and select country
		CheckoutPage checkoutPage = new CheckoutPage(driver);
		CardDetails cardDetails = new CardDetails(driver);
		cardDetails.fillCardDetails(number, inputDate, inputYearIndex, cv, name); // These should be class variables or
																					// constants
		checkoutPage.selectCountry("india");

		// Step 5: Place the order and verify confirmation message
		ConfirmationPage confirmationPage = checkoutPage.placeOrder();
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."),
				"Order confirmation message not as expected.");
	}

	/**
	 * Test Case: Orders History Verification Depends on successful execution of
	 * submitOrder(). Logs into the account and verifies the previously ordered
	 * product appears in the order history.
	 */
	@Test(dependsOnMethods = { "submitOrder" })
	public void ordersHistory() throws IOException {

		// Step 1: Login
		launchApplication();
		landingPage.loginApplication("testid1demo@gmail.com", "Test@123");

		// Step 2: Navigate to Orders Page
		OrdersPage ordersPage = new OrdersPage(driver);
		ordersPage.goToOrdersPage();

		// Step 3: Validate the ordered product appears in order history
		boolean orderExists = ordersPage.verifyOrderNameDisplay(productName);
		Assert.assertTrue(orderExists, "Ordered product not found in order history.");

		String latestProduct = ordersPage.getLatestOrderedProductName();
		System.out.println("Latest ordered product: " + latestProduct);
		Assert.assertEquals(latestProduct, productName, "Latest order product name does not match.");
	}

	/**
	 * DataProvider: Provides two sets of login and product data using HashMap. This
	 * allows easy scalability and parameterization of test data.
	 */
	@DataProvider
	public Object[][] getData() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("email", "testid1demo@gmail.com");
		map.put("password", "Test@123");
		map.put("productName", "ZARA COAT 3");

		HashMap<String, String> map1 = new HashMap<String, String>();
		map1.put("email", "testid1demo@gmail.com");
		map1.put("password", "Test@123");
		map1.put("productName", "ADIDAS ORIGINAL");

		return new Object[][] { { map }, { map1 } };
	}
}
