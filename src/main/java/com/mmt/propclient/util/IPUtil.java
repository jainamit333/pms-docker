package com.mmt.propclient.util;

import javax.servlet.http.HttpServletRequest;

public class IPUtil {
	public static String getIP(HttpServletRequest request) {
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		return ipAddress;
	}
}
