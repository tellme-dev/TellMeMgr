package com.hotel.modelVM;

import java.util.List;

import com.hotel.model.Bbs;
import com.hotel.model.BbsAttach;
import com.hotel.model.Customer;

public class BbsVM extends Bbs {
	private Customer customer;
	private List<BbsAttach> bbsAttachUrls;
	private Integer collectionCount;//收藏次数
	private String categoryName;
	private List<BbsVM> children;
	private String uuid;//随机数 上传图片时作文件夹
	private Boolean isAgreed;
	private Boolean isCollected;

	public Boolean getIsCollected() {
		return isCollected;
	}

	public void setIsCollected(Boolean isCollected) {
		this.isCollected = isCollected;
	}

	public Boolean getIsAgreed() {
		return isAgreed;
	}

	public void setIsAgreed(Boolean isAgreed) {
		this.isAgreed = isAgreed;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<BbsVM> getChildren() {
		return children;
	}

	public void setChildren(List<BbsVM> children){
		this.children = children;
	}
	
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getCollectionCount() {
		return collectionCount;
	}

	public void setCollectionCount(Integer collectionCount) {
		this.collectionCount = collectionCount;
	}

	public List<BbsAttach> getBbsAttachUrls() {
		return bbsAttachUrls;
	}

	public void setBbsAttachUrls(List<BbsAttach> bbsAttachUrls) {
		this.bbsAttachUrls = bbsAttachUrls;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
