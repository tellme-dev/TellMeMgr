/**
 *
 */
package com.hotel.common.utils.app;

import java.util.ResourceBundle;

public class Parameter {

	public static int maxUpPicSize; //文件上传大小
	public static int maxUpFileSize; //文件上传大小
	public static int maxUpShowPicSize; //上传大小

	public static String imageSaveRootPath;// 图片存储根路径
	public static String imageDeleteSwitch;// 图片定时删除
	public static int imageSaveMonth;// 图片保存的月份
	public static String needThumbnail;// 是否�?��缩略�?

	//static private Properties props = new Properties();

	static {
		/**
		 * InputStream propStream =
		 * Parameter.class.getResourceAsStream("/config/properties/imageserviceconfig.properties");
		 * if (propStream == null) { throw new ExceptionInInitializerError("Can
		 * not find imageserviceconfig.properties"); } try{
		 * props.load(propStream); }catch(IOException ex) { throw new
		 * ExceptionInInitializerError("Load imageserviceconfig.properties data
		 * error."); }
		 * 
		 * 
		 * String value = props.getProperty("image.save.rootpath"); if (value ==
		 * null) { throw new IllegalArgumentException("Can not get
		 * image.save.rootpath."); } else {
		 * System.out.println("imageSaveRootPath=["+value+"]");
		 * imageSaveRootPath = value; }
		 * 
		 * value = props.getProperty("image.save.month"); if (value == null) {
		 * throw new IllegalArgumentException("Can not get image.save.month."); }
		 * else { System.out.println("imageSaveMonth=["+value+"]");
		 * imageSaveMonth = Integer.valueOf(value).intValue(); }
		 * 
		 * value = props.getProperty("image.delete.switch"); if (value == null) {
		 * throw new IllegalArgumentException("Can not get
		 * image.delete.switch."); } else {
		 * System.out.println("imageDeleteSwitch=["+value+"]");
		 * imageDeleteSwitch = value; }
		 * 
		 * value = props.getProperty("need.thumbnail"); if (value == null) {
		 * throw new IllegalArgumentException("Can not get need.thumbnail."); }
		 * else { System.out.println("needThumbnail=["+value+"]"); needThumbnail =
		 * value; }
		 */
		ResourceBundle resource = ResourceBundle
				.getBundle("imageserviceconfig");
		if (resource == null) {
			throw new ExceptionInInitializerError(
					"Can not find imageserviceconfig.properties");
		}

		String value = resource.getString("image.save.rootpath");
		System.out.println("imageSaveRootPath=[" + imageSaveRootPath + "]");
		imageSaveRootPath = value;

		value = resource.getString("image.save.month");
		System.out.println("imageSaveMonth=[" + value + "]");
		imageSaveMonth = Integer.valueOf(value).intValue();

		value = resource.getString("image.delete.switch");
		System.out.println("imageDeleteSwitch=[" + value + "]");
		imageDeleteSwitch = value;

		value = resource.getString("need.thumbnail");
		System.out.println("needThumbnail=[" + value + "]");
		needThumbnail = value;
	}

}
