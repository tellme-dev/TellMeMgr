package com.hotel.modelVM;

import java.math.BigDecimal;

public class HotelListInfoVM {
	private Integer id;
    private String name;
    private String text;
    private String tel;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String imgUrl;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public BigDecimal getLongitude() {
		return longitude;
	}
	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	public BigDecimal getLatitude() {
		return latitude;
	}
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	public void clear(){
		if(name == null){
			name = "";
		}
		if(text == null){
			text = "";
		}
		if(tel == null){
			tel = "";
		}
		if(imgUrl == null){
			imgUrl = "";
		}
	}
}
