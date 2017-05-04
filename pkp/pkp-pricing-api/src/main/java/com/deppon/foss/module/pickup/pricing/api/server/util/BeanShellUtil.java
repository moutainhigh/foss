/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: pkp-pricing-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/util/BeanShellUtil.java
 * 
 * FILE NAME        	: BeanShellUtil.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.server.util;

import java.util.Vector;

import bsh.Interpreter;

/**
 *  
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:zhangdongping,date:2012-11-9 上午11:50:04,content: </p>
 * @author zhangdongping
 * @date 2012-11-9 上午11:50:04
 * @since
 * @version
 */
public class BeanShellUtil {
	
	private BeanShellUtil(){
		
	}

	private static Vector<Interpreter> pool = new Vector<Interpreter>();
	private static ClassLoader loader = Thread.currentThread()
			.getContextClassLoader();

	public static synchronized Interpreter getInterpreter() {
		if (pool.isEmpty()) {
			Interpreter i = new Interpreter();
			i.setClassLoader(loader);
			return i;
		} else {
			Interpreter i = pool.lastElement();
			pool.setSize(pool.size() - 1);
			return i;
		}
	}

	public static synchronized void freeInterpreter(Interpreter i) {
		pool.addElement(i);
	}
}