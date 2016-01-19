package com.hotel.dao;

import java.util.List;
import java.util.Map;

import com.hotel.model.CustomerCollection;

@MyBatisRepository
public interface CustomerCollectionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CustomerCollection record);

    int insertSelective(CustomerCollection record);

    CustomerCollection selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CustomerCollection record);

    int updateByPrimaryKey(CustomerCollection record);
    
    int countByItemId(int itemId);
    
    int countByCustomerCollection(CustomerCollection record);
    
    CustomerCollection selectByCustomerCollection(CustomerCollection record);
    
    int countByCustomer(int customerId);
    
    List<CustomerCollection> getPageCollectionByCustomer(Map<String, Object> map);
    
    int deleteByItem(int targetId);
    
    int deleteById(int id);
    
    int resetById(int id);
}