package com.mmt.adminui.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NodeOperationResponse extends OperationResponse {
	@JsonProperty("id")
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
