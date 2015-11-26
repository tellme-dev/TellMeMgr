package com.hotel.service;

import java.util.List;
import java.util.Map;

import com.hotel.model.ItemTagAssociation;

public interface ItemTagAssociationService {

	int insert(ItemTagAssociation record);
	
	int deleteByItemId(Map<String, Object> idMap);
	
	List<ItemTagAssociation> getTagTypeItem(int tagType);
}
