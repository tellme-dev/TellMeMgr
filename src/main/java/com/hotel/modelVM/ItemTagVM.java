package com.hotel.modelVM;


import java.util.List;

import com.hotel.model.Item;
import com.hotel.model.ItemTag;

public class ItemTagVM extends ItemTag{
	private List<Item> itemList;

	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}
	
}
