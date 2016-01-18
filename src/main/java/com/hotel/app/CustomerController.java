package com.hotel.app;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.hotel.common.ListResult;
import com.hotel.common.Result;
import com.hotel.common.utils.BbsContainer;
import com.hotel.common.utils.EndecryptUtils;
import com.hotel.common.utils.GeneralUtil;
import com.hotel.common.utils.StringUtil;
import com.hotel.model.AdDetail;
import com.hotel.model.Advertisement;
import com.hotel.model.Bbs;
import com.hotel.model.Customer;
import com.hotel.model.CustomerBrowse;
import com.hotel.model.CustomerCollection;
import com.hotel.model.Hotel;
import com.hotel.model.Item;
import com.hotel.model.ItemDetail;
import com.hotel.model.Region;
import com.hotel.model.User;
import com.hotel.model.Varifycode;
import com.hotel.modelVM.AdvertisementListInfoVM;
import com.hotel.modelVM.AdvertisementVM;
import com.hotel.modelVM.BbsCommentVM;
import com.hotel.modelVM.BbsDynamicVM;
import com.hotel.modelVM.BbsVM;
import com.hotel.modelVM.CountVM;
import com.hotel.modelVM.CustomerBrowseVM;
import com.hotel.modelVM.CustomerVM;
import com.hotel.modelVM.HotelListInfoVM;
import com.hotel.modelVM.RegisterData;
import com.hotel.service.AdService;
import com.hotel.service.AdvertisementService;
import com.hotel.service.BaseDataService;
import com.hotel.service.BbsService;
import com.hotel.service.CustomerBrowseService;
import com.hotel.service.CustomerCollectionService;
import com.hotel.service.CustomerService;
import com.hotel.service.HotelService;
import com.hotel.service.ItemDetailService;
import com.hotel.service.ItemService;
import com.hotel.service.ItemTagAssociationService;
import com.hotel.service.RoomCheckService;
import com.hotel.service.VarifycodeService;

/**
 * 顾客数据访问接口
 * @author charo
 *
 */
@Controller
@RequestMapping("/app/customer")
public class CustomerController {
	private final static int DEFAULT_PAGE_SIZE = 10;
	//酒店本身的标签类型
//	private final static int TAG_TYPE_HOTEL = 1;
	//酒店本身的标签类型
	private final static String TAG_NAME_HOTEL_DEFAULT = "介绍";
	
	//酒店收藏类型
	private final static int BROWSE_TYPE_HOTEL = 1;
	//广告收藏类型
	private final static int BROWSE_TYPE_AD = 2;
	//论坛收藏类型
	private final static int BROWSE_TYPE_BBS = 3;
	
	private final static int DEFAULT_VARIFY_TIME = 2*60;//秒
	
	@Autowired CustomerService customerService;
	@Autowired AdService adService;
	@Autowired AdvertisementService advertisementService;
	@Autowired CustomerCollectionService customerCollectionService;
	@Autowired VarifycodeService varifycodeService;
	@Autowired BbsService bbsService;
	
	@Autowired RoomCheckService roomCheckService;
	@Autowired CustomerBrowseService customerBrowseService;
	@Autowired HotelService hotelService;
	@Autowired ItemTagAssociationService itemTagAssociationService;
	@Autowired ItemService itemService;
	@Autowired ItemDetailService itemDetailService;
	@Autowired BaseDataService baseDataService;
	
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
		JSONObject jObj = JSONObject.fromObject(mobile);
		String phone = jObj.getString("mobile");
		Customer c = customerService.getCustomerByMobile(phone);
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
		
		
		User u = EndecryptUtils.md5Password(registerInfo.getMobile(), registerInfo.getPsd());
		Customer customer = new Customer();
		customer.setMobile(u.getName());
		customer.setPsd(u.getPsd());
		customer.setSalt(u.getSalt());
		customer.setRegTime(new Date());
		String name=u.getName().substring(0,3)+"****"+u.getName().substring(7);//隐藏中间四位电话号码作昵称
		customer.setName(name);
		
		int temp = customerService.insert(customer);
		if(temp ==-1){
			return new Result<Object>(null, false, "注册失败").toJson();
		}else{
			return new Result<Object>(null, true, "注册成功").toJson();
		}
	}
	/**
	 * 修改电话号码
	 * @author jun
	 * @param registerData
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "editMobile.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String editMobile(
			@RequestParam(value = "registerData", required = false) String registerData,
			HttpServletRequest request){
		JSONObject jObj = JSONObject.fromObject(registerData);
		if(jObj.getString("id")==null||"".equals(jObj.getString("id"))){
			return new Result<Object>(null, false, "传入后台用户id为空").toJson();
		}
		RegisterData registerInfo = new RegisterData();
		registerInfo.setMobile(jObj.getString("mobile"));
		registerInfo.setPsd(jObj.getString("psd"));
		registerInfo.setVerifyCode(jObj.getString("verifyCode"));
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
		User u = EndecryptUtils.md5Password(registerInfo.getMobile(), registerInfo.getPsd());
		Customer customer = new Customer();
		customer.setMobile(u.getName());
		customer.setPsd(u.getPsd());
		customer.setSalt(u.getSalt());
		customer.setId(jObj.getInt("id"));
		
		try{
			customerService.update(customer);
			return new Result<Object>(null, true, "注册成功").toJson();
		}catch(Exception e){
			return new Result<Object>(null, false, "注册失败").toJson();
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
	 * @author jun
	 * @param autoInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="saveCustomer.do",produces = "application/json;charset=UTF-8")
	public Result<Customer> saveCustomer(
			@RequestParam(value = "customerInfo", required = false) String customerInfo)
	{
		JSONObject jObj = JSONObject.fromObject(customerInfo);
		String birthday = jObj.getString("birthday");
		Customer customer = (Customer) JSONObject.toBean(jObj,Customer.class);
		if(customer == null||customer.getId() == null){
			return new Result<Customer>(null,false,"传入后台参数为空或缺少");
		}
		try{
			Date birthDay = GeneralUtil.strToDate(birthday);//传的是String类型，转换成Date
			customer.setBirthday(birthDay);
			customerService.update(customer);
			return new Result<Customer>(null,true,"保存成功");
		}catch(Exception e){
			return new Result<Customer>(null, false, "保存失败");
		}
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
	 * 获取用户个人中心基本信息
	 * @author LiuTaiXiong
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/getCustomerInfo.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody Result<CustomerVM> getCustomerInfo(
			@RequestParam(value = "json", required = false) String json)
	{
		int customerId = 0;
		try{
			JSONObject jsonObject = JSONObject.fromObject(json);
			if(jsonObject.containsKey("customerId")){
				customerId = jsonObject.getInt("customerId");
			}
		}catch(Exception e){
			e.printStackTrace();
			return new Result<CustomerVM>(null, false, "json解析异常");
		}
		if(customerId < 1){
			return new Result<CustomerVM>(null, false, "请求无效");
		}
		
		Customer customer = customerService.selectByPrimaryKey(customerId);
		
		if(customer == null){
			return new Result<CustomerVM>(null, false, "没有找到对应的用户信息");
		}
		
		CustomerVM vm = new CustomerVM();
		vm.setCustomer(customer);
		vm.setCountAlways(roomCheckService.countHotelByCustomer(customerId));
		vm.setCountBrowse(customerBrowseService.countByCustomer(customerId));
		vm.setCountCollection(customerCollectionService.countByCustomer(customerId));
		vm.setCountTopic(bbsService.countPostByCustomer(customerId));
		//int countPBy = bbsService.countDPraiseByCustomer(customerId);
		int countPTo = bbsService.countDNewPraiseToCustomer(customerId);
		//int countCBy = bbsService.countDCommentByCustomer(customerId);
		int countCTo = bbsService.countDNewCommentToCustomer(customerId);
		//vm.setCountDynamic(countPBy + countPTo + countCBy + countCTo);
		vm.setCountDynamic(countPTo + countCTo);
		
		
		return new Result<CustomerVM>(vm, true, "获取数据成功");
	}
	
	/**
	 * 获取用户常住酒店
	 * @author LiuTaiXiong
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/getCustomerAlways.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody ListResult<HotelListInfoVM> getCustomerAlways(
			@RequestParam(value = "json", required = false) String json)
	{
		int customerId = 0;
		int pageNumber = 1;
		int pageSize = DEFAULT_PAGE_SIZE;
		try{
			JSONObject jsonObject = JSONObject.fromObject(json);
			if(jsonObject.containsKey("customerId")){
				customerId = jsonObject.getInt("customerId");
			}
			if(jsonObject.containsKey("pageNumber")){
				pageNumber = jsonObject.getInt("pageNumber");
			}
			if(jsonObject.containsKey("pageSize")){
				pageSize = jsonObject.getInt("pageSize");
			}
		}catch(Exception e){
			e.printStackTrace();
			return new ListResult<HotelListInfoVM>(null, false, "json解析异常");
		}
		if(customerId < 1){
			return new ListResult<HotelListInfoVM>(null, false, "请求无效");
		}
		if(pageNumber < 1){
			return new ListResult<HotelListInfoVM>(null, false, "请求无效");
		}
		
		//获取数据
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageStart", 0);
		map.put("pageSize", pageSize * pageNumber);
		map.put("customerId", customerId);
		
		List<Hotel> lh = hotelService.getPageHotelByCustomer(map);
		int count = roomCheckService.countHotelByCustomer(customerId);
		
		List<HotelListInfoVM> list = new ArrayList<HotelListInfoVM>();
		if(lh != null && lh.size() > 0){
			Map<String, Object> imap = new HashMap<String, Object>();
			imap.put("tagName", TAG_NAME_HOTEL_DEFAULT);
			//逐一转存
			for(Hotel hotel : lh){
				//转存酒店基本数据
				HotelListInfoVM vm = new HotelListInfoVM();
				vm.setId(hotel.getId());
				vm.setName(hotel.getName());
				vm.setLogo(hotel.getLogo());
				vm.setText(hotel.getText());
				vm.setLatitude(hotel.getLatitude());
				vm.setLongitude(hotel.getLongitude());
				
				imap.put("hotelId", hotel.getId());
				List<Item> introItem = itemService.selectItemByHotelAndTagName(imap);
				
				if(introItem != null && introItem.size() > 0){
					Item temp = introItem.get(0);
					vm.setTel(temp.getTel());
					vm.setAddress(temp.getPosition());
					vm.setScore(temp.getScore());
					List<ItemDetail> details = itemDetailService.selectByItemId(temp.getId());
					if(details != null && details.size() > 0){
						//仅获取第一张图片
						vm.setImgUrl(details.get(0).getImageUrl());
					}
				}
				
				//查询所有酒店所有的项目
				List<Item> items = itemService.getItemByHotel(hotel.getId());
				
				if(hotel.getRegionId() != null){
					//设置位置
					Region area = baseDataService.getRegionById(hotel.getRegionId());
					String path = area.getPath();
					String[] arr = path.split("\\.");
					//Region city = baseDataService.getRegionById(new Integer(arr[1]));
					vm.setCity(arr[1]);
				}
				vm.setProjects(items);
				
				//空数据清理
				vm.clear();
				//添加到数据集
				list.add(vm);
			}
		}
		
		int pageCount = count/pageSize;
		if(count%pageSize != 0){
			pageCount ++;
		}
		
		//返回对象处理
		ListResult<HotelListInfoVM> result = new ListResult<HotelListInfoVM>();
		result.setIsSuccess(true);
		result.setTotal(pageCount);
		result.setMsg("");
		result.setRows(list);
		
		return result;
	}
	
	/**
	 * 获取用户最近浏览
	 * @author LiuTaiXiong
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/getCustomerBrowse.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody ListResult<CustomerBrowseVM> getCustomerBrowse(
			@RequestParam(value = "json", required = false) String json)
	{
		int customerId = 0;
		int pageNumber = 1;
		int pageSize = DEFAULT_PAGE_SIZE;
		try{
			JSONObject jsonObject = JSONObject.fromObject(json);
			if(jsonObject.containsKey("customerId")){
				customerId = jsonObject.getInt("customerId");
			}
			if(jsonObject.containsKey("pageNumber")){
				pageNumber = jsonObject.getInt("pageNumber");
			}
			if(jsonObject.containsKey("pageSize")){
				pageSize = jsonObject.getInt("pageSize");
			}
		}catch(Exception e){
			e.printStackTrace();
			return new ListResult<CustomerBrowseVM>(null, false, "json解析异常");
		}
		if(customerId < 1){
			return new ListResult<CustomerBrowseVM>(null, false, "请求无效");
		}
		if(pageNumber < 1){
			return new ListResult<CustomerBrowseVM>(null, false, "请求无效");
		}
		
		//获取数据
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageStart", 0);
		map.put("pageSize", pageSize * pageNumber);
		map.put("customerId", customerId);
		
		//浏览记录数据获取
		List<CustomerBrowse> browses = customerBrowseService.getPageByCustomer(map);
		int count = customerBrowseService.countByCustomer(customerId);
		
		int pageCount = count/pageSize;
		if(count%pageSize != 0){
			pageCount ++;
		}
		
		//返回结果处理
		//*********************************************
		// 根据类型做相应的数据处理
		//*********************************************
		List<CustomerBrowseVM> vms = new ArrayList<CustomerBrowseVM>();
		
		for(CustomerBrowse browse : browses){
			switch (browse.getTargetType()) {
			case BROWSE_TYPE_HOTEL:
				Item item = itemService.selectByPrimaryKey(browse.getTargetId());
				Hotel hotel = hotelService.selectByPrimaryKey(item.getHotelId());
				//转存酒店基本数据
				HotelListInfoVM vm = new HotelListInfoVM();
				vm.setId(hotel.getId());
				vm.setName(hotel.getName());
				vm.setText(hotel.getText());
				vm.setLatitude(hotel.getLatitude());
				vm.setLongitude(hotel.getLongitude());
				
				vm.setTel(item.getTel());
				vm.setAddress(item.getPosition());
				vm.setScore(item.getScore());
				List<ItemDetail> details = itemDetailService.selectByItemId(item.getId());
				if(details != null && details.size() > 0){
					//仅获取第一张图片
					vm.setImgUrl(details.get(0).getImageUrl());
				}
				
				//查询所有酒店所有的项目
				List<Item> items = itemService.getItemByHotel(hotel.getId());
				
				if(hotel.getRegionId() != null){
					//设置位置
					Region area = baseDataService.getRegionById(hotel.getRegionId());
					String path = area.getPath();
					String[] arr = path.split("\\.");
					//Region city = baseDataService.getRegionById(new Integer(arr[1]));
					vm.setCity(arr[1]);
				}
				vm.setProjects(items);
				
				//空数据清理
				vm.clear();
				//设置数据对象
				CustomerBrowseVM browseVM = new CustomerBrowseVM();
				browseVM.setId(browse.getId());
				browseVM.setType(BROWSE_TYPE_HOTEL);
				browseVM.setHotel(vm);
				vms.add(browseVM);
				break;
			case BROWSE_TYPE_AD:
				Advertisement advertisement = adService.selectByPrimaryKey(browse.getTargetId());
				AdvertisementListInfoVM avm = new AdvertisementListInfoVM();
				avm.setAdvertisement(advertisement);
				
				AdDetail detail = adService.getFirstDetail(advertisement.getId());
				if(detail != null){
					avm.setImgUrl(detail.getImageUrl());
					avm.setText(detail.getText());
				}
				CustomerBrowseVM abrowseVM = new CustomerBrowseVM();
				abrowseVM.setId(browse.getId());
				abrowseVM.setType(BROWSE_TYPE_AD);
				abrowseVM.setAdvertisement(avm);
				vms.add(abrowseVM);
				break;
			case BROWSE_TYPE_BBS:
				BbsVM bvm = bbsService.loadBbsById(browse.getTargetId());
				CustomerBrowseVM bbrowseVM = new CustomerBrowseVM();
				bbrowseVM.setId(browse.getId());
				bbrowseVM.setType(BROWSE_TYPE_BBS);
				bbrowseVM.setBbs(bvm);
				vms.add(bbrowseVM);
				break;

			default:
				break;
			}
		}
		
		ListResult<CustomerBrowseVM> res = new ListResult<CustomerBrowseVM>();
		res.setIsSuccess(true);
		res.setTotal(pageCount);
		res.setMsg("");
		res.setRows(vms);
		return res;
	}
	
	/**
	 * 获取用户最近浏览
	 * @author LiuTaiXiong
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/deleteCustomerBrowse.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody Result<String> deleteCustomerBrowse(
			@RequestParam(value = "json", required = false) String json)
	{
		int browseId = 0;
		try{
			JSONObject jsonObject = JSONObject.fromObject(json);
			if(jsonObject.containsKey("browseId")){
				browseId = jsonObject.getInt("browseId");
			}
		}catch(Exception e){
			e.printStackTrace();
			return new Result<String>(null, false, "json解析异常");
		}
		if(browseId < 1){
			return new Result<String>(null, false, "请求无效");
		}
		
		//浏览记录数据获取
		int count = customerBrowseService.deleteById(browseId);
		if(count > 0){
			return new Result<String>(null, true, "删除成功");
		}
		return new Result<String>(null, false, "删除失败");
	}
	
	/**
	 * 获取用户收藏
	 * @author LiuTaiXiong
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/getCustomerCollection.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody ListResult<CustomerBrowseVM> getCustomerCollection(
			@RequestParam(value = "json", required = false) String json)
	{
		int customerId = 0;
		int pageNumber = 1;
		int pageSize = DEFAULT_PAGE_SIZE;
		try{
			JSONObject jsonObject = JSONObject.fromObject(json);
			if(jsonObject.containsKey("customerId")){
				customerId = jsonObject.getInt("customerId");
			}
			if(jsonObject.containsKey("pageNumber")){
				pageNumber = jsonObject.getInt("pageNumber");
			}
			if(jsonObject.containsKey("pageSize")){
				pageSize = jsonObject.getInt("pageSize");
			}
		}catch(Exception e){
			e.printStackTrace();
			return new ListResult<CustomerBrowseVM>(null, false, "json解析异常");
		}
		if(customerId < 1){
			return new ListResult<CustomerBrowseVM>(null, false, "请求无效");
		}
		if(pageNumber < 1){
			return new ListResult<CustomerBrowseVM>(null, false, "请求无效");
		}
		
		//获取数据
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageStart", 0);
		map.put("pageSize", pageSize * pageNumber);
		map.put("customerId", customerId);
		
		//浏览记录数据获取
		List<CustomerCollection> collections = customerCollectionService.getPageCollectionByCustomer(map);
		int count = customerCollectionService.countByCustomer(customerId); 
		
		int pageCount = count/pageSize;
		if(count%pageSize != 0){
			pageCount ++;
		}
		
		//返回结果处理
		
		//*********************************************
		// 根据类型做相应的数据处理
		//*********************************************
		List<CustomerBrowseVM> vms = new ArrayList<CustomerBrowseVM>();
		for(CustomerCollection collection : collections){
			switch (collection.getCollectionType()) {
			case BROWSE_TYPE_HOTEL:
				Item item = itemService.selectByPrimaryKey(collection.getTargetId());
				Hotel hotel = hotelService.selectByPrimaryKey(item.getHotelId());
				//转存酒店基本数据
				HotelListInfoVM vm = new HotelListInfoVM();
				vm.setId(hotel.getId());
				vm.setName(hotel.getName());
				vm.setText(hotel.getText());
				vm.setLatitude(hotel.getLatitude());
				vm.setLongitude(hotel.getLongitude());
				
				vm.setTel(item.getTel());
				vm.setAddress(item.getPosition());
				vm.setScore(item.getScore());
				List<ItemDetail> details = itemDetailService.selectByItemId(item.getId());
				if(details != null && details.size() > 0){
					//仅获取第一张图片
					vm.setImgUrl(details.get(0).getImageUrl());
				}
				
				//查询所有酒店所有的项目
				List<Item> items = itemService.getItemByHotel(hotel.getId());
				
				if(hotel.getRegionId() != null){
					//设置位置
					Region area = baseDataService.getRegionById(hotel.getRegionId());
					String path = area.getPath();
					String[] arr = path.split("\\.");
					//Region city = baseDataService.getRegionById(new Integer(arr[1]));
					vm.setCity(arr[1]);
				}
				vm.setProjects(items);
				
				//空数据清理
				vm.clear();
				//设置数据对象
				CustomerBrowseVM browseVM = new CustomerBrowseVM();
				browseVM.setId(collection.getId());
				browseVM.setType(BROWSE_TYPE_HOTEL);
				browseVM.setHotel(vm);
				vms.add(browseVM);
				break;
			case BROWSE_TYPE_AD:
				Advertisement advertisement = adService.selectByPrimaryKey(collection.getTargetId());
				AdvertisementListInfoVM avm = new AdvertisementListInfoVM();
				avm.setAdvertisement(advertisement);
				
				AdDetail detail = adService.getFirstDetail(advertisement.getId());
				if(detail != null){
					avm.setImgUrl(detail.getImageUrl());
					avm.setText(detail.getText());
				}
				CustomerBrowseVM abrowseVM = new CustomerBrowseVM();
				abrowseVM.setId(collection.getId());
				abrowseVM.setType(BROWSE_TYPE_AD);
				abrowseVM.setAdvertisement(avm);
				vms.add(abrowseVM);
				break;
			case BROWSE_TYPE_BBS:
				BbsVM bvm = bbsService.loadBbsById(collection.getTargetId());
				CustomerBrowseVM bbrowseVM = new CustomerBrowseVM();
				bbrowseVM.setId(collection.getId());
				bbrowseVM.setType(BROWSE_TYPE_BBS);
				bbrowseVM.setBbs(bvm);
				vms.add(bbrowseVM);
				break;

			default:
				break;
			}
		}
		
		ListResult<CustomerBrowseVM> res = new ListResult<CustomerBrowseVM>();
		res.setIsSuccess(true);
		res.setTotal(pageCount);
		res.setMsg("");
		res.setRows(vms);
		
		return res;
	}
	
	/**
	 * 获取用户最近浏览
	 * @author LiuTaiXiong
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/deleteCustomerCollection.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody Result<String> deleteCustomerCollection(
			@RequestParam(value = "json", required = false) String json)
	{
		int collectionId = 0;
		try{
			JSONObject jsonObject = JSONObject.fromObject(json);
			if(jsonObject.containsKey("collectionId")){
				collectionId = jsonObject.getInt("collectionId");
			}
		}catch(Exception e){
			e.printStackTrace();
			return new Result<String>(null, false, "json解析异常");
		}
		if(collectionId < 1){
			return new Result<String>(null, false, "请求无效");
		}
		
		//浏览记录数据获取
		int count = customerCollectionService.deleteById(collectionId);
		if(count > 0){
			return new Result<String>(null, true, "删除成功");
		}
		return new Result<String>(null, false, "删除失败");
	}
	
	/**
	 * 获取用户话题
	 * @author LiuTaiXiong
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/getCustomerTopic.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody ListResult<BbsVM> getCustomerTopic(
			@RequestParam(value = "json", required = false) String json)
	{
		int customerId = 0;
		int pageNumber = 1;
		int pageSize = DEFAULT_PAGE_SIZE;
		try{
			JSONObject jsonObject = JSONObject.fromObject(json);
			if(jsonObject.containsKey("customerId")){
				customerId = jsonObject.getInt("customerId");
			}
			if(jsonObject.containsKey("pageNumber")){
				pageNumber = jsonObject.getInt("pageNumber");
			}
			if(jsonObject.containsKey("pageSize")){
				pageSize = jsonObject.getInt("pageSize");
			}
		}catch(Exception e){
			e.printStackTrace();
			return new ListResult<BbsVM>(null, false, "json解析异常");
		}
		if(customerId < 1){
			return new ListResult<BbsVM>(null, false, "请求无效");
		}
		if(pageNumber < 1){
			return new ListResult<BbsVM>(null, false, "请求无效");
		}
		
		//获取数据
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageStart", 0);
		map.put("pageSize", pageSize * pageNumber);
		map.put("customerId", customerId);
		
		//浏览记录数据获取
		List<Bbs> bbs = bbsService.getPagePostByCustomer(map);
		int count = bbsService.countPostByCustomer(customerId);
		List<BbsVM> bvm = new ArrayList<BbsVM>();
		for(Bbs b : bbs){
			BbsVM vm = new BbsVM();
			vm.setText(b.getText());
			vm.setTitle(vm.getTitle());
			vm.setBbsAttachUrls(bbsService.selectBaByBbsId(b.getId()));
			bvm.add(vm);
		}
		
		int pageCount = count/pageSize;
		if(count%pageSize != 0){
			pageCount ++;
		}
		
		//返回对象处理
		ListResult<BbsVM> result = new ListResult<BbsVM>();
		result.setIsSuccess(true);
		result.setTotal(pageCount);
		result.setMsg("");
		result.setRows(bvm);
		return result;
	}
	
	/**
	 * 删除用户话题
	 * @author LiuTaiXiong
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/deleteCustomerTopic.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody ListResult<String> deleteCustomerTopic(
			@RequestParam(value = "json", required = false) String json)
	{
		int customerId = 0;
		int bbsId= 0;
		try{
			JSONObject jsonObject = JSONObject.fromObject(json);
			if(jsonObject.containsKey("customerId")){
				customerId = jsonObject.getInt("customerId");
			}
			if(jsonObject.containsKey("bbsId")){
				bbsId = jsonObject.getInt("bbsId");
			}
		}catch(Exception e){
			e.printStackTrace();
			return new ListResult<String>(null, false, "json解析异常");
		}
		if(customerId < 1 || bbsId < 1){
			return new ListResult<String>(null, false, "请求无效");
		}
		
		Bbs bbs = bbsService.selectByPrimaryKey(bbsId);
		if(bbs == null){
			return new ListResult<String>(null, false, "没有找到删除的话题");
		}
		
		if(bbs.getCustomerId() != customerId){
			return new ListResult<String>(null, false, "不是您的话题无法删除");
		}
		
		int count = bbsService.updatePostDeleteInfo(bbsId);
		if(count > 0){
			return new ListResult<String>(null, true, "");
		}else{
			return new ListResult<String>(null, false, "删除话题失败");
		}
	}
	
	/**
	 * 获取个人中心动态点赞数和评论数
	 * @author LiuTaiXiong
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/getCustomerDynamicCount.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody Result<CountVM> getCustomerDynamicCount(@RequestParam(value = "json", required = false) String json){
		int customerId = 0;
		try{
			JSONObject jsonObject = JSONObject.fromObject(json);
			if(jsonObject.containsKey("customerId")){
				customerId = jsonObject.getInt("customerId");
			}
		}catch(Exception e){
			e.printStackTrace();
			return new Result<CountVM>(null, false, "json解析异常");
		}
		if(customerId < 1){
			return new Result<CountVM>(null, false, "请求无效");
		}
		
		//int countDPF = bbsService.countDPraiseByCustomer(customerId);
		int countDPT = bbsService.countDPraiseToCustomer(customerId);
		//int countDCF = bbsService.countDCommentByCustomer(customerId);
		int countDCT = bbsService.countDCommentToCustomer(customerId);
		CountVM vm = new CountVM();
//		vm.setCountPraise(countDPF + countDPT);
//		vm.setCountComments(countDCF + countDCT);
		vm.setCountPraise(countDPT);
		vm.setCountComments(countDCT);
		
		return new Result<CountVM>(vm, true, "");
	}
	
	/**
	 * 获取个人中心动态点赞数和评论数
	 * @author LiuTaiXiong
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/getCustomerNewDynamicCount.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody Result<CountVM> getCustomerNewDynamicCount(@RequestParam(value = "json", required = false) String json){
		int customerId = 0;
		try{
			JSONObject jsonObject = JSONObject.fromObject(json);
			if(jsonObject.containsKey("customerId")){
				customerId = jsonObject.getInt("customerId");
			}
		}catch(Exception e){
			e.printStackTrace();
			return new Result<CountVM>(null, false, "json解析异常");
		}
		if(customerId < 1){
			return new Result<CountVM>(null, false, "请求无效");
		}
		
		//int countDPF = bbsService.countDPraiseByCustomer(customerId);
		int countDPT = bbsService.countDNewPraiseToCustomer(customerId);
		//int countDCF = bbsService.countDCommentByCustomer(customerId);
		int countDCT = bbsService.countDNewCommentToCustomer(customerId);
		CountVM vm = new CountVM();
//		vm.setCountPraise(countDPF + countDPT);
//		vm.setCountComments(countDCF + countDCT);
		vm.setCountPraise(countDPT);
		vm.setCountComments(countDCT);
		
		return new Result<CountVM>(vm, true, "");
	}
	
	/**
	 * 获取用户动态-点赞
	 * @author LiuTaiXiong
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/getCustomerDynamicPraise.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody ListResult<BbsDynamicVM> getCustomerDynamicPraise(
			@RequestParam(value = "json", required = false) String json)
	{
		int customerId = 0;
		int pageNumber = 1;
		int pageSize = DEFAULT_PAGE_SIZE;
		try{
			JSONObject jsonObject = JSONObject.fromObject(json);
			if(jsonObject.containsKey("customerId")){
				customerId = jsonObject.getInt("customerId");
			}
			if(jsonObject.containsKey("pageNumber")){
				pageNumber = jsonObject.getInt("pageNumber");
			}
			if(jsonObject.containsKey("pageSize")){
				pageSize = jsonObject.getInt("pageSize");
			}
		}catch(Exception e){
			e.printStackTrace();
			return new ListResult<BbsDynamicVM>(null, false, "json解析异常");
		}
		if(customerId < 1){
			return new ListResult<BbsDynamicVM>(null, false, "请求无效");
		}
		if(pageNumber < 1){
			return new ListResult<BbsDynamicVM>(null, false, "请求无效");
		}
		
		int total = pageSize * pageNumber;
		
		//获取数据
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageStart", 0);
		map.put("pageSize", total);
		map.put("customerId", customerId);
		
		//用户点赞数据获取
//		List<Bbs> bbs = bbsService.getPageDPByCustomer(map);
//		int countBy = bbsService.countDPraiseByCustomer(customerId);
		int countTo = bbsService.countDPraiseToCustomer(customerId);
//		
//		List<Bbs> bbsTo = new ArrayList<Bbs>();
//		if(bbs.size() < total && countTo > 0){
//			int lastSize = total - bbs.size();
//			map.put("pageSize", lastSize);
//			//用户被点赞数据获取
//			bbsTo = bbsService.getPageDPToCustomer(map);
//		}
		
		List<Bbs> bbs = bbsService.getPageDPToCustomer(map);
		
//		int count = countBy + countTo;
		int count =  countTo;
		int pageCount = count/pageSize;
		if(count%pageSize != 0){
			pageCount ++;
		}
		
		//返回对象处理
		
		//*********************************************
		// 根据类型做相应的数据处理
		//*********************************************
		List<BbsDynamicVM> list = new ArrayList<BbsDynamicVM>();
//		if(bbs.size() > 0){
//			for(Bbs b : bbs){
//				BbsDynamicVM bbsDynamicVM = new BbsDynamicVM();
//				bbsDynamicVM.setFrom(b);
//				Bbs to = bbsService.selectByPrimaryKey(b.getTargetId());
//				bbsDynamicVM.setTo(to);
//				bbsDynamicVM.setCustomer(customerService.selectByPrimaryKey(to.getCustomerId()));
//				list.add(bbsDynamicVM);
//			}
//		}
//		if(bbsTo.size() > 0){
//			for(Bbs b : bbsTo){
//				BbsDynamicVM bbsDynamicVM = new BbsDynamicVM();
//				bbsDynamicVM.setFrom(b);
//				Bbs to = bbsService.selectByPrimaryKey(b.getTargetId());
//				bbsDynamicVM.setTo(to);
//				bbsDynamicVM.setCustomer(customerService.selectByPrimaryKey(b.getCustomerId()));
//				list.add(bbsDynamicVM);
//			}
//		}
		
		if(bbs.size() > 0){
			for(Bbs b : bbs){
				BbsDynamicVM bbsDynamicVM = new BbsDynamicVM();
				bbsDynamicVM.setFrom(b);
				Bbs to = bbsService.selectByPrimaryKey(b.getTargetId());
				bbsDynamicVM.setTo(to);
				bbsDynamicVM.setCustomer(customerService.selectByPrimaryKey(b.getCustomerId()));
				list.add(bbsDynamicVM);
				if(b.getReadStatus() == 0){
					bbsService.updateReadStatusRead(b.getId());
				}
			}
		}
		
		ListResult<BbsDynamicVM> result = new ListResult<BbsDynamicVM>();
		result.setIsSuccess(true);
		result.setTotal(pageCount);
		result.setMsg("");
		result.setRows(list);
		return result;
	}
	
	/**
	 * 获取用户动态-评论
	 * @author LiuTaiXiong
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/getCustomerDynamicComments.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody ListResult<BbsCommentVM> getCustomerDynamicComments(
			@RequestParam(value = "json", required = false) String json)
	{
		int customerId = 0;
		int pageNumber = 1;
		int pageSize = DEFAULT_PAGE_SIZE;
		try{
			JSONObject jsonObject = JSONObject.fromObject(json);
			if(jsonObject.containsKey("customerId")){
				customerId = jsonObject.getInt("customerId");
			}
			if(jsonObject.containsKey("pageNumber")){
				pageNumber = jsonObject.getInt("pageNumber");
			}
			if(jsonObject.containsKey("pageSize")){
				pageSize = jsonObject.getInt("pageSize");
			}
		}catch(Exception e){
			e.printStackTrace();
			return new ListResult<BbsCommentVM>(null, false, "json解析异常");
		}
		if(customerId < 1){
			return new ListResult<BbsCommentVM>(null, false, "请求无效");
		}
		if(pageNumber < 1){
			return new ListResult<BbsCommentVM>(null, false, "请求无效");
		}
		
		int total = pageSize * pageNumber;
		
		//获取数据
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageStart", 0);
		map.put("pageSize", total);
		map.put("customerId", customerId);
		
		//用户点赞数据获取
		//List<Bbs> bbs = bbsService.getPageDCByCustomer(map);
		//int countBy = bbsService.countDCommentByCustomer(customerId);
		int countTo = bbsService.countDCommentToCustomer(customerId);
//		
//		List<Bbs> bbsTo = new ArrayList<Bbs>();
//		if(bbs.size() < total && countTo > 0){
//			int lastSize = total - bbs.size();
//			map.put("pageSize", lastSize);
//			//用户被点赞数据获取
//			bbsTo = bbsService.getPageDCToCustomer(map);
//		}
		
//		int count = countBy + countTo;
		List<Bbs> bbs = bbsService.getPageDCToCustomer(map);
		int count = countTo;
		int pageCount = count/pageSize;
		if(count%pageSize != 0){
			pageCount ++;
		}
		
		//返回对象处理
		
		//*********************************************
		// 根据类型做相应的数据处理
		//*********************************************
//		List<BbsDynamicVM> list = new ArrayList<BbsDynamicVM>();
//		if(bbs.size() > 0){
//			for(Bbs b : bbs){
//				BbsDynamicVM bbsDynamicVM = new BbsDynamicVM();
//				bbsDynamicVM.setFrom(b);
//				Bbs to = bbsService.selectByPrimaryKey(b.getTargetId());
//				bbsDynamicVM.setTo(to);
//				bbsDynamicVM.setCustomer(customerService.selectByPrimaryKey(to.getCustomerId()));
//				list.add(bbsDynamicVM);
//			}
//		}
//		if(bbsTo.size() > 0){
//			for(Bbs b : bbsTo){
//				BbsDynamicVM bbsDynamicVM = new BbsDynamicVM();
//				bbsDynamicVM.setFrom(b);
//				Bbs to = bbsService.selectByPrimaryKey(b.getTargetId());
//				bbsDynamicVM.setTo(to);
//				bbsDynamicVM.setCustomer(customerService.selectByPrimaryKey(b.getCustomerId()));
//				list.add(bbsDynamicVM);
//			}
//		}
		
		List<BbsCommentVM> comments = new ArrayList<BbsCommentVM>();
		if(bbs.size() > 0){
			BbsContainer container = new BbsContainer();
			for(Bbs b : bbs){
				if(b.getPath() != null && !b.equals("")){
					String idstr = b.getPath();
					if(b.getPath().contains(".")){
						idstr = b.getPath().split("\\.")[0];
					}
					
					Integer id = new Integer(idstr);
					BbsDynamicVM bbsDynamicVM = new BbsDynamicVM();
					bbsDynamicVM.setFrom(b);
					//Bbs to = bbsService.selectByPrimaryKey(b.getTargetId());
					//bbsDynamicVM.setTo(to);
					bbsDynamicVM.setCustomer(customerService.selectByPrimaryKey(b.getCustomerId()));
					//list.add(bbsDynamicVM);
					container.add(b.getTargetType()+"-"+id, bbsDynamicVM);
				}
				if(b.getReadStatus() == 0){
					bbsService.updateReadStatusRead(b.getId());
				}
			}
			if(container.size() > 0){
				for(String sid : container.getIds()){
					BbsCommentVM vm = new BbsCommentVM();
					
					String[] tempArr = sid.split("-");
					int type = new Integer(tempArr[0]);
					int id = new Integer(tempArr[1]);
					
					vm.setTargetType(type);
					//论坛
					if(type == 0){
						vm.setPost(bbsService.loadBbsById(id));
						vm.setDynamics(container.getValues(sid));
						comments.add(vm);
					}
					//酒店
					if(type == 1){
						BbsVM bvm = new BbsVM();
						Item item = itemService.selectByPrimaryKey(id);
						Hotel hotel = hotelService.selectByPrimaryKey(item.getHotelId());
						bvm.setText(item.getText());
						bvm.setId(item.getId());
						
						Customer customer = new Customer();
						customer.setPhotoUrl(hotel.getLogo());
						customer.setName(item.getName());
						
						bvm.setCustomer(customer);
						
						vm.setPost(bvm);
						vm.setDynamics(container.getValues(sid));
						comments.add(vm);
					}
					//广告
					if(type == 2){
						BbsVM bvm = new BbsVM();
						AdvertisementVM avm = advertisementService.loadAdById(id);
						//bvm.setText(avm.getAdDetailList()..getText());
						bvm.setId(avm.getId());
						
						Customer customer = new Customer();
						customer.setName(avm.getName());
						
						if(avm.getAdDetailList() != null && avm.getAdDetailList().size() > 0){
							bvm.setText(avm.getAdDetailList().get(0).getText());
							customer.setPhotoUrl(avm.getAdDetailList().get(0).getImageUrl());
						}
						
						bvm.setCustomer(customer);
						
						vm.setPost(bvm);
						vm.setDynamics(container.getValues(sid));
						comments.add(vm);
					}
					
				}
			}
		}
		
		ListResult<BbsCommentVM> result = new ListResult<BbsCommentVM>();
		result.setIsSuccess(true);
		result.setTotal(pageCount);
		result.setMsg("");
		result.setRows(comments);
		return result;
	}
	
	/**
	 * 保存用户收藏/关注（包括酒店(服务)、广告(专题)、论坛等）
	 * @author LiuTaiXiong
	 * @param json
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
		
		//查询是否存在相同的记录
		int collectionTimes = customerCollectionService.countByCustomerCollection(collection);
		//已收藏
		if(collectionTimes > 0){
			return new Result<String>("", false, "您已收藏");
		}
		
		int count = customerCollectionService.insert(collection);
		if(count > 0){
			return new Result<String>("", true, "收藏成功");
		}
		return new Result<String>("", false, "收藏失败");
	}
	
	/**
	 * 保存用户点赞（包括酒店(服务)、广告(专题)、论坛等）
	 * @author LiuTaiXiong
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/savePraiseHistory.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody Result<String> saveAgreeHistory(
			@RequestParam(value = "json", required = false) String json)
	{
		int praiseType = -1;
		int customerId = 0;
		int targetId = 0;
		try{
			JSONObject jsonObject = JSONObject.fromObject(json);
			if(jsonObject.containsKey("praiseType")){
				praiseType = jsonObject.getInt("praiseType");
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
		if(praiseType < 0 || customerId < 1 || targetId < 1){
			return new Result<String>("", false, "请求无效");
		}
		
		Bbs bbs = new Bbs();
		bbs.setCustomerId(customerId);
		bbs.setBbsType(3);
		bbs.setCategoryId(0);
		bbs.setPostType(2);
		bbs.setTargetType(praiseType);
		bbs.setTargetId(targetId);
		bbs.setParentId(0);
		bbs.setLevel(0);
		bbs.setCreateTime(new Date());
	    bbs.setTimeStamp(new Date());
		
		//相同数据检查
		int bbsCount = bbsService.countByBbs(bbs);
		//已点赞
		if(bbsCount > 0){
			return new Result<String>("", false, "您已点赞");
		}
		
		int count = bbsService.insert(bbs);
		if(count > 0){
			if(praiseType == 0){
				bbsService.updateAgreeCount(targetId);
			}
			return new Result<String>("", true, "");
		}
		return new Result<String>("", false, "点赞失败");
	}
	
	/**
	 * 保存用户点赞（包括酒店(服务)、广告(专题)、论坛等）
	 * @author LiuTaiXiong
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/saveBrowseHistory.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody Result<String> saveBrowseHistory(
			@RequestParam(value = "json", required = false) String json)
	{
		int browseType = -1;
		int customerId = 0;
		int targetId = 0;
		try{
			JSONObject jsonObject = JSONObject.fromObject(json);
			if(jsonObject.containsKey("browseType")){
				browseType = jsonObject.getInt("browseType");
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
		if(browseType < 0 || customerId < 1 || targetId < 1){
			return new Result<String>("", false, "请求无效");
		}
		
		CustomerBrowse browse = new CustomerBrowse();
		browse.setCustomerId(customerId);
		browse.setTargetType(browseType);
		browse.setTargetId(targetId);
		browse.setVisitTime(new Date());
		
		int tcount = customerBrowseService.countByBrowse(browse);
		if(tcount > 0){
			return new Result<String>("", true, "");
		}
		
		int bcount = customerBrowseService.insert(browse);
		
		if(bcount > 0){
			return new Result<String>("", true, "");
		}
		return new Result<String>("", false, "浏览失败");
	}
	
	@RequestMapping(value = "/saveShare.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody Result<String> saveShare(
			@RequestParam(value = "json", required = false) String json)
	{
		int targetId = 0;
		try{
			JSONObject jsonObject = JSONObject.fromObject(json);
			
			if(jsonObject.containsKey("targetId")){
				targetId = jsonObject.getInt("targetId");
			}
		}catch(Exception e){
			e.printStackTrace();
			return new Result<String>("", false, "json解析异常");
		}
		if(targetId < 1){
			return new Result<String>("", false, "请求无效");
		}
		bbsService.updateShareCount(targetId);
		return new Result<String>("", true, "");
	}
	
	/**
	 * 用户修改密码
	 * @author LiuTaiXiong
	 * @param json
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
		if(newTime - oldTime > DEFAULT_VARIFY_TIME*1000){
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
	 * 上传头像
	 * @author jun
	 * @param customerImg
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "uploadHeadImg.do", produces = "application/json;charset=UTF-8")  
    public @ResponseBody String upload(
    		@RequestParam MultipartFile customerImg,
    		HttpServletRequest request) { 
		Result<Customer> result = null;
        try { 
        	String path = request.getSession().getServletContext().getRealPath("/")+"app/head";
        	String fileName = customerImg.getOriginalFilename();
        	//String rootPath = PathConfig.getNewPathConfig();
        	//String path = rootPath+"app/head";
        	File filedir = new File(path);
        	if(!filedir.exists()){  
        		filedir.mkdirs();  
            }  
        	File uploadFile = new File(path,fileName);
        	customerImg.transferTo(uploadFile); //保存
        	result = new Result<Customer>(null, true, "上传成功");
        	return result.toJson();
        }catch(Exception e){
        	result = new Result<Customer>(null, false, "上传失败");
        	return result.toJson();
        }
	}
	
}
