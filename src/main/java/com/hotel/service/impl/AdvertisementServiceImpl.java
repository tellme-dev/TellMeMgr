package com.hotel.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotel.common.utils.GeneralUtil;
import com.hotel.dao.AdDetailMapper;
import com.hotel.dao.AdvertisementMapper;
import com.hotel.model.AdDetail;
import com.hotel.model.Advertisement;
import com.hotel.service.AdvertisementService;

@Service("adService")
public class AdvertisementServiceImpl implements AdvertisementService {
	
	@Resource
	private AdvertisementMapper adMapper;
	@Resource
	private AdDetailMapper adDetailMapper;

	@Override
	public List<Advertisement> getAdPageList(Advertisement ad) {
		// TODO Auto-generated method stub
		List<Advertisement> adlist = adMapper.getAdPageList(ad);
		/*转换时间格式*/
		List<Advertisement> list = new ArrayList<Advertisement>();
		for(int i=0;i<adlist.size();i++){
			Advertisement a = adlist.get(i);
			Date createTime = a.getCreateTime();
			String cteatetime = GeneralUtil.dateToStrLong(createTime);
			a.setCreatetime(cteatetime);
			list.add(a);
		}
		return list;
	}

	@Override
	public int getAdPageListCount(Advertisement ad) {
		// TODO Auto-generated method stub
		return adMapper.getAdPageListCount(ad);
	}

	@Override
	public void saveorUpdateAd(Advertisement ad) {
		// TODO Auto-generated method stub
		String[] arr = ad.getImageText().split(",");
		List imageTexts = Arrays.asList(arr);
		//List imageUrls = Arrays.asList(arr);
		AdDetail adDetail = new AdDetail();
		if(ad.getId()>0){
			adMapper.updateByPrimaryKeySelective(ad);
			for(int i=0;i<imageTexts.size();i++){
			}
			//adDetailMapper.updateByPrimaryKeySelective(ad);
			
		}else{
			ad.setCreateTime(new Date());
			ad.setTimeStamp(new Date());
			ad.setIsUsed(true);
			adMapper.insert(ad);
			for(int i=0;i<imageTexts.size();i++){
				String text = (String) imageTexts.get(i);
				//String imageUrl = (String) imageUrls.get(i);
				
				adDetail.setAdId(ad.getId());
				adDetail.setId(0);
				//adDetail.setImageUrl(imageTexts);
				adDetail.setText(text);
				adDetailMapper.insert(adDetail);
			}
		}
	}

	@Override
	public Advertisement getAdById(Integer adId) {
		// TODO Auto-generated method stub
		Advertisement ad = adMapper.selectByPrimaryKey(adId);
		List<AdDetail> adDetail = adDetailMapper.selectByAdId(adId);
		//ad.setAdDetail(adDetail);
		ad.setAdDetailList(adDetail);
		return ad;
	}

}
