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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.common.ListResult;
import com.hotel.common.Result;
import com.hotel.model.Bbs;
import com.hotel.model.Comment;
import com.hotel.model.CustomerBrowse;
import com.hotel.model.Hotel;
import com.hotel.model.Item;
import com.hotel.model.ItemDetail;
import com.hotel.model.ItemTag;
import com.hotel.model.ItemTagAssociation;
import com.hotel.model.Region;
import com.hotel.model.SearchText;
import com.hotel.modelVM.HotelInfoVM;
import com.hotel.modelVM.HotelListInfoVM;
import com.hotel.modelVM.HotelParam;
import com.hotel.modelVM.ImageVM;
import com.hotel.modelVM.ItemHotelVM;
import com.hotel.service.BaseDataService;
import com.hotel.service.BbsService;
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
	
	@Autowired BbsService bbsService;
	
	/**
	 * APP获取酒店列表接口（专题项目）
	 * @author LiuTaiXiong
	 * @param json
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/hotelListByItemChild.do", produces = "application/json;charset=UTF-8")
	public ListResult<HotelListInfoVM> hotelListByItemChild(@RequestParam(value = "json", required = false)String json, HttpServletRequest request, HttpServletResponse response) {
		//初始化分页数据
		int pageNumber = DEFAULT_PAGE_NUM;
		int pageSize = DEFAULT_PAGE_SIZE;
		int itemTagId = 0;
		
		
		JSONObject jsonObject = JSONObject.fromObject(json);
		if(jsonObject.containsKey("pageNumber")){
			pageNumber = new Integer(jsonObject.getString("pageNumber"));
		}
		if(jsonObject.containsKey("pageSize")){
			pageSize = new Integer(jsonObject.getString("pageSize"));
		}
		if(jsonObject.containsKey("itemTagId")){
			itemTagId = new Integer(jsonObject.getString("itemTagId"));
		}
		
		if(itemTagId < 1){
			ListResult<HotelListInfoVM> result = new ListResult<HotelListInfoVM>();
			result.setIsSuccess(false);
			result.setTotal(0);
			result.setMsg("请求参数无效");
			result.setRows(new ArrayList<HotelListInfoVM>());
			return result;
		}
		Map<String, Object> idMap = new HashMap<String, Object>();
		idMap.put("tagId", itemTagId);
		List<Item> itemsByTags = itemService.selectByItemTagChildOrderByScore(idMap);
		if(itemsByTags.size() < 1){
			ListResult<HotelListInfoVM> result = new ListResult<HotelListInfoVM>();
			result.setIsSuccess(true);
			result.setTotal(0);
			result.setMsg("");
			result.setRows(new ArrayList<HotelListInfoVM>());
			return result;
		}
		
		List<HashMap<String, Integer>> ids = new ArrayList<HashMap<String,Integer>>();
		for(Item it : itemsByTags){
			HashMap<String, Integer> temp = new HashMap<String, Integer>();
			temp.put("id", new Integer(it.getHotelId()));
			ids.add(temp);
		}
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageStart", 0);
		map.put("pageSize", pageNumber * pageSize);
		map.put("idList", ids);
		
		
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
		
		int pageCount = count/pageSize;
		if(count % pageSize != 0){
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
	 * APP获取指定项目类型的酒店项目列表
	 * @author LiuTaiXiong
	 * @param json
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/itemListByTagChild.do", produces = "application/json;charset=UTF-8")
	public ListResult<ItemHotelVM> itemListByTagChild(@RequestParam(value = "json", required = false)String json, HttpServletRequest request, HttpServletResponse response) {
		//初始化分页数据
		int pageNumber = DEFAULT_PAGE_NUM;
		int pageSize = DEFAULT_PAGE_SIZE;
		int itemTagId = 0;
		int hotelId = 0;
		
		
		JSONObject jsonObject = JSONObject.fromObject(json);
		if(jsonObject.containsKey("pageNumber")){
			pageNumber = new Integer(jsonObject.getString("pageNumber"));
		}
		if(jsonObject.containsKey("pageSize")){
			pageSize = new Integer(jsonObject.getString("pageSize"));
		}
		if(jsonObject.containsKey("itemTagId")){
			itemTagId = new Integer(jsonObject.getString("itemTagId"));
		}
		if(jsonObject.containsKey("hotelId")){
			hotelId = new Integer(jsonObject.getString("hotelId"));
		}
		
		if(itemTagId < 1){
			ListResult<ItemHotelVM> result = new ListResult<ItemHotelVM>();
			result.setIsSuccess(false);
			result.setTotal(0);
			result.setMsg("请求参数无效");
			result.setRows(new ArrayList<ItemHotelVM>());
			return result;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageStart", (pageNumber - 1) * pageSize);
		map.put("pageSize", pageSize);
		map.put("tagId", itemTagId);
		map.put("hotelId", hotelId);
		
		List<Item> itemsByTags = itemService.selectByItemTagChildOrderByScore(map);
		int count = itemService.countByItemTagChild(itemTagId);
		if(itemsByTags != null && itemsByTags.size() < 1){
			ListResult<ItemHotelVM> result = new ListResult<ItemHotelVM>();
			result.setIsSuccess(true);
			result.setTotal(0);
			result.setMsg("");
			result.setRows(new ArrayList<ItemHotelVM>());
			return result;
		}
		
		List<ItemHotelVM> list = new ArrayList<ItemHotelVM>();
		for(Item it : itemsByTags){
			Hotel hotel = hotelService.selectByPrimaryKey(it.getHotelId());
			List<ItemDetail> details = itemDetailService.selectByItemId(it.getId());
			ItemHotelVM hotelVM = new ItemHotelVM();
			hotelVM.setHotel(hotel);
			hotelVM.setItem(it);
			if(details.size() > 0){
				hotelVM.setItemDetail(details.get(0));
			}
			list.add(hotelVM);
		}
		
		
		int pageCount = count/pageSize;
		if(count % pageSize != 0){
			pageCount ++;
		}
		
		//返回对象处理
		ListResult<ItemHotelVM> result = new ListResult<ItemHotelVM>();
		result.setIsSuccess(true);
		result.setTotal(pageCount);
		result.setMsg("");
		result.setRows(list);
		
		return result;
	}
	
	/**
	 * APP获取指定项目类型的酒店项目列表
	 * @author LiuTaiXiong
	 * @param json
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/itemListByTagRootAndHotel.do", produces = "application/json;charset=UTF-8")
	public ListResult<ItemHotelVM> itemListByTagRootAndHotel(@RequestParam(value = "json", required = false)String json, HttpServletRequest request, HttpServletResponse response) {
		int itemTagId = 0;
		int hotelId = 0;
		
		
		JSONObject jsonObject = JSONObject.fromObject(json);
		if(jsonObject.containsKey("itemTagId")){
			itemTagId = new Integer(jsonObject.getString("itemTagId"));
		}
		if(jsonObject.containsKey("hotelId")){
			hotelId = new Integer(jsonObject.getString("hotelId"));
		}
		
		if(itemTagId < 1 || hotelId < 1){
			ListResult<ItemHotelVM> result = new ListResult<ItemHotelVM>();
			result.setIsSuccess(false);
			result.setTotal(0);
			result.setMsg("请求参数无效");
			result.setRows(new ArrayList<ItemHotelVM>());
			return result;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("itemTagId", itemTagId);
		map.put("hotelId", hotelId);
		
		List<Item> itemsByTags = itemService.selectByItemTagRootAndHotel(map);
		if(itemsByTags != null && itemsByTags.size() < 1){
			ListResult<ItemHotelVM> result = new ListResult<ItemHotelVM>();
			result.setIsSuccess(true);
			result.setTotal(0);
			result.setMsg("");
			result.setRows(new ArrayList<ItemHotelVM>());
			return result;
		}
		
		List<ItemHotelVM> list = new ArrayList<ItemHotelVM>();
		for(Item it : itemsByTags){
			Hotel hotel = hotelService.selectByPrimaryKey(it.getHotelId());
			List<ItemDetail> details = itemDetailService.selectByItemId(it.getId());
			List<ItemTag> tags = itemTagService.selectByItemId(it.getId());
			
			ItemHotelVM hotelVM = new ItemHotelVM();
			hotelVM.setHotel(hotel);
			hotelVM.setItem(it);
			if(details.size() > 0){
				hotelVM.setItemDetail(details.get(0));
			}
			hotelVM.setDetails(details);
			if(foundTag(tags,"交通")){
				hotelVM.setTagTransport(true);
			}else{
				hotelVM.setTagTransport(false);
			}
			list.add(hotelVM);
		}
		
		//返回对象处理
		ListResult<ItemHotelVM> result = new ListResult<ItemHotelVM>();
		result.setIsSuccess(true);
		result.setTotal(list.size());
		result.setMsg("");
		result.setRows(list);
		
		return result;
	}
	
	/**
	 * APP获取指定酒店项目的评论列表
	 * @author LiuTaiXiong
	 * @param json
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/commentListByHotelItem.do", produces = "application/json;charset=UTF-8")
	public ListResult<Comment> commentListByHotelItem(@RequestParam(value = "json", required = false)String json, HttpServletRequest request, HttpServletResponse response) {
		int itemId = 0;
		int pageNo = 1;
		int pageSize = 10;
		
		JSONObject jsonObject = JSONObject.fromObject(json);
		if(jsonObject.containsKey("itemId")){
			itemId = new Integer(jsonObject.getString("itemId"));
		}
		if(jsonObject.containsKey("pageNo")){
			pageNo = new Integer(jsonObject.getString("pageNo"));
		}
		if(jsonObject.containsKey("pageSize")){
			pageSize = new Integer(jsonObject.getString("pageSize"));
		}
		
		if(itemId < 1){
			ListResult<Comment> result = new ListResult<Comment>();
			result.setIsSuccess(false);
			result.setTotal(0);
			result.setMsg("请求参数无效");
			result.setRows(new ArrayList<Comment>());
			return result;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("targetId", itemId);
		map.put("startRow", (pageNo -1)*pageSize);
		map.put("pageSize", pageSize);
		
		List<Comment> comments = bbsService.selectCommentByHotel(map);
		int count = bbsService.countCommentByHotel(itemId);
		int total = count/pageSize;
		if(count%pageSize != 0){
			total ++;
		}
		
		//返回对象处理
		ListResult<Comment> result = new ListResult<Comment>();
		result.setIsSuccess(true);
		result.setTotal(total);
		result.setMsg("");
		result.setRows(comments);
		
		return result;
	}
	
	/**
	 * APP获取指定酒店已添加的酒店项目所属的父级菜单
	 * @author LiuTaiXiong
	 * @param json
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getRootItemTagByHotelId.do", produces = "application/json;charset=UTF-8")
	public ListResult<ItemTag> getRootItemTagByHotelId(@RequestParam(value = "json", required = false)String json, HttpServletRequest request, HttpServletResponse response) {
		int hotelId = 0;
		
		JSONObject jsonObject = JSONObject.fromObject(json);
		if(jsonObject.containsKey("hotelId")){
			hotelId = new Integer(jsonObject.getString("hotelId"));
		}
		
		if(hotelId < 1){
			ListResult<ItemTag> result = new ListResult<ItemTag>();
			result.setIsSuccess(false);
			result.setTotal(0);
			result.setMsg("请求参数无效");
			result.setRows(new ArrayList<ItemTag>());
			return result;
		}
		
		List<ItemTag> list = itemTagService.selectRootItemByHotelId(hotelId);
		
		//返回对象处理
		ListResult<ItemTag> result = new ListResult<ItemTag>();
		result.setIsSuccess(true);
		result.setTotal(list.size());
		result.setMsg("");
		result.setRows(list);
		
		return result;
	}
	
	/**
	 * APP获取指定酒店已添加的酒店项目所属的二级菜单
	 * @author LiuTaiXiong
	 * @param json
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getChildItemTagByHotelId.do", produces = "application/json;charset=UTF-8")
	public ListResult<ItemTag> getChildItemTagByHotelId(@RequestParam(value = "json", required = false)String json, HttpServletRequest request, HttpServletResponse response) {
		int hotelId = 0;
		int rootTagId = 0;
		
		JSONObject jsonObject = JSONObject.fromObject(json);
		if(jsonObject.containsKey("hotelId")){
			hotelId = new Integer(jsonObject.getString("hotelId"));
		}
		if(jsonObject.containsKey("itemTagId")){
			rootTagId = new Integer(jsonObject.getString("itemTagId"));
		}
		
		if(hotelId < 1){
			ListResult<ItemTag> result = new ListResult<ItemTag>();
			result.setIsSuccess(false);
			result.setTotal(0);
			result.setMsg("请求参数无效");
			result.setRows(new ArrayList<ItemTag>());
			return result;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hotelId", hotelId);
		map.put("itemTagId", rootTagId);
		
		List<ItemTag> list = itemTagService.selectChildItemByHotelId(map);
		
		//返回对象处理
		ListResult<ItemTag> result = new ListResult<ItemTag>();
		result.setIsSuccess(true);
		result.setTotal(list.size());
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
	@RequestMapping(value = "/hotelListByItem.do", produces = "application/json;charset=UTF-8")
	public ListResult<HotelListInfoVM> hotelListByItem(@RequestParam(value = "json", required = false)String json, HttpServletRequest request, HttpServletResponse response) {
		//初始化分页数据
		int pageNumber = DEFAULT_PAGE_NUM;
		int pageSize = DEFAULT_PAGE_SIZE;
		int itemTagId = 0;
		
		
		JSONObject jsonObject = JSONObject.fromObject(json);
		if(jsonObject.containsKey("pageNumber")){
			pageNumber = new Integer(jsonObject.getString("pageNumber"));
		}
		if(jsonObject.containsKey("pageSize")){
			pageSize = new Integer(jsonObject.getString("pageSize"));
		}
		if(jsonObject.containsKey("itemTagId")){
			itemTagId = new Integer(jsonObject.getString("itemTagId"));
		}
		
		if(itemTagId < 1){
			ListResult<HotelListInfoVM> result = new ListResult<HotelListInfoVM>();
			result.setIsSuccess(false);
			result.setTotal(0);
			result.setMsg("请求参数无效");
			result.setRows(new ArrayList<HotelListInfoVM>());
			return result;
		}
		
		//获取数据
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageStart", 0);
		map.put("pageSize", pageSize * pageNumber);
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
		
		int pageCount = count/pageSize;
		if(count % pageSize != 0){
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
	public Result<HotelInfoVM> getHotelInfo(String json, HttpServletRequest request, HttpServletResponse response) {
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
		Result<HotelInfoVM> result = new Result<HotelInfoVM>();
		
		//数据存储器
		HotelInfoVM data = new HotelInfoVM();
		//请求参数无效
		if(hotelId < 1){
			result.setIsSuccess(false);
			result.setMsg("请求参数无效");
			result.setData(data);
			return result;
		}
		
		Hotel hotel = hotelService.selectByPrimaryKey(hotelId);
		
		//没有找到请求的数据
		if(hotel == null){
			result.setIsSuccess(false);
			result.setMsg("没有找到请求的数据");
			result.setData(data);
			return result;
		}
		//转存返回对象
		//HotelInfoVM vm = new HotelInfoVM();
		data.setId(hotel.getId());
		data.setName(hotel.getName());
		data.setText(hotel.getText());
		data.setLatitude(hotel.getLatitude());
		data.setLongitude(hotel.getLongitude());
		
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
				data.setTel(temp.getTel());
				//设置评分
				data.setScore(temp.getScore());
				//设置位置描述
				data.setAddress(temp.getPosition());
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
				data.setImgUrl(images);
				
				//设置数量统计
				//查询浏览次数
				int countBrowse = customerBrowseService.countByItemId(temp.getId());
				//查询收藏次数
				int countCollectiono = customerCollectionService.countByItemId(temp.getId());
				data.setCountBrowse(countBrowse);
				data.setCountCollection(countCollectiono);
				
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
		//Region area = baseDataService.getRegionById(hotel.getRegionId());
		//String path = area.getPath();
		//String[] arr = path.split("\\.");
		//Region province = baseDataService.getRegionById(new Integer(arr[0]));
		//Region city = baseDataService.getRegionById(new Integer(arr[1]));
		
		//处理成功的结果返回
		result.setIsSuccess(true);
		result.setMsg("");
		result.setData(data);
		return result;
	}
	
	/**
	 * 保存用户评论酒店
	 * @author LiuTaiXiong
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/saveHotelComment.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody Result<String> saveAgreeHistory(
			@RequestParam(value = "json", required = false) String json)
	{
		int customerId = 0;
		int targetId = 0;
		String text = "";
		try{
			JSONObject jsonObject = JSONObject.fromObject(json);
			if(jsonObject.containsKey("customerId")){
				customerId = jsonObject.getInt("customerId");
			}
			if(jsonObject.containsKey("targetId")){
				targetId = jsonObject.getInt("targetId");
			}
			if(jsonObject.containsKey("text")){
				text = jsonObject.getString("text");
			}
		}catch(Exception e){
			e.printStackTrace();
			return new Result<String>("", false, "json解析异常");
		}
		if(customerId < 1 || targetId < 1 || text.trim().equals("")){
			return new Result<String>("", false, "请求无效");
		}
		
		Bbs bbs = new Bbs();
		bbs.setCustomerId(customerId);
		bbs.setBbsType(2);
		bbs.setCategoryId(0);
		bbs.setPostType(0);
		bbs.setTargetType(1);
		bbs.setTargetId(targetId);
		bbs.setParentId(0);
		bbs.setLevel(0);
		bbs.setText(text);
		bbs.setCreateTime(new Date());
	    bbs.setTimeStamp(new Date());
		
		int count = bbsService.insert(bbs);
		if(count > 0){
			return new Result<String>("", true, "");
		}
		return new Result<String>("", false, "评论失败");
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
	@RequestMapping(value = "fullTextSearchOfHotel.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody  String fullTextSearchOfHotel(
			@RequestParam(value = "searchData", required = false) String searchData,
			HttpServletRequest request)
	{
		//先保存查询内容，然后进行全文查询
		JSONObject jObj = JSONObject.fromObject(searchData);
		SearchText text = (SearchText) JSONObject.toBean(jObj,SearchText.class);
		text.setSearchTime(new Date());
		//全文查询:查询酒店
		List<HotelParam> list = hotelService.fullTextSearchOfHotel(text.getText());
		if(list ==null||list.size() ==0){
			return new ListResult<HotelParam>(null,false,"全文搜索酒店失败").toJson();
		}
		return new ListResult<HotelParam>(list,true,"获取推荐酒店成功").toJson();
	}
	
	//查找指定类型的标签
	private boolean foundTag(List<ItemTag> tags, String name){
		boolean res = false;
		if(tags != null && tags.size() > 0){
			for(ItemTag tag : tags){
				if(tag.getName().equals(name)){
					res = true;
					break;
				}
			}
		}
		return res;
	}
}
