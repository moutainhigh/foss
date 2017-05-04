package com.deppon.foss.module.login.server.action;

import com.deppon.foss.module.frameworkimpl.server.interceptor.CookieNonCheckRequired;

/**
 * 
 * 模拟登录请求Action类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:ztjie,date:2013-3-18 下午5:15:46,content:TODO </p>
 * @author ztjie
 * @date 2013-3-18 下午5:15:46
 * @since
 * @version
 */
public class SimulationLoginAction {
	
	//注入LoginAction
	private LoginAction loginAction;
	
	//用户名
	private String loginName;
	
	//密码
	private String password;
	
	private boolean loginSuccess;

	@CookieNonCheckRequired
	public String execute() throws Exception {
		loginAction.setDoLogin(true);
		loginAction.setLoginName(loginName);
		loginAction.setPassword(password);
		String result = loginAction.execute();
		loginSuccess = loginAction.isSuccess();
		return result;
	}

	public void setLoginAction(LoginAction loginAction) {
		this.loginAction = loginAction;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isLoginSuccess() {
		return loginSuccess;
	}

}
