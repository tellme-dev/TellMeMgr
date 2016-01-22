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
import com.hotel.link.SocketRouter;
import com.hotel.modelVM.RcuVM;
import com.hotel.service.RcuService;


@Controller
@RequestMapping("/app/rcu")
public class RcuController {
	@Autowired RcuService rcuService;
	
	@RequestMapping(value = "getRoomRCUs.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String getRoomRCUs(
			@RequestParam(value = "roomInfo", required = false) String roomInfo,
			HttpServletRequest request)
	{
		JSONObject jObj = JSONObject.fromObject(roomInfo);
		int id = 0;
		if(jObj.containsKey("roomId")){
			id = jObj.getInt("roomId");
		}
		if(id<0){
			return new ListResult<>(null, false, "传入的房间Id不合法").toJson();
		}
		List<RcuVM> list = rcuService.getRCUsByRoomId(id);
		if(list==null ||list.size() ==0){
			return new ListResult<>(null, false, "未查询到任何RCU信息").toJson();
		}
		return new ListResult<>(list,true,"查询RCU成功").toJson();
	}
	
	@RequestMapping(value = "sendACOrder.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String sendACOrder(
			@RequestParam(value = "lampOrder", required = false) String lampOrder,
			HttpServletRequest request)
	{
		JSONObject jObj = JSONObject.fromObject(lampOrder);
		SocketRouter.execute(jObj);
		return "";
	}

}
