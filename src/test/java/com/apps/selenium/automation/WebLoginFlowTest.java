package com.apps.selenium.automation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.apps.selenium.pageobjects.HomePage;
import com.apps.selenium.util.LocalProperties;
import com.apps.selenium.util.WebElements;
import com.apps.selenium.util.WebFunctions;

/**
 * Tests pertaining to the login on c-date.de
 * 
 * @author oliver
 * 
 *         The test data is configured in the local.properties file Parameters:
 *         Web.Browser = browser type where the tests will be executed (firefox,
 *         chrome, ie) Base.URL = the home page where the login form is located
 *         User.Email = the tested email address (must be valid) User.Email =
 *         the tested password (must be valid for the email address above)
 * 
 */

public class WebLoginFlowTest {

	static WebDriver driver;
	String baseUrl;

	LocalProperties localProperties = null;
	static final Logger LOGGER = LogManager.getLogger(WebLoginFlowTest.class
			.getName());

	@Before
	/**
	 * Initializing the web driver and accessing the
	 * c-date.de home page.
	 */
	public void initialize() throws IOException {
		LOGGER.info("-");
		LOGGER.info("start execution, a new browser will start");

		localProperties = new LocalProperties();
		baseUrl = localProperties.getValue("Base.URL");
		LOGGER.info("read the url from local properties: " + baseUrl);

		String browserType = localProperties.getValue("Web.Browser");
		LOGGER.info("read the browser type from local properties: "
				+ browserType);

		LOGGER.info("new webdriver session");
		if ("firefox".equals(browserType))
			driver = new FirefoxDriver();
		else if ("chrome".equals(browserType)) {
			System.setProperty("webdriver.chrome.driver",
					"src/main/resources/chromedriver");
			driver = new ChromeDriver();
		} else if ("ie".equals(browserType)) {
			System.setProperty("webdriver.ie.driver",
					"src/main/resources/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		} else {
			throw new IllegalArgumentException("The Browser Type is Undefined");
		}
		LOGGER.info("browser session:" + browserType + " [" + driver.hashCode()
				+ "]");

		assertNotNull("The base URL is not defined.", baseUrl);
		driver.get(baseUrl);
		assertEquals("Web Driver is not in the expected state: ",
				"C-date: Casual Dating - Einfach zum Casual Date.",
				driver.getTitle());
	}

	@After
	/**
	 * Close the driver if initialized before.
	 */
	public void close() {
		if (driver != null)
			driver.quit();
		LOGGER.info("browser closed");
	}

	@Test
	/**
	 * Test objective: User logs in with valid user name and valid password.
	 * The start page is expected to show and have title='Start'.
	 */
	public void LoginWithValidEmailAndValidPassword() {
		String emailFromUserInput = localProperties.getValue("User.Email");
		String passwordFromUserInput = localProperties
				.getValue("User.Password");

		LOGGER.info("case: user logs in with valid user name and valid password.");
		LOGGER.info("email address from configuration file: "
				+ emailFromUserInput);
		LOGGER.info("password from configuration file: "
				+ passwordFromUserInput);

		HomePage homePage = new HomePage(driver);
		homePage.login(emailFromUserInput, passwordFromUserInput);

		String expected = "Start";
		String actual = driver.getTitle();

		LOGGER.info("expect: " + expected);
		LOGGER.info("actual: " + actual);

		assertEquals(
				"Login with valid email and password failed. The page title",
				expected, actual);

		LOGGER.info("test passed");
	}

	@Test
	/**
	 * Test objective: User gets an error message when trying to
	 * login with the correct user name and a wrong password.
	 * The password is a random generated string of 15 characters.
	 */
	public void LoginWithValidEmailAndNotValidPassword() {
		String emailFromUserInput = localProperties.getValue("User.Email");
		String passwordFromUserInput = WebFunctions.generateRandomString(15);

		LOGGER.info("case: user logs in with valid user name and invalid password.");
		LOGGER.info("email address from configuration file: "
				+ emailFromUserInput);
		LOGGER.info("password generated: " + passwordFromUserInput);

		HomePage homePage = new HomePage(driver);
		homePage.login(emailFromUserInput, passwordFromUserInput);

		String expected = "Die von Ihnen verwendete E-Mail Adresse oder Passwort sind nicht gültig!";
		String actual = driver.findElement(
				By.xpath(WebElements.LoginPage_ErrorBox_Xpath)).getText();

		LOGGER.info("expect: " + expected);
		LOGGER.info("actual: " + actual);

		assertEquals(
				"Login with valid email and invalid password didn't fail. The error text displayed",
				expected, actual);

		LOGGER.info("test passed");
	}

	@Test
	/**
	 * Test objective: User gets an error message when tries
	 * to login with a valid user name and an empty password.
	 */
	public void LoginWithValidEmailAndEmptyPassword() {
		String emailFromUserInput = localProperties.getValue("User.Email");
		String passwordFromUserInput = "";

		LOGGER.info("case: user logs in with valid user name and empty password.");
		LOGGER.info("email address from configuration file: "
				+ emailFromUserInput);
		LOGGER.info("password: " + passwordFromUserInput);

		HomePage homePage = new HomePage(driver);
		homePage.login(emailFromUserInput, passwordFromUserInput);

		String expected = "Das Passwort muss mindestens 6 Zeichen lang sein!";
		String actual = driver.findElement(
				By.xpath(WebElements.LoginPage_ErrorBox_Xpath)).getText();

		LOGGER.info("expect: " + expected);
		LOGGER.info("actual: " + actual);

		assertEquals(
				"Login with valid email and empty password didn't fail. The error text displayed",
				expected, actual);

		LOGGER.info("test passed");
	}

	@Test
	/**
	 * Test objective: User gets and error message when tries to
	 * login with a valid user name and a less then 6 characters password.
	 * The password is a generated string of 5 random characters.
	 */
	public void LoginWithValidEmailAndShortPassword() {
		String emailFromUserInput = localProperties.getValue("User.Email");
		String passwordFromUserInput = WebFunctions.generateRandomString(5);

		LOGGER.info("case: user logs in with valid user name and a short password (less then 6 characters).");
		LOGGER.info("email address from configuration file: "
				+ emailFromUserInput);
		LOGGER.info("password: " + passwordFromUserInput);

		HomePage homePage = new HomePage(driver);
		homePage.login(emailFromUserInput, passwordFromUserInput);

		String expected = "Die von Ihnen verwendete E-Mail Adresse oder Passwort sind nicht gültig!";
		String actual = driver.findElement(
				By.xpath(WebElements.LoginPage_ErrorBox_Xpath)).getText();

		LOGGER.info("expect: " + expected);
		LOGGER.info("actual: " + actual);

		assertEquals(
				"Login with valid email and password less then 6 characters didn't fail. The error text displayed",
				expected, actual);

		LOGGER.info("test passed");
	}

}
