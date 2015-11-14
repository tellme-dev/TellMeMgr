package com.hotel.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.common.JsonResult;
import com.hotel.common.ReturnResult;
import com.hotel.common.utils.Constants;
import com.hotel.model.Function;
import com.hotel.model.User;
import com.hotel.service.FunctionService;
import com.hotel.service.UserService;

@Scope("prototype")
@Controller
public class LoginAction extends BaseAction {
	
	@Resource(name="userService")
	private UserService userService;
	
	@Resource(name="functionService")
	private FunctionService functionService;
	
	@ResponseBody
	@RequestMapping(value="/login.do", method=RequestMethod.POST)
	public JsonResult <User> login(User user, HttpServletRequest request, HttpServletResponse response){
		JsonResult <User>json  = new JsonResult<User>();
		json.setCode(new Integer(1));
		json.setMessage("登录失败!");
		try{
			ReturnResult<User> res = userService.login(user.getName(), user.getPsd(),user.isRememberMe());
			if(res.getCode() == ReturnResult.SUCCESS){
				List<Function> lf = parseFunctionList(functionService.getFunctionByParentId(0));
				request.getSession().setAttribute(Constants.USER_SESSION_FUNCTION,lf);
				
				res.getResultObject().setSelectedMainMemu(lf.get(0).getId());
				res.getResultObject().setSelectedChildMenu(lf.get(0).getChildFunctionlist().get(0).getId());
				res.getResultObject().setChildMenuList(lf.get(0).getChildFunctionlist());
				this.setLoginUser(res.getResultObject());
				
				json.setCode(new Integer(0));
//				json.setGotoUrl(lf.get(0).getUrl());
//				json.setGotoUrl("web/user/userinfo.do");
				//模块拆分
				json.setGotoUrl("web/user/userList.do");
				json.setMessage("登录成功!");
			}else{				
				json.setMessage(res.getMessage());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return json;
	}
	
	private List<Function> parseFunctionList(List<Function> src){
		for(Function f : src){
			f.setChildFunctionlist(functionService.getFunctionByParentId(f.getId()));
		}
		return src;
	}
	
} 
