package com.hotel.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotel.dao.CustomerBrowseMapper;
import com.hotel.model.CustomerBrowse;
import com.hotel.service.CustomerBrowseService;

@Service("customerBrowseService")
public class CustomerBrowseServiceImpl implements CustomerBrowseService{
	
	@Resource
	private CustomerBrowseMapper customerBrowseMapper;

	@Override
	public int insert(CustomerBrowse record) {
		// TODO Auto-generated method stub
		return customerBrowseMapper.insert(record);
	}

	@Override
	public int countByItemId(int itemId) {
		// TODO Auto-generated method stub
		return customerBrowseMapper.countByItemId(itemId);
	}


}
