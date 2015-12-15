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
	private List<Region> historicRegions;
	/**
	 * 附近热门地域
	 */
	private List<Region> hotAndNearRegions;
	/**
	 * 所有的地域
	 */
	private List<Region> allRegions;
	public List<Region> getHistoricRegions() {
		return historicRegions;
	}
	public void setHistoricRegions(List<Region> historicRegions) {
		this.historicRegions = historicRegions;
	}
	public List<Region> getHotAndNearRegions() {
		return hotAndNearRegions;
	}
	public void setHotAndNearRegions(List<Region> hotAndNearRegions) {
		this.hotAndNearRegions = hotAndNearRegions;
	}
	public List<Region> getAllRegions() {
		return allRegions;
	}
	public void setAllRegions(List<Region> allRegions) {
		this.allRegions = allRegions;
	}
	

}
