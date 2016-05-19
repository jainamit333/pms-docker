package com.mmt.propclient.util;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;

public class IPUtilTest {
	
	
	@Test
	public void testGetIP() {
		HttpServletRequest request=mock(HttpServletRequest.class);
		when(request.getHeader("X-FORWARDED-FOR")).thenReturn("172.16.81.25");
		String ip=IPUtil.getIP(request);
		assertEquals("172.16.81.25", ip);
		when(request.getHeader("X-FORWARDED-FOR")).thenReturn(null);
		when(request.getRemoteAddr()).thenReturn("172.16.81.25");
		ip=IPUtil.getIP(request);
		assertEquals("172.16.81.25", ip);
	}

}
