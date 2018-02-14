package browsers;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import automation.ConfigurationRead.ConfigurationReader;

import com.aem.genericutilities.CommonFunctions;


/**
 * @author shwetha kulkarni 
 * parameters : It is a zero parameterized constructor
 * Description: This class is to initialize chrome browser 
 * <p>
 * Single instance of  chrome web driver is created for each feature to be executed
 *  screenshots are embedded into the report for each scenario.
 * </p>
 * @throws IOException 
 * 
 */
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
			System.setProperty(
					"webdriver.chrome.driver",
					System.getProperty("user.dir")
							+ ConfigurationReader
									.getValue("WebDriverChromeDriverPath"));
			browser = new ChromeBrowser();
			browser.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			browser.manage().window().fullscreen();
			break;
		default:
			ChromeOptions opt = new ChromeOptions();
			opt.addArguments("test-type");
			DesiredCapabilities desired = DesiredCapabilities.chrome();
			desired.setCapability("chrome.binary", System.getProperty("user.dir")+ "\\BrowserDrivers\\ChromeSetup.exe");
			desired.setCapability(ChromeOptions.CAPABILITY, opt);
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+ConfigurationReader.getValue("WebDriverChromeDriverPath"));
			browser = new ChromeBrowser(desired);
			browser.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
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
