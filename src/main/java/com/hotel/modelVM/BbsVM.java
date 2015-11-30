package com.hotel.modelVM;

import java.util.List;

import com.hotel.model.Bbs;
import com.hotel.model.BbsAttach;
import com.hotel.model.Customer;

public class BbsVM extends Bbs {
	private Customer customer;
	private List<BbsAttach> bbsAttachUrls;
	private List<BbsVM> children;
	private Integer childCount;//子节点数量
	private Integer collectionCount;//收藏次数

	public Integer getChildCount() {
		return childCount;
	}

	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}

	public Integer getCollectionCount() {
		return collectionCount;
	}

	public void setCollectionCount(Integer collectionCount) {
		this.collectionCount = collectionCount;
	}

	public List<BbsVM> getChildren() {
		return children;
	}

	public void setChildren(List<BbsVM> children) {
		this.children = children;
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
