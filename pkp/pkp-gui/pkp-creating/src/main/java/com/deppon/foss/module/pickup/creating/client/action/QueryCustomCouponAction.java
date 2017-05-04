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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/QueryConsignerAction.java
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
package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;
import com.deppon.foss.module.pickup.creating.client.ui.editui.ConsignerPanel;
import com.deppon.foss.module.pickup.creating.client.ui.print.CustomerCouponDialog;

/**
 * 
* @Description: 优惠券查询按钮
* @author hbhk 
* @date 2015-7-6 下午3:16:57
 */
public class QueryCustomCouponAction extends AbstractButtonActionListener<WaybillEditUI> {

	private WaybillEditUI ui;

	/**
	 * 获取客户优惠券编码
	 */
	public void actionPerformed(ActionEvent e) {
		ConsignerPanel consignerPanel = ui.getConsignerPanel();
		JTextFieldValidate consignerMobile= consignerPanel.getTxtConsignerMobile();
		String phone = consignerMobile.getText();
		String customCode =  consignerPanel.getTxtDeliveryCustomerCode().getText();
		if(StringUtils.isEmpty(customCode)){
			MsgBox.showInfo("请选设置发货客户编码");
			return;
		}
		new CustomerCouponDialog(customCode, phone,ui);
	}

	@Override
	public void setIInjectUI(WaybillEditUI ui) {

		this.ui = ui;

	}

}