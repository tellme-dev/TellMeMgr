package com.hotel.service;

import java.util.List;
import java.util.Map;

import com.hotel.common.ListResult;
import com.hotel.common.utils.Page;
import com.hotel.model.Advertisement;
import com.hotel.modelVM.AdvertisementVM;
import com.hotel.viewmodel.AdvertisementWebVM;

public interface AdvertisementService {

	List<AdvertisementWebVM> getAdPageList(Map<String, Object> map);

	int getAdPageListCount(Map<String, Object> map);

	void saveorUpdateAd(AdvertisementWebVM ad);

	AdvertisementWebVM getAdById(Integer id);

	void updateUserByIds(String adIds);
	
	List<AdvertisementVM> getAdList(int banner,int adNum);

	Advertisement getAdByPrimaryKey(Integer id);

	/**
	 * @author jun
	 * @param page
	 * @return
	 */
	ListResult<AdvertisementVM> loadAdList(Page page);

	ListResult<AdvertisementVM> loadAdListByHotelId(int hotelId);

	AdvertisementVM loadAdById(int adId);

}
