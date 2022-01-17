package com.java.automation.web.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.java.automation.utils.GlobalUtils;
import com.java.automation.utils.ManagerFileUtils;

public class WebPropertiesClass {

	private Properties properties;

	private String webDriverFileName;

	public WebPropertiesClass() {
		//
		this.properties		   = new Properties();
		this.webDriverFileName = WebDriverPropertiesFile.getConfigPropFileName();
	}

	public String getConfigFileProperty(String key) {

		try (InputStream inputStream =
			new FileInputStream(
				new File(
					String.format(
						"%s/config/%s",
						ManagerFileUtils
							.checkAndGenerateFilePath("src/test/resources"),
							this.webDriverFileName
						//
					)
				)
			)
		)
		{
			this.properties.load(inputStream);
			//
		} catch (IOException ex) {
			System.err.println(
				String.format(
					"¡Exception thrown! ¡Failure to generate file! - %s",
					ex.getMessage()
				)
			);
			//
			System.err.println(
				String.format(
					"¡Check the properties file name ( %s )!",
					this.webDriverFileName
				)
			);
		}
		//
		return GlobalUtils
			.removeWhiteSpacesAndTabulation(
				this.properties.getProperty(key)
			);
		//
	}
}