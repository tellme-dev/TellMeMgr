package com.hotel.web.controller;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.hotel.common.utils.Constants;
import com.hotel.common.utils.FileUtil;
import com.hotel.model.Function;
import com.hotel.model.Hotel;
import com.hotel.model.Item;
import com.hotel.model.ItemTag;
import com.hotel.model.ItemTagAssociation;
import com.hotel.model.Region;
import com.hotel.model.User;
import com.hotel.service.BaseDataService;
import com.hotel.service.FunctionService;
import com.hotel.service.HotelService;
import com.hotel.service.ItemService;
import com.hotel.service.ItemTagService;

@Scope("prototype")
@Controller
@RequestMapping("/web/hotel")
public class HotelAction extends BaseAction {

	// [start] 接口引用
	@Resource(name="functionService")
	private FunctionService functionService;
	@Resource(name="hotelService")
	private HotelService hotelService;
	@Resource(name="baseDataService")
	private BaseDataService baseDataService;
	@Resource(name="itemTagService")
	private ItemTagService itemTagService;
	@Resource(name="itemService")
	private ItemService itemService;

	// [end]

	// [start] 员工公司模块 ---- 页面跳转

	
	/**
	 * 
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/hotelList.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String logInithotel(Hotel hotel, 
			HttpServletRequest request,
			HttpServletResponse response) {
		if (hotel.getPageNo() == null)
			hotel.setPageNo(1);
		hotel.setPageSize(Constants.DEFAULT_PAGE_SIZE);
		
		List<Hotel> lh = hotelService.getPageHotel(hotel);
		int count = hotelService.getPageHotelCount(hotel);
		hotel.setTotalCount(count);

		//加载菜单
		List<Function> lf = functionService.getFunctionByParentUrl("/web/hotel/hotelList.do");
		User user = new User();
		user.setChildMenuList(lf);
		request.getSession().setAttribute(Constants.USER_SESSION_NAME,user);
		request.setAttribute("hotel", hotel);
		request.setAttribute("hotelList", lh);
		return "web/hotel/hotelList";
	}
	
	
	/**
	 * 
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/hotelInfo.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String gotoHotelInfo(Hotel hotel, 
			HttpServletRequest request,
			HttpServletResponse response) {
		if (hotel.getPageNo() == null)
			hotel.setPageNo(1);
		hotel.setPageSize(Constants.DEFAULT_PAGE_SIZE);
		
		List<Item> items = new ArrayList<Item>();
		String hotelId = request.getParameter("hotelId");
		if(hotelId != null){
			items = itemService.getItemByHotel(Integer.parseInt(hotelId));
		}
		
		List<Region> regions = baseDataService.getProvinceRegion();
		List<ItemTagAssociation> tag = hotelService.getTagTypeItem(1);
		List<ItemTag> tags = itemTagService.getTagFromMin(0);
		
		System.out.println(tag.size()+">>>>>>>>>>>>>>>>>>>>");

		//加载菜单
		//List<Function> lf = functionService.getFunctionByParentUrl("/web/hotel/hotelInfo.do");
		User user = new User();
		//user.setChildMenuList(lf);
		request.getSession().setAttribute(Constants.USER_SESSION_NAME,user);
		request.setAttribute("hotel", hotel);
		request.setAttribute("regionList", regions);
		request.setAttribute("tagList", tags);
		request.setAttribute("itemList", items);
		return "web/hotel/hotelInfo";
	}
	
	@ResponseBody
	@RequestMapping(value = "/jsonLoadUploadItem.do", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String uploadFile(Hotel hotel, 
			HttpServletRequest request,
			HttpServletResponse response) {
		String suffix = request.getParameter("suffix");
		if(suffix != null){
			System.out.println(suffix);
		}
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest  
        .getFile("file");  
		String realFileName = file.getOriginalFilename(); 
		System.out.println(realFileName);
		
//		try {
//			FileUtil.uploadSingleFile(request, "123.png", "/tempfiles/item", FileUtil.RELATIVELY_PATH);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return "success111";
	}
	

	// [end]

}

