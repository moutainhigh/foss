<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/ext" prefix="ext" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta content="LOGIN_JSP">
	<link rel="stylesheet" type="text/css" href="${resources}/styles/layout.css"/>
	<ext:module groups="login"/>
	<script type="text/javascript">
		login.dev = ${dev};
	</script>
	<script src="http://pv.sohu.com/cityjson?ie=utf-8"></script>  
</head>
<body onload='bodyReady()'>
	<div class="logo"><h1><ext:i18nForJsp key="foss.login.logo"/></h1></div>
	<div class="layout">
		<div class="login_layout">
			<div class="login">
				<h2 id="dateTime"></h2>
				<ul>
					<form id='loginForm' >
					    <input type="hidden" name="publicIp"  id="publicIp">
					     
						<input type="hidden" name="doLogin" value="true">
						<li><label><ext:i18nForJsp key="foss.login.loginName"/></label><input id='loginName' name='loginName' onblur="checkUserName(this);"/></li>
						<li><label><ext:i18nForJsp key="foss.login.password"/></label><input id='password' name='password' type='password'/></li>
						<div id='cashierValidatorModule' style="display:none"><label style="font-size:18px"><ext:i18nForJsp key="foss.login.cashierValidator"/></label><input id='cashierValidator' name='cashierValidator'/><button id="validationCode" type="button" onclick="sendCode(this);" title="发送短信">发送短信</button></div>
						<input id='phoneNum' type="hidden" name="phoneNum">
						<input id='validator' type="hidden" name="validator">
						<% String resetSuccuess=(String)request.getSession().getAttribute("resetSuccuess");%>
						<% String message=(String)request.getAttribute("message");%>
						<li id='errorLi'><label></label><span class="error" id='error'><%if(null != message){%><%=message%><%} %><%else if(null != resetSuccuess && !"".equals(resetSuccuess)){ %><%=resetSuccuess%><%}%></span></li>
						<li text-align="center" class="t-r"><a href="javascript:" onclick='checkUser()'><font color="#e99d06"><strong>忘记密码了？</strong></font></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="a_login" href="javascript:" onclick='loginHandler()'><ext:i18nForJsp key="foss.login.submit"/></a></li>	
					</form>
				</ul>
			</div>
		</div>
	</div>
</body>

</html>