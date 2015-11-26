package com.hotel.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.common.ListResult;
import com.hotel.dao.BbsCategoryMapper;
import com.hotel.dao.BbsMapper;
import com.hotel.model.Bbs;
import com.hotel.model.BbsCategory;
import com.hotel.modelVM.BbsVM;
import com.hotel.service.BbsService;

@Service
public class BbsServiceImpl implements BbsService {
	
	@Autowired BbsMapper bbsMapper;
	
	@Autowired BbsCategoryMapper bbsCategoryMapper;

	@Override
	public ListResult<BbsCategory> loadBbsCategoryList() {
		// TODO Auto-generated method stub
		return bbsCategoryMapper.selectCategoryList();
	}

	@Override
	public ListResult<BbsVM> loadBbsListByCategoryId(int categoryId) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("categoryId", categoryId);
		List<BbsVM> list = bbsMapper.selectByMap(map);
		ListResult<BbsVM> result = new ListResult<BbsVM>(list);
		return result;
	}

	@Override
	public void saveBbs(BbsVM bbs) {
		// TODO Auto-generated method stub
		
	}
	

}
