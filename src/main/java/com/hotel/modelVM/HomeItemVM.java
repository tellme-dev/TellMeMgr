package com.hotel.modelVM;

import java.util.ArrayList;
import java.util.List;
/**
 * 首页菜单的封装结构
 * @author seeLittleGirlAgain
 *
 */
public class HomeItemVM {
	private int itemTagId;
	private List<String> imageUrls;
	private String name;
	public int getItemTagId() {
		return itemTagId;
	}
	public void setItemTagId(int itemTagId) {
		this.itemTagId = itemTagId;
	}
	public List<String> getImageUrls() {
		return imageUrls;
	}
	public void setImageUrls(List<String> imageUrls) {
		this.imageUrls = imageUrls;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public HomeItemVM(String name){
		this.itemTagId = 0;//0针对是"更多"
		this.name = name;
		this.imageUrls = new ArrayList<String>();
	}
	
	public HomeItemVM(int itemTagId,String name){
		this.itemTagId = itemTagId;
		this.name = name;
		this.imageUrls = new ArrayList<String>();
	}

}
