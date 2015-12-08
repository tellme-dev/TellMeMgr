package com.hotel.app;

import java.util.Date;
import java.io.File;
import java.lang.Math;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hotel.common.ListResult;
import com.hotel.common.Result;
import com.hotel.common.utils.FileUtil;
import com.hotel.common.utils.Page;
import com.hotel.model.Bbs;
import com.hotel.model.BbsCategory;
import com.hotel.model.SearchText;
import com.hotel.modelVM.BbsVM;
import com.hotel.modelVM.HotelParam;
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
	 * @param bbsParam:categoryId,pageNo
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "loadBbsList.do", produces = "application/json;charset=UTF-8")
	public String loadBbsList(
			@RequestParam(value = "bbsParam", required = false) String bbsParam,
			HttpServletRequest request){
		JSONObject jObj = JSONObject.fromObject(bbsParam);
		//Bbs bbs = (Bbs) JSONObject.toBean(jObj,Bbs.class);
		int type = jObj.getInt("type");// 最新活动： type=1 ，热门话题：type=2 ,吐槽专区：type=3，达人推荐：type=4
		int pageNo = jObj.getInt("pageNo");
		int pageSize = jObj.getInt("pageSize");
		try{
			Page page = new Page();
			page.setPageNo(pageNo);
			page.setPageSize(pageSize);
			ListResult<BbsVM> result = bbsService.loadBbsListByType(page,type);
			return result.toJson();
		}catch(Exception e){
			ListResult<BbsVM> result = new ListResult<BbsVM>(null, false, "获取数据失败");
			return result.toJson();
		}
	}
	
	/**
	 * 获取单个帖子信息
	 * @author jun
	 * @param bbsParam
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "loadBbs.do", produces = "application/json;charset=UTF-8")
	public String loadBbs(
			@RequestParam(value = "bbsParam", required = false) String bbsParam,
			HttpServletRequest request){
		JSONObject jObj = JSONObject.fromObject(bbsParam);
		Bbs bbs = (Bbs) JSONObject.toBean(jObj,Bbs.class);
		int id = bbs.getId();
		Result<BbsVM> result = new Result<BbsVM>();
		try{
			BbsVM b = bbsService.loadBbsById(id);
			result = new Result<BbsVM>(b,true,"获取数据成功");
			return result.toJson();
		}catch(Exception e){
			result = new Result<BbsVM>(null,true,"获取数据失败");
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
	@RequestMapping(value = "loadBbsChildren.do", produces = "application/json;charset=UTF-8")
	public String loadBbsChildren(
			@RequestParam(value = "bbsParam", required = false) String bbsParam,
			HttpServletRequest request){
		JSONObject jObj = JSONObject.fromObject(bbsParam);
		//BbsVM bbs = (BbsVM) JSONObject.toBean(jObj,BbsVM.class);
		int pid = jObj.getInt("id");
		int pageNo = jObj.getInt("pageNo");
		int pageSize = jObj.getInt("pageSize");
		try{
			Page page = new Page();
			page.setPageNo(pageNo);
			page.setPageSize(pageSize);
			ListResult<BbsVM> result = bbsService.loadBbsChildren(page,pid);
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
	
	/**
	 * 发帖上传图片
	 * @author jun
	 * @param bbsPhoto
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "uploadPhoto.do", produces = "application/json;charset=UTF-8")  
    public @ResponseBody String upload(
    		@RequestParam MultipartFile bbsPhoto,
    		HttpServletRequest request) { 
		Result<Bbs> result = null;
        try { 
        	//String path = request.getSession().getServletContext().getRealPath("washPhoto"); 
        	String path = request.getSession().getServletContext().getRealPath("/")+"app/bbs/temp";
//        	String path = getClass().getResource("/").getFile().toString();
//			path = path.substring(0, (path.length() - 16))+"washPhoto";
        	String fileName = bbsPhoto.getOriginalFilename()+".jpg";//接收到的Name是没有格式的
        	
        	File uploadFile = new File(path,fileName);
        	if(!uploadFile.exists()){  
        		uploadFile.mkdirs();  
            }  
        	bbsPhoto.transferTo(uploadFile); //保存
        	result = new Result<Bbs>(null, true, "上传成功");
        	return result.toJson();
        }catch(Exception e){
        	result = new Result<Bbs>(null, false, "上传失败");
        	return result.toJson();
        }
	}
	
	@RequestMapping(value = "fullTextSearchOfBbs.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody  String fullTextSearchOfBbs(
			@RequestParam(value = "searchData", required = false) String searchData,
			HttpServletRequest request)
	{
		//先保存查询内容，然后进行全文查询
		JSONObject jObj = JSONObject.fromObject(searchData);
		SearchText text = (SearchText) JSONObject.toBean(jObj,SearchText.class);
		text.setSearchTime(new Date());
		//全文查询:查询酒店
		List<BbsVM> list = bbsService.fullTextSearchOfBbs(text.getText());
		if(list ==null||list.size() ==0){
			return new ListResult<BbsVM>(null,false,"全文搜索帖子失败").toJson();
		}
		return new ListResult<BbsVM>(list,true,"获取推荐帖子成功").toJson();
	}
	
	
	@RequestMapping(value = "test.do", produces = "application/json;charset=UTF-8")
	@ResponseBody 
	public String test(
			HttpServletRequest request)
	{
		Result<Bbs> result = null;
		try{
			String path = request.getSession().getServletContext().getRealPath("/")+"app/bbs/temp";
			String toPath = request.getSession().getServletContext().getRealPath("/")+"app/bbs/bbs-id";
			FileUtil.copyToOtherPath2(path, toPath,1);
			result = new Result<Bbs>(null, true, "yes");
			return result.toJson();
		}catch(Exception e){
			result = new Result<Bbs>(null, true, "no");
			return result.toJson();
		}
	}
}
