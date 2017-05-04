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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/NewAddAction.java
 * 
 * FILE NAME        	: NewAddAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;


import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.core.application.IApplication;
import com.deppon.foss.framework.client.core.workbench.EditorConfig;
import com.deppon.foss.framework.client.core.workbench.IEditor;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;

/**
 * 
 * 运单NewAddAction
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-17 上午11:16:43,
 * </p>
 * @author 105089-FOSS-yangtong
 * @date 2012-10-17 上午11:16:43
 * @since
 * @version
 */
public class NewAddAction extends AbstractButtonActionListener<WaybillEditUI> {

	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();

	WaybillEditUI ui;

	/**
	 * 国际化
	 */
	private static II18n i18n = I18nManager.getI18n(NewAddAction.class);
	
	/**
	 * 
	 * <p>
	 * 运单暂存
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-16 下午02:46:47
	 * @param e
	 * @see com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		IApplication application = ui.getApplication();
		IEditor editor = ui.getEditor();
		editor.close();
		
		if(WaybillConstants.WAYBILL_FOCUS.equals(ui.getWaybillType()))
		{
			openFocusWaybill(application);
		}else if(WaybillConstants.WAYBILL_SALE_DEPARTMENT.equals(ui.getWaybillType()))
		{
			openSalesDepartmentWaybill(application);
		}
			

	}
	
	/**
	 * 
	 * 打开集中开单
	 * @author 025000-FOSS-helong
	 * @date 2012-12-12 下午07:19:28
	 */
	private void openFocusWaybill(IApplication application)
	{
		EditorConfig editConfig = new EditorConfig();
		editConfig.setTitle(i18n.get("foss.gui.creating.buttonPanel.waybillFocus.label"));
		editConfig.setPluginId("com.deppon.foss.module.pkp-creating");
		IEditor editor = application
				.openEditorAndRetrun(editConfig,
						"com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI");
		WaybillEditUI waybillEditUI = (WaybillEditUI) editor.getComponent();
		waybillEditUI.setEditor(editor);
		
		//营业部开单
		waybillEditUI.setWaybillType(WaybillConstants.WAYBILL_FOCUS);
		waybillEditUI.openUI();
	}
	
	
	/**
	 * 
	 * 打开营业部开单
	 * @author 025000-FOSS-helong
	 * @date 2012-12-12 下午07:20:02
	 */
	private void openSalesDepartmentWaybill(IApplication application)
	{
		EditorConfig editConfig = new EditorConfig();
		editConfig.setTitle(i18n.get("foss.gui.creating.buttonPanel.waybillSaleDepartment.label"));
		editConfig.setPluginId("com.deppon.foss.module.pkp-creating");
		IEditor editor = application
				.openEditorAndRetrun(editConfig,
						"com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI");
		WaybillEditUI waybillEditUI = (WaybillEditUI) editor.getComponent();
		waybillEditUI.setEditor(editor);
		
		//营业部开单
		waybillEditUI.setWaybillType(WaybillConstants.WAYBILL_SALE_DEPARTMENT);
		waybillEditUI.openUI();
	}
	
	/**
	 * setIInjectUI
	 */
	@Override
	public void setIInjectUI(WaybillEditUI ui) {
		this.ui = ui;
	}

}