package com.hotel.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.common.ListResult;
import com.hotel.common.utils.Page;
import com.hotel.dao.BbsAttachMapper;
import com.hotel.dao.BbsCategoryMapper;
import com.hotel.dao.BbsMapper;
import com.hotel.model.Bbs;
import com.hotel.model.BbsCategory;
import com.hotel.modelVM.BbsVM;
import com.hotel.service.BbsService;

@Service("bbsService")
public class BbsServiceImpl implements BbsService {
	
	@Autowired BbsMapper bbsMapper;
	
	@Autowired BbsCategoryMapper bbsCategoryMapper;
	
	@Autowired BbsAttachMapper bbsAttachMapper;

	@Override
	public ListResult<BbsCategory> loadBbsCategoryList() {
		// TODO Auto-generated method stub
		List<BbsCategory> list = bbsCategoryMapper.selectCategoryList();
		ListResult<BbsCategory> result = new ListResult<BbsCategory>(list);
		return result;
	}

	@Override
	public ListResult<BbsVM> loadBbsListByType(Page page,int type) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("pageNo", page.getPageNo());
		map.put("pageSize", page.getPageSize());
		map.put("pageEnd",page.getPageSize()*page.getPageNo());
		map.put("parentId", 0);//加载主贴
		map.put("type", type);
		List<BbsVM> list = bbsMapper.selectByMap(map);
		int total = bbsMapper.countByMap(map);
		ListResult<BbsVM> result = new ListResult<BbsVM>(total,list);
		return result;
	}

	@Override
	public void saveBbs(BbsVM bbs) {
		// TODO Auto-generated method stub
		if(bbs.getBbsType()==1){//论坛
			//bbs.setId(0);
			if(bbs.getPostType() == 0){//发帖
				bbs.setCategoryId(1);
				bbs.setLevel(1);
				bbs.setCreateTime(new Date());
				bbs.setTimeStamp(new Date());
				bbsMapper.insertSelective(bbs);
				//bbsAttachMapper.insertSelective(record);
			}
			else if(bbs.getPostType() == 1){//回贴回复
				Bbs b = bbsMapper.selectByPrimaryKey(bbs.getParentId());
				bbsMapper.updateAnswerCount(bbs.getParentId());//更新父节点的回帖次数
				bbs.setCategoryId(b.getCategoryId());
				bbs.setLevel(b.getLevel()+1);
				if(b.getPath()!=null&&b.getPath().length()>0){
					bbs.setPath(b.getPath()+"."+b.getId());
				}else{
					bbs.setPath(b.getId().toString());
				}
				bbs.setCreateTime(new Date());
				bbs.setTimeStamp(new Date());
				bbsMapper.insertSelective(bbs);
			}
			else if(bbs.getPostType() == 2){//点赞 
				bbsMapper.updateAgreeCount(bbs.getId());
			}else{//分享 postType = 3
				bbsMapper.updateShareCount(bbs.getId());
			}
		}
	}
	
	@Override
	public ListResult<BbsVM> loadBbsChildren(Page page,Integer pid) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("pageNo", page.getPageNo());
		map.put("pageSize", page.getPageSize());
		map.put("pageEnd",page.getPageSize()*page.getPageNo());
		map.put("parentId", pid);
		List<BbsVM> ls = bbsMapper.selectByPid(map);
		int count = bbsMapper.countByMap(map);
		ListResult<BbsVM> result = new ListResult<BbsVM>(count,ls);
		bbsMapper.updateBrowseCount(pid);//更新浏览次数
		return result;
	}
	
	/*public ListResult<BbsVM> loadBbsTree(Page page,Integer pid) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("pageStart", page.getPageStart());
		map.put("pageSize", page.getPageSize());
		map.put("parentId", pid);
		List<BbsVM> ls = new ArrayList<BbsVM>();
		List<BbsVM> list = new ArrayList<BbsVM>();
		ls = bbsMapper.selectByPid(map); 
		int count = bbsMapper.countByMap(map);
		list = getItemTagNodes(ls);
		ListResult<BbsVM> result = new ListResult<BbsVM>(count,list);
		return result;
	}
	private List<BbsVM> getItemTagNodes(List<BbsVM> ls) {
		// TODO Auto-generated method stub
		List<BbsVM> list = new ArrayList<BbsVM>();
		for(BbsVM bbs:ls){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("parentId", bbs.getId());
			map.put("pageStart", 0);
			map.put("pageSize", 2);
			List<BbsVM> clist = bbsMapper.selectByPid(map);
			if(clist.size()>0){
				bbs.setChildren(getItemTagNodes(clist));
			}
			list.add(bbs);
		}
		return list;
	}*/

	@Override
	public BbsVM loadBbsById(Integer id) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return bbsMapper.selectBbsByMap(map);
	}

	@Override
	public List<BbsVM> loadBbsList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return bbsMapper.selectByMap(map);
	}

	@Override
	public List<BbsVM> fullTextSearchOfBbs(String text) {
		// TODO Auto-generated method stub
		return bbsMapper.fullTextSearchOfBbs(text);
	}

	@Override
	public int insert(Bbs record) {
		// TODO Auto-generated method stub
		return bbsMapper.insert(record);
	}

	@Override
	public int countByBbs(Bbs bbs) {
		// TODO Auto-generated method stub
		return bbsMapper.countByBbs(bbs);
	}

	@Override
	public void updateAgreeCount(Integer id) {
		// TODO Auto-generated method stub
		bbsMapper.updateAgreeCount(id);
	}

	@Override
	public int countPostByCustomer(int customerId) {
		// TODO Auto-generated method stub
		return bbsMapper.countPostByCustomer(customerId);
	}

	@Override
	public int countDynamicByCustomer(int customerId) {
		// TODO Auto-generated method stub
		return bbsMapper.countDynamicByCustomer(customerId);
	}

}
