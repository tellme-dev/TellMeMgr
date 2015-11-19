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
	
<link rel="stylesheet" href="http://cache.amap.com/lbs/static/main.css?v=1.0" />
<script src="http://webapi.amap.com/maps?v=1.3&key=7794bac5851d466a825637e82e4a7926"></script>
  	
<script
	src="${pageContext.request.contextPath}/source/js/pager/jquery.pager.js"></script>
<link
	href="${pageContext.request.contextPath}/source/js/pager/Pager.css"
	rel="stylesheet" />
<script type="text/javascript">
var map_show = null;
var map_edit = null;

var tag_arr = new Array();
var img_arr = new Array();


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
			
			$("#province").combobox({
				onChange: function (n,o) {
					if(n == 0){
						return ;
					}
					selectProvince(n);
				}
			});
			
			$("#city").combobox({
				onChange: function (n,o) {
					if(n == 0){
						return ;
					}
					selectCity(n);
				}
			});
			
			//初始化对象滑动效果
			initMouseEvent();
			//初始化图片选择效果
			initImgEvent();
			
			map_show = new AMap.Map('show_mapContainer', {
		      // 设置中心点
		      center: [104.065735, 30.659462],
		
		      // 设置缩放级别
		      zoom: 12
		    });
		    
		    map_edit = new AMap.Map('edit_mapContainer', {
		      // 设置中心点
		      center: [104.065735, 30.659462],
		
		      // 设置缩放级别
		      zoom: 12
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

//初始化鼠标事件
function initMouseEvent(){
	var arr = document.getElementsByName("tag_item");
	if(arr != null && arr.length > 0){
		for(var i = 0, len = arr.length; i < len; i ++){
			var tag;
			if(i == 0){
				tag = new TagItem(i, arr[i]);
			}else{
				tag = new TagItem(i, arr[i]);
			}
			tag_arr.push(tag);
			tag.register();
		}
	};
}

//初始化
function initImgEvent(){
	var arr = document.getElementsByName("img_item");
	if(arr != null && arr.length > 0){
		for(var i = 0, len = arr.length; i < len; i ++){
			var img;
			if(i == 0){
				img = new ImgItem(i, arr[i]);
			}else{
				img = new ImgItem(i, arr[i]);
			}
			img_arr.push(img);
			img.register();
		}
	};
}

//显示地理位置区域
function showdialog(){
	var wz = getDialogPosition($('#hotelInfoWindow').get(0),100);
	$('#hotelInfoWindow').window({
		  	top: 100,
		    left: wz[1],
		    onBeforeClose: function () {
		    }
	});
	if(map_edit != null){
		map_edit.setCity("成都");
		var center = map_edit.getCenter();
		
		var marker = new AMap.Marker({map:map_edit,position:center,draggable:true});
		marker.on("dragend", function(e){
			setLocationText(marker);
		});
		setLocationText(marker);
	}
	$('#hotelInfoWindow').window('open');
}

//设置初始位置或拖拽后的经纬度信息
function setLocationText(marker){
	var position = marker.getPosition();
	document.getElementById("txt_location").innerHTML = "["+position.getLng()+", "+position.getLat()+"]";
}

//保存当前的经纬度信息到文本框
function saveLocation(){
	document.getElementById("input_location").value = document.getElementById("txt_location").innerHTML;
	$('#hotelInfoWindow').window('close');
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

//选择指定省份后初始化城市数据
function selectProvince(val){
	var url = "http://127.0.0.1:8080/hotel/web/base/jsonLoadRegionCityComboList.do?provinceId="+val;
	requestGet(url, {}, function(data){
		if(data.length > 0){
			var dat = "[";
			var fopt = "{\"value\":\"0\",\"text\":\"=请选择城市=\",\"selected\":true},";
			dat += fopt;
			
			var len = data.length;
			for(var i =0; i < len; i++){
				var opt;
				if(i == len - 1){
					opt = "{\"value\":\""+data[i].id+"\",\"text\":\""+data[i].name+"\",\"selected\":false}";
				}else{
					opt = "{\"value\":\""+data[i].id+"\",\"text\":\""+data[i].name+"\",\"selected\":false},";
				}
				
				dat += opt;
			}
			
			dat += "]";
			var json = eval(dat);
			$("#city").combobox("loadData", json);
			$("#area").combobox("loadData", eval("[{\"value\":\"0\",\"text\":\"=请选择区域=\",\"selected\":true}]"));
		}
	});
}

//选择指定城市后初始化区域数据
function selectCity(val){
	var url = "http://127.0.0.1:8080/hotel/web/base/jsonLoadRegionAreaComboList.do?cityId="+val;
	requestGet(url, {}, function(data){
		
		if(data.length > 0){
			var dat = "[";
			var fopt = "{\"value\":\"0\",\"text\":\"=请选择区域=\",\"selected\":true},";
			dat += fopt;
			var len = data.length;
			for(var i =0; i < len; i++){
				var opt;
				if(i == len - 1){
					opt = "{\"value\":\""+data[i].id+"\",\"text\":\""+data[i].name+"\",\"selected\":false}";
				}else{
					opt = "{\"value\":\""+data[i].id+"\",\"text\":\""+data[i].name+"\",\"selected\":false},";
				}
				
				dat += opt;
			}
			
			dat += "]";
			var json = eval(dat);
			$("#area").combobox("loadData", json);
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

//====================
// Object 对象区域
//====================
/**
* TagItem
*/
var TagItem = function(id, obj){
	this.id = id;
	this.obj = obj;
	this.selected = false;
	
	TagItem.prototype.register = function(){
		this.obj.onmouseover = function(){
			if(this.selected){
				return ;
			}
			obj.className = "fl tag_item_over ml20";
		};
		this.obj.onmouseout = function(){
			if(this.selected){
				return ;
			}
			obj.className = "fl tag_item ml20";
		};
		this.obj.onclick = function(){
			if(this.selected){
				obj.className = "fl tag_item ml20";
			}else{
				obj.className = "fl tag_item_selected ml20";
			}
			
			this.selected = !this.selected;
		};
	};
};

/**
* TagItem
*/
var ImgItem = function(id, obj){
	this.id = id;
	this.obj = obj;
	this.selected = false;
	
	ImgItem.prototype.register = function(){
		this.obj.onclick = function(){
			if(this.selected){
				obj.className = "photo_show";
			}else{
				obj.className = "photo_show_selected";
			}
			
			this.selected = !this.selected;
		};
	};
};


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
.map_style{
	width: 600px;
	height: 240px;
}

.imgs_style{
	width: 600px;
	height: 60px;
	padding-top: 10px;
	padding-bottom: 10px;
	overflow: auto;
}

.icon_location{
	width: 32px;
	height: 32px;
	vertical-align: top;
	cursor: pointer;
	margin-left: 20px;
}

.txt_location{
	font-size: 15px;
	color: green;
}
.hint_red{
	font-family: '微软雅黑';
	font-size: 12px;
	color: #FF5500;
}
.wid600{
	width: 600px;
}

.border{
	border: 1px #C4C4C4 solid;
	padding: 10px;
	border-radius: 4px; 
	-moz-border-radius:4px;
	-webkit-border-radius:4px;
}

.divider{
	width: 100%;
	height: 1px;
	background-color: #F3F3F3;
}

.tag_item{
	padding: 10px;
	border-radius: 4px; 
	-moz-border-radius:4px;
	-webkit-border-radius:4px;
	background-color: #FFFFFF;
	border:1px #C4C4C4 solid;
	cursor: pointer;
	color: #4C4C4C;
}

.tag_item_over{
	padding: 10px;
	border-radius: 4px; 
	-moz-border-radius:4px;
	-webkit-border-radius:4px;
	background-color: #FAFAFA;
	border:1px #C4C4C4 solid;
	cursor: pointer;
	color: #4C4C4C;
}

.tag_item_selected{
	padding: 10px;
	border-radius: 4px; 
	-moz-border-radius:4px;
	-webkit-border-radius:4px;
	background-color: #FAFAFA;
	border:1px #00B7FF solid;
	cursor: pointer;
	color: #00B7FF;
}

.txt_function{
	font-family: "微软雅黑";
	font-size: 12px;
	color: #00B7FF;
	cursor: pointer;
}

.photo_show{
	width: 60px;
	height: 60px;
	margin-left: 5px;
	cursor: pointer;
}

.photo_show_selected{
	width: 58px;
	height: 58px;
	margin-left: 5px;
	cursor: pointer;
	border: 1px #FF0000 solid;
}
</style>
</head>

<body>
	<div class="con-right" id="conRight">
		<div class="fl yw-lump">
			<div class="yw-lump-title">
				<span class="ml20 ts14">当前状态：未选择</span>
			</div>
		</div>

		<div class="fl yw-lump mt10">
			<div id="tab2" class="yw-tab">
				<div class="fl yw-lump mt10">
					<form id="userForm" name="userForm"
						action="userList.do" method="get">
						<div class="pd10-28">
							<div class="fl">
								<div class = "mt20">
									<span class="ts15">酒店名称：</span><input type="text" class="yw-input wid200" />
									<span class="hint_red">**必填项**</span>
								</div>
								<div class = "mt20">
									<span class="ts15">酒店区域：</span>
									<select id="province" name="province" style="width:120px;height:30px;" class="easyui-combobox">
								 	 	<option selected="selected" value="0">=请选择省份=</option>
								 	 	<c:forEach var="item" items="${regionList}">
								 	 		<option value="${item.id}">${item.name}</option>
										</c:forEach>
								 	 	
									</select>
									<select id="city" name="city" style="width:120px;height:30px;" class="easyui-combobox">
								 	 	<option  value="0" selected="selected">=请选择城市=</option>
									</select>
									<select id="area" name="area" style="width:120px;height:30px;" class="easyui-combobox">
								 	 	<option  value="0" selected="selected">=请选择区域=</option>
									</select>
									<span class="hint_red">**必填项**</span>
								</div>
								<div class = "mt20">
									<span class="ts15">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</span><textarea cols="50" rows="6" style="vertical-align: top; border: 1px #C4C4C4 solid;"></textarea>
								</div>
								<div class = "mt20">
									<span class="ts15">经&nbsp;纬&nbsp;度：</span><input id="input_location" type="text" readonly="readonly" class="yw-input wid200 ts14" /><img alt="点击标记位置" onclick="showdialog();" class="icon_location" src="${pageContext.request.contextPath}/source/images/location.png">
									<span class="hint_red">**必填项&nbsp;&nbsp;&nbsp;&nbsp;点击标记图标可设置指定位置的经纬度**</span>
								</div>
								
								<div class = "mt20 wid600 border">
									<div class="fl"><span class="ts15">标签：</span></div>
									<div class="fr"><span class="txt_function">[删除选中]</span><span class="txt_function ml10">[设置]</span></div>
									<div class="cl"></div>
									<div class="mt10">
										<div name="tag_item" class="fl tag_item ml20"><span class="ts14">枕头</span></div>
										<div name="tag_item" class="fl tag_item ml20"><span class="ts14">床垫</span></div>
										<div class="cl"></div>
									</div>
								</div>
							</div>
							<div class="fr">
								<div class="fl"><span class="ts15">位置：</span></div><div id="show_mapContainer" class="fr map_style"></div>
								<div class="cl"></div>
								<div class="mt20 border">
									<div class="fl"><span class="ts15">照片：</span></div><div class="fr"><span class="txt_function">[添加]</span><span class="txt_function ml10">[删除]</span></div>
									<div class="cl"></div>
									<div class="imgs_style">
										<img alt="" name="img_item" class="photo_show" src="${pageContext.request.contextPath}/source/images/userhaed1.png" />
										<img alt="" name="img_item" class="photo_show" src="${pageContext.request.contextPath}/source/images/userhaed1.png" />
										<img alt="" name="img_item" class="photo_show" src="${pageContext.request.contextPath}/source/images/userhaed1.png" />
										<img alt="" name="img_item" class="photo_show" src="${pageContext.request.contextPath}/source/images/userhaed1.png" />
									</div>
								</div>
							</div>
							<div class="cl"></div>
							<div class="divider mt10"></div>
							<div align="left" class="mt30">
								<span class="yw-btn bg-green cur ts15" onclick="showdialog();">添加酒店</span>
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
	<div id="hotelInfoWindow" class="easyui-window" title="选择位置" style="width:560px;height:480px;overflow:hidden;padding:10px;" iconCls="icon-info" closed="true" modal="true"   resizable="false" collapsible="false" minimizable="false" maximizable="false">
		<div>
			<div class="fl"><span class="ts14">经纬度：</span><span id="txt_location" class="txt_location">[104.065735, 30.659462]</span></div>
			<div class="fr"><span class="yw-btn bg-blue ml40 cur ts15" onclick="saveLocation();">确定</span></div>
			<div class="cl"></div>
		</div>
		<div id="edit_mapContainer" style="width: 540px; height: 390px; margin-top: 10px;"></div>
	</div>
</body>
</html>
