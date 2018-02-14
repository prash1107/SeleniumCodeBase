package com.aem.genericutilities.locatorfactory;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class LocateElementsByExcluding extends By{
	private final By by;
	private LocateElementsByExcluding(By by) {
	this.by = by;
	}
	public static By not(By by) {
	return new LocateElementsByExcluding(by);
	}
	@Override
	public List<WebElement> findElements(SearchContext context) {
	List<WebElement> elements =
	context.findElements(By.cssSelector("*"));
	elements.removeAll(context.findElements(by));
	return elements;
	}
}
