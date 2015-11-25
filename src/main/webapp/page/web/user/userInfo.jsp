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
	$(document).ready(function() {
		type = ${type};
		initOrg();
		/*编辑*/
		if(type==1){
			$("#orgtree1").combobox("setValue", '${userinfo.orgId3}');
			$("#orgtree2").combobox("setValue", '${userinfo.orgId2}');
			$("#orgtree3").combobox("setValue", '${userinfo.orgId}');
		}
		
	});   
	function setShowStates(){
		$("#userName").attr("readonly","readonly");
		$("#orgName").combobox("disable",true);
		$("#editBtn").show();
		$("#saveBtn").hide();
	};
	function editUser(){
		$("#userName").removeAttr("readonly");
		$("#orgName").combobox("enable",true);
		$("#editBtn").hide();
		$("#saveBtn").show();
	};
	function saveUser(obj) {
		if($("#orgtree3").combobox("getValue")==""){
			$.messager.alert("操作提示", "请选择组织机构！", "error");
			return;
		}
		if ($('#userForm').form('validate')) {
			$(obj).attr("onclick", "");
			$('#userForm').form(
					'submit',
					{
						success : function(data) {
							data = $.parseJSON(data);
							if (data.code == 1) {
								$.messager.alert('保存信息', data.message, 'info',
										function() {  
									     window.location.href='userList.do';
										});
							} else {
								$.messager.alert('错误信息', data.message, 'error');
							}
						}
					});
		}
	}; 
	function initOrg(){
		
	    $('#orgtree1').combobox( { 
          url : 'jsonLoadOrgList.do?pid=0', 
          valueField:'id',
          textField:'name',
          onChange:function(){
        	$('#orgtree2').combobox("setValue","");
        	$('#orgtree3').combobox("setValue","");
        	var pid = $('#orgtree1').combobox("getValue");
	    	initOrg2(pid);	
	      }
       });  
	}
	function initOrg2(pid){
	    $('#orgtree2').combobox( {  
          url : 'jsonLoadOrgList.do?pid='+pid,
        	  valueField:'id',
              textField:'name',
              onChange:function(){
            	$('#orgtree3').combobox("setValue","");
              	var pid = $('#orgtree2').combobox("getValue");
      	    	initOrg3(pid);	
      	      }
       });  
	}
	function initOrg3(pid){
	    $('#orgtree3').combobox( {  
          url : 'jsonLoadOrgList.do?pid='+pid,
        	  valueField:'id',
              textField:'name',
              onChange:function(){
            	    /*此pid为最后一级选择的id，将她放到
                	var pid = $('#orgtree3').combobox("getValue"); */
                	
        	      }
       });  
	}
</script>
</head>

<body>
	<div class="con-right" id="conRight">
		<div class="fl yw-lump">
			<div class="yw-lump-title">
				<span style="cursor: pointer;" onclick="window.location.href='userList.do'"><i class="yw-icon icon-back"></i>返回</span>
			</div>
		</div>

		<div class="fl yw-lump mt10">
			<div class="yw-bi-rows">
				<div class="yw-bi-tabs mt5" id="ywTabs">
					<span class="yw-bi-now">用户信息</span>
				</div>
				<div class="fr mt10">
					<!-- <span class="yw-btn bg-green mr26" id="editBtn" onclick="editUser();">编辑</span> --> 
					<span style="cursor: pointer;" class="yw-btn bg-red mr26" id="saveBtn" onclick="saveUser(this);">保存</span>
				</div>
			</div>
			<div id="tab1" class="yw-tab">
				<form id="userForm" name="userForm"
					action="saveOrupdateUser.do" method="post">
					<table class="yw-cm-table font16">
						<tr>
							<td width="10%" align="center">用户名称：</td>
							<td>
								<input id="userName" name="name" type="text" value="${userinfo.name}" class="easyui-validatebox" style="width:254px;height:30px;"  required/> 
								<input name="id" type="hidden" value="${userinfo.id}" /> 
						</tr>
						<tr>
							<td width="10%" align="center">所属机构：</td>
							<td>
							    <input id="orgtree1" class="easyui-combobox" data-options="editable:false" style="width:100px;height:30px;" required/>
							    <input id="orgtree2" class="easyui-combobox" data-options="editable:false" style="width:100px;height:30px;" required/>
							    <input id="orgtree3" name="orgId" class="easyui-combobox" data-options="editable:false" style="width:100px;height:30px;" required/>
							</td>
						</tr>
						<%-- <tr>
							<td align="center">角色：</td>
							<td>
								 <c:if test="${userinfo.roleKey == 1 }">
									<label><input id="roleKey1" type="radio" name="roleKey" checked="checked" value="1" onclick="$('#projectModel').attr('style','display:none');$('#systemModel').removeAttr('style');//$('#projectModel input[type=\'checkbox\']').attr('checked',false);" />服务台</label>
									<label><input id="roleKey2" type="radio" name="roleKey" value="2" onclick="//$('#projectModel input[type=\'checkbox\']').attr('checked',false);$('#systemModel input[type=\'checkbox\']').attr('checked',false);" />专家</label>
									<label><input id="roleKey3" type="radio" name="roleKey" value="3" onclick="$('#systemModel').attr('style','display:none');$('#projectModel').removeAttr('style');//$('#systemModel input[type=\'checkbox\']').attr('checked',false);" />维护人员</label>
								 </c:if>
								 <c:if test="${userinfo.roleKey == 2 }">
									<label><input id="roleKey1" type="radio" name="roleKey" value="1" onclick="$('#projectModel').attr('style','display:none');$('#systemModel').removeAttr('style');$('#projectModel input[type=\'checkbox\']').attr('checked',false);" />服务台</label>
									<label><input id="roleKey2" type="radio" name="roleKey" checked="checked" value="2" onclick="//$('#projectModel input[type=\'checkbox\']').attr('checked',false);$('#systemModel input[type=\'checkbox\']').attr('checked',false);" />专家</label>
									<label><input id="roleKey3" type="radio" name="roleKey" value="3" onclick="$('#systemModel').attr('style','display:none');$('#projectModel').removeAttr('style');$('#systemModel input[type=\'checkbox\']').attr('checked',false);" />维护人员</label>
								 </c:if>
								 <c:if test="${userinfo.roleKey == 3 }">
									<label><input id="roleKey1" type="radio" name="roleKey" value="1" onclick="$('#projectModel').attr('style','display:none');$('#systemModel').removeAttr('style');//$('#projectModel input[type=\'checkbox\']').attr('checked',false);" />服务台</label>
									<label><input id="roleKey2" type="radio" name="roleKey" value="2" onclick="//$('#projectModel input[type=\'checkbox\']').attr('checked',false);$('#systemModel input[type=\'checkbox\']').attr('checked',false);" />专家</label>
									<label><input id="roleKey3" type="radio" name="roleKey" checked="checked" value="3" onclick="$('#systemModel').attr('style','display:none');$('#projectModel').removeAttr('style');//$('#systemModel input[type=\'checkbox\']').attr('checked',false);" />维护人员</label>
								 </c:if>
							</td>
						</tr>  --%>
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