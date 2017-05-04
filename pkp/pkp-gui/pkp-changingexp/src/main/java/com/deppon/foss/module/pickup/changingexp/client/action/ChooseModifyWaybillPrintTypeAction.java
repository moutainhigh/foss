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
 * PROJECT NAME	: pkp-changingexp
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/action/ChooseModifyWaybillPrintTypeAction.java
 * 
 * FILE NAME        	: ChooseModifyWaybillPrintTypeAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.changingexp.client.action;

import java.awt.event.ActionEvent;

import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.module.pickup.changingexp.client.ui.WaybillRFCUI;
import com.deppon.foss.module.pickup.changingexp.client.ui.dialog.RFCPrintChoiceDiaglogNew;

/**
 * 选择打印更改单的类型
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-jiangfei,date:2012-10-24 上午11:25:20
 * </p>
 * 
 * @author foss-jiangfei
 * @date 2012-10-24 上午11:25:20
 * @since
 * @version
 */
public class ChooseModifyWaybillPrintTypeAction implements
		IButtonActionListener<WaybillRFCUI> {
	private WaybillRFCUI waybillRFCUI;

	/**
	 * <p>
	 * 弹出更改单选择对话框
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-10-24 上午11:26:03
	 * @param e
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		RFCPrintChoiceDiaglogNew dialog = new RFCPrintChoiceDiaglogNew(waybillRFCUI);
		WindowUtil.centerAndShow(dialog);
	}

	/**
	 * <p>
	 * UI注入
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-10-24 上午11:26:03
	 * @param ui
	 * @see com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener#setInjectUI(java.awt.Container)
	 */
	@Override
	public void setInjectUI(WaybillRFCUI ui) {
		waybillRFCUI = ui;
	}

}