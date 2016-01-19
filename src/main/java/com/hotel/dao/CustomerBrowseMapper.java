package com.hotel.dao;

import java.util.List;
import java.util.Map;

import com.hotel.model.CustomerBrowse;

@MyBatisRepository
public interface CustomerBrowseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CustomerBrowse record);

    int insertSelective(CustomerBrowse record);

    CustomerBrowse selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CustomerBrowse record);

    int updateByPrimaryKey(CustomerBrowse record);
    
    int countByItemId(int itemId);
    
    int countByCustomer(int customerId);
    
    int countByBrowse(CustomerBrowse browse);
    
    CustomerBrowse selectByBrowse(CustomerBrowse browse);
    
    List<CustomerBrowse> getPageByCustomer(Map<String, Object> map);
    
    int deleteByItem(int targetId);
    int deleteById(int id);
    int resetById(int id);
}