package com.hotel.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hotel.common.utils.Constants;
import com.hotel.model.Function;
import com.hotel.model.User;
import com.hotel.service.FunctionService;
import com.hotel.service.UserService;

@Scope("prototype")
@Controller
@RequestMapping("/web/user")
public class UserAction extends BaseAction {

	// [start] 接口引用
	@Resource(name = "userService")
	private UserService userService;
	
	@Resource(name="functionService")
	private FunctionService functionService;

	// [end]

	// [start] 员工公司模块 ---- 页面跳转

	
	/**
	 * 页面跳转到员工列表，加载员工所属公司列表
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/userList.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String logInituser(User user, 
			@RequestParam(value = "companyId", required = false) Integer companyId,
			HttpServletRequest request,
			HttpServletResponse response) {
		if (user.getPageNo() == null)
			user.setPageNo(1);
		user.setPageSize(Constants.DEFAULT_PAGE_SIZE);
		//加载菜单
		List<Function> lf = functionService.getFunctionByParentUrl("/web/user/userList.do");
		User u = new User();
		u.setChildMenuList(lf);
		request.getSession().setAttribute(Constants.USER_SESSION_NAME,u);
		//user.setCompanyId(companyId); 
		//Company company = companyService.getCompanyById(companyId);
		List<User> lc = userService.getUserPageList(user);
		int totalCount = userService.getUserPageListCount(user);
		user.setTotalCount(totalCount);
		//request.setAttribute("company", company);
		request.setAttribute("user", user);
		request.setAttribute("userlist", lc);
		return "web/user/userList";
	}
	

	// [end]

}
