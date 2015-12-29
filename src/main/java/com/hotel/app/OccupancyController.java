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
import com.hotel.model.RoomCheck;
import com.hotel.modelVM.HotelVM;
import com.hotel.service.HotelService;
import com.hotel.service.RoomCheckService;

@Controller
@RequestMapping("/app/occupancy")
public class OccupancyController {
	@Autowired RoomCheckService roomCheckService;
	@Autowired HotelService hotelService;
	
	/**
	 * 查询正在入住的酒店信息 或者附近酒店信息
	 * @author jun
	 * @param param
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "loadHotelByOccupancyInfo.do", produces = "application/json;charset=UTF-8")
	public String loadHotelByOccupancyInfo(
			@RequestParam(value = "param", required = true) String param,
			HttpServletRequest request)
	{
		JSONObject jObj = JSONObject.fromObject(param);
		int customerId = 0;
		int regionId = 0;
		try{
			if(jObj.containsKey("customerId")){
				customerId = jObj.getInt("customerId");
			}
			if(jObj.containsKey("regionId")){
				regionId = jObj.getInt("regionId");
			}
		}catch(Exception e){
			return new Result<RoomCheck>(null,false,"json解析异常").toJson();
		}
		try{
			List<RoomCheck> rclist = roomCheckService.loadRoomCheckListByCustomerId(customerId);
			//有入住记录信息
			if(rclist.size() != 0){
				for(RoomCheck rc: rclist){
					//且是正在入住中，返回入住的酒店信息
					if(rc.getCheckoutTime() == null){
						HotelVM hotel = hotelService.getHotelVMById(rc.getHotelId());
						hotel.setRoomId(rc.getRoomId());
						return new Result<HotelVM>(hotel,true,"获取入住酒店信息成功").toJson();
					}
				}
				//遍历完后,没有发现入住中的，即全是历史入住信息，返回附近酒店列表
				return getHotelListByRegionId(regionId);
			}else{//无入住信息，返回附近酒店列表
				return getHotelListByRegionId(regionId);
			}
		}catch(Exception e){
			return new Result<HotelVM>(null,false,"加载数据失败").toJson();
		}
	}
	/**
	 * 获取附近酒店列表
	 * @param regionId
	 * @return
	 */
	public String getHotelListByRegionId(int regionId){
		ListResult<HotelVM> result = hotelService.getHotelListByRegionId(regionId);
		return result.toJson();
	}
}
