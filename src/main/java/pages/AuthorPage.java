package pages;

import com.aem.genericutilities.CommonFunctions;

/*
 * @author : m karthik
 * This class is useful for authoring pages where all the functions performed during authoring the page
 * is captured inside this class as a methods.
 * Below are sample methods to start writing methods in the similar manner.
 */

public class AuthorPage {
	CommonFunctions commonFunctions;

	public AuthorPage() throws Throwable {
		commonFunctions = new CommonFunctions();
	}

	public boolean addComponent(String component) throws Throwable {
		commonFunctions.scrollElementIntoView("parsysOnPage");
		commonFunctions.clickOnElementForNumberOfTimes("parsysOnPage", "2");
		commonFunctions.scrollElementIntoView("text-component");
		CommonFunctions.click("text-component", "");
		return commonFunctions.isElementDisplayed("textcomp", "");
	}

	public void editTextComponent(String value) throws Throwable {
		CommonFunctions.click("textcomp", "");
		CommonFunctions.click("editText", "");
		CommonFunctions.click("fulscr", "");
		CommonFunctions.click("txtenter", value);
		CommonFunctions.highlightElement("txtenter");
		CommonFunctions.locateListofElements("txtenter").get(0).sendKeys(value);
		CommonFunctions.click("fullscrfinish", "");
		CommonFunctions.click("savechanges", "");
	}

	public void publishPage() throws Throwable {
		commonFunctions.click("pageinfo", "");
		commonFunctions.click("publish", "");
	}

	public boolean verifyPublishMessage() throws Throwable {
		return commonFunctions.isElementDisplayed("publishConfirmation", "");
	}
}
