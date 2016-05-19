package com.mmt.adminui.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mmt.entity.Property;

public class PropertyOperationResponse extends OperationResponse {
	@JsonProperty("property")
	private Property property;

	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

}
