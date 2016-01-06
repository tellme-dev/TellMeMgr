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
		var consoleId;
		var wsHost= window.location.host ;
		var svrName=window.location.pathname.split("/")[1];
		
		var wsUrl="ws:" + wsHost +"/" + svrName;
		
		$(document).ready(function(){
			$("#pager").pager({
			    pagenumber:'${page.pageNo}',                         /* 表示初始页数 */
			    pagecount:'${page.pageCount}',                      /* 表示总页数 */
			    totalCount:'${page.totalCount}',
			    buttonClickCallback:PageClick                     /* 表示点击分页数按钮调用的方法 */                  
			});
			consoleId = createUUID();
			
			
			
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
function onConnection(){
			var uid = $("#uid").val();
			var json = $("#textJson").val();
			var res = $("#lblInfo").val();
			if(res != ""){
			    res += "\n\t\r";
			}
			if(uid == ""){
			  $.messager.alert('系统提示', "请输入uid", 'warning');
			  return;
			}

			this.wsClient =new WebSocket(wsUrl+'/appWs/' + uid); //改成当前输入的用户id
			
			this.wsClient.onopen=function(){
				$("#lblInfo").val(res+"已连接");
				console.log('open');
				var result=wsClient.send(json);
				//var result=wsClient.send("{type:'csts',uid:'u123',sid:'s556'}");
				var x=result;
			};
			
			this.wsClient.onmessage =function(msg){
			try{
			    var obj=JSON.parse(msg.data);
				$("#lblInfo").val(res+JSON.stringify(obj));
			}catch(e){
			    $("#lblInfo").val(res+"返回结果无法转换成json格式:" + msg.data);
			}
			};
			
			this.wsClient.onclose=function(){
				$("#lblInfo").val(res+'连接被关闭!');
			};
			
			this.wsClient.onerror = function(evt)
			{
				$("#lblInfo").val(res+'连接发送错误!' + JSON.stringify(evt));
			};
			
		}
function createUUID(){
		function S4() {
			return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
	    }
			return (S4()+S4()+"-"+S4()+"-"+S4()+"-"+S4()+"-"+S4()+S4()+S4());
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
function clean(){
    $("#textJson").val("");
}
function send(json){
}
</script>
</head>

<body>
	<div class="con-right" id="conRight">
		<div class="fl yw-lump mt10">
			<div class="yw-bi-rows">
				<div class="yw-bi-tabs mt5" id="ywTabs">
					<span class="yw-bi-now">控制台</span>
					<!-- <span style="background-color:#75B74B" class="bg-green" onclick="onConnection()">连接</span> -->
				</div>
			</div>
			<div class="yw-tab">
				<form id="rcuForm" name="rcuForm" action="saveOrupdateRcu.do" method="post">
					<table id="tab1" class="yw-cm-table font16">
					    <tr>
							<td width="10%" align="center">uid：</td>
							<td colspan="6">
								<input id="uid" name="uid" type="text" class="easyui-validatebox" style="width:100px;height:30px;" /> 
							</td>
						</tr>
						<!-- <tr>
							<td width="10%" align="center">sid：</td>
							<td colspan="6">
								<input id="sid" name="id" type="text" class="easyui-validatebox" style="width:100px;height:30px;" /> 
							</td>
						</tr> -->
						<!-- <tr>
							<td width="10%" align="center">灯光：</td>
							<td colspan="6">
							     <span id="model_1" class="yw-btn bg-gray ml20 cur ts15" onclick="selectModel(1)">模式一</span>
							     <span id="model_2" class="yw-btn bg-gray ml20 cur ts15" onclick="selectModel(2)">模式二</span>
							     <span id="model_3" class="yw-btn bg-gray ml20 cur ts15" onclick="selectModel(3)">模式三</span>
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
							<td colspan="6">
							        <div class="switch demo3">
					                      <input type="checkbox" checked>
					                      <label><i></i></label>
				                    </div>
							</td>
						</tr> -->
						<tr>
						    <td width="10%" align="center">json命令：</td>
							<td colspan="5">
							   <input id="textJson" type="text" placeholder="json格式" style="width:1000px;height:30px;"/>
							</td>
							<td>
							   <span class="yw-btn bg-green ml20 cur" onclick="onConnection()">发送</span>
						       <span class="yw-btn bg-gray ml20 cur" onclick="clean()">清除</span>
						    </td>
						</tr>
						<tr>
						  <td width="10%" align="center">返回结果：</td>
						  <td colspan="6">
						    <textarea style="margin-top:10px" id="lblInfo" rows="20" cols="150" required></textarea>
						  </td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
