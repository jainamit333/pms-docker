package com.mmt.propclient.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.mmt.propclient.pojo.ClientPropsOPResponse;
import com.mmt.propclient.service.ClientPropService;
@RunWith(MockitoJUnitRunner.class)
public class ClientPropControllerTest {
	@Mock
	ClientPropService propService;
	@Mock
	HttpServletRequest servletRequest;
	@InjectMocks
	private ClientPropsController clientPropController;
	
	@Before
	public void setUP(){
		when(servletRequest.getHeader("X-FORWARDED-FOR")).thenReturn("172.16.23.45");
		when(servletRequest.getRemoteAddr()).thenReturn("172.16.23.45");
	}
	
	@Test
	public void testGetPropertyFile() {
		String propFile="AgentCode=DELMM95000\nOrgCode=DELMM95000\nAgentPassName=DELMM95000\nAgentPass=Sp1ce2015%\nBookingUrl=https://sgr3xapi.navitaire.com/BookingManager.svc\nSessionUrl=https://sgr3xapi.navitaire.com/SessionManager.svc\nCurrency=USD\nActionStatusCode=NN\nLocCode=www\nAgentName=Spicejet\nDomainCode=www";
		when(propService.getPropertyFile(11,"172.16.23.45")).thenReturn(propFile);
		String returned=clientPropController.getPropertyFile(11, servletRequest);
		assertEquals(propFile,returned);
	}

	@Test
	public void testCreateSubscription() {
		ClientPropsOPResponse mockResp=new ClientPropsOPResponse();
		when(propService.createSubscription("172.16.23.45", "http://", ":9090/pmsclient/notifypropchange", "spicejet.properties", new Integer[]{11,12})).thenReturn(mockResp);
		ClientPropsOPResponse resp=clientPropController.createSubscription("http://", ":9090/pmsclient/notifypropchange", "spicejet.properties", "11,12", servletRequest);
	    assertNotNull(resp);
	    assertNull(resp.getError());
	    assertEquals(mockResp, resp);
	}

	@Test
	public void testUnSubscribe() {
		ClientPropsOPResponse mockResp = new ClientPropsOPResponse();
		when(propService.unSubscribe("spicejet.properties", "172.16.23.45")).thenReturn(mockResp);
		ClientPropsOPResponse resp = clientPropController.unSubscribe("spicejet.properties", servletRequest);
		assertNotNull(resp);
		assertNull(resp.getError());
		assertEquals(mockResp, resp);
	}

}
