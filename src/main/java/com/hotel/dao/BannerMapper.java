package com.hotel.dao;

import java.util.List;
import java.util.Map;

import com.hotel.model.Banner;
import com.hotel.viewmodel.BannerWebVM;

@MyBatisRepository
public interface BannerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Banner record);

    int insertSelective(Banner record);

    Banner selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Banner record);

    int updateByPrimaryKey(Banner record);

	List<BannerWebVM> selectByMap(Map<String, Object> map);

	int countByMap(Map<String, Object> map);

	BannerWebVM selectObjByMap(Map<String, Object> map);
}