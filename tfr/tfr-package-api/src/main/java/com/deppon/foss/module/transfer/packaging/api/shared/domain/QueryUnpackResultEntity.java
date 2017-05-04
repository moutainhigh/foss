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
 *  PROJECT NAME  : tfr-package-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/packaging/api/shared/domain/QueryUnpackResultEntity.java
 *  
 *  FILE NAME          :QueryUnpackResultEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/**
 * 
 */
package com.deppon.foss.module.transfer.packaging.api.shared.domain;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 查询营业部代打包装的返回结果集实体
 * @author 046130-foss-xuduowei
 * @date 2012-10-12 下午6:19:47
 */
public class QueryUnpackResultEntity extends BaseEntity {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 运单号
	 */
	private String wayBillNumber;
	/**
	 * 开单件数
	 */
	private int waybillNum;
	/**
	 * 开单体积(方)
	 */
	private BigDecimal waybillVolume;
	/**
	 * 需要包装体积(方)
	 */
	private BigDecimal needPackVolume;
	/**代包装货区入库件数
	 * 
	 */
	private int packStockNum;
	/**
	 * 需要包装件数
	 */
	private int needPackNum;
	/**
	 * 已包装件数
	 */
	private int packedNum;
	/**
	 * 货物状态
	 */
	private String goodsStatus;
	/**
	 * 运输类型
	 */
	private String transportationType;
	/**
	 * 货物名称
	 */
	private String goodsName;
	/**
	 * 开单部门
	 */
	private String waybillCreateDept;
	/**
	 * 开单部门编码
	 */
	private String waybillCreateDeptCode;
	/**
	 * 包装部门编码
	 */
	private String packDept;
	/**
	 * 货物开单时间
	 */
	private String waybillCreateDate;
	/**
	 * 预计到达时间
	 */
	private String predictArriveDate;
	/**
	 * 预计发车时间
	 */
	private String predictDepartDate;
	/**
	 * 代包装要求
	 */
	private String packRequire;
	
	/**
	 * 目的站(提货网点)
	 */
	private String customerPickupOrgName;
	
	
	/**
	 * 获取 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWayBillNumber() {
		return wayBillNumber;
	}
	
	/**
	 * 设置 运单号.
	 *
	 * @param wayBillNumber the new 运单号
	 */
	public void setWayBillNumber(String wayBillNumber) {
		this.wayBillNumber = wayBillNumber;
	}
	
	
	/**
	 * 获取 开单件数.
	 *
	 * @return the 开单件数
	 */
	public int getWaybillNum() {
		return waybillNum;
	}

	/**
	 * 设置 开单件数.
	 *
	 * @param waybillNum the new 开单件数
	 */
	public void setWaybillNum(int waybillNum) {
		this.waybillNum = waybillNum;
	}

	/**
	 * 获取 开单体积(方).
	 *
	 * @return the 开单体积(方)
	 */
	public BigDecimal getWaybillVolume() {
		return waybillVolume;
	}

	/**
	 * 设置 开单体积(方).
	 *
	 * @param waybillVolume the new 开单体积(方)
	 */
	public void setWaybillVolume(BigDecimal waybillVolume) {
		this.waybillVolume = waybillVolume.setScale(2,BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 获取 需要包装体积(方).
	 *
	 * @return the 需要包装体积(方)
	 */
	public BigDecimal getNeedPackVolume() {
		return needPackVolume;
	}

	/**
	 * 设置 需要包装体积(方).
	 *
	 * @param needPackVolume the new 需要包装体积(方)
	 */
	public void setNeedPackVolume(BigDecimal needPackVolume) {
		this.needPackVolume = needPackVolume.setScale(2,BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 获取 代包装货区入库件数.
	 *
	 * @return the 代包装货区入库件数
	 */
	public int getPackStockNum() {
		return packStockNum;
	}

	/**
	 * 设置 代包装货区入库件数.
	 *
	 * @param packStockNum the new 代包装货区入库件数
	 */
	public void setPackStockNum(int packStockNum) {
		this.packStockNum = packStockNum;
	}

	/**
	 * 获取 需要包装件数.
	 *
	 * @return the 需要包装件数
	 */
	public int getNeedPackNum() {
		return needPackNum;
	}

	/**
	 * 设置 需要包装件数.
	 *
	 * @param needPackNum the new 需要包装件数
	 */
	public void setNeedPackNum(int needPackNum) {
		this.needPackNum = needPackNum;
	}

	/**
	 * 获取 已包装件数.
	 *
	 * @return the 已包装件数
	 */
	public int getPackedNum() {
		return packedNum;
	}

	/**
	 * 设置 已包装件数.
	 *
	 * @param packedNum the new 已包装件数
	 */
	public void setPackedNum(int packedNum) {
		this.packedNum = packedNum;
	}

	/**
	 * 获取 货物状态.
	 *
	 * @return the 货物状态
	 */
	public String getGoodsStatus() {
		return goodsStatus;
	}
	
	/**
	 * 设置 货物状态.
	 *
	 * @param goodsStatus the new 货物状态
	 */
	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}
	
	/**
	 * 获取 运输类型.
	 *
	 * @return the 运输类型
	 */
	public String getTransportationType() {
		return transportationType;
	}
	
	/**
	 * 设置 运输类型.
	 *
	 * @param transportationType the new 运输类型
	 */
	public void setTransportationType(String transportationType) {
		this.transportationType = transportationType;
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
	 * 获取 开单部门.
	 *
	 * @return the 开单部门
	 */
	public String getWaybillCreateDept() {
		return waybillCreateDept;
	}
	
	/**
	 * 设置 开单部门.
	 *
	 * @param waybillCreateDept the new 开单部门
	 */
	public void setWaybillCreateDept(String waybillCreateDept) {
		this.waybillCreateDept = waybillCreateDept;
	}
	
	/**
	 * 获取 货物开单时间.
	 *
	 * @return the 货物开单时间
	 */
	public String getWaybillCreateDate() {
		return waybillCreateDate;
	}
	
	/**
	 * 设置 货物开单时间.
	 *
	 * @param waybillCreateDate the new 货物开单时间
	 */
	public void setWaybillCreateDate(String waybillCreateDate) {
		this.waybillCreateDate = waybillCreateDate;
	}
	
	/**
	 * 获取 预计到达时间.
	 *
	 * @return the 预计到达时间
	 */
	public String getPredictArriveDate() {
		return predictArriveDate;
	}
	
	/**
	 * 设置 预计到达时间.
	 *
	 * @param predictArriveDate the new 预计到达时间
	 */
	public void setPredictArriveDate(String predictArriveDate) {
		this.predictArriveDate = predictArriveDate;
	}
	
	/**
	 * 获取 预计发车时间.
	 *
	 * @return the 预计发车时间
	 */
	public String getPredictDepartDate() {
		return predictDepartDate;
	}
	
	/**
	 * 设置 预计发车时间.
	 *
	 * @param predictDepartDate the new 预计发车时间
	 */
	public void setPredictDepartDate(String predictDepartDate) {
		this.predictDepartDate = predictDepartDate;
	}
	
	/**
	 * 获取 代包装要求.
	 *
	 * @return the 代包装要求
	 */
	public String getPackRequire() {
		return packRequire;
	}
	
	/**
	 * 设置 代包装要求.
	 *
	 * @param packRequire the new 代包装要求
	 */
	public void setPackRequire(String packRequire) {
		this.packRequire = packRequire;
	}
	
	/**
	 * 获取 开单部门编码.
	 *
	 * @return the 开单部门编码
	 */
	public String getWaybillCreateDeptCode() {
		return waybillCreateDeptCode;
	}
	
	/**
	 * 设置 开单部门编码.
	 *
	 * @param waybillCreateDeptCode the new 开单部门编码
	 */
	public void setWaybillCreateDeptCode(String waybillCreateDeptCode) {
		this.waybillCreateDeptCode = waybillCreateDeptCode;
	}
	
	/**
	 * 获取 包装部门编码.
	 *
	 * @return the 包装部门编码
	 */
	public String getPackDept() {
		return packDept;
	}
	
	/**
	 * 设置 包装部门编码.
	 *
	 * @param packDept the new 包装部门编码
	 */
	public void setPackDept(String packDept) {
		this.packDept = packDept;
	}

	/**
	 * TODO
	 * @return the customerPickupOrgName
	 */
	public String getCustomerPickupOrgName() {
		return customerPickupOrgName;
	}

	/**
	 * @param customerPickupOrgName the customerPickupOrgName to set
	 */
	public void setCustomerPickupOrgName(String customerPickupOrgName) {
		this.customerPickupOrgName = customerPickupOrgName;
	}
	
	
}