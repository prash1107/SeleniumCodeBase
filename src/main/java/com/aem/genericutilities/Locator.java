package com.aem.genericutilities;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.aem.genericutilities.CommonFunctions.glb_Webdriver_driver;
import java.io.FileInputStream;
import java.util.Properties;
import static com.aem.genericutilities.CommonFunctions.glb_Logger_commonlogs;
import automation.ConfigurationRead.ConfigurationReader;

public class Locator {
	private static Locator locator = null;
	private Exceptions m_exception = null;
	private static Properties glb_Properties_objectRepository = null;
	
	/**
	 * @author Rajdeep
	 * @Description- private constructor to prevent object creation from outside
	 */
	private Locator(){
		
	}
	
	/**
	 * @author Rajdeep
	 * @Description- Singleton Design Pattern. Single object creation.
	 */
	public static Locator getInstance(){
		try {
			if(locator == null){
				//wait = new WebDriverWait(glb_Webdriver_driver,Long.parseLong(ConfigurationReader.getValue("explicit_wait_timeout_seconds")));
			
				glb_Properties_objectRepository = new Properties();
				FileInputStream obj_properties = new FileInputStream(System.getProperty("user.dir")	+ ConfigurationReader.getValue("objectpropertiespath"));
				glb_Properties_objectRepository.load(obj_properties);
				locator = new Locator();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return locator;
	}
	
	/**
	 * @author Rajdeep 
	 * @description: This function fetches locator value from properties file using locator key
	 * @param locator: locator key as specified in the properties file
	 * @return locator Value for the corresponding locator key in properties file
	 */
	private String getLocatorFromRepository(String locatorKey) {
		String m_locatorValue = null;
		try {
			if (locatorKey == null) {
				glb_Logger_commonlogs.error("locatorKey passed is null.");
				Exceptions m_Exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions);
			}
			m_locatorValue = glb_Properties_objectRepository.getProperty(locatorKey);
			if (m_locatorValue == null) {
				glb_Logger_commonlogs.error("Locator value is not found in properties file for locator key: " + locatorKey);
				Exceptions m_exception = Exceptions.COULD_NOT_FIND_LOCATOR_FROM_REPOSITORY;
				throw new CommonFunctionsExceptions(m_exception, locatorKey);
			}
			else{
			glb_Logger_commonlogs.info("Locator value: \"" + m_locatorValue + "\" is found in properties file for locator key: \""	+ locatorKey + "\"");
			}
		} catch (CommonFunctionsExceptions ex) {
			glb_Logger_commonlogs.error(ex.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error(e.getMessage());
		}
		return m_locatorValue;
	}
	
	/**
	 * @author Rajdeep 
	 * @description This function locates WebElement from the locator value passed to it
	 * @param locator value returned from properties file. e.g - xpath~//input[@type='submit']
	 * @return WebElement
	 */
	public WebElement getWebElement(String locatorValue) //properties file
	{
		WebElement m_WebElemnt_element = null;
		try {
			if (locator != null) {
				if (locatorValue.contains("~")) { //~ is a separator of locator type and value in OR file
					String[] m_locator = locatorValue.split("~");
					String m_locator_type = m_locator[0]; //locator type
					String m_locator_value = m_locator[1]; //locator value
					
					switch (m_locator_type) {
					
						case "xpath":
							try {
								
								m_WebElemnt_element = glb_Webdriver_driver.findElement(By.xpath(m_locator_value));
								if (m_WebElemnt_element != null) {
									glb_Logger_commonlogs.info("WebElement successfully located using xpath: " + m_locator_value);
								}
								else if(m_WebElemnt_element == null){
									glb_Logger_commonlogs.info("Unable to locate element using xpath: " + m_locator_value);
									m_exception = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
									throw new CommonFunctionsExceptions(m_exception, m_locator_value);
								}
							} catch (CommonFunctionsExceptions e) {
								glb_Logger_commonlogs.error(e.getMessage());
							} catch (Exception e) {
								glb_Logger_commonlogs.info(e.getMessage());
							}
							break;
						//----------------------------------------------------------------------------------------------------------------	
						case "id":
							try {
								
								m_WebElemnt_element = glb_Webdriver_driver.findElement(By.id(m_locator_value));
								if (m_WebElemnt_element != null) {
									glb_Logger_commonlogs.info("WebElement successfully located using id: " + m_locator_value);
								}
								else if(m_WebElemnt_element == null){
									glb_Logger_commonlogs.info("Unable to locate element using id: " + m_locator_value);
									m_exception = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
									throw new CommonFunctionsExceptions(m_exception, m_locator_value);
								}
							} catch (CommonFunctionsExceptions e) {
								glb_Logger_commonlogs.error(e.getMessage());
							} catch (Exception e) {
								glb_Logger_commonlogs.info(e.getMessage());
							}
							break;
						//----------------------------------------------------------------------------------------------------------------	
						case "name":
							try {
								
								m_WebElemnt_element = glb_Webdriver_driver.findElement(By.name(m_locator_value));
								if (m_WebElemnt_element != null) {
									glb_Logger_commonlogs.info("WebElement successfully located using name: " + m_locator_value);
								}
								else if(m_WebElemnt_element == null){
									glb_Logger_commonlogs.info("Unable to locate element using name: " + m_locator_value);
									m_exception = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
									throw new CommonFunctionsExceptions(m_exception, m_locator_value);
								}
							} catch (CommonFunctionsExceptions e) {
								glb_Logger_commonlogs.error(e.getMessage());
							} catch (Exception e) {
								glb_Logger_commonlogs.info(e.getMessage());
							}
							break;
						//----------------------------------------------------------------------------------------------------------------	
						case "class":
							try {
								
								m_WebElemnt_element = glb_Webdriver_driver.findElement(By.className(m_locator_value));
								if (m_WebElemnt_element != null) {
									glb_Logger_commonlogs.info("WebElement successfully located using class: " + m_locator_value);
								}
								else if(m_WebElemnt_element == null){
									glb_Logger_commonlogs.info("Unable to locate element using class: " + m_locator_value);
									m_exception = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
									throw new CommonFunctionsExceptions(m_exception, m_locator_value);
								}
							} catch (CommonFunctionsExceptions e) {
								glb_Logger_commonlogs.error(e.getMessage());
							} catch (Exception e) {
								glb_Logger_commonlogs.info(e.getMessage());
							}
							break;
						//----------------------------------------------------------------------------------------------------------------	
						case "css":
							try {
								
								m_WebElemnt_element = glb_Webdriver_driver.findElement(By.cssSelector(m_locator_value));
								if (m_WebElemnt_element != null) {
									glb_Logger_commonlogs.info("WebElement successfully located using css: " + m_locator_value);
								}
								else if(m_WebElemnt_element == null){
									glb_Logger_commonlogs.info("Unable to locate element using css: " + m_locator_value);
									m_exception = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
									throw new CommonFunctionsExceptions(m_exception, m_locator_value);
								}
							} catch (CommonFunctionsExceptions e) {
								glb_Logger_commonlogs.error(e.getMessage());
							} catch (Exception e) {
								glb_Logger_commonlogs.info(e.getMessage());
							}
							break;
						//----------------------------------------------------------------------------------------------------------------
						default:
							m_exception = Exceptions.INVALID_LOCATOR_TYPE_EXCEPTION;
							throw new CommonFunctionsExceptions(m_exception, m_locator_type);
					} // end of switch
				} else { //if locator value does not contain ~
					m_exception = Exceptions.LOCATOR_FORMAT_EXCEPTION;
					throw new CommonFunctionsExceptions(m_exception);
				}
			} else {//if locator value is null
				m_exception = Exceptions.LOCATOR_VALUE_NULL_EXCEPTION;
				throw new CommonFunctionsExceptions(m_exception);
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error(e.getMessage());
		}
		return m_WebElemnt_element;
	}
	
	/**
	 * @author Rajdeep 
	 * @description This function locates WebElement from the locator key passed to it
	 * @param locator key mentioned in the properties file. 
	 * @return WebElement
	 */	
	public WebElement getObject(String locatorKey){
		String locatorValue = getLocatorFromRepository(locatorKey);
		WebElement element = getWebElement(locatorValue);
		return element;
	}

}
