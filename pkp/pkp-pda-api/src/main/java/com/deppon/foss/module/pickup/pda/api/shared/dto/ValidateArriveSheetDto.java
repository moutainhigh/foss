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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pda/api/shared/dto/ValidateArriveSheetDto.java
 * 
 * FILE NAME        	: ValidateArriveSheetDto.java
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
import java.util.List;
/**
 * 
 *检验到达联接口返回类
 * @author 
 *		097972-foss-dengtingting
 * @date 
 *     2012-12-12 下午2:11:58
 * @since
 * @version
 */
public class ValidateArriveSheetDto implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 件数
	 */
	private Integer arriveSheetGoodsQty;
	/**
	 * 运输性质
	 */
	private String productCode;
	/**
	 *  提货网点
	 */
	private String customerPickupOrgCode;
	/**
	 *  提货方式
	 */
	private String receiveMethod;
	/**
	 *  货物总体积
	 */
	private BigDecimal goodsVolumeTotal;
	/**
	 * 流水号集合
	 */
	private List<String> serialNos;
	/**
	 * 特殊增值服务
	 */
	private String specialValueAddedService;
	/**
	 *  投诉变更状态
	 */
	private String complainStatus;
	/**
	 * 获取 投诉变更状态
	 * @return complainStatus
	 */
	public String getComplainStatus() {
		return complainStatus;
	}
	/**
	 * set 投诉变更状态
	 * @param complainStatus
	 */
	public void setComplainStatus(String complainStatus) {
		this.complainStatus = complainStatus;
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
	 * Gets the 件数.
	 *
	 * @return the 件数
	 */
	public Integer getArriveSheetGoodsQty() {
		return arriveSheetGoodsQty;
	}

	/**
	 * Sets the 件数.
	 *
	 * @param arriveSheetGoodsQty the new 件数
	 */
	public void setArriveSheetGoodsQty(Integer arriveSheetGoodsQty) {
		this.arriveSheetGoodsQty = arriveSheetGoodsQty;
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
	 * Gets the 提货网点.
	 *
	 * @return the 提货网点
	 */
	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}

	/**
	 * Sets the 提货网点.
	 *
	 * @param customerPickupOrgCode the new 提货网点
	 */
	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
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
	 * Gets the 货物总体积.
	 *
	 * @return the 货物总体积
	 */
	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	/**
	 * Sets the 货物总体积.
	 *
	 * @param goodsVolumeTotal the new 货物总体积
	 */
	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	public List<String> getSerialNos() {
		return serialNos;
	}

	public void setSerialNos(List<String> serialNos) {
		this.serialNos = serialNos;
	}

	public String getSpecialValueAddedService() {
		return specialValueAddedService;
	}

	public void setSpecialValueAddedService(String specialValueAddedService) {
		this.specialValueAddedService = specialValueAddedService;
	}
}