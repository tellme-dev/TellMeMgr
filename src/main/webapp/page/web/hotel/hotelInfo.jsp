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
<script src="http://webapi.amap.com/maps?v=1.3&key=7794bac5851d466a825637e82e4a7926&plugin=AMap.DistrictSearch"></script>
  	
<script
	src="${pageContext.request.contextPath}/source/js/pager/jquery.pager.js"></script>
<link
	href="${pageContext.request.contextPath}/source/js/pager/Pager.css"
	rel="stylesheet" />
<script type="text/javascript">
var map_show = null;
var map_edit = null;

//地图省市区数据对象
var amapAdcode = {};

$(document).ready(function(){
	
	$("#province").combobox({
		onChange: function (n,o) {
			if(n == 0){
				return ;
			}
			amapAdcode.createCity(n);
		}
	});
	
	$("#city").combobox({
		onChange: function (n,o) {
			if(n == 0){
				return ;
			}
			amapAdcode.createDistrict(n);
		}
	});
	
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
    
    var id = ${ht.id};
	if(id != 0){
		var lng = ${ht.longitude};
		var lat = ${ht.latitude};
		document.getElementById("input_location").value = "["+lng+","+lat+"]";
		var location = new AMap.LngLat(lng, lat);
		if(map_show != null){
			map_show.clearMap();
			new AMap.Marker({map:map_show,position:location});
			map_show.panTo(location);
		}
	}
	
	//省市区数据对象设置
	amapAdcode._district = new AMap.DistrictSearch({//高德行政区划查询插件实例
        subdistrict: 1   //返回下一级行政区
    });
    amapAdcode._overlay = [];//行政区划覆盖物
    amapAdcode.createSelectList = function(selectId, list) {//生成下拉列表
    	/*
        var selectList = document.getElementById(selectId);
        selectList.innerHTML = '';
        selectList.add(new Option('--请选择--'));
        for (var i = 0, l = list.length, option; i < l; i++) {
            option = new Option(list[i].name);
            option.setAttribute("value", list[i].adcode);
            selectList.add(option);
        }
        */
        var provinceName = '${provinceRegion.name}';
        var cityName = '${cityRegion.name}';
        var areaName = '${arearRegion.name}';
        var areaCode = '${arearRegion.code}';
        var sName = "区域";
        var usName = "";
        if(areaName != ''){
        	usName = areaName;
        }
        if(selectId == "province"){
			$("#city").combobox("loadData", eval("[{\"value\":\"0\",\"text\":\"=请选择城市=\",\"selected\":true}]"));
			$("#district").combobox("loadData", eval("[{\"value\":\"0\",\"text\":\"=请选择区域=\",\"selected\":true}]"));
			sName = "省份";
			if(provinceName != ''){
				usName = provinceName;
				$("#city").combobox("loadData", eval("[{\"value\":\"0\",\"text\":\"="+cityName+"=\",\"selected\":true}]"));
				$("#district").combobox("loadData", eval("[{\"value\":\""+areaCode+"\",\"text\":\"="+areaName+"=\",\"selected\":true}]"));
			}
		}
		if(selectId == "city"){
			$("#district").combobox("loadData", eval("[{\"value\":\"0\",\"text\":\"=请选择区域=\",\"selected\":true}]"));
			sName = "城市";
			if(cityName != ''){
				usName = cityName;
				$("#district").combobox("loadData", eval("[{\"value\":\""+areaCode+"\",\"text\":\"="+areaName+"=\",\"selected\":true}]"));
			}
		}
        var titleString = "请选择"+sName;
        if(usName != ''){
        	titleString = usName;
        }
        
        var dat = "[";
		var fopt = "{\"value\":\"0\",\"text\":\"="+titleString+"=\",\"selected\":true},";
		dat += fopt;
		
		var len = list.length;
		for(var i =0; i < len; i++){
			var opt;
			if(i == len - 1){
				opt = "{\"value\":\""+list[i].adcode+"\",\"text\":\""+list[i].name+"\",\"selected\":false}";
			}else{
				opt = "{\"value\":\""+list[i].adcode+"\",\"text\":\""+list[i].name+"\",\"selected\":false},";
			}
			
			dat += opt;
		}
		
		dat += "]";
		var json = eval(dat);
		
		$("#"+selectId).combobox("loadData", json);
    };
    amapAdcode.search = function(adcodeLevel, keyword, selectId) {//查询行政区划列表并生成相应的下拉列表
        var me = this;
        if (adcodeLevel == 'district'||adcodeLevel == 'city') {//第三级时查询边界点
            this._district.setExtensions('all');
        } else {
            this._district.setExtensions('base');
        }
        this._district.setLevel(adcodeLevel); //行政区级别
        this._district.search(keyword, function(status, result) {//注意，api返回的格式不统一，在下面用三个条件分别处理
            var districtData = result.districtList[0];
            if (districtData.districtList) {
                me.createSelectList(selectId, districtData.districtList);
            } else if (districtData.districts) {
                me.createSelectList(selectId, districtData.districts);
            } else {
                document.getElementById(selectId).innerHTML = '';
            }
        });
    };
    amapAdcode.clear = function(selectId) {//清空下拉列表
        var selectList = document.getElementById(selectId);
        selectList.innerHTML = '';
    };
    amapAdcode.createProvince = function() {//创建省列表
        this.search('country', '中国', 'province');
    };
    amapAdcode.createCity = function(provinceAdcode) {//创建市列表
        this.search('province', provinceAdcode, 'city');
        this.clear('district');
    };
    amapAdcode.createDistrict = function(cityAdcode) {//创建区县列表
        this.search('city', cityAdcode, 'district');
    };
    amapAdcode.createProvince();
});



//显示地理位置区域
function showdialog(){
	var val = $("#city").combobox("getValue");
	if(val == 0){
		myAlert("请先选中一个城市");
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
		if(city.indexOf("=") != -1){
			city = city.substring(1, city.length - 1);
		}
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
	            	myAlert("城市解析失败");
	            }
	        });
			
		});
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
			$("#district").combobox("loadData", eval("[{\"value\":\"0\",\"text\":\"=请选择区域=\",\"selected\":true}]"));
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
			$("#district").combobox("loadData", json);
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

function returnBack(){
	window.history.back();
}

function submitHotel(obj){
	var name = document.getElementById("hotel_name");
	if(name.value.trim() == ""){
		myAlert("请输入酒店名称");
		return ;
	}
	var region = $("#district").combobox("getValue");
	if(region == 0){
		myAlert("请选择酒店所属区域");
		return ;
	}
	//隐藏控件赋值
	document.getElementById("hotel_region").value = region;
	document.getElementById("ht_province").value = $("#province").combobox("getText");
	document.getElementById("ht_city").value = $("#city").combobox("getText");
	document.getElementById("ht_area").value = $("#district").combobox("getText");
	
	var location = document.getElementById("input_location");
	if(location.value.trim() == ""){
		myAlert("请标记酒店所在位置");
		return ;
	}
	var temp = location.value.substring(1, location.value.length - 1);
	var arr = temp.split(",");
	//隐藏控件赋值
	document.getElementById("hotel_lng").value = new Number(arr[0]);
	document.getElementById("hotel_lat").value = new Number(arr[1]);
	
	saveHotel(obj);
}

function saveHotel(obj){
		$(obj).attr("onclick", "");
		$('#hotelForm').form(
					'submit',
					{
						success : function(data) {
							data = $.parseJSON(data);
							
							if (data.code == 1) {
								$.messager.alert('保存信息', data.message, 'info',
										function() {
											returnBack();
											//setShowStates();
										});
								$(obj).attr("onclick", "submitHotel(this);");
							} else {
								$.messager.alert('错误信息', data.message, 'error',
										function() {
											$(obj).attr("onclick",
													"submitHotel(this);");
										});
							}
						}
					});
}

function myAlert(msg){
	$.messager.alert('提示', msg,'info',function(){});
}

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

.map_style{
	width: 600px;
	height: 240px;
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
</style>
</head>

<body>
	<div class="con-right" id="conRight">
		<div class="fl yw-lump">
			<div class="yw-lump-title">
				<span class="txt_function ml20" onclick="returnBack();">[返回]</span>
			</div>
		</div>

		<div class="fl yw-lump mt10">
			<div id="tab2" class="yw-tab">
				<div class="fl yw-lump mt10">
					<form id="hotelForm" name="hotelForm"
						action="saveOrupdateHotel.do" method="post">
						<div class="pd10-28">
							<div class="fl">
								<div>
									<input name="id" type="hidden" id="hotel_id" value="${ht.id}" />
									<span class="ts15">酒店名称：</span><input id="hotel_name" value="${ht.name}" name="name" type="text" class="yw-input wid200 ts14" />
									<span class="hint_red">**必填项**</span>
								</div>
								<div class = "mt20">
									<input name = "regionId" id="hotel_region" type="hidden" value="${ht.regionId}" />
									<span class="ts15">酒店区域：</span>
									<select id="province" name="province" style="width:120px;height:30px;" class="easyui-combobox">
										<c:if test="${ht.id==0}">
											<option selected="selected" value="0">=请选择省份=</option>
										</c:if>
										
										<c:if test="${ht.id!=0}">
											<option selected="selected" value="0">=${provinceRegion.name}=</option>
										</c:if>
								 	 	
									</select>
									<select id="city" name="city" style="width:120px;height:30px;" class="easyui-combobox">
								 	 	<c:if test="${ht.id==0}">
											<option  value="0" selected="selected">=请选择城市=</option>
										</c:if>
										
										<c:if test="${ht.id!=0}">
											<option selected="selected" value="0">=${cityRegion.name}=</option>
										</c:if>
									</select>
									<select id="district" name="area" style="width:120px;height:30px;" class="easyui-combobox">
								 	 	
								 	 	<c:if test="${ht.id==0}">
											<option  value="0" selected="selected">=请选择区域=</option>
										</c:if>
										
										<c:if test="${ht.id!=0}">
											<option selected="selected" value="${arearRegion.code}">=${arearRegion.name}=</option>
										</c:if>
									</select>
									<span class="hint_red">**必填项**</span>
								</div>
								<div class = "mt20">
									<span class="ts15">描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述：</span><textarea name="text" id="hotel_text" cols="50" rows="6" style="vertical-align: top; border: 1px #C4C4C4 solid;">${ht.text}</textarea>
								</div>
								<div class = "mt20">
									<input name = "ht_province" id="ht_province" type="hidden" value="" />
									<input name = "ht_city" id="ht_city" type="hidden" value="" />
									<input name = "ht_area" id="ht_area" type="hidden" value="" />
									<input name = "longitude" id="hotel_lng" type="hidden" value="${ht.longitude}" />
									<input name = "latitude" id="hotel_lat" type="hidden" value="${ht.latitude}" />
									<span class="ts15">经&nbsp;纬&nbsp;度：</span><input id="input_location" name="hotel_location" type="text" readonly="readonly" class="yw-input wid200 ts14" /><img alt="点击标记位置" onclick="showdialog();" class="icon_location" src="${pageContext.request.contextPath}/source/images/location.png">
									<span class="hint_red">**必选项&nbsp;&nbsp;&nbsp;&nbsp;点击标记图标可设置指定位置的经纬度**</span>
								</div>
							</div>
							<div class="fr">
								<div class="fl"><span class="ts15">位置：</span></div><div id="show_mapContainer" class="fr map_style"></div>
								<div class="cl"></div>
							</div>
							<div class="cl"></div>
						</div>
					</form>
				</div>
				<div class="fl yw-lump">
					<div class="yw-lump-title">
						
						<c:if test="${ht.id==0}">
							<span class="yw-btn bg-blue ml20 cur ts15" onclick="submitHotel(this);">添加</span>
						</c:if>
						
						<c:if test="${ht.id!=0}">
							<span class="yw-btn bg-blue ml20 cur ts15" onclick="submitHotel(this);">修改</span>
						</c:if>
					</div>
				</div>
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
