package pages;

import com.aem.genericutilities.CommonFunctions;

public class SitesPage extends BasePage {
	CommonFunctions commonFunctions;

	public SitesPage() throws Throwable {
		commonFunctions = new CommonFunctions();
	}

	public boolean verifyTemplate(String template) throws Throwable {
		commonFunctions.click("button_create_page", "");
		commonFunctions.click("Create_page", "");
		return CommonFunctions.getTextFromElementAndCompare("product_page",
				template);
	}

	public boolean createPageUsingTemplate(String pageTitle, String template,
			String current) throws Throwable {
		commonFunctions.refreshPage();
		verifyTemplate(template);
		commonFunctions.click("product_page", "");
		commonFunctions.click("button_Next", "");
		commonFunctions.enterText("title_box", pageTitle);
		commonFunctions.click("create_btn", "");
		commonFunctions.clickUsingJS("button_Done", "");
		commonFunctions.navigate("", current + pageTitle + ".html");
		return commonFunctions.getTitleAndCompare(pageTitle);
	}
}
