package com.hotel.modelVM;

import java.math.BigDecimal;
import java.util.List;

import com.hotel.model.Item;

public class HotelListInfoVM {
	private Integer id;
    private String name;
    private String logo;
    private String text;
    private String tel;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String imgUrl;
    private String address;
    private String city;
    private BigDecimal score;
    private int countBrowse;
    private int countCollection;
    private List<Item> projects;
    
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
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
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
	public List<Item> getProjects() {
		return projects;
	}
	public void setProjects(List<Item> projects) {
		this.projects = projects;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
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
		if(address == null){
			address = "";
		}
		if(city == null){
			city = "";
		}
	}
}
