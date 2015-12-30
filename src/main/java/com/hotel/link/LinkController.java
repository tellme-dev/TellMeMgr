package com.hotel.link;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hotel.common.Result;

@Controller
@RequestMapping("/link")
public class LinkController {

		@RequestMapping(value = "/send2Rcu.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
		public  String  send2Rcu(
				@RequestParam(value = "message", required = true) String message,
				HttpServletRequest request){
			
			Result <Boolean>result=new Result<Boolean>();
			
			
			JSONObject jo = JSONObject.fromObject(message);

			try {
				SocketRouter.client2Rcu(jo);
				result.setData(true);
			} catch (Exception e) {
				result.setData(true);
				result.setMsg("发送到RCU识别，命令格式不正确，可能缺少SID字段");
			}

			return result.toJson();
		}
}
