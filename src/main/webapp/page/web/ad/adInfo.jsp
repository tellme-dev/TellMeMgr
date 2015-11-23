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
    	
    	/******弹出窗*******/
    	var i = 1;
    	$("#btn_add").click(function(){  
            document.getElementById("div_1").innerHTML+='<div id="div_'+i+'"><input id="file_'+i+'" type="file" style="width:200px;height:30px"/><input id="imageText" value="" placeholder="图片描述" style="width:200px;height:30px"/><span class="yw-btn bg-blue mr26" style="cursor: pointer;" onclick="del_f('+i+')">删除</span></div>'; 
            document.getElementById("img_1").innerHTML+='<div id="img_'+i+'" style="display:none"><input name="file_'+i+'" type="file" style="width:200px;height:30px"/></div>'; 
              i++;  
        });   
    });
    
    function del_f(i){  
         document.getElementById("newUpload").removeChild(document.getElementById("file_"+i));  
         document.getElementById("div_image").removeChild(document.getElementById("img_"+i));
    } 
    function fileChange (i){
         //将弹窗中的file value赋值到隐藏标签中
         document.getElementByName("file_"+i).value = $("#file_"+i).val();
         var a = document.getElementByName("file_"+i).value;
	}
	
	
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
		//$("#txtname").attr("readonly","readonly");
		//$("#txtkey").attr("readonly","readonly");
		//$("#imageTextShow").show();
		//$("#imageTextEdit").hide();
		//$("#imageUrlAdd").hide();
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
		/* var data = "";
		var imageText = document.getElementsByName("imagetext");
		for(var i=0;i<imageText.length;i++){
			data += imageText[i].value+",";
		}
		var imageTexts = document.getElementById("imageText");
		imageTexts.value = data; */
		var doc=$(":radio:checked");
    	if(doc.length == 0){
    		$.messager.alert('提示信息', "请选择类型！", "warning");
    		return;
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
	function savePhoto(obj){
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
          textField:'name'
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
			<div id="tab1" class="yw-tab">
				<form id="adForm" name="adForm"
					action="saveOrupdateAd.do" method="post">
					<table class="yw-cm-table font16">
					    <%-- <tr>
					        <td width="10%" align="center">描述：</td>
							<td id="imageTextEdit">
							    <c:forEach var="item" items="${adinfo.adDetailList}">
							    <input id="imagetext" name="imagetext" value="${item.text}" style="width:100px;height:30px;"/> 
							    </c:forEach> 
							 <td>
							    <input id="imageText" name="imageText" type="hidden" style="width:100px;height:30px;"/>
							</td>
						</tr> --%>
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
							   <input id="hotelSelect" name="targetId" class="easyui-combobox" data-options="editable:false" style="width:254px;height:30px;"/>
							</td>
						</tr>
						<tr id="itemTag">
							<td width="10%" align="center">标签：</td>
							<td>
							   <input id="itemTagSelect" name="targetId" class="easyui-combotree" data-options="editable:false" style="width:254px;height:30px;"/>
							</td>
						</tr>
						<tr id="tagContent">
							<td width="10%" align="center">其他：</td>
							<td>
							    <input id="txtTagContent" name="targetContent" type="text" value="${adinfo.targetContent}" class="easyui-validatebox"  validType="Length[1,25]" style="width:254px;height:30px;"/>
							</td>
						</tr>
						<tr>
						    <td>
						    <div id="img_0" style="display:none"><input name="file0" type="file"/></div>
						    </td>
						</tr>
					</table>
				</form>
			</div>
			<div class="cl">
		       <span class="txt ts15 ml20">图片</span><span class="yw-window-btn bg-blue ml20" onclick="showdialog();">[添加]</span><span class="yw-window-btn bg-blue ml20">[修改]</span><span class="yw-window-btn bg-blue ml20">[删除]</span>
		       <table class="yw-cm-table" id="photoList">
		          <tr class="ts15">
				      <td><img id="imageUrl" name="imageUrlList" src="<%=basePath%>source/images/userhaed1.png" style="width:100px;height:50px;" /></td>
		          </tr>
		          <tr class="ts15">
				      <td><img id="imageUrl" name="imageUrlList" src="<%=basePath%>source/images/userhaed1.png" style="width:100px;height:50px;" /></td>
		          </tr>
		       </table>
		   </div>
		</div>
		<div class="cl">
		     <span></span>
		</div>
	</div>
    <div id="photoWindow" class="easyui-window" title="添加图片" style="width:560px;height:480px;overflow:hidden;padding:10px;" iconCls="icon-info" closed="true" modal="true"   resizable="false" collapsible="false" minimizable="false" maximizable="false">
        <div id="div_0">  
            <input type="file" id="file_0" onchange="fileChange(0)" style="width:200px;height:30px"><input id="imageText" value="" placeholder="图片描述" style="width:200px;height:30px"/> 
        </div>  
        <input type="button" id="btn_add" value="增加一行" class="yw-btn bg-blue mt12">  
        <div class="yw-window-footer txt-right">
        	<span class="yw-window-btn bg-lightgray mt12" style="cursor: pointer;" onclick="$('#photoWindow').window('close');">退出</span>
        	<span class="yw-window-btn bg-blue mt12" onclick="savePhoto(this);" style="cursor: pointer;">保存</span>
        </div>
	 </div>
</body>
</html>
