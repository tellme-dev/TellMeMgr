package com.hotel.dao;

import java.util.List;

import com.hotel.model.ItemTag;
import com.hotel.viewmodel.ItemTagWebVM;

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
    
    List<ItemTag> getchildItemTagsByParentId(Integer parentId);
	
	ItemTag selectByItemId(int itemId);

	List<ItemTagWebVM> selectByPid(Integer pid);
}
