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
