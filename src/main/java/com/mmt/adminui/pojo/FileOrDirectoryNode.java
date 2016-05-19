package com.mmt.adminui.pojo;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mmt.adminui.util.NodeIcon;

public class FileOrDirectoryNode {
	@JsonProperty("text")
	private String displayText;
	@JsonProperty("children")
	private Boolean canHaveChild;
	@JsonProperty("id")
	private String id;
	@JsonProperty("icon")
	private NodeIcon icon;
	@JsonProperty("type")
	private NodeIcon type;
	@JsonProperty("state")
	private Map<String, Boolean> stateMap;

	public String getDisplayText() {
		return displayText;
	}

	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}

	public Boolean getCanHaveChild() {
		return canHaveChild;
	}

	public void setCanHaveChild(Boolean canHaveChild) {
		this.canHaveChild = canHaveChild;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public NodeIcon getIcon() {
		return icon;
	}

	public void setIcon(NodeIcon icon) {
		this.icon = icon;
	}

	public NodeIcon getType() {
		return type;
	}

	public void setType(NodeIcon type) {
		this.type = type;
	}

	public Map<String, Boolean> getStateMap() {
		return stateMap;
	}

	public void setStateMap(Map<String, Boolean> stateMap) {
		this.stateMap = stateMap;
	}
   
}
