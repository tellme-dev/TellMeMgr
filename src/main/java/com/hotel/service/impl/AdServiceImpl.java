package com.hotel.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hotel.dao.AdvertisementMapper;
import com.hotel.modelVM.AdvertisementVM;
import com.hotel.service.AdService;

public class AdServiceImpl implements AdService {
	@Autowired AdvertisementMapper advertisementMapper;
	@Autowired 

	@Override
	public List<AdvertisementVM> getAdList(int banner, int adNum) {
		//通过banner获取adid
		
		List<AdvertisementVM> adList = new ArrayList<AdvertisementVM>();
		for(int i= 0;i<adNum;i++){
			int id = 0;
			AdvertisementVM ad =advertisementMapper.getAdList(id); 
			if(ad!=null){
				adList.add(ad);
			}
		}
		return adList;
		
	}

}
