package com.hotel.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.common.JsonResult;
import com.hotel.common.utils.Constants;
import com.hotel.model.Advertisement;
import com.hotel.model.Function;
import com.hotel.model.Hotel;
import com.hotel.model.ItemTag;
import com.hotel.model.Org;
import com.hotel.model.User;
import com.hotel.service.AdvertisementService;
import com.hotel.service.BaseDataService;
import com.hotel.service.FunctionService;
import com.hotel.service.HotelService;

@Scope("prototype")
@Controller
@RequestMapping("/web/ad")
public class AdAction extends BaseAction {
	
	// [start] 接口引用
			@Resource(name="functionService")
			private FunctionService functionService;
			
			@Resource(name="adService")
			private AdvertisementService adService;
			
			@Resource(name="baseDataService")
			private BaseDataService baseDataService;
			
			@Resource(name="hotelService")
			private HotelService hotelService;

			/**
			 * 
			 * 
			 * @param user
			 * @param request
			 * @param response
			 * @return
			 */
			@RequestMapping(value = "/adList.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
			public String logInitAd(Advertisement ad, 
					HttpServletRequest request,
					HttpServletResponse response) {
				if (ad.getPageNo() == null)
					ad.setPageNo(1);
				ad.setPageSize(Constants.DEFAULT_PAGE_SIZE);
				/*加载菜单*/
				List<Function> lf = functionService.getFunctionByParentUrl("/web/ad/adList.do");
				User user = new User();
				user.setChildMenuList(lf);
				request.getSession().setAttribute(Constants.USER_SESSION_NAME,user);
				/*查询项目tag*/
				int tagType = 10;
				List<ItemTag> taglist = baseDataService.selectTagByTagType(tagType);
				request.setAttribute("taglist", taglist);
				/*查询广告列表*/
				List<Advertisement> lc = adService.getAdPageList(ad);
				int totalCount = adService.getAdPageListCount(ad);
				ad.setTotalCount(totalCount);
				//request.setAttribute("company", company);
				request.setAttribute("baseData", ad);
				request.setAttribute("adlist", lc);
				return "web/ad/adList";
			}
			
			@RequestMapping(value = "/adinfo.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
			public String gotoAdInfo(
					Advertisement ad,
					@RequestParam(value = "adId", required = false) Integer id,
					HttpServletRequest request, HttpServletResponse response) {
//				if(ad.getId()!=null){
//					id = ad.getId();
//				}
				if(id!=0){//编辑时加载编辑的广告
					/*根据Id查询所选择的广告详情*/
					ad = adService.getAdById(id);
					request.setAttribute("adinfo", ad);
				}
				/*查询项目tag*/
				List<ItemTag> taglist = baseDataService.selectTagList();
				request.setAttribute("taglist", taglist);
				/*查询酒店*/
				List<Hotel> hotellist = hotelService.selectHotelList();
				request.setAttribute("hotellist", hotellist);
				return "web/ad/adInfo";
			}
			
			@ResponseBody
			@RequestMapping(value = "/saveOrupdateAd.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
			public JsonResult<Advertisement> saveOrupdateAd(
					Advertisement ad, 
					HttpServletRequest request,
					HttpServletResponse response) {
				JsonResult<Advertisement> json = new JsonResult<Advertisement>();
				json.setCode(new Integer(0));
				json.setMessage("保存失败!");
				try { 
					adService.saveorUpdateAd(ad);
					json.setCode(new Integer(1));
					json.setMessage("保存成功!");
				} catch (Exception e) {
					e.printStackTrace();
				}
				return json;
			}
			/**
			 * 加载酒店列表，以下拉选择形式呈现
			 * @param pid
			 * @param request
			 * @param response
			 * @return
			 */
			@ResponseBody
			@RequestMapping(value = "/jsonLoadHotelComboList.do", produces = "text/html;charset=UTF-8")
			public String loadHotelComboList(
					HttpServletRequest request,
					HttpServletResponse response) {
				try{
					List<Hotel> list = hotelService.selectHotelList();
					//request.setAttribute("region", itemTag);
					JSONArray  json = JSONArray.fromObject(list);
					return json.toString();
				}catch(Exception e){
					return "";
				}
			}

			// [end]

}
