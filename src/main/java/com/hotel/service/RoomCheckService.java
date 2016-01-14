package com.hotel.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hotel.model.RoomCheck;
import com.hotel.viewmodel.RoomCheckWebVM;

@Service
public interface RoomCheckService {
	
	int countHotelByCustomer(Integer customerId);

	List<RoomCheck> loadRoomCheckListByCustomerId(int customerId);

	/**
	 * jun
	 * @param map
	 * @return
	 */
	List<RoomCheckWebVM> getCheckPageList(Map<String, Object> map);

	int getCheckPageListCount(Map<String, Object> map);

	void saveorUpdateCheck(RoomCheckWebVM roomCheck);
}
