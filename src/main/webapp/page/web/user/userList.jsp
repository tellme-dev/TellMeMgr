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
			    pagenumber:'${page.pageNo}',                         /* 表示初始页数 */
			    pagecount:'${page.pageCount}',                      /* 表示总页数 */
			    totalCount:'${page.totalCount}',
			    buttonClickCallback:PageClick                     /* 表示点击分页数按钮调用的方法 */                  
			});
			
			$("#userinfoList tr").each(function(i){
				if(i>0){
					$(this).bind("click",function(){
						var userId = $(this).find("td").first().text();
						 //window.location.href="userinfo.do?userId="+userId;
					});
				}
			}); 
		}); 
		
PageClick = function(pageclickednumber) {
	$("#pager").pager({
	    pagenumber:pageclickednumber,                 /* 表示启示页 */
	    pagecount:'${page.pageCount}',                  /* 表示最大页数pagecount */
	    buttonClickCallback:PageClick                 /* 表示点击页数时的调用的方法就可实现javascript分页功能 */            
	});
	
	$("#pageNumber").val(pageclickednumber);          /* 给pageNumber从新赋值 */
	/* 执行Action */
	pagesearch();
};
function search(){
	$("#pageNumber").val("1");
	pagesearch(); 
} 
function checkAll(){
	if($("#checkAll").prop("checked")){
		$("#userinfoList input[name='checkbox']").attr("checked",true);
	}else{
		$("#userinfoList input[name='checkbox']").attr("checked",false);
	}
}
function pagesearch(){
	userForm.submit();
}
function gotoAdd(){
	window.location.href="userinfo.do?userId=0";
}
function gotoEdit(){
	var doc=$(":checkbox:checked");
	if(doc.length == 0){
		$.messager.alert('提示信息', "请选择操作项！", "warning");
		return;
	}
	if(doc.length > 1){
		$.messager.alert('提示信息', "只能编辑一项！", "warning");
		return;
	}
	$(":checkbox:checked").each(function(i){
		var userId = $(this).parents("tr").find("td").first().text();
		window.location.href="userinfo.do?userId="+userId;
	}); 
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
function deleteUser(){
	var doc=$(":checkbox:checked");
	if(doc.length == 0){
		$.messager.alert('提示信息', "请选择操作项！", "warning");
		return;
	}
	/*将选中的项的id放入userIds中*/
	var userIds="";
	var userNames="";
	$(":checkbox:checked").each(function(i){
		var userId = $(this).parents("tr").find("td").first().text();
		var userName = $(this).parents("tr").find("td")[2].innerText;
		if(i==0){
			userIds += userId;
			userNames += userName;
		}else{
			userIds += ","+userId; 
			userNames += "，"+userName;
		}
	}); 
	$.messager.confirm('提示信息',"确认删除用户["+userNames+"]？",function(r){
			if(r){
				deleteAction(userIds);
			}
	});
}
function deleteAction(userIds){
	$.ajax({
		url :  "jsonDeleteUser.do?userIds="+userIds,
		type : "POST",
		dataType : "json",
		async : false,
		success : function(req) {
			if (req.isSuccess) {
				window.location.href="userList.do";
			} else {
				$.messager.alert('删除失败ʾ', req.msg, "warning");
			}
		}
	});
}
function initOrg(){
	    $('#orgtree').combobox( {  
          url : 'jsonLoadOrgList.do',
          valueField:'id',
          textField:'name'
          /* onSelect : function(node) {  
            var tree = $(this).tree;  
            //选中的节点是否为叶子节点,如果不是叶子节点,清除选中  
            var isLeaf = tree('isLeaf', node.target);  
            if (!isLeaf) {  
                //清除选中  
                $('#orgtree').combotree('clear');  
            }  
         }   */
       });  
	}  
</script>
</head>

<body>
	<div class="con-right" id="conRight">
		<%-- <div class="fl yw-lump">
			<div class="yw-lump-title">
				<i class="yw-icon icon-back" onclick="window.location.href='companyList.do'"></i><span>公司：${company.name}</span>
			</div>
		</div> --%>

		<div class="fl yw-lump mt10">
			<%-- <div class="yw-bi-rows">
				<div class="yw-bi-tabs mt5" id="ywTabs">
					<span onclick="window.location.href='companyInfo.do?companyId=\'${company.id}\''">基本信息</span> <span class="yw-bi-now" onclick="javascript:void(0);">员工</span>
				</div> 
			</div>  --%>
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
								<%-- <input type="hidden" name="companyId"  value="${company.id}" />
								<input type="text" name="searchName" class="yw-input wid170"
									placeholder="搜索" value="${user.searchName}" />
								<span class="yw-btn bg-orange ml30 cur" onclick="search();">开始查找</span> --%>
								<span class="yw-btn bg-green ml20 cur" onclick="gotoAdd();">新建用户</span>
								<span class="yw-btn bg-blue ml20 cur" onclick="gotoEdit();">编辑用户</span>
								<span class="yw-btn bg-orange ml20 cur" onclick="deleteUser()">删除用户</span>
							</div>
							<div class="cl"></div>
						</div>
			
						<input type="hidden" id="pageNumber" name="pageNo"
							value="${page.pageNo}" />
					</form>
				</div>
				<table class="yw-cm-table" id="userinfoList">
					<tr>
						<th style="display:none">序号</th>
						<th width="5%"></th>
						<th>用户名</th> 
						<th>所属机构</th>
						<th>创建时间</th>
					</tr>
					<c:forEach var="item" items="${userlist}">
						<tr>
						    <td align="left" style="display:none">${item.id}</td>
							<td width="5%"><input name="checkbox" type="checkbox"/></td>
							<td name="userName">${item.name}</td> 
							<td>${item.orgName}</td> 
							<td>${item.createtime}</td> 
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
	
 	  <div id="userInfoWindow" class="easyui-window" title="新添用户" style="width:560px;height:480px;overflow:hidden;padding:10px;" iconCls="icon-info" closed="true" modal="true"   resizable="false" collapsible="false" minimizable="false" maximizable="false">
		<form id="saveUserForm" name ="saveUserForm" action="saveOrupdateUser.do"  method="post">
		<p style="display:none">
        	<span class="fl">id：</span><input name="id" type="text" value="0" class="easyui-validatebox"/>
        </p>
		<p class="yw-window-p">
        	<span class="fl">姓名：</span><input name="name" type="text" value="" class="easyui-validatebox" required="true"  validType="Length[1,25]" style="width:254px;height:30px;"/>
        </p> 
		<!-- <p class="yw-window-p">
        	<span class="fl">性别：</span><select name="sex" class="easyui-combobox"><option value="-1">=请选择性别=</option><option value="0">男</option><option value="1">女</option></select>
        </p> -->
        <p class="yw-window-p">
        	<span class="fl">所属机构：</span><input id="orgtree" name="org" type="text" value="" class="easyui-combotree" required="true"  validType="Length[1,25]" style="width:254px;height:30px;"/>
        </p>
        <div class="yw-window-footer txt-right">
        	<span class="yw-window-btn bg-lightgray mt12"  onclick="$('#userInfoWindow').window('close');">退出</span>
        	<span class="yw-window-btn bg-blue mt12" onclick="saveUser(this);">保存</span>
        </div>
        </form>
	</div>
</body>
</html>