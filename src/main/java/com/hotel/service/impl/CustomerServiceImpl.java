package com.hotel.service.impl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.common.shiro.ShiroUsernamePasswordToken;
import com.hotel.dao.CustomerMapper;
import com.hotel.model.Customer;

import com.hotel.service.CustomerService;
@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired CustomerMapper customerMapper;
	/**
	 * 
	 */
	public int login(String mobile, String password) {
		Subject subject = SecurityUtils.getSubject();
		try {
			Customer c = customerMapper.getCustomerByMobile(mobile);
			if (null == c) {
				return 2;
			} else {
				ShiroUsernamePasswordToken token  = new ShiroUsernamePasswordToken(c.getMobile(), password, c.getPsd(), c.getSalt(),null);
				subject.login(token);
				if (subject.isAuthenticated()) {
					return 1;
				} else {
					return 3;
				}
			}
		} catch (ExcessiveAttemptsException ex) {
			return 4;
		} catch (AuthenticationException ex) {
			return 3;
		}
	}

}