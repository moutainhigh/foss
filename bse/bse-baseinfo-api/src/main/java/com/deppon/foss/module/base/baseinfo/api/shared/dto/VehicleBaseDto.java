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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/VehicleBaseDto.java
 * 
 * FILE NAME        	: VehicleBaseDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.util.define.FossConstants;

/**
 * 用来交互“车辆（公司、外请车）”的数据实体信息的DTO的基类
 * <p>基本属性包括：车辆车牌号、车辆额定载重、车辆净空、是否有GPS、货柜编号、直属部门编码、业务编码、品牌编码 </p>
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-19下午4:05:35</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-19 下午4:05:35
 * @since
 * @version
 */
public class VehicleBaseDto implements Serializable {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 7490411027720644569L;

    /**
     * 车辆车牌号
     */
    private String vehicleNo;

    /**
     * 车辆额定载重
     */
    private BigDecimal vehicleDeadLoad;

    /**
     * 车辆净空
     */
    private BigDecimal vehicleSelfVolume;

    /**
     * 是否有GPS
     */
    private String vehicleGps;

    /**
     * GPS设备号
     */
    private String vehicleGpsNo;
    
    /**
     * 货柜编号
     */
    private String vehicleContainerCode;
    
    /**
     * 具体车车长
     */
    private BigDecimal vehicleLength;
    
    /**
     * 具体车辆宽度
     */
    private BigDecimal vehicleWidth;

    /**
     * 具体车辆高度
     */
    private BigDecimal vehicleHeight;
    
    /**
     * 是否敞篷车
     */
    private String openVehicle;
    
    /**
     * 是否有尾板
     */
    private String tailBoard;
    
    /**
     * 车辆归属类型（公司、外请）
     */
    private String vehicleOwnershipType;

    /**
     * 车辆直属部门编码
     */
    private String vehicleOrganizationCode;
    
    /**
     * 车辆业务编码（车辆使用类型编码，如：物流班车的值代码）
     */
    private String vehicleUsedTypeCode;
    
    /**
     * 车辆品牌编码
     */
    private String vehicleBrandCode;
    
    /**
     * 车辆车型（编码）
     */
    private String vehicleLengthCode;
    /**
     * 车辆车型（名称）
     */
    private String vehicleLengthName;
    
    /**
     * 是否合同车
     */
    private String bargainVehicle; 
    
    /**
     * 车辆状态
     */
    private String status;
    
    /**
     * 单双桥
     */
    private String bridge;
    
    /**
     * 自重
     */
    private BigDecimal selfWeight;
    
    /**
     * 是否高栏车
     */
    private String railVehicle;
    
    /**
     * 停车原因代码
     */
    private String unavilableCode;
    
    /**
     * 车辆类型（挂车/厢式车/拖头）
     */
    private String vehicleType;
    
    /**
     * 百公里油耗
     */
    private BigDecimal consumeFuel;
    
    /**
     * 出厂日期
     */
    private Date beginTime;

    /**
     * 报废日期
     */
    private Date endTime;
    
    /**
     * 车辆车牌号
     * 
     * @return the vehicleNo
     */
    public String getVehicleNo() {
	return vehicleNo;
    }

    /**
     * @param vehicleNo the vehicleNo to set
     */
    public void setVehicleNo(String vehicleNo) {
	this.vehicleNo = vehicleNo;
    }

    /**
     * 车辆额定载重
     * 
     * @return the vehicleDeadLoad
     */
    public BigDecimal getVehicleDeadLoad() {
	return vehicleDeadLoad;
    }

    /**
     * @param vehicleDeadLoad the vehicleDeadLoad to set
     */
    public void setVehicleDeadLoad(BigDecimal vehicleDeadLoad) {
	this.vehicleDeadLoad = vehicleDeadLoad;
    }

    /**
     * 车辆净空
     * 
     * @return the vehicleSelfVolume
     */
    public BigDecimal getVehicleSelfVolume() {
	return vehicleSelfVolume;
    }

    /**
     * @param vehicleSelfVolume the vehicleSelfVolume to set
     */
    public void setVehicleSelfVolume(BigDecimal vehicleSelfVolume) {
	this.vehicleSelfVolume = vehicleSelfVolume;
    }

    /**
     * 是（true）否（false）有GPS;
     * 
     * @return the hasGps
     */
    public boolean isHasGps() {
	return StringUtils.equalsIgnoreCase(vehicleGps, FossConstants.ACTIVE);
    }

    /**
     * @return  the vehicleGps
     */
    public String getVehicleGps() {
        return vehicleGps;
    }
    
    /**
     * @param hasGps the hasGps to set
     */
    public void setVehicleGps(String vehicleGps) {
	this.vehicleGps = vehicleGps;
    }

    /**
     * GPS设备号
     * 
     * @return  the vehicleGpsNo
     */
    public String getVehicleGpsNo() {
        return vehicleGpsNo;
    }

    
    /**
     * @param vehicleGpsNo the vehicleGpsNo to set
     */
    public void setVehicleGpsNo(String vehicleGpsNo) {
        this.vehicleGpsNo = vehicleGpsNo;
    }

    /**
     * 货柜编号
     * 
     * @return the containerCode
     */
    public String getVehicleContainerCode() {
	return vehicleContainerCode;
    }

    /**
     * @param containerCode the containerCode to set
     */
    public void setVehicleContainerCode(String vehicleContainerCode) {
	this.vehicleContainerCode = vehicleContainerCode;
    }

    /**
     * 具体车辆车长
     * 
     * @return  the vehicleLength
     */
    public BigDecimal getVehicleLength() {
        return vehicleLength;
    }
    
    /**
     * @param vehicleLength the vehicleLength to set
     */
    public void setVehicleLength(BigDecimal vehicleLength) {
        this.vehicleLength = vehicleLength;
    }
    
    /**
     * 具体车辆宽度
     * 
     * @return  the vehicleWidth
     */
    public BigDecimal getVehicleWidth() {
        return vehicleWidth;
    }

    /**
     * @param vehicleWidth the vehicleWidth to set
     */
    public void setVehicleWidth(BigDecimal vehicleWidth) {
        this.vehicleWidth = vehicleWidth;
    }
    
    /**
     * 具体车辆高度
     * 
     * @return  the vehicleHeight
     */
    public BigDecimal getVehicleHeight() {
        return vehicleHeight;
    }
    
    /**
     * @param vehicleHeight the vehicleHeight to set
     */
    public void setVehicleHeight(BigDecimal vehicleHeight) {
        this.vehicleHeight = vehicleHeight;
    }

    /**
     * 是否敞篷车
     * 
     * @return  the openVehicle
     */
    public boolean isOpenVehicle() {
        return StringUtils.equalsIgnoreCase(openVehicle, FossConstants.ACTIVE);
    }
    
    /**
     * @param openVehicle the openVehicle to set
     */
    public void setOpenVehicle(String openVehicle) {
        this.openVehicle = openVehicle;
    }
    
    /**
     * 是否有尾板
     * 
     * @return  the tailBoard
     */
    public boolean isHasTailBoard() {
        return StringUtils.equalsIgnoreCase(tailBoard, FossConstants.ACTIVE);
    }

    /**
     * @param tailBoard the tailBoard to set
     */
    public void setTailBoard(String tailBoard) {
        this.tailBoard = tailBoard;
    }

    /**
     * 车辆归属类型（公司、外请）
     * 
     * @return the vehicleOwnershipTypeName
     */
    public String getVehicleOwnershipType() {
	if (StringUtils.isEmpty(getVehicleOrganizationCode())) {
	    vehicleOwnershipType = ComnConst.ASSETS_OWNERSHIP_TYPE_LEASED;
	}else{
	    vehicleOwnershipType = ComnConst.ASSETS_OWNERSHIP_TYPE_COMPANY;
	}
	return vehicleOwnershipType;
    }

    /**
     * 车辆直属部门编码
     * 
     * @return  the vehicleOrganizationCode
     */
    public String getVehicleOrganizationCode() {
        return vehicleOrganizationCode;
    }
    
    /**
     * @param vehicleOrganizationCode the vehicleOrganizationCode to set
     */
    public void setVehicleOrganizationCode(String vehicleOrganizationCode) {
        this.vehicleOrganizationCode = vehicleOrganizationCode;
    }
    
    /**
     * 车辆业务编码（车辆使用类型编码，如：物流班车的值代码）
     * 
     * @return  the vehicleUsedTypeCode
     */
    public String getVehicleUsedTypeCode() {
        return vehicleUsedTypeCode;
    }
    
    /**
     * @param vehicleUsedTypeCode the vehicleUsedTypeCode to set
     */
    public void setVehicleUsedTypeCode(String vehicleUsedTypeCode) {
        this.vehicleUsedTypeCode = vehicleUsedTypeCode;
    }
    
    /**
     * 车辆品牌编码
     * 
     * @return  the vehicleBrandCode
     */
    public String getVehicleBrandCode() {
        return vehicleBrandCode;
    }
    
    /**
     * @param vehicleBrandCode the vehicleBrandCode to set
     */
    public void setVehicleBrandCode(String vehicleBrandCode) {
        this.vehicleBrandCode = vehicleBrandCode;
    }
    
    /**
     * 车辆车型（编码）
     * 
     * @return  the vehicleLengthCode
     */
    public String getVehicleLengthCode() {
        return vehicleLengthCode;
    }
    
    /**
     * @param vehicleLengthCode the vehicleLengthCode to set
     */
    public void setVehicleLengthCode(String vehicleLengthCode) {
        this.vehicleLengthCode = vehicleLengthCode;
    }
    
    /**
     * 是否合同车
     * 
     * @return  the bargainVehicle
     */
    public String getBargainVehicle() {
        return bargainVehicle;
    }
    
    /**
     * @param bargainVehicle the bargainVehicle to set
     */
    public void setBargainVehicle(String bargainVehicle) {
        this.bargainVehicle = bargainVehicle;
    }

    /**
     * 车辆状态
     * 
     * @return  the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 车辆状态
     * 
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * 单双桥
     * 
     * @return  the bridge
     */
    public String getBridge() {
        return bridge;
    }

    /**
     * 单双桥
     * 
     * @param bridge the bridge to set
     */
    public void setBridge(String bridge) {
        this.bridge = bridge;
    }
    
    /**
     * 自重
     * 
     * @return  the selfWeight
     */
    public BigDecimal getSelfWeight() {
        return selfWeight;
    }
    
    /**
     * 自重
     * 
     * @param selfWeight the selfWeight to set
     */
    public void setSelfWeight(BigDecimal selfWeight) {
        this.selfWeight = selfWeight;
    }

    /**
     * 是否高栏车
     * 
     * @return  the railVehicle
     */
    public String getRailVehicle() {
        return railVehicle;
    }
    
    /**
     * 是否高栏车
     * 
     * @param railVehicle the railVehicle to set
     */
    public void setRailVehicle(String railVehicle) {
        this.railVehicle = railVehicle;
    }

    /**
     * 停车原因代码
     * 
     * @return  the unavilableCode
     */
    public String getUnavilableCode() {
        return unavilableCode;
    }
    
    /**
     * 停车原因代码
     * 
     * @param unavilableCode the unavilableCode to set
     */
    public void setUnavilableCode(String unavilableCode) {
        this.unavilableCode = unavilableCode;
    }
    
    /**
     * 车辆类型（挂车/厢式车/拖头）
     * 
     * @return  the vehicleType
     */
    public String getVehicleType() {
        return vehicleType;
    }
    
    /**
     * 车辆类型（挂车/厢式车/拖头）
     * 
     * @param vehicleType the vehicleType to set
     */
    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
    
    /**
     * 百公里油耗
     * 
     * @return  the consumeFuel
     */
    public BigDecimal getConsumeFuel() {
        return consumeFuel;
    }
    
    /**
     * 百公里油耗
     * 
     * @param consumeFuel the consumeFuel to set
     */
    public void setConsumeFuel(BigDecimal consumeFuel) {
        this.consumeFuel = consumeFuel;
    }
    
    /**
     * 出厂日期
     * 
     * @return  the beginTime
     */
    public Date getBeginTime() {
        return beginTime;
    }
    
    /**
     * 出厂日期
     * 
     * @param beginTime the beginTime to set
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }
    
    /**
     * 报废日期
     * 
     * @return  the endTime
     */
    public Date getEndTime() {
        return endTime;
    }

    public String getVehicleLengthName() {
		return vehicleLengthName;
	}

	public void setVehicleLengthName(String vehicleLengthName) {
		this.vehicleLengthName = vehicleLengthName;
	} 
	/**
     * 报废日期
     * 
     * @param endTime the endTime to set
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
