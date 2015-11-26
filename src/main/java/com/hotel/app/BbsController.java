package com.hotel.app;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.common.ListResult;
import com.hotel.model.Bbs;
import com.hotel.model.BbsCategory;
import com.hotel.modelVM.AdParam;
import com.hotel.service.BbsService;

@Controller
@RequestMapping("/app/bbs")
public class BbsController {
	
	@Autowired BbsService bbsService;
	/**
	 * 获取社区分类标签
	 * @author jun
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "loadBbsCategoryList.do", produces = "application/json;charset=UTF-8")
	public String loadBbsCategoryList(
			HttpServletRequest request){
		try{
			ListResult<BbsCategory> result = bbsService.loadBbsCategoryList();
			return result.toJson();
		}catch(Exception e){
			ListResult<BbsCategory> result = new ListResult<BbsCategory>(null, false, "获取数据失败");
			return result.toJson();
		}
	}
	
	/**
	 * 获取社区帖子列表
	 * @author jun
	 * @param bbsParam:categoryId
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "loadBbsList.do", produces = "application/json;charset=UTF-8")
	public String loadBbsList(
			@RequestParam(value = "bbsParam", required = false) String bbsParam,
			HttpServletRequest request){
		JSONObject jObj = JSONObject.fromObject(bbsParam);
		Bbs bbs = (Bbs) JSONObject.toBean(jObj,Bbs.class);
		int categoryId = bbs.getCategoryId();
		try{
			ListResult<Bbs> result = bbsService.loadBbsListByCategoryId(categoryId);
			return result.toJson();
		}catch(Exception e){
			ListResult<BbsCategory> result = new ListResult<BbsCategory>(null, false, "获取数据失败");
			return result.toJson();
		}
	}

}
