package com.java.automation.utils;

public class JavaCMDLineClass {

	private final static String ENV = System.getProperty("env");

	private final static String BROWSER_VERSION = System.getProperty("bwr_version");

	public static String getEnv() {
		return
			JavaCMDLineClass.ENV.isEmpty() ||
			JavaCMDLineClass.ENV == null ?
				"dev" : JavaCMDLineClass.ENV;
	}

	public static String getBrowserVersion() {
		return
			JavaCMDLineClass.BROWSER_VERSION.isEmpty() ||
			JavaCMDLineClass.BROWSER_VERSION == null ?
				"dev" : JavaCMDLineClass.ENV;
	}
}