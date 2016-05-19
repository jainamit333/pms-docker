package com.mmt.adminui.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mmt.entity.Property;

public class PropertyChangeRequest {
	@JsonProperty("fileid")
	private int fileID;
	@JsonProperty("property")
	private Property property;

	public int getFileID() {
		return fileID;
	}

	public void setFileID(int fileID) {
		this.fileID = fileID;
	}

	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

}
