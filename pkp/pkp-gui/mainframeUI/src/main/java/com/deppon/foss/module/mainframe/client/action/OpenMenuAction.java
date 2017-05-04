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
 * PROJECT NAME	: pkp-gui
 * 
 * FILE PATH        	: mainframeUI/src/main/java/com/deppon/foss/module/mainframe/client/action/OpenMenuAction.java
 * 
 * FILE NAME        	: OpenMenuAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.mainframe.client.action;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.java.plugin.Plugin;
import org.limewire.cef.CefChromeBrowserManager;
import org.limewire.cef.CefCookie;
import org.limewire.cef.ChromeBrowser;

import com.deppon.foss.framework.client.component.remote.IRemoteInfo;
import com.deppon.foss.framework.client.core.application.IApplication;
import com.deppon.foss.framework.client.core.application.IApplicationAware;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.core.workbench.EditorConfig;
import com.deppon.foss.framework.client.core.workbench.IEditor;
import com.deppon.foss.framework.client.core.workbench.IPluginAware;
import com.deppon.foss.framework.client.widget.browser.ui.BrowserFrame;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.define.LocaleConst;
import com.deppon.foss.module.mainframe.client.IMainframe;
import com.deppon.foss.module.mainframe.client.MenuNodeInfo;
import com.deppon.foss.module.mainframe.client.utills.RemotingInfoUtils;
/**
 * 菜单时间触发器
 * @author 026113-foss-linwensheng
 *
 */
public class OpenMenuAction extends AbstractOpenUIAction<BrowserFrame> implements IApplicationAware,IPluginAware,IMainframe {

	public static List openMenuList = new ArrayList();
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 菜单节点信息
	 */
	MenuNodeInfo menuNodeInfo;
	/**
	 * 应用接口
	 */
	IApplication application;
	/**
	 * 插件对象
	 */
	Plugin plugin;
	/**
	 * 编辑配置
	 */
	private EditorConfig editConfig;
	/**
	 * 统一资源标识
	 */
	private String uri;
	/**
	 * 统一资源标识名称
	 */
	private String uriName;

	/**
	 * @param uriName the uriName to set
	 */
	public void setUriName(String uriName) {
		this.uriName = uriName;
	}

	/**
	 * @param uri the uri to set
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	@Override
	public void setApplication(IApplication application) {
		 this.application=application;

	}

	@Override
	public void setMenuNodeInfo(MenuNodeInfo menuNodeInfo) {
		this.menuNodeInfo=menuNodeInfo;
	}

	@Override
	public void setPlugin(Plugin plugin) {
		this.plugin=plugin;
		
	}

	@Override
	public void openUIAction() {
	    	/**
		 * 获取本地默认的语言
		 */
		String localLanguage = Locale.getDefault().getLanguage();
		/**
		 * 获取本地默认的国家
		 */
		String localCountry = Locale.getDefault().getCountry();
		
		String ip=RemotingInfoUtils.getRemotingWebInfo(IRemoteInfo.DEFAULT_NAME).getHostName()+":"+RemotingInfoUtils.getRemotingWebInfo(IRemoteInfo.DEFAULT_NAME).getPort();
		
		/**
		 * 获取FOSS URI 地址并追加语言和国家信息，将最终的字符串赋给urlValue
		 */
		String urlValue = "http://"+ip+uri.trim()
				+"?"+LocaleConst.KEY_LOCALE_LANGUAGE+"="+localLanguage+"&"+LocaleConst.KEY_LOCALE_COUNTRY+"="+localCountry;
		
		try {

			/**
			 * 创建CefCookie对象
			 */
			CefCookie cookie = new CefCookie();
			/**
			 * 设置cookie的path
			 */
			cookie.setCookiepath("/");
			/**
			 * 设置cookie的url
			 */
			cookie.setCookieurl(urlValue);
			/**
			 * 设置cookie的value
			 */
			cookie.setCookievalue(SessionContext.KEY_TOKEN+"="+(String)SessionContext.get(SessionContext.KEY_TOKEN));

			/**
			 * 创建ChromeBrowser对象并获取ChromeBrowser的实例
			 */
			ChromeBrowser browser = CefChromeBrowserManager.getChromeBrowser(urlValue,false,cookie);
			/**
			 * 修改默认JS处理行为
			 */
			browser.setCefV8Handler(new OpenMenuHandler(application, browser, "GUI_Proxy", "addTab"));
			/**
			 * 创建EditorConfig对象editConfig
			 */
			editConfig = new EditorConfig();
			/**
			 * 设置editConfig标题
			 */
			editConfig.setTitle(uriName);
			/**
			 * 设置editConfig插件ID
			 */
			editConfig.setPluginId("com.deppon.foss.module.pkp-common");
			/**
			 * 调用打开界面方法openUI
			 */
			IEditor editor = application.openUI(editConfig, browser);
			
			openMenuList.add(editor);
		} catch (Exception e) {
			MsgBox.showError("未找到相应页面");
		}	
	}

	@Override
	public IApplication getApplication() {
		// TODO Auto-generated method stub
		return application;	//返回application
	}

	@Override
	public Class<BrowserFrame> getOpenUIType() {
		// TODO Auto-generated method stub
		return BrowserFrame.class;	//返回BrowserFrame.class
	}
	
	


}