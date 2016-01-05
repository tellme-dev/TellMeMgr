package com.hotel.service;

import java.util.List;
import java.util.Map;

import com.hotel.model.BannerDetail;
import com.hotel.viewmodel.BannerWebVM;


public interface BannerService {

	List<BannerWebVM> getBannerPageList(Map<String, Object> map);

	int getBannerPageListCount(Map<String, Object> map);

	BannerWebVM loadBannerById(Integer id);

	void saveorUpdateAd(BannerWebVM banner);

	List<BannerDetail> loadBannerDetailListByBannerId(Integer id);

}
