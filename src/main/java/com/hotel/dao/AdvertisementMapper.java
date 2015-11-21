package com.hotel.dao;

import java.util.List;
import java.util.Map;

import com.hotel.model.Advertisement;
import com.hotel.viewmodel.AdvertisementVM;

@MyBatisRepository
public interface AdvertisementMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Advertisement record);

    int insertSelective(Advertisement record);

    Advertisement selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Advertisement record);

    int updateByPrimaryKey(Advertisement record);

	List<AdvertisementVM> getAdPageList(Map<String, Object> map);

	int getAdPageListCount(AdvertisementVM ad);

	AdvertisementVM selectById(Integer id);

	AdvertisementVM selectAdVMByPrimaryKey(Integer adId);
}