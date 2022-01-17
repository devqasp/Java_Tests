package com.java.automation.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;

public class ScreenshotGenerator {

	public static void generateScreenshot(Scenario scenario, WebDriver driver) {

		if (scenario.isFailed()) {
			TakesScreenshot screenshot = (TakesScreenshot) driver;
			File file = screenshot.getScreenshotAs(OutputType.FILE);
			EvidencePathGenerator.generateEvidencePath(scenario, file);
		}
	}

	public static byte[] getScreenshot(Scenario scenario, WebDriver driver) {
		return (((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
	}

	public static void allureScreenshot(WebDriver driver) {
		Allure.addAttachment(
			"<<-- SCREENSHOT OF FAILURE -->>",
			new ByteArrayInputStream(((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.BYTES)
			)
		);
	}
}

class EvidencePath {

	private static final String evidencePath = "target/screenshots/";

	public static String getEvidencePath(Scenario scenario) {
		return evidencePath + scenario.getName();
	}
}

class EvidencePathGenerator {

	static void generateEvidencePath(Scenario scenario, File file) {

		long i = 1;

		try {
			if (Counter.countDirectoryFile(scenario) > 0) {
				Counter.setCounter(i += Counter.countDirectoryFile(scenario));
			} else {
				Counter.setCounter(i);
				FileUtils.copyFile(
					file,
					new File(EvidencePath.getEvidencePath(scenario)
						+ "/"
						+ "evidencia_0"
						+ Long.toString(Counter.getCounter())
						+ "_"
						+ GlobalUtils.generateReferenceDate()
						+ ".png"
					)
				);
				//
				System.out.println("Evidence was successfully generated...!");
			}
		} catch (IOException ex) {
			System.out.println(
				"¡Exception thrown! ¡Failure to generate evidence!" + ex.getMessage()
			);
		}
	}
}

class Counter {

	private static long counter;

	static long getCounter() {
		return counter;
	}

	static void setCounter(long counter) {
		Counter.counter = counter;
	}

	public static long countDirectoryFile(Scenario scenario) {

		long $iterator = 0;

		try {
			$iterator = Files.list(
				Paths.get(
					EvidencePath.getEvidencePath(scenario)
				)
			)
			.filter(arq -> arq.toFile().isFile())
			.count();
			//
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		//
		return $iterator;
	}
}