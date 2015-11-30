package com.hotel.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotel.dao.CustomerCollectionMapper;
import com.hotel.model.CustomerCollection;
import com.hotel.service.CustomerCollectionService;

@Service("customerCollectionService")
public class CustomerCollectionServiceImpl implements CustomerCollectionService{
	
	@Resource
	private CustomerCollectionMapper customerCollectionMapper;

	@Override
	public int insert(CustomerCollection record) {
		// TODO Auto-generated method stub
		return customerCollectionMapper.insert(record);
	}

	@Override
	public int countByItemId(int itemId) {
		// TODO Auto-generated method stub
		return customerCollectionMapper.countByItemId(itemId);
	}
}
