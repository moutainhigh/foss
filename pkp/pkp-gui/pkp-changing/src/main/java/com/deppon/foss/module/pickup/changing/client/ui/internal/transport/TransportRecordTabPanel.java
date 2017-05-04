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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/ui/internal/transport/TransferTabPanel.java
 * 
 * FILE NAME        	: TransferTabPanel.java
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
package com.deppon.foss.module.pickup.changing.client.ui.internal.transport;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.module.mainframe.client.ui.JDTitledBorder;

/**
 * 
 * 转运页签面板
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-12-25 上午9:51:24
 */
public class TransportRecordTabPanel extends JTabbedPane implements ITransterInject {

	/**
	 *  国际化
	 */
	private static II18n i18n = I18nManager.getI18n(TransportRecordTabPanel.class);
	
	/**
	 * 转运信息面板
	 */
	private TransferChangedInfoPanel transferInfoPanel;

	/**
	 * 转运记录面板
	 */
	private TransferChangedRecordPanel transferRecordPanel;

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -5427348035662224847L;

	/**
	 * 托管面板
	 */
	private JPanel managedPanel;

	/**
	 * 
	 * TransferTabPanel
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:52:06
	 */
	public TransportRecordTabPanel() {
		setBorder(new JDTitledBorder(""));
		init();
	}


	/**
	 * 
	 * 布局初始化
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:51:55
	 */
	private void init() {
		managedPanel = new JPanel();
		managedPanel.setLayout(new BorderLayout(0, 0));
		transferInfoPanel = new TransferChangedInfoPanel();
		transferRecordPanel = new TransferChangedRecordPanel();
		//运输信息
		addTab(i18n.get("foss.gui.changing.transportSinglePanel.title.label"), null, managedPanel, null);
		//运输信息变更
		addTab(i18n.get("foss.gui.changing.transportInfoChangePanel.title.label"), null, transferInfoPanel, null);
		//变更记录
		addTab(i18n.get("foss.gui.changing.transportChangeRecordPanel.title.label"), null, transferRecordPanel, null);

	}

	/**
	 * 
	 * 注入转运信息面板
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:52:17
	 * @see com.deppon.foss.module.pickup.changing.client.ui.internal.transport.ITransterInject#injectTransportPanel(com.deppon.foss.module.pickup.changing.client.ui.internal.transport.TransportInfoPanel)
	 */
	public void injectTransportPanel(TransportInfoPanel transportInfoPanel) {
		managedPanel.add(transportInfoPanel, SwingUtilities.CENTER);
	}

	/**
	 * @return the transferInfoPanel
	 */
	public TransferChangedInfoPanel getTransferInfoPanel() {
		return transferInfoPanel;
	}

	/**
	 * @return the transferRecordPanel
	 */
	public TransferChangedRecordPanel getTransferRecordPanel() {
		return transferRecordPanel;
	}

}