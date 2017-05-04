/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-gui
 * 
 * FILE PATH        	: login/src/main/java/com/deppon/foss/module/login/client/action/LoginAction.java
 * 
 * FILE NAME        	: LoginAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.login.client.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.server.Definitions;
import com.deppon.foss.module.login.client.Login;
import com.deppon.foss.module.login.client.ui.AppLoginFrame;
import com.deppon.foss.module.pickup.common.client.utils.BZPartnersJudge;

/**
 * GUI登陆动作统一处理类
 * Description: Company: deppon
 * 
 * @author: david 寸
 * @version: 1.0 Create at: 2011-4-16 下午05:22:13
 * 
 *           Modification History: Date Author Version Description
 *           ------------------------------------------------------------------
 *           2011-4-16 david 寸 1.0 1.0 Version
 */

public class LoginAction extends AbstractAction {
	
	private static final long serialVersionUID = 1849842401847207366L;

	private II18n i18n = I18nManager.getI18n(LoginAction.class);
	private AppLoginFrame loginFrame;
	private LoginType loginType;
	private Login login;

	public LoginAction(LoginType loginType, AppLoginFrame loginFrame,
			Login login) {
		putValue(Action.NAME, i18n.get("offline.signin.button"));
		this.login = login;
		this.loginType = loginType;
		this.loginFrame = loginFrame;
		destoryResource();
	}

	/**
	 * 释放上次登录资源
	 */
	private void destoryResource() {
		loginType.count = 0;
		SessionContext.remove(SessionContext.KEY_USER);
		SessionContext.remove(Definitions.KEY_USER);
		SessionContext.remove("user_status");
		SessionContext.remove("login_info");
		SessionContext.remove("FOSS_KEY_CURRENT_DEPTCODE");
		SessionContext.remove("FRAMEWORK_KEY_EMPCODE");
		//是否合伙人权限信息
		BZPartnersJudge.init() ;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Runnable loginRunnable = new Runnable() {
			
			@Override
			public void run() {
				/**
				 * 设置语言
				 */
				SessionContext.set(SessionContext.ACCEPT_LANGUAGE, "zh-cn");
				LoginProcesser processer = new LoginProcesser(loginType, loginFrame, login);
				processer.doLoginProcess();
			}
		};
		//不阻塞EDT线程，登陆操作与UI控制并行
		new Thread(loginRunnable).start();
	}
}