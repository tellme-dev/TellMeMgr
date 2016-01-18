package com.hotel.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotel.dao.CustomerBrowseMapper;
import com.hotel.model.CustomerBrowse;
import com.hotel.service.CustomerBrowseService;

@Transactional
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

	@Override
	public int countByCustomer(int customerId) {
		// TODO Auto-generated method stub
		return customerBrowseMapper.countByCustomer(customerId);
	}

	@Override
	public List<CustomerBrowse> getPageByCustomer(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return customerBrowseMapper.getPageByCustomer(map);
	}

	@Override
	public int deleteByItem(int targetId) {
		// TODO Auto-generated method stub
		return customerBrowseMapper.deleteByItem(targetId);
	}

	@Override
	public int deleteById(int id) {
		// TODO Auto-generated method stub
		return customerBrowseMapper.deleteById(id);
	}

	@Override
	public int countByBrowse(CustomerBrowse browse) {
		// TODO Auto-generated method stub
		return customerBrowseMapper.countByBrowse(browse);
	}


}
