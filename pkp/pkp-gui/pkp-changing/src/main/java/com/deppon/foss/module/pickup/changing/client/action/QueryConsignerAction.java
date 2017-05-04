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
 * PROJECT NAME	: pkp-changing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/action/QueryConsignerAction.java
 * 
 * FILE NAME        	: QueryConsignerAction.java
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
package com.deppon.foss.module.pickup.changing.client.action;

import java.awt.event.ActionEvent;

import com.deppon.foss.module.pickup.changing.client.ui.WaybillRFCUI;
import com.deppon.foss.module.pickup.changing.client.utils.Common;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.common.client.utils.BusinessUtils;

/**
 * 
 * “查询会员”Action
 * @author 102246-foss-shaohongliang
 * @date 2012-12-24 下午5:33:16
 */
public class QueryConsignerAction extends AbstractButtonActionListener<WaybillRFCUI> {

	/**
	 * UI OBJECT
	 */
	WaybillRFCUI ui;


	// 定义业务工具类
	BusinessUtils bu = new BusinessUtils();
	/**
	 * 
	 * <p>
	 * 查询发货客户
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-16 下午02:46:47
	 * @param e
	 * @see com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		ui.getButtonPanel().getBtnSubmit().setEnabled(false);// 提交为不可编辑
		//设置发货客户信息
		Common.setQueryDeliveryCustomer(ui);
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