package com.hotel.service;

import org.springframework.stereotype.Service;

import com.hotel.model.Varifycode;

@Service
public interface VarifycodeService {
	/**
	 * 插入电话和验证码
	 * @param v
	 * @return
	 */
	int insert(Varifycode v);
	Varifycode selectByMobile(String mobile);
}
