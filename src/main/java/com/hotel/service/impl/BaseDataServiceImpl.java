package com.hotel.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotel.dao.ItemTagMapper;
import com.hotel.dao.OrgMapper;
import com.hotel.model.ItemTag;
import com.hotel.dao.RegionMapper;
import com.hotel.model.Org;
import com.hotel.model.Region;
import com.hotel.service.BaseDataService;
import com.hotel.viewmodel.ItemTagVM;

@Service("baseDataService")
public class BaseDataServiceImpl implements BaseDataService {
	
	@Resource
	private OrgMapper orgMapper;
	@Resource
	private ItemTagMapper tagMapper;
	@Resource
	private RegionMapper regionMapper;

	@Override
	public List<Org> getOrgComboList(Integer pid) {
		// TODO Auto-generated method stub
		List<Org> ls = new ArrayList<Org>();
		List<Org> list = new ArrayList<Org>();
		ls = orgMapper.getOrganList(pid); 
		list = getNodes(ls,pid);
//		JSONArray  json = JSONArray.fromObject(list);
//		String resutl  = json.toString();
//		return resutl;
		return list;
	}
	
	@Override
	public List<Org> getOrgList(Integer pid) {
		// TODO Auto-generated method stub
		List<Org> ls = orgMapper.getOrganList(pid);
		return ls;
	}
	
	private List<Org> getNodes(List<Org> ls, Integer pid) {
		// TODO Auto-generated method stub
		List<Org> list = new ArrayList<Org>(); 
		for(Org o :ls ){
			if(o.getParentId() == pid){
				Org v = new Org();
				v.setId(o.getId());
				v.setPath(o.getPath());
				v.setParentId(o.getParentId());
				v.setLevel(o.getLevel());
				v.setName(o.getName());
				v.setText(o.getName());
				v.setHotelId(o.getHotelId());
				List<Org> l = getItemByParentId(o.getId());
				if(l.size()>0){ 
					v.setChildren(getNodes(l,o.getId())); 
				}else{
					v.setChildren(null); 
				}
				list.add(v);
			}
		}
		return list;
	}

	private List<Org> getItemByParentId(Integer id) {
		// TODO Auto-generated method stub
		return orgMapper.getOrganList(id);
	}
	
	@Override
	public List<Region> getAllRegion() {
		// TODO Auto-generated method stub
		return regionMapper.getAllRegion();
	}

	@Override
	public List<Region> getProvinceRegion() {
		// TODO Auto-generated method stub
		return regionMapper.getProvinceRegion();
	}

	@Override
	public List<Region> getCityRegion(Integer provinceId) {
		// TODO Auto-generated method stub
		return regionMapper.getCityRegion(provinceId);
	}

	@Override
	public List<Region> getAreaRegion(Integer cityId) {
		// TODO Auto-generated method stub
		return regionMapper.getAreaRegion(cityId);
	}

	@Override
	public List<ItemTag> selectTagByTagType(int tagType) {
		// TODO Auto-generated method stub
		return tagMapper.selectTagByTagType(tagType);
	}

	@Override
	public List<ItemTag> selectTagList() {
		// TODO Auto-generated method stub
		return tagMapper.selectTagList();
	}
    /**
     * 加载ItemTag树形结构
     */
	@Override
	public List<ItemTagVM> getItemTagTree(Integer pid) {
		// TODO Auto-generated method stub
		List<ItemTagVM> ls = new ArrayList<ItemTagVM>();
		List<ItemTagVM> list = new ArrayList<ItemTagVM>();
		ls = tagMapper.selectByPid(pid); 
		list = getItemTagNodes(ls);
		return list;
	}
	private List<ItemTagVM> getItemTagNodes(List<ItemTagVM> ls) {
		// TODO Auto-generated method stub
		List<ItemTagVM> list = new ArrayList<ItemTagVM>();
		for(ItemTagVM t:ls){
			List<ItemTagVM> clist = tagMapper.selectByPid(t.getId());
			if(clist.size()>0){
				t.setChildren(getItemTagNodes(clist));
			}
			list.add(t);
		}
		return list;
	}

}
