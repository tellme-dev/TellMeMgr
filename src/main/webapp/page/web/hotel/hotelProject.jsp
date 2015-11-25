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

var tempImgArr = null;
var projectArr = new Array();


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
			
			tempImgArr = new ObjectImgItemList();
			
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

//显示地理位置区域
function showdialog(){
	var val = $("#city").combobox("getValue");
	if(val == 0){
		alert("请先选中一个城市");
		return ;
	}

	var wz = getDialogPosition($('#hotelInfoWindow').get(0),100);
	$('#hotelInfoWindow').window({
		  	top: 100,
		    left: wz[1],
		    onBeforeClose: function () {
		    }
	});
	if(map_edit != null){
		map_edit.clearMap();
		var city = $("#city").combobox("getText");
		map_edit.setCity(city);
		
		var center = map_edit.getCenter();
		
		var marker = new AMap.Marker({map:map_edit,position:center,draggable:true});
		marker.on("dragend", function(e){
			setLocationText(marker);
		});
		setLocationText(marker);
		
		AMap.service(["AMap.Geocoder"], function() {
			geocoder = new AMap.Geocoder({
	            radius: 1000,
	            extensions: "all"
	        });
	        //步骤三：通过服务对应的方法回调服务返回结果，本例中通过逆地理编码方法getAddress回调结果
	        geocoder.getLocation(city, function(status, result){
	            //根据服务请求状态处理返回结果
	            if(status=='complete') {
	                var arr = result.geocodes;
	                if(arr.length > 0){
	                	marker.setPosition(arr[0].location);
	                	setLocationText(marker);
	                }
	            }else{
	            	alert("城市解析失败");
	            }
	        });
			
		});
	}
	$('#hotelInfoWindow').window('open');
}

//显示标签设置窗口
function showTagdialog(type){
	var wz = getDialogPosition($('#tagInfoWindow').get(0),100);
	$('#tagInfoWindow').window({
		  	top: 100,
		    left: wz[1],
		    onBeforeClose: function () {
		    }
	});
	$('#tagInfoWindow').window('open');
}

//设置初始位置或拖拽后的经纬度信息
function setLocationText(marker){
	var position = marker.getPosition();
	document.getElementById("txt_location").innerHTML = "["+position.getLng()+", "+position.getLat()+"]";
}

//保存当前的经纬度信息到文本框
function saveLocation(){
	var txtLocation = document.getElementById("txt_location").innerHTML;
	document.getElementById("input_location").value = txtLocation;
	var temp = txtLocation.substring(1, txtLocation.length - 1);
	var arr = temp.split(",");
	var location = new AMap.LngLat(new Number(arr[0]), new Number(arr[1]));
	if(map_show != null){
		map_show.clearMap();
		new AMap.Marker({map:map_show,position:location});
		map_show.panTo(location);
	}
	
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
	var url = "${pageContext.request.contextPath}/web/base/jsonLoadRegionCityComboList.do?provinceId="+val;
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
	var url = "${pageContext.request.contextPath}/web/base/jsonLoadRegionAreaComboList.do?cityId="+val;
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

//更新项目列表
function refreshTable(){
	var table = document.getElementById("projectList");
	table.innerHTML = "";
	var trTitle = document.createElement("tr");
	trTitle.className = "ts15";
	trTitle.innerHTML = "<th style=\"width:5%;\">&nbsp;</th><th style=\"width:10%;\">项目名称</th><th style=\"width:5%;\">项目类型</th>"
	+"<th style=\"width:15%;\">项目描述</th><th style=\"width:5%;\">联系电话</th><th style=\"width:10%;\">位置</th><th style=\"width:5%;\">评分</th>"
	+"<th style=\"width:5%;\">状态</th><th style=\"width:40%;\">图片</th>";
	table.appendChild(trTitle);
	
	if(projectArr.length > 0){
		for(var i = 0; i < projectArr.length; i++){
			var pro = projectArr[i];
			var trLine = document.createElement("tr");
			trLine.className = "ts14";
			
			var tempImg = "<td><input type=\"checkbox\" value=\"0\"></td><td>"
			+pro.name+"</td><td>"+pro.type+"</td><td>"+pro.text+"</td><td>"+pro.tel+"</td><td>"+pro.position+"</td><td>"+pro.score+"</td><td>"+pro.status+"</td><td>";
			if(pro.imgItem != null){
				if(pro.imgItem.arr.length > 0){
					for(var j = 0; j < pro.imgItem.arr.length; j ++){
						tempImg+= "<img class=\"icon\" src=\""+pro.imgItem.arr[j].url+"\"/>";
					}
				}
			}
			tempImg += "</td>";
			
			trLine.innerHTML = tempImg;
			table.appendChild(trLine);
		}
	}
}

function returnBack(){
	window.history.back();
}

//保存数据
function save(){
	
}

//保存一个项目
function saveItem(){
	
	
	var proName = document.getElementById("project_name").value;
	if(proName.trim() == ""){
		alert("请输入项目名称");
		return ;
	}
	
	var proTel = document.getElementById("project_tel").value;
	if(proTel.trim() == ""){
		alert("请输入联系电话");
		return ;
	}
	
	alert(checkTel(proTel));
	
	var classification = $("#project_classification").combobox("getValue");
	if(classification == 0){
		alert("请选择项目分类");
		return ;
	}
	
	var proText = document.getElementById("project_text").value;
	if(proText.trim() == ""){
		alert("请输入项目描述");
		return ;
	}
	
	var proPosition = document.getElementById("project_position").value;
	if(proPosition.trim() == ""){
		alert("请输入位置描述");
		return ;
	}
	
	var project = new ObjectProject();
	project.name = proName.trim();
	project.tel = proTel.trim();
	project.type = new Number(classification);
	project.text = proText.trim();
	project.position = proPosition.trim();
	project.imgItem = tempImgArr;
	if($("#project_type").combobox("getValue") != 0){
		project.type = new Number($("#project_type").combobox("getValue"));
	}

	projectArr.push(project);
	$('#tagInfoWindow').window('close');
	
	document.getElementById("project_name").value = "";
	document.getElementById("project_tel").value = "";
	document.getElementById("project_text").value = "";
	document.getElementById("project_position").value = "";
	$("#project_classification").combobox("setValue", 0);
	$("#project_type").combobox("setValue", 0);
	document.getElementById("item_info").value = "";
	document.getElementById("item_file").value = "";
	clearFile();
	
	refreshTable();
}

function addFile(){
	var fileItem = document.getElementById("item_file");
	if(fileItem.value != ""){
		
		var obj = fileItem.files[0];
		//alert(obj.type); image/png
		var reader = new FileReader();
		reader.readAsDataURL(obj);
		reader.onload = function(e){
			addImgItem(obj, this.result);
		};
	}else{
		alert("请先选择一个文件");
	}
}

function deleteFile(){
	if(tempImgArr == null){
		alert("缓存内存初始化失败");
		return;
	}
	if(tempImgArr.arr.length > 0){
		var temp = new Array();
		for(var i = 0; i < tempImgArr.arr.length; i++){
			if(tempImgArr.arr[i].selected){
				temp.push(tempImgArr.arr[i]);
			}
		}
		if(temp.length > 0){
			var tempView = document.getElementById("temp_img_view");
			for(var j = 0; j < temp.length; j ++){
				tempView.removeChild(temp[j].obj);
				tempImgArr.remove(temp[j]);
			}
		}else{
			alert("没有找到需要删除的元素");
		}
	}else{
		alert("当前还没有可以删除的元素");
	}
}

function clearFile(){
	if(tempImgArr == null || tempImgArr.arr.length < 1){
		return ;
	}
	var tempView = document.getElementById("temp_img_view");
	for(var i = 0; i < tempImgArr.arr.length; i++){
		tempView.removeChild(tempImgArr.arr[i].obj);
	}
	tempImgArr = new ObjectImgItemList();
}

/**
* 添加缓存的酒店元素
*/
function addImgItem(file, url){
	var text = document.getElementById("item_info").value;

	var tempView = document.getElementById("temp_img_view");
	var imgItem = document.createElement("div");
	imgItem.className = "fl border ml10 mt10 ht160";
	imgItem.align = "center";
	
	var imgView = document.createElement("div");
	var img = document.createElement("img");
	img.className = "photo_show";
	img.src = url;
	imgView.appendChild(img);
	
	var txtView = document.createElement("div");
	txtView.className = "wid120 mt10";
	var txt = document.createElement("font");
	txt.className = "txt ts12";
	
	txt.innerHTML = text;
	txtView.appendChild(txt);
	
	imgItem.appendChild(imgView);
	imgItem.appendChild(txtView);
	
	tempView.appendChild(imgItem);
	
	if(tempImgArr == null){
		alert("缓存内存初始化失败");
		return ;
	}
	
	var objectImgItem = new ObjectImgItem(tempImgArr.id, imgItem);
	objectImgItem.setFile(file);
	objectImgItem.setUrl(url);
	objectImgItem.setText(text);
	objectImgItem.register();
	
	tempImgArr.add(objectImgItem);
}

//电话号码验证
function checkTel(value){
	var isPhone = /^([0-9]{3,4}-)?[0-9]{7,8}$/;
	var isMob=/^1[34578][0-9]{9}$/;
	if(isMob.test(value)||isPhone.test(value)){
		return true;
	}else{
		return false;
	}
}

//====================
// Object 对象区域
//====================

/**
* ObjectImgItem
*/
var ObjectImgItem = function(id, obj){
	this.id = id;
	this.obj = obj;
	this.selected = false;
	this.file= null;
	this.url = "";
	this.text = "";
	
	ObjectImgItem.prototype.register = function(){
		var context = this;
		this.obj.onclick = function(){
			if(context.selected){
				obj.className = "fl border ml10 mt10 ht160";
			}else{
				obj.className = "fl border_selected ml10 mt10 ht160";
			}
			
			context.selected = !context.selected;
		};
	};
	
	ObjectImgItem.prototype.setFile = function(file){
		this.file = file;
	};
	
	ObjectImgItem.prototype.setUrl = function(url){
		this.url = url;
	};
	
	ObjectImgItem.prototype.setText = function(text){
		this.text = text;
	};
};

/**
* ObjectImgItemList
*/
var ObjectImgItemList = function(){
	this.id = 1;
	this.arr = new Array();
	
	ObjectImgItemList.prototype.add = function(item){
		this.arr.push(item);
		this.id ++;
	};
	
	ObjectImgItemList.prototype.size = function(){
		return this.arr.length;
	};
	
	ObjectImgItemList.prototype.remove = function(item){
		if(this.size() > 0){
			var temp = new Array();
			for(var i = 0; i < this.size(); i ++){
				if(item.id != this.arr[i].id){
					temp.push(this.arr[i]);
				}
			}
			this.arr = temp;
			temp = null;		
		}
	};
};

/**
* ObjectImgItemList
*/
var ObjectProject = function(){
	this.name = "";
	this.tel = "";
	this.type = 0;
	this.text = "";
	this.position = "";
	this.score = 0;
	this.status = "激活";
	this.imgItem=null;
};


</script>
<style type="text/css">
.ts12{
	font-family: '微软雅黑';
	font-size: 12px;
}

.ts14{
	font-family: '微软雅黑';
	font-size: 14px;
}

.ts15{
	font-family: '微软雅黑';
	font-size: 15px;
}

.icon{
	width: 32px;
	height: 32px;
}

.map_style{
	width: 600px;
	height: 240px;
}

.imgs_style{
	width: auto;
	height: 190px;
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

.ht160{
	height: 160px;
}

.border{
	border: 1px #C4C4C4 solid;
	padding: 10px;
	border-radius: 4px; 
	-moz-border-radius:4px;
	-webkit-border-radius:4px;
	cursor: pointer;
}

.border_selected{
	border: 1px #FF0000 solid;
	padding: 10px;
	border-radius: 4px; 
	-moz-border-radius:4px;
	-webkit-border-radius:4px;
	cursor: pointer;
}

.divider{
	width: 100%;
	height: 1px;
	background-color: #F3F3F3;
}


.txt_function{
	font-family: "微软雅黑";
	font-size: 12px;
	color: #00B7FF;
	cursor: pointer;
}

.photo_show{
	width: 120px;
	height: 120px;
	cursor: pointer;
}

.photo_show_selected{
	width: 118px;
	height: 118px;
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
				<div class="fl"><span class="txt_function ml20" onclick="returnBack();">[返回]</span></div>
				<div class="fr"><span class="txt_function mr20" onclick="save();">[保存]</span></div>
				<div class="cl"></div>
			</div>
		</div>

		<div class="fl yw-lump mt10">
			<div id="tab2" class="yw-tab">
				<div class="fl yw-lump mt10">
					<form id="userForm" name="userForm"
						action="userList.do" method="get">
						<div class="pd10-28">
							<div class="fl">
								<div>
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
									<span class="ts15">描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述：</span><textarea cols="50" rows="6" style="vertical-align: top; border: 1px #C4C4C4 solid;"></textarea>
								</div>
								<div class = "mt20">
									<span class="ts15">经&nbsp;纬&nbsp;度：</span><input id="input_location" type="text" readonly="readonly" class="yw-input wid200 ts14" /><img alt="点击标记位置" onclick="showdialog();" class="icon_location" src="${pageContext.request.contextPath}/source/images/location.png">
									<span class="hint_red">**必填项&nbsp;&nbsp;&nbsp;&nbsp;点击标记图标可设置指定位置的经纬度**</span>
								</div>
							</div>
							<div class="fr">
								<div class="fl"><span class="ts15">位置：</span></div><div id="show_mapContainer" class="fr map_style"></div>
								<div class="cl"></div>
							</div>
							<div class="cl"></div>
						</div>
			
						<input type="hidden" id="pageNumber" name="pageNo"
							value="${hotel.pageNo}" />
					</form>
				</div>
				<div class="fl yw-lump">
					<div class="yw-lump-title">
						<span class="txt ts15 ml20">项目</span>
					</div>
				</div>
				<div class="cl"></div>
				<div class="mt10"><span class="txt_function ml20" onclick="showTagdialog();">[添加]</span><span class="txt_function ml20">[修改]</span><span class="txt_function ml20">[删除]</span></div>
				<table class="yw-cm-table" id="projectList">
					<tr class="ts15">
						<th style="width:5%;">&nbsp;</th>
						<th style="width:10%;">项目名称</th> 
						<th style="width:5%;">项目类型</th>
						<th style="width:15%;">项目描述</th>
						<th style="width:5%;">联系电话</th>
						<th style="width:10%;">位置</th>
						<th style="width:5%;">评分</th>
						<th style="width:5%;">状态</th>
						<th style="width:40%;">图片</th>
					</tr>
					<c:forEach var="item" items="${itemList}">
						<tr class="ts14">
							<td align="left"><input type="checkbox" value="${item.id}"></td>
							<td>${item.name}</td> 
							<td>${item.text}</td> 
							<td>${item.tel}</td> 
							<td>${item.position}</td> 
							<td>${item.score}</td>
							<td>
								<c:if test="${item.isUsed==1}">激活</c:if>
								<c:if test="${item.isUsed!=1}">禁用</c:if>
							</td>
							<td>&nbsp;</td>
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
	
	<div id="tagInfoWindow" class="easyui-window" title="项目设置" style="width:520px;height:560px;overflow:hidden;padding:10px;" iconCls="icon-info" closed="true" modal="true"   resizable="false" collapsible="false" minimizable="false" maximizable="false">
		<div>
			<span class="txt ts14">项目名称：</span><input id="project_name" type="text" class="yw-input wid170 ts14" />
			<span class="txt ts14 ml10">联系电话：</span><input id="project_tel" type="text" class="yw-input wid170 ts14" />
		</div>
		<div class="mt10">
			<span class="txt ts14">项目类型：</span>
			<select id="project_classification" style="width:160px;height:30px;" class="easyui-combobox">
		 	 	<option  value="0" selected="selected">=请选择项目分类=</option>
		 	 	<c:forEach var="item" items="${tagList}">
					<option  value="${item.id}">${item.name}</option>
				</c:forEach>
			</select>
			<select id="project_type" style="width:160px;height:30px;" class="easyui-combobox ml10">
		 	 	<option  value="0" selected="selected">=请选择项目类型=</option>
			</select>
		</div>
		<div class="mt10">
			<span class="txt ts14">项目描述：</span><input id="project_text" type="text" class="yw-input wid170 ts14" />
			<span class="txt ts14 ml10">位置描述：</span><input id="project_position" type="text" class="yw-input wid170 ts14" />
		</div>
		<div class="divider mt20"></div>
		<div class="mt10">
			<div class="fl"><span class="txt ts14">图片：</span></div>
			<div class="fr"><span class="txt_function" onclick="deleteFile();">[删除]</span></div>
			<div class="cl"></div>
		</div>
		<div id="temp_img_view" class="imgs_style">
			
		</div>
		
		<div>
			<div class="fl">
				<div class="ml10 mt10">
					<span class="txt ts14">文件：</span><input id="item_file" type="file"/>
				</div>
				<div class="ml10 mt10">
					<span class="txt ts14">描述：</span><input id="item_info" type="text" class="yw-input ts14 wid200"/>
				</div>
			</div>
			<div class="fr">
				<span class="txt_function" onclick="addFile();">[添加]</span>
			</div>
			<div class="cl"></div>
		</div>
		
		<div class="divider mt10"></div>
		
		<div class="mt30" align="center">
			<span onclick="saveItem();" class="yw-btn bg-blue ml20 cur ts15">保存</span>
		</div>
	</div>
</body>
</html>
