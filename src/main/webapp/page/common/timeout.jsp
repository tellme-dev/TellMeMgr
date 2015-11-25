<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   <title>登录超时</title>
   <script type="text/javascript" src="<%=basePath %>js/jquery/jquery-1.7.2.min.js"></script>
  </head>
  
  <body>
  	  您好，您未登录，或者登录超时，系统将在<font color="red"><span id="timecount"> </span></font>秒后自动登录，您也可以点击<a href="<%=basePath %>system/login/loginInit.do" target="_top">这里</a>重新登录
	  <script type="text/javascript">
	  var count = 8;
	  $(document).ready(function(){
		  $("#timecount").html(count);
		  
		  window.setInterval(showtimeCount,1000);
		});
	  function showtimeCount()
	  {
		  count--;
		  if(count == 0)
		  {
		   top.location.href="<%=basePath %>system/login/loginInit.do";
		  }else{
	  		$("#timecount").html(count);
	  	  }
	  }
	  </script> 
  </body>
</html>