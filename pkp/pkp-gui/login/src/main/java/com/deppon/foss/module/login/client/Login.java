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
 * FILE PATH        	: login/src/main/java/com/deppon/foss/module/login/client/Login.java
 * 
 * FILE NAME        	: Login.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.login.client;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.module.boot.client.app.ILogin;
import com.deppon.foss.module.login.client.action.EnterKeyAdapter;
import com.deppon.foss.module.login.client.action.LoginAction;
import com.deppon.foss.module.login.client.action.LoginType;
import com.deppon.foss.module.login.client.ui.AppLoginFrame;

/**
 * 登陆功能统一接口对象类
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:dp-yangtong,date:2012-10-12 上午11:45:33,content:TODO
 * </p>
 * 
 * @author dp-yangtong
 * @date 2012-10-12 上午11:45:33
 * @since
 * @version
 */
public class Login implements ILogin {

    /**
	 * 
	 */
    private static final long serialVersionUID = 8490706914663242368L;
    public static final Log LOG = LogFactory.getLog(Login.class);

    private AppLoginFrame loginFrame;
    private LoginAction onLineByButton;
    private EnterKeyAdapter onLineByKey;

    // private LoginAction offLine;
    private AtomicBoolean loginOk = new AtomicBoolean(false);
    private AtomicBoolean loginReturn = new AtomicBoolean(false);
    private CountDownLatch loginSignal = new CountDownLatch(1);

    public CountDownLatch getLoginSignal() {
        return loginSignal;
    }

    public AtomicBoolean getLoginOk() {
        return loginOk;
    }

    public AtomicBoolean getLoginReturn() {
        return loginReturn;
    }

    @Override
    public boolean login() {

        loginFrame = new AppLoginFrame();
        /*loginFrame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

            }
        });*/

        onLineByButton = new LoginAction(LoginType.ONLINE_LOGIN, loginFrame, this);
        onLineByKey = new EnterKeyAdapter(LoginType.ONLINE_LOGIN, loginFrame, this);

        // offLine = new LoginAction(LoginType.OFFLINE_LOGIN,loginFrame,this);

        loginFrame.getBtnOnLineLogin().addActionListener(onLineByButton);
        loginFrame.getTextUsername().addKeyListener(onLineByKey);
        loginFrame.getTextPassword().addKeyListener(onLineByKey);
        // loginFrame.getBtnOffLineLogin().addActionListener(offLine);

        loginFrame.setVisible(true);

        // while (flag) {
        // if (this.loginOk.get()) {
        // return this.loginReturn.get();
        // }
        // }
        try {
            this.loginSignal.await();
        } catch (InterruptedException e1) {
        	// to do nothing
        }
        return this.loginReturn.get();
    }

}