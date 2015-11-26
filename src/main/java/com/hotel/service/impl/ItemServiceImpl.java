package com.hotel.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.dao.ItemMapper;
import com.hotel.model.Item;
import com.hotel.modelVM.ItemVM;
import com.hotel.service.ItemService;

@Service("itemService")
public class ItemServiceImpl implements ItemService{
	
	@Autowired
	private ItemMapper itemMapper;

	@Override
	public List<Item> getItemByHotel(int hotelId) {
		// TODO Auto-generated method stub
		return itemMapper.getItemByHotel(hotelId);
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

}
