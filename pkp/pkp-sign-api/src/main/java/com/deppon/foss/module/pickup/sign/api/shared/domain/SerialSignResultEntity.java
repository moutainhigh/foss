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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/domain/SerialSignResultEntity.java
 * 
 * FILE NAME        	: SerialSignResultEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 外发流水签收Entity
 * @author foss-sunyanfei
 * @date 2015-10-19 上午9:45:28
 * @since
 * @version
 */
public class SerialSignResultEntity extends BaseEntity{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 2480073985081803136L;
	/**
	 *  运单号
	 */
	private String waybillNo;
	/** 
	 * 流水号
	 */
	private String serialNo;
	/***
	 * 外发单号
	 */
	private String externalWaybillNo;
	/**
	 * 流水号签收情况
	 */
	private String signSituation;
	/**
	 * 签收人
	 */
	private String deliverymanName;
	/**
	 * 签收件数
	 */
	private Integer signGoodsQty;
	/**
	 * 是否有效
	 */
	private String active;
	/**
	 * 签收时间
	 */
	private Date signTime;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 失效/修改时间
	 */
	private Date modifyTime;
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
	
	public String getWaybillNo() {
		return waybillNo;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public String getExternalWaybillNo() {
		return externalWaybillNo;
	}
	public String getSignSituation() {
		return signSituation;
	}
	public String getDeliverymanName() {
		return deliverymanName;
	}
	public Integer getSignGoodsQty() {
		return signGoodsQty;
	}
	public String getActive() {
		return active;
	}
	public Date getSignTime() {
		return signTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public String getCreateOrgName() {
		return createOrgName;
	}
	public String getCreator() {
		return creator;
	}
	public String getCreatorCode() {
		return creatorCode;
	}
	public void setCreatorCode(String creatorCode) {
		this.creatorCode = creatorCode;
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
	 * Sets the 流水号.
	 *
	 * @param serialNo the new 流水号
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	/**
	 * Sets the 外发单号.
	 *
	 * @param externalWaybillNo the new 外发单号
	 */
	public void setExternalWaybillNo(String externalWaybillNo) {
		this.externalWaybillNo = externalWaybillNo;
	}
	/**
	 * Sets the 流水号签收情况.
	 *
	 * @param signSituation the new 流水号签收情况
	 */
	public void setSignSituation(String signSituation) {
		this.signSituation = signSituation;
	}
	/**
	 * Sets the 签收人.
	 *
	 * @param deliverymanName the new 签收人
	 */
	public void setDeliverymanName(String deliverymanName) {
		this.deliverymanName = deliverymanName;
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
	 * Sets the 是否有效.
	 *
	 * @param active the new 是否有效
	 */
	public void setActive(String active) {
		this.active = active;
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
	 * Sets the 创建时间.
	 *
	 * @param createTime the new 创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * Sets the 失效/修改时间.
	 *
	 * @param modifyTime the new 失效/修改时间
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getCreateOrgCode() {
		return createOrgCode;
	}
	/**
	 * Sets the 签收部门编码.
	 *
	 * @param createOrgCode the new 签收部门编码
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}
	/**
	 * Sets the 签收部门名称.
	 *
	 * @param createOrgName the new 签收部门名称
	 */
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}
	/**
	 * Sets the 操作人.
	 *
	 * @param creator the new 操作人
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}
	/**
	 * Sets the 操作人编码.
	 *
	 * @param creatorCode the new 操作人编码
	 */
	
}