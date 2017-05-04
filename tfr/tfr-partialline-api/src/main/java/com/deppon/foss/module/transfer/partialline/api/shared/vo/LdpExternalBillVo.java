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
 *  DESCRIPTION   : 落地配外发管理
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/partialline/api/shared/vo/LdpExternalBillVo.java
 * 
 *  FILE NAME     :LdpExternalBillVo.java
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

import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpExternalBillDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpExternalBillWayBillInfoDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpHandoverBillDetailDto;

/**
 * 落地配Vo
 * 
 * @author liuzhaowei
 * @date 2013-07-16 上午9:18:36
 */
public class LdpExternalBillVo implements java.io.Serializable {

	private static final long serialVersionUID = -165940826818885556L;

	/**
	 * 落地配相关数据
	 */
	private LdpExternalBillDto dto;
	/**
	 * 落地配相关数据列表
	 */
	private List<LdpExternalBillDto> ldpExternalBillList = new ArrayList<LdpExternalBillDto>();
	/**
	 * 落地配相关的运单信息及代理信息
	 */
	private LdpExternalBillWayBillInfoDto billInfo;
	/**
	 * 已交接未录入的落地配运单
	 */
	private List<LdpExternalBillWayBillInfoDto> uninputBills = new ArrayList<LdpExternalBillWayBillInfoDto>();
	/**
	 * 是否验证运单号是否已录入
	 */
	private String validateWaybillNo;
	/**
	 * 审核落地配ID列表
	 */
	private List<String> auditIds;
	/**
	 * 未录入落地配列表
	 */
	private List<LdpHandoverBillDetailDto> uninputLdpExternalBill;
	/**
	 * 未录入落地配Dto
	 */
	private LdpHandoverBillDetailDto uninputLdpExternalBillDto;
	/**
	 * 未录入落地配运单号列表
	 */
	private List<String> waybillNoList;
	/**
	 * 批量生成落地配外发单失败的运单集合
	 */
	private String waybillNos;
	/**
	 * 269701--lln--20151022
	 * 一票多件，生成外发单可以部分生成
	 * 未录入落地配流水号列表
	 */
	private List<String> serialNoList;
	
		/**
	 *Excel导出文件头
	 */
	public String[] rowHeads;

	/**
	 * 获取 落地配相关数据.
	 * 
	 * @return the 落地配相关数据
	 */
	public LdpExternalBillDto getDto() {
		return dto;
	}

	/**
	 * 设置 落地配相关数据.
	 * 
	 * @param dto  落地配相关数据
	 */
	public void setDto(LdpExternalBillDto dto) {
		this.dto = dto;
	}

	/**
	 * 获取 落地配相关数据列表.
	 * 
	 * @return the 落地配相关数据列表
	 */
	public List<LdpExternalBillDto> getLdpExternalBillList() {
		return ldpExternalBillList;
	}

	/**
	 * 设置 落地配相关数据列表.
	 * 
	 * @param ldpExternalBillList  落地配相关数据列表
	 */
	public void setLdpExternalBillList(List<LdpExternalBillDto> ldpExternalBillList) {
		this.ldpExternalBillList = ldpExternalBillList;
	}

	/**
	 * 获取 落地配相关的运单信息及代理信息.
	 * 
	 * @return the 落地配相关的运单信息及代理信息
	 */
	public LdpExternalBillWayBillInfoDto getBillInfo() {
		return billInfo;
	}

	/**
	 * 设置 落地配相关的运单信息及代理信息.
	 * 
	 * @param billInfo
	 *            the new 落地配相关的运单信息及代理信息
	 */
	public void setBillInfo(LdpExternalBillWayBillInfoDto billInfo) {
		this.billInfo = billInfo;
	}

	/**
	 * 获取 已交接未录入的运单.
	 * 
	 * @return the 已交接未录入的运单
	 */
	public List<LdpExternalBillWayBillInfoDto> getUninputBills() {
		return uninputBills;
	}

	/**
	 * 设置 已交接未录入的运单.
	 * 
	 * @param uninputBills
	 */
	public void setUninputBills(List<LdpExternalBillWayBillInfoDto> uninputBills) {
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
	 * the new 是否验证运单号是否已录入
	 */
	public void setValidateWaybillNo(String validateWaybillNo) {
		this.validateWaybillNo = validateWaybillNo;
	}

	/**
	 * 获取 审核落地配外发单ID列表.
	 * 
	 * @return the 审核落地配外发单ID列表
	 */
	public List<String> getAuditIds() {
		return auditIds;
	}

	/**
	 * 设置 审核落地配外发单ID列表.
	 * 
	 * @param auditIds
	 * the new 审核落地配外发单ID列表
	 */
	public void setAuditIds(List<String> auditIds) {
		this.auditIds = auditIds;
	}

	/**
	 * 获取 未录入落地配外发单列表.
	 * 
	 * @return the 未录入落地配外发单列表
	 */
	public List<LdpHandoverBillDetailDto> getUninputLdpExternalBill() {
		return uninputLdpExternalBill;
	}

	/**
	 * 设置 未录入落地配外发单列表.
	 * 
	 * @param uninputedpartials 未录入落地配外发单列表
	 */
	public void setUninputLdpExternalBill(List<LdpHandoverBillDetailDto> uninputLdpExternalBill) {
		this.uninputLdpExternalBill = uninputLdpExternalBill;
	}

	/**
	 * 获取 未录入落地配外发单uninputLdpExternalBillDto.
	 * 
	 * @return  未录入落地配外发单uninputLdpExternalBillDto
	 */
	public LdpHandoverBillDetailDto getUninputLdpExternalBillDto() {
		return uninputLdpExternalBillDto;
	}

	/**
	 * 设置 未录入落地配外发单Dto.
	 * 
	 * @param uninputLdpExternalBillDto  未录入落地配外发单Dto
	 */
	public void setUninputLdpExternalBillDto(LdpHandoverBillDetailDto uninputLdpExternalBillDto) {
		this.uninputLdpExternalBillDto = uninputLdpExternalBillDto;
	}
	
	public List<String> getWaybillNoList() {
		return waybillNoList;
	}

	public void setWaybillNoList(List<String> waybillNoList) {
		this.waybillNoList = waybillNoList;
	}

	public String getWaybillNos() {
		return waybillNos;
	}

	public void setWaybillNos(String waybillNos) {
		this.waybillNos = waybillNos;
	}

	public String[] getRowHeads() {
		return rowHeads;
	}

	public void setRowHeads(String[] rowHeads) {
		this.rowHeads = rowHeads;
	}
	/**
	 * 未录入落地配流水号列表
	 * @return the serialNoList
	 */
	public List<String> getSerialNoList() {
		return serialNoList;
	}

	/**
	 * 未录入落地配流水号列表
	 * @param serialNoList the serialNoList to set
	 */
	public void setSerialNoList(List<String> serialNoList) {
		this.serialNoList = serialNoList;
	}

}