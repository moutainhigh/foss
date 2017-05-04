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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/ui/dialog/MultiUserChooser.java
 * 
 * FILE NAME        	: MultiUserChooser.java
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
package com.deppon.foss.module.pickup.changingexp.client.ui.dialog;

import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTextField;

import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.pickup.changingexp.client.action.MultiUserChooserAction;
import com.deppon.foss.module.pickup.changingexp.client.ui.WaybillRFCAuthorizeUI;
import com.deppon.foss.module.pickup.common.client.ui.commonUI.TextFieldSelector;

/**
 * 
 * 本部门职称为普通员工的选择控件
 * @author 102246-foss-shaohongliang
 * @date 2012-12-27 上午11:03:04
 */
public class MultiUserChooser extends TextFieldSelector {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -8053368907102997074L;
	
	@ButtonAction(icon = PREVIEWPNG, shortcutKey = "", type = MultiUserChooserAction.class)
	protected JButton btnUserButton;

	private static final String PREVIEWPNG = "query.png";

	private JTextField txtUsername;
	
	private List<EmployeeEntity> employeeEntities;
	
	

	public MultiUserChooser(boolean editabled, WaybillRFCAuthorizeUI ui) {
		super(editabled);
		this.btnUserButton = super.btnQuery;
		this.txtUsername= super.txtField;
	}

	/**
	 * @return the employeeEntities
	 */
	@SuppressWarnings("unchecked")
	public List<EmployeeEntity> getEmployeeEntities() {
		if(employeeEntities == null){
			return Collections.EMPTY_LIST;
		}
		return employeeEntities;
	}

	
	/**
	 * @param employeeEntities the employeeEntities to set
	 */
	public void setEmployeeEntities(List<EmployeeEntity> employeeEntities) {
		this.employeeEntities = employeeEntities;
	}

	/**
	 * 
	 * 刷新控件数据
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-27 上午11:47:50
	 */
	public void refreshData() {
		//获取员工数据
		List<EmployeeEntity> employeeEntities = getEmployeeEntities();
		//拼接字符串
		StringBuffer txtUsernameStr = new StringBuffer();
		for(EmployeeEntity employeeEntity : employeeEntities){
			txtUsernameStr.append(employeeEntity.getEmpName());
			txtUsernameStr.append(",");
		}
		//给控件设置文本
		if(txtUsernameStr.length() > 0){
			txtUsername.setText(txtUsernameStr.substring(0, txtUsernameStr.length() - 1));
		}else{
			txtUsername.setText(txtUsernameStr.toString());
		}
		
	}
	

	@Override
	public JTextField getTxtField() {
		return txtUsername;
	}

	@Override
	public JButton getBtnQuery() {
		return btnQuery;
	}

	
}