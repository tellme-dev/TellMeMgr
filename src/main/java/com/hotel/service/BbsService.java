package com.hotel.service;

import java.util.List;
import java.util.Map;

import com.hotel.common.ListResult;
import com.hotel.common.utils.Page;
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
	 *  最新活动： type=1 ，热门话题：type=2 ,吐槽专区：type=3，达人推荐：type=4
	 * @author jun
	 * @param page 
	 * @param categoryId
	 * @return 
	 */
	ListResult<BbsVM> loadBbsListByType(Page page, int type);
	/**
	 * @author jun
	 * @return
	 */
	BbsVM loadBbsById(Integer id);

	/**
	 * @author jun
	 * @param bbs
	 */
	void saveBbs(BbsVM bbs);

	/**
	 * @author jun
	 * 加载bbs树形子节点
	 * @param page 
	 * @param pid
	 * @return
	 */
	ListResult<BbsVM> loadBbsChildren(Page page, Integer pid);

	/**
	 * （AdAction用到）
	 * @author jun
	 * @param map
	 * @return
	 */
	List<BbsVM> loadBbsList(Map<String, Object> map);

}
