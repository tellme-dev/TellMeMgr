<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript">

</script>
<div class="con-left" id="conLeft">
            	<%-- <div class="yw-user">
                	<div class="yw-userhead">
                    	<img src="${pageContext.request.contextPath}/source/images/userhaed.png"/>
                   	</div>
                    <p class="yw-usertxt">陈某</p>
                    <p class="yw-usertxt font-size14">职位</p>
                </div> --%>
                <div class="yw-left-menu">
                	<ul>
                	<c:forEach var="item" items="${sessionScope.userInfo.childMenuList}">
                		<li>
                        	<em></em><span onclick="javascript:window.location.href = '${pageContext.request.contextPath}${item.url}'"><i class="fl yw-icon icon-todayjob"></i>${item.name}</span>
                        </li>
                	</c:forEach>
                    	<!-- <li class="yw-left-menu-now">
                        	<em></em><span><i class="fl yw-icon icon-todayjob"></i>合作伙伴</span>
                        </li> -->
                    </ul>
                </div>
            </div>