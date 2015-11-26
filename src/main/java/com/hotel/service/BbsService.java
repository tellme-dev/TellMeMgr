package com.hotel.service;

import com.hotel.common.ListResult;
import com.hotel.model.Bbs;
import com.hotel.model.BbsCategory;

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
	ListResult<Bbs> loadBbsListByCategoryId(int categoryId);

}
