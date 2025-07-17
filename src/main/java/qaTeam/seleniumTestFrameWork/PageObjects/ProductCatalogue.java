package qaTeam.seleniumTestFrameWork.PageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import qaTeam.seleniumTestFrameWork.AbstractComponents.AbstractComponent;

/**
 * Page Object representing the Product Catalogue page (product listing screen).
 * This class provides methods to fetch product list, locate specific products,
 * and add products to cart using Selenium interactions.
 */
public class ProductCatalogue extends AbstractComponent {

	WebDriver driver;

	/**
	 * Constructor to initialize WebDriver and PageFactory elements.
	 * 
	 * @param driver WebDriver instance passed from test class
	 */

	public ProductCatalogue(WebDriver driver) {
		super(driver); // Reuse abstract component features (e.g., waits)
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// ✅ List of all product cards (visible on UI)
	@FindBy(css = ".mb-3")
	List<WebElement> products;

	// ✅ Locator to wait until products are loaded
	By productsList = By.cssSelector(".mb-3");

	// ✅ Locator for toast container (used for success/failure notifications)
	By toastMessage = By.id("toast-container");

	// ✅ Loader spinner that appears when "Add to Cart" is clicked
	By spinner = By.cssSelector(".ng-animating");

	/**
	 * Waits until product list appears, then returns all visible product elements.
	 *
	 * @return List of WebElements representing individual products
	 */
	public List<WebElement> getproductList() {
		waitForElementToAppear(productsList);
		return products;
	}

	/**
	 * Retrieves a specific product element by matching product name.
	 *
	 * @param productName Name of the product to search for
	 * @return WebElement of the matching product, or null if not found
	 */
	public WebElement getProductByName(String productName) {
		return getproductList().stream()
				.filter(product -> product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst()
				.orElse(null);
	}

	/**
	 * Clicks the "Add to Cart" button for a given product name and waits for
	 * loader.
	 *
	 * @param productName Name of the product to add to cart
	 * @throws InterruptedException for wait fallback handling
	 */
	public void addProductToCart(String productName) throws InterruptedException {
		WebElement prod = getProductByName(productName);
		if (prod != null) {
			prod.findElement(By.cssSelector("button[class='btn w-10 rounded']")).click();
			waitForWebElementToDisappear(spinner);
		}
	}
}
