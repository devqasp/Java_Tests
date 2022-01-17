package com.java.automation.web.pages;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.java.automation.utils.ManagerFileUtils;
import com.java.automation.utils.WebDriverUtils;

public class FirstWebTestPG {
	
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

	@CacheLookup
	@FindBy(name = "q")
	private WebElement searchTextBox;

	public WebElement getSearchTextBox() {
		return searchTextBox;
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
}