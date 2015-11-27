package com.hotel.service.impl;

import java.util.ArrayList;
import java.util.Date;
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
		List<BbsCategory> list = bbsCategoryMapper.selectCategoryList();
		ListResult<BbsCategory> result = new ListResult<BbsCategory>(list);
		return result;
	}

	@Override
	public ListResult<BbsVM> loadBbsListByCategoryId(int categoryId) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("parentId", 0);//加载主贴
		map.put("categoryId", categoryId);
		List<BbsVM> list = bbsMapper.selectByMap(map);
		ListResult<BbsVM> result = new ListResult<BbsVM>(list);
		return result;
	}

	@Override
	public void saveBbs(BbsVM bbs) {
		// TODO Auto-generated method stub
		if(bbs.getBbsType()==1){//论坛
			//bbs.setId(0);
			bbs.setCategoryId(1);
			if(bbs.getPostType() == 0){//发帖
				bbs.setLevel(1);
				bbs.setCreateTime(new Date());
				bbsMapper.insertSelective(bbs);
			}
			else if(bbs.getPostType() == 1){//回帖
				Bbs b = bbsMapper.selectByPrimaryKey(bbs.getParentId());
				//bbsMapper.updateAnswerCount();//更新父节点的回帖次数
				bbs.setCategoryId(b.getCategoryId());
				bbs.setLevel(b.getLevel()+1);
				if(b.getPath()!=null&&b.getPath().length()>0){
					bbs.setPath(b.getPath()+"."+b.getId());
				}else{
					bbs.setPath(b.getId().toString());
				}
				bbs.setCreateTime(new Date());
			}
		}
	}
	
	@Override
	public List<BbsVM> loadBbsTree(Integer pid) {
		// TODO Auto-generated method stub
		List<BbsVM> ls = new ArrayList<BbsVM>();
		List<BbsVM> list = new ArrayList<BbsVM>();
		ls = bbsMapper.selectByPid(pid); 
		list = getItemTagNodes(ls);
		return list;
	}
	private List<BbsVM> getItemTagNodes(List<BbsVM> ls) {
		// TODO Auto-generated method stub
		List<BbsVM> list = new ArrayList<BbsVM>();
		for(BbsVM bbs:ls){
			List<BbsVM> clist = bbsMapper.selectByPid(bbs.getId());
			if(clist.size()>0){
				bbs.setChildren(getItemTagNodes(clist));
			}
			list.add(bbs);
		}
		return list;
	}
	

}
