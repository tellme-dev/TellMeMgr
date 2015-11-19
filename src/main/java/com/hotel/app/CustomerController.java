package com.hotel.app;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.hotel.common.Result;
import com.hotel.model.Customer;
import com.hotel.service.CustomerService;
/**
 * 顾客数据访问接口
 * @author charo
 *
 */
@Controller
@RequestMapping("/app/customer")
public class CustomerController {
	@Autowired CustomerService customerService;
	
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
	@RequestMapping(value = "sendSMSVerificationCode.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String sendSMSVerificationCode(
			@RequestParam(value = "mobile", required = false) String mobile,
			HttpServletRequest request)
	{
		//Result<Object> result = null;
		
		if(mobile ==null ||"".equals(mobile)){
			return new Result<Object>(null, false, "请输入电话号码").toJson();
		}
		
//		HashMap hMap = null;
//		CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
//		//restAPI.init("app.cloopen.com", "8883"); 
//		restAPI.init("sandboxapp.cloopen.com", "8883"); 
//
//		// 初始化服务器地址和端口，沙盒环境配置成sandboxapp.cloopen.com，生产环境配置成app.cloopen.com，端口都是8883. 
//		restAPI.setAccount("aaf98f894dd77eab014ddb6a41de0252","fc045501549c41bb8b44a8580865ef97"); 
//		// 初始化主账号名称和主账号令牌，登陆云通讯网站后，可在"控制台-应用"中看到开发者主账号ACCOUNT SID和
//		// 主账号令牌AUTH TOKEN。
//		restAPI.setAppId("aaf98f894dd77eab014ddc85adb403e9");
//		// 初始化应用ID，如果是在沙盒环境开发，请配置"控制台-应用-测试DEMO"中的APPID。
//		// 如切换到生产环境，请使用自己创建应用的APPID
//		String verifCode = GeneralUtil.createVerifCode();
//		hMap = restAPI.sendTemplateSMS(mobile,"22423", new String[] { verifCode, "2" });
//		
//		if ("000000".equals(hMap.get("statusCode"))) {
//			@SuppressWarnings("unchecked")
//			Map<String, SmsInfo> smss = (Map<String, SmsInfo>) request.getSession()
//					.getAttribute("verifCodes");
//
//			if (smss == null) {
//				smss = new HashMap<String, SmsInfo>();
//				request.getSession().setAttribute("verifCodes", smss);
//			}
//			
//			SmsInfo smsInfo = new SmsInfo(); 
//			smsInfo.setMobile(mobile); 
//			smsInfo.setVerifCode(verifCode);
//			smsInfo.setSendTime(new Date());
//			smss.put(mobile, smsInfo);
//			result = new Result<Object>(null, true, verifCode); 
//		}else{
//			String retMsg = hMap.get("statusMsg").toString();
//			result = new Result<Object>(null, true, retMsg); 
//		}
		return new Result<Object>(null,true,"获取短信验证码成功").toJson();
	}
	
	/**
	 * 用户登录
	 * @param autoInfo
	 * @return
	 */
	@RequestMapping(value = "login.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String login(
			@RequestParam(value = "loginData", required = false) String loginData,
			HttpServletRequest request)
	{
		JSONObject jObj = JSONObject.fromObject(loginData);
		Customer customer = (Customer) JSONObject.toBean(jObj,Customer.class);
		int temp = customerService.login(customer.getMobile(), customer.getPsd());
		Result<Integer> result = new Result<Integer>(temp,true,"");
		return result.toJson();
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
