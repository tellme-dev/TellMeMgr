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

var tempImgArr = null;
var itemTagIds = "";

$(document).ready(function(){
	//$("#project_classification").combobox({
	//	onChange: function (n,o) {
	//		if(n == 0){
	///			return ;
	//		}
	//		setTag(n);
	//	}
	//});
	//initItemTagTree();
	$('#itemTagSelect').combotree( {  
       url : '${pageContext.request.contextPath}/web/ad/jsonLoadItemTagTree.do?pid=0',
       onChange:function(){
       		 var tree = $('#itemTagSelect').combotree('tree');
       		 var node = tree.tree('getChecked');
       		 var txt = "";
       		 var ids = "";
       		 if(node.length > 0){
       		 	for(var i = 0; i < node.length; i ++){
       		 		if(node[i].level == 2){
       		 			ids += node[i].id+",";
       		 			txt += node[i].name+",";
       		 		}
       		 	}
       		 }
       		 if(txt.indexOf(",") != -1){
       		 	txt = txt.substring(0, txt.length - 1);
       		 }
       		 if(ids.indexOf(",") != -1){
       		 	ids = ids.substring(0, ids.length - 1);
       		 }
       		 //$('#itemTagSelect').combotree('setValues',ids);
       		 itemTagIds = ids;
       		 $('#itemTagSelect').combotree('setText',txt);
       }
    });

	tempImgArr = new ObjectImgItemList();
});

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

function saveProject(obj){
	$(obj).attr("onclick", ""); 
	$('#projectForm').form('submit',{
	 		success:function(data){
	 			data = $.parseJSON(data);
	 			if(data.code==1){
	 				$.messager.alert('保存信息',data.message,'info',function(){
	 					$('#tagInfoWindow').window('close');
	 					resetWindow();
	 					$(obj).attr("onclick", "saveItem(this);"); 
	 					refresh();
	      			});
	 			}else{
				$.messager.alert('错误信息',data.message,'error',function(){
					$(obj).attr("onclick", "saveItem(this);"); 
	      			});
	 			}
	 		}
	 	 });  
}


//删除所选项目
function deleteProject(obj){
	var cbs = document.getElementsByName("cb_projectId");
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
			var url = "${pageContext.request.contextPath}/web/hotel/jsonLoadDeleteHotelProject.do?projectId="+ids;
			requestGet(url, {}, function(data){
				if(data.code == 1){
					myAlert("删除数据成功");
					$(obj).attr("onclick", "deleteProject(this);");
					refresh();
				}else{
					myAlert(data.message);
					$(obj).attr("onclick", "deleteProject(this);");
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

/**
function setTag(val){
	var url = "${pageContext.request.contextPath}/web/base/jsonLoadItemTagComboList.do?itemId="+val;
	requestGet(url, {}, function(data){
		if(data.length > 0){
			var dat = "[";
			var fopt = "{\"value\":\"0\",\"text\":\"=请选择类型=\",\"selected\":true},";
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
			$("#project_type").combobox("loadData", json);
		}
	});
}
*/

function returnBack(){
	window.history.back();
}
function refresh(){
	window.location.reload();
}

//保存一个项目
function saveItem(obj){
	
	var proName = document.getElementById("project_name").value;
	if(proName.trim() == ""){
		myAlert("请输入项目名称");
		return ;
	}
	
	var proTel = document.getElementById("project_tel").value;
	if(proTel.trim() == ""){
		myAlert("请输入联系电话");
		return ;
	}
	
	if(!checkTel(proTel)){
		myAlert("联系电话格式不正确");
		return ;
	}
	
	//var classification = $("#project_classification").combobox("getValue");
	//if(classification == 0){
	//	myAlert("请选择项目分类");
	//	return ;
	//}
	var type = itemTagIds;
	if(type == ""){
		myAlert("请选择项目类型");
		return ;
	}
	
	var proText = document.getElementById("project_text").value;
	if(proText.trim() == ""){
		myAlert("请输入项目描述");
		return ;
	}
	
	//var proPosition = document.getElementById("project_position").value;
	//if(proPosition.trim() == ""){
	//	myAlert("请输入位置描述");
	//	return ;
	//}
	
	//var type = classification;
	//if($("#project_type").combobox("getValue") != 0){
	//	type = $("#project_type").combobox("getValue");
	//}
	//alert(type);
	//return;
	document.getElementById("project_type_item").value = type;
	
	if(tempImgArr != null && tempImgArr.size() > 0){
		var view = document.getElementById("submit_hidden_view");
		var len = tempImgArr.size();
		document.getElementById("project_file_count").value = ""+len;
		for(var i = 0; i < len; i ++){
			var item = tempImgArr.arr[i];
			var input = document.createElement("input");
			input.type = "hidden";
			input.name = "file"+i;
			input.value = item.url;
			view.appendChild(input);
			
			var ip_text = document.createElement("input");
			ip_text.type = "hidden";
			ip_text.name = "fileText"+i;
			ip_text.value = item.ta.value;
			view.appendChild(ip_text);
		}
	}
	saveProject(obj);
}

function resetWindow(){
	document.getElementById("project_name").value = "";
	document.getElementById("project_tel").value = "";
	document.getElementById("project_text").value = "";
	document.getElementById("project_position").value = "";
	//$("#project_classification").combobox("setValue", 0);
	//$("#project_type").combobox("setValue", 0);
	//document.getElementById("item_info").value = "";
	$('#itemTagSelect').combotree('setValues',"");
    itemTagIds = "";
	document.getElementById("item_file").value = "";
	document.getElementById("project_file_count").value="0";
	document.getElementById("project_type_item").value="";
	document.getElementById("project_id").value="0";
	clearFile();
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
		myAlert("请先选择一个文件");
	}
}

function deleteFile(obj){
	var tempView = document.getElementById("temp_img_view");
	tempView.removeChild(obj.obj);
	tempImgArr.remove(obj);
	return ;
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
* 添加缓存的酒店图片元素
*/
function addImgItem(file, url){
	var tempView = document.getElementById("temp_img_view");
	var imgItem = document.createElement("div");
	imgItem.className = "border ml10 mt10";
	
	var imgView = document.createElement("div");
	imgView.className = "fl";
	var img = document.createElement("img");
	img.className = "photo_show";
	img.src = url;
	imgView.appendChild(img);
	
	var txtView = document.createElement("div");
	txtView.className = "wid300 ml10 fl";
	var txt = document.createElement("textarea");
	txt.className = "v_top wid300 ts14";
	txt.rows= "3";
	txt.maxLength = "100";
	
	var iconView = document.createElement("div");
	iconView.className = "fl ml40";
	var icon = document.createElement("img");
	icon.className = "icon_show";
	icon.src = "${pageContext.request.contextPath}/source/images/icon_delete.png";
	iconView.appendChild(icon);
	
	txtView.appendChild(txt);
	
	var clear = document.createElement("div");
	clear.className = "cl";
	
	imgItem.appendChild(imgView);
	imgItem.appendChild(txtView);
	imgItem.appendChild(iconView);
	imgItem.appendChild(clear);
	
	tempView.appendChild(imgItem);
	
	var objectImgItem = new ObjectImgItem(tempImgArr.id, imgItem);
	objectImgItem.setFile(file);
	objectImgItem.setUrl(url);
	objectImgItem.ta = txt;
	
	icon.onclick = function(){
		deleteFile(objectImgItem);
	};
	
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

function myAlert(msg){
	$.messager.alert('提示', msg,'info',function(){});
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
	this.ta = null;

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
.wid300{
	width: 300px;
}

.wid428{
	width: 428px;
}

.wid400{
	width: 420px;
}

.ht160{
	height: 160px;
}

.v_top{
	vertical-align: top;
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
	width: 60px;
	height: 60px;
	cursor: pointer;
}

.icon_show{
	width: 32px;
	height: 32px;
	margin-top: 14px;
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
				<span class="txt_function ml20" onclick="returnBack();">[返回]</span>
			</div>
		</div>

		<div class="fl yw-lump mt10">
			<div id="tab2" class="yw-tab">
				<div class="fl yw-lump">
					<div class="yw-lump-title">
						<div align="left" class="ml10">
							<span class="yw-btn bg-green cur ts15" onclick="showTagdialog(true);">添加项目</span>
							<!-- <span class="yw-btn bg-blue ml20 cur ts15" onclick="loadHotelInfo(false);">修改项目</span> -->
							<span class="yw-btn bg-orange ml20 cur ts15" onclick="deleteProject(this);">删除项目</span>
						</div>
					</div>
				</div>
				<div class="cl"></div>
				
				<table class="yw-cm-table" id="projectList">
					<tr class="ts15">
						<th style="width:5%;">选择</th>
						<th style="width:10%;">项目名称</th> 
						<th style="width:5%;">项目类型</th>
						<th style="width:15%;">项目描述</th>
						<th style="width:10%;">联系电话</th>
						<th style="width:10%;">位置</th>
						<th style="width:5%;">评分</th>
						<th style="width:5%;">状态</th>
						<th style="width:35%;">图片</th>
					</tr>
					<c:forEach var="item" items="${itemList}">
						<tr class="ts14">
							<td align="left"><input name="cb_projectId" type="checkbox" value="${item.item.id}"></td>
							<td>${item.item.name}</td>
							<td>&nbsp;</td>
							<td>${item.item.text}</td> 
							<td>${item.item.tel}</td> 
							<td>${item.item.position}</td> 
							<td>${item.item.score}</td>
							<td>
								<c:if test="${item.item.isUsed}">激活</c:if>
								<c:if test="${!item.item.isUsed}">禁用</c:if>
							</td>
							<td>
								<c:forEach var="temp" items="${item.details}">
									<img class="icon" alt="" src="${pageContext.request.contextPath}/${temp.imageUrl}">
								</c:forEach>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		
		<div class="cl"></div>

	</div>
	<div class="cl"></div>
	
	<div id="tagInfoWindow" class="easyui-window" title="项目设置" style="width:520px;height:680px;overflow:hidden;padding:10px;" iconCls="icon-info" closed="true" modal="true"   resizable="false" collapsible="false" minimizable="false" maximizable="false">
		<form name="projectForm" id="projectForm" action="saveOrupdateHotelProject.do" method="post">
			<div>
				<span class="txt ts14">项目名称：</span><input id="project_name" name="projectName" type="text" class="yw-input wid428 ts14" />
			</div>
			<div class="mt10">
				<span class="txt ts14">联系电话：</span><input id="project_tel" name="projectTel" type="text" class="yw-input wid428 ts14" />
			</div>
			<div class="mt10">
				<span class="txt ts14">项目类型：</span>
				<input id="itemTagSelect" name="targetId" class="easyui-combotree" data-options="editable:false,multiple:true" style="width:420px;height:30px;"/>
				<!--
				<select id="project_classification" style="width:160px;height:30px;" class="easyui-combobox">
			 	 	<option  value="0" selected="selected">=请选择项目分类=</option>
			 	 	<c:forEach var="item" items="${tagList}">
						<option  value="${item.id}">${item.name}</option>
					</c:forEach>
				</select>
				<select id="project_type" style="width:160px;height:30px;" class="easyui-combobox ml10">
			 	 	<option  value="0" selected="selected">=请选择项目类型=</option>
				</select>
				-->
			</div>
			<div class="mt10">
				<span class="txt ts14">项目描述：</span>
				<!-- <input id="project_text" name="projectText" type="text" class="yw-input wid428 ts14" /> -->
				<textarea id="project_text" name="projectText" rows="3" cols="" maxlength="100" class="v_top wid400 ts14"></textarea>
			</div>
			<div class="mt10">
				<span class="txt ts14">位置描述：</span>
				<!-- <input id="project_position" name="projectPosition" type="text" class="yw-input wid428 ts14" /> -->
				<textarea id=project_position name="projectPosition" rows="3" maxlength="100" cols="" class="v_top wid400 ts14"></textarea>
			</div>
			<input id="project_id" name="projectId" type="hidden" value="0"/>
			<input name="hotelId" type="hidden" value="${hotelId}"/>
			<input id="project_type_item" name="projectType" type="hidden"/>
			<input id="project_file_count" name="fileCount" type="hidden" value="0"/>
			<div id="submit_hidden_view">
			</div>
		</form>
		<div class="divider mt20"></div>
		<div class="mt10">
			<div class="fl"><span class="txt ts14">图片：</span></div>
			<div class="cl"></div>
		</div>
			
		<div id="temp_img_view" class="imgs_style">
			
		</div>
		
		<div>
			<div class="fl">
				<div class="ml10 mt10">
					<span class="txt ts14">文件：</span><input onchange="addFile()" id="item_file" type="file" accept="image/png, image/jpeg"/>
				</div>
			</div>
			<div class="fr">
			</div>
			<div class="cl"></div>
		</div>
		
		<div class="divider mt10"></div>
		
		<div class="mt30" align="center">
			<span onclick="saveItem(this);" class="yw-btn bg-blue ml20 cur ts15">保存</span>
		</div>
		
	</div>
</body>
</html>
