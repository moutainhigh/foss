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
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/cache/UserCache.java
 * 
 * FILE NAME        	: UserCache.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.server.cache;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.exceptions.JedisException;

import com.deppon.foss.framework.cache.CacheUtils;
import com.deppon.foss.framework.cache.redis.RedisClient;
import com.deppon.foss.framework.entity.IUser;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.context.SessionContext;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.util.common.FossTTLCache;

/**
 * 
 * 
*******************************************
* <b style="font-family:微软雅黑"><small>Description:用户对象缓存</small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
* 1 2012-08-30 钟庭杰    新增
* </div>  
********************************************
 */
public class UserCache extends FossTTLCache<IUser>{
	
	private static final Log LOG = LogFactory.getLog(UserCache.class);
	
	private RedisClient client;

	public void setClient(RedisClient client) {
		this.client = client;
	}

	public String getUUID() {
		return USER_CACHE_UUID;
	}
	
	@Override
    public IUser get(String key) {
		if(!client.getPoolInited()) {
			LOG.error("jedis pool is not init!");
			//走数据库查询
			return cacheProvider.get(key);
        }
		if(StringUtils.isBlank(key)) {
			LOG.error("key does not allow for null!");
			//走数据库查询
			return cacheProvider.get(key);
		}
        Jedis j = null;
        boolean borrowOrOprSuccess = true;
		
		String empCode = (String) SessionContext.getSession().getObject("FRAMEWORK_KEY_EMPCODE");
		String deptCode = (String) SessionContext.getSession().getObject("FOSS_KEY_CURRENT_DEPTCODE");
		
		if(empCode == null || deptCode == null) {
			//key对应的user在缓存中不存在
			try {
	            j = client.getResource();
	            String skey = CacheUtils.toJsonString(getKey(key));
	            String re = (String) j.get(skey);
	            Pipeline p = j.pipelined();
	            //313353 sonar
	            UserEntity entity = this.sonarSplitOne(re, key, skey, p);
	            //313353 sonar
	            this.sonarSplitTwo(entity);
	            
	            String online = "ONLINE#" + entity.getEmployee().getDepartment().getCode() + "#" + entity.getUserName();
	            p.setex(online, SessionContext.getSession().getMaxInactiveInterval(), entity.getUserName());
	            p.syncAndReturnAll();
	            return entity;
	        } catch (JedisException e) {
	        	borrowOrOprSuccess = false;
	        	//313353 sonar
	            this.sonarSplitThree(j);
	            LOG.error(e.getMessage(),e);
	            //走数据库查询
	            return cacheProvider.get(key);
	        } finally {
	        	//313353 sonar
	        	this.sonarSplitFour(borrowOrOprSuccess, j);
	        }
		} else {
			//key对应的user在缓存中存在
			try {
	            j = client.getResource();
	            String online = "ONLINE#" + deptCode + "#" + empCode;
	            String skey = CacheUtils.toJsonString(getKey(key));
	            Pipeline p = j.pipelined();
	            p.get(skey);
	            p.setex(online, SessionContext.getSession().getMaxInactiveInterval(), empCode);
	            List<Object> list = p.syncAndReturnAll();
	            String re = (String) list.get(0);
	            if(re == null) {
	            	//这里取出来为null是因为 ttl的user缓存过期了
	            	UserEntity entity = (UserEntity) cacheProvider.get(key);
	            	String svalue = CacheUtils.toJsonString(entity);
	            	j.setex(skey, timeOut, svalue);
	                return entity;
	            }
	            return (IUser) CacheUtils.jsonParseObject(re);
	        } catch (JedisException e) {
	        	borrowOrOprSuccess = false;
	        	//313353 sonar
	            this.sonarSplitThree(j);
	            LOG.error(e.getMessage(),e);
	            //走数据库查询
	            return cacheProvider.get(key);
	        } finally {
	        	//313353 sonar
	        	this.sonarSplitFour(borrowOrOprSuccess, j);
	        }
		}
    }
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private UserEntity sonarSplitOne(String re, String key, String skey, Pipeline p) {
		UserEntity entity = null;
		if(re == null) {
            //这里取出来为null是因为 ttl的user缓存过期了
            entity = (UserEntity) cacheProvider.get(key);
            String svalue = CacheUtils.toJsonString(entity);
            p.setex(skey, timeOut, svalue);
        } else {
            entity = (UserEntity) CacheUtils.jsonParseObject(re);
        }
		return entity;
	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private void sonarSplitTwo(UserEntity entity) {
		if(entity.getEmployee()==null){
        	throw new BusinessException("基础数据发生异常，该用户没有对应的员工信息！");
        }
        if(entity.getEmployee().getDepartment()==null){
        	throw new BusinessException("基础数据发生异常，该用户所关联的员工信息没有部门信息！");
        }
	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private void sonarSplitThree(Jedis j) {
		if (j != null) {
            client.returnBrokenResource(j);
        }
	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private void sonarSplitFour(boolean borrowOrOprSuccess, Jedis j) {
		if (borrowOrOprSuccess) {
            //返回到资源池
            client.returnResource(j);
        }
	}
}
