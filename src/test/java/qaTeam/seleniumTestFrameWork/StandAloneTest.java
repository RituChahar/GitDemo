package qaTeam.seleniumTestFrameWork;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

/**
 * This standalone script simulates a complete flow on
 * https://rahulshettyacademy.com/client/ including: Login, Product Selection,
 * Cart Validation, Checkout, and Order Confirmation.
 */
public class StandAloneTest {

	public static void main(String[] args) {

		// Setup ChromeDriver
		System.setProperty("WebDriver.Chrome.driver", "C:/Eclipse/chrome driver/chromedriver-win64/chromedriver.exe");
		WebDriver driver = new ChromeDriver();

		// Basic browser settings
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Step 1: Open application and login
		driver.get("https://rahulshettyacademy.com/client/");
		driver.findElement(By.id("userEmail")).sendKeys("testid1demo@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Test@123");
		driver.findElement(By.id("login")).click();

		// Step 2: Wait for login toast & validate login success
		WebElement toast1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toast-container")));
		String toastText1 = toast1.getText();
		System.out.println("Toast message: " + toastText1);
		Assert.assertEquals(toastText1, "Login Successfully");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("toast-container")));

		// Step 3: Define products to be added
		String[] inputProds = { "ZARA COAT 3", "ADIDAS ORIGINAL" };

		// Step 4: Loop to add each product to cart
		for (String inputProd : inputProds) {

			List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
			WebElement prod = products.stream()
					.filter(product -> product.findElement(By.cssSelector("b")).getText().equals(inputProd)).findFirst()
					.orElse(null);

			if (prod != null) {
				// Click 'Add to Cart'
				prod.findElement(By.cssSelector("button[class='btn w-10 rounded']")).click();
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));

				// Toast confirmation
				WebElement toast2 = wait
						.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
				String toastText2 = toast2.getText();
				System.out.println("Toast message: " + toastText2);
			} else {
				System.out.println("Product not found: " + inputProd);
			}
		}

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#toast-container")));

		// Step 5: Validate cart item count
		String cartno = driver.findElement(By.xpath("//li/button/label")).getText();
		System.out.println("No. of items in cart = " + cartno);

		// Step 6: Go to cart page
		driver.findElement(By.xpath("//button[@routerlink='/dashboard/cart']")).click();

		// Step 7: Validate that expected items exist in cart
		List<WebElement> cartItems = driver.findElements(By.cssSelector(".cart h3"));
		for (String cartProd : inputProds) {
			boolean foundInCart = cartItems.stream()
					.anyMatch(cartItem -> cartItem.getText().equalsIgnoreCase(cartProd));
			Assert.assertTrue(foundInCart, cartProd + " not found in cart!");
		}

		// Step 8: Click on checkout
		driver.findElement(By.xpath("//div/ul/li/button")).click();

		// Step 9: Fill in payment details
		WebElement cardno = driver.findElement(By.xpath("//input[@value='4542 9931 9292 2293']"));
		cardno.clear();
		cardno.sendKeys("4111111111111111");

		// Select month and year
		Select date = new Select(driver.findElement(By.xpath("(//select[@class='input ddl'])[1]")));
		date.selectByVisibleText("04");

		Select year = new Select(driver.findElement(By.xpath("(//select[@class='input ddl'])[2]")));
		year.selectByIndex(26);

		// Fill CVV and Card Name
		driver.findElement(By.xpath("(//input[@class='input txt'])[1]")).sendKeys("123");
		driver.findElement(By.xpath("(//input[@class='input txt'])[2]")).sendKeys("Test");

		// Step 10: Enter Country
		driver.findElement(By.cssSelector("input[placeholder='Select Country']")).sendKeys("ind");

		List<WebElement> list = driver
				.findElements(By.cssSelector("section[class='ta-results list-group ng-star-inserted'] button span"));

		for (WebElement option : list) {
			if (option.getText().equalsIgnoreCase("India")) {
				option.click();
				break;
			}
		}

		// Step 11: Place Order
		driver.findElement(By.cssSelector(".action__submit")).click();

		// Step 12: Order confirmation toast
		WebElement toast3 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toast-container")));
		String toastText3 = toast3.getText();
		System.out.println("Toast message: " + toastText3);

		// Step 13: Final validations
		String title = driver.getTitle();
		Assert.assertEquals(title, "Let's Shop");
		System.out.println("Page Title: " + title);

		String heading = driver.findElement(By.tagName("h1")).getText();
		Assert.assertTrue(heading.equalsIgnoreCase("Thankyou for the order."));
		System.out.println("Confirmation Heading: " + heading);
	}
}
