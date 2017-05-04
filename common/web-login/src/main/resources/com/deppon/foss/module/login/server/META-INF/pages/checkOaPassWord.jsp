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
			<div class="login" style = "width:440px height:320px;">
				<ul>
				<div align ="left">
				<h3 style="font-size:30px"><font color="#e99d06"><strong>身份验证</strong></font><strong>>找回密码</strong></h3>
				<h3>请正确填写您的信息，以便您能更快的找回密码</h3><br/>
				<h3>--------------------------------------------------------------------------</h3>
				</div>
					<form id='oaPassCheckForm' action='../login/checkFossPasswordByOa.action' method='post'>
					    <br/>
						<li><label>OA工号</label><input id='emp_code' name='emp_code' size = "240px"/></li>
						<br/>
						<li><label>OA密码</label><input id='emp_password' name='emp_password' type='password'  size = "240px"/></li>
						<% String exception=(String)request.getAttribute("checkxception");%>
						<% 
						   String message=(String)request.getAttribute("message");
						   if(message==null){
							   message = (String)request.getSession().getAttribute("web_foss_login_message");
							   request.getSession().setAttribute("web_foss_login_message",null);
						   }
						%>
						<li id='errorLi' style="font-size:12px"><label></label><span class="error" id='error'><%if(null != message){%><%=message%><%} %><%else if(null != exception && !"".equals(exception)){ %><%=exception%><%}%></span></li>
						<li class="t-r"><a class="a_login" href="javascript:" onclick='checkIsNull()'>下一步</a></li>
					</form>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>
