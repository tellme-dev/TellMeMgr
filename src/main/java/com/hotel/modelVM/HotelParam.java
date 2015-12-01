package com.hotel.modelVM;

import java.math.BigDecimal;
/**
 * 
 * @author hzf
 * 存取'酒店'itemid,酒店名称和得分
 */
public class HotelParam {
	private int id;
	private String name;
	private BigDecimal score;
	private String imageUrl;
	
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
