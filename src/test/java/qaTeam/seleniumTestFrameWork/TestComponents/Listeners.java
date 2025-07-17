package qaTeam.seleniumTestFrameWork.TestComponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import qaTeam.seleniumTestFrameWork.resources.ExtentReporterNG;

/**
 * This class implements TestNG's ITestListener interface to listen to test
 * execution events. It integrates with ExtentReports for logging and reporting.
 */
public class Listeners extends BaseTest implements ITestListener {

	// Extent report object from reusable method
	ExtentReports extent = ExtentReporterNG.getReportObject();

	// Thread-safe object to handle parallel test logging
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();

	ExtentTest test;

	/**
	 * Invoked when a test starts. Creates a new ExtentTest entry for the test.
	 */
	@Override
	public void onTestStart(ITestResult result) {
		test = extent.createTest(result.getMethod().getMethodName()); // Create test node in report
		extentTest.set(test); // Assign test to current thread
	}

	/**
	 * Invoked when a test passes. Marks it as passed in the report.
	 */
	@Override
	public void onTestSuccess(ITestResult result) {
		extentTest.get().log(Status.PASS, "Test Passed");
	}

	/**
	 * Invoked when a test fails. Logs the failure, captures a screenshot, and
	 * attaches it to the report.
	 */
	@Override
	public void onTestFailure(ITestResult result) {
		extentTest.get().fail(result.getThrowable()); // Log the exception or error in report

		// Fetch WebDriver instance from test class using reflection
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}

		// Capture screenshot on failure and attach to the report
		String filePath = null;
		try {
			filePath = getScreenshot(result.getMethod().getMethodName(), driver);
			extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Invoked when a test is skipped.
	 */
	@Override
	public void onTestSkipped(ITestResult result) {
		extentTest.get().log(Status.SKIP, "Test Skipped");
	}

	/**
	 * Invoked when a test fails but is within success percentage.
	 */
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// Not commonly used, but can be overridden if needed
	}

	/**
	 * Invoked when a test fails due to a timeout.
	 */
	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		onTestFailure(result); // Treat as regular failure
	}

	/**
	 * Invoked before the test suite starts.
	 */
	@Override
	public void onStart(ITestContext context) {
		// Can log test context details if needed
	}

	/**
	 * Invoked after all tests are executed. Flushes the ExtentReport to write
	 * results to disk.
	 */
	@Override
	public void onFinish(ITestContext context) {
		extent.flush(); // Finalize and write report
	}
}
