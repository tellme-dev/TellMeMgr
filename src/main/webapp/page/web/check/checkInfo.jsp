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
<title>员工信息</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1  ,maximum-scale=1, user-scalable=no" /> 
<script type="text/javascript">
    var type;//0为新增；1为编辑
    var customer = {};
	$(document).ready(function() {
		type = ${type};
		initHotel();
		/*编辑*/
		/* if(type==1){
			$("#orgtree1").combobox("setValue", '${userinfo.orgId3}');
			$("#orgtree2").combobox("setValue", '${userinfo.orgId2}');
			$("#orgtree3").combobox("setValue", '${userinfo.orgId}');
		} */
		
	});
    function selectCustomer(){
    	var mobile = $("#customerMobile").val();
    	if(mobile == ""){
    		$.messager.alert("操作提示", "请输入电话，匹配用户！", "error");
			return;
    	}
    	$.ajax({
    		url :  "jsonSelectCustomer.do?mobile="+mobile,
    		type : "POST",
    		dataType : "json",
    		async : false,
    		success : function(req) {
    			if (req.isSuccess) {
    				customer = req.data;
    				/*将匹配到的用户信息放到对应的位置*/
    				$("#customerName").val(customer.name);
    				$("#customerId").val(customer.id);
    			} else {
    				$.messager.alert('匹配失败', req.msg, "warning");
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
            	var hotelId = $('#hotelSelect').combobox("getValue");
            	initRoom(hotelId);
      	      }
       });  
	}
    function initRoom(hotelId){
	    $('#roomSelect').combobox( {  
          url : 'jsonLoadRoomComboList.do?hotelId='+hotelId,
          valueField:'id',
          textField:'name'
       });  
	}
	function saveCheckInfo(obj) {
		if($("#customerId").val()==""){
			$.messager.alert("操作提示", "请先匹配用户！", "error");
			return;
		}
		if($("#hotelSelect").combobox("getValue")==""){
			$.messager.alert("操作提示", "请选择酒店！", "error");
			return;
		}
		if($("#roomSelect").combobox("getValue")==""){
			$.messager.alert("操作提示", "请选择房间！", "error");
			return;
		}
		if ($('#checkForm').form('validate')) {
			$(obj).attr("onclick", "");
			$('#checkForm').form(
					'submit',
					{
						success : function(data) {
							data = $.parseJSON(data);
							if (data.code == 1) {
								$.messager.alert('保存信息', data.message, 'info',
										function() {  
									     window.location.href='checkList.do';
										});
							} else {
								$.messager.alert('错误信息', data.message, 'error');
							}
						}
					});
		}
	};
</script>
</head>

<body>
	<div class="con-right" id="conRight">
		<div class="fl yw-lump">
			<div class="yw-lump-title">
				<span style="cursor: pointer;" onclick="window.location.href='checkList.do'"><i class="yw-icon icon-back"></i>返回</span>
			</div>
		</div>

		<div class="fl yw-lump mt10">
			<div class="yw-bi-rows">
				<div class="yw-bi-tabs mt5" id="ywTabs">
					<span class="yw-bi-now">用户信息</span>
				</div>
				<div class="fr mt10">
					<!-- <span class="yw-btn bg-green mr26" id="editBtn" onclick="editCheck();">编辑</span> --> 
					<span style="cursor: pointer;" class="yw-btn bg-red mr26" id="saveBtn" onclick="saveCheckInfo(this);">保存</span>
				</div>
			</div>
			<div id="tab1" class="yw-tab">
				<form id="checkForm" name="checkForm"
					action="saveOrupdateCheck.do" method="post">
					<table class="yw-cm-table font16">
						<tr>
							<td width="10%" align="center">电话：</td>
							<td>
								<input id="customerMobile" type="text" class="easyui-validatebox" style="width:254px;height:30px;"/> 
								<input name="id" type="hidden" value="${checkinfo.id}" /> 
								<span class="yw-btn bg-green ml20 cur" onclick="selectCustomer()">匹配</span>
							</td>
						</tr>
						<tr>
							<td width="10%" align="center">姓名：</td>
							<td>
							    <input id="customerName" type="text" value="${checkinfo.name}" placeholder="输入电话匹配姓名" readonly="readonly" class="easyui-validatebox" style="width:254px;height:30px;"/>
							    <input id="customerId" name="customerId" type="hidden" />
							</td>
						</tr>
						<tr>
							<td width="10%" align="center">酒店：</td>
							<td>
							    <input id="hotelSelect" name="hotelId" class="easyui-combobox" data-options="editable:false" style="width:254px;height:30px;" required/>
							</td>
						</tr>
						<tr>
							<td width="10%" align="center">房间：</td>
							<td>
							    <input id="roomSelect" name="roomId" class="easyui-combobox" data-options="editable:false" style="width:254px;height:30px;" required/>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<div class="cl"></div>
	</div>
	<div class="cl"></div>

	</div>

</body>
</html>