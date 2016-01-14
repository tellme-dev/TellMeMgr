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
<title>入住信息</title>
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
}
function search(){
	$("#pageNumber").val("1");
	pagesearch(); 
} 
function pagesearch(){
	checkForm.submit();
}
function gotoAdd(){
	window.location.href="checkinfo.do?id=0";
}
function checkout(id){
	var a= id;
	$.messager.confirm('提示信息',"确认退房？",function(r){
		if(r){
			$.ajax({
				url :  "jsonCheckout.do?id="+id,
				type : "POST",
				dataType : "json",
				async : false,
				success : function(req) {
					if (req.isSuccess) {
						window.location.href="checkList.do";
						search();
					} else {
						$.messager.alert('提示', req.msg, "warning");
					}
				}
			});
		}
    });
}
function showdialog(){
	var wz = getDialogPosition($('#checkInfoWindow').get(0),100);
	$('#checkInfoWindow').window({
		  	top: 100,
		    left: wz[1],
		    onBeforeClose: function () {
		    }
	});
	$('#checkInfoWindow').window('open');
}
function saveCheck(obj){
	if ($('#saveCheckForm').form('validate')) {
		$(obj).attr("onclick", ""); 
		 $('#saveCheckForm').form('submit',{
		  		success:function(data){
		  			data = $.parseJSON(data);
		  			if(data.code==0){
		  				$.messager.alert('保存信息',data.message,'info',function(){
		  					$('#checkInfoWindow').window('close');
		  					//search();
	        			});
		  			}else{
						$.messager.alert('错误信息',data.message,'error',function(){
							$(obj).attr("onclick", "saveCheck(this);"); 
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

		<div class="fl yw-lump mt10">
			<%-- <div class="yw-bi-rows">
				<div class="yw-bi-tabs mt5" id="ywTabs">
					<span onclick="window.location.href='companyInfo.do?companyId=\'${company.id}\''">基本信息</span> <span class="yw-bi-now" onclick="javascript:void(0);">员工</span>
				</div> 
			</div>  --%>
			<div id="tab2" class="yw-tab">
				<div class="fl yw-lump mt10">
					<form id="checkForm" name="checkForm"
						action="checkList.do" method="get">
						<div class="pd10-28">
							<div class="fl">
								<!-- <button class="yw-btn bg-blue cur">全部客户</button> -->
								<!-- <button class="yw-btn bg-gray ml20 cur">满意度调查</button> -->
							</div>
							<div class="fr">
								<input type="text" name="customerMobile" class="yw-input wid170"
									placeholder="电话" value="${roomCheck.customerMobile}" />
								<span class="yw-btn bg-orange ml30 cur" onclick="search();">开始查找</span>
								<span class="yw-btn bg-green ml20 cur" onclick="gotoAdd();">新增入住信息</span>
								<!-- <span class="yw-btn bg-blue ml20 cur" onclick="gotoEdit();">编辑用户</span>
								<span class="yw-btn bg-orange ml20 cur" onclick="deleteCheck()">删除用户</span> -->
							</div>
							<div class="cl"></div>
						</div>
			
						<input type="hidden" id="pageNumber" name="pageNo"
							value="${page.pageNo}" />
					</form>
				</div>
				<table class="yw-cm-table" id="checkinfoList">
					<tr>
						<th style="display:none">序号</th>
						<th width="5%"></th>
						<th>姓名</th> 
						<th>电话</th>
						<th>酒店</th>
						<th>房间</th>
						<th>入住时间</th>
						<th>退房时间</th>
						<th></th>
					</tr>
					<c:forEach var="item" items="${checklist}">
						<tr>
						    <td align="left" style="display:none">${item.id}</td>
							<td width="5%"><input name="checkbox" type="checkbox"/></td>
							<td>${item.customerName}</td> 
							<td>${item.customerMobile}</td> 
							<td>${item.hotelName}</td> 
							<td>${item.roomNumber}</td>
							<td>${item.checkInTime}</td> 
							<td>${item.checkOutTime}</td>
							<c:if test="${item.checkOutTime == null }">
							<td><span onclick="checkout(${item.id});" class="yw-btn bg-orange ml20 cur">退房</span></td>
							</c:if>
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
	
 	  <div id="checkInfoWindow" class="easyui-window" title="" style="width:560px;height:480px;overflow:hidden;padding:10px;" iconCls="icon-info" closed="true" modal="true"   resizable="false" collapsible="false" minimizable="false" maximizable="false">
		<form id="saveCheckForm" name ="saveCheckForm" action="saveOrupdateCheck.do"  method="post">
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
        	<span class="yw-window-btn bg-lightgray mt12"  onclick="$('#checkInfoWindow').window('close');">退出</span>
        	<span class="yw-window-btn bg-blue mt12" onclick="saveCheck(this);">保存</span>
        </div>
        </form>
	</div>
</body>
</html>
