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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/action/WaybillRfcAuthorizeEditAction.java
 * 
 * FILE NAME        	: WaybillRfcAuthorizeEditAction.java
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

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.pickup.changing.client.ui.WaybillRFCAuthorizeUI;
import com.deppon.foss.module.pickup.changing.client.ui.dialog.WaybillRfcAuthorizeDialog;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcAgentEntity;


/**
 * 
 * 修改运单审核授权数据
 * @author 102246-foss-shaohongliang
 * @date 2012-12-17 上午9:51:56
 */
public class WaybillRfcAuthorizeEditAction implements IButtonActionListener<WaybillRFCAuthorizeUI>{
	
	/**
	 * ui object
	 */
	private WaybillRFCAuthorizeUI ui;
	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager.getI18n(WaybillRfcAuthorizeEditAction.class);
	/**
	 * 
	 * 修改审核代理
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午5:26:31
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		WaybillRfcAgentEntity bean = ui.getSelectedAuthorizeBean();
		if(bean == null){
			MsgBox.showError(i18n.get("foss.gui.changing.waybillRfcAuthorizeEditAction.msgBox.nullSelectedModifyRecord"));
			return;
		}
		
		
		WaybillRfcAuthorizeDialog dialog = new WaybillRfcAuthorizeDialog(ui, bean);
		WindowUtil.centerAndShow(dialog);
	}

	/**
	 * 
	 * UI注入
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午4:48:26
	 * @see com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener#setInjectUI(java.awt.Container)
	 */
	@Override
	public void setInjectUI(WaybillRFCAuthorizeUI ui) {
		this.ui = ui;
	}

}