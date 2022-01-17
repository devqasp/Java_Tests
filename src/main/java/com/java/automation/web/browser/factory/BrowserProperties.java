package com.java.automation.web.browser.factory;

import com.java.automation.web.properties.WebPropertiesClass;

public class BrowserProperties {

	private String browserName;

	private String browserVersion;

	public BrowserProperties() {
		//
		this.browserName 	= getProperty("browser.name");
		this.browserVersion = getProperty("browser.version");
	}

	public String getProperty(String key) {
		return new WebPropertiesClass().getConfigFileProperty(key);
	}

	public String getBrowserName() {
		return this.browserName;
	}

	public String getBrowserVersion() {
		return this.browserVersion;
	}
}