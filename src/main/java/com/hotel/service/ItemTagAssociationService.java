package com.hotel.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.hotel.model.ItemTagAssociation;

@Service
public interface ItemTagAssociationService {

	int insert(ItemTagAssociation record);
	
	int deleteByItemId(Map<String, Object> idMap);
	
	List<ItemTagAssociation> getTagTypeItem(int tagType);
	
	List<ItemTagAssociation> getAssociationListByItemTagId(int tagId);
}
