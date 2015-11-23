package com.hotel.dao;

import com.hotel.model.BannerDetail;

@MyBatisRepository
public interface BannerDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BannerDetail record);

    int insertSelective(BannerDetail record);

    BannerDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BannerDetail record);

    int updateByPrimaryKey(BannerDetail record);
}