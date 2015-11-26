/**
   * Copy Right Info		  : 四川天翼网络服务有限责任公司版权�?�� 
   * Project                  : 天翼�?��框架
   * File name                : CommonActionException.java
   * Version                  : 
   * Create time     		  : 2013-9-30
   * Modify History           : 
   * Description   文件描述。�?�?
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
