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
import com.hotel.model.Customer;
import com.hotel.modelVM.AdParam;
import com.hotel.modelVM.AdvertisementVM;
import com.hotel.service.AdService;

/**
 * 广告，主题，推广的APP数据服务接口
 * @author charo
 *
 *
 */

@Controller
@RequestMapping("/app/ad")
public class AdController {
	
	@Autowired AdService adService;
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

}
