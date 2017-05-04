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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/OwnTruckEntity.java
 * 
 * FILE NAME        	: OwnTruckEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * 用来存储交互“公司车辆”的数据库对应实体：SUC-753
 * 
 * @author 100847-foss-GaoPeng
 * @date 2012-10-11 下午4:56:39
 * @since
 * @version
 */
public class OwnTruckEntity extends BaseEntity {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -5866294865425489602L;

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
	
	public String getIsOwnTruck() {
		return isOwnTruck;
	}

	public void setIsOwnTruck(String isOwnTruck) {
		this.isOwnTruck = isOwnTruck;
	}

	public String getIsTrailer() {
		return isTrailer;
	}

	public void setIsTrailer(String isTrailer) {
		this.isTrailer = isTrailer;
	}

	public String getReginName() {
		return reginName;
	}

	public void setReginName(String reginName) {
		this.reginName = reginName;
	}

	public String getExpressTruck() {
		return expressTruck;
	}

	public void setExpressTruck(String expressTruck) {
		this.expressTruck = expressTruck;
	}

	/**
	 * 
	* @Title: getLoopOrgCode 
	* @Description:  获取  查询该组织及以下组织列表 
	* @param @return    设定文件 
	* @return List<String>    返回类型 
	* @author 189284 ZhangXu
	* @date 2015-5-4 下午2:47:20 
	* @throws
	 */
	public List<String> getLoopOrgCode() {
		return loopOrgCode;
	}

	/**
	 * 
	* @Title: setLoopOrgCode 
	* @Description: 设置   查询该组织及以下组织列表 
	* @param @param loopOrgCode    设定文件 
	* @return void    返回类型 
	* @author 189284 ZhangXu
	* @date 2015-5-4 下午2:47:43 
	* @throws
	 */
	public void setLoopOrgCode(List<String> loopOrgCode) {
		this.loopOrgCode = loopOrgCode;
	}

	/**
	 * @return the vehicleNo
	 */
	public String getTopFleetOrgCode() {
		return topFleetOrgCode;
	}

	public void setTopFleetOrgCode(String topFleetOrgCode) {
		this.topFleetOrgCode = topFleetOrgCode;
	}

	public String getParentOrgCode() {
		return parentOrgCode;
	}


	public void setParentOrgCode(String parentOrgCode) {
		this.parentOrgCode = parentOrgCode;
	}


	
	public String getVehicleNo() {
		return vehicleNo;
	}


	/**
	 * @param vehicleNo
	 *            the vehicleNo to set
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * @return the orgId
	 */
	public String getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId
	 *            the orgId to set
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the selfWeight
	 */
	public BigDecimal getSelfWeight() {
		return selfWeight;
	}

	/**
	 * @param selfWeight
	 *            the selfWeight to set
	 */
	public void setSelfWeight(BigDecimal selfWeight) {
		this.selfWeight = selfWeight;
	}

	/**
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * @param brand
	 *            the brand to set
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * @return the usedType
	 */
	public String getUsedType() {
		return usedType;
	}

	/**
	 * @param usedType
	 *            the usedType to set
	 */
	public void setUsedType(String usedType) {
		this.usedType = usedType;
	}

	/**
	 * @return the vehicleLength
	 */
	public BigDecimal getVehicleLength() {
		return vehicleLength;
	}

	/**
	 * @param vehicleLength
	 *            the vehicleLength to set
	 */
	public void setVehicleLength(BigDecimal vehicleLength) {
		this.vehicleLength = vehicleLength;
	}

	/**
	 * @return the vehicleWidth
	 */
	public BigDecimal getVehicleWidth() {
		return vehicleWidth;
	}

	/**
	 * @param vehicleWidth
	 *            the vehicleWidth to set
	 */
	public void setVehicleWidth(BigDecimal vehicleWidth) {
		this.vehicleWidth = vehicleWidth;
	}

	/**
	 * @return the vehicleHeight
	 */
	public BigDecimal getVehicleHeight() {
		return vehicleHeight;
	}

	/**
	 * @param vehicleHeight
	 *            the vehicleHeight to set
	 */
	public void setVehicleHeight(BigDecimal vehicleHeight) {
		this.vehicleHeight = vehicleHeight;
	}

	/**
	 * @return the selfVolume
	 */
	public BigDecimal getSelfVolume() {
		return selfVolume;
	}

	/**
	 * @param selfVolume
	 *            the selfVolume to set
	 */
	public void setSelfVolume(BigDecimal selfVolume) {
		this.selfVolume = selfVolume;
	}

	/**
	 * @return the tailBoard
	 */
	public String getTailBoard() {
		return tailBoard;
	}

	/**
	 * @param tailBoard
	 *            the tailBoard to set
	 */
	public void setTailBoard(String tailBoard) {
		this.tailBoard = tailBoard;
	}

	/**
	 * @return the deadLoad
	 */
	public BigDecimal getDeadLoad() {
		return deadLoad;
	}

	/**
	 * @param deadLoad
	 *            the deadLoad to set
	 */
	public void setDeadLoad(BigDecimal deadLoad) {
		this.deadLoad = deadLoad;
	}

	/**
	 * @return the consumeFuel
	 */
	public BigDecimal getConsumeFuel() {
		return consumeFuel;
	}

	/**
	 * @param consumeFuel
	 *            the consumeFuel to set
	 */
	public void setConsumeFuel(BigDecimal consumeFuel) {
		this.consumeFuel = consumeFuel;
	}

	/**
	 * @return the gps
	 */
	public String getGps() {
		return gps;
	}

	/**
	 * @param gps
	 *            the gps to set
	 */
	public void setGps(String gps) {
		this.gps = gps;
	}

	/**
	 * @return the gpsNo
	 */
	public String getGpsNo() {
		return gpsNo;
	}

	/**
	 * @return the trailerType
	 */
	public String getTrailerType() {
		return trailerType;
	}

	/**
	 * @param trailerType
	 *            the trailerType to set
	 */
	public void setTrailerType(String trailerType) {
		this.trailerType = trailerType;
	}

	/**
	 * @return the isBallon
	 */
	public String getIsBallon() {
		return isBallon;
	}

	/**
	 * @param isBallon
	 *            the isBallon to set
	 */
	public void setIsBallon(String isBallon) {
		this.isBallon = isBallon;
	}

	/**
	 * @return the versionNo
	 */
	public String getVersionNo() {
		return versionNo;
	}

	/**
	 * @param versionNo
	 *            the versionNo to set
	 */
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	/**
	 * @param gpsNo
	 *            the gpsNo to set
	 */
	public void setGpsNo(String gpsNo) {
		this.gpsNo = gpsNo;
	}

	/**
	 * @return the vehicleDeadLoad
	 */
	public BigDecimal getVehicleDeadLoad() {
		return vehicleDeadLoad;
	}

	/**
	 * @param vehicleDeadLoad
	 *            the vehicleDeadLoad to set
	 */
	public void setVehicleDeadLoad(BigDecimal vehicleDeadLoad) {
		this.vehicleDeadLoad = vehicleDeadLoad;
	}

	/**
	 * @return the tankVolume
	 */
	public BigDecimal getTankVolume() {
		return tankVolume;
	}

	/**
	 * @param tankVolume
	 *            the tankVolume to set
	 */
	public void setTankVolume(BigDecimal tankVolume) {
		this.tankVolume = tankVolume;
	}

	/**
	 * @return the bridge
	 */
	public String getBridge() {
		return bridge;
	}

	/**
	 * @param bridge
	 *            the bridge to set
	 */
	public void setBridge(String bridge) {
		this.bridge = bridge;
	}

	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active
	 *            the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return the vehicleType
	 */
	public String getVehicleType() {
		return vehicleType;
	}

	/**
	 * @param vehicleType
	 *            the vehicleType to set
	 */
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	/**
	 * @return the containerCode
	 */
	public String getContainerCode() {
		return containerCode;
	}

	/**
	 * @param containerCode
	 *            the containerCode to set
	 */
	public void setContainerCode(String containerCode) {
		this.containerCode = containerCode;
	}

	/**
	 * @return the vehicleLmsCode
	 */
	public String getVehicleLmsCode() {
		return vehicleLmsCode;
	}

	/**
	 * @param vehicleLmsCode
	 *            the vehicleLmsCode to set
	 */
	public void setVehicleLmsCode(String vehicleLmsCode) {
		this.vehicleLmsCode = vehicleLmsCode;
	}

	/**
	 * @return the vehcleLengthCode
	 */
	public String getVehcleLengthCode() {
		return vehcleLengthCode;
	}

	/**
	 * @param vehcleLengthCode
	 *            the vehcleLengthCode to set
	 */
	public void setVehcleLengthCode(String vehcleLengthCode) {
		this.vehcleLengthCode = vehcleLengthCode;
	}

	/**
	 * @return the unavilableCode
	 */
	public String getUnavilableCode() {
		return unavilableCode;
	}

	/**
	 * @param unavilableCode
	 *            the unavilableCode to set
	 */
	public void setUnavilableCode(String unavilableCode) {
		this.unavilableCode = unavilableCode;
	}

	/**
	 * @return the beginDate
	 */
	public Date getBeginDate() {
		return beginDate;
	}

	/**
	 * @param beginDate
	 *            the beginDate to set
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	public List<String> getOrgIds() {
		return orgIds;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public void setOrgIds(List<String> orgIds) {
		this.orgIds = orgIds;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public String getQueryParam() {
		return queryParam;
	}

	
	public void setQueryParam(String queryParam) {
		this.queryParam = queryParam;
	}

	public List<String> getVehicleTypes() {
		return vehicleTypes;
	}

	
	public String getVehicleNoNoLike() {
		return vehicleNoNoLike;
	}

	public void setVehicleNoNoLike(String vehicleNoNoLike) {
		this.vehicleNoNoLike = vehicleNoNoLike;
	}

	public void setVehicleTypes(List<String> vehicleTypes) {
		this.vehicleTypes = vehicleTypes;
	}
}