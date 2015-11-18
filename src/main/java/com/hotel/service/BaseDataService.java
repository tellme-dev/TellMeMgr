package com.hotel.service;

import java.util.List;

import com.hotel.model.Org;

public interface BaseDataService {
	
	List<Org> getOrgComboList(Integer pid);

}
