package com.hotel.service;

import java.util.List;

import com.hotel.model.ItemTag;
import com.hotel.viewmodel.ItemTagWebVM;

public interface ItemTagService {

	List<ItemTag> getTagFromMin(int tagType);
	
	List<ItemTag> getTagByParentId(int parentId);
	
	/**
	 * 获取首页的菜单栏项目
	 * @return
	 */
	List<ItemTag> getHomeItemList();

	List<ItemTag> getchildItemTagsByParentId(Integer parentId);
	
	ItemTag selectByItemId(int itemId);

	ItemTag getItemTagById(int tagId);
	
	/**
	 * 获取level对应的所有itemItags
	 * @param i
	 * @return
	 * @author hzf
	 */
	List<ItemTag> getItemTagsByLevel(int i);
	
}
