package com.hotel.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.common.JsonResult;
import com.hotel.common.Result;
import com.hotel.common.utils.Constants;
import com.hotel.common.utils.Page;
import com.hotel.model.Customer;
import com.hotel.model.Function;
import com.hotel.model.Hotel;
import com.hotel.model.Room;
import com.hotel.model.RoomCheck;
import com.hotel.model.User;
import com.hotel.service.CustomerService;
import com.hotel.service.FunctionService;
import com.hotel.service.HotelService;
import com.hotel.service.RoomCheckService;
import com.hotel.service.RoomService;
import com.hotel.viewmodel.RoomCheckWebVM;
import com.hotel.viewmodel.UserWebVM;

@Scope("prototype")
@Controller
@RequestMapping("/web/check")
public class CheckAction extends BaseAction {
	
	@Autowired FunctionService functionService;
	
	@Autowired RoomCheckService roomCheckService;
	
	@Autowired CustomerService customerService;
	
	@Autowired HotelService hotelService;
	
	@Autowired RoomService roomService;
	
	@RequestMapping(value = "/checkList.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String logInitcheck(Page page,
			HttpServletRequest request,
			HttpServletResponse response) {
		/*分页参数*/
		if (page.getPageNo() == null){
			page.setPageNo(1);
		}
		page.setPageSize(Constants.DEFAULT_PAGE_SIZE);
		//加载菜单
		List<Function> lf = functionService.getFunctionByParentUrl("/web/check/checkList.do");
		User user = new User();
		user.setChildMenuList(lf);
		request.getSession().setAttribute(Constants.USER_SESSION_NAME,user);
		/*查询入住信息列表*/
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageStart",page.getPageStart());
		map.put("pageSize",page.getPageSize());
		List<RoomCheckWebVM> list = roomCheckService.getCheckPageList(map);
		int totalCount = roomCheckService.getCheckPageListCount(map);
		page.setTotalCount(totalCount);
		request.setAttribute("checklist", list);
		return "web/check/checkList";
	}
	
	@RequestMapping(value = "/checkinfo.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String gotoUserInfo(
			UserWebVM user,
			@RequestParam(value = "id", required = false) Integer id,
			HttpServletRequest request, HttpServletResponse response) {
		if(id != 0){//编辑
//			user = userService.getUserByID(userId);
//			request.setAttribute("userinfo", user);
			request.setAttribute("type", Constants.EDIT_TYPE);
		}else{
			request.setAttribute("type", Constants.ADD_TYPE);
		}
		return "web/check/checkInfo";
	}
	
	@ResponseBody
	@RequestMapping(value = "/jsonSelectCustomer.do",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String selectCustomer(
			@RequestParam(value = "mobile", required = false) String mobile,
			HttpServletRequest request,
			HttpServletResponse response) {
		Result<Customer> result = new Result<Customer>();
		result.setIsSuccess(false);
		try{
			Customer customer = customerService.getCustomerByMobile(mobile);
			if(customer == null){
				result.setMsg("电话不存在");
			}else{
				List<RoomCheck> rclist = roomCheckService.loadRoomCheckListByCustomerId(customer.getId());
				//有入住记录信息
				if(rclist.size() != 0){
					for(RoomCheck rc: rclist){
						//正在入住中哦
						if(rc.getCheckoutTime() == null){
							result.setIsSuccess(false);
							result.setMsg("此用户正在入住中");
							return result.toJson();
						}
					}
				}
				result.setData(customer);
				result.setIsSuccess(true);
				result.setMsg("匹配成功");
			}
			return result.toJson();
		}catch(Exception e){
			result = new Result<Customer>(null, false, "匹配失败!");
			return result.toJson();
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/saveOrupdateCheck.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public JsonResult<RoomCheckWebVM> saveOrupdateCheck(
			RoomCheckWebVM roomCheck, 
			HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult<RoomCheckWebVM> json = new JsonResult<RoomCheckWebVM>();
		json.setCode(new Integer(0));
		json.setMessage("保存失败!");
		try {
			/*新增时没有传id值*/
			if(roomCheck.getId()==null){
				roomCheck.setId(0);
			}
			roomCheckService.saveorUpdateCheck(roomCheck);
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
	
	@ResponseBody
	@RequestMapping(value = "/jsonLoadRoomComboList.do", produces = "text/html;charset=UTF-8")
	public String loadRoomComboList(
			@RequestParam(value = "hotelId", required = false) Integer hotelId,
			HttpServletRequest request,
			HttpServletResponse response) {
		try{
			List<Room> list = roomService.loadByHotelId(hotelId);
			JSONArray  json = JSONArray.fromObject(list);
			return json.toString();
		}catch(Exception e){
			return "";
		}
	}

}
