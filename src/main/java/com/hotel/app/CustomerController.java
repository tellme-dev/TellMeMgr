package com.hotel.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * 顾客数据访问接口
 * @author charo
 *
 */
@Controller
@RequestMapping("/app/customer")
public class CustomerController {
	
	/**
	 * 注册用户
	 * @param autoInfo
	 * @return
	 */
	public @ResponseBody  String regsister(
			@RequestParam(value = "customerInfo", required = false) String customerInfo)
	{
		
		return null;
	}
	
	/**
	 * 获取短信验证码
	 * @param autoInfo
	 * @return  成功或失败
	 */
	public @ResponseBody String getSMSVerificationCode(
			@RequestParam(value = "mobile", required = false) String mobile)
	{
		return null;
	}
	
	/**
	 * 用户登录
	 * @param autoInfo
	 * @return
	 */
	public @ResponseBody String login(
			@RequestParam(value = "loginInfo", required = false) String loginInfo)
	{
		return null;
	} 
	/**
	 * 保存用户信息
	 * 保存用户修改后的个人资料
	 * @param autoInfo
	 * @return
	 */
	public @ResponseBody String saveCustomer(
			@RequestParam(value = "customerInfo", required = false) String customerInfo)
	{
		return null;
	}
	/**
	 * 获取客户基本信息
	 * @param autoInfo
	 * @return
	 */
	public @ResponseBody String loadCustomer(
			@RequestParam(value = "loginInfo", required = false) String loginInfo)
	{
		return null;
	}
	/**
	 * 获取用户访问、收藏、点赞、发帖，入住酒店、浏览等历史纪录
	 * @param autoInfo
	 * @return
	 */
	public @ResponseBody String loadHistoryInfo(
			@RequestParam(value = "customerInfo", required = false) String customerInfo)
	{
		return null;
	} 
	/**
	 * 保存浏览的页面（包括酒店，酒店项目，广告，发现，论坛等）
	 * @param customerInfo
	 * @return
	 */
	public @ResponseBody String saveBrowseHistory(
			@RequestParam(value = "browseInfo", required = false) String browseInfo)
	{
		return null;
	} 
	
}
