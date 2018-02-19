package application.teststeps;

import static org.junit.Assert.assertTrue;
import pages.AuthorPage;
import pages.LoginPage;
import pages.SitesPage;
import automation.ConfigurationRead.ConfigurationReader;

import com.aem.genericutilities.CommonFunctions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AuthorSteps {
	private CommonFunctions commonFunctions;
	private LoginPage loginPage;
	private SitesPage sitesPage;
	private AuthorPage authorPage;

	public AuthorSteps(CommonFunctions commFunctions, LoginPage lpage,
			SitesPage spage, AuthorPage aPage) {
		this.commonFunctions = commFunctions;
		loginPage = lpage;
		sitesPage = spage;
		authorPage = aPage;
	}

	@Given("^I login to aem as \"([^\"]*)\" and \"([^\"]*)\"$")
	public void i_login_to_aem_as_and(String username, String password)
			throws Throwable {
		commonFunctions.openApplication(ConfigurationReader.getValue("author"));
		assertTrue("Unable to login : ",
				loginPage.loginToAem(username, password, "Start"));
	}

	@When("^I navigate to the \"([^\"]*)\"$")
	public void i_navigate_to_the_folder(String folder) throws Throwable {
		commonFunctions.navigate("", ConfigurationReader.getValue("author")
				+ folder);
	}

	@Then("^I should be able to see that template \"([^\"]*)\" is present$")
	public void i_should_be_able_to_see_that_template_is_present(String template)
			throws Throwable {
		assertTrue("Template not present..", sitesPage.verifyTemplate(template));
	}

	@Then("^I should be able to createpage with title \"([^\"]*)\" using \"([^\"]*)\"$")
	public void i_should_be_able_to_createpage_with_title_using(String title,
			String template) throws Throwable {
		String currenturl = commonFunctions.getCurrentUrl();
		currenturl = currenturl.replace("sites", "editor");
		assertTrue("Page not created..",
				sitesPage.createPageUsingTemplate(title, template, currenturl));
	}

	@When("^I add commponenet \"([^\"]*)\"$")
	public void i_add_commponenet(String component) throws Throwable {
		assertTrue("Unable to add the component...",
				authorPage.addComponent(component));
	}

	@When("^I edit the component \"([^\"]*)\"$")
	public void i_edit_the_component(String value) throws Throwable {
		authorPage.editTextComponent(value);
	}

	@When("^I publish the page$")
	public void i_publish_the_page() throws Throwable {
		authorPage.publishPage();
	}

	@Then("^I should be able to see that the page is published$")
	public void i_should_be_able_to_see_that_the_page_is_published()
			throws Throwable {
		assertTrue("Page not published..", authorPage.verifyPublishMessage());
	}
}
