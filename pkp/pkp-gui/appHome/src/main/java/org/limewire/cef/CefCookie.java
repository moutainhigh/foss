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
 * FILE PATH        	: appHome/src/main/java/org/limewire/cef/CefCookie.java
 * 
 * FILE NAME        	: CefCookie.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package org.limewire.cef;

/**
 * Cef cookie 功能调用参数类
 * @author niujian
 *
 */
public class CefCookie {

	private String cookieurl;
	private String cookiename;
	private String cookievalue;
	private String cookiedomain;
	private String cookiepath;
	public String getCookieurl() {
		return cookieurl;
	}
	public void setCookieurl(String cookieurl) {
		this.cookieurl = cookieurl;
	}
	public String getCookiename() {
		return cookiename;
	}
	public void setCookiename(String cookiename) {
		this.cookiename = cookiename;
	}
	public String getCookievalue() {
		return cookievalue;
	}
	public void setCookievalue(String cookievalue) {
		this.cookievalue = cookievalue;
	}
	public String getCookiedomain() {
		return cookiedomain;
	}
	public void setCookiedomain(String cookiedomain) {
		this.cookiedomain = cookiedomain;
	}
	public String getCookiepath() {
		return cookiepath;
	}
	public void setCookiepath(String cookiepath) {
		this.cookiepath = cookiepath;
	}
	
}