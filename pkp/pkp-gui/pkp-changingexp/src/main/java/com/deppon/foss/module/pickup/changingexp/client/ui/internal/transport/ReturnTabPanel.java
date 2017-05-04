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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/ui/internal/transport/ReturnTabPanel.java
 * 
 * FILE NAME        	: ReturnTabPanel.java
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
package com.deppon.foss.module.pickup.changingexp.client.ui.internal.transport;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.module.mainframe.client.ui.JDTitledBorder;

/**
 * 
 * 返货页签
 * @author 102246-foss-shaohongliang
 * @date 2012-12-25 上午9:41:34
 */
public class ReturnTabPanel extends JTabbedPane implements ITransterInject{

	/**
	 *  国际化
	 */
	private static II18n i18n = I18nManager.getI18n(ReturnTabPanel.class);

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 2705843848583802769L;

	/**
	 * 托管面板
	 */
	private JPanel managedPanel;
	
	/**
	 * 返货面板
	 */
	private	ReturnInfoPanel returnInfoPanel;
	
	/**
	 * 返货记录面板
	 */
	private ReturnRecordPanel returnRecordPanel;

	/**
	 * 
	 * ReturnTabPanel
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:42:23
	 */
	public ReturnTabPanel() {
		setBorder(new JDTitledBorder(""));
		init();
	}

	/**
	 * 
	 * 初始化布局
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:42:10
	 */
	private void init() {
		managedPanel = new JPanel();
		managedPanel.setLayout(new BorderLayout(0, 0));
		
		returnInfoPanel = new ReturnInfoPanel();
		
		returnRecordPanel = new ReturnRecordPanel();

		//运输信息
		addTab(i18n.get("foss.gui.changingexp.transportSinglePanel.title.label"), null, managedPanel, null);
		//返货信息
		addTab(i18n.get("foss.gui.changingexp.returnInfoPanel.title.label"), null, returnInfoPanel, null);
		//返货记录
		addTab(i18n.get("foss.gui.changingexp.returnRecordPanel.title.label"), null, returnRecordPanel, null);
	}

	/**
	 * 
	 * 注入返货页面
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:42:35
	 * @see com.deppon.foss.module.pickup.changingexp.client.ui.internal.transport.ITransterInject#injectTransportPanel(com.deppon.foss.module.pickup.changingexp.client.ui.internal.transport.TransportInfoPanel)
	 */
	public void injectTransportPanel(TransportInfoPanel transportInfoPanel) {
		managedPanel.add(transportInfoPanel, SwingUtilities.CENTER);
	}

	
	/**
	 * @return the returnInfoPanel
	 */
	public ReturnInfoPanel getReturnInfoPanel() {
		return returnInfoPanel;
	}

	
	/**
	 * @return the returnRecordPanel
	 */
	public ReturnRecordPanel getReturnRecordPanel() {
		return returnRecordPanel;
	}


}