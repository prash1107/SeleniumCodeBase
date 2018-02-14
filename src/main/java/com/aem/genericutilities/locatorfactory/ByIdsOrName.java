package com.aem.genericutilities.locatorfactory;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;

public class ByIdsOrName 
{
public static Logger log=Logger.getLogger(ByIdsOrName.class);
	
	public static WebElement locateElementByIdOrName(String value,WebDriver driver)
	{
		WebElement element=null;
		try{
			Assert.assertNotNull("value sent is null..", value);
			element=driver.findElement(new ByIdOrName(value));
			Assert.assertNotNull("element could not be located...", element);
			log.info("located the element...");
		}
		catch(Exception e)
		{
			log.error(e.getMessage());
		}
		return element;
	}
	
	public static List<WebElement> locateElementsByIdOrName(String value,WebDriver driver)
	{
		List<WebElement> element=null;
		try{
			Assert.assertNotNull("value sent is null..", value);
			element=driver.findElements(new ByIdOrName(value));
			log.info("located the element...");
		}
		catch(Exception e)
		{
			log.error(e.getMessage());
		}
		return element;
	}
}
