package com.mmt.adminui.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificationRequest {
	@JsonProperty("fileid")
	private Integer fileID;
	@JsonProperty("oblist")
	private List<String> observerList;
	public Integer getFileID() {
		return fileID;
	}
	public void setFileID(Integer fileID) {
		this.fileID = fileID;
	}
	public List<String> getObserverList() {
		return observerList;
	}
	public void setObserverList(List<String> observerList) {
		this.observerList = observerList;
	}
	
	

}
