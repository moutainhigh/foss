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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/dto/ContrabandInfoDto.java
 * 
 * FILE NAME        	: ContrabandInfoDto.java
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

import com.deppon.foss.module.pickup.sign.api.shared.domain.SignDetailEntity;

/**
 * 
 * 违禁品签收dto
 * @author foss-meiying
 * @date 2013-1-25 下午3:41:47
 * @since
 * @version
 */
public class ContrabandInfoDto implements Serializable{
	private static final long serialVersionUID = 8818181445539860884L;
	/**
	 * 运输性质
	 */
	private String productCode;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**  
	 * 签收件数
	 */
	private Integer signGoodsQty;
	/**
	 * 库存件数
	 */
	private Integer stockGoodsQty;
	/**
	 * 签收备注
	 */
	private String signNote;
	/** 
	 * 签收情况
	 */
	private String situation;
	/**
	 * 签收时间
	 */
	private Date signTime;
	/**
	 * 签收明细集合
	 */
	private List<SignDetailEntity> signDetailList;
	/**
	 * 部门编码
	 */
	private String orgCode;
	/**
	 * 异常类型 
	 */
	private String expType;
	/**
	 * 库区
	 */
	private String goodsAreaCode;
	/**
	 * 快递零担库区
	 */
	private List<String> goodsAreaCodes;
	/**
	 * 违禁品、丢货状态
	 */
	private String exceptionStatus;
	
	public List<String> getGoodsAreaCodes() {
		return goodsAreaCodes;
	}

	public void setGoodsAreaCodes(List<String> goodsAreaCodes) {
		this.goodsAreaCodes = goodsAreaCodes;
	}

	/**
	 * 已生成到达联件数
	 */
	private Integer generateGoodsQty;
	
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
	 * Gets the 部门编码.
	 *
	 * @return the 部门编码
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * Sets the 部门编码.
	 *
	 * @param orgCode the new 部门编码
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * Gets the 签收明细集合.
	 *
	 * @return the 签收明细集合
	 */
	public List<SignDetailEntity> getSignDetailList() {
		return signDetailList;
	}

	/**
	 * Sets the 签收明细集合.
	 *
	 * @param signDetailList the new 签收明细集合
	 */
	public void setSignDetailList(List<SignDetailEntity> signDetailList) {
		this.signDetailList = signDetailList;
	}

	/**
	 * Gets the 库存件数.
	 *
	 * @return the 库存件数
	 */
	public Integer getStockGoodsQty() {
		return stockGoodsQty;
	}

	/**
	 * Sets the 库存件数.
	 *
	 * @param stockGoodsQty the new 库存件数
	 */
	public void setStockGoodsQty(Integer stockGoodsQty) {
		this.stockGoodsQty = stockGoodsQty;
	}

	/**
	 * Gets the 异常类型.
	 *
	 * @return the 异常类型
	 */
	public String getExpType() {
		return expType;
	}

	/**
	 * Sets the 异常类型.
	 *
	 * @param expType the new 异常类型
	 */
	public void setExpType(String expType) {
		this.expType = expType;
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
	 * Gets the 已生成到达联件数.
	 *
	 * @return the 已生成到达联件数
	 */
	public Integer getGenerateGoodsQty() {
		return generateGoodsQty;
	}

	/**
	 * Sets the 已生成到达联件数.
	 *
	 * @param generateGoodsQty the new 已生成到达联件数
	 */
	public void setGenerateGoodsQty(Integer generateGoodsQty) {
		this.generateGoodsQty = generateGoodsQty;
	}

	public String getExceptionStatus() {
		return exceptionStatus;
	}

	public void setExceptionStatus(String exceptionStatus) {
		this.exceptionStatus = exceptionStatus;
	}
	
}