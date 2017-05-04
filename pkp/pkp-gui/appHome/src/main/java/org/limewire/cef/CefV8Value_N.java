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
 * FILE PATH        	: appHome/src/main/java/org/limewire/cef/CefV8Value_N.java
 * 
 * FILE NAME        	: CefV8Value_N.java
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
 * Cef8Value 标准变量数值类对于CEF jni dll类
 * @author niujian
 *
 */
public class CefV8Value_N implements CefV8Value {
	
	private static final Log log = LogFactory.getLog(CefV8Value_N.class);
	
	// Used internally to store a pointer to the CEF object.
    private long N_CefHandle = 0;
	
    CefV8Value_N() {}
    
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
	public boolean isUndefined() {
		try {
        	return N_IsUndefined();
        } catch (UnsatisfiedLinkError ule) {
        	log.error(ule);
        }
		return false;
	}

	@Override
	public boolean isNull() {
		try {
        	return N_IsNull();
        } catch (UnsatisfiedLinkError ule) {
        	log.error(ule);
        }
		return false;
	}

	@Override
	public boolean isBool() {
		try {
        	return N_IsBool();
        } catch (UnsatisfiedLinkError ule) {
        	log.error(ule);
        }
		return false;
	}

	@Override
	public boolean isInt() {
		try {
        	return N_IsInt();
        } catch (UnsatisfiedLinkError ule) {
        	log.error(ule);
        }
		return false;
	}

	@Override
	public boolean isDouble() {
		try {
        	return N_IsDouble();
        } catch (UnsatisfiedLinkError ule) {
        	log.error(ule);
        }
		return false;
	}

	@Override
	public boolean isString() {
		try {
        	return N_IsString();
        } catch (UnsatisfiedLinkError ule) {
        	log.error(ule);
        }
		return false;
	}

	@Override
	public boolean isObject() {
		try {
        	return N_IsObject();
        } catch (UnsatisfiedLinkError ule) {
        	log.error(ule);
        }
		return false;
	}

	@Override
	public boolean isArray() {
		try {
        	return N_IsArray();
        } catch (UnsatisfiedLinkError ule) {
        	log.error(ule);
        }
		return false;
	}

	@Override
	public boolean isFunction() {
		try {
        	return N_IsFunction();
        } catch (UnsatisfiedLinkError ule) {
        	log.error(ule);
        }
		return false;
	}

	@Override
	public boolean getBoolValue() {
		try {
        	return N_GetBoolValue();
        } catch (UnsatisfiedLinkError ule) {
        	log.error(ule);
        }
		return false;
	}

	@Override
	public int getIntValue() {
		try {
        	return N_GetIntValue();
        } catch (UnsatisfiedLinkError ule) {
        	log.error(ule);
        }
		return 0;
	}

	@Override
	public double getDoubleValue() {
		try {
        	return N_GetDoubleValue();
        } catch (UnsatisfiedLinkError ule) {
        	log.error(ule);
        }
		return 0;
	}

	@Override
	public String getStringValue() {
		try {
        	return N_GetStringValue();
        } catch (UnsatisfiedLinkError ule) {
        	log.error(ule);
        }
		return null;
	}

	@Override
	public boolean hasValue(String key) {
		try {
        	return N_HasValue(key);
        } catch (UnsatisfiedLinkError ule) {
        	log.error(ule);
        }
		return false;
	}

	@Override
	public boolean hasValue(int index) {
		try {
        	return N_HasValue(index);
        } catch (UnsatisfiedLinkError ule) {
        	log.error(ule);
        }
		return false;
	}

	@Override
	public boolean deleteValue(String key) {
		try {
        	return N_DeleteValue(key);
        } catch (UnsatisfiedLinkError ule) {
        	log.error(ule);
        }
		return false;
	}

	@Override
	public boolean deleteValue(int index) {
		try {
        	return N_DeleteValue(index);
        } catch (UnsatisfiedLinkError ule) {
        	log.error(ule);
        }
		return false;
	}

	@Override
	public CefV8Value getValue(String key) {
		try {
        	return N_GetValue(key);
        } catch (UnsatisfiedLinkError ule) {
        	log.error(ule);
        }
		return null;
	}

	@Override
	public CefV8Value getValue(int index) {
		try {
        	return N_GetValue(index);
        } catch (UnsatisfiedLinkError ule) {
        	log.error(ule);
        }
		return null;
	}

	@Override
	public boolean setValue(String key, CefV8Value value) {
		try {
        	return N_SetValue(key, value);
        } catch (UnsatisfiedLinkError ule) {
        	log.error(ule);
        }
		return false;
	}

	@Override
	public boolean setValue(int index, CefV8Value value) {
		try {
        	return N_SetValue(index, value);
        } catch (UnsatisfiedLinkError ule) {
        	log.error(ule);
        }
		return false;
	}

	@Override
	public String[] getKeys() {
		try {
        	return N_GetKeys();
        } catch (UnsatisfiedLinkError ule) {
        	log.error(ule);
        }
		return null;
	}

	@Override
	public Object getUserData() {
		try {
        	return N_GetUserData();
        } catch (UnsatisfiedLinkError ule) {
        	log.error(ule);
        }
		return null;
	}

	@Override
	public int getArrayLength() {
		try {
        	return N_GetArrayLength();
        } catch (UnsatisfiedLinkError ule) {
        	log.error(ule);
        }
		return 0;
	}

	@Override
	public String getFunctionName() {
		try {
        	return N_GetFunctionName();
        } catch (UnsatisfiedLinkError ule) {
        	log.error(ule);
        }
		return null;
	}

	@Override
	public CefV8Handler getFunctionHandler() {
		try {
        	return N_GetFunctionHandler();
        } catch (UnsatisfiedLinkError ule) {
        	log.error(ule);
        }
		return null;
	}

	@Override
	public boolean executeFunction(CefV8FunctionParams params) {
		try {
        	return N_ExecuteFunction(params);
        } catch (UnsatisfiedLinkError ule) {
        	log.error(ule);
        }
		return false;
	}

	private final native void N_Destroy();
	private final native boolean N_IsUndefined();
	private final native boolean N_IsNull();
	private final native boolean N_IsBool();
	private final native boolean N_IsInt();
	private final native boolean N_IsDouble();
	private final native boolean N_IsString();
	private final native boolean N_IsObject();
	private final native boolean N_IsArray();
	private final native boolean N_IsFunction();
	private final native boolean N_GetBoolValue();
	private final native int N_GetIntValue();
	private final native double N_GetDoubleValue();
	private final native String N_GetStringValue();
	private final native boolean N_HasValue(String key);
	private final native boolean N_HasValue(int index);
	private final native boolean N_DeleteValue(String key);
	private final native boolean N_DeleteValue(int index);
	private final native CefV8Value N_GetValue(String key);
	private final native CefV8Value N_GetValue(int index);
	private final native boolean N_SetValue(String key, CefV8Value value);
	private final native boolean N_SetValue(int index, CefV8Value value);
	private final native String[] N_GetKeys();
	private final native Object N_GetUserData();
	private final native int N_GetArrayLength();
	private final native String N_GetFunctionName();
	private final native CefV8Handler N_GetFunctionHandler() ;
	private final native boolean N_ExecuteFunction(CefV8FunctionParams params);
}