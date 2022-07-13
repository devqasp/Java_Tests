package com.java.automation.api;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.java.automation.api.model.HeaderClass;
import com.java.automation.api.model.ResponseClass;
import com.java.automation.utils.ManagerFileUtils;

import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class PersonApi {
	
	/* POST PERSON */
	@Test
	public void insertPerson(String name, String age, String comments) {
		
		SortedMap<String, String> contentInsertion = new TreeMap<String, String>();
		contentInsertion.put("name",	  name);
		contentInsertion.put("age",	  age);
		contentInsertion.put("comments", comments);
		
		RestAssured.baseURI = ManagerFileUtils.getUrlFromJson("My_Heroku_API_Url");
		RequestSpecification request = RestAssured
			.given()
			.accept("application/json")
			.header(
				"Authorization",
				HeaderClass.getToken()
			)
			.contentType(ContentType.JSON)
			.body(contentInsertion);
		
		Response response =	
			request
				.when()
				.post("api/usr/person");
		
		ResponseClass.setHttpStatusCode(response.getStatusCode());
		
		ValidatableResponse validatableResponse = 
			response
				.then()
				.statusCode(200);
		//
		validatableResponse
			.log()
			.body();
	}
	
	/* PUT PERSON */
	@Test
	public void updatePersonUsingId(String id, String name, String age, String comment) {
		
		Map<String, Object> contentUpdate = new HashMap<>();
		contentUpdate.put("id",	  id);
		contentUpdate.put("name",	  name);
		contentUpdate.put("age",	  age);
		contentUpdate.put("comment", comment);
		
		RestAssured.baseURI = ManagerFileUtils.getUrlFromJson("My_Heroku_API_Url");
		RequestSpecification request = RestAssured
			.given()
			.header(
				"Authorization",
				HeaderClass.getToken()
			)
			.contentType(ContentType.JSON)
			.body(contentUpdate);
		
		Response response =	
			request
				.when()
				.put(String.format("api/usr/person/%s", id));
		
		ResponseClass.setHttpStatusCode(response.getStatusCode());
		
		ValidatableResponse validatableResponse = 
			response
				.then()
				.statusCode(200);
		//
		validatableResponse
			.log()
			.body();
	}

	/* GET PERSONS */
	@Test
	public void getPersons() {

		RestAssured.baseURI = ManagerFileUtils.getUrlFromJson("My_Heroku_API_Url");
		RequestSpecification httpRequest = RestAssured
			.given()
			.header(
				"Authorization",
				HeaderClass.getToken()
			)
			.contentType(ContentType.JSON);
		
		Response response = httpRequest.get("api/usr/persons");
			response
				.then()
				.statusCode(200);
			
		System.out.println(
			String.format(
				"(( All registered people: %s ))",
				response.body().asPrettyString()
			)
		);
		
		ResponseClass.setResponse(response.body().asString());
	}

	/* GET PERSON BY ID */
	@Test
	public void getPersonById(String id) {

		RestAssured.baseURI = ManagerFileUtils.getUrlFromJson("My_Heroku_API_Url");
		RequestSpecification request = RestAssured
			.given()
			.header(
				"Authorization",
				HeaderClass.getToken()
			)
			.contentType(ContentType.JSON);
 
		Response response = request.get(String.format("api/usr/person/%s", id));
			response
				.then()
				.statusCode(200);

		System.out.println(
			String.format(
				"(( Result with id(%s): %s ))",
				id,
				response.body().asPrettyString()
			)
		);
	
		ResponseClass.setResponse(response.body().asString());
	}
	
	/* DELETE PERSON BY ID */
	@Test
	public void deletePersonById(String id) {
		
		RestAssured.baseURI = ManagerFileUtils.getUrlFromJson("My_Heroku_API_Url");
		RequestSpecification request = RestAssured
			.given()
			.header(
				"Authorization",
				HeaderClass.getToken()
			)
			.contentType(ContentType.JSON);
		
		Response response = request.delete(String.format("api/usr/person/%s", id));
		response
			.then()
			.statusCode(200);
		
		ValidatableResponse validatableResponse = 
			response
				.then()
				.statusCode(200);
		//
		validatableResponse
			.log()
			.body();
	}
}