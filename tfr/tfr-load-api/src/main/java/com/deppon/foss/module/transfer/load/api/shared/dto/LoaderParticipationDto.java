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
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/LoaderParticipationDto.java
 *  
 *  FILE NAME          :LoaderParticipationDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-load-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.api.shared.dto
 * FILE    NAME: LoaderParticipationDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.math.BigDecimal;

import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;

/**
 * LoaderParticipationDto
 * @author dp-duyi
 * @date 2012-12-24 上午11:03:22
 */
public class LoaderParticipationDto extends LoaderParticipationEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -678489167486406874L;
	/**ID*/
	private String id;
	/**上级部门编码*/
	private String orgCode;
	/**上级部门名称*/
	private String orgName;
	/**重量*/
	private BigDecimal weight;
	/**体积*/
	private BigDecimal volume;
	/**总件数*/
	private BigDecimal goodsQty;
	/**总票数*/
	private BigDecimal waybillQty;
	/**理货员所属部门编码*/
	private String loaderOrgCode;
	/**理货员所属部门名称*/
	private String loaderOrgName;
	
	/**272681 快递重量，体积，件数，票数*/
	private BigDecimal expressGoodsQty;
	
	private BigDecimal expressWaybillQty;
	
	private BigDecimal expressWeight;
	
	private BigDecimal expressVolume;
	
	/**272681 零担重量，体积，件数，票数*/
	private BigDecimal ldGoodsQty;
	
	private BigDecimal ldWaybillQty;
	
	private BigDecimal ldWeight;
	
	private BigDecimal ldVolume;
	
	/**272681 快递or零担*/
	private String ExpressOrLd;
	
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the 上级部门编码.
	 *
	 * @return the 上级部门编码
	 */
	public String getOrgCode() {
		return orgCode;
	}
	
	/**
	 * Sets the 上级部门编码.
	 *
	 * @param orgCode the new 上级部门编码
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	/**
	 * Gets the 上级部门名称.
	 *
	 * @return the 上级部门名称
	 */
	public String getOrgName() {
		return orgName;
	}
	
	/**
	 * Sets the 上级部门名称.
	 *
	 * @param orgName the new 上级部门名称
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	/**
	 * Gets the 重量.
	 *
	 * @return the 重量
	 */
	public BigDecimal getWeight() {
		return weight;
	}
	
	/**
	 * Sets the 重量.
	 *
	 * @param weight the new 重量
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	
	/**
	 * Gets the 体积.
	 *
	 * @return the 体积
	 */
	public BigDecimal getVolume() {
		return volume;
	}
	
	/**
	 * Sets the 体积.
	 *
	 * @param volume the new 体积
	 */
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	
	/**
	 * Gets the 总件数.
	 *
	 * @return the 总件数
	 */
	public BigDecimal getGoodsQty() {
		return goodsQty;
	}
	
	/**
	 * Sets the 总件数.
	 *
	 * @param goodsQty the new 总件数
	 */
	public void setGoodsQty(BigDecimal goodsQty) {
		this.goodsQty = goodsQty;
	}
	
	/**
	 * Gets the 总票数.
	 *
	 * @return the 总票数
	 */
	public BigDecimal getWaybillQty() {
		return waybillQty;
	}
	
	/**
	 * Sets the 总票数.
	 *
	 * @param waybillQty the new 总票数
	 */
	public void setWaybillQty(BigDecimal waybillQty) {
		this.waybillQty = waybillQty;
	}

	/**
	 * Gets the 理货员所属部门编码.
	 *
	 * @return the 理货员所属部门编码
	 */
	public String getLoaderOrgCode() {
		return loaderOrgCode;
	}

	/**
	 * Sets the 理货员所属部门编码.
	 *
	 * @param loaderOrgCode the new 理货员所属部门编码
	 */
	public void setLoaderOrgCode(String loaderOrgCode) {
		this.loaderOrgCode = loaderOrgCode;
	}

	/**
	 * Gets the 理货员所属部门名称.
	 *
	 * @return the 理货员所属部门名称
	 */
	public String getLoaderOrgName() {
		return loaderOrgName;
	}

	/**
	 * Sets the 理货员所属部门名称.
	 *
	 * @param loaderOrgName the new 理货员所属部门名称
	 */
	public void setLoaderOrgName(String loaderOrgName) {
		this.loaderOrgName = loaderOrgName;
	}

	public BigDecimal getExpressGoodsQty() {
		return expressGoodsQty;
	}

	public void setExpressGoodsQty(BigDecimal expressGoodsQty) {
		this.expressGoodsQty = expressGoodsQty;
	}

	public BigDecimal getExpressWaybillQty() {
		return expressWaybillQty;
	}

	public void setExpressWaybillQty(BigDecimal expressWaybillQty) {
		this.expressWaybillQty = expressWaybillQty;
	}

	public BigDecimal getExpressWeight() {
		return expressWeight;
	}

	public void setExpressWeight(BigDecimal expressWeight) {
		this.expressWeight = expressWeight;
	}

	public BigDecimal getExpressVolume() {
		return expressVolume;
	}

	public void setExpressVolume(BigDecimal expressVolume) {
		this.expressVolume = expressVolume;
	}

	public BigDecimal getLdGoodsQty() {
		return ldGoodsQty;
	}

	public void setLdGoodsQty(BigDecimal ldGoodsQty) {
		this.ldGoodsQty = ldGoodsQty;
	}

	public BigDecimal getLdWaybillQty() {
		return ldWaybillQty;
	}

	public void setLdWaybillQty(BigDecimal ldWaybillQty) {
		this.ldWaybillQty = ldWaybillQty;
	}

	public BigDecimal getLdWeight() {
		return ldWeight;
	}

	public void setLdWeight(BigDecimal ldWeight) {
		this.ldWeight = ldWeight;
	}

	public BigDecimal getLdVolume() {
		return ldVolume;
	}

	public void setLdVolume(BigDecimal ldVolume) {
		this.ldVolume = ldVolume;
	}

	public String getExpressOrLd() {
		return ExpressOrLd;
	}

	public void setExpressOrLd(String expressOrLd) {
		ExpressOrLd = expressOrLd;
	}

	
}