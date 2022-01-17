package com.java.automation.runner;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.java.automation.utils.ManagerFileUtils;
import com.java.automation.utils.WebDriverUtils;
import com.java.automation.web.driver.factory.DriverManager;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;

@RunWith(Cucumber.class)
@CucumberOptions(
	dryRun = false,
	features = "src/test/resources/features",
	glue =
	{
		"com.java.automation.runner",
		"com.java.automation.web.step.definitions",
		"com.java.automation.api.step.definitions",
		"com.java.automation.api.domain.starwar.root.step.definitions"
	},
	monochrome = true,
	plugin =
	{
        "html:target/cucumber-reports/cucumber.html",
        // -Dcucumber.options="(replace this for 2 hyphens)plugin io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm"
        // Execution command Cucumber Maven, the same thing below in this class...
		"io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm",
        "json:target/cucumber-reports/cucumber.json",
        "junit:target/cucumber-reports/cucumber.xml",
		"pretty",
        "rerun:target/cucumber-reports/rerun.txt",
		"summary"
	},
	snippets = SnippetType.CAMELCASE,
	stepNotifications = true,
	strict = true
)
public class CucumberRunner {
	
	@BeforeClass
	public static void setUp() {
		
		ManagerFileUtils.checkAndGenerateFilePath("src/target");
	}

	@AfterClass
	public static void tearDown() {
		
		List.of(WebHook.getTagNames()).parallelStream().forEach(
			tagName -> {
				if (tagName.contains("@ui")) {
					DriverManager.addDriver(
						WebDriverUtils
							.getDriverManager()
							.getDriver()
						//
					);
					//
					DriverManager.closeAndQuitDriver();
				}
			}
		);
	}
}