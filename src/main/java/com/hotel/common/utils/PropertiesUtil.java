package com.hotel.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

	public static String getParamsProperty(String name) {
//		System.out.println("------------"+getProjectPath()
//				+ "WEB-INF/classes/params.properties");
		Properties p = new Properties();
		InputStream input;
		try {
			input = new FileInputStream(new File(getProjectPath()
					+ "WEB-INF/classes/params.properties"));
			p.load(input);
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String param = p.getProperty(name);
		return param;
	}

	public static String getProjectPath() {
		String nowpath; // 当前tomcat的bin目录的路径 如
						// D:\java\software\apache-tomcat-6.0.14\bin
		String tempdir;
		nowpath = System.getProperty("user.dir");
		tempdir = nowpath.replace("bin", "webapps"); // 把bin 文件夹变到 webapps文件里面
		tempdir += "/police/"; // 拼成D:\java\software\apache-tomcat-6.0.14\webapps\sz_pro
		return tempdir;
	}

	public static void main(String[] args) {

		System.out.println(getProjectPath()
				+ "WEB-INF/classes/params.properties"); // Class文件所在路径
		
		System.out.println(getParamsProperty("BLOWFISHCODE"));
		System.out.println(getParamsProperty("OPENFIRE_IP"));
		System.out.println(getParamsProperty("VIDEO_ACCESSIP"));
		System.out.println(getParamsProperty("VIDEO_ACCESSPORT"));
	}

}
