package com.hotel.modelVM;

import java.util.List;

import com.hotel.model.Item;
import com.hotel.model.ItemDetail;
import com.hotel.model.ItemTag;

public class ItemVM extends Item{
	List<ItemDetail> itemDetails;
	List<ItemTag> itemTags;

	public List<ItemTag> getItemTags() {
		return itemTags;
	}

	public void setItemTags(List<ItemTag> itemTags) {
		this.itemTags = itemTags;
	}

	public List<ItemDetail> getItemDetails() {
		return itemDetails;
	}

	public void setItemDetails(List<ItemDetail> itemDetails) {
		this.itemDetails = itemDetails;
	}

	
}
