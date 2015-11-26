package com.hotel.modelVM;

import java.util.List;

import com.hotel.model.Item;
import com.hotel.model.ItemDetail;
import com.hotel.model.ItemTag;

public class ItemVM extends Item{
	List<ItemDetail> itemDetails;
	ItemTag itemTag;

	public List<ItemDetail> getItemDetails() {
		return itemDetails;
	}

	public void setItemDetails(List<ItemDetail> itemDetails) {
		this.itemDetails = itemDetails;
	}

	public ItemTag getItemTag() {
		return itemTag;
	}

	public void setItemTag(ItemTag itemTag) {
		this.itemTag = itemTag;
	}
	
}
