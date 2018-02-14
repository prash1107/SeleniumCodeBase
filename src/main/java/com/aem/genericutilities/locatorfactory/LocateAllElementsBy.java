package com.aem.genericutilities.locatorfactory;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class LocateAllElementsBy extends By{
	private final By[] bys;
	private LocateAllElementsBy(By... bys) {
		this.bys = bys;
	}
	public static LocateAllElementsBy all(By... bys) {
		return new LocateAllElementsBy(bys);
	}
	@Override
	public List<WebElement> findElements(SearchContext context) 
	{
		List<WebElement> elements = null;
		for (By by : bys) {
			List<WebElement> newElements = context.findElements(by);
			if (elements == null) {
				elements = newElements;
			} 
			else 
			{
				elements.retainAll(newElements);
			}
		}
		return elements;
	}
}
