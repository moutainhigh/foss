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
 * FILE PATH        	: boot/src/main/java/com/deppon/foss/module/boot/client/app/action/ExitAction.java
 * 
 * FILE NAME        	: ExitAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.boot.client.app.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.deppon.foss.framework.client.core.context.ApplicationContext;
 
/**
 * 点击退出按钮退出系统
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:25:43,content:TODO </p>
 * @author dp-yangtong
 * @date 2012-10-12 上午9:25:43
 * @since
 * @version
 */
public class ExitAction implements ActionListener {
	 
	/**
	 * <p>
	 * 退出系统执行事件
	 * </p>
	 * @param arg0
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
	    	/**
	    	 * 调用系统退出事件
	    	 */
		ApplicationContext.getApplication().exit();
	}

}