<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<META http-equiv="X-UA-Compatible" content="IE=edge" />
<title>易维-<sitemesh:write property='title'/></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link type="text/css" href="${pageContext.request.contextPath}/source/css/base.css" rel="stylesheet"/>
<link type="text/css" href="${pageContext.request.contextPath}/source/css/global.css" rel="stylesheet"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/source/js/easyUI/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/source/js/easyUI/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/source/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/source/js/easyUI/jquery.easyui.min.js"></script>
<script src="${pageContext.request.contextPath}/source/js/easyUI/easyui-lang-zh_CN.js"></script>
<script src="${pageContext.request.contextPath}/source/js/common/validate.js"></script>
<script src="${pageContext.request.contextPath}/source/js/common/common.js"></script>
<!-- 导入页面引用的特殊js和css文件 -->
<sitemesh:write property='head' />
<script type="text/javascript">
	$(function(){
		//计算网页高度
		setHei();
		$(window).resize(function(){
			setHei();
		});
	});
	
	function setHei(){
		var h = $(window).height();
		var t = $("#content").offset().top;
		var v = h-t-1;
		var rh = $("#conRight").height();
		if(rh>v){
			v = rh+20;
		}
		$("#content").css("height",v);
	}
</script>
</head>

<body>
	<div id="main">
		<div id="header"><jsp:include page="/page/decorators/header.jsp"></jsp:include></div>
		<div id="content"><jsp:include page="/page/decorators/left.jsp"></jsp:include><sitemesh:write property='body'/></div>
	</div>
</body>
</html>