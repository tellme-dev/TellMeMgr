/**
 * copy right 2012 sctiyi all rights reserved
 * create time:10:45:38 AM
 * author:ftd
 */
package com.hotel.web.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hotel.common.exception.SessionTimeOutException;
import com.hotel.common.utils.Constants;



/**
 * @author ftd
 * 系统拦截�?
 *
 */
public class SessionTimeoutInteceptor implements HandlerInterceptor {
	
	private List<String> allowAction;

	public List<String> getAllowAction() {
		return allowAction;
	}

	public void setAllowAction(List<String> allowAction) {
		this.allowAction = allowAction;
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
		
		System.out.println(" MyInteceptor afterCompletion");
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(" MyInteceptor postHandle");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
        String uri = request.getRequestURI();  
        System.out.println(" MyInteceptor preHandle request uri="+uri);
        //后台session控制
        if (uri.indexOf("/api/") != -1) {  
        	//api接口，进行token验证.
        } else {  
            boolean beFilter = true;  
            for (String s : allowAction) {  
                if (uri.indexOf(s) != -1) {  
                    beFilter = false;  
                    break;  
                }  
            }  
            if (beFilter) {  
                Object obj = request.getSession().getAttribute(  
                        Constants.USER_SESSION_NAME);  
                if (null == obj) {  
                    // 未登�? 
                	throw new SessionTimeOutException("登录超时");
                    //return false;  
                } else {  
                    // 添加日志  
                    
                }  
            }  
        }
        
        /**
        Map paramsMap = request.getParameterMap();  
        for (Iterator<Map.Entry> it = paramsMap.entrySet().iterator(); it  
                .hasNext();) {  
            Map.Entry entry = it.next();  
            Object[] values = (Object[]) entry.getValue();  
            for (Object obj : values) {  
                
            }  
        } 
        
         */

		return true;
	}

	

	

}
