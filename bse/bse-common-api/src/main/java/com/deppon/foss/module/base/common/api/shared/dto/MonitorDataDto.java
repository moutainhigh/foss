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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/shared/dto/MonitorDataDto.java
 * 
 * FILE NAME        	: MonitorDataDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-common-api
 * PACKAGE NAME: com.deppon.foss.module.base.common.api.shared.dto
 * FILE    NAME: MonitorDataDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.common.api.shared.dto;


/**
 * 指标数据DTO
 * 
 * @author ibm-zhuwei
 * @date 2013-2-27 下午2:15:14
 */
public class MonitorDataDto extends MonitorDataDimensionDto {

	/**
	 * 指标组
	 */
	private String indicatorGroup;

	/**
	 * 指标编码
	 */
	private String indicatorCode;

	/**
	 * 指标名称
	 */
	private String indicatorName;

	/**
	 * 序号
	 */
	private Integer seq;

	/**
	 * 指标值
	 */
	private Long indicatorValue;

	/**
	 * 动态指标公式
	 */
	private String formula;

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
