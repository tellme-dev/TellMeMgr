package com.hotel.modelVM;

import java.util.List;

import com.hotel.model.Rcu;
import com.hotel.model.RcuCfgItem;
/**
 * 
 * @author hzf
 *
 */
public class RcuVM extends Rcu {
	private List<RcuCfgItem> rcuCfgItems;

	public List<RcuCfgItem> getRcuCfgItems() {
		return rcuCfgItems;
	}

	public void setRcuCfgItems(List<RcuCfgItem> rcuCfgItems) {
		this.rcuCfgItems = rcuCfgItems;
	}
	
}
