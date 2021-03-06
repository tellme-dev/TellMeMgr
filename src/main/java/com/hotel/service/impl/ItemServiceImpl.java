package com.hotel.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotel.dao.ItemMapper;
import com.hotel.model.Item;
import com.hotel.modelVM.ItemVM;
import com.hotel.service.ItemService;

@Service("itemService")
public class ItemServiceImpl implements ItemService{
	
	@Resource
	private ItemMapper itemMapper;

	@Override
	public List<Item> getItemByHotel(int hotelId) {
		// TODO Auto-generated method stub
		return itemMapper.getItemByHotel(hotelId);
	}

	@Override
	public int addAndReturnId(Item item) {
		// TODO Auto-generated method stub
		return itemMapper.addAndReturnId(item);
	}

	@Override
	public int deleteByItemId(Map<String, Object> idMap) {
		// TODO Auto-generated method stub
		return itemMapper.deleteByItemId(idMap);
	}
	
	@Override
	public ItemVM getItemVMById(Integer itemId) {
		// TODO Auto-generated method stub
		return itemMapper.selectItemVMById(itemId);
	}

	@Override
	public Item getItemById(Integer itemId) {
		// TODO Auto-generated method stub
		return itemMapper.selectByPrimaryKey(itemId);
	}

	@Override
	public List<Item> selectByItemTagChildOrderByScore(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return itemMapper.selectByItemTagChildOrderByScore(map);
	}

	@Override
	public int countByItemTagChild(int tagId) {
		// TODO Auto-generated method stub
		return itemMapper.countByItemTagChild(tagId);
	}

	@Override
	public List<Item> selectByItemTagRootAndHotel(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return itemMapper.selectByItemTagRootAndHotel(map);
	}

	@Override
	public List<ItemVM> getItemVMByChildTagId(int childTagId, int num) {
		// TODO Auto-generated method stub
		return itemMapper.getItemVMByChildTagId(childTagId,num);
	}
	
	@Override
	public List<Item> selectItemByHotelAndTagName(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return itemMapper.selectItemByHotelAndTagName(map);
	}

	@Override
	public Item selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return itemMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<ItemVM> getItemVMByTagId(int tagId, int num) {
		// TODO Auto-generated method stub
		return itemMapper.getItemVMByTagId(tagId,num);
	}

}
