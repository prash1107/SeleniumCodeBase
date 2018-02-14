package application.teststeps;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import browsers.BrowserConfigurator;



import com.aem.genericutilities.CommonFunctions;
import com.aem.genericutilities.GrowlNotification;

import cucumber.api.java.en.Given;

public class BookSpadays {
	
	CommonFunctions commonFunctions = null;
	BrowserConfigurator config = null;
	WebDriver driver = null;
	GrowlNotification notify = null;

	public BookSpadays(CommonFunctions comm, BrowserConfigurator conf,
			GrowlNotification notfication) {
		commonFunctions = comm;
		config = conf;
		driver = BrowserConfigurator.getWebdriver();
		notify = notfication;
	}
	@Given("^I am a guest$")
	public void i_am_a_guest() throws IOException {
		
		CommonFunctions.glb_Logger_commonlogs.info("i am a guest user");
	}
	
	 @Given("^go to page \"([^\"]*)\" in \"([^\"]*)\"$")
	    public void go_to_page_in_author_or_publish(String pathToPage,String instance) throws Throwable {
		 
	 }
	 
}
