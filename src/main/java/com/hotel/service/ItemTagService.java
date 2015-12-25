package com.hotel.service;

import java.util.List;
import java.util.Map;

import com.hotel.model.ItemTag;

public interface ItemTagService {

	List<ItemTag> getTagFromMin(int tagType);
	
	List<ItemTag> getTagByParentId(int parentId);
	
	ItemTag selectByPrimaryKey(Integer id);
	
	/**
	 * 获取首页的菜单栏项目
	 * @return
	 */
	List<ItemTag> getHomeItemList();

	List<ItemTag> getchildItemTagsByParentId(Integer parentId);
	
	List<ItemTag> selectByItemId(int itemId);

	ItemTag getItemTagById(int tagId);
	
	/**
	 * 获取level对应的所有itemItags
	 * @param i
	 * @return
	 * @author hzf
	 */
	List<ItemTag> getItemTagsByLevel(int i);

	List<ItemTag> getMoreItemList();
	
	List<ItemTag> selectRootItemByHotelId(int hotelId);
	
	List<ItemTag> selectChildItemByHotelId(Map<String, Object> map);
}
