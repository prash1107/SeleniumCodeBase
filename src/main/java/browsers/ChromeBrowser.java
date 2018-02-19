package browsers;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import automation.ConfigurationRead.ConfigurationReader;

import com.aem.genericutilities.CommonFunctions;

public class ChromeBrowser extends ChromeDriver {
	public static ChromeBrowser configuredChromeBrowser() throws Throwable {
		String path = null;
		String osname = ConfigurationReader.getValue("OSName");
		ChromeBrowser browser = null;
		switch (osname.toLowerCase()) {
		case ("linux"):
			path = "//usr//bin//chromedriver";
			System.setProperty("webdriver.chrome.driver", path);
			browser = new ChromeBrowser();
			browser.manage()
					.timeouts()
					.implicitlyWait(
							Long.parseLong(ConfigurationReader
									.getValue("implicit_wait_timout")),
							TimeUnit.SECONDS);
			browser.manage().window().fullscreen();
			CommonFunctions.glb_Logger_commonlogs
					.info(" chrome webdriver is initialized for linux...");
			break;
		case ("windows"):
			System.out.println(System.getProperty("user.dir")
					+ ConfigurationReader
							.getValue("WebDriverChromeDriverPath"));
			System.setProperty(
					"webdriver.chrome.driver",
					System.getProperty("user.dir")
							+ ConfigurationReader
									.getValue("WebDriverChromeDriverPath"));
		
			browser = new ChromeBrowser();
			browser.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			//browser.manage().window().fullscreen();
			break;
		default:
			ChromeOptions opt = new ChromeOptions();
			opt.addArguments("test-type");
			DesiredCapabilities desired = DesiredCapabilities.chrome();
			desired.setCapability("chrome.binary",
					System.getProperty("user.dir")
							+ "\\BrowserDrivers\\ChromeSetup.exe");
			desired.setCapability(ChromeOptions.CAPABILITY, opt);
			System.setProperty("webdriver.chrome.driver",
					ConfigurationReader.getValue("WebDriverChromeDriverPath"));
			browser = new ChromeBrowser(desired);
			browser.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			//browser.manage().window().fullscreen();
			CommonFunctions.glb_Logger_commonlogs
					.info("local chrome webdriver is initialized...");
			break;
		}
		return browser;
	}

	private ChromeBrowser() {
		super();
	}

	private ChromeBrowser(DesiredCapabilities desired) {
		super(desired);
	}
}
