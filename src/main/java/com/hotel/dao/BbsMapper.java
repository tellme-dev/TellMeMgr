package com.hotel.dao;

import java.util.Map;

import com.hotel.common.ListResult;
import com.hotel.model.Bbs;

@MyBatisRepository
public interface BbsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Bbs record);

    int insertSelective(Bbs record);

    Bbs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Bbs record);

    int updateByPrimaryKey(Bbs record);

	ListResult<Bbs> selectByMap(Map<String, Object> map);
}