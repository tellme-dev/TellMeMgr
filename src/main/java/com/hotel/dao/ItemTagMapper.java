package com.hotel.dao;

import java.util.List;

import com.hotel.model.ItemTag;

@MyBatisRepository
public interface ItemTagMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemTag record);

    int insertSelective(ItemTag record);

    ItemTag selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemTag record);

    int updateByPrimaryKey(ItemTag record);

	List<ItemTag> selectTagByTagType(int tagType);

	List<ItemTag> selectTagList();
    
    List<ItemTag> getTagFromMin(int tagType);
}
