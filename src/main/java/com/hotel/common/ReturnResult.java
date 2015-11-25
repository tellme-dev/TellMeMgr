package com.hotel.common;

import java.io.Serializable;

public class ReturnResult <T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4459853194783840244L;

	private Integer code;
	
	private String message;
	
	private T resultObject;
	
	public static final int SUCCESS = 1;
	
	public static final int FAILURE = 0;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getResultObject() {
		return resultObject;
	}

	public void setResultObject(T result) {
		this.resultObject = result;
	}
	/**
	 * 判断返回码是否成功
	 * @return
	 */
	public boolean succeed(){
		return this.code == ReturnResult.SUCCESS;
	}
}
