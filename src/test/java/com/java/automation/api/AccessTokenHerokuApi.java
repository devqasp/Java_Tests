package com.java.automation.api;

import java.util.HashMap;
import java.util.Map;

import com.java.automation.api.model.HeaderClass;
import com.java.automation.utils.ManagerFileUtils;

import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class AccessTokenHerokuApi {

	@Test
	public void getAccessToken() {

		Map<String, Object> credencials = new HashMap<>();
		credencials.putAll(
			ManagerFileUtils
				.getParamsFromJson(
					"src/test/resources/json-repo/password.json"
				)
			//
		);

		RestAssured.baseURI = ManagerFileUtils.getUrlFromJson("My_Heroku_API_Url");
		RequestSpecification request =
			RestAssured
				.given()
				.header("Accept", ContentType.JSON.getAcceptHeader())
				.contentType(ContentType.JSON)
				.body(credencials);
		
		Response response =	
			request
				.when()
				.post("api/auth/signin");
		
		ValidatableResponse validatableResponse = 
			response
				.then()
				.statusCode(200);
		
		validatableResponse
			.log()
			.all();
		
		System.out.println(
			String.format(
				"(( The generated access token was: %s ))",
				response.body().asPrettyString()
			)
		);
		//
		HeaderClass.setToken(response.path("accessToken").toString());
	}
	
	@Test
	public void getAccessToken(String username, String password) {

		Map<String, Object> credencials = new HashMap<>();
		credencials.put("username", username);
		credencials.put("password", password);

		RestAssured.baseURI = ManagerFileUtils.getUrlFromJson("My_Heroku_API_Url");
		RequestSpecification request =
			RestAssured
				.given()
				.header("Accept", ContentType.JSON.getAcceptHeader())
				.contentType(ContentType.JSON)
				.body(credencials);
		
		Response response =	
			request
				.when()
				.post("api/auth/signin");
		
		ValidatableResponse validatableResponse = 
			response
				.then()
				.statusCode(200);
		
		validatableResponse
			.log()
			.all();
		
		System.out.println(
			String.format(
				"(( The generated access token was: %s ))",
				response
					.body()
					.asPrettyString()
			)
		);
		//
		HeaderClass.setToken(response.path("accessToken").toString());
	}
}