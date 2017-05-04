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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/ui/internal/message/InvalidInfoPanel.java
 * 
 * FILE NAME        	: InvalidInfoPanel.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.changing.client.ui.internal.message;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
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
 * 作废信息
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-10-29 上午9:54:05
 */
public class InvalidInfoPanel extends JPanel {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -2672934744964661352L;

	/**
	 *  国际化
	 */
	private static final II18n i18n = I18nManager.getI18n(InvalidInfoPanel.class);

	private static final int TWELVE = 12;

	/**
	 * 作废人
	 */
	private JLabel lblOperateUser;
	
	/**
	 * 作废时间
	 */
	private JLabel lblOperateTime;

	/**
	 * 
	 * 构造作废面板
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:34:46
	 */
	public InvalidInfoPanel() {
		setBorder(new JDTitledBorder(i18n.get("foss.gui.changing.invalidInfoPanel.title")));
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));

		//作废人
		JLabel lblNewLabel = new JLabel(i18n.get("foss.gui.changing.invalidInfoPanel.operateUser.label")+"：");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("宋体", Font.BOLD, TWELVE));
		add(lblNewLabel, "2, 1");

		lblOperateUser = new JLabel();
		lblOperateUser.setFont(new Font("宋体", Font.BOLD, TWELVE));
		add(lblOperateUser, "4, 1");

		//作废时间
		JLabel label1 = new JLabel(i18n.get("foss.gui.changing.invalidInfoPanel.operateTime.label")+"：");
		label1.setFont(new Font("宋体", Font.BOLD, TWELVE));
		label1.setForeground(Color.RED);
		add(label1, "2, 3");

		lblOperateTime = new JLabel();
		lblOperateTime.setFont(new Font("宋体", Font.BOLD, TWELVE	));
		add(lblOperateTime, "4, 3");
	}

	
	/**
	 * @return the lblOperateUser
	 */
	public JLabel getLblOperateUser() {
		return lblOperateUser;
	}

	
	/**
	 * @return the lblOperateTime
	 */
	public JLabel getLblOperateTime() {
		return lblOperateTime;
	}


	
}