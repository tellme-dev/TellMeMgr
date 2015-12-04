package com.hotel.service;

import java.util.List;
import java.util.Map;

import com.hotel.common.ListResult;
import com.hotel.common.utils.Page;
import com.hotel.model.Bbs;
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
	 * @param page 
	 * @param categoryId
	 * @return 
	 */
	ListResult<BbsVM> loadBbsListByCategoryId(Page page, int categoryId);
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

	/**
	 * 全文检索bbs
	 * @author hzf
	 * @param text
	 * @return
	 */
	List<BbsVM> fullTextSearchOfBbs(String text);
	
	/**
	 * 添加一条bbs记录
	 * @author LiuTaiXiong
	 * @param record
	 * @return
	 */
	int insert(Bbs record);
	
	/**
	 * 查询是否存在相同类型ID和关联的用户ID
	 * @author LiuTaiXiong
	 * @param bbs
	 * @return
	 */
	int countByBbs(Bbs bbs);
	
	/**
	 * 更新点赞数量
	 * @author LiuTaiXiong
	 * @param id
	 */
	void updateAgreeCount(Integer id);
	
	/**
	 * 获取主贴数量
	 * @author LiuTaiXiong
	 * @param customerId
	 */
	int countPostByCustomer(int customerId);
	
	/**
	 * 获取动态数量（回复、评价、分享、点赞）
	 * @author LiuTaiXiong
	 * @param customerId
	 */
	int countDynamicByCustomer(int customerId);

}
