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
<title>酒店管理</title>
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
			    pagenumber:'${hotel.pageNo}',                         /* 表示初始页数 */
			    pagecount:'${hotel.pageCount}',                      /* 表示总页数 */
			    totalCount:'${hotel.totalCount}',
			    buttonClickCallback:PageClick                     /* 表示点击分页数按钮调用的方法 */                  
			});
			$("#hotelinfoList tr").each(function(i){
				if(i>0){
					$(this).bind("click",function(){
						var hotelId = $(this).find("td").first().text();
						 window.location.href="hotelinfo.do?hotelId="+hotelId;
					});
				}
			}); 
		}); 
		
PageClick = function(pageclickednumber) {
	$("#pager").pager({
	    pagenumber:pageclickednumber,                 /* 表示启示页 */
	    pagecount:'${hotel.pageCount}',                  /* 表示最大页数pagecount */
	    buttonClickCallback:PageClick                 /* 表示点击页数时的调用的方法就可实现javascript分页功能 */            
	});
	
	$("#pageNumber").val(pageclickednumber);          /* 给pageNumber从新赋值 */
	/* 执行Action */
	pagesearch();
}
function search(){
	$("#pageNumber").val("1");
	pagesearch(); 
} 
function pagesearch(){
	hotelForm.submit();
}



function saveHotel(obj){
	if ($('#saveHotelForm').form('validate')) {
		$(obj).attr("onclick", ""); 
		 $('#saveHotelForm').form('submit',{
		  		success:function(data){
		  			data = $.parseJSON(data);
		  			if(data.code==0){
		  				$.messager.alert('保存信息',data.message,'info',function(){
		  					$('#hotelInfoWindow').window('close');
		  					search();
	        			});
		  			}else{
						$.messager.alert('错误信息',data.message,'error',function(){
							$(obj).attr("onclick", "saveHotel(this);"); 
	        			});
		  			}
		  		}
		  	 });  
	}
}

function loadHotelInfo(){
	window.location.href = "hotelInfo.do";
}


</script>
<style type="text/css">
.ts14{
	font-family: '微软雅黑';
	font-size: 14px;
}

.ts15{
	font-family: '微软雅黑';
	font-size: 15px;
}
</style>
</head>

<body>
	<div class="con-right" id="conRight">

		<div class="fl yw-lump mt10">
			<div id="tab2" class="yw-tab">
				<div class="fl yw-lump mt10">
					<form id="userForm" name="userForm"
						action="hotelList.do" method="get">
						<div class="pd10-28">
							<div align="left" class="mt30">
								<span class="yw-btn bg-green cur ts15" onclick="loadHotelInfo();">添加酒店</span>
								<span class="yw-btn bg-blue ml20 cur ts15" onclick="showdialog();">修改酒店</span>
								<span class="yw-btn bg-orange ml20 cur ts15" onclick="showdialog();">删除酒店</span>
							</div>
						</div>
			
						<input type="hidden" id="pageNumber" name="pageNo"
							value="${hotel.pageNo}" />
					</form>
				</div>
				<table class="yw-cm-table" id="userinfoList">
					<tr class="ts15">
						<th>序号</th>
						<th>酒店名称</th> 
						<th>描述</th>
						<th>所属区域编号</th>
						<th>经度</th>
						<th>纬度</th>
					</tr>
					<c:forEach var="item" items="${hotelList}">
						<tr class="ts14">
							<td align="left">${item.id}</td>
							<td>${item.name}</td> 
							<td>${item.text}</td> 
							<td>${item.regionId}</td> 
							<td>${item.longitude}</td> 
							<td>${item.latitude}</td>
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
