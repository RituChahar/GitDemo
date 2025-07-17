package qaTeam.seleniumTestFrameWork.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

/**
 * Utility class to configure and return an instance of ExtentReports. This
 * helps in generating rich HTML test reports.
 */
public class ExtentReporterNG {

	/**
	 * This method sets up and returns an ExtentReports object configured with
	 * report file path, report name, and system info.
	 *
	 * @return ExtentReports - configured instance to log test results
	 */
	public static ExtentReports getReportObject() {

		// Path where the report will be saved (inside /reports/index.html)
		String path = System.getProperty("user.dir") + "\\reports\\index.html";

		// Create ExtentSparkReporter object with the above path
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);

		// Customize the report with name and title
		reporter.config().setReportName("Web Automation Results");
		reporter.config().setDocumentTitle("Test Results");

		// Create the main ExtentReports object and attach the reporter
		ExtentReports extent = new ExtentReports();
		extent.attachReporter(reporter);

		// Add system info (appears in the report)
		extent.setSystemInfo("Tester", "Ritu");

		// Return the configured ExtentReports object
		return extent;
	}
}
