package com.hotel.modelVM;

import java.util.List;

import com.hotel.model.Bbs;
import com.hotel.model.BbsAttach;
import com.hotel.model.Customer;

public class BbsVM extends Bbs {
	private Customer customer;
	private List<BbsAttach> BbsAttachs;
	private String attachUrl;

	public String getAttachUrl() {
		return attachUrl;
	}

	public void setAttachUrl(String attachUrl) {
		this.attachUrl = attachUrl;
	}

	public List<BbsAttach> getBbsAttachs() {
		return BbsAttachs;
	}

	public void setBbsAttachs(List<BbsAttach> bbsAttachs) {
		BbsAttachs = bbsAttachs;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
