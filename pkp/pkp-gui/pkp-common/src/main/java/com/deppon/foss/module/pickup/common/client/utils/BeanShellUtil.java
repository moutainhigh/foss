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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/utils/BeanShellUtil.java
 * 
 * FILE NAME        	: BeanShellUtil.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.common.client.utils;

import java.util.Vector;

import bsh.Interpreter;

public class BeanShellUtil {

	/**
	 * bsh拦截器列表
	 */
	private static Vector<Interpreter> pool = new Vector<Interpreter>();
	
	/**
	 * 当前线程拦截器
	 */
	private static ClassLoader loader = Thread.currentThread()
			.getContextClassLoader();
	
	/**
	 * 私有构造器 用于单例
	 */
	private BeanShellUtil(){
	}

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