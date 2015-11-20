package com.hotel.web.controller;

import java.util.Date;
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
import com.hotel.common.utils.Constants;
import com.hotel.common.utils.EndecryptUtils;
import com.hotel.common.utils.GeneralUtil;
import com.hotel.model.Function;
import com.hotel.model.Org;
import com.hotel.model.User;
import com.hotel.service.BaseDataService;
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
	public String logInituser(User user, 
			//@RequestParam(value = "companyId", required = false) Integer companyId,
			HttpServletRequest request,
			HttpServletResponse response) {
		if (user.getPageNo() == null)
			user.setPageNo(1);
		user.setPageSize(Constants.DEFAULT_PAGE_SIZE);
		user.setIsUsed(true);
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
	
	@RequestMapping(value = "/userinfo.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String gotoUserInfo(
			User user,
			@RequestParam(value = "userId", required = false) Integer userId,
			HttpServletRequest request, HttpServletResponse response) {
		/**/
//		if(user.getId()!=null){
//			userId = user.getId();
//		}
		if(userId != 0){//编辑时加载编辑的用户详情
			user = userService.getUserByPrimaryKey(userId);
			request.setAttribute("userinfo", user);
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
	
	@RequestMapping(value = "/deleteUser.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public JsonResult deleteUser(
			@RequestParam(value = "userId", required = false) Integer id,
			HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult json = new JsonResult();
		json.setCode(new Integer(0));
		json.setMessage("删除失败!");
		try{
			userService.deleteUser(id);
			json.setCode(new Integer(1));
			json.setMessage("删除成功!");
			return json;
		}catch(Exception e){
			return json;
		}
	}

	// [end]

}
