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
 * PROJECT NAME	: bse-common-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/server/service/IBusinessLockService.java
 * 
 * FILE NAME        	: IBusinessLockService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-common-api
 * PACKAGE NAME: com.deppon.foss.module.base.common.api.server.service
 * FILE    NAME: ILockService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.common.api.server.service;

import java.util.List;

import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;


/**
 * 业务互斥锁服务
 * @author ibm-zhuwei
 * @date 2013-1-17 下午4:14:40
 */
public interface IBusinessLockService {

	/**
	 * 锁定某个业务对象
	 * timeout = 0 时，非阻塞调用，如果对象已锁定立刻返回失败
	 * timeout > 0 时，阻塞调用，如果对象已锁定，会等待直到1）获取锁并返回成功 2）超时并返回失败
	 * @author ibm-zhuwei
	 * @date 2013-1-17 下午4:54:08
	 * @param mutex 互斥对象
	 * @param timeout 超时时间，单位：秒
	 * @return true：锁定成功，false：锁定失败
	 */
	boolean lock(MutexElement mutex, int timeout);

	/**
	 * 解除某个业务对象锁定
	 * @author ibm-zhuwei
	 * @date 2013-1-17 下午4:54:09
	 * @param mutex 互斥对象
	 */
	void unlock(MutexElement mutex);
	
	/**
	 * 批量锁定多个业务对象
	 * timeout = 0 时，非阻塞调用，如果任意对象已锁定立刻返回失败
	 * timeout > 0 时，阻塞调用，如果任意对象已锁定，会等待直到1）获取锁并返回成功 2）超时并返回失败
	 * @author ibm-zhuwei
	 * @date 2013-2-20 下午5:15:05
	 * @param mutexes 多个互斥对象
	 * @param timeout 超时时间，单位：秒
	 * @return true：锁定成功，false：锁定失败
	 */
	boolean lock(List<MutexElement> mutexes, int timeout);
	
	/**
	 * 批量解除多个业务对象锁定
	 * @author ibm-zhuwei
	 * @date 2013-2-20 下午5:14:45
	 * @param mutexes
	 */
	void unlock(List<MutexElement> mutexes);
}
