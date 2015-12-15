package com.hotel.modelVM;


import java.util.List;

import com.hotel.model.Region;

/**
 * 返回app的定位数据信息
 * @author hzf
 *
 */
public class RegionData {
	/**
	 * 常住地域
	 */
	private List<RegionVM> historicRegions;
	/**
	 * 附近热门地域
	 */
	private List<RegionVM> hotAndNearRegions;
	/**
	 * 所有的地域
	 */
	private List<RegionVM> allRegions;
	public List<RegionVM> getHistoricRegions() {
		return historicRegions;
	}
	public void setHistoricRegions(List<RegionVM> historicRegions) {
		this.historicRegions = historicRegions;
	}
	public List<RegionVM> getHotAndNearRegions() {
		return hotAndNearRegions;
	}
	public void setHotAndNearRegions(List<RegionVM> hotAndNearRegions) {
		this.hotAndNearRegions = hotAndNearRegions;
	}
	public List<RegionVM> getAllRegions() {
		return allRegions;
	}
	public void setAllRegions(List<RegionVM> allRegions) {
		this.allRegions = allRegions;
	}
	
	
	

}
