package com.hotel.common.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GeneralUtil {

	static  String[] seed={"0","1","2","3","4","5","6","7","8","9"};
	
	static int verifCodeLength=4;
	
	public static String createVerifCode(){
		
		String result="";
		
		for(int i=0;i<verifCodeLength;i++){
			result +=Math.round(Math.random() * 9);
		}
		return result;
	}
	
	
	public static Map<String,Object> getSerialNoPars(Integer billType){
		Calendar  calendar=Calendar.getInstance();
		
		DateFormat fmt=new SimpleDateFormat("yyMMdd");
		String d=fmt.format(calendar.getTime());
		
		Integer billDate=Integer.valueOf(d);
		
		Map<String,Object> map=new HashMap<String,Object>();
		
		map.put("billType", billType);
		map.put("billDate", billDate);
		
		return map;
	}
//	public static Date stringToDate(String str){
//		DateFormat fmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date time = fmt.parse(str);
//	}
	/**
	 * 获取当前时间、当前时间7分钟前的时间、一个月前的时间。格式yyyy-MM-dd HH:mm:ss 类型Date
	 * @return
	 * @throws ParseException
	 */
	public static Map<String,Object> getCurrentTime() throws ParseException{
		Calendar  calendar=Calendar.getInstance();
		DateFormat fmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String d=fmt.format(calendar.getTime());
		Date time = fmt.parse(d);
		
		calendar.setTime(time);
		calendar.add(Calendar.MINUTE,-7);
		String d2=fmt.format(calendar.getTime());
		Date time2 = fmt.parse(d2);
		
		calendar.add(Calendar.MONTH, -1);
		String d3=fmt.format(calendar.getTime());
		Date time3 = fmt.parse(d3);
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("currentTime", time);
		map.put("beginTime", time2);
		map.put("monthAgo", time3);
		return map;
	}
	/**
	 * 获取昨天的日期，  类型Date
	 * @return
	 * @throws ParseException
	 */
	public static Map<String,Object> getYesterday() throws ParseException{
		Calendar  calendar=Calendar.getInstance();
		DateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
		String d=fmt.format(calendar.getTime());
		Date date = fmt.parse(d);
		
		calendar.setTime(date);
		calendar.add(Calendar.DATE,-1);
		String d2=fmt.format(calendar.getTime());
		Date ye = fmt.parse(d2);
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("yesterday", ye);
		return map;
	}
	/**
	 * 获取当前日期，和时间，类型String
	 * @return
	 * @throws ParseException
	 */
	public static Map<String,Object> getCurrentDate() throws ParseException{
		Calendar  calendar=Calendar.getInstance();
		DateFormat fmt=new SimpleDateFormat("yyyyMMdd");
		String d=fmt.format(calendar.getTime());
		DateFormat fmat=new SimpleDateFormat("yyMMddHHmmss");
		String t=fmat.format(calendar.getTime());
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("currentDate", d);
		map.put("currentTime", t);
		return map;
	}
	

	public static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                "出生时间大于当前时间!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;//注意此处，如果不加1的话计算结果是错误的
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                //monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                } else {
                    //do nothing
                }
            } else {
                //monthNow>monthBirth
                age--;
            }
        } else {
            //monthNow<monthBirth
            //donothing
        }

        return age;
    }
	
	public static String MD5(String value) throws NoSuchAlgorithmException{
		//FBB9A43AAF72B914DAFF3BC30F166649
//		String result;
//		MessageDigest md;  
//		BASE64Encoder base64en = new BASE64Encoder();
//		md = MessageDigest.getInstance("MD5");   
//	    // 计算md5函数   
//	    md.update(value.getBytes());   
//	    // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符   
//	    // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值   
//	    result = new BigInteger(1, md.digest()).toString(16);   
//	    return result;
		MessageDigest messageDigest = null;  
		  
        try {  
            messageDigest = MessageDigest.getInstance("MD5");  
  
            messageDigest.reset();  
  
            messageDigest.update(value.getBytes("UTF-8"));  
        } catch (NoSuchAlgorithmException e) {  
            System.out.println("NoSuchAlgorithmException caught!");  
            System.exit(-1);  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
  
        byte[] byteArray = messageDigest.digest();  
  
        StringBuffer md5StrBuff = new StringBuffer();  
  
        for (int i = 0; i < byteArray.length; i++) {              
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)  
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));  
            else  
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));  
        }  
  
        return md5StrBuff.toString();  
	}
	/**
	   * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	   * 
	   * @param strDate
	   * @return
	   */
	public static Date strToDateLong(String strDate) {
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   ParsePosition pos = new ParsePosition(0);
	   Date strtodate = formatter.parse(strDate, pos);
	   return strtodate;
	}
	/**
	   * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	   * 
	   * @param dateDate
	   * @return
	   */
	public static String dateToStrLong(java.util.Date dateDate) {
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   String dateString = formatter.format(dateDate);
	   return dateString;
	}
	/**
	   * 将短时间格式时间转换为字符串 yyyy-MM-dd
	   * 
	   * @param dateDate
	   * @param k
	   * @return
	   */
	public static String dateToStr(java.util.Date dateDate) {
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	   String dateString = formatter.format(dateDate);
	   return dateString;
	}
	/**
	   * 将短时间格式字符串转换为时间 yyyy-MM-dd 
	   * 
	   * @param strDate
	   * @return
	   */
	public static Date strToDate(String strDate) {
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	   ParsePosition pos = new ParsePosition(0);
	   Date strtodate = formatter.parse(strDate, pos);
	   return strtodate;
	}
	
}