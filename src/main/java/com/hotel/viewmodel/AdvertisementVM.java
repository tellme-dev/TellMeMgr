package com.hotel.viewmodel;

import java.util.List;

import com.hotel.model.AdDetail;
import com.hotel.model.Advertisement;

public class AdvertisementVM extends Advertisement {
	
    private String  createtime;
    
    private String hotelName;
    
    private String targetName;
    
    private List<AdvertisementVM> targetIds;
    
    private String imageUrl;
    
    private List<AdvertisementVM> imageUrlList;
    
    private String imageText;
    
    private List<AdvertisementVM> imageTextList;
    
    private AdDetail adDetail;
    
    private List<AdDetail> adDetailList;

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

	public List<AdvertisementVM> getTargetIds() {
		return targetIds;
	}

	public void setTargetIds(List<AdvertisementVM> targetIds) {
		this.targetIds = targetIds;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<AdvertisementVM> getImageUrlList() {
		return imageUrlList;
	}

	public void setImageUrlList(List<AdvertisementVM> imageUrlList) {
		this.imageUrlList = imageUrlList;
	}

	public String getImageText() {
		return imageText;
	}

	public void setImageText(String imageText) {
		this.imageText = imageText;
	}

	public List<AdvertisementVM> getImageTextList() {
		return imageTextList;
	}

	public void setImageTextList(List<AdvertisementVM> imageTextList) {
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
