package com.mmt.adminui.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteRequest {
	@JsonProperty("id")
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
    
}
