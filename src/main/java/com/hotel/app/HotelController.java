package com.hotel.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.common.ListResult;
import com.hotel.common.Result;
import com.hotel.model.CustomerBrowse;
import com.hotel.model.Hotel;
import com.hotel.model.Item;
import com.hotel.model.ItemDetail;
import com.hotel.model.ItemTagAssociation;
import com.hotel.model.Region;
import com.hotel.model.SearchText;
import com.hotel.modelVM.HotelInfoVM;
import com.hotel.modelVM.HotelListInfoVM;
import com.hotel.modelVM.HotelParam;
import com.hotel.modelVM.ImageVM;
import com.hotel.service.BaseDataService;
import com.hotel.service.CustomerBrowseService;
import com.hotel.service.CustomerCollectionService;
import com.hotel.service.HotelService;
import com.hotel.service.ItemDetailService;
import com.hotel.service.ItemService;
import com.hotel.service.ItemTagAssociationService;
import com.hotel.service.ItemTagService;

/**
 * 酒店数据访问接口
 * @author charo
 *
 */
@Controller
@RequestMapping("/app/hotel")
public class HotelController {
	private final static int DEFAULT_PAGE_NUM = 1;
	private final static int DEFAULT_PAGE_SIZE = 10;
	private final static int TAG_TYPE_HOTEL = 1;
	private final static int DEFAULT_RECOMMAND_NUM = 5;
	
	@Resource(name="hotelService")
	private HotelService hotelService;
	@Resource(name="itemService")
	private ItemService itemService;
	@Resource(name="itemTagService")
	private ItemTagService itemTagService;
	@Resource(name="itemDetailService")
	private ItemDetailService itemDetailService;
	@Resource(name="itemTagAssociationService")
	private ItemTagAssociationService itemTagAssociationService;
	@Resource(name="customerBrowseService")
	private CustomerBrowseService customerBrowseService;
	@Resource(name="customerCollectionService")
	private CustomerCollectionService customerCollectionService;
	@Resource(name="baseDataService")
	private BaseDataService baseDataService;
	
	/**
	 * APP获取酒店列表接口
	 * @author LiuTaiXiong
	 * @param json
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/hotelListByItem.do", produces = "application/json;charset=UTF-8")
	public ListResult<HotelListInfoVM> hotelListByItem(@RequestParam(value = "json", required = false)String json, HttpServletRequest request, HttpServletResponse response) {
		//初始化分页数据
		int pageNumber = DEFAULT_PAGE_NUM;
		int itemTagId = 0;
		
		
		JSONObject jsonObject = JSONObject.fromObject(json);
		if(jsonObject.containsKey("pageNumber")){
			pageNumber = new Integer(jsonObject.getString("pageNumber"));
		}
		if(jsonObject.containsKey("itemTagId")){
			itemTagId = new Integer(jsonObject.getString("itemTagId"));
		}
		
		if(itemTagId < 1){
			ListResult<HotelListInfoVM> result = new ListResult<HotelListInfoVM>();
			result.setIsSuccess(true);
			result.setTotal(0);
			result.setMsg("请求参数无效");
			result.setRows(new ArrayList<HotelListInfoVM>());
			return result;
		}
		
		//获取数据
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageStart", (pageNumber - 1)*DEFAULT_PAGE_SIZE);
		map.put("pageSize", DEFAULT_PAGE_SIZE);
		map.put("itemTagId", itemTagId);
		
		//获取指定酒店对象集
		List<Hotel> lh = hotelService.getPageHotelByItemTag(map);
		//获取数据的总量
		int count = hotelService.getPageHotelCountByItemTag(itemTagId);
		//返回结果对象集
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
					//Region province = baseDataService.getRegionById(new Integer(arr[0]));
					Region city = baseDataService.getRegionById(new Integer(arr[1]));
					vm.setCity(city.getName());
					//String address = province.getName()+city.getName()+area.getName();
					//检查是否有位置描述
					//if(vm.getAddress() != null){
					//	vm.setAddress(address+vm.getAddress());
					//}else{
					//	vm.setAddress(address);
					//}
				}
				vm.setProjects(notSelfItems);
				
				//空数据清理
				vm.clear();
				//添加到数据集
				list.add(vm);
			}
		}
		
		int pageCount = count/DEFAULT_PAGE_SIZE;
		if(count % DEFAULT_PAGE_SIZE != 0){
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
	 * APP获取酒店列表接口
	 * @author LiuTaiXiong
	 * @param json
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/hotelList.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ListResult<HotelListInfoVM> hotelList(@RequestParam(value = "json", required = false)String json, HttpServletRequest request, HttpServletResponse response) {
		//初始化分页数据
		int pageNumber = DEFAULT_PAGE_NUM;
		
		JSONObject jsonObject = JSONObject.fromObject(json);
		if(jsonObject.containsKey("pageNumber")){
			pageNumber = new Integer(jsonObject.getString("pageNumber"));
		}
		
		//获取数据
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageStart", (pageNumber - 1)*DEFAULT_PAGE_SIZE);
		map.put("pageSize", DEFAULT_PAGE_SIZE);
		//获取指定酒店对象集
		List<Hotel> lh = hotelService.getPageHotel(map);
		//获取数据的总量
		int count = hotelService.getPageHotelCount();
		//返回结果对象集
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
						int countBrowse = customerBrowseService.countByItemId(temp.getId());
						//查询收藏次数
						int countCollectiono = customerCollectionService.countByItemId(temp.getId());
						vm.setCountBrowse(countBrowse);
						vm.setCountCollection(countCollectiono);
					}
				}
				if(hotel.getRegionId() != null){
					//设置位置
					Region area = baseDataService.getRegionById(hotel.getRegionId());
					String path = area.getPath();
					String[] arr = path.split("\\.");
					Region province = baseDataService.getRegionById(new Integer(arr[0]));
					Region city = baseDataService.getRegionById(new Integer(arr[1]));
					
					String address = province.getName()+city.getName()+area.getName();
					//检查是否有位置描述
					if(vm.getAddress() != null){
						vm.setAddress(address+vm.getAddress());
					}else{
						vm.setAddress(address);
					}
				}
				
				vm.clear();
				//添加到数据集
				list.add(vm);
			}
		}
		
		//返回对象处理
		ListResult<HotelListInfoVM> result = new ListResult<HotelListInfoVM>();
		result.setIsSuccess(true);
		result.setTotal(count);
		result.setMsg("");
		result.setRows(list);
		
		return result;
	}
	
	/**
	 * APP获取酒店详情数据
	 * @author LiuTaiXiong
	 * @param json
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/hotelInfo.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ListResult<HotelInfoVM> getHotelInfo(String json, HttpServletRequest request, HttpServletResponse response) {
		int hotelId = 0;
		int customerId = 0;
		//参数过滤处理
		if(json == null || !json.equals("")){
			String parameter = request.getParameter("requestBody");
			JSONObject object = JSONObject.fromObject(parameter);
			if(object.containsKey("hotelId")){
				hotelId = new Integer(object.getString("hotelId"));
			}
			if(object.containsKey("customerId")){
				customerId = new Integer(object.getString("customerId"));
			}
		}
		
		//数据返回对象
		ListResult<HotelInfoVM> result = new ListResult<HotelInfoVM>();
		
		//数据存储器
		List<HotelInfoVM> list = new ArrayList<HotelInfoVM>();
		//请求参数无效
		if(hotelId < 1){
			result.setIsSuccess(false);
			result.setTotal(0);
			result.setMsg("请求参数无效");
			result.setRows(list);
			return result;
		}
		
		Hotel hotel = hotelService.selectByPrimaryKey(hotelId);
		
		//没有找到请求的数据
		if(hotel == null){
			result.setIsSuccess(false);
			result.setTotal(0);
			result.setMsg("没有找到请求的数据");
			result.setRows(list);
			return result;
		}
		//转存返回对象
		HotelInfoVM vm = new HotelInfoVM();
		vm.setId(hotel.getId());
		vm.setName(hotel.getName());
		vm.setText(hotel.getText());
		vm.setLatitude(hotel.getLatitude());
		vm.setLongitude(hotel.getLongitude());
		
		//查询所有酒店类型的项目
		List<ItemTagAssociation> associations =  itemTagAssociationService.getTagTypeItem(TAG_TYPE_HOTEL);
		//查询所有酒店所有的项目
		List<Item> items = itemService.getItemByHotel(hotel.getId());
		
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
				//设置评分
				vm.setScore(temp.getScore());
				//设置位置描述
				vm.setAddress(temp.getPosition());
				//查找该项目的详细信息
				List<ItemDetail> details = itemDetailService.selectByItemId(temp.getId());
				//图片对象转存
				List<ImageVM> images = new ArrayList<ImageVM>();
				if(details != null && details.size() > 0){
					//转存所有的项目详情
					for(ItemDetail detail : details){
						ImageVM imageVM = new ImageVM();
						imageVM.setImgUrl(detail.getImageUrl());
						imageVM.setNote(detail.getNote());
						images.add(imageVM);
					}
				}
				//设置项目的图文信息
				vm.setImgUrl(images);
				
				//设置数量统计
				//查询浏览次数
				int countBrowse = customerBrowseService.countByItemId(temp.getId());
				//查询收藏次数
				int countCollectiono = customerCollectionService.countByItemId(temp.getId());
				vm.setCountBrowse(countBrowse);
				vm.setCountCollection(countCollectiono);
				
				//添加一次浏览记录
				CustomerBrowse record = new CustomerBrowse();
				record.setCustomerId(customerId);
				record.setId(0);
				record.setTargetId(temp.getId());
				record.setTargetType(1);
				record.setVisitTime(new Date());
				customerBrowseService.insert(record);
			}
		}
		
		//设置位置
		Region area = baseDataService.getRegionById(hotel.getRegionId());
		String path = area.getPath();
		String[] arr = path.split("\\.");
		Region province = baseDataService.getRegionById(new Integer(arr[0]));
		Region city = baseDataService.getRegionById(new Integer(arr[1]));
		
		String address = province.getName()+city.getName()+area.getName();
		//检查是否有位置描述
		if(vm.getAddress() != null){
			vm.setAddress(address+vm.getAddress());
		}else{
			vm.setAddress(address);
		}
		list.add(vm);
		
		//处理成功的结果返回
		result.setIsSuccess(true);
		result.setTotal(1);
		result.setMsg("");
		result.setRows(list);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/demo.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ListResult<HotelListInfoVM> demo(String json, HttpServletRequest request, HttpServletResponse response) {
		return null;
	}
	/**
	 * 获取推荐的酒店列表(搜索)
	 * @param request
	 * @return
	 * @author hzf
	 */
	@RequestMapping(value = "getRecommandHotels.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody  String getRecommandHotels(
			HttpServletRequest request)
	{
		List<HotelParam> temp = hotelService.getRecommandHotelList(DEFAULT_RECOMMAND_NUM);
		if(temp ==null){
			return new ListResult<HotelParam>(null,false,"获取推荐酒店失败").toJson();
		}
		return new ListResult<HotelParam>(temp,true,"获取推荐酒店成功").toJson();
	}
}
