package com.mmt.adminui.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mmt.adminui.util.NodeIcon;

public class CreateNodeRequest {
	@JsonProperty("parentid")
	private int parentid;
	@JsonProperty("name")
	private String selfName;
	@JsonProperty("type")
	private NodeIcon type;

	public int getParentid() {
		return parentid;
	}

	public void setParentid(int parentid) {
		this.parentid = parentid;
	}

	public String getSelfName() {
		return selfName;
	}

	public void setSelfName(String selfName) {
		this.selfName = selfName;
	}

	public NodeIcon getType() {
		return type;
	}

	public void setType(NodeIcon type) {
		this.type = type;
	}

}
