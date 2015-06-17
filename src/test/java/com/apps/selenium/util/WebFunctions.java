package com.apps.selenium.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebElement;

/**
 * 
 * @author oliver
 *
 */
public class WebFunctions {

	/**
	 * 
	 * @param textField
	 * @param textToEnter
	 */
	public static void enterTextInHTMLTextField(WebElement textField,
			CharSequence textToEnter) {
		textField.clear();
		textField.sendKeys(textToEnter);
	}

	/**
	 * 
	 * @param length
	 * @return
	 */
	public static String generateRandomString(int length) {
		boolean useLetters = true;
		boolean useNumbers = false;
		String generatedString = RandomStringUtils.random(length, useLetters,
				useNumbers);
		return generatedString;
	}

}
