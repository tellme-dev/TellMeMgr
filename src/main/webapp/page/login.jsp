<%@ page language="java" pageEncoding="utf-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<META http-equiv="X-UA-Compatible" content="IE=edge" />
<meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport' />
<title>易维管理平台-登录</title>
<link type="text/css" href="<%=basePath%>source/css/base.css" rel="stylesheet"/>
<link type="text/css" href="<%=basePath%>source/css/global.css" rel="stylesheet"/>
<link type="text/css" href="<%=basePath%>source/js/easyUI/themes/default/easyui.css" rel="stylesheet"/>
<link type="text/css" href="<%=basePath%>source/js/easyUI/themes/icon.css" rel="stylesheet"/>
<script type="text/javascript" src="<%=basePath%>source/js/jquery-1.11.2.min.js"></script>
<script src="<%=basePath%>source/js/easyUI/jquery.easyui.min.js" type="text/javascript"></script>
<script src="<%=basePath%>source/js/easyUI/easyui-lang-zh_CN.js" type="text/javascript"></script>
<script src="<%=basePath%>source/js/common/validate.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	  // 在这里写你的代码...
	  $("#loginName").keypress(function(e){
		  $("#login-alert").hide();
			if(e.keyCode == 13){
				$("#password").focus();
			}
		});
		$("#password").keypress(function(e){
			$("#login-alert").hide();
			if(e.keyCode == 13){
				login();
			}
		});
});
function loginCheck(obj){
	var ck = $(obj).parent().find("input[type='checkbox']");
	if($(obj).hasClass("yw-checkbox-true")){
		$(obj).removeClass("yw-checkbox-true");
		ck.attr("value","false");
	}else{
		$(obj).addClass("yw-checkbox-true");
		ck.attr("value","true");
	}
}
function login(){
	if ($('#loginForm').form('validate')) {
	    $('#loginForm').form('submit', {
	        url:"login.do",
	        success:function(data){
	        	var item = eval("(" + data + ")");
	        	if(item.code == 0){
	        		window.location.href = item.gotoUrl;
	        		$("#login-alert").html("");
	        		$("#login-alert").hide();
	        	}
	        	else{
	        		$("#login-alert").html("<span style='color:red;'>"+item.message+"</span>");
	        		$("#login-alert").show();
	        	}
	        }
	    });
	}
}
</script>
</head>

<body class="login">
    <div class="box">
        <div class="logo">
            <img src="<%=basePath%>source/images/logo1.png"/>
        </div>
        <form method="post"  id="loginForm" >
        <div class="login-panel">
        
            <div class="fl login-left">
                <div class="fl login-title">用户登录</div>
                <div class="fl panel">
                	<p class="login-rows mt30">
                    	<input type="text" name="name" id="loginName" class="login-input login-name easyui-validatebox" placeholder="用户名"  validType="Length[4,22]" data-options="required:true" value="<shiro:principal/>"/>
                    </p>
                    <p class="login-rows mt30">
                    	<input type="password" name="psd" id="password" class="login-input login-pwd easyui-validatebox" placeholder="密码"  validType="Length[4,22]" data-options="required:true"/>
                    </p>
                    <p class="login-rows mt30">
                    	<label class="yw-checkbox">
                    	<shiro:guest>
	                    	<i class="mt4 mr10"  onClick="loginCheck(this);"></i>
	                    	<input type="checkbox" name="rememberMe" value="false" id="chkRememberPwd">下次自动登录</label>
                    	</shiro:guest>
                    	<shiro:user>
                    		<i class="mt4 mr10 yw-checkbox-true"  onClick="loginCheck(this);"></i>
                    		<input type="checkbox" name="rememberMe" value="true" id="chkRememberPwd">下次自动登录</label>
                    	</shiro:user>
                        <span class="fr font-size12">
                        	<a href="#">忘记密码</a>
                        </span>
                    </p>
                   	<div id="login-alert" class="login-rows" style="display:none;"></div>
                    <p class="login-rows mt30">
                    	<span class="login-btn" onclick="javascript:login()">登 录</span>
                    </p>
                </div>
            </div>
           
            <div class="fl login-line"></div>
            <div class="fl login-right txt-center">
                <img src="<%=basePath%>source/images/twocode.png" class="twocode"/>
                <p class="login-rows font-size12 mt20">
                	<a href="#">扫一扫</a>
                    <a href="#" class="ml20">下载员工版APP</a>
                </p>
            </div>
        </div>
         </form>
        <div class="cl"></div>
    </div>
    <div class="bottom">
    	<p>&copy;天翼运维中心 2015. ALL rights reserved.</p>
    </div>
</body>
</html>
