package com.hotel.service;

import java.text.ParseException;
import java.util.List;

import com.hotel.common.ReturnResult;
import com.hotel.model.User;

public interface UserService {

    int deleteUser(Integer id);

    int saveUser(User record);
    
    int updateUser(User record);
    
    
    /////
    List<User> getUserPageList(User example);
    
    int getUserPageListCount(User example);

    ReturnResult<User> getUserById(Integer id);

    User getUserByName(String username);
    
	public ReturnResult<User> login(String loginName, String passwd,boolean rememberMe) throws Exception;

	void saveorUpdateUser(User user) throws ParseException;

	User getUserByPrimaryKey(Integer userId);

//	List<UserFunction> getUserFunListByUserId(Integer userId);
//
//	List<UserType> getUserTypeListByUserId(Integer userId);
	
}
