package com.hotel.dao;

import java.util.List;

import com.hotel.model.Region;

@MyBatisRepository
public interface RegionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Region record);

    int insertSelective(Region record);

    Region selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Region record);

    int updateByPrimaryKey(Region record);
    
    List<Region> getAllRegion();
    
    List<Region> getProvinceRegion();
	
	List<Region> getCityRegion(Integer provinceId);
	
	List<Region> getAreaRegion(Integer cityId);

	List<Region> getNearAndHotRegionsByCustomer(int customerId,int defaultRegionNum);

	List<Region> getHistoricRegions(int customerId);
}