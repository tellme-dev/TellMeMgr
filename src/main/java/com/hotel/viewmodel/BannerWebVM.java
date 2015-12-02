package com.hotel.viewmodel;

import com.hotel.model.Banner;

public class BannerWebVM extends Banner {
	private String adName;
	private String adIds;
	private String position;
	private String createtime;//字符串格式
	
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getAdName() {
		return adName;
	}
	public void setAdName(String adName) {
		this.adName = adName;
	}
	public String getAdIds() {
		return adIds;
	}
	public void setAdIds(String adIds) {
		this.adIds = adIds;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
}
