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
 *  PROJECT NAME  : tfr-partialline-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 偏线外发管理
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/partialline/api/shared/vo/ExternalBillVo.java
 * 
 *  FILE NAME     :ExternalBillVo.java
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
package com.deppon.foss.module.transfer.partialline.api.shared.vo;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillWayBillInfoDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.HandoverBillDetailDto;

/**
 * 偏线Vo
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-10-12 上午9:18:36
 */
public class ExternalBillVo implements java.io.Serializable {

	private static final long serialVersionUID = -165944826818885556L;

	/**
	 * 偏线相关数据
	 */
	private ExternalBillDto dto;
	/**
	 * 偏线相关数据列表
	 */
	private List<ExternalBillDto> externalBillList = new ArrayList<ExternalBillDto>();
	/**
	 * 偏线相关的运单信息及代理信息
	 */
	private ExternalBillWayBillInfoDto billInfo;
	/**
	 * 已交接未录入的运单
	 */
	private List<ExternalBillWayBillInfoDto> uninputBills = new ArrayList<ExternalBillWayBillInfoDto>();
	/**
	 * 是否验证运单号是否已录入
	 */
	private String validateWaybillNo;
	/**
	 * 审核偏线ID列表
	 */
	private List<String> auditIds;
	/**
	 * 未录入偏线列表
	 */
	private List<HandoverBillDetailDto> uninputedpartials;
	/**
	 * 未录入Dto
	 */
	private HandoverBillDetailDto uninputDto;
	/**
	 *Excel导出文件头
	 */
	public String[] rowHeads;

	/**
	 * 获取 偏线相关数据.
	 * 
	 * @return the 偏线相关数据
	 */
	public ExternalBillDto getDto() {
		return dto;
	}

	/**
	 * 设置 偏线相关数据.
	 * 
	 * @param dto
	 *            the new 偏线相关数据
	 */
	public void setDto(ExternalBillDto dto) {
		this.dto = dto;
	}

	/**
	 * 获取 偏线相关数据列表.
	 * 
	 * @return the 偏线相关数据列表
	 */
	public List<ExternalBillDto> getExternalBillList() {
		return externalBillList;
	}

	/**
	 * 设置 偏线相关数据列表.
	 * 
	 * @param externalBillList
	 *            the new 偏线相关数据列表
	 */
	public void setExternalBillList(List<ExternalBillDto> externalBillList) {
		this.externalBillList = externalBillList;
	}

	/**
	 * 获取 偏线相关的运单信息及代理信息.
	 * 
	 * @return the 偏线相关的运单信息及代理信息
	 */
	public ExternalBillWayBillInfoDto getBillInfo() {
		return billInfo;
	}

	/**
	 * 设置 偏线相关的运单信息及代理信息.
	 * 
	 * @param billInfo
	 *            the new 偏线相关的运单信息及代理信息
	 */
	public void setBillInfo(ExternalBillWayBillInfoDto billInfo) {
		this.billInfo = billInfo;
	}

	/**
	 * 获取 已交接未录入的运单.
	 * 
	 * @return the 已交接未录入的运单
	 */
	public List<ExternalBillWayBillInfoDto> getUninputBills() {
		return uninputBills;
	}

	/**
	 * 设置 已交接未录入的运单.
	 * 
	 * @param uninputBills
	 *            the new 已交接未录入的运单
	 */
	public void setUninputBills(List<ExternalBillWayBillInfoDto> uninputBills) {
		this.uninputBills = uninputBills;
	}

	/**
	 * 获取 是否验证运单号是否已录入.
	 * 
	 * @return the 是否验证运单号是否已录入
	 */
	public String getValidateWaybillNo() {
		return validateWaybillNo;
	}

	/**
	 * 设置 是否验证运单号是否已录入.
	 * 
	 * @param validateWaybillNo
	 *            the new 是否验证运单号是否已录入
	 */
	public void setValidateWaybillNo(String validateWaybillNo) {
		this.validateWaybillNo = validateWaybillNo;
	}

	/**
	 * 获取 审核偏线ID列表.
	 * 
	 * @return the 审核偏线ID列表
	 */
	public List<String> getAuditIds() {
		return auditIds;
	}

	/**
	 * 设置 审核偏线ID列表.
	 * 
	 * @param auditIds
	 *            the new 审核偏线ID列表
	 */
	public void setAuditIds(List<String> auditIds) {
		this.auditIds = auditIds;
	}

	/**
	 * 获取 未录入偏线列表.
	 * 
	 * @return the 未录入偏线列表
	 */
	public List<HandoverBillDetailDto> getUninputedpartials() {
		return uninputedpartials;
	}

	/**
	 * 设置 未录入偏线列表.
	 * 
	 * @param uninputedpartials
	 *            the new 未录入偏线列表
	 */
	public void setUninputedpartials(List<HandoverBillDetailDto> uninputedpartials) {
		this.uninputedpartials = uninputedpartials;
	}

	/**
	 * 获取 未录入Dto.
	 * 
	 * @return the 未录入Dto
	 */
	public HandoverBillDetailDto getUninputDto() {
		return uninputDto;
	}

	/**
	 * 设置 未录入Dto.
	 * 
	 * @param uninputDto
	 *            the new 未录入Dto
	 */
	public void setUninputDto(HandoverBillDetailDto uninputDto) {
		this.uninputDto = uninputDto;
	}

	public String[] getRowHeads() {
		return rowHeads;
	}

	public void setRowHeads(String[] rowHeads) {
		this.rowHeads = rowHeads;
	}
	

}