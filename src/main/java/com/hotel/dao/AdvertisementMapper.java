
package com.hotel.dao;

import com.hotel.model.Advertisement;
import com.hotel.modelVM.AdvertisementVM;

import java.util.List;

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
	
	AdvertisementVM getAdList(int id);
}
