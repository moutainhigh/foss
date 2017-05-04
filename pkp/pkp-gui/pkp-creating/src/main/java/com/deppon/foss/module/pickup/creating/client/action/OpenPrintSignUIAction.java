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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/OpenPrintSignUIAction.java
 * 
 * FILE NAME        	: OpenPrintSignUIAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.core.application.IApplication;
import com.deppon.foss.framework.client.core.workbench.EditorConfig;
import com.deppon.foss.framework.client.core.workbench.IEditor;
import com.deppon.foss.module.mainframe.client.action.AbstractOpenUIAction;
import com.deppon.foss.module.pickup.common.client.utils.RepositoryCenter;
import com.deppon.foss.module.pickup.creating.client.ui.PrintSignUI;

 
/**
 * 
 * 运单OpenSalesDeptWaybillUIAction
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-17 上午11:16:43,
 * </p>
 * @author 105089-FOSS-yangtong
 * @date 2012-10-17 上午11:16:43
 * @since
 * @version
 */
public class OpenPrintSignUIAction extends
AbstractOpenUIAction<PrintSignUI>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private IApplication application;

	private EditorConfig editConfig;

	// 日志
	private static final Log log = LogFactory
			.getLog(OpenPrintSignUIAction.class);

	// 国际化
	private static final II18n i18n = I18nManager
			.getI18n(OpenPrintSignUIAction.class);
	
	
	/**
	 * 功能：setApplication
	 * 
	 * @param:
	 * @return:
	 * @since:1.6
	 */
	@Override
	public void setApplication(IApplication application) {
		this.application = application;
		RepositoryCenter.getInstance().register("application", application);
		
	}
	
	/**
	 * 
	 * 功能：openUIAction
	 * 
	 * @param:
	 * @return:
	 * @since:1.6
	 */
	@Override
	public void openUIAction() {
		editConfig = new EditorConfig();
		editConfig.setTitle(i18n.get("foss.gui.creating.submitConfirmDialog.printLabel.label"));
		editConfig.setPluginId("com.deppon.foss.module.pkp-creating");
		IEditor editor = application
				.openEditorAndRetrun(editConfig,
						"com.deppon.foss.module.pickup.creating.client.ui.PrintSignUI");

		PrintSignUI printSignUI = (PrintSignUI) editor.getComponent();
		
		log.info("open UI :com.deppon.foss.module.pickup.creating.client.ui.PrintSignUI"+editor);
		log.info(application.getWorkbench().getEditors().size());
		log.debug(printSignUI);
	}

	@Override
	public IApplication getApplication() {
		return application;
	}

	@Override
	public Class<PrintSignUI> getOpenUIType() {
		return PrintSignUI.class;
	}

}