package com.hotel.app;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.common.ListResult;
import com.hotel.common.Result;
import com.hotel.model.Item;
import com.hotel.model.ItemTag;
import com.hotel.model.ItemTagAssociation;
import com.hotel.model.SwiperHotelItem;
import com.hotel.modelVM.HomeItemVM;
import com.hotel.modelVM.HotelVM;
import com.hotel.modelVM.ItemVM;
import com.hotel.service.HotelService;
import com.hotel.service.ItemService;
import com.hotel.service.ItemTagAssociationService;
import com.hotel.service.ItemTagService;

/**
 * 酒店服务项目
 * @author charo
 *
 */
@Controller
@RequestMapping("/app/menu")
public class ItemTagController {
	@Autowired ItemTagService itemTagService;
	@Autowired ItemTagAssociationService itemTagAssociationService; 
	@Autowired ItemService itemService;
	@Autowired HotelService hotelService;
	/**
	 * 获取后台配置的菜单
	 * @param customerInfo
	 * @return
	 */
	@RequestMapping(value = "loadMenuList.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String loadMenuList(HttpServletRequest request)
	{
		List<HomeItemVM> homeItemVMList = new ArrayList<HomeItemVM>();
		//获取8+1
		List<ItemTag> itemTagList = itemTagService.getHomeItemList();
		for(int index=0;index<itemTagList.size();index++){
			homeItemVMList.add(new HomeItemVM(itemTagList.get(index).getId(),itemTagList.get(index).getName()));
		}
		homeItemVMList.add(new HomeItemVM( "更多"));
		
		int size = homeItemVMList.size();
		for(int count = 0;count<size;count++){
			List<SwiperHotelItem> urlList = this.getImageUrlsByTagId(homeItemVMList.get(count).getItemTagId());
//			if(urlList==null){
//				//return new ListResult<HomeItemVM>(null,false,"获取菜单项失败").toJson();
//				homeItemVMList.get(count).setImageUrls(null);
//			}
			homeItemVMList.get(count).setHotelItems(urlList);
		}
		return new ListResult<HomeItemVM>(homeItemVMList,true,"获取菜单项成功").toJson();
	}
	@RequestMapping(value = "loadSwiperList.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String loadSwiperList(HttpServletRequest request)
	{
		List<HomeItemVM> homeItemVMList = new ArrayList<HomeItemVM>();
		//获取8+1
		List<ItemTag> itemTagList = itemTagService.getHomeItemList();
		for(int index=0;index<itemTagList.size();index++){
			homeItemVMList.add(new HomeItemVM(itemTagList.get(index).getId(),itemTagList.get(index).getName()));
		}
		homeItemVMList.add(new HomeItemVM( "更多"));
		
		int size = homeItemVMList.size();
		for(int count = 0;count<size;count++){
			List<SwiperHotelItem> urlList = this.getImageUrlsByTagId(homeItemVMList.get(count).getItemTagId());
			homeItemVMList.get(count).setHotelItems(urlList);
		}
		return new ListResult<HomeItemVM>(homeItemVMList,true,"获取菜单项成功").toJson();
	}
	
	/**
	 * 获取后台配置的菜单
	 * @param customerInfo
	 * @return
	 */
	@RequestMapping(value = "loadMenuRootList.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String loadMenuRootListss(HttpServletRequest request)
	{
		List<HomeItemVM> homeItemVMList = new ArrayList<HomeItemVM>();
		List<ItemTag> itemTagList = itemTagService.getTagByParentId(0);
		for(int index=0;index<itemTagList.size();index++){
			if(itemTagList.get(index).getTagType() == 2 || itemTagList.get(index).getTagType() == 1){
				homeItemVMList.add(new HomeItemVM(itemTagList.get(index).getId(),itemTagList.get(index).getName()));
			}
		}
		
		return new ListResult<HomeItemVM>(homeItemVMList,true,"获取菜单项成功").toJson();
	}
	
	/**
	 * 获取后台配置的二级菜单菜单
	 * @author LiuTaiXiong
	 * @param customerInfo
	 * @return
	 */
	@RequestMapping(value = "loadMenuChildList.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String loadMenuChildList(@RequestParam(value = "json", required = false) String json, HttpServletRequest request)
	{
		JSONObject object = JSONObject.fromObject(json);
		int itemTagId = 0;
		if(object.containsKey("itemTagId")){
			itemTagId = object.getInt("itemTagId");
		}
		if(itemTagId < 1){
			return new ListResult<HomeItemVM>(null,false,"请求参数无效").toJson();
		}
		
		List<HomeItemVM> homeItemVMList = new ArrayList<HomeItemVM>();
		//获取子节点菜单
		List<ItemTag> itemTagList = itemTagService.getTagByParentId(itemTagId);
		for(int index=0;index<itemTagList.size();index++){
			HomeItemVM homeItemVM = new HomeItemVM(itemTagList.get(index).getId(),itemTagList.get(index).getName());
			homeItemVM.setDefaultImageUrl(itemTagList.get(index).getImageUrl());
			homeItemVMList.add(homeItemVM);
		}
		
		return new ListResult<HomeItemVM>(homeItemVMList,true,"获取菜单项成功").toJson();
	}
	
	/**
	 * 获取指定项目类型的基本信息
	 * @author LiuTaiXiong
	 * @param customerInfo
	 * @return
	 */
	@RequestMapping(value = "loadItemTag.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String loadItemTag(@RequestParam(value = "json", required = false) String json, HttpServletRequest request)
	{
		JSONObject object = JSONObject.fromObject(json);
		int itemTagId = 0;
		if(object.containsKey("itemTagId")){
			itemTagId = object.getInt("itemTagId");
		}
		if(itemTagId < 1){
			return new ListResult<HomeItemVM>(null,false,"请求参数无效").toJson();
		}
		
		ItemTag itemTag = itemTagService.selectByPrimaryKey(itemTagId);
		return new Result<ItemTag>(itemTag, true, "").toJson();
	}
	
	/**
	 * 根据一类标签获取对应的酒店图片url列表
	 * @param tagId
	 * @return
	 */
	private List<SwiperHotelItem> getImageUrlsByTagId(int tagId){
		List<SwiperHotelItem> urlList = null;
		//所有酒店进行排序
		//1、获取酒店
		List<HotelVM> hotels = this.getHotelVMListByTagId(tagId);
		if(hotels==null||hotels.size() ==0){
			return null;
		}
		//2、获取所有酒店的评分
		
		if(tagId ==0){
			hotels = this.findTheBestHotels(hotels,8);
		}else{
			hotels = this.findTheBestHotels(hotels,4);
		}
		
		//3、获取前几名的酒店的"酒店"图片信息
		urlList =this.getImageUrlsByItemTag(hotels, tagId);
		return urlList;
	}
	/**
	 * 获取标签下的图片
	 * @param hotels
	 * @param itemTag
	 * @return
	 */
	private List<SwiperHotelItem> getImageUrlsByItemTag(List<HotelVM> hotels,int itemTag){
		List<SwiperHotelItem> result = new ArrayList<SwiperHotelItem>();
		for(int i =0;i<hotels.size();i++){
			List<ItemVM> list = hotels.get(i).getItemVMs();
			for(int j = 0;j<list.size();j++){
				ItemVM itemVM = list.get(j);
				if(itemVM.getItemTags()!=null&&itemVM.getItemTags().size()>0){
					for(int c = 0;c<itemVM.getItemTags().size();c++){
						if(itemVM.getItemTags().get(c).getId() ==itemTag){
							SwiperHotelItem item = new SwiperHotelItem();
							item.setImageUrl(itemVM.getItemDetails().get(0).getImageUrl());
							item.setItemTagId(itemVM.getItemTags().get(0).getId());
							item.setItemId(itemVM.getId());
							result.add(item);
							break;
						}
					}
					
				}
			}
		}
		return result;
	}
	/**
	 * 找到最好的几个酒店
	 * @param hotels
	 * @return
	 */
	private List<HotelVM> findTheBestHotels(List<HotelVM> hotels,int hotelNum){
		List<HotelVM> result = new ArrayList<HotelVM>();
		if(hotels==null||hotels.size()==0){
			return null;
		}
		if(hotels.size()<hotelNum){
			hotelNum = hotels.size();
		}
		for(int i=0;i<hotelNum;i++){
			HotelVM hotel =this.getTheBestHotel(hotels); 
			result.add(hotel);
			hotels.remove(hotel);
		}
		
		return result;
	}
	/**
	 * 在酒店列表中查找得分最高的第一个
	 * @param hotels
	 * @return
	 */
	private HotelVM getTheBestHotel(List<HotelVM> hotels){
		HotelVM hotel = hotels.get(0);
		BigDecimal maxScore = this.getScoreOfHotel(hotels.get(0));
		for(int i=0;i<hotels.size();i++){
			BigDecimal score = this.getScoreOfHotel(hotels.get(i));
			if(score.compareTo(maxScore)==1){//大于
				maxScore = score;
				hotel = hotels.get(i);
			}
		}
		return hotel;
	}
	/**
	 * 获取一个酒店的得分
	 * @param hotel
	 * @return
	 */
	private BigDecimal getScoreOfHotel(HotelVM hotel){
		BigDecimal result = BigDecimal.ZERO;
		for(int i = 0;i<hotel.getItemVMs().size();i++){
//			获取“酒店”标签下的评分
//			if(hotel.getItemVMs().get(i).getItemTag().getId() ==1){
//				return hotel.getItemVMs().get(i).getScore();
//			}
			result.add(hotel.getItemVMs().get(i).getScore());
		}
		
		return result.divide(BigDecimal.valueOf(hotel.getItemVMs().size()));
	}
	/**
	 * 获取标签下的所有酒店
	 * @param tagId
	 * @return
	 */
	private List<HotelVM> getHotelVMListByTagId(int tagId){
		//1、获取二级itemtags
		//2、获取二级itemTags对应的itemAssociations
		//3、获取itemAssociations对应的items
		//4、剔除
		//5、根据items获取对应的酒店
		List<ItemTag> childItemTagList = this.getChildItemTagList(tagId);
		if(childItemTagList.size() ==0){
			return null;
		}
		List<ItemTagAssociation> itemAssociationList = this.getItemAssociationsList(childItemTagList);
		if(itemAssociationList.size() ==0){
			return null;
		}
		List<Item> itemList = this.getItemsList(itemAssociationList);
		if(itemList.size() ==0){
			return null;
		}
		
		List<HotelVM> hotels = this.getHotelVM(itemList);
		return hotels;
	}
	/**
	 * 根据items获取对应的酒店
	 * @param itemList
	 * @return
	 */
	private List<HotelVM> getHotelVM(List<Item> itemList){
		List<HotelVM> result = new ArrayList<HotelVM>();
		for(int i = 0;i<itemList.size();i++){
			HotelVM temp = hotelService.getHotelVMById(itemList.get(i).getHotelId());
			if(temp ==null){
				continue;
			}
			result.add(temp);
		}
		return result;
	} 
	/**
	 * 获取itemAssociations对应的items
	 * @param list
	 * @return
	 */
	private List<Item> getItemsList(List<ItemTagAssociation> list){
		List<Item> result = new ArrayList<Item>();
		for(int i = 0;i<list.size();i++){
			Item temp = itemService.getItemById(list.get(i).getItemId());
			if(temp ==null){
				continue;
			}
			//剔除相同的酒店
			int j = 0;
			for(j=0;j<=result.size();j++){
				if(j ==0){
					result.add(temp);
					break;
				}
				if(temp.getHotelId() == result.get(j).getHotelId()){
					break;
				}
				if(j== result.size()-1){
					result.add(temp);
				}
			}
		}
		return result;
	}
	
	/**
	 * 查询tags对应的itemAssociations:
	 * @param itemTagList
	 * @return
	 */
	private List<ItemTagAssociation> getItemAssociationsList(List<ItemTag> itemTagList){
		List<ItemTagAssociation> result = new ArrayList<ItemTagAssociation>();
		//获取对应的具体就提服务标签
		for(int j = 0;j<itemTagList.size();j++){
			List<ItemTagAssociation> temp = itemTagAssociationService.getAssociationListByItemTagId(itemTagList.get(j).getId());
			if(temp==null||temp.size()==0){
				continue;
			}
			result.addAll(temp);
		}
		return result;
	}
	/**
	 * 获取二级itemtags
	 * @param tagId
	 * @return
	 */
	private List<ItemTag> getChildItemTagList(int tagId){
		List<ItemTag> childItemTagList = new ArrayList<ItemTag>();
		//如果是"更多"
		if(tagId ==0){
			List<ItemTag> temp = itemTagService.getMoreItemList();
			childItemTagList = itemTagService.getchildItemTagsByParentId(temp.get(0).getId());
		}else if(tagId ==1){
			//如果是"酒店"的话，它就没有二级标签
			childItemTagList.add(itemTagService.getItemTagById(tagId));
		}
		else{
			childItemTagList = itemTagService.getchildItemTagsByParentId(tagId);
		}
		return childItemTagList;
	}
	/**
	 * 获取酒店服务项目标签的一级项目
	 * @param request
	 * @return
	 * @author hzf
	 */
	@RequestMapping(value = "getFirstLevelItemTag.do", produces = "application/json;charset=UTF-8")
	public String getFirstLevelItemTag(HttpServletRequest request){
		List<ItemTag> list = itemTagService.getItemTagsByLevel(1);
		if(list ==null ||list.size() ==0){
			return new ListResult<ItemTag>(null,false,"获取一级项目标签失败").toJson();
		}
		return new ListResult<ItemTag>(list,true,"获取成功").toJson();
	}
	/**
	 * 获取酒店服务项目标签的二级项目
	 * @param parentId
	 * @param request
	 * @return
	 * @author hzf
	 */
	@RequestMapping(value = "getSecondLevelItemTagByParentId.do", produces = "application/json;charset=UTF-8")
	public String getSecondLevelItemTagByParentId(
			@RequestParam(value = "parentId", required = false) int parentId,
			HttpServletRequest request){
		List<ItemTag> list = itemTagService.getchildItemTagsByParentId(parentId);
		if(list ==null ||list.size() ==0){
			return new ListResult<ItemTag>(null,false,"获取二级项目标签失败").toJson();
		}
		return new ListResult<ItemTag>(list,true,"获取成功").toJson();
	}
}
