package com.hotel.dao;

import java.util.List;

import com.hotel.model.Menu;

@MyBatisRepository
public interface MenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

	List<Menu> getMenuList();
}