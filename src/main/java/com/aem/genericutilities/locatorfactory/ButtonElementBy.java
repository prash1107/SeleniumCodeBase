package com.aem.genericutilities.locatorfactory;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ButtonElementBy {
	public static Logger log=Logger.getLogger(ButtonElementBy.class);

	public static WebElement usingDescendantOfFollowingSiblingOfLabelAndLabelText(String value,WebDriver driver)
	{
		WebElement element=null;
		try{
			 Assert.assertNotNull("value sent is null..",value);
			 element=driver.findElement(By.xpath("//label[contains(.,'"+value+"')]/following-sibling::*/descendant::button"));
		}
		catch(Exception e)
		{
			log.error(e.getMessage());
		}
		return element;
	}
}
