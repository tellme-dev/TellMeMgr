package com.hotel.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.common.ListResult;
import com.hotel.model.Hotel;
import com.hotel.model.Item;
import com.hotel.model.ItemDetail;
import com.hotel.model.ItemTagAssociation;
import com.hotel.service.HotelService;
import com.hotel.service.ItemDetailService;
import com.hotel.service.ItemService;
import com.hotel.service.ItemTagAssociationService;
import com.hotel.viewmodel.HotelVM;

/**
 * 酒店数据访问接口
 * @author charo
 *
 */
@Controller
@RequestMapping("/app/hotel")
public class HotelController {
	private final static int DEFAULT_START_ROW = 0;
	private final static int DEFAULT_PAGE_SIZE = 10;
	private final static int TAG_TYPE_HOTEL = 1;
	
	@Resource(name="hotelService")
	private HotelService hotelService;
	@Resource(name="itemService")
	private ItemService itemService;
	@Resource(name="itemDetailService")
	private ItemDetailService itemDetailService;
	@Resource(name="itemTagAssociationService")
	private ItemTagAssociationService itemTagAssociationService;
	
	
	/**
	 * APP获取酒店列表接口
	 * @param json
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/hotelList.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ListResult<HotelVM> hotelList(String json, HttpServletRequest request, HttpServletResponse response) {
		//初始化分页数据
		int pageStart = DEFAULT_START_ROW;
		int pageSize = DEFAULT_PAGE_SIZE;
		
		//参数过滤处理
		if(json == null || !json.equals("")){
			String parameter = request.getParameter("requestBody");
			JSONObject object = JSONObject.fromObject(parameter);
			if(object.containsKey("pageStart")){
				pageStart = new Integer(object.getString("pageStart"));
			}
			if(object.containsKey("pageSize")){
				pageSize = new Integer(object.getString("pageSize"));
			}
		}
		
		//获取数据
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageStart", pageStart);
		map.put("pageSize", pageSize);
		//获取指定酒店对象集
		List<Hotel> lh = hotelService.getPageHotel(map);
		//获取数据的总量
		int count = hotelService.getPageHotelCount();
		//返回结果对象集
		List<HotelVM> list = new ArrayList<HotelVM>();
		if(lh != null && lh.size() > 0){
			//逐一转存
			for(Hotel hotel : lh){
				//转存酒店基本数据
				HotelVM vm = new HotelVM();
				vm.setId(hotel.getId());
				vm.setName(hotel.getName());
				vm.setText(hotel.getText());
				vm.setLatitude(hotel.getLatitude());
				vm.setLongitude(hotel.getLongitude());
				
				//查询所有酒店类型的项目
				List<ItemTagAssociation> associations =  itemTagAssociationService.getTagTypeItem(TAG_TYPE_HOTEL);
				//查询所有酒店所有的项目
				List<Item> items = itemService.getItemByHotel(hotel.getId());
				
				//如果单方没有数据则没有交集
				if(associations != null && items != null && items.size() > 0 && associations.size() > 0){
					Item temp = null;
					boolean isFind = false;
					//找出属于酒店自身的项目（求交集）
					for(ItemTagAssociation association : associations){
						for(Item item : items){
							if(association.getItemId() == item.getId()){
								temp = item;
								isFind = true;
								break;
							}
						}
						if(isFind){
							break;
						}
					}
					//判断是否找到该酒店的项目
					if(temp != null){
						//设置联系方式
						vm.setTel(temp.getTel());
						//查找该项目的详细信息
						List<ItemDetail> details = itemDetailService.selectByItemId(temp.getId());
						if(details != null && details.size() > 0){
							//仅获取第一张图片
							vm.setImgUrl(details.get(0).getImageUrl());
						}
					}
				}
				vm.clear();
				//添加到数据集
				list.add(vm);
			}
		}
		
		//返回对象处理
		ListResult<HotelVM> result = new ListResult<HotelVM>();
		result.setIsSuccess(true);
		result.setTotal(count);
		result.setMsg("");
		result.setRows(list);
		
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/demo.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ListResult<HotelVM> demo(String json, HttpServletRequest request, HttpServletResponse response) {
		return null;
	}
}
