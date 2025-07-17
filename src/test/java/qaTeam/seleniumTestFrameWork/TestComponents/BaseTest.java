package qaTeam.seleniumTestFrameWork.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import qaTeam.seleniumTestFrameWork.PageObjects.LandingPage;

/**
 * BaseTest class provides reusable setup and utility methods for all test
 * classes. It handles WebDriver initialization, reading test data from JSON,
 * and capturing screenshots for reports.
 */
public class BaseTest {

	public WebDriver driver; // Global WebDriver instance
	public LandingPage landingPage; // Reference to landing (login) page

	// Common test data (used across multiple test cases)
	public String productName = "ZARA COAT 3";
	public String number = "4111111111111111";
	public String inputDate = "04";
	public int inputYearIndex = 26;
	public String cv = "123";
	public String name = "Test";

	/**
	 * Initializes WebDriver based on the browser name provided in the
	 * GlobalData.properties file.
	 *
	 * @return WebDriver instance
	 * @throws IOException if reading the properties file fails
	 */
	public WebDriver initializeDriver() throws IOException {
		Properties prop = new Properties();

		// Load configuration from properties file
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
				+ "\\src\\test\\java\\qaTeam\\seleniumTestFrameWork\\resources\\GlobalData.properties");
		prop.load(fis);

		String browserName = System.getProperty("browser")!=null ? System.getProperty("browser") : prop.getProperty("browser");
//		String browserName = prop.getProperty("browser");

		// Preprocess browserName to determine base browser and headless flag
		boolean isHeadless = browserName.toLowerCase().contains("headless");
		String baseBrowser = "";

		if (browserName.toLowerCase().contains("chrome")) {
			baseBrowser = "chrome";
		} else if (browserName.toLowerCase().contains("firefox")) {
			baseBrowser = "firefox";
		} else if (browserName.toLowerCase().contains("edge")) {
			baseBrowser = "edge";
		} else if (browserName.toLowerCase().contains("safari")) {
			baseBrowser = "safari";
		} else if (browserName.toLowerCase().contains("opera")) {
			baseBrowser = "opera";
		}

		// Launch browser based on the cleaned-up browser name
		switch (baseBrowser) {
			case "chrome":
				WebDriverManager.chromedriver().setup();
				ChromeOptions chromeOptions = new ChromeOptions();
				if (isHeadless) chromeOptions.addArguments("--headless=new"); // modern headless
				driver = new ChromeDriver(chromeOptions);
				driver.manage().window().setSize(new Dimension(1440,900)); //to run in full screen
				break;

			case "firefox":
				WebDriverManager.firefoxdriver().setup();
				FirefoxOptions firefoxOptions = new FirefoxOptions();
				if (isHeadless) firefoxOptions.addArguments("-headless");
				driver = new FirefoxDriver(firefoxOptions);
				driver.manage().window().setSize(new Dimension(1440,900));
				break;

			case "edge":
				WebDriverManager.edgedriver().setup();
				EdgeOptions edgeOptions = new EdgeOptions();
				if (isHeadless) edgeOptions.addArguments("headless");
				driver = new EdgeDriver(edgeOptions);
				driver.manage().window().setSize(new Dimension(1440,900));
				break;

			case "safari":
				driver = new SafariDriver(); // Headless not supported officially for Safari
				System.out.println("SafariDriver setup required separately for macOS.");
				break;

			case "opera":
				System.out.println("OperaDriver setup not configured.");
				break;

			default:
				throw new RuntimeException("Browser name not recognized: " + browserName);
		}

		// Common driver settings
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.manage().window().maximize();

		return driver;
	}

	/**
	 * Converts a JSON file into a list of HashMaps for data-driven testing.
	 *
	 * @param filePath - full path to the JSON file
	 * @return List of key-value pairs from JSON
	 * @throws IOException if file reading fails
	 */
	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
		String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);

		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent,
				new TypeReference<List<HashMap<String, String>>>() {
				});

		return data;
	}

	/**
	 * Captures a screenshot of the current browser screen and saves it under
	 * /reports.
	 *
	 * @param testCaseName - the name to be used in screenshot file
	 * @param driver       - the WebDriver instance
	 * @return full path to the saved screenshot
	 * @throws IOException if screenshot saving fails
	 */
	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File destination = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
		FileUtils.copyFile(source, destination);
		return destination.getAbsolutePath();
	}

	/**
	 * This method runs before every @Test method. It launches the browser and
	 * navigates to the landing page.
	 *
	 * @return LandingPage object for login actions
	 * @throws IOException if driver initialization fails
	 */
	@BeforeMethod(alwaysRun = true)
	public LandingPage launchApplication() throws IOException {
		driver = initializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.goTo();
		return landingPage;
	}

	/**
	 * This method runs after every @Test method. It closes the browser.
	 */
	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		driver.close();
	}
}
