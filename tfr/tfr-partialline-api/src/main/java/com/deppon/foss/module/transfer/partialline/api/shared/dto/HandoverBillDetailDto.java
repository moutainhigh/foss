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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/partialline/api/shared/dto/HandoverBillDetailDto.java
 * 
 *  FILE NAME     :HandoverBillDetailDto.java
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
package com.deppon.foss.module.transfer.partialline.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;

/**
 * 交接单Dto
 * 
 * @author dp-zhongyubing
 * @date 2012-12-25 下午6:18:20
 */
public class HandoverBillDetailDto implements java.io.Serializable {

	private static final long serialVersionUID = 8011126023267418484L;

	/**
	 * T_OPT_HANDOVERBILL_DETAIL.ID 交接明细ID
	 */
	private String detailId;
	/**
	 * T_OPT_HANDOVERBILL_DETAIL.WAYBILL_NO 运单号
	 */
	private String waybillNo;
	/**
	 * T_OPT_HANDOVERBILL_DETAIL.HANDOVER_NO 交接单号
	 */
	private String handoverNo;
	/**
	 * T_OPT_HANDOVERBILL_DETAIL.HANDOVER_GOODS_QTY 件数
	 */
	private BigDecimal handoverGoodsQty;
	/**
	 * T_OPT_HANDOVERBILL_DETAIL.HANDOVER_WEIGHT 重量（公斤）
	 */
	private BigDecimal handoverWeight;
	/**
	 * T_OPT_HANDOVERBILL_DETAIL.HANDOVER_VOLUME 体积（方）
	 */
	private BigDecimal handoverVolume;
	/**
	 * T_OPT_HANDOVERBILL_DETAIL.DEST_ORG_NAME 外发代理
	 */
	private String destOrgName;
	/**
	 * T_OPT_HANDOVERBILL_DETAIL.DEST_ORG_CODE 外发代理COde
	 */
	private String destOrgCode;
	/**
	 * 交接类型
	 */
	private String handoverType;
	/**
	 * T_OPT_HANDOVERBILL_DETAIL.CREATE_TIME 外发交接时间
	 */
	private Date handoverTime;
	/**
	 * 外发交接时间从
	 */
	private String handoverTimeFrom;
	/**
	 * 外发交接时间到
	 */
	private String handoverTimeTo;
	/**
	 * 审核状态
	 */
	private Integer auditStatus;
	/**
	 * 是否中转外发
	 */
	private String isTransferExternal;
	/**
	 * 状态列表
	 */
	private List<String> list;
	/**
	 * 交接单状态
	 */
	private List<Integer> billStatuslist;

	/**
	 * 查询过滤部门编码
	 */
	private String filterOrgCode;
	private List<String> orgCodes;

	/**
	 * 获取 t_OPT_HANDOVERBILL_DETAIL.
	 * 
	 * @return the t_OPT_HANDOVERBILL_DETAIL
	 */
	public String getDetailId() {
		return detailId;
	}

	/**
	 * 设置 t_OPT_HANDOVERBILL_DETAIL.
	 * 
	 * @param detailId
	 *            the new t_OPT_HANDOVERBILL_DETAIL
	 */
	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	/**
	 * 获取 t_OPT_HANDOVERBILL_DETAIL.
	 * 
	 * @return the t_OPT_HANDOVERBILL_DETAIL
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * 设置 t_OPT_HANDOVERBILL_DETAIL.
	 * 
	 * @param waybillNo
	 *            the new t_OPT_HANDOVERBILL_DETAIL
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * 获取 t_OPT_HANDOVERBILL_DETAIL.
	 * 
	 * @return the t_OPT_HANDOVERBILL_DETAIL
	 */
	public String getHandoverNo() {
		return handoverNo;
	}

	/**
	 * 设置 t_OPT_HANDOVERBILL_DETAIL.
	 * 
	 * @param handoverNo
	 *            the new t_OPT_HANDOVERBILL_DETAIL
	 */
	public void setHandoverNo(String handoverNo) {
		this.handoverNo = handoverNo;
	}

	/**
	 * 获取 t_OPT_HANDOVERBILL_DETAIL.
	 * 
	 * @return the t_OPT_HANDOVERBILL_DETAIL
	 */
	public BigDecimal getHandoverGoodsQty() {
		return handoverGoodsQty;
	}

	/**
	 * 设置 t_OPT_HANDOVERBILL_DETAIL.
	 * 
	 * @param handoverGoodsQty
	 *            the new t_OPT_HANDOVERBILL_DETAIL
	 */
	public void setHandoverGoodsQty(BigDecimal handoverGoodsQty) {
		this.handoverGoodsQty = handoverGoodsQty;
	}

	/**
	 * 获取 t_OPT_HANDOVERBILL_DETAIL.
	 * 
	 * @return the t_OPT_HANDOVERBILL_DETAIL
	 */
	public BigDecimal getHandoverWeight() {
		return handoverWeight;
	}

	/**
	 * 设置 t_OPT_HANDOVERBILL_DETAIL.
	 * 
	 * @param handoverWeight
	 *            the new t_OPT_HANDOVERBILL_DETAIL
	 */
	public void setHandoverWeight(BigDecimal handoverWeight) {
		this.handoverWeight = handoverWeight;
	}

	/**
	 * 获取 t_OPT_HANDOVERBILL_DETAIL.
	 * 
	 * @return the t_OPT_HANDOVERBILL_DETAIL
	 */
	public BigDecimal getHandoverVolume() {
		return handoverVolume;
	}

	/**
	 * 设置 t_OPT_HANDOVERBILL_DETAIL.
	 * 
	 * @param handoverVolume
	 *            the new t_OPT_HANDOVERBILL_DETAIL
	 */
	public void setHandoverVolume(BigDecimal handoverVolume) {
		this.handoverVolume = handoverVolume;
	}

	/**
	 * 获取 t_OPT_HANDOVERBILL_DETAIL.
	 * 
	 * @return the t_OPT_HANDOVERBILL_DETAIL
	 */
	public String getDestOrgName() {
		return destOrgName;
	}

	/**
	 * 设置 t_OPT_HANDOVERBILL_DETAIL.
	 * 
	 * @param destOrgName
	 *            the new t_OPT_HANDOVERBILL_DETAIL
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	/**
	 * 获取 t_OPT_HANDOVERBILL_DETAIL.
	 * 
	 * @return the t_OPT_HANDOVERBILL_DETAIL
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}

	/**
	 * 设置 t_OPT_HANDOVERBILL_DETAIL.
	 * 
	 * @param destOrgCode
	 *            the new t_OPT_HANDOVERBILL_DETAIL
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	/**
	 * 获取 t_OPT_HANDOVERBILL_DETAIL.
	 * 
	 * @return the t_OPT_HANDOVERBILL_DETAIL
	 */
	@DateFormat(formate = "yyyy-MM-dd")
	public Date getHandoverTime() {
		return handoverTime;
	}

	/**
	 * 设置 t_OPT_HANDOVERBILL_DETAIL.
	 * 
	 * @param handoverTime
	 *            the new t_OPT_HANDOVERBILL_DETAIL
	 */
	@DateFormat(formate = "yyyy-MM-dd")
	public void setHandoverTime(Date handoverTime) {
		this.handoverTime = handoverTime;
	}

	/**
	 * 获取 外发交接时间从.
	 * 
	 * @return the 外发交接时间从
	 */
	public String getHandoverTimeFrom() {
		return handoverTimeFrom;
	}

	/**
	 * 设置 外发交接时间从.
	 * 
	 * @param handoverTimeFrom
	 *            the new 外发交接时间从
	 */
	public void setHandoverTimeFrom(String handoverTimeFrom) {
		this.handoverTimeFrom = handoverTimeFrom;
	}

	/**
	 * 获取 外发交接时间到.
	 * 
	 * @return the 外发交接时间到
	 */
	public String getHandoverTimeTo() {
		return handoverTimeTo;
	}

	/**
	 * 设置 外发交接时间到.
	 * 
	 * @param handoverTimeTo
	 *            the new 外发交接时间到
	 */
	public void setHandoverTimeTo(String handoverTimeTo) {
		this.handoverTimeTo = handoverTimeTo;
	}

	/**
	 * 获取 审核状态.
	 * 
	 * @return the 审核状态
	 */
	public Integer getAuditStatus() {
		return auditStatus;
	}

	/**
	 * 设置 审核状态.
	 * 
	 * @param auditStatus
	 *            the new 审核状态
	 */
	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	/**
	 * 获取 交接类型.
	 * 
	 * @return the 交接类型
	 */
	public String getHandoverType() {
		return handoverType;
	}

	/**
	 * 设置 交接类型.
	 * 
	 * @param handoverType
	 *            the new 交接类型
	 */
	public void setHandoverType(String handoverType) {
		this.handoverType = handoverType;
	}

	/**
	 * 获取 是否中转外发.
	 * 
	 * @return the 是否中转外发
	 */
	public String getIsTransferExternal() {
		return isTransferExternal;
	}

	/**
	 * 设置 是否中转外发.
	 * 
	 * @param isTransferExternal
	 *            the new 是否中转外发
	 */
	public void setIsTransferExternal(String isTransferExternal) {
		this.isTransferExternal = isTransferExternal;
	}

	/**
	 * 获取 状态列表.
	 * 
	 * @return the 状态列表
	 */
	public List<String> getList() {
		return list;
	}

	/**
	 * 设置 状态列表.
	 * 
	 * @param list
	 *            the new 状态列表
	 */
	public void setList(List<String> list) {
		this.list = list;
	}

	/**
	 * 获取 交接单状态.
	 * 
	 * @return the 交接单状态
	 */
	public List<Integer> getBillStatuslist() {
		return billStatuslist;
	}

	/**
	 * 设置 交接单状态.
	 * 
	 * @param billStatuslist
	 *            the new 交接单状态
	 */
	public void setBillStatuslist(List<Integer> billStatuslist) {
		this.billStatuslist = billStatuslist;
	}

	public String getFilterOrgCode() {
		return filterOrgCode;
	}

	public void setFilterOrgCode(String filterOrgCode) {
		this.filterOrgCode = filterOrgCode;
	}

	public List<String> getOrgCodes() {
		return orgCodes;
	}

	public void setOrgCodes(List<String> orgCodes) {
		this.orgCodes = orgCodes;
	}
	
	

}