<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  
    <title>错误页面</title>

  </head>
  
  <body>
  <h1>出错了,上传错误</h1>  
   <%   
	Exception e = (Exception)request.getAttribute("exception");   
	out.print(e.getMessage());   
	%> 
  </body>
</html>

