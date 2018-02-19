package application;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = { "src/test/resources/features/" }, plugin = { "com.cucumber.listener.ExtentCucumberFormatter:CucumberReport/LatestResult/report.html" }, format = {
		"pretty", "html: cucumber-html-reports" }, dryRun = false, monochrome = false, glue = {
		"application.teststeps", "browsers" }, tags = { "@now" })
public class RunCukesTest {
}

