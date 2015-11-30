package com.hotel.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.dao.HotelMapper;
import com.hotel.dao.ItemTagAssociationMapper;
import com.hotel.model.Hotel;
import com.hotel.model.ItemTagAssociation;
import com.hotel.modelVM.HotelVM;
import com.hotel.service.HotelService;

@Service("hotelService")
public class HotelServiceImpl implements HotelService{
	
	@Autowired
	private HotelMapper hotelMapper;
	@Autowired
	private ItemTagAssociationMapper associationMapper;
	

	@Override
	public List<Hotel> selectHotelList() {
		// TODO Auto-generated method stub
		return hotelMapper.selectHotelList();
	}

	@Override
	public List<Hotel> getPageHotel(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return hotelMapper.getPageHotel(map);
	}
	@Override
	public int getPageHotelCount() {
		// TODO Auto-generated method stub
		return hotelMapper.getPageHotelCount();
	}
	@Override
	public List<ItemTagAssociation> getTagTypeItem(int tagType) {
		// TODO Auto-generated method stub
		return associationMapper.getTagTypeItem(tagType);
	}

	@Override
	public int insert(Hotel record) {
		// TODO Auto-generated method stub
		return hotelMapper.insert(record);
	}

	@Override
	public int updateByPrimaryKeySelective(Hotel record) {
		// TODO Auto-generated method stub
		return hotelMapper.updateByPrimaryKey(record);
	}

	@Override
	public int deleteByHotelId(Map<String, Object> idMap) {
		// TODO Auto-generated method stub
		return hotelMapper.deleteByHotelId(idMap);
	}

	@Override
	public Hotel selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return hotelMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public HotelVM getHotelVMById(int id) {
		// TODO Auto-generated method stub
		return hotelMapper.getHotelVMById(id);
	}

	@Override
	public HotelVM getHotelVMByAdId(Integer adId) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("adId", adId);
		return hotelMapper.getHotelVMByMap(map);
	}

	@Override
	public List<Hotel> getPageHotelByItemTag(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return hotelMapper.getPageHotelByItemTag(map);
	}

	@Override
	public int getPageHotelCountByItemTag(int itemTagId) {
		// TODO Auto-generated method stub
		return hotelMapper.getPageHotelCountByItemTag(itemTagId);
	}
}
