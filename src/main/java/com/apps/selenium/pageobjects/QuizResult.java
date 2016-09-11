package com.apps.selenium.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.apps.selenium.util.WebElements;

/**
 * 
 * @author oliver
 *
 */

public class QuizResult {

	final WebDriver driver;
	
	public QuizResult(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(xpath=WebElements.QUIZRESULT_RESULTS_IMAGE_ID)
	public WebElement resultImage;

}
