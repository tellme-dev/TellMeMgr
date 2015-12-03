package com.hotel.dao;

import com.hotel.model.Customer;

@MyBatisRepository
public interface CustomerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);

	Customer getCustomerByMobile(String mobile);
	
	int updatePassword(Customer record);
}