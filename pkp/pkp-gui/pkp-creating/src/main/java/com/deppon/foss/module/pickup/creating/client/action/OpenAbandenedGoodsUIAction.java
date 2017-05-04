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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/OpenAbandenedGoodsUIAction.java
 * 
 * FILE NAME        	: OpenAbandenedGoodsUIAction.java
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
import com.deppon.foss.module.mainframe.client.action.AbstractOpenUIAction;
import com.deppon.foss.module.pickup.common.client.utils.RepositoryCenter;
import com.deppon.foss.module.pickup.creating.client.ui.AbandonedGoodsHandleUI;


/**
 * 
 * 弃货处理
 * @author 025000-FOSS-helong
 * @date 2012-12-11 上午10:52:22
 */
public class OpenAbandenedGoodsUIAction extends
		AbstractOpenUIAction<AbandonedGoodsHandleUI>{
	private static final long serialVersionUID = 8270945595530862470L;

	private IApplication application;

	private EditorConfig editConfig;
	
	// 日志
	private static final Log log = LogFactory
			.getLog(OpenAbandenedGoodsUIAction.class);

	/**
	 * 国际化
	 */
	private static II18n i18n = I18nManager.getI18n(OpenAbandenedGoodsUIAction.class);
	
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
	 * 营业部开单界面
	 * @author 025000-FOSS-helong
	 * @date 2012-12-11 上午10:51:59
	 * @see com.deppon.foss.module.mainframe.client.action.AbstractOpenUIAction#openUIAction()
	 */
	public void openUIAction() {
		editConfig = new EditorConfig();
		editConfig.setTitle(i18n.get("foss.gui.creating.OpenAbandenedGoodsUIAction.title"));

		editConfig.setPluginId("com.deppon.foss.module.pkp-creating");
		application
				.openEditorAndRetrun(editConfig,
						"com.deppon.foss.module.pickup.creating.client.ui.AbandonedGoodsHandleUI");

		log.info("com.deppon.foss.module.pickup.creating.client.ui.AbandonedGoodsHandleUI");
		log.info(application.getWorkbench().getEditors().size());
	}

	@Override
	public IApplication getApplication() {
		return application;
	}

	@Override
	public Class<AbandonedGoodsHandleUI> getOpenUIType() {
		return AbandonedGoodsHandleUI.class;
	}

}