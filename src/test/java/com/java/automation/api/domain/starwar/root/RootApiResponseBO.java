package com.java.automation.api.domain.starwar.root;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RootApiResponseBO implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("data")
	private List<RootApiModelBO> data;

	public List<RootApiModelBO> getData() {
		return this.data;
	}

	public RootApiModelBO getFirtData() {
		return this.data.get(0);
	}
}