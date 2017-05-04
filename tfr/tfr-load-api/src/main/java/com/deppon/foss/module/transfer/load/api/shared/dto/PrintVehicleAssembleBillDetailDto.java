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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/PrintVehicleAssembleBillDetailDto.java
 *  
 *  FILE NAME          :PrintVehicleAssembleBillDetailDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 打印配载单Detaildto
 * @author ibm-zhangyixin
 * @date 2012-12-25 下午6:42:16
 */
public class PrintVehicleAssembleBillDetailDto extends BaseEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2211762110467200432L;
	
	/**运输性质**/
	private String transportType;				

	/**运单号**/
	private String waybillNo;					

	/**交接单号**/
	private String handoverNo;					

	/**收货部门**/
	private String receiveOrgName;				

	/**目的站**/
	private String reachOrgName;				

	/**收货人**/
	private String receiveCustomerName;			

	/**收货人手机**/
	private String receiveCustomerMobilephone;	

	/**收货人电话**/
	private String receiveCustomerPhone;		

	/**品名**/
	private String goodsName;					

	/**包装**/
	private String goodsPackage;				

	/**交接件数**/
	private BigDecimal handoverGoodsQty;		

	/**总件数**/
	private BigDecimal goodsQtyTotal;			

	/**总重量**/
	private BigDecimal goodsWeightTotal;		

	/**总体积**/
	private BigDecimal goodsVolumeTotal;		

	/**备注**/
	private String waybillNotes;				
	
	/**
	 * 获取 运输性质*.
	 *
	 * @return the 运输性质*
	 */
	public String getTransportType() {
		return transportType;
	}
	
	/**
	 * 设置 运输性质*.
	 *
	 * @param transportType the new 运输性质*
	 */
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	
	/**
	 * 获取 运单号*.
	 *
	 * @return the 运单号*
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	
	/**
	 * 设置 运单号*.
	 *
	 * @param waybillNo the new 运单号*
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	
	/**
	 * 获取 收货部门*.
	 *
	 * @return the 收货部门*
	 */
	public String getReceiveOrgName() {
		return receiveOrgName;
	}
	
	/**
	 * 设置 收货部门*.
	 *
	 * @param receiveOrgName the new 收货部门*
	 */
	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}
	
	/**
	 * 获取 目的站*.
	 *
	 * @return the 目的站*
	 */
	public String getReachOrgName() {
		return reachOrgName;
	}
	
	/**
	 * 设置 目的站*.
	 *
	 * @param reachOrgName the new 目的站*
	 */
	public void setReachOrgName(String reachOrgName) {
		this.reachOrgName = reachOrgName;
	}
	
	/**
	 * 获取 收货人*.
	 *
	 * @return the 收货人*
	 */
	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}
	
	/**
	 * 设置 收货人*.
	 *
	 * @param receiveCustomerName the new 收货人*
	 */
	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}
	
	/**
	 * 获取 收货人手机*.
	 *
	 * @return the 收货人手机*
	 */
	public String getReceiveCustomerMobilephone() {
		return receiveCustomerMobilephone;
	}
	
	/**
	 * 设置 收货人手机*.
	 *
	 * @param receiveCustomerMobilephone the new 收货人手机*
	 */
	public void setReceiveCustomerMobilephone(String receiveCustomerMobilephone) {
		this.receiveCustomerMobilephone = receiveCustomerMobilephone;
	}
	
	/**
	 * 获取 收货人电话*.
	 *
	 * @return the 收货人电话*
	 */
	public String getReceiveCustomerPhone() {
		return receiveCustomerPhone;
	}
	
	/**
	 * 设置 收货人电话*.
	 *
	 * @param receiveCustomerPhone the new 收货人电话*
	 */
	public void setReceiveCustomerPhone(String receiveCustomerPhone) {
		this.receiveCustomerPhone = receiveCustomerPhone;
	}
	
	/**
	 * 获取 品名*.
	 *
	 * @return the 品名*
	 */
	public String getGoodsName() {
		return goodsName;
	}
	
	/**
	 * 设置 品名*.
	 *
	 * @param goodsName the new 品名*
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	/**
	 * 获取 包装*.
	 *
	 * @return the 包装*
	 */
	public String getGoodsPackage() {
		return goodsPackage;
	}
	
	/**
	 * 设置 包装*.
	 *
	 * @param goodsPackage the new 包装*
	 */
	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}
	
	/**
	 * 获取 总件数*.
	 *
	 * @return the 总件数*
	 */
	public BigDecimal getGoodsQtyTotal() {
		return goodsQtyTotal;
	}
	
	/**
	 * 设置 总件数*.
	 *
	 * @param goodsQtyTotal the new 总件数*
	 */
	public void setGoodsQtyTotal(BigDecimal goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}
	
	/**
	 * 获取 总重量*.
	 *
	 * @return the 总重量*
	 */
	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}
	
	/**
	 * 设置 总重量*.
	 *
	 * @param goodsWeightTotal the new 总重量*
	 */
	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}
	
	/**
	 * 获取 总体积*.
	 *
	 * @return the 总体积*
	 */
	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}
	
	/**
	 * 设置 总体积*.
	 *
	 * @param goodsVolumeTotal the new 总体积*
	 */
	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}
	
	/**
	 * 获取 备注*.
	 *
	 * @return the 备注*
	 */
	public String getWaybillNotes() {
		return waybillNotes;
	}
	
	/**
	 * 设置 备注*.
	 *
	 * @param waybillNotes the new 备注*
	 */
	public void setWaybillNotes(String waybillNotes) {
		this.waybillNotes = waybillNotes;
	}
	
	/**
	 * 获取 交接件数*.
	 *
	 * @return the 交接件数*
	 */
	public BigDecimal getHandoverGoodsQty() {
		return handoverGoodsQty;
	}
	
	/**
	 * 设置 交接件数*.
	 *
	 * @param handoverGoodsQty the new 交接件数*
	 */
	public void setHandoverGoodsQty(BigDecimal handoverGoodsQty) {
		this.handoverGoodsQty = handoverGoodsQty;
	}
	
	/**
	 * 获取 交接单号*.
	 *
	 * @return the 交接单号*
	 */
	public String getHandoverNo() {
		return handoverNo;
	}
	
	/**
	 * 设置 交接单号*.
	 *
	 * @param handoverNo the new 交接单号*
	 */
	public void setHandoverNo(String handoverNo) {
		this.handoverNo = handoverNo;
	}
}