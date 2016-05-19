package com.mmt.adminui.controller;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.context.request.async.DeferredResult;

import com.mmt.adminui.pojo.CreateVariantRequest;
import com.mmt.adminui.pojo.DeleteRequest;
import com.mmt.adminui.pojo.NotificationRequest;
import com.mmt.adminui.pojo.NotificationResponse;
import com.mmt.adminui.pojo.OperationResponse;
import com.mmt.adminui.pojo.SingleObNotificationResponse;
import com.mmt.adminui.services.AdminPanelMiscService;
import com.mmt.entity.File;
import com.mmt.entity.Observer;
import com.mmt.entity.Property;
import com.mmt.entity.Subscription;
import com.mmt.entity.Variant;

import rx.Observable;

@RunWith(MockitoJUnitRunner.class)
public class AdminPanelMiscControllerTest {
	@Mock
	private AdminPanelMiscService adminPanelService;
	@InjectMocks
	private AdminPanelMiscController adminPanelController;

	@Test
	public void testGetSubscriptionsForFile() {
		List<Subscription> subs = new ArrayList<>();
		Subscription sub = new Subscription();
		sub.setSubsQualifier("spicejet.propeties");
		Observer ob = new Observer();
		ob.setId(34);
		ob.setIp("172.16.25.115");
		sub.setObserver(ob);
		subs.add(sub);
		when(adminPanelService.getSubscriptionsForFile(11)).thenReturn(subs);
		List<Subscription> returnedSubs = adminPanelController.getSubscriptionsForFile(11);
		assertNotNull(returnedSubs);
		assertTrue(returnedSubs.size() > 0);
		assertEquals("spicejet.propeties", returnedSubs.get(0).getSubsQualifier());
		assertEquals("172.16.25.115", returnedSubs.get(0).getObserver().getIp());
	}

	@Test
	public void testGetVariantsForFile() {
		List<Variant> vars=new ArrayList<>();
		Variant var=new Variant();
		File file=new File();
		file.setSelfName("spicejet.properties");
		var.setFileID(file);
		List<Observer> obList=new ArrayList<>();
		Observer observer=new Observer();
		observer.setId(34);
		observer.setIp("172.16.25.115");
		obList.add(observer);
		var.setObservers(obList);
		List<Property> props=new ArrayList<>();
		Property prop=new Property();
		prop.setKey("test");
		prop.setValue("testvalue");
		props.add(prop);
		var.setProperties(props);
		vars.add(var);
		when(adminPanelService.getVariantsForFile(any())).thenReturn(vars);
		List<Variant> returned=adminPanelController.getVariantsForFile(11);
		Variant varret=returned.get(0);
		assertNotNull(returned);
		assertTrue(returned.size()>0);
		assertEquals("spicejet.properties",varret.getFileID().getSelfName());
		assertTrue(varret.getObservers().size()>0);
		assertEquals("172.16.25.115",varret.getObservers().get(0).getIp());
		assertTrue(varret.getProperties().size()>0);
		assertEquals("test",varret.getProperties().get(0).getKey());
		assertEquals("testvalue",varret.getProperties().get(0).getValue());
	}

	@Test
	public void testGetAllObservers() {
		List<Observer> observers=new ArrayList<>();
		Observer ob = new Observer();
		ob.setId(34);
		ob.setIp("172.16.25.115");
		observers.add(ob);
		when(adminPanelService.getAllObservers()).thenReturn(observers);
		List<Observer> returned=adminPanelController.getAllObservers();
		assertNotNull(returned);
		assertTrue(returned.size()>0);
		assertEquals(34,returned.get(0).getId());
		assertEquals("172.16.25.115", returned.get(0).getIp());
	}

	@Test
	public void testCreateVariant() {
		List<Observer> observers=new ArrayList<>();
		Observer ob = new Observer();
		ob.setId(34);
		ob.setIp("172.16.25.115");
		observers.add(ob);
		CreateVariantRequest createRequest=new CreateVariantRequest();
		List<Property> props=new ArrayList<>();
		Property prop=new Property();
		prop.setKey("test");
		prop.setValue("testvalue");
		props.add(prop);
		createRequest.setFileID(11);
		createRequest.setObserverList(observers);
		createRequest.setProperties(props);
		OperationResponse mockResp=new OperationResponse();
		when(adminPanelService.createVariant(any(CreateVariantRequest.class))).thenReturn(mockResp);
		OperationResponse resp=adminPanelController.createVariant(createRequest);
	    assertNotNull(resp);
	    assertNull(resp.getError());
	}

	@Test
	public void testDeleteVariant() {
		DeleteRequest delReqeust=new DeleteRequest();
		delReqeust.setId(11);
		when(adminPanelService.deleteVariant(any(DeleteRequest.class))).thenReturn(new OperationResponse());
		OperationResponse resp=adminPanelController.deleteVariant(delReqeust);
		assertNotNull(resp);
		assertNull(resp.getError());
	}

	@Test
	public void testNotifyObservers() {
		SingleObNotificationResponse obNotificationResp=new SingleObNotificationResponse();
		Observable<SingleObNotificationResponse> respOb=Observable.just(obNotificationResp);
		when(adminPanelService.notifyObservers(any(NotificationRequest.class))).thenReturn(respOb);
		NotificationRequest request=new NotificationRequest();
		request.setFileID(11);
		DeferredResult<NotificationResponse> result=adminPanelController.notifyObservers(request);
		//since we are mocking the service itself so we can write below code
		while(result.getResult()==null);
		assertNotNull(result.getResult());
		
		obNotificationResp=new SingleObNotificationResponse();
		obNotificationResp.setError("Error in notifying");
		obNotificationResp.setObserver("172.16.25.115");
		respOb=Observable.just(obNotificationResp);
		when(adminPanelService.notifyObservers(any(NotificationRequest.class))).thenReturn(respOb);
		request=new NotificationRequest();
		request.setFileID(11);
		result=adminPanelController.notifyObservers(request);
		while(result.getResult()==null);
		assertNotNull(result.getResult());
		Entry<String, String> firstEntry=((NotificationResponse)result.getResult()).getObErrorMap().entrySet().iterator().next();
		assertEquals("172.16.25.115",firstEntry.getKey());
		assertEquals("Error in notifying",firstEntry.getValue());
		
	}

}
