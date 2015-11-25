package com.hotel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.dao.ItemTagAssociationMapper;
import com.hotel.model.ItemTagAssociation;
import com.hotel.service.ItemTagAssociationService;
@Service
public class ItemTagAssociationServiceImpl implements ItemTagAssociationService {
	@Autowired ItemTagAssociationMapper itemTagAssociationMapper;
	@Override
	public List<ItemTagAssociation> getAssociationListByItemTagId(int tagId) {
		// TODO Auto-generated method stub
		return itemTagAssociationMapper.getItemsBytagId(tagId);
	}

}
