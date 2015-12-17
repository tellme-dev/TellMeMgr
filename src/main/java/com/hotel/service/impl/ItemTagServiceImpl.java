package com.hotel.service.impl;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.dao.ItemTagMapper;
import com.hotel.model.ItemTag;
import com.hotel.service.ItemTagService;

@Service("itemTagService")
public class ItemTagServiceImpl implements ItemTagService{
	
	@Autowired
	private ItemTagMapper itemTagMapper;

	@Override
	public List<ItemTag> getTagFromMin(int tagType) {
		// TODO Auto-generated method stub
		return itemTagMapper.getTagFromMin(tagType);
	}

	@Override
	public List<ItemTag> getTagByParentId(int parentId) {
		// TODO Auto-generated method stub
		return itemTagMapper.getTagByParentId(parentId);
	}
	
	@Override
	public List<ItemTag> getHomeItemList() {
		// TODO Auto-generated method stub
		List<ItemTag> list =itemTagMapper.getTagByHomeItemList(1);
		list.addAll(itemTagMapper.getTagByHomeItemList(2));
		return list;
	}
	public List<ItemTag> getMoreItemList() {
		// TODO Auto-generated method stub
		List<ItemTag> list =itemTagMapper.getTagByHomeItemList(3);
		return list;
	}

	@Override
	public List<ItemTag> getchildItemTagsByParentId(Integer parentId) {
		// TODO Auto-generated method stub
		return itemTagMapper.getTagByParentId(parentId);
	}

	@Override
	public ItemTag selectByItemId(int itemId) {
		// TODO Auto-generated method stub
		return itemTagMapper.selectByItemId(itemId);
	}
	@Test
	public void test(){
		itemTagMapper.selectByItemId(1);
	}

	@Override
	public ItemTag getItemTagById(int tagId) {
		// TODO Auto-generated method stub
		return itemTagMapper.selectByPrimaryKey(tagId);
	}

	@Override
	public List<ItemTag> getItemTagsByLevel(int i) {
		// TODO Auto-generated method stub
		return itemTagMapper.getItemTagsByLevel(i);
	}

	@Override
	public ItemTag selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return itemTagMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<ItemTag> selectRootItemByHotelId(int hotelId) {
		// TODO Auto-generated method stub
		return itemTagMapper.selectRootItemByHotelId(hotelId);
	}

	@Override
	public List<ItemTag> selectChildItemByHotelId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return itemTagMapper.selectChildItemByHotelId(map);
	}
}
