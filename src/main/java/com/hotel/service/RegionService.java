package com.hotel.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hotel.model.Region;
/**
 * 
 * @author hzf
 *
 */
@Service
public interface RegionService {
	List<Region> getAllRegions();
	List<Region> getNearAndHotRegionsByCustomer(int customerId,int defaultRegionNum);
	List<Region> getHistoricRegions(int customerId);
}
