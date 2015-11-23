package com.hotel.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;

import com.hotel.common.utils.Constants;
import com.hotel.model.User;

public abstract class BaseAction {
	protected Log log = LogFactory.getLog(getClass());

	private String resultCode;
	private String resultMessage;
	
	private HttpServletRequest req;
	private HttpServletResponse res;
	private Map<String, Object> session;
	

	public HttpServletRequest getReq() {
		return req;
	}

	public void setReq(HttpServletRequest req) {
		this.req = req;
	}

	public HttpServletResponse getRes() {
		return res;
	}

	public void setRes(HttpServletResponse res) {
		this.res = res;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	/**
	 * 用户session
	 * 
	 * @return
	 */
	protected User getLoginUser() {
		User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute(Constants.USER_SESSION_NAME);
		return loginUser;
	}

	/**
	 * 用户session
	 * 
	 * @param loginUser
	 */
	protected void setLoginUser(User loginUser) {
		SecurityUtils.getSubject().getSession().setAttribute(Constants.USER_SESSION_NAME, loginUser);
	}

	
}
