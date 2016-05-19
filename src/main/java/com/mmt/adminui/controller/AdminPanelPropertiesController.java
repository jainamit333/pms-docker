package com.mmt.adminui.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mmt.adminui.pojo.OperationResponse;
import com.mmt.adminui.pojo.PropertyChangeRequest;
import com.mmt.adminui.services.AdminPanelPropertiesService;
import com.mmt.entity.Property;

@Controller
public class AdminPanelPropertiesController {
	@Autowired
	private AdminPanelPropertiesService propertyService;

	@RequestMapping("/adminui/getPropertyForFile")
	@ResponseBody
	public List<Property> getPropertiesForFile(@RequestParam("fileid") Integer fileId) {
		return propertyService.getPropertiesFromFile(fileId);
	}

	@RequestMapping(value = "/adminui/addProperty", method = RequestMethod.POST, consumes = {
			"application/json; charset=utf-8" })
	@ResponseBody
	public OperationResponse addProperty(@RequestBody PropertyChangeRequest request) {
		return propertyService.addProperty(request);
	}

	@RequestMapping(value = "/adminui/deleteProperty", method = RequestMethod.POST, consumes = {
			"application/json; charset=utf-8" })
	@ResponseBody
	public OperationResponse deleteProperty(@RequestBody PropertyChangeRequest request) {
		return propertyService.deleteProperty(request);
	}

	@RequestMapping(value = "/adminui/changeProperty", method = RequestMethod.POST, consumes = {
			"application/json; charset=utf-8" })
	@ResponseBody
	public OperationResponse changeProperty(@RequestBody PropertyChangeRequest request) {
		return propertyService.changeProperty(request);
	}
}
