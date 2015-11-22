package com.hotel.dao;

import java.util.List;
import java.util.Map;

import com.hotel.model.User;
import com.hotel.viewmodel.UserVM;

@MyBatisRepository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    UserVM selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    //新添加的方法
	User getUserByName(String username);

	List<User> getUserPageList(Map<String, Object> map);

	int getUserPageListCount(Map<String, Object> map);

	UserVM selectByID(Integer userId);

	void deleteByIds(Map<String, Object> map);

}