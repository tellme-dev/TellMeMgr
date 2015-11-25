package com.hotel.service;

import java.util.List;

import com.hotel.model.ItemTag;
import com.hotel.model.Org;
import com.hotel.model.Region;
import com.hotel.viewmodel.ItemTagWebVM;

public interface BaseDataService {
	
	List<Org> getOrgComboList(Integer pid);

	List<ItemTag> selectTagByTagType(int tagType);

	List<Org> getOrgList(Integer pid);

	List<ItemTag> selectTagList();

	List<Region> getAllRegion();
	
	List<Region> getProvinceRegion();
	
	List<Region> getCityRegion(Integer provinceId);
	
	List<Region> getAreaRegion(Integer cityId);
	
	Region getRegionById(int id);

	List<ItemTagWebVM> getItemTagTree(Integer pid);
}