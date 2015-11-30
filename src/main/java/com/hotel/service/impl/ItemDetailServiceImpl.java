package com.hotel.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotel.dao.ItemDetailMapper;
import com.hotel.model.ItemDetail;
import com.hotel.service.ItemDetailService;

@Service("itemDetailService")
public class ItemDetailServiceImpl implements ItemDetailService{
	
	@Resource
	private ItemDetailMapper itemDetailMapper;

	@Override
	public int batchInsert(List<ItemDetail> list) {
		return itemDetailMapper.batchInsert(list);
	}

	@Override
	public int deleteByItemId(Map<String, Object> idMap) {
		// TODO Auto-generated method stub
		return itemDetailMapper.deleteByItemId(idMap);
	}

	@Override
	public List<ItemDetail> selectByItemId(int itemId) {
		// TODO Auto-generated method stub
		return itemDetailMapper.selectByItemId(itemId);
	}
}
