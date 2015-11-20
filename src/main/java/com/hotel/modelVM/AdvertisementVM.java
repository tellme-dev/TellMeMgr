package com.hotel.modelVM;

import java.util.List;

import com.hotel.model.AdDetail;
import com.hotel.model.Advertisement;


public class AdvertisementVM extends Advertisement{
	private List<AdDetail> adDetailList;

	public List<AdDetail> getAdDetailList() {
		return adDetailList;
	}

	public void setAdDetailList(List<AdDetail> adDetailList) {
		this.adDetailList = adDetailList;
	}
	
}
