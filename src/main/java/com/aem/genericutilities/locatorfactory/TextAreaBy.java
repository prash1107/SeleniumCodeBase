package com.aem.genericutilities.locatorfactory;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TextAreaBy {
 public static Logger log=Logger.getLogger(TextAreaBy.class);
	public static WebElement usingFollowingSiblingOfLabelAndLabelText(String value,WebDriver driver)
    {
		WebElement element=null;
	    try{
			 Assert.assertNotNull("value sent is null..",value);
			 element=driver.findElement(By.xpath("//label[contains(.,'"+value+"')]/following-sibling::textarea"));
		 }
		 catch(Exception e)
	     {
			log.error(e.getMessage());
		 }
	   return element;
	 }
}
