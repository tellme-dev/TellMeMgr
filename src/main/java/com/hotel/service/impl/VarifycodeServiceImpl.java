package com.hotel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.dao.VarifycodeMapper;
import com.hotel.model.Varifycode;
import com.hotel.service.VarifycodeService;
@Service
public class VarifycodeServiceImpl implements VarifycodeService {

	@Autowired VarifycodeMapper varifycodeMapper;

	/**
	 * @author hzf
	 */
	public int insert(Varifycode v) {
		Varifycode temp = varifycodeMapper.selectByMobile(v.getMobile());
		if(temp==null){
			return varifycodeMapper.insert(v);
		}else{
			v.setId(temp.getId());
			return varifycodeMapper.updateByPrimaryKey(v);
		}
		
	}

	/**
	 * @author hzf
	 */
	public Varifycode selectByMobile(String mobile) {
		return varifycodeMapper.selectByMobile(mobile);
	}

}
