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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/action/OpenCrmMemberViewUIAction.java
 * 
 * FILE NAME        	: OpenCrmMemberViewUIAction.java
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
package com.deppon.foss.module.pickup.common.client.action;

import org.limewire.cef.CefChromeBrowserManager;
import org.limewire.cef.ChromeBrowser;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.core.application.IApplication;
import com.deppon.foss.framework.client.core.workbench.EditorConfig;
import com.deppon.foss.framework.client.widget.browser.ui.BrowserFrame;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.mainframe.client.action.AbstractOpenUIAction;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IFossSSOLoginRemoting;

/**
 * CRM 客户360视图，单点登录访问如下页面
 *  http://192.168.13.36:8081/crm/custview/memberViewIndex.action
 * @author 102246-foss-shaohongliang
 * @date 2012-12-1 上午10:28:27
 */
public class OpenCrmMemberViewUIAction extends
		AbstractOpenUIAction<BrowserFrame> {
	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager.getI18n(OpenCrmMemberViewUIAction.class);
	/**
	 * 序列化版本
	 */
	private static final long serialVersionUID = -6309382600739332104L;

	/**
	 * gui上下文
	 */
	private IApplication application;

	/**
	 * 编辑器
	 */
	private EditorConfig editConfig;

	/**
	 * 登录服务
	 */
	private IFossSSOLoginRemoting service;

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
		//得到service crm##/custview/#!memberViewIndex.action
		service = DefaultRemoteServiceFactory.getService(IFossSSOLoginRemoting.class);
		//得到url
		String urlValue = service.getUrl("crm","custview/memberViewIndex.action");
		try {
			ChromeBrowser browser = CefChromeBrowserManager.getChromeBrowser(urlValue,false);
    		editConfig = new EditorConfig();
    		editConfig.setTitle(i18n.get("foss.gui.common.openCrmMemberViewUIAction.setTitle.label"));
    		editConfig.setPluginId("com.deppon.foss.module.pkp-common");
    		application.openUI(editConfig, browser);
    	} catch (Exception e) {
    		MsgBox.showError(i18n.get("foss.gui.common.openCrmAllocationOrderUIAction.msgBox.nullUrl"));
    	}
	}


	/**
	 * @return the application
	 */
	public IApplication getApplication() {
		return application;
	}

	/**
	 * @return the BrowserFrame
	 */
	public Class<BrowserFrame> getOpenUIType() {
		return BrowserFrame.class;
	}
	/**
	 * @return the editConfig
	 */
	public EditorConfig getEditConfig() {
		return editConfig;
	}

	/**
	 * @return the service
	 */
	public IFossSSOLoginRemoting getService() {
		return service;
	}

}