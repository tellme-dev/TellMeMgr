<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<meta charset="utf-8">
<title>广告位置</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1  ,maximum-scale=1, user-scalable=no" />
<script
	src="${pageContext.request.contextPath}/source/js/pager/jquery.pager.js"></script>
<link
	href="${pageContext.request.contextPath}/source/js/pager/Pager.css"
	rel="stylesheet" />
<script type="text/javascript">
		$(document).ready(function(){
			$("#pager").pager({
			    pagenumber:'${page.pageNo}',                         /* 表示初始页数 */
			    pagecount:'${page.pageCount}',                      /* 表示总页数 */
			    totalCount:'${page.totalCount}',
			    buttonClickCallback:PageClick                     /* 表示点击分页数按钮调用的方法 */                  
			});
		}); 
PageClick = function(pageclickednumber) {
	$("#pager").pager({
	    pagenumber:pageclickednumber,                 /* 表示启示页 */
	    pagecount:'${page.pageCount}',                  /* 表示最大页数pagecount */
	    buttonClickCallback:PageClick                 /* 表示点击页数时的调用的方法就可实现javascript分页功能 */            
	});
	
	$("#pageNumber").val(pageclickednumber);          /* 给pageNumber从新赋值 */
	/* 执行Action */
	pagesearch();
};
function search(){
	$("#pageNumber").val("1");
	pagesearch(); 
} 
function pagesearch(){
	adForm.submit();
}
function gotoAdd(){
	window.location.href="bannerinfo.do?bannerId=0";
}
function gotoEdit(){
	var doc=$(":checkbox:checked");
	if(doc.length == 0){
		$.messager.alert('提示信息', "请选择操作项！", "warning");
		return;
	}
	if(doc.length > 1){
		$.messager.alert('提示信息', "只能编辑一项！", "warning");
		return;
	}
	$(":checkbox:checked").each(function(i){
		var bannerId = $(this).parents("tr").find("td").first().text();
		window.location.href="bannerinfo.do?bannerId="+bannerId;
	}); 
}
</script>
</head>

<body>
	<div class="con-right" id="conRight">
		<div class="fl yw-lump mt10">
			<div id="tab2" class="yw-tab">
				<div class="fl yw-lump mt10">
					<form id="bannerForm" name="bannerForm"
						action="adList.do" method="get">
						<div class="pd10-28">
							<div class="fr">
								<%-- <input type="text" name="searchName" class="yw-input wid170"
									placeholder="搜索" value="${banner.searchName}" />
								<span class="yw-btn bg-orange ml30 cur" onclick="search();">开始查找</span> --%>
								<!-- <span class="yw-btn bg-green ml20 cur" onclick="gotoAdd();">新建广告</span> -->
								<span class="yw-btn bg-blue ml20 cur" onclick="gotoEdit();">编辑广告</span>
								<!-- <span class="yw-btn bg-orange ml20 cur" onclick="deleteBanner()">删除广告</span> -->
							</div>
							<div class="cl"></div>
						</div>
			
						<input type="hidden" id="pageNumber" name="pageNo"
							value="${page.pageNo}" />
					</form>
				</div>
				<table class="yw-cm-table" id="adinfoList">
					<tr>
						<th style="display:none">序号</th>
						<th width="5%"></th>
						<th>位置</th>
						<th>广告</th>
						<th>是否使用</th> 
						<th>创建时间</th>
					</tr>
					<c:forEach var="item" items="${bannerlist}">
						<tr>
							<td style="display:none" align="left">${item.id}</td>
							<td width="5%"><input name="checkbox" type="checkbox"/></td>
							<td>${item.position}</td> 
							<td>${item.adName}</td>
							<c:if test="${item.isUsed == true}">
							<td>是</td>
							</c:if>
							<c:if test="${item.isUsed == false}">
							<td>否</td>
							</c:if>
							<td>${item.createtime}</td>
						</tr>
					</c:forEach>
				</table>
				<div class="page" id="pager"></div>
			</div>
		</div>
		<div class="cl"></div>
	</div>
	<div class="cl"></div>
</body>
</html>
