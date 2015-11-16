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
import com.hotel.model.ItemTag;
import com.hotel.model.Region;
import com.hotel.model.User;
import com.hotel.service.FunctionService;
import com.hotel.viewmodel.BaseData;

@Scope("prototype")
@Controller
@RequestMapping("/web/base")
public class BaseDataAction extends BaseAction {
	
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
		@RequestMapping(value = "/baseDataList.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
		public String logInitBaseData(BaseData baseData, 
				HttpServletRequest request,
				HttpServletResponse response) {
			if (baseData.getPageNo() == null)
				baseData.setPageNo(1);
			baseData.setPageSize(Constants.DEFAULT_PAGE_SIZE);
			//加载菜单
			List<Function> lf = functionService.getFunctionByParentUrl("/web/base/baseDataList.do");
			User user = new User();
			user.setChildMenuList(lf);
			request.getSession().setAttribute(Constants.USER_SESSION_NAME,user);
			//request.setAttribute("company", company);
			request.setAttribute("baseData", baseData);
			return "web/base/baseDataList";
		}
		
		@RequestMapping(value = "/regionList.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
		public String logInitRegion(Region region, 
				HttpServletRequest request,
				HttpServletResponse response) {
			if (region.getPageNo() == null)
				region.setPageNo(1);
			region.setPageSize(Constants.DEFAULT_PAGE_SIZE);
//			//加载菜单
//			List<Function> lf = functionService.getFunctionByParentUrl("/web/base/baseDataList.do");
//			User user = new User();
//			user.setChildMenuList(lf);
//			request.getSession().setAttribute(Constants.USER_SESSION_NAME,user);
			//request.setAttribute("company", company);
			request.setAttribute("region", region);
			return "web/base/regionList";
		}
		
		@RequestMapping(value = "/itemTagList.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
		public String logInitItemTag(ItemTag itemTag, 
				HttpServletRequest request,
				HttpServletResponse response) {
			if (itemTag.getPageNo() == null)
				itemTag.setPageNo(1);
			itemTag.setPageSize(Constants.DEFAULT_PAGE_SIZE);
			//加载菜单
//			List<Function> lf = functionService.getFunctionByParentUrl("/web/base/baseDataList.do");
//			User user = new User();
//			user.setChildMenuList(lf);
//			request.getSession().setAttribute(Constants.USER_SESSION_NAME,user);
			//request.setAttribute("company", company);
			request.setAttribute("region", itemTag);
			return "web/base/itemTagList";
		}
		

		// [end]

}
