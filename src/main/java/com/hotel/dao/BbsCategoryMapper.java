package com.hotel.dao;

import java.util.List;

import com.hotel.common.ListResult;
import com.hotel.model.BbsCategory;

@MyBatisRepository
public interface BbsCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BbsCategory record);

    int insertSelective(BbsCategory record);

    BbsCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BbsCategory record);

    int updateByPrimaryKey(BbsCategory record);

	List<BbsCategory> selectCategoryList();
}