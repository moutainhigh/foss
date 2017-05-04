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
 * PROJECT NAME	: bse-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/service/impl/BusinessLockService.java
 * 
 * FILE NAME        	: BusinessLockService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-common
 * PACKAGE NAME: com.deppon.foss.module.base.common.server.service.impl
 * FILE    NAME: BusinessLockService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon allmap Rights Reserved.
 */
package com.deppon.foss.module.base.common.server.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.exceptions.JedisConnectionException;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.cache.redis.RedisClient;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;

/**
 * 业务互斥锁服务 redis实现版本
 * 
 * @author ibm-zhuwei
 * @date 2013-1-17 下午5:27:36
 */
public class BusinessLockService implements IBusinessLockService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BusinessLockService.class);

	/**
	 * Jedis客户端
	 */
	private RedisClient client;

	/**
	 * 锁定某个业务对象 timeout = 0 时，非阻塞调用，如果对象已锁定立刻返回失败 timeout > 0
	 * 时，阻塞调用，如果对象已锁定，会等待直到1）获取锁并返回成功 2）超时并返回失败
	 * 
	 * @author ibm-zhuwei
	 * @date 2013-1-17 下午4:54:08
	 * @param mutex
	 *            互斥对象
	 * @param timeout
	 *            超时时间，单位：秒
	 * @return true：锁定成功，false：锁定失败
	 * @see com.deppon.foss.module.base.common.api.server.service.IBusinessLockService#lock(com.deppon.foss.module.base.common.api.shared.dto.MutexElement,
	 *      long)
	 */
	@Override
	public boolean lock(MutexElement mutex, int timeout) {

		// 输入参数校验
		if (mutex == null || mutex.getType() == null
				|| StringUtils.isEmpty(mutex.getBusinessNo())) {
			throw new BusinessException("互斥参数为空");
		}

		Jedis jedis = null;
		String key = mutex.getType().getPrefix() + mutex.getBusinessNo();
		String value = mutex.getBusinessDesc();

		try {
			
			jedis = client.getResource();
			long nano = System.nanoTime();

			do {
				LOGGER.debug("try lock key: " + key);
				
				// 使用setnx模拟锁
				Long i = jedis.setnx(key, value);
				
				if (i == 1) {	// setnx成功，获得锁
					jedis.expire(key, mutex.getTtl());
					LOGGER.debug("get lock, key: " + key + " , expire in " + mutex.getTtl() + " seconds.");
					return true;
				} else {	// 存在锁
					if (LOGGER.isDebugEnabled()) {
						String desc = jedis.get(key);
						LOGGER.debug("key: " + key + " locked by another business：" + desc);
					}
				}
				
				if (timeout == 0) {	// 非阻塞调用，则退出
					break;
				}
				
				Thread.sleep(NumberConstants.NUMERAL_1000);	// 每秒访问一次
				
			} while ((System.nanoTime() - nano) < timeout * NumberConstants.NUM_1000 * NumberConstants.NUM_1000 * NumberConstants.NUM_1000);

			// 得不到锁，返回失败
			return false;

		} catch (JedisConnectionException je) {
			LOGGER.error(je.getMessage(), je);
			client.returnBrokenResource(jedis);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				client.returnResource(jedis);
			}
		}

		// 锁不再作为业务的的强制必要条件
		// 发生REDIS异常，则不再处理锁
		return true;
	}

	/**
	 * 解除某个业务对象锁定
	 * 
	 * @author ibm-zhuwei
	 * @date 2013-1-17 下午4:54:09
	 * @param mutex
	 *            互斥对象
	 * @see com.deppon.foss.module.base.common.api.server.service.IBusinessLockService#unlock(com.deppon.foss.module.base.common.api.shared.dto.MutexElement)
	 */
	@Override
	public void unlock(MutexElement mutex) {

		// 输入参数校验
		if (mutex == null || mutex.getType() == null
				|| StringUtils.isEmpty(mutex.getBusinessNo())) {
			throw new BusinessException("互斥参数为空");
		}

		Jedis jedis = null;
		
		String key = mutex.getType().getPrefix() + mutex.getBusinessNo();
		
		try {
			jedis = client.getResource();
			
			jedis.del(key);
			LOGGER.debug("release lock, key :" + key);
		} catch (JedisConnectionException je) {
			LOGGER.error(je.getMessage(), je);
			client.returnBrokenResource(jedis);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				client.returnResource(jedis);
			}
		}

	}

	
	/** 
	 * 批量锁定多个业务对象
	 * @author ibm-zhuwei
	 * @date 2013-2-20 下午5:19:27
	 * @param mutexes
	 * @param timeout
	 * @return
	 * @see com.deppon.foss.module.base.common.api.server.service.IBusinessLockService#lock(java.util.List, int)
	 */
	@Override
	public boolean lock(List<MutexElement> mutexes, int timeout) {

		// 输入参数校验
		if (CollectionUtils.isEmpty(mutexes)) {
			throw new BusinessException("互斥参数为空");
		}
		
		// 定义并构造所有锁MAP
		LinkedHashMap<String, MutexElement> map = new LinkedHashMap<String, MutexElement>();
		
		for (MutexElement mutex : mutexes) {

			if (mutex == null || mutex.getType() == null
					|| StringUtils.isEmpty(mutex.getBusinessNo())) {
				throw new BusinessException("互斥参数为空");
			}

			String key = mutex.getType().getPrefix() + mutex.getBusinessNo();
			map.put(key, mutex);
		}
		
		Jedis jedis = null;

		try {

			List<String> locking = new ArrayList<String>();	// 要锁定的KEY集合
			List<String> locked = new ArrayList<String>();	// 已锁定的KEY集合
			locking.addAll(map.keySet());
			jedis = client.getResource();
			long nano = System.nanoTime();

			do {
				LOGGER.debug("try lock keys: " + locking);
				
				// 构建pipeline，批量提交
				Pipeline pipeline = jedis.pipelined();
				for (String key : locking) {
					// 使用setnx模拟锁
					pipeline.setnx(key, map.get(key).getBusinessDesc());
				}

				// 提交redis执行计数
				List<Object> results = pipeline.syncAndReturnAll();
				
				for (int i = 0; i < results.size(); ++i) {
					Long result = (Long) results.get(i);
					String key = locking.get(i);
					
					if (result == 1) {	// setnx成功，获得锁
						jedis.expire(key, map.get(key).getTtl());
						locked.add(key);
					}// 存在锁
					
				}
				
				locking.removeAll(locked);	// 已锁定资源去除
				
				if (CollectionUtils.isEmpty(locking)) {	// 得到所有锁资源
					return true;
				} else {	// 部分资源未能锁住
					LOGGER.debug("keys: " + locking + " locked by another business：");
				}
				
				if (timeout == 0) {	// 非阻塞调用，则退出
					break;
				}
				
				Thread.sleep(NumberConstants.NUMERAL_1000);	// 每秒访问一次
				
			} while ((System.nanoTime() - nano) < timeout*NumberConstants.NUMERAL_10001*NumberConstants.NUMERAL_10001*NumberConstants.NUMERAL_10001);

			// 得不到锁，释放锁定的部分对象，并返回失败
			if (CollectionUtils.isNotEmpty(locked)) {
				jedis.del(locked.toArray(new String[0]));
			}
			
			return false;

		} catch (JedisConnectionException je) {
			LOGGER.error(je.getMessage(), je);
			client.returnBrokenResource(jedis);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				client.returnResource(jedis);
			}
		}

		// 锁不再作为业务的的强制必要条件
		// 发生REDIS异常，则不再处理锁
		return true;
	}

	/** 
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * @author ibm-zhuwei
	 * @date 2013-2-20 下午5:19:27
	 * @param mutexes
	 * @see com.deppon.foss.module.base.common.api.server.service.IBusinessLockService#unlock(java.util.List)
	 */
	@Override
	public void unlock(List<MutexElement> mutexes) {

		// 输入参数校验
		if (CollectionUtils.isEmpty(mutexes)) {
			throw new BusinessException("互斥参数为空");
		}
		
		List<String> keys = new ArrayList<String>();
		for (MutexElement mutex : mutexes) {

			if (mutex == null || mutex.getType() == null
					|| StringUtils.isEmpty(mutex.getBusinessNo())) {
				throw new BusinessException("互斥参数为空");
			}

			String key = mutex.getType().getPrefix() + mutex.getBusinessNo();
			keys.add(key);
		}
		
		Jedis jedis = null;
		
		
		try {
			jedis = client.getResource();
			
			jedis.del(keys.toArray(new String[0]));
			LOGGER.debug("release lock, keys :" + keys);
		} catch (JedisConnectionException je) {
			LOGGER.error(je.getMessage(), je);
			client.returnBrokenResource(jedis);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				client.returnResource(jedis);
			}
		}
		
	}

	/**
	 * @param client
	 */
	public void setClient(RedisClient client) {
		this.client = client;
	}

}
