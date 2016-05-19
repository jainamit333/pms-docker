package com.mmt.adminui.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RenameNodeRequest {
	@JsonProperty("id")
	private int id;
	@JsonProperty("name")
	private String newName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

}
