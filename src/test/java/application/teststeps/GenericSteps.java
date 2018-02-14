package application.teststeps;

import java.io.File;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import automation.ConfigurationRead.ConfigurationReader;

import com.aem.genericutilities.CommonFunctions;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class GenericSteps {
	
	@Before
 	public void setup() throws Throwable
 	{
		String path=System.getProperty("user.dir")+ConfigurationReader.getValue("latest_report");
 		File fik=new File(path);
 		File [] fi=fik.listFiles();
 		for (File file : fi) {
				file.delete();
		}
 	}
	@After
	public void tearDown(Scenario result)
    {
        /*on failure, embed a screenshot in the test report
        if(result.isFailed())
        {
            byte[] screenshot =  ((TakesScreenshot)CommonFunctions.glb_Webdriver_driver)
                .getScreenshotAs(OutputType.BYTES);
            result.embed(screenshot, "image/png");
        }*/
    }
}
