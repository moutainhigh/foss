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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/action/QueryPublicPriceLocalUIAction.java
 * 
 * FILE NAME        	: QueryPublicPriceLocalUIAction.java
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
package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;

import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.core.context.ApplicationContext;
import com.deppon.foss.module.pickup.common.client.ui.ExpQueryPublishPriceUI;
import com.deppon.foss.module.pickup.common.client.utils.ModalFrameUtil;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI;

/**
 * 查询本地公布价
 * @author 026123-foss-lifengteng
 *
 */
public class ExpQueryPublicPriceLocalUIAction implements IButtonActionListener<ExpWaybillEditUI>{
	ExpWaybillEditUI ui;
	/**
	 * 500
	 */
	private static final int FIVEZEROZERO = 500;
	/**
	 * 1030
	 */
	private static final int EIGHTFIVESZERO = 1030;
	

	/**
	 * 
	 * 功能：openUIAction
	 * 
	 * @param:
	 * @return:
	 * @since:1.6
	 */
	public void actionPerformed(ActionEvent e) {
		// 定义全局对象，用来判断该窗口是否已创建，已节约资源
		ExpQueryPublishPriceUI panel= ui.getExpQueryPublishPriceUI();
		panel.setSize(EIGHTFIVESZERO, FIVEZEROZERO);
		// 居中显示弹出窗口
		ModalFrameUtil.getInstance().showAsModal(panel, ApplicationContext.getApplication().getWorkbench().getFrame());
	}

	@Override
	public void setInjectUI(ExpWaybillEditUI ui) {
		this.ui = ui;
	}
}