package qaTeam.seleniumTestFrameWork.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import qaTeam.seleniumTestFrameWork.AbstractComponents.AbstractComponent;

/**
 * Page Object for the Card Details section used during payment at checkout.
 * This class contains methods to interact with card input fields and dropdowns.
 * Inherits common reusable methods from AbstractComponent.
 */
public class CardDetails extends AbstractComponent {

	WebDriver driver;

	/**
	 * Constructor initializes WebDriver and page elements using PageFactory.
	 *
	 * @param driver WebDriver instance used to interact with the browser.
	 */
	public CardDetails(WebDriver driver) {
		super(driver); // Call to AbstractComponent constructor for shared utilities
		this.driver = driver;
		PageFactory.initElements(driver, this); // Initialize all @FindBy fields
	}

	// ✅ Input field for card number (prefilled, needs clearing before entering)
	@FindBy(xpath = "//input[@value='4542 9931 9292 2293']")
	WebElement cardno;

	// ✅ Dropdown for card expiry month
	@FindBy(xpath = "(//select[@class='input ddl'])[1]")
	WebElement d;

	// ✅ Dropdown for card expiry year
	@FindBy(xpath = "(//select[@class='input ddl'])[2]")
	WebElement y;

	// ✅ Input field for CVV
	@FindBy(xpath = "(//input[@class='input txt'])[1]")
	WebElement cvv;

	// ✅ Input field for name on the card
	@FindBy(xpath = "(//input[@class='input txt'])[2]")
	WebElement cardName;

	/**
	 * Fills out the card details during payment.
	 *
	 * @param number         Card number (e.g., "1234 5678 9012 3456")
	 * @param inputDate      Expiry month to be selected from dropdown (e.g., "03")
	 * @param inputYearIndex Index of expiry year in dropdown (0-based)
	 * @param cv             CVV number (e.g., "123")
	 * @param name           Name on the card (e.g., "John Doe")
	 */
	public void fillCardDetails(String number, String inputDate, int inputYearIndex, String cv, String name) {
		cardno.clear(); // Clear default/pre-filled card number
		cardno.sendKeys(number); // Enter provided card number

		// Select expiry month from dropdown by visible text
		Select date = new Select(d);
		date.selectByVisibleText(inputDate);

		// Select expiry year from dropdown by index
		Select year = new Select(y);
		year.selectByIndex(inputYearIndex);

		// Enter CVV and cardholder name
		cvv.sendKeys(cv);
		cardName.sendKeys(name);
		
		
	}
}
