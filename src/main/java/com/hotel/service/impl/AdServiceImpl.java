package com.hotel.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.dao.AdvertisementMapper;
import com.hotel.dao.BannerDetailMapper;
import com.hotel.model.BannerDetail;
import com.hotel.modelVM.AdvertisementVM;
import com.hotel.service.AdService;

@Service
public class AdServiceImpl implements AdService {
	@Autowired AdvertisementMapper advertisementMapper;
	@Autowired BannerDetailMapper bannerDetailMapper;

	@Override
	public List<AdvertisementVM> getAdList(int banner, int adNum) {
		//通过banner获取adid
		List<BannerDetail> BannerDetailList = bannerDetailMapper.getAdIdList(banner);
		if(BannerDetailList==null||BannerDetailList.size()==0){
			return null;
		}
		//获取adNum数量的广告
		if(BannerDetailList.size()<adNum){
			adNum = BannerDetailList.size();
		}
		
		List<AdvertisementVM> adList = new ArrayList<AdvertisementVM>();
		for(int i= 0;i<adNum;i++){
			int id = BannerDetailList.get(i).getAdId();
			AdvertisementVM ad =advertisementMapper.getAdList(id); 
			if(ad!=null){
				adList.add(ad);
			}
		}
		return adList;
		
	}

}
