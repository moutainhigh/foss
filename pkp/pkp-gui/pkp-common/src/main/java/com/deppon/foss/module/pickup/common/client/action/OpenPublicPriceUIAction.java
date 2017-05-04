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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/action/OpenPublicPriceUIAction.java
 * 
 * FILE NAME        	: OpenPublicPriceUIAction.java
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

import java.util.Locale;

import org.limewire.cef.CefChromeBrowserManager;
import org.limewire.cef.CefCookie;
import org.limewire.cef.ChromeBrowser;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.core.application.IApplication;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.core.workbench.EditorConfig;
import com.deppon.foss.framework.client.widget.browser.ui.BrowserFrame;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.define.LocaleConst;
import com.deppon.foss.module.mainframe.client.action.AbstractOpenUIAction;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;

/**
 * 公布价查询，单点登录访问如下页面
 * http://localhost:8080/foss/login/index.action#!pricing/indexPublishPrice
 * .action
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-12-1 上午10:28:27
 */
public class OpenPublicPriceUIAction extends AbstractOpenUIAction<BrowserFrame>{
	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager.getI18n(OpenPublicPriceUIAction.class);
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
		// 公布价查询页面地址
		String localLanguage = Locale.getDefault().getLanguage();
		String localCountry = Locale.getDefault().getCountry();
		String urlValue = WaybillConstants.PRICE_SEARCH_URL
				+"?"+LocaleConst.KEY_LOCALE_LANGUAGE+"="+localLanguage+"&"+LocaleConst.KEY_LOCALE_COUNTRY+"="+localCountry;
		
		try {
			
			CefCookie cookie = new CefCookie();
			cookie.setCookiepath("/");
			cookie.setCookieurl(urlValue);
			cookie.setCookievalue(SessionContext.KEY_TOKEN+"="+(String)SessionContext.get(SessionContext.KEY_TOKEN));
			ChromeBrowser browser = CefChromeBrowserManager.getChromeBrowser(urlValue,false,cookie);
			editConfig = new EditorConfig();
			editConfig.setTitle(i18n.get("foss.gui.common.openPublicPriceUIAction.setTitle.label"));
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

}