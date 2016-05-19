package com.mmt.adminui.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.context.request.async.DeferredResult;

import com.mmt.adminui.pojo.NotificationResponse;
import com.mmt.adminui.pojo.SingleObNotificationResponse;

import rx.exceptions.CompositeException;

public class NotificationResponseAggregatorTest {
	
	private NotificationResponseAggregator responseAgregator;
	private DeferredResult<NotificationResponse> result;
	
	@Before
	public void setUp(){
		result=new DeferredResult<>();
		responseAgregator=new NotificationResponseAggregator(result);
	}

	

	@Test
	public void testOnCompleted() {
		SingleObNotificationResponse mockSingleResp=new SingleObNotificationResponse();
		mockSingleResp.setError("Error Occured");
		mockSingleResp.setObserver("10.68.84.123");
		responseAgregator.onNext(mockSingleResp);
		responseAgregator.onCompleted();
		assertNotNull(result.getResult());
		assertTrue(result.getResult() instanceof NotificationResponse);
		Map<String,String> errorMap=((NotificationResponse)result.getResult()).getObErrorMap();
		assertTrue(errorMap.size()>0);
		assertEquals("10.68.84.123",errorMap.keySet().iterator().next());
		assertEquals("Error Occured",errorMap.values().iterator().next());
		
	}

	@Test
	public void testOnErrorSingle() {
		SingleObNotificationResponse mockSingleResp=new SingleObNotificationResponse();
		mockSingleResp.setError("Error Occured");
		mockSingleResp.setObserver("10.68.84.123");
		responseAgregator.onError(mockSingleResp);
		NotificationResponse resp=(NotificationResponse) result.getResult();
		assertEquals("Notification to 10.68.84.123 failed", resp.getError());
		Map<String,String> errorMap=resp.getObErrorMap();
		assertTrue(errorMap.size()>0);
		assertEquals("10.68.84.123",errorMap.keySet().iterator().next());
		assertEquals("Error Occured",errorMap.values().iterator().next());
	}
	@Test
	public void testOnErrorComposite(){
		SingleObNotificationResponse mockSingleResp=new SingleObNotificationResponse();
		mockSingleResp.setError("Error Occured");
		mockSingleResp.setObserver("10.68.84.123");
		SingleObNotificationResponse mockSingleResp1=new SingleObNotificationResponse();
		mockSingleResp1.setError("Error Occured");
		mockSingleResp1.setObserver("10.68.84.124");
		CompositeException compositeException =new CompositeException(mockSingleResp,mockSingleResp1);
		responseAgregator.onError(compositeException);
		NotificationResponse resp=(NotificationResponse) result.getResult();
		assertEquals("Some or All Notifications failed", resp.getError());
		Map<String,String> errorMap=resp.getObErrorMap();
		assertTrue(errorMap.size()>0);
		Iterator<String> keySetIterator=errorMap.keySet().iterator();
		Iterator<String> valueIterator=errorMap.values().iterator();
		assertEquals("10.68.84.124",keySetIterator.next());
		assertEquals("Error Occured",valueIterator.next());
		assertEquals("10.68.84.123",keySetIterator.next());
		assertEquals("Error Occured",valueIterator.next());
	}
	@Test
	public void testOnErrorUnknown(){
		responseAgregator.onError(new Throwable("Some error occured"));
		NotificationResponse resp=(NotificationResponse) result.getResult();
		assertEquals("Unknown Error occured",resp.getError());
	}

}
