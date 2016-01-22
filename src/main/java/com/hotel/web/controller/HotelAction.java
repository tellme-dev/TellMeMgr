package com.hotel.web.controller;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.common.BaseResult;
import com.hotel.common.JsonResult;
import com.hotel.common.utils.Constants;
import com.hotel.common.utils.FileUtil;
import com.hotel.common.utils.ImageCompress;
import com.hotel.common.utils.ImgBase64;
import com.hotel.common.utils.Page;
import com.hotel.common.utils.PathConfig;
import com.hotel.model.Function;
import com.hotel.model.Hotel;
import com.hotel.model.Item;
import com.hotel.model.ItemDetail;
import com.hotel.model.ItemMg;
import com.hotel.model.ItemTag;
import com.hotel.model.ItemTagAssociation;
import com.hotel.model.Region;
import com.hotel.model.User;
import com.hotel.service.BaseDataService;
import com.hotel.service.BbsService;
import com.hotel.service.CustomerBrowseService;
import com.hotel.service.CustomerCollectionService;
import com.hotel.service.FunctionService;
import com.hotel.service.HotelService;
import com.hotel.service.ItemDetailService;
import com.hotel.service.ItemService;
import com.hotel.service.ItemTagAssociationService;
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
	@Resource(name="itemDetailService")
	private ItemDetailService itemDetailService;
	@Resource(name="itemTagAssociationService")
	private ItemTagAssociationService itemTagAssociationService;
	@Autowired CustomerCollectionService customerCollectionService;
	@Autowired CustomerBrowseService customerBrowseService;
	@Autowired BbsService bbsService;

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
		int count = hotelService.getPageHotelCount();
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
		
		String file = request.getParameter("file_logo");
		String province = request.getParameter("ht_province");
		String city = request.getParameter("ht_city");
		String area = request.getParameter("ht_area");
		if(province == null || province.trim().equals("")){
			js.setMessage("请选择省份!");
			return js;
		}
		String url = null;
		if(file != null && !file.trim().equals("")){
			//String path = request.getSession().getServletContext().getRealPath("/");
			String rootPath = PathConfig.getNewPathConfig();
			String savePath = "hotel"+File.separator+"logo"+File.separator;
			String fileName = new Date().getTime()+"";
			//String filePath = path + savePath;
			String filePath = rootPath + savePath;
			File fl = new File(filePath);
			if(!fl.exists()){
				fl.mkdirs();
			}
			String[] arr = file.split(",");
			String suffix = arr[0].split(";")[0].split("\\/")[1];
			if(FileUtil.checkSuffix(suffix)){
				fileName += "."+suffix;
				//arr[0]
				ImgBase64.GenerateImage(arr[1], filePath+fileName);
				url = "picture/hotel/logo/"+fileName;
				/*取图片大小，小于100k则不压缩*/
				File f = new File(filePath+"/"+fileName);
		        if (f.exists() && f.isFile()){  
		        	if(f.length()>102400){
						//压缩图片
					    ImageCompress.imageCompress(filePath+"/", fileName, fileName, 1.0f, 0.25f);
					 }
		        }else{  
		            System.out.println("file doesn't exist or is not a file");  
		        }
			}
			
		}
		hotel.setLogo(url);
		try {
			//新增时没有传id值
			if(hotel.getId()==null){
				hotel.setId(0);
			}
			
			int regionId = 0;
			List<Region> regions = baseDataService.getRegionByCode(hotel.getRegionId()+"");
			if(regions != null && regions.size() > 0){
				regionId = regions.get(0).getId();
			}else{
				Region record = new Region();
				record.setName(area);
				record.setLevel(3);
				record.setPath(province+"."+city);
				record.setCode(hotel.getRegionId()+"");
				int count = baseDataService.insertAndReturnId(record);
				if(count > 0){
					regionId = record.getId();
				}
			}
			if(regionId > 0){
				hotel.setRegionId(regionId);
				if(hotel.getId() == 0){
					js.setMessage("添加失败!");
					hotelService.insert(hotel);
				}else{
					js.setMessage("修改失败!");
					hotelService.updateByPrimaryKeySelective(hotel);
				}
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
	@ResponseBody
	@RequestMapping(value = "/saveOrupdateHotelProject.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public JsonResult<Hotel> saveOrupdateHotelProject(Hotel hotel, 
			HttpServletRequest request,
			HttpServletResponse response) {
		
		JsonResult<Hotel> js = new JsonResult<Hotel>();
		js.setCode(new Integer(0));
		js.setMessage("保存失败!");
		
		String hotelId = request.getParameter("hotelId");
		int hid = 0;
		if(hotelId == null){
			js.setMessage("未识别的请求!");
			return js;
		}
		try {
			hid = new Integer(hotelId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(hid == 0){
			js.setMessage("请求错误!");
			return js;
		}
		
		String proName = request.getParameter("projectName");
		String proTel = request.getParameter("projectTel");
		String proText = request.getParameter("projectText");
		String proPosition= request.getParameter("projectPosition");
		String proType = request.getParameter("projectType");
		String fileCount = request.getParameter("fileCount");
		if(proName == null || proTel == null || proText == null || proType == null || fileCount == null){
			js.setMessage("参数不完整!");
			return js;
		}
		
		Item item = new Item();
		item.setId(0);
		item.setName(proName);
		item.setHotelId(hid);
		item.setTel(proTel);
		item.setText(proText);
		item.setPosition(proPosition);
		item.setIsUsed(true);
		item.setScore(new BigDecimal(0));
		
		
		String projectId = request.getParameter("projectId");
		int proId = 0;
		if(projectId != null && !projectId.equals("")){
			try {
				proId = new Integer(projectId);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		if(proId > 0){
			//*****************
			// 修改的分支
			//********************
			js.setMessage("修改失败!");
			
			
			js.setCode(new Integer(1));
			js.setMessage("修改项目成功!");
		}else{
			//*****************
			// 添加的分支
			//********************
			js.setMessage("添加项目失败!");
			itemService.addAndReturnId(item);
			int itemId = item.getId();
			
			if(itemId < 1){
				js.setMessage("添加项目数据失败!");
				return js;
			}
			
			int count = 0;
			try {
				count = new Integer(fileCount);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			//=================
			// 项目详情添加部分 start
			//=================
			if(count > 0){
				List<ItemDetail> list = new ArrayList<ItemDetail>();
				for(int i = 0; i < count; i ++){
					ItemDetail detail = new ItemDetail();
					detail.setId(0);
					detail.setItemId(itemId);
					
					String file = request.getParameter("file"+i);
					String fileText = request.getParameter("fileText"+i);
					if(file != null){
						
						//String path = request.getSession().getServletContext().getRealPath("/");
						String rootPath = PathConfig.getNewPathConfig();
						String savePath = "hotel"+File.separator+"item"+File.separator+"h"+hotelId+File.separator;
						String fileName = hotelId + "_" + new Date().getTime();
						String filePath = rootPath + savePath;
						File fl = new File(filePath);
						if(!fl.exists()){
							fl.mkdirs();
						}
						String[] arr = file.split(",");
						String suffix = arr[0].split(";")[0].split("\\/")[1];
						if(!FileUtil.checkSuffix(suffix)){
							continue;
						}
						fileName += "."+suffix;
						//arr[0]
						ImgBase64.GenerateImage(arr[1], filePath+fileName);
						/*取图片大小，小于100k则不压缩*/
						File f = new File(filePath+"/"+fileName);
				        if (f.exists() && f.isFile()){  
				        	if(f.length()>102400){
								//压缩图片
							    ImageCompress.imageCompress(filePath+"/", fileName, fileName, 1.0f, 0.25f);
							 }
				        }else{  
				            System.out.println("file doesn't exist or is not a file");  
				        }  
				        
						detail.setImageUrl("picture/hotel/item/h"+hotelId+"/" + fileName);
						
					}
					if(fileText != null){
						detail.setNote(fileText);
					}
					list.add(detail);
				}
				itemDetailService.batchInsert(list);
			}
			//===============
			// 项目详情添加部分 end
			//===============
			
			if(proType.contains(",")){
				String[] types = proType.split(",");
				for(String type : types){
					int tid = 0;
					try {
						tid = new Integer(type);
					} catch (Exception e) {
						// TODO: handle exception
					}
					ItemTagAssociation tagAssociation = new ItemTagAssociation();
					tagAssociation.setItemId(itemId);
					tagAssociation.setItemTagId(tid);
					itemTagAssociationService.insert(tagAssociation);
				}
				js.setCode(new Integer(1));
				js.setMessage("添加项目成功!");
			}else{
				int tid = 0;
				try {
					tid = new Integer(proType);
				} catch (Exception e) {
					// TODO: handle exception
				}
				ItemTagAssociation tagAssociation = new ItemTagAssociation();
				tagAssociation.setItemId(itemId);
				tagAssociation.setItemTagId(tid);
				itemTagAssociationService.insert(tagAssociation);
				js.setCode(new Integer(1));
				js.setMessage("添加项目成功!");
			}
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
		
		//List<Region> regions = baseDataService.getProvinceRegion();
		
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
				String[] arr = tempArearRegion.getPath().split("\\.");
				provinceRegion.setName(arr[0]);
				cityRegion.setName(arr[1]);
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
		//request.setAttribute("regionList", regions);
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
		
		List<ItemMg> itemMgs = new ArrayList<ItemMg>();
		if(items.size() > 0){
			for(Item item : items){
				ItemMg img = new ItemMg();
				img.setItem(item);
				img.setDetails(new ArrayList<ItemDetail>());
				List<ItemDetail> details = itemDetailService.selectByItemId(item.getId());
				if(details != null){
					img.setDetails(details);
				}
				itemMgs.add(img);
			}
		}
		
		List<ItemTag> tags = itemTagService.getTagByParentId(0);
		
		//加载菜单
		//List<Function> lf = functionService.getFunctionByParentUrl("/web/hotel/hotelInfo.do");
		User user = new User();
		//user.setChildMenuList(lf);
		request.getSession().setAttribute(Constants.USER_SESSION_NAME,user);
		request.setAttribute("hotel", hotel);
		request.setAttribute("hotelId", hotelId);
		request.setAttribute("tagList", tags);
		request.setAttribute("itemList", itemMgs);
		return "web/hotel/hotelProject";
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
		String path = request.getSession().getServletContext().getRealPath("/");
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
			//删除无效的logo图片
			deleteLogoFile(list, path);
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/jsonLoadDeleteHotelProject.do", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public BaseResult jsonLoadDeleteHotelProject(Hotel hotel, 
			HttpServletRequest request,
			HttpServletResponse response) {
		BaseResult result = new BaseResult();
		result.setCode(0);
		result.setMessage("删除项目数据失败");
		String projectId = request.getParameter("projectId");
		if(projectId == null){
			result.setMessage("参数不完整");
		}
		//String path = request.getSession().getServletContext().getRealPath("/");
		List<HashMap<String, Integer>> list = new ArrayList<HashMap<String,Integer>>();
		if(projectId.contains(",")){
			String[] arr = projectId.split(",");
			for(String id : arr){
				HashMap<String, Integer> map = new HashMap<String, Integer>();
				int iid = new Integer(id);
				map.put("id", iid);
				list.add(map);
				customerBrowseService.deleteByItem(iid);
				customerCollectionService.deleteByItem(iid);
				bbsService.deleteByItem(iid);
				//deleteItemFiles(iid, path);
			}
			
		}else{
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			int iid = new Integer(projectId);
			map.put("id", iid);
			list.add(map);
			customerBrowseService.deleteByItem(iid);
			customerCollectionService.deleteByItem(iid);
			bbsService.deleteByItem(iid);
			//deleteItemFiles(iid, path);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idList", list);
		
		//itemTagAssociationService.deleteByItemId(map);
		//itemDetailService.deleteByItemId(map);
		itemService.deleteByItemId(map);
		result.setCode(1);
		result.setMessage("");
		
		return result;
	}
	

	// [end]

	//删除元素的图片
	private void deleteItemFiles(int itemId, String path){
		List<ItemDetail> details = itemDetailService.selectByItemId(itemId);
		if(details != null && details.size() > 0){
			for(ItemDetail detail : details){
				if(detail.getImageUrl() != null){
					File file = new File(path+detail.getImageUrl());
					if(file.exists()){
						file.delete();
					}
				}
			}
		}
	}
	
	//删除酒店logo
	private void deleteLogoFile(List<HashMap<String, Integer>> list, String path){
		if(list != null && list.size() > 0){
			for(HashMap<String, Integer> map : list){
				int id = map.get("id");
				Hotel hotel = hotelService.selectByPrimaryKey(id);
				if(hotel != null){
					if(hotel.getLogo() != null && !hotel.getLogo().trim().equals("")){
						File file = new File(path+hotel.getLogo());
						if(file.exists()){
							file.delete();
						}
					}
				}
			}
		}
	}
}
