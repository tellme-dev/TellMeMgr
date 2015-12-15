package com.hotel.app;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.common.ListResult;
import com.hotel.common.Result;
import com.hotel.model.Region;
import com.hotel.modelVM.RegionData;
import com.hotel.service.RegionService;

/**
 * 
 * @author hzf
 *
 */
@Controller
@RequestMapping("/app/region")
public class RegionController {
	private static final int DEFAULT_REGION_NUM = 5;
	
	@Autowired RegionService regionService;
	/**
	 * 获取定位页面的所有区域信息
	 * @param customerId
	 * @param request
	 * @return
	 * @author hzf
	 */
	@RequestMapping(value = "getRegionInfo", produces = "application/json;charset=UTF-8")
	public @ResponseBody String getRegionInfo(
			@RequestParam(value = "customerId", required = false) String customerId,
			HttpServletRequest request)
	{
		JSONObject object = JSONObject.fromObject(customerId);
		int id = 0;
		if(object.containsKey("customerId")){
			id = object.getInt("customerId");
		}
		if(id <= 0){
			return new ListResult<Region>(null,false,"请求参数无效").toJson();
		}
		RegionData result = new RegionData();
		List<Region> temp = this.getAllRegions();
		if(temp ==null||temp.size() ==0){
			return new Result<RegionData>(null,false,"获取城市信息失败").toJson();
		}
		result.setAllRegions(this.getAllRegions());
		
		result.setHistoricRegions(this.getHistoricRegions(id));
		
		result.setHotAndNearRegions(this.getNearAndHotRegionsByCustomer(id,DEFAULT_REGION_NUM));
		
		return new Result<RegionData>(result,true,"获取城市信息成功").toJson();
	}
	/**
	 * 获取常住区域
	 * @author hzf
	 * @param customerId
	 * @return
	 */
	private List<Region> getHistoricRegions(int customerId){
		List<Region> result = regionService.getHistoricRegions(customerId);
		return result;
	}
	/**
	 * 获取附件热门地域
	 * @author hzf
	 * @param customerId
	 * @return
	 */
	private List<Region> getNearAndHotRegionsByCustomer(int customerId,int defaultRegionNum){
		List<Region> result = regionService.getNearAndHotRegionsByCustomer(customerId, defaultRegionNum);
		return result;
	}
	/**
	 * 获取所有地域信息
	 * @author hzf
	 * @return
	 * 
	 */
	private List<Region> getAllRegions(){
		List<Region> result = regionService.getAllRegions();
		return result;
	}
}
