package browsers;

import static browsers.BrowserFactory.configuredBrowser;

import java.awt.AWTException;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.aem.genericutilities.CommonLogging;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

/**
 * <p>
 * 
 * Single instance of webdriver is created for each feature to be executed
 * screenshots are embedded into the report for each scenario.
 * </p>
 */
public class BrowserConfigurator extends EventFiringWebDriver {
	static Logger logs;
	public static WebDriver sharedDriver;
	private static final WebDriver CURRENT_DRIVER;
	private static final Thread EXISTING_THREAD = new Thread() {
		@Override
		public void run() {
			try {
				//BrowserConfigurator.CURRENT_DRIVER.close();
			} catch (Exception e) {
				logs.error("error message: " + e.getMessage());
			}
		}
	};
	static {
		Runtime.getRuntime().addShutdownHook(BrowserConfigurator.EXISTING_THREAD);
		try {
			CURRENT_DRIVER = configuredBrowser();
		} catch (Throwable throwable) {
			throwable.printStackTrace();
			throw new Error(throwable);
		}
	}

	public static WebDriver getWebdriver() {
		if (BrowserConfigurator.CURRENT_DRIVER != null) {
			BrowserConfigurator.sharedDriver = BrowserConfigurator.CURRENT_DRIVER;
		} else {
			logs.error("webdriver is null");
		}
		return BrowserConfigurator.sharedDriver;
	}

	public BrowserConfigurator() throws Throwable {
		super(BrowserConfigurator.CURRENT_DRIVER);
		logs = CommonLogging.getLogObj(BrowserConfigurator.class);
	}

	@Override
	public void close() {
		if (Thread.currentThread() != BrowserConfigurator.EXISTING_THREAD) {
			throw new UnsupportedOperationException(
					"You shouldn't close this WebDriver");
		}
		super.close();
	}

	@Before
	public void deleteAllCookies() throws IOException, AWTException {
		manage().deleteAllCookies();
	}

	@After
	public void embedScreenshot(final Scenario scenario) throws IOException {
		try {
			byte[] screenshot = getScreenshotAs(OutputType.BYTES);
			scenario.embed(screenshot, "image/png");
		} catch (WebDriverException somePlatformsDontSupportScreenshots) {
			System.err
					.println(somePlatformsDontSupportScreenshots.getMessage());
		}
	}
}
