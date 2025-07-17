package qaTeam.seleniumTestFrameWork.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import qaTeam.seleniumTestFrameWork.AbstractComponents.AbstractComponent;

/**
 * Page Object representing the Login (Landing) Page of the application. Extends
 * AbstractComponent to reuse common utility methods like wait handling.
 */
public class LandingPage extends AbstractComponent {

	WebDriver driver;

	/**
	 * Constructor initializes WebDriver and PageFactory elements.
	 *
	 * @param driver WebDriver instance passed from test classes.
	 */
	public LandingPage(WebDriver driver) {
		super(driver); // Access shared utility methods
		this.driver = driver;
		PageFactory.initElements(driver, this); // Initialize @FindBy elements
	}

	// ✅ Email input field
	@FindBy(id = "userEmail")
	WebElement userEmail;

	// ✅ Password input field
	@FindBy(id = "userPassword")
	WebElement userPassword;

	// ✅ Login button
	@FindBy(id = "login")
	WebElement loginButton;

	// ✅ Error message toast (shown on failed login)
	@FindBy(css = "[class*='flyInOut']")
	WebElement errorMessage;

	/**
	 * Navigates to the application's landing (login) page.
	 */
	public void goTo() {
		driver.get("https://rahulshettyacademy.com/client/");
	}

	/**
	 * Logs in to the application using the provided email and password.
	 *
	 * @param email    Email address of the user
	 * @param password Password of the user
	 * @return 
	 */
	public ProductCatalogue loginApplication(String email, String password) {
		userEmail.sendKeys(email);
		userPassword.sendKeys(password);
		loginButton.click();
		return new ProductCatalogue(driver);
	}

	/**
	 * Waits for and returns the error message text shown on failed login.
	 *
	 * @return String error message content
	 */
	public String getErrorMessage() {
		waitForWebElementToAppear(errorMessage);
		return errorMessage.getText();
	}
}
