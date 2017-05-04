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
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/domain/ActualFreightEntity.java
 * 
 * FILE NAME        	: ActualFreightEntity.java
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
package com.deppon.foss.module.settlement.dubbo.api.define;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;


/**
 * 
 * 实际货物
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-10-31 下午2:42:48
 */
public class ActualFreightEntity extends BaseEntity {
	/**
     * 生成序列化标识
     * （用一句话描述这个变量表示什么）
     */
	private static final long serialVersionUID = -305730990549520324L;
	/**
	 * dp-foss-dongjialing
	 * 225131
	 * 梯度事后折需求 在运单里添加运费最低一票标记
	 */
	// 最低一票费用
	private BigDecimal minTransportFee;
	
	public BigDecimal getMinTransportFee() {
		return minTransportFee;
	}

	public void setMinTransportFee(BigDecimal minTransportFee) {
		this.minTransportFee = minTransportFee;
	}
	/**
	 * 内部员工发货
	 * dp-foss-dongjialing
	 * 225131
	 */
	//内部发货类型
	private String  internalDeliveryType;
	//折让方式-装卸费
	private  BigDecimal dcServicefee;
		
	
	public BigDecimal getDcServicefee() {
		return dcServicefee;
	}

	public void setDcServicefee(BigDecimal dcServicefee) {
		this.dcServicefee = dcServicefee;
	}
	//员工号
	private String  employeeNo;
	//特殊增值服务类型
	private String  specialValueAddedServiceType;
	
	
	public String getSpecialValueAddedServiceType() {
		return specialValueAddedServiceType;
	}

	public void setSpecialValueAddedServiceType(String specialValueAddedServiceType) {
		this.specialValueAddedServiceType = specialValueAddedServiceType;
	}

	public String getInternalDeliveryType() {
		return internalDeliveryType;
	}

	public void setInternalDeliveryType(String internalDeliveryType) {
		this.internalDeliveryType = internalDeliveryType;
	}

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}
	/**
	 * 是否批量开单
	 * dp-foss-dongjialing
	 * 225131
	 */
	private String isBatchCreateWaybill;
	
	public String getIsBatchCreateWaybill() {
		return isBatchCreateWaybill;
	}

	public void setIsBatchCreateWaybill(String isBatchCreateWaybill) {
		this.isBatchCreateWaybill = isBatchCreateWaybill;
	}
	/**
	 * DMANA-8928 FOSS开单品名自动匹配货源品类需求
	 * @author Foss-206860
	 * 新增字段：货源品类
	 * */
	private String industrySourceCategory;
	
	public String getIndustrySourceCategory() {
		return industrySourceCategory;
	}

	public void setIndustrySourceCategory(String industrySourceCategory) {
		this.industrySourceCategory = industrySourceCategory;
	}
	/* zhangchengfu 20150530 FOSS系统新客户标签需求 begin */
	/*
	 * 客户分群
	 */
	private String flabelleavemonth;

	public String getFlabelleavemonth() {
		return flabelleavemonth;
	}

	public void setFlabelleavemonth(String flabelleavemonth) {
		this.flabelleavemonth = flabelleavemonth;
	}
	/* zhangchengfu 20150530 FOSS系统新客户标签需求 end */
	
	/**
	 * 开始开单时间
	 */
	private Date startBillTime;

	/**
	 * 始发客户是否统一结算
	 */
	private String startCentralizedSettlement;

	/**
	 * 到达客户是否统一结算
	 */
	private String arriveCentralizedSettlement;

	/**
	 * 始发客户合同部门编码
	 */
	private String startContractOrgCode;

	/**
	 * 到达客户合同部门编码
	 */
	private String arriveContractOrgCode;

	/**
	 * 始发客户合同上催款部门编码
	 */
	private String startReminderOrgCode;

	/**
	 * 到达客户合同上催款部门编码
	 */
	private String arriveReminderOrgCode;

	/**
	 * 始发客户合同部门名称
	 */
	private String startContractOrgName;

	/**
	 * 到达客户合同部门名称
	 */
	private String arriveContractOrgName;

	public Date getStartBillTime() {
		return startBillTime;
	}

	public void setStartBillTime(Date startBillTime) {
		this.startBillTime = startBillTime;
	}

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 货物名称
	 */
	private String goodsName;

	/**
	 * 重量
	 */
	private BigDecimal weight;

	/**
	 * 体积
	 */
	private BigDecimal volume;

	/**
	 * 件数
	 */
	private Integer goodsQty;

	/**
	 * 尺寸
	 */
	private String dimension;

	/**
	 * 保险声明价值
	 */
	private BigDecimal insuranceValue;

	/**
	 * 包装费
	 */
	private BigDecimal packingFee;

	/**
	 * 送货费
	 */
	private BigDecimal deliverFee;

	/**
	 * 装卸费
	 */
	private BigDecimal laborFee;

	/**
	 * 代收货款
	 */
	private BigDecimal codAmount;

	/**
	 * 增值服务
	 */
	private BigDecimal valueaddFee;

	/**
	 * 公布价运费
	 */
	private BigDecimal freight;

	/**
	 * 结清状态
	 */
	private String settleStatus;

	/**
	 * 结清时间
	 */
	private Date settleTime;

	/**
	 * 通知状态
	 */
	private String notificationResult;

	/**
	 * 通知时间
	 */
	private Date notificationTime;

	/**
	 * 派送日期
	 */
	private Date deliverDate;

	/**
	 * 提货方式
	 */
	private String actualDeliverType;

	/**
	 * 
	 */
	private Integer storageDay;

	/**
	 * 
	 */
	private BigDecimal storageCharge;

	/**
	 * 
	 */
	private Integer overdueDay;

	/**
	 * 是否大票货
	 */
	private String bigTicketGoods;
	/**
	 * 到达件数
	 */
	private Integer arriveGoodsQty;

	/**
	 * 到达时间
	 */
	private Date arriveTime;

	/**
	 * 
	 */
	private Integer arrangeGoodsQty;

	/**
	 * 到达未出库件数
	 */
	private Integer arriveNotoutGoodsQty;

	/**
	 * 已生成到达联件数
	 */
	private Integer generateGoodsQty;

	/**
	 * 
	 */
	private Integer exceptionGoodsQty;

	/**
	 * 通知客户产生的付款方式
	 */
	private String paymentType;

	/**
	 * 送货区域编码
	 */
	private String deliverRegionCode;

	/**
	 * -始发库存部门编码
	 */
	private String startStockOrgCode;

	/**
	 * 最终库存部门编码
	 */
	private String endStockOrgCode;

	/**
	 * 运单状态
	 */
	private String status;
	
	
	/**
	 * 渠道客户Id:官网发货人德邦用户名
	 */
	private String channelCustId;

	
	/**
	 * 到达提货人
	 */
    private String deliverymanName;

    /**
     * 证件类型
     */
    private String identifyType;

    /**
     * 证件号
     */
    private String identifyCode;
    
    /**
     * 获取编码
     */
    private String goodsAreaCode;
    
    /**
     * 是否曾经电话通知成功过
     */
    private String everTelnoticeSuccess;
    /**
     * 通知部门
     */
    private String notificationOrgCode;
    /**
     * 证件号码（代收人）
     */
    private String codIdentifyCode;
    
    /**
     * 证件类型（代收人）
     */
    private String codIdentifyType;
    /**
     * BUG-47796
     * 记录创建时间
     */
    private Date createTime;
    
	/**
	 * 开发版本：RSTL201310260111
	 * 发票标识
	 */
	private String invoice;
	
	/**
	 * 短信标识
	 */
	private String isSMS;
	
	/**
	 * 是否已采集
	 */
	private String isCollect;
	
	/**
	 * 优惠类型
	 * @return
	 */
	private String specialOffer;
	/**
	 * 合送标识
	 */
	private String   togetherSendMark;
	/**
	 * 合送编码
	 */
	private String togetherSendCode;
	
	/**
	 * 修改时间
	 * @return
	 */
	private Date modifyTime;
	
	/**
	 * 是否次日补录
	 */
	private String isNextDayPending;
	
	/**
	 * 补录时间
	 * @return
	 */
	private Date pendingTime;
	
	/**
	 * 设置运单类型
	 */
	private String waybillType;
	
	/**
	 * 是否展货
	 */
	private String isExhibitCargo;
	
	/**
	 * 发货具体地址备注
	 */
	private String deliveryCustomerAddressNote;
	
	/**
	 * 收货具体地址备注
	 */
	private String receiveCustomerAddressNote;

    private String packageRemark;

    //是否商业区
    private String businessZone;
    //是否住宅区
    private String residentialDistrict;
    
    /**
	 * 到付折扣金额
	 */
	private BigDecimal toPayAmountDiscount;

	/**
	 * 预付折扣金额
	 */
	private BigDecimal prePayAmountDiscount;
	
	/**
     * 关联单号费用
     */
    private BigDecimal connectionNumFee;
    /**
     * 是否有投诉变更签收结果
     * add by 231438 DMANA-9716
     */
    private String complainStatus;
    /**
     * 伙伴名称
     */
	private String partnerName;
	/**
	 * 伙伴手机
	 */
	private String partnerPhome;
	/**
	 * 伙伴开单
	 */
	private String partnerBillingLogo;
	
	/**
	 * 是否为上门发货
	 */
	private String homeDelivery;
	
    /**
     * 交单件数
     */
	private Integer deliverBillQty;
	/**
     * 送货时间点(起)
     */
    private String deliveryTimeStart;
    /**
     * 送货时间点(止)
     */
    private String deliveryTimeOver;
    /**
     * 发票类型
     */
    private String invoiceType;
    /**
     * 送货时间段
     */
    private String deliveryTimeInterval;
    /**
     * 修改预计送货日期部门
     */
    private String upDeliverDateOrgCode;
    
    /**
     * 收货人乡镇(街道)
     * @return
     */
    private String receiveCustomerVillageCode;   
    
    /**
     * PDA付款方式
     * @return
     */
    private String pdaPaidMethod;
    
	/**
     * PDA现付金额
     * @return
     */
    private BigDecimal pdaPrePayAmount;
    /**
     * 是否快递集中录单
     * @return
     */
    private String isExpressFocus;

	public String getPdaPaidMethod() {
		return pdaPaidMethod;
	}

	public void setPdaPaidMethod(String pdaPaidMethod) {
		this.pdaPaidMethod = pdaPaidMethod;
	}

	public BigDecimal getPdaPrePayAmount() {
		return pdaPrePayAmount;
	}

	public void setPdaPrePayAmount(BigDecimal pdaPrePayAmount) {
		this.pdaPrePayAmount = pdaPrePayAmount;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getPartnerPhome() {
		return partnerPhome;
	}

	public void setPartnerPhome(String partnerPhome) {
		this.partnerPhome = partnerPhome;
	}

	
	public String getPartnerBillingLogo() {
		return partnerBillingLogo;
	}

	public void setPartnerBillingLogo(String partnerBillingLogo) {
		this.partnerBillingLogo = partnerBillingLogo;
	}

	
	/**
	 * 获取upDeliverDateOrgCode  
	 * @return upDeliverDateOrgCode upDeliverDateOrgCode
	 */
	public String getUpDeliverDateOrgCode() {
		return upDeliverDateOrgCode;
	}

	/**
	 * 设置upDeliverDateOrgCode  
	 * @param upDeliverDateOrgCode upDeliverDateOrgCode 
	 */
	public void setUpDeliverDateOrgCode(String upDeliverDateOrgCode) {
		this.upDeliverDateOrgCode = upDeliverDateOrgCode;
	}

		public String getReceiveCustomerVillageCode() {
		return receiveCustomerVillageCode;
	}

	public void setReceiveCustomerVillageCode(String receiveCustomerVillageCode) {
		this.receiveCustomerVillageCode = receiveCustomerVillageCode;
	}
	public BigDecimal getConnectionNumFee() {
		return connectionNumFee;
	}

	public void setConnectionNumFee(BigDecimal connectionNumFee) {
		this.connectionNumFee = connectionNumFee;
	}

	public BigDecimal getToPayAmountDiscount() {
		return toPayAmountDiscount;
	}

	public void setToPayAmountDiscount(BigDecimal toPayAmountDiscount) {
		this.toPayAmountDiscount = toPayAmountDiscount;
	}

	public BigDecimal getPrePayAmountDiscount() {
		return prePayAmountDiscount;
	}

	public void setPrePayAmountDiscount(BigDecimal prePayAmountDiscount) {
		this.prePayAmountDiscount = prePayAmountDiscount;
	}

	public String getBusinessZone() {
		return businessZone;
	}

	public void setBusinessZone(String businessZone) {
		this.businessZone = businessZone;
	}

	public String getResidentialDistrict() {
		return residentialDistrict;
	}

	public void setResidentialDistrict(String residentialDistrict) {
		this.residentialDistrict = residentialDistrict;
	}

	public String getPackageRemark() {
		return packageRemark;
	}

	public void setPackageRemark(String packageRemark) {
		this.packageRemark = packageRemark;
	}	
	public String getIsNextDayPending() {
		return isNextDayPending;
	}

	public void setIsNextDayPending(String isNextDayPending) {
		this.isNextDayPending = isNextDayPending;
	}

	public Date getPendingTime() {
		return pendingTime;
	}

	public void setPendingTime(Date pendingTime) {
		this.pendingTime = pendingTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}
	
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	public String getSpecialOffer() {
		return specialOffer;
	}

	public void setSpecialOffer(String specialOffer) {
		this.specialOffer = specialOffer;
	}

	public String getIsSMS() {
		return isSMS;
	}

	public void setIsSMS(String isSMS) {
		this.isSMS = isSMS;
	}

	/**
	 * 获得发票标识
	 */
	public String getInvoice() {
		return invoice;
	}
	
	public String getBigTicketGoods() {
		return bigTicketGoods;
	}

	public void setBigTicketGoods(String bigTicketGoods) {
		this.bigTicketGoods = bigTicketGoods;
	}

	/**
	 * 设置发票标识
	 */
	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}
	
    
	public String getEverTelnoticeSuccess() {
		return everTelnoticeSuccess;
	}

	public void setEverTelnoticeSuccess(String everTelnoticeSuccess) {
		this.everTelnoticeSuccess = everTelnoticeSuccess;
	}

	/**
	 * @return the goodsAreaCode
	 */
	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}

	/**
	 * @param goodsAreaCode the goodsAreaCode to set
	 */
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}

	/**
	 * @return the channelCustId
	 */
	public String getChannelCustId() {
		return channelCustId;
	}

	/**
	 * @param channelCustId the channelCustId to set
	 */
	public void setChannelCustId(String channelCustId) {
		this.channelCustId = channelCustId;
	}

	/**
	 * @return the arriveGoodsQty .
	 */
	public Integer getArriveGoodsQty() {
		return arriveGoodsQty;
	}

	/**
	 * @param arriveGoodsQty
	 *            the arriveGoodsQty to set.
	 */
	public void setArriveGoodsQty(Integer arriveGoodsQty) {
		this.arriveGoodsQty = arriveGoodsQty;
	}

	/**
	 * @return the arriveTime .
	 */
	public Date getArriveTime() {
		return arriveTime;
	}

	/**
	 * @param arriveTime
	 *            the arriveTime to set.
	 */
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	/**
	 * @return the arrangeGoodsQty .
	 */
	public Integer getArrangeGoodsQty() {
		return arrangeGoodsQty;
	}

	/**
	 * @param arrangeGoodsQty
	 *            the arrangeGoodsQty to set.
	 */
	public void setArrangeGoodsQty(Integer arrangeGoodsQty) {
		this.arrangeGoodsQty = arrangeGoodsQty;
	}

	/**
	 * @return the arriveNotoutGoodsQty .
	 */
	public Integer getArriveNotoutGoodsQty() {
		return arriveNotoutGoodsQty;
	}

	/**
	 * @param arriveNotoutGoodsQty
	 *            the arriveNotoutGoodsQty to set.
	 */
	public void setArriveNotoutGoodsQty(Integer arriveNotoutGoodsQty) {
		this.arriveNotoutGoodsQty = arriveNotoutGoodsQty;
	}

	/**
	 * @return the generateGoodsQty .
	 */
	public Integer getGenerateGoodsQty() {
		return generateGoodsQty;
	}

	/**
	 * @param generateGoodsQty
	 *            the generateGoodsQty to set.
	 */
	public void setGenerateGoodsQty(Integer generateGoodsQty) {
		this.generateGoodsQty = generateGoodsQty;
	}

	/**
	 * @return the exceptionGoodsQty .
	 */
	public Integer getExceptionGoodsQty() {
		return exceptionGoodsQty;
	}

	/**
	 * @param exceptionGoodsQty
	 *            the exceptionGoodsQty to set.
	 */
	public void setExceptionGoodsQty(Integer exceptionGoodsQty) {
		this.exceptionGoodsQty = exceptionGoodsQty;
	}

	/**
	 * @return the paymentType .
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * @param paymentType
	 *            the paymentType to set.
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * @return the deliverRegionCode .
	 */
	public String getDeliverRegionCode() {
		return deliverRegionCode;
	}

	/**
	 * @param deliverRegionCode
	 *            the deliverRegionCode to set.
	 */
	public void setDeliverRegionCode(String deliverRegionCode) {
		this.deliverRegionCode = deliverRegionCode;
	}

	/**
	 * @return the startStockOrgCode .
	 */
	public String getStartStockOrgCode() {
		return startStockOrgCode;
	}

	/**
	 * @param startStockOrgCode
	 *            the startStockOrgCode to set.
	 */
	public void setStartStockOrgCode(String startStockOrgCode) {
		this.startStockOrgCode = startStockOrgCode;
	}

	/**
	 * @return the endStockOrgCode .
	 */
	public String getEndStockOrgCode() {
		return endStockOrgCode;
	}

	/**
	 * @param endStockOrgCode
	 *            the endStockOrgCode to set.
	 */
	public void setEndStockOrgCode(String endStockOrgCode) {
		this.endStockOrgCode = endStockOrgCode;
	}

	/**
	 * @return the status .
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo
	 *            the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return the goodsName
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * @param goodsName
	 *            the goodsName to set
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * @return the weight
	 */
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * @param weight
	 *            the weight to set
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
	 * @return the volume
	 */
	public BigDecimal getVolume() {
		return volume;
	}

	/**
	 * @param volume
	 *            the volume to set
	 */
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	/**
	 * @return the goodsQty
	 */
	public Integer getGoodsQty() {
		return goodsQty;
	}

	/**
	 * @param goodsQty
	 *            the goodsQty to set
	 */
	public void setGoodsQty(Integer goodsQty) {
		this.goodsQty = goodsQty;
	}

	/**
	 * @return the dimension
	 */
	public String getDimension() {
		return dimension;
	}

	/**
	 * @param dimension
	 *            the dimension to set
	 */
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	/**
	 * @return the insuranceValue
	 */
	public BigDecimal getInsuranceValue() {
		return insuranceValue;
	}

	/**
	 * @param insuranceValue
	 *            the insuranceValue to set
	 */
	public void setInsuranceValue(BigDecimal insuranceValue) {
		this.insuranceValue = insuranceValue;
	}

	/**
	 * @return the packingFee
	 */
	public BigDecimal getPackingFee() {
		return packingFee;
	}

	/**
	 * @param packingFee
	 *            the packingFee to set
	 */
	public void setPackingFee(BigDecimal packingFee) {
		this.packingFee = packingFee;
	}

	/**
	 * @return the deliverFee
	 */
	public BigDecimal getDeliverFee() {
		return deliverFee;
	}

	/**
	 * @param deliverFee
	 *            the deliverFee to set
	 */
	public void setDeliverFee(BigDecimal deliverFee) {
		this.deliverFee = deliverFee;
	}

	/**
	 * @return the laborFee
	 */
	public BigDecimal getLaborFee() {
		return laborFee;
	}

	/**
	 * @param laborFee
	 *            the laborFee to set
	 */
	public void setLaborFee(BigDecimal laborFee) {
		this.laborFee = laborFee;
	}

	/**
	 * @return the codAmount
	 */
	public BigDecimal getCodAmount() {
		return codAmount;
	}

	/**
	 * @param codAmount
	 *            the codAmount to set
	 */
	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}

	/**
	 * @return the valueaddFee
	 */
	public BigDecimal getValueaddFee() {
		return valueaddFee;
	}

	/**
	 * @param valueaddFee
	 *            the valueaddFee to set
	 */
	public void setValueaddFee(BigDecimal valueaddFee) {
		this.valueaddFee = valueaddFee;
	}

	/**
	 * @return the freight
	 */
	public BigDecimal getFreight() {
		return freight;
	}

	/**
	 * @param freight
	 *            the freight to set
	 */
	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	/**
	 * @return the settleStatus
	 */
	public String getSettleStatus() {
		return settleStatus;
	}

	/**
	 * @param settleStatus
	 *            the settleStatus to set
	 */
	public void setSettleStatus(String settleStatus) {
		this.settleStatus = settleStatus;
	}

	/**
	 * @return the settleTime
	 */
	public Date getSettleTime() {
		return settleTime;
	}

	/**
	 * @param settleTime
	 *            the settleTime to set
	 */
	public void setSettleTime(Date settleTime) {
		this.settleTime = settleTime;
	}

	/**
	 * @return the notificationTime
	 */
	public Date getNotificationTime() {
		return notificationTime;
	}

	/**
	 * @param notificationTime
	 *            the notificationTime to set
	 */
	public void setNotificationTime(Date notificationTime) {
		this.notificationTime = notificationTime;
	}

	/**
	 * @return the deliverDate .
	 */
	public Date getDeliverDate() {
		return deliverDate;
	}

	/**
	 * @param deliverDate
	 *            the deliverDate to set.
	 */
	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}

	/**
	 * @return the actualDeliverType .
	 */
	public String getActualDeliverType() {
		return actualDeliverType;
	}

	/**
	 * @param actualDeliverType
	 *            the actualDeliverType to set.
	 */
	public void setActualDeliverType(String actualDeliverType) {
		this.actualDeliverType = actualDeliverType;
	}

	/**
	 * @return the storageDay .
	 */
	public Integer getStorageDay() {
		return storageDay;
	}

	/**
	 * @param storageDay
	 *            the storageDay to set.
	 */
	public void setStorageDay(Integer storageDay) {
		this.storageDay = storageDay;
	}

	/**
	 * @return the storageCharge .
	 */
	public BigDecimal getStorageCharge() {
		return storageCharge;
	}

	/**
	 * @param storageCharge
	 *            the storageCharge to set.
	 */
	public void setStorageCharge(BigDecimal storageCharge) {
		this.storageCharge = storageCharge;
	}

	/**
	 * @return the overdueDay .
	 */
	public Integer getOverdueDay() {
		return overdueDay;
	}

	/**
	 * @param overdueDay
	 *            the overdueDay to set.
	 */
	public void setOverdueDay(Integer overdueDay) {
		this.overdueDay = overdueDay;
	}

	
	/**
	 * @return  the notificationResult
	 */
	public String getNotificationResult() {
		return notificationResult;
	}

	
	/**
	 * @param notificationResult the notificationResult to set
	 */
	public void setNotificationResult(String notificationResult) {
		this.notificationResult = notificationResult;
	}

	/**
	 * @return the deliverymanName
	 */
	public String getDeliverymanName() {
		return deliverymanName;
	}

	/**
	 * @param deliverymanName the deliverymanName to set
	 */
	public void setDeliverymanName(String deliverymanName) {
		this.deliverymanName = deliverymanName;
	}

	/**
	 * @return the identifyType
	 */
	public String getIdentifyType() {
		return identifyType;
	}

	/**
	 * @param identifyType the identifyType to set
	 */
	public void setIdentifyType(String identifyType) {
		this.identifyType = identifyType;
	}

	/**
	 * @return the identifyCode
	 */
	public String getIdentifyCode() {
		return identifyCode;
	}

	/**
	 * @param identifyCode the identifyCode to set
	 */
	public void setIdentifyCode(String identifyCode) {
		this.identifyCode = identifyCode;
	}

	public String getNotificationOrgCode() {
		return notificationOrgCode;
	}

	public void setNotificationOrgCode(String notificationOrgCode) {
		this.notificationOrgCode = notificationOrgCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCodIdentifyCode() {
		return codIdentifyCode;
	}

	public void setCodIdentifyCode(String codIdentifyCode) {
		this.codIdentifyCode = codIdentifyCode;
	}

	public String getCodIdentifyType() {
		return codIdentifyType;
	}

	public void setCodIdentifyType(String codIdentifyType) {
		this.codIdentifyType = codIdentifyType;
	}

	public String getIsCollect() {
		return isCollect;
	}

	public void setIsCollect(String isCollect) {
		this.isCollect = isCollect;
	}

	public String getTogetherSendMark() {
		return togetherSendMark;
	}

	public void setTogetherSendMark(String togetherSendMark) {
		this.togetherSendMark = togetherSendMark;
	}

	public String getTogetherSendCode() {
		return togetherSendCode;
	}

	public void setTogetherSendCode(String togetherSendCode) {
		this.togetherSendCode = togetherSendCode;
	}
	/**
	  * 是否电子发票
	  * @author:218371-foss-zhaoyanjun
	  * @date:2014-10-21下午14:12
	  */
	private String isElectronicInvoice;
	
	public String getWaybillType() {
		return waybillType;
	}

	public void setWaybillType(String waybillType) {
		this.waybillType = waybillType;
	}

	public String getIsExhibitCargo() {
		return isExhibitCargo;
	}

	public void setIsExhibitCargo(String isExhibitCargo) {
		this.isExhibitCargo = isExhibitCargo;
	}

	public String getDeliveryCustomerAddressNote() {
		return deliveryCustomerAddressNote;
	}

	public void setDeliveryCustomerAddressNote(String deliveryCustomerAddressNote) {
		this.deliveryCustomerAddressNote = deliveryCustomerAddressNote;
	}

	public String getReceiveCustomerAddressNote() {
		return receiveCustomerAddressNote;
	}

	public void setReceiveCustomerAddressNote(String receiveCustomerAddressNote) {
		this.receiveCustomerAddressNote = receiveCustomerAddressNote;
	}	

	/**
	 * 发票手机号
	 * @author:218371-foss-zhaoyanjun
	 * @date:2014-10-21下午14:12
	 */
	private String invoiceMobilePhone;
	
	/**
	 * 发票发送邮箱
	 * @author:218371-foss-zhaoyanjun
	 * @date:2014-10-21下午14:23
	 */
	private String email;

	public String getIsElectronicInvoice() {
		return isElectronicInvoice;
	}

	public void setIsElectronicInvoice(String isElectronicInvoice) {
		this.isElectronicInvoice = isElectronicInvoice;
	}

	public String getInvoiceMobilePhone() {
		return invoiceMobilePhone;
	}

	public void setInvoiceMobilePhone(String invoiceMobilePhone) {
		this.invoiceMobilePhone = invoiceMobilePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * 打包装价格合计
	 * @author：218371-foss-zhaoyanjun
	 * @date:2014-12-4下午21:20
	 */
	private BigDecimal packingTotle;
		 
	/**
	 * 纸箱总价
	 * @author：218371-foss-zhaoyanjun
	 * @date:2014-12-4下午21:20
	 */
	private BigDecimal paperBoxTotlePrice;
			
	/**
	 * 纤袋总价
	 * @author：218371-foss-zhaoyanjun
	 * @date:2014-12-4下午21:20
	 */
	private BigDecimal fibelBagTotlePrice;
		
	/**
	 * 打包装其它合计
	 * @author：218371-foss-zhaoyanjun
	 * @date:2014-12-4下午21:20
	 */


	private BigDecimal otherTotle;

	public BigDecimal getPackingTotle() {
		return packingTotle;
	}

	public void setPackingTotle(BigDecimal packingTotle) {
		this.packingTotle = packingTotle;
	}

	public BigDecimal getPaperBoxTotlePrice() {
		return paperBoxTotlePrice;
	}

	public void setPaperBoxTotlePrice(BigDecimal paperBoxTotlePrice) {
		this.paperBoxTotlePrice = paperBoxTotlePrice;
	}

	public BigDecimal getFibelBagTotlePrice() {
		return fibelBagTotlePrice;
	}

	public void setFibelBagTotlePrice(BigDecimal fibelBagTotlePrice) {
		this.fibelBagTotlePrice = fibelBagTotlePrice;
	}

	public BigDecimal getOtherTotle() {
		return otherTotle;
	}

	public void setOtherTotle(BigDecimal otherTotle) {
		this.otherTotle = otherTotle;
	}
	
	
	public String getStartCentralizedSettlement() {
		return startCentralizedSettlement;
	}

	public void setStartCentralizedSettlement(String startCentralizedSettlement) {
		this.startCentralizedSettlement = startCentralizedSettlement;
	}

	public String getArriveCentralizedSettlement() {
		return arriveCentralizedSettlement;
	}

	public void setArriveCentralizedSettlement(String arriveCentralizedSettlement) {
		this.arriveCentralizedSettlement = arriveCentralizedSettlement;
	}

	public String getStartContractOrgCode() {
		return startContractOrgCode;
	}

	public void setStartContractOrgCode(String startContractOrgCode) {
		this.startContractOrgCode = startContractOrgCode;
	}

	public String getArriveContractOrgCode() {
		return arriveContractOrgCode;
	}

	public void setArriveContractOrgCode(String arriveContractOrgCode) {
		this.arriveContractOrgCode = arriveContractOrgCode;
	}

	public String getStartReminderOrgCode() {
		return startReminderOrgCode;
	}

	public void setStartReminderOrgCode(String startReminderOrgCode) {
		this.startReminderOrgCode = startReminderOrgCode;
	}

	public String getArriveReminderOrgCode() {
		return arriveReminderOrgCode;
	}

	public void setArriveReminderOrgCode(String arriveReminderOrgCode) {
		this.arriveReminderOrgCode = arriveReminderOrgCode;
	}

	public String getStartContractOrgName() {
		return startContractOrgName;
	}

	public void setStartContractOrgName(String startContractOrgName) {
		this.startContractOrgName = startContractOrgName;
	}

	public String getArriveContractOrgName() {
		return arriveContractOrgName;
	}

	public void setArriveContractOrgName(String arriveContractOrgName) {
		this.arriveContractOrgName = arriveContractOrgName;
	}
	
	/**
	 * 交易流水号
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-01-22下午18:55
	 */
	private String transactionSerialNumber;

	public String getTransactionSerialNumber() {
		return transactionSerialNumber;
	}

	public void setTransactionSerialNumber(String transactionSerialNumber) {
		this.transactionSerialNumber = transactionSerialNumber;
	}
	
	/**
	 * Dmana-9885运费
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-02-02 下午16:49
	 */
	private BigDecimal crmTransportFee;
	
	/**
	 * Dmana-9885重量
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-02-02下午16:50
	 */
	private BigDecimal crmWeight;
	
	/**
	 * Dmana-9885体积
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-02-02下午16:52
	 */
	private BigDecimal crmVolume;

	public BigDecimal getCrmTransportFee() {
		return crmTransportFee;
	}

	public void setCrmTransportFee(BigDecimal crmTransportFee) {
		this.crmTransportFee = crmTransportFee;
	}

	public BigDecimal getCrmWeight() {
		return crmWeight;
	}

	public void setCrmWeight(BigDecimal crmWeight) {
		this.crmWeight = crmWeight;
	}

	public BigDecimal getCrmVolume() {
		return crmVolume;
	}

	public void setCrmVolume(BigDecimal crmVolume) {
		this.crmVolume = crmVolume;
	}

	public String getComplainStatus() {
		return complainStatus;
	}

	public void setComplainStatus(String complainStatus) {
		this.complainStatus = complainStatus;
	}

    public void setDeliverBillQty(Integer deliverBillQty) {
        this.deliverBillQty = deliverBillQty;
    }

    public Integer getDeliverBillQty() {
        return deliverBillQty;
    }

    /**
	 * 获取deliveryTimeStart  
	 * @return deliveryTimeStart deliveryTimeStart
	 */
	public String getDeliveryTimeStart() {
		return deliveryTimeStart;
	}

	/**
	 * 设置deliveryTimeStart  
	 * @param deliveryTimeStart deliveryTimeStart 
	 */
	public void setDeliveryTimeStart(String deliveryTimeStart) {
		this.deliveryTimeStart = deliveryTimeStart;
	}

	/**
	 * 获取deliveryTimeOver  
	 * @return deliveryTimeOver deliveryTimeOver
	 */
	public String getDeliveryTimeOver() {
		return deliveryTimeOver;
	}

	/**
	 * 设置deliveryTimeOver  
	 * @param deliveryTimeOver deliveryTimeOver 
	 */
	public void setDeliveryTimeOver(String deliveryTimeOver) {
		this.deliveryTimeOver = deliveryTimeOver;
	}

	/**
	 * 获取invoiceType  
	 * @return invoiceType invoiceType
	 */
	public String getInvoiceType() {
		return invoiceType;
	}

	/**
	 * 设置invoiceType  
	 * @param invoiceType invoiceType 
	 */
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	/**
	 * 获取deliveryTimeInterval  
	 * @return deliveryTimeInterval deliveryTimeInterval
	 */
	public String getDeliveryTimeInterval() {
		return deliveryTimeInterval;
	}

	/**
	 * 设置deliveryTimeInterval  
	 * @param deliveryTimeInterval deliveryTimeInterval 
	 */
	public void setDeliveryTimeInterval(String deliveryTimeInterval) {
		this.deliveryTimeInterval = deliveryTimeInterval;
	}

	public String getHomeDelivery() {
		return homeDelivery;
	}

	public void setHomeDelivery(String homeDelivery) {
		this.homeDelivery = homeDelivery;
	}

	public String getIsExpressFocus() {
		return isExpressFocus;
	}

	public void setIsExpressFocus(String isExpressFocus) {
		this.isExpressFocus = isExpressFocus;
	}
}