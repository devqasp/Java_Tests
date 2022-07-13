package com.java.automation.web.driver.factory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.java.automation.web.browser.factory.BrowserProperties;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public abstract class DriverManager {
	
	private static String browserName;
	
	private static ThreadLocal<WebDriver> drivers = new ThreadLocal<>();
	
	private static List<WebDriver> storedDrivers  = new LinkedList<>();

	protected abstract void createDriver();

	protected abstract void startService();

	protected abstract void stopService();
	
	protected DriverManager() {
		DriverManager.browserName = new BrowserProperties()
			.getBrowserName()
			.toLowerCase();
	}

	static {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				DriverManager
					.storedDrivers
					.parallelStream()
					.forEach(dvr ->
						{
							String closeAllTabsJsCommand =
								String.format(
									"%s%n%s",
									"window.open('', '_parent', '');",
									"window.close();"
								);

							JavascriptExecutor jsExecutor = (JavascriptExecutor) DriverManager.drivers.get();

							if (DriverManager.browserName.contains("firefox")) {
								//
								jsExecutor.executeScript(closeAllTabsJsCommand);
								dvr.close();
								//
							} else if (dvr != null) {
								//
								if (DriverManager.browserName.contains("chrome")) {
									/*
									try {
										TimeUnit.SECONDS.sleep(1);
									} catch (InterruptedException ex) {
										ex.printStackTrace();
									}
									*/
								}
								dvr.close();
								dvr.quit();
							}
							/* System.exit(0); */
						}
					);
				//
			}
		});
	}

	public WebDriver getDriver() {
		//
		if (DriverManager.drivers.get() == null) {
			//
			startService();
			createDriver();
			//
			DriverManager.drivers
				.get()
				.manage()
				.timeouts()
				.implicitlyWait(30, TimeUnit.SECONDS);
			//
			DriverManager.drivers
				.get()
				.manage()
				.timeouts()
				.pageLoadTimeout(30, TimeUnit.SECONDS);
			//
			DriverManager
				.drivers
				.get()
				.manage()
				.timeouts()
				.setScriptTimeout(30, TimeUnit.SECONDS);
			//
		}
		//
		return DriverManager.drivers.get();
	}

	public static void addDriver(WebDriver driver) {
		//
		DriverManager.storedDrivers.add(driver);
		DriverManager.drivers.set(driver);
	}

	public static void closeAndQuitDriver() {
		//
		DriverManager.storedDrivers.remove(
			DriverManager.drivers.get()
		);
		DriverManager.drivers.remove();
	}
}