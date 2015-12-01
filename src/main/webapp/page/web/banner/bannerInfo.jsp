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
<title>广告信息</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1  ,maximum-scale=1, user-scalable=no" /> 
<script type="text/javascript">
    var type;//0为新增；1为编辑
    $(document).ready(function(){
    	type = ${type};
    	setShowStates();
    	if(type==1){//编辑
    	}
    	
    });
    
	function setShowStates(){
		$("#txtTagContent").attr("readonly","readonly");
		$("#itemTagSelect").combotree("disable",true);
		$("#hotelSelect").combobox("disable",true);
		$("#select_bbs").hide();
	};
	function save(obj) {
		var doc=$(":radio:checked");
    	if(doc.length == 0){
    		$.messager.alert('提示信息', "请选择类型！", "warning");
    		return;
    	}
    	var targetType = doc[0].value;
    	if(targetType == 1&&$("#hotelSelect").combobox("getValue") == ""){
    		$.messager.alert('提示信息', "请选择酒店！", "warning");
    		return;
    	}
    	if(targetType == 2&&$("#itemTagSelect").combotree("getValue") == ""){
    		$.messager.alert('提示信息', "请选择标签 ！", "warning");
    		return;
    	}
    	if(targetType == 3&&$("#bbsSelect").val() == ""){
    		$.messager.alert('提示信息', "请选择社区！", "warning");
    		return;
    	}
    	if(targetType != 1&&targetType != 2&&targetType != 3&&$("#txtTagContent").val() == ""){
    		$.messager.alert('提示信息', "请填写内容！", "warning");
    		return;
    	}
    	//遍历文件 判断是否有空的
    	var file = $("#tab1 input[type='file']");
    	for(var j=0;j<file.length;j++){
    	   var url = $("#file_"+(j+1)).val();
			if(url == ""||url == null){
    		   $.messager.alert('提示信息', "请选择图片！", "warning");
    		   return;
    	    }
    	}
    	
    	//遍历图片描述框
    	var text = "";
		var imageText = document.getElementsByName("imagetext");
		for(var i=0;i<imageText.length;i++){
			var imgText = imageText[i].value;
			if(imgText == ""||imgText == null){
    		   $.messager.alert('提示信息', "请填写图片描述！", "warning");
    		   return;
    	    }
			//将多个图片描述拼接为字符串
			if(i==0){
				text += imgText;
			}else{
				text += ","+imgText;
			}
		}
		//将图片描述放到影藏标签中
		var imageTexts = document.getElementById("imageText");
		imageTexts.value = text;
		
		//编辑时遍历原有图片id 将其拼接为字符串再放入隐藏标签（不包括新增的图片）
		if(type == 1){
		    var adDetailIds = "";
		    var doc = document.getElementsByName("adDetailId");
		    for(var k=0;k<doc.length;k++){
		         var adDetailId = doc[k].value;
		         if(k==0){
		            adDetailIds += adDetailId;
		         }else{
		            adDetailIds += ","+adDetailId;
		         }
		    }
		    var doc2 = document.getElementById("adDetailIds");
		    doc2.value = adDetailIds;
		    //将已删除的图片id放入隐藏标签中
		    document.getElementById("delAdDetailIds").value = delAdDetailIds;
		}
		if ($('#adForm').form('validate')) {
			$(obj).attr("onclick", "");
			$('#adForm').form(
					'submit',
					{
						success : function(data) {
							data = $.parseJSON(data);
							if (data.code == 1) {
								$.messager.alert('保存信息', data.message, 'info',
										function() {  
									     window.location.href='adList.do';
										});
							} else {
								$.messager.alert('错误信息', data.message, 'error');
							}
						}
					});
		}
	}; 
	function showdialog(){
		var wz = getDialogPosition($('#bbsWindow').get(0),100);
		$('#bbsWindow').window({
			  	top: 100,
			    left: wz[1],
			    scrollbars:true,
			    onBeforeClose: function () {
			    }
		});
		$('#bbsWindow').window('open',"/","","scrollbars=yes");
	}
	function selectAd(bbsId,title){
		document.getElementById("bbsShow").value = title;
		document.getElementById("bbsSelect").value = bbsId;
		$('#bbsWindow').window('close');
	}
</script>
</head>

<body>
	<div class="con-right" id="conRight">
		<div class="fl yw-lump">
			<div class="yw-lump-title">
				<span style="cursor: pointer;" onclick="window.location.href='bannerList.do'"><i class="yw-icon icon-back"></i>返回</span>
			</div>
		</div>

		<div class="fl yw-lump mt10">
			<div class="yw-bi-rows">
				<div class="yw-bi-tabs mt5" id="ywTabs">
					<span class="yw-bi-now">广告位置</span>
				</div>
				<div class="fr mt10">
					<!-- <span class="yw-btn bg-green mr26" id="editBtn" onclick="edit();">编辑</span>  -->
					<span class="yw-btn bg-red mr26" id="saveBtn" onclick="save(this);" style="cursor: pointer;">保存</span>
				</div>
			</div>
			<div class="yw-tab">
				<form id="bannerForm" name="bannerForm"
					action="saveOrupdateBanner.do" method="post">
					<table id="tab1" class="yw-cm-table font16">
						<tr>
							<td width="10%" align="center">位置：</td>
							<td colspan="2">
							   <select id="positionSelect" name="positionType" style="width:250px;height:30px;" class="easyui-combobox" data-options="editable:false">
							     <!-- <option value="1">首页顶部</option>
							     <option value="2">首页底部</option> -->
							     <c:forEach var="item" items="${bannerlist }">
							       <option value="${item.positionType }">${item.position}</option>
							     </c:forEach>
							  </select>
							</td>
						</tr>
						<tr>
							<td width="10%" align="center">广告：</td>
							<td colspan="2">
							   <input id="adShow" class="easyui-validatebox" value="${bannerinfo.adName}" readonly="readonly" placeholder="请选择广告" style="width:500px;height:30px;"/>
							   <input type="hidden" id="adSelect" name="adIds" value="${bannerinfo.adIds}" class="easyui-validatebox" style="width:500px;height:30px;"/>
							   <span id="select_ad" class="yw-btn bg-blue mt12" style="cursor: pointer;" onclick="showdialog()">选择广告</span>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
	<div id="bbsWindow" class="easyui-window" title="关联社区" style="width:560px;height:480px;overflow:hidden;padding:10px;" iconCls="icon-info" closed="true" modal="true"   resizable="false" collapsible="false" minimizable="false" maximizable="false">
	   <table id="tab2" class="yw-cm-table font16">
	     <c:forEach var="item" items="${adList}"> 
	      <tr>
	        <td>${item.name }</td>
	      </tr>
	      </c:forEach>
	   </table>
	</div>
</body>
</html>