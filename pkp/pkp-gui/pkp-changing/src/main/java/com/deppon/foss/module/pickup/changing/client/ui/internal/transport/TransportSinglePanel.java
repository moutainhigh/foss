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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/ui/internal/transport/TransportSinglePanel.java
 * 
 * FILE NAME        	: TransportSinglePanel.java
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

import javax.swing.JPanel;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.module.mainframe.client.ui.JDTitledBorder;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 
 * 运输信息单独页面
 * @author 102246-foss-shaohongliang
 * @date 2012-12-25 上午9:57:22
 */
public class TransportSinglePanel extends JPanel implements ITransterInject{

	/**
	 *  国际化
	 */
	private static II18n i18n = I18nManager.getI18n(TransportSinglePanel.class);

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 4539776965463817441L;

	/**
	 * 
	 * TransportSinglePanel
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:57:44
	 */
	public TransportSinglePanel() {
		init();
	}

	/**
	 * 
	 * 初始化布局
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:57:36
	 */
	private void init() {
		setBorder(new JDTitledBorder(i18n.get("foss.gui.changing.transportSinglePanel.title.label")));
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,}));

	}

	/**
	 * 
	 * 注入运输信息Single面板
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:57:53
	 * @see com.deppon.foss.module.pickup.changing.client.ui.internal.transport.ITransterInject#injectTransportPanel(com.deppon.foss.module.pickup.changing.client.ui.internal.transport.TransportInfoPanel)
	 */
	@Override
	public void injectTransportPanel(TransportInfoPanel transportInfoPanel) {
		add(transportInfoPanel, "2, 1, fill, fill");
	}

}