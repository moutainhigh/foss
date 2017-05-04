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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/domain/WaybillSignResultEntity.java
 * 
 * FILE NAME        	: WaybillSignResultEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 运单签收结果 ----签收信息
 * @author foss-meiying
 * @date 2012-10-16 上午10:13:42
 * @since
 * @version
 */
public class VTSWaybillSignResultEntity extends BaseEntity {
	/**
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *  运单号
	 */
	private String waybillNo;
	/**
	 *  签收情况
	 */
	private String signSituation;
	/**
	 *  提货人名称（签收人）
	 */
	private String deliverymanName;
	/**
	 *  签收人类型
	 */
	private String deliverymanType;
	/**
	 *  证件类型
	 */
	private String identifyType;
	/**
	 *  证件号码
	 */
	private String identifyCode;
	/**
	 *  结清状态
	 */
	private String settleStatus;
	/**
	 *  签收件数
	 */
	private Integer signGoodsQty;
	/**
	 *  签收备注
	 */
	private String signNote;
	/**
	 *  签收时间
	 */
	private Date signTime;
	/**
	 *  生效时间
	 */
	private Date createTime;
	/**
	 *  时效时间
	 */
	private Date modifyTime;
	/**
	 *  是否有效
	 */
	private String active;
	/**
	 *  是否PDA签到
	 */
	private String isPdaSign;
	/**
	 *  签收状态
	 */
	private String signStatus;
	/**
	 *  代理编码
	 */
	private String agentCode;
	/**
	 *  是否审批中
	 */
	private String isRfcing;
	/**
	 * 送货时间
	 */
	private Date deliverDate;
	/**
	 * 到达时间
	 */
    private Date arriveTime;
    /**
     * 提货方式
     */
    private String receiveMethod;
    /**
     * 签收部门编码
     */
    private String createOrgCode;
    /**
     * 签收部门名称
     */
    private String createOrgName;
    /**
     * 操作人
     */
    private String creator;
    /**
     * 操作人编码
     */
    private String creatorCode;
    //lizhiguo--开始
    /**
     * 运单货物件数
     */
    private Integer signCount;
    //lizhiguo-结束
    
    
    //-------------快递新增字段
	/**
	 * 快递员code
	 */
	private String expressEmpCode;
	/**
	 * 快递员名称
	 */
	private String expressEmpName;

	/**
	 * 快递点部CODE
	 */
	private String expressOrgCode;

	/**
	 * 快递点部名称
	 */
	private String expressOrgName;
    
	/**
	 * 到付金额
	 */
	private BigDecimal toPayAmount;
	
	/**
	 * 代收货款
	 */
	private BigDecimal codAmount;
	
	/**
	 * 代收货款--付款方式 
	 * 	（现金、临欠、月结、银行卡、支票、电汇）
	 */
	private String codPaymentType;
	
	/**
	 * 银行交易流水号--代收货款流水号
	 */
	private String codBankTradeSerail;
	
	
	/**
	 * 合并金额
	 */
	private BigDecimal totalAmount;
	
	
	/**
	 * 合并--付款方式 
	 * 	（现金、临欠、月结、银行卡、支票、电汇）
	 */
	private String totalPaymentType;
	
	/***
     * 短少件数量和对应的流水号 
     */
	private String goodShortAndSerialNo;

	/***
	 * 运输性质
	 */
	private String productCode;
    
	//合作伙伴新增字段  268377 yl
	/**
	 * 是否是合作伙伴
	 */
	private String isPartner;
	/**
	 * 伙伴姓名
	 */
	private String partnerName;
	/**
	 * 伙伴手机
	 */
	private String partnerPhone;
	
	/**
	 * 外发流水号，一票多件外发签收使用
	 */
	private String serialNo;
	
	/**
	 * 外发单号
	 */
	private String externalWaybillNo;
	
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

	public String getExternalWaybillNo() {
		return externalWaybillNo;
	}

	public void setExternalWaybillNo(String externalWaybillNo) {
		this.externalWaybillNo = externalWaybillNo;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public BigDecimal getToPayAmount() {
		return toPayAmount;
	}

	public void setToPayAmount(BigDecimal toPayAmount) {
		this.toPayAmount = toPayAmount;
	}

	public BigDecimal getCodAmount() {
		return codAmount;
	}

	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}

	public String getCodPaymentType() {
		return codPaymentType;
	}

	public void setCodPaymentType(String codPaymentType) {
		this.codPaymentType = codPaymentType;
	}

	public String getCodBankTradeSerail() {
		return codBankTradeSerail;
	}

	public void setCodBankTradeSerail(String codBankTradeSerail) {
		this.codBankTradeSerail = codBankTradeSerail;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getTotalPaymentType() {
		return totalPaymentType;
	}

	public void setTotalPaymentType(String totalPaymentType) {
		this.totalPaymentType = totalPaymentType;
	}

	public String getExpressEmpCode() {
		return expressEmpCode;
	}

	public void setExpressEmpCode(String expressEmpCode) {
		this.expressEmpCode = expressEmpCode;
	}

	public String getExpressEmpName() {
		return expressEmpName;
	}

	public void setExpressEmpName(String expressEmpName) {
		this.expressEmpName = expressEmpName;
	}

	public String getExpressOrgCode() {
		return expressOrgCode;
	}

	public void setExpressOrgCode(String expressOrgCode) {
		this.expressOrgCode = expressOrgCode;
	}

	public String getExpressOrgName() {
		return expressOrgName;
	}

	public void setExpressOrgName(String expressOrgName) {
		this.expressOrgName = expressOrgName;
	}

	public String getIsPartner() {
		return isPartner;
	}

	public void setIsPartner(String isPartner) {
		this.isPartner = isPartner;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getPartnerPhone() {
		return partnerPhone;
	}

	public void setPartnerPhone(String partnerPhone) {
		this.partnerPhone = partnerPhone;
	}


	/**
	 * @return signCount : return the property signCount.
	 */
	public Integer getSignCount() {
		return signCount;
	}

	/**
	 * @param signCount : set the property signCount.
	 */
	public void setSignCount(Integer signCount) {
		this.signCount = signCount;
	}
	

	/**
	 * Gets the 是否PDA签到.
	 *
	 * @return the 是否PDA签到
	 */
	public String getIsPdaSign() {
		return isPdaSign;
	}

	/**
	 * Sets the 是否PDA签到.
	 *
	 * @param isPdaSign the new 是否PDA签到
	 */
	public void setIsPdaSign(String isPdaSign) {
		this.isPdaSign = isPdaSign;
	}

	/**
	 * Gets the 签收状态.
	 *
	 * @return the 签收状态
	 */
	public String getSignStatus() {
		return signStatus;
	}

	/**
	 * Sets the 签收状态.
	 *
	 * @param signStatus the new 签收状态
	 */
	public void setSignStatus(String signStatus) {
		this.signStatus = signStatus;
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
	 * Gets the 签收情况.
	 *
	 * @return the 签收情况
	 */
	public String getSignSituation() {
		return signSituation;
	}

	/**
	 * Sets the 签收情况.
	 *
	 * @param signSituation the new 签收情况
	 */
	public void setSignSituation(String signSituation) {
		this.signSituation = signSituation;
	}

	/**
	 * Gets the 提货人名称.
	 *
	 * @return the 提货人名称
	 */
	public String getDeliverymanName() {
		return deliverymanName;
	}

	/**
	 * Sets the 提货人名称.
	 *
	 * @param deliverymanName the new 提货人名称
	 */
	public void setDeliverymanName(String deliverymanName) {
		this.deliverymanName = deliverymanName;
	}

	/**
	 * Gets the 证件类型.
	 *
	 * @return the 证件类型
	 */
	public String getIdentifyType() {
		return identifyType;
	}

	/**
	 * Sets the 证件类型.
	 *
	 * @param identifyType the new 证件类型
	 */
	public void setIdentifyType(String identifyType) {
		this.identifyType = identifyType;
	}

	/**
	 * Gets the 证件号码.
	 *
	 * @return the 证件号码
	 */
	public String getIdentifyCode() {
		return identifyCode;
	}

	/**
	 * Sets the 证件号码.
	 *
	 * @param identifyCode the new 证件号码
	 */
	public void setIdentifyCode(String identifyCode) {
		this.identifyCode = identifyCode;
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
	 * Gets the 签收件数.
	 *
	 * @return the 签收件数
	 */
	public Integer getSignGoodsQty() {
		return signGoodsQty;
	}

	/**
	 * Sets the 签收件数.
	 *
	 * @param signGoodsQty the new 签收件数
	 */
	public void setSignGoodsQty(Integer signGoodsQty) {
		this.signGoodsQty = signGoodsQty;
	}

	/**
	 * Gets the 签收备注.
	 *
	 * @return the 签收备注
	 */
	public String getSignNote() {
		return signNote;
	}

	/**
	 * Sets the 签收备注.
	 *
	 * @param signNote the new 签收备注
	 */
	public void setSignNote(String signNote) {
		this.signNote = signNote;
	}

	/**
	 * Gets the 签收时间.
	 *
	 * @return the 签收时间
	 */
	public Date getSignTime() {
		return signTime;
	}

	/**
	 * Sets the 签收时间.
	 *
	 * @param signTime the new 签收时间
	 */
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	/**
	 * Gets the 生效时间.
	 *
	 * @return the 生效时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * Sets the 生效时间.
	 *
	 * @param createTime the new 生效时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * Gets the 时效时间.
	 *
	 * @return the 时效时间
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * Sets the 时效时间.
	 *
	 * @param modifyTime the new 时效时间
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * Gets the 是否有效.
	 *
	 * @return the 是否有效
	 */
	public String getActive() {
		return active;
	}

	/**
	 * Sets the 是否有效.
	 *
	 * @param active the new 是否有效
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * Gets the 代理编码.
	 *
	 * @return the 代理编码
	 */
	public String getAgentCode() {
		return agentCode;
	}

	/**
	 * Sets the 代理编码.
	 *
	 * @param agentCode the new 代理编码
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	/**
	 * Gets the 是否审批中.
	 *
	 * @return the 是否审批中
	 */
	public String getIsRfcing() {
		return isRfcing;
	}

	/**
	 * Sets the 是否审批中.
	 *
	 * @param isRfcing the new 是否审批中
	 */
	public void setIsRfcing(String isRfcing) {
		this.isRfcing = isRfcing;
	}

	/**
	 * Gets the 送货时间.
	 *
	 * @return the 送货时间
	 */
	public Date getDeliverDate() {
		return deliverDate;
	}

	/**
	 * Sets the 送货时间.
	 *
	 * @param deliverDate the new 送货时间
	 */
	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}

	/**
	 * Gets the 到达时间.
	 *
	 * @return the 到达时间
	 */
	public Date getArriveTime() {
		return arriveTime;
	}

	/**
	 * Sets the 到达时间.
	 *
	 * @param arriveTime the new 到达时间
	 */
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	/**
	 * Gets the 提货方式.
	 *
	 * @return the 提货方式
	 */
	public String getReceiveMethod() {
		return receiveMethod;
	}

	/**
	 * Sets the 提货方式.
	 *
	 * @param receiveMethod the new 提货方式
	 */
	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}
	
	/**
	 * Gets the 签收人类型.
	 *
	 * @param the new 签收人类型
	 */
	public String getDeliverymanType() {
		return deliverymanType;
	}

	/**
	 * Sets the 签收人类型.
	 *
	 * @param deliverymanType the new 签收人类型
	 */
	public void setDeliverymanType(String deliverymanType) {
		this.deliverymanType = deliverymanType;
	}

	/**
	 * Gets the 运输性质.
	 *
	 * @param the new 运输性质
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * Sets the 运输性质.
	 *
	 * @param productCode the 运输性质
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getCreateOrgCode() {
		return createOrgCode;
	}

	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	public String getCreateOrgName() {
		return createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatorCode() {
		return creatorCode;
	}

	public void setCreatorCode(String creatorCode) {
		this.creatorCode = creatorCode;
	}

	public String getGoodShortAndSerialNo() {
		return goodShortAndSerialNo;
	}

	public void setGoodShortAndSerialNo(String goodShortAndSerialNo) {
		this.goodShortAndSerialNo = goodShortAndSerialNo;
	}

}