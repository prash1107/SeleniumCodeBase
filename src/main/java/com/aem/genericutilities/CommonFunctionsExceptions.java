package com.aem.genericutilities;

/**
 * 
 * @author mkarthik
 * 
 *         This class should have all the exceptions which are thrown by the
 *         common functions
 * 
 *         please add description for all the methods defined and declared in
 *         this class
 * 
 */
public class CommonFunctionsExceptions extends Exception {
	/**
	 * @author mkarthik date of creation : October 2nd date of review:
	 *         Description : This exception is used when the url value is null
	 * @param url
	 *            : The url to be opened
	 */
	String glb_Exception_Message = null;

	public CommonFunctionsExceptions(String url) {
		glb_Exception_Message = "The URL parameter is null.." + url;
	}

	/**
	 * @author mkarthik date of creation : October 2nd date of review:
	 *         Description : This exception is used when the response code for
	 *         the url hit is not 200
	 * @param b
	 *            = response code
	 * @param url
	 *            : The url to be opened
	 */
	public CommonFunctionsExceptions(int b, String url) {
		glb_Exception_Message = "The URL parameter is.." + url
				+ " and response code is: " + b;
	}

	/**
	 * @author mkarthik date of creation : October 2nd date of review:
	 *         Description : This exception is used when webdriver object is
	 *         null
	 */
	public CommonFunctionsExceptions() {
		glb_Exception_Message = "The webdriver is not initialized and is null..";
	}

	/**
	 * @author mkarthik date of creation : October 2nd date of review:
	 *         Description : This exception is used when source and destination
	 *         locators are null
	 * @param source
	 *            : locator of the source element
	 * @param destination
	 *            : locator of the destination element
	 */
	public CommonFunctionsExceptions(String source, String destination) {
		glb_Exception_Message = "The source and destination locator values are null..source: "
				+ source + " destination: " + destination;
	}

	/**
	 * @author mkarthik date: October 2nd date of review: Description: This
	 *         exception is thrown when element cannot be located due to invalid
	 *         locator startegy of invalid locator
	 * @param m_exeption
	 * @param m_locator_type
	 */
	public CommonFunctionsExceptions(Exceptions m_exeption, String m_locator) {
		if (m_exeption == Exceptions.INVALID_LOCATOR_TYPE_EXCEPTION) {
			glb_Exception_Message = "Invalid locator type being sent...Locator type sent: "
					+ m_locator;
		} else if (m_exeption == Exceptions.COULD_NOT_LOCATE_ELEMENT_EXCEPTION) {
			glb_Exception_Message = "Element could not be located using locator: "
					+ m_locator;
		} else if (m_exeption == Exceptions.COULD_NOT_FIND_LOCATOR_FROM_REPOSITORY) {
			glb_Exception_Message = "Locator value is not found in properties file for locator key: "
					+ m_locator;
		} else if (m_exeption == Exceptions.NO_SUCH_WINDOWID_EXISTS) {
			glb_Exception_Message = "no such window id exists.." + m_locator;
		} else if (m_exeption == Exceptions.COULD_NOT_FIND_UNIQUE_ELEMENT) {
			glb_Exception_Message = "could not find unique element as per : "
					+ m_locator;
		}
	}

	/**
	 * @author mkarthik date: October 2nd date of review: Description: This
	 *         exception gets called when the string sent to locate the element
	 *         is null
	 * @param m_exeption
	 */
	public CommonFunctionsExceptions(Exceptions m_exeption) {
		if (m_exeption == Exceptions.LOCATOR_VALUE_NULL_EXCEPTION) {
			glb_Exception_Message = "Locator value given is null...";
		} else if (m_exeption == Exceptions.NULL_VALUE_PASSED_EXCEPTION) {
			glb_Exception_Message = "NULL Value passed...";
		} else if (m_exeption == Exceptions.LOCATOR_FORMAT_EXCEPTION) {
			glb_Exception_Message = "Locator string is not of the expected format...";
		} else if (m_exeption == Exceptions.ALERT_NOT_AVAILABLE) {
			glb_Exception_Message = "Alert Not available/displayed...";
		} else if (m_exeption == Exceptions.NO_TEXT_FOUND) {
			glb_Exception_Message = "No text displayed...";
		} else if (m_exeption == Exceptions.INVALID_NUMBER_OF_VALUES) {
			glb_Exception_Message = "Number of expected values didnt match..";
		} else if (m_exeption == Exceptions.INVALID_VALUES_SENT_AS_PARAMETER) {
			glb_Exception_Message = "Invalid values sent as parameter..";
		} else if (m_exeption == Exceptions.NAVIGATION_EXCEPTION) {
			glb_Exception_Message = "There has been a problem in navigation..";
		} else if (m_exeption == Exceptions.By_is_null) {
			glb_Exception_Message = "By is null..";
		}
	}

	public CommonFunctionsExceptions(Exceptions m_exeption, int value) {
		if (m_exeption == Exceptions.INVALID_VALUES_SENT_AS_PARAMETER) {
			glb_Exception_Message = "Invalid value sent as parameter.." + value;
		}
	}
}
