/**
 * copy right 2012 sctiyi all rights reserved
 * create time:5:08:41 PM
 * author:ftd
 */
package com.hotel.common.utils.app;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ftd
 *
 */
public class GetIP {
	
	public static String getIPAddress(HttpServletRequest request)
	  {
	    String ip = request.getHeader("x-forwarded-for");
	    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip)))
	      ip = request.getHeader("Proxy-Client-IP");

	    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip)))
	      ip = request.getHeader("WL-Proxy-Client-IP");

	    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip)))
	      ip = request.getRemoteAddr();

	    return ip;
	  }
}
