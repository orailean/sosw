package com.apps.selenium.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.apps.selenium.util.WebElements;
import com.apps.selenium.util.WebFunctions;

/**
 * 
 * @author oliver
 *
 */

public class FBLogin {

	final WebDriver driver;

	/**
	 * 
	 * @param driver
	 */
	public FBLogin(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(id = WebElements.FBLOGIN_EMAIL_TXT_ID)
	public WebElement email_Txt;
	
	@FindBy(id = WebElements.FBLOGIN_PASSWORD_TXT_ID)
	public WebElement password_Txt;
	
	@FindBy(id = WebElements.FBLOGIN_LOGIN_BTN_ID)
	public WebElement login_Btn;
	
	public QuizResult login(String emailInput, String passwordInput) {
		WebFunctions.enterTextInHTMLTextField(email_Txt, emailInput);
		WebFunctions.enterTextInHTMLTextField(password_Txt, passwordInput);
		login_Btn.click();
		return PageFactory.initElements(driver, QuizResult.class);
	}

}
