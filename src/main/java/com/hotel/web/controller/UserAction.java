package com.hotel.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.common.JsonResult;
import com.hotel.common.Result;
import com.hotel.common.utils.Constants;
import com.hotel.common.utils.EndecryptUtils;
import com.hotel.common.utils.GeneralUtil;
import com.hotel.common.utils.Page;
import com.hotel.model.Function;
import com.hotel.model.Hotel;
import com.hotel.model.Org;
import com.hotel.model.User;
import com.hotel.service.BaseDataService;
import com.hotel.service.FunctionService;
import com.hotel.service.UserService;
import com.hotel.viewmodel.UserVM;

@Scope("prototype")
@Controller
@RequestMapping("/web/user")
public class UserAction extends BaseAction {

	// [start] 接口引用
	@Resource(name = "userService")
	private UserService userService;
	
	@Resource(name="functionService")
	private FunctionService functionService;
	
	@Resource(name="baseDataService")
	private BaseDataService baseDataService;

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
	public String logInituser(Page page,
			User user, 
			//@RequestParam(value = "companyId", required = false) Integer companyId,
			HttpServletRequest request,
			HttpServletResponse response) {
		/*分页参数*/
		if (page.getPageNo() == null){
			page.setPageNo(1);
		}
		page.setPageSize(Constants.DEFAULT_PAGE_SIZE);
		//加载菜单
		List<Function> lf = functionService.getFunctionByParentUrl("/web/user/userList.do");
		User u = new User();
		u.setChildMenuList(lf);
		request.getSession().setAttribute(Constants.USER_SESSION_NAME,u);
		/*加载数据，数量*/
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageStart",page.getPageStart());
		map.put("pageSize",page.getPageSize());
		List<User> lc = userService.getUserPageList(map);
		int totalCount = userService.getUserPageListCount(map);
		page.setTotalCount(totalCount);
		request.setAttribute("userlist", lc);
		return "web/user/userList";
	}
	
	@RequestMapping(value = "/userinfo.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String gotoUserInfo(
			UserVM user,
			@RequestParam(value = "userId", required = false) Integer userId,
			HttpServletRequest request, HttpServletResponse response) {
		/**/
//		if(user.getId()!=null){
//			userId = user.getId();
//		}
		if(userId != 0){//编辑时加载编辑的用户详情
			user = userService.getUserByID(userId);
			request.setAttribute("userinfo", user);
			request.setAttribute("type", Constants.EDIT_TYPE);
		}else{
			request.setAttribute("type", Constants.ADD_TYPE);
		}
		return "web/user/userInfo";
	}
	
	@ResponseBody
	@RequestMapping(value = "/saveOrupdateUser.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public JsonResult<User> saveOrupdateUser(
			User user, 
			HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult<User> js = new JsonResult<User>();
		js.setCode(new Integer(0));
		js.setMessage("保存失败!");
		try {
			User u = userService.getUserByName(user.getName());
			if(u!=null){
				js.setMessage("用户名已存在!");
				return js;
			}
			/*新增时没有传id值*/
			if(user.getId()==null){
				user.setId(0);
			}
			userService.saveorUpdateUser(user);
			js.setCode(new Integer(1));
			js.setMessage("保存成功!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js;
	}
	@ResponseBody
	@RequestMapping(value = "/jsonLoadOrgList.do", produces = "text/html;charset=UTF-8")
	public String loadOrgComboList(
			@RequestParam(value = "pid", required = false) Integer pid,
			HttpServletRequest request,
			HttpServletResponse response) {
		try{
			List<Org> list = baseDataService.getOrgList(pid);
			//request.setAttribute("region", itemTag);
			JSONArray  json = JSONArray.fromObject(list);
			return json.toString();
		}catch(Exception e){
			return "";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/jsonDeleteUser.do",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String deleteUser(
			@RequestParam(value = "userIds", required = false) String userIds,
			HttpServletRequest request,
			HttpServletResponse response) {
		Result<User> result = null;
		try{
			userService.deleteUserByIds(userIds);
			result = new Result<User>(null, true, "删除成功!");
			return result.toJson();
		}catch(Exception e){
			result = new Result<User>(null, false, "删除失败!");
			return result.toJson();
		}
	}

	// [end]

}
