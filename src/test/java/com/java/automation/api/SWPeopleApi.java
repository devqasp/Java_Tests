package com.java.automation.api;

import com.java.automation.api.model.ResponseClass;
import com.java.automation.utils.GlobalUtils;
import com.java.automation.utils.ManagerFileUtils;

import io.cucumber.datatable.DataTable;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class SWPeopleApi {
	
	@SuppressWarnings("rawtypes")
	public void getResponseBodyPeople(String id) {
		RestAssured.baseURI = ManagerFileUtils.getUrlFromJson("Star_Wars_Url");
		RequestSpecification httpRequest = RestAssured.given();
		Response response	= httpRequest.get(String.format("/people/%s", id));
		ResponseBody body	= response.getBody();
		String bodyAsString = body.asString();
		ResponseClass.setResponse(bodyAsString);
		ResponseClass.setHttpStatusCode(response.getStatusCode());
	}

	public void checkHttpStatusCodePeople(int httpStatusCode) {
		GlobalUtils.assertHttpStatusCode(httpStatusCode);
	}

	public void checkResponseBodyPeople(String value) {
		GlobalUtils.compareValueResponseString(value);
	}

	public void checkResponseBodyPeople(DataTable dataTable) {
		GlobalUtils.assertResponseList(
			GlobalUtils.getDataTable(dataTable),
			GlobalUtils.getResponse()
		);
	}

	public void checkResponseBodyPeople(DataTable dataTable, String key) {
		GlobalUtils.assertResponseListReduced(
			GlobalUtils.getDataTable(dataTable),
			GlobalUtils.getValueByIndexFromResponse(key)
		);
	}
}