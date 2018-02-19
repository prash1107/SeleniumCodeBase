package pages;

import java.io.FileInputStream;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aem.genericutilities.CommonFunctions;
import com.aem.genericutilities.CommonFunctionsExceptions;
import com.aem.genericutilities.CommonLogging;
import com.aem.genericutilities.Exceptions;
import com.google.common.base.Function;

import automation.ConfigurationRead.ConfigurationReader;
import browsers.BrowserConfigurator;

public class BasePage {
	private CommonFunctions commFunc = null;
	
	public BasePage() throws Throwable{
		commFunc = new CommonFunctions();
	}
	
	/*********************************************************************************************************************************
	 * @author:	Rajdeep A Chowdhury
	 * @Date: 29/07/2017 
	 * @Date_of_review: 
	 * @Description: This method is used to refresh the page
	 * @param:  
	 * @throws:
	 **/
	public void refreshPage() {
		commFunc.refreshPage();
	}
	
	/********************************************************************************************************************************
	 * @author:	Rajdeep A Chowdhury
	 * @Date: 29/07/2017 
	 * @Date_of_review: 
	 * @Description: This method is used to wait for the page to be ready
	 * @param:  
	 * @throws: 
	 **/
	public void waitForPageToBeReady() {
		commFunc.waitForPageToBeReady();
	}

	/*********************************************************************************************************************************
	 * @author:	Rajdeep A Chowdhury
	 * @Date: 29/07/2017 
	 * @Date_of_review: 
	 * @Description: This method is used to wait for page to load
	 * @param:  
	 * @throws: 
	 **/
	public void waitForPageToLoad() {
		commFunc.waitForPageToLoad();
	}
	
	/*********************************************************************************************************************************
	 * @author:	Rajdeep A Chowdhury
	 * @Date: 29/07/2017 
	 * @Date_of_review: 
	 * @Description: This method is used to get current url of the page
	 * @param:  
	 * @throws: 
	 **/
	public String getCurrentUrl() {
		return commFunc.getCurrentUrl();
	}
	
	/*********************************************************************************************************************************
	 * @author:	Rajdeep A Chowdhury
	 * @Date: 29/07/2017 
	 * @Date_of_review: 
	 * @Description: This method is used to get current page URL and find text
	 * @param:  Text to find in URL
	 * @throws: 
	 **/
	public boolean getCurrentWindowURLAndFindText(String textToFindInURL) {
		return commFunc.getCurrentWindowURLAndFindText(textToFindInURL);
	}
	
	/*********************************************************************************************************************************
	 * @author:	Rajdeep A Chowdhury
	 * @Date: 29/07/2017 
	 * @Date_of_review: 
	 * @Description: This method is used to wait for ajax call to be completed and ajax element to be loaded
	 * @param:  locator string
	 * @throws: 
	 **/
	public void waitForAjaxCall(String locator){
		commFunc.waitForAjaxCall(locator);
	}

}
