package com.java.automation.api.domain.starwar.request;

import com.java.automation.api.domain.starwar.error.ErrorResponseBO;
import com.java.automation.api.domain.starwar.error.ResponseException;
import com.java.automation.api.domain.starwar.root.RootApiModelBO;
import com.java.automation.utils.ManagerFileUtils;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class RootApiRequest {

	public static RequestSpecification request;
	private static RootApiRequest root;

	private RootApiRequest() {
		//
	}

	public static RootApiRequest getInstance() {
		//
		if (root == null)
			root = new RootApiRequest();
		return root;
	}

	public RootApiModelBO requestRoot() throws ResponseException {

		RequestSpecBuilder builder = new RequestSpecBuilder();
		builder.setBaseUri(ManagerFileUtils.getUrlFromJson("Star_Wars_Url"));
		builder.setRelaxedHTTPSValidation();
		request = RestAssured.given().spec(builder.build());
		//
		Response response =
			RootApiRequest
				.request
					.contentType(ContentType.JSON)
					.get();
		//
		ValidatableResponse validatableResponse =
			response
				.then();
		//
		validatableResponse
			.log()
			.all();
		//
		if (response.statusCode() != 200) {
			System.out.println(
				String.format(
					"(( Root API Result: %s ))",
					response.body().asPrettyString()
				)
			);
			ErrorResponseBO errorResponse = response.as(ErrorResponseBO.class);
			throw new ResponseException(response.statusCode(), errorResponse);
		}
		//
		return response.as(RootApiModelBO.class);
		//
	}
}