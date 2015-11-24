package com.hotel.modelVM;

import com.hotel.model.Item;
import com.hotel.model.ItemDetail;

public class ItemDetailVM extends ItemDetail {
	private Item item;

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
}
