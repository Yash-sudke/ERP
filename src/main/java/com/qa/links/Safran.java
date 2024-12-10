package com.qa.links;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.base.TestBase;

public class Safran extends TestBase {

	@FindBy(xpath = "//input[@id='username']")
	WebElement username;

	@FindBy(xpath = "//input[@id='password']")
	WebElement password;

	@FindBy(xpath = "//a[normalize-space()='LOGIN']")
	WebElement loginBtn;

	@FindBy(xpath = "//img[@title='Powered by KagamiERP']")
	WebElement loginimg;

	@FindBy(xpath = "//img[@alt='company_logo']")
	WebElement safranLogo;

	public Safran() {
		PageFactory.initElements(driver, this);
	}

	public String validateLoginPageTitle() {
		return driver.getTitle();
	}

	public boolean validateLogo() {
		return safranLogo.isDisplayed();
	}

	public void login(String un, String pwd) {
		username.sendKeys(un);
		password.sendKeys(pwd);
//			JavascriptExecutor js = (JavascriptExecutor)driver;
//			js.executeScript("arguments[0].click();", loginBtn);
		loginBtn.click();
	}

	public boolean verifyLoginsucess() {
		return loginimg.isDisplayed();
	}

}
