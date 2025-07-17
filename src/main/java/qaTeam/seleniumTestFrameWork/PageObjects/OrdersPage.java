package qaTeam.seleniumTestFrameWork.PageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import qaTeam.seleniumTestFrameWork.AbstractComponents.AbstractComponent;

/**
 * Page Object representing the Orders Page in the application. Provides methods
 * to validate orders by order number or product name.
 */
public class OrdersPage extends AbstractComponent {

	WebDriver driver;

	// ✅ List of all order number cells in the table (1st column)
	@FindBy(xpath = "th[scope='row']")
	List<WebElement> ordersNo;

	// ✅ List of all product names in the table (2nd column)
	@FindBy(xpath = "//tbody/tr/td[2]")
	List<WebElement> ordersName;

	/**
	 * Constructor to initialize WebDriver and elements.
	 *
	 * @param driver WebDriver instance passed from test class.
	 */
	public OrdersPage(WebDriver driver) {
		super(driver); // Access shared wait/click methods
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	/**
	 * Verifies if the given order number is present in the order table.
	 *
	 * @param orderId Order number to search for
	 * @return true if order is found, false otherwise
	 */
	public boolean verifyOrderNoDisplay(String orderId) {
		return ordersNo.stream().anyMatch(order -> order.getText().equalsIgnoreCase(orderId));
	}

	/**
	 * Verifies if any ordered product name matches the given name.
	 *
	 * @param productName Product name to verify
	 * @return true if the product name exists in the order table
	 */
	public boolean verifyOrderNameDisplay(String productName) {
		return ordersName.stream().anyMatch(itemName -> itemName.getText().equalsIgnoreCase(productName));
	}

	/**
	 * Returns the name of the most recent (topmost) ordered product.
	 *
	 * @return Product name if available, else null
	 */
	public String getLatestOrderedProductName() {
		if (!ordersName.isEmpty()) {
			return ordersName.get(0).getText(); // First row is considered latest
		}
		return null;
	}
}
