package com.hotel.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.hotel.common.JsonResult;
import com.hotel.common.Result;
import com.hotel.common.utils.Constants;
import com.hotel.common.utils.FileUtil;
import com.hotel.common.utils.Page;
import com.hotel.model.Advertisement;
import com.hotel.model.Function;
import com.hotel.model.Hotel;
import com.hotel.model.ItemTag;
import com.hotel.model.User;
import com.hotel.service.AdvertisementService;
import com.hotel.service.BaseDataService;
import com.hotel.service.FunctionService;
import com.hotel.service.HotelService;
import com.hotel.viewmodel.AdvertisementVM;
import com.hotel.viewmodel.ItemTagVM;

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
			public String logInitAd(Page page,
					AdvertisementVM ad, 
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
				/*查询项目tag*/
				int tagType = 10;
				List<ItemTag> taglist = baseDataService.selectTagByTagType(tagType);
				request.setAttribute("taglist", taglist);
				/*查询广告列表*/
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("pageStart",page.getPageStart());
				map.put("pageSize",page.getPageSize());
				List<AdvertisementVM> lc = adService.getAdPageList(map);
				int totalCount = adService.getAdPageListCount(ad);
				page.setTotalCount(totalCount);
				request.setAttribute("adlist", lc);
				return "web/ad/adList";
			}
			
			@RequestMapping(value = "/adinfo.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
			public String gotoAdInfo(
					AdvertisementVM ad,
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
			public JsonResult<AdvertisementVM> saveOrupdateAd(
					AdvertisementVM ad, 
					HttpServletRequest request,
					HttpServletResponse response) {
				JsonResult<AdvertisementVM> json = new JsonResult<AdvertisementVM>();
				json.setCode(new Integer(0));
				json.setMessage("保存失败!");
				try { 
					/*新增时没有传id值*/
					if(ad.getId()==null){
						ad.setId(0);
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
			/**
			 * 加载ItemTag列表，以下拉树形式呈现
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
					List<ItemTagVM> list = baseDataService.getItemTagTree(pid);
					//request.setAttribute("region", itemTag);
					JSONArray  json = JSONArray.fromObject(list);
					return json.toString();
				}catch(Exception e){
					return "";
				}
			}
			
			@ResponseBody
			@RequestMapping(value = "/jsonDeleteAd.do",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
			public String deleteAd(
					@RequestParam(value = "adIds", required = false) String adIds,
					HttpServletRequest request,
					HttpServletResponse response) {
				Result<Advertisement> result = null;
				try{
					adService.deleteUserByIds(adIds);
					result = new Result<Advertisement>(null, true, "删除成功!");
					return result.toJson();
				}catch(Exception e){
					result = new Result<Advertisement>(null, false, "删除失败!");
					return result.toJson();
				}
			}
			@ResponseBody
			@RequestMapping(value = "/saveOrupdatePhoto2.do", method = RequestMethod.POST)
			public JsonResult<AdvertisementVM> saveOrupdatePhoto2(
					//@RequestParam MultipartFile files,
					//@RequestParam("file") MultipartFile files,
					//@RequestParam("file") CommonsMultipartFile[] files,
					//MultipartHttpServletRequest request
					HttpServletRequest request,
					HttpServletResponse response
					) {
				//List<MultipartFile> files = (List<MultipartFile>) request.getFile("myfiles"); 
				//List<MultipartFile> files = UploadHelper.getFileSet(request, 1024 * 20, null);
				//MultipartHttpServletRequest   multipartRequest = (MultipartHttpServletRequest) request;
				JsonResult<AdvertisementVM> json = new JsonResult<AdvertisementVM>();
				json.setCode(new Integer(0));
				json.setMessage("保存失败!");
				List list = new ArrayList();
				try{
					//创建一个通用的多部分解析器  
			        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
			        //判断 request 是否有文件上传,即多部分请求  
			        if(multipartResolver.isMultipart(request)){  
			            //转换成多部分request    
			            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
			            //取得request中的所有文件名  
			            Iterator<String> iter = multiRequest.getFileNames();  
			            MultiValueMap<String, MultipartFile> iter1 = multiRequest.getMultiFileMap();
			            while(iter.hasNext()){  
			                //记录上传过程起始时的时间，用来计算上传时间  
			                int pre = (int) System.currentTimeMillis();  
			                //取得上传文件  
			                MultipartFile file = multiRequest.getFile(iter.next());  
			                if(file != null){  
			                    //取得当前上传文件的文件名称  
			                    String myFileName = file.getOriginalFilename();  
			                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在  
			                    if(myFileName.trim() !=""){  
			                        System.out.println(myFileName);  
			                        //重命名上传后的文件名  
			                        //String fileName = "demoUpload" + file.getOriginalFilename();  
			                        //定义上传路径  
			                        String path = request.getSession().getServletContext().getRealPath(  
			            	                "/")+"image/ad";  
			                        File localFile = new File(path,myFileName); 
			                        //如果路徑不存在 自動創建
			                        if(!localFile.exists()){  
			                        	localFile.mkdirs();  
			                        }  
			                        try {
										file.transferTo(localFile);
									} catch (IllegalStateException | IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}  
			                    }  
			                }  
			                //记录上传该文件后的时间  
			                int finaltime = (int) System.currentTimeMillis();  
			                System.out.println(finaltime - pre);  
			            }  
			              
			        }  
				}catch(Exception e){
					return json;
				}
				
		        return json;  
			}
			@ResponseBody
			@RequestMapping(value = "/saveOrupdatePhoto.do", method = RequestMethod.POST)
			public JsonResult<AdvertisementVM> saveOrupdatePhoto(
					HttpServletRequest request,
					HttpServletResponse response
					) {
				JsonResult<AdvertisementVM> json = new JsonResult<AdvertisementVM>();
				json.setCode(new Integer(0));
				json.setMessage("保存失败!");
				try{
					//创建一个通用的多部分解析器  
			        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
			        //判断 request 是否有文件上传,即多部分请求  
			        if(multipartResolver.isMultipart(request)){  
			        	FileUtil.uploadMultiFile2(request, "image/ad", FileUtil.RELATIVELY_PATH);
			        }  
				}catch(Exception e){
					return json;
				}
				
		        return json;  
			}

			// [end]

}
