package com.java.automation.web.test.execution;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

import com.java.automation.utils.ManagerFileUtils;
import com.java.automation.utils.WebDriverUtils;
import com.java.automation.web.browser.factory.BrowserProperties;
import com.java.automation.web.driver.factory.DriverManager;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

public class WebExecutionTestClass {

	public static void main(String[] args) throws InterruptedException {

		WebDriver driver = WebDriverUtils.getDriverManager().getDriver();

		Wait<WebDriver> wait =
			new FluentWait<WebDriver>(driver)
				.withTimeout(
					Duration.ofSeconds(20)
				)
				.pollingEvery(
					Duration.ofSeconds(5)
				)
				.ignoring(NoSuchElementException.class);

		driver.navigate().to(ManagerFileUtils.getUrlFromJson("Google"));

		if (new BrowserProperties().getBrowserName().contains("iexplorer")) {
			//
			WebElement htmlTest = wait.until((WebDriver dvr) -> dvr.findElement(By.xpath("//html")));
			System.out.println(htmlTest.getAttribute("outerHTML"));
			//
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			jsExecutor.executeScript("document.getElementsByName('q')[0].value = 'selenium documentation';");
			jsExecutor.executeScript("document.getElementsByName('btnK')[0].click();");
			//
		} else {
			//
			WebElement textBox = wait.until((WebDriver dvr) -> dvr.findElement(By.name("q")));
			/*
			WebElement textBox = wait.until(new Function<WebDriver, WebElement>() {

				@Override
				public WebElement apply(WebDriver driver) {
					return driver.findElement(By.name("q"));
				}
			});
			*/
			
			textBox.sendKeys("selenium documentation");
			textBox.sendKeys(Keys.ENTER);
		}

		List<WebElement> links = wait.until(new Function<WebDriver, List<WebElement>>() {
			@Override
			public List<WebElement> apply(WebDriver driver) {
				return driver.findElements(By.xpath("//a/*[contains(., 'Selenium')]"));
			}
		});
		links.get(0).click();

		if (StringUtils.contains(driver.getTitle(), "Selenium"))
			System.out.println("----------PASSOU!!!----------");
		else
			System.err.println("----------FALHOU!!!----------");
		
		driver.manage().deleteAllCookies();
		//
		DriverManager.addDriver(driver);
		DriverManager.closeAndQuitDriver();
	}
}