package com.hotel.modelVM;

import java.math.BigDecimal;
import java.util.List;

public class HotelInfoVM {
	private Integer id;
    private String name;
    private String text;
    private String tel;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String address;
    private BigDecimal score;
    private List<ImageVM> imgUrl;
    private int countBrowse;
    private int countCollection;
    
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public BigDecimal getScore() {
		return score;
	}
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	public List<ImageVM> getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(List<ImageVM> imgUrl) {
		this.imgUrl = imgUrl;
	}
	public int getCountBrowse() {
		return countBrowse;
	}
	public void setCountBrowse(int countBrowse) {
		this.countBrowse = countBrowse;
	}
	public int getCountCollection() {
		return countCollection;
	}
	public void setCountCollection(int countCollection) {
		this.countCollection = countCollection;
	}
}
