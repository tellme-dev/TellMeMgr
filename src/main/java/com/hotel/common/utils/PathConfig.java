package com.hotel.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

public class PathConfig {
	private static final String ConfigPath = "tellmeapp.properties";  
	
	public static String getNewPathConfig(){
	        Properties prop = new Properties();  
	        String path=null;
	        FileInputStream fis=null;
	        String imagePath = "";
	        
	        try {
	            path = PathConfig.class.getClassLoader().getResource("").toURI().getPath()+"properties/";
	            fis = new FileInputStream(new File(path + ConfigPath));
	            prop.load(fis);
	            imagePath = prop.getProperty("image_path");
	        } catch (URISyntaxException e) {
	            e.printStackTrace();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }  
	        
	        return imagePath;
	    }

}
