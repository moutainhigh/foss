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
 * FILE PATH        	: appHome/src/main/java/org/limewire/cef/CefBrowser_N.java
 * 
 * FILE NAME        	: CefBrowser_N.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package org.limewire.cef;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 应用jni 调用CEF框架的 CEF browser 接口(C++)
 * @author niujian
 *
 */
class CefBrowser_N extends CefBrowser {
	
	private static final long serialVersionUID = 8047092170602936889L;
	private static final Log log = LogFactory.getLog(CefBrowser_N.class);
	
	// The value of these members will be retrieved when the CEF browser
	// is actually created.
	private String load_url = null;
	private CefV8Handler v8handler = null;
	private String v8handlername = null;
		
	private CefCookie cefcookie = null;
    private String acceptlanguage = "zh-CN";
	
	// Used internally to track if the browser has been created.
    private boolean N_CefCreated = false;
    
    // Used internally to store a pointer to the CEF object.
    private long N_CefHandle = 0;
	
    // Constructor used for already existing browser instances.
    CefBrowser_N() {
    	N_CefCreated = true;
    }
    
    // Constructor used for pending browser instances.
	CefBrowser_N(String url,CefV8Handler v8handler) {
		this.load_url = url;
    	this.v8handler = v8handler;
    }
    
	CefBrowser_N(String url,CefV8Handler v8handler, CefCookie cefCookie) {
		this.load_url = url;
    	this.v8handler = v8handler;
    	this.cefcookie = cefCookie;
    }
	

	@Override
	public void setCefV8Handler(CefV8Handler cefV8Handler) {
		this.v8handler = cefV8Handler;
	}
	
    @Override
	protected void finalize() throws Throwable
    {
    	try {
        	N_Destroy();
        } catch (UnsatisfiedLinkError ule) {
        	log.error(ule);
        }
    	super.finalize();
    }
    
    @Override
	public void goBack() {
        try {
        	N_GoBack();
        } catch (UnsatisfiedLinkError ule) {
        	log.error(ule);
        }
	}

    @Override
	public void goForward() {
        try {
        	N_GoForward();
        } catch (UnsatisfiedLinkError ule) {
        	log.error(ule);
        }
	}
    
    @Override
    public long getWindowHandle() {
    	try {
        	return N_GetWindowHandle();
        } catch (UnsatisfiedLinkError ule) {
        	log.error(ule);
        }
        return 0;
    }
    
    @Override
    public boolean isPopup() {
    	try {
        	return N_IsPopup();
        } catch (UnsatisfiedLinkError ule) {
        	log.error(ule);
        }
        return false;
    }

    @Override
	public CefFrame getMainFrame() {
        try {
        	return N_GetMainFrame();
        } catch (UnsatisfiedLinkError ule) {
        	log.error(ule);
        } catch (Exception e) {
        	log.error(e);
		}
		return null;
	}
    
    @Override
	public CefFrame getFocusedFrame() {
    	try {
        	return N_GetFocusedFrame();
        } catch (UnsatisfiedLinkError ule) {
        	log.error(ule);
        }
        return null;
	}
    
    class MyCefV8Handler implements CefV8Handler {
    	private String v8objname;
    	private String v8objfuncname;
    	
    	MyCefV8Handler(String v8objname,String v8objfuncname ){
    		this.v8objname = v8objname;
    		this.v8objfuncname = v8objfuncname;
    	}
    	
    	@Override
    	public boolean executeFunction(String name, CefV8FunctionParams params) {
    		
    		String paramString = params.getArguments()[0].getStringValue();
    		
    		System.out.println("my test func: "+ paramString);
    		return false;
    	}
    }
    
    private boolean forseToNpaint = true;
    @Override
	public void paint(Graphics g) {
        try {
        	if(forseToNpaint){
        		forseToNpaint = false;
        		N_Paint(getWidth(), getHeight());
        	}
        } catch (UnsatisfiedLinkError ule) {
        	log.error(ule);
        } catch (Exception e) {
        	log.error(e);
		}
	}
	
    @Override
    public void setForseToNPaint(boolean forse) {
    	this.forseToNpaint = forse;
    }
    
    @Override
	public void setBounds(int x, int y, int width, int height) {
		try {
        	N_SetWindowSize(width, height);
        } catch (UnsatisfiedLinkError ule) {
        	log.error(ule);
        }
		super.setBounds(x, y, width, height);
	}
	
    @Override
	public void setBounds(Rectangle r) {
		setBounds(r.x, r.y, r.width, r.height);
	}
	
    @Override
	public void setSize(int width, int height) {
		try {
        	N_SetWindowSize(width, height);
        } catch (UnsatisfiedLinkError ule) {
        	log.error(ule);
        }
		super.setSize(width, height);
	}
	
    @Override
	public void setSize(Dimension d) {
		setSize(d.width, d.height);
	}

    @Override
    public void setAcceptLanguage(String acceptlanguage) {
    	this.acceptlanguage = acceptlanguage;
    }
    
    @Override
    public void loadByUrl(String url) {
    	this.load_url = url;
    	N_LoadByUrl();
    }
    
    @Override
    public void setCefCookie(CefCookie cefCookie) {
    	this.cefcookie = cefCookie;
    	N_SetCookie();
    }
    
    @Override
    public void reload() {
    	N_Reload();
    }
    
    @Override
    public void stop() {
    	N_Stop();
    }
    
    @Override
    public void showDevTools() {
    	N_ShowDevTools();
    }
    
	private final native void N_Destroy();
    private final native void N_GoBack();
    private final native void N_GoForward();
    private final native void N_Reload();
    private final native void N_Stop();
    private final native void N_ShowDevTools();
    private final native long N_GetWindowHandle();
    private final native boolean N_IsPopup();
    private final native CefFrame N_GetMainFrame();
    private final native CefFrame N_GetFocusedFrame();
    private final native void N_Paint(int width, int height);
    private final native void N_SetWindowSize(int width, int height);
    private final native void N_LoadByUrl();
    private final native void N_SetCookie();

}