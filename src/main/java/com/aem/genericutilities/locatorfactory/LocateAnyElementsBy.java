package com.aem.genericutilities.locatorfactory;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class LocateAnyElementsBy extends By{
	private final By[] bys;
	private LocateAnyElementsBy(By... bys) {
	this.bys = bys;
	}
	public static By any(By... bys) {
	return new LocateAnyElementsBy(bys);
	}
	@Override
	public List<WebElement> findElements(SearchContext context) {
	List<WebElement> elements = new ArrayList<>();
	for (By by : bys) {
	elements.addAll(context.findElements(by));
	}
	return elements;
	}
}
