package com.hotel.service;

import java.util.List;
import java.util.Map;

import com.hotel.viewmodel.AdvertisementVM;

public interface AdvertisementService {

	List<AdvertisementVM> getAdPageList(Map<String, Object> map);

	int getAdPageListCount(Map<String, Object> map);

	void saveorUpdateAd(AdvertisementVM ad);

	AdvertisementVM getAdById(Integer id);

	void updateUserByIds(String adIds);

}
