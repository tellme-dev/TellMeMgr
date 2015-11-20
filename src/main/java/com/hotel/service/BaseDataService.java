package com.hotel.service;

import java.util.List;

import com.hotel.model.ItemTag;
import com.hotel.model.Org;

public interface BaseDataService {
	
	List<Org> getOrgComboList(Integer pid);

	List<ItemTag> selectTagByTagType(int tagType);

	List<Org> getOrgList(Integer pid);

	List<ItemTag> selectTagList();

}
