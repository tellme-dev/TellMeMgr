package com.hotel.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hotel.modelVM.RcuVM;

@Service
/**
 * RCU服务
 * @author hzf
 *
 */
public interface RcuService {
	/**
	 * @author hzf
	 * @param roomId
	 * @return
	 */
	List<RcuVM> getRCUsByRoomId(int roomId);
}
