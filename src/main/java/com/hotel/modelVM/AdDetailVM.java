package com.hotel.modelVM;

import com.hotel.model.AdDetail;
import com.hotel.model.Customer;

public class AdDetailVM extends AdDetail{
	private Customer customer;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
}
