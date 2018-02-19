package pages;

import com.aem.genericutilities.CommonFunctions;

public class LoginPage extends BasePage {
	CommonFunctions commonFunctions;

	public LoginPage() throws Throwable {
		commonFunctions = new CommonFunctions();
	}

	public boolean loginToAem(String userName, String password, String title)
			throws Throwable {
		commonFunctions.enterText("txtbx_UserName", userName);
		commonFunctions.enterText("txtbx_Password", password);
		commonFunctions.click("btn_LogIn", "");
		return commonFunctions.getTitleAndCompare("Start");
	}
}
