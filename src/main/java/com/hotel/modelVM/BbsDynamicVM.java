package com.hotel.modelVM;

import com.hotel.model.Bbs;
import com.hotel.model.Customer;

public class BbsDynamicVM {
	private Bbs from;
	private Bbs to;
	private Customer customer;
	
	public Bbs getFrom() {
		return from;
	}
	public void setFrom(Bbs from) {
		this.from = from;
	}
	public Bbs getTo() {
		return to;
	}
	public void setTo(Bbs to) {
		this.to = to;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
