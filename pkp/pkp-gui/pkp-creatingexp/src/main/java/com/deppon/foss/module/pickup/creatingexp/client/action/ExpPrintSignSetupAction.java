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
 * PROJECT NAME	: pkp-creating
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/PrintSignSetupAction.java
 * 
 * FILE NAME        	: PrintSignSetupAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpPrintSignUI;
import com.deppon.foss.print.labelprint.gui.LblPrtSetupWindow;
import com.deppon.foss.print.labelprint.util.PropertiesUtil;

 
/**
 * 
 * 打印整车标签设置
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-17 上午11:16:43,
 * </p>
 * @author 105089-FOSS-yangtong
 * @date 2012-10-17 上午11:16:43
 * @since
 * @version
 */
@SuppressWarnings("unused")
public class ExpPrintSignSetupAction implements IButtonActionListener<ExpPrintSignUI> {
	
	// 日志
	private static final Log LOG = LogFactory.getLog(ExpPrintSignSetupAction.class);
		
	private ExpPrintSignUI ui;
	 
	/**
	 * 标签设置
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try{
			PropertiesUtil.initProperties();
			LblPrtSetupWindow setupwindow = LblPrtSetupWindow.getInstance();
			setupwindow.openWindow();
		}catch (Exception exp) {
			LOG.error("打印设置异常:"+exp.toString(),exp);
		}
	}

 
	@Override
	public void setInjectUI(ExpPrintSignUI ui) {
		this.ui = ui;
	}

}