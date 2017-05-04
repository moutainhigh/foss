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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/dto/AirAgencyQueryDto.java
 * 
 * FILE NAME        	: AirAgencyQueryDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;

/**
 * AirAgencyQueryDto
 * @author foss-meiying
 * @date 2012-11-15 下午4:19:30
 * @since
 * @version
 */
public class AirAgencyQueryDto implements Serializable{
	//序列
	private static final long serialVersionUID = 4016787819198550597L;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 收货人(收货客户名称)
	 */
	private String receiveCustomerName;
	/**
	 * 货客户电话
	 */
	private String receiveCustomerPhone;
	/**
	 * 收货人手机
	 */
	private String receiveCustomerMobilephone;
	/**
	 * 运单状态
	 */
	private String active;
	/**
	 * 结清状态
	 */
	private String settleStatus;
	/**
	 * 最终配载部门（判断是否为本部门）
	 */
	private String lastLoadOrgCode;
	
	/**
	 * 收货部门
	 */
	private String receiveOrgCode;
	/**
	 * 外发单号
	 */
	private String externalBillNo;
	/**
	 * 运输性质
	 */
	private String productCode;
	
	/***
	 * 是否中转外发
	 */
	private String transferExternal;

	/**
	 * 入库时间(起)
	 */
	private Date storageTimeBegin;
	/**
	 * 入库时间(止)
	 */
	private Date storageTimeEnd;
	/**
	 * 件数
	 */
	private Integer storageQty;
	/**
	 * 最后库存code
	 */
	private String endStockOrgCode;	
	/**
	 * 库区
	 */
	/**
	 * 快递零担库区
	 */
	private List<String> goodsAreaCodes;
	
	/**
	 * 流水号
	 */
	private String serialNo;
	
	/**
	 * 是否是一票多件
	 */
	private String isOneInMore;

  	public String getIsOneInMore() {
		return isOneInMore;
	}

	public void setIsOneInMore(String isOneInMore) {
		this.isOneInMore = isOneInMore;
	}
	
	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	
	public List<String> getGoodsAreaCodes() {
		return goodsAreaCodes;
	}

	public void setGoodsAreaCodes(List<String> goodsAreaCodes) {
		this.goodsAreaCodes = goodsAreaCodes;
	}
	private String goodsAreaCode;
	/**
	 * 审核状态
	 * 
	 */
	private String auditStatus;
	/**
	 * 到达未出库件数
	 */
	private Integer arriveNotoutGoodsQty;
	/**
	 * 到达时间(起)
	 */
	private Date arriveTimeBegin;
	/**
	 * 到达时间(止)
	 */
	private Date arriveTimeEnd;
	/**
	 * 运单状态
	 */
	private List<String> waybillStatus;
	//出库类型---空运交接出库
	private String inOutStockType;
	/*
	 * 航空正单的部门
	 */
	private String airCreateOrgCode;
	
	/**
	 * 运单处理类型
	 */
	private List<String> checkStatus;
	/**
	 * 最后库存code集合
	 */
	private List<String> endStockOrgCodes;
	/**
	 * 运单号
	 */
	private List<String> waybillNos;
	/*
	 *  快递代理理公司名称 
	 */
	private String expAirAgencySignOrgName;
	/**
	 * 快递代理公司编码
	 */
	private String expAirAgencySignOrgCode;
	/** 
	 * 签收件数
	 */
	private Integer signGoodsQty;
	/**
	 *  提货人名称
	 */
	private String deliverymanName;
	/** 
	 * 签收时间
	 */
	private Date signTime;
	/** 
	 * 签收情况
	 */
	private String signSituation;
	/** 
	 * 签收备注
	 */
	private String signNote;
	/**
	 * 保管费
	 */
	private BigDecimal storageCharge;
	/**
	 * 货物总件数
	 */
	private Integer goodsQtyTotal;
	/**
	 * 证件类型
	 */
	private String identifyType;
	
	/**
	 * 是否快递
	 * @return
	 */
	private String isExpress;
	/**
	 * 外场与外发交接时间(起)
	 */
	private Date successionTimingBegin;
	/**
	 * 外场与外发交接时间(止)
	 */
	private Date successionTimingEnd;
	/**
	 * 快递100签收单号信息推送原因编码
	 */
	private String reasonCode;

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public List<String> getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(List<String> checkStatus) {
		this.checkStatus = checkStatus;
	}

	/**
	 * Instantiates a new air agency query dto.
	 */
	public AirAgencyQueryDto() {
		super();
	}

	/**
	 * Instantiates a new air agency query dto.
	 * @param waybillNo the waybill no
	 * @param active the active
	 */
	public AirAgencyQueryDto(String waybillNo, String active) {
		super();
		this.waybillNo = waybillNo;
		this.active = active;
	}

	/**
	 * Gets the 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * Sets the 运单号.
	 *
	 * @param waybillNo the new 运单号
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * Gets the 收货人(收货客户名称).
	 *
	 * @return the 收货人(收货客户名称)
	 */
	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}

	/**
	 * Sets the 收货人(收货客户名称).
	 *
	 * @param receiveCustomerName the new 收货人(收货客户名称)
	 */
	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}

	/**
	 * Gets the 货客户电话.
	 *
	 * @return the 货客户电话
	 */
	public String getReceiveCustomerPhone() {
		return receiveCustomerPhone;
	}

	/**
	 * Sets the 货客户电话.
	 *
	 * @param receiveCustomerPhone the new 货客户电话
	 */
	public void setReceiveCustomerPhone(String receiveCustomerPhone) {
		this.receiveCustomerPhone = receiveCustomerPhone;
	}

	/**
	 * Gets the 收货人手机.
	 *
	 * @return the 收货人手机
	 */
	public String getReceiveCustomerMobilephone() {
		return receiveCustomerMobilephone;
	}

	/**
	 * Sets the 收货人手机.
	 *
	 * @param receiveCustomerMobilephone the new 收货人手机
	 */
	public void setReceiveCustomerMobilephone(String receiveCustomerMobilephone) {
		this.receiveCustomerMobilephone = receiveCustomerMobilephone;
	}

	/**
	 * Gets the 运单状态.
	 *
	 * @return the 运单状态
	 */
	public String getActive() {
		return active;
	}

	/**
	 * Sets the 运单状态.
	 *
	 * @param active the new 运单状态
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * Gets the 最终配载部门（判断是否为本部门）.
	 *
	 * @return the 最终配载部门（判断是否为本部门）
	 */
	public String getLastLoadOrgCode() {
		return lastLoadOrgCode;
	}

	/**
	 * Sets the 最终配载部门（判断是否为本部门）.
	 *
	 * @param lastLoadOrgCode the new 最终配载部门（判断是否为本部门）
	 */
	public void setLastLoadOrgCode(String lastLoadOrgCode) {
		this.lastLoadOrgCode = lastLoadOrgCode;
	}

	/**
	 * Gets the 外发单号.
	 *
	 * @return the 外发单号
	 */
	public String getExternalBillNo() {
		return externalBillNo;
	}

	/**
	 * Sets the 外发单号.
	 *
	 * @param externalBillNo the new 外发单号
	 */
	public void setExternalBillNo(String externalBillNo) {
		this.externalBillNo = externalBillNo;
	}

	/**
	 * Gets the 运输性质.
	 *
	 * @return the 运输性质
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * Sets the 运输性质.
	 *
	 * @param productCode the new 运输性质
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * Gets the 入库时间(起).
	 *
	 * @return the 入库时间(起)
	 */
	public Date getStorageTimeBegin() {
		return storageTimeBegin;
	}

	/**
	 * Sets the 入库时间(起).
	 *
	 * @param storageTimeBegin the new 入库时间(起)
	 */
	@DateFormat
	public void setStorageTimeBegin(Date storageTimeBegin) {
		this.storageTimeBegin = storageTimeBegin;
	}

	/**
	 * Gets the 入库时间(止).
	 *
	 * @return the 入库时间(止)
	 */
	public Date getStorageTimeEnd() {
		return storageTimeEnd;
	}

	/**
	 * Sets the 入库时间(止).
	 *
	 * @param storageTimeEnd the new 入库时间(止)
	 */
	@DateFormat
	public void setStorageTimeEnd(Date storageTimeEnd) {
		this.storageTimeEnd = storageTimeEnd;
	}

	/**
	 * Gets the 件数.
	 *
	 * @return the 件数
	 */
	public Integer getStorageQty() {
		return storageQty;
	}

	/**
	 * Sets the 件数.
	 *
	 * @param storageQty the new 件数
	 */
	public void setStorageQty(Integer storageQty) {
		this.storageQty = storageQty;
	}

	/**
	 * Gets the 结清状态.
	 *
	 * @return the 结清状态
	 */
	public String getSettleStatus() {
		return settleStatus;
	}
	
	/**
	 * Sets the 结清状态.
	 *
	 * @param settleStatus the new 结清状态
	 */
	public void setSettleStatus(String settleStatus) {
		this.settleStatus = settleStatus;
	}
	
	/**
	 * Gets the * 是否中转外发.
	 *
	 * @return the * 是否中转外发
	 */
	public String getTransferExternal() {
		return transferExternal;
	}
	
	/**
	 * Sets the * 是否中转外发.
	 *
	 * @param transferExternal the new * 是否中转外发
	 */
	public void setTransferExternal(String transferExternal) {
		this.transferExternal = transferExternal;
	}
	

	/**
	 * Gets the 最后库存code.
	 *
	 * @return the 最后库存code
	 */
	public String getEndStockOrgCode() {
		return endStockOrgCode;
	}

	/**
	 * Sets the 最后库存code.
	 *
	 * @param endStockOrgCode the new 最后库存code
	 */
	public void setEndStockOrgCode(String endStockOrgCode) {
		this.endStockOrgCode = endStockOrgCode;
	}

	/**
	 * Gets the 库区.
	 *
	 * @return the 库区
	 */
	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}

	/**
	 * Sets the 库区.
	 *
	 * @param goodsAreaCode the new 库区
	 */
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}

	/**
	 * Gets the 审核状态.
	 *
	 * @return the 审核状态
	 */
	public String getAuditStatus() {
		return auditStatus;
	}

	/**
	 * Sets the 审核状态.
	 *
	 * @param auditStatus the new 审核状态
	 */
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	/**
	 * Gets the 到达未出库件数.
	 *
	 * @return the 到达未出库件数
	 */
	public Integer getArriveNotoutGoodsQty() {
		return arriveNotoutGoodsQty;
	}

	/**
	 * Sets the 到达未出库件数.
	 *
	 * @param arriveNotoutGoodsQty the new 到达未出库件数
	 */
	public void setArriveNotoutGoodsQty(Integer arriveNotoutGoodsQty) {
		this.arriveNotoutGoodsQty = arriveNotoutGoodsQty;
	}

	public Date getArriveTimeBegin() {
		return arriveTimeBegin;
	}

	public void setArriveTimeBegin(Date arriveTimeBegin) {
		this.arriveTimeBegin = arriveTimeBegin;
	}

	public Date getArriveTimeEnd() {
		return arriveTimeEnd;
	}

	public void setArriveTimeEnd(Date arriveTimeEnd) {
		this.arriveTimeEnd = arriveTimeEnd;
	}

	public List<String> getWaybillStatus() {
		return waybillStatus;
	}

	public void setWaybillStatus(List<String> waybillStatus) {
		this.waybillStatus = waybillStatus;
	}

	public String getInOutStockType() {
		return inOutStockType;
	}

	public void setInOutStockType(String inOutStockType) {
		this.inOutStockType = inOutStockType;
	}

	public String getAirCreateOrgCode() {
		return airCreateOrgCode;
	}

	public void setAirCreateOrgCode(String airCreateOrgCode) {
		this.airCreateOrgCode = airCreateOrgCode;
	}

	public List<String> getEndStockOrgCodes() {
		return endStockOrgCodes;
	}

	public void setEndStockOrgCodes(List<String> endStockOrgCodes) {
		this.endStockOrgCodes = endStockOrgCodes;
	}

	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	public List<String> getWaybillNos() {
		return waybillNos;
	}

	public void setWaybillNos(List<String> waybillNos) {
		this.waybillNos = waybillNos;
	}

	public String getExpAirAgencySignOrgName() {
		return expAirAgencySignOrgName;
	}

	public void setExpAirAgencySignOrgName(String expAirAgencySignOrgName) {
		this.expAirAgencySignOrgName = expAirAgencySignOrgName;
	}

	public String getExpAirAgencySignOrgCode() {
		return expAirAgencySignOrgCode;
	}

	public void setExpAirAgencySignOrgCode(String expAirAgencySignOrgCode) {
		this.expAirAgencySignOrgCode = expAirAgencySignOrgCode;
	}

	public Integer getSignGoodsQty() {
		return signGoodsQty;
	}

	public void setSignGoodsQty(Integer signGoodsQty) {
		this.signGoodsQty = signGoodsQty;
	}

	public String getDeliverymanName() {
		return deliverymanName;
	}

	public void setDeliverymanName(String deliverymanName) {
		this.deliverymanName = deliverymanName;
	}

	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	public String getSignSituation() {
		return signSituation;
	}

	public void setSignSituation(String signSituation) {
		this.signSituation = signSituation;
	}

	public String getSignNote() {
		return signNote;
	}

	public void setSignNote(String signNote) {
		this.signNote = signNote;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BigDecimal getStorageCharge() {
		return storageCharge;
	}

	public void setStorageCharge(BigDecimal storageCharge) {
		this.storageCharge = storageCharge;
	}

	public String getIsExpress() {
		return isExpress;
	}

	public void setIsExpress(String isExpress) {
		this.isExpress = isExpress;
	}

	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	public String getIdentifyType() {
		return identifyType;
	}

	public void setIdentifyType(String identifyType) {
		this.identifyType = identifyType;
	}

	public Date getSuccessionTimingBegin() {
		return successionTimingBegin;
	}

	public void setSuccessionTimingBegin(Date successionTimingBegin) {
		this.successionTimingBegin = successionTimingBegin;
	}

	public Date getSuccessionTimingEnd() {
		return successionTimingEnd;
	}

	public void setSuccessionTimingEnd(Date successionTimingEnd) {
		this.successionTimingEnd = successionTimingEnd;
	}

	
}