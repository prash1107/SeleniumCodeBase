package browsers;

import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BrowserFactory {
	static String grid_url = "http://192.168.0.106:4444/wd/hub";

	public static WebDriver configuredBrowser() throws Throwable {
		String desiredBrowser = System.getProperty("browser", "chrome");
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
