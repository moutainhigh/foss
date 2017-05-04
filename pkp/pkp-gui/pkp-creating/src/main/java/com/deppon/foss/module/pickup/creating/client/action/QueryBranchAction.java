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
 * PROJECT NAME	: pkp-creating
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/QueryBranchAction.java
 * 
 * FILE NAME        	: QueryBranchAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-creating
 * PACKAGE NAME: com.deppon.foss.module.pickup.creating.client.action
 * FILE    NAME: WaybillSaveAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;

import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;
import com.deppon.foss.module.pickup.creating.client.ui.branch.PickupGoodsBranchDialog;

/**
 * （查询提货网点）
 * 
 * @author 025000-foss-helong
 * @date 2012-11-1 下午7:59:52
 */
public class QueryBranchAction implements IButtonActionListener<WaybillEditUI> {


	
	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
	
	// 主界面
	WaybillEditUI ui;

	@Override
	public void actionPerformed(ActionEvent e) {
		/*IApplication application = ui.getApplication();
		EditorConfig editConfig = new EditorConfig();
		editConfig.setTitle(i18n.get("foss.gui.creating.buttonPanel.searchBranch.label"));
		editConfig.setPluginId("com.deppon.foss.module.pkp-creating");
		application.openEditorAndRetrun(editConfig,"com.deppon.foss.module.pickup.creating.client.ui.branch.PickupGoodsBranchJPanel");
		*/
		WindowUtil.centerAndShow(new PickupGoodsBranchDialog());
	}
	

	@Override
	public void setInjectUI(WaybillEditUI ui) {
		this.ui = ui;
	}
	
}