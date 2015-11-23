package com.hotel.service;

import java.util.List;

import com.hotel.model.Advertisement;

public interface AdvertisementService {

	List<Advertisement> getAdPageList(Advertisement ad);

	int getAdPageListCount(Advertisement ad);

	void saveorUpdateAd(Advertisement ad);

	Advertisement getAdById(Integer id);

}
