package com.hotel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.dao.RegionMapper;
import com.hotel.model.Region;
import com.hotel.service.RegionService;

/**
 * 
 * @author hzf
 *
 */
@Service
public class RegionServiceImpl implements RegionService {
	@Autowired RegionMapper regionMapper;
	
	@Override
	public List<Region> getAllRegions() {
		// TODO Auto-generated method stub
		return regionMapper.getAllRegion();
	}

	@Override
	public List<Region> getNearAndHotRegionsByCustomer(int customerId,int defaultRegionNum) {
		// TODO Auto-generated method stub
		return regionMapper.getNearAndHotRegionsByCustomer(customerId,defaultRegionNum);
	}

	@Override
	public List<Region> getHistoricRegions(int customerId) {
		// TODO Auto-generated method stub
		return regionMapper.getHistoricRegions(customerId);
	}

}
