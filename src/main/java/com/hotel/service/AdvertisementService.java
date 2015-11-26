package com.hotel.service;

import java.util.List;
import java.util.Map;

import com.hotel.viewmodel.AdvertisementWebVM;

public interface AdvertisementService {

	List<AdvertisementWebVM> getAdPageList(Map<String, Object> map);

	int getAdPageListCount(Map<String, Object> map);

	void saveorUpdateAd(AdvertisementWebVM ad);

	AdvertisementWebVM getAdById(Integer id);

	void updateUserByIds(String adIds);

}
