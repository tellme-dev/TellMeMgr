package com.hotel.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hotel.model.AdDetail;
import com.hotel.model.Advertisement;
import com.hotel.modelVM.AdvertisementVM;

@Service
public interface AdService {
	List<AdvertisementVM> getAdList(int banner,int adNum);
	
	Advertisement selectByPrimaryKey(Integer id);
	
	AdDetail getFirstDetail(int adId);
}
