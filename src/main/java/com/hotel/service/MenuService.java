package com.hotel.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hotel.model.Menu;

@Service
public interface MenuService {
	List<Menu> getMenulist();
}
