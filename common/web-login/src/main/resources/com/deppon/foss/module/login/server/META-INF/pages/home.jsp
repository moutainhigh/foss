<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib uri="/ext" prefix="ext" %>
<ext:module groups="home"/>
<div class="home_bg">
	<div class="welMes">
		<img src="${images}/user.png" width="12" height="16" style="margin-right:10px;"/>
		<span>
			欢迎登录FOSS,您上次登录时间为:${lastLoginTime}
		</span>
	</div>
	<div class="procl">
    	<div class="titlepic"></div>
    	<div class="grid" id="announcementgird"></div>
    </div>
    <div class="procl">
		<table>
			<tr>
				<td>
					<img  src="${images}/syshelp.png" width="147" height="86"  style="float:left; margin-top:5px;"/>
				</td>
				<td>
					<div class="systemHelpGird" id="systemHelpGird"></div>
				</td>
			</tr>
		</table>
    </div>
</div>
