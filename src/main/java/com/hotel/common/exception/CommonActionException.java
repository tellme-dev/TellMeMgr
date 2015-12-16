/**
   * Copy Right Info		  :
   * Project                  : 
   * File name                : CommonActionException.java
   * Version                  : 
   * Create time     		  : 2013-9-30
   * Modify History           : 
   * Description   
   * 
   **/
package com.hotel.common.exception;

/**
 * @author goulin
 * @Descripntion	Web Action执行异常�?
 */
public class CommonActionException extends RuntimeException {
	private String detail;
	
	

	public CommonActionException() {
		super();
		// TODO Auto-generated constructor stub
	}



	public CommonActionException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}



	public CommonActionException(Throwable cause) {
		super(cause);
	}

	
	/**
	 * @param message	异常�?��描述
	 * @param detail	异常详细信息
	 */
	public CommonActionException(String message, String detail) {
		super(message);
		this.detail = detail;
	}



	/**
	 * 
	 * @return 异常详细信息
	 */
	public String getDetail() {
		return detail;
	}



	/**
	 * @param detail	异常详细信息
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	

}
