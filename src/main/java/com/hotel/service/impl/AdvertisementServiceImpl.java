package com.hotel.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotel.common.ListResult;
import com.hotel.common.utils.GeneralUtil;
import com.hotel.common.utils.Page;
import com.hotel.dao.AdDetailMapper;
import com.hotel.dao.AdvertisementMapper;
import com.hotel.dao.BannerDetailMapper;
import com.hotel.dao.BbsMapper;
import com.hotel.model.AdDetail;
import com.hotel.model.Advertisement;
import com.hotel.model.BannerDetail;
import com.hotel.model.Bbs;
import com.hotel.modelVM.AdvertisementVM;
import com.hotel.service.AdvertisementService;
import com.hotel.viewmodel.AdvertisementWebVM;

@Transactional
@Service("adService")
public class AdvertisementServiceImpl implements AdvertisementService {
	
	@Autowired
	private AdvertisementMapper advertisementMapper;
	@Autowired
	private AdDetailMapper adDetailMapper;
	@Autowired
	private BannerDetailMapper bannerDetailMapper;
	@Autowired
	private BbsMapper bbsMapper;

	@Override
	public List<AdvertisementWebVM> getAdPageList(Map<String,Object> map) {
		// TODO Auto-generated method stub
		List<AdvertisementWebVM> adlist = advertisementMapper.getAdPageList(map);
		/*转换时间格式*/
		List<AdvertisementWebVM> list = new ArrayList<AdvertisementWebVM>();
		for(int i=0;i<adlist.size();i++){
			AdvertisementWebVM a = adlist.get(i);
			Date createTime = a.getCreateTime();
			String cteatetime = GeneralUtil.dateToStrLong(createTime);
			a.setCreatetime(cteatetime);
			list.add(a);
		}
		return list;
	}

	@Override
	public int getAdPageListCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return advertisementMapper.getAdPageListCount(map);
	}

	@Override
	public void saveorUpdateAd(AdvertisementWebVM ad) {
		// TODO Auto-generated method stub
		List<String> imageTexts = new ArrayList<String>();
		List<String> imageUrls = ad.getImageUrlList();
		List<Integer> adDetailIds = new ArrayList<Integer>();//存放的编辑时原有的图片id
		List<Integer> delAdDetailIds = new ArrayList<Integer>();
		//取出imageTexts
		if(ad.getImageText() != null&&!"".equals(ad.getImageText())){
			String[] arr = ad.getImageText().split(",");
			imageTexts = Arrays.asList(arr);
		}
		/*if(ad.getImageUrlList() != null){
			String[] arr = ad.getImageText().split(",");
			imageTexts = Arrays.asList(arr);
			//List imageUrls = Arrays.asList(arr);
		}*/
		//取出adDetailIds编辑时用
		if(ad.getAdDetailIds() != null&&!"".equals(ad.getAdDetailIds())){
			String[] str = ad.getAdDetailIds().split(",");
			Integer array[] = new Integer[str.length];  
			for(int i=0;i<str.length;i++){  
			    array[i]=Integer.parseInt(str[i]);
			}
			adDetailIds = Arrays.asList(array);
		}
		//取出删除的照片id delAdDetailIds编辑时用
		if(ad.getDelAdDetailIds() != null&&!"".equals(ad.getDelAdDetailIds())){
			String[] str = ad.getDelAdDetailIds().split(",");
			Integer array[] = new Integer[str.length];  
			for(int i=0;i<str.length;i++){  
			    array[i]=Integer.parseInt(str[i]);
			}
			delAdDetailIds = Arrays.asList(array);
		}
		
		AdDetail adDetail = new AdDetail();
		if(ad.getId()>0){
			advertisementMapper.updateByPrimaryKeySelective(ad);
			//adDetail表
			int i=0;
			//编辑
			for(i=0;i<adDetailIds.size();i++){
				Integer id = adDetailIds.get(i);
				String text = imageTexts.get(i);
				
				adDetail.setAdId(ad.getId());
				adDetail.setId(id);
				adDetail.setText(text);
				adDetailMapper.updateByPrimaryKeySelective(adDetail);
			}
			//新插入
			int k = i;
			for(int j=0;j<imageTexts.size()-i;j++,k++){
				String text = imageTexts.get(k);
				String imageUrl = imageUrls.get(j);
				
				adDetail.setAdId(ad.getId());
				adDetail.setId(0);
				adDetail.setImageUrl(imageUrl);
				adDetail.setText(text);
				adDetailMapper.insert(adDetail);
			}
			//删除的图片
			if(delAdDetailIds.size() != 0){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("ids", delAdDetailIds);
				adDetailMapper.deleteByIds(map);
			}
				
		}else{
			ad.setCreateTime(new Date());
			ad.setIsUsed(true);
			advertisementMapper.insert(ad);
			//adDetail表插入
			for(int i=0;i<imageUrls.size();i++){
				String text = imageTexts.get(i);
				String imageUrl = imageUrls.get(i);
				
				adDetail.setAdId(ad.getId());
				adDetail.setId(0);
				adDetail.setImageUrl(imageUrl);
				adDetail.setText(text);
				adDetailMapper.insert(adDetail);
			}
		}
	}

	@Override
	public AdvertisementWebVM getAdById(Integer adId) {
		// TODO Auto-generated method stub
		AdvertisementWebVM ad = advertisementMapper.selectAdVMByPrimaryKey(adId);
		List<AdDetail> adDetail = adDetailMapper.selectByAdId(adId);
		//ad.setAdDetail(adDetail);
		ad.setAdDetailList(adDetail);
		return ad;
	}

	@Override
	public void updateUserByIds(String adIds) {
		// TODO Auto-generated method stub
		//adIds格式"1,2,3"
		String[] str = adIds.split(",");
		Integer array[] = new Integer[str.length];  
		for(int i=0;i<str.length;i++){  
		    array[i]=Integer.parseInt(str[i]);
		}
		List<Integer> ids = Arrays.asList(array);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ids", ids);
		map.put("isUsed", false);
		advertisementMapper.updateByIds(map);
	}
	
	@Override
	public List<AdvertisementVM> getAdList(int banner, int adNum) {
		//通过banner获取adid
		List<BannerDetail> BannerDetailList = bannerDetailMapper.getAdIdList(banner);
		if(BannerDetailList==null||BannerDetailList.size()==0){
			return null;
		}
		//获取adNum数量的广告
		if(BannerDetailList.size()<adNum){
			adNum = BannerDetailList.size();
		}
		
		List<AdvertisementVM> adList = new ArrayList<AdvertisementVM>();
		for(int i= 0;i<adNum;i++){
			int id = BannerDetailList.get(i).getAdId();
			AdvertisementVM ad =advertisementMapper.getAdList(id); 
			if(ad!=null){
				adList.add(ad);
			}
		}
		return adList;
		
	}

	@Override
	public Advertisement getAdByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return advertisementMapper.selectByPrimaryKey(id);
	}

	@Override
	public ListResult<AdvertisementVM> loadAdList(Page page) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("pageNo", page.getPageNo());
		map.put("pageSize", page.getPageSize());
		map.put("pageEnd",page.getPageSize()*page.getPageNo());
		map.put("positionType", 3);
		List<AdvertisementVM> list = advertisementMapper.selectWithPage(map);
		int total = advertisementMapper.countByMap(map);
		ListResult<AdvertisementVM> result = new ListResult<AdvertisementVM>(total,list);
		return result;
	}

	@Override
	public ListResult<AdvertisementVM> loadAdListByHotelId(int hotelId) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("targetType", 1);
		map.put("targetId", hotelId);
		map.put("isUsed", true);
		int total = advertisementMapper.countByMap(map);
		List<AdvertisementVM> list = advertisementMapper.selectAdListByMap(map);
		return new ListResult<AdvertisementVM>(total,list);
	}

	@Override
	public AdvertisementVM loadAdById(int adId) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", adId);
		return advertisementMapper.selectByMap(map);
	}

	@Override
	public void saveAdComment(Bbs bbs) {
		// TODO Auto-generated method stub
		//parentId,targetId,text,customerId前台传过来
		bbs.setId(0);
		bbs.setBbsType(2);
		bbs.setTargetType(2);
		bbs.setLevel(0);
		bbs.setCreateTime(new Date());
		bbs.setTimeStamp(new Date());
		if(bbs.getParentId() != 0&&bbs.getParentId() != null){//回复
			bbs.setPostType(1);
			bbs.setLevel(3);
			bbs.setPath(bbs.getTargetId().toString()+"."+bbs.getParentId().toString());
		}else{//评论
			bbs.setLevel(2);
			bbs.setPath(bbs.getTargetId().toString());
		}
		bbsMapper.insertSelective1(bbs);
	}
}