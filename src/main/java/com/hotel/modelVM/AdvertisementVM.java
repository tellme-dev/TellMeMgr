package com.hotel.modelVM;

import java.util.List;

import com.hotel.model.AdDetail;
import com.hotel.model.Advertisement;


public class AdvertisementVM extends Advertisement{
	private List<AdDetail> adDetails;

	public List<AdDetail> getAdDetailList() {
		return adDetails;
	}

	public void setAdDetailList(List<AdDetail> adDetailList) {
		this.adDetails = adDetailList;
	}
	
}
