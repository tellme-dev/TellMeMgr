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
			/* $("#rcuinfoList tr").each(function(i){
				if(i>0){
					$(this).bind("click",function(){
						var rcuId = $(this).find("td").first().text();
						 window.location.href="rcuinfo.do?rcuId="+rcuId;
					});
				}
			});  */
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
function selectModel(index){
    switch(index){
    case 1:
        var doc = document.getElementById("model_1");
    	doc.style.backgroundColor="#ffa8a8";
    	
    	var doc = document.getElementById("model_2");
    	doc.style.backgroundColor="#7A818B";
    	var doc = document.getElementById("model_3");
    	doc.style.backgroundColor="#7A818B";
        break;
    case 2:
        var doc = document.getElementById("model_2");
    	doc.style.backgroundColor="#ffa8a8";
    	
    	var doc = document.getElementById("model_1");
    	doc.style.backgroundColor="#7A818B";
    	var doc = document.getElementById("model_3");
    	doc.style.backgroundColor="#7A818B";
        break;
    case 3:
        var doc = document.getElementById("model_3");
    	doc.style.backgroundColor="#ffa8a8";
    	
    	var doc = document.getElementById("model_1");
    	doc.style.backgroundColor="#7A818B";
    	var doc = document.getElementById("model_2");
    	doc.style.backgroundColor="#7A818B";
        break;    
    }
}
function search(){
	$("#pageNumber").val("1");
	pagesearch(); 
} 
function pagesearch(){
	rcuForm.submit();
}
function showdialog(){
	var wz = getDialogPosition($('#rcuInfoWindow').get(0),100);
	$('#rcuInfoWindow').window({
		  	top: 100,
		    left: wz[1],
		    onBeforeClose: function () {
		    }
	});
	$('#rcuInfoWindow').window('open');
}
function saveRcu(obj){
	if ($('#saveRcuForm').form('validate')) {
		$(obj).attr("onclick", ""); 
		 $('#saveRcuForm').form('submit',{
		  		success:function(data){
		  			data = $.parseJSON(data);
		  			if(data.code==0){
		  				$.messager.alert('保存信息',data.message,'info',function(){
		  					$('#rcuInfoWindow').window('close');
		  					search();
	        			});
		  			}else{
						$.messager.alert('错误信息',data.message,'error',function(){
							$(obj).attr("onclick", "saveRcu(this);"); 
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
			<div class="yw-bi-rows">
				<div class="yw-bi-tabs mt5" id="ywTabs">
					<span class="yw-bi-now">控制台</span>
				</div>
			</div>
			<div class="yw-tab">
				<form id="rcuForm" name="rcuForm" action="saveOrupdateRcu.do" method="post">
					<table id="tab1" class="yw-cm-table font16">
						<tr>
							<td width="10%" align="center">sid：</td>
							<td colspan="5">
								<input id="sid" name="id" type="text" onkeyup='this.value=this.value.replace(/\D/gi,"")' class="easyui-validatebox" style="width:100px;height:30px;" /> 
							</td>
							<td>
						       <span class="yw-btn bg-green ml20 cur" onclick="send();">发送</span>
						    </td>
						</tr>
						<tr>
							<td width="10%" align="center">灯光：</td>
							<td colspan="5">
							     <span id="model_1" class="yw-btn bg-gray ml20 cur ts15" onclick="selectModel(1)">模式一</span>
							     <span id="model_2" class="yw-btn bg-gray ml20 cur ts15" onclick="selectModel(2)">模式二</span>
							     <span id="model_3" class="yw-btn bg-gray ml20 cur ts15" onclick="selectModel(3)">模式三</span>
							</td>
							<td>
						       <span class="yw-btn bg-green ml20 cur" onclick="send();">发送</span>
						    </td>
						</tr>
						<tr>
							<td width="10%" align="center">空调：</td>
							<td colspan="5">
							        <span style="margin-left:50px">风：</span><input type="text" name="wind" onkeyup='this.value=this.value.replace(/\D/gi,"")' style="width:100px;height:30px;"/>
							        <span style="margin-left:50px">温度：</span><input type="text" name="temperature" onkeyup='this.value=this.value.replace(/\D/gi,"")' style="width:100px;height:30px;"/>
                                    <div class="switch demo3">
					                      <input type="checkbox" checked>
					                      <label><i></i></label>
				                    </div>
							</td>
							<td>
						       <span class="yw-btn bg-green ml20 cur" onclick="send();">发送</span>
						    </td>
						</tr>
						<tr>
							<td width="10%" align="center">窗帘：</td>
							<td colspan="5">
							        <div class="switch demo3">
					                      <input type="checkbox" checked>
					                      <label><i></i></label>
				                    </div>
							</td>
							<td>
						       <span class="yw-btn bg-green ml20 cur" onclick="send();">发送</span>
						    </td>
						</tr>
						<tr>
							<td colspan="6">
							   <input type="text" placeholder="json格式" style="width:1000px;height:30px;"/>
							</td>
							<td>
						       <span class="yw-btn bg-green ml20 cur" onclick="send();">发送</span>
						    </td>
						</tr>
						<tr id="rcuGrid" class="ts15">
				           <td></td>
				           <td>一</td>
				           <td>二</td>
				           <td>三</td>
				           <td>四</td>
				           <td>五</td>
		               </tr>
		               <%-- <c:forEach var="item" items="">
		                   <tr>
		                      <td></td>
		                   </tr>
		               </c:forEach> --%>
					</table>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
