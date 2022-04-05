package com.java.automation.ui.pages;

import java.lang.reflect.InvocationTargetException;

import com.java.automation.utils.WebDriverUtils;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

public class PageRegister extends LoadableComponent<PageRegister> {

	@SuppressWarnings("unchecked")
	private static <T> T getPage(Class<?> clazz) {
		//
		T page = null;
		//
		try {
			page = (T) clazz
				.getDeclaredConstructor()
				.newInstance();
		//
		} catch (
			InstantiationException
			| IllegalAccessException
			| IllegalArgumentException
			| InvocationTargetException
			| NoSuchMethodException
			| SecurityException ex)
		{
			ex.printStackTrace();
		}
		//
		PageFactory.initElements(
			WebDriverUtils.getDriverManager().getDriver(),
			page
		);
		//
		return page;
	}
	
	public static FirstWebTestPG getFirstWebTestPG() {
		return getPage(FirstWebTestPG.class);
	}
	
	public static TodoListPG getTodoListPG() {
		return getPage(TodoListPG.class);
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