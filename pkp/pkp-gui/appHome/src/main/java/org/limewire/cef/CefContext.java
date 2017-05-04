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
 * FILE PATH        	: appHome/src/main/java/org/limewire/cef/CefContext.java
 * 
 * FILE NAME        	: CefContext.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package org.limewire.cef;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 调用Google CEF框架，初始化CEF context
 * @author niujian
 *
 */
public class CefContext {
	
	private static boolean hasInited = false;
	private static final Log log = LogFactory.getLog(CefContext.class);
	/**
	 * initialize cef context
	 * @param cachePath
	 * @return
	 */
	public static final boolean initialize(String cachePath) {
		try {
			if(!hasInited){
				hasInited = true;
				System.loadLibrary("icudt");
				System.loadLibrary("libcef");
				System.loadLibrary("cefclient");
				return N_Initialize(System.getProperty("sun.boot.library.path"), cachePath);
			}
		} catch (UnsatisfiedLinkError err) {
			log.error(err);
		}
		return false;
	}
	
	/**
	 * Shut down the context.
	 */
	public static final void shutdown() {
		System.runFinalization();
		try {
			N_Shutdown();
        } catch (UnsatisfiedLinkError err) {
        	log.error(err);
        }
	}
	
	/**
	 * Create a new browser window.
	 * @param handler The object that will receive browser events.
	 * @param url The initial URL to load in the browser window.
	 * @return A new browser object.
	 */
	public static final CefBrowser createBrowser(String url,CefV8Handler handler) {
		return new CefBrowser_N(url, handler);
	}
	
	public static final CefBrowser createBrowser(String url,CefV8Handler handler, CefCookie cefCookier) {
		return new CefBrowser_N(url, handler, cefCookier);
	}
	
	/**
	 * Create a new V8 undefined value.
	 * @return The new object if successful.
	 */
	public static final CefV8Value createV8Undefined() {
		try {
			return N_CreateV8Undefined();
        } catch (UnsatisfiedLinkError err) {
        	log.error(err);
        }
        return null;
	}
	
	/**
	 * Create a new V8 null value.
	 * @return The new object if successful.
	 */
	public static final CefV8Value createV8Null() {
		try {
			return N_CreateV8Null();
        } catch (UnsatisfiedLinkError err) {
        	log.error(err);
        }
        return null;
	}
	
	/**
	 * Create a new V8 boolean value.
	 * @param val The value.
	 * @return The new object if successful.
	 */
	public static final CefV8Value createV8Bool(boolean val) {
		try {
			return N_CreateV8Bool(val);
        } catch (UnsatisfiedLinkError err) {
        	log.error(err);
        }
        return null;
	}
	
	/**
	 * Create a new V8 int value.
	 * @param val The value.
	 * @return The new object if successful.
	 */
	public static final CefV8Value createV8Int(int val) {
		try {
			return N_CreateV8Int(val);
        } catch (UnsatisfiedLinkError err) {
        	log.error(err);
        }
        return null;
	}
	
	/**
	 * Create a new V8 double value.
	 * @param val The value.
	 * @return The new object if successful.
	 */
	public static final CefV8Value createV8Double(double val) {
		try {
			return N_CreateV8Double(val);
        } catch (UnsatisfiedLinkError err) {
        	log.error(err);
        }
        return null;
	}
	
	/**
	 * Create a new V8 string value.
	 * @param val The value.
	 * @return The new object if successful.
	 */
	public static final CefV8Value createV8String(String val) {
		try {
			return N_CreateV8String(val);
        } catch (UnsatisfiedLinkError err) {
        	log.error(err);
        }
        return null;
	}
	
	/**
	 * Create a new V8 object value.
	 * @param user_data The object representing user data.
	 * @return The new object if successful.
	 */
	public static final CefV8Value createV8Object(Object user_data) {
		try {
			return N_CreateV8Object(user_data);
        } catch (UnsatisfiedLinkError err) {
        	log.error(err);
        }
        return null;
	}
	
	/**
	 * Create a new V8 array value.
	 * @return The new object if successful.
	 */
	public static final CefV8Value createV8Array() {
		try {
			return N_CreateV8Array();
        } catch (UnsatisfiedLinkError err) {
        	log.error(err);
        }
        return null;
	}
	
	/**
	 * Create a new V8 function value.
	 * @param name The name of the function.
	 * @param handler The handler that will be called when the function is executed.
	 * @return The new object if successful.
	 */
	public static final CefV8Value createV8Function(String name, CefV8Handler handler) {
		try {
			return N_CreateV8Function(name, handler);
        } catch (UnsatisfiedLinkError err) {
        	log.error(err);
        }
        return null;
	}
	
	private static final native boolean N_Initialize(String pathToJavaDLL, String cachePath);
	private static final native void N_Shutdown();
	private static final native CefV8Value N_CreateV8Undefined();
	private static final native CefV8Value N_CreateV8Null();
	private static final native CefV8Value N_CreateV8Bool(boolean val);
	private static final native CefV8Value N_CreateV8Int(int val);
	private static final native CefV8Value N_CreateV8Double(double val);
	private static final native CefV8Value N_CreateV8String(String val);
	private static final native CefV8Value N_CreateV8Object(Object user_data);
	private static final native CefV8Value N_CreateV8Array();
	private static final native CefV8Value N_CreateV8Function(String name, CefV8Handler handler);
}