package com.hotel.dao;

import java.util.List;
import java.util.Map;

import com.hotel.model.ItemTagAssociation;

@MyBatisRepository
public interface ItemTagAssociationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemTagAssociation record);

    int insertSelective(ItemTagAssociation record);

    ItemTagAssociation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemTagAssociation record);

    int updateByPrimaryKey(ItemTagAssociation record);
    
    List<ItemTagAssociation> getTagTypeItem(int tagType);
    
    int deleteByItemId(Map<String, Object> idMap);
}