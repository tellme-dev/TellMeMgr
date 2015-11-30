package com.hotel.dao;

import com.hotel.model.Varifycode;
@MyBatisRepository
public interface VarifycodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Varifycode record);

    int insertSelective(Varifycode record);

    Varifycode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Varifycode record);

    int updateByPrimaryKey(Varifycode record);
    /**
     * 通过电话号码查询验证码记录
     * @param mobile
     * @return
     * @author hzf
     */
    Varifycode selectByMobile(String mobile);
}