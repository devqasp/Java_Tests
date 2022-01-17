package com.java.automation.utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.java.automation.web.driver.factory.DriverManager;
import com.java.automation.web.driver.factory.DriverManagerFactory;

public class WebDriverUtils {

	private static final DriverManager DRIVER_MANAGER;
	private static final Wait<WebDriver> WAIT;
	
	static {

		DRIVER_MANAGER = new DriverManagerFactory().executeDriverManager();
		WAIT =
			new FluentWait<WebDriver>(
				DRIVER_MANAGER.getDriver()
			)
			.withTimeout(
				Duration.ofSeconds(20)
			)
			.pollingEvery(
				Duration.ofSeconds(5)
			)
			.ignoring(NoSuchElementException.class);
	}
	
	public static DriverManager getDriverManager() {

		DriverManager dvrManager = null;

		if (((RemoteWebDriver) DRIVER_MANAGER.getDriver()).getSessionId() == null) {
			return dvrManager;
		} else {
			dvrManager = DRIVER_MANAGER;
			return dvrManager;
		}
	}
	
	public static Wait<WebDriver> getWait() {

		return WAIT;
	}

	public static String getRemoteDriverBrowserName() {

		return ((RemoteWebDriver) DRIVER_MANAGER
			.getDriver())
			.getCapabilities()
			.getBrowserName()
			.toLowerCase();
	}

	public static WebElement getInputListByIndex(int index) {
		//
		return WebDriverUtils
			.getWait()
			.until(
				ExpectedConditions.presenceOfAllElementsLocatedBy(
					By.xpath(
						String.format("//input")
					)
				)
			)
			.get(index - 1);
		//
	}

	public static WebElement getElementListByText(String text) {
		//
		return WebDriverUtils
			.getWait()
			.until(
				ExpectedConditions.presenceOfAllElementsLocatedBy(
					By.xpath(
						String.format("//*[text()[contains(., '%s')]]", text)
					)
				)
			)
			.get(0);
		//
	}
	
	public static void sleepInSeconds(int seconds) {
		//
		try {
			Thread.sleep(
				Duration
					.ofSeconds(seconds)
					.toMillis()
				//
			);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}