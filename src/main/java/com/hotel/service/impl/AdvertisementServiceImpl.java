package com.hotel.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotel.common.utils.GeneralUtil;
import com.hotel.dao.AdDetailMapper;
import com.hotel.dao.AdvertisementMapper;
import com.hotel.model.AdDetail;
import com.hotel.model.Advertisement;
import com.hotel.service.AdvertisementService;
import com.hotel.viewmodel.AdvertisementWebVM;

@Service("adService")
public class AdvertisementServiceImpl implements AdvertisementService {
	
	@Resource
	private AdvertisementMapper adMapper;
	@Resource
	private AdDetailMapper adDetailMapper;

	@Override
	public List<AdvertisementWebVM> getAdPageList(Map<String,Object> map) {
		// TODO Auto-generated method stub
		List<AdvertisementWebVM> adlist = adMapper.getAdPageList(map);
		/*转换时间格式*/
		List<AdvertisementWebVM> list = new ArrayList<AdvertisementWebVM>();
		for(int i=0;i<adlist.size();i++){
			AdvertisementWebVM a = adlist.get(i);
			Date createTime = a.getCreateTime();
			String cteatetime = GeneralUtil.dateToStrLong(createTime);
			a.setCreatetime(cteatetime);
			list.add(a);
		}
		return list;
	}

	@Override
	public int getAdPageListCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return adMapper.getAdPageListCount(map);
	}

	@Override
	public void saveorUpdateAd(AdvertisementWebVM ad) {
		// TODO Auto-generated method stub
		List<String> imageTexts = new ArrayList<String>();
		List<String> imageUrls = ad.getImageUrlList();
		List<Integer> adDetailIds = new ArrayList<Integer>();
		List<Integer> delAdDetailIds = new ArrayList<Integer>();
		//取出imageTexts
		if(ad.getImageText() != null&&!"".equals(ad.getImageText())){
			String[] arr = ad.getImageText().split(",");
			imageTexts = Arrays.asList(arr);
		}
		/*if(ad.getImageUrlList() != null){
			String[] arr = ad.getImageText().split(",");
			imageTexts = Arrays.asList(arr);
			//List imageUrls = Arrays.asList(arr);
		}*/
		//取出adDetailIds编辑时用
		if(ad.getAdDetailIds() != null&&!"".equals(ad.getAdDetailIds())){
			String[] str = ad.getAdDetailIds().split(",");
			Integer array[] = new Integer[str.length];  
			for(int i=0;i<str.length;i++){  
			    array[i]=Integer.parseInt(str[i]);
			}
			adDetailIds = Arrays.asList(array);
		}
		//取出删除的照片id delAdDetailIds编辑时用
		if(ad.getDelAdDetailIds() != null&&!"".equals(ad.getDelAdDetailIds())){
			String[] str = ad.getDelAdDetailIds().split(",");
			Integer array[] = new Integer[str.length];  
			for(int i=0;i<str.length;i++){  
			    array[i]=Integer.parseInt(str[i]);
			}
			delAdDetailIds = Arrays.asList(array);
		}
		
		AdDetail adDetail = new AdDetail();
		if(ad.getId()>0){
			adMapper.updateByPrimaryKeySelective(ad);
			//adDetail表
			int i=0;
			//编辑
			for(i=0;i<adDetailIds.size();i++){
				Integer id = adDetailIds.get(i);
				String text = imageTexts.get(i);
				
				adDetail.setAdId(ad.getId());
				adDetail.setId(id);
				adDetail.setText(text);
				adDetailMapper.updateByPrimaryKeySelective(adDetail);
			}
			//新插入
			int k = i;
			for(int j=0;j<imageTexts.size()-i;j++,k++){
				String text = imageTexts.get(k);
				String imageUrl = imageUrls.get(j);
				
				adDetail.setAdId(ad.getId());
				adDetail.setId(0);
				adDetail.setImageUrl(imageUrl);
				adDetail.setText(text);
				adDetailMapper.insert(adDetail);
			}
			//删除的图片
			if(delAdDetailIds.size() != 0){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("ids", delAdDetailIds);
				adDetailMapper.deleteByIds(map);
			}
				
		}else{
			ad.setCreateTime(new Date());
			ad.setIsUsed(true);
			adMapper.insert(ad);
			//adDetail表插入
			for(int i=0;i<imageUrls.size();i++){
				String text = imageTexts.get(i);
				String imageUrl = imageUrls.get(i);
				
				adDetail.setAdId(ad.getId());
				adDetail.setId(0);
				adDetail.setImageUrl(imageUrl);
				adDetail.setText(text);
				adDetailMapper.insert(adDetail);
			}
		}
	}

	@Override
	public AdvertisementWebVM getAdById(Integer adId) {
		// TODO Auto-generated method stub
		AdvertisementWebVM ad = adMapper.selectAdVMByPrimaryKey(adId);
		List<AdDetail> adDetail = adDetailMapper.selectByAdId(adId);
		//ad.setAdDetail(adDetail);
		ad.setAdDetailList(adDetail);
		return ad;
	}

	@Override
	public void updateUserByIds(String adIds) {
		// TODO Auto-generated method stub
		//adIds格式"1,2,3"
		String[] str = adIds.split(",");
		Integer array[] = new Integer[str.length];  
		for(int i=0;i<str.length;i++){  
		    array[i]=Integer.parseInt(str[i]);
		}
		List<Integer> ids = Arrays.asList(array);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ids", ids);
		map.put("isUsed", false);
		adMapper.updateByIds(map);
	}

}
