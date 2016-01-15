package com.hotel.dao;

import java.util.List;

import com.hotel.model.Room;

@MyBatisRepository
public interface RoomMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Room record);

    int insertSelective(Room record);

    Room selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Room record);

    int updateByPrimaryKey(Room record);

	List<Room> selectRoomList(Integer hotelId);
}