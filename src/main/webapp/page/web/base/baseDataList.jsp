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
			    pagenumber:'${baseData.pageNo}',                         /* 表示初始页数 */
			    pagecount:'${baseData.pageCount}',                      /* 表示总页数 */
			    totalCount:'${baseData.totalCount}',
			    buttonClickCallback:PageClick                     /* 表示点击分页数按钮调用的方法 */                  
			});
			$("#baseDatainfoList tr").each(function(i){
				if(i>0){
					$(this).bind("click",function(){
						var baseDataId = $(this).find("td").first().text();
						 window.location.href="baseDatainfo.do?baseDataId="+baseDataId;
					});
				}
			}); 
		}); 
		
PageClick = function(pageclickednumber) {
	$("#pager").pager({
	    pagenumber:pageclickednumber,                 /* 表示启示页 */
	    pagecount:'${baseData.pageCount}',                  /* 表示最大页数pagecount */
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
	baseDataForm.submit();
}
function showdialog(){
	var wz = getDialogPosition($('#baseDataInfoWindow').get(0),100);
	$('#baseDataInfoWindow').window({
		  	top: 100,
		    left: wz[1],
		    onBeforeClose: function () {
		    }
	});
	$('#baseDataInfoWindow').window('open');
}
function saveBaseData(obj){
	if ($('#saveBaseDataForm').form('validate')) {
		$(obj).attr("onclick", ""); 
		 $('#saveBaseDataForm').form('submit',{
		  		success:function(data){
		  			data = $.parseJSON(data);
		  			if(data.code==0){
		  				$.messager.alert('保存信息',data.message,'info',function(){
		  					$('#baseDataInfoWindow').window('close');
		  					search();
	        			});
		  			}else{
						$.messager.alert('错误信息',data.message,'error',function(){
							$(obj).attr("onclick", "saveBaseData(this);"); 
	        			});
		  			}
		  		}
		  	 });  
	}
}  
</script>
</head>

<body>
	content
</body>
</html>
