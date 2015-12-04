package com.hotel.dao;

import java.util.List;
import java.util.Map;

import com.hotel.model.Bbs;
import com.hotel.modelVM.BbsVM;

@MyBatisRepository
public interface BbsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Bbs record);

    int insertSelective(BbsVM record);

    Bbs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Bbs record);

    int updateByPrimaryKey(Bbs record);

	List<BbsVM> selectByMap(Map<String, Object> map);

	List<BbsVM> selectByPid(Map<String, Object> map);

	int countByMap(Map<String, Object> map);
	
	BbsVM selectBbsByMap(Map<String, Object> map);

	void updateAnswerCount(Integer id);

	void updateAgreeCount(Integer id);
	
	void updateShareCount(Integer id);

	void updateBrowseCount(Integer id);

	List<BbsVM> fullTextSearchOfBbs(String text);
	
	int countByBbs(Bbs bbs);
	
	int countPostByCustomer(int customerId);
	
	int countDynamicByCustomer(int customerId);
}