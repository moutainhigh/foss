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
 *  PROJECT NAME  : tfr-stock-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stock/api/shared/domain/StockEntity.java
 *  
 *  FILE NAME          :StockEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.api.shared.domain;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 货件库存
 * @author 097457-foss-wangqiang
 * @date 2012-10-16 下午4:56:01
 */
public class StockTrackingEntity extends BaseEntity{

	private static final long serialVersionUID = 6614592429994535486L;
	
	/**
	 * 运单号
	 */
	private String waybillNO;
	/**
	 * 交接单号
	 */
	private String handoverNo;
	/**
	 * 流水号
	 */
	private String serialNO;
	/**
	 * 流水号（复数）
	 */
	private List<String> serialNos;
	/**
	 * 操作人编号
	 */
	private String operatorCode;
	/**
	 * 操作人姓名
	 */
	private String operatorName;
	/**
	 * 部门编号
	 */
	private String orgCode;
	/**
	 * 货区编号
	 */
	private String goodsAreaCode;
	/**
	 * 设备类型
	 */
	private String deviceType;
	/**
	 * 入库时间
	 */
	private Date inStockTime;
	/**
	 * PDA扫描时间
	 */
	private Date scanTime;
	/**
	 * 预配状态
	 */
	private String preHandOverState;
	/**
	 * 下一部门
	 */
	private String nextOrgCode;
	/**
	 * 件数
	 */
	private Integer goodsCount;
	
	private String serialNoss;
	
	/**
	 * 
	 *
	 * @return 
	 */
	public String getPreHandOverState() {
		return preHandOverState;
	}

	/**
	 * 
	 *
	 * @param preHandOverState 
	 */
	public void setPreHandOverState(String preHandOverState) {
		this.preHandOverState = preHandOverState;
	}

	/**
	 * 获取 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWaybillNO() {
		return waybillNO;
	}

	/**
	 * 设置 运单号.
	 *
	 * @param waybillNO the new 运单号
	 */
	public void setWaybillNO(String waybillNO) {
		this.waybillNO = waybillNO;
	}

	/**
	 * 获取 流水号.
	 *
	 * @return the 流水号
	 */
	public String getSerialNO() {
		return serialNO;
	}

	/**
	 * 设置 流水号.
	 *
	 * @param serialNO the new 流水号
	 */
	public void setSerialNO(String serialNO) {
		this.serialNO = serialNO;
	}

	/**
	 * 获取 操作人编号.
	 *
	 * @return the 操作人编号
	 */
	public String getOperatorCode() {
		return operatorCode;
	}

	/**
	 * 设置 操作人编号.
	 *
	 * @param operatorCode the new 操作人编号
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * 获取 操作人姓名.
	 *
	 * @return the 操作人姓名
	 */
	public String getOperatorName() {
		return operatorName;
	}

	/**
	 * 设置 操作人姓名.
	 *
	 * @param operatorName the new 操作人姓名
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	/**
	 * 获取 部门编号.
	 *
	 * @return the 部门编号
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * 设置 部门编号.
	 *
	 * @param orgCode the new 部门编号
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * 获取 货区编号.
	 *
	 * @return the 货区编号
	 */
	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}

	/**
	 * 设置 货区编号.
	 *
	 * @param goodsAreaCode the new 货区编号
	 */
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}


	/**
	 * 获取 设备类型.
	 *
	 * @return the 设备类型
	 */
	public String getDeviceType() {
		return deviceType;
	}

	/**
	 * 设置 设备类型.
	 *
	 * @param deviceType the new 设备类型
	 */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * 获取 入库时间.
	 *
	 * @return the 入库时间
	 */
	public Date getInStockTime() {
		return inStockTime;
	}

	/**
	 * 设置 入库时间.
	 *
	 * @param inStockTime the new 入库时间
	 */
	public void setInStockTime(Date inStockTime) {
		this.inStockTime = inStockTime;
	}

	/**
	 * 获取 pDA扫描时间.
	 *
	 * @return the pDA扫描时间
	 */
	public Date getScanTime() {
		return scanTime;
	}

	/**
	 * 设置 pDA扫描时间.
	 *
	 * @param scanTime the new pDA扫描时间
	 */
	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}

	/**
	 * 获取 下一部门.
	 *
	 * @return the 下一部门
	 */
	public String getNextOrgCode() {
		return nextOrgCode;
	}

	/**
	 * 设置 下一部门.
	 *
	 * @param nextOrgCode the new 下一部门
	 */
	public void setNextOrgCode(String nextOrgCode) {
		this.nextOrgCode = nextOrgCode;
	}

	public Integer getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(Integer goodsCount) {
		this.goodsCount = goodsCount;
	}

	public String getHandoverNo() {
		return handoverNo;
	}

	public void setHandoverNo(String handoverNo) {
		this.handoverNo = handoverNo;
	}

	public List<String> getSerialNos() {
		return serialNos;
	}

	public void setSerialNos(List<String> serialNos) {
		this.serialNos = serialNos;
	}

	public String getSerialNoss() {
		return serialNoss;
	}

	public void setSerialNoss(String serialNoss) {
		this.serialNoss = serialNoss;
	}
	
	
}