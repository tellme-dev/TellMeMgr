package com.hotel.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.common.ListResult;
import com.hotel.common.Result;
import com.hotel.common.utils.Page;
import com.hotel.common.utils.PathConfig;
import com.hotel.model.Bbs;
import com.hotel.model.CustomerCollection;
import com.hotel.modelVM.AdParam;
import com.hotel.modelVM.AdvertisementVM;
import com.hotel.modelVM.BbsVM;
import com.hotel.service.AdvertisementService;
import com.hotel.service.BbsService;
import com.hotel.service.CustomerCollectionService;
import com.hotel.service.HotelService;
import com.hotel.service.ItemTagService;

/**
 * 广告，主题，推广的APP数据服务接口
 * @author charo
 *
 *
 */

@Controller
@RequestMapping("/app/ad")
public class AdController {
	
	@Autowired AdvertisementService adService;
	
	@Autowired HotelService hotelService;
	
	@Autowired ItemTagService itemTagService;
	
	@Autowired BbsService bbsService;
	
	@Autowired CustomerCollectionService customerCollectionService;
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
			msg="无广告信息";
			result.setIsSuccess(true);
			result.setMsg(msg);
		}else{
			result.setIsSuccess(true);
		}
		
		return result.toJson();
	}
	
	/**
	 * 发现页面广告列表查询(查询条件positionType=3)
	 * @author jun
	 * @param adParam
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "loadAdList.do", produces = "application/json;charset=UTF-8")
	public String loadAdList(
			@RequestParam(value = "adParam", required = false) String adParam,
			HttpServletRequest request){
		JSONObject jObj = JSONObject.fromObject(adParam);
		int pageNo = jObj.getInt("pageNo");
		int pageSize = jObj.getInt("pageSize");
		try{
			Page page = new Page();
			page.setPageNo(pageNo);
			page.setPageSize(pageSize);
			ListResult<AdvertisementVM> result = adService.loadAdList(page);
			return result.toJson();
		}catch(Exception e){
			ListResult<AdvertisementVM> result = new ListResult<AdvertisementVM>(null, false, "获取数据失败");
			return result.toJson();
		}
	}
	/**
	 * @author jun
	 * @param adParam
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "loadAdListByHotelId.do", produces = "application/json;charset=UTF-8")
	public String loadAdListByHotelId(
			@RequestParam(value = "adParam", required = true) String adParam,
			HttpServletRequest request)
	{
		JSONObject jObj = JSONObject.fromObject(adParam);
		int hotelId = 0;
		try{
			if(jObj.containsKey("hotelId")){
				hotelId = jObj.getInt("hotelId");
			}
		}catch(Exception e){
			e.printStackTrace();
			return new ListResult<AdvertisementVM>(null, false, "json解析异常").toJson();
		}
		
        try{
        	ListResult<AdvertisementVM> result = adService.loadAdListByHotelId(hotelId);
        	return result.toJson();
		}catch(Exception e){
			
			return new ListResult<AdvertisementVM>(null,false,"加载数据失败").toJson();
		}
	}
	/**
	 * @jun
	 * 加载单个广告信息
	 */
	@ResponseBody
	@RequestMapping(value = "loadAdById.do", produces = "application/json;charset=UTF-8")
	public String loadAdById(
			@RequestParam(value = "adParam", required = true) String adParam,
			HttpServletRequest request)
	{
		JSONObject jObj = JSONObject.fromObject(adParam);
		int adId = 0;
		int customerId = -1;
		try{
			if(jObj.containsKey("id")){
				adId = jObj.getInt("id");
			}
			if(jObj.containsKey("customerId")){
				customerId = jObj.getInt("customerId");
			}
		}catch(Exception e){
			e.printStackTrace();
			return new Result<AdvertisementVM>(null, false, "json解析异常").toJson();
		}
		
        try{
        	AdvertisementVM ad = adService.loadAdById(adId);
        	/*添加用户是否已点赞*/
        	Bbs bbs = new Bbs();
        	bbs.setCustomerId(customerId);
        	bbs.setBbsType(3);
        	bbs.setTargetType(2);
        	bbs.setTargetId(ad.getId());
        	int count = bbsService.countByBbs(bbs);
        	if(count>0){//已赞
        		ad.setIsAgreed(true);
        	}else{
        		ad.setIsAgreed(false);
        	}
        	/*添加用户是否已收藏*/
			CustomerCollection cc = new CustomerCollection();
			cc.setCustomerId(customerId);
			cc.setCollectionType(2);
			cc.setTargetId(ad.getId());
			CustomerCollection col = customerCollectionService.selectByCustomerCollection(cc);
			if(col!=null){
				ad.setIsCollected(true);
			}else{
				ad.setIsCollected(false);
			}
        	return new Result<AdvertisementVM>(ad,true,"加载数据成功").toJson();
		}catch(Exception e){
			
			return new Result<AdvertisementVM>(null,false,"加载数据失败").toJson();
		}
	}
	
	/**
	 * 广告评论、回复
	 * @author jun
	 * @param adParam={parentId,targetId,text,customerId}
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveAdComment.do", produces = "application/json;charset=UTF-8")
	public String saveAdComment(
			@RequestParam(value = "adParam", required = true) String adParam,
			HttpServletRequest request)
	{
		JSONObject jObj = JSONObject.fromObject(adParam);
		Bbs bbs = (Bbs) JSONObject.toBean(jObj,Bbs.class);
		if(bbs.getParentId()==null||"".equals(bbs.getParentId())){
			return new Result<Bbs>(null,false,"传入后台参数错误").toJson();
		}
		try{
			adService.saveAdComment(bbs);
			return new Result<Bbs>(null,true,"保存成功").toJson();
		}catch(Exception e){
			return new Result<Bbs>(null,false,"保存失败").toJson();
		}
	}
	/**
	 * 加载广告评论
	 * @author jun
	 * @param adParam
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "loadAdComment.do", produces = "application/json;charset=UTF-8")
	public String loadAdComment(
			@RequestParam(value = "adParam", required = true) String adParam,
			HttpServletRequest request)
	{
		JSONObject jObj = JSONObject.fromObject(adParam);
		int targetType = -1;
		int targetId = -1;
		try{
			if(jObj.containsKey("targetId")){
				targetId = jObj.getInt("targetId");
			}
			if(jObj.containsKey("targetType")){
				targetType = jObj.getInt("targetType");
			}
		}catch(Exception e){
			e.printStackTrace();
			return new Result<AdvertisementVM>(null, false, "json解析异常").toJson();
		}
		if(targetType == -1||targetId == -1){
			return new Result<AdvertisementVM>(null, false, "传入后台参数错误").toJson();
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("targetType", targetType);
		map.put("targetId", targetId);
		map.put("bbsType", 2);//表示评论
		map.put("deleteUserId", null);//过滤未被删除的
		ListResult<BbsVM> result = new ListResult<BbsVM>();
		try{
			if(targetType == 2){
				result = bbsService.loadAdComment(map);
			}
			return result.toJson();
		}catch(Exception e){
			return new ListResult<BbsVM>(null,false,"加载数据失败").toJson();
		}
	}
}
