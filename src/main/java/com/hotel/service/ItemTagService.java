package com.hotel.service;

import java.util.List;

import com.hotel.model.ItemTag;

public interface ItemTagService {

	List<ItemTag> getTagFromMin(int tagType);
}
