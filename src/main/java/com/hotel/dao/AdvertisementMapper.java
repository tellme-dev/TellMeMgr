package com.hotel.dao;

import java.util.List;
import java.util.Map;

import com.hotel.model.Advertisement;
import com.hotel.modelVM.AdvertisementVM;
import com.hotel.viewmodel.AdvertisementWebVM;

@MyBatisRepository
public interface AdvertisementMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Advertisement record);

    int insertSelective(Advertisement record);

    Advertisement selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdvertisementWebVM record);

    int updateByPrimaryKey(Advertisement record);

	List<AdvertisementWebVM> getAdPageList(Map<String, Object> map);

	int getAdPageListCount(Map<String, Object> map);

	AdvertisementWebVM selectById(Integer id);
	
	AdvertisementVM getAdList(int id);

	AdvertisementWebVM selectAdVMByPrimaryKey(Integer adId);

	void updateByIds(Map<String, Object> map);
}
