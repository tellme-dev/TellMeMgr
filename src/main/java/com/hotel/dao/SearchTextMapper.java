package com.hotel.dao;

import com.hotel.model.SearchText;

@MyBatisRepository
public interface SearchTextMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SearchText record);

    int insertSelective(SearchText record);

    SearchText selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SearchText record);

    int updateByPrimaryKey(SearchText record);
}