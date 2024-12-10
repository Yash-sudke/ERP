package com.qa.testcases;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.qa.base.TestBase;
import com.qa.links.Safran;
import com.qa.util.Extendsreports;

public class Safrantest extends TestBase {

	Safran safran;
	ExtentTest test;

	public Safrantest() {
		super();
	}

	@BeforeMethod
	public void setUp() {
		initialization("url1");
		Extendsreports.setupExtentReports();
		safran = new Safran();
	}

	@Test(priority = 1)
	public void loginPageTitleTest() {
		test = Extendsreports.startTest("Login Page Title Test");
		String title = safran.validateLoginPageTitle();
		Assert.assertEquals(title, data.getProperty("Safrantitle"));
		Extendsreports.logStatus("pass", "Title matched successfully");
	}

	@Test(priority = 2)
	public void LogoImageTest() {
		test = Extendsreports.startTest("Login Page Logo Test");
		boolean flag = safran.validateLogo();
		Assert.assertTrue(flag);
		Extendsreports.logStatus("pass", "Logo visible successfully");
	}

	@Test(priority = 3)
	public void loginTest() {
		test = Extendsreports.startTest("Login Page Login Test");
		safran.login(prop.getProperty("safusername"), prop.getProperty("safpassword"));
		Extendsreports.logStatus("pass", "Login successfully");
	}

	@Test(priority = 4)
	public void verifyLoginsucess() {
		test = Extendsreports.startTest("Login Page Sucessfully Login and land on Home Page Test");
		loginTest();
		boolean flag = safran.verifyLoginsucess();
		Assert.assertTrue(flag);
		Extendsreports.logStatus("pass", "Land on home page successfully");
	}

	@AfterMethod
	public void tearDown(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			Extendsreports.logStatus("fail", result.getThrowable().getMessage());
			Extendsreports.addScreenshot(driver, result.getMethod().getMethodName());
		}
		Extendsreports.endTest();
		if (driver != null) {
			driver.quit();
		}

	}

	@AfterSuite(alwaysRun = true)
	public void sendReportAfterSuite() {
		System.out.println("Sending the report via email...");
		Extendsreports.sendReportViaEmail();
	}

}
