package com.hotel.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hotel.model.ItemTagAssociation;

@Service
public interface ItemTagAssociationService {

	List<ItemTagAssociation> getAssociationListByItemTagId(int tagId);

}
