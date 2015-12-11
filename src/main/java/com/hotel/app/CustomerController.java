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
import com.hotel.model.ItemTagAssociation;
import com.hotel.model.Region;
import com.hotel.model.User;
import com.hotel.model.Varifycode;
import com.hotel.modelVM.AdvertisementListInfoVM;
import com.hotel.modelVM.BbsVM;
import com.hotel.modelVM.CustomerBrowseVM;
import com.hotel.modelVM.CustomerVM;
import com.hotel.modelVM.HotelListInfoVM;
import com.hotel.modelVM.RegisterData;
import com.hotel.service.AdService;
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
	private final static int TAG_TYPE_HOTEL = 1;
	
	//酒店收藏类型
	private final static int BROWSE_TYPE_HOTEL = 1;
	//广告收藏类型
	private final static int BROWSE_TYPE_AD = 2;
	//论坛收藏类型
	private final static int BROWSE_TYPE_BBS = 3;
	
	private final static int DEFAULT_VARIFY_TIME = 2*60;//秒
	
	@Autowired CustomerService customerService;
	@Autowired AdService adService;
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
			new Result<CustomerVM>(null, false, "没有找到对应的用户信息");
		}
		
		CustomerVM vm = new CustomerVM();
		vm.setCustomer(customer);
		vm.setCountAlways(roomCheckService.countHotelByCustomer(customerId));
		vm.setCountBrowse(customerBrowseService.countByCustomer(customerId));
		vm.setCountCollection(customerCollectionService.countByCustomer(customerId));
		vm.setCountTopic(bbsService.countPostByCustomer(customerId));
		vm.setCountDynamic(bbsService.countDynamicByCustomer(customerId));
		
		
		return new Result<CustomerVM>(vm, true, "");
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
			//逐一转存
			for(Hotel hotel : lh){
				//转存酒店基本数据
				HotelListInfoVM vm = new HotelListInfoVM();
				vm.setId(hotel.getId());
				vm.setName(hotel.getName());
				vm.setText(hotel.getText());
				vm.setLatitude(hotel.getLatitude());
				vm.setLongitude(hotel.getLongitude());
				
				//查询所有酒店类型的项目
				List<ItemTagAssociation> associations =  itemTagAssociationService.getTagTypeItem(TAG_TYPE_HOTEL);
				//查询所有酒店所有的项目
				List<Item> items = itemService.getItemByHotel(hotel.getId());
				
				//酒店非自身项目缓存
				List<Item> notSelfItems = new ArrayList<Item>();
				
				//如果单方没有数据则没有交集
				if(associations != null && items != null && items.size() > 0 && associations.size() > 0){
					Item temp = null;
					boolean isFind = false;
					//找出属于酒店自身的项目（求交集）
					for(ItemTagAssociation association : associations){
						for(Item item : items){
							if(association.getItemId() == item.getId()){
								temp = item;
								isFind = true;
								break;
							}
						}
						if(isFind){
							break;
						}
					}
					//判断是否找到该酒店的项目
					if(temp != null){
						//设置联系方式
						vm.setTel(temp.getTel());
						vm.setAddress(temp.getPosition());
						vm.setScore(temp.getScore());
						//查找该项目的详细信息
						List<ItemDetail> details = itemDetailService.selectByItemId(temp.getId());
						if(details != null && details.size() > 0){
							//仅获取第一张图片
							vm.setImgUrl(details.get(0).getImageUrl());
						}
						//设置数量统计
						//查询浏览次数
						//int countBrowse = customerBrowseService.countByItemId(temp.getId());
						//查询收藏次数
						//int countCollectiono = customerCollectionService.countByItemId(temp.getId());
						//vm.setCountBrowse(countBrowse);
						//vm.setCountCollection(countCollectiono);
						
						//设置非自身的项目数据
						for(Item tempItem : items){
							if(!tempItem.getId().equals(temp.getId())){
								notSelfItems.add(tempItem);
							}
						}
					}else{
						notSelfItems = items;
					}
				}
				if(hotel.getRegionId() != null){
					//设置位置
					Region area = baseDataService.getRegionById(hotel.getRegionId());
					String path = area.getPath();
					String[] arr = path.split("\\.");
					Region city = baseDataService.getRegionById(new Integer(arr[1]));
					vm.setCity(city.getName());
				}
				vm.setProjects(notSelfItems);
				
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
				Hotel hotel = hotelService.selectByPrimaryKey(browse.getTargetId());
				//转存酒店基本数据
				HotelListInfoVM vm = new HotelListInfoVM();
				vm.setId(hotel.getId());
				vm.setName(hotel.getName());
				vm.setText(hotel.getText());
				vm.setLatitude(hotel.getLatitude());
				vm.setLongitude(hotel.getLongitude());
				
				//查询所有酒店类型的项目
				List<ItemTagAssociation> associations =  itemTagAssociationService.getTagTypeItem(TAG_TYPE_HOTEL);
				//查询所有酒店所有的项目
				List<Item> items = itemService.getItemByHotel(hotel.getId());
				
				//酒店非自身项目缓存
				List<Item> notSelfItems = new ArrayList<Item>();
				
				//如果单方没有数据则没有交集
				if(associations != null && items != null && items.size() > 0 && associations.size() > 0){
					Item temp = null;
					boolean isFind = false;
					//找出属于酒店自身的项目（求交集）
					for(ItemTagAssociation association : associations){
						for(Item item : items){
							if(association.getItemId() == item.getId()){
								temp = item;
								isFind = true;
								break;
							}
						}
						if(isFind){
							break;
						}
					}
					//判断是否找到该酒店的项目
					if(temp != null){
						//设置联系方式
						vm.setTel(temp.getTel());
						vm.setAddress(temp.getPosition());
						vm.setScore(temp.getScore());
						//查找该项目的详细信息
						List<ItemDetail> details = itemDetailService.selectByItemId(temp.getId());
						if(details != null && details.size() > 0){
							//仅获取第一张图片
							vm.setImgUrl(details.get(0).getImageUrl());
						}
												
						//设置非自身的项目数据
						for(Item tempItem : items){
							if(!tempItem.getId().equals(temp.getId())){
								notSelfItems.add(tempItem);
							}
						}
					}else{
						notSelfItems = items;
					}
				}
				if(hotel.getRegionId() != null){
					//设置位置
					Region area = baseDataService.getRegionById(hotel.getRegionId());
					String path = area.getPath();
					String[] arr = path.split("\\.");
					Region city = baseDataService.getRegionById(new Integer(arr[1]));
					vm.setCity(city.getName());
				}
				vm.setProjects(notSelfItems);
				
				//空数据清理
				vm.clear();
				//设置数据对象
				CustomerBrowseVM browseVM = new CustomerBrowseVM();
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
				abrowseVM.setType(BROWSE_TYPE_AD);
				abrowseVM.setAdvertisement(avm);
				vms.add(abrowseVM);
				break;
			case BROWSE_TYPE_BBS:
				BbsVM bvm = bbsService.loadBbsById(browse.getTargetId());
				CustomerBrowseVM bbrowseVM = new CustomerBrowseVM();
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
				Hotel hotel = hotelService.selectByPrimaryKey(collection.getTargetId());
				//转存酒店基本数据
				HotelListInfoVM vm = new HotelListInfoVM();
				vm.setId(hotel.getId());
				vm.setName(hotel.getName());
				vm.setText(hotel.getText());
				vm.setLatitude(hotel.getLatitude());
				vm.setLongitude(hotel.getLongitude());
				
				//查询所有酒店类型的项目
				List<ItemTagAssociation> associations =  itemTagAssociationService.getTagTypeItem(TAG_TYPE_HOTEL);
				//查询所有酒店所有的项目
				List<Item> items = itemService.getItemByHotel(hotel.getId());
				
				//酒店非自身项目缓存
				List<Item> notSelfItems = new ArrayList<Item>();
				
				//如果单方没有数据则没有交集
				if(associations != null && items != null && items.size() > 0 && associations.size() > 0){
					Item temp = null;
					boolean isFind = false;
					//找出属于酒店自身的项目（求交集）
					for(ItemTagAssociation association : associations){
						for(Item item : items){
							if(association.getItemId() == item.getId()){
								temp = item;
								isFind = true;
								break;
							}
						}
						if(isFind){
							break;
						}
					}
					//判断是否找到该酒店的项目
					if(temp != null){
						//设置联系方式
						vm.setTel(temp.getTel());
						vm.setAddress(temp.getPosition());
						vm.setScore(temp.getScore());
						//查找该项目的详细信息
						List<ItemDetail> details = itemDetailService.selectByItemId(temp.getId());
						if(details != null && details.size() > 0){
							//仅获取第一张图片
							vm.setImgUrl(details.get(0).getImageUrl());
						}
												
						//设置非自身的项目数据
						for(Item tempItem : items){
							if(!tempItem.getId().equals(temp.getId())){
								notSelfItems.add(tempItem);
							}
						}
					}else{
						notSelfItems = items;
					}
				}
				if(hotel.getRegionId() != null){
					//设置位置
					Region area = baseDataService.getRegionById(hotel.getRegionId());
					String path = area.getPath();
					String[] arr = path.split("\\.");
					Region city = baseDataService.getRegionById(new Integer(arr[1]));
					vm.setCity(city.getName());
				}
				vm.setProjects(notSelfItems);
				
				//空数据清理
				vm.clear();
				//设置数据对象
				CustomerBrowseVM browseVM = new CustomerBrowseVM();
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
				abrowseVM.setType(BROWSE_TYPE_AD);
				abrowseVM.setAdvertisement(avm);
				vms.add(abrowseVM);
				break;
			case BROWSE_TYPE_BBS:
				BbsVM bvm = bbsService.loadBbsById(collection.getTargetId());
				CustomerBrowseVM bbrowseVM = new CustomerBrowseVM();
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
	 * 获取用户话题
	 * @author LiuTaiXiong
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/getCustomerTopic.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody ListResult<Bbs> getCustomerTopic(
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
			return new ListResult<Bbs>(null, false, "json解析异常");
		}
		if(customerId < 1){
			return new ListResult<Bbs>(null, false, "请求无效");
		}
		if(pageNumber < 1){
			return new ListResult<Bbs>(null, false, "请求无效");
		}
		
		//获取数据
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageStart", 0);
		map.put("pageSize", pageSize * pageNumber);
		map.put("customerId", customerId);
		
		//浏览记录数据获取
		List<Bbs> bbs = bbsService.getPagePostByCustomer(map);
		int count = bbsService.countPostByCustomer(customerId);
		
		int pageCount = count/pageSize;
		if(count%pageSize != 0){
			pageCount ++;
		}
		
		//返回对象处理
		ListResult<Bbs> result = new ListResult<Bbs>();
		result.setIsSuccess(true);
		result.setTotal(pageCount);
		result.setMsg("");
		result.setRows(bbs);
		return result;
	}
	
	/**
	 * 获取用户动态
	 * @author LiuTaiXiong
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/getCustomerDynamic.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody ListResult<Bbs> getCustomerDynamic(
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
			return new ListResult<Bbs>(null, false, "json解析异常");
		}
		if(customerId < 1){
			return new ListResult<Bbs>(null, false, "请求无效");
		}
		if(pageNumber < 1){
			return new ListResult<Bbs>(null, false, "请求无效");
		}
		
		//获取数据
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageStart", 0);
		map.put("pageSize", pageSize * pageNumber);
		map.put("customerId", customerId);
		
		//浏览记录数据获取
		List<Bbs> bbs = bbsService.getPageDynamicByCustomer(map);
		int count = bbsService.countDynamicByCustomer(customerId);
		
		int pageCount = count/pageSize;
		if(count%pageSize != 0){
			pageCount ++;
		}
		
		//返回对象处理
		
		//*********************************************
		// 根据类型做相应的数据处理
		//*********************************************
		
		return null;
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
			new Result<String>("", false, "您已点赞");
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
        	//String path = request.getSession().getServletContext().getRealPath("Photo"); 
        	String path = request.getSession().getServletContext().getRealPath("/")+"app/head";
//        	String path = getClass().getResource("/").getFile().toString();
//			path = path.substring(0, (path.length() - 16))+"washPhoto";
        	String fileName = customerImg.getOriginalFilename();
        	
        	File uploadFile = new File(path,fileName);
        	if(!uploadFile.exists()){  
        		uploadFile.mkdirs();  
            }  
        	customerImg.transferTo(uploadFile); //保存
        	result = new Result<Customer>(null, true, "上传成功");
        	return result.toJson();
        }catch(Exception e){
        	result = new Result<Customer>(null, false, "上传失败");
        	return result.toJson();
        }
	}
	
}
