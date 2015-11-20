package com.hotel.app;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 广告，主题，推广的APP数据服务接口
 * @author charo
 *
 *
 */

@Controller
@RequestMapping("/app/ad")
public class AdController {
	/**
	 * 查询获取广告信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getAdList.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String getAdList(
			@RequestParam(value = "banner", required = false) int banner,
			@RequestParam(value = "adNum", required = false) int adNum,
			HttpServletRequest request)
	{
		return "";
	}

}
