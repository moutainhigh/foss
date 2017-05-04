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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/WayBillLocusDto.java
 * 
 * FILE NAME        	: WayBillLocusDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * PDA查询货物DTO
 * 
 * @author ibm-wangfei
 * @date Jan 11, 2013 3:59:11 PM
 */
public class WayBillLocusDto implements Serializable {

	private static final long serialVersionUID = -6314003626261024181L;
	/**
	 * 运单号 
	 */
	private String waybillNo;
	/**
	 * 货物名称
	 */
	private String goodsName;
	/**
	 * 目的站
	 */
	private String targetOrgCode;
	/**
	 * 提货方式
	 */
	private String receiveMethod;
	/**
	 * 运输性质
	 */
	private String productCode;
	/**
	 * 货物总件数
	 */
	private Integer goodsQtyTotal;

	/**
	 * 货物总重量
	 */
	private BigDecimal goodsWeightTotal;

	/**
	 * 货物总体积
	 */
	private BigDecimal goodsVolumeTotal;
	
	/**
	 * 货物追踪信息 PDA需要的 操作时间 operateTime ，PDA操作内容notes
	 */
	private List<WayBillNoLocusDTO> wayBillNoLocusDTOList;
	
	/**
	 * 获取 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * 设置 运单号.
	 *
	 * @param waybillNo the new 运单号
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * 获取 货物名称.
	 *
	 * @return the 货物名称
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * 设置 货物名称.
	 *
	 * @param goodsName the new 货物名称
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * 获取 目的站.
	 *
	 * @return the 目的站
	 */
	public String getTargetOrgCode() {
		return targetOrgCode;
	}

	/**
	 * 设置 目的站.
	 *
	 * @param targetOrgCode the new 目的站
	 */
	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
	}

	/**
	 * 获取 提货方式.
	 *
	 * @return the 提货方式
	 */
	public String getReceiveMethod() {
		return receiveMethod;
	}

	/**
	 * 设置 提货方式.
	 *
	 * @param receiveMethod the new 提货方式
	 */
	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	/**
	 * 获取 运输性质.
	 *
	 * @return the 运输性质
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * 设置 运输性质.
	 *
	 * @param productCode the new 运输性质
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * 获取 货物总件数.
	 *
	 * @return the 货物总件数
	 */
	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	/**
	 * 设置 货物总件数.
	 *
	 * @param goodsQtyTotal the new 货物总件数
	 */
	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	/**
	 * 获取 货物总重量.
	 *
	 * @return the 货物总重量
	 */
	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}

	/**
	 * 设置 货物总重量.
	 *
	 * @param goodsWeightTotal the new 货物总重量
	 */
	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}

	/**
	 * 获取 货物总体积.
	 *
	 * @return the 货物总体积
	 */
	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	/**
	 * 设置 货物总体积.
	 *
	 * @param goodsVolumeTotal the new 货物总体积
	 */
	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	/**
	 * 获取 货物追踪信息.
	 *
	 * @return the 货物追踪信息
	 */
	public List<WayBillNoLocusDTO> getWayBillNoLocusDTOList() {
		return wayBillNoLocusDTOList;
	}

	/**
	 * 设置 货物追踪信息.
	 *
	 * @param wayBillNoLocusDTOList the new 货物追踪信息
	 */
	public void setWayBillNoLocusDTOList(List<WayBillNoLocusDTO> wayBillNoLocusDTOList) {
		this.wayBillNoLocusDTOList = wayBillNoLocusDTOList;
	}
}