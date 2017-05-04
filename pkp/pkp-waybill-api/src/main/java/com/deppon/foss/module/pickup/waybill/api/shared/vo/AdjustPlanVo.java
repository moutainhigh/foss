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
 * PROJECT NAME	: pkp-waybill-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/shared/vo/AdjustPlanVo.java
 * 
 * FILE NAME        	: AdjustPlanVo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.pickup.waybill.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.waybill.api.shared.dto.AdjustPlanResultDto;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.AdjustPlanSearcherDto;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.ChangeNodeDto;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.OrgAdministrativeInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcChangeDetailEntity;

/***
 * 
 * 手工执行计划Vo
 * 
 * @author 105089-foss-yangtong
 * @date 2012-10-18 下午1:58:49
 * @since
 * @version
 */
public class AdjustPlanVo implements Serializable {

	/**
	 * 序列化标识 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 签收出库查询条件dto
	 */
	private AdjustPlanSearcherDto adjustPlanSearcherDto;
	/**
	 * 执行计划结果List
	 */
	private List<AdjustPlanResultDto> adjustPlanResultDtoList;
	/**
	 * 查询 Dto
	 */
	private AdjustPlanResultDto adjustPlanResultDto;
	/**
	 * 更改单明细(变更信息明细)
	 * 
	 */
	private List<WaybillRfcChangeDetailEntity> waybillRfcChangeDetailList;
	/**
	 * 变更节点List
	 */
	private List<ChangeNodeDto> changeNodeDtoList;
	/**
	 * 查询 变更节点
	 */
	private ChangeNodeDto changeNodeDto;
	/**
	 * 执行节点List
	 */
	private List<OrgAdministrativeInfoDto> orgList;

	/**
	 * 获取 签收出库查询条件dto.
	 * 
	 * @return the 签收出库查询条件dto
	 */
	public AdjustPlanSearcherDto getAdjustPlanSearcherDto() {
		return adjustPlanSearcherDto;
	}

	/**
	 * 设置 签收出库查询条件dto.
	 * 
	 * @param adjustPlanSearcherDto the new 签收出库查询条件dto
	 */
	public void setAdjustPlanSearcherDto(AdjustPlanSearcherDto adjustPlanSearcherDto) {
		this.adjustPlanSearcherDto = adjustPlanSearcherDto;
	}

	/**
	 * 获取 执行计划结果List.
	 * 
	 * @return the 执行计划结果List
	 */
	public List<AdjustPlanResultDto> getAdjustPlanResultDtoList() {
		return adjustPlanResultDtoList;
	}

	/**
	 * 设置 执行计划结果List.
	 * 
	 * @param adjustPlanResultDtoList the new 执行计划结果List
	 */
	public void setAdjustPlanResultDtoList(List<AdjustPlanResultDto> adjustPlanResultDtoList) {
		this.adjustPlanResultDtoList = adjustPlanResultDtoList;
	}

	/**
	 * 获取 更改单明细(变更信息明细).
	 * 
	 * @return the 更改单明细(变更信息明细)
	 */
	public List<WaybillRfcChangeDetailEntity> getWaybillRfcChangeDetailList() {
		return waybillRfcChangeDetailList;
	}

	/**
	 * 设置 更改单明细(变更信息明细).
	 * 
	 * @param waybillRfcChangeDetailList the new 更改单明细(变更信息明细)
	 */
	public void setWaybillRfcChangeDetailList(List<WaybillRfcChangeDetailEntity> waybillRfcChangeDetailList) {
		this.waybillRfcChangeDetailList = waybillRfcChangeDetailList;
	}

	/**
	 * 获取 查询 Dto.
	 * 
	 * @return the 查询 Dto
	 */
	public AdjustPlanResultDto getAdjustPlanResultDto() {
		return adjustPlanResultDto;
	}

	/**
	 * 设置 查询 Dto.
	 * 
	 * @param adjustPlanResultDto the new 查询 Dto
	 */
	public void setAdjustPlanResultDto(AdjustPlanResultDto adjustPlanResultDto) {
		this.adjustPlanResultDto = adjustPlanResultDto;
	}

	/**
	 * 获取 变更节点List.
	 * 
	 * @return the 变更节点List
	 */
	public List<ChangeNodeDto> getChangeNodeDtoList() {
		return changeNodeDtoList;
	}

	/**
	 * 设置 变更节点List.
	 * 
	 * @param changeNodeDtoList the new 变更节点List
	 */
	public void setChangeNodeDtoList(List<ChangeNodeDto> changeNodeDtoList) {
		this.changeNodeDtoList = changeNodeDtoList;
	}

	/**
	 * 获取 查询 变更节点.
	 * 
	 * @return the 查询 变更节点
	 */
	public ChangeNodeDto getChangeNodeDto() {
		return changeNodeDto;
	}

	/**
	 * 设置 查询 变更节点.
	 * 
	 * @param changeNodeDto the new 查询 变更节点
	 */
	public void setChangeNodeDto(ChangeNodeDto changeNodeDto) {
		this.changeNodeDto = changeNodeDto;
	}

	/**
	 * 获取 执行节点List.
	 * 
	 * @return the 执行节点List
	 */
	public List<OrgAdministrativeInfoDto> getOrgList() {
		return orgList;
	}

	/**
	 * 设置 执行节点List.
	 * 
	 * @param orgList the new 执行节点List
	 */
	public void setOrgList(List<OrgAdministrativeInfoDto> orgList) {
		this.orgList = orgList;
	}

}