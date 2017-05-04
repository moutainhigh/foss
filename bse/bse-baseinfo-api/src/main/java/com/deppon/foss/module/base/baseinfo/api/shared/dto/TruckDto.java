/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/TruckDto.java
 * 
 * FILE NAME        	: TruckDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo-api
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.api.shared.dto
 * FILE    NAME: TruckDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity;

/**
 * 车辆dto
 * @author 078823-foss-panGuangJun
 * @date 2012-12-28 上午10:32:05
 */
public class TruckDto extends OwnTruckEntity {

	/**
	*serialVersionUID
	*/
	private static final long serialVersionUID = -5693041697099655794L;

	//车辆类型 -- 公司车、外请车
	private String truckType;
	
	/**
	 * 车牌号
	 */
	private String vehicleNo;

	/**
	 * 所属部门
	 */
	private String orgId;

	/**
	 * 所属部门名称“扩展字段”
	 */
	private String orgName;
	
	/**
	 * 公共选择器查询“扩展字段”
	 */
	private String queryParam;
	
	/**
	 * 公共选择器查询“扩展字段” 用于精确查询车牌号
	 */
	private String vehicleNoNoLike;
	
	/**
	 * 所属部门
	 */
	private List<String> orgIds;

	/**
	 * 车辆状态
	 */
	private String status;

	/**
	 * 自重
	 */
	private BigDecimal selfWeight;

	/**
	 * 品牌
	 */
	private String brand;

	/**
	 * 车辆使用类型
	 */
	private String usedType;

	/**
	 * 车辆长
	 */
	private BigDecimal vehicleLength;

	/**
	 * 车辆宽
	 */
	private BigDecimal vehicleWidth;

	/**
	 * 车辆高
	 */
	private BigDecimal vehicleHeight;

	/**
	 * 净空
	 */
	private BigDecimal selfVolume;

	/**
	 * 是否有尾板
	 */
	private String tailBoard;

	/**
	 * 额定载重
	 */
	private BigDecimal deadLoad;

	/**
	 * 百公里油耗
	 */
	private BigDecimal consumeFuel;

	/**
	 * 是否有GPS
	 */
	private String gps;

	/**
	 * GPS设备号
	 */
	private String gpsNo;

	/**
	 * 车货总重
	 */
	private BigDecimal vehicleDeadLoad;

	/**
	 * 油箱容积
	 */
	private BigDecimal tankVolume;

	/**
	 * 单双桥
	 */
	private String bridge;

	/**
	 * 是否启用
	 */
	private String active;

	/**
	 * 车辆类型
	 */
	private String vehicleType;
	
	/**
	 * 车辆类型集合  扩展字段
	 */
	private List<String> vehicleTypes;
		
	/**
	 * 货柜编号
	 */
	private String containerCode;

	/**
	 * LMS同步过来的车辆唯一标识
	 */
	private String vehicleLmsCode;

	/**
	 * 车辆车型编码
	 */
	private String vehcleLengthCode;

	/**
	 * 执行LMS的版本号
	 */
	private String versionNo;

	/**
	 * 关联停车原因代码
	 */
	private String unavilableCode;

	/**
	 * 停车计划开始时间
	 */
	private Date beginDate;

	/**
	 * 停车计划结束时间
	 */
	private Date endDate;

	/**
	 * 挂车类型编码
	 */
	private String trailerType;

	/**
	 * 是否气囊柜
	 */
	private String isBallon;
	
	 /**
	  * 组织编码
	  */
	private String parentOrgCode;
	/**
	 * 查询该组织及以下组织列表
	 */
	private List<String> loopOrgCode;
	/**
	 * 查询该组织下面顶级车队
	 */
	private String topFleetOrgCode;
	/**
	 * 查询快递车
	 */
	private String expressTruck;
	/**
	 * 区域名称
	 */
	private String reginName;
	/**
	 * 是否挂车（如果是公司车的挂车这个字段显示“挂牌号”供中转使用）
	 */
	private String isTrailer;
	/**
	 * 是否指限制公司车（用来判断查询条件是否只限制公司车查询）
	 */
	private String isOwnTruck;

	/**
	 *getter
	 */
	public String getTruckType() {
		return truckType;
	}

	/**
	 *setter
	 */
	public void setTruckType(String truckType) {
		this.truckType = truckType;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getQueryParam() {
		return queryParam;
	}

	public void setQueryParam(String queryParam) {
		this.queryParam = queryParam;
	}

	public String getVehicleNoNoLike() {
		return vehicleNoNoLike;
	}

	public void setVehicleNoNoLike(String vehicleNoNoLike) {
		this.vehicleNoNoLike = vehicleNoNoLike;
	}

	public List<String> getOrgIds() {
		return orgIds;
	}

	public void setOrgIds(List<String> orgIds) {
		this.orgIds = orgIds;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getSelfWeight() {
		return selfWeight;
	}

	public void setSelfWeight(BigDecimal selfWeight) {
		this.selfWeight = selfWeight;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getUsedType() {
		return usedType;
	}

	public void setUsedType(String usedType) {
		this.usedType = usedType;
	}

	public BigDecimal getVehicleLength() {
		return vehicleLength;
	}

	public void setVehicleLength(BigDecimal vehicleLength) {
		this.vehicleLength = vehicleLength;
	}

	public BigDecimal getVehicleWidth() {
		return vehicleWidth;
	}

	public void setVehicleWidth(BigDecimal vehicleWidth) {
		this.vehicleWidth = vehicleWidth;
	}

	public BigDecimal getVehicleHeight() {
		return vehicleHeight;
	}

	public void setVehicleHeight(BigDecimal vehicleHeight) {
		this.vehicleHeight = vehicleHeight;
	}

	public BigDecimal getSelfVolume() {
		return selfVolume;
	}

	public void setSelfVolume(BigDecimal selfVolume) {
		this.selfVolume = selfVolume;
	}

	public String getTailBoard() {
		return tailBoard;
	}

	public void setTailBoard(String tailBoard) {
		this.tailBoard = tailBoard;
	}

	public BigDecimal getDeadLoad() {
		return deadLoad;
	}

	public void setDeadLoad(BigDecimal deadLoad) {
		this.deadLoad = deadLoad;
	}

	public BigDecimal getConsumeFuel() {
		return consumeFuel;
	}

	public void setConsumeFuel(BigDecimal consumeFuel) {
		this.consumeFuel = consumeFuel;
	}

	public String getGps() {
		return gps;
	}

	public void setGps(String gps) {
		this.gps = gps;
	}

	public String getGpsNo() {
		return gpsNo;
	}

	public void setGpsNo(String gpsNo) {
		this.gpsNo = gpsNo;
	}

	public BigDecimal getVehicleDeadLoad() {
		return vehicleDeadLoad;
	}

	public void setVehicleDeadLoad(BigDecimal vehicleDeadLoad) {
		this.vehicleDeadLoad = vehicleDeadLoad;
	}

	public BigDecimal getTankVolume() {
		return tankVolume;
	}

	public void setTankVolume(BigDecimal tankVolume) {
		this.tankVolume = tankVolume;
	}

	public String getBridge() {
		return bridge;
	}

	public void setBridge(String bridge) {
		this.bridge = bridge;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public List<String> getVehicleTypes() {
		return vehicleTypes;
	}

	public void setVehicleTypes(List<String> vehicleTypes) {
		this.vehicleTypes = vehicleTypes;
	}

	public String getContainerCode() {
		return containerCode;
	}

	public void setContainerCode(String containerCode) {
		this.containerCode = containerCode;
	}

	public String getVehicleLmsCode() {
		return vehicleLmsCode;
	}

	public void setVehicleLmsCode(String vehicleLmsCode) {
		this.vehicleLmsCode = vehicleLmsCode;
	}

	public String getVehcleLengthCode() {
		return vehcleLengthCode;
	}

	public void setVehcleLengthCode(String vehcleLengthCode) {
		this.vehcleLengthCode = vehcleLengthCode;
	}

	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	public String getUnavilableCode() {
		return unavilableCode;
	}

	public void setUnavilableCode(String unavilableCode) {
		this.unavilableCode = unavilableCode;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getTrailerType() {
		return trailerType;
	}

	public void setTrailerType(String trailerType) {
		this.trailerType = trailerType;
	}

	public String getIsBallon() {
		return isBallon;
	}

	public void setIsBallon(String isBallon) {
		this.isBallon = isBallon;
	}

	public String getParentOrgCode() {
		return parentOrgCode;
	}

	public void setParentOrgCode(String parentOrgCode) {
		this.parentOrgCode = parentOrgCode;
	}

	public List<String> getLoopOrgCode() {
		return loopOrgCode;
	}

	public void setLoopOrgCode(List<String> loopOrgCode) {
		this.loopOrgCode = loopOrgCode;
	}

	public String getTopFleetOrgCode() {
		return topFleetOrgCode;
	}

	public void setTopFleetOrgCode(String topFleetOrgCode) {
		this.topFleetOrgCode = topFleetOrgCode;
	}

	public String getExpressTruck() {
		return expressTruck;
	}

	public void setExpressTruck(String expressTruck) {
		this.expressTruck = expressTruck;
	}

	public String getReginName() {
		return reginName;
	}

	public void setReginName(String reginName) {
		this.reginName = reginName;
	}

	public String getIsTrailer() {
		return isTrailer;
	}

	public void setIsTrailer(String isTrailer) {
		this.isTrailer = isTrailer;
	}

	public String getIsOwnTruck() {
		return isOwnTruck;
	}

	public void setIsOwnTruck(String isOwnTruck) {
		this.isOwnTruck = isOwnTruck;
	}
}
