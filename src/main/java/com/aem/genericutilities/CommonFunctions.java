package com.aem.genericutilities;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.PixelGrabber;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.impl.util.Base64;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.security.Credentials;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;

import automation.ConfigurationRead.ConfigurationReader;
import browsers.BrowserConfigurator;

import com.google.common.base.Function;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

/**
 * 
 * @author mkarthik
 * 
 *         This class should have all the commonly used functions developed
 *         using selenium web driver
 * 
 *         please add description for all the methods defined and declared in
 *         this class
 * 
 */
public class CommonFunctions {
	public static WebDriver glb_Webdriver_driver = null;
	public static Logger glb_Logger_commonlogs = null;
	public static int glb_Int_captureScreenshotCount = 0;
	public static String failMsg = null;
	public static Properties glb_Properties_objectRepository = null;
	public static CommonFunctions commonFunctions = null;
	public static WebDriverWait wait = null;

	/**
	 * @author mkarthik date: October 2nd date of review: parameters : It is a
	 *         zero parameterized constructor Description: This constructor is
	 *         used to initialize the logs
	 * @throws Throwable
	 * 
	 */
	public CommonFunctions() throws Throwable {
		// glb_Webdriver_driver = JavaSample.driver;
		glb_Webdriver_driver = BrowserConfigurator.getWebdriver();
		glb_Logger_commonlogs = CommonLogging.getLogObj(CommonFunctions.class);
		glb_Properties_objectRepository = new Properties();
		FileInputStream obj_properties = new FileInputStream(
				System.getProperty("user.dir")
						+ ConfigurationReader.getValue("objectpropertiespath"));
		glb_Properties_objectRepository.load(obj_properties);
		wait = new WebDriverWait(glb_Webdriver_driver,
				Long.parseLong(ConfigurationReader.getValue("explicit_wait")));
	}

	public static CommonFunctions getCommonFunctionsInstance() throws Throwable {
		CommonFunctions function = null;
		if (commonFunctions == null) {
			function = new CommonFunctions();
			commonFunctions = function;
		}
		return commonFunctions;
	}

	/**
	 * @author mkarthik date: October 2nd date of review: Description: This
	 *         method will is used to open the webapplication using the given
	 *         url. Only if the response code is 200 the webapplication is
	 *         opened else exception is thrown
	 * @param url
	 *            : url of the application
	 * @return This method returns a boolean value
	 * @throws Throwable
	 */
	public boolean openApp(String object, String url) throws Throwable {
		boolean m_bln_openApp_Status = false;
		int int_response_code = 0;
		try {
			Assert.assertNotNull(url);
			if (url != null) {
				int_response_code = sendGet("", url);
				if (int_response_code == 200) {
					glb_Webdriver_driver.get(url);
					waitForPageToLoad();
					waitForPageToBeReady();
					m_bln_openApp_Status = true;
					glb_Logger_commonlogs.info("WebApplication opened..");
				} else {
					Exceptions m_exceptions = Exceptions.INVALID_URL_EXCEPTION;
					if (m_exceptions == Exceptions.INVALID_URL_EXCEPTION) {
						throw new CommonFunctionsExceptions(int_response_code,
								url);
					}
				}
			} else {
				Exceptions m_exceptions = Exceptions.NULL_URL_EXCEPTION;
				throw new CommonFunctionsExceptions(url);
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs
					.error("unable to open app.." + e.getMessage());
		}
		return m_bln_openApp_Status;
	}

	/**
	 * @author mkarthik date: October 2nd date of review: Description: This
	 *         method is used to verify response code for specified url
	 * @param urls
	 *            : link to be verified
	 * @return m_responseCode : status code of the link that has been currently
	 *         hit
	 * @throws Exception
	 */
	private static int sendGet(String object, String urls) throws Exception {
		int m_responseCode = 0;
		try {
			String m_url = urls;
			URL m_obj = new URL(m_url);
			HttpURLConnection m_con = (HttpURLConnection) m_obj
					.openConnection();
			m_con.setRequestMethod("GET");
			m_responseCode = m_con.getResponseCode();
			m_con.disconnect();
		} catch (Exception e) {
			glb_Logger_commonlogs.error("could not get the response code...");
		}
		return m_responseCode;
	}

	/**
	 * @author mkarthik date: October 2nd date of review: Description: This
	 *         method is used to close the browser which is currently
	 *         opened.(Not to be called with new framework enhancement as
	 *         browser close is taken care automatically)
	 * @return m_bln_close_state : this method returns the status once the
	 *         browser is closed
	 * @throws CommonFunctionsExceptions
	 * @return
	 */
	public static boolean closeBrowser(String object, String testdata)
			throws CommonFunctionsExceptions {
		boolean m_bln_close_state = false;
		try {
			if (glb_Webdriver_driver != null) {
				glb_Webdriver_driver.close();
				m_bln_close_state = true;
				glb_Logger_commonlogs.info("Closed browser...");
			} else {
				Exceptions m_exceptions = Exceptions.NULL_WEBDRIVER_EXCEPTION;
				throw new CommonFunctionsExceptions();
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error("closing browser.." + e.getMessage());
		}
		return m_bln_close_state;
	}

	/**
	 * @author mkarthik date: October 2nd date of review: Description: This
	 *         method is used to perform drag and drop operation. The parameter
	 *         v_object should have the string which would have source and
	 *         target locators seperated by <>
	 * @param source
	 *            : locator for source
	 * @param destination
	 *            : locator for destination
	 * @return m_bln_dragAndDrop_status
	 * @throws Throwable
	 */
	public boolean dragAndDrop(String v_object, String v_testdata)
			throws Throwable {
		boolean m_bln_dragAndDrop_status = false;
		WebElement m_WebElement_Source = null;
		WebElement m_WebElement_Destination = null;
		String[] locator = null;
		String m_str_source = null;
		String m_str_destination = null;
		Assert.assertNotNull(v_object, "The object passes is null");
		locator = splitLocators(glb_Properties_objectRepository
				.getProperty(v_object));
		int m_int_size = locator.length;
		if (m_int_size == 2) {
			m_str_source = locator[0];
			m_str_destination = locator[1];
		} else {
			glb_Logger_commonlogs
					.info("Expected only 2 elements. But number of elements found: "
							+ m_int_size);
		}
		try {
			Assert.assertNotNull(m_str_source,
					"The source element is null. Hence halting the execution...");
			Assert.assertNotNull(m_str_destination,
					"The destination element is null. Hence halting the execution...");
			if ((m_str_source != null) && (m_str_destination != null)) {
				m_WebElement_Source = locateElement(m_str_source);
				m_WebElement_Destination = locateElement(m_str_destination);
				Assert.assertNotNull(
						"The source element is not located. Hence halting the execution..",
						m_WebElement_Source);
				Assert.assertNotNull(
						"The destination element is not located. Hence halting the execution..",
						m_WebElement_Destination);
				if ((m_WebElement_Source != null)
						&& (m_WebElement_Destination != null)) {
					Actions m_Actions_action = new Actions(glb_Webdriver_driver);
					m_Actions_action
							.dragAndDrop(m_WebElement_Source,
									m_WebElement_Destination).build().perform();
					m_bln_dragAndDrop_status = true;
					glb_Logger_commonlogs
							.info("Drag and Drop operation completed...");
				} else {
					Exceptions m_Exceptions = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
					if (m_WebElement_Source == null) {
						throw new CommonFunctionsExceptions(m_Exceptions,
								m_str_source);
					} else if (m_WebElement_Destination == null) {
						throw new CommonFunctionsExceptions(m_Exceptions,
								m_str_destination);
					}
				}
			} else {
				Exceptions m_exceptions = Exceptions.SOURCE_DESTINATION_VALUES_NULL_EXCEPTION;
				throw new CommonFunctionsExceptions(m_str_source,
						m_str_destination);
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error("unable to drag and drop"
					+ e.getMessage());
		}
		return m_bln_dragAndDrop_status;
	}

	/**
	 * @author Rajdeep date: December 6th date of review: Description: Select
	 *         drop down based on option visible text
	 * @param: v_str_object = element locator key mentioned in the excel and
	 *         properties file, v_str_data = the text which will be selected
	 *         from the dropdown
	 * @return: void
	 * @throws Throwable
	 */
	public static void selectByVisibleText(String v_str_object,
			String v_str_data) throws Throwable {
		WebElement m_WebElement_Select_Object = null;
		try {
			glb_Logger_commonlogs
					.info("Inside selectByVisibleText() function...");
			if (v_str_object == null) {
				glb_Logger_commonlogs
						.error("v_str_object passed is null. Hence halting execution...");
				Exceptions m_Exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions);
			}
			if (v_str_data == null) {
				glb_Logger_commonlogs
						.error("v_str_data passed is null. Hence halting execution...");
				Exceptions m_Exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions);
			}
			Assert.assertNotNull(v_str_object, "Null Select object passed.");
			Assert.assertNotNull(v_str_data, "Null select visible text passed.");
			List<WebElement> elements = getObjectFromRepo(v_str_object);
			if (elements == null) {
				glb_Logger_commonlogs.error("Not able to locate element: "
						+ v_str_object);
				Exceptions m_Exceptions = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions, v_str_object);
			} else if (elements.size() > 1) {
				glb_Logger_commonlogs
						.error("More than one element passed to select. Considering the first element.");
			}
			m_WebElement_Select_Object = elements.get(0);
			Select dropDown = new Select(m_WebElement_Select_Object);
			dropDown.selectByVisibleText(v_str_data);
			glb_Logger_commonlogs.info("Dropdown- " + v_str_object
					+ " successfully selected by visible text- " + v_str_data);
		} catch (CommonFunctionsExceptions ex) {
			glb_Logger_commonlogs.error(ex.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error("unable to select by visible text.."
					+ e.getMessage());
		}
	}

	/**
	 * @author Rajdeep date: December 6th date of review: Description: Select
	 *         drop down based on option value
	 * @param: v_str_object = element locator key mentioned in the excel and
	 *         properties file, v_str_data = the value which will be selected
	 *         from the dropdown
	 * @return: void
	 * @throws Throwable
	 */
	public static boolean selectByValue(String v_str_object, String v_str_data)
			throws Throwable {
		WebElement m_WebElement_Select_Object = null;
		boolean select = false;
		try {
			if (v_str_object == null) {
				glb_Logger_commonlogs
						.error("v_str_object passed is null. Hence halting execution...");
				Exceptions m_Exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions);
			}
			if (v_str_data == null) {
				glb_Logger_commonlogs
						.error("v_str_data passed is null. Hence halting execution...");
				Exceptions m_Exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions);
			}
			List<WebElement> elements = getObjectFromRepo(v_str_object);
			if (elements == null) {
				glb_Logger_commonlogs.error("Not able to locate element: "
						+ v_str_object);
				Exceptions m_Exceptions = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions, v_str_object);
			} else if (elements.size() > 1) {
				glb_Logger_commonlogs
						.error("More than one element passed to select. Selecting the first element.");
			}
			m_WebElement_Select_Object = elements.get(0);
			Select dropDown = new Select(m_WebElement_Select_Object);
			dropDown.selectByValue(v_str_data);
			String actual_value = dropDown.getFirstSelectedOption().getText();
			if (actual_value.trim().equals(v_str_data)) {
				select = true;
				glb_Logger_commonlogs.info("Dropdown- " + v_str_object
						+ " successfully selected by value- " + v_str_data);
			}
		} catch (CommonFunctionsExceptions ex) {
			glb_Logger_commonlogs.error(ex.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error("unable to select by value.."
					+ e.getMessage());
		}
		return select;
	}

	/**
	 * @author mkarthik date: October 2nd date of review: Description: This
	 *         method is used to locate the element using different locating
	 *         strateg
	 * @param locator
	 *            : locator key as specified in object repository : Locator key
	 *            as specified in object repository
	 * @return m_WebElemnt_element
	 * @throws Throwable
	 */
	private static WebElement locateElement(String locator) throws Throwable {
		WebElement m_WebElemnt_element = null;
		try {
			if (locator != null) {
				if (locator.contains("~")) {
					String[] m_locator = locator.split("~");
					String m_locator_type = m_locator[0];
					String m_locator_value = m_locator[1];
					WebDriverWait wait = new WebDriverWait(
							glb_Webdriver_driver,
							Integer.parseInt(ConfigurationReader
									.getValue("explicit_wait")));
					switch (m_locator_type) {
					case "xpath":
						try {
							wait.until(ExpectedConditions
									.presenceOfElementLocated(By
											.xpath(m_locator_value)));
							m_WebElemnt_element = glb_Webdriver_driver
									.findElement(By.xpath(m_locator_value));
							if (m_WebElemnt_element != null) {
								glb_Logger_commonlogs
										.info("WebElement located with using xpath : "
												+ m_locator_value);
							}
						} catch (TimeoutException e) {
							glb_Logger_commonlogs.error(e.getMessage());
						} catch (Exception e) {
							glb_Logger_commonlogs.info("Not able to locate"
									+ e.getMessage());
						}
						break;
					case "id":
						try {
							wait.until(ExpectedConditions
									.presenceOfElementLocated(By
											.id(m_locator_value)));
							m_WebElemnt_element = glb_Webdriver_driver
									.findElement(By.id(m_locator_value));
							if (m_WebElemnt_element != null) {
								glb_Logger_commonlogs
										.info("WebElement located with using id : "
												+ m_locator_value);
							}
						} catch (TimeoutException e) {
							glb_Logger_commonlogs.error(e.getMessage());
						} catch (Exception e) {
							glb_Logger_commonlogs.error(e.getMessage());
						}
						break;
					case "name":
						try {
							wait.until(ExpectedConditions
									.presenceOfElementLocated(By
											.name(m_locator_value)));
							m_WebElemnt_element = glb_Webdriver_driver
									.findElement(By.name(m_locator_value));
							if (m_WebElemnt_element != null) {
								glb_Logger_commonlogs
										.info("WebElement located with using name : "
												+ m_locator_value);
							}
						} catch (TimeoutException e) {
							glb_Logger_commonlogs.error(e.getMessage());
						} catch (Exception e) {
							glb_Logger_commonlogs.error(e.getMessage());
						}
						break;
					case "class":
						try {
							wait.until(ExpectedConditions
									.presenceOfElementLocated(By
											.className(m_locator_value)));
							m_WebElemnt_element = glb_Webdriver_driver
									.findElement(By.className(m_locator_value));
							if (m_WebElemnt_element != null) {
								glb_Logger_commonlogs
										.info("WebElement located with using classname : "
												+ m_locator_value);
							}
						} catch (TimeoutException e) {
							glb_Logger_commonlogs.error(e.getMessage());
						} catch (Exception e) {
							glb_Logger_commonlogs.error(e.getMessage());
						}
						break;
					case "css":
						try {
							wait.until(ExpectedConditions
									.presenceOfElementLocated(By
											.cssSelector(m_locator_value)));
							m_WebElemnt_element = glb_Webdriver_driver
									.findElement(By
											.cssSelector(m_locator_value));
							if (m_WebElemnt_element != null) {
								glb_Logger_commonlogs
										.info("WebElement located with using css : "
												+ m_locator_value);
							}
						} catch (TimeoutException e) {
							glb_Logger_commonlogs.error(e.getMessage());
						} catch (Exception e) {
							glb_Logger_commonlogs.error(e.getMessage());
						}
						break;
					default:
						Exceptions m_exeption = Exceptions.INVALID_LOCATOR_TYPE_EXCEPTION;
						if (m_exeption == Exceptions.INVALID_LOCATOR_TYPE_EXCEPTION) {
							throw new CommonFunctionsExceptions(m_exeption,
									m_locator_type);
						}
					}
				} else {
					Exceptions m_Exceptions = Exceptions.LOCATOR_FORMAT_EXCEPTION;
					throw new CommonFunctionsExceptions(m_Exceptions);
				}
			} else {
				Exceptions m_Exception = Exceptions.LOCATOR_VALUE_NULL_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exception);
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error(e.getMessage());
		}
		return m_WebElemnt_element;
	}

	/**
	 * @author mkarthik date: October 2nd date of review: Description: This
	 *         method is used to enter text to element based on locator
	 * @param locator
	 *            : locator key as specified in object repository
	 * @param text_Value
	 *            : text values to be entered
	 * @return m_bln_enter_text_state
	 * @throws Throwable
	 */
	public boolean enterText(String v_str_object, String v_text_Value)
			throws Throwable {
		boolean m_bln_enter_text_state = false;
		WebElement m_WebElement_textfield = null;
		String locate = null;
		String[] locator = null;
		String m_str_locator = null;
		Assert.assertNotNull(v_str_object, "The object passes is null");
		locate = glb_Properties_objectRepository.getProperty(v_str_object);
		locator = splitLocators(locate);
		int m_int_size = locator.length;
		if (m_int_size == 1) {
			m_str_locator = locator[0];
		} else {
			glb_Logger_commonlogs
					.info("Expected only 1 element. But number of elements found: "
							+ m_int_size);
		}
		try {
			Assert.assertNotNull(m_str_locator,
					"The value passed is null. Hence halting the execution...");
			if (m_str_locator != null) {
				m_WebElement_textfield = locateElement(m_str_locator);
				if (m_WebElement_textfield != null) {
					m_WebElement_textfield.clear();
					m_WebElement_textfield.sendKeys(v_text_Value);
					glb_Logger_commonlogs
							.info("Completed entering text to element...");
					m_bln_enter_text_state = true;
				} else {
					Exceptions m_Exceptions = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
					throw new CommonFunctionsExceptions(m_Exceptions,
							m_str_locator);
				}
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error(e.getMessage());
		}
		return m_bln_enter_text_state;
	}

	/**
	 * @author mkarthik description : This method is used to scroll to the
	 *         specified webelement based on locator
	 * @param : locator
	 * @throws Throwable
	 */
	public void scrollElementIntoView(String locator) throws Throwable {
		try {
			WebElement m_element = locateElement(glb_Properties_objectRepository
					.getProperty(locator));
			if (m_element != null) {
				glb_Logger_commonlogs.info("WebElement located...");
				((JavascriptExecutor) glb_Webdriver_driver).executeScript(
						"arguments[0].scrollIntoView(true);", m_element);
				setDelay("5000");
				glb_Logger_commonlogs.info("Scrolled element into view...");
			} else {
				Exceptions m_Exceptions = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions, locator);
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error(e.getMessage());
		}
	}

	/**
	 * @author mkarthik description : this method is used to click on the
	 *         element as specified by locator using java script
	 * @param : locator
	 * @throws Throwable
	 */
	public void clickUsingJS(String locator) throws Throwable {
		try {
			setDelay("5000");
			WebElement m_element = locateElement(glb_Properties_objectRepository
					.getProperty(locator));
			if (m_element != null) {
				glb_Logger_commonlogs
						.info("In clickUisngJS WebElement located...");
				((JavascriptExecutor) CommonFunctions.glb_Webdriver_driver)
						.executeScript("arguments[0].click();", m_element);
				setDelay("5000");
				glb_Logger_commonlogs.info("Clicked on element...");
			} else {
				Exceptions m_Exceptions = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions, locator);
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.getMessage());
		} catch (Exception e) {
			glb_Logger_commonlogs.error("couldnt click using js: "
					+ e.getMessage());
		}
	}

	/**
	 * @author mkarthik date: October 2nd date of review: Description: This
	 *         function is used to get the attribute of the element
	 * @param locator
	 *            : locator key as specified in object repository : locator key
	 *            as specified in object repository
	 * @param attribute
	 *            : expected attribute to be searched
	 * @param expected_Value
	 *            : expected attribute value
	 * @return m_Str_attribute_value : actual attribute value
	 * @throws Throwable
	 */
	private static String getAttributeOfElement(String v_str_locator,
			String v_str_expectedAttribute_value) throws Throwable {
		String m_Str_attribute_value = null;
		WebElement m_WebElement_element = null;
		String[] locators = null;
		String[] testdata = null;
		String attribute = null;
		String value = null;
		String m_str_locators = null;
		try {
			if (v_str_locator != null) {
				testdata = v_str_expectedAttribute_value.split("=");
				Assert.assertNotNull("There are no testdata passed..", testdata);
				int m_int_size_testdata = testdata.length;
				if (m_int_size_testdata == 1) {
					glb_Logger_commonlogs
							.info("only one string is passed, assuming that passed string is the attribute..");
					attribute = testdata[0];
				} else if (m_int_size_testdata == 2) {
					attribute = testdata[0];
					value = testdata[1];
				} else {
					glb_Logger_commonlogs
							.info("Expected one attribute and value for an element. But number of string passed may be more.");
					Exceptions mExceptions = Exceptions.INVALID_NUMBER_OF_VALUES;
					throw new CommonFunctionsExceptions(mExceptions);
				}
				locators = splitLocators(v_str_locator);
				int m_int_size = locators.length;
				if (m_int_size == 1) {
					m_str_locators = locators[0];
				} else {
					glb_Logger_commonlogs
							.info("Expected only 1 element. But number of elements found: "
									+ m_int_size);
				}
				Assert.assertNotNull(m_str_locators,
						"The value passed is null. Hence halting the execution...");
				if (m_str_locators != null) {
					m_WebElement_element = locateElement(getLocatorFromRepository(m_str_locators));
					if (m_WebElement_element != null) {
						m_Str_attribute_value = m_WebElement_element
								.getAttribute(attribute);
						if (m_Str_attribute_value != null) {
							glb_Logger_commonlogs.info("Got attribute :"
									+ attribute + " and value: "
									+ m_Str_attribute_value
									+ " for the specified element..");
						}
					} else {
						Exceptions m_Exceptions = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
						throw new CommonFunctionsExceptions(m_Exceptions,
								m_str_locators);
					}
				}
			} else {
				Exceptions mExceptions = Exceptions.LOCATOR_VALUE_NULL_EXCEPTION;
				throw new CommonFunctionsExceptions(mExceptions);
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error(e.getMessage());
		}
		return m_Str_attribute_value;
	}

	/**
	 * @author Rajdeep date: November- 4th date of review: Description: This
	 *         function is used to get WebElement objects from locators
	 *         mentioned in the object repository
	 * @param: v_str_object = element locator key mentioned in the excel and
	 *         properties file
	 * @return List<WebElement> of WebElements. Returns multiple web elements if
	 *         two locators are mentioned in properties file separated by <>,
	 *         else return one webelement
	 * @throws Throwable
	 */
	private static List<WebElement> getObjectFromRepo(String v_str_object)
			throws Throwable {
		/*
		 * v_str_object = locator 1. get locator from properties file- this may
		 * have multiple locators separated by "<>". 2. pass it to splitLocator
		 * function to separate multiple locators 3. split function returns
		 * string array of each locator- pass it to locateElement function one
		 * by one to fetch separate webelements
		 */
		List<WebElement> m_listOfWebElements = new ArrayList<WebElement>();
		try {
			glb_Logger_commonlogs
					.info("Inside getObjectFromRepo() function...");
			if (v_str_object == null) {
				glb_Logger_commonlogs.error("Null value passed as locator: "
						+ v_str_object);
				Exceptions m_exception = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_exception);
			}
			Assert.assertNotNull(v_str_object,
					"The locator passed is null. Hence halting the execution...");
			// Getting locator from properties file- this may have multiple
			// locators separated by "<>".
			String locator = getLocatorFromRepository(v_str_object);
			// Spiting multiple xpaths (if present) separated by <> and put them
			// into an array
			String[] locators = splitLocators(locator);
			// Passing each xpath present in the array to locateElement function
			// to get webelement
			for (int i = 0; i < locators.length; i++) {
				if (locateElement(locators[i]) != null) {
					m_listOfWebElements.add(locateElement(locators[i]));
					glb_Logger_commonlogs.info("Successfully located element: "
							+ v_str_object);
				} else {
					glb_Logger_commonlogs
							.error("Unable to locate WebElement using locator: "
									+ locators[i]);
					Exceptions m_exception = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
					throw new CommonFunctionsExceptions(m_exception,
							locators[i]);
				}
			}
		} catch (CommonFunctionsExceptions ex) {
			glb_Logger_commonlogs.error(ex.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error(e.getMessage());
		}
		return m_listOfWebElements;
	}

	/**
	 * @author Rajdeep date: October-6th date of review: October-7th
	 *         Description: This function is used to get the text of an element
	 *         - used internally by another function, so making it private
	 * @param: v_str_object = element locator key mentioned in the excel and
	 *         properties file
	 * @return String- the text of the element
	 * @throws Throwable
	 */
	private static String getTextFromElement(String v_str_object)
			throws Throwable {
		String m_Str_text = null;
		try {
			if (v_str_object == null) {
				glb_Logger_commonlogs
						.error("v_str_object passed is null. Hence halting execution...");
				Exceptions m_Exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions);
			}
			List<WebElement> m_WebElement_element = getObjectFromRepo(v_str_object);
			if (m_WebElement_element == null) {
				glb_Logger_commonlogs.error("Not able to locate element: "
						+ v_str_object);
				Exceptions m_Exceptions = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions, v_str_object);
			} else if (m_WebElement_element.size() > 1) {
				glb_Logger_commonlogs
						.error("More than one element found. Considering first element.");
			}
			m_Str_text = m_WebElement_element.get(0).getText();
			glb_Logger_commonlogs.info("Text got from element " + v_str_object
					+ " :: " + m_Str_text);
		} catch (CommonFunctionsExceptions ex) {
			glb_Logger_commonlogs.error(ex.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error(e.getMessage());
		}
		return m_Str_text;
	}

	/**
	 * @author Rajdeep date: October-6th date of review: October-7th
	 *         Description: This function is used to highlight an element - used
	 *         internally by other functions
	 * @param: v_str_object = element locator key mentioned in the excel and
	 *         properties file
	 * @return: void
	 * @throws Throwable
	 */
	public static void highlightElement(String v_str_object) throws Throwable {
		List<WebElement> m_WebElement_element = null;
		JavascriptExecutor js = (JavascriptExecutor) glb_Webdriver_driver;
		try {
			// Null checking - both object and data are mandatory
			if (v_str_object == null) {
				glb_Logger_commonlogs
						.error("v_str_object passed is null. Hence halting execution...");
				Exceptions m_Exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions);
			}
			Assert.assertNotNull(v_str_object,
					"The locator passed is null. Hence halting the execution...");
			m_WebElement_element = getObjectFromRepo(v_str_object);
			if (m_WebElement_element == null) {
				glb_Logger_commonlogs.error("Not able to locate element: "
						+ v_str_object);
				Exceptions m_exception = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
				throw new CommonFunctionsExceptions(m_exception, v_str_object);
			} else if (m_WebElement_element.size() > 1) {
				glb_Logger_commonlogs
						.error("More than one object passed. Considering first element.");
			}
			for (int i = 0; i <= 5; i++) {
				js.executeScript(
						"arguments[0].setAttribute('style', arguments[1]);",
						m_WebElement_element.get(0), "border: 5px solid red;");
				Thread.sleep(1000);
				js.executeScript(
						"arguments[0].setAttribute('style', arguments[1]);",
						m_WebElement_element.get(0), "");
			}
			glb_Logger_commonlogs.info("Successfully highlighed element: "
					+ v_str_object);
		} catch (CommonFunctionsExceptions ex) {
			glb_Logger_commonlogs.error(ex.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error(e.getMessage());
		}
	}

	/**
	 * @author mkarthik Description : This method is used to highlight the
	 *         webelement
	 * @param element
	 *            : The element that needs to be highlighted
	 * @throws Throwable
	 */
	public static void highlightWebElement(WebElement element) throws Throwable {
		try {
			if (glb_Webdriver_driver != null) {
				JavascriptExecutor js = (JavascriptExecutor) glb_Webdriver_driver;
				if (element != null) {
					for (int i = 0; i <= 5; i++) {
						js.executeScript(
								"arguments[0].setAttribute('style', arguments[1]);",
								element, "border: 5px solid red;");
						Thread.sleep(1000);
						js.executeScript(
								"arguments[0].setAttribute('style', arguments[1]);",
								element, "");
					}
				} else {
					Exceptions mExceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
					throw new CommonFunctionsExceptions(mExceptions);
				}
			} else {
				Exceptions mExceptions = Exceptions.NULL_WEBDRIVER_EXCEPTION;
				throw new CommonFunctionsExceptions();
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error("unable to highlight web element..");
		}
	}

	/**
	 * @author Rajdeep date: October-7th date of review: October-8th
	 *         Description: This function is used to compare two texts. Used
	 *         internally by other functions
	 * @param v_text1
	 *            to be compared with v_text2
	 * @return boolean- true: if two texts are matched, false: if not matched
	 */
	private static boolean compareTexts(String v_text1, String v_text2) {
		boolean m_bln_compare = false;
		try {
			Assert.assertNotNull(v_text1,
					"Text passed is null. Hence halting the execution...");
			Assert.assertNotNull(v_text2,
					"Text passed is null. Hence halting the execution...");
			m_bln_compare = v_text1.equals(v_text2);
			if (m_bln_compare == true) {
				glb_Logger_commonlogs.info("Comparision went successfull...");
			}
			if (m_bln_compare == false) {
				glb_Logger_commonlogs.error("Text - " + v_text1 + " and Text- "
						+ v_text2 + " did not match... ");
			}
		} catch (Exception e) {
			glb_Logger_commonlogs.error(e.getMessage());
		}
		return m_bln_compare;
	}

	/**
	 * @author Rajdeep date: October-7th date of review: October-8th
	 *         Description: This function is used to get text from an element
	 *         and compare with expected text
	 * @param: v_str_object = element locator key mentioned in properties file,
	 *         v_str_expectedText = expected text to be compared with element's
	 *         actual text
	 * @return boolean- true: if actual and expected texts are matched, false:
	 *         if not matched
	 * @throws Throwable
	 */
	public static boolean getTextFromElementAndCompare(String v_str_object,
			String v_str_expectedText) throws Throwable {
		String m_str_actual_text = null;
		boolean m_bln_compareText = false;
		try {
			if (v_str_object == null) {
				Exceptions m_Exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions);
			}
			if (v_str_expectedText == null) {
				Exceptions m_Exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions);
			}
			m_str_actual_text = getTextFromElement(v_str_object);
			m_bln_compareText = compareTexts(m_str_actual_text,
					v_str_expectedText);
			if (m_bln_compareText) {
				glb_Logger_commonlogs.info("Texts are matched.");
			} else {
				glb_Logger_commonlogs.error(failMsg);
			}
		} catch (CommonFunctionsExceptions ex) {
			glb_Logger_commonlogs.error(ex.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error(e.getMessage());
			glb_Logger_commonlogs.error(failMsg);
		}
		return m_bln_compareText;
	}

	/**
	 * @author Rajdeep date: October-7th date of review: Description: This
	 *         function is used to read pdf file content by pdf file path- used
	 *         by another function manually
	 * @param: filePath = path of the pdf which will be read
	 * @return: String - returns the content of the pdf
	 */
	private static String readPdfFile(String filePath) throws Exception {
		failMsg = "Not able to read pdf file- " + filePath;
		String page = null;
		try {
			if (filePath == null) {
				glb_Logger_commonlogs
						.error("The file path passed is null. Hence halting the execution...");
				Exceptions m_Exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions);
			}
			Assert.assertNotNull(filePath,
					"The file path passed is null. Hence halting the execution...");
			PdfReader reader = new PdfReader(filePath);
			page = PdfTextExtractor.getTextFromPage(reader, 1);
			glb_Logger_commonlogs.info("Successfully read pdf file: "
					+ filePath);
		} catch (CommonFunctionsExceptions ex) {
			glb_Logger_commonlogs.error(ex.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error(e.getMessage());
		}
		return page;
	}

	/**
	 * @author Rajdeep date: December -7th date of review: Description: This
	 *         function is used download pdf from current window url into local
	 *         system.
	 * @param: no parameter
	 * @return: String - PDF download path
	 */
	private static String downloadPdfFromCurrentWindowUrl() throws IOException {
		String pdfDownloadPath = null;
		try {
			String urlString = glb_Webdriver_driver.getCurrentUrl();
			pdfDownloadPath = System.getProperty("user.dir") + "\\test.pdf";
			URL url = new URL(urlString);
			InputStream in = url.openStream();
			Files.copy(in, Paths.get(pdfDownloadPath),
					StandardCopyOption.REPLACE_EXISTING);
			in.close();
		} catch (Exception e) {
			glb_Logger_commonlogs.error(e.getMessage());
		}
		return pdfDownloadPath;
	}

	/**
	 * @author Rajdeep date: December -7th date of review: Description: This
	 *         function is used to verify a text present in a pdf or not
	 * @param: v_str_object = null, v_str_data = text to be searched in the pdf
	 * @return: boolean- return true: if text present in the pdf else return
	 *          false
	 */
	public static boolean verifyTextPresentInPdf(String v_str_object,
			String v_str_data) throws Exception {
		boolean m_bln_text_presence = false;
		try {
			glb_Logger_commonlogs
					.info("In verifyTextPresentInPdf() function...");
			failMsg = "Text " + v_str_data + " is not found in pdf";
			Assert.assertNotNull(v_str_data,
					"Text passed is null. Halting executiom...");
			String pdfDownloadPath = downloadPdfFromCurrentWindowUrl();
			String pdfText = readPdfFile(pdfDownloadPath);
			if (pdfText.equals(v_str_data)) {
				glb_Logger_commonlogs.info("Text " + v_str_data
						+ " is found in pdf");
				m_bln_text_presence = true;
			} else {
				glb_Logger_commonlogs.error(failMsg);
			}
		} catch (Exception e) {
			glb_Logger_commonlogs.error(e.getMessage());
		}
		return m_bln_text_presence;
	}

	/**
	 * @author Rajdeep date: October-7th date of review: October-8th
	 *         Description: This function is used to check whether a locator is
	 *         present or not
	 * @param: v_str_object = element locator key mentioned in the excel and
	 *         properties file, v_str_data = empty string ""
	 * @return boolean- true if element is found on the page, else return false
	 * @throws Throwable
	 */
	public static boolean verifyElementPresent(String v_str_object,
			String v_str_data) throws Throwable {
		WebElement m_WebElement_element = null;
		boolean m_bln_element_presence = false;
		try {
			Assert.assertNotNull(v_str_object,
					"The locator passed is null. Hence halting the execution...");
			m_WebElement_element = locateElement(getLocatorFromRepository(v_str_object));
			if (m_WebElement_element != null) {
				glb_Logger_commonlogs.info("Successfully locate element: "
						+ v_str_object);
				m_bln_element_presence = true;
			} else {
				Exceptions m_exception = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
				throw new CommonFunctionsExceptions(m_exception, v_str_object);
			}
		} catch (CommonFunctionsExceptions ex) {
			glb_Logger_commonlogs.error(ex.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error(e.getMessage());
		}
		return m_bln_element_presence;
	}

	/**
	 * @author Rajdeep date: December 7th date of review: Description: This
	 *         function is used to switch to last opened window
	 * @param: v_str_object = null, v_str_data = null
	 * @return: void
	 */
	public static void switchToLatestOpenedWindow(String v_str_object,
			String v_str_data) {
		try {
			glb_Logger_commonlogs
					.info("In switchToLatestOpenedWindow() function...");
			if (glb_Webdriver_driver != null) {
				Set<String> windowIds = glb_Webdriver_driver.getWindowHandles();
				Iterator<String> it = windowIds.iterator();
				while (it.hasNext()) {
					glb_Webdriver_driver.switchTo().window((String) it.next());
				}
			} else {
				Exceptions mExceptions = Exceptions.NULL_WEBDRIVER_EXCEPTION;
				throw new CommonFunctionsExceptions();
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error(e.getMessage());
		}
	}

	/**
	 * @author Rajdeep date: December 7th date of review: Description: This
	 *         function is used to get url of current window and find text in it
	 * @param: v_str_object = null, v_str_data = text to be searched in url
	 * @return: boolean - true if text is found in the url else returns false
	 */
	public static boolean getCurrentWindowURLAndFindText(String v_str_object,
			String v_str_data) {
		boolean m_bln_text_presence = false;
		try {
			glb_Logger_commonlogs
					.info("Inside getCurrentWindowURLAndFindText() function...");
			String windowUrl = glb_Webdriver_driver.getCurrentUrl();
			failMsg = v_str_data + " is not found in url- " + windowUrl;
			if (v_str_data == null) {
				glb_Logger_commonlogs
						.error("v_str_data passed is null. Hence halting execution...");
				Exceptions m_Exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions);
			}
			if (windowUrl.contains(v_str_data)) {
				m_bln_text_presence = true;
				glb_Logger_commonlogs.info("Text " + v_str_data
						+ " is found in the url: " + windowUrl);
			} else {
				glb_Logger_commonlogs.error(failMsg);
			}
		} catch (CommonFunctionsExceptions ex) {
			glb_Logger_commonlogs.error(ex.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error(e.getMessage());
		}
		return m_bln_text_presence;
	}

	/**
	 * @author Rajdeep date: October-7th date of review: October-8th
	 *         Description: This function is used to check whether a locator is
	 *         present or not after waiting 30 seconds
	 * @param: v_str_object = element locator key mentioned in the excel and
	 *         properties file, v_str_data = ""
	 * @return boolean - true if web element is visible within 30 seconds else
	 *         returns false
	 * @throws Throwable
	 * @throws NumberFormatException
	 */
	public static boolean waitForElementToBeVisibleLocatedByXpath(
			String v_str_object, String v_str_data)
			throws NumberFormatException, Throwable {
		WebElement m_WebElement_element = null;
		boolean m_bln_element_presence = false;
		WebDriverWait wait = new WebDriverWait(glb_Webdriver_driver,
				Long.parseLong(ConfigurationReader.getValue("explicit_wait")));
		try {
			glb_Logger_commonlogs
					.info("Inside waitForElementToBeVisible() function...");
			// Null checking - object is mandatory
			if (v_str_object == null) {
				glb_Logger_commonlogs
						.error("v_str_object passed is null. Hence halting execution...");
				Exceptions m_Exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions);
			} else {
				String locatorValue = getLocatorFromRepository(v_str_object);
				String[] arrStr = locatorValue.split(":");
				String xpathExpression = arrStr[1];
				if (xpathExpression.contains("xpath")) {
					m_WebElement_element = wait
							.until(ExpectedConditions
									.presenceOfElementLocated(By
											.xpath(xpathExpression)));
					if (m_WebElement_element != null) {
						glb_Logger_commonlogs.info("Element is visible: "
								+ v_str_object);
						m_bln_element_presence = true;
					} else {
						glb_Logger_commonlogs.error(failMsg);
						Exceptions m_exception = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
						throw new CommonFunctionsExceptions(m_exception,
								v_str_object);
					}
				} else {
					Exceptions mExceptions = Exceptions.INVALID_LOCATOR_TYPE_EXCEPTION;
					throw new CommonFunctionsExceptions(mExceptions,
							v_str_object);
				}
			}
		} catch (CommonFunctionsExceptions ex) {
			glb_Logger_commonlogs.error(ex.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error(e.getMessage());
		}
		return m_bln_element_presence;
	}

	/**
	 * @author Rajdeep date: October-8th date of review: October-8th
	 *         Description: This function is used to move mouse over an element
	 * @param: v_str_object = element locator key mentioned in the excel and
	 *         properties file, v_str_data = ""
	 * @return boolean- true if mouse successfully hover on the element, false
	 *         otherwise
	 * @throws Throwable
	 */
	public static boolean mouseOverElement(String v_str_object,
			String v_str_data) throws Throwable {
		boolean m_bln_element_mouseOver = false;
		List<WebElement> m_WebElement_element = null;
		try {
			glb_Logger_commonlogs
					.info("Inside waitForElementToBeVisible() function...");
			// Null checking - object is mandatory
			if (v_str_object == null) {
				glb_Logger_commonlogs
						.error("v_str_object passed is null. Hence halting execution...");
				Exceptions m_Exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions);
			}
			m_WebElement_element = getObjectFromRepo(v_str_object);
			if (m_WebElement_element == null) {
				glb_Logger_commonlogs.error("Not able to locate element: "
						+ v_str_object);
				Exceptions m_exception = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
				throw new CommonFunctionsExceptions(m_exception, v_str_object);
			} else if (m_WebElement_element != null
					&& m_WebElement_element.size() > 1) {
				glb_Logger_commonlogs
						.error("Multiple elements found. Mouse hover function is applicable for the first element...");
				Exceptions m_exception = Exceptions.COULD_NOT_FIND_UNIQUE_ELEMENT;
				throw new CommonFunctionsExceptions(m_exception, v_str_object);
			}
			Actions act = new Actions(glb_Webdriver_driver);
			act.moveToElement(m_WebElement_element.get(0)).build().perform();
			glb_Logger_commonlogs.info("Mouse successfully moved to element: "
					+ v_str_object);
			m_bln_element_mouseOver = true;
		} catch (CommonFunctionsExceptions ex) {
			glb_Logger_commonlogs.error(ex.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error(e.getMessage());
		}
		return m_bln_element_mouseOver;
	}

	/**
	 * @author mkarthik Description: This function is used to get the text of an
	 *         element
	 * @param locator
	 *            : locator key as specified in object repository
	 * @throws Throwable
	 */
	public static void clearField(String locator) throws Throwable {
		glb_Logger_commonlogs.info("Within clear field function");
		WebElement m_WebElement_element = null;
		try {
			if (locator == null) {
				Exceptions mExceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(mExceptions);
			}
			m_WebElement_element = locateElement(getLocatorFromRepository(locator));
			if (m_WebElement_element != null) {
				m_WebElement_element.clear();
				glb_Logger_commonlogs.info("Cleared webelement field");
			} else {
				Exceptions m_Exceptions = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions, locator);
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs
					.error("Couldn't clear field" + e.getMessage());
		}
	}

	/**
	 * @author mkarthik Description: This function is used to set window size
	 *         using height and width dimensions
	 * @param : object : empty string "" , widthHeight : integer/integer example
	 *        200/300
	 */
	public static void setWindowSize(String object, String widthHeight) {
		String width = null;
		String height = null;
		try {
			if (widthHeight != null) {
				if (widthHeight.contains("/")) {
					String[] m_locator = widthHeight.split("/");
					width = m_locator[0];
					height = m_locator[1];
				}
				if (width == null && height == null)
					glb_Webdriver_driver.manage().window()
							.setPosition(new Point(0, 0));
				int intWidth = Integer.parseInt(width);
				int intHeight = Integer.parseInt(height);
				glb_Webdriver_driver.manage().window()
						.setSize(new Dimension(intWidth, intHeight));
				glb_Logger_commonlogs.info("Window resized");
			} else {
				Exceptions mException = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(mException);
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error("unable to set the window size: "
					+ e.getMessage());
		}
	}

	/**
	 * @author mkarthik Description: This function is used to switch to a frame
	 *         with given id
	 * @param : frameid = locator id in properties file,data=empty string "" ""
	 * @throws Throwable
	 */
	public static void switchToFrameById(String frameId, String data)
			throws Throwable {
		glb_Logger_commonlogs.info("Within function switch frame using ID");
		WebElement m_WebElement_element = null;
		try {
			if (frameId != null) {
				if (getLocatorFromRepository(frameId).contains("id~")) {
					m_WebElement_element = locateElement(getLocatorFromRepository(frameId));
					if (m_WebElement_element != null) {
						glb_Webdriver_driver.switchTo().frame(
								m_WebElement_element);
						glb_Logger_commonlogs.info("Switched to frame");
					} else {
						Exceptions mExceptions = Exceptions.INVALID_LOCATOR_TYPE_EXCEPTION;
						throw new CommonFunctionsExceptions(mExceptions,
								frameId);
					}
				} else {
					Exceptions mExceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
					throw new CommonFunctionsExceptions(mExceptions);
				}
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error("Couldn't switch to frame"
					+ e.getMessage());
		}
	}

	/**
	 * 
	 * @author mkarthik : Description: This function is used to return list of
	 *         elements as per the locator specified
	 * @param locator
	 *            : locator key as specified in object repository : locator key
	 *            from object repository
	 * @return
	 * @throws Throwable
	 */
	private static List<WebElement> getListOfElements(String locator)
			throws Throwable {
		List<WebElement> list1 = null;
		try {
			if (locator == null) {
				Exceptions mExceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(mExceptions, locator);
			} else {
				if (locator.contains("~")) {
					String[] m_locator = locator.split("~");
					String m_locator_type = m_locator[0];
					String m_locator_value = m_locator[1];
					WebDriverWait wait = new WebDriverWait(
							glb_Webdriver_driver,
							Integer.parseInt(ConfigurationReader
									.getValue("explicit_wait")));
					switch (m_locator_type) {
					case "xpath":
						try {
							list1 = glb_Webdriver_driver.findElements(By
									.xpath(m_locator_value));
							if (list1.size() == 0)
								glb_Logger_commonlogs
										.error("None of the elements found using locator. Hence halting the execution...");
							Assert.assertNotNull(
									"None of the elements found using locator. Hence halting the execution...",
									list1);
							glb_Logger_commonlogs
									.info("Elements found using locator");
							return list1;
						} catch (Exception e) {
							glb_Logger_commonlogs
									.error("Couldn't get list of elements"
											+ e.getMessage());
						}
						break;
					case "id":
						try {
							list1 = glb_Webdriver_driver.findElements(By
									.id(m_locator_value));
							if (list1.size() == 0)
								glb_Logger_commonlogs
										.error("None of the elements found using locator. Hence halting the execution...");
							Assert.assertNotNull(
									"None of the elements found using locator. Hence halting the execution...",
									list1);
							glb_Logger_commonlogs
									.info("Elements found using locator");
							return list1;
						} catch (Exception e) {
							glb_Logger_commonlogs
									.error("Couldn't get list of elements"
											+ e.getMessage());
						}
						break;
					case "name":
						try {
							list1 = glb_Webdriver_driver.findElements(By
									.name(m_locator_value));
							if (list1.size() == 0)
								glb_Logger_commonlogs
										.error("None of the elements found using locator. Hence halting the execution...");
							Assert.assertNotNull(
									"None of the elements found using locator. Hence halting the execution...",
									list1);
							glb_Logger_commonlogs
									.info("Elements found using locator");
							return list1;
						} catch (Exception e) {
							glb_Logger_commonlogs
									.error("Couldn't get list of elements"
											+ e.getMessage());
						}
						break;
					case "class":
						try {
							list1 = glb_Webdriver_driver.findElements(By
									.className(m_locator_value));
							if (list1.size() == 0)
								glb_Logger_commonlogs
										.error("None of the elements found using locator. Hence halting the execution...");
							Assert.assertNotNull(
									"None of the elements found using locator. Hence halting the execution...",
									list1);
							glb_Logger_commonlogs
									.info("Elements found using locator");
							return list1;
						} catch (Exception e) {
							glb_Logger_commonlogs
									.error("Couldn't get list of elements"
											+ e.getMessage());
						}
						break;
					case "css":
						try {
							list1 = glb_Webdriver_driver.findElements(By
									.cssSelector(m_locator_value));
							if (list1.size() == 0)
								glb_Logger_commonlogs
										.error("None of the elements found using locator. Hence halting the execution...");
							Assert.assertNotNull(
									"None of the elements found using locator. Hence halting the execution...",
									list1);
							glb_Logger_commonlogs
									.info("Elements found using locator");
							return list1;
						} catch (Exception e) {
							glb_Logger_commonlogs
									.error("Couldn't get list of elements"
											+ e.getMessage());
						}
						break;
					default:
						Exceptions m_exeption = Exceptions.INVALID_LOCATOR_TYPE_EXCEPTION;
						if (m_exeption == Exceptions.INVALID_LOCATOR_TYPE_EXCEPTION) {
							throw new CommonFunctionsExceptions(m_exeption,
									m_locator_type);
						}
					}
				} else {
					Exceptions m_Exceptions = Exceptions.LOCATOR_FORMAT_EXCEPTION;
					throw new CommonFunctionsExceptions(m_Exceptions);
				}
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error("Couldn't get list of elements"
					+ e.getMessage());
		}
		return list1;
	}

	/**
	 * @author mkarthik Description: This function moves cursor to an element
	 *         and clicks on it
	 * @param locator
	 *            : locator key as specified in object repository : The key as
	 *            specified in object repository
	 * @param data
	 *            empty string "" ""
	 * @throws Throwable
	 */
	public static void moveToElementandclickOnElement(String locator,
			String data) throws Throwable {
		glb_Logger_commonlogs
				.info("Within function moveToElement and clickOnElement");
		WebElement m_WebElement_element = null;
		try {
			if (locator == null) {
				Exceptions mExceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(mExceptions, locator);
			} else {
				m_WebElement_element = locateElement(getLocatorFromRepository(locator));
				if (m_WebElement_element != null) {
					Actions act = new Actions(glb_Webdriver_driver);
					act.moveToElement(m_WebElement_element)
							.click(m_WebElement_element).build().perform();
					glb_Logger_commonlogs
							.info("moved and clicke on the element: " + locator);
				} else {
					Exceptions m_Exceptions = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
					throw new CommonFunctionsExceptions(m_Exceptions, locator);
				}
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error("Couldn't click on element "
					+ e.getMessage());
		}
	}

	/**
	 * @author mkarthik Description : This method return the value attribute of
	 *         the radio button which is selected under a parent
	 * @param locator
	 *            : locator key as specified in object repository : Locator of
	 *            the parent under which the radio button is present. The
	 *            locator is a key as specified in object repository
	 * @param data
	 *            : empty string "" ""
	 * @return
	 * @throws Throwable
	 */
	public static String checkSelectedRadioButton(String locator, String data)
			throws Throwable {
		glb_Logger_commonlogs
				.info("Within function check selected radio button");
		WebElement m_WebElement_element = null;
		String value = null;
		String no_value = "None of the radio button is selected";
		try {
			if (locator == null) {
				Exceptions mExceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(mExceptions, locator);
			} else {
				m_WebElement_element = locateElement(getLocatorFromRepository(locator));
				if (m_WebElement_element != null) {
					List<WebElement> childs = m_WebElement_element
							.findElements(By.xpath("//input[@type='radio']"));
					for (WebElement e : childs) {
						if (e.isSelected()) {
							value = e.getAttribute("value");
							glb_Logger_commonlogs
									.info("value of radio button selected: "
											+ value);
							return value;
						} else {
							continue;
						}
					}
				} else {
					Exceptions m_Exceptions = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
					throw new CommonFunctionsExceptions(m_Exceptions, locator);
				}
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs
					.error("Couldn't click on element after mouse over"
							+ e.getMessage());
		}
		return no_value;
	}

	/**
	 * Author: mkarthik Description: To switch control from one window to
	 * another window as per the window title specified
	 * 
	 * @param : object : empty string "" , Window_Title: Title of the window to
	 *        be switched
	 */
	public static void switchToWindowByTitle(String object, String Window_Title) {
		glb_Logger_commonlogs
				.info("Within function switch to wwindow by title");
		try {
			if (Window_Title != null) {
				glb_Logger_commonlogs.info("Window title is not null");
				if (glb_Webdriver_driver != null) {
					Set<String> winHandleBefore = getWindowIds();
					for (String winHandle : winHandleBefore) {
						String act_title = glb_Webdriver_driver.switchTo()
								.window(winHandle).getTitle();
						if (getTitleAndCompare(Window_Title)) {
							glb_Logger_commonlogs
									.info("Switched to window with title : "
											+ Window_Title);
							break;
						}
					}
				} else {
					Exceptions mExceptions = Exceptions.NULL_WEBDRIVER_EXCEPTION;
					throw new CommonFunctionsExceptions();
				}
			} else {
				Exceptions mExceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(mExceptions);
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error("Couldn't switch to given frame");
		}
	}

	/**
	 * @author mkarthik Description : This method is used to return the current
	 *         URL
	 * @return
	 */
	public static String getCurrentUrl() {
		String str_currenet_url = null;
		try {
			if (glb_Webdriver_driver != null) {
				str_currenet_url = glb_Webdriver_driver.getCurrentUrl();
			} else {
				Exceptions mExceptions = Exceptions.NULL_WEBDRIVER_EXCEPTION;
				throw new CommonFunctionsExceptions();
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error("unable to get current url: "
					+ e.getMessage());
		}
		return str_currenet_url;
	}

	/**
	 * @author mkarthik Description : This method is used to select the value
	 *         from drop down by considering all option values
	 * @param : locator for select webelement and value : expected value
	 * @throws Throwable
	 */
	public static boolean selectOptionFromDropDownBasedOnText(String locator,
			String value) throws Throwable {
		boolean m_bln_value_selected = false;
		String act_selection = null;
		try {
			Select m_select = new Select(
					locateElement(glb_Properties_objectRepository
							.getProperty(locator)));
			if (m_select != null) {
				List<WebElement> Optionelement = m_select.getOptions();
				for (WebElement webElement : Optionelement) {
					if (webElement.getText().equals(value)) {
						webElement.click();
						setDelay("5000");
						act_selection = m_select.getFirstSelectedOption()
								.getText();
						break;
					}
				}
				if (act_selection.trim().equals(value)) {
					m_bln_value_selected = true;
					glb_Logger_commonlogs
							.info("Selected the expected value from drop down...");
				} else {
					glb_Logger_commonlogs
							.info("Did not select the expected value from drop down...");
				}
			} else {
				Exceptions m_Exceptions = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions);
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs
					.error("unable to select option from dropdown "
							+ e.getMessage());
		}
		return m_bln_value_selected;
	}

	/**
	 * @author mkarthik Description : This method is used to locate list of
	 *         elements as per the locator specified
	 * @param locator
	 *            : locator key as specified in object repository : The locator
	 *            key specified in object repository
	 * @return
	 * @throws Throwable
	 */
	public static List<WebElement> locateListofElements(String locator)
			throws Throwable {
		List<WebElement> lis_elements = null;
		try {
			if (locator != null) {
				locator = glb_Properties_objectRepository.getProperty(locator);
				if (locator.contains("~")) {
					String[] m_locator = locator.split("~");
					String m_locator_type = m_locator[0];
					String m_locator_value = m_locator[1];
					WebDriverWait wait = new WebDriverWait(
							glb_Webdriver_driver,
							Integer.parseInt(ConfigurationReader
									.getValue("explicit_wait")));
					switch (m_locator_type) {
					case "xpath":
						try {
							wait.until(ExpectedConditions
									.presenceOfAllElementsLocatedBy(By
											.xpath(m_locator_value)));
							lis_elements = glb_Webdriver_driver.findElements(By
									.xpath(m_locator_value));
							if (lis_elements != null) {
								glb_Logger_commonlogs
										.info("List of WebElements located with using xpath : "
												+ m_locator_value);
							}
						} catch (TimeoutException e) {
							glb_Logger_commonlogs.error(e.getMessage());
						} catch (Exception e) {
							glb_Logger_commonlogs.info("Not able to locate"
									+ e.getMessage());
						}
						break;
					case "id":
						try {
							wait.until(ExpectedConditions
									.presenceOfAllElementsLocatedBy(By
											.id(m_locator_value)));
							lis_elements = glb_Webdriver_driver.findElements(By
									.id(m_locator_value));
							if (lis_elements != null) {
								glb_Logger_commonlogs
										.info("List of WebElements located with using id : "
												+ m_locator_value);
							}
						} catch (TimeoutException e) {
							glb_Logger_commonlogs.error(e.getMessage());
						} catch (Exception e) {
							glb_Logger_commonlogs.error(e.getMessage());
						}
						break;
					case "name":
						try {
							wait.until(ExpectedConditions
									.presenceOfAllElementsLocatedBy(By
											.name(m_locator_value)));
							lis_elements = glb_Webdriver_driver.findElements(By
									.name(m_locator_value));
							if (lis_elements != null) {
								glb_Logger_commonlogs
										.info("List of WebElements located with using name : "
												+ m_locator_value);
							}
						} catch (TimeoutException e) {
							glb_Logger_commonlogs.error(e.getMessage());
						} catch (Exception e) {
							glb_Logger_commonlogs.error(e.getMessage());
						}
						break;
					case "class":
						try {
							wait.until(ExpectedConditions
									.presenceOfAllElementsLocatedBy(By
											.className(m_locator_value)));
							lis_elements = glb_Webdriver_driver.findElements(By
									.className(m_locator_value));
							if (lis_elements != null) {
								glb_Logger_commonlogs
										.info("List of WebElements located with using classname : "
												+ m_locator_value);
							}
						} catch (TimeoutException e) {
							glb_Logger_commonlogs.error(e.getMessage());
						} catch (Exception e) {
							glb_Logger_commonlogs.error(e.getMessage());
						}
						break;
					case "css":
						try {
							wait.until(ExpectedConditions
									.presenceOfAllElementsLocatedBy(By
											.cssSelector(m_locator_value)));
							lis_elements = glb_Webdriver_driver.findElements(By
									.cssSelector(m_locator_value));
							if (lis_elements != null) {
								glb_Logger_commonlogs
										.info("List of WebElements located with using css : "
												+ m_locator_value);
							}
						} catch (TimeoutException e) {
							glb_Logger_commonlogs.error(e.getMessage());
						} catch (Exception e) {
							glb_Logger_commonlogs.error(e.getMessage());
						}
						break;
					default:
						Exceptions m_exeption = Exceptions.INVALID_LOCATOR_TYPE_EXCEPTION;
						if (m_exeption == Exceptions.INVALID_LOCATOR_TYPE_EXCEPTION) {
							throw new CommonFunctionsExceptions(m_exeption,
									m_locator_type);
						}
					}
				} else {
					Exceptions m_Exceptions = Exceptions.LOCATOR_FORMAT_EXCEPTION;
					throw new CommonFunctionsExceptions(m_Exceptions);
				}
			} else {
				Exceptions m_Exception = Exceptions.LOCATOR_VALUE_NULL_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exception);
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error(e.getMessage());
		}
		return lis_elements;
	}

	/**
	 * @author mkarthik Description : This method is used to set the delay.
	 * @param : String delay : Should be in milliseconds example for 5 seconds
	 *        "5000"
	 */
	public static void setDelay(String delay) throws InterruptedException {
		Thread.sleep(Long.parseLong(delay));
	}

	/**
	 * @author mkarthik Description : This method is used to launch any url
	 * @param : url : link to be opened
	 * @throws : Throwable
	 */
	public static void openApplication(String url) throws Throwable {
		try {
			if (glb_Webdriver_driver != null) {
				glb_Webdriver_driver.manage().window().maximize();
				glb_Webdriver_driver.get(url);
				waitForPageToLoad();
				getCommonFunctionsInstance().waitForPageToBeReady();
				glb_Logger_commonlogs.info("opened the required page..");
			} else {
				Exceptions m_exceptions = Exceptions.NULL_WEBDRIVER_EXCEPTION;
				throw new CommonFunctionsExceptions();
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error("unable to open required page...");
		}
	}

	/**
	 * @author mkarthik Description : This method is used to get the current
	 *         page title and compare it with expected text
	 * @param expected_title
	 *            : The expected title value
	 * @return true if title matches expected value or false if title doesnt
	 *         match the expected value
	 */
	public static boolean getTitleAndCompare(String expected_title) {
		boolean bln_value = false;
		try {
			if (expected_title != null) {
				if (glb_Webdriver_driver != null) {
					String m_str_act_titile = glb_Webdriver_driver.getTitle();
					if (m_str_act_titile != null) {
						bln_value = compareTexts(m_str_act_titile,
								expected_title);
						if (bln_value == true) {
							glb_Logger_commonlogs.info("title matches....");
						} else {
							glb_Logger_commonlogs
									.info("title doesnt matches....expected: "
											+ expected_title + " actual: "
											+ m_str_act_titile);
						}
					} else {
						glb_Logger_commonlogs.info("Could not get title...");
					}
				} else {
					Exceptions mExceptions = Exceptions.NULL_WEBDRIVER_EXCEPTION;
					throw new CommonFunctionsExceptions();
				}
			} else {
				Exceptions mExceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(mExceptions);
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error("unable to get title and compare "
					+ e.getMessage());
		}
		return bln_value;
	}

	/**
	 * Author: mkarthik Description: To switch control from page content to a
	 * frame with given frame title. Closing browsers would be done
	 * automatically hence this method should not be called
	 */
	public static void closesAllWindows(String window, String data) {
		glb_Logger_commonlogs.info("Within function to close all windows");
		try {
			if (glb_Webdriver_driver != null) {
				Set<String> winHandleBefore = glb_Webdriver_driver
						.getWindowHandles();
				for (String winHandle : winHandleBefore) {
					glb_Webdriver_driver.switchTo().window(winHandle).close();
					glb_Logger_commonlogs.info("Closed window with handle as"
							+ winHandle);
				}
			} else {
				Exceptions mExceptions = Exceptions.NULL_WEBDRIVER_EXCEPTION;
				throw new CommonFunctionsExceptions();
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error("Could not close all window handles");
		}
	}

	/**
	 * @author mkarthik Description: Navigate back to previous page
	 * @param : object empty string "" ""
	 * @param : data : empty string "" ""
	 * @throws Throwable
	 */
	public static void navigateBack(String object, String data)
			throws Throwable {
		glb_Logger_commonlogs.info("Within fucntion navigate back");
		try {
			String currentURL = null;
			String newURL = null;
			if (glb_Webdriver_driver != null) {
				currentURL = glb_Webdriver_driver.getCurrentUrl();
				glb_Webdriver_driver.navigate().back();
				waitForPageToLoad();
				commonFunctions.waitForPageToBeReady();
				newURL = glb_Webdriver_driver.getCurrentUrl();
				if (newURL.equals(currentURL)) {
					glb_Logger_commonlogs
							.error("Browser still at same URL, something went wrong");
					Exceptions mExceptions = Exceptions.NAVIGATION_EXCEPTION;
					throw new CommonFunctionsExceptions(mExceptions);
				} else {
					glb_Logger_commonlogs.info("Browser navigated back");
				}
			} else {
				Exceptions mExceptions = Exceptions.NULL_WEBDRIVER_EXCEPTION;
				throw new CommonFunctionsExceptions();
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error("Browser didnt navigate"
					+ e.getMessage());
		}
	}

	/**
	 * @author mkarthik Description: Clicks forward button on browser
	 * @param : object : empty string "" ""
	 * @param :data : empty string "" ""
	 * @throws Throwable
	 */
	public static void navigateForWard(String object, String data)
			throws Throwable {
		glb_Logger_commonlogs.info("Within fucntion navigateForWard");
		try {
			String currentURL = null;
			String newURL = null;
			if (glb_Webdriver_driver != null) {
				currentURL = glb_Webdriver_driver.getCurrentUrl();
				glb_Webdriver_driver.navigate().forward();
				waitForPageToLoad();
				commonFunctions.waitForPageToBeReady();
				newURL = glb_Webdriver_driver.getCurrentUrl();
				if (newURL.equals(currentURL)) {
					glb_Logger_commonlogs
							.error("Browser still at same URL, something went wrong");
					Exceptions mExceptions = Exceptions.NAVIGATION_EXCEPTION;
					throw new CommonFunctionsExceptions(mExceptions);
				} else {
					glb_Logger_commonlogs.info("Browser navigated forward");
				}
			} else {
				Exceptions mExceptions = Exceptions.NULL_WEBDRIVER_EXCEPTION;
				throw new CommonFunctionsExceptions();
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error("Browser didnt navigate"
					+ e.getMessage());
		}
	}

	/**
	 * @author mkarthik Description: Clears all cookies from browser
	 * @param : object and data : empty string "" ""
	 */
	public static void ClearCookies(String object, String data) {
		try {
			if (glb_Webdriver_driver != null) {
				glb_Logger_commonlogs.info("Within fucntion clear cookies");
				glb_Webdriver_driver.manage().deleteAllCookies();
				glb_Logger_commonlogs.info("Browser cookies cleared");
			} else {
				Exceptions mExceptions = Exceptions.NULL_WEBDRIVER_EXCEPTION;
				throw new CommonFunctionsExceptions();
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error("Couldn't clear browser cookies"
					+ e.getMessage());
		}
	}

	/**
	 * Author: mkarthik Description: To switch from a frame to default page
	 * content
	 * 
	 * @param : no parameters are required. empty string "" needs to be passed
	 */
	public static void switchToDefaultContent(String object, String data) {
		try {
			glb_Logger_commonlogs
					.info("Switching to default content, out from frame");
			if (glb_Webdriver_driver != null) {
				glb_Webdriver_driver.switchTo().defaultContent();
				glb_Logger_commonlogs.info("Switched to default content");
			} else {
				Exceptions mExceptions = Exceptions.NULL_WEBDRIVER_EXCEPTION;
				throw new CommonFunctionsExceptions();
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error("unable to switch to default content "
					+ e.getMessage());
		}
	}

	/**
	 * Author: mkarthik Description: To click on an image using sikuli
	 */
	public static void clickUsingSikuli(String object, String ImagePath)
			throws FindFailed {
		glb_Logger_commonlogs.info("Within function click using sikuli");
		try {
			if (ImagePath != null) {
				Screen Screen = new Screen();
				Screen.find(ImagePath);
				Screen.click();
				glb_Logger_commonlogs.info("Clicked using sikuli");
			} else {
				glb_Logger_commonlogs
						.error("Path of image to be cliked is null");
			}
		} catch (Exception e) {
			glb_Logger_commonlogs
					.error("Not able to click on image using sikuli"
							+ e.getMessage());
		}
	}

	/**
	 * Author: mkarthik Description: To click using elements co-ordinates values
	 * 
	 * @param : locator : locator to locate element as specified in object
	 *        repository
	 * @param : int x, int y: co-cordinates on the element where click action
	 *        would be performed
	 * @throws Throwable
	 */
	public static void clickUsingOffset(String locator, int x, int y)
			throws Throwable {
		try {
			if (locator != null) {
				WebElement element = null;
				element = locateElement(getLocatorFromRepository(locator));
				Actions act = new Actions(glb_Webdriver_driver);
				act.moveToElement(element, x, y).click().build().perform();
				glb_Logger_commonlogs.info("Clicked on location");
			} else {
				Exceptions mException = Exceptions.LOCATOR_VALUE_NULL_EXCEPTION;
				throw new CommonFunctionsExceptions(mException, locator);
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error("Could not locate element"
					+ e.getMessage());
		}
	}

	/**
	 * Author: mkarthik Description: To maximize window
	 * 
	 * @param : Object and data : empty string "" ""
	 */
	public static void maximizeWindow(String object, String data) {
		glb_Logger_commonlogs.info("Within function maximize window");
		try {
			if (glb_Webdriver_driver != null) {
				glb_Webdriver_driver.manage().window().maximize();
				glb_Logger_commonlogs.info("Window maximized");
			} else {
				Exceptions mExceptions = Exceptions.NULL_WEBDRIVER_EXCEPTION;
				throw new CommonFunctionsExceptions();
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error("Unable to maximize the window.."
					+ e.getMessage());
		}
	}

	/**
	 * Author: mkarthik Description: To switch to a frame by locating it based
	 * on locators
	 * 
	 * @throws Throwable
	 */
	public static void switchToFrameAsWebElement(String locator, String data)
			throws Throwable {
		glb_Logger_commonlogs.info("Within function switch to frame by Name");
		try {
			if (locator != null) {
				WebElement fr = null;
				fr = locateElement(glb_Properties_objectRepository
						.getProperty(locator));
				glb_Webdriver_driver.switchTo().frame(fr);
				glb_Logger_commonlogs.info("Switched to frame with name:"
						+ locator);
			} else {
				Exceptions m_Exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions);
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.info("Not able to locate frame"
					+ e.getMessage());
		}
	}

	/**
	 * Author: mkarthik Description: This method locates the parent element and
	 * switches to the frame under the parent element as specified by the index
	 * 
	 * @param : locator : locator key as specified in the object repository
	 *        index : index of the frame under the parent element. Index number
	 *        should be number-1 ie if you want to switch for 2nd frame then
	 *        index should be 1
	 * @throws Throwable
	 */
	public static void switchToFrameByIndexUnderParentWebElement(
			String locator, int index) throws Throwable {
		WebElement element = null;
		try {
			if (locator != null) {
				element = locateElement(getLocatorFromRepository(locator));
				List<WebElement> listOfFrames = element.findElements(By
						.xpath("descendant::iframe"));
				if (listOfFrames.size() != 0) {
					if (index <= listOfFrames.size()) {
						if (index > 0) {
							if (glb_Webdriver_driver != null) {
								glb_Logger_commonlogs
										.info("Within function switch to frame by Index");
								glb_Webdriver_driver.switchTo().frame(
										listOfFrames.get(index));
								glb_Logger_commonlogs
										.info("Switched to frame by Index:"
												+ index);
							} else {
								Exceptions mExceptions = Exceptions.NULL_WEBDRIVER_EXCEPTION;
								throw new CommonFunctionsExceptions();
							}
						} else {
							Exceptions mExceptions = Exceptions.INVALID_VALUES_SENT_AS_PARAMETER;
							throw new CommonFunctionsExceptions(mExceptions,
									index);
						}
					} else {
						Exceptions mException = Exceptions.INVALID_VALUES_SENT_AS_PARAMETER;
						throw new CommonFunctionsExceptions(mException, index);
					}
				} else {
					Exceptions mExceptions = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
				}
			} else {
				Exceptions mExceptions = Exceptions.LOCATOR_VALUE_NULL_EXCEPTION;
				throw new CommonFunctionsExceptions(mExceptions, index);
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.info("Not able to locate frame"
					+ e.getMessage());
		}
	}

	/**
	 * Author: mkarthik Description: This method is used to perform drag and
	 * drop
	 * 
	 * @param v_object
	 *            : Locator key as specified in object repository, source and
	 *            target elements needs to be separated by <>
	 * @param testdata
	 *            : empty string "" ""
	 * 
	 * @throws Throwable
	 */
	public static boolean clickAndHoldSourceAndDropOnTarget(String v_object,
			String testdata) throws Throwable {
		boolean m_bln_dragAndDrop_status = false;
		WebElement m_WebElement_Source = null;
		WebElement m_WebElement_Destination = null;
		String[] locator = null;
		String m_str_source = null;
		String m_str_destination = null;
		Assert.assertNotNull(v_object, "The object passes is null");
		locator = splitLocators(getLocatorFromRepository(v_object));
		int m_int_size = locator.length;
		if (m_int_size == 2) {
			m_str_source = locator[0];
			m_str_destination = locator[1];
		} else {
			glb_Logger_commonlogs
					.info("Expected only 2 elements. But number of elements found: "
							+ m_int_size);
		}
		try {
			Assert.assertNotNull(m_str_source,
					"The source element is null. Hence halting the execution...");
			Assert.assertNotNull(m_str_destination,
					"The destination element is null. Hence halting the execution...");
			if ((m_str_source != null) && (m_str_destination != null)) {
				m_WebElement_Source = locateElement(m_str_source);
				m_WebElement_Destination = locateElement(m_str_destination);
				Assert.assertNotNull(
						"The source element is not located. Hence halting the execution..",
						m_WebElement_Source);
				Assert.assertNotNull(
						"The destination element is not located. Hence halting the execution..",
						m_WebElement_Destination);
				if ((m_WebElement_Source != null)
						&& (m_WebElement_Destination != null)) {
					Actions m_Actions_action = new Actions(glb_Webdriver_driver);
					m_Actions_action.clickAndHold(m_WebElement_Source).build()
							.perform();
					m_Actions_action
							.moveToElement(m_WebElement_Destination, 50, 50)
							.build().perform();
					m_Actions_action.release(m_WebElement_Destination).build()
							.perform();
					m_bln_dragAndDrop_status = true;
					glb_Logger_commonlogs
							.info("Drag and Drop operation completed...");
				} else {
					Exceptions m_Exceptions = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
					if (m_WebElement_Source == null) {
						throw new CommonFunctionsExceptions(m_Exceptions,
								m_str_source);
					} else if (m_WebElement_Destination == null) {
						throw new CommonFunctionsExceptions(m_Exceptions,
								m_str_destination);
					}
				}
			} else {
				Exceptions m_exceptions = Exceptions.SOURCE_DESTINATION_VALUES_NULL_EXCEPTION;
				throw new CommonFunctionsExceptions(m_str_source,
						m_str_destination);
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error(e.getMessage());
		}
		return m_bln_dragAndDrop_status;
	}

	/**
	 * @author mkarthik date: October-20th date of review: Description: This
	 *         function is used to split the string and return the locators if
	 *         more than one elements are passed in single string
	 * @param v_object
	 * @return locators
	 */
	private static String[] splitLocators(String v_object) {
		String[] locators = null;
		try {
			if (v_object != null) {
				if (v_object.contains("<>")) {
					glb_Logger_commonlogs
							.info("More than one locator exists...");
					locators = v_object.split("<>");
				} else {
					glb_Logger_commonlogs.info("Only one locator exists..");
					locators = new String[1];
					locators[0] = v_object;
				}
			} else {
				Exceptions m_exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_exceptions);
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error(e.getMessage());
		}
		return locators;
	}

	/**
	 * @author mkarthik date: October-20th date of review: Description: This
	 *         function is used to split the string and return the testdata if
	 *         more than one string values are passed
	 * @param v_object
	 *            : values in a string seperated by delimiter
	 * @param delimiter
	 *            : Seperator of values in a string
	 * @return array of String
	 */
	public static String[] splitTestData(String v_object, String delimiter) {
		String[] testdata = null;
		try {
			if (v_object != null) {
				if (v_object.contains(delimiter)) {
					glb_Logger_commonlogs
							.info("More than one testdata exists...");
					testdata = v_object.split(",");
				} else {
					glb_Logger_commonlogs.info("Only one testdata exists..");
					testdata = new String[1];
					testdata[0] = v_object;
				}
			} else {
				Exceptions m_exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_exceptions);
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error(e.getMessage());
		}
		return testdata;
	}

	/**
	 * @author mkarthik date: October-20th date of review: Description: This
	 *         function is used to find the attribute and value for an element
	 *         and compare it with executed attribute value
	 * @param v_object
	 *            : locator key as specified in object repositories
	 * @param v_expectedAttributeValue
	 *            : attribute and expected value separated by '='
	 * @return m_bln_attribute_compare
	 * @throws Throwable
	 */
	public static boolean getAttributeAndCompare(String v_object,
			String v_expectedAttributeValue) throws Throwable {
		boolean m_bln_attribute_compare = false;
		String m_str_attribute = null;
		String m_str_value = null;
		String[] testData = null;
		try {
			testData = v_expectedAttributeValue.split("=");
			if (testData.length != 2 || testData.length == 2) {
				m_str_attribute = testData[0];
				m_str_value = testData[1];
				String m_act_value = getAttributeOfElement(v_object,
						m_str_attribute);
				if (compareTexts(m_str_value, m_act_value.trim())) {
					m_bln_attribute_compare = true;
					glb_Logger_commonlogs.info("Attribute value matching...");
				} else {
					glb_Logger_commonlogs
							.info("Attribute value are not matching...");
				}
			} else {
				Exceptions mExceptions = Exceptions.INVALID_NUMBER_OF_VALUES;
				throw new CommonFunctionsExceptions(mExceptions);
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error("Unable to get attribute and compare: "
					+ e.getMessage());
		}
		return m_bln_attribute_compare;
	}

	/**
	 * @author mkarthik description : This method is used to get the value of
	 *         the specified attribute for an element and verify if the value
	 *         contains expected value
	 * @param v_object
	 *            : locator as specifed in object repository
	 * @param v_expectedAttributeValue
	 *            : attribute and expected value seperated by "="
	 * @return
	 * @throws Throwable
	 */
	public static boolean getAttributeAndVerifyActualValueContainsExpectedValue(
			String v_object, String v_expectedAttributeValue) throws Throwable {
		boolean m_bln_attribute_compare = false;
		String m_str_attribute = null;
		String m_str_value = null;
		String[] testData = null;
		try {
			if (v_expectedAttributeValue != null) {
				if (v_expectedAttributeValue.contains("=")) {
					testData = v_expectedAttributeValue.split("=");
					m_str_attribute = testData[0];
					m_str_value = testData[1];
					String m_act_value = getAttributeOfElement(v_object,
							v_expectedAttributeValue);
					if (m_act_value.contains(m_str_value)) {
						m_bln_attribute_compare = true;
						glb_Logger_commonlogs
								.info("Expected attribute and value is available..");
					}
				} else {
					glb_Logger_commonlogs
							.info("attribute and value not sent in expected format..."
									+ v_expectedAttributeValue);
					Exceptions mExceptions = Exceptions.INVALID_VALUES_SENT_AS_PARAMETER;
					throw new CommonFunctionsExceptions(mExceptions,
							v_expectedAttributeValue);
				}
			} else {
				Exceptions mExceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(mExceptions);
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs
					.error("could not verify if attribute value contains expected value due to: "
							+ e.getMessage());
		}
		return m_bln_attribute_compare;
	}

	/**
	 * @author mkarthik date: October-21st date of review: Description: This
	 *         function is used to verify if the specified web element is
	 *         displayed
	 * @param v_str_object
	 *            : locator key as specified in object repository
	 * @param v_str_testData
	 *            : empty string "" ""
	 * @return m_bln_display
	 * @throws Throwable
	 */
	public static boolean isElementDisplayed(String v_str_object,
			String v_str_testData) throws Throwable {
		boolean m_bln_display = false;
		WebElement m_WebElement_element = null;
		String[] locators = null;
		String m_str_locator = null;
		String locate = null;
		try {
			Assert.assertNotNull(v_str_object, "The object passes is null");
			locators = splitLocators(getLocatorFromRepository(v_str_object));
			int m_int_size = locators.length;
			if (m_int_size == 1 && m_int_size > 0) {
				m_str_locator = locators[0];
				Assert.assertNotNull(m_str_locator,
						"The value passed is null. Hence halting the execution...");
				if (m_str_locator != null) {
					m_WebElement_element = locateElement(m_str_locator);
					if (m_WebElement_element != null) {
						m_bln_display = m_WebElement_element.isDisplayed();
						if (m_bln_display == true) {
							glb_Logger_commonlogs
									.info("Element is displayed..");
						} else {
							glb_Logger_commonlogs
									.info("Element not displayed.."
											+ m_str_locator);
						}
					} else {
						Exceptions m_Exceptions = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
						throw new CommonFunctionsExceptions(m_Exceptions,
								m_str_locator);
					}
				}
			} else {
				Exceptions mExceptions = Exceptions.COULD_NOT_FIND_UNIQUE_ELEMENT;
				throw new CommonFunctionsExceptions(mExceptions);
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs
					.error("could not verify if element is displayed.."
							+ e.getMessage());
		}
		return m_bln_display;
	}

	/**
	 * @author mkarthik date: October-21st date of review: Description: This
	 *         function is used to verify if the specified web element is
	 *         enabled
	 * @param v_str_object
	 * @param v_str_testData
	 * @return m_bln_enabled
	 * @throws Throwable
	 */
	public static boolean isElementEnabled(String v_str_object,
			String v_str_testData) throws Throwable {
		boolean m_bln_enabled = false;
		WebElement m_WebElement_element = null;
		String[] locators = null;
		String m_str_locator = null;
		String locate = null;
		try {
			Assert.assertNotNull(v_str_object, "The object passes is null");
			locators = splitLocators(getLocatorFromRepository(v_str_object));
			int m_int_size = locators.length;
			if (m_int_size == 1 && m_int_size > 0) {
				m_str_locator = locators[0];
				Assert.assertNotNull(m_str_locator,
						"The value passed is null. Hence halting the execution...");
				if (m_str_locator != null) {
					m_WebElement_element = locateElement(m_str_locator);
					if (m_WebElement_element != null) {
						m_bln_enabled = m_WebElement_element.isEnabled();
						if (m_bln_enabled == true) {
							glb_Logger_commonlogs.info("Element is enabled..");
						} else {
							glb_Logger_commonlogs.info("Element not enabled.."
									+ m_str_locator);
						}
					} else {
						Exceptions m_Exceptions = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
						throw new CommonFunctionsExceptions(m_Exceptions,
								m_str_locator);
					}
				}
			} else {
				Exceptions mExceptions = Exceptions.COULD_NOT_FIND_UNIQUE_ELEMENT;
				throw new CommonFunctionsExceptions(mExceptions);
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs
					.error("could not verify if element is displayed.."
							+ e.getMessage());
		}
		return m_bln_enabled;
	}

	/**
	 * author : mkarthik Description : This method is used to click accept on
	 * the alert
	 * 
	 * @param v_str_obj
	 *            and v_test_data : empty string "" ""
	 * @return click_accept_on_alert
	 */
	public boolean clickAcceptOnAlert(String v_str_obj, String v_test_data) {
		boolean click_accept_on_alert = false;
		try {
			if (glb_Webdriver_driver != null) {
				Alert m_alert = glb_Webdriver_driver.switchTo().alert();
				if (m_alert != null) {
					m_alert.accept();
					click_accept_on_alert = true;
					glb_Logger_commonlogs.info("Clicked Accept on Alert...");
				} else {
					Exceptions m_exeption = Exceptions.ALERT_NOT_AVAILABLE;
					throw new CommonFunctionsExceptions(m_exeption);
				}
			} else {
				Exceptions mExceptions = Exceptions.NULL_WEBDRIVER_EXCEPTION;
				throw new CommonFunctionsExceptions();
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.getMessage());
		} catch (Exception e) {
			glb_Logger_commonlogs.error("unable to click on accept.."
					+ e.getMessage());
		}
		return click_accept_on_alert;
	}

	/**
	 * author : mkarthik Description : This method is used to click cancel on
	 * the alert
	 * 
	 * @param v_str_obj
	 *            and v_test_data : empty string "" ""
	 * @return click_cancel_on_alert
	 */
	public boolean clickCancelOnAlert(String v_str_obj, String v_test_data) {
		boolean click_cancel_on_alert = false;
		try {
			if (glb_Webdriver_driver != null) {
				Alert m_alert = glb_Webdriver_driver.switchTo().alert();
				if (m_alert != null) {
					m_alert.dismiss();
					click_cancel_on_alert = true;
					glb_Logger_commonlogs.info("Clicked dismiss on Alert...");
				} else {
					Exceptions m_exeption = Exceptions.ALERT_NOT_AVAILABLE;
					throw new CommonFunctionsExceptions(m_exeption);
				}
			} else {
				Exceptions mExceptions = Exceptions.NULL_WEBDRIVER_EXCEPTION;
				throw new CommonFunctionsExceptions();
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.getMessage());
		} catch (Exception e) {
			glb_Logger_commonlogs.error("unable to click on dismiss.."
					+ e.getMessage());
		}
		return click_cancel_on_alert;
	}

	/**
	 * author : mkarthik Description : This method is used to get the text on
	 * the alert
	 * 
	 * @param v_str_obj
	 *            and v_test_data : empty string "" ""
	 * @return click_cancel_on_alert
	 */
	private String getPopUpMessageFromAlert(String v_str_obj, String v_test_data) {
		boolean get_pop_up_msg = false;
		String m_str_alert_msg = null;
		try {
			if (glb_Webdriver_driver != null) {
				Alert m_alert = glb_Webdriver_driver.switchTo().alert();
				if (m_alert != null) {
					m_str_alert_msg = m_alert.getText();
					if (m_str_alert_msg != null) {
						get_pop_up_msg = true;
						glb_Logger_commonlogs.info("Got the text on Alert...");
					} else {
						glb_Logger_commonlogs
								.error("No message displayed on Alert...");
						Exceptions m_exeption = Exceptions.NO_TEXT_FOUND;
						throw new CommonFunctionsExceptions(m_exeption);
					}
				} else {
					Exceptions m_exeption = Exceptions.ALERT_NOT_AVAILABLE;
					throw new CommonFunctionsExceptions(m_exeption);
				}
			} else {
				Exceptions mExceptions = Exceptions.NULL_WEBDRIVER_EXCEPTION;
				throw new CommonFunctionsExceptions();
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.getMessage());
		} catch (Exception e) {
			glb_Logger_commonlogs.error("unable to get the alert message : "
					+ e.getMessage());
		}
		return m_str_alert_msg;
	}

	/**
	 * author : mkarthik Description : This method compare the text on pop up
	 * with expected value.
	 * 
	 * @param v_str_obj
	 * @param v_str_testdata
	 * @return m_compare_text
	 */
	public boolean getAlertMessageAndCompare(String v_str_obj,
			String v_str_testdata) {
		boolean m_compare_text = false;
		try {
			if (v_str_testdata != null) {
				String m_str_act_message = getPopUpMessageFromAlert("", "");
				if (m_str_act_message != null) {
					m_compare_text = compareTexts(m_str_act_message,
							v_str_testdata);
				}
			} else {
				Exceptions m_exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_exceptions);
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error("could not get the text from alert: "
					+ e.getMessage());
		}
		return m_compare_text;
	}

	/**
	 * author : mkarthik Description : This method is used to handle the Basic
	 * authentication pop up
	 * 
	 * @param m_str_username
	 * @param m_str_password
	 * @return authenticate
	 * @throws CommonFunctionsExceptions
	 */
	public boolean authenticate_pop_up(String m_str_username,
			String m_str_password) throws CommonFunctionsExceptions {
		boolean authenticate = false;
		try {
			Alert m_alert = glb_Webdriver_driver.switchTo().alert();
			if (m_alert != null) {
				m_alert.authenticateUsing((Credentials) new UsernamePasswordCredentials(
						m_str_username, m_str_password));
				authenticate = true;
			} else {
				Exceptions m_exceptions = Exceptions.ALERT_NOT_AVAILABLE;
				throw new CommonFunctionsExceptions(m_exceptions);
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.getMessage());
		}
		return authenticate;
	}

	/**
	 * author : mkarthik Description : This method is used to perform double
	 * click action on the webelement as specifed by the image using sikuli.
	 * 
	 * @param ImagePath
	 * @throws FindFailed
	 */
	public boolean doubleclick_using_sikuli(String ImagePath) throws FindFailed {
		boolean m_double_click = false;
		try {
			if (ImagePath != null) {
				Screen Screen = new Screen();
				Screen.find(ImagePath);
				Screen.doubleClick();
				m_double_click = true;
				glb_Logger_commonlogs
						.info("doubleclick_using_sikuli using sikuli");
			} else {
				Exceptions m_exception = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_exception);
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs
					.error("Not able to click on image using sikuli"
							+ e.getMessage());
		}
		return m_double_click;
	}

	/**
	 * author : mkarthik Description : This method is used to highlight the
	 * element using sikuli
	 * 
	 * @param ImagePath
	 * @throws FindFailed
	 */
	public void highlight_Using_Sikuli(String ImagePath) throws FindFailed {
		try {
			if (ImagePath != null) {
				Screen screen = new Screen();
				Pattern pattern = new Pattern(ImagePath).similar(0.8f);
				Region region = screen.exists(pattern);
				region.highlight(5);
			} else {
				Exceptions m_exception = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_exception);
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error("Not able to highlight using sikuli"
					+ e.getMessage());
		}
	}

	/**
	 * author : mkarthik Description : This method is used to perform drag and
	 * drop using sikuli
	 * 
	 * @param v_str_obj
	 * @param v_str_testdata
	 * @return
	 * @throws FindFailed
	 */
	public boolean clickAndHoldMoveToAndDropUsingSikuli(String v_str_obj,
			String v_str_testdata) throws FindFailed {
		boolean m_drag_drop = false;
		try {
			glb_Logger_commonlogs
					.info("In clickAndHoldMoveToAndDropUsingSikuli()..");
			if (v_str_testdata != null) {
				String[] imagesPath = v_str_testdata.split(",");
				if (imagesPath.length != 2) {
					glb_Logger_commonlogs
							.info("In clickAndHoldMoveToAndDropUsingSikuli()..");
					Exceptions m_exceptions = Exceptions.INVALID_NUMBER_OF_VALUES;
					throw new CommonFunctionsExceptions(m_exceptions);
				} else {
					Screen screen = new Screen();
					Pattern src_pattern = new Pattern(imagesPath[0])
							.similar(0.8f);
					Region src_region = screen.exists(src_pattern);
					src_region.drag(src_pattern);
					Pattern dest_pattern = new Pattern(imagesPath[1])
							.similar(0.8f);
					Region dest_region = screen.exists(dest_pattern);
					dest_region.dropAt(dest_pattern);
					m_drag_drop = true;
					glb_Logger_commonlogs
							.info("In clickAndHoldMoveToAndDropUsingSikuli()..");
					glb_Logger_commonlogs.info("Drag and drop completed..");
				}
			} else {
				Exceptions m_exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_exceptions);
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs
					.info("In clickAndHoldMoveToAndDropUsingSikuli()..");
			glb_Logger_commonlogs.error(e.getMessage());
		}
		return false;
	}

	/**
	 * author: mkarthik Description : This method is used to perform double
	 * click operation on the webelement as specifed by locator
	 * 
	 * @param v_str_locator
	 *            : locator key as specified in Object reprository
	 * @param v_test_data
	 *            : empty string "" : ""
	 * @return
	 * @throws Throwable
	 */
	public boolean doubleClickOnElement(String v_str_locator, String v_test_data)
			throws Throwable {
		boolean m_click_double_status = false;
		try {
			if (v_str_locator != null) {
				WebElement m_webElement = locateElement(getLocatorFromRepository(v_str_locator));
				if (m_webElement != null) {
					Actions m_actions = new Actions(glb_Webdriver_driver);
					m_actions.doubleClick(m_webElement).build().perform();
					m_click_double_status = true;
					glb_Logger_commonlogs
							.info("Double clicked on element with locator..."
									+ "v_str_locator");
				} else {
					Exceptions m_Exceptions = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
					throw new CommonFunctionsExceptions(m_Exceptions,
							v_str_locator);
				}
			} else {
				Exceptions m_Exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions);
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error(e.getMessage());
		}
		return m_click_double_status;
	}

	/**
	 * author : mkarthik Descripton : This method is used to click on a element
	 * for a specified number of time
	 * 
	 * @param v_str_locator
	 *            : locator key as specified in the object repository
	 * @param v_str_testdata
	 *            : number of time the element needs to be clicked
	 * @return
	 * @throws Throwable
	 */
	public boolean clickOnElementForNumberOfTimes(String v_str_locator,
			String v_str_testdata) throws Throwable {
		boolean m_click_state = false;
		int m_int_currentCount = 0;
		try {
			if ((v_str_locator != null) || (v_str_testdata != null)) {
				WebElement m_element = locateElement(getLocatorFromRepository(v_str_locator));
				int m_int_numberOfClick = Integer.parseInt(v_str_testdata);
				if (m_element != null) {
					for (int i = 0; i < m_int_numberOfClick; i++) {
						if (m_int_currentCount == m_int_numberOfClick) {
							m_click_state = true;
						}
						m_element.click();
						setDelay("3000");
						m_int_currentCount = m_int_currentCount + 1;
					}
					if (m_click_state == false) {
						glb_Logger_commonlogs
								.info("Didnt click on the element for the number of times: "
										+ v_str_testdata);
					} else {
						glb_Logger_commonlogs
								.info("Completed clicking on element for the specified number of time..");
					}
				} else {
					Exceptions m_Exceptions = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
					throw new CommonFunctionsExceptions(m_Exceptions,
							v_str_locator);
				}
			} else {
				if (v_str_locator == null) {
					Exceptions m_Exceptions = Exceptions.LOCATOR_VALUE_NULL_EXCEPTION;
					throw new CommonFunctionsExceptions(m_Exceptions);
				}
				if (v_str_testdata == null) {
					Exceptions mExceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
					throw new CommonFunctionsExceptions(mExceptions);
				}
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs
					.error("could not click on element for specified number of time..."
							+ e.getMessage());
		}
		return m_click_state;
	}

	/**
	 * author : mkarthik Descripton : This method is used to click on a element
	 * for a specified number of time using JS
	 * 
	 * @param v_str_locator
	 *            : locator key as specified in the object repository
	 * @param v_str_testdata
	 *            : number of time the element needs to be clicked
	 * @return
	 * @throws Throwable
	 */
	public boolean clickOnElementForNumberOfTimesUisngJS(String v_str_locator,
			String v_str_testdata) throws Throwable {
		boolean m_click_state = false;
		int m_int_currentCount = 0;
		try {
			if ((v_str_locator != null) || (v_str_testdata != null)) {
				WebElement m_element = locateElement(getLocatorFromRepository(v_str_locator));
				int m_int_numberOfClick = Integer.parseInt(v_str_testdata);
				if (m_element != null) {
					for (int i = 0; i < m_int_numberOfClick; i++) {
						if (m_int_currentCount == m_int_numberOfClick) {
							m_click_state = true;
						}
						clickOnElementUisngJS(m_element);
						setDelay("3000");
						m_int_currentCount = m_int_currentCount + 1;
					}
					if (m_click_state == false) {
						glb_Logger_commonlogs
								.info("Didnt click on the element for the number of times: "
										+ v_str_testdata);
					} else {
						glb_Logger_commonlogs
								.info("Completed clicking on element for the specified number of time..");
					}
				} else {
					Exceptions m_Exceptions = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
					throw new CommonFunctionsExceptions(m_Exceptions,
							v_str_locator);
				}
			} else {
				if (v_str_locator == null) {
					Exceptions m_Exceptions = Exceptions.LOCATOR_VALUE_NULL_EXCEPTION;
					throw new CommonFunctionsExceptions(m_Exceptions);
				}
				if (v_str_testdata == null) {
					Exceptions mExceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
					throw new CommonFunctionsExceptions(mExceptions);
				}
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs
					.error("could not click on element for specified number of time..."
							+ e.getMessage());
		}
		return m_click_state;
	}

	/**
	 * author : mkarthik Description : This method is used to click on the
	 * webElement using JavaScriptExecutor
	 * 
	 * @param m_element
	 *            : element on which the click operation needs to be performed
	 */
	private void clickOnElementUisngJS(WebElement m_element) {
		try {
			if (m_element != null) {
				((JavascriptExecutor) CommonFunctions.glb_Webdriver_driver)
						.executeScript("arguments[0].click();", m_element);
				glb_Logger_commonlogs.info("Clicked on element...");
			} else {
				Exceptions m_exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_exceptions);
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error(e.getMessage());
		}
	}

	/**
	 * author : mkarthik description : this method is used to click on the web
	 * element as specified by the locator
	 * 
	 * @param v_str_locator
	 * @param v_str_testdata
	 * @return
	 * @throws Throwable
	 */
	public boolean clickUsingJS(String v_str_locator, String v_str_testdata)
			throws Throwable {
		boolean m_click = false;
		try {
			if (v_str_locator != null) {
				WebElement m_element = locateElement((glb_Properties_objectRepository
						.getProperty(v_str_locator)));
				clickOnElementUisngJS(m_element);
				m_click = true;
			} else {
				Exceptions m_exception = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_exception);
			}
		} catch (Exception e) {
			glb_Logger_commonlogs.error(e.getMessage());
		}
		return m_click;
	}

	/*
      * 
      */
	public boolean enterText_using_sikuli(String ImagePath, String testdata)
			throws FindFailed {
		boolean m_enter_text = false;
		try {
			if ((ImagePath != null) || (testdata != null)) {
				Screen Screen = new Screen();
				Screen.find(ImagePath);
				Screen.type(testdata);
				m_enter_text = true;
				glb_Logger_commonlogs.info("Entered text using sikuli");
			} else {
				Exceptions m_exception = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_exception);
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error("Not able to enter text using sikuli"
					+ e.getMessage());
		}
		return m_enter_text;
	}

	/**
	 * @author mkarthik date: October-21st date of review: Description: This
	 *         function is used to verify if the specified web element is
	 *         selected
	 * @param v_str_object
	 *            : locator key as specified in object repository
	 * @param v_str_testData
	 *            : Empty string ""
	 * @return m_bln_enabled
	 * @throws Throwable
	 */
	public static boolean isElementSelected(String v_str_object,
			String v_str_testData) throws Throwable {
		boolean m_bln_selected = false;
		WebElement m_WebElement_element = null;
		String[] locators = null;
		String m_str_locator = null;
		try {
			Assert.assertNotNull(v_str_object, "The object passes is null");
			locators = splitLocators(v_str_object);
			int m_int_size = locators.length;
			if (m_int_size == 1) {
				m_str_locator = locators[0];
				if (m_str_locator != null) {
					m_WebElement_element = locateElement(getLocatorFromRepository(m_str_locator));
					if (m_WebElement_element != null) {
						m_bln_selected = m_WebElement_element.isSelected();
						if (m_bln_selected == true) {
							glb_Logger_commonlogs.info("Element is Selected..");
						} else {
							glb_Logger_commonlogs
									.info("Element is not Selected..");
						}
					} else {
						Exceptions m_Exceptions = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
						throw new CommonFunctionsExceptions(m_Exceptions,
								m_str_locator);
					}
				} else {
					glb_Logger_commonlogs
							.info("Expected only 1 element. But number of elements found: "
									+ m_int_size);
				}
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs
					.error("Unable to verify is element is selected.."
							+ e.getMessage());
		}
		return m_bln_selected;
	}

	/**
	 * @author mkarthik date: October-21st date of review: Description: This
	 *         function is navigate to the specified link
	 * @param v_str_object
	 *            : empty string "" ""
	 * @param v_str_testdata
	 *            : link to which navigation is to be done
	 * @return m_bln_navigate
	 * @throws Throwable
	 * @throws IOException
	 */
	public static boolean navigateToLink(String v_str_object,
			String v_str_testdata) throws Throwable {
		boolean m_bln_navigate = false;
		int m_int_status = 0;
		try {
			if (v_str_testdata != null) {
				m_int_status = sendGet("", v_str_testdata);
				Assert.assertEquals("The response code is not 200",
						m_int_status, 200);
				if (m_int_status == 200) {
					if (glb_Webdriver_driver != null) {
						glb_Webdriver_driver.navigate().to(v_str_testdata);
						waitForPageToLoad();
						commonFunctions.waitForPageToBeReady();
						m_bln_navigate = true;
						glb_Logger_commonlogs.info("Navigated to page..");
					} else {
						Exceptions mExceptions = Exceptions.NULL_WEBDRIVER_EXCEPTION;
						throw new CommonFunctionsExceptions();
					}
				} else {
					Exceptions m_exceptions = Exceptions.INVALID_URL_EXCEPTION;
					throw new CommonFunctionsExceptions(m_int_status,
							v_str_testdata);
				}
			} else {
				Exceptions m_exceptions = Exceptions.NULL_URL_EXCEPTION;
				throw new CommonFunctionsExceptions(v_str_testdata);
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error("unable to navigate to link.."
					+ e.getMessage());
		}
		return m_bln_navigate;
	}

	/**
	 * @author mkarthik date: October-21th date of review: Description: This
	 *         function is used to implicitly wait for the specified time for
	 *         the elements in the page to load up
	 * @throws Throwable
	 * @throws IOException
	 */
	private static void implicitwait() throws Throwable {
		try {
			if (glb_Webdriver_driver != null
					&& ConfigurationReader.getValue("implicit_wait_timout") != null) {
				glb_Webdriver_driver
						.manage()
						.timeouts()
						.implicitlyWait(
								Long.parseLong(ConfigurationReader
										.getValue("implicit_wait_timout")),
								TimeUnit.MINUTES);
				glb_Logger_commonlogs.info("Impilicit wait completed..");
			} else {
				if (glb_Webdriver_driver == null) {
					Exceptions mExceptions = Exceptions.NULL_WEBDRIVER_EXCEPTION;
					throw new CommonFunctionsExceptions();
				}
				if (ConfigurationReader.getValue("implicit_wait_timout") == null) {
					glb_Logger_commonlogs
							.info("the value of timeout is not specified..");
					Exceptions mExceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
					throw new CommonFunctionsExceptions(mExceptions);
				}
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error("could not wait implicitly.."
					+ e.getMessage());
		}
	}

	/**
	 * @author mkarthik date: October-21th date of review: Description: This
	 *         function is used to WAIT for the entire page to load up in the
	 *         specified time as per the global config "page_load_timeout_value"
	 *         in MINUTES
	 * @throws Throwable
	 */
	public static void waitForPageToLoad() throws Throwable {
		try {
			if (glb_Webdriver_driver != null
					&& ConfigurationReader.getValue("page_load_timeout_value") != null) {
				glb_Webdriver_driver
						.manage()
						.timeouts()
						.pageLoadTimeout(
								Long.parseLong(ConfigurationReader
										.getValue("page_load_timeout_value")),
								TimeUnit.MINUTES);
				glb_Logger_commonlogs.info("Wait for Page load completed..");
			} else {
				if (glb_Webdriver_driver == null) {
					Exceptions mExceptions = Exceptions.NULL_WEBDRIVER_EXCEPTION;
					throw new CommonFunctionsExceptions();
				}
				if (ConfigurationReader.getValue("page_load_timeout_value") == null) {
					glb_Logger_commonlogs
							.info("the value of timeout is not specified..");
					Exceptions mExceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
					throw new CommonFunctionsExceptions(mExceptions);
				}
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error("could not wait for page to load..."
					+ e.getMessage());
		}
	}

	/**
	 * @author mkarthik date: October-21th date of review: Description: This
	 *         function is used refreshing the current page
	 * @throws Throwable
	 */
	public void refreshPage() throws Throwable {
		try {
			if (glb_Webdriver_driver != null) {
				glb_Webdriver_driver.navigate().refresh();
				waitForPageToLoad();
				waitForPageToBeReady();
				glb_Logger_commonlogs.info("Refresh page completed..");
			} else {
				Exceptions mExceptions = Exceptions.NULL_WEBDRIVER_EXCEPTION;
				throw new CommonFunctionsExceptions();
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error("could not refresh the page..."
					+ e.getMessage());
		}
	}

	/**
	 * @author Narendra Prasad date: October 26th 2015 date of review:
	 *         Description: This method will initialize the browser specified in
	 *         the external data source.
	 * @throws Throwable
	 */
	public static void openBrowser(String v_str_object, String v_str_data)
			throws Throwable {
		String path;
		glb_Logger_commonlogs.info("The webdriver to be initialized is : "
				+ v_str_data);
		try {
			switch (v_str_data) {
			case "Mozilla":
				glb_Webdriver_driver = new FirefoxDriver();
				glb_Logger_commonlogs.info(v_str_data
						+ " webdriver is initialized...");
				break;
			case "IE":
				path = System.getProperty("user.dir")
						+ "\\BrowserDrivers\\IEDriverServer.exe";
				System.setProperty("webdriver.ie.driver", path);
				glb_Webdriver_driver = new InternetExplorerDriver();
				glb_Logger_commonlogs.info(v_str_data
						+ " webdriver is initialized...");
				break;
			case "Chrome":
				path = System.getProperty("user.dir")
						+ "\\BrowserDrivers\\chromedriver.exe";
				System.setProperty("webdriver.chrome.driver", path);
				glb_Webdriver_driver = new ChromeDriver();
				glb_Logger_commonlogs.info(v_str_data
						+ " webdriver is initialized...");
				break;
			case "Opera":
				path = System.getProperty("user.dir")
						+ "\\BrowserDrivers\\opera.exe";
				System.setProperty("webdriver.opera.driver", path);
				glb_Webdriver_driver = new OperaDriver();
				glb_Logger_commonlogs.info(v_str_data
						+ " webdriver is initialized...");
				break;
			case "Safari":
				path = System.getProperty("user.dir")
						+ "\\BrowserDrivers\\safari.exe";
				System.setProperty("webdriver.safari.driver", path);
				glb_Webdriver_driver = new SafariDriver();
				glb_Logger_commonlogs.info(v_str_data
						+ " webdriver is initialized...");
				break;
			default:
				glb_Logger_commonlogs.info("invalid name of driver");
				break;
			}
			glb_Webdriver_driver.manage().window().maximize();
			implicitwait();
		} catch (Exception e) {
			glb_Logger_commonlogs.error("Not able to open the Browser --- "
					+ e.getMessage());
			String failMsg = "Not able to open the Browser --- " + v_str_data
					+ " -- Error Description: " + e.getMessage();
		}
	}

	/**
	 * @author Narendra Prasad date: October 26th 2015 date of review:
	 *         Description: This method will navigate to the URL specified in
	 *         the external data source
	 * @param v_str_object
	 *            : empty string "" ""
	 * @param v_str_data
	 *            : URL to be navigated to
	 * @throws Throwable
	 */
	public static void navigate(String v_str_object, String v_str_data)
			throws Throwable {
		try {
			if (v_str_data != null) {
				glb_Logger_commonlogs.info("Navigating to URL..");
				if (glb_Webdriver_driver != null) {
					glb_Webdriver_driver.manage().timeouts()
							.implicitlyWait(10, TimeUnit.SECONDS);
					glb_Webdriver_driver.navigate().to(v_str_data);
					waitForPageToLoad();
					commonFunctions.waitForPageToBeReady();
				} else {
					Exceptions mExceptions = Exceptions.NULL_WEBDRIVER_EXCEPTION;
					throw new CommonFunctionsExceptions();
				}
			} else {
				Exceptions mExceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(mExceptions);
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error("Not able to navigate --- "
					+ e.getMessage());
			String failMsg = "Not able to navigate to--- " + v_str_data
					+ " -- Error Description: " + e.getMessage();
		}
	}

	/**
	 * @author Narendra Description : This method is used to click on the
	 *         locator specified
	 * @param v_str_object
	 *            : locator key as pecifed in Object Repository
	 * @param v_str_data
	 *            :empty string ""
	 * @throws Throwable
	 */
	public static void click(String v_str_object, String v_str_data)
			throws Throwable {
		try {
			glb_Logger_commonlogs.info("Clicking on Webelement.."
					+ v_str_object);
			List<WebElement> m_WebElement_elements = getObjectFromRepo(v_str_object);
			if (m_WebElement_elements == null) {
				glb_Logger_commonlogs.error("No WebElement found: "
						+ v_str_object);
			}
			if (m_WebElement_elements.size() > 1) {
				glb_Logger_commonlogs
						.warn("Click function works on only 1 WebElement...");
			}
			if (m_WebElement_elements != null) {
				m_WebElement_elements.get(0).click();
				glb_Logger_commonlogs.info("Successfully clicked on: "
						+ v_str_object);
			}
		} catch (Exception e) {
			glb_Logger_commonlogs.error("Not able to click " + v_str_object);
		}
	}

	/**
	 * @author Rajdeep date: December 6th date of review: Description: This
	 *         method will enter password after decryption in the text field
	 *         v_str_object
	 * @param: v_str_object = element locator key mentioned in the excel and
	 *         properties file, v_str_data = encrypted password to be entered in
	 *         the field
	 * @return: void
	 * @throws Throwable
	 */
	public static void inputPassword(String v_str_object, String v_str_data)
			throws Throwable {
		try {
			if (v_str_object == null) {
				Exceptions m_Exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions);
			}
			List<WebElement> m_WebElement_elements = getObjectFromRepo(v_str_object);
			if (m_WebElement_elements == null) {
				Exceptions m_exception = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
				throw new CommonFunctionsExceptions(m_exception, v_str_object);
			} else if (m_WebElement_elements != null
					&& m_WebElement_elements.size() > 1) {
				glb_Logger_commonlogs
						.warn("Click function works on only 1 WebElement. Considering first element..");
			}
			m_WebElement_elements.get(0).clear();
			byte[] encodedByte = v_str_data.getBytes();
			byte[] decodedByte = Base64.decode(encodedByte);
			String decodedString = new String(decodedByte);
			m_WebElement_elements.get(0).sendKeys(decodedString);
			glb_Logger_commonlogs.info("Successfully input password on: "
					+ v_str_object);
		} catch (CommonFunctionsExceptions ex) {
			glb_Logger_commonlogs.error(ex.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error("unable to enter password.. "
					+ e.getMessage());
		}
	}

	/**
	 * @author Narendra Prasad date: October 26th 2015 date of review:
	 *         Description: This method will enter data in the object specified
	 *         in the external data source.
	 * @param v_str_object
	 *            : locator key as specified in object repository
	 * @param v_str_data
	 *            : text to be entered
	 * @throws Throwable
	 */
	public static void inputTextIntoField(String v_str_object, String v_str_data)
			throws Throwable {
		try {
			glb_Logger_commonlogs.info("Entering the text in " + v_str_object);
			List<WebElement> m_WebElement_elements = getObjectFromRepo(v_str_object);
			if (m_WebElement_elements == null) {
				Exceptions mExceptions = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
				throw new CommonFunctionsExceptions(mExceptions, v_str_object);
			}
			if (m_WebElement_elements.size() > 1) {
				glb_Logger_commonlogs
						.warn("Click function works on only 1 WebElement...");
			}
			if (m_WebElement_elements != null) {
				m_WebElement_elements.get(0).clear();
				m_WebElement_elements.get(0).sendKeys(v_str_data);
				glb_Logger_commonlogs.info("Successfully input text on: "
						+ v_str_object);
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error("unable to enter text.."
					+ e.getMessage());
		}
	}

	/**
	 * @author Rajdeep date: October-12th date of review: Description: This
	 *         function is used to get all texts from auto-suggest list
	 * @param locatorSerachBox
	 *            : locator key for the text field as specified in object
	 *            repository
	 * @param locatorAutoCompleteDropdown
	 *            : locator key for the drop down as specified in object
	 *            repository
	 * @param textToSearch
	 *            : text to be entered into locatorSearchBox
	 * @param by
	 *            : By object which is used to find the list of values under
	 *            drop down
	 * @return
	 * @throws Throwable
	 */
	public List<String> getTextFromAutoComplete(String locatorSerachBox,
			String locatorAutoCompleteDropdown, String textToSearch, By by)
			throws Throwable {
		List<String> textsFromDropDown = new ArrayList<String>();
		List<WebElement> elementsFromDropDown = new ArrayList<WebElement>();
		List<WebElement> m_WebElement_searchBox = null;
		List<WebElement> m_WebElement_AutoCompleteDropdown = null;
		try {
			if (locatorSerachBox == null) {
				Exceptions m_Exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions);
			}
			if (locatorAutoCompleteDropdown == null) {
				Exceptions m_Exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions);
			}
			if (textToSearch == null) {
				Exceptions m_Exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions);
			}
			m_WebElement_searchBox = getObjectFromRepo(locatorSerachBox);
			if (m_WebElement_searchBox == null) {
				Exceptions m_exception = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
				throw new CommonFunctionsExceptions(m_exception,
						locatorSerachBox);
			} else if (m_WebElement_searchBox.size() > 1) {
				glb_Logger_commonlogs
						.error("Multiple elements passed. Considering first element...");
			}
			m_WebElement_searchBox.get(0).sendKeys(textToSearch);
			m_WebElement_AutoCompleteDropdown = getObjectFromRepo(locatorAutoCompleteDropdown);
			if (m_WebElement_AutoCompleteDropdown == null) {
				Exceptions m_exception = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
				throw new CommonFunctionsExceptions(m_exception,
						locatorAutoCompleteDropdown);
			} else if (m_WebElement_AutoCompleteDropdown.size() > 1) {
				glb_Logger_commonlogs
						.error("Multiple elements passed. Considering first element...");
			}
			if (by == null) {
				Exceptions mExceptions = Exceptions.By_is_null;
				throw new CommonFunctionsExceptions(mExceptions);
			} else {
				elementsFromDropDown = m_WebElement_AutoCompleteDropdown.get(0)
						.findElements(by);
				for (WebElement element : elementsFromDropDown) {
					textsFromDropDown.add(element.getText());
				}
			}
		} catch (CommonFunctionsExceptions ex) {
			glb_Logger_commonlogs.error(ex.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error(e.getMessage());
		}
		return textsFromDropDown;
	}

	/**
	 * @author Rajdeep date: October-12th date of review: Description: This
	 *         function is used to locate a link using linkText or
	 *         partialLinkText
	 * @param: v_str_object = empty string "" "", v_str_data = null
	 * @return: WebElement found using link text
	 */
	public static WebElement locateLinkElementByPartialTextOrExactText(
			String v_str_object, String v_str_data) {
		WebElement m_WebElement_element = null;
		try {
			if (v_str_data == null) {
				Exceptions m_Exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions);
			}
			try {
				m_WebElement_element = glb_Webdriver_driver.findElement(By
						.linkText(v_str_data));
			} catch (Exception e) {
				Exceptions m_exception = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
				// throw new CommonFunctionsExceptions(m_exception,
				// v_str_object);
			}
			if (m_WebElement_element == null) {
				m_WebElement_element = glb_Webdriver_driver.findElement(By
						.partialLinkText(v_str_data));
				if (m_WebElement_element == null) {
					Exceptions m_exception = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
					throw new CommonFunctionsExceptions(m_exception,
							v_str_object);
				}
			}
			glb_Logger_commonlogs.info("Link is successfully located: "
					+ v_str_object);
		} catch (CommonFunctionsExceptions ex) {
			glb_Logger_commonlogs.error(ex.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs
					.error("could not locate link with link text or partial link text..."
							+ e.getMessage());
		}
		return m_WebElement_element;
	}

	/**
	 * @author Rajdeep date: October-12th date of review: Description: This
	 *         function is used to locate an element using tagName
	 * @param: v_str_object = tag name of the element, v_str_data = empty string
	 *         ""
	 * @return: WebElement found using tag name
	 */
	public static WebElement getElementByTagName(String v_str_object,
			String v_str_dat) {
		WebElement element = null;
		List<WebElement> m_WebElement_element = null;
		try {
			if (v_str_object == null) {
				Exceptions m_Exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions);
			}
			m_WebElement_element = glb_Webdriver_driver.findElements(By
					.tagName(v_str_object));
			if (m_WebElement_element == null
					|| m_WebElement_element.size() == 0) {
				Exceptions m_exception = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
				throw new CommonFunctionsExceptions(m_exception);
			} else if (m_WebElement_element.size() > 1) {
				glb_Logger_commonlogs
						.warn("expected onely one element..looks like more element match the given tag..Hence returning only first element..");
				element = m_WebElement_element.get(0);
			} else if (m_WebElement_element.size() == 1) {
				glb_Logger_commonlogs
						.info("Successfully located element by tag name: "
								+ v_str_object);
				element = m_WebElement_element.get(0);
			}
		} catch (CommonFunctionsExceptions ex) {
			glb_Logger_commonlogs.error(ex.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error(e.getMessage());
		}
		return element;
	}

	/**
	 * @author Rajdeep date: October-12th date of review: Description: This
	 *         function is used to deselect all selections in a dropdown
	 * @param: v_str_object = element locator key mentioned in the excel and
	 *         properties file, v_str_data = null
	 * @return: void
	 * @throws Throwable
	 */
	public static void deselectAllFromDropDown(String v_str_object,
			String v_str_data) throws Throwable {
		List<WebElement> m_WebElement_element = null;
		try {
			if (v_str_object == null) {
				glb_Logger_commonlogs
						.error("v_str_object passed is null. Hence halting execution...");
				Exceptions m_Exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions);
			}
			m_WebElement_element = getObjectFromRepo(v_str_object);
			if (m_WebElement_element == null) {
				Exceptions m_exception = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
				throw new CommonFunctionsExceptions(m_exception, v_str_object);
			} else if (m_WebElement_element != null
					&& m_WebElement_element.size() > 1) {
				glb_Logger_commonlogs
						.info("Multiple elements found. This function works only on 1 element. Considering first element.");
			}
			Select select = new Select(m_WebElement_element.get(0));
			select.deselectAll();
		} catch (CommonFunctionsExceptions ex) {
			glb_Logger_commonlogs.error(ex.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs
					.error("unable to deselect All values From DropDown..."
							+ e.getMessage());
		}
	}

	/**
	 * @author Rajdeep date: October-20th date of review: Description: This
	 *         function fetches locator value from properties file using locator
	 *         key - used internally by other methods
	 * @param locator
	 *            : locator key as specified in object repository Key mentioned
	 *            in the properties file
	 * @return locator Value for the corresponding locator key in properties
	 *         file
	 */
	private static String getLocatorFromRepository(String locatorKey) {
		String m_locatorValue = null;
		try {
			if (locatorKey == null) {
				glb_Logger_commonlogs
						.error("locatorKey passed is null. Hence halting execution...");
				Exceptions m_Exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions);
			}
			Assert.assertNotNull(locatorKey,
					"locatorKey passed is null. Hence halting execution...");
			m_locatorValue = glb_Properties_objectRepository
					.getProperty(locatorKey);
			if (m_locatorValue == null) {
				glb_Logger_commonlogs
						.error("Locator value is not found in properties file for locator key: "
								+ locatorKey);
				Exceptions m_exception = Exceptions.COULD_NOT_FIND_LOCATOR_FROM_REPOSITORY;
				throw new CommonFunctionsExceptions(m_exception, locatorKey);
			}
			glb_Logger_commonlogs
					.info("Locator value is found in properties file for locator key: "
							+ locatorKey);
		} catch (CommonFunctionsExceptions ex) {
			glb_Logger_commonlogs.error(ex.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error(e.getMessage());
		}
		return m_locatorValue;
	}

	/**
	 * @author Rajdeep date: 25-Feb-2016 date of review: Description: This
	 *         function takes two images as input and compares them
	 * @param Image1_Path
	 *            : Path of image 1
	 * @param Image2_Path
	 *            : Path of Image 2
	 * @return boolean- true is both the images match, false otherwise
	 */
	public static boolean compareImages(String Image1_Path, String Image2_Path) {
		boolean flag = false;
		try {
			if (Image1_Path == null) {
				glb_Logger_commonlogs
						.error("Image1 Path passed is null. Hence halting execution...");
				Exceptions m_Exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions);
			}
			if (Image2_Path == null) {
				glb_Logger_commonlogs
						.error("Image2 Path passed is null. Hence halting execution...");
				Exceptions m_Exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions);
			}
			Image img1 = Toolkit.getDefaultToolkit().getImage(Image1_Path);
			Image img2 = Toolkit.getDefaultToolkit().getImage(Image2_Path);
			PixelGrabber pg1 = new PixelGrabber(img1, 0, 0, -1, -1, false);
			PixelGrabber pg2 = new PixelGrabber(img2, 0, 0, -1, -1, false);
			int[] pixelImg1 = null;
			int[] pixelImg2 = null;
			int height1 = 0;
			int height2 = 0;
			int width1 = 0;
			int width2 = 0;
			if (pg1.grabPixels()) {
				height1 = pg1.getHeight();
				width1 = pg1.getWidth();
				pixelImg1 = new int[height1 * width1];
				pixelImg1 = (int[]) pg1.getPixels();
			}
			if (pg2.grabPixels()) {
				height2 = pg2.getHeight();
				width2 = pg2.getWidth();
				pixelImg2 = new int[height2 * width2];
				pixelImg2 = (int[]) pg2.getPixels();
			}
			if ((height1 == height2) || (width1 == width2)) {
				if (Arrays.equals(pixelImg1, pixelImg2)) {
					flag = true;
					glb_Logger_commonlogs.info("Images are same.");
				} else {
					glb_Logger_commonlogs.error("Images are not same.");
				}
			} else {
				glb_Logger_commonlogs.error("Images are not same.");
			}
		} catch (CommonFunctionsExceptions ex) {
			glb_Logger_commonlogs.error(ex.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error("unable to compare two images.."
					+ e.getMessage());
		}
		return flag;
	}

	/**
	 * @author Rajdeep date: 25-Feb-2016 date of review: Description: This
	 *         function moves slider across slidingbar with offset x,y
	 * @param String
	 *            v_str_object = locator key of the object repository for slider
	 * @param v_str_data
	 *            = in the format x,y String
	 * @return void
	 * @throws Throwable
	 */
	public static void sliderAndSlidingBar(String v_str_object,
			String v_str_data) throws Throwable {
		try {
			if (v_str_object == null) {
				Exceptions m_Exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions);
			}
			if (v_str_data == null) {
				Exceptions m_Exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions);
			}
			List<WebElement> elements = getObjectFromRepo(v_str_object);
			if (elements == null) {
				Exceptions m_exception = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
				throw new CommonFunctionsExceptions(m_exception, v_str_object);
			}
			WebElement slider = elements.get(0);
			String[] offset = v_str_data.split(",");
			if (offset.length == 2) {
				Actions act = new Actions(glb_Webdriver_driver);
				act.clickAndHold(slider)
						.moveByOffset(Integer.parseInt(offset[0]),
								Integer.parseInt(offset[1])).release(slider)
						.build().perform();
				glb_Logger_commonlogs.info("performed slide action...");
			} else {
				glb_Logger_commonlogs
						.warn("Please provide proper offset data in the format: x,y");
			}
		} catch (CommonFunctionsExceptions ex) {
			glb_Logger_commonlogs.error(ex.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs
					.error("unable to perform slide or sliderbar.."
							+ e.getMessage());
		}
	}

	/**
	 * @author Rajdeep date: 26-Feb-2016 date of review: Description: This
	 *         function switches the webdriver control to a window
	 * @param String
	 *            v_str_object = null, String v_str_data = window number .
	 *            example incase of 2 window tabs parent tab is 0,new tab is 1.
	 *            to shift to parent tab use 0, to switch to child tab use 1
	 * @return void
	 */
	public static void switchToWindow(String v_str_object, String v_str_data) {
		try {
			if (v_str_data == null) {
				glb_Logger_commonlogs
						.error("v_str_data passed is null. Hence halting execution...");
				Exceptions m_Exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions);
			}
			Set<String> windowId = getWindowIds();
			String[] windowids = windowId.toArray(new String[windowId.size()]);
			String required_id = windowids[Integer.parseInt(v_str_data)];
			if (windowId.contains(required_id)) {
				glb_Webdriver_driver.switchTo().window(required_id);
			} else {
				Exceptions mExceptions = Exceptions.NO_SUCH_WINDOWID_EXISTS;
				throw new CommonFunctionsExceptions(mExceptions, v_str_data);
			}
		} catch (CommonFunctionsExceptions ex) {
			glb_Logger_commonlogs.error(ex.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error(e.getMessage());
		}
	}

	/**
	 * @author Rajdeep date: 26-Feb-2016 date of review: Description: This
	 *         function switches the webdriver control to a window mentioned by
	 *         window id and find an element in that window
	 * @param String
	 *            v_str_object = locator key in properties file of the object to
	 *            be found in the window, String v_str_data = empty string ""
	 * @return boolean- true: if the object is found in the window, false
	 *         otherwise
	 * @throws Throwable
	 */
	public static boolean switchToWindowAndFindElement(String v_str_object,
			String v_str_data) throws Throwable {
		boolean m_bln_found = false;
		try {
			glb_Logger_commonlogs
					.info("Inside switchToWindowAndFindElement() function...");
			if (v_str_object == null) {
				glb_Logger_commonlogs
						.error("v_str_object passed is null. Hence halting execution...");
				Exceptions m_Exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions);
			}
			if (v_str_data == null) {
				glb_Logger_commonlogs
						.error("v_str_data passed is null. Hence halting execution...");
				Exceptions m_Exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions);
			}
			Set<String> windows = getWindowIds();
			Iterator<String> it = windows.iterator();
			while (it.hasNext()) {
				String windowID = it.next();
				glb_Webdriver_driver.switchTo().window(windowID);
				List<WebElement> objectToFound = getObjectFromRepo(v_str_object);
				if (objectToFound == null) {
					glb_Logger_commonlogs.error("Object: " + v_str_object
							+ " is not found in the window: " + v_str_data);
					Exceptions m_exception = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
					throw new CommonFunctionsExceptions(m_exception,
							v_str_object);
				}
				if (objectToFound != null && objectToFound.size() >= 1) {
					glb_Logger_commonlogs.info("Success!! Object: "
							+ v_str_object + " is found in the window: "
							+ v_str_data);
					m_bln_found = true;
					break;
				}
			}
		} catch (CommonFunctionsExceptions ex) {
			glb_Logger_commonlogs.error(ex.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error(e.getMessage());
		}
		return m_bln_found;
	}

	/**
	 * @author Rajdeep date: 26-Feb-2016 date of review: Description: This
	 *         function waits until an element contains a text
	 * @param String
	 *            v_str_object = element locator key in the object repository,
	 *            String v_str_data = text to contain
	 * @return WebElement
	 * @throws Throwable
	 */
	public static WebElement waitUntilElementContainsText(String v_str_object,
			String v_str_data) throws Throwable {
		List<WebElement> objects = null;
		WebElement element = null;
		try {
			if (v_str_object == null) {
				Exceptions m_Exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions);
			}
			if (v_str_data == null) {
				Exceptions m_Exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions);
			}
			boolean elementPresent = false;
			objects = getObjectFromRepo(v_str_object);
			if (objects == null || objects.size() == 0) {
				Exceptions m_Exceptions = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions, v_str_object);
			} else if (objects != null && objects.size() == 1) {
				WebDriverWait wait = new WebDriverWait(glb_Webdriver_driver,
						Long.parseLong(ConfigurationReader
								.getValue("explicit_wait")));
				elementPresent = wait.until(ExpectedConditions
						.textToBePresentInElementValue(objects.get(0),
								v_str_data));
			} else if (objects != null && objects.size() > 1) {
				glb_Logger_commonlogs
						.error("Two elements found. Cosidering first element.");
				WebDriverWait wait = new WebDriverWait(glb_Webdriver_driver,
						Long.parseLong(ConfigurationReader
								.getValue("explicit_wait")));
				elementPresent = wait.until(ExpectedConditions
						.textToBePresentInElementValue(objects.get(0),
								v_str_data));
			}
			// verifying whether the element contains the text or not
			if (elementPresent == true) {
				// log - element found with text
				glb_Logger_commonlogs.info("Element " + v_str_object
						+ " found with text: " + v_str_data);
				element = objects.get(0);
			} else {
				// log - element could not be found with text
				glb_Logger_commonlogs.error("Element " + v_str_object
						+ " could not be found with text: " + v_str_data);
			}
		} catch (CommonFunctionsExceptions ex) {
			glb_Logger_commonlogs.error(ex.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error(e.getMessage());
		}
		return element;
	}

	/**
	 * @author Rajdeep date: 21-Mar-2016 date of review: Description: This
	 *         function get list of checkboxes and checks them all
	 * @param v_str_object
	 *            = common locator for checkboxes in object repository,
	 *            v_str_data = empty string ""
	 * @return void
	 * @throws Throwable
	 */
	public static void getListOfCheckboxesAndCheck(String v_str_object,
			String v_str_data) throws Throwable {
		try {
			if (v_str_object == null) {
				Exceptions m_Exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions);
			}
			List<WebElement> checkboxes = getListOfElements(getLocatorFromRepository(v_str_object));
			if (checkboxes == null) {
				Exceptions m_Exceptions = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions, v_str_object);
			} else {
				for (WebElement e : checkboxes) {
					e.click();
				}
				glb_Logger_commonlogs
						.info("Successfully checked all checkboxes..."
								+ checkboxes.size()
								+ " checkboxes have been checked.");
			}
		} catch (CommonFunctionsExceptions ex) {
			glb_Logger_commonlogs.error(ex.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error("could not check all checkboxes..."
					+ e.getMessage());
		}
	}

	/**
	 * @author Rajdeep date: 21-Mar-2016 date of review: Description: This
	 *         function selects values from dropdowns, values will be given by
	 *         the user as comma separated
	 * @param v_str_object
	 *            = common locator for dropdowns in object repository,
	 *            v_str_data = comma separated values (value1, value2,
	 *            value3.... so on)
	 * @return void
	 * @throws Throwable
	 */
	public static void getListOfDropdownsAndSelectValues(String v_str_object,
			String v_str_data) throws Throwable {
		try {
			if (v_str_object == null) {
				Exceptions m_Exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions);
			}
			if (v_str_data == null) {
				Exceptions m_Exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions);
			}
			List<WebElement> dropdowns = getListOfElements(getLocatorFromRepository(v_str_object));
			String values[] = v_str_data.split(",");
			List<String> valuesToSelectFromDropdowns = Arrays.asList(values);
			if (dropdowns == null) {
				glb_Logger_commonlogs.error("Not able to locate element: "
						+ v_str_object);
				Exceptions m_Exceptions = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions, v_str_object);
			} else {
				Select s;
				for (int i = 0; i < dropdowns.size(); i++) {
					s = new Select(dropdowns.get(i));
					s.selectByVisibleText(valuesToSelectFromDropdowns.get(i));
				}
				glb_Logger_commonlogs
						.info("Successfully selected all Dropdowns..."
								+ dropdowns.size()
								+ " dropdowns are selected...");
			}
		} catch (CommonFunctionsExceptions ex) {
			glb_Logger_commonlogs.error(ex.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error(e.getMessage());
		}
	}

	/**
	 * @author Rajdeep date: 19-Apr-2016 date of review: Description: This
	 *         function gets multiple objects from a page and compares
	 *         attributes
	 * @param v_str_object
	 *            = common locator for elements in object repository, v_str_data
	 *            = attribute:value pair separated by comma
	 *            (href:val1,class:val2,type:val3...so on)
	 * @throws Throwable
	 *             @ return void
	 */
	public static boolean getListOfElementsAndCompareAttributes(
			String v_str_object, String v_str_data) throws Throwable {
		boolean match = true;
		try {
			if (v_str_object == null) {
				Exceptions m_Exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions);
			}
			if (v_str_data == null) {
				Exceptions m_Exceptions = Exceptions.NULL_VALUE_PASSED_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions);
			}
			List<WebElement> objects = getListOfElements(getLocatorFromRepository(v_str_object));
			if (objects == null) {
				Exceptions m_exception = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
				throw new CommonFunctionsExceptions(m_exception, v_str_object);
			}
			String attributeValueArray[] = v_str_data.split(",");
			if (attributeValueArray.length != objects.size()) {
				glb_Logger_commonlogs
						.error("Number of attribute:value pair is not matched with number of objects found...");
			} else {
				List<String> keyValuePair = new ArrayList<String>();
				for (int i = 0; i < attributeValueArray.length; i++) {
					String a[] = attributeValueArray[i].split(":");
					if (a.length != 2) {
						glb_Logger_commonlogs
								.error("attribute:value pair is given properly.");
						Exceptions mExceptions = Exceptions.INVALID_VALUES_SENT_AS_PARAMETER;
						throw new CommonFunctionsExceptions(mExceptions,
								attributeValueArray[i]);
					}
					String keyValue = a[0] + "/" + a[1];
					keyValuePair.add(keyValue);
				}
				String[] attributesArray = new String[keyValuePair.size()];
				int i = 0;
				for (String keyvalue : keyValuePair) {
					String a[] = keyvalue.split("/");
					attributesArray[i] = a[0];
					i = i + 1;
				}
				String[] valueArray = new String[keyValuePair.size()];
				int j = 0;
				for (String keyvalue : keyValuePair) {
					String a[] = keyvalue.split("/");
					valueArray[j] = a[1];
					j = j + 1;
				}
				for (int k = 0; k < objects.size(); k++) {
					WebElement we = objects.get(k);
					String attribute = attributesArray[k];
					String expectedAttributeValue = valueArray[k];
					String actualAttributeValue = we.getAttribute(attribute);
					if (expectedAttributeValue.equals(actualAttributeValue)) {
						glb_Logger_commonlogs.info("Attribute value for "
								+ attribute + " is matched...");
					} else {
						glb_Logger_commonlogs.error("Attribute value for "
								+ attribute + " is NOT matched...");
						match = false;
					}
				}
			}
		} catch (CommonFunctionsExceptions ex) {
			glb_Logger_commonlogs.error(ex.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs
					.error("unable to get elements and compare its attributes.."
							+ e.getMessage());
		}
		return match;
	}

	/**
	 * @author mkarthik description : this method is used to wait for the page
	 *         to be in readyState
	 */
	public void waitForPageToBeReady() {
		try {
			WebDriverWait wait = new WebDriverWait(glb_Webdriver_driver, 120);
			wait.until(new Function<WebDriver, Boolean>() {
				public Boolean apply(WebDriver driver) {
					return String
							.valueOf(
									((JavascriptExecutor) driver)
											.executeScript("return document.readyState"))
							.equals("complete");
				}
			});
		} catch (Exception e) {
			glb_Logger_commonlogs
					.error("could not wait for page to be ready...");
		}
	}

	/**
	 * @author mkarthik description : selects the dropdown values based on index
	 *         (index starts from 0)
	 * @param locator
	 *            : locator key as specified in object repository
	 * @param index
	 * @return
	 * @throws Throwable
	 */
	public static boolean selectByIndex(String locator, String index)
			throws Throwable {
		boolean m_bln_value_selected = false;
		String act_selection = null;
		try {
			Select m_select = new Select(
					locateElement(glb_Properties_objectRepository
							.getProperty(locator)));
			if (m_select != null) {
				if (index != null) {
					int index_value = Integer.parseInt(index);
					index_value = index_value - 1;
					int number_of_options = m_select.getOptions().size();
					if (index_value > number_of_options) {
						glb_Logger_commonlogs.info("number of values "
								+ number_of_options + " value sent: "
								+ index_value);
						Exceptions m_Exceptions = Exceptions.INVALID_VALUES_SENT_AS_PARAMETER;
						throw new CommonFunctionsExceptions(m_Exceptions);
					} else {
						if (index_value > 0) {
							m_select.selectByIndex(index_value);
							m_bln_value_selected = true;
						} else {
							Exceptions m_Exceptions = Exceptions.INVALID_VALUES_SENT_AS_PARAMETER;
							throw new CommonFunctionsExceptions(m_Exceptions);
						}
					}
				} else {
					Exceptions m_Exceptions = Exceptions.LOCATOR_VALUE_NULL_EXCEPTION;
					throw new CommonFunctionsExceptions(m_Exceptions);
				}
			} else {
				Exceptions m_Exceptions = Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION;
				throw new CommonFunctionsExceptions(m_Exceptions);
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs
					.error("unable to select option from dropdown "
							+ e.getMessage());
		}
		return m_bln_value_selected;
	}

	/**
	 * @author mkarthik description : This method is used to return the window
	 *         id
	 * @return
	 */
	private static Set<String> getWindowIds() {
		Set<String> windowIds = null;
		try {
			if (glb_Webdriver_driver != null) {
				windowIds = glb_Webdriver_driver.getWindowHandles();
			} else {
				Exceptions mExceptions = Exceptions.NULL_WEBDRIVER_EXCEPTION;
				throw new CommonFunctionsExceptions();
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error("could not get the window ids "
					+ e.getMessage());
		}
		return windowIds;
	}

	/**
	 * @author mkarthik Description : This method is used to return the set of
	 *         cookies currently available
	 * @return
	 */
	public static Set<Cookie> getAllCookies() {
		Set<Cookie> setOfCookies = null;
		try {
			if (glb_Webdriver_driver != null) {
				setOfCookies = glb_Webdriver_driver.manage().getCookies();
			} else {
				Exceptions mExceptions = Exceptions.NULL_WEBDRIVER_EXCEPTION;
				throw new CommonFunctionsExceptions();
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs.error("Could not get all cookies.."
					+ e.getMessage());
		}
		return setOfCookies;
	}

	/**
	 * @author mkarthik Description : This function scrolls the current page
	 *         until the exepcted element is available
	 * @param locator
	 *            : locator key as specified in object repository : Loctaor key
	 *            as specified in object repository
	 * @throws Throwable
	 */
	public void scrollUntilElementIsFound(String locator) throws Throwable {
		boolean end_of_page = false;
		WebElement element = null;
		try {
			if (locator != null) {
				if (glb_Webdriver_driver != null) {
					while (end_of_page != true) {
						element = locateElement(getLocatorFromRepository(locator));
						if (element != null) {
							glb_Logger_commonlogs.info("required element "
									+ locator
									+ " found. Hence stopping scrolling...");
							end_of_page = true;
						} else {
							JavascriptExecutor jse = (JavascriptExecutor) glb_Webdriver_driver;
							jse.executeScript("window.scrollBy(0,300)", "");
						}
					}
				} else {
					Exceptions mExceptions = Exceptions.NULL_WEBDRIVER_EXCEPTION;
					throw new CommonFunctionsExceptions();
				}
			} else {
				Exceptions mExceptions = Exceptions.LOCATOR_VALUE_NULL_EXCEPTION;
				throw new CommonFunctionsExceptions(mExceptions);
			}
		} catch (CommonFunctionsExceptions e) {
			glb_Logger_commonlogs.error(e.glb_Exception_Message);
		} catch (Exception e) {
			glb_Logger_commonlogs
					.error("could not scroll to bottom of the page..."
							+ e.getMessage());
		}
	}
}