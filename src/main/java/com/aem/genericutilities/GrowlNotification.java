package com.aem.genericutilities;

/**
 * This class is used for growl notication
 */
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class GrowlNotification {
	/**
	 * Description : This method is used for displaying growl notification
	 * 
	 * @author mkarthik
	 * @param driver
	 * @param msgType
	 *            : ERROR,INFO,WARNING,SUCCESS
	 * @param msg
	 *            : Message that needs to be printed
	 * @throws InterruptedException
	 */
	public void growlNotification(WebDriver driver, String msgType, String msg)
			throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// Check for jQuery on the page, add it if need be
		js.executeScript("if (!window.jQuery) {"
				+ "var jquery = document.createElement('script'); jquery.type = 'text/javascript';"
				+ "jquery.src = 'https://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js';"
				+ "document.getElementsByTagName('head')[0].appendChild(jquery);"
				+ "}");
		Thread.sleep(2000);
		// Use jQuery to add jquery-growl to the page
		js.executeScript("$.getScript('http://the-internet.herokuapp.com/js/vendor/jquery.growl.js')");
		Thread.sleep(3000);
		// Use jQuery to add jquery-growl styles to the page
		js.executeScript("$('head').append('<link rel=\"stylesheet\" href=\"http://the-internet.herokuapp.com/css/jquery.growl.css\" type=\"text/css\" />');");
		Thread.sleep(2000);
		// jquery-growl w/ no frills
		switch (msgType) {
		default:
			infoMessage(js, msg);
			break;
		case "error":
			errorMessage(js, msg);
			break;
		case "success":
			successMessage(js, msg);
			break;
		case "warning":
			warningMessage(js, msg);
			break;
		}
	}

	private void warningMessage(JavascriptExecutor js, String msg)
			throws InterruptedException {
		js.executeScript("$.growl.warning({ title: 'Warning!', message: '"
				+ msg + "' });");
		Thread.sleep(2000);
	}

	private void successMessage(JavascriptExecutor js, String msg)
			throws InterruptedException {
		js.executeScript("$.growl.notice({ title: 'Success', message: '" + msg
				+ "' });");
		Thread.sleep(2000);
	}

	private void errorMessage(JavascriptExecutor js, String msg)
			throws InterruptedException {
		js.executeScript("$.growl.error({ title: 'ERROR', message: '" + msg
				+ "' });");
		Thread.sleep(2000);
	}

	private void infoMessage(JavascriptExecutor js, String msg)
			throws InterruptedException {
		js.executeScript("$.growl({ title: 'info', message: '" + msg + "' });");
		Thread.sleep(2000);
	}
}