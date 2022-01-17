package com.java.automation.api.domain.starwar.error;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorMessageBO implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("code")
	private String code;

	@JsonProperty("description")
	private String description;

	public String getCode() {
		return this.code;
	}

	public String getDescription() {
		return this.description;
	}
}