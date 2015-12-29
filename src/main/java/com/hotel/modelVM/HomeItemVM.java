package com.hotel.modelVM;

import java.util.ArrayList;
import java.util.List;

import com.hotel.model.SwiperHotelItem;
/**
 * 首页菜单的封装结构
 * @author seeLittleGirlAgain
 *
 */
public class HomeItemVM {
	private int itemTagId;
	private List<SwiperHotelItem> hotelItems;
	private String name;
	private String defaultImageUrl;
	
	public String getDefaultImageUrl() {
		return defaultImageUrl;
	}
	public void setDefaultImageUrl(String defaultImageUrl) {
		this.defaultImageUrl = defaultImageUrl;
	}
	public int getItemTagId() {
		return itemTagId;
	}
	public void setItemTagId(int itemTagId) {
		this.itemTagId = itemTagId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public List<SwiperHotelItem> getHotelItems() {
		return hotelItems;
	}
	public void setHotelItems(List<SwiperHotelItem> hotelItems) {
		this.hotelItems = hotelItems;
	}
	public HomeItemVM(String name){
		this.itemTagId = 0;//0针对是"更多"
		this.name = name;
		this.defaultImageUrl = "app/images/b.png";
		this.hotelItems = new ArrayList<SwiperHotelItem>();
	}
	
	public HomeItemVM(int itemTagId,String name){
		this.itemTagId = itemTagId;
		this.name = name;
		this.defaultImageUrl = "app/images/b.png";
		this.hotelItems = new ArrayList<SwiperHotelItem>();
	}

}
