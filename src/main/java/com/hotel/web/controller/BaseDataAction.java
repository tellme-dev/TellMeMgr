package com.hotel.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.common.JsonResult;
import com.hotel.common.utils.Constants;
import com.hotel.common.utils.Page;
import com.hotel.model.Function;
import com.hotel.model.ItemTag;
import com.hotel.model.Org;
import com.hotel.model.Region;
import com.hotel.model.User;
import com.hotel.service.BaseDataService;
import com.hotel.service.FunctionService;
import com.hotel.viewmodel.BaseData;

@Scope("prototype")
@Controller
@RequestMapping("/web/base")
public class BaseDataAction extends BaseAction {
	
	// [start] 接口引用
		@Resource(name="functionService")
		private FunctionService functionService;

		@Resource(name="baseDataService")
		private BaseDataService baseDataService;
		
		@Resource(name="itemTagService")
		private ItemTagService itemTagService;

		
		/**
		 * 
		 * 
		 * @param user
		 * @param request
		 * @param response
		 * @return
		 */
		@RequestMapping(value = "/baseDataList.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
		public String logInitBaseData(Page page,
				BaseData baseData, 
				HttpServletRequest request,
				HttpServletResponse response) {
			/*分页参数*/
			if (page.getPageNo() == null){
				page.setPageNo(1);
			}
			page.setPageSize(Constants.DEFAULT_PAGE_SIZE);
			//加载菜单
			List<Function> lf = functionService.getFunctionByParentUrl("/web/base/baseDataList.do");
			User user = new User();
			user.setChildMenuList(lf);
			request.getSession().setAttribute(Constants.USER_SESSION_NAME,user);
			request.setAttribute("baseData", baseData);
			//page.setTotalCount(totalCount);
			return "web/base/baseDataList";
		}
		
		@RequestMapping(value = "/regionList.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
		public String logInitRegion(Page page, 
				HttpServletRequest request,
				HttpServletResponse response) {
			/*分页参数*/
			if (page.getPageNo() == null){
				page.setPageNo(1);
			}
			page.setPageSize(Constants.DEFAULT_PAGE_SIZE);
			//page.setTotalCount(totalCount);
			return "web/base/regionList";
		}
		
//		@RequestMapping(value = "/itemTagList.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
//		public String logInitItemTag(ItemTag itemTag, 
//				HttpServletRequest request,
//				HttpServletResponse response) {
//			if (itemTag.getPageNo() == null)
//				itemTag.setPageNo(1);
//			itemTag.setPageSize(Constants.DEFAULT_PAGE_SIZE);
//			//加载菜单
////			List<Function> lf = functionService.getFunctionByParentUrl("/web/base/baseDataList.do");
////			User user = new User();
////			user.setChildMenuList(lf);
////			request.getSession().setAttribute(Constants.USER_SESSION_NAME,user);
//			//request.setAttribute("company", company);
//			request.setAttribute("region", itemTag);
//			return "web/base/itemTagList";
//		}
		
		@RequestMapping(value = "/orgComboList.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
		public String logInitOrg(
				HttpServletRequest request,
				HttpServletResponse response) {
			JsonResult<Org> json = new JsonResult<Org>();
			json.setCode(new Integer(0));
			json.setMessage("获取失败!");
			try{
				List<Org> list = baseDataService.getOrgComboList(0);
				if(list.size()>0){
					json.setCode(new Integer(1));
					json.setMessage("保存成功!");
					json.setList(list);
				}
				//request.setAttribute("region", itemTag);
				return json.toString();
			}catch(Exception e){
				return json.toString();
			}
		}
		
		@ResponseBody
		@RequestMapping(value = "/jsonLoadRegionCityComboList.do", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
		public List<Region> loadRegionCity(HttpServletRequest request, HttpServletResponse response) {
			
			List<Region> list = new ArrayList<Region>();
			String provinceId = request.getParameter("provinceId");
			try{
				List<Region> temp = baseDataService.getCityRegion(Integer.parseInt(provinceId));
				if(temp != null && temp.size() > 0){
					list = temp;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return list;
		}
		
		@ResponseBody
		@RequestMapping(value = "/jsonLoadRegionAreaComboList.do", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
		public List<Region> loadRegionArae(
				HttpServletRequest request,
				HttpServletResponse response) {
			
			List<Region> list = new ArrayList<Region>();
			String cityId = request.getParameter("cityId");
			
			try{
				List<Region> temp = baseDataService.getAreaRegion(Integer.parseInt(cityId));
				if(temp != null && temp.size() > 0){
					list = temp;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return list;
		}
		
		@ResponseBody
		@RequestMapping(value = "/jsonLoadItemTagComboList.do", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
		public List<ItemTag> loadItemTag(
				HttpServletRequest request,
				HttpServletResponse response) {
			
			List<ItemTag> list = new ArrayList<ItemTag>();
			String itemId = request.getParameter("itemId");
			
			try{
				List<ItemTag> temp = itemTagService.getTagByParentId(new Integer(itemId));
				if(temp != null && temp.size() > 0){
					list = temp;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return list;
		}

		// [end]

}
