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
import com.hotel.model.Advertisement;
import com.hotel.model.Function;
import com.hotel.model.User;
import com.hotel.service.FunctionService;
import com.hotel.viewmodel.BaseData;

@Scope("prototype")
@Controller
@RequestMapping("/web/ad")
public class AdAction extends BaseAction {
	
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
			@RequestMapping(value = "/adList.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
			public String logInitAd(Advertisement ad, 
					HttpServletRequest request,
					HttpServletResponse response) {
				if (ad.getPageNo() == null)
					ad.setPageNo(1);
				ad.setPageSize(Constants.DEFAULT_PAGE_SIZE);
				//加载菜单
				List<Function> lf = functionService.getFunctionByParentUrl("/web/ad/adList.do");
				User user = new User();
				user.setChildMenuList(lf);
				request.getSession().setAttribute(Constants.USER_SESSION_NAME,user);
				//request.setAttribute("company", company);
				request.setAttribute("baseData", ad);
				return "web/ad/adList";
			}
			

			// [end]

}
