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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/ui/internal/MessagePanel.java
 * 
 * FILE NAME        	: MessagePanel.java
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

import java.awt.CardLayout;

import javax.swing.JPanel;

import com.deppon.foss.module.pickup.changing.client.ui.internal.message.AbortInfoPanel;
import com.deppon.foss.module.pickup.changing.client.ui.internal.message.ChangedInfoPanel;
import com.deppon.foss.module.pickup.changing.client.ui.internal.message.InvalidInfoPanel;

/**
 * 更改提示操作面板
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:102246-foss-shaohongliang,date:2012-10-17 上午9:20:30,
 * </p>
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-10-17 上午9:20:30
 * @since
 * @version
 */
public class MessagePanel extends JPanel {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 3497510607586378114L;

	/**
	 * 变更信息面板
	 */
	private ChangedInfoPanel changedInfoPanel;

	/**
	 * 中止信息面板
	 */
	private AbortInfoPanel abortInfoPanel;

	/**
	 * 作废信息面板
	 */
	private InvalidInfoPanel invalidInfoPanel;

	/**
	 * 当前布局名称
	 */
	private String currentLayoutName;

	/**
	 * 
	 * create panel
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午6:03:57
	 */
	public MessagePanel() {
		init();
	}

	/**
	 * 
	 * 布局初始化
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午6:04:04
	 */
	private void init() {
		setLayout(new CardLayout(0, 0));

		changedInfoPanel = new ChangedInfoPanel();
		add(ChangedInfoPanel.class.getName(), changedInfoPanel);

		invalidInfoPanel = new InvalidInfoPanel();
		add(InvalidInfoPanel.class.getName(), invalidInfoPanel);

		abortInfoPanel = new AbortInfoPanel();
		add(AbortInfoPanel.class.getName(), abortInfoPanel);

	}

	/**
	 * @return the changedInfoPanel
	 */
	public ChangedInfoPanel getChangedInfoPanel() {
		return changedInfoPanel;
	}

	/**
	 * @return the abortInfoPanel
	 */
	public AbortInfoPanel getAbortInfoPanel() {
		return abortInfoPanel;
	}

	/**
	 * @return the invalidInfoPanel
	 */
	public InvalidInfoPanel getInvalidInfoPanel() {
		return invalidInfoPanel;
	}

	/**
	 * @return the currentLayoutName
	 */
	public String getCurrentLayoutName() {
		return currentLayoutName;
	}

	/**
	 * @param currentLayoutName
	 *            the currentLayoutName to set
	 */
	public void setCurrentLayoutName(String currentLayoutName) {
		this.currentLayoutName = currentLayoutName;
	}

}