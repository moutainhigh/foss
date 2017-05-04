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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/WebOrderQueryResetAction.java
 * 
 * FILE NAME        	: WebOrderQueryResetAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;

import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.module.pickup.creating.client.ui.order.WebOrderDialog;

/**
 * 
 * 网上订单对话框查询条件重置
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-11-13 下午4:32:39
 */
public class WebOrderQueryResetAction implements
		IButtonActionListener<WebOrderDialog> {

	private WebOrderDialog ui;

	@Override
	public void actionPerformed(ActionEvent e) {
		ui.getOrderComponentListener().resetToDefaultValue();
	}

	@Override
	public void setInjectUI(WebOrderDialog ui) {
		this.ui = ui;
	}

}