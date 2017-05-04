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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/ui/internal/DetailCanvasPanel.java
 * 
 * FILE NAME        	: DetailCanvasPanel.java
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
package com.deppon.foss.module.pickup.changingexp.client.ui.internal;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.module.pickup.changingexp.client.action.DetailCanvasPanelAction;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 详细计价信息
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:102246-foss-shaohongliang,date:2012-10-17
 * 下午4:57:47,content:
 * </p>
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-10-17 下午4:57:47
 * @since
 * @version
 */
public class DetailCanvasPanel extends JPanel {

	/**
	 * 300
	 */
	private static final int THREEHUNDRED = 300;

	/**
	 * 40
	 */
	private static final int FORTY = 40;

	/**
	 * POSTION IN WINDOW
	 */
	private static final String POSITION = "1, 2";

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -5147804144810099755L;

	/**
	 * 国际化
	 */
	private static II18n i18n = I18nManager.getI18n(DetailCanvasPanel.class);

	@ButtonAction(type = DetailCanvasPanelAction.class, icon = "buttonClose.png", shortcutKey = "F12")
	private JButton btnDetail = new JButton(i18n.get("pickup.changingexp.htmlDetailValuation"));

	/**
	 * 扩展面板
	 */
	private ExtendPanel extendPanel;

	public DetailCanvasPanel() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,}));
		btnDetail.setPreferredSize(new Dimension(FORTY, THREEHUNDRED));
		btnDetail.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnDetail.setHorizontalTextPosition(SwingConstants.CENTER);
		
		extendPanel = new ExtendPanel();
		JPanel tempPanel = new JPanel();
		tempPanel.setMaximumSize(new Dimension(0, 0));
		tempPanel.setPreferredSize(new Dimension(0, 0));
		tempPanel.add(extendPanel);
		tempPanel.setVisible(false);
		add(tempPanel, POSITION);
		
		add(btnDetail, POSITION);
		
	}


	/**
	 * @return the extendPanel
	 */
	public ExtendPanel getExtendPanel() {
		return extendPanel;
	}

	
}