package com.apps.selenium.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.apps.selenium.util.WebElements;

/**
 * 
 * @author oliver
 *
 */

public class QuizStart {

	final WebDriver driver;
	
	public QuizStart(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(xpath=WebElements.QUIZSTART_LOGIN_FB_BTN_XPATH)
	private WebElement loginFBButton;
	
	public FBLogin clickLogin() {
		loginFBButton.click();
		return PageFactory.initElements(driver, FBLogin.class);
	}

}
