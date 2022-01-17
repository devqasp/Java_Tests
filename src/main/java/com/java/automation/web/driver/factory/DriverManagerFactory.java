package com.java.automation.web.driver.factory;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.java.automation.utils.DriverPathUtils;
import com.java.automation.web.browser.factory.BrowserProperties;
import com.java.automation.web.browser.factory.ChromeDriverManager;
import com.java.automation.web.browser.factory.ChromeDriverManager_v2;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverManagerFactory {

	private DriverManager driverManager;

	private String browserName;

	private String driverResourcePath;

	public DriverManagerFactory() {
		this.driverResourcePath = "src/main/resources/drivers";
		this.browserName = new BrowserProperties().getBrowserName();
	}

	public DriverManager executeDriverManager() {
		//
		switch ((this.browserName == null
				|| this.browserName.isEmpty())
				 ? this.browserName = "_NULL_"
				 : this.browserName
			 )
		{
		//
		case "chrome":
			//
			this.driverManager =
				new ChromeDriverManager(
					this.browserName,
					DriverPathUtils.getDriverPathUsingOSName(
						driverResourcePath
					)
				);
			//
			break;
		case "chrome_v2":
			//
			WebDriverManager.chromedriver().setup();
			this.driverManager =
				new ChromeDriverManager_v2(
					this.browserName
				);
			//
			break;
		default:
			JOptionPane.showMessageDialog(
				new JFrame(),
				String.format(
					"Driver name: %s has not been implemented or does not exist! (Check the config.properties too!)",
					this.browserName
				),
				"# ERROR! #",
				JOptionPane.ERROR_MESSAGE
			);
			break;
		}
		//
		return this.driverManager;
	}
}