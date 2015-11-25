package com.hotel.viewmodel;

import java.util.List;

import com.hotel.model.ItemTag;

public class ItemTagWebVM extends ItemTag {
	
	private String text;
	private String state;
	private Boolean checked;
	private List<ItemTagWebVM> children;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public List<ItemTagWebVM> getChildren() {
		return children;
	}

	public void setChildren(List<ItemTagWebVM> children) {
		this.children = children;
	}
	

}
