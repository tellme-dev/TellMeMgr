package com.hotel.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.stereotype.Service;

import com.hotel.dao.ItemTagMapper;
import com.hotel.model.ItemTag;
import com.hotel.service.ItemTagService;

@Service("itemTagService")
public class ItemTagServiceImpl implements ItemTagService{
	
	@Resource
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
		List<ItemTag> list =itemTagMapper.getTagFromMin(1);
		list.addAll(itemTagMapper.getTagFromMin(2));
		return list;
	}

	@Override
	public List<ItemTag> getchildItemTagsByParentId(Integer parentId) {
		// TODO Auto-generated method stub
		return itemTagMapper.getchildItemTagsByParentId(parentId);
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
}
