package com.hotel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.common.ReturnResult;
import com.hotel.dao.FunctionMapper;
import com.hotel.model.Function;
import com.hotel.service.FunctionService;
@Service("functionService")
public class FunctionServiceImpl implements FunctionService {
	@Autowired
	private FunctionMapper functionMapper;

	@Override
	public List<Function> getFunctionByParentId(Integer parentId) {
		//List<UserFunction> lf = userFunctionMapper.getFunctionByUserId(userId);
		
		List<Function> mainlist = functionMapper.getFunctionByParentId(parentId);
//		for(UserFunction uf:lf){
//			if(uf.getFunction().getParentId()==0){
//				Function function  = uf.getFunction();
//				function.setChildFunctionlist(getChildrenList(lf,function.getId()));
//				mainlist.add(function);
//			}
//		}
		return mainlist;
	}
	
//	private List<Function> getChildrenList(List<UserFunction> lf,Integer parentFunctionId){
//		List<Function> mainlist = new ArrayList<Function>();
//		for(UserFunction uf:lf){
//			if(uf.getFunction().getParentId()==parentFunctionId){
//				Function function  = uf.getFunction();
////				function.setChildFunctionlist(getChildrenList(lf));
//				mainlist.add(function);
//			}
//		}
//		return mainlist;
//	}

	@Override
	public int deleteFunction(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int saveFunction(Function record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateFunction(Function record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Function> getFunctionPageList(Function example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getFunctionPageListCount(Function example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ReturnResult<Function> getFunctionById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Function> getFunctionByParentUrl(String url) {
		// TODO Auto-generated method stub
		List<Function> list = functionMapper.getFunctionByParentUrl(url);
		return list;
	}

}