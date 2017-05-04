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
 *  PROJECT NAME  : tfr-management-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/shared/dto/SendSignBillDto.java
 *  
 *  FILE NAME          :SendSignBillDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.management.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


/**
 * 派送签单entity
 * 
 * @author 099197-foss-liming
 * @date 2012-11-29 上午9:49:47
 */
public class SendSignBillDto  implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6170460708425583130L;
	
	/**id*/
	private String id;

	/**	  
	 * 签单编号
	 */
	private String signBillNo;

	/**
	 * 交接单号
	 */
	private String handoverNo; 

	/**
	 *  用车部门
	 */
	private String useTruckOrgCode;

	/**
	 * 用车部门名称
	 */
	private String useTruckOrgName; 

	/**
	 * 司机
	 */
	private String driverCode;

	/**
	 * 司机姓名	
	 */
	private String driverName; 

	/**
	 * 车牌号
	 */
	private String vehicleNo;

	/**
	 * 车型
	 */
	private String vehicleTypeLength;

	/**
	 *  转货里程
	 */
	private BigDecimal transferDistance;

	/**
	 * 派送票数
	 */
	private Integer sendBillQty;

	/**
	 *  体积
	 */
	private BigDecimal volume;

	/**
	 * 重量
	 */
	private BigDecimal weight;

	/**
	 * 进仓票数
	 */
	private Integer inStockBillQty;

	/**
	 * 上楼票数
	 */
	private Integer upstairsBillQty;

	/**
	 * 里程
	 */
	private BigDecimal distance;

	/**
	 * 单独配送
	 */
	private String isSingleSend; 
	
	

	/**
	 *  开始 日期
	 */
	private String beginSignBillDate;

	/**
	 * 结束日期
	 */
	private String endSignBillDate; 
	
	
	/**
	 * 司机个数
	 */
	private Integer  driverCount;
	
   /**
    * 总派送票数
    */
   private BigDecimal sendBillQtyTotal;

    /**
     * 总体积
     */
    private BigDecimal volumeTotal;

    /**
     * 总重量
     */
    private BigDecimal weightTotal;   
    
	/**
	 *  总进仓票数
	 */
	private Integer inStockBillQtyTotal;
	
	/**
	 * 总上楼费
	 */
	private Long upstairsFeeTotal; 
	
	/**
	 * 总里程
	 */
	private BigDecimal distanceTotal;
	
	/**
	 * 部门
	 */
	private List<String> orgCodes;
	
	
	/**
	 * 获取 id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置 id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取 签单编号.
	 *
	 * @return the 签单编号
	 */
	public String getSignBillNo() {
		return signBillNo;
	}

	/**
	 * 设置 签单编号.
	 *
	 * @param signBillNo the new 签单编号
	 */
	public void setSignBillNo(String signBillNo) {
		this.signBillNo = signBillNo;
	}

	/**
	 * 获取 交接单号.
	 *
	 * @return the 交接单号
	 */
	public String getHandoverNo() {
		return handoverNo;
	}

	/**
	 * 设置 交接单号.
	 *
	 * @param handoverNo the new 交接单号
	 */
	public void setHandoverNo(String handoverNo) {
		this.handoverNo = handoverNo;
	}

	/**
	 * 获取 用车部门.
	 *
	 * @return the 用车部门
	 */
	public String getUseTruckOrgCode() {
		return useTruckOrgCode;
	}

	/**
	 * 设置 用车部门.
	 *
	 * @param useTruckOrgCode the new 用车部门
	 */
	public void setUseTruckOrgCode(String useTruckOrgCode) {
		this.useTruckOrgCode = useTruckOrgCode;
	}

	/**
	 * 获取 用车部门名称.
	 *
	 * @return the 用车部门名称
	 */
	public String getUseTruckOrgName() {
		return useTruckOrgName;
	}

	/**
	 * 设置 用车部门名称.
	 *
	 * @param useTruckOrgName the new 用车部门名称
	 */
	public void setUseTruckOrgName(String useTruckOrgName) {
		this.useTruckOrgName = useTruckOrgName;
	}

	/**
	 * 获取 司机.
	 *
	 * @return the 司机
	 */
	public String getDriverCode() {
		return driverCode;
	}

	/**
	 * 设置 司机.
	 *
	 * @param driverCode the new 司机
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	/**
	 * 获取 司机姓名.
	 *
	 * @return the 司机姓名
	 */
	public String getDriverName() {
		return driverName;
	}

	/**
	 * 设置 司机姓名.
	 *
	 * @param driverName the new 司机姓名
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	/**
	 * 获取 车牌号.
	 *
	 * @return the 车牌号
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * 设置 车牌号.
	 *
	 * @param vehicleNo the new 车牌号
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * 获取 车型.
	 *
	 * @return the 车型
	 */
	public String getVehicleTypeLength() {
		return vehicleTypeLength;
	}

	/**
	 * 设置 车型.
	 *
	 * @param vehicleTypeLength the new 车型
	 */
	public void setVehicleTypeLength(String vehicleTypeLength) {
		this.vehicleTypeLength = vehicleTypeLength;
	}

	/**
	 * 获取 转货里程.
	 *
	 * @return the 转货里程
	 */
	public BigDecimal getTransferDistance() {
		return transferDistance;
	}

	/**
	 * 设置 转货里程.
	 *
	 * @param transferDistance the new 转货里程
	 */
	public void setTransferDistance(BigDecimal transferDistance) {
		this.transferDistance = transferDistance;
	}

	/**
	 * 获取 派送票数.
	 *
	 * @return the 派送票数
	 */
	public Integer getSendBillQty() {
		return sendBillQty;
	}

	/**
	 * 设置 派送票数.
	 *
	 * @param sendBillQty the new 派送票数
	 */
	public void setSendBillQty(Integer sendBillQty) {
		this.sendBillQty = sendBillQty;
	}

	/**
	 * 获取 体积.
	 *
	 * @return the 体积
	 */
	public BigDecimal getVolume() {
		return volume;
	}

	/**
	 * 设置 体积.
	 *
	 * @param volume the new 体积
	 */
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	/**
	 * 获取 重量.
	 *
	 * @return the 重量
	 */
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * 设置 重量.
	 *
	 * @param weight the new 重量
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
	 * 获取 进仓票数.
	 *
	 * @return the 进仓票数
	 */
	public Integer getInStockBillQty() {
		return inStockBillQty;
	}

	/**
	 * 设置 进仓票数.
	 *
	 * @param inStockBillQty the new 进仓票数
	 */
	public void setInStockBillQty(Integer inStockBillQty) {
		this.inStockBillQty = inStockBillQty;
	}

	/**
	 * 获取 上楼票数.
	 *
	 * @return the 上楼票数
	 */
	public Integer getUpstairsBillQty() {
		return upstairsBillQty;
	}

	/**
	 * 设置 上楼票数.
	 *
	 * @param upstairsBillQty the new 上楼票数
	 */
	public void setUpstairsBillQty(Integer upstairsBillQty) {
		this.upstairsBillQty = upstairsBillQty;
	}

	/**
	 * 获取 里程.
	 *
	 * @return the 里程
	 */
	public BigDecimal getDistance() {
		return distance;
	}

	/**
	 * 设置 里程.
	 *
	 * @param distance the new 里程
	 */
	public void setDistance(BigDecimal distance) {
		this.distance = distance;
	}

	/**
	 * 获取 单独配送.
	 *
	 * @return the 单独配送
	 */
	public String getIsSingleSend() {
		return isSingleSend;
	}

	/**
	 * 设置 单独配送.
	 *
	 * @param isSingleSend the new 单独配送
	 */
	public void setIsSingleSend(String isSingleSend) {
		this.isSingleSend = isSingleSend;
	}

	/**
	 * 获取 开始 日期.
	 *
	 * @return the 开始 日期
	 */
	public String getBeginSignBillDate() {
		return beginSignBillDate;
	}

	/**
	 * 设置 开始 日期.
	 *
	 * @param beginSignBillDate the new 开始 日期
	 */
	public void setBeginSignBillDate(String beginSignBillDate) {
		this.beginSignBillDate = beginSignBillDate;
	}

	/**
	 * 获取 结束日期.
	 *
	 * @return the 结束日期
	 */
	public String getEndSignBillDate() {
		return endSignBillDate;
	}

	/**
	 * 设置 结束日期.
	 *
	 * @param endSignBillDate the new 结束日期
	 */
	public void setEndSignBillDate(String endSignBillDate) {
		this.endSignBillDate = endSignBillDate;
	}

	/**
	 * 获取 司机个数.
	 *
	 * @return the 司机个数
	 */
	public Integer getDriverCount() {
		return driverCount;
	}

	/**
	 * 设置 司机个数.
	 *
	 * @param driverCount the new 司机个数
	 */
	public void setDriverCount(Integer driverCount) {
		this.driverCount = driverCount;
	}

	/**
	 * 获取 总派送票数.
	 *
	 * @return the 总派送票数
	 */
	public BigDecimal getSendBillQtyTotal() {
		return sendBillQtyTotal;
	}

	/**
	 * 设置 总派送票数.
	 *
	 * @param sendBillQtyTotal the new 总派送票数
	 */
	public void setSendBillQtyTotal(BigDecimal sendBillQtyTotal) {
		this.sendBillQtyTotal = sendBillQtyTotal;
	}

	/**
	 * 获取 总体积.
	 *
	 * @return the 总体积
	 */
	public BigDecimal getVolumeTotal() {
		return volumeTotal;
	}

	/**
	 * 设置 总体积.
	 *
	 * @param volumeTotal the new 总体积
	 */
	public void setVolumeTotal(BigDecimal volumeTotal) {
		this.volumeTotal = volumeTotal;
	}

	/**
	 * 获取 总重量.
	 *
	 * @return the 总重量
	 */
	public BigDecimal getWeightTotal() {
		return weightTotal;
	}

	/**
	 * 设置 总重量.
	 *
	 * @param weightTotal the new 总重量
	 */
	public void setWeightTotal(BigDecimal weightTotal) {
		this.weightTotal = weightTotal;
	}

	/**
	 * 获取 总进仓票数.
	 *
	 * @return the 总进仓票数
	 */
	public Integer getInStockBillQtyTotal() {
		return inStockBillQtyTotal;
	}

	/**
	 * 设置 总进仓票数.
	 *
	 * @param inStockBillQtyTotal the new 总进仓票数
	 */
	public void setInStockBillQtyTotal(Integer inStockBillQtyTotal) {
		this.inStockBillQtyTotal = inStockBillQtyTotal;
	}

	/**
	 * 获取 总上楼费.
	 *
	 * @return the 总上楼费
	 */
	public Long getUpstairsFeeTotal() {
		return upstairsFeeTotal;
	}

	/**
	 * 设置 总上楼费.
	 *
	 * @param upstairsFeeTotal the new 总上楼费
	 */
	public void setUpstairsFeeTotal(Long upstairsFeeTotal) {
		this.upstairsFeeTotal = upstairsFeeTotal;
	}

	/**
	 * 获取 总里程.
	 *
	 * @return the 总里程
	 */
	public BigDecimal getDistanceTotal() {
		return distanceTotal;
	}

	/**
	 * 设置 总里程.
	 *
	 * @param distanceTotal the new 总里程
	 */
	public void setDistanceTotal(BigDecimal distanceTotal) {
		this.distanceTotal = distanceTotal;
	}

	/**   
	 * orgCodes   
	 *   
	 * @return  the orgCodes   
	 */
	
	public List<String> getOrgCodes() {
		return orgCodes;
	}

	/**   
	 * @param orgCodes the orgCodes to set
	 * Date:2013-5-7下午3:04:05
	 */
	public void setOrgCodes(List<String> orgCodes) {
		this.orgCodes = orgCodes;
	}
}