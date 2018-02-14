package application.teststeps;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import browsers.BrowserConfigurator;

import com.aem.genericutilities.CommonFunctions;
import com.aem.genericutilities.GrowlNotification;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

public class CommonSteps {
	CommonFunctions commonFunctions = null;
	BrowserConfigurator config = null;
	WebDriver driver = null;
	GrowlNotification notify = null;

	public CommonSteps(CommonFunctions comm, BrowserConfigurator conf,
			GrowlNotification notfication) {
		commonFunctions = comm;
		config = conf;
		driver = BrowserConfigurator.getWebdriver();
		notify = notfication;
	}

	@When("b")
	public void b() throws Throwable {
		CommonFunctions.openApplication("https://jqueryui.com/droppable/");
		commonFunctions.waitForPageToBeReady();
		commonFunctions.switchToFrameAsWebElement("frame", "");
		commonFunctions.dragAndDrop("drag_and_drop_test", "");
		CommonFunctions.switchToDefaultContent("", "");
	}

	@Given("c")
	public void c() throws Throwable {
		CommonFunctions
				.openApplication("https://www.w3schools.com/tags/tryit.asp?filename=tryhtml_select");
		commonFunctions.switchToFrameAsWebElement("iframe_2", "");
		commonFunctions.highlightElement("drop_down_select");
		commonFunctions.selectByVisibleText("drop_down_select", "Opel");
		commonFunctions.highlightElement("drop_down_select");
		CommonFunctions.selectByValue("drop_down_select", "volvo");
		commonFunctions.highlightElement("drop_down_select");
		CommonFunctions.selectOptionFromDropDownBasedOnText("drop_down_select",
				"Saab");
		commonFunctions.highlightElement("drop_down_select");
		commonFunctions.selectByIndex("drop_down_select", "4");
		commonFunctions.switchToDefaultContent("", "");
	}

	@Given("d")
	public void d() throws Throwable {
		CommonFunctions
				.openApplication("https://www.flipkart.com/exchange-store?otracker=hp_banner_widget_0");
		commonFunctions.enterText("enter_name_text", "oneplus 3");
		Thread.sleep(5000);
		CommonFunctions.clearField("enter_name_text");
		Thread.sleep(5000);
		commonFunctions.enterText("enter_name_text", "oneplus 3");
		// commonFunctions.clickUsingJS("click_js", "");
		commonFunctions.clickUsingJS("click_js");
		commonFunctions.waitForPageToLoad();
		commonFunctions.waitForPageToBeReady();
		commonFunctions.navigateBack("", "");
		commonFunctions.waitForPageToLoad();
		commonFunctions.waitForPageToBeReady();
		commonFunctions.scrollElementIntoView("scroll_element");
		commonFunctions.getTextFromElementAndCompare("scroll_element",
				"Contact Us");
	}

	@Given("e")
	public void e() throws Throwable {
		commonFunctions
				.openApplication("https://www.w3schools.com/tags/tryit.asp?filename=tryhtml_link_target");
		commonFunctions.switchToFrameAsWebElement("iframe_2", "");
		CommonFunctions.verifyElementPresent("element_present", "");
		CommonFunctions.click("element_present", "");
		CommonFunctions.switchToDefaultContent("", "");
		CommonFunctions.switchToLatestOpenedWindow("", "");
		CommonFunctions.getCurrentWindowURLAndFindText("", "w3schools");
		commonFunctions.closeBrowser("", "");
		commonFunctions.switchToWindow("", "0");
		commonFunctions.switchToFrameAsWebElement("iframe_2", "");
		CommonFunctions.verifyElementPresent("element_present", "");
		CommonFunctions.click("element_present", "");
		CommonFunctions
				.switchToWindowAndFindElement("element_on_newwindow", "");
		CommonFunctions.click("element_on_newwindow", "");
		commonFunctions.closeBrowser("", "");
		CommonFunctions.switchToWindowByTitle("", "Tryit Editor v3.3");
	}

	@Given("f")
	public void f() throws Throwable {
		CommonFunctions
				.openApplication("https://www.w3schools.com/cssref/tryit.asp?filename=trycss_sel_hover");
		CommonFunctions.waitForElementToBeVisibleLocatedByXpath("iframe_2", "");
		// commonFunctions.switchToFrameAsWebElement("iframe_2","");
		CommonFunctions.switchToFrameById("frameid", "");
		CommonFunctions.highlightElement("element_present");
		CommonFunctions.mouseOverElement("element_present", "");
		CommonFunctions.setWindowSize("", "200/300");
		CommonFunctions.switchToDefaultContent("", "");
		BrowserConfigurator.getWebdriver().manage().window().maximize();
	}

	@Given("g")
	public void g() throws Throwable {
		CommonFunctions
				.openApplication("https://www.w3schools.com/Html/tryit.asp?filename=tryhtml_checkbox");
		CommonFunctions.switchToFrameById("frameid", "");
		CommonFunctions.getListOfCheckboxesAndCheck("chk_box", "");
		System.out.println("selected ????? :"
				+ CommonFunctions.isElementSelected("chk_box_to_check", ""));
		CommonFunctions.click("chk_box_to_check", "");
		System.out.println("selected ????? :"
				+ CommonFunctions.isElementSelected("chk_box_to_check", ""));
		CommonFunctions.switchToDefaultContent("", "");
		CommonFunctions
				.openApplication("https://www.w3schools.com/html/tryit.asp?filename=tryhtml_radio");
		CommonFunctions.switchToFrameById("frameid", "");
		CommonFunctions.click("radio", "");
		CommonFunctions.checkSelectedRadioButton("radio_parent", "");
	}

	@Given("^h$")
	public void h() throws Throwable {
		commonFunctions
				.openApplication("https://www.w3schools.com/tags/tryit.asp?filename=tryhtml_button_test");
		Assert.assertTrue(
				"doesnt match...",
				CommonFunctions
						.getCurrentUrl()
						.equals("https://www.w3schools.com/tags/tryit.asp?filename=tryhtml_button_test"));
		CommonFunctions.switchToFrameById("frameid", "");
		CommonFunctions.moveToElementandclickOnElement("button_mouse", "");
		CommonFunctions.switchToDefaultContent("", "");
	}

	@Given("i")
	public void i() throws Throwable {
		CommonFunctions.openApplication("https://www.flipkart.com/");
		CommonFunctions.setDelay("5000");
		CommonFunctions
				.getTitleAndCompare("Online shopping Site: Shop online for mobiles,electronics,fashion & more @ Flipkart");
		if (CommonFunctions.locateListofElements("listofelement").size() == 8) {
			System.out.println("values are matching...");
		} else {
			System.out.println("values are not matching...");
		}
		CommonFunctions.navigate("", "https://www.flipkart.com/mobiles");
		CommonFunctions.navigateBack("", "");
		CommonFunctions.navigateForWard("", "");
	}

	@Given("j")
	public void j() throws Throwable {
		CommonFunctions.openApplication("https://www.underarmour.com/en-us");
		CommonFunctions.getAllCookies();
		CommonFunctions.ClearCookies("", "");
		CommonFunctions.clickUsingOffset("close", 5, 10);
	}

	@Given("k")
	public void k() throws Throwable {
		BrowserConfigurator.getWebdriver().get(
				"http://timesofindia.indiatimes.com/");
		commonFunctions.waitForPageToLoad();
		commonFunctions.waitForPageToBeReady();
		CommonFunctions.maximizeWindow("", "");
		commonFunctions.scrollUntilElementIsFound("footer");
		commonFunctions.scrollElementIntoView("scroll_to");
		CommonFunctions.switchToFrameByIndexUnderParentWebElement("parent", 22);
	}

	@Given("l")
	public void l() throws Throwable {
		CommonFunctions.openApplication("https://jqueryui.com/droppable/");
		commonFunctions.waitForPageToBeReady();
		commonFunctions.switchToFrameAsWebElement("frame", "");
		System.out.println(CommonFunctions.isElementDisplayed("source", ""));
		System.out.println(CommonFunctions.isElementEnabled("source", ""));
		commonFunctions.getAttributeAndCompare("source", "id=draggable");
		commonFunctions.getAttributeAndCompare("target", "id=droppable");
		commonFunctions.clickAndHoldSourceAndDropOnTarget("drag_and_drop_test",
				"");
		CommonFunctions.switchToDefaultContent("", "");
	}

	@Given("m")
	public void m() throws Throwable {
		CommonFunctions
				.openApplication("https://www.w3schools.com/jsref/tryit.asp?filename=tryjsref_confirm");
		commonFunctions.waitForPageToBeReady();
		CommonFunctions.switchToFrameById("frameid", "");
		CommonFunctions.click("clickbtn", "");
		commonFunctions.getAlertMessageAndCompare("", "Press a button!");
		commonFunctions.clickAcceptOnAlert("", "");
		CommonFunctions.switchToDefaultContent("", "");
		CommonFunctions
				.openApplication("https://www.w3schools.com/jsref/tryit.asp?filename=tryjsref_confirm");
		commonFunctions.waitForPageToBeReady();
		CommonFunctions.switchToFrameById("frameid", "");
		CommonFunctions.click("clickbtn", "");
		commonFunctions.clickCancelOnAlert("", "");
		CommonFunctions.switchToDefaultContent("", "");
	}

	@Given("n")
	public void n() throws Throwable {
		CommonFunctions
				.openApplication("https://www.w3schools.com/jsref/tryit.asp?filename=tryjsref_ondblclick");
		commonFunctions.waitForPageToBeReady();
		CommonFunctions.switchToFrameById("frameid", "");
		commonFunctions.doubleClickOnElement("dbl_click", "");
		CommonFunctions.switchToDefaultContent("", "");
		CommonFunctions
				.openApplication("https://www.w3schools.com/html/tryit.asp?filename=tryhtml5_webstorage_local_clickcount");
		commonFunctions.waitForPageToBeReady();
		CommonFunctions.switchToFrameById("frameid", "");
		commonFunctions.clickOnElementForNumberOfTimes("btn_number_clik", "5");
		CommonFunctions
				.openApplication("https://www.w3schools.com/html/tryit.asp?filename=tryhtml5_webstorage_local_clickcount");
		commonFunctions.waitForPageToBeReady();
		CommonFunctions.switchToFrameById("frameid", "");
		commonFunctions.clickOnElementForNumberOfTimesUisngJS(
				"btn_number_clik", "5");
		CommonFunctions.switchToDefaultContent("", "");
		String values = "Test1,Test2,Test3";
		String valuess[] = CommonFunctions.splitTestData(values, ",");
		for (String string : valuess) {
			System.out.println(string);
		}
	}

	@Given("o")
	public void o() throws Throwable {
		CommonFunctions
				.openApplication("https://www.w3schools.com/css/tryit.asp?filename=trycss_forms");
		CommonFunctions.waitForPageToLoad();
		commonFunctions.waitForPageToBeReady();
		CommonFunctions.switchToFrameById("frameid", "");
		commonFunctions.getAttributeAndVerifyActualValueContainsExpectedValue(
				"txt_field", "id=fn");
		commonFunctions.switchToDefaultContent("", "");
		commonFunctions.refreshPage();
		commonFunctions
				.navigateToLink("",
						"https://www.w3schools.com/css/tryit.asp?filename=trycss_forms");
		CommonFunctions.switchToFrameById("frameid", "");
		CommonFunctions.inputPassword("txt_field", "password");
		CommonFunctions.inputTextIntoField("txt_field_2", "mks");
		commonFunctions.switchToDefaultContent("", "");
	}

	@Given("p")
	public void p() throws Throwable {
		CommonFunctions
				.openApplication("https://www.w3schools.com/tags/tryit.asp?filename=tryhtml_select_multiple");
		CommonFunctions.waitForPageToLoad();
		commonFunctions.waitForPageToBeReady();
		CommonFunctions.switchToFrameById("frameid", "");
		CommonFunctions.selectByVisibleText("drop_down_select", "Volvo");
		CommonFunctions.selectByVisibleText("drop_down_select", "Audi");
		CommonFunctions.deselectAllFromDropDown("drop_down_select", "");
		Thread.sleep(5000);
		commonFunctions.switchToDefaultContent("", "");
		CommonFunctions
				.openApplication("https://www.w3schools.com/html/tryit.asp?filename=tryhtml_links_w3schools");
		CommonFunctions.waitForPageToLoad();
		commonFunctions.waitForPageToBeReady();
		CommonFunctions.switchToFrameById("frameid", "");
		commonFunctions.locateLinkElementByPartialTextOrExactText("", "tut")
				.click();
		commonFunctions.switchToDefaultContent("", "");
	}

	@Given("q")
	public void q() throws Throwable {
		commonFunctions
				.openApplication("https://www.w3schools.com/tags/tryit.asp?filename=tryhtml_input_value");
		CommonFunctions.waitForPageToLoad();
		commonFunctions.waitForPageToBeReady();
		CommonFunctions.switchToFrameById("frameid", "");
		CommonFunctions.waitUntilElementContainsText("input_value", "John");
	}

	@Given("r")
	public void r() throws Throwable {
		CommonFunctions.openApplication("https://jqueryui.com/autocomplete/");
		commonFunctions.waitForPageToBeReady();
		commonFunctions.switchToFrameAsWebElement("autocomplete_frame", "");
		List<String> text = commonFunctions.getTextFromAutoComplete(
				"autocomplete_text", "auto_dropdown", "e",
				By.xpath("descendant::div[not(text()='')]"));
		commonFunctions.switchToDefaultContent("", "");
		System.out.println(commonFunctions.getElementByTagName("a", "")
				.getText());
		CommonFunctions
				.compareImages(
						"C:\\Users\\mkarthik\\Pictures\\chummy\\IMG_20161023_192552 - Copy.jpg",
						"C:\\Users\\mkarthik\\Pictures\\chummy\\IMG_20161023_192552.jpg");
	}

	@Given("s")
	public void s() throws Throwable {
		CommonFunctions
				.openApplication("https://www.w3schools.com/jquerymobile/tryit.asp?filename=tryjqmob_forms_slider");
		CommonFunctions.waitForPageToLoad();
		commonFunctions.waitForPageToBeReady();
		CommonFunctions.switchToFrameById("frameid", "");
		CommonFunctions.sliderAndSlidingBar("slider", "200,200");
		CommonFunctions.switchToDefaultContent("", "");
		CommonFunctions
				.openApplication("file:///C:/Users/mkarthik/Desktop/multipledropdown.html");
		commonFunctions.waitForPageToLoad();
		commonFunctions.waitForPageToBeReady();
		CommonFunctions.getListOfDropdownsAndSelectValues("multiple_select",
				"2,7");
		assertTrue("not matching..",
				CommonFunctions.getListOfElementsAndCompareAttributes(
						"multiple_elements", "id:sel1,id:dome,multiple:true"));
	}
}