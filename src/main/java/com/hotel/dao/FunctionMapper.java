package com.hotel.dao;

import java.util.List;

import com.hotel.model.Function;

@MyBatisRepository
public interface FunctionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Function record);

    int insertSelective(Function record);

    Function selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Function record);

    int updateByPrimaryKey(Function record);

	List<Function> getFunctionByParentId(Integer parentId);

	List<Function> getFunctionByParentUrl(String url);
}