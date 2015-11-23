/**
 * copy right 2012 sctiyi all rights reserved
 * create time:下午05:05:30
 * author:ftd
 */
package com.hotel.common.utils.app;

import java.util.Locale;
import java.util.Map;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

/**
 * @author ftd
 * 
 */
public class MixedViewResolver implements ViewResolver {
	private Map<String, ViewResolver> resolvers;

	public void setResolvers(Map<String, ViewResolver> resolvers) {
		this.resolvers = resolvers;
	}

	@Override
	public View resolveViewName(String viewName, Locale locale)
			throws Exception {
		String fields = "";
		if(viewName.indexOf('?') != -1){
			fields = viewName.substring(viewName.lastIndexOf('?'));
			viewName = viewName.substring(0,viewName.lastIndexOf('?'));
		}
		int n = viewName.lastIndexOf(".");
		if (n != -1) {
			// 取出扩展�?
			String suffix = viewName.substring(n + 1);
			if(suffix.equalsIgnoreCase("do"))
			{
				suffix="jsp";
			}
			// 取出对应的ViewResolver
			ViewResolver resolver = resolvers.get(suffix);
			if (resolver == null) {
				throw new RuntimeException("No ViewResolver for " + suffix);
			}
			return resolver.resolveViewName(viewName + fields, locale);
		} else {
			ViewResolver resolver = resolvers.get("jsp");
			return resolver.resolveViewName(viewName + fields, locale);
		}
	}

}
