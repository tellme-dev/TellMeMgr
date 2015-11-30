package com.hotel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.dao.SearchTextMapper;
import com.hotel.model.SearchText;
import com.hotel.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired SearchTextMapper searchTextMapper;
	@Override
	public int insertSearchText(SearchText record) {
		// TODO Auto-generated method stub
		return searchTextMapper.insert(record);
	}

}
