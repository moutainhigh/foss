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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/utils/BeanShellExecutor.java
 * 
 * FILE NAME        	: BeanShellExecutor.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.common.client.utils;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import bsh.EvalError;
import bsh.Interpreter;
import bsh.TargetError;

public class BeanShellExecutor {
	
	private BeanShellExecutor(){
		
	}

	public static Object execute(String script, Map<String, Object> placeholder) {
		Interpreter i = BeanShellUtil.getInterpreter();
		try {

			if (null != placeholder && !placeholder.isEmpty()) {
				Set<Entry<String, Object>> es = placeholder.entrySet();
				for (Entry<String, Object> e : es) {
					i.set(e.getKey(), e.getValue());
				}
			}

			Object result = i.eval(script);

			if (null == result) {
				throw new BeanShellException("Return result is null");
			} else {
				return result;
			}
		} catch (TargetError targetError) {
			throw new BeanShellException("Could not execute BeanShell script",
					targetError);
		} catch (EvalError evalError) {
			throw new BeanShellException("Could not execute BeanShell script",
					evalError);
		} finally {
			BeanShellUtil.freeInterpreter(i);
		}
	}
	
	public static Number executeNumber(String script, Map<String, Object> placeholder) {
		return (Number)execute(script, placeholder);
	}

}