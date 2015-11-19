package com.hotel.service;

import java.util.List;

import com.hotel.model.Org;
import com.hotel.model.Region;

public interface BaseDataService {
	
	List<Org> getOrgComboList(Integer pid);

	List<Region> getAllRegion();
	
	List<Region> getProvinceRegion();
	
	List<Region> getCityRegion(Integer provinceId);
	
	List<Region> getAreaRegion(Integer cityId);
}
