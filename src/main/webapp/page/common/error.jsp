<%@ page language="java" contentType="text/html; charset=UTF-8" isErrorPage="true"%>
<%@ include file="/page/common/taglibs.jsp"%>  

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <title>错误页面</title>  
    <script type="text/javascript">  
        $(function(){  
            $("#center-div").center(true);  
        })  
    </script>  
</head>  
<body style="margin: 0;padding: 0;background-color: #f5f5f5;">  
    <div id="center-div">  
        <table style="height: 100%; width: 600px; text-align: center;">  
            <tr>  
                <td>  
              <!-- <img width="220" height="393" src="${basePath}/images/common/error.png" style="float: left; padding-right: 20px;" alt="" />  --> 
                    <%=exception.getMessage() %>
                    <p style="line-height: 12px; color: #666666; font-family: Tahoma; font-size: 12px; text-align: left;">  
                    <a href="javascript:history.go(-1);">返回</a>!!!  
                    </p>  
                </td>  
            </tr>  
        </table>  
    </div>  
</body>  
</html>
