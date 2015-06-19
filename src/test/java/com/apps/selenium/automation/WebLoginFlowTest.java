package com.apps.selenium.automation;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

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
 */

public class WebLoginFlowTest {

	WebDriver driver;
	String baseUrl;

	LocalProperties localProperties = null;

	@Before
	/**
	 * Initializing the web driver and 
	 */
	public void initialize() throws IOException {
		
		localProperties = new LocalProperties();
		baseUrl = localProperties.getValue("Base.URL");

		String browser = localProperties.getValue("Web.Browser");
		if ("firefox".equals(browser))
			driver = new FirefoxDriver();
		else if ("chrome".equals(browser)) {
			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
			driver = new ChromeDriver();
		} else if ("ie".equals(browser)) {
			System.setProperty("webdriver.ie.driver", "src/main/resources/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		} else {
			throw new IllegalArgumentException("The Browser Type is Undefined");
		}

		driver.get(baseUrl);
		assertEquals("Web Driver is not in the expected state: ",
				"C-date: Casual Dating - Einfach zum Casual Date.",
				driver.getTitle());
	}

	@After
	/**
	 * 
	 */
	public void close() {
		driver.quit();
	}

	@Test
	/**
	 * 
	 */
	public void LoginWithValidEmailAndValidPassword() {
		String emailFromUserInput = localProperties.getValue("User.Email");
		String passwordFromUserInput = localProperties.getValue("User.Password");

		HomePage homePage = new HomePage(driver);
		homePage.login(emailFromUserInput, passwordFromUserInput);

		String expected = "Start";
		String actual = driver.getTitle();
		assertEquals("Login with valid email and password failed: ", expected,
				actual);
	}

	@Test
	/**
	 * 
	 */
	public void LoginWithValidEmailAndNotValidPassword() {
		String emailFromUserInput = localProperties.getValue("User.Email");
		String passwordFromUserInput = WebFunctions.generateRandomString(15);

		HomePage homePage = new HomePage(driver);
		homePage.login(emailFromUserInput, passwordFromUserInput);

		String expected = "Die von Ihnen verwendete E-Mail Adresse oder Passwort sind nicht gültig!";
		String actual = driver.findElement(
				By.xpath(WebElements.LoginPage_ErrorBox_Xpath)).getText();
		assertEquals(
				"Login with valid email and invalid password didn't fail: ",
				expected, actual);
	}

	@Test
	/**
	 * 
	 */
	public void LoginWithValidEmailAndEmptyPassword() {
		String emailFromUserInput = localProperties.getValue("User.Email");
		String passwordFromUserInput = "";

		HomePage homePage = new HomePage(driver);
		homePage.login(emailFromUserInput, passwordFromUserInput);

		String expected = "Das Passwort muss mindestens 6 Zeichen lang sein!";
		String actual = driver.findElement(
				By.xpath(WebElements.LoginPage_ErrorBox_Xpath)).getText();

		assertEquals("Login with valid email and empty password didn't fail:",
				expected, actual);
	}

	@Test
	/**
	 * 
	 */
	public void LoginWithValidEmailAndShortPassword() {
		String emailFromUserInput = localProperties.getValue("User.Email");
		String passwordFromUserInput = WebFunctions.generateRandomString(5);

		HomePage homePage = new HomePage(driver);
		homePage.login(emailFromUserInput, passwordFromUserInput);

		String expected = "Die von Ihnen verwendete E-Mail Adresse oder Passwort sind nicht gültig!";
		String actual = driver.findElement(
				By.xpath(WebElements.LoginPage_ErrorBox_Xpath)).getText();

		assertEquals(
				"Login with valid email and password less then 6 characters didn't fail:",
				expected, actual);
	}

}
