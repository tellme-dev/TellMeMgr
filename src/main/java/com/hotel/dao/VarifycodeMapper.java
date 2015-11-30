package com.hotel.dao;

import com.hotel.model.Varifycode;

public interface VarifycodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Varifycode record);

    int insertSelective(Varifycode record);

    Varifycode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Varifycode record);

    int updateByPrimaryKey(Varifycode record);
}