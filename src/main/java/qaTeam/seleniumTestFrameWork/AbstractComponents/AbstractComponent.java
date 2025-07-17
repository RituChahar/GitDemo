package qaTeam.seleniumTestFrameWork.AbstractComponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import qaTeam.seleniumTestFrameWork.PageObjects.CartPage;

/**
 * AbstractComponent serves as a base class for all page objects. It contains
 * common utility methods and elements reused across multiple pages, such as
 * waits and navigation to cart or orders.
 */
public class AbstractComponent {

	WebDriver driver;

	/**
	 * Constructor initializes WebDriver and page elements using PageFactory.
	 *
	 * @param driver WebDriver instance passed from the page class.
	 */
	public AbstractComponent(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this); // Initializes @FindBy elements
	}

	// ✅ Web element for Cart button in header (for navigating to cart page)
	@FindBy(xpath = "//button[@routerlink='/dashboard/cart']")
	WebElement cartHeader;

	// ✅ Web element for Orders button in header (for navigating to orders page)
	@FindBy(xpath = "//button[@routerlink='/dashboard/myorders']")
	WebElement orderHeader;

	/**
	 * Explicitly waits for a given locator to become visible on the DOM.
	 *
	 * @param findBy The By locator of the element.
	 */
	public void waitForElementToAppear(By findBy) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}

	/**
	 * Explicitly waits for a given WebElement to become visible.
	 *
	 * @param findBy The WebElement to wait for.
	 */
	public void waitForWebElementToAppear(WebElement findBy) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(findBy));
	}

	/**
	 * Waits until the element located by given locator is no longer visible. Useful
	 * for waiting until loaders or spinners disappear.
	 *
	 * @param findBy The By locator of the element.
	 */
	public void waitForElementToDisappear(By findBy) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(findBy));
	}

	/**
	 * Placeholder method for waiting WebElement to disappear using Thread.sleep.
	 * Can be improved using explicit wait instead of hardcoded sleep.
	 *
	 * @param findBy The WebElement to wait for (currently unused).
	 */
	public void waitForWebElementToDisappear(By findBy) throws InterruptedException {
		Thread.sleep(5000); // ⚠️ Not recommended for production. Replace with explicit wait.
	}

	/**
	 * Navigates to the Cart page by clicking the Cart button in the header.
	 * @return 
	 */
	public CartPage goToCartPage() {
		cartHeader.click();
		 return new CartPage(driver);
	}

	/**
	 * Navigates to the Orders page by clicking the Orders button in the header.
	 */
	public void goToOrdersPage() {
		orderHeader.click();
	}

}
