<%@ page language="java" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="com.hotel.common.utils.Constants"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<link rel="stylesheet" type="text/css"href="<%=basePath%>js/easyui/themes/default/easyui.css"></link>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/easyui/themes/icon.css"></link>
<!-- Just for debugging purposes. Don't actually copy this line! -->
<!--[if lt IE 9]><script src="../../docs-assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
  <script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.min.js"></script>
  <script src="http://cdn.bootcss.com/respond.js/1.3.0/respond.min.js"></script>
<![endif]-->
<%-- <link rel="stylesheet" type="text/css" href="<%=basePath%>style/base.css"></link>   
<link rel="stylesheet" type="text/css" href="<%=basePath%>style/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>style/bootstrap/css/bootstrap-theme.css">
<script type="text/javascript" src="<%=path%>/style/bootstrap/js/bootstrap.min.js"></script>

<script type="text/javascript" src="<%=path%>/js/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/easyui/locale/easyui-lang-zh_CN.js"></script> --%>

<script type="text/javascript" language="javascript">
	var basePath="<%=path%>/";
	Date.prototype.format = function(pattern){
		function getFormatDate(date, dateFormat){
	        if (isNaN(date)) return null;
	        var format = dateFormat;
	        var o = {
	            "M+": date.getMonth() + 1,
	            "d+": date.getDate(),
	            "h+|H+": date.getHours(),
	            "m+": date.getMinutes(),
	            "s+": date.getSeconds(),
	            "q+": Math.floor((date.getMonth() + 3) / 3),
	            "S": date.getMilliseconds()
	        }
	        if (/(y+|Y+)/.test(format)){
	            format = format.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
	        }
	        for (var k in o){
	            if (new RegExp("(" + k + ")").test(format)){
	                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
	            }
	        }
	        return format;
	    }
		pattern = pattern || "yyyy-MM-dd";
	    return getFormatDate(this, pattern);
	};
</script>
<c:if test="${resultMessage!=null}">
	<script type="text/javascript" language="javascript">
		$(function(){
			alert('${resultMessage}');
		});
	</script>
</c:if>