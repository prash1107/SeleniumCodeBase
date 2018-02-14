package application.teststeps;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import browsers.BrowserConfigurator;

import com.aem.genericutilities.CommonFunctions;
import com.aem.genericutilities.GrowlNotification;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class BookingMagnet {
	// CommonFunctions commonFunctionObj =
	// GenericStepDefinition.common_functions_object;
	CommonFunctions commonFunctionObj = null;
	BrowserConfigurator config = null;
	WebDriver driver = null;
	GrowlNotification notify = null;

	public BookingMagnet(CommonFunctions comm, BrowserConfigurator conf,
			GrowlNotification notfication) {
		commonFunctionObj = comm;
		config = conf;
		driver = BrowserConfigurator.getWebdriver();
		notify = notfication;
	}

	@Then("^I see the Booking Magnet$")
	public void i_should_be_able_to_see_sticky_Booking_Magnet()
			throws Throwable {
		if (commonFunctionObj.verifyElementPresent("tab_spaday", "")) {
			CommonFunctions.glb_Logger_commonlogs
					.info("Booking Magnet is present");
		} else {
			CommonFunctions.glb_Logger_commonlogs
					.error("Booking Magnet is not present");
			Assert.fail();
		}
	}

	@When("^I select \"([^\"]*)\" date \"([^\"]*)\" month \"([^\"]*)\" year from date dropdown in booking magnet$")
	public void select_from_date_dropdown(String dateString, String month,
			String year) throws Throwable {
		int i = 0;
		for (i = 0; i < 20; i++) {
			if (CommonFunctions.isElementDisplayed("input_calendar", "")) {
				CommonFunctions.click("input_calendar", "");
				break;
			}
		}
		// CommonFunctions.click("input_calendar", "");
		CommonFunctions.click("input_calendar_year", "");
		commonFunctionObj.selectByValue("input_calendar_year", year);
		Thread.sleep(2000);
		// CommonFunctions.click("input_calendar_month", "");
		CommonFunctions.click("input_calendar_month", "");
		commonFunctionObj.selectByVisibleText("input_calendar_month", month);
		Thread.sleep(2000);
		driver.findElement(
				By.xpath("//div[@class='pika-lendar']//table[@class='pika-table']//td[@data-day='"
						+ dateString + "']/button")).click();
		Thread.sleep(4000);
	}

	@When("^I select \"([^\"]*)\" as date variance and search in booking magnet$")
	public void select_as_date_variance_and_click_on_search_now(
			String dateDifference) throws Throwable {
		// CommonFunctions.click("tab_spaday_datedifference", "");
		commonFunctionObj.selectOptionFromDropDownBasedOnText(
				"tab_spaday_datedifference", dateDifference);
		CommonFunctions.click("btn_booking_magnet_search", "");
		// SThread.sleep(4000);
	}

	@When("^I select \"([^\"]*)\" from Spa locations dropdown in booking magnet$")
	public void select_from_Spa_locations_dropdown(String villageCode)
			throws Throwable {
		CommonFunctions.click("tab_spaday", "");
		// CommonFunctions.click("tab_spaday_spalocation_dropdown", "");
		CommonFunctions.selectOptionFromDropDownBasedOnText(
				"tab_spaday_spalocation_dropdown", villageCode);
		Thread.sleep(4000);
		// Robot robot=new Robot();
		// robot.keyPress(KeyEvent.VK_ENTER);
	}

	@Then("^I should see the Search Results Page for Spa days$")
	public void i_should_see_the_Search_Results_Page_for_Spa_days()
			throws Throwable {
		commonFunctionObj.waitForElementToBeVisibleLocatedByXpath(
				"//div[@class='searchresult']", "");
		if (CommonFunctions.glb_Webdriver_driver.getTitle().contains(
				"Search | Aqua Sana")) {
			CommonFunctions.glb_Logger_commonlogs
					.info("Landed on Search Results Page");
		} else {
			CommonFunctions.glb_Logger_commonlogs
					.error("Failed to land on Search Results Page");
			Assert.fail();
		}
	}

	@Given("^I am on the home page \"([^\"]*)\"$")
	public void i_am_on_the_home_page_http_Homepage_html(String URL)
			throws Throwable {
		System.out.println(URL);
		commonFunctionObj.openApplication(URL);
		commonFunctionObj.waitForPageToLoad();
		commonFunctionObj.waitForPageToBeReady();
	}
}
