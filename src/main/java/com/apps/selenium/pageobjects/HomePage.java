package com.apps.selenium.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.apps.selenium.util.WebElements;
import com.apps.selenium.util.WebFunctions;

/**
 * 
 * @author oliver
 *
 */

public class HomePage {

	private WebDriver driver;

	/**
	 * 
	 * @param driver
	 */
	public HomePage(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * 
	 * @return
	 */
	private WebElement loginBox() {
		return driver.findElement(By.id("loginBoxSmallCenter"));
	}

	/**
	 * 
	 * @return
	 */
	private WebElement emailTxt() {
		return driver.findElement(By.id(WebElements.HomePage_Email_ID));
	}

	/**
	 * 
	 * @return
	 */
	private WebElement passwordTxt() {
		return driver.findElement(By.id(WebElements.HomePage_Password_ID));
	}

	/**
	 * 
	 * @return
	 */
	private WebElement loginBtn() {
		return driver.findElement(By.id(WebElements.HomePage_LoginBtn_ID));
	}

	/**
	 * 
	 */
	private void showLoginBox() {
		loginBox().click();
	}

	/**
	 * 
	 * @param emailInput
	 * @param passwordInput
	 */
	public void login(String emailInput, String passwordInput) {
		showLoginBox();
		WebFunctions.enterTextInHTMLTextField(emailTxt(), emailInput);
		WebFunctions.enterTextInHTMLTextField(passwordTxt(), passwordInput);
		loginBtn().click();
	}

}
