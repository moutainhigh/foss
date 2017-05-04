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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/action/OpenWaybillRFCDealUIAction.java
 * 
 * FILE NAME        	: OpenWaybillRFCDealUIAction.java
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

package com.deppon.foss.module.pickup.changing.client.action;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.core.application.IApplication;
import com.deppon.foss.framework.client.core.workbench.EditorConfig;
import com.deppon.foss.module.mainframe.client.action.AbstractOpenUIAction;
import com.deppon.foss.module.pickup.changing.client.ui.WaybillRFCDealUI;

/**
 * 
 * <p>
 * 打开更改单受理UI action
 * </p>
 * @title OpenWaybillRFCDealUIAction.java
 * @package com.deppon.foss.module.pickup.changing.client.action 
 * @author suyujun
 * @version 0.1 2012-12-18
 */
public class OpenWaybillRFCDealUIAction extends
		AbstractOpenUIAction<WaybillRFCDealUI> {


	// 日志
	private static final Log LOG = LogFactory
			.getLog(OpenWaybillRFCDealUIAction.class);
	
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -6309382600739332106L;
	
	/**
	 * 国际化
	 */
	private static II18n i18n = I18nManager
			.getI18n(OpenWaybillRFCDealUIAction.class);

	/**
	 * APPLICATION CONTEXT
	 */
	private transient IApplication application;

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
		EditorConfig editConfig = new EditorConfig();
		editConfig.setTitle(i18n.get("pickup.changing.waybillRfcDeal"));//??
		editConfig.setPluginId("com.deppon.foss.module.pkp-changing");
		application
				.openEditorAndRetrun(editConfig,
						"com.deppon.foss.module.pickup.changing.client.ui.WaybillRFCDealUI");
		LOG.info("open UI :com.deppon.foss.module.waybillrfc.client.ui.WaybillRFCDealUI");
		LOG.info(application.getWorkbench().getEditors().size());
	}

	/**
	 * 
	 * 获取Application实例
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午8:19:52
	 * @see com.deppon.foss.module.mainframe.client.action.AbstractOpenUIAction#getApplication()
	 */
	@Override
	public IApplication getApplication() {
		return application;
	}

	/**
	 * 
	 * 获取页面类型
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午8:20:13
	 * @see com.deppon.foss.module.mainframe.client.action.AbstractOpenUIAction#getOpenUIType()
	 */
	@Override
	public Class<WaybillRFCDealUI> getOpenUIType() {
		return WaybillRFCDealUI.class;
	}

}