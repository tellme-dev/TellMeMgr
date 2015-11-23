package com.hotel.common.utils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;



public class StartLoadUtil extends HttpServlet {
	
	private static Logger logger = Logger.getLogger("StartLoadUtil");
	
	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		
		super.init(servletConfig);
		logger.info("auto login StartLoadUtil server.............");
		
		  WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletConfig.getServletContext()); 
//		  FunctionService  functionService =(FunctionService) webApplicationContext.getBean("functionServiceImpl");
//		  Constants.functionlist =  functionService.getFunctionlist();
		  
		  

		  logger.info("StartLoadUtil is already login..............");
	}
	

}
