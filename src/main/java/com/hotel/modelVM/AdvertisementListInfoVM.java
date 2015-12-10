package com.hotel.modelVM;

import com.hotel.model.Advertisement;


public class AdvertisementListInfoVM extends Advertisement{
    private String imgUrl;
    
	private String text;

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public void setAdvertisement(Advertisement advertisement){
		setId(advertisement.getId());
		setName(advertisement.getName());
		setKeyWord(advertisement.getKeyWord());
		setTargetType(advertisement.getTargetType());
		setTargetId(advertisement.getTargetId());
		setTargetContent(advertisement.getTargetContent());
		setIsUsed(advertisement.getIsUsed());
		setCreateTime(advertisement.getCreateTime());
		setTimeStamp(advertisement.getTimeStamp());
	}
}
