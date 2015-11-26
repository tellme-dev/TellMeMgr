package com.hotel.modelVM;

import com.hotel.model.Bbs;
import com.hotel.model.Customer;

public class BbsVM extends Bbs {
	private Customer customer;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
