package com.qa.util;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.qa.base.TestBase;


public class Extendsreports {

	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> testReport = new ThreadLocal<>();
	private static String reportPath;

	public static ExtentReports setupExtentReports() {
		if (extent == null) {
			File reportDir = new File(System.getProperty("user.dir") + "/test-output/extent-reports/");
			if (!reportDir.exists()) {
				reportDir.mkdirs();
			}
			reportPath = reportDir + "/TestReport_" + getTimeStamp() + ".html";

			ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
			extent = new ExtentReports();
			extent.attachReporter(sparkReporter);
			extent.setSystemInfo("OS", System.getProperty("os.name"));
			extent.setSystemInfo("Environment", "Test Automation");
		}
		return extent;
	}

	public static ExtentTest startTest(String testName) {
		ExtentTest test = extent.createTest(testName);
		testReport.set(test);
		return test;
	}

	public static void logStatus(String status, String message) {
		ExtentTest test = testReport.get();
		if (test != null) {
			switch (status.toLowerCase()) {
			case "pass":
				test.pass(message);
				break;
			case "fail":
				test.fail(message);
				break;
			case "skip":
				test.skip(message);
				break;
			default:
				test.info(message);
			}
		}
	}

	public static void addScreenshot(WebDriver driver, String screenshotName) {
		String path = TestBase.captureScreenshot(driver, screenshotName);
		testReport.get().addScreenCaptureFromPath(path);
	}

	public static void sendReportViaEmail() {
		try {
			if (reportPath == null) {
				System.out.println("Report path is not set. Generate a report before sending.");
				return;
			}
			
			String recipientEmail = "pradeep.chandra@bitkemy.com"; // Replace with recipient email
			String subject = "Test Automation Report";
			String body = "Hi Team,\n\nPlease find the attached test automation report.\n\nThanks,\nAutomation Team";

			File reportFile = new File(reportPath);
			if (reportFile.exists()) {
				EmailUtils.sendEmailWithAttachment(recipientEmail, subject, body, reportPath);
				System.out.println("Report email sent successfully.");
			} else {
				System.out.println("Report file not found: " + reportPath);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to send report via email.");
		}
	}

	public static void endTest() {
		if (extent != null) {
			extent.flush();
		}
	}

	private static String getTimeStamp() {
		return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	}
}
