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
 *  
 *  PROJECT NAME  : tfr-scheduling-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/domain/TruckDepartPlanUpdateEntity.java
 * 
 *  FILE NAME     :TruckDepartPlanUpdateEntity.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.scheduling.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 发车计划日志实体
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-12-25 下午5:26:58
 */
public class TruckDepartPlanUpdateEntity extends BaseEntity implements Cloneable {

	private static final long serialVersionUID = 4178039930336459352L;

	/**
	 * ID
	 */
	private String id;

	/**
	 * 发车计划明细ID
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private String truckDepartPlanDetailId;

	/**
	 * 
	 * 修改内容
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private String modifyContent;

	/**
	 * 创建时间
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private Date createTime;

	/**
	 * 创建人编码
	 */
	private String createUserCode;

	/**
	 * 
	 * 操作人
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private String createUserName;

	/**
	 * 创建人组织编码
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private String createOrgCode;
	/**
	 * 日志类型
	 */
	private String logType;

	/**
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-25 下午5:26:25
	 * @see com.deppon.foss.framework.entity.BaseEntity#getId()
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-25 下午5:26:25
	 * @see com.deppon.foss.framework.entity.BaseEntity#setId(java.lang.String)
	 */
	@Override
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取 发车计划明细ID.
	 * 
	 * @return the 发车计划明细ID
	 */
	public String getTruckDepartPlanDetailId() {
		return truckDepartPlanDetailId;
	}

	/**
	 * 设置 发车计划明细ID.
	 * 
	 * @param truckDepartPlanDetailId
	 *            the new 发车计划明细ID
	 */
	public void setTruckDepartPlanDetailId(String truckDepartPlanDetailId) {
		this.truckDepartPlanDetailId = truckDepartPlanDetailId;
	}

	/**
	 * 获取 修改内容.
	 * 
	 * @return the 修改内容
	 */
	public String getModifyContent() {
		return modifyContent;
	}

	/**
	 * 设置 修改内容.
	 * 
	 * @param modifyContent
	 *            the new 修改内容
	 */
	public void setModifyContent(String modifyContent) {
		this.modifyContent = modifyContent;
	}

	/**
	 * 获取 创建时间.
	 * 
	 * @return the 创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置 创建时间.
	 * 
	 * @param createTime
	 *            the new 创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取 创建人编码.
	 * 
	 * @return the 创建人编码
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * 设置 创建人编码.
	 * 
	 * @param createUserCode
	 *            the new 创建人编码
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/**
	 * 获取 操作人.
	 * 
	 * @return the 操作人
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * 设置 操作人.
	 * 
	 * @param createUserName
	 *            the new 操作人
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * 获取 创建人组织编码.
	 * 
	 * @return the 创建人组织编码
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * 设置 创建人组织编码.
	 * 
	 * @param createOrgCode
	 *            the new 创建人组织编码
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * 获取 日志类型.
	 * 
	 * @return the 日志类型
	 */
	public String getLogType() {
		return logType;
	}

	/**
	 * 设置 日志类型.
	 * 
	 * @param logType
	 *            the new 日志类型
	 */
	public void setLogType(String logType) {
		this.logType = logType;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}