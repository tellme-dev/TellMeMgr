package com.hotel.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.hotel.common.BaseResult;
import com.hotel.common.JsonResult;
import com.hotel.common.utils.Constants;
import com.hotel.common.utils.Page;
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
	public String logInithotel(Page page,
			Hotel hotel, 
			HttpServletRequest request,
			HttpServletResponse response) {
		/*分页参数*/
		if (page.getPageNo() == null){
			page.setPageNo(1);
		}
		page.setPageSize(Constants.DEFAULT_PAGE_SIZE);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageStart",page.getPageStart());
		map.put("pageSize",page.getPageSize());
		List<Hotel> lh = hotelService.getPageHotel(map);
		int count = hotelService.getPageHotelCount(hotel);
		page.setTotalCount(count);

		//加载菜单
		List<Function> lf = functionService.getFunctionByParentUrl("/web/hotel/hotelList.do");
		User user = new User();
		user.setChildMenuList(lf);
		request.getSession().setAttribute(Constants.USER_SESSION_NAME,user);
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
	@ResponseBody
	@RequestMapping(value = "/saveOrupdateHotel.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public JsonResult<Hotel> saveOrupdateHotel(Hotel hotel, 
			HttpServletRequest request,
			HttpServletResponse response) {
		
		JsonResult<Hotel> js = new JsonResult<Hotel>();
		js.setCode(new Integer(0));
		js.setMessage("保存失败!");
		try {
			/*新增时没有传id值*/
			if(hotel.getId()==null){
				hotel.setId(0);
			}
			if(hotel.getId() == 0){
				js.setMessage("添加失败!");
				hotelService.insert(hotel);
			}else{
				js.setMessage("修改失败!");
				hotelService.updateByPrimaryKeySelective(hotel);
			}
			
			js.setCode(new Integer(1));
			js.setMessage("保存成功!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js;
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
		
		List<Region> regions = baseDataService.getProvinceRegion();
		
		String hotelId = request.getParameter("hotelId");
		int id = 0;
		if(hotelId != null){
			id = new Integer(hotelId);
		}
		Hotel ht = new Hotel();
		Region provinceRegion = new Region(true);
		Region cityRegion = new Region(true);
		Region arearRegion = new Region(true);
		if(id != 0){
			ht = hotelService.selectByPrimaryKey(id);
			Region tempArearRegion = baseDataService.getRegionById(ht.getRegionId());
			if(tempArearRegion != null){
				arearRegion = tempArearRegion;
			}
			String[] arr = arearRegion.getPath().split("\\.");
			
			Region tempCityRegion = baseDataService.getRegionById(new Integer(arr[1]));
			if(tempCityRegion != null){
				cityRegion = tempCityRegion;
			}
			for(Region region : regions){
				if(region.getId().equals(new Integer(arr[0]))){
					provinceRegion = region;
					break;
				}
			}
		}else{
			ht.setId(0);
			ht.setName("");
			ht.setText("");
			ht.setLatitude(new BigDecimal(0));
			ht.setLongitude(new BigDecimal(0));
			ht.setRegionId(0);
		}
		
		//加载菜单
		//List<Function> lf = functionService.getFunctionByParentUrl("/web/hotel/hotelInfo.do");
		User user = new User();
		//user.setChildMenuList(lf);
		request.getSession().setAttribute(Constants.USER_SESSION_NAME,user);
		request.setAttribute("hotel", hotel);
		request.setAttribute("ht", ht);
		request.setAttribute("provinceRegion", provinceRegion);
		request.setAttribute("cityRegion", cityRegion);
		request.setAttribute("arearRegion", arearRegion);
		request.setAttribute("regionList", regions);
		return "web/hotel/hotelInfo";
	}
	
	
	@RequestMapping(value = "/hotelProject.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String gotoHotelProject(Hotel hotel, 
			HttpServletRequest request,
			HttpServletResponse response) {
		
		List<Item> items = new ArrayList<Item>();
		String hotelId = request.getParameter("hotelId");
		if(hotelId != null){
			items = itemService.getItemByHotel(Integer.parseInt(hotelId));
		}
		
//		List<Region> regions = baseDataService.getProvinceRegion();
		List<ItemTagAssociation> tag = hotelService.getTagTypeItem(1);
		List<ItemTag> tags = itemTagService.getTagFromMin(0);
		
		System.out.println(tag.size()+">>>>>>>>>>>>>>>>>>>>");

		//加载菜单
		//List<Function> lf = functionService.getFunctionByParentUrl("/web/hotel/hotelInfo.do");
		User user = new User();
		//user.setChildMenuList(lf);
		request.getSession().setAttribute(Constants.USER_SESSION_NAME,user);
		request.setAttribute("hotel", hotel);
//		request.setAttribute("regionList", regions);
		request.setAttribute("tagList", tags);
		request.setAttribute("itemList", items);
		return "web/hotel/hotelProject";
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
	
	@ResponseBody
	@RequestMapping(value = "/jsonLoadDeleteHotel.do", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public BaseResult DeleteHotel(Hotel hotel, 
			HttpServletRequest request,
			HttpServletResponse response) {
		BaseResult result = new BaseResult();
		result.setCode(0);
		result.setMessage("删除酒店数据失败");
		String hotelId = request.getParameter("hotelId");
		if(hotelId == null){
			result.setMessage("参数不完整");
		}
		List<HashMap<String, Integer>> list = new ArrayList<HashMap<String,Integer>>();
		if(hotelId.contains(",")){
			String[] arr = hotelId.split(",");
			for(String id : arr){
				HashMap<String, Integer> map = new HashMap<String, Integer>();
				map.put("id", new Integer(id));
				list.add(map);
			}
			
		}else{
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			map.put("id", new Integer(hotelId));
			list.add(map);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idList", list);
		
		int count = hotelService.deleteByHotelId(map);
		if(count > 0){
			result.setCode(1);
			result.setMessage("");
		}
		
		return result;
	}
	

	// [end]

}
