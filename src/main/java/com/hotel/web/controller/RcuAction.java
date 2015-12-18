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
import com.hotel.common.utils.Page;
import com.hotel.model.Function;
import com.hotel.model.User;
import com.hotel.service.FunctionService;

@Scope("prototype")
@Controller
@RequestMapping("/web/rcu")
public class RcuAction extends BaseAction {

	@Resource(name="functionService")
	private FunctionService functionService;

	
	
	@RequestMapping(value = "/rcu.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String logInitrcu(Page page,
			HttpServletRequest request,
			HttpServletResponse response) {
		/*分页参数*/
		if (page.getPageNo() == null){
			page.setPageNo(1);
		}
		page.setPageSize(Constants.DEFAULT_PAGE_SIZE);
		//加载菜单
		List<Function> lf = functionService.getFunctionByParentUrl("/web/rcu/rcu.do");
		User user = new User();
		user.setChildMenuList(lf);
		request.getSession().setAttribute(Constants.USER_SESSION_NAME,user);
		//page.setTotalCount(totalCount);
		return "web/rcu/rcu";
	}
	

	// [end]

}
