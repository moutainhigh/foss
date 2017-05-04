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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/dto/WaybillSignResultDto.java
 * 
 * FILE NAME        	: WaybillSignResultDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 运单签收结果
 * @author ibm-lizhiguo
 * @date 2012-10-15 下午2:29:27
 */
public class WaybillSignResultDto implements Serializable {
	private static final long serialVersionUID = 1076692511307950823L;
	private String id;
	/**
	 *  运单号
	 */
	private String waybillNo;
	/** 
	 * 签收情况
	 */
	private String signSituation;
	/**
	 *  提货人名称
	 */
	private String deliverymanName;
	/**
	 *  提货人类型
	 */
	private String  deliverymanType;
	/** 
	 * 证件类型
	 */
	private String identifytype;
	/**
	 *  证件号码
	 */
	private String identifyCode;
	/** 
	 * 签收件数
	 */
	private Integer signGoodsQty;
	/** 
	 * 签收备注
	 */
	private String signNote;
	/** 
	 * 签收时间
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
	 *  运单表里货物总件数
	 */
	private Integer goodsQtyTotal;
	/**
	 *  签收状态
	 */
	private String signStatus;
	/**
	 *  起始时间
	 */
	private Date signTimeStart;
	/**
	 *  结束时间
	 */
	private Date signTimeEnd;
	/**
	 * 运单号集合
	 */
	private List<String> waybillNos;
	/**
	 * 是否有异常
	 */
	private String isException;
	/**
	 * 签收情况
	 */
	private String situation;
	/**
	 * 客户名称
	 */
	private String customerName;
	/**
	 * 是否给收货客户发短信
	 */
	private String isSendSMSToReceiveCustomer;

	/**
	 * 到达城市
	 */
	private String arriveCity;
	/**
	 *收货 部门手机号
	 */
	private String receiveOrgTel;
	/**
	 * 到达部门手机号
	 */
	private String customerPickupOrgTel;
	/**
	 * 到达部门电话
	 */
	private String arriveDepTelephone;
	/**
	 * 是否停发收货人短信-----提供给中转的营业部签收接口  快递100需求 要求停发
	 */
	private boolean isRecordReceiverMessageStop;
	/**
	 *  是否PDA签收
	 */
	private String isPdaSign;
	/**
	 * 快递员姓名
	 */
	private String expressEmpName;
	/**
	 * 快递员电话
	 */
	private String expressEmpTel;
	
	/**
	 * 原单单号
	 */
	private String rawWaybillNo;

	/**
	 * 
	 *
	 * @return 
	 */
	public String getId() {
		return id;
	}

	/**
	 * 
	 *
	 * @param id 
	 */
	public void setId(String id) {
		this.id = id;
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
	public String getIdentifytype() {
		return identifytype;
	}

	/**
	 * Sets the 证件类型.
	 *
	 * @param identifytype the new 证件类型
	 */
	public void setIdentifytype(String identifytype) {
		this.identifytype = identifytype;
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
	 * Gets the 运单表里货物总件数.
	 *
	 * @return the 运单表里货物总件数
	 */
	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	/**
	 * Sets the 运单表里货物总件数.
	 *
	 * @param goodsQtyTotal the new 运单表里货物总件数
	 */
	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
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
	 * Gets the 起始时间.
	 *
	 * @return the 起始时间
	 */
	public Date getSignTimeStart() {
		return signTimeStart;
	}

	/**
	 * Sets the 起始时间.
	 *
	 * @param signTimeStart the new 起始时间
	 */
	public void setSignTimeStart(Date signTimeStart) {
		this.signTimeStart = signTimeStart;
	}

	/**
	 * Gets the 结束时间.
	 *
	 * @return the 结束时间
	 */
	public Date getSignTimeEnd() {
		return signTimeEnd;
	}

	/**
	 * Sets the 结束时间.
	 *
	 * @param signTimeEnd the new 结束时间
	 */
	public void setSignTimeEnd(Date signTimeEnd) {
		this.signTimeEnd = signTimeEnd;
	}

	/**
	 * Gets the 运单号集合.
	 *
	 * @return the 运单号集合
	 */
	public List<String> getWaybillNos() {
		return waybillNos;
	}

	/**
	 * Sets the 运单号集合.
	 *
	 * @param waybillNos the new 运单号集合
	 */
	public void setWaybillNos(List<String> waybillNos) {
		this.waybillNos = waybillNos;
	}

	/**
	 * Gets the 是否有异常.
	 *
	 * @return the 是否有异常
	 */
	public String getIsException() {
		return isException;
	}

	/**
	 * Sets the 是否有异常.
	 *
	 * @param isException the new 是否有异常
	 */
	public void setIsException(String isException) {
		this.isException = isException;
	}

	/**
	 * Gets the 签收情况.
	 *
	 * @return the 签收情况
	 */
	public String getSituation() {
		return situation;
	}

	/**
	 * Sets the 签收情况.
	 *
	 * @param situation the new 签收情况
	 */
	public void setSituation(String situation) {
		this.situation = situation;
	}

	/**
	 * Gets the 客户名称.
	 *
	 * @return the 客户名称
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * Sets the 客户名称.
	 *
	 * @param customerName the new 客户名称
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

	public String getIsSendSMSToReceiveCustomer() {
		return isSendSMSToReceiveCustomer;
	}

	public void setIsSendSMSToReceiveCustomer(String isSendSMSToReceiveCustomer) {
		this.isSendSMSToReceiveCustomer = isSendSMSToReceiveCustomer;
	}

	public String getArriveCity() {
		return arriveCity;
	}

	public void setArriveCity(String arriveCity) {
		this.arriveCity = arriveCity;
	}

	public String getReceiveOrgTel() {
		return receiveOrgTel;
	}

	public void setReceiveOrgTel(String receiveOrgTel) {
		this.receiveOrgTel = receiveOrgTel;
	}

	public String getCustomerPickupOrgTel() {
		return customerPickupOrgTel;
	}

	public void setCustomerPickupOrgTel(String customerPickupOrgTel) {
		this.customerPickupOrgTel = customerPickupOrgTel;
	}

	public boolean isRecordReceiverMessageStop() {
		return isRecordReceiverMessageStop;
	}

	public void setRecordReceiverMessageStop(boolean isRecordReceiverMessageStop) {
		this.isRecordReceiverMessageStop = isRecordReceiverMessageStop;
	}

	public String getIsPdaSign() {
		return isPdaSign;
	}

	public void setIsPdaSign(String isPdaSign) {
		this.isPdaSign = isPdaSign;
	}
	public String getExpressEmpName() {
		return expressEmpName;
	}

	public void setExpressEmpName(String expressEmpName) {
		this.expressEmpName = expressEmpName;
	}

	public String getExpressEmpTel() {
		return expressEmpTel;
	}

	public void setExpressEmpTel(String expressEmpTel) {
		this.expressEmpTel = expressEmpTel;
	}

	public String getRawWaybillNo() {
		return rawWaybillNo;
	}

	public void setRawWaybillNo(String rawWaybillNo) {
		this.rawWaybillNo = rawWaybillNo;
	}

	/**
	 * Gets the 到达部门电话.
	 *
	 * @return the 到达部门电话
	 */
	public String getArriveDepTelephone() {
		return arriveDepTelephone;
	}

	/**
	 * Sets the 到达部门电话.
	 *
	 * @param arriveDepTelephone the new 到达部门电话
	 */
	public void setArriveDepTelephone(String arriveDepTelephone) {
		this.arriveDepTelephone = arriveDepTelephone;
	}
	

}