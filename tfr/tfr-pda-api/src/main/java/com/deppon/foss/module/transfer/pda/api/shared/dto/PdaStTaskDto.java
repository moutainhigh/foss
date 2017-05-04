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
 *  PROJECT NAME  : tfr-pda-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/pda/api/shared/dto/PdaStTaskDto.java
 *  
 *  FILE NAME          :PdaStTaskDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.pda.api.shared.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.common.api.shared.dto.BaseDataDictDto;

public class PdaStTaskDto {
	/** 清仓任务编号 */
	private String stTaskNo;
	private String deptCode;
	/** 运单号 */
	private String waybillNo;
	/** 流水号 */
	private String serialNo;
	/** 流水号List */
	private List<PDAGoodsSerialNoDto> serialNos = new ArrayList<PDAGoodsSerialNoDto>();
	/** 类型 */
	private String productCode;
	/** 重量 */
	private double goodsWeight;
	/** 体积 */
	private double goodsVolume;
	/** 货名 */
	private String goodsName;
	/** 入库时间 */
	private Date inStockTime;
	/** 是否贵重物品 */
	private String preciousGoods;
	/** 是否代打木架 */
	private String needWoodenPackage;
	/** 是否有更改 */
	private String hasChange;
	/** 更改内容提示 */
	private String changeContent;
	/** 是否违禁品 */
	private String contraband;
	/** 创建人 */
	private String creatorCode;
	/** 包装类型 */
	private String packageType;
	/** 收货部门 */
	private String receiveOrgCode;
	/** 收货部门名称 */
	private String receiveOrgName;
	/** 到达网点 */
	private String targetOrgCode;
	/** 到达网点名称 */
	private String targetOrgName;
	/** 是否标签作废 */
	private String labelTrash;
	/** 开单件数 */
	private int wayBillQty;
	/** 相对于一个运单号PDA已处理件数 */
	private int finishedQty;

	private String stationNumber;

	// 货区名称
	private String goodsAreaName;
	// 货区编号
	private String goodsAreaCode;
	// 库位号List
	private List<BaseDataDictDto> positionNoList;
	/** 收货区名称 */
	private String receiveCustomerDistName;

	private String receiveCustomerDistCode;

	private String customerPickupOrgCode;

	public String getReceiveCustomerDistName() {
		return receiveCustomerDistName;
	}

	public void setReceiveCustomerDistName(String receiveCustomerDistName) {
		this.receiveCustomerDistName = receiveCustomerDistName;
	}

	public List<BaseDataDictDto> getPositionNoList() {
		return positionNoList;
	}

	public void setPositionNoList(List<BaseDataDictDto> positionNoList) {
		this.positionNoList = positionNoList;
	}

	public String getGoodsAreaName() {
		return goodsAreaName;
	}

	public void setGoodsAreaName(String goodsAreaName) {
		this.goodsAreaName = goodsAreaName;
	}

	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}

	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}

	public String getStationNumber() {
		return stationNumber;
	}

	public void setStationNumber(String stationNumber) {
		this.stationNumber = stationNumber;
	}

	public String getStTaskNo() {
		return stTaskNo;
	}

	public void setStTaskNo(String stTaskNo) {
		this.stTaskNo = stTaskNo;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public double getGoodsWeight() {
		return goodsWeight;
	}

	public void setGoodsWeight(double goodsWeight) {
		this.goodsWeight = goodsWeight;
	}

	public double getGoodsVolume() {
		return goodsVolume;
	}

	public void setGoodsVolume(double goodsVolume) {
		this.goodsVolume = goodsVolume;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Date getInStockTime() {
		return inStockTime;
	}

	public void setInStockTime(Date inStockTime) {
		this.inStockTime = inStockTime;
	}

	public String getChangeContent() {
		return changeContent;
	}

	public void setChangeContent(String changeContent) {
		this.changeContent = changeContent;
	}

	public String getPackageType() {
		return packageType;
	}

	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}

	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	public String getTargetOrgCode() {
		return targetOrgCode;
	}

	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
	}

	public String getCreatorCode() {
		return creatorCode;
	}

	public void setCreatorCode(String creatorCode) {
		this.creatorCode = creatorCode;
	}

	public int getWayBillQty() {
		return wayBillQty;
	}

	public void setWayBillQty(int wayBillQty) {
		this.wayBillQty = wayBillQty;
	}

	public String getPreciousGoods() {
		return preciousGoods;
	}

	public void setPreciousGoods(String preciousGoods) {
		this.preciousGoods = preciousGoods;
	}

	public String getNeedWoodenPackage() {
		return needWoodenPackage;
	}

	public void setNeedWoodenPackage(String needWoodenPackage) {
		this.needWoodenPackage = needWoodenPackage;
	}

	public String getHasChange() {
		return hasChange;
	}

	public void setHasChange(String hasChange) {
		this.hasChange = hasChange;
	}

	public String getContraband() {
		return contraband;
	}

	public void setContraband(String contraband) {
		this.contraband = contraband;
	}

	public String getLabelTrash() {
		return labelTrash;
	}

	public void setLabelTrash(String labelTrash) {
		this.labelTrash = labelTrash;
	}

	public List<PDAGoodsSerialNoDto> getSerialNos() {
		return serialNos;
	}

	public void setSerialNos(List<PDAGoodsSerialNoDto> serialNos) {
		this.serialNos = serialNos;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getReceiveOrgName() {
		return receiveOrgName;
	}

	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}

	public String getTargetOrgName() {
		return targetOrgName;
	}

	public void setTargetOrgName(String targetOrgName) {
		this.targetOrgName = targetOrgName;
	}

	public int getFinishedQty() {
		return finishedQty;
	}

	public void setFinishedQty(int finishedQty) {
		this.finishedQty = finishedQty;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}

	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}

	public String getReceiveCustomerDistCode() {
		return receiveCustomerDistCode;
	}

	public void setReceiveCustomerDistCode(String receiveCustomerDistCode) {
		this.receiveCustomerDistCode = receiveCustomerDistCode;
	}

	@Override
	public String toString() {
		return "PdaStTaskDto [stTaskNo=" + stTaskNo + ", deptCode=" + deptCode + ", waybillNo=" + waybillNo
				+ ", stationNumber=" + stationNumber + "]";
	}

	

}