package com.hotel.modelVM;

import com.hotel.model.AdDetail;
import com.hotel.model.Advertisement;

public class AdDetailVM extends AdDetail{
	private Advertisement ad;

	public Advertisement getCustomer() {
		return ad;
	}

	public void setCustomer(Advertisement ad) {
		this.ad = ad;
	}
	
}
