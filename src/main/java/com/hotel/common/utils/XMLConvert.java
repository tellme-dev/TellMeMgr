/**
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved. 
 * This software is the confidential and proprietary information of Founder. 
 * You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 */
package com.hotel.common.utils;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * XML转换
 * @author yin85@163.com
 * @date  2013-9-29 上午9:37:10
 */
public class XMLConvert {

	private static final Log _LOG = LogFactory.getLog(XMLConvert.class);
	
	/** 实例化对�?*/
	public static XMLConvert xml = new XMLConvert();
	
	private static final Map<Class<?>, Marshaller> JAXB_MARSHALLER_MAP = new HashMap<Class<?>, Marshaller>();
	private static final Map<Class<?>, Unmarshaller> JAXB_UNMARSHALLER_MAP = new HashMap<Class<?>, Unmarshaller>();
	
	/**
	 * XML转换ObjClazz对象
	 * @param xml XML内容
	 * @param clazz 类对�?
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <X> X xmlToObjClazz(String xml, Class<X> clazz) {
		try {
			Unmarshaller unmarshaller = JAXB_UNMARSHALLER_MAP.get(clazz);
			if (unmarshaller == null) {
				JAXBContext context = JAXBContext.newInstance(clazz);
				unmarshaller = context.createUnmarshaller();
				JAXB_UNMARSHALLER_MAP.put(clazz, unmarshaller);
			}
			StringReader reader = new StringReader(xml);
			return (X) unmarshaller.unmarshal(reader);
		}catch (Exception e) {
			_LOG.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * ObjClazz转换为XML
	 * @param objClazz
	 * @return
	 */
	public String objClazzToXml(Object objClazz) {
		try {
			Marshaller marshaller = JAXB_MARSHALLER_MAP.get(objClazz.getClass());
			if (marshaller == null) {
				JAXBContext context = JAXBContext.newInstance(objClazz.getClass());
				marshaller = context.createMarshaller();
				// 是否格式化生成的XML�?
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				JAXB_MARSHALLER_MAP.put(objClazz.getClass(), marshaller);
			}
			StringWriter writer = new StringWriter();
			marshaller.marshal(objClazz, writer);
			String xml = writer.toString();
			return xml;
		}
		catch (Exception e) {
			_LOG.error(e.getMessage(), e);
		} 
		return null;
	}
	
}
