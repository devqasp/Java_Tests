package com.java.automation.api.domain.starwar.root.step.definitions;

import com.java.automation.api.domain.starwar.error.ResponseException;
import com.java.automation.api.domain.starwar.request.RootApiRequest;
import com.java.automation.api.domain.starwar.root.RootApiModelBO;

import io.cucumber.java.pt.Dado;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;

public class RootApiSD {

	public ResponseOptions<Response> response;
	public ResponseException responseException;
	// public RootApiResponseBO rootApiResponseBO;
	public RootApiModelBO rootApiModelBO;
	private final RootApiRequest request = RootApiRequest.getInstance();

	@Dado("que eu acesso a api root")
	public void queEuAcessoAApiRoot() {
		//
		// this.rootApiResponseBO = null;
		this.rootApiModelBO = null;
		//
		try {
			// this.rootApiResponseBO = request.requestRoot();
			this.rootApiModelBO = request.requestRoot();
		} catch (ResponseException ex) {
			this.responseException = ex;
		}
		//
		// System.out.println(rootApiResponseBO.getData());
		System.out.println(
			String.format(
				"Starships API: %s",
				rootApiModelBO.getStarships()
			)
		);
	}
}