package com.hotel.app;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.hotel.common.Result;
import com.hotel.common.utils.EndecryptUtils;
import com.hotel.common.utils.GeneralUtil;
import com.hotel.common.utils.StringUtil;
import com.hotel.model.Customer;
import com.hotel.model.CustomerCollection;
import com.hotel.model.User;
import com.hotel.model.Varifycode;
import com.hotel.modelVM.RegisterData;
import com.hotel.service.CustomerCollectionService;
import com.hotel.service.CustomerService;
import com.hotel.service.VarifycodeService;

/**
 * 顾客数据访问接口
 * @author charo
 *
 */
@Controller
@RequestMapping("/app/customer")
public class CustomerController {
	
	private final static int DEFAULT_VARIFY_TIME = 2*60;//秒
	
	@Autowired CustomerService customerService;
	@Autowired CustomerCollectionService customerCollectionService;
	@Autowired VarifycodeService varifycodeService;
	/**
	 * 判断用户是否注册
	 * @param autoInfo
	 * @return
	 */
	@RequestMapping(value = "isExistByMobile.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody  String isExistByMobile(
			@RequestParam(value = "mobile", required = false) String mobile,
			HttpServletRequest request)
	{
		Customer c = customerService.getCustomerByMobile(mobile);
		if(c !=null){
			return new Result<Customer>(null,false,"该号码已注册").toJson();
		}
		return new Result<Customer>(null,true,"该号码未注册").toJson();
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
		Result<Object> result = null;
		
		if(mobile ==null ||"".equals(mobile)){
			return new Result<Object>(null, false, "请输入电话号码").toJson();
		}
		
		HashMap hMap = null;
		CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
		restAPI.init("app.cloopen.com", "8883"); 
		//restAPI.init("sandboxapp.cloopen.com", "8883"); 

		// 初始化服务器地址和端口，沙盒环境配置成sandboxapp.cloopen.com，生产环境配置成app.cloopen.com，端口都是8883. 
		restAPI.setAccount("8a48b55150f4a7260150fa741c1118ec","5e487ba9d0de4bf080cf847853b45d03"); 
		// 初始化主账号名称和主账号令牌，登陆云通讯网站后，可在"控制台-应用"中看到开发者主账号ACCOUNT SID和
		// 主账号令牌AUTH TOKEN。
		restAPI.setAppId("aaf98f89512446e2015138cc2f883f66");
		// 初始化应用ID，如果是在沙盒环境开发，请配置"控制台-应用-测试DEMO"中的APPID。
		// 如切换到生产环境，请使用自己创建应用的APPID
		String verifCode = GeneralUtil.createVerifCode();
		hMap = restAPI.sendTemplateSMS(mobile,"51106", new String[] { verifCode, "2" });
		
		if ("000000".equals(hMap.get("statusCode"))) {
			Varifycode v = new Varifycode();
			v.setMobile(mobile);
			v.setVarifyCode(verifCode);
			v.setCreatetime(new Date());
			
			varifycodeService.insert(v);
			
			result = new Result<Object>(null, true, "获取短信验证码成功");
		}else{
			String retMsg = hMap.get("statusMsg").toString();
			result = new Result<Object>(null, true, retMsg); 
		}
		return result.toJson();

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
		if(temp ==1){
			customer = customerService.getCustomerByMobile(customer.getMobile());
			return new Result<Customer>(customer,true,"登录成功").toJson();
		}
		return new Result<Customer>(null,false,""+temp).toJson();
	} 
	/**
	 * 用户注册
	 * @param registerData
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "register.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String register(
			@RequestParam(value = "registerData", required = false) String registerData,
			HttpServletRequest request){
		JSONObject jObj = JSONObject.fromObject(registerData);
		RegisterData registerInfo = (RegisterData) JSONObject.toBean(jObj,RegisterData.class);
		//判断注册信息是否符合格式		
		String checkMsg =this.checkRegisterData(registerInfo);
		
		if(!StringUtil.isEmpty(checkMsg)){
			return new Result<Object>(null, false, checkMsg).toJson();
		}
		//判断该电话号码是否已经注册
		Customer c = customerService.getCustomerByMobile(registerInfo.getMobile());
		if(c !=null){
			return new Result<Customer>(null,false,"该号码已注册").toJson();
		}
		//判断输入的验证码是否正确
		Varifycode v = varifycodeService.selectByMobile(registerInfo.getMobile());
		if(!v.getVarifyCode().equals(registerInfo.getVerifyCode())){
			return new Result<Customer>(null,false,"验证码输入不正确").toJson();
		}
		long seconds = this.getBetweenDate(v.getCreatetime(), new Date());
		if(seconds==0&&seconds>=DEFAULT_VARIFY_TIME){
			return new Result<Customer>(null,false,"验证码已过期").toJson();
		}
		
		//插入数据
		
		
		User u = EndecryptUtils.md5Password(registerInfo.getMobile(), "111111");
		Customer customer = new Customer();
		customer.setMobile(u.getName());
		customer.setPsd(u.getPsd());
		customer.setSalt(u.getSalt());
		customer.setRegTime(new Date());
		
		int temp = customerService.insert(customer);
		if(temp ==-1){
			return new Result<Object>(null, false, "注册失败").toJson();
		}else{
			return new Result<Object>(null, true, "注册成功").toJson();
		}
	}
	/**
	 * 获取两个时间之间的秒差
	 * @param min
	 * @param max
	 * @return
	 * @author hzf
	 */
	private long getBetweenDate(Date min,Date max){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String minStr = sdf.format(min);
		String maxStr = sdf.format(max);
		try
		{
		    Date d1 = sdf.parse(maxStr);
		    Date d2 = sdf.parse(minStr);
		    long diff = d1.getTime() - d2.getTime();
		    long seconds = diff / (1000 );
		    return seconds;
		}
		catch (Exception e)
		{
			return 0;
		}
	}
	@Test
	public void test(){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try
		{
		    Date d1 = df.parse("2004-03-26 13:40:40");
		    Date d2 = df.parse("2004-03-26 13:40:20");
		    long diff = d1.getTime() - d2.getTime();
		    long days = diff / (1000 );
		    System.out.println(diff);
		    System.out.println(days);
		}
		catch (Exception e)
		{
		}
	}
	/**
	 * 插件传入的注册信息是否符合规范
	 * @param data
	 * @return 表示注册信息OK;表示电话号码无效;电话号码为空;密码为空;验证码为空
	 */
	private String checkRegisterData(RegisterData data){
		if(!StringUtil.isMobileNumber(data.getMobile())){
			return "电话号码无效";
		}
		if(StringUtil.isEmpty(data.getMobile())){
			return "电话号码为空";
		}
		if(StringUtil.isEmpty(data.getPsd())){
			return "密码为空";
		}
		if(StringUtil.isEmpty(data.getVerifyCode())){
			return "验证码为空";
		}
		return "";
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
	 * 保存用户收藏/关注（包括酒店(服务)、广告(专题)、论坛等）
	 * @author LiuTaiXiong
	 * @param customerInfo
	 * @return
	 */
	@RequestMapping(value = "/saveCollectionHistory.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody Result<String> saveCollectionHistory(
			@RequestParam(value = "json", required = false) String json)
	{
		int ctype = 0;
		int customerId = 0;
		int targetId = 0;
		try{
			JSONObject jsonObject = JSONObject.fromObject(json);
			if(jsonObject.containsKey("collectionType")){
				ctype = jsonObject.getInt("collectionType");
			}
			if(jsonObject.containsKey("customerId")){
				customerId = jsonObject.getInt("customerId");
			}
			if(jsonObject.containsKey("targetId")){
				targetId = jsonObject.getInt("targetId");
			}
		}catch(Exception e){
			e.printStackTrace();
			return new Result<String>("", false, "json解析异常");
		}
		if(ctype < 1 || customerId < 1 || targetId < 1){
			return new Result<String>("", false, "请求无效");
		}
		CustomerCollection collection = new CustomerCollection();
		collection.setCollectionType(ctype);
		collection.setCustomerId(customerId);
		collection.setTargetId(targetId);
		collection.setCreateTime(new Date());
		
		int count = customerCollectionService.insert(collection);
		if(count > 0){
			return new Result<String>("", true, "");
		}
		return new Result<String>("", false, "收藏失败");
	}
	
	/**
	 * 用户修改密码
	 * @author LiuTaiXiong
	 * @param customerInfo
	 * @return
	 */
	@RequestMapping(value = "/updatePassword.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody Result<String> updatePassword(
			@RequestParam(value = "json", required = false) String json)
	{
		int customerId = 0;
		String password = "";
		String oldPassword = "";
		try{
			JSONObject jsonObject = JSONObject.fromObject(json);
			if(jsonObject.containsKey("customerId")){
				customerId = jsonObject.getInt("customerId");
			}
			if(jsonObject.containsKey("password")){
				password = jsonObject.getString("password");
			}
			if(jsonObject.containsKey("oldPassword")){
				oldPassword = jsonObject.getString("oldPassword");
			}
		}catch(Exception e){
			e.printStackTrace();
			return new Result<String>("", false, "json解析异常");
		}
		if(customerId < 1 || password.trim().equals("") || oldPassword.trim().equals("")){
			return new Result<String>("", false, "请求无效");
		}
		
		Customer customer = customerService.selectByPrimaryKey(customerId);
		
		//设置密码
		User nu = EndecryptUtils.md5Password(customer.getMobile(), password);
		User ou = EndecryptUtils.md5Password(customer.getMobile(), oldPassword);
		
		Customer record = new Customer();
		record.setId(customerId);
		record.setName(nu.getPsd());
		record.setPsd(ou.getPsd());
		
		int count = customerService.updatePassword(record);
		if(count > 0){
			return new Result<String>("", true, "");
		}
		return new Result<String>("", false, "修改密码失败");
	} 
	
	/**
	 * 用户找回密码
	 * @author LiuTaiXiong
	 * @param customerInfo
	 * @return
	 */
	@RequestMapping(value = "/setPassword.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody Result<String> setPassword(
			@RequestParam(value = "json", required = false) String json)
	{
		String phoneNumber = "";
		String validateCode = "";
		String password = "";
		try{
			JSONObject jsonObject = JSONObject.fromObject(json);
			if(jsonObject.containsKey("phoneNumber")){
				phoneNumber = jsonObject.getString("phoneNumber");
			}
			if(jsonObject.containsKey("password")){
				password = jsonObject.getString("password");
			}
			if(jsonObject.containsKey("validateCode")){
				validateCode = jsonObject.getString("validateCode");
			}
		}catch(Exception e){
			e.printStackTrace();
			return new Result<String>("", false, "json解析异常");
		}
		if(phoneNumber.trim().equals("") || password.trim().equals("") || validateCode.trim().equals("")){
			return new Result<String>("", false, "请求无效");
		}
		
		Customer record = customerService.getCustomerByMobile(phoneNumber);
		if(record == null){
			return new Result<String>("", false, "该手机号尚未注册");
		}
		
		Varifycode varifycode = varifycodeService.selectByMobile(phoneNumber);
		long oldTime = varifycode.getCreatetime().getTime();
		long newTime = new Date().getTime();
		//验证码时间验证--2分钟
		if(newTime - oldTime > 120000){
			return new Result<String>("", false, "验证码已失效");
		}
		//验证码错误
		if(!varifycode.getVarifyCode().equals(validateCode)){
			return new Result<String>("", false, "验证码错误");
		}
		
		
		//设置密码
		User u = EndecryptUtils.md5Password(phoneNumber, password);
		int count = customerService.setPassword(record.getId(), u.getPsd());
		if(count > 0){
			return new Result<String>("", true, "");
		}
		return new Result<String>("", false, "找回密码失败");
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
	
	/**
	 * 收藏bbs帖子
	 * @author jun
	 * @param param
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "collectBbs.do", produces = "application/json;charset=UTF-8")
	public String collectBbs(
			@RequestParam(value = "param", required = false) String param,
			HttpServletRequest request){
		JSONObject jObj = JSONObject.fromObject(param);
		CustomerCollection cc = (CustomerCollection) JSONObject.toBean(jObj,CustomerCollection.class);
		Result<CustomerCollection> result = null;
		try{
			cc.setCreateTime(new Date());
			customerService.saveCollection(cc);
			result = new Result<CustomerCollection>(null, true, "收藏成功");
			return result.toJson();
		}catch(Exception e){
			result = new Result<CustomerCollection>(null, false, "收藏失败");
			return result.toJson();
		}
	}
	
}
