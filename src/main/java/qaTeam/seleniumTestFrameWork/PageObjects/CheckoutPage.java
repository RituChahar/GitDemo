package qaTeam.seleniumTestFrameWork.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import qaTeam.seleniumTestFrameWork.AbstractComponents.AbstractComponent;

/**
 * Page Object for the Checkout Page. Handles country selection and placing the
 * final order. Inherits common behavior from AbstractComponent.
 */
public class CheckoutPage extends AbstractComponent {

	WebDriver driver;

	/**
	 * Constructor initializes driver and page elements using PageFactory.
	 *
	 * @param driver WebDriver instance for this page.
	 */
	public CheckoutPage(WebDriver driver) {
		super(driver); // Access shared wait/navigation methods
		this.driver = driver;
		PageFactory.initElements(driver, this); // Initialize @FindBy WebElements
	}

	// ✅ Country input field (autocomplete dropdown will appear on typing)
	@FindBy(css = "input[placeholder='Select Country']")
	WebElement country;

	// ✅ Final button to submit and place the order
	@FindBy(css = ".action__submit")
	WebElement submit;

	// ✅ First country option in the dropdown suggestion list
	@FindBy(css = "section[class='ta-results list-group ng-star-inserted'] button span")
	WebElement dropdownList;

	// ✅ Used with explicit wait to detect if suggestions dropdown is visible
	By results = By.cssSelector(".ta-results");

	/**
	 * Selects the specified country from the autocomplete dropdown.
	 *
	 * @param countryName Country to be selected (e.g., "India")
	 */
	public void selectCountry(String countryName) {
		// Type the country name into the input using Actions class
		Actions a = new Actions(driver);
		a.sendKeys(country, countryName).build().perform();

		// Wait for the suggestion list to appear
		waitForElementToAppear(results);

		// Click on the first suggestion
		dropdownList.click();
	}

	/**
	 * Clicks the Place Order button and navigates to the confirmation page.
	 *
	 * @return ConfirmationPage object representing the next page.
	 */
	public ConfirmationPage placeOrder() {
		submit.click();
		return new ConfirmationPage(driver);
	}
}
