package com.hotel.service;

import org.springframework.stereotype.Service;

import com.hotel.model.SearchText;

@Service
public interface SearchService {
	int insertSearchText(SearchText record);
}
