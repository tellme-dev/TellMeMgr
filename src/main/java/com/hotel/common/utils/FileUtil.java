package com.hotel.common.utils;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * 文件工具�?依赖与spirng�?/br> spring中的xml�?��配置</br> &lt;bean id="multipartResolver"
 * class="org.springframework.web.multipart.commons.CommonsMultipartResolver"
 * p:defaultEncoding="utf-8"/&gt;
 * 
 * @author dengbin
 * 
 */
public class FileUtil {

	protected final Log log = LogFactory.getLog(getClass());

	/** 觉得路径 */
	public static final int ABSOLUTE_PATH = 1;
	/** 相对路径 */
	public static final int RELATIVELY_PATH = 2;
	
	public final static String[] imgSuffix = {"jpg","jpeg","png"};

	/**
	 * 保存对象到磁盘文件上
	 * 
	 * @param path
	 * @param object
	 */
	public void saveFile(String path, byte[] bytes) {

		File file = new File(path);
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
			os.write(bytes);
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}
	}
	
	public static boolean checkSuffix(String suffix){
		boolean res = false;
		for(String sf : imgSuffix){
			if(suffix.equals(sf)){
				res = true;
				break;
			}
		}
		return res;
	} 

	/**
	 * 单文件上传
	 * 
	 * @param request
	 * @param path
	 * @throws IOException
	 */
	public static String uploadSingleFile(HttpServletRequest request,
			String saveName, String path, int pathType) throws IOException {
		System.out.println("单文件上传");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest
				.getFile("file");
		String realFileName = file.getOriginalFilename();
		if (saveName == null || "".equals(saveName))
			saveName = multipartRequest.getParameter("name");
		if (saveName == null || "".equals(saveName))
			saveName = realFileName;
		String savePath = "";
		if (pathType == FileUtil.RELATIVELY_PATH)
			savePath = request.getSession().getServletContext()
					.getRealPath("/")
					+ path;
		else
			savePath = path;
		File dirPath = new File(savePath);
		if (!dirPath.exists()) {
			dirPath.mkdir();
		}
		File uploadFile = new File(savePath + "\\" + saveName);
		FileCopyUtils.copy(file.getBytes(), uploadFile);
		request.setAttribute("files", loadFiles(request, path));
		return saveName;
	}

	public static String uploadSingleFile2(HttpServletRequest request,
			String saveName, String path, int pathType) throws IOException {

		return "";
	}

	/**
	 * 多文件上传
	 * 
	 * @param request
	 * @param path
	 * @throws IOException
	 */
	public static void uploadMultiFile(HttpServletRequest request, String path,
			int pathType) throws IOException {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		String savePath = "";
		if (pathType == FileUtil.RELATIVELY_PATH)
			savePath = request.getSession().getServletContext()
					.getRealPath("/")
					+ path;
		else
			savePath = path;

		File file = new File(savePath);
		if (!file.exists()) {
			file.mkdir();
		}
		String fileName = null;
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile mf = entity.getValue();
			fileName = mf.getOriginalFilename();
			File uploadFile = new File(savePath + fileName);
			FileCopyUtils.copy(mf.getBytes(), uploadFile);
		}
		request.setAttribute("files", loadFiles(request, path));
	}

	/**
	 * 多文件上传
	 * 
	 * @param request
	 * @param path
	 * @throws IOException
	 * @throws ParseException 
	 */
	public static List<String> uploadMultiFile2(HttpServletRequest request,
			String path, int pathType) throws IOException, ParseException {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		List<String> imageUrl = new ArrayList<String>();// 存放图片url
		String savePath = "";
		if (pathType == FileUtil.RELATIVELY_PATH)
			savePath = request.getSession().getServletContext()
					.getRealPath("/")
					+ path;
		else
			savePath = path;

		File file = new File(savePath);
		if (!file.exists()) {
			file.mkdir();
		}
		String fileName = null;
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile mf = entity.getValue();
			String name = mf.getOriginalFilename();
			String suffix = name.substring(name.lastIndexOf(".")+1);//文件格式
			fileName = new Date().getTime() + CommonUtil.getRandom(4) + "." + suffix;
			// File uploadFile = new File(savePath + fileName);
			File uploadFile = new File(savePath, fileName);
			// 如果路徑不存在 自動創建
			if (!uploadFile.exists()) {
				uploadFile.mkdirs();
			}
			mf.transferTo(uploadFile);
			
			/*取图片大小，小于200k则不压缩*/
			File f = new File(savePath+"/"+fileName);
	        if (f.exists() && f.isFile()){  
	        	if(f.length()>204800){
					//压缩图片
				    //ImageCompress.imageCompress(savePath+"/", fileName, fileName, 1.0f, 0.25f);
				    ImageCompress.imageCompress(savePath+"/", fileName, fileName, 1.0f,540,3000);
				 }
	        }else{  
	            System.out.println("file doesn't exist or is not a file");  
	        }  
			 
			imageUrl.add("picture/ad/" + fileName);
			
			
		}
		request.setAttribute("files", loadFiles(request, path));
		return imageUrl;
	}

	public void downloadFile() {

	}
	
	/**
	 * 文件保存路径
	 * 
	 * @param request
	 * @return
	 */
	public static List<String> loadFiles(HttpServletRequest request, String path) {
		List<String> files = new ArrayList<String>();
		String ctxPath = request.getSession().getServletContext()
				.getRealPath("/")
				+ path;
		File file = new File(ctxPath);
		if (file.exists()) {
			File[] fs = file.listFiles();
			String fname = null;
			for (File f : fs) {
				fname = f.getName();
				if (f.isFile()) {
					files.add(fname);
				}
			}
		}
		return files;
	}

	/**
	 * 文件下载
	 * 
	 * @param request
	 * @param response
	 * @param url
	 *            文件路径
	 * @param pathType
	 *            路径类型
	 * @throws Exception
	 */
	public static void download(HttpServletRequest request,
			HttpServletResponse response, String url, int pathType)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		String path = "";
		if (pathType == FileUtil.RELATIVELY_PATH)
			path = request.getSession().getServletContext().getRealPath("/")
					+ url;
		else
			path = url;
		try {
			String fileName = url.substring(url.lastIndexOf("/") + 1,
					url.length());
			long fileLength = new File(path).length();
			response.setContentType("application/x-msdownload;");
			response.setHeader("Content-disposition", "attachment; filename="
					+ java.net.URLEncoder.encode(fileName, "UTF-8"));
			response.setHeader("Content-Length", String.valueOf(fileLength));
			bis = new BufferedInputStream(new FileInputStream(path));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			try {
				PrintWriter out;
				out = response.getWriter();
				if (e instanceof FileNotFoundException) {
					out.write("文件不存在！");
				} else {
					out.write(e.getMessage());
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
	}

	/**
	 * 功能： 拷贝image从一个地址至另一个地址
	 * 
	 * @param sourcePath
	 *            图片初始生成地址
	 * @param toPath
	 *            图片要复制到的地方
	 * @param FileName
	 *            图片的名字
	 * @param FileN
	 *            生成的文件夹名字
	 */
	@SuppressWarnings("resource")
	public void copyToOtherPath(String sourcePath, String toPath,
			String FileName, String FileN) {
		try {
			File toFile = new File(toPath + FileName + "//" + FileN);
			if (!toFile.exists())
				toFile.mkdirs();
			FileChannel srcChannel = new FileInputStream(sourcePath).getChannel();
			FileChannel dstChannel = new FileOutputStream(toPath + FileName
					+ "//" + FileN + "//" + FileName + ".JPEG").getChannel();
			dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
			srcChannel.close();
			dstChannel.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void copyToOtherPath2(String sourcePath,String toPath,Integer customerId){
		List<String> fileUrls = new ArrayList<String>();
		File sourcefile = new File(sourcePath);
		File dirPath = new File(toPath);
		if(!dirPath.exists()){
			dirPath.mkdirs();
		}
		File[] files = sourcefile.listFiles();
		for(File file:files){
			String name = file.getName();
			if(name.startsWith(customerId+"_")){
				File tarFile = new File(toPath,file.getName());
				file.renameTo(tarFile);
				fileUrls.add("app/bbs/"+customerId+"/"+name);
			}
			
		}
	}
}
