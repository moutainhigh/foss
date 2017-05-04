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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/action/MultiUserChooserAction.java
 * 
 * FILE NAME        	: MultiUserChooserAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.changingexp.client.action;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.ListSelectionModel;

import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.pickup.changingexp.client.service.IWaybillRfcVarificationService;
import com.deppon.foss.module.pickup.changingexp.client.service.WaybillRfcServiceFactory;
import com.deppon.foss.module.pickup.changingexp.client.ui.WaybillRFCAuthorizeUI;
import com.deppon.foss.module.pickup.changingexp.client.ui.dialog.MultiUserChooserDialog;
import com.deppon.foss.module.pickup.changingexp.client.ui.dialog.WaybillRfcAuthorizeDialog;

public class MultiUserChooserAction implements IButtonActionListener<Container> {

	private Container ui;
	
	/**
	 * 变更单审核受理Service
	 */
	private IWaybillRfcVarificationService rfcVarificationService = 
			WaybillRfcServiceFactory.getWaybillRfcVarificationService();

	protected int selectionMode = ListSelectionModel.SINGLE_SELECTION;
	
	private List<EmployeeEntity> employeeEntities;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		UserEntity user = (UserEntity)SessionContext.getCurrentUser();
		//部门标杆编码
		String unifieldCode = user.getEmployee().getUnifieldCode();
		employeeEntities = rfcVarificationService.queryEmployeeByEntity(unifieldCode);
		if(ui instanceof WaybillRFCAuthorizeUI){
			WaybillRFCAuthorizeUI ui1 = (WaybillRFCAuthorizeUI)ui;
			MultiUserChooserDialog dialog = new MultiUserChooserDialog(employeeEntities,ui1.getTxtUser());
			dialog.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			WindowUtil.centerAndShow(dialog);
			
		}else if(ui instanceof WaybillRfcAuthorizeDialog){
			WaybillRfcAuthorizeDialog ui2 = (WaybillRfcAuthorizeDialog)ui;
			MultiUserChooserDialog dialog = new MultiUserChooserDialog(employeeEntities,ui2.getDelegateUserName());
			dialog.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			WindowUtil.centerAndShow(dialog);
		}
	}
	
	
	/**
	 * 
	 * 设置多选还是单选
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-27 下午6:12:56
	 */
	 public void setSelectionMode(int selectionMode) {
		 this.selectionMode = selectionMode;
	 }
	


	@Override
	public void setInjectUI(Container ui) {
			this.ui= ui;
	}

}