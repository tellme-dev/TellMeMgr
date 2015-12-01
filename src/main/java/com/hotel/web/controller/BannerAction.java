package com.hotel.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.hotel.common.JsonResult;
import com.hotel.common.utils.Constants;
import com.hotel.common.utils.FileUtil;
import com.hotel.common.utils.Page;
import com.hotel.model.Function;
import com.hotel.model.User;
import com.hotel.modelVM.BbsVM;
import com.hotel.service.AdvertisementService;
import com.hotel.service.BannerService;
import com.hotel.service.FunctionService;
import com.hotel.viewmodel.AdvertisementWebVM;
import com.hotel.viewmodel.BannerWebVM;

@Scope("prototype")
@Controller
@RequestMapping("/web/banner")
public class BannerAction {
	
	@Autowired FunctionService functionService;
	
	@Autowired BannerService bannerService;
	
	@Autowired AdvertisementService adService;
	
	/**
	 * @author jun
	 * @param page
	 * @param banner
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/bannerList.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String logInitBanner(Page page,
			BannerWebVM banner, 
			HttpServletRequest request,
			HttpServletResponse response) {
		/*分页参数*/
		if (page.getPageNo() == null){
			page.setPageNo(1);
		}
		page.setPageSize(Constants.DEFAULT_PAGE_SIZE);
		/*加载菜单*/
		List<Function> lf = functionService.getFunctionByParentUrl("/web/banner/bannerList.do");
		User user = new User();
		user.setChildMenuList(lf);
		request.getSession().setAttribute(Constants.USER_SESSION_NAME,user);
		/*查询列表*/
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageStart",page.getPageStart());
		map.put("pageSize",page.getPageSize());
		//map.put("isUsed", true);
		List<BannerWebVM> lc = bannerService.getBannerPageList(map);
		int totalCount = bannerService.getBannerPageListCount(map);
		page.setTotalCount(totalCount);
		request.setAttribute("bannerlist", lc);
		return "web/banner/bannerList";
	}
	
	@RequestMapping(value = "/bannerinfo.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String gotoBannerInfo(
			BannerWebVM banner,
			@RequestParam(value = "bannerId", required = false) Integer id,
			HttpServletRequest request, HttpServletResponse response) {
		if(id!=0){//编辑
			/*根据Id查询所选择的banner详情*/
			banner = bannerService.loadBannerById(id);
			request.setAttribute("bannerinfo", banner);
			request.setAttribute("type", Constants.EDIT_TYPE);
		}else{
			Map<String,Object> map = new HashMap<String,Object>();
			//map.put("isUsed", true);
			List<BannerWebVM> lc = bannerService.getBannerPageList(map);
			request.setAttribute("bannerlist", lc);
			request.setAttribute("type", Constants.ADD_TYPE);
		}
		/*查询广告列表*/
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("isUsed", true);
		List<AdvertisementWebVM> list = adService.getAdPageList(map);
		request.setAttribute("adList", list);
		return "web/banner/bannerInfo";
	}
	
	@ResponseBody
	@RequestMapping(value = "/saveOrupdateBanner.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public JsonResult<BannerWebVM> saveOrupdateBanner(
			BannerWebVM banner, 
			HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult<BannerWebVM> json = new JsonResult<BannerWebVM>();
		json.setCode(new Integer(0));
		json.setMessage("保存失败!");
		try { 
			/*新增时没有传id值*/
			if(banner.getId()==null){
				banner.setId(0);
			}
			bannerService.saveorUpdateAd(banner);
			json.setCode(new Integer(1));
			json.setMessage("保存成功!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

}
