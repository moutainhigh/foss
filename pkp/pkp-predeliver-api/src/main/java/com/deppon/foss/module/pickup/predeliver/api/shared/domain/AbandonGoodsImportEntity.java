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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/domain/AbandonGoodsImportEntity.java
 * 
 * FILE NAME        	: AbandonGoodsImportEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 内部带货中间导入表 
 * @date 2012-11-27 上午11:02:07
 */
public class AbandonGoodsImportEntity implements Serializable{

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
    private String id;

    /**
     * 状态  新导入和已经被gui使用
     */
    private String status;

    /**
     * 运单号
     */
    private String waybillNo;
    
    /**
     * 货物名称
     */
    private String goodsName;

    /**
     * 重量--运单
     */
  	private BigDecimal goodsWeightTotal;
  	
  	/**
  	 * 体积--运单
  	 */
  	private BigDecimal goodsVolumeTotal;
  	
  	/**
  	 * 发货人--(运单--发货客户联系人)
  	 */
  	private String deliveryCustomerContact;
    
  	/**
  	 * 发货人电话
  	 */
  	private String deliveryCustomerPhone;
  	
  	/**
  	 * 发货人手机
  	 */
  	private String deliveryCustomerMobilephone;
  	/**
  	 * 发货部门
  	 */
  	private String receiveOrgCode;
  	/**
  	 * 所属区域(运单中的部门编码--调用李俊给的接口，获得所属区域)
  	 */
  	private String respectiveRegional;
  	
	/**
	 * 收货人
	 */
	private String receiveCustomerContact;
	
	/**
	 * 收货人电话
	 */
	private String receiveCustomerPhone;
	
	/**
	 * 到达部门(运单--最终配载部门--在营业部表中，把CODE改成NAME)
	 */
	private String lastLoadOrgCode;
	
	/**
	 * 仓储部门 = 到达部门
	 */
	private String storageOrgCode;
  	
	/**
	 * 代收金额
	 */
	private BigDecimal codAmount;
	
	/**
	 * 保险金额
	 */
	private BigDecimal insuranceAmount;
	
	/**
	 * 预付金额
	 */
	private BigDecimal prePayAmount;
	
	/**
	 * 到付金额
	 */
	private BigDecimal toPayAmount;
	
	/**
	 * 弃货类型--(申请转弃货--弃货类型)
	 */
	private String abandonedgoodsType;
	
	/**
	 * 处理状态----(申请转弃货--状态)
	 */
	private String abandonedgoodsStatus;
	
	/**
	 *  货物总件数
	 */
    private Integer goodsQtyTotal;
    
    /**
     * 预弃货时间
     */
    private Date preabandonedgoodsTime;


    /**
     * 预弃货人--（记录的创建人姓名）
     */
    private String createUserName;

    /**
     * 弃货事由
     */
    private String notes;
    
    /**
     * 天数
     */
	private Integer storageDay;
	/*
	 * 导入时间
	 */
	private Date createTime;

	/*
	 * 修改时间
	 */
    private Date modifyTime;

    /*
     * 创建人CODE
     */
    private String createUserCode;

    /*
     * 创建部门CODE
     */
    private String createOrgCode;

    /*
     * 是否激活
     */
    private String active;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
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
	 * @param waybillNo the waybillNo to set
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
	 * @param goodsName the goodsName to set
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * @return the goodsWeightTotal
	 */
	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}

	/**
	 * @param goodsWeightTotal the goodsWeightTotal to set
	 */
	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}

	/**
	 * @return the goodsVolumeTotal
	 */
	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	/**
	 * @param goodsVolumeTotal the goodsVolumeTotal to set
	 */
	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	/**
	 * @return the deliveryCustomerContact
	 */
	public String getDeliveryCustomerContact() {
		return deliveryCustomerContact;
	}

	/**
	 * @param deliveryCustomerContact the deliveryCustomerContact to set
	 */
	public void setDeliveryCustomerContact(String deliveryCustomerContact) {
		this.deliveryCustomerContact = deliveryCustomerContact;
	}

	/**
	 * @return the deliveryCustomerPhone
	 */
	public String getDeliveryCustomerPhone() {
		return deliveryCustomerPhone;
	}

	/**
	 * @param deliveryCustomerPhone the deliveryCustomerPhone to set
	 */
	public void setDeliveryCustomerPhone(String deliveryCustomerPhone) {
		this.deliveryCustomerPhone = deliveryCustomerPhone;
	}

	/**
	 * @return the deliveryCustomerMobilephone
	 */
	public String getDeliveryCustomerMobilephone() {
		return deliveryCustomerMobilephone;
	}

	/**
	 * @param deliveryCustomerMobilephone the deliveryCustomerMobilephone to set
	 */
	public void setDeliveryCustomerMobilephone(String deliveryCustomerMobilephone) {
		this.deliveryCustomerMobilephone = deliveryCustomerMobilephone;
	}

	/**
	 * @return the receiveOrgCode
	 */
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	/**
	 * @param receiveOrgCode the receiveOrgCode to set
	 */
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	/**
	 * @return the respectiveRegional
	 */
	public String getRespectiveRegional() {
		return respectiveRegional;
	}

	/**
	 * @param respectiveRegional the respectiveRegional to set
	 */
	public void setRespectiveRegional(String respectiveRegional) {
		this.respectiveRegional = respectiveRegional;
	}

	/**
	 * @return the receiveCustomerContact
	 */
	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}

	/**
	 * @param receiveCustomerContact the receiveCustomerContact to set
	 */
	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}

	/**
	 * @return the receiveCustomerPhone
	 */
	public String getReceiveCustomerPhone() {
		return receiveCustomerPhone;
	}

	/**
	 * @param receiveCustomerPhone the receiveCustomerPhone to set
	 */
	public void setReceiveCustomerPhone(String receiveCustomerPhone) {
		this.receiveCustomerPhone = receiveCustomerPhone;
	}

	/**
	 * @return the lastLoadOrgCode
	 */
	public String getLastLoadOrgCode() {
		return lastLoadOrgCode;
	}

	/**
	 * @param lastLoadOrgCode the lastLoadOrgCode to set
	 */
	public void setLastLoadOrgCode(String lastLoadOrgCode) {
		this.lastLoadOrgCode = lastLoadOrgCode;
	}

	/**
	 * @return the storageOrgCode
	 */
	public String getStorageOrgCode() {
		return storageOrgCode;
	}

	/**
	 * @param storageOrgCode the storageOrgCode to set
	 */
	public void setStorageOrgCode(String storageOrgCode) {
		this.storageOrgCode = storageOrgCode;
	}

	/**
	 * @return the codAmount
	 */
	public BigDecimal getCodAmount() {
		return codAmount;
	}

	/**
	 * @param codAmount the codAmount to set
	 */
	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}

	/**
	 * @return the insuranceAmount
	 */
	public BigDecimal getInsuranceAmount() {
		return insuranceAmount;
	}

	/**
	 * @param insuranceAmount the insuranceAmount to set
	 */
	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	/**
	 * @return the prePayAmount
	 */
	public BigDecimal getPrePayAmount() {
		return prePayAmount;
	}

	/**
	 * @param prePayAmount the prePayAmount to set
	 */
	public void setPrePayAmount(BigDecimal prePayAmount) {
		this.prePayAmount = prePayAmount;
	}

	/**
	 * @return the toPayAmount
	 */
	public BigDecimal getToPayAmount() {
		return toPayAmount;
	}

	/**
	 * @param toPayAmount the toPayAmount to set
	 */
	public void setToPayAmount(BigDecimal toPayAmount) {
		this.toPayAmount = toPayAmount;
	}

	/**
	 * @return the abandonedgoodsType
	 */
	public String getAbandonedgoodsType() {
		return abandonedgoodsType;
	}

	/**
	 * @param abandonedgoodsType the abandonedgoodsType to set
	 */
	public void setAbandonedgoodsType(String abandonedgoodsType) {
		this.abandonedgoodsType = abandonedgoodsType;
	}

	/**
	 * @return the abandonedgoodsStatus
	 */
	public String getAbandonedgoodsStatus() {
		return abandonedgoodsStatus;
	}

	/**
	 * @param abandonedgoodsStatus the abandonedgoodsStatus to set
	 */
	public void setAbandonedgoodsStatus(String abandonedgoodsStatus) {
		this.abandonedgoodsStatus = abandonedgoodsStatus;
	}

	/**
	 * @return the goodsQtyTotal
	 */
	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	/**
	 * @param goodsQtyTotal the goodsQtyTotal to set
	 */
	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	/**
	 * @return the preabandonedgoodsTime
	 */
	public Date getPreabandonedgoodsTime() {
		return preabandonedgoodsTime;
	}

	/**
	 * @param preabandonedgoodsTime the preabandonedgoodsTime to set
	 */
	public void setPreabandonedgoodsTime(Date preabandonedgoodsTime) {
		this.preabandonedgoodsTime = preabandonedgoodsTime;
	}

	/**
	 * @return the createUserName
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * @param createUserName the createUserName to set
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return the storageDay
	 */
	public Integer getStorageDay() {
		return storageDay;
	}

	/**
	 * @param storageDay the storageDay to set
	 */
	public void setStorageDay(Integer storageDay) {
		this.storageDay = storageDay;
	}

	/**
	 * @return createTime : return the property createTime.
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime : set the property createTime.
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return modifyTime : return the property modifyTime.
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime : set the property modifyTime.
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return createUserCode : return the property createUserCode.
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * @param createUserCode : set the property createUserCode.
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/**
	 * @return createOrgCode : return the property createOrgCode.
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * @param createOrgCode : set the property createOrgCode.
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * @return active : return the property active.
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active : set the property active.
	 */
	public void setActive(String active) {
		this.active = active;
	}

}