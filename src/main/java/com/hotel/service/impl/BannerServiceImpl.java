package com.hotel.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.common.utils.GeneralUtil;
import com.hotel.dao.BannerMapper;
import com.hotel.service.BannerService;
import com.hotel.viewmodel.AdvertisementWebVM;
import com.hotel.viewmodel.BannerWebVM;

@Service("bannerService")
public class BannerServiceImpl implements BannerService {
	
	@Autowired
	private BannerMapper bannerMapper;

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

}
