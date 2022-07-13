package com.java.automation.web.browser.factory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.java.automation.utils.DriverPathUtils;
import com.java.automation.utils.GlobalUtils;
import com.java.automation.utils.ManagerFileUtils;
import com.java.automation.web.driver.factory.DriverManager;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeDriverManager_v2 extends DriverManager {

	private ChromeOptions chromeOptions;

	private String browserName;

	private ChromeDriverService chromeService;

	public ChromeDriverManager_v2(String browserName) {
		//
		this.chromeOptions = new ChromeOptions();
		this.browserName   = browserName;
	}

	@Override
	public void createDriver() {
		//
		Map<String, Object> prefs = new HashMap	<>();
		prefs.put("application_cache_enabled", 	false);
		prefs.put("credentials_enable_service", false);
		prefs.put("password_manager_enabled",	false);
		prefs.put("profile.default_content_setting_values.notifications", 1);
		prefs.put("settings.language.preferred_languages", "pt-BR");

		chromeOptions.addArguments(getArguments());
		chromeOptions.setExperimentalOption(
			"excludeSwitches",
			Arrays.asList(
				"allow-running-insecure-content",
				"enable-automation",
				"ignore-certificate-errors"
			)
		);
		chromeOptions.setExperimentalOption("prefs", prefs);
		chromeOptions.setExperimentalOption("useAutomationExtension", false);

		DriverManager.addDriver(
			new ChromeDriver(
				chromeService,
				chromeOptions
			)
		);
	}

	private String[] getArguments() {
		//
		return new String[] {
			"--aggressive-cache-discard",
			"--disable-dev-shm-usage",
			"--disable-extensions-file-access-check",
			"--disable-gl-drawing-for-tests",
			"--disable-gpu",
			"--disable-infobars",
			"--disable-low-res-tiling",
			"--disable-popup-blocking",
			"--disable-web-security",
			"--headless",
			"--ignore-gpu-blacklist",
			// "--incognito",
			"--lang=pt-BR",
			"--no-sandbox",
			"--reduce-security-for-testing",
			"--remote-debugging-port=9222",
			// "--start-maximized",
			"--test-type"
		};
	}

	@Override
	public void startService() {
		//
		if (chromeService == null) {
			try {
				chromeService = new ChromeDriverService
					.Builder()
						.usingAnyFreePort()
						.withLogFile(
							new File(
								String.format(
									"%s/%s_driver_%s.log",
									ManagerFileUtils.checkAndGenerateFilePath(
										DriverPathUtils.getFilePathUsingOSName(
											"target/log-exec"
										)
									),
									this.browserName,
									GlobalUtils.generateReferenceDate()
								)
							)
						)
						.withVerbose(false)
						.build();
				//
				chromeService.start();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	@Override
	protected void stopService() {
		//
		if (chromeService != null || chromeService.isRunning())
			chromeService.stop();
	}
}