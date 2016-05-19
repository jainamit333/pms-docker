package com.mmt.adminui.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mmt.adminui.pojo.CopyOrMoveRequest;
import com.mmt.adminui.pojo.CreateNodeRequest;
import com.mmt.adminui.pojo.DeleteRequest;
import com.mmt.adminui.pojo.NodeOperationResponse;
import com.mmt.adminui.pojo.RenameNodeRequest;
import com.mmt.adminui.services.AdminPanelNodesService;
import com.mmt.entity.FileSystemObject;

@Controller
public class AdminPanelNodesController {
	@Autowired
	AdminPanelNodesService adminPanelNodesService;

	@RequestMapping("/adminui/getChildren")
	@ResponseBody
	public List<FileSystemObject> getChildren(@RequestParam("id") String id) {
		List<FileSystemObject> files=adminPanelNodesService.getChildren(id);
		return files;
	}
    @RequestMapping(path="/adminui/addNode",method=RequestMethod.POST,consumes="application/json; charset=utf-8")
    @ResponseBody
    public NodeOperationResponse addNode(@RequestBody CreateNodeRequest request){
    	return adminPanelNodesService.addNode(request);
    }
    @RequestMapping(path="/adminui/renameNode",method=RequestMethod.POST,consumes="application/json; charset=utf-8")
    @ResponseBody
    public NodeOperationResponse renameNode(@RequestBody RenameNodeRequest request){
    	NodeOperationResponse resp=adminPanelNodesService.renameNode(request);
    	return resp;
    }
    @RequestMapping(path="/adminui/moveNode",method=RequestMethod.POST,consumes="application/json; charset=utf-8")
    @ResponseBody
    public NodeOperationResponse moveNode(@RequestBody CopyOrMoveRequest request){
    	return adminPanelNodesService.moveNode(request);
    }
    @RequestMapping(path="/adminui/copyNode",method=RequestMethod.POST,consumes="application/json; charset=utf-8")
    @ResponseBody
    public NodeOperationResponse copyNode(@RequestBody CopyOrMoveRequest request){
    	return adminPanelNodesService.copyNode(request);
    }
    @RequestMapping(path="/adminui/deleteNode",method=RequestMethod.POST,consumes="application/json; charset=utf-8")
    @ResponseBody
    public NodeOperationResponse deleteNode(@RequestBody DeleteRequest request){
    	return adminPanelNodesService.deleteNode(request);
    }
}
