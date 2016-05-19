package com.mmt.adminui.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.mmt.adminui.pojo.CreateVariantRequest;
import com.mmt.adminui.pojo.DeleteRequest;
import com.mmt.adminui.pojo.NotificationRequest;
import com.mmt.adminui.pojo.NotificationResponse;
import com.mmt.adminui.pojo.OperationResponse;
import com.mmt.adminui.services.AdminPanelMiscService;
import com.mmt.entity.Observer;
import com.mmt.entity.Subscription;
import com.mmt.entity.Variant;

@Controller
public class AdminPanelMiscController {
  @Autowired
  private AdminPanelMiscService adminPanelService;
  @RequestMapping("/adminui/getsubsforfile")
  @ResponseBody
  public List<Subscription> getSubscriptionsForFile(@RequestParam("fileid")Integer fileId){
	  return adminPanelService.getSubscriptionsForFile(fileId);
  }
  @RequestMapping("/adminui/getvarsforfile")
  @ResponseBody
  public List<Variant> getVariantsForFile(@RequestParam("fileid")Integer fileId){
	  return adminPanelService.getVariantsForFile(fileId);
  }
  @RequestMapping("/adminui/getallObservers")
  @ResponseBody
  public List<Observer> getAllObservers(){
	  return adminPanelService.getAllObservers();
  }
  @RequestMapping(value="/adminui/createvariant",method=RequestMethod.POST,consumes="application/json; charset=utf-8")
  @ResponseBody
  public OperationResponse createVariant(@RequestBody CreateVariantRequest request){
	  return adminPanelService.createVariant(request);
  }
  @RequestMapping(value="/adminui/deletevariant",method=RequestMethod.POST,consumes="application/json; charset=utf-8")
  @ResponseBody
  public OperationResponse deleteVariant(@RequestBody DeleteRequest request){
	  return adminPanelService.deleteVariant(request);
  }
  @RequestMapping(value="/adminui/notifyObservers",method=RequestMethod.POST,consumes="application/json; charset=utf-8")
  @ResponseBody
  public DeferredResult<NotificationResponse> notifyObservers(@RequestBody NotificationRequest request){
	  DeferredResult<NotificationResponse> result=new DeferredResult<>(60000L);
	  adminPanelService.notifyObservers(request).subscribe(new NotificationResponseAggregator(result));
	  return result;
  }
}
