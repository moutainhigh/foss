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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/action/QueryOtherChargeAction.java
 * 
 * FILE NAME        	: QueryOtherChargeAction.java
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
import com.deppon.foss.module.pickup.changingexp.client.ui.WaybillRFCUI;
import com.deppon.foss.module.pickup.changingexp.client.ui.dialog.QueryOtherChargeDialog;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;

/**
 * 
 * 查询其他费用Action
 * @author 102246-foss-shaohongliang
 * @date 2012-12-24 下午5:33:52
 */
public class QueryOtherChargeAction extends
		AbstractButtonActionListener<WaybillRFCUI> {

	/**
	 * UI OBJECT
	 */
	private WaybillRFCUI ui;

	/**
	 * 
	 * <p>
	 * (计算总费用)
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-16 下午02:46:47
	 * @param e
	 * @see com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		showOtherChargeDialog();

	}
	
	
	/**
	 * 
	 * 计算计费重量
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-31 下午02:14:05
	 */
	private void showOtherChargeDialog() {
		QueryOtherChargeDialog dialog = new QueryOtherChargeDialog(ui);
		// 剧中显示弹出窗口
		WindowUtil.centerAndShow(dialog);
	}

	/**
	 * 
	 * UI注入
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午5:28:30
	 * @see com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener#setIInjectUI(java.awt.Container)
	 */
	@Override
	public void setIInjectUI(WaybillRFCUI ui) {
		this.ui = ui;
	}

}