package application.teststeps;

import static org.junit.Assert.assertTrue;


import com.aem.genericutilities.CommonFunctions;

import automation.ConfigurationRead.ConfigurationReader;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import pages.LoginPage;
import pages.SitesPage;

public class AuthorAccess 
{
	private CommonFunctions commonFunctions;
	private LoginPage loginPage; 
	private SitesPage sitespage;
	
	public AuthorAccess(CommonFunctions commFunctions, LoginPage lpage, SitesPage spage)
	{
		this.commonFunctions = commFunctions;
		this.loginPage = lpage;
		this.sitespage = spage;
	}
	
	@Given("^I login to aem \"([^\"]*)\" and \"([^\"]*)\"$")
	public void accessAEM(String username, String password) throws Throwable 
	{
		commonFunctions.openApplication(ConfigurationReader.getValue("author"));
		assertTrue("able to login", loginPage.loginToAem(username, password, "Start"));
		
	}
	@When("^I navigate to \"([^\"]*)\"$")
	public void i_navigate_to_folder(String folder) throws Throwable {
		
		commonFunctions.navigate("", ConfigurationReader.getValue("Domain")
				+ folder);
//		commonFunctions.navigate("", "http://localhost:4502/sites.html/content/we-retail/us/en");
          
	}
	@When("^create a page with title \"([^\"]*)\" using \"([^\"]*)\"$")
	public void create_a_page_with_title_using(String pageTitle, String template) throws Throwable 
	{
		String currenturl = commonFunctions.getCurrentUrl();
		currenturl = currenturl.replace("sites", "editor");
		assertTrue("Page not created..",
				sitespage.createPageUsingTemplate(pageTitle, template, currenturl));   
	}
}
