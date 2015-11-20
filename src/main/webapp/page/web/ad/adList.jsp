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
			    pagenumber:'${ad.pageNo}',                         /* 表示初始页数 */
			    pagecount:'${ad.pageCount}',                      /* 表示总页数 */
			    totalCount:'${ad.totalCount}',
			    buttonClickCallback:PageClick                     /* 表示点击分页数按钮调用的方法 */                  
			});
			$("#adinfoList tr").each(function(i){
				if(i>0){
					$(this).bind("click",function(){
						var adId = $(this).find("td").first().text();
						 window.location.href="adinfo.do?adId="+adId;
					});
				}
			}); 
		}); 
PageClick = function(pageclickednumber) {
	$("#pager").pager({
	    pagenumber:pageclickednumber,                 /* 表示启示页 */
	    pagecount:'${ad.pageCount}',                  /* 表示最大页数pagecount */
	    buttonClickCallback:PageClick                 /* 表示点击页数时的调用的方法就可实现javascript分页功能 */            
	});
	
	$("#pageNumber").val(pageclickednumber);          /* 给pageNumber从新赋值 */
	/* 执行Action */
	pagesearch();
};
function tagChange(type){
	if(type == 1){
		$("#tagContent").hide();
		$("#itemTag").show();
	}else{
		$("#tagContent").show();
		$("#itemTag").hide();
	}
}
function search(){
	$("#pageNumber").val("1");
	pagesearch(); 
} 
function pagesearch(){
	adForm.submit();
}
function gotoAdd(){
	window.location.href="adinfo.do?adId=0";
}
function showdialog(){
	var wz = getDialogPosition($('#adInfoWindow').get(0),100);
	$('#adInfoWindow').window({
		  	top: 100,
		    left: wz[1],
		    onBeforeClose: function () {
		    }
	});
	$('#adInfoWindow').window('open');
}
function saveAd(obj){
	if ($('#saveAdForm').form('validate')) {
		$(obj).attr("onclick", ""); 
		 $('#saveAdForm').form('submit',{
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
</script>
</head>

<body>
	<div class="con-right" id="conRight">
		<div class="fl yw-lump mt10">
			<div id="tab2" class="yw-tab">
				<div class="fl yw-lump mt10">
					<form id="adForm" name="adForm"
						action="adList.do" method="get">
						<div class="pd10-28">
							<div class="fr">
								<%-- <input type="text" name="searchName" class="yw-input wid170"
									placeholder="搜索" value="${ad.searchName}" />
								<span class="yw-btn bg-orange ml30 cur" onclick="search();">开始查找</span> --%>
								<span class="yw-btn bg-green ml20 cur" onclick="gotoAdd();">新建广告</span>
								<span class="yw-btn bg-green ml20 cur" onclick="">删除广告</span>
							</div>
							<div class="cl"></div>
						</div>
			
						<input type="hidden" id="pageNumber" name="pageNo"
							value="${ad.pageNo}" />
					</form>
				</div>
				<table class="yw-cm-table" id="adinfoList">
					<tr>
						<th style="display:none">序号</th>
						<th>名称</th>
						<th>关键字</th> 
						<th>类型</th>
						<th>项目</th>
						<th>内容</th>
						<th>创建时间</th>
					</tr>
					<c:forEach var="item" items="${adlist}">
						<tr>
							<td style="display:none" align="left">${item.id}</td>
							<td>${item.name}</td> 
							<td>${item.key}</td> 
							<c:if test="${item.targetType == 1}">
							  <td>项目标签</td>
                            </c:if>
							<c:if test="${item.targetType == 2}">
							  <td>社区</td>
                            </c:if>
							<c:if test="${item.targetType == 3}">
							  <td>URL地址</td>
                            </c:if>
							<c:if test="${item.targetType == 4}">
							  <td>HTML页面</td>
                            </c:if>
							<td>${item.targetName}</td> 
							<td>${item.targetContent}</td> 
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
	
 	  <div id="adInfoWindow" class="easyui-window" title="新添广告" style="width:560px;height:480px;overflow:hidden;padding:10px;" iconCls="icon-info" closed="true" modal="true"   resizable="false" collapsible="false" minimizable="false" maximizable="false">
		<form id="saveAdForm" name ="saveAdForm" action="saveOrupdateAd.do"  method="post">
		<p style="display:none">
        	<span class="fl">id：</span><input name="id" type="text" value="0" class="easyui-validatebox"/>
        </p>
		<p class="yw-window-p">
        	<span class="fl">选择图片：</span><input name="imageUrl" type="file" id="file" value="" class="easyui-validatebox"  validType="Length[1,25]" style="width:254px;height:30px;"/>
        </p> 
		<p class="yw-window-p">
        	<span class="fl">图片描述：</span><input name="imageText" type="text" value="" class="easyui-validatebox"  validType="Length[1,25]" style="width:254px;height:30px;"/>
        </p> 
		<p class="yw-window-p">
        	<span class="fl">名称：</span><input name="name" type="text" value="" class="easyui-validatebox" required="true"  validType="Length[1,25]" style="width:254px;height:30px;"/>
        </p> 
		<p class="yw-window-p">
        	<span class="fl">关键字：</span><input name="key" type="text" value="" placeholder="关键字之间用逗号隔开" class="easyui-validatebox"  validType="Length[1,25]" style="width:254px;height:30px;"/>
        </p>
        <p class="yw-window-p">
        	<span class="fl">类型：</span>
        	  <input type="radio" name="targetType" onclick="tagChange(1)" value="1" checked>项目标签　　
        	  <input type="radio" name="targetType" onclick="tagChange(2)" value="2">社区　　
        	  <input type="radio" name="targetType" onclick="tagChange(3)" value="3">URL地址　　
        	  <input type="radio" name="targetType" onclick="tagChange(4)" value="4">HTML页面　　
        </p> 
		<!-- <p class="yw-window-p">
        	<span class="fl">类型：</span><select name="targetType" onchange="tagChange()" class="easyui-combobox"><option value="" selected>=请选择类型=</option><option value="1">项目标签</option><option value="2">社区</option><option value="3">URL地址</option><option value="4">HTML页面</option></select>
        </p> -->
        <p id="tagContent" style="display:none" class="yw-window-p">
        	<span class="fl">内容：</span><input name="targetContent" type="text" value="" class="easyui-validatebox" required="true"  validType="Length[1,25]" style="width:254px;height:30px;"/>
        </p>
        <p id="itemTag" class="yw-window-p">
        	<span class="fl">项目：</span>
        	<c:forEach var="item" items="${taglist}">
        	  <input type="radio" name="targetIds"  value="${item.id}" checked>${item.name}　　
			</c:forEach>
        </p>
        <div class="yw-window-footer txt-right">
        	<span class="yw-window-btn bg-lightgray mt12"  onclick="$('#adInfoWindow').window('close');">退出</span>
        	<span class="yw-window-btn bg-blue mt12" onclick="saveAd(this);">保存</span>
        </div>
        </form>
	</div>
</body>
</html>
