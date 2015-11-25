package com.hotel.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hotel.web.controller.BaseAction;

public class PageInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2) throws Exception {
		BaseAction base = (BaseAction)arg2;
		
//		if(!StringUtil.isEmpty(arg0.getParameter("page")) && !StringUtil.isEmpty(arg0.getParameter("rows"))){
//			PaginationData page = new PaginationData();
//			page.setPageNo(Integer.valueOf(arg0.getParameter("page")));
//			page.setPageSize(Integer.valueOf(arg0.getParameter("rows")));
//			page.setOrder(arg0.getParameter("order"));
//			page.setSort(arg0.getParameter("sort"));
//			base.setPage(page);
//		}
		return true;
	}

}
