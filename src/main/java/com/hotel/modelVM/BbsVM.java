package com.hotel.modelVM;

import java.util.List;

import com.hotel.model.Bbs;
import com.hotel.model.BbsAttach;
import com.hotel.model.Customer;

public class BbsVM extends Bbs {
	private Customer customer;
	private List<BbsAttach> bbsAttachUrls;
	private List<BbsVM> children;

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
