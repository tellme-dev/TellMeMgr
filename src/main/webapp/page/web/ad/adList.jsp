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
<title>公司管理</title>
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
			$("#adinfoList tr").each(function(i){
				if(i>0){
					$(this).bind("click",function(){
						var adId = $(this).find("td").first().text();
						 //window.location.href="adinfo.do?adId="+adId;
					});
				}
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
function tagChange(type){
	if(type == 1){
		$("#tagContent").hide();
		$("#itemTag").show();
	}else{
		$("#tagContent").show();
		$("#itemTag").hide();
	}
}
function search(){
	$("#pageNumber").val("1");
	pagesearch(); 
} 
function pagesearch(){
	adForm.submit();
}
function gotoAdd(){
	window.location.href="adinfo.do?adId=0";
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
		var adId = $(this).parents("tr").find("td").first().text();
		window.location.href="adinfo.do?adId="+adId;
	}); 
}
function showdialog(){
	var wz = getDialogPosition($('#adInfoWindow').get(0),100);
	$('#adInfoWindow').window({
		  	top: 100,
		    left: wz[1],
		    onBeforeClose: function () {
		    }
	});
	$('#adInfoWindow').window('open');
}
function saveAd(obj){
	if ($('#saveAdForm').form('validate')) {
		$(obj).attr("onclick", ""); 
		 $('#saveAdForm').form('submit',{
		  		success:function(data){
		  			data = $.parseJSON(data);
		  			if(data.code==0){
		  				$.messager.alert('保存信息',data.message,'info',function(){
		  					$('#adInfoWindow').window('close');
		  					search();
	        			});
		  			}else{
						$.messager.alert('错误信息',data.message,'error',function(){
							$(obj).attr("onclick", "saveAd(this);"); 
	        			});
		  			}
		  		}
		  	 });  
	}
} 
function deleteAd(){
	var doc=$(":checkbox:checked");
	if(doc.length == 0){
		$.messager.alert('提示信息', "请选择操作项！", "warning");
		return;
	}
	/*将选中的项的id放入adIds中*/
	var adIds="";
	var adNames="";
	$(":checkbox:checked").each(function(i){
		var adId = $(this).parents("tr").find("td").first().text();
		var adName = $(this).parents("tr").find("td")[2].innerText;
		if(i==0){
			adIds += adId;
			adNames += adName;
		}else{
			adIds += ","+adId; 
			adNames += "，"+adName;
		}
	}); 
	$.messager.confirm('提示信息',"确认删除广告["+adNames+"]？",function(r){
			if(r){
				deleteAction(adIds);
			}
	});
}
function deleteAction(adIds){
	$.ajax({
		url :  "jsonDeleteAd.do?adIds="+adIds,
		type : "POST",
		dataType : "json",
		async : false,
		success : function(req) {
			if (req.isSuccess) {
				window.location.href="adList.do";
			} else {
				$.messager.alert('删除失败ʾ', req.msg, "warning");
			}
		}
	});
}
</script>
</head>

<body>
	<div class="con-right" id="conRight">
		<div class="fl yw-lump mt10">
			<div id="tab2" class="yw-tab">
				<div class="fl yw-lump mt10">
					<form id="adForm" name="adForm"
						action="adList.do" method="get">
						<div class="pd10-28">
							<div class="fr">
								<%-- <input type="text" name="searchName" class="yw-input wid170"
									placeholder="搜索" value="${ad.searchName}" />
								<span class="yw-btn bg-orange ml30 cur" onclick="search();">开始查找</span> --%>
								<span class="yw-btn bg-green ml20 cur" onclick="gotoAdd();">新建广告</span>
								<span class="yw-btn bg-blue ml20 cur" onclick="gotoEdit();">编辑广告</span>
								<span class="yw-btn bg-orange ml20 cur" onclick="deleteAd()">删除广告</span>
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
						<th>名称</th>
						<th>关键字</th> 
						<th>类型</th>
						<th>酒店或标签</th>
						<th>内容</th>
						<th>创建时间</th>
					</tr>
					<c:forEach var="item" items="${adlist}">
						<tr>
							<td style="display:none" align="left">${item.id}</td>
							<td width="5%"><input name="checkbox" type="checkbox"/></td>
							<td>${item.name}</td> 
							<td>${item.keyWord}</td> 
							<c:if test="${item.targetType == 1}">
							  <td>酒店</td>
                            </c:if>
							<c:if test="${item.targetType == 2}">
							  <td>标签</td>
                            </c:if>
							<c:if test="${item.targetType == 3}">
							  <td>社区</td>
                            </c:if>
							<c:if test="${item.targetType == 4}">
							  <td>URL地址</td>
                            </c:if>
							<c:if test="${item.targetType == 5}">
							  <td>HTML页面</td>
                            </c:if>
                            <c:if test="${item.targetType == 1}">
							  <td>${item.hotelName}</td>
                            </c:if>
                            <c:if test="${item.targetType == 2}">
							  <td>${item.targetName}</td>
                            </c:if>
                            <c:if test="${item.targetType == 3||item.targetType == 4||item.targetType == 5}">
							  <td></td>
                            </c:if>
							<td>${item.targetContent}</td> 
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
