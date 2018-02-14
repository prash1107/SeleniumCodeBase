package browsers;

import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


/**
 * @author shwetha kulkarni 
 * parameters : It is a zero parameterized constructor
 * Description: This class is to select required browser 
 * <p>
 * Single instance of  web driver is created for each feature to be executed
 *  screenshots are embedded into the report for each scenario.
 * </p>
 * @throws IOException 
 * 
 */
public class BrowserFactory {
	static String grid_url = "http://10.193.122.188:4444/wd/hub";

	public static WebDriver configuredBrowser() throws Throwable {
		String desiredBrowser = System.getProperty("browser", "");
		WebDriver selectedDriver = null;
		switch (desiredBrowser) {
		case "ie":
			selectedDriver = IeBrowser.configuredIeBrowser();
			break;
		case "chrome":
			selectedDriver = ChromeBrowser.configuredChromeBrowser();
			break;
		case "firefox":
			selectedDriver = FirefoxBrowser.configuredFirefoxBrowser();
			break;
		default:
			DesiredCapabilities capability = DesiredCapabilities.firefox();
			capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			selectedDriver = new RemoteWebDriver(new URL(grid_url), capability);
			break;
		}
		return selectedDriver;
	}
}
