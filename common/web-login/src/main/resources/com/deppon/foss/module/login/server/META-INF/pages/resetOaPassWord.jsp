<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/ext" prefix="ext" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta content="LOGIN_JSP">
	<link rel="stylesheet" type="text/css" href="${resources}/styles/layout.css"/>
	<ext:module groups="login"/>
</head>
<body >
	<div class="logo"><h1><ext:i18nForJsp key="foss.login.logo"/></h1></div>
	<div class="layout">
		<div class="login_layout">
			<div class="login" style = "width:420px;">
				<ul>
				<div align ="left">
				<h3 style="font-size:30px"><strong>身份验证</strong><font color="#e99d06"><strong>>找回密码</strong></font></h3><br/>
				<h3>为了您的账号安全，建议您使用字母加数字组合，密码长度设置为6-16位</h3><br/>
				<h3>--------------------------------------------------------------------------</h3>
				</div>
				<% String token=(String)request.getAttribute("token");%>
					<form id='resetPasswordForm' action='/bse-baseinfo-web/login/resetFossLoginPassword.action' method='post'>
					    <li><input  type="hidden" id='token' name='token' value = '<%=token%>'/></li>
						<li><label style = "width:125px;">请输入新密码</label><input id='newPassword' name='newPassword' type='password' size = "50px"/></li>
						<br/>
						<li><label style = "width:125px;">确认密码&nbsp;&nbsp;</label><input id='confirmPassword' name='confirmPassword' type='password' size = "50px"/></li>
						<% String exception=(String)request.getAttribute("checkxception1");%>
						<% String message=(String)request.getAttribute("web_foss_login_message");
						   if(message==null){
							   message = (String)request.getSession().getAttribute("web_foss_login_message");
							   request.getSession().setAttribute("web_foss_login_message",null);
						   }
						%>
						<li id='errorLi' style="font-size:12px"><label></label><span class="error" id='error'><%if(null != message){%><%=message%><%} %><%else if(null != exception && !"".equals(exception)){ %><%=exception%><%}%></span></li>
						<li class="t-r"><a class="a_login" href="javascript:" onclick='checkNewPassword()'>完成</a></li>
					</form>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>