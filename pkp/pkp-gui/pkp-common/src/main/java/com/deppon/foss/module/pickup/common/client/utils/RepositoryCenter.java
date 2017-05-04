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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/utils/RepositoryCenter.java
 * 
 * FILE NAME        	: RepositoryCenter.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.common.client.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 
 * 
*******************************************
* <b style="font-family:微软雅黑"><small>Description:对象仓库：用于注册，起解耦作用</small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
*  2012-4-1 linws    新增
* </div>  
********************************************
*/
public class RepositoryCenter {


	//Logger
	private static final Log LOG = LogFactory.getLog(RepositoryCenter.class);
	
	//数据center
	private static RepositoryCenter repositoryCenter;
	
	//Logger
	private static ReentrantLock lock = new ReentrantLock();
	
	
	//数据仓库
	private Map<String, Object> repository;
	
	
	static{
		if (repositoryCenter==null) {
			try{//xiaowei add because even if it is exception case 
				//jvm still need unlock resource(sornar required)
				lock.lock();
				if (ObjectUtils.NULL.equals(
						ObjectUtils.defaultIfNull(repositoryCenter, ObjectUtils.NULL))) {
					repositoryCenter = new RepositoryCenter();
				}
			}catch(Exception e){
				LOG.error("lock exception", e);
			}
			lock.unlock();
		}
	}

	
	/**
	 * 构造方法
	 */
	private RepositoryCenter(){
		repository = new ConcurrentHashMap<String, Object>();
	}
	
	/**
	 * 单例
	 * @return
	 */
	public static RepositoryCenter getInstance(){
		return repositoryCenter;
	}
	
	/**
	 * 向数据仓库中注册
	 * @param key
	 * @param value
	 */
	public void register(String key,Object value){
		repository.put(key, value);
	}
	
	/**
	 * 获得数据仓库中的值
	 * @param key
	 * @return
	 */
	public Object get(String key){
		return repository.get(key);
	}
}