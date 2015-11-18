/**
   * Copy Right Info		  : 四川天翼网络服务有限责任公司版权�?�� 
   * Project                  : 天翼�?��框架
   * File name                : CustomSimpleMappingExceptionResolver.java
   * Version                  : 
   * Create time     		  : 2013-10-12
   * Modify History           : 
   * Description   文件描述。�?�?
   * 
   **/
package com.hotel.common.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * @author Administrator
 * @Descripntion	
 * 		通过复写基类SimpleMappingExceptionResolver�?
 * 		 doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
              方法，实现对普�?异常和ajax异常的处�?
 */
public class CustomSimpleMappingExceptionResolver extends
		SimpleMappingExceptionResolver {
	@Override  
    protected ModelAndView doResolveException(HttpServletRequest request,  
            HttpServletResponse response, Object handler, Exception ex) {  
        // Expose ModelAndView for chosen error view.  
        String viewName = determineViewName(ex, request);  
        if (viewName != null) {// JSP格式返回  
            if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request  
                    .getHeader("X-Requested-With")!= null && request  
                    .getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {  
                // 如果不是异步请求  
                // Apply HTTP status code for error views, if specified.  
                // Only apply it if we're processing a top-level request.  
                Integer statusCode = determineStatusCode(request, viewName);  
                if (statusCode != null) {  
                    applyStatusCodeIfPossible(request, response, statusCode);  
                }  
                return getModelAndView(viewName, ex, request);  
            } else {// JSON格式返回  
                try {  
                    PrintWriter writer = response.getWriter();  
                    writer.write(ex.getMessage());  
                    writer.flush();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
                return null;  
  
            }  
        } else {  
            return null;  
        }  
    }  
}
