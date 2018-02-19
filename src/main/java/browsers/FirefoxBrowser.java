package browsers;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import automation.ConfigurationRead.ConfigurationReader;

import com.aem.genericutilities.CommonLogging;

public class FirefoxBrowser extends FirefoxDriver {
	static Logger logs = null;

	public static FirefoxBrowser configuredFirefoxBrowser() throws Throwable {
		FirefoxBrowser browser = null;
		FirefoxProfile profile = null;
		logs = CommonLogging.getLogObj(FirefoxBrowser.class);
		if (ConfigurationReader.getValue("selenium_version").equals("3")) {
			logs.info("configured selenium 3 or above...");
			System.setProperty(
					"webdriver.gecko.driver",
					System.getProperty("user.dir")
							+ ConfigurationReader
									.getValue("WebDriverfirefoxDriverPath"));
			profile = new FirefoxProfile();
			profile.setAcceptUntrustedCertificates(true);
			browser = new FirefoxBrowser(profile);
			browser.manage()
					.timeouts()
					.implicitlyWait(
							Long.parseLong(ConfigurationReader
									.getValue("implicit_wait_timout")),
							TimeUnit.SECONDS);
		} else {
			logs.info("configured lower version of selenium below selenium 3...");
			profile = new FirefoxProfile();
			profile.setPreference("browser.startup.page", 0); // Empty start
																// page
			profile.setPreference("browser.startup.homepage_override.mstone",
					"ignore");
			profile.setAcceptUntrustedCertificates(true);
			browser = new FirefoxBrowser(profile);
			browser.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		}
		return browser;
	}

	private FirefoxBrowser(final FirefoxProfile profile) throws Throwable {
		super(profile);
	}
}