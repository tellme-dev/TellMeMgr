package com.hotel.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.common.utils.GeneralUtil;
import com.hotel.dao.BannerDetailMapper;
import com.hotel.dao.BannerMapper;
import com.hotel.model.BannerDetail;
import com.hotel.service.BannerService;
import com.hotel.viewmodel.BannerWebVM;

@Service("bannerService")
public class BannerServiceImpl implements BannerService {
	
	@Autowired
	private BannerMapper bannerMapper;
	
	@Autowired
	private BannerDetailMapper badMapper;

	@Override
	public List<BannerWebVM> getBannerPageList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<BannerWebVM> ls = bannerMapper.selectByMap(map);
		/*转换时间格式*/
		List<BannerWebVM> list = new ArrayList<BannerWebVM>();
		for(int i=0;i<ls.size();i++){
			BannerWebVM b = ls.get(i);
			Date createTime = b.getCreateTime();
			String cteatetime = GeneralUtil.dateToStrLong(createTime);
			b.setCreatetime(cteatetime);
			list.add(b);
		}
		return list;
	}

	@Override
	public int getBannerPageListCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return bannerMapper.countByMap(map);
	}

	@Override
	public BannerWebVM loadBannerById(Integer id) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		return bannerMapper.selectObjByMap(map);
	}

	@Override
	public void saveorUpdateAd(BannerWebVM banner) {
		// TODO Auto-generated method stub
		//adIds格式"1,2,3"
		List<Integer> adIds = new ArrayList<Integer>();
		if(banner.getAdIds() != null&&!"".equals(banner.getAdIds())){
			String[] str = banner.getAdIds().split(",");
			Integer array[] = new Integer[str.length];  
			for(int i=0;i<str.length;i++){  
			    array[i]=Integer.parseInt(str[i]);
			}
			adIds = Arrays.asList(array);
		}
		if(banner.getId() == 0){//新增
			banner.setCreateTime(new Date());
			bannerMapper.insert(banner);
			int i = 1;
			for(int adId: adIds){
				BannerDetail bad = new BannerDetail();
				bad.setId(0);
				bad.setAdId(adId);
				bad.setBannerId(banner.getId());
				bad.setSort(i++);
				badMapper.insert(bad);
			}
		}else{
			bannerMapper.updateByPrimaryKeySelective(banner);
			badMapper.deleteByBannerId(banner.getId());
			int i = 1;
			for(int adId: adIds){
				BannerDetail bad = new BannerDetail();
				bad.setId(0);
				bad.setAdId(adId);
				bad.setBannerId(banner.getId());
				bad.setSort(i++);
				badMapper.insert(bad);
			}
		}
		
	}

}
