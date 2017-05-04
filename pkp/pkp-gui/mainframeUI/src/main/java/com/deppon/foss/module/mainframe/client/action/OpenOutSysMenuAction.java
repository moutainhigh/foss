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


import org.java.plugin.Plugin;
import org.limewire.cef.CefChromeBrowserManager;
import org.limewire.cef.ChromeBrowser;

import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.core.application.IApplication;
import com.deppon.foss.framework.client.core.application.IApplicationAware;
import com.deppon.foss.framework.client.core.workbench.EditorConfig;
import com.deppon.foss.framework.client.core.workbench.IPluginAware;
import com.deppon.foss.framework.client.widget.browser.ui.BrowserFrame;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.mainframe.client.IMainframe;
import com.deppon.foss.module.mainframe.client.MenuNodeInfo;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IFossSSOLoginRemoting;
/**
 * 菜单时间触发器
 * @author 026113-foss-linwensheng
 *
 */
public class OpenOutSysMenuAction extends AbstractOpenUIAction<BrowserFrame> implements IApplicationAware,IPluginAware,IMainframe {

	
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
	 * 系统名称
	 */
	
	private String sysName;
	
	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

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
		//得到service
		IFossSSOLoginRemoting	service = DefaultRemoteServiceFactory.getService(IFossSSOLoginRemoting.class);
				//得到url
				String urlValue = service.getUrl(sysName,uri);
				try {
					ChromeBrowser browser = CefChromeBrowserManager.getChromeBrowser(urlValue,false);
		    		editConfig = new EditorConfig();
		    		editConfig.setTitle(uriName);
		    		editConfig.setPluginId("com.deppon.foss.module.mainframe");
		    		application.openUI(editConfig, browser);
		    	} catch (Exception e) {
		    		MsgBox.showError("无法登陆！");
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