package com.hotel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hotel.dao.MenuMapper;
import com.hotel.model.Menu;
import com.hotel.service.MenuService;

public class MenuServiceImpl implements MenuService {

	@Autowired MenuMapper menuMapper;
	@Override
	public List<Menu> getMenulist() {
		return menuMapper.getMenuList();
	}

}
