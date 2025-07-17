package qaTeam.seleniumTestFrameWork.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import qaTeam.seleniumTestFrameWork.AbstractComponents.AbstractComponent;

/**
 * Page Object representing the final confirmation page after an order is
 * placed. Inherits shared utilities from AbstractComponent.
 */
public class ConfirmationPage extends AbstractComponent {

	WebDriver driver;

	/**
	 * Constructor initializes WebDriver and web elements for this page.
	 *
	 * @param driver WebDriver instance for browser interaction.
	 */
	public ConfirmationPage(WebDriver driver) {
		super(driver); // Inherit wait and navigation utilities
		this.driver = driver;
		PageFactory.initElements(driver, this); // Initialize WebElements
	}

	// ✅ Optional: Page heading (e.g., "Thank You" or "Order Confirmed")
	@FindBy(tagName = "h1")
	WebElement heading;

	// ✅ Confirmation message after successful order (e.g., "THANKYOU FOR THE
	// ORDER.")
	@FindBy(css = ".hero-primary")
	WebElement confirmationMessage;

	/**
	 * Returns the confirmation message displayed on the page.
	 *
	 * @return String message confirming successful order.
	 */
	public String getConfirmationMessage() {
		return confirmationMessage.getText();
	}
}
