package com.hotel.dao;

import java.util.List;

import com.hotel.model.Rcu;
import com.hotel.modelVM.RcuVM;

@MyBatisRepository
public interface RcuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Rcu record);

    int insertSelective(Rcu record);

    Rcu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Rcu record);

    int updateByPrimaryKey(Rcu record);

	List<RcuVM> getRCUsRoomId(int roomId);
}