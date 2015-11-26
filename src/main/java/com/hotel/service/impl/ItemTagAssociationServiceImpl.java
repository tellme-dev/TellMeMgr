package com.hotel.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.dao.ItemTagAssociationMapper;
import com.hotel.model.ItemTagAssociation;
import com.hotel.service.ItemTagAssociationService;

@Service("itemTagAssociationService")
public class ItemTagAssociationServiceImpl implements ItemTagAssociationService{
	
	@Resource
	private ItemTagAssociationMapper itemTagAssociationMapper;

	@Override
	public int insert(ItemTagAssociation record) {
		// TODO Auto-generated method stub
		return itemTagAssociationMapper.insert(record);
	}

	@Override
	public int deleteByItemId(Map<String, Object> idMap) {
		// TODO Auto-generated method stub
		return itemTagAssociationMapper.deleteByItemId(idMap);
	}

	@Override
	public List<ItemTagAssociation> getTagTypeItem(int tagType) {
		// TODO Auto-generated method stub
		return itemTagAssociationMapper.getTagTypeItem(tagType);
	}
	
	@Override
	public List<ItemTagAssociation> getAssociationListByItemTagId(int tagId) {
		// TODO Auto-generated method stub
		return itemTagAssociationMapper.getItemsBytagId(tagId);
	}
}
