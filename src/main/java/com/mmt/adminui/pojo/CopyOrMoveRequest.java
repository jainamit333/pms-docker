package com.mmt.adminui.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CopyOrMoveRequest {
	@JsonProperty("id")
	private int id;
	@JsonProperty("parent")
	private int parent;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

}
