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
<title>公司管理</title>
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
			    pagenumber:'${user.pageNo}',                         /* 表示初始页数 */
			    pagecount:'${user.pageCount}',                      /* 表示总页数 */
			    totalCount:'${user.totalCount}',
			    buttonClickCallback:PageClick                     /* 表示点击分页数按钮调用的方法 */                  
			});
			$("#userinfoList tr").each(function(i){
				if(i>0){
					$(this).bind("click",function(){
						var userId = $(this).find("td").first().text();
						 window.location.href="userinfo.do?userId="+userId;
					});
				}
			}); 
		}); 
		
PageClick = function(pageclickednumber) {
	$("#pager").pager({
	    pagenumber:pageclickednumber,                 /* 表示启示页 */
	    pagecount:'${user.pageCount}',                  /* 表示最大页数pagecount */
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
	userForm.submit();
}
function showdialog(){
	var wz = getDialogPosition($('#userInfoWindow').get(0),100);
	$('#userInfoWindow').window({
		  	top: 100,
		    left: wz[1],
		    onBeforeClose: function () {
		    }
	});
	$('#userInfoWindow').window('open');
}
function saveUser(obj){
	if ($('#saveUserForm').form('validate')) {
		$(obj).attr("onclick", ""); 
		 $('#saveUserForm').form('submit',{
		  		success:function(data){
		  			data = $.parseJSON(data);
		  			if(data.code==0){
		  				$.messager.alert('保存信息',data.message,'info',function(){
		  					$('#userInfoWindow').window('close');
		  					search();
	        			});
		  			}else{
						$.messager.alert('错误信息',data.message,'error',function(){
							$(obj).attr("onclick", "saveUser(this);"); 
	        			});
		  			}
		  		}
		  	 });  
	}
}  
</script>
</head>

<body>
	<div class="con-right" id="conRight">
		<div class="fl yw-lump">
			<div class="yw-lump-title">
				<i class="yw-icon icon-back" onclick="window.location.href='companyList.do'"></i><span>公司：${company.name}</span>
			</div>
		</div>

		<div class="fl yw-lump mt10">
			<div class="yw-bi-rows">
				<div class="yw-bi-tabs mt5" id="ywTabs">
					<span onclick="window.location.href='companyInfo.do?companyId=\'${company.id}\''">基本信息</span> <span class="yw-bi-now" onclick="javascript:void(0);">员工</span>
				</div> 
			</div> 
			<div id="tab2" class="yw-tab">
				<div class="fl yw-lump mt10">
					<form id="userForm" name="userForm"
						action="userList.do" method="get">
						<div class="pd10-28">
							<div class="fl">
								<!-- <button class="yw-btn bg-blue cur">全部客户</button> -->
								<!-- <button class="yw-btn bg-gray ml20 cur">满意度调查</button> -->
							</div>
							<div class="fr">
								<input type="hidden" name="companyId"  value="${company.id}" />
								<input type="text" name="searchName" class="yw-input wid170"
									placeholder="搜索" value="${user.searchName}" />
								<!-- <button class="yw-btn bg-orange ml30 cur" type="submit" onclick="search();">开始查找</button> -->
								<!-- <button class="yw-btn bg-green ml20 cur" onclick="showdialog();">新建公司</button> -->
								<span class="yw-btn bg-orange ml30 cur" onclick="search();">开始查找</span>
								<span class="yw-btn bg-green ml20 cur" onclick="showdialog();">新建员工</span>
							</div>
							<div class="cl"></div>
						</div>
			
						<input type="hidden" id="pageNumber" name="pageNo"
							value="${user.pageNo}" />
					</form>
				</div>
				<table class="yw-cm-table" id="userinfoList">
					<tr>
						<th>序号</th>
						<th>员工</th> 
						<th>公司名称</th>
						<th>岗位</th>
						<th>手机号码</th>
						<th>邮箱</th>
						<th>是否签到</th> 
					</tr>
					<c:forEach var="item" items="${userlist}">
						<tr>
							<td align="left">${item.id}</td>
							<td>${item.name}</td> 
							<td>${item.companyName}</td> 
							<td>${item.post}</td> 
							<td>${item.phone}</td> 
							<td>${item.email}</td> 
							<td>${item.signStatus}</td> 
						</tr>
					</c:forEach>
				</table>
				<div class="page" id="pager"></div>
			</div>
		</div>
		<div class="cl"></div>
	</div>
	<div class="cl"></div>

	</div>
	
 	  <div id="userInfoWindow" class="easyui-window" title="新添新员工" style="width:560px;height:480px;overflow:hidden;padding:10px;" iconCls="icon-info" closed="true" modal="true"   resizable="false" collapsible="false" minimizable="false" maximizable="false">
		<form id="saveUserForm" name ="saveUserForm" action="saveOrupdateUser.do"  method="post">
		<p style="display:none">
        	<span class="fl">id：</span><input name="id" type="text" value="0" class="easyui-validatebox"/>
        	<span class="fl">companyId：</span><input name="companyId" type="text" value="${company.id}" class="easyui-validatebox"/>
        </p>
		<p class="yw-window-p">
        	<span class="fl">员工姓名：</span><input name="name" type="text" value="" class="easyui-validatebox" required="true"  validType="Length[1,25]" style="width:254px;height:30px;"/>
        </p> 
		<p class="yw-window-p">
        	<span class="fl">性别：</span><select name="sex" class="easyui-combobox"><option value="-1">=请选择性别=</option><option value="0">男</option><option value="1">女</option></select>
        </p>
        <p class="yw-window-p">
        	<span class="fl">联系电话：</span><input name="phone" type="text" value="" class="easyui-validatebox" required="true"  validType="Length[1,25]" style="width:254px;height:30px;"/>
        </p>
        <p class="yw-window-p">
        	<span class="fl">邮箱：</span><input name="email" type="text" value="" class="easyui-validatebox" required="true"  validType="Length[1,25]" style="width:254px;height:30px;"/>
        </p>
        <p class="yw-window-p">
        	<span class="fl">所在岗位：</span><input name="post" type="text" value="" class="easyui-validatebox" required="true"  validType="Length[1,25]" style="width:254px;height:30px;"/>
        </p>
        <p class="yw-window-p">
        	<span class="fl">角色：</span>
			<label><input id="roleKey1" type="radio" name="roleKey" value="1" onclick="$('#projectModel').attr('style','display:none');$('#systemModel').removeAttr('style');$('#projectModel input[type=\'checkbox\']').attr('checked',false);" />服务台</label>
			<label><input id="roleKey2" type="radio" name="roleKey" value="2" onclick="$('#projectModel input[type=\'checkbox\']').attr('checked',false);$('#systemModel input[type=\'checkbox\']').attr('checked',false);" />专家</label>
			<label><input id="roleKey3" type="radio" name="roleKey" value="3" onclick="$('#systemModel').attr('style','display:none');$('#projectModel').removeAttr('style');$('#systemModel input[type=\'checkbox\']').attr('checked',false);" />维护人员</label>
        </p>
        <p id="projectModel" class="yw-window-p">
        	<span class="fl">工种类别：</span>
        	<input id="project1" type="checkbox" name="userTypeList" value="1">电源　　
			<input id="project2" type="checkbox" name="userTypeList"  value="2">平台　　
			<input id="project3" type="checkbox" name="userTypeList"  value="3">前端
        </p>
        <p id="systemModel" class="yw-window-p">
        	<span class="fl">功能模块：</span>
        	<input id="model1" type="checkbox" name="userFunList"  value="1">个人工作台　　
			<input id="model2" type="checkbox" name="userFunList"  value="2">客户管理
			<input id="model3" type="checkbox" name="userFunList"  value="3">员工管理
			<input id="model4" type="checkbox" name="userFunList"  value="4">项目管理
        </p>
        <div class="yw-window-footer txt-right">
        	<span class="yw-window-btn bg-lightgray mt12"  onclick="$('#userInfoWindow').window('close');">退出</span>
        	<span class="yw-window-btn bg-blue mt12" onclick="saveUser(this);">保存</span>
        </div>
        </form>
	</div>
</body>
</html>
