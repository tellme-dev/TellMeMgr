<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<div class="fl head-logo">
        <img src="${pageContext.request.contextPath}/source/images/logo2.png" class="mt12 ml26" />
    </div>
    <div class="fr head-menu">
    	<ul class="fl">
    		<c:forEach var="item" items="${sessionScope.userFunctions}">
    			<li onclick="javascript:window.location.href = '${pageContext.request.contextPath}${item.url}'">${item.name}</li>
    		</c:forEach>
        	<!-- <li class="head-menu-now">个人工作台</li><li>统计分析</li><li>客户管理</li><li>员工管理</li><li>项目管理</li><li>知识库</li> -->
        </ul>
        <div class="fr head-menu-right ml40">
            <!-- <a href="#"><i class="fl yw-icon icon-dot"></i><span>你有新消息</span></a>
            <a href="#"><i class="fl yw-icon icon-fork"></i><span>退出</span></a> -->
        </div>
    </div>
