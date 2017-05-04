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
 * PROJECT NAME	: pkp-changingexp
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/vo/CheckStatusVo.java
 * 
 * FILE NAME        	: CheckStatusVo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.changingexp.client.vo;

/**
 * 
 * <p>
 * 审核状态类
 * </p>
 * @title CheckStatusVo.java
 * @package com.deppon.foss.module.pickup.changingexp.client.vo 
 * @author suyujun
 * @version 0.1 2012-12-18
 */
public class CheckStatusVo {
	
	/**
	 * 审核状态编码
	 */
	private String checkStatusCode;
	
	/**
	 * 审核状态名称
	 */
	private String checkStatusName;

	/**
	 * 
	 * 构造类
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午9:34:02
	 */
	public CheckStatusVo(String code, String name) {
		checkStatusCode = code;
		checkStatusName = name;
	}


	
	/**
	 * @return the checkStatusCode .
	 */
	public String getCheckStatusCode() {
		return checkStatusCode;
	}


	
	/**
	 *@param checkStatusCode the checkStatusCode to set.
	 */
	public void setCheckStatusCode(String checkStatusCode) {
		this.checkStatusCode = checkStatusCode;
	}


	
	/**
	 * @return the checkStatusName .
	 */
	public String getCheckStatusName() {
		return checkStatusName;
	}


	
	/**
	 *@param checkStatusName the checkStatusName to set.
	 */
	public void setCheckStatusName(String checkStatusName) {
		this.checkStatusName = checkStatusName;
	}


	/**
	 * 
	 * 获得对象名字
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午9:34:37
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return checkStatusName;

	}
}