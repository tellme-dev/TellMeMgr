package com.hotel.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hotel.common.utils.Page;


@Scope("prototype")
@Controller
@RequestMapping("/web/rcu")
public class RCUAction extends BaseAction{
	
	
	@RequestMapping(value = "/execute.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String execute(Page page,
			String message,
			HttpServletRequest request,
			HttpServletResponse response){
		
		return null;
	}
}
