<<<<<<< HEAD
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
    var j = 1;
    var delAdDetailIds = "";//存放删除的图片id
    $(document).ready(function(){
    	type = ${type};
    	initHotel();
    	initItemTagTree();
    	setShowStates();
    	if(type==1){//编辑
    		var targetType = '${adinfo.targetType}';
    		var doc = document.getElementsByName("targetType");
    	    switch(targetType){
    	    case '1':
    	    	doc[0].checked = true; 
    	    	$("#hotelSelect").combobox("setValue",'${adinfo.targetId}');
    	    	$("#hotelSelect").combobox("enable",true); 
    	    	break;
    	    case '2':
    	    	doc[1].checked = true;
    	    	$("#itemTagSelect").combotree("setValue",'${adinfo.targetId}');
    	    	$("#itemTagSelect").combotree("enable",true);
    	    	break;
    	    case '3':
    	    	doc[2].checked = true;
    	    	$("#txtTagContent").removeAttr("readonly");break;
    	    case '4':
    	    	doc[3].checked = true;
    	    	$("#txtTagContent").removeAttr("readonly");break;
    	    case '5':
    	    	doc[4].checked = true;
    	    	$("#txtTagContent").removeAttr("readonly");break;
    	    };
    	    
    	}
    	
    });
    
    function tagChange(type){
    	if(type == 1){//#ffa8a8
    		//选择“酒店” 操作 hotelSelect
    		/*改变颜色*/
    		var doc = document.getElementById("hotel");
    	    doc.style.backgroundColor="#ffa8a8";
    	    
    	    var doc = document.getElementById("itemTag");
    	    doc.style.backgroundColor="white";
    	    
    	    var doc = document.getElementById("tagContent");
    	    doc.style.backgroundColor="white";
    	    
    	    /*内容清空*/
    	    $("#itemTagSelect").combotree("setValue","");
    	    $("#txtTagContent").val("");
    	    
    	    /*只读、可编辑转换*/
    	    $("#hotelSelect").combobox("enable",true);
    	    
    	    $("#txtTagContent").attr("readonly","readonly");
    	    $("#itemTagSelect").combotree("disable",true);
    	}
    	else if(type == 2){
    		//选择“项目” 操作 itemTag
    		/*改变颜色*/
    		var doc = document.getElementById("itemTag");
    	    doc.style.backgroundColor="#ffa8a8";
    	    
    	    var doc = document.getElementById("hotel");
    	    doc.style.backgroundColor="white";
    	    
    	    var doc = document.getElementById("tagContent");
    	    doc.style.backgroundColor="white";
    	    
    	    /*内容清空*/
    	    $("#hotelSelect").combobox("setValue","");
    	    $("#txtTagContent").val("");
    	    
    	    /*只读、可编辑转换*/
    	    $("#itemTagSelect").combotree("enable",true);
    	    
    		$("#txtTagContent").attr("readonly","readonly");
    		$("#hotelSelect").combobox("disable",true);
    	}
    	else{
    		//选择“html”、“url” 操作 tagContent文本框
    		/*改变颜色*/
    		var doc = document.getElementById("tagContent");
    	    doc.style.backgroundColor="#ffa8a8";
    	    
    	    var doc = document.getElementById("hotel");
    	    doc.style.backgroundColor="white";
    	    
    	    var doc = document.getElementById("itemTag");
    	    doc.style.backgroundColor="white";
    	    
    	    /*内容清空*/
    	    $("#hotelSelect").combobox("setValue","");
    	    $("#itemTagSelect").combotree("setValue","");
    	    
    	    /*只读、可编辑转换*/
    		$("#txtTagContent").removeAttr("readonly");
    		
    		$("#itemTagSelect").combotree("disable",true);
    		$("#hotelSelect").combobox("disable",true);
    	}
    }
	function setShowStates(){
		$("#txtTagContent").attr("readonly","readonly");
		$("#itemTagSelect").combotree("disable",true);
		$("#hotelSelect").combobox("disable",true);
	};
	function edit(){
		$("#hotelSelect").combobox("enable",true);
		$("#txtname").removeAttr("readonly");
		$("#txtkey").removeAttr("readonly");
		$("#imageTextShow").hide();
		$("#imageTextEdit").show();
		$("#imageUrlAdd").show();
		$("#txtTagContent").removeAttr("readonly");
		$("#tab1 input[type='radio']").removeAttr("disabled");
		$("#tab1 input[type='checkbox']").removeAttr("disabled");
		$("#createtime").hide();
		$("#editBtn").hide();
		$("#saveBtn").show();
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
    		$.messager.alert('提示信息', "请选择标签！", "warning");
    		return;
    	}
    	if(targetType != 1&&targetType != 2&&$("#txtTagContent").val() == ""){
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
	function add_img(){
	    var html = "";
	    i = j;
		html +='<tr id="tr_'+i+'"><td width="15%"><span class="yw-window-btn bg-blue mr26" style="cursor: pointer;" onclick="del_f('+i+')">删除</span></td><td><img id="image_'+i+'" src=""/></td><td><input name="imagetext" placeholder="图片描述" style="width:200px;height:30px" required/></td><td><input name="file'+i+'" id="file_'+i+'" type="file" onchange="fileChange('+i+')" style="width:200px;height:30px"/></td></tr>';
    	//document.getElementById("upload1").innerHTML+='<div id="div_'+i+'"><input id="file_'+i+'" type="file" style="width:200px;height:30px"/><input name="imagetext" value="" placeholder="图片描述" style="width:200px;height:30px"/><span class="yw-btn bg-blue mr26" style="cursor: pointer;" onclick="del_f('+i+')">删除</span></div>'; 
        //document.getElementById("upload2").innerHTML+='<div id="img_'+i+'" style="display:none"><input id="image_'+i+'" name="file_'+i+'" type="file" class="easyui-validatebox" style="width:200px;height:30px"/></div>'; 
          j++;
        $("#tab1").append(html);
    }
    function del_f(i){
         var a = document.getElementById("tr_"+i);
         a.remove();
         //document.getElementById("tab1").removeChild(document.getElementById("tr_"+i));  
         //document.getElementById("upload2").removeChild(document.getElementById("img_"+i));
    } 
    function del_p(obj){
         var id = $(obj).parents("tr").find("td").first()[0].childNodes[0].value;
         delAdDetailIds += id+",";
         $(obj).parents("tr").remove();
    }
    function fileChange (i){
         //将file中选择的图片赋值到img中
         var url = $("#file"+i).val();
         //$("#image_"+i).attr("src",url);
	}
	
	
	function saveImageText(obj){
	if ($('#uploadPhotoForm').form('validate')) {
		$(obj).attr("onclick", ""); 
		 $('#uploadPhotoForm').form('submit',{
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
	function showdialog(){
		var wz = getDialogPosition($('#photoWindow').get(0),100);
		$('#photoWindow').window({
			  	top: 100,
			    left: wz[1],
			    onBeforeClose: function () {
			    }
		});
		$('#photoWindow').window('open');
	}
	function initItemTagTree(){
	    $('#itemTagSelect').combotree( {  
          url : 'jsonLoadItemTagTree.do?pid=0',
          onSelect : function(node) {  
            var tree = $(this).tree;  
            //选中的节点是否为叶子节点,如果不是叶子节点,清除选中  
            var isLeaf = tree('isLeaf', node.target);  
            if (!isLeaf) {  
                //清除选中  
                $('#itemTagSelect').combotree('clear');  
            }  
         }  
       });  
	}
	function initHotel(){
	    $('#hotelSelect').combobox( {  
          url : 'jsonLoadHotelComboList.do',
          valueField:'id',
          textField:'name',
          onChange:function(){
            	var a = $('#hotelSelect').combobox("getValue");
      	      }
       });  
	}
	
</script>
</head>

<body>
	<div class="con-right" id="conRight">
		<div class="fl yw-lump">
			<div class="yw-lump-title">
				<span style="cursor: pointer;" onclick="window.location.href='adList.do'"><i class="yw-icon icon-back"></i>返回</span>
			</div>
		</div>

		<div class="fl yw-lump mt10">
			<div class="yw-bi-rows">
				<div class="yw-bi-tabs mt5" id="ywTabs">
					<span class="yw-bi-now">广告详情</span>
				</div>
				<div class="fr mt10">
					<!-- <span class="yw-btn bg-green mr26" id="editBtn" onclick="edit();">编辑</span>  -->
					<span class="yw-btn bg-red mr26" id="saveBtn" onclick="save(this);" style="cursor: pointer;">保存</span>
				</div>
			</div>
			<div class="yw-tab">
				<form id="adForm" name="adForm" enctype="multipart/form-data"
					action="saveOrupdateAd.do" method="post">
					<table id="tab1" class="yw-cm-table font16">
						<tr>
							<td width="10%" align="center">名称：</td>
							<td>
								<input id="txtname" name="name" type="text" value="${adinfo.name}" class="easyui-validatebox" required="true" validType="Length[1,25]" style="width:254px;height:30px;" /> 
								<input name="id" type="hidden" value="${adinfo.id}" /> 
							</td>
						</tr>
						<tr>
							<td width="10%" align="center">关键字：</td>
							<td>
							    <input id="txtkey" name="keyWord" type="text" value="${adinfo.keyWord}" placeholder="关键字之间用逗号隔开" class="easyui-validatebox" validType="Length[1,25]" style="width:254px;height:30px;" />
							</td>
						</tr>
						<tr>
							<td align="center">类型：</td>
							<td>
							        <input type="radio" name="targetType" onclick="tagChange(1)" value="1">酒店　　
							        <input type="radio" name="targetType" onclick="tagChange(2)" value="2">标签　　
        	                        <input type="radio" name="targetType" onclick="tagChange(3)" value="3">社区　　
        	                        <input type="radio" name="targetType" onclick="tagChange(4)" value="4">URL地址　　
        	                        <input type="radio" name="targetType" onclick="tagChange(5)" value="5">HTML页面
							</td>
						</tr> 
						<tr id="hotel">
							<td width="10%" align="center">酒店：</td>
							<td>
							   <input id="hotelSelect" name="hotelId" class="easyui-combobox" data-options="editable:false" style="width:254px;height:30px;"/>
							</td>
						</tr>
						<tr id="itemTag">
							<td width="10%" align="center">标签：</td>
							<td>
							   <input id="itemTagSelect" name="targetId" class="easyui-combotree" data-options="editable:false" style="width:254px;height:30px;"/>
							</td>
						</tr>
						<tr id="tagContent">
							<td width="10%" align="center">内容：</td>
							<td>
							    <input id="txtTagContent" name="targetContent" type="text" value="${adinfo.targetContent}" class="easyui-validatebox"  validType="Length[1,25]" style="width:254px;height:30px;"/>
							</td>
						</tr>
						<tr>
						    <td width="10%" align="center">图片：</td>
					        <td><span class="yw-btn bg-blue mt28" onclick="add_img();" style="cursor: pointer;">添加一行</span>
					        </td>
					        <td><input id="imageText" name="imageText" type="hidden" style="width:100px;height:30px;"/>
						    </td>
						    <td><input id="adDetailIds" name="adDetailIds" type="hidden" style="width:100px;height:30px;"/>
						    </td>
						    <td><input id="delAdDetailIds" name="delAdDetailIds" type="hidden" style="width:100px;height:30px;"/>
						    </td>
					    </tr>
					    <tr id="imageGrid" class="ts15">
				           <td width="15%"></td>
				           <td>图片</td>
				           <td>描述</td>
				           <td>选择</td>
		               </tr>
		               <c:if test="${type == 1}">
		                 <c:forEach var="item" items="${adinfo.adDetailList}">
		                   <tr>
		                   <td style="display:none"><input name="adDetailId" value="${item.id}" /></td>
		                   <td width="15%"><span class="yw-window-btn bg-blue mr26" style="cursor: pointer;" onclick="del_p(this)">删除</span></td>
		                   <td><img id="image" src="<%=basePath%>${item.imageUrl}" style="width:50px;height:50px"/></td>
		                   <td><input name="imagetext" value="${item.text}" style="width:200px;height:30px" required/></td>
		                   <td>修改图片请先删除后重新添加</td>
		                   </tr>
		                 </c:forEach>
		               </c:if>
					</table>
				</form>
			</div>
		</div>
	</div>
    <div id="photoWindow" class="easyui-window" title="添加图片" style="width:560px;height:480px;overflow:hidden;padding:10px;" iconCls="icon-info" closed="true" modal="true"   resizable="false" collapsible="false" minimizable="false" maximizable="false">
        <form id="uploadPhotoForm" action="saveOrupdatePhoto.do" enctype="multipart/form-data" method="post">
        <div id="upload2">  
            <input type="file" id="file" name="file1" onchange="" style="width:200px;height:30px"><input id="imageText" value="" placeholder="图片描述" style="width:200px;height:30px"/> 
            <input type="file" id="file" name="file2" onchange="" style="width:200px;height:30px"><input id="imageText" value="" placeholder="图片描述" style="width:200px;height:30px"/> 
        </div>  
        <input type="button" id="btn_add" value="增加一行" class="yw-btn bg-blue mt12">  
        <div class="yw-window-footer txt-right">
        	<span class="yw-window-btn bg-lightgray mt12" style="cursor: pointer;" onclick="$('#photoWindow').window('close');">退出</span>
        	<span class="yw-window-btn bg-blue mt12" onclick="saveImageText(this);" style="cursor: pointer;">保存</span>
        </div>
        </form>
	</div>
</body>
</html>
