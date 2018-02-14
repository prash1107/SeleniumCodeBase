package application.teststeps;

import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class JavaSample {
	public static final String USERNAME = "karthik368";
	public static final String AUTOMATE_KEY = "	cYZyWiu7VhMMete2PbpT";
	public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY
			+ "@hub-cloud.browserstack.com/wd/hub";
	public static WebDriver driver = null;

	// glb_Webdriver_driver = JavaSample.driver; in commonfunctions()
	public static void main(String[] args) throws Throwable {
		DesiredCapabilities caps = new DesiredCapabilities();
		/*
		 * caps.setCapability("browser", "Firefox");
		 * caps.setCapability("browser_version", "47"); caps.setCapability("os",
		 * "Windows"); caps.setCapability("browserstack.local", "false");
		 * caps.setCapability("os_version", "XP");
		 */
		caps.setCapability("browser", "iPhone");
		caps.setCapability("device", "iPhone 6S Plus");
		caps.setCapability("os", "ios");
		caps.setCapability("browserstack.debug", "true");
		driver = new RemoteWebDriver(
				new URL(
						"https://karthik368:cYZyWiu7VhMMete2PbpT@hub-cloud.browserstack.com/wd/hub"),
				caps);
		// CommonFunctions comm = new CommonFunctions();
		/*
		 * comm.openApplication("http://www.google.com");
		 * comm.enterText("searchfields", "BrowserStack");
		 * comm.click("searchfields", "");
		 * comm.getTitleAndCompare("browserstack - Google zoeken");
		 */
		/*
		 * comm.openApplication("https://www.aquasana.co.uk/");
		 * comm.waitForPageToLoad(); comm.waitForPageToBeReady(); if
		 * (comm.verifyElementPresent("tab_spaday", "")) {
		 * CommonFunctions.glb_Logger_commonlogs
		 * .info("Booking Magnet is present"); } else {
		 * CommonFunctions.glb_Logger_commonlogs
		 * .error("Booking Magnet is not present"); Assert.fail(); }
		 * comm.selectOptionFromDropDownBasedOnText("tab_spaday_datedifference",
		 * "Whinfell Forest");
		 * CommonFunctions.click("btn_booking_magnet_search", ""); int i = 0;
		 * for (i = 0; i < 20; i++) { if
		 * (CommonFunctions.isElementDisplayed("input_calendar", "")) {
		 * CommonFunctions.click("input_calendar", ""); break; } }
		 * CommonFunctions.click("input_calendar_year", "");
		 * comm.selectByValue("input_calendar_year", "2017");
		 * Thread.sleep(2000); //
		 * CommonFunctions.click("input_calendar_month","");
		 * CommonFunctions.click("input_calendar_month", "");
		 * comm.selectByVisibleText("input_calendar_month", "May");
		 * Thread.sleep(2000); driver.findElement( By.xpath(
		 * "//div[@class='pika-lendar']//table[@class='pika-table']//td[@data-day='"
		 * + "30" + "']/button")).click(); Thread.sleep(4000);
		 * comm.selectOptionFromDropDownBasedOnText("tab_spaday_datedifference",
		 * "+/- 1 days"); CommonFunctions.click("btn_booking_magnet_search",
		 * ""); comm.waitForElementToBeVisibleLocatedByXpath(
		 * "//div[@class='searchresult']", ""); if
		 * (CommonFunctions.glb_Webdriver_driver.getTitle().contains(
		 * "Search | Aqua Sana")) { CommonFunctions.glb_Logger_commonlogs
		 * .info("Landed on Search Results Page"); } else {
		 * CommonFunctions.glb_Logger_commonlogs
		 * .error("Failed to land on Search Results Page"); Assert.fail(); }
		 */
		driver.get("https://www.aquasana.co.uk");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
				.xpath("//*[text()='Book Now']")));
		driver.findElement(By.xpath("//*[text()='Book Now']")).click();
		driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.MINUTES);
		Select select = new Select(
				driver.findElement(By
						.xpath("//*[@class='is-required']/following-sibling::*/*[@name='villageCode' and contains(@id,'spa')]")));
		select.selectByVisibleText("Whinfell Forest");
		Thread.sleep(5000);
		driver.findElement(By.xpath(".//*[@id='spaDayDatePicker']")).click();
		List<WebElement> calendar = wait
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
						.xpath("//*[contains(@class,'ka-lendar')]/table/descendant::button")));
		for (WebElement webElement : calendar) {
			if (webElement.getText().equals("30")) {
				webElement.click();
				break;
			}
		}
		driver.findElement(
				By.xpath("//*[contains(@id,'spa')]/descendant::*[contains(text(),'Search now')]"))
				.click();
		driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.MINUTES);
		Thread.sleep(5000);
		driver.findElements(By.xpath("//*[text()='More Information']")).get(1)
				.click();
		Thread.sleep(5000);
		driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.MINUTES);
		driver.findElement(By.xpath("//*[contains(text(),'Spa days')]"))
				.click();
		Thread.sleep(5000);
		driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.MINUTES);
		if (driver.findElement(By.xpath("//*[@class='details']")).getText()
				.equals("Whinfell Forest, 17 February 2017")) {
			System.out.println("done");
		}
		driver.close();
		driver.quit();
	}
}