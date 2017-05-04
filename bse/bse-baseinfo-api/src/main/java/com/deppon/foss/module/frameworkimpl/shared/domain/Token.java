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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/frameworkimpl/shared/domain/Token.java
 * 
 * FILE NAME        	: Token.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.frameworkimpl.shared.domain;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import org.apache.commons.lang.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;



/**
 * Foss系统内Token,继承自DPAP sso的Token
 * 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:ningyu,date:2012-11-28 下午9:01:15</p>
 * @author ningyu
 * @date 2012-11-28 下午9:01:15
 * @since
 * @version
 */
public class Token extends com.deppon.foss.framework.server.sso.domain.Token {
	/**
	 * 日志打印对象声明
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(Token.class);
	/**
	 * 工号
	 */
	private String empCode;
	
	/**
	 * 当前部门编码
	 */
	private String currentDeptCode;
	
	/**
	 * 当前部门名称
	 */
	private String currentDeptName;
	
	/**
	 * 有效时间，单位毫秒，默认为当前系统毫秒数
	 */
	private Long expireTime = System.currentTimeMillis();

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getCurrentDeptName() {
		return currentDeptName;
	}

	public void setCurrentDeptName(String currentDeptName) {
		this.currentDeptName = currentDeptName;
	}

	public String getCurrentDeptCode() {
		return currentDeptCode;
	}

	public void setCurrentDeptCode(String currentDeptCode) {
		this.currentDeptCode = currentDeptCode;
	}

	public Long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Long millisecond) {
		this.expireTime = millisecond;
	}

	/**
	 * 根据sessionId,userId,empCode,currentDeptCode,second秒来创建token
	 * 
	 * @author ningyu
	 * @date 2012-11-29 下午8:49:40
	 * @param sessionId 会话ID
	 * @param userId	用户ID
	 * @param empCode	工号
	 * @param currentDeptCode	当前部门编号
	 * @param currentDeptName	当前部门名称
	 * @param expireSecond	有效时间 单位秒
	 */
	public Token(String sessionId, String userId, String empCode, String currentDeptCode, String currentDeptName, int expireSecond) {
		this.setUserId(userId);
		this.setSessionId(sessionId);
		this.empCode = empCode;
		this.currentDeptCode = currentDeptCode;
		this.currentDeptName = currentDeptName;
		//生成时间戳
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		int millisecond = expireSecond * NumberConstants.NUMBER_1000;
		c.add(Calendar.MILLISECOND, millisecond);
		this.expireTime = c.getTimeInMillis();
	}
	
	/**
	 * byte[]数组的内容复制到Token中
	 * 
	 * @author ningyu
	 * @date 2012-11-30 上午8:34:29
	 * @param tokenBytes
	 */
	public Token(byte[] tokenBytes) {
		try {
			String token = new String(tokenBytes, CharEncoding.UTF_8);
			String[] keys = token.split(",");
			this.setSessionId(keys[0]);
			this.setUserId(keys[1]);
			this.empCode = keys[2];
			this.currentDeptCode = keys[NumberConstants.NUMBER_3];
			this.currentDeptName = keys[NumberConstants.NUMBER_4];
			this.expireTime = Long.parseLong(keys[NumberConstants.NUMBER_5]);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e.getMessage(),e);
		}
	}

	/**
	 * 返回该对象的byte[]数组表示
	 * 
	 * @author ningyu
	 * @date 2012-11-29 下午8:49:22
	 * @return
	 * @see
	 */
	public byte[] toBytes() {
		try {
			return this.toString().getBytes(CharEncoding.UTF_8);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e.getMessage(),e);
		}
		return null;
	}

	/** 
	 * 返回该对象的字符串表示
	 * 
	 * @author ningyu
	 * @date 2012-11-29 下午8:49:11
	 * @return 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		super.toString();
		StringBuffer sb = new StringBuffer(NumberConstants.NUMBER_8);
		sb.append(getSessionId()).append(",");
		sb.append(getUserId()).append(",");
		sb.append(getEmpCode()).append(",");
		sb.append(getCurrentDeptCode()).append(",");
		sb.append(getCurrentDeptName()).append(",");
		sb.append(getExpireTime());
		return sb.toString();
	}
	
	/**
	 * token是否过期
	 * 		1.expireTime >= currentTime 未过期返回false
	 * 		2.expireTime < currentTime 已过期返回true
	 * 
	 * @author ningyu
	 * @date 2012-11-30 上午11:41:31
	 * @return 1.expireTime >= currentTime 未过期返回false 2.expireTime < currentTime 已过期返回true
	 */
	public boolean expired() {
		long millisecond = this.getExpireTime();
		long currentMs = System.currentTimeMillis();
		return millisecond >= currentMs ? false : true;
	}
	
}
