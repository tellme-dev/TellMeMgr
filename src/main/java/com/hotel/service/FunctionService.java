package com.hotel.service;

import java.util.List;

import com.hotel.common.ReturnResult;
import com.hotel.model.Function;

public interface FunctionService {

	int deleteFunction(Integer id);

    int saveFunction(Function record);

    int updateFunction(Function record);
    
    
    /////主表自定义
    List<Function> getFunctionPageList(Function example);
    
    int getFunctionPageListCount(Function example);

    ReturnResult<Function> getFunctionById(Integer id);
    
    //List<Function> getMainFunction(Integer parentId);
    
    List<Function> getFunctionByParentId(Integer parentId);

	List<Function> getFunctionByParentUrl(String url);
}
