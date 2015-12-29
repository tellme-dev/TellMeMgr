package com.hotel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.dao.RcuMapper;
import com.hotel.modelVM.RcuVM;
import com.hotel.service.RcuService;
@Service
/**
 * 
 * @author hzf
 *
 */
public class RcuServiceImpl implements RcuService {
	
	@Autowired RcuMapper rcuMapper;
	
	@Override
	public List<RcuVM> getRCUsByRoomId(int roomId) {
		// TODO Auto-generated method stub
		return rcuMapper.getRCUsRoomId(roomId);
	}

}
