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
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/domain/LoaderParticipationEntity.java
 *  
 *  FILE NAME          :LoaderParticipationEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 装卸车人员参与情况
 * @author ibm-zhangyixin
 * @date 2012-12-25 下午6:16:52
 */
public class LoaderParticipationEntity extends BaseEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7577566888048499644L;

	/**ID**/
	private String id;
	
	/**理货员姓名**/
	private String loaderName;

	/**理货员工号**/
	private String loaderCode;

	/**装卸车队编号**/
	private String loadOrgCode;

	/**装卸车队名称**/
	private String loadOrgName;

	/**加入任务时间**/
	private Date joinTime;

	/**离开任务时间**/
	private Date leaveTime;

	/**是否建立任务理货员**/
	private String beCreator;

	/**类型**/
	private String taskType;
	
	/**快递件数**/
	private BigDecimal expressCount;

	/**A货件数**/
	private BigDecimal acount;

	/**B货件数**/
	private BigDecimal bcount;
	

	/**任务ID**/
	private String taskId;
	
	/**标识*/
	private String flag;
	
	/**
	 * Gets the 标识.
	 *
	 * @return the 标识
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * Sets the 标识.
	 *
	 * @param flag the new 标识
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/** 
	 * 
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 下午6:19:32
	 * @see com.deppon.foss.framework.entity.BaseEntity#getId()
	 */
	public String getId() {
		return id;
	}
	
	/** 
	 * 
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 下午6:19:32
	 * @see com.deppon.foss.framework.entity.BaseEntity#setId(java.lang.String)
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 获取 理货员姓名*.
	 *
	 * @return the 理货员姓名*
	 */
	public String getLoaderName() {
		return loaderName;
	}
	
	/**
	 * 设置 理货员姓名*.
	 *
	 * @param loaderName the new 理货员姓名*
	 */
	public void setLoaderName(String loaderName) {
		this.loaderName = loaderName;
	}
	
	/**
	 * 获取 理货员工号*.
	 *
	 * @return the 理货员工号*
	 */
	public String getLoaderCode() {
		return loaderCode;
	}
	
	/**
	 * 设置 理货员工号*.
	 *
	 * @param loaderCode the new 理货员工号*
	 */
	public void setLoaderCode(String loaderCode) {
		this.loaderCode = loaderCode;
	}
	
	/**
	 * 获取 装卸车队编号*.
	 *
	 * @return the 装卸车队编号*
	 */
	public String getLoadOrgCode() {
		return loadOrgCode;
	}
	
	/**
	 * 设置 装卸车队编号*.
	 *
	 * @param loadOrgCode the new 装卸车队编号*
	 */
	public void setLoadOrgCode(String loadOrgCode) {
		this.loadOrgCode = loadOrgCode;
	}
	
	/**
	 * 获取 装卸车队名称*.
	 *
	 * @return the 装卸车队名称*
	 */
	public String getLoadOrgName() {
		return loadOrgName;
	}
	
	/**
	 * 设置 装卸车队名称*.
	 *
	 * @param loadOrgName the new 装卸车队名称*
	 */
	public void setLoadOrgName(String loadOrgName) {
		this.loadOrgName = loadOrgName;
	}
	
	/**
	 * 获取 加入任务时间*.
	 *
	 * @return the 加入任务时间*
	 */
	public Date getJoinTime() {
		return joinTime;
	}
	
	/**
	 * 设置 加入任务时间*.
	 *
	 * @param joinTime the new 加入任务时间*
	 */
	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}
	
	/**
	 * 获取 离开任务时间*.
	 *
	 * @return the 离开任务时间*
	 */
	public Date getLeaveTime() {
		return leaveTime;
	}
	
	/**
	 * 设置 离开任务时间*.
	 *
	 * @param leaveTime the new 离开任务时间*
	 */
	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}
	
	/**
	 * 获取 是否建立任务理货员*.
	 *
	 * @return the 是否建立任务理货员*
	 */
	public String getBeCreator() {
		return beCreator;
	}
	
	/**
	 * 设置 是否建立任务理货员*.
	 *
	 * @param beCreator the new 是否建立任务理货员*
	 */
	public void setBeCreator(String beCreator) {
		this.beCreator = beCreator;
	}
	
	/**
	 * 获取 类型*.
	 *
	 * @return the 类型*
	 */
	public String getTaskType() {
		return taskType;
	}
	
	/**
	 * 设置 类型*.
	 *
	 * @param taskType the new 类型*
	 */
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	
	/**
	 * 获取 任务ID*.
	 *
	 * @return the 任务ID*
	 */
	public String getTaskId() {
		return taskId;
	}
	
	/**
	 * 设置 任务ID*.
	 *
	 * @param taskId the new 任务ID*
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public BigDecimal getExpressCount() {
		return expressCount;
	}

	public void setExpressCount(BigDecimal expressCount) {
		this.expressCount = expressCount;
	}

	public BigDecimal getAcount() {
		return acount;
	}

	public void setAcount(BigDecimal acount) {
		this.acount = acount;
	}

	public BigDecimal getBcount() {
		return bcount;
	}

	public void setBcount(BigDecimal bcount) {
		this.bcount = bcount;
	}

}