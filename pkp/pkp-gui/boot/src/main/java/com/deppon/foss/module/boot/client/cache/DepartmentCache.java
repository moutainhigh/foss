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
 * FILE PATH        	: boot/src/main/java/com/deppon/foss/module/boot/client/cache/DepartmentCache.java
 * 
 * FILE NAME        	: DepartmentCache.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.boot.client.cache;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 部门缓存对象
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:31:00,
 * </p>
 * 
 * @author dp-yangtong
 * @date 2012-10-12 上午9:31:00
 * @since
 * @version
 */
public class DepartmentCache {

    	/**
    	 * 定义静态的DepartmentEntity类型的List集合allDepts
    	 */
	private static List allDepts;

	/**
	 * 日志对象
	 */
	public static final Log LOG = LogFactory.getLog(DepartmentCache.class);
	
	static void init() {
		try {
		    	/**
		    	 * allDepts设置为空
		    	 */
			allDepts = null;
		} catch (Exception t) {
		    	/**
		    	 * 记录错误日志
		    	 */
			LOG.error("EXCEPTION",t);
			/**
		    	 * 创建一个DepartmentEntity类型的List集合
		    	 */
			allDepts = new ArrayList();
		}
	}

	/**
	 * 获取所有部门
	 * 
	 * @return
	 */
	public static List getAllDepartments() {
	    	/**
	    	 * 返回allDepts
	    	 */
		return allDepts;
	}


}