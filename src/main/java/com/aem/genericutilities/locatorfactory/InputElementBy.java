package com.aem.genericutilities.locatorfactory;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.aem.genericutilities.CommonFunctions;
import com.aem.genericutilities.CommonFunctionsExceptions;
import com.aem.genericutilities.Exceptions;


public class InputElementBy {
	public static Logger log=Logger.getLogger(InputElementBy.class);

	public static WebElement usingFollowingSiblingOfLabelAndLabelText(String value,WebDriver driver)
	{
		WebElement element=null;
		try{
			 Assert.assertNotNull("value sent is null..",value);
			 element=driver.findElement(By.xpath("//label[contains(.,'"+value+"')]/following-sibling::input"));
		}
		catch(Exception e)
		{
			log.error(e.getMessage());
		}
		return element;
	}
	
	public static List<WebElement> usingDescendantOfFollowingSiblingOfLabelAndLabelText(String value,WebDriver driver)
    {
		List<WebElement>element=null;
	    try{
			 Assert.assertNotNull("value sent is null..",value);
			 element=driver.findElements(By.xpath("//label[contains(.,'"+value+"')]/following-sibling::*/descendant::input"));
		 }
		 catch(Exception e)
	     {
			log.error(e.getMessage());
		 }
	   return element;
	 }
	
	public static WebElement usingFollowingSiblingOfLabelAndLabelTextAndTypeOfInput(String value,WebDriver driver,String type)
	{
		WebElement element=null;
		try{
			 Assert.assertNotNull("value sent is null..",value);
			 Assert.assertNotNull("type sent is null..",type);
			 element=driver.findElement(By.xpath("//label[contains(.,'"+value+"')]/following-sibling::input[@type="+type+"]"));
		}
		catch(Exception e)
		{
			log.error(e.getMessage());
		}
		return element;
	}
	
}
