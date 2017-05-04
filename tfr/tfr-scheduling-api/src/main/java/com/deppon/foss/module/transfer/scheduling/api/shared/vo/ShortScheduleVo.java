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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/vo/ShortScheduleVo.java
 * 
 *  FILE NAME     :ShortScheduleVo.java
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
/*
 * PROJECT NAME: tfr-scheduling-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.api.shared.vo
 * FILE    NAME: ShortScheduleVo.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.api.shared.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.GridHeaderDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckScheduleGridDto;

/**
 * 短途排班Vo
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-10-25 上午9:17:49
 */
public class ShortScheduleVo implements java.io.Serializable {

	private static final long serialVersionUID = -6935383511052871349L;

	/**
	 * 表格列头
	 */
	public List<GridHeaderDto> gridHeaderFields;
	/**
	 * 查询制定列表(较复杂的视图)
	 */
	public List<TruckScheduleGridDto> tsDtos;
	/**
	 * 排班计划实体
	 */
	public TruckSchedulingEntity tsEntity;
	/**
	 * 查询排班DTO
	 */
	public SimpleTruckScheduleDto simDto;
	/**
	 * 查询排班列表(简单的视图)或者用于查询计划任务
	 */
	public List<SimpleTruckScheduleDto> simDtos;
	/**
	 * 导入错误信息
	 */
	public Map<String, String> impErros = new HashMap<String, String>();
	/**
	 * 不存在的司机code
	 */
	public List<String> unexistDriver = new ArrayList<String>();
	/**
	 * 导入条数
	 */
	public int importTotal;

	/**
	 * 错误信息
	 */
	public StringBuffer errorMsgs;
	
	/**
	 * 顶级车队Code
	 */
	private String topFleetOrgCode;
	/**
	 * 排班总条数
	 * 
	 * */
	private long  tatolScheduling;
	
	

	public long getTatolScheduling() {
		return tatolScheduling;
	}

	public void setTatolScheduling(long tatolScheduling) {
		this.tatolScheduling = tatolScheduling;
	}

	/**
	 * 获取 表格列头.
	 * 
	 * @return the 表格列头
	 */
	public List<GridHeaderDto> getGridHeaderFields() {
		return gridHeaderFields;
	}

	/**
	 * 设置 表格列头.
	 * 
	 * @param gridHeaderFields
	 *            the new 表格列头
	 */
	public void setGridHeaderFields(List<GridHeaderDto> gridHeaderFields) {
		this.gridHeaderFields = gridHeaderFields;
	}

	/**
	 * 获取 排班计划实体.
	 * 
	 * @return the 排班计划实体
	 */
	public TruckSchedulingEntity getTsEntity() {
		return tsEntity;
	}

	/**
	 * 设置 排班计划实体.
	 * 
	 * @param tsEntity
	 *            the new 排班计划实体
	 */
	public void setTsEntity(TruckSchedulingEntity tsEntity) {
		this.tsEntity = tsEntity;
	}

	/**
	 * 获取 查询制定列表(较复杂的视图).
	 * 
	 * @return the 查询制定列表(较复杂的视图)
	 */
	public List<TruckScheduleGridDto> getTsDtos() {
		return tsDtos;
	}

	/**
	 * 设置 查询制定列表(较复杂的视图).
	 * 
	 * @param tsDtos
	 *            the new 查询制定列表(较复杂的视图)
	 */
	public void setTsDtos(List<TruckScheduleGridDto> tsDtos) {
		this.tsDtos = tsDtos;
	}

	/**
	 * 获取 查询排班DTO.
	 * 
	 * @return the 查询排班DTO
	 */
	public SimpleTruckScheduleDto getSimDto() {
		return simDto;
	}

	/**
	 * 设置 查询排班DTO.
	 * 
	 * @param simDto
	 *            the new 查询排班DTO
	 */
	public void setSimDto(SimpleTruckScheduleDto simDto) {
		this.simDto = simDto;
	}

	/**
	 * 获取 查询排班列表(简单的视图)或者用于查询计划任务.
	 * 
	 * @return the 查询排班列表(简单的视图)或者用于查询计划任务
	 */
	public List<SimpleTruckScheduleDto> getSimDtos() {
		return simDtos;
	}

	/**
	 * 设置 查询排班列表(简单的视图)或者用于查询计划任务.
	 * 
	 * @param simDtos
	 *            the new 查询排班列表(简单的视图)或者用于查询计划任务
	 */
	public void setSimDtos(List<SimpleTruckScheduleDto> simDtos) {
		this.simDtos = simDtos;
	}

	/**
	 * 获取 导入错误信息.
	 * 
	 * @return the 导入错误信息
	 */
	public Map<String, String> getImpErros() {
		return impErros;
	}

	/**
	 * 设置 导入错误信息.
	 * 
	 * @param impErros
	 *            the new 导入错误信息
	 */
	public void setImpErros(Map<String, String> impErros) {
		this.impErros = impErros;
	}

	/**
	 * 获取 不存在的司机code.
	 * 
	 * @return the 不存在的司机code
	 */
	public List<String> getUnexistDriver() {
		return unexistDriver;
	}

	/**
	 * 设置 不存在的司机code.
	 * 
	 * @param unexistDriver
	 *            the new 不存在的司机code
	 */
	public void setUnexistDriver(List<String> unexistDriver) {
		this.unexistDriver = unexistDriver;
	}

	/**
	 * 获取 导入条数.
	 * 
	 * @return the 导入条数
	 */
	public int getImportTotal() {
		return importTotal;
	}

	/**
	 * 设置 导入条数.
	 * 
	 * @param importTotal
	 *            the new 导入条数
	 */
	public void setImportTotal(int importTotal) {
		this.importTotal = importTotal;
	}

	public StringBuffer getErrorMsgs() {
		return errorMsgs;
	}

	public void setErrorMsgs(StringBuffer errorMsgs) {
		this.errorMsgs = errorMsgs;
	}

	/**   
	 * topFleetOrgCode   
	 *   
	 * @return  the topFleetOrgCode   
	 */
	
	public String getTopFleetOrgCode() {
		return topFleetOrgCode;
	}

	/**   
	 * @param topFleetOrgCode the topFleetOrgCode to set
	 * Date:2013-6-3下午9:20:32
	 */
	public void setTopFleetOrgCode(String topFleetOrgCode) {
		this.topFleetOrgCode = topFleetOrgCode;
	}
}