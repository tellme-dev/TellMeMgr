package com.hotel.viewmodel;

import java.util.List;

import com.hotel.model.AdDetail;
import com.hotel.model.Advertisement;

public class AdvertisementWebVM extends Advertisement {
	
    private String  createtime;//时间格式转换
    
    private Integer sort;
    
    private Integer hotelId;
    
    private Integer bbsId;
    
    private String hotelName;
    
    private String targetName;
    
    private String bbsName;
    
    private String bbsText;
    
    private List<Integer> targetIds;
    
    private String imageUrl;
    
    private List<String> imageUrlList;
    
    private String imageText;
    
    private List<String> imageTextList;
    
    private AdDetail adDetail;
    
    private String adDetailIds;
    
    private String delAdDetailIds;
    
    private List<AdDetail> adDetailList;
    
	public String getBbsText() {
		return bbsText;
	}

	public void setBbsText(String bbsText) {
		this.bbsText = bbsText;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getBbsId() {
		return bbsId;
	}

	public void setBbsId(Integer bbsId) {
		this.bbsId = bbsId;
	}

	public String getBbsName() {
		return bbsName;
	}

	public void setBbsName(String bbsName) {
		this.bbsName = bbsName;
	}

	public String getDelAdDetailIds() {
		return delAdDetailIds;
	}

	public void setDelAdDetailIds(String delAdDetailIds) {
		this.delAdDetailIds = delAdDetailIds;
	}

	public Integer getHotelId() {
		return hotelId;
	}

	public void setHotelId(Integer hotelId) {
		this.hotelId = hotelId;
	}

	public String getAdDetailIds() {
		return adDetailIds;
	}

	public void setAdDetailIds(String adDetailIds) {
		this.adDetailIds = adDetailIds;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public List<Integer> getTargetIds() {
		return targetIds;
	}

	public void setTargetIds(List<Integer> targetIds) {
		this.targetIds = targetIds;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<String> getImageUrlList() {
		return imageUrlList;
	}

	public void setImageUrlList(List<String> imageUrlList) {
		this.imageUrlList = imageUrlList;
	}

	public String getImageText() {
		return imageText;
	}

	public void setImageText(String imageText) {
		this.imageText = imageText;
	}

	public List<String> getImageTextList() {
		return imageTextList;
	}

	public void setImageTextList(List<String> imageTextList) {
		this.imageTextList = imageTextList;
	}

	public AdDetail getAdDetail() {
		return adDetail;
	}

	public void setAdDetail(AdDetail adDetail) {
		this.adDetail = adDetail;
	}

	public List<AdDetail> getAdDetailList() {
		return adDetailList;
	}

	public void setAdDetailList(List<AdDetail> adDetailList) {
		this.adDetailList = adDetailList;
	}
}
