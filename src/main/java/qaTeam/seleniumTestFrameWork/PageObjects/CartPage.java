package qaTeam.seleniumTestFrameWork.PageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import qaTeam.seleniumTestFrameWork.AbstractComponents.AbstractComponent;

/**
 * Page Object representing the Cart Page in the application. Displays products
 * added by the user before checkout. Inherits reusable utility methods from
 * AbstractComponent.
 */
public class CartPage extends AbstractComponent {

	WebDriver driver;

	/**
	 * Constructor to initialize WebDriver and PageFactory elements.
	 * 
	 * @param driver WebDriver instance used for element interactions.
	 */
	public CartPage(WebDriver driver) {
		super(driver); // Reuse wait and navigation methods
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// ✅ WebElement for the checkout button on the cart page
	@FindBy(xpath = "//div/ul/li/button")
	WebElement checkoutbutton;

	// ✅ List of all product names currently in the cart
	@FindBy(css = ".cart h3")
	List<WebElement> cartItems;

	/**
	 * Verifies if a specific product is displayed in the cart.
	 * 
	 * @param productName Name of the product to verify.
	 * @return true if product is found, false otherwise.
	 */
	public Boolean verifyProductDisplay(String productName) {
		// Check if any item in the cart matches the product name (case-insensitive)
		boolean foundInCart = cartItems.stream().anyMatch(cartItem -> cartItem.getText().equalsIgnoreCase(productName));
		return foundInCart;
	}

	/**
	 * Clicks the checkout button to proceed from the cart to the checkout page.
	 * @return 
	 */
	public CheckoutPage goToCheckout() {
		checkoutbutton.click();
		return new CheckoutPage(driver);
	}
}
