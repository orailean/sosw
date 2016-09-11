package com.apps.selenium.automation;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.apps.selenium.pageobjects.FBLogin;
import com.apps.selenium.pageobjects.QuizResult;
import com.apps.selenium.pageobjects.QuizStart;
import com.apps.selenium.util.LocalProperties;

/**
 * Tests pertaining to the social sweethearts
 * 
 * @author oliver
 * 
 *         The test data is configured in the local.properties file Parameters:
 * 
 *         Base.URL = the home page where the quiz form is located User.Email =
 *         the tested fb email address (must be valid) User.Email = the tested
 *         fb password (must be valid for the email address above)
 * 
 */

public class WebLoginFlow {

	WebDriver driver;
	String baseUrl;

	LocalProperties localProperties = null;
	static final Logger LOGGER = LogManager.getLogger(WebLoginFlow.class.getName());
	private static final long TIMEOUT_QUIZ = 30000; // will not allow to wait
													// more than 30 seconds

	private void initialize(Integer quizID) throws IOException {
		LOGGER.info("-");
		LOGGER.info("start execution, a new browser will start");

		localProperties = new LocalProperties();
		baseUrl = localProperties.getValue("Base.URL");
		LOGGER.info("read the url from local properties: " + baseUrl);

		String browserType = localProperties.getValue("Web.Browser");
		LOGGER.info("read the browser type from local properties: " + browserType);

		LOGGER.info("new webdriver session");
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		options.addArguments("--auth-schemes=digest");
		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(TIMEOUT_QUIZ, TimeUnit.MILLISECONDS);
		LOGGER.info("browser session:" + browserType + " [" + driver.hashCode() + "]");

		Assert.assertNotNull("The base URL is not defined.", baseUrl);
		driver.get(baseUrl + quizID.toString());
	}

	@AfterTest
	/**
	 * Close the driver if initialized before.
	 */
	private void close() {
		if (driver != null)
			driver.quit();
		LOGGER.info("browser closed");
	}

	private String getJSErrors() {
		LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);

		String jserrors = "";
		for (LogEntry entry : logEntries) {
			jserrors += new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage() + "\n";
		}
		return jserrors;
	}

	@DataProvider(name = "quizzes")
	public static Object[][] quizzes() {
		return new Object[][] { { 9164 }, { 8292 }, { 11005 } };
	}

	@Test(dataProvider = "quizzes")
	/**
	 * Test objective: User logs in with valid user name and valid password and
	 * the results show up in 30 seconds.
	 */
	public void LoginWithValidEmailAndValidPassword(Integer quizID) throws IOException {
		initialize(quizID);

		String emailFromUserInput = localProperties.getValue("User.Email");
		String passwordFromUserInput = localProperties.getValue("User.Password");

		LOGGER.info("case: user logs in with valid user name and valid password.");
		LOGGER.info("email address from configuration file: " + emailFromUserInput);
		LOGGER.info("password from configuration file: " + passwordFromUserInput);

		LOGGER.info("access the quiz page");
		QuizStart quizStart = PageFactory.initElements(driver, QuizStart.class);
		FBLogin fBLogin = quizStart.clickLogin();

		LOGGER.info("login into facebook");
		QuizResult quizResult = fBLogin.login(emailFromUserInput, passwordFromUserInput);

		LOGGER.info("check if the results page is displayed");
		Assert.assertTrue(quizResult.resultImage.isDisplayed());

		LOGGER.info("check for javascript errors on the nametests scripts");
		Assert.assertFalse(getJSErrors().contains("nametests"), "The JS console contains errors: " + getJSErrors());

		LOGGER.info("test passed");
		driver.quit();
	}
}
