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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/shared/domain/MonitorIndicatorEntity.java
 * 
 * FILE NAME        	: MonitorIndicatorEntity.java
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
 * 监控指标
 * 
 * @author ibm-zhuwei
 * @date 2013-2-1 下午4:31:57
 */
public class MonitorIndicatorEntity extends BaseEntity {
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1000526555109188156L;

	/**
	 * 指标编码
	 */
	private String indicatorCode;

	/**
	 * 指标名称
	 */
	private String indicatorName;

	/**
	 * 指标类型
	 */
	private String indicatorType;

	/**
	 * 指标组
	 */
	private String indicatorGroup;

	/**
	 * 指标类别
	 */
	private String indicatorCategory;

	/**
	 * 备注
	 */
	private String notes;

	/**
	 * 顺序
	 */
	private Integer seq;

	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	/**
	 * 监控开关
	 */
	private String monitorFlag;
	
	/**
	 * 公式
	 */
	private String formula;

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
	 * @return indicatorName
	 */
	public String getIndicatorName() {
		return indicatorName;
	}

	/**
	 * @param indicatorName
	 */
	public void setIndicatorName(String indicatorName) {
		this.indicatorName = indicatorName;
	}

	/**
	 * @return indicatorType
	 */
	public String getIndicatorType() {
		return indicatorType;
	}

	/**
	 * @param indicatorType
	 */
	public void setIndicatorType(String indicatorType) {
		this.indicatorType = indicatorType;
	}

	/**
	 * @return indicatorGroup
	 */
	public String getIndicatorGroup() {
		return indicatorGroup;
	}

	/**
	 * @param indicatorGroup
	 */
	public void setIndicatorGroup(String indicatorGroup) {
		this.indicatorGroup = indicatorGroup;
	}

	/**
	 * @return notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return seq
	 */
	public Integer getSeq() {
		return seq;
	}

	/**
	 * @param seq
	 */
	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active
	 */
	public void setActive(String active) {
		this.active = active;
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
	 * @return modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return monitorFlag
	 */
	public String getMonitorFlag() {
		return monitorFlag;
	}

	/**
	 * @param monitorFlag
	 */
	public void setMonitorFlag(String monitorFlag) {
		this.monitorFlag = monitorFlag;
	}
	
	/**
	 * @return indicatorCategory
	 */
	public String getIndicatorCategory() {
		return indicatorCategory;
	}

	/**
	 * @param indicatorCategory
	 */
	public void setIndicatorCategory(String indicatorCategory) {
		this.indicatorCategory = indicatorCategory;
	}
	
	/**
	 * @return formula
	 */
	public String getFormula() {
		return formula;
	}
	
	/**
	 * @param formula
	 */
	public void setFormula(String formula) {
		this.formula = formula;
	}

}
