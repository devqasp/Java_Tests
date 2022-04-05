package com.java.automation.ui.pages;

import java.util.List;

import com.java.automation.utils.ManagerFileUtils;
import com.java.automation.utils.WebDriverUtils;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;

public class FirstWebTestPG extends LoadableComponent<FirstWebTestPG> {
	
	public void accessUrl(String urlName) {
		//
		WebDriverUtils.getDriverManager()
			.getDriver()
			.navigate()
			.to(ManagerFileUtils
				.getUrlFromJson(urlName)
			);
		//
	}

	public List<WebElement> getSearchTextBoxInList() {
		return WebDriverUtils
			.getWait()
			.until(ExpectedConditions
				.presenceOfAllElementsLocatedBy(
					By.xpath(
						String.format(
							"//*[@*='text'][@*='Pesquisar']"
						)
					)
				)
			);
		//
	}

	public List<WebElement> getResultSearchLinks() {
		return WebDriverUtils
			.getWait()
			.until(ExpectedConditions
				.presenceOfAllElementsLocatedBy(
					By.xpath(
						String.format(
							"//div[@*='center_col']//a//span"
						)
					)
				)
			);
		//
	}

	@SuppressWarnings("deprecation")
	public void validateTitle(String titulo) {

		Assert.assertThat(
			WebDriverUtils
				.getDriverManager()
				.getDriver()
				.getTitle(),
			CoreMatchers
				.containsString(titulo)
			//
		);
	}
	
	@Override
	protected void load() {
		//
	}

	@Override
	protected void isLoaded() throws Error {
		//
	}
}