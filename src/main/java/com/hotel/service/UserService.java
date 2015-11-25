package com.hotel.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.hotel.common.ReturnResult;
import com.hotel.model.User;
import com.hotel.viewmodel.UserVM;

public interface UserService {

    int deleteUser(Integer id);

    int saveUser(User record);
    
    int updateUser(User record);
    
    
    /////
    List<User> getUserPageList(Map<String, Object> map);
    
    int getUserPageListCount(Map<String, Object> map);

    ReturnResult<User> getUserById(Integer id);

    User getUserByName(String username);
    
	public ReturnResult<User> login(String loginName, String passwd,boolean rememberMe) throws Exception;

	void saveorUpdateUser(User user) throws ParseException;

	UserVM getUserByPrimaryKey(Integer userId);

	UserVM getUserByID(Integer userId);

	void updateUserByIds(String userIds);

//	List<UserFunction> getUserFunListByUserId(Integer userId);
//
//	List<UserType> getUserTypeListByUserId(Integer userId);
	
}
