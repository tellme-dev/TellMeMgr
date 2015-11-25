package com.hotel.common.utils;

import java.util.List;

import com.hotel.common.BaseConstants;
import com.hotel.common.BaseObject;

public class PaginationData extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3803928734424285900L;

	private int total;
	
	private List<?> rows;
	
	private int pageNo = 1;
	
	private int pageSize = BaseConstants.PAGE_SIZE;
	
	private int pageCount = 1;
	
	private String sort;
	
	private String order;
	
	public int getTotal() {
		return total;
	}
	
	public PaginationData(){}
	
	public PaginationData(int total, int pageNo){
		this.total = total < 0?0:total;
		this.pageNo = pageNo < 1?1:pageNo > pageCount?pageCount:pageNo;
		setPageCount();
	}
	
	public PaginationData(int total, int pageNo, int pageSize){
		this.total = total < 0?0:total;
		this.pageNo = pageNo < 1?1:pageNo > pageCount?pageCount:pageNo;
		this.pageSize = pageSize < 1?BaseConstants.PAGE_SIZE:pageSize;
		setPageCount();
	}

	public void setTotal(int total) {
		this.total = total < 0?0:total;
		setPageCount();
	}
	
	private void setPageCount(){
		if(total > 0){
			pageCount = (int)Math.ceil((double)total / pageSize);
		}
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	public int getPageNo() {
		return pageNo < 1?1:pageNo > pageCount?pageCount:pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if(this.pageSize == pageSize) return;
		this.pageSize = pageSize < 1?BaseConstants.PAGE_SIZE:pageSize;
		setPageCount();
	}

	public int getPageCount() {
		return pageCount;
	}

	public int getStartIndex() {
		return (this.pageNo - 1) * this.pageSize;
	}

	public int getEndIndex() {
		return this.pageNo * this.pageSize;
	}
	
	public boolean hasNextPage(){
		return this.pageNo < this.pageCount;
	}
	
	public boolean hasPreviousPage(){
		return this.pageNo > 1;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
}
