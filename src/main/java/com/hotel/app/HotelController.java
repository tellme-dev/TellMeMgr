package com.hotel.app;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.common.Result;
import com.hotel.model.Item;
import com.hotel.service.ItemService;

/**
 * 酒店数据访问接口
 * @author charo
 *
 */
@Controller
@RequestMapping("/app/hotel")
public class HotelController {
	@Autowired ItemService itemService;
	
	@ResponseBody
	@RequestMapping(value = "", produces = "application/json;charset=UTF-8")
	public String load(
			@RequestParam(value = "ItemParam", required = false) String ItemParam,
			HttpServletRequest request){
		Result<Item> result = new Result<Item>();
		try{
			return result.toJson();
		}catch(Exception e){
			return result.toJson();
		}
	}
	
}
