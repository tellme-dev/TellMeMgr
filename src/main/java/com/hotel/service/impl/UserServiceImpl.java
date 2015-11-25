package com.hotel.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.hotel.common.ReturnResult;
import com.hotel.common.shiro.ShiroUsernamePasswordToken;
import com.hotel.common.utils.EndecryptUtils;
import com.hotel.common.utils.GeneralUtil;
import com.hotel.dao.UserMapper;
import com.hotel.model.User;
import com.hotel.service.UserService;
import com.hotel.viewmodel.UserVM;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;
	
	@Override
	public int deleteUser(Integer id) {
		// TODO Auto-generated method stub
		return userMapper.deleteByPrimaryKey(id);
	}


	@Override
	public User getUserByName(String username) {
		// TODO Auto-generated method stub
		return userMapper.getUserByName(username);
	}

	@Override
	public ReturnResult<User> login(String loginName, String passwd,boolean rememberMe)
			throws Exception {

		Subject subject = SecurityUtils.getSubject();
		ReturnResult<User> res = new ReturnResult<User>();
		try {
			User u = userMapper.getUserByName(loginName);
			if (null == u) {
				res.setCode(ReturnResult.FAILURE);
				res.setMessage("用户[" + loginName + "]不存在！");
			} else {
				ShiroUsernamePasswordToken token  = new ShiroUsernamePasswordToken(u.getName(), passwd, u.getPsd(), u.getSalt(),null);
				token.setRememberMe(rememberMe);
				subject.login(token);
				if (subject.isAuthenticated()) {
					res.setCode(ReturnResult.SUCCESS);
					res.setMessage("登录成功！");
					res.setResultObject(u);
				} else {
					res.setCode(ReturnResult.FAILURE);
					res.setMessage("登录失败，密码错误。");
				}
			}
		} catch (ExcessiveAttemptsException ex) {
			res.setCode(ReturnResult.FAILURE);
			res.setMessage("登录失败，未知错误。");
		} catch (AuthenticationException ex) {
			res.setCode(ReturnResult.FAILURE);
			res.setMessage("登录失败，密码错误。");
		}
		return res;
	}


	@Override
	public int saveUser(User record) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int updateUser(User record) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public List<User> getUserPageList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<User> userlist = userMapper.getUserPageList(map);
		/*转换时间格式*/
		List<User> list = new ArrayList<User>();
		for(int i=0;i<userlist.size();i++){
			User u = userlist.get(i);
			Date createTime = u.getCreateTime();
			String cteatetime = GeneralUtil.dateToStrLong(createTime);
			u.setCreatetime(cteatetime);
			list.add(u);
		}
		return list;
	}


	@Override
	public int getUserPageListCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return userMapper.getUserPageListCount(map);
	}


	@Override
	public ReturnResult<User> getUserById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void saveorUpdateUser(User user) throws ParseException {
		// TODO Auto-generated method stub
		if(user.getId()>0){
			userMapper.updateByPrimaryKeySelective(user);
		}else{
			User u = EndecryptUtils.md5Password(user.getName(), "111111");
			if(u!=null){
				user.setPsd(u.getPsd());
				user.setSalt(u.getSalt());
			}
			user.setIsUsed(true);
			Map<String,Object> m = GeneralUtil.getCurrentTime();
			Date time = (Date) m.get("currentTime");
			user.setCreateTime(time);
			userMapper.insert(user);
		}
	}


	@Override
	public UserVM getUserByPrimaryKey(Integer userId) {
		// TODO Auto-generated method stub
		return userMapper.selectByPrimaryKey(userId);
	}


	@Override
	public UserVM getUserByID(Integer userId) {
		// TODO Auto-generated method stub
		return userMapper.selectByID(userId);
	}


	@Override
	public void updateUserByIds(String userIds) {
		// TODO Auto-generated method stub
		/*将"1,2,3"格式的字符串转换为List<Integer>*/
		String[] str = userIds.split(",");
		Integer array[] = new Integer[str.length];  
		for(int i=0;i<str.length;i++){  
		    array[i]=Integer.parseInt(str[i]);
		}
		List<Integer> ids = Arrays.asList(array);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ids", ids);
		map.put("isUsed", false);
		userMapper.updateByIds(map);
	}

}
