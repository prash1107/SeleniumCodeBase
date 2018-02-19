package com.aem.genericutilities.locatorfactory;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ByChained;

public class LocateByComposition {
	public static Logger log=Logger.getLogger(LocateByComposition.class);
	
	public static WebElement locateElementUsingChain(By by[],WebDriver driver)
	{
		WebElement element=null;
		try{
			element=driver.findElement(new ByChained(by));
			Assert.assertNotNull("element could not be located...", element);
			log.info("located the element...");
		}
		catch(Exception e)
		{
			log.error(e.getMessage());
		}
		return element;
	}
	public static List<WebElement> locateListOfElementsUsingChain(By by[],WebDriver driver)
	{
		List<WebElement> element=null;
		try{
			element=driver.findElements(new ByChained(by));
			Assert.assertNotNull("element could not be located...", element);
			log.info("located the element...");
		}
		catch(Exception e)
		{
			log.error(e.getMessage());
		}
		return element;
	}
}
