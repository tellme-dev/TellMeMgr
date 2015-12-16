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
<title>广告信息</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1  ,maximum-scale=1, user-scalable=no" /> 
<script type="text/javascript">
    var type;//0为新增；1为编辑
    $(document).ready(function(){
    	type = ${type};
    	//setShowStates();
    	if(type==1){//编辑
    		var adIds = "${bannerinfo.adIds}"
    	    var str = adIds.split(",");
    		$("#tab2 :checkbox").each(function(i){
    			var adId = $(this).parents("tr").find("td").first().text();
    			var checkbox = $(this).parents("tr").find("td")[1].childNodes[0];
    			for(var j=0;j<str.length;j++){
    				if(adId == str[j]){
    					checkbox.checked = true;
    				}
    			}
    		}); 
    	}
    	
    });
	function setShowStates(){
		$("#txtTagContent").attr("readonly","readonly");
		$("#itemTagSelect").combotree("disable",true);
		$("#hotelSelect").combobox("disable",true);
		$("#select_bbs").hide();
	};
	function save(obj) {
		if ($('#bannerForm').form('validate')) {
			$(obj).attr("onclick", "");
			$('#bannerForm').form(
					'submit',
					{
						success : function(data) {
							data = $.parseJSON(data);
							if (data.code == 1) {
								$.messager.alert('保存信息', data.message, 'info',
										function() {  
									     window.location.href='bannerList.do';
										});
							} else {
								$.messager.alert('错误信息', data.message, 'error');
							}
						}
					});
		}
	}; 
	function showdialog(){
		var wz = getDialogPosition($('#adWindow').get(0),100);
		$('#adWindow').window({
			  	top: 100,
			    left: wz[1],
			    onBeforeClose: function () {
			    }
		});
		$('#adWindow').window('open');
		
	}
	function saveAds(){
		var adIds = "";
		var adNames = "";
		$("#tab2 :checkbox:checked").each(function(i){
			var adId = $(this).parents("tr").find("td").first().text();
			var adName = $(this).parents("tr").find("td")[2].innerText;
			if(i==0){
				adIds += adId;
				adNames += adName;
			}else{
				adIds += ","+adId; 
				adNames += ","+adName;
			}
		}); 
		document.getElementById("adShow").value = adNames;
		document.getElementById("adSelect").value = adIds;
		$('#adWindow').window('close');
	}
</script>
</head>

<body>
	<div class="con-right" id="conRight">
		<div class="fl yw-lump">
			<div class="yw-lump-title">
				<span style="cursor: pointer;" onclick="window.location.href='bannerList.do'"><i class="yw-icon icon-back"></i>返回</span>
			</div>
		</div>

		<div class="fl yw-lump mt10">
			<div class="yw-bi-rows">
				<div class="yw-bi-tabs mt5" id="ywTabs">
					<span class="yw-bi-now">广告位置</span>
				</div>
				<div class="fr mt10">
					<!-- <span class="yw-btn bg-green mr26" id="editBtn" onclick="edit();">编辑</span>  -->
					<span class="yw-btn bg-red mr26" id="saveBtn" onclick="save(this);" style="cursor: pointer;">保存</span>
				</div>
			</div>
			<div class="yw-tab">
				<form id="bannerForm" name="bannerForm"
					action="saveOrupdateBanner.do" method="post">
					<table id="tab1" class="yw-cm-table font16">
						<tr>
							<td width="10%" align="center">位置：</td>
							<td colspan="2">
							   <input type="hidden" name="id" value="${bannerinfo.id }"/>
							   <select id="positionSelect" name="positionType" style="width:250px;height:30px;" class="easyui-combobox" data-options="editable:false">
							     <c:choose>  
                                    <c:when test="${bannerinfo.positionType == 1}">
                                         <option value="1" selected="selected">首页顶部</option>
							             <option value="2">首页底部</option>
                                    </c:when>  
                                    <c:when test="${bannerinfo.positionType == 2}">
                                         <option value="2" selected="selected">首页底部</option>
							             <option value="1">首页顶部</option>
                                    </c:when>
                                    <c:otherwise>  
                                         <option value="1">首页顶部</option>
							             <option value="2">首页底部</option>
                                    </c:otherwise>  
                                 </c:choose>  
							     <%-- <c:forEach var="item" items="${bannerlist }">
							       <option value="${item.positionType }">${item.position}</option>
							     </c:forEach> --%>
							  </select>
							</td>
						</tr>
						<tr>
						   <td width="10%" align="center"></td>
						   <td>
						   <c:choose>  
                                    <c:when test="${bannerinfo.isUsed == true}">
                                         <label><input type="checkbox" name="isUsed" id="is_used" checked>是否使用</label>
                                    </c:when>  
                                    <c:when test="${bannerinfo.isUsed == false}">
                                         <label><input type="checkbox" name="isUsed" id="is_used">是否使用</label>
                                    </c:when>
                                    <c:otherwise>  
                                         <label><input type="checkbox" name="isUsed" id="is_used" checked>是否使用</label>
                                    </c:otherwise>  
                           </c:choose> 
						   </td>
						</tr>
						<tr>
							<td width="10%" align="center">广告：</td>
							<td colspan="2">
							   <input id="adShow" class="easyui-validatebox" value="${bannerinfo.adName}" readonly="readonly" placeholder="请选择广告" style="width:500px;height:30px;"/>
							   <input type="hidden" id="adSelect" name="adIds" value="${bannerinfo.adIds}" class="easyui-validatebox" style="width:500px;height:30px;"/>
							   <span id="select_ad" class="yw-btn bg-blue mt12" style="cursor: pointer;" onclick="showdialog()">选择广告</span>
							</td>
						</tr>
						<tr>
						  <th style="display:none">序号</th>
						  <th width="5%"></th>
						</tr>
						<c:forEach var="item" items="${badlist}">
						<tr>
							<td style="display:none" align="left">${item.id}</td>
							<td width="5%"><input name="checkbox" type="checkbox"/></td>
							<td>${item.name}</td> 
						</tr>
					    </c:forEach>
					</table>
				</form>
			</div>
		</div>
	</div>
	<div id="adWindow" class="easyui-window" title="选择广告" style="width:560px;height:480px;overflow:scrollbars;padding:10px;" iconCls="icon-info" closed="true" modal="true"   resizable="false" collapsible="false" minimizable="false" maximizable="false">
	   <table id="tab2" class="yw-cm-table font16">
	     <c:forEach var="item" items="${adList}"> 
	      <tr>
	        <td style="display:none" align="left">${item.id}</td>
	        <td width="5%"><input name="checkbox" type="checkbox"/></td>
	        <td>${item.name}</td>
	      </tr>
	      </c:forEach>
	   </table>
	   <div class="yw-window-footer txt-right">
        	<span class="yw-window-btn bg-lightgray mt12" style="cursor: pointer;" onclick="$('#adWindow').window('close');">退出</span>
        	<span class="yw-window-btn bg-blue mt12" onclick="saveAds();" style="cursor: pointer;">保存</span>
       </div>
	</div>
</body>
</html>