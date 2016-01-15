package com.hotel.dao;

import java.util.List;
import java.util.Map;

import com.hotel.model.BbsAttach;

@MyBatisRepository
public interface BbsAttachMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BbsAttach record);

    int insertSelective(BbsAttach record);

    BbsAttach selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BbsAttach record);

    int updateByPrimaryKey(BbsAttach record);
    
    //
    BbsAttach selectByBbsId(Integer bbsId);
    
    List<BbsAttach> selectBaByBbsId(Integer bbsId);

	int countByMap(Map<String, Object> map);

	List<BbsAttach> selectListByMap(Map<String, Object> map);
}