package com.apps.selenium.automation;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.apps.selenium.pageobjects.HomePage;
import com.apps.selenium.util.WebElements;
import com.apps.selenium.util.WebFunctions;
import com.sun.istack.internal.logging.Logger;

/**
 * Tests pertaining to the login on c-date.de
 * 
 * @author oliver
 */

public class WebLoginFlowTest {

	WebDriver driver;
	String baseUrl;

	Logger logger = Logger.getLogger(WebLoginFlowTest.class);

	@Before
	/**
	 * Initializing the web driver and 
	 */
	public void openBrowser() {
		baseUrl = "http://www.c-date.de";
		driver = new FirefoxDriver();
		logger.info("Opened driver session: " + driver.hashCode());
		driver.get(baseUrl);
		logger.info("URL: " + baseUrl);
		assertEquals("Web Driver is not in the expected state:",
				"C-date: Casual Dating - Einfach zum Casual Date.",
				driver.getTitle());
	}

	@After
	/**
	 * 
	 */
	public void closeBrowser() {
		logger.info("Closing driver session: " + driver.hashCode());
		driver.quit();
	}

	@Test
	/**
	 * 
	 */
	public void LoginWithValidEmailAndValidPassword() {
		logger.info("Case: Login with valid email address and valid password.");
		String emailFromUserInput = "orailpublic@gmail.com";
		String passwordFromUserInput = "orailpublic";
		logger.info("Login with [email,password] [" + emailFromUserInput + ","
				+ passwordFromUserInput + "]");

		HomePage homePage = new HomePage(driver);
		homePage.login(emailFromUserInput, passwordFromUserInput);
		
		String expected = "Start";
		String actual = driver.getTitle();
		assertEquals("Login with valid email and password failed: ", expected, actual);
	}

	@Test
	/**
	 * 
	 */
	public void LoginWithValidEmailAndNotValidPassword() {
		logger.info("Case: Login with valid email address and invalid password.");
		String emailFromUserInput = "email@email.com";
		String passwordFromUserInput = "password";
		logger.info("Login with [email,password] [" + emailFromUserInput + ","
				+ passwordFromUserInput + "]");

		HomePage homePage = new HomePage(driver);
		homePage.login(emailFromUserInput, passwordFromUserInput);

		String expected = "Die von Ihnen verwendete E-Mail Adresse oder Passwort sind nicht gültig!";
		String actual = driver.findElement(
				By.xpath(WebElements.LoginPage_ErrorBox_Xpath)).getText();
		assertEquals("Login with valid email and invalid password didn't fail: ", expected, actual);
	}

	@Test
	/**
	 * 
	 */
	public void LoginWithValidEmailAndEmptyPassword() {
		logger.info("Case: Login with valid email address and empty password.");
		String emailFromUserInput = "email@email.com";
		String passwordFromUserInput = "";
		logger.info("Login with [email,password] [" + emailFromUserInput + ","
				+ passwordFromUserInput + "]");

		HomePage homePage = new HomePage(driver);
		homePage.login(emailFromUserInput, passwordFromUserInput);

		String expected = "Das Passwort muss mindestens 6 Zeichen lang sein!";
		String actual = driver.findElement(
				By.xpath(WebElements.LoginPage_ErrorBox_Xpath)).getText();

		assertEquals("Login with valid email and empty password didn't fail:", expected, actual);
	}

	@Test
	/**
	 * 
	 */
	public void LoginWithValidEmailAndShortPassword() {
		logger.info("Case: Login with valid email address and less then 6 characters password.");
		String emailFromUserInput = "email@email.com";
		String passwordFromUserInput = WebFunctions.generateRandomString(5);
		logger.info("Login with [email,password] [" + emailFromUserInput + ","
				+ passwordFromUserInput + "]");

		HomePage homePage = new HomePage(driver);
		homePage.login(emailFromUserInput, passwordFromUserInput);

		String expected = "Die von Ihnen verwendete E-Mail Adresse oder Passwort sind nicht gültig!";
		String actual = driver.findElement(
				By.xpath(WebElements.LoginPage_ErrorBox_Xpath)).getText();

		assertEquals("Login with valid email and password less then 6 characters didn't fail:", expected, actual);
	}

}
