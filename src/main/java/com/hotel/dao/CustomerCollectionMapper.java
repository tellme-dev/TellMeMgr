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
    
    int countByCustomer(int customerId);
    
    List<CustomerCollection> getPageCollectionByCustomer(Map<String, Object> map);
}