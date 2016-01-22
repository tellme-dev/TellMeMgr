package com.hotel.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.hotel.common.JsonResult;
import com.hotel.common.Result;
import com.hotel.common.utils.Constants;
import com.hotel.common.utils.FileUtil;
import com.hotel.common.utils.Page;
import com.hotel.common.utils.PathConfig;
import com.hotel.model.Advertisement;
import com.hotel.model.Function;
import com.hotel.model.Hotel;
import com.hotel.model.User;
import com.hotel.modelVM.BbsVM;
import com.hotel.service.AdvertisementService;
import com.hotel.service.BaseDataService;
import com.hotel.service.BbsService;
import com.hotel.service.FunctionService;
import com.hotel.service.HotelService;
import com.hotel.viewmodel.AdvertisementWebVM;
import com.hotel.viewmodel.ItemTagWebVM;

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
			
			@Resource(name="bbsService")
			private BbsService bbsService;

			/**
			 * 
			 * @param user
			 * @param request
			 * @param response
			 * @return
			 */
			@RequestMapping(value = "/adList.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
			public String logInitAd(Page page,
					AdvertisementWebVM ad, 
					HttpServletRequest request,
					HttpServletResponse response) {
				/*分页参数*/
				if (page.getPageNo() == null){
					page.setPageNo(1);
				}
				page.setPageSize(Constants.DEFAULT_PAGE_SIZE);
				/*加载菜单*/
				List<Function> lf = functionService.getFunctionByParentUrl("/web/ad/adList.do");
				User user = new User();
				user.setChildMenuList(lf);
				request.getSession().setAttribute(Constants.USER_SESSION_NAME,user);
				/*查询广告列表*/
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("pageStart",page.getPageStart());
				map.put("pageSize",page.getPageSize());
				map.put("isUsed", true);
				List<AdvertisementWebVM> lc = adService.getAdPageList(map);
				int totalCount = adService.getAdPageListCount(map);
				page.setTotalCount(totalCount);
				request.setAttribute("adlist", lc);
				return "web/ad/adList";
			}
			
			@RequestMapping(value = "/adinfo.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
			public String gotoAdInfo(
					AdvertisementWebVM ad,
					@RequestParam(value = "adId", required = false) Integer id,
					HttpServletRequest request, HttpServletResponse response) {
//				if(ad.getId()!=null){
//					id = ad.getId();
//				}
				if(id!=0){//编辑时加载编辑的广告
					/*根据Id查询所选择的广告详情*/
					ad = adService.getAdById(id);
					request.setAttribute("adinfo", ad);
					request.setAttribute("type", Constants.EDIT_TYPE);
				}else{
					request.setAttribute("type", Constants.ADD_TYPE);
				}
				/*查询社区列表*/
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("parentId", 0);
				map.put("bbsType", 1);
				map.put("postType", 0);
				map.put("deleteUserId", null);
				List<BbsVM> list = bbsService.loadBbsList(map);
				request.setAttribute("bbsList", list);
//				/*查询项目tag*/
//				List<ItemTag> taglist = baseDataService.selectTagList();
//				request.setAttribute("taglist", taglist);
//				/*查询酒店*/
//				List<Hotel> hotellist = hotelService.selectHotelList();
//				request.setAttribute("hotellist", hotellist);
				return "web/ad/adInfo";
			}
			
			@ResponseBody
			@RequestMapping(value = "/saveOrupdateAd.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
			public JsonResult<AdvertisementWebVM> saveOrupdateAd(
					AdvertisementWebVM ad, 
					HttpServletRequest request,
					HttpServletResponse response) {
				JsonResult<AdvertisementWebVM> json = new JsonResult<AdvertisementWebVM>();
				json.setCode(new Integer(0));
				json.setMessage("保存失败!");
				try { 
					/*新增时没有传id值*/
					if(ad.getId()==null){
						ad.setId(0);
					}
					//创建一个通用的多部分解析器  
			        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
			        //判断 request 是否有文件上传,即多部分请求  
			        if(multipartResolver.isMultipart(request)){ 
			        	String rootPath = PathConfig.getNewPathConfig();//获取绝对路径
			        	List<String> imageUrl = FileUtil.uploadMultiFile2(request, rootPath+"ad", FileUtil.ABSOLUTE_PATH);
			        	ad.setImageUrlList(imageUrl);
			        }
			        if(ad.getBbsId()!=null&&!"".equals(ad.getBbsId())){
			        	ad.setTargetId(ad.getBbsId());
			        }
			        if(ad.getHotelId()!=null&&!"".equals(ad.getHotelId())){
			        	ad.setTargetId(ad.getHotelId());
			        }
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
			 * @author jun
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
			/**
			 * 加载ItemTag列表，以下拉树形式呈现
			 * @author jun
			 * @param pid
			 * @param request
			 * @param response
			 * @return
			 */
			@ResponseBody
			@RequestMapping(value = "/jsonLoadItemTagTree.do", produces = "text/html;charset=UTF-8")
			public String loadItemTagTree(
					@RequestParam(value = "pid", required = false) Integer pid,
					HttpServletRequest request,
					HttpServletResponse response) {
				try{
					List<ItemTagWebVM> list = baseDataService.getItemTagTree(pid);
					//request.setAttribute("region", itemTag);
					JSONArray  json = JSONArray.fromObject(list);
					return json.toString();
				}catch(Exception e){
					return "";
				}
			}
			/**
			 * 加载社区列表
			 * @author jun
			 * @param request
			 * @param response
			 * @return
			 */
			/*@ResponseBody
			@RequestMapping(value = "/jsonLoadBbsComboList.do", produces = "text/html;charset=UTF-8")
			public String loadBbsComboList(
					HttpServletRequest request,
					HttpServletResponse response) {
				try{
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("parentId", 0);
					List<BbsVM> list = bbsService.loadBbsList(map);
					request.setAttribute("bbsList", list);
					JSONArray  json = JSONArray.fromObject(list);
					return json.toString();
				}catch(Exception e){
					return "";
				}
			}*/
			/**
			 * @author jun
			 * @param adIds
			 * @param request
			 * @param response
			 * @return
			 */
			@ResponseBody
			@RequestMapping(value = "/jsonDeleteAd.do",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
			public String deleteAd(
					@RequestParam(value = "adIds", required = false) String adIds,
					HttpServletRequest request,
					HttpServletResponse response) {
				Result<Advertisement> result = null;
				try{
					adService.updateUserByIds(adIds);
					result = new Result<Advertisement>(null, true, "删除成功!");
					return result.toJson();
				}catch(Exception e){
					result = new Result<Advertisement>(null, false, "删除失败!");
					return result.toJson();
				}
			}
			// [end]

}
