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
 * FILE PATH        	: login/src/main/java/com/deppon/foss/module/login/client/action/EnterKeyAdapter.java
 * 
 * FILE NAME        	: EnterKeyAdapter.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.login.client.action;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.module.login.client.Login;
import com.deppon.foss.module.login.client.ui.AppLoginFrame;


/**
 * 登陆界面按键输入回车执行类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午11:43:51, </p>
 * @author dp-yangtong
 * @date 2012-10-12 上午11:43:51
 * @since
 * @version
 */
public class EnterKeyAdapter extends KeyAdapter {

	private AppLoginFrame loginFrame;
	private LoginType loginType;
	private Login login;
	
	public EnterKeyAdapter(LoginType loginType, AppLoginFrame loginFrame,
			Login login) {
		
		this.login = login;
		this.loginType = loginType;
		this.loginFrame = loginFrame;
	}
	/**
	 * 
	 * 按钮触发快捷键
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int k=e.getKeyCode();
		if(k == KeyEvent.VK_ENTER){
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
	
	
	

}