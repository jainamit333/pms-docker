package com.mmt.propclient.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mmt.propclient.pojo.ClientPropsOPResponse;
import com.mmt.propclient.service.ClientPropService;
import com.mmt.propclient.util.IPUtil;

@Controller
public class ClientPropsController {
	@Autowired
	ClientPropService propService;

	@RequestMapping("/client/getPropertyFile")
	@ResponseBody
	public String getPropertyFile(@RequestParam("id") Integer fileID,HttpServletRequest request) {
		return propService.getPropertyFile(fileID,IPUtil.getIP(request));
	}

	@RequestMapping("/client/createOrUpdateSubscription")
	@ResponseBody
	public ClientPropsOPResponse createSubscription(@RequestParam("prefix") String prefix, @RequestParam("suffix") String suffix,
			@RequestParam("qualifier") String qualifier,@RequestParam("fileIDs")String fileIDsCSV,HttpServletRequest request) {
		   String[] fileIDs=fileIDsCSV.split(",");
		   Integer[] fileIDsInt=new Integer[fileIDs.length];
		   for(int i=0;i<fileIDs.length;i++){
			   fileIDsInt[i]=Integer.parseInt(fileIDs[i]);
		   }
         return propService.createSubscription(IPUtil.getIP(request), prefix, suffix, qualifier, fileIDsInt);
	}
	@RequestMapping("client/unSubscribe")
	@ResponseBody
	public ClientPropsOPResponse unSubscribe(@RequestParam("qualifier") String qualifier,HttpServletRequest request){
	  return propService.unSubscribe(qualifier,IPUtil.getIP(request));	
	}

}
