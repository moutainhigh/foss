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
 * FILE PATH        	: appHome/src/main/java/org/limewire/cef/CefBrowser.java
 * 
 * FILE NAME        	: CefBrowser.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package org.limewire.cef;

import java.awt.Canvas;

/**
 * CEF browser 调用接口类
 * @author niujian
 *
 */
public abstract class CefBrowser extends Canvas {
	

	private static final long serialVersionUID = 4681497178131370287L;
	
	/**
	 * Go back.
	 */
    public abstract void goBack();
    
    /**
     * Go forward.
     */
	public abstract void goForward();
	
	/**
	 * reload.
	 */
    public abstract void reload();
    
	/**
	 * stop.
	 */
    public abstract void stop();
	
	/**
	 * show dev tools.
	 */
    public abstract void showDevTools();
    
	/**
	 * Returns the window handle for this browser.
	 */
	public abstract long getWindowHandle();
	
	/**
	 * Returns true if the window is a popup window.
	 */
	public abstract boolean isPopup();
	
	/**
	 * Returns the main (top-level) frame for the browser window.
	 */
	public abstract CefFrame getMainFrame();
	
	/**
	 * Returns the focused frame for the browser window.
	 */
	public abstract CefFrame getFocusedFrame();
	
	public abstract void loadByUrl(String url );
	
	public abstract void setForseToNPaint(boolean forse);
	
	public abstract void setCefCookie(CefCookie cefCookie);
	public abstract void setAcceptLanguage(String acceptLanguage);

	public abstract void setCefV8Handler(CefV8Handler cefV8Handler);
}