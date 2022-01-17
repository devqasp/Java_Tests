package com.java.automation.web.properties;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class WebDriverPropertiesFile {

	public static String getConfigPropFileName() {
		//
		String[] fileNames = {};
		//
		try {
			//
			fileNames = Files.list(
				Paths.get(
						"src/test/resources/config"
					)
				)
				.filter(Files::isRegularFile)
				.map(p -> p.toFile().getName())
				.toArray(String[]::new);
		//
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		//
		return Arrays
			.stream(fileNames)
			.filter(f -> f.contains("webdriver"))
			.findFirst().get();
		//
	}
}