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
 * PROJECT NAME	: pkp-pda-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pda/api/shared/dto/PdaDeliverSignDto.java
 * 
 * FILE NAME        	: PdaDeliverSignDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pda.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.pda.api.shared.vo.PictureAddress;

/**
 * 派送签收接口 参数
 * @author foss-meiying
 * @date 2012-12-19 上午10:09:29
 * @since
 * @version
 */
public class PdaDeliverSignDto implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3296867869735207739L;

	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 到达联编号
	 */
	private String arrivesheetNo;
	/**
	 * 签收部门编码
	 */
	private String signDeptCode;
	/**
	 * 签收件数
	 */
	private Integer signGoodsQty;
	/**
	 * 签收情况
	 * 	（正常签收、
	 * 	异常-破损、
	 * 	异常-潮湿、
	 * 	异常-污染、
	 * 	异常-其他、
	 * 	部分签收、
	 *  异常-内物短少）
	 */
	private String situation;
	
	/***
	 * 签收人类型（本人、门卫、前台、收发室、同事、亲属、其他）
	 */
	private String deliverymanType;
	/**
	 * 签收时间
	 */
	private Date signTime;
	/**
	 * 车牌号
	 */
	private String vehicleNo;
	/**
	 * 司机工号
	 */
	private String driverCode;
	/**
	 * 签收流水号列表
	 */
	private List<PdaSignDetailDto> pdaSignDetailDtos;
	/**
	 * 签收备注
	 */
	private String signNote;
	/**
	 * 付款方式 -- 快递  到付金额
	 * 	（现金、临欠、月结、银行卡、支票、电汇）
	 */
	private String paymentType;
	
	//------------快递新增字段
	
	/**
	 * PDA串号
	 */
	private String pdaSerial;
	
	/**
	 * 银行交易流水号--到付流水号
	 */
	private String bankTradeSerail;
	
	
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
	//PDA 增加签收人  MANA-581
	private String deliverymanName;//签收人
	
	//发票系统新增
	private String isofferInvoice;
	
	//异常签收图片地址保存类
	private PictureAddress adresses;

    /**
     * NCI ----交易流水号
     * @return
     */
	private String radeSerialNo;
	
	public PictureAddress getAdresses() {
		return adresses;
	}

	public void setAdresses(PictureAddress adresses) {
		this.adresses = adresses;
	}

	public String getCodBankTradeSerail() {
		return codBankTradeSerail;
	}

	public void setCodBankTradeSerail(String codBankTradeSerail) {
		this.codBankTradeSerail = codBankTradeSerail;
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

	public String getPdaSerial() {
		return pdaSerial;
	}

	public void setPdaSerial(String pdaSerial) {
		this.pdaSerial = pdaSerial;
	}

	public String getBankTradeSerail() {
		return bankTradeSerail;
	}

	public void setBankTradeSerail(String bankTradeSerail) {
		this.bankTradeSerail = bankTradeSerail;
	}

    public String getRadeSerialNo() {
        return radeSerialNo;
    }

    public void setRadeSerialNo(String radeSerialNo) {
        this.radeSerialNo = radeSerialNo;
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
	 * Gets the 到达联编号.
	 *
	 * @return the 到达联编号
	 */
	public String getArrivesheetNo() {
		return arrivesheetNo;
	}

	/**
	 * Sets the 到达联编号.
	 *
	 * @param arrivesheetNo the new 到达联编号
	 */
	public void setArrivesheetNo(String arrivesheetNo) {
		this.arrivesheetNo = arrivesheetNo;
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
	 * Gets the 签收部门编码.
	 *
	 * @return the 签收部门编码
	 */
	public String getSignDeptCode() {
		return signDeptCode;
	}

	/**
	 * Sets the 签收部门编码.
	 *
	 * @param signDeptCode the new 签收部门编码
	 */
	public void setSignDeptCode(String signDeptCode) {
		this.signDeptCode = signDeptCode;
	}

	/**
	 * Gets the 签收情况 （正常签收、 异常-破损、 异常-潮湿、 异常-污染、 异常-其他、 部分签收）.
	 *
	 * @return the 签收情况 （正常签收、 异常-破损、 异常-潮湿、 异常-污染、 异常-其他、 部分签收）
	 */
	public String getSituation() {
		return situation;
	}

	/**
	 * Sets the 签收情况 （正常签收、 异常-破损、 异常-潮湿、 异常-污染、 异常-其他、 部分签收）.
	 *
	 * @param situation the new 签收情况 （正常签收、 异常-破损、 异常-潮湿、 异常-污染、 异常-其他、 部分签收）
	 */
	public void setSituation(String situation) {
		this.situation = situation;
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
	 * Gets the 车牌号.
	 *
	 * @return the 车牌号
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * Sets the 车牌号.
	 *
	 * @param vehicleNo the new 车牌号
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * Gets the 司机工号.
	 *
	 * @return the 司机工号
	 */
	public String getDriverCode() {
		return driverCode;
	}

	/**
	 * Sets the 司机工号.
	 *
	 * @param driverCode the new 司机工号
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	/**
	 * Gets the 付款方式 （现金、临欠、月结、银行卡、支票、电汇）.
	 *
	 * @return the 付款方式 （现金、临欠、月结、银行卡、支票、电汇）
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * Sets the 付款方式 （现金、临欠、月结、银行卡、支票、电汇）.
	 *
	 * @param paymentType the new 付款方式 （现金、临欠、月结、银行卡、支票、电汇）
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * Gets the 签收流水号列表.
	 *
	 * @return the 签收流水号列表
	 */
	public List<PdaSignDetailDto> getPdaSignDetailDtos() {
		return pdaSignDetailDtos;
	}

	/**
	 * Sets the 签收流水号列表.
	 *
	 * @param pdaSignDetailDtos the new 签收流水号列表
	 */
	public void setPdaSignDetailDtos(List<PdaSignDetailDto> pdaSignDetailDtos) {
		this.pdaSignDetailDtos = pdaSignDetailDtos;
	}

	/**
	 * Gets the 签收人类型.
	 *
	 * @return the 签收人类型
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
	public String getDeliverymanName() {
		return deliverymanName;
	}

	public void setDeliverymanName(String deliverymanName) {
		this.deliverymanName = deliverymanName;
	}

	public String getIsofferInvoice() {
		return isofferInvoice;
	}
	
	public void setIsofferInvoice(String isofferInvoice) {
		this.isofferInvoice = isofferInvoice;
	}
}