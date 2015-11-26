package com.hotel.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hotel.common.ReturnResult;
import com.hotel.common.utils.Constants;
import com.hotel.common.utils.Page;
import com.hotel.model.Customer;
import com.hotel.model.Function;
import com.hotel.model.Hotel;
import com.hotel.model.User;
import com.hotel.service.FunctionService;

@Scope("prototype")
@Controller
@RequestMapping("/web/customer")
public class CustomerAction extends BaseAction {

	// [start] 接口引用
	@Resource(name="functionService")
	private FunctionService functionService;

	// [end]

	// [start] 员工公司模块 ---- 页面跳转

	
	/**
	 * 
	 * 
	 * @param customer
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/customerList.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String logInitcustomer(Page page,
			HttpServletRequest request,
			HttpServletResponse response) {
		/*分页参数*/
		if (page.getPageNo() == null){
			page.setPageNo(1);
		}
		page.setPageSize(Constants.DEFAULT_PAGE_SIZE);
		//加载菜单
		List<Function> lf = functionService.getFunctionByParentUrl("/web/customer/customerList.do");
		User user = new User();
		user.setChildMenuList(lf);
		request.getSession().setAttribute(Constants.USER_SESSION_NAME,user);
		//page.setTotalCount(totalCount);
		return "web/customer/customerList";
	}
	

	// [end]

}
