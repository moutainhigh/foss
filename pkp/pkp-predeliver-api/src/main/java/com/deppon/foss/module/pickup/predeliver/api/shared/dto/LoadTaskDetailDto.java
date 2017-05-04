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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/LoadTaskDetailDto.java
 * 
 * FILE NAME        	: LoadTaskDetailDto.java
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

/**
 * 派送装车任务明细DTO，属性从com.deppon.foss.module.transfer.load.api.shared.dto.
 * LoadWayBillDetailDto中拷贝
 * @author ibm-wangxiexu
 * @date 2012-11-30 下午4:50:10
 */
public class LoadTaskDetailDto implements Serializable {
	private static final long serialVersionUID = 2062349342561032618L;

	private String id;
	private String origOrgCode; // 出发部门编号
	private String reachOrgName; // 到达部门
	private String waybillNo; // 运单号
	private String transportType; // 运输性质
	private Integer stockQty; // 库存件数
	private Integer scanQty; // 已扫描件数
	private Integer loadQty; // 已装车件数
	private BigDecimal stockWeight; // 库存重量
	private BigDecimal stockVolume; // 库存体积
	private String goodsName; // 货名
	private String pack; // 包装

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to see
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the origOrgCode
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}

	/**
	 * @param origOrgCode the origOrgCode to see
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	/**
	 * @return the reachOrgName
	 */
	public String getReachOrgName() {
		return reachOrgName;
	}

	/**
	 * @param reachOrgName the reachOrgName to see
	 */
	public void setReachOrgName(String reachOrgName) {
		this.reachOrgName = reachOrgName;
	}

	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo the waybillNo to see
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return the transportType
	 */
	public String getTransportType() {
		return transportType;
	}

	/**
	 * @param transportType the transportType to see
	 */
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	/**
	 * @return the stockQty
	 */
	public Integer getStockQty() {
		return stockQty;
	}

	/**
	 * @param stockQty the stockQty to see
	 */
	public void setStockQty(Integer stockQty) {
		this.stockQty = stockQty;
	}

	/**
	 * @return the scanQty
	 */
	public Integer getScanQty() {
		return scanQty;
	}

	/**
	 * @param scanQty the scanQty to see
	 */
	public void setScanQty(Integer scanQty) {
		this.scanQty = scanQty;
	}

	/**
	 * @return the loadQty
	 */
	public Integer getLoadQty() {
		return loadQty;
	}

	/**
	 * @param loadQty the loadQty to see
	 */
	public void setLoadQty(Integer loadQty) {
		this.loadQty = loadQty;
	}

	/**
	 * @return the stockWeight
	 */
	public BigDecimal getStockWeight() {
		return stockWeight;
	}

	/**
	 * @param stockWeight the stockWeight to see
	 */
	public void setStockWeight(BigDecimal stockWeight) {
		this.stockWeight = stockWeight;
	}

	/**
	 * @return the stockVolume
	 */
	public BigDecimal getStockVolume() {
		return stockVolume;
	}

	/**
	 * @param stockVolume the stockVolume to see
	 */
	public void setStockVolume(BigDecimal stockVolume) {
		this.stockVolume = stockVolume;
	}

	/**
	 * @return the goodsName
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * @param goodsName the goodsName to see
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * @return the pack
	 */
	public String getPack() {
		return pack;
	}

	/**
	 * @param pack the pack to see
	 */
	public void setPack(String pack) {
		this.pack = pack;
	}

}