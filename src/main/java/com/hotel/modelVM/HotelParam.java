package com.hotel.modelVM;

import java.math.BigDecimal;
/**
 * 
 * @author hzf
 * 存取'酒店'itemid,酒店名称和得分
 */
public class HotelParam {
	private int id;
	private int hotelId;
	private String name;
	private BigDecimal score;
	private String imageUrl;
	
	public int getHotelId() {
		return hotelId;
	}
	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getScore() {
		return score;
	}
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	
}
