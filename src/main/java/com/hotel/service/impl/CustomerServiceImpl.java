package com.hotel.service.impl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.common.shiro.ShiroUsernamePasswordToken;
import com.hotel.dao.CustomerCollectionMapper;
import com.hotel.dao.CustomerMapper;
import com.hotel.model.Customer;
import com.hotel.model.CustomerCollection;
import com.hotel.service.CustomerService;
@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired CustomerMapper customerMapper;
	
	@Autowired CustomerCollectionMapper customerCollectionMapper;
	
	/**
	 * 
	 */
	@Override
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
	@Override
	public Customer getCustomerByMobile(String mobile) {
		// TODO Auto-generated method stub
		return customerMapper.getCustomerByMobile(mobile);
	}
	@Override
	public int insert(Customer c) {
		// TODO Auto-generated method stub
		return customerMapper.insert(c);
	}
	@Override
	public void saveCollection(CustomerCollection cc) {
		// TODO Auto-generated method stub
		customerCollectionMapper.insertSelective(cc);
	}

}
