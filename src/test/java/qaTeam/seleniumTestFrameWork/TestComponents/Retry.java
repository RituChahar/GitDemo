package qaTeam.seleniumTestFrameWork.TestComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * This class implements TestNG's IRetryAnalyzer interface. It allows failed
 * tests to be re-executed automatically based on retry logic.
 */
public class Retry implements IRetryAnalyzer {

	// Track the current retry count
	int count = 0;

	// Set the maximum number of retry attempts (1 retry means test runs max 2
	// times)
	int maxTry = 1;

	/**
	 * This method is invoked whenever a test fails. If it returns true, TestNG will
	 * retry the test.
	 */
	@Override
	public boolean retry(ITestResult result) {
		// If retry count is less than max, allow retry
		if (count < maxTry) {
			count++;
			return true; // Retry the test
		}
		return false; // Don't retry
	}
}
