package com.hotel.app;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.hotel.common.ListResult;
import com.hotel.model.Menu;
import com.hotel.service.MenuService;

/**
 * 首页，启动页面APP数据接口
 * @author charo
 *
 */
public class HomeController {
	@Autowired MenuService menuService;
	/**
	 * 获取后台配置的菜单
	 * @param customerInfo
	 * @return
	 */
	@RequestMapping(value = "loadMenuList.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String loadMenuList(HttpServletRequest request)
	{
		List<Menu> menuList = menuService.getMenulist();
		if(menuList ==null||menuList.size() ==0){
			return new ListResult<Menu>(null,false,"获取菜单项失败").toJson();
		}
		return new ListResult<Menu>(menuList,true,"获取菜单项成功").toJson();
	} 
}
