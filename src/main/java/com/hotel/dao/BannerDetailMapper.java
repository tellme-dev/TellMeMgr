package com.hotel.dao;

import java.util.List;

import com.hotel.model.BannerDetail;

@MyBatisRepository
public interface BannerDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BannerDetail record);

    int insertSelective(BannerDetail record);

    BannerDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BannerDetail record);

    int updateByPrimaryKey(BannerDetail record);

	List<BannerDetail> getAdIdList(int banner);

	void deleteByBannerId(Integer id);
}