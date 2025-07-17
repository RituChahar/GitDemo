package qaTeam.seleniumTestFrameWork;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import qaTeam.seleniumTestFrameWork.PageObjects.*;
import qaTeam.seleniumTestFrameWork.TestComponents.BaseTest;

/**
 * This TestNG class performs a complete end-to-end test of the order submission
 * process and verifies that the ordered product appears in order history.
 */
public class SubmitOrderTest extends BaseTest {

	/**
	 * This test logs into the application, adds a product to the cart, completes
	 * checkout using card details, and verifies the order confirmation.
	 *
	 * @param input - A HashMap containing keys: email, password, productName
	 */
	@Test(dataProvider = "getData", groups = { "pruchase" })
	public void submitOrder(HashMap<String, String> input) throws IOException, InterruptedException {

		// Step 1: Login with user credentials from data provider
		landingPage.loginApplication(input.get("email"), input.get("password"));

		// Step 2: Add product to cart
		ProductCatalogue productCatalogue = new ProductCatalogue(driver);
		productCatalogue.addProductToCart(input.get("productName"));
		productCatalogue.goToCartPage();

		// Step 3: Verify product is present in cart
		CartPage cartPage = new CartPage(driver);
		Boolean match = cartPage.verifyProductDisplay(input.get("productName"));
		Assert.assertTrue(match, "Product not found in cart.");
		cartPage.goToCheckout();

		// Step 4: Fill payment details and select country
		CheckoutPage checkoutPage = new CheckoutPage(driver);
		CardDetails cardDetails = new CardDetails(driver);

		// These are assumed to be class-level or inherited values
		cardDetails.fillCardDetails(number, inputDate, inputYearIndex, cv, name);
		checkoutPage.selectCountry("india");

		// Step 5: Place order and verify confirmation message
		ConfirmationPage confirmationPage = checkoutPage.placeOrder();
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."),
				"Order confirmation message not as expected.");
	}

	/**
	 * This test depends on successful order placement. It validates whether the
	 * product appears in the user's order history.
	 */
	@Test(dependsOnMethods = { "submitOrder" })
	public void ordersHistory() throws IOException {

		// Login to application
		launchApplication();
		landingPage.loginApplication("testid1demo@gmail.com", "Test@123");

		// Navigate to Orders page
		OrdersPage ordersPage = new OrdersPage(driver);
		ordersPage.goToOrdersPage();

		// Validate that the product appears in the order history
		boolean orderExists = ordersPage.verifyOrderNameDisplay(productName);
		Assert.assertTrue(orderExists, "Ordered product not found in order history.");

		// Check that the latest order matches the expected product
		String latestProduct = ordersPage.getLatestOrderedProductName();
		System.out.println("Latest ordered product: " + latestProduct);
		Assert.assertEquals(latestProduct, productName, "Latest order product name does not match.");
	}

	/**
	 * DataProvider that fetches test data from JSON file and returns it as a 2D
	 * array of HashMaps (for data-driven testing).
	 */
	@DataProvider
	public Object[][] getData() throws IOException {
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")
				+ "\\src\\test\\java\\qaTeam\\seleniumTestFrameWork\\data\\PurchaseOrder.json");

		// Return 2 test data sets
		return new Object[][] { { data.get(0) }, { data.get(1) } };
	}
}
