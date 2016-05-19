package com.mmt.adminui.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mmt.entity.Observer;
import com.mmt.entity.Property;

public class CreateVariantRequest {
	@JsonProperty("fileid")
	private Integer fileID;
	@JsonProperty("oblist")
	private List<Observer> observerList;
	@JsonProperty("props")
	private List<Property> properties;
	public Integer getFileID() {
		return fileID;
	}
	public void setFileID(Integer fileID) {
		this.fileID = fileID;
	}
	public List<Observer> getObserverList() {
		return observerList;
	}
	public void setObserverList(List<Observer> observerList) {
		this.observerList = observerList;
	}
	public List<Property> getProperties() {
		return properties;
	}
	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}
	

}
