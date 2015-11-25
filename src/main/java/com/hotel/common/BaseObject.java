/**
 * @File: AppDemo.java
 * @author ibm
 * @create date 2013-9-25 上午11:03:09
 * @copyright 四川天翼网络服务有限责任公司
 */
package com.hotel.common;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @author ibm
 * @descrption 用于mode对象需要进行分页操作时，需要继承该类
 * @create 2013-9-25
 */
public class BaseObject implements Serializable {
	/** 变量 serialVersionUID(long) */
	private static final long serialVersionUID = 1L;

	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * 打印Model Object实例对象属性值为字符串显示
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	/**
	 * 将对象转换为格式的字符串
	 * @return
	 */
	public String toJson() {
		return null;
	}
}
