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
			    pagenumber:'${page.pageNo}',                         /* 表示初始页数 */
			    pagecount:'${page.pageCount}',                      /* 表示总页数 */
			    totalCount:'${page.totalCount}',
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
	hotelForm.submit();
}

function refresh(){
	window.location.reload();
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

//删除酒店信息
function deleteHotel(obj){
	var cbs = document.getElementsByName("cb_hotelId");
	if(cbs.length == 0){
		myAlert("当前还没有数据");
		return;
	}
	var ids = "";
	for(var i = 0, len = cbs.length; i < len; i ++){
		if(cbs[i].checked){
			ids += cbs[i].value+",";
		}
	}
	
	if(ids == ""){
		myAlert("请选择需要删除的酒店信息");
		return;
	}
	
	$.messager.confirm("删除确认","确认要删除所选数据吗？", function(b){
		if(b){
			ids = ids.substring(0, ids.length - 1);
	
			$(obj).attr("onclick", "javascript:void(0);");
			var url = "${pageContext.request.contextPath}/web/hotel/jsonLoadDeleteHotel.do?hotelId="+ids;
			requestGet(url, {}, function(data){
				if(data.code == 1){
					myAlert("删除数据成功");
					$(obj).attr("onclick", "deleteHotel(this);");
					refresh();
				}else{
					myAlert(data.message);
					$(obj).attr("onclick", "deleteHotel(this);");
				}
			});
		}
	});
}

function requestGet(url, data, success){
	$.ajax({
	    type: 'GET',
	    url: url,
	    data: data,
	    success: success,
	    error:function(){   
        	alert('error');   
        },
	    dataType: 'json'
	});
	
}

function loadHotelInfo(isAdd){
	if(isAdd){
		window.location.href = "hotelInfo.do?hotelId=0";
	}else{
		var cbs = document.getElementsByName("cb_hotelId");
		if(cbs.length == 0){
			myAlert("当前还没有数据");
			return;
		}
		var id = "";
		var ids = "";
		for(var i = 0, len = cbs.length; i < len; i ++){
			if(cbs[i].checked){
				if(id == ""){
					id = cbs[i].value;
				}
				ids += cbs[i].value+",";
			}
		}
		
		if(id == ""){
			myAlert("请选择需要修改的酒店信息");
			return;
		}
		if(id.length + 1 < ids.length){
			myAlert("当前功能仅支持单选");
			return;
		}
		
		window.location.href = "hotelInfo.do?hotelId="+id;
	}
}

function loadHotelProject(){
	var cbs = document.getElementsByName("cb_hotelId");
	if(cbs.length == 0){
		myAlert("当前还没有数据");
		return;
	}
	var id = "";
	var ids = "";
	for(var i = 0, len = cbs.length; i < len; i ++){
		if(cbs[i].checked){
			if(id == ""){
				id = cbs[i].value;
			}
			ids += cbs[i].value+",";
		}
	}
	
	if(id == ""){
		myAlert("请选择需要设置的酒店信息");
		return;
	}
	if(id.length + 1 < ids.length){
		myAlert("当前功能仅支持单选");
		return;
	}
	window.location.href = "hotelProject.do?hotelId="+id;
}

function myAlert(msg){
	$.messager.alert('提示', msg,'info',function(){});
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
.logo_icon{
	width: 60px;
	height: 60px
}
</style>
</head>

<body>
	<div class="con-right" id="conRight">

		<div class="fl yw-lump mt10">
			<div id="tab2" class="yw-tab">
				<div class="fl yw-lump mt10">
					<form id="hotelForm" name="hotelForm"
						action="hotelList.do" method="get">
						<div class="pd10-28">
							<div align="left" class="mt30">
								<span class="yw-btn bg-green cur ts15" onclick="loadHotelInfo(true);">添加酒店</span>
								<span class="yw-btn bg-blue ml20 cur ts15" onclick="loadHotelInfo(false);">修改酒店</span>
								<span class="yw-btn bg-gray ml20 cur ts15" onclick="loadHotelProject();">项目管理</span>
								<span class="yw-btn bg-orange ml20 cur ts15" onclick="deleteHotel(this);">删除酒店</span>
							</div>
						</div>
			
						<input type="hidden" id="pageNumber" name="pageNo"
							value="${page.pageNo}" />
					</form>
				</div>
				<table class="yw-cm-table" id="userinfoList">
					<tr class="ts15">
						<th>选择</th>
						<th>酒店名称</th> 
						<th>LOGO</th>
						<th>描述</th>
						<th>所属区域编号</th>
						<th>经度</th>
						<th>纬度</th>
					</tr>
					<c:forEach var="item" items="${hotelList}">
						<tr class="ts14">
							<td align="left"><input type="checkbox" name="cb_hotelId" value="${item.id}" /></td>
							<td>${item.name}</td> 
							<td><img class="logo_icon" src="${pageContext.request.contextPath}/${item.logo}"></td> 
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
