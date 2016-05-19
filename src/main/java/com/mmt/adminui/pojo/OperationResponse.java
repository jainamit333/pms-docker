package com.mmt.adminui.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OperationResponse {

	@JsonProperty("error")
	private String error;
	
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}