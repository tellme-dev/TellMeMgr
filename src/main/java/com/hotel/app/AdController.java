package com.hotel.app;

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
import com.hotel.common.utils.Page;
import com.hotel.modelVM.AdParam;
import com.hotel.modelVM.AdvertisementVM;
import com.hotel.service.AdvertisementService;
import com.hotel.service.HotelService;
import com.hotel.service.ItemTagService;

/**
 * 广告，主题，推广的APP数据服务接口
 * @author charo
 *
 *
 */

@Controller
@RequestMapping("/app/ad")
public class AdController {
	
	@Autowired AdvertisementService adService;
	
	@Autowired HotelService hotelService;
	
	@Autowired ItemTagService itemTagService;
	/**
	 * 查询获取广告信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getAdList.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String getAdList(
			@RequestParam(value = "adParam", required = false) String adParam,
			HttpServletRequest request)
	{
		JSONObject jObj = JSONObject.fromObject(adParam);
		AdParam param = (AdParam) JSONObject.toBean(jObj,AdParam.class);
		int bannerId = 1;
		switch(param.getBanner()){
		case "top":
			bannerId = 1;
			break;
		case "down":
			bannerId = 2;
			break;
		default:
			bannerId = 1;
			break;
		}
		List<AdvertisementVM> list =adService.getAdList(bannerId, param.getAdNum());
		String msg = "";
		ListResult<AdvertisementVM> result = new ListResult<AdvertisementVM>(list,true,"");
		if(list==null||list.size()==0){
			msg="无广告信息";
			result.setIsSuccess(true);
			result.setMsg(msg);
		}else{
			result.setIsSuccess(true);
		}
		
		return result.toJson();
	}
	
	/**
	 * 发现页面广告列表查询(查询条件positionType=3)
	 * @author jun
	 * @param adParam
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "loadAdList.do", produces = "application/json;charset=UTF-8")
	public String loadAdList(
			@RequestParam(value = "adParam", required = false) String adParam,
			HttpServletRequest request){
		JSONObject jObj = JSONObject.fromObject(adParam);
		int pageNo = jObj.getInt("pageNo");
		int pageSize = jObj.getInt("pageSize");
		try{
			Page page = new Page();
			page.setPageNo(pageNo);
			page.setPageSize(pageSize);
			ListResult<AdvertisementVM> result = adService.loadAdList(page);
			return result.toJson();
		}catch(Exception e){
			ListResult<AdvertisementVM> result = new ListResult<AdvertisementVM>(null, false, "获取数据失败");
			return result.toJson();
		}
	}
	/**
	 * @author jun
	 * @param adParam
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "loadAdListByHotelId.do", produces = "application/json;charset=UTF-8")
	public String loadAdListByHotelId(
			@RequestParam(value = "adParam", required = true) String adParam,
			HttpServletRequest request)
	{
		JSONObject jObj = JSONObject.fromObject(adParam);
		int hotelId = 0;
		try{
			if(jObj.containsKey("hotelId")){
				hotelId = jObj.getInt("hotelId");
			}
		}catch(Exception e){
			e.printStackTrace();
			return new ListResult<AdvertisementVM>(null, false, "json解析异常").toJson();
		}
		
        try{
        	ListResult<AdvertisementVM> result = adService.loadAdListByHotelId(hotelId);
        	return result.toJson();
		}catch(Exception e){
			
			return new ListResult<AdvertisementVM>(null,false,"加载数据失败").toJson();
		}
	}
	@ResponseBody
	@RequestMapping(value = "loadAdById.do", produces = "application/json;charset=UTF-8")
	public String loadAdById(
			@RequestParam(value = "adParam", required = true) String adParam,
			HttpServletRequest request)
	{
		JSONObject jObj = JSONObject.fromObject(adParam);
		int adId = 0;
		try{
			if(jObj.containsKey("id")){
				adId = jObj.getInt("id");
			}
		}catch(Exception e){
			e.printStackTrace();
			return new Result<AdvertisementVM>(null, false, "json解析异常").toJson();
		}
		
        try{
        	AdvertisementVM ad = adService.loadAdById(adId);
        	return new Result<AdvertisementVM>(ad,true,"加载数据成功").toJson();
		}catch(Exception e){
			
			return new Result<AdvertisementVM>(null,false,"加载数据失败").toJson();
		}
	}

}
