package com.hotel.dao;

import java.util.List;
import java.util.Map;

import com.hotel.model.AdDetail;
import com.hotel.modelVM.AdDetailVM;

@MyBatisRepository
public interface AdDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdDetail record);

    int insertSelective(AdDetail record);

    List<AdDetail> selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdDetail record);

    int updateByPrimaryKey(AdDetail record);

	List<AdDetail> selectByAdId(Integer adId);
	
	List<AdDetailVM> selectAdVMByAdId(Integer adId);

	void deleteByIds(Map<String, Object> map);
}
