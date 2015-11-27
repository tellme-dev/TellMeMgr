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
import com.hotel.model.Bbs;
import com.hotel.model.BbsCategory;
import com.hotel.modelVM.BbsVM;
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
			ListResult<BbsVM> result = bbsService.loadBbsListByCategoryId(categoryId);
			return result.toJson();
		}catch(Exception e){
			ListResult<BbsVM> result = new ListResult<BbsVM>(null, false, "获取数据失败");
			return result.toJson();
		}
	}
	/**
	 * 获取社区帖子正文内容ByPid
	 * @author jun
	 * @param bbsParam
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "loadBbsTree.do", produces = "application/json;charset=UTF-8")
	public String loadBbsByPid(
			@RequestParam(value = "bbsParam", required = false) String bbsParam,
			HttpServletRequest request){
		JSONObject jObj = JSONObject.fromObject(bbsParam);
		BbsVM bbs = (BbsVM) JSONObject.toBean(jObj,BbsVM.class);
		int pid = bbs.getId();
		try{
			List<BbsVM> bbsVM = bbsService.loadBbsTree(pid);
			ListResult<BbsVM> result = new ListResult<BbsVM>(bbsVM, false, "获取数据成功");
			return result.toJson();
		}catch(Exception e){
			ListResult<BbsVM> result = new ListResult<BbsVM>(null, false, "获取数据失败");
			return result.toJson();
		}
	}
	
	/**
	 * @author jun
	 * @param bbsParam
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveBbs.do", produces = "application/json;charset=UTF-8")
	public String saveBbs(
			@RequestParam(value = "bbsParam", required = false) String bbsParam,
			HttpServletRequest request){
		JSONObject jObj = JSONObject.fromObject(bbsParam);
		BbsVM bbs = (BbsVM) JSONObject.toBean(jObj,BbsVM.class);
		Result<BbsVM> result = new Result<BbsVM>();
		try{
			bbsService.saveBbs(bbs);
			result = new Result<BbsVM>(null, true, "保存成功");
			return result.toJson();
		}catch(Exception e){
			result = new Result<BbsVM>(null, false, "保存失败");
			return result.toJson();
		}
	}
	
}
