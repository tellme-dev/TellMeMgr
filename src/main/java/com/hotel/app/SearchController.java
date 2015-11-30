package com.hotel.app;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.common.Result;
import com.hotel.model.SearchText;
import com.hotel.service.SearchService;

/**
 * 搜索
 * @author hzf
 *
 */
@Controller
@RequestMapping("/app/search")
public class SearchController {
	
	@Autowired SearchService searchService;
	/**
	 * 保存用户查询的内容
	 * @param searchText
	 * @param request
	 * @return
	 * @author hzf 
	 */
	@RequestMapping(value = "saveSearchText.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody  String saveSearchText(
			@RequestParam(value = "searchText", required = false) String searchText,
			HttpServletRequest request)
	{
		JSONObject jObj = JSONObject.fromObject(searchText);
		SearchText text = (SearchText) JSONObject.toBean(jObj,SearchText.class);
		text.setSearchTime(new Date());
		
		int temp = searchService.insertSearchText(text);
		if(temp == -1){
			return new Result<SearchText>(null,false,"插入搜索数据失败").toJson();
		}
		
		return new Result<SearchText>(null,true,"插入搜索数据成功").toJson();
	}
}
