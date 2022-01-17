package com.java.automation.api.domain.starwar.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ResponseException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private final int httpStatusCode;
	private final ErrorResponseBO errorResponse;
	private final ObjectMapper mapper = new ObjectMapper();

	public ResponseException(int httpStatusCode, ErrorResponseBO errorResponse) {
		super();
		this.httpStatusCode = httpStatusCode;
		this.errorResponse 	= errorResponse;
	}

	public int getHttpStatusCode() {
		return this.httpStatusCode;
	}

	public ErrorResponseBO getErrorResponse() {
		return this.errorResponse;
	}

	public ErrorMessageBO getErrorMessage() throws JsonProcessingException {
		return mapper
			.readValue(
				this.getErrorResponse().getMessage(),
				ErrorMessageBO.class
			);
		//
	}
}