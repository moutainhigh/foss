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
 * FILE PATH        	: appHome/src/main/java/org/limewire/cef/MainHandler.java
 * 
 * FILE NAME        	: MainHandler.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package org.limewire.cef;

/**
 * 调用CEF jni接口参考列子程序
 * @author niujian
 */
public class MainHandler implements CefHandler {
	private MainFrame main_frame;
	private long main_handle = 0;

	public MainHandler(MainFrame main_frame) {
    	this.main_frame = main_frame;
    }
	
	@Override
	public void handleAfterCreated(CefBrowser browser) {
		if(!browser.isPopup())
			main_handle = browser.getWindowHandle();
	}
	
	@Override
	public void handleAddressChange(CefBrowser browser, CefFrame frame, String url) {
		if(browser.getWindowHandle() == main_handle && frame.isMain())
			main_frame.setAddress(url);
	}

	@Override
	public void handleTitleChange(CefBrowser browser, String title) {
		long handle = browser.getWindowHandle();
		if(handle == main_handle)
			main_frame.setTitle(title);
	}

	@Override
	public void handleJSBinding(CefBrowser browser, CefFrame frame, CefV8Value object) {
		// Add a "window.test" object.
		//CefV8Value test_obj = CefContext.createV8Object(null);
		//object.setValue("GIS_Results", test_obj);
		
		// Add a "showMessage" function to the "window.test" object.
		//test_obj.setValue("returnResults",
		//CefContext.createV8Function("returnResults", new MainV8Handler(main_frame)));
		
		CefContext.createV8Function("returnResults1",  new MainV8Handler(main_frame));
	}
}