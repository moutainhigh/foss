/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-pda-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/pda/api/shared/domain/QueryAssignedLoadTaskEntity.java
 *  
 *  FILE NAME          :QueryAssignedLoadTaskEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-pda-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.pda.api.shared.domain
 * FILE    NAME: QueryAssignedLoadTaskEntity.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.dubbo.api.define;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * PDA接口：查询已分配派送装车任务查询条件
 * @author dp-duyi
 * @date 2012-11-6 下午12:22:38
 */
public class QueryAssignedLoadTaskEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4060996517015138992L;
	/**loaderCode*/
	private String loaderCode;
	/**loaderOrgCode*/
	private String loaderOrgCode;
	/**查询派送装车任务时间始*/
	private Date queryTimeBegin;
	/**查询派送装车任务时间止*/
	private Date queryTimeEnd;
	/*2013-04-19修改*/
	/**查询中转装车任务时间始*/
	private Date queryTransportTimeBegin;
	/**查询中转装车任务时间止*/
	private Date queryTransportTimeEnd;	
	/**装车类型*/
	private String taskType;
	/**装车状态*/
	private List<String> states;
	
	public Date getQueryTransportTimeBegin() {
		return queryTransportTimeBegin;
	}

	public void setQueryTransportTimeBegin(Date queryTransportTimeBegin) {
		this.queryTransportTimeBegin = queryTransportTimeBegin;
	}

	public Date getQueryTransportTimeEnd() {
		return queryTransportTimeEnd;
	}

	public void setQueryTransportTimeEnd(Date queryTransportTimeEnd) {
		this.queryTransportTimeEnd = queryTransportTimeEnd;
	}

	/**
	 * Gets the loaderCode.
	 *
	 * @return the loaderCode
	 */
	public String getLoaderCode() {
		return loaderCode;
	}
	
	/**
	 * Sets the loaderCode.
	 *
	 * @param loaderCode the new loaderCode
	 */
	public void setLoaderCode(String loaderCode) {
		this.loaderCode = loaderCode;
	}
	
	/**
	 * Gets the loaderOrgCode.
	 *
	 * @return the loaderOrgCode
	 */
	public String getLoaderOrgCode() {
		return loaderOrgCode;
	}
	
	/**
	 * Sets the loaderOrgCode.
	 *
	 * @param loaderOrgCode the new loaderOrgCode
	 */
	public void setLoaderOrgCode(String loaderOrgCode) {
		this.loaderOrgCode = loaderOrgCode;
	}
	
	/**
	 * Gets the queryTimeBegin.
	 *
	 * @return the queryTimeBegin
	 */
	public Date getQueryTimeBegin() {
		return queryTimeBegin;
	}
	
	/**
	 * Sets the queryTimeBegin.
	 *
	 * @param queryTimeBegin the new queryTimeBegin
	 */
	public void setQueryTimeBegin(Date queryTimeBegin) {
		this.queryTimeBegin = queryTimeBegin;
	}
	
	/**
	 * Gets the queryTimeEnd.
	 *
	 * @return the queryTimeEnd
	 */
	public Date getQueryTimeEnd() {
		return queryTimeEnd;
	}
	
	/**
	 * Sets the queryTimeEnd.
	 *
	 * @param queryTimeEnd the new queryTimeEnd
	 */
	public void setQueryTimeEnd(Date queryTimeEnd) {
		this.queryTimeEnd = queryTimeEnd;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public List<String> getStates() {
		return states;
	}

	public void setStates(List<String> states) {
		this.states = states;
	}
}