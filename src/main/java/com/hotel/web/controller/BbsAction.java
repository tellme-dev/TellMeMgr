package com.hotel.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hotel.common.utils.Constants;
import com.hotel.model.Bbs;
import com.hotel.model.Function;
import com.hotel.model.Hotel;
import com.hotel.model.User;
import com.hotel.service.AdvertisementService;
import com.hotel.service.FunctionService;

@Scope("prototype")
@Controller
@RequestMapping("/web/bbs")
public class BbsAction extends BaseAction {

	// [start] 接口引用
	@Resource(name="functionService")
	private FunctionService functionService;
	// [end]

	// [start] 员工公司模块 ---- 页面跳转

	
	/**
	 * 
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/bbsList.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String logInitbbs(Bbs bbs, 
			HttpServletRequest request,
			HttpServletResponse response) {
		if (bbs.getPageNo() == null)
			bbs.setPageNo(1);
		bbs.setPageSize(Constants.DEFAULT_PAGE_SIZE);
		//加载菜单
		List<Function> lf = functionService.getFunctionByParentUrl("/web/bbs/bbsList.do");
		User user = new User();
		user.setChildMenuList(lf);
		request.getSession().setAttribute(Constants.USER_SESSION_NAME,user);
		request.setAttribute("bbs", bbs);
		return "web/bbs/bbsList";
	}
	

	// [end]

}