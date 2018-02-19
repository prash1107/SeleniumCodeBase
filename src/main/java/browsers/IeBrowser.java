package browsers;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.ie.InternetExplorerDriver;

import automation.ConfigurationRead.ConfigurationReader;

public class IeBrowser extends InternetExplorerDriver {
	public static IeBrowser configuredIeBrowser() throws Throwable {
		System.setProperty(
				"webdriver.ie.driver",
				System.getProperty("user.dir")
						+ ConfigurationReader.getValue("WebDriverIeDriverPath"));
		IeBrowser browser = new IeBrowser();
		browser.manage()
				.timeouts()
				.implicitlyWait(
						Long.parseLong(ConfigurationReader
								.getValue("implicit_wait_timout")),
						TimeUnit.SECONDS);
		browser.manage().window().fullscreen();
		return browser;
	}

	private IeBrowser() {
		super();
	}
}