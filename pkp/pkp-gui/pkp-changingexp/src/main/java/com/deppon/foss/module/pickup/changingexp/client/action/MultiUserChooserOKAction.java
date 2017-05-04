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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/action/MultiUserChooserOKAction.java
 * 
 * FILE NAME        	: MultiUserChooserOKAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.changingexp.client.action;

import java.awt.event.ActionEvent;
import java.util.List;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.pickup.changingexp.client.ui.dialog.MultiUserChooser;
import com.deppon.foss.module.pickup.changingexp.client.ui.dialog.MultiUserChooserDialog;

/**
 * 
 * 人员选择后确认Action
 * @author 102246-foss-shaohongliang
 * @date 2012-12-27 上午11:37:13
 */
public class MultiUserChooserOKAction implements IButtonActionListener<MultiUserChooserDialog>{
	
	/**
	 * log
	 */
	private static final Logger LOG = Logger
			.getLogger(MultiUserChooserOKAction.class);
	
	/**
	 * ui object
	 */
	private MultiUserChooserDialog ui;
	
	/**
	 * 
	 * 提交审核代理
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午5:26:06
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		LOG.debug(ui);//sonar use it
		List<EmployeeEntity> employeeEntities = ui.getSelectedEmployees();
		MultiUserChooser chooser = ui.getMultiUserChooser();
		chooser.setEmployeeEntities(employeeEntities);
		chooser.refreshData();
		ui.dispose();
	}

	/**
	 * 
	 * UI注入
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午4:48:26
	 * @see com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener#setInjectUI(java.awt.Container)
	 */
	@Override
	public void setInjectUI(MultiUserChooserDialog ui) {
		this.ui = ui;
	}

}