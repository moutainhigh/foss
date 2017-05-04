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
 * FILE PATH        	: appHome/src/main/java/org/limewire/cef/CefHandler.java
 * 
 * FILE NAME        	: CefHandler.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package org.limewire.cef;

/**
 * cef 调用处理类
 * @author niujian
 *
 */
public interface CefHandler {
	/**
	 * Handle creation of a new browser window.
	 * @param browser The browser generating the event.
	 */
	public void handleAfterCreated(CefBrowser browser);
	
	/**
	 * Handle address changes.
	 * @param browser The browser generating the event.
	 * @param url The new address.
	 */
	public void handleAddressChange(CefBrowser browser, CefFrame frame, String url);
	
	/**
	 * Handle title changes.
	 * @param browser The browser generating the event.
	 * @param title The new title.
	 */
	public void handleTitleChange(CefBrowser browser, String title);
	
	/**
	 * Event called for adding values to a frame's JavaScript 'window' object.
	 * @param browser The browser generating the event.
	 * @param object The 'window' object.
	 */
	public void handleJSBinding(CefBrowser browser, CefFrame frame, CefV8Value object);
}