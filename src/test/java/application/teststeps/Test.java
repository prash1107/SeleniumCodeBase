package application.teststeps;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import browsers.BrowserConfigurator;

import com.aem.genericutilities.GrowlNotification;
import com.aem.genericutilities.locatorfactory.LocateByComposition;
import com.applitools.eyes.Eyes;

import cucumber.api.java.en.Given;

public class Test {
	public A aobj;
	public B bobj;
	public D dobj;

	public Test(A a, B b, D d) {
		this.aobj = a;
		this.bobj = b;
		this.dobj = d;
	}

	@Given("^aa$")
	public void growl() throws InterruptedException {
		WebDriver driver = BrowserConfigurator.getWebdriver();
		driver.get("http://localhost:4502/libs/granite/core/content/login.html");
		Thread.sleep(5000);
		GrowlNotification growl = new GrowlNotification();
		growl.growlNotification(driver, "", "loaded page...");
		By trys[] = new By[2];
		trys[0] = By.id("login");
		trys[1] = By.id("username");
		By trys1[] = new By[2];
		trys1[0] = By.id("login");
		trys1[1] = By.id("password");
		By trys2[] = new By[2];
		trys2[0] = By.id("login");
		trys2[1] = By.id("submit-button");
		LocateByComposition.locateElementUsingChain(trys, driver).sendKeys(
				"admin");
		growl.growlNotification(driver, "", "entered username...");
		LocateByComposition.locateElementUsingChain(trys1, driver).sendKeys(
				"admin");
		growl.growlNotification(driver, "", "entered password...");
		LocateByComposition.locateElementUsingChain(trys2, driver).click();
		growl.growlNotification(driver, "success", "logged in...");
		driver.get("http://localhost:4502/libs/cq/core/content/projects/wizard/newproject.html");
		driver.findElement(By.xpath("//h4[1]")).click();
		driver.findElement(By.xpath("html/body/form/nav/button/span")).click();
	}

	@Given("^a$")
	public void a() throws Throwable {
		WebDriver driver = BrowserConfigurator.getWebdriver();
		Eyes eyes = new Eyes(); // This is your api key, make sure you use it in
								// all your tests.
		eyes.setApiKey("rqMXttZ2R106JXrqLp33100AIUO0iwthk9oiyvmi68leS108Q110");
		try {
			// Start the test and set the browser's viewport size to800x600.
			eyes.open(driver, "Hello World!", "My first Selenium Java test!"); // Navigate
																				// the
																				// browser
																				// to
																				// the
			// "hello world!" web-site.
			driver.get("https://applitools.com/helloworld?diff2");
			// Visual checkpoint #1.
			eyes.checkWindow("Hello!");
			// Click the "Click me!"button.
			driver.findElement(By.tagName("button")).click();
			// Visual checkpoint #2.
			eyes.checkWindow("Click!"); // End the test.
			eyes.close();
		} finally { // Close the browser.
			// driver.quit();
		}
		// //
		// //driver.findElement(InputElementBy.usingFollowingSiblingOfLabel("Title"))
		// .sendKeys("test");
		/*
		 * DesiredCapabilities capability=new DesiredCapabilities();
		 * capability.setBrowserName("firefox");
		 * capability.setPlatform(Platform.WIN10); WebDriver driver = new
		 * RemoteWebDriver(new
		 * URL("http://mkarthik-wx-1.corp.adobe.com:4444/wd/hub"),capability);
		 * driver.get("http://www.google.com"); driver.close();
		 */
		// BrowserConfigurator.getWebdriver().get("http://www.google.com");
	}

	private static void prepare(WebDriver driver) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("var jquery = document.createElement('script'); jquery.type = 'text/javascript';jquery.src = 'https://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js';document.getElementsByTagName('head')[0].appendChild(jquery);");
		Thread.sleep(10000);
		if (waitForJStoLoad(driver)) {
			// https://cdnjs.cloudflare.com/ajax/libs/jquery-jgrowl/1.4.5/jquery.jgrowl.js
			// https://cdnjs.cloudflare.com/ajax/libs/jquery-jgrowl/1.2.12/jquery.jgrowl.min.js
			System.out.println("done loading jquery...");
			js.executeScript("var jq= document.createElement('script'); jq.type = 'text/javascript';jq.src = 'https://cdnjs.cloudflare.com/ajax/libs/jquery-jgrowl/1.4.5/jquery.jgrowl.js';document.getElementsByTagName('head')[0].appendChild(jq);");
			Thread.sleep(10000);
		}
		if (waitForJStoLoad(driver)) {
			System.out.println("done loading jGrowl...");
			// https://cdnjs.cloudflare.com/ajax/libs/jquery-jgrowl/1.4.5/jquery.jgrowl.css
			// https://cdnjs.cloudflare.com/ajax/libs/jquery-jgrowl/1.2.12/jquery.jgrowl.min.css
			js.executeScript("var lnk = document.createElement('link'); lnk.rel = 'stylesheet'; lnk.href = 'https://cdnjs.cloudflare.com/ajax/libs/jquery-jgrowl/1.4.5/jquery.jgrowl.css'; lnk.type = 'text/css'; document.getElementsByTagName('head')[0].appendChild(lnk);");
			Thread.sleep(10000);
		}
		if (waitForJStoLoad(driver)) {
			System.out.println("done loading css...");
			Thread.sleep(6000);
			// js.executeScript("document.jQuery.jGrowl('hi', { header: 'Selenium Internal Event' });");
			try {
				((FirefoxDriver) driver).executeScript("$.jGrowl('helloooo');");// $.jGrowl('"hi"',
																				// {
																				// header:
																				// '"Selenium
																				// Internal
																				// Event"'
																				// });
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		System.out.println("govindaaaa....");
	}

	public void growlNotification(WebDriver driver, String msg, String header) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("document.jQuery.jGrowl('" + msg + "', { header: '"
				+ header + "' });");
	}

	public static void waitForAjaxLoad(WebDriver driver)
			throws InterruptedException {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		if ((Boolean) executor
				.executeScript("return window.jQuery != undefined")) {
			while (!(Boolean) executor
					.executeScript("return jQuery.active == 0")) {
				Thread.sleep(1000);
			}
		}
		return;
	}

	public static boolean waitForJStoLoad(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		// wait for jQuery to load
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					long a = (long) ((JavascriptExecutor) driver)
							.executeScript("return jQuery.active");
					return a == 0;
				} catch (Exception e) {
					return true;
				}
			}
		};
		// wait for Javascript to load
		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver)
						.executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		return wait.until(jQueryLoad) && wait.until(jsLoad);
	}
}
