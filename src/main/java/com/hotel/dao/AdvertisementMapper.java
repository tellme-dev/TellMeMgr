package com.hotel.dao;

import java.util.List;

import com.hotel.model.Advertisement;

@MyBatisRepository
public interface AdvertisementMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Advertisement record);

    int insertSelective(Advertisement record);

    Advertisement selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Advertisement record);

    int updateByPrimaryKey(Advertisement record);

	List<Advertisement> getAdPageList(Advertisement ad);

	int getAdPageListCount(Advertisement ad);

	Advertisement selectById(Integer id);

	Advertisement selectAdVMByPrimaryKey(Integer adId);
}