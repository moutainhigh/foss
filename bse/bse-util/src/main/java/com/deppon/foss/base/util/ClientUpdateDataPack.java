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
 * PROJECT NAME	: bse-util
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/base/util/ClientUpdateDataPack.java
 * 
 * FILE NAME        	: ClientUpdateDataPack.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.base.util;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;

/**
 * 客户端下载数据的请求包装类
 * 
 * @author zhangdongping
 * @date 2012-10-15 下午2:17:46
 * @since
 * @version
 */
public class ClientUpdateDataPack implements Serializable {
	
	/**
     * 序列化对象
     */
    private static final long serialVersionUID = 5062420428704695754L;

	/**
	 * 上次该数据更新时间
	 */
	private Date lastUpdateTime;

	/**
	 *区域ID 
	 */
	private String regionId;
	
	/**
	 * 语言, 为空的话 默认简体中文
	 */
	private String localString;
	
	/**
	 * 组织机构Code
	 */
	private String orgCode;
	
	
	/**
	 * 客户端用户登录名
	 */
	private String empCode;	

	/**
	 * 同步分页
	 */
	private int syncPage;
	
	private String pagination;
	
	

	/**
	 * @return the pagination
	 */
	public String getPagination() {
		return pagination;
	}

	/**
	 * @param pagination the pagination to set
	 */
	public void setPagination(String pagination) {
		this.pagination = pagination;
	}

	/**
	 * @return the syncPage
	 */
	public int getSyncPage() {
		return syncPage;
	}

	/**
	 * @param syncPage the syncPage to set
	 */
	public void setSyncPage(int syncPage) {
		this.syncPage = syncPage;
	}

	/**
	 * @return lastUpdateTime
	 */
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param  lastUpdateTime  
	 */
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
	/**
	 * @return regionId
	 */
	public String getRegionId() {
		return regionId;
	}

	/**
	 * @param  regionId  
	 */
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	/**
	 * @return localString
	 */
	public String getLocalString() {
		if (localString==null)
		   localString=Locale.SIMPLIFIED_CHINESE.toString();		 
		
		return localString;
	}

	/**
	 * @param  localString  
	 */
	public void setLocalString(String localString) {
		this.localString = localString;
	}
	
	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	/**
	 * @return  the empCode
	 */
	public String getEmpCode() {
	    return empCode;
	}

	
	/**
	 * @param empCode the empCode to set
	 */
	public void setEmpCode(String empCode) {
	    this.empCode = empCode;
	}

}
