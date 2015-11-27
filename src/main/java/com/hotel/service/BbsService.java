package com.hotel.service;

import com.hotel.common.ListResult;
import com.hotel.model.BbsCategory;
import com.hotel.modelVM.BbsVM;

public interface BbsService {

	/**
	 * 获取社区分类标签
	 * @author jun
	 */
	ListResult<BbsCategory> loadBbsCategoryList();

	/**
	 * 获取社区帖子列表 
	 * @author jun
	 * @param categoryId
	 * @return 
	 */
	ListResult<BbsVM> loadBbsListByCategoryId(int categoryId);

	/**
	 * @author jun
	 * @param bbs
	 */
	void saveBbs(BbsVM bbs);

}
