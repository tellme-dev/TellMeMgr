package com.hotel.modelVM;

import com.hotel.model.Region;

public class RegionVM extends Region{
	private String pinyin;
	private String firstChar;
	
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	public String getFirstChar() {
		return firstChar;
	}
	public void setFirstChar(String firstChar) {
		this.firstChar = firstChar;
	}
	

}
