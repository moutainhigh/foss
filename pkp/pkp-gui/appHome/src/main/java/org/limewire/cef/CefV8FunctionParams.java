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
 * FILE PATH        	: appHome/src/main/java/org/limewire/cef/CefV8FunctionParams.java
 * 
 * FILE NAME        	: CefV8FunctionParams.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package org.limewire.cef;

/**
 * CEF browser 调用js方法回调函数参数类
 * @author niujian
 *
 */
public class CefV8FunctionParams {
	private CefV8Value object = null;
	private CefV8Value[] arguments = null;
	private CefV8Value retval = null;
	private String exception = null;
  
	CefV8FunctionParams() {
	}
	public CefV8FunctionParams(CefV8Value object, CefV8Value[] arguments) {
		this.object = object;
		this.arguments = arguments;
	}
  
	/**
	 * Return the object that this function is being executed on.
	 */
	public CefV8Value getObject() { return object; }
	
	/**
	 * Return the arguments passed to the function.
	 */
	public CefV8Value[] getArguments() { return arguments; }
	public boolean hasArguments() { return arguments.length > 0; }
  
	/**
	 * Set the return value.
	 */
	public void setRetVal(CefV8Value retval) { this.retval = retval; }
	
	/**
	 * Get the return value.
	 */
	public CefV8Value getRetVal() { return retval; }
  
	/**
	 * Set the exception, if any, that occurred during function execution.
	 */
	public void setException(String exception) { this.exception = exception; }
	
	/**
	 * Get the exception, if any, that occurred during function execution.
	 */
	public String getException() { return exception; }
}