package com.hotel.service.impl;

import java.util.List;

import javax.annotation.Resource;

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
}
