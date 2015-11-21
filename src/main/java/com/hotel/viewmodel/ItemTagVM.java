package com.hotel.viewmodel;

import java.util.List;

import com.hotel.model.ItemTag;

public class ItemTagVM extends ItemTag {
	
	private String text;
	private String state;
	private Boolean checked;
	private List<ItemTagVM> children;

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

	public List<ItemTagVM> getChildren() {
		return children;
	}

	public void setChildren(List<ItemTagVM> children) {
		this.children = children;
	}
	

}
