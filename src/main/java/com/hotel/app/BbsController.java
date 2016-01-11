package com.hotel.app;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hotel.common.ListResult;
import com.hotel.common.Result;
import com.hotel.common.utils.FileUtil;
import com.hotel.common.utils.Page;
import com.hotel.model.Bbs;
import com.hotel.model.BbsAttach;
import com.hotel.model.BbsCategory;
import com.hotel.model.SearchText;
import com.hotel.modelVM.BbsVM;
import com.hotel.service.BbsService;

@Controller
@RequestMapping("/app/bbs")
public class BbsController {
	
	@Autowired BbsService bbsService;
	/**
	 * 获取社区分类标签
	 * @author jun
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "loadBbsCategoryList.do", produces = "application/json;charset=UTF-8")
	public String loadBbsCategoryList(
			HttpServletRequest request){
		try{
			ListResult<BbsCategory> result = bbsService.loadBbsCategoryList();
			return result.toJson();
		}catch(Exception e){
			ListResult<BbsCategory> result = new ListResult<BbsCategory>(null, false, "获取数据失败");
			return result.toJson();
		}
	}
	
	/**
	 * 获取社区帖子列表
	 * @author jun
	 * @param bbsParam:categoryId,pageNo
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "loadBbsList.do", produces = "application/json;charset=UTF-8")
	public String loadBbsList(
			@RequestParam(value = "bbsParam", required = false) String bbsParam,
			HttpServletRequest request){
		JSONObject jObj = JSONObject.fromObject(bbsParam);
		//Bbs bbs = (Bbs) JSONObject.toBean(jObj,Bbs.class);
		int type = jObj.getInt("type");// 最新活动： type=1 ，热门话题：type=2 ,吐槽专区：type=3，达人推荐：type=4
		int pageNo = jObj.getInt("pageNo");
		int pageSize = jObj.getInt("pageSize");
		try{
			Page page = new Page();
			page.setPageNo(pageNo);
			page.setPageSize(pageSize);
			ListResult<BbsVM> result = bbsService.loadBbsListByType(page,type);
			return result.toJson();
		}catch(Exception e){
			ListResult<BbsVM> result = new ListResult<BbsVM>(null, false, "获取数据失败");
			return result.toJson();
		}
	}
	
	/**
	 * 获取单个帖子信息
	 * @author jun
	 * @param bbsParam
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "loadBbs.do", produces = "application/json;charset=UTF-8")
	public String loadBbs(
			@RequestParam(value = "bbsParam", required = false) String bbsParam,
			HttpServletRequest request){
		JSONObject jObj = JSONObject.fromObject(bbsParam);
		Bbs bbs = (Bbs) JSONObject.toBean(jObj,Bbs.class);
		int id = bbs.getId();
		Result<BbsVM> result = new Result<BbsVM>();
		try{
			BbsVM b = bbsService.loadBbsById(id);
			result = new Result<BbsVM>(b,true,"获取数据成功");
			return result.toJson();
		}catch(Exception e){
			result = new Result<BbsVM>(null,true,"获取数据失败");
			return result.toJson();
		}
	}
	/**
	 * 获取社区帖子正文内容ByPid
	 * @author jun
	 * @param bbsParam
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "loadBbsChildren.do", produces = "application/json;charset=UTF-8")
	public String loadBbsChildren(
			@RequestParam(value = "bbsParam", required = false) String bbsParam,
			HttpServletRequest request){
		JSONObject jObj = JSONObject.fromObject(bbsParam);
		//BbsVM bbs = (BbsVM) JSONObject.toBean(jObj,BbsVM.class);
		int pid = jObj.getInt("id");
		int pageNo = jObj.getInt("pageNo");
		int pageSize = jObj.getInt("pageSize");
		try{
			Page page = new Page();
			page.setPageNo(pageNo);
			page.setPageSize(pageSize);
			ListResult<BbsVM> result = bbsService.loadBbsChildren(page,pid);
			return result.toJson();
		}catch(Exception e){
			ListResult<BbsVM> result = new ListResult<BbsVM>(null, false, "获取数据失败");
			return result.toJson();
		}
	}
	
	/**
	 * @author jun
	 * @param bbsParam
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveBbs.do", produces = "application/json;charset=UTF-8")
	public String saveBbs(
			@RequestParam(value = "bbsParam", required = false) String bbsParam,
			HttpServletRequest request){
		JSONObject jObj = JSONObject.fromObject(bbsParam);
		BbsVM bbs = (BbsVM) JSONObject.toBean(jObj,BbsVM.class);
		int id = bbs.getCustomerId();
		/*if(bbs.getCustomerId()==null||){
			
		}*/
		Result<BbsVM> result = new Result<BbsVM>();
		try{
			bbsService.saveBbs(request,bbs);
			result = new Result<BbsVM>(null, true, "保存成功");
			return result.toJson();
		}catch(Exception e){
			result = new Result<BbsVM>(null, false, "保存失败");
			return result.toJson();
		}
	}
	
	/**
	 * 发帖上传图片
	 * @author jun
	 * @param bbsPhoto
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "uploadPhoto.do", produces = "application/json;charset=UTF-8")  
    public @ResponseBody String upload(
    		@RequestParam MultipartFile bbsPhoto,
    		HttpServletRequest request) { 
		Result<Bbs> result = null;
        try { 
        	//String path = request.getSession().getServletContext().getRealPath("Photo"); 
        	String path = request.getSession().getServletContext().getRealPath("/")+"app/bbs/temp";
//        	String path = getClass().getResource("/").getFile().toString();
//			path = path.substring(0, (path.length() - 16))+"washPhoto";
        	String fileName = bbsPhoto.getOriginalFilename();
        	//分割出customerId作为左后一级的文件夹
        	String[] str = fileName.split("_");
        	String customerID = str[0];
        	path = path +"/"+ customerID;
        	
        	File uploadFile = new File(path,fileName);
        	if(!uploadFile.exists()){  
        		uploadFile.mkdirs();  
            }  
        	bbsPhoto.transferTo(uploadFile); //保存
        	result = new Result<Bbs>(null, true, "上传成功");
        	return result.toJson();
        }catch(Exception e){
        	result = new Result<Bbs>(null, false, "上传失败");
        	return result.toJson();
        }
	}
	@ResponseBody
	@RequestMapping(value = "deletePhoto.do", produces = "application/json;charset=UTF-8")
	public String deletePhoto(
			@RequestParam(value = "param", required = true) String param,
			HttpServletRequest request){
		JSONObject jObj = JSONObject.fromObject(param);
		String fileUrl = "";
		int customerId = 0;
		try{
			if(jObj.containsKey("fileUrl")){
				fileUrl = jObj.getString("fileUrl");
			}
			if(jObj.containsKey("customerId")){
				customerId = jObj.getInt("customerId");
			}
		}catch(Exception e){
			e.printStackTrace();
			return new Result<String>(null, false, "json解析异常").toJson();
		}
		Result<Bbs> result = null;
		try{
			//删除对应的图片
			if(fileUrl != ""&&customerId == 0){
				String path = request.getSession().getServletContext().getRealPath("/")+fileUrl;//fileUrl是文件相对路径
				boolean isSuccess = (new File(path)).delete();
				result = new Result<Bbs>(null, true,"删除照片成功");
				return result.toJson();
			}
			//删除整个文件夹
			if(customerId != 0){
				String path = request.getSession().getServletContext().getRealPath("/")+"app/bbs/temp/"+customerId;
				String msg = "";
				try{

					File file = new File(path);
					msg = deleteFile(file); 
				}catch(Exception ex){
					ex.printStackTrace();
				}
				result = new Result<Bbs>(null, true, msg);
				return result.toJson();
			}
			return new Result<Bbs>(null, true,"要删除的文件出错").toJson();
		}catch(Exception ex){
			result = new Result<Bbs>(null, false,"删除失败");
			return result.toJson();
		} 
	}
	
//	private void deleteDir(File dir) { 
//		// TODO Auto-generated method stub 
//		 if (dir.isDirectory()) {
//	            String[] children = dir.list(); 
//	            for (int i=0; i<children.length; i++) {
//	                deleteDir(new File(dir, children[i])); 
//	            }
//	        } 
//	}
	private String deleteFile(File file) { 
		String msg = "";
	    if (file.exists()) {//判断文件是否存在  
	        if (file.isFile()) {//判断是否是文件  
	            file.delete();//删除文件  
	            msg = "删除文件成功";
	        } else if (file.isDirectory()) {//否则如果它是一个目录  
	             File[] files = file.listFiles();//声明目录下所有的文件 files[];  
	             for (int i = 0;i < files.length;i ++) {//遍历目录下所有的文件  
	             this.deleteFile(files[i]);//把每个文件用这个方法进行迭代  
	             }  
	             file.delete();//删除文件夹  
	             msg = "删除文件夹及子文件成功";
	        } 
	    } else {  
	       msg = "所删除的文件不存在";  
	    }  
	    return msg;
	}  

	@RequestMapping(value = "fullTextSearchOfBbs.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody  String fullTextSearchOfBbs(
			@RequestParam(value = "searchData", required = false) String searchData,
			HttpServletRequest request)
	{
		//先保存查询内容，然后进行全文查询
		JSONObject jObj = JSONObject.fromObject(searchData);
		SearchText text = (SearchText) JSONObject.toBean(jObj,SearchText.class);
		text.setSearchTime(new Date());
		//全文查询:查询酒店
		List<BbsVM> list = bbsService.fullTextSearchOfBbs(text.getText());
		if(list ==null||list.size() ==0){
			return new ListResult<BbsVM>(null,false,"全文搜索帖子失败").toJson();
		}
		return new ListResult<BbsVM>(list,true,"获取推荐帖子成功").toJson();
	}
	/**
	 * 获取bbs图片
	 * @param bbsParam:bbsId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "loadImageByBbsId.do",produces = "application/json;charset=UTF-8")
	public String loadImageByBbsId(
			@RequestParam(value = "bbsParam",required = true) String bbsParam){
		JSONObject jObj = JSONObject.fromObject(bbsParam);
		int bbsId = 0;
		try{
			if(jObj.containsKey("bbsId")){
				bbsId = jObj.getInt("bbsId");
			}
		}catch(Exception e){
			e.printStackTrace();
			return new Result<String>("", false, "json解析异常").toJson();
		}
		try{
			ListResult<BbsAttach> result = bbsService.loadBbsAttachByBbsId(bbsId);
			return result.toJson();
		}catch(Exception e){
			return new ListResult<BbsAttach>(null,false,"获取数据失败").toJson();
		}
	}
	/**
	 * test
	 * @author jun
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "test.do", produces = "application/json;charset=UTF-8")
	@ResponseBody 
	public String test(
			HttpServletRequest request)
	{
		Result<Bbs> result = null;
		try{
			String path = request.getSession().getServletContext().getRealPath("/")+"app/bbs/temp";
			String toPath = request.getSession().getServletContext().getRealPath("/")+"app/bbs/bbs-id";
			FileUtil.copyToOtherPath2(path, toPath,1);
			result = new Result<Bbs>(null, true, "yes");
			return result.toJson();
		}catch(Exception e){
			result = new Result<Bbs>(null, true, "no");
			return result.toJson();
		}
	}
}
