package com.hotel.common.utils;

import java.awt.geom.Point2D;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.MultipartPostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author Leo
 * 
 */
public class CommonUtil {

	/**
	 * 将class转换为Map，当class为null或者异常的时候，返回new HashMap
	 * 
	 * @param obj
	 * @return Map<String, Object>
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> classToMap(Object obj) {

		if (obj == null) {
			return new HashMap<String, Object>();
		} else {
			try {
				return PropertyUtils.describe(obj);
			} catch (Exception e) {
				e.printStackTrace();
				return new HashMap<String, Object>();
			}
		}
	}

	/**
	 * 返回6位随机密码
	 * 
	 * @return
	 */
	public static String getRandomPassword() {
		return RandomStringUtils.random(6, true, true);
	}

	/**
	 * 将数组转换成字符串
	 * 
	 * @param array
	 * @return
	 */
	public static String arrayToString(Object[] array) {

		StringBuffer str = new StringBuffer("{");
		if (array != null) {

			for (int i = 0; i < array.length; i++) {

				str.append(array[i]);

				if (i != array.length - 1) {
					str.append(",");
				}
			}
		}
		str.append("}");

		return str.toString();
	}

	/**
	 * 从ISO编码字符串转换为GBK
	 * 
	 * @param str
	 * @return
	 */
	public static String getGBStr(String str) {
		if (str == null || str.trim().equalsIgnoreCase(""))
			return null;
		try {
			String temp_p = str;
			String temp = new String(temp_p.getBytes("ISO8859_1"), "GBK");
			return temp;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 从GBK编码字符串转换为UTF-8
	 * 
	 * @param str
	 * @return
	 */
	public static String getUtf8Str(String str) {
		if (str == null || str.trim().equalsIgnoreCase(""))
			return null;
		try {
			String temp_p = str;
			String temp = new String(temp_p.getBytes("GBK"), "UTF-8");
			return temp;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 生成随机数
	 * 
	 * @param int
	 * @return
	 */
	public static String getRandom(int count) {
		String sRand = "";
		Random random = new Random();
		for (int i = 0; i < count; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
		}
		return sRand;
	}

	public static String getNotNullStr(String param) {
		if (param == null) {
			return "";
		}
		return param;
	}

	public static String getFileType(String fileFullName) {
		if (StringUtils.isNotBlank(fileFullName)) {
			int start = StringUtils.lastIndexOf(fileFullName, ".");
			return StringUtils.substring(fileFullName, start + 1,
					fileFullName.length());
		} else {
			return "";
		}
	}

	public static String getFileName(String fileFullName) {
		if (StringUtils.isNotBlank(fileFullName)) {
			int start = StringUtils.lastIndexOf(fileFullName, ".");
			return StringUtils.substring(fileFullName, 0, start);
		} else {
			return "";
		}
	}

	public static String getDateFormat(Date date) {
		if (date != null) {
			SimpleDateFormat dateformat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String resultDateStr = dateformat.format(date);
			return resultDateStr;
		} else {
			return "";
		}
	}

	public static String getDateFormat(Date date, String format) {
		if (date != null) {
			SimpleDateFormat dateformat = new SimpleDateFormat(format);
			String resultDateStr = dateformat.format(date);
			return resultDateStr;
		} else {
			return "";
		}
	}

	public static Date parse(String date, SimpleDateFormat format) {
		if (StringUtils.isEmpty(date)) {
			return null;
		}
		try {
			return format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static int isPicture(String fileName) {
		String fileType = CommonUtil.getFileType(fileName);
		if (StringUtils.equalsIgnoreCase(fileType, "BMP")
				|| StringUtils.equalsIgnoreCase(fileType, "GIF")
				|| StringUtils.equalsIgnoreCase(fileType, "JPEG")
				|| StringUtils.equalsIgnoreCase(fileType, "JPG")
				|| StringUtils.equalsIgnoreCase(fileType, "PNG")) {
			return 0;
		} else if (StringUtils.equalsIgnoreCase(fileType, "AMR")) {
			return 2;
		} else {
			return 1;
		}
	}

	public static int isWord(String fileName) {
		String fileType = CommonUtil.getFileType(fileName);
		if (StringUtils.equalsIgnoreCase(fileType, "DOC")
				|| StringUtils.equalsIgnoreCase(fileType, "DOCX")) {
			return 0;
		} else {
			return 1;
		}
	}

	public static int isExcel(String fileName) {
		String fileType = CommonUtil.getFileType(fileName);
		if (StringUtils.equalsIgnoreCase(fileType, "XLS")
				|| StringUtils.equalsIgnoreCase(fileType, "XLSX")) {
			return 0;
		} else {
			return 1;
		}
	}

	public static int isZip(String fileName) {
		String fileType = CommonUtil.getFileType(fileName);
		if (StringUtils.equalsIgnoreCase(fileType, "ZIP")
				|| StringUtils.equalsIgnoreCase(fileType, "RAR")
				|| StringUtils.equalsIgnoreCase(fileType, "7Z")) {
			return 0;
		} else {
			return 1;
		}
	}

	public static int isVideo(String fileName) {
		String fileType = CommonUtil.getFileType(fileName);
		if (StringUtils.equalsIgnoreCase(fileType, "3GP")
				|| StringUtils.equalsIgnoreCase(fileType, "MP4")) {
			return 0;
		} else {
			return 1;
		}
	}

	public static int isOther(String infileType, String fileName) {
		String fileType = CommonUtil.getFileType(fileName);
		if (StringUtils.equalsIgnoreCase(fileType, infileType.toUpperCase())
				|| StringUtils.equalsIgnoreCase(fileType,
						infileType.toUpperCase())) {
			return 0;
		} else {
			return 1;
		}
	}

	public static String getSystemPath(HttpServletRequest request) {
		System.out.println(request.getServerName() + ":::"
				+ request.getRemoteAddr());
		if (!request.getServerName().startsWith("www")) {
			return request.getScheme() + "://" + request.getServerName() + ":"
					+ request.getServerPort() + request.getContextPath();
		} else {
			return request.getScheme() + "://" + request.getServerName()
					+ request.getContextPath();
		}
	}

	public static boolean isMobile(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][3,5,7,8][0-9]{9}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches();
		return b;
	}

	public static String postFile(String actionUrl, Map<String, String> map,
			Map<String, File> fileMap) throws IOException {
		String resultStr = "";
		HttpClient client = new HttpClient();
		// client.getHttpConnectionManager().getParams().setConnectionTimeout(60);
		// client.getHttpConnectionManager().getParams().setSoTimeout(60);
		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String MULTIPART_FROM_DATA = "multipart/form-data";
		MultipartPostMethod postMethod = new MultipartPostMethod(actionUrl);
		postMethod.setRequestHeader("Content-type", MULTIPART_FROM_DATA
				+ ";boundary=" + BOUNDARY);
		if (map != null && !map.isEmpty()) {
			Iterator<Map.Entry<String, String>> iter = map.entrySet()
					.iterator();
			while (iter.hasNext()) {
				Map.Entry<String, String> entry = iter.next();
				StringPart stringPart = new StringPart(entry.getKey(),
						entry.getValue(), "UTF-8");
				postMethod.addPart(stringPart);
			}
		}
		if (fileMap != null && !fileMap.isEmpty()) {
			File targetFile = fileMap.get("file");
			FilePart fp = new FilePart("file", targetFile, null, "UTF-8");
			File file = new File("d:\\Chrysanthemum.jpg");
			FilePart fp1 = new FilePart("file", file, null, "UTF-8");
			postMethod.addPart(fp);
			postMethod.addPart(fp1);
		}
		client.executeMethod(postMethod);
		resultStr = postMethod.getResponseBodyAsString();
		return resultStr;
	}

	public static String Guid() {
		StringBuffer str = new StringBuffer();
		// 产生 1个 GUID
		for (int i = 0; i < 1; i++) {
			// 创建 GUID 对象
			UUID uuid = UUID.randomUUID();

			// 得到对象产生的ID
			String a = uuid.toString();

			// 转换为大写
			a = a.toUpperCase();
			// 替换 -
			a = a.replaceAll("-", "");
			// System.out.println(a);
			str.append(a);
			// return a;
		}
		return str.toString();
	}



	/**
	 * 判断坐标是否在多边形内
	 * @param point 点坐标
	 * @param polygon 多边形
	 * @return
	 */
	public static boolean checkWithJdkGeneralPath(Point2D.Double point,
			List<Point2D.Double> polygon) {
		java.awt.geom.GeneralPath p = new java.awt.geom.GeneralPath();

		Point2D.Double first = polygon.get(0);
		p.moveTo(first.x, first.y);

		for (Point2D.Double d : polygon) {
			p.lineTo(d.x, d.y);
		}

		p.lineTo(first.x, first.y);

		p.closePath();

		return p.contains(point);

	}
	
	  // Map --> Bean 1: 利用Introspector,PropertyDescriptor实现 Map --> Bean  
    public static void transMap2Bean(Map<String, Object> map, Object obj) {  
  
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
  
                if (map.containsKey(key)) {  
                    Object value = map.get(key);  
                    // 得到property对应的setter方法  
                    Method setter = property.getWriteMethod();  
                    setter.invoke(obj, value);  
                }  
  
            }  
  
        } catch (Exception e) {  
            System.out.println("transMap2Bean Error " + e);  
        }  
  
        return;  
  
    }  
  
    // Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map  
    public static Map<String, Object> transBean2Map(Object obj) {  
    	String sdfd = null;
        if(obj == null){  
            return null;  
        }          
        Map<String, Object> map = new HashMap<String, Object>();  
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
                sdfd = key;
                //System.out.println(key);
                // 过滤class属性  
                if (!key.equals("class")) {  
                    // 得到property对应的getter方法  
                    Method getter = property.getReadMethod();  
                    Object value = getter.invoke(obj);  
  
                    map.put(key, value);  
                }  
  
            }  
        } catch (Exception e) {  
            System.out.println("transBean2Map Error " + e);  
        }  
  
        return map;  
  
    }  
    
    public static Date getStartTime(Date date){  
        Calendar todayStart = Calendar.getInstance();
        todayStart.setTime(date);
        todayStart.set(Calendar.HOUR_OF_DAY, 0);  
        todayStart.set(Calendar.MINUTE, 0);  
        todayStart.set(Calendar.SECOND, 0);  
        todayStart.set(Calendar.MILLISECOND, 0);  
        return todayStart.getTime();  
    }  
      
    public static Date getEndTime(Date date){
        Calendar todayEnd = Calendar.getInstance();  
        todayEnd.setTime(date);
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);  
        todayEnd.set(Calendar.MINUTE, 59);  
        todayEnd.set(Calendar.SECOND, 59);  
        todayEnd.set(Calendar.MILLISECOND, 999);  
        return todayEnd.getTime();  
    }
    
    public static Date getStartMonthDay(Date date){  
        Calendar todayStart = Calendar.getInstance();
        todayStart.setTime(date);
        todayStart.set(Calendar.DATE, 1);
        todayStart.set(Calendar.HOUR_OF_DAY, 0);  
        todayStart.set(Calendar.MINUTE, 0);  
        todayStart.set(Calendar.SECOND, 0);  
        todayStart.set(Calendar.MILLISECOND, 0);  
        return todayStart.getTime();  
    }  
      
    public static Date getEndMonthDay(Date date){
        Calendar todayEnd = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));  
        todayEnd.setTime(date);
//        System.out.println(todayEnd.get(Calendar.DATE)); //获取日);
        todayEnd.set(Calendar.DAY_OF_MONTH, todayEnd.getActualMaximum(Calendar.DAY_OF_MONTH));
//        System.out.println(todayEnd.get(Calendar.DATE)); //获取日);
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);  
        todayEnd.set(Calendar.MINUTE, 59);  
        todayEnd.set(Calendar.SECOND, 59);  
//        todayEnd.set(Calendar.MILLISECOND, 999);  
//        System.out.println(todayEnd.getTime()); //获取日);
        return todayEnd.getTime();  
    } 
    
	public static void main(String[] args) {
		List<Point2D.Double> list = new ArrayList<Point2D.Double>();
		Point2D.Double point1 = new Point2D.Double(116.295294,40.062139);
		Point2D.Double point2 = new Point2D.Double(116.291162,40.055209);
		Point2D.Double point3 = new Point2D.Double(116.301833,40.055789);
		list.add(point1);
		list.add(point2);
		list.add(point3);
		
		Point2D.Double point = new Point2D.Double(116.295869,40.061753);
		System.out.println(checkWithJdkGeneralPath(point,list));
		/*Map<String, String> map = new HashMap<String, String>();
		map.put("summary", "1");
		map.put("detail", "11111");
		map.put("type", "1");
		map.put("address", "高新区九兴大道");
		map.put("additional", "恐怖、暴力");
		Map<String, File> fileMap = new HashMap<String, File>();
		File file = new File("d:\\server.xml");
		// File file1 = new File("d:\\csdn.xml");
		fileMap.put("file", file);
		try {
			// postFile("http://127.0.0.1:8080/police/phone!addinfo",map,fileMap);
			postFile("http://127.0.0.1:8080/police/phone!sendFile", map,
					fileMap);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// try {
		// Date d = new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2014-06-16 21:15:33");
		// System.out.println(d);
		// } catch (ParseException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
*/
	}
	
}
