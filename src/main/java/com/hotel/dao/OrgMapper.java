package com.hotel.dao;

import java.util.List;

import com.hotel.model.Org;

@MyBatisRepository
public interface OrgMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Org record);

    int insertSelective(Org record);

    Org selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Org record);

    int updateByPrimaryKey(Org record);

	List<Org> getOrganList(Integer pid);
}