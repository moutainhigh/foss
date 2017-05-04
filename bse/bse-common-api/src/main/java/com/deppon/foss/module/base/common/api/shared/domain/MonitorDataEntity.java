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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/shared/domain/MonitorDataEntity.java
 * 
 * FILE NAME        	: MonitorDataEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 监控数据
 * 
 * @author ibm-zhuwei
 * @date 2013-2-1 下午4:29:03
 */
public class MonitorDataEntity extends BaseEntity {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 4276897322139266262L;

	/**
	 * 指标编码
	 */
	private String indicatorCode;

	/**
	 * 指标子编码
	 */
	private String indicatorSubCode;

	/**
	 * 指标值
	 */
	private Long indicatorValue;

	/**
	 * 监控日期
	 */
	private Date monitorDate;

	/**
	 * 监控时间段
	 */
	private String monitorTimeRange;

	/**
	 * 监控起始时间
	 */
	private Date monitorStartTime;

	/**
	 * 监控截止时间
	 */
	private Date monitorEndTime;

	/**
	 * 部门编码
	 */
	private String orgCode;

	/**
	 * 二级组织编码
	 */
	private String level2OrgCode;

	/**
	 * 三级组织编码
	 */
	private String level3OrgCode;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * @return indicatorCode
	 */
	public String getIndicatorCode() {
		return indicatorCode;
	}

	/**
	 * @param indicatorCode
	 */
	public void setIndicatorCode(String indicatorCode) {
		this.indicatorCode = indicatorCode;
	}

	/**
	 * @return indicatorValue
	 */
	public Long getIndicatorValue() {
		return indicatorValue;
	}

	/**
	 * @param indicatorValue
	 */
	public void setIndicatorValue(Long indicatorValue) {
		this.indicatorValue = indicatorValue;
	}

	/**
	 * @return monitorDate
	 */
	public Date getMonitorDate() {
		return monitorDate;
	}

	/**
	 * @param monitorDate
	 */
	public void setMonitorDate(Date monitorDate) {
		this.monitorDate = monitorDate;
	}

	/**
	 * @return monitorTimeRange
	 */
	public String getMonitorTimeRange() {
		return monitorTimeRange;
	}

	/**
	 * @param monitorTimeRange
	 */
	public void setMonitorTimeRange(String monitorTimeRange) {
		this.monitorTimeRange = monitorTimeRange;
	}

	/**
	 * @return orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return monitorStartTime
	 */
	public Date getMonitorStartTime() {
		return monitorStartTime;
	}

	/**
	 * @param monitorStartTime
	 */
	public void setMonitorStartTime(Date monitorStartTime) {
		this.monitorStartTime = monitorStartTime;
	}

	/**
	 * @return monitorEndTime
	 */
	public Date getMonitorEndTime() {
		return monitorEndTime;
	}

	/**
	 * @param monitorEndTime
	 */
	public void setMonitorEndTime(Date monitorEndTime) {
		this.monitorEndTime = monitorEndTime;
	}

	/**
	 * @return level2OrgCode
	 */
	public String getLevel2OrgCode() {
		return level2OrgCode;
	}

	/**
	 * @param level2OrgCode
	 */
	public void setLevel2OrgCode(String level2OrgCode) {
		this.level2OrgCode = level2OrgCode;
	}

	/**
	 * @return level3OrgCode
	 */
	public String getLevel3OrgCode() {
		return level3OrgCode;
	}

	/**
	 * @param level3OrgCode
	 */
	public void setLevel3OrgCode(String level3OrgCode) {
		this.level3OrgCode = level3OrgCode;
	}
	
	/**
	 * @return indicatorSubCode
	 */
	public String getIndicatorSubCode() {
		return indicatorSubCode;
	}

	/**
	 * @param indicatorSubCode
	 */
	public void setIndicatorSubCode(String indicatorSubCode) {
		this.indicatorSubCode = indicatorSubCode;
	}

}
