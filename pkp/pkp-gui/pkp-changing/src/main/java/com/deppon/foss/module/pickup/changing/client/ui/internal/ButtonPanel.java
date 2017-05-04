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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/ui/internal/ButtonPanel.java
 * 
 * FILE NAME        	: ButtonPanel.java
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
package com.deppon.foss.module.pickup.changing.client.ui.internal;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.module.pickup.changing.client.action.ChooseModifyWaybillPrintTypeAction;
import com.deppon.foss.module.pickup.changing.client.action.GISDialogChangeAction;
import com.deppon.foss.module.pickup.changing.client.action.QueryMemberDialogChangeAction;
import com.deppon.foss.module.pickup.changing.client.action.RfcInfoAction;
import com.deppon.foss.module.pickup.common.client.action.QueryPublicPriceLocalUIAction;
import com.deppon.foss.module.pickup.waybill.shared.define.IconConstants;

/**
 * 运单更改按钮面板
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:102246-foss-shaohongliang,date:2012-10-17
 * 上午9:23:53,content:
 * </p>
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-10-17 上午9:23:53
 * @since
 * @version
 */
public class ButtonPanel extends JPanel {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -3336526732144273181L;

	/**
	 * 国际化
	 */
	private static II18n i18n = I18nManager.getI18n(ButtonPanel.class);

	/**
	 * 提交确认按键
	 */
	@ButtonAction(icon = IconConstants.SUBMIT, shortcutKey = "F3", type = RfcInfoAction.class)
	private JButton btnSubmit = new JButton(i18n.get("pickup.changing.commit"));

	/**
	 * 打印按键
	 */
	@ButtonAction(icon = IconConstants.PRINT, shortcutKey = "", type = ChooseModifyWaybillPrintTypeAction.class)
	private JButton btnPrint = new JButton(i18n.get("pickup.changing.print"));
	
	
	/**
	 * 查询客户
	 */
	@ButtonAction(icon = IconConstants.SEARCHMEMBER, shortcutKey = "", type = QueryMemberDialogChangeAction.class)
	private JButton btnSearchMember = new JButton(i18n.get("foss.gui.changing.ButtonPanel.queryMember"));

	/**
	 * 查询网点
	 */
	@ButtonAction(icon = IconConstants.SEARCHBRANCH, shortcutKey = "F6", type = GISDialogChangeAction.class)
	private JButton btnSearchBranch = new JButton(i18n.get("foss.gui.changing.ButtonPanel.queryStation"));



	/**
	 * 公布价查询
	 */
	@ButtonAction(icon = IconConstants.PUBLIC, shortcutKey = "F8", type = QueryPublicPriceLocalUIAction.class)
	private JButton btnSearchPrice = new JButton(i18n.get("foss.gui.changing.ButtonPanel.queryPulicCharge"));
	
	/**
	 * 
	 * 初始化
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:16:09
	 */
	public ButtonPanel() {
		init();
	}
	
	/**
	 * 
	 * 界面初始化
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:16:56
	 * @see
	 */
	private void init() {
		setLayout(new FlowLayout(FlowLayout.LEFT, NumberConstants.NUMBER_5, NumberConstants.NUMBER_5));
		
		add(btnSubmit);

		add(btnPrint);

		add(btnSearchMember);// 查找会员
		add(btnSearchBranch);// 查询网点
		// 电子地图
		add(btnSearchPrice);// 公布价查询
		
	}


	/**
	 * 
	 * 提交确认按键get方法
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:17:03
	 * @return
	 * @see
	 */
	public JButton getBtnSubmit() {
		return btnSubmit;
	}


	/**
	 * 
	 * 打印按键get方法
	 * @author foss-gengzhe
	 * @date 2012-12-25 上午10:17:06
	 * @return
	 * @see
	 */
	public JButton getBtnPrint() {
		return btnPrint;
	}

	/**
	 * @return the btnSearchMember
	 */
	public JButton getBtnSearchMember() {
		return btnSearchMember;
	}

	/**
	 * @return the btnSearchBranch
	 */
	public JButton getBtnSearchBranch() {
		return btnSearchBranch;
	}

	/**
	 * @return the btnSearchPrice
	 */
	public JButton getBtnSearchPrice() {
		return btnSearchPrice;
	}


}