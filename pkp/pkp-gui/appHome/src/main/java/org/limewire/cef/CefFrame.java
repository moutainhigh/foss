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
 * FILE PATH        	: appHome/src/main/java/org/limewire/cef/CefFrame.java
 * 
 * FILE NAME        	: CefFrame.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package org.limewire.cef;

/**
 * Cef frame 调用接口类
 * @author niujian
 *
 */
public interface CefFrame {
	/**
	 * Load the specified url.
	 */
	public void loadURL(String url);
	
	/**
	 * Execute JavaScript.
	 * @param jsCode The JavaScript code to execute.
	 * @param scriptUrl The URL where the script in question can be found, if any.
	 * @param startLine The base line number to use for error reporting.
	 */
	public void executeJavaScript(String jsCode, String scriptUrl, int startLine);
	
	/**
	 * Returns true if this is the main frame.
	 */
	public boolean isMain();
	
	/**
	 * Returns true if this is the focused frame.
	 */
	public boolean isFocused();
}