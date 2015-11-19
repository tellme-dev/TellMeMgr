package com.hotel.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hotel.common.utils.Constants;
import com.hotel.model.Function;
import com.hotel.model.Hotel;
import com.hotel.model.Region;
import com.hotel.model.User;
import com.hotel.service.BaseDataService;
import com.hotel.service.FunctionService;
import com.hotel.service.HotelService;

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
		
		List<Region> regions = baseDataService.getProvinceRegion();

		//加载菜单
		List<Function> lf = functionService.getFunctionByParentUrl("/web/hotel/hotelList.do");
		User user = new User();
		user.setChildMenuList(lf);
		request.getSession().setAttribute(Constants.USER_SESSION_NAME,user);
		//request.setAttribute("company", company);
		request.setAttribute("hotel", hotel);
		request.setAttribute("hotelList", lh);
		request.setAttribute("regionList", regions);
		return "web/hotel/hotelList";
	}
	

	// [end]

}

