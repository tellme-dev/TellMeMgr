package com.hotel.model;

import java.util.List;

public class ItemMg {
    private Item item;
    private List<ItemDetail> details;

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public List<ItemDetail> getDetails() {
		return details;
	}

	public void setDetails(List<ItemDetail> details) {
		this.details = details;
	}
}