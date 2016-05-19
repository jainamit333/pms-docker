package com.mmt.adminui.pojo;

import java.util.HashMap;
import java.util.Map;

public class NotificationResponse extends OperationResponse{
	private Map<String,String> obErrorMap=new HashMap<String,String>();

	public Map<String, String> getObErrorMap() {
		return obErrorMap;
	}

	public void setObErrorMap(Map<String, String> obErrorMap) {
		this.obErrorMap = obErrorMap;
	}
	
	
}
