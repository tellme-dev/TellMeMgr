package com.hotel.modelVM;


public class CustomerBrowseVM {
	private int type;
	private HotelListInfoVM hotel;
	private AdvertisementListInfoVM advertisement;
	private BbsVM bbs;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public HotelListInfoVM getHotel() {
		return hotel;
	}
	public void setHotel(HotelListInfoVM hotel) {
		this.hotel = hotel;
	}
	public AdvertisementListInfoVM getAdvertisement() {
		return advertisement;
	}
	public void setAdvertisement(AdvertisementListInfoVM advertisement) {
		this.advertisement = advertisement;
	}
	public BbsVM getBbs() {
		return bbs;
	}
	public void setBbs(BbsVM bbs) {
		this.bbs = bbs;
	}
}
