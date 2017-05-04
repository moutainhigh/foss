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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/action/WaybillRfcAuthorizeDeleteAction.java
 * 
 * FILE NAME        	: WaybillRfcAuthorizeDeleteAction.java
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

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.mainframe.client.utills.ExceptionHandler;
import com.deppon.foss.module.pickup.changingexp.client.service.IWaybillRfcVarificationService;
import com.deppon.foss.module.pickup.changingexp.client.service.WaybillRfcServiceFactory;
import com.deppon.foss.module.pickup.changingexp.client.ui.WaybillRFCAuthorizeUI;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcAgentEntity;


/**
 * 
 * 删除运单审核授权数据
 * @author 102246-foss-shaohongliang
 * @date 2012-12-17 上午9:51:56
 */
public class WaybillRfcAuthorizeDeleteAction implements IButtonActionListener<WaybillRFCAuthorizeUI>{
	
	/**
	 * log
	 */
	private static final Logger LOG = Logger
			.getLogger(WaybillRfcAuthorizeDeleteAction.class);
	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager.getI18n(WaybillRfcAuthorizeDeleteAction.class);
	/**
	 * ui object
	 */
	private WaybillRFCAuthorizeUI ui;
	
	/**
	 * 变更单审核受理Service
	 */
	private IWaybillRfcVarificationService rfcVarificationService = WaybillRfcServiceFactory.getWaybillRfcVarificationService();

	/**
	 * 
	 * 删除审核代理
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午5:26:17
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		WaybillRfcAgentEntity bean = ui.getSelectedAuthorizeBean();
		if(bean == null){
			MsgBox.showError(i18n.get("foss.gui.changingexp.waybillRfcAuthorizeDeleteAction.msgBox.nullSelectedDeleteRecord"));
			return;
		}
		int option = JOptionPane.showConfirmDialog(ui, i18n.get("foss.gui.changingexp.waybillRfcAuthorizeDeleteAction.confirmDialog.deleteRecord"), i18n.get("foss.gui.changingexp.waybillRFCUI.common.prompt"), JOptionPane.YES_NO_OPTION);
		if(option == JOptionPane.YES_OPTION){
			// 删除
			LOG.debug("sonar required option = "+ option);
			try {
				rfcVarificationService.deleteWayBillRfcAgent(bean);
				//刷新表格数据
				ui.refreshData();
			} catch (BusinessException be) {
				ExceptionHandler.alert(be);
			}
		}
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