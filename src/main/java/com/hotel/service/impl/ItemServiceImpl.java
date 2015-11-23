package com.hotel.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotel.dao.ItemMapper;
import com.hotel.model.Item;
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

}
