package com.hotel.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.dao.RoomCheckMapper;
import com.hotel.model.RoomCheck;
import com.hotel.service.RoomCheckService;
import com.hotel.viewmodel.RoomCheckWebVM;
@Service
public class RoomCheckServiceImpl implements RoomCheckService {

	@Autowired RoomCheckMapper roomCheckMapper;

	@Override
	public int countHotelByCustomer(Integer customerId) {
		// TODO Auto-generated method stub
		return roomCheckMapper.countHotelByCustomer(customerId);
	}

	@Override
	public List<RoomCheck> loadRoomCheckListByCustomerId(int customerId) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("customerId", customerId);
		return roomCheckMapper.selectByMap(map);
	}

	@Override
	public List<RoomCheckWebVM> getCheckPageList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return roomCheckMapper.getCheckPageList(map);
	}

	@Override
	public int getCheckPageListCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return roomCheckMapper.getCheckPageListCount(map);
	}

	@Override
	public void saveorUpdateCheck(RoomCheckWebVM roomCheck) {
		// TODO Auto-generated method stub
		if(roomCheck.getId() == 0){
			roomCheck.setCheckinTime(new Date());
			roomCheckMapper.insertSelective(roomCheck);
		}
	}
}
