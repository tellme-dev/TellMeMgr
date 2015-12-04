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
import com.hotel.model.Hotel;
import com.hotel.modelVM.AdParam;
import com.hotel.modelVM.AdvertisementVM;
import com.hotel.modelVM.BbsVM;
import com.hotel.modelVM.HotelVM;
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
			msg="未获取到广告";
			result.setIsSuccess(false);
			result.setMsg(msg);
		}else{
			result.setIsSuccess(true);
		}
		
		return result.toJson();
	}
	
	/**
	 * 发现页面广告列表查询
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
	 * 对酒店打广告，根据广告对应的的hotelId查询各种信息
	 * @author jun
	 * @param hotelParam {"hotelId": }
	 * @param request
	 * @return
	 */
	/*@ResponseBody
	@RequestMapping(value = "getHotelInfo.do", produces = "application/json;charset=UTF-8")
	public String getHotelInfo(
			@RequestParam(value = "hotelParam", required = false) String hotelParam,
			HttpServletRequest request)
	{
		JSONObject jObj = JSONObject.fromObject(hotelParam);
		Hotel hotel = (Hotel) JSONObject.toBean(jObj,Hotel.class);
		Result<HotelVM> result = new Result<HotelVM>();
		int adId = hotel.getId();
        try{
        	HotelVM h = hotelService.getHotelVMByAdId(adId);
        	return result.toJson();
		}catch(Exception e){
			result = new Result<HotelVM>(null,false,"获取数据失败");
			return result.toJson();
		}
	}*/

}
