package qaTeam.seleniumTestFrameWork.stepDefinitions;


import java.io.IOException;

import org.testng.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import qaTeam.seleniumTestFrameWork.PageObjects.CardDetails;
import qaTeam.seleniumTestFrameWork.PageObjects.CartPage;
import qaTeam.seleniumTestFrameWork.PageObjects.CheckoutPage;
import qaTeam.seleniumTestFrameWork.PageObjects.ConfirmationPage;
import qaTeam.seleniumTestFrameWork.PageObjects.LandingPage;
import qaTeam.seleniumTestFrameWork.PageObjects.ProductCatalogue;
import qaTeam.seleniumTestFrameWork.TestComponents.BaseTest;

public class StepDefinitionImpl extends BaseTest {

	public LandingPage landingPage;
	public ProductCatalogue productCatalogue;
	public CartPage cartPage;
	public CheckoutPage checkoutPage;
	public ConfirmationPage confirmationPage;
	public CardDetails cardDetails;

	@Given("I landed on Ecommerce Page")
	public void I_landed_on_Ecommerce_Page() throws IOException {

		landingPage = launchApplication();

	}

	@Given("^Logged in with username (.+) and password (.+)$")
	public void Logged_in_username_and_password(String email, String password) {
	    productCatalogue = landingPage.loginApplication(email, password);
	}


	@When("^I add product (.+) to cart$")
	public void added_product_to_cart(String productName) throws InterruptedException {

		productCatalogue.addProductToCart(productName);
		cartPage = productCatalogue.goToCartPage();

		Boolean match = cartPage.verifyProductDisplay(productName);
		Assert.assertTrue(match, "Product not found in cart.");
		checkoutPage = cartPage.goToCheckout(); 

		

	}

	@And("^Checkout (.+) and submit the order$")
	public void checkout_and_submit_order(String productName) {

		
		CardDetails cardDetails = new CardDetails(driver);
		cardDetails.fillCardDetails(number, inputDate, inputYearIndex, cv, name);
		checkoutPage.selectCountry("india");
		confirmationPage = checkoutPage.placeOrder();

	}

	@Then("{string} message is displayed on ConfirmationPage")
	public void message_displayed_on_confirmationpage(String expectedMessage) {

		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."),
				"Order confirmation message not as expected.");

		driver.close();
	}

	@Then ("{string} message is displayed")
	public void warrning_message_displayed(String strArg1) {
		
		Assert.assertEquals(strArg1, landingPage.getErrorMessage());
		
		driver.close();
		
	}
	
	
	
	

	
	
	
	
	
	
	
	
	
}
