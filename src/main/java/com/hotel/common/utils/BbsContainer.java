package com.hotel.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hotel.modelVM.BbsDynamicVM;

public class BbsContainer {
	private Map<String, List<BbsDynamicVM>> map;
	public BbsContainer() {
		map = new HashMap<String, List<BbsDynamicVM>>();
	}
	
	public void add(String id, BbsDynamicVM vm){
		if(map.size() == 0){
			List<BbsDynamicVM> list = new ArrayList<BbsDynamicVM>();
			list.add(vm);
			map.put(id, list);
			return ;
		}
		
		if(map.containsKey(id)){
			map.get(id).add(vm);
		}else{
			List<BbsDynamicVM> list = new ArrayList<BbsDynamicVM>();
			list.add(vm);
			map.put(id, list);
		}
	}
	
	public Set<String> getIds(){
		return map.keySet();
	}
	
	public List<BbsDynamicVM> getValues(String id){
		return map.get(id);
	}
	
	public int size(){
		return map.size();
	}
}
