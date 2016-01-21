package com.hotel.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.common.ListResult;
import com.hotel.common.Result;
import com.hotel.common.utils.PinYinUtil;
import com.hotel.model.Region;
import com.hotel.modelVM.RegionData;
import com.hotel.modelVM.RegionVM;
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
		String regionCode = "";
		if(object.containsKey("customerId")){
			id = object.getInt("customerId");
		}
		if(object.containsKey("regionCode")){
			regionCode = object.getString("regionCode");
		}
		RegionData result = new RegionData();
		List<RegionVM> temp = this.getAllRegions();
		if(temp ==null||temp.size() ==0){
			return new Result<RegionData>(null,false,"获取城市信息失败").toJson();
		}
		result.setAllRegions(this.getAllRegions());
		
		result.setHistoricRegions(this.getHistoricRegions(id));
		
		result.setHotAndNearRegions(this.getNearAndHotRegions(regionCode,DEFAULT_REGION_NUM));
		
		return new Result<RegionData>(result,true,"获取城市信息成功").toJson();
	}
	/**
	 * 获取常住区域
	 * @author hzf
	 * @param customerId
	 * @return
	 */
	private List<RegionVM> getHistoricRegions(int customerId){
		List<Region> temp = regionService.getHistoricRegions(customerId);
		List<RegionVM> result = new ArrayList<RegionVM>();
		if(temp ==null||temp.size() ==0){
			return null;
		}
		for(int i = 0;i<temp.size();i++){
			result.add(this.getPinyin(temp.get(i)));
		}
		
		return result ==null?null:this.sort(result);
	}
	/**
	 * 获取附件热门地域
	 * @author hzf
	 * @param customerId
	 * @return
	 */
	private List<RegionVM> getNearAndHotRegions(String regionCode,int defaultRegionNum){
		List<Region> temp = regionService.getNearAndHotRegions(regionCode, defaultRegionNum);
		List<RegionVM> result = new ArrayList<RegionVM>();
		if(temp ==null||temp.size() ==0){
			return null;
		}
		for(int i = 0;i<temp.size();i++){
			result.add(this.getPinyin(temp.get(i)));
		}
		return result ==null?null:this.sort(result);
	}
	/**
	 * 获取所有地域信息
	 * @author hzf
	 * @return
	 * 
	 */
	private List<RegionVM> getAllRegions(){
		List<Region> temp = regionService.getAllRegions();
		List<RegionVM> result = new ArrayList<RegionVM>();
		if(temp ==null||temp.size() ==0){
			return null;
		}
		for(int i = 0;i<temp.size();i++){
			result.add(this.getPinyin(temp.get(i)));
		}
		return result ==null?null:this.sort(result);
	}
	/**
	 * 将区域对象转换成带有中文拼音和拼音首字母的对象
	 * @author hzf
	 * @param r
	 * @return
	 */
	private RegionVM getPinyin(Region r){
		RegionVM result = new RegionVM();
		result.setId(r.getId());
		result.setLevel(r.getLevel());
		result.setName(r.getName());
		result.setParentId(r.getParentId());
		result.setPath(r.getPath());
		
		result.setFirstChar(PinYinUtil.converterToFirstSpell(r.getName()));
		result.setPinyin(PinYinUtil.converterToSpell(r.getName()));
		
		return result;
	}
	/**
	 * 按照首字母排序
	 * @author hzf
	 * @param region
	 * @return
	 */
	private List<RegionVM> sort(List<RegionVM> region){
		List<RegionVM> result = region;
		
		for(int i = 0;i<result.size();i++){
			String temp = result.get(i).getFirstChar();
			int index = i;
			for(int j = i+1;j<result.size();j++){
				if(temp.compareTo(result.get(j).getFirstChar())>0){
					index = j;
					temp = result.get(j).getFirstChar();
				}
			}
			if(index !=i){
				RegionVM re = result.get(i);
				result.set(i, result.get(index));
				result.set(index, re);
			}
		}
		return result;
	}
	@Test
	public void test(){
		String str1 = "b";
		String str2 = "b";
		if(str1.compareTo(str2)>0){
			System.out.println("大于");
		}else{
			System.out.println("xiao于");
		}
	}
}
