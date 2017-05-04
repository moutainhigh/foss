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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/shared/domain/FuelConsumptionEntity.java
 *  
 *  FILE NAME          :FuelConsumptionEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.management.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;

/**
 * 油耗信息实体
 * @author foss-liuxue(for IBM)
 * @date 2012-12-26 下午3:39:28
 */
public class FuelConsumptionEntity implements Serializable {
	
	private static final long serialVersionUID = 1005194688570451022L;

	/**
	 * id
	 */
	private String id;

    /**
     * 车牌号
     */
    private String vehicleNo;  

    /**
     * 货柜号
     */
    private String containerNo;  

    /**
     * 车型
     */
    private String vehicleTypeLength;  

    /**
     * 车辆品牌
     */
    private String vehicleBrand;  

    /**
     * 事业部
     */
    private String divisionOrgName;  

    /**
     * 车队
     */
    private String transDepartmentName;  

    /**
     * 小组
     */
    private String groupOrgName;  

    /**
     * 所属大区
     */
    private String largeDistrictName;  

    /**
     * 油耗标准
     */
    private BigDecimal standardConsumption;  

    /**
     * 线路
     */
    private String lineCode;  

    /**
     * 线路名称
     */
    private String lineName;  

    /**
     * 出发站
     */
    private String deptRegionCode;  

    /**
     * 出发站名称
     */
    private String deptRegionName;  

    /**
     * 目的站
     */
    private String arrvRegionCode;  

    /**
     * 目的站名称
     */
    private String arrvRegionName;  

    /**
     * 发车类型
     */
    private String departureTypeCode;  

    /**
     * 出发公里数
     */
    private BigDecimal startKm;  

    /**
     * 到达公里数
     */
    private BigDecimal arriveKm;  

    /**
     * 载重
     */
    private BigDecimal ratedLoad;  

    /**
     * 行驶公里数
     */
    private BigDecimal runKm;  

    /**
     * 司机1
     */
    private String driver1Code;  

    /**
     * 司机2
     */
    private String driver2Code;  

    /**
     * 司机1姓名
     */
    private String driver1Name;  

    /**
     * 司机2姓名
     */
    private String driver2Name;  

    /**
     * 体积
     */
    private BigDecimal volume;  

    /**
     * 发车模式
     */
    private String departureMode;  

    /**
     * 加油升数合计
     */
    private BigDecimal fuelAmount;  

    /**
     * 平均单价
     */
    private BigDecimal averagePrice;  

    /**
     * 油费合计
     */
    private BigDecimal fuelFeeAmount;  

    /**
     * 百公里油耗
     */
    private BigDecimal ohKmFuelConsumption;  

    /**
     * 吨百公里油耗
     */
    private BigDecimal tohKmFuelConsumption;  

    /**
     * 备注
     */
    private String notes;  
    
    /**
     * 币种
     */
    private String currencyCode; 
    
    /**
     * 创建时间
     */
    private Date createDate;
    
    /**
     * 加油日期
     */
    private Date fuelDate;
    /**
     * 路桥费
     */
    private BigDecimal roadToll;
    
    

    /**
     * 获取 创建时间.
     *
     * @return the 创建时间
     */
    public Date getCreateDate() {
		return createDate;
	}
    
	/**
	 * 设置 创建时间.
	 *
	 * @param createDate the new 创建时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

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
     * 获取 货柜号.
     *
     * @return the 货柜号
     */
    public String getContainerNo() {
        return containerNo;
    }

    /**
     * 设置 货柜号.
     *
     * @param containerNo the new 货柜号
     */
    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo;
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
     * 获取 车辆品牌.
     *
     * @return the 车辆品牌
     */
    public String getVehicleBrand() {
        return vehicleBrand;
    }

    /**
     * 设置 车辆品牌.
     *
     * @param vehicleBrand the new 车辆品牌
     */
    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }

    /**
     * 获取 事业部.
     *
     * @return the 事业部
     */
    public String getDivisionOrgName() {
        return divisionOrgName;
    }

    /**
     * 设置 事业部.
     *
     * @param divisionOrgName the new 事业部
     */
    public void setDivisionOrgName(String divisionOrgName) {
        this.divisionOrgName = divisionOrgName;
    }

    /**
     * 获取 车队.
     *
     * @return the 车队
     */
    public String getTransDepartmentName() {
        return transDepartmentName;
    }

    /**
     * 设置 车队.
     *
     * @param transDepartmentName the new 车队
     */
    public void setTransDepartmentName(String transDepartmentName) {
        this.transDepartmentName = transDepartmentName;
    }

    /**
     * 获取 小组.
     *
     * @return the 小组
     */
    public String getGroupOrgName() {
        return groupOrgName;
    }

    /**
     * 设置 小组.
     *
     * @param groupOrgName the new 小组
     */
    public void setGroupOrgName(String groupOrgName) {
        this.groupOrgName = groupOrgName;
    }

    /**
     * 获取 所属大区.
     *
     * @return the 所属大区
     */
    public String getLargeDistrictName() {
        return largeDistrictName;
    }

    /**
     * 设置 所属大区.
     *
     * @param largeDistrictName the new 所属大区
     */
    public void setLargeDistrictName(String largeDistrictName) {
        this.largeDistrictName = largeDistrictName;
    }

    /**
     * 获取 油耗标准.
     *
     * @return the 油耗标准
     */
    public BigDecimal getStandardConsumption() {
        return standardConsumption;
    }

    /**
     * 设置 油耗标准.
     *
     * @param standardConsumption the new 油耗标准
     */
    public void setStandardConsumption(BigDecimal standardConsumption) {
        this.standardConsumption = standardConsumption;
    }

    /**
     * 获取 线路.
     *
     * @return the 线路
     */
    public String getLineCode() {
        return lineCode;
    }

    /**
     * 设置 线路.
     *
     * @param lineCode the new 线路
     */
    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }

    /**
     * 获取 线路名称.
     *
     * @return the 线路名称
     */
    public String getLineName() {
        return lineName;
    }

    /**
     * 设置 线路名称.
     *
     * @param lineName the new 线路名称
     */
    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    /**
     * 获取 出发站.
     *
     * @return the 出发站
     */
    public String getDeptRegionCode() {
        return deptRegionCode;
    }

    /**
     * 设置 出发站.
     *
     * @param deptRegionCode the new 出发站
     */
    public void setDeptRegionCode(String deptRegionCode) {
        this.deptRegionCode = deptRegionCode;
    }

    /**
     * 获取 出发站名称.
     *
     * @return the 出发站名称
     */
    public String getDeptRegionName() {
        return deptRegionName;
    }

    /**
     * 设置 出发站名称.
     *
     * @param deptRegionName the new 出发站名称
     */
    public void setDeptRegionName(String deptRegionName) {
        this.deptRegionName = deptRegionName;
    }

    /**
     * 获取 目的站.
     *
     * @return the 目的站
     */
    public String getArrvRegionCode() {
        return arrvRegionCode;
    }

    /**
     * 设置 目的站.
     *
     * @param arrvRegionCode the new 目的站
     */
    public void setArrvRegionCode(String arrvRegionCode) {
        this.arrvRegionCode = arrvRegionCode;
    }

    /**
     * 获取 目的站名称.
     *
     * @return the 目的站名称
     */
    public String getArrvRegionName() {
        return arrvRegionName;
    }

    /**
     * 设置 目的站名称.
     *
     * @param arrvRegionName the new 目的站名称
     */
    public void setArrvRegionName(String arrvRegionName) {
        this.arrvRegionName = arrvRegionName;
    }

    /**
     * 获取 发车类型.
     *
     * @return the 发车类型
     */
    public String getDepartureTypeCode() {
        return departureTypeCode;
    }

    /**
     * 设置 发车类型.
     *
     * @param departureTypeCode the new 发车类型
     */
    public void setDepartureTypeCode(String departureTypeCode) {
        this.departureTypeCode = departureTypeCode;
    }

    /**
     * 获取 出发公里数.
     *
     * @return the 出发公里数
     */
    public BigDecimal getStartKm() {
        return startKm;
    }

    /**
     * 设置 出发公里数.
     *
     * @param startKm the new 出发公里数
     */
    public void setStartKm(BigDecimal startKm) {
        this.startKm = startKm;
    }

    /**
     * 获取 到达公里数.
     *
     * @return the 到达公里数
     */
    public BigDecimal getArriveKm() {
        return arriveKm;
    }

    /**
     * 设置 到达公里数.
     *
     * @param arriveKm the new 到达公里数
     */
    public void setArriveKm(BigDecimal arriveKm) {
        this.arriveKm = arriveKm;
    }

    /**
     * 获取 载重.
     *
     * @return the 载重
     */
    public BigDecimal getRatedLoad() {
        return ratedLoad;
    }

    /**
     * 设置 载重.
     *
     * @param ratedLoad the new 载重
     */
    public void setRatedLoad(BigDecimal ratedLoad) {
        this.ratedLoad = ratedLoad;
    }

    /**
     * 获取 行驶公里数.
     *
     * @return the 行驶公里数
     */
    public BigDecimal getRunKm() {
        return runKm;
    }

    /**
     * 设置 行驶公里数.
     *
     * @param runKm the new 行驶公里数
     */
    public void setRunKm(BigDecimal runKm) {
        this.runKm = runKm;
    }

    /**
     * 获取 司机1.
     *
     * @return the 司机1
     */
    public String getDriver1Code() {
        return driver1Code;
    }

    /**
     * 设置 司机1.
     *
     * @param driver1Code the new 司机1
     */
    public void setDriver1Code(String driver1Code) {
        this.driver1Code = driver1Code;
    }

    /**
     * 获取 司机2.
     *
     * @return the 司机2
     */
    public String getDriver2Code() {
        return driver2Code;
    }

    /**
     * 设置 司机2.
     *
     * @param driver2Code the new 司机2
     */
    public void setDriver2Code(String driver2Code) {
        this.driver2Code = driver2Code;
    }

    /**
     * 获取 司机1姓名.
     *
     * @return the 司机1姓名
     */
    public String getDriver1Name() {
        return driver1Name;
    }

    /**
     * 设置 司机1姓名.
     *
     * @param driver1Name the new 司机1姓名
     */
    public void setDriver1Name(String driver1Name) {
        this.driver1Name = driver1Name;
    }

    /**
     * 获取 司机2姓名.
     *
     * @return the 司机2姓名
     */
    public String getDriver2Name() {
        return driver2Name;
    }

    /**
     * 设置 司机2姓名.
     *
     * @param driver2Name the new 司机2姓名
     */
    public void setDriver2Name(String driver2Name) {
        this.driver2Name = driver2Name;
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
     * 获取 发车模式.
     *
     * @return the 发车模式
     */
    public String getDepartureMode() {
        return departureMode;
    }

    /**
     * 设置 发车模式.
     *
     * @param departureMode the new 发车模式
     */
    public void setDepartureMode(String departureMode) {
        this.departureMode = departureMode;
    }

    /**
     * 获取 加油升数合计.
     *
     * @return the 加油升数合计
     */
    public BigDecimal getFuelAmount() {
        return fuelAmount;
    }

    /**
     * 设置 加油升数合计.
     *
     * @param fuelAmount the new 加油升数合计
     */
    public void setFuelAmount(BigDecimal fuelAmount) {
        this.fuelAmount = fuelAmount;
    }

    /**
     * 获取 平均单价.
     *
     * @return the 平均单价
     */
    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    /**
     * 设置 平均单价.
     *
     * @param averagePrice the new 平均单价
     */
    public void setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
    }

    /**
     * 获取 油费合计.
     *
     * @return the 油费合计
     */
    public BigDecimal getFuelFeeAmount() {
        return fuelFeeAmount;
    }

    /**
     * 设置 油费合计.
     *
     * @param fuelFeeAmount the new 油费合计
     */
    public void setFuelFeeAmount(BigDecimal fuelFeeAmount) {
        this.fuelFeeAmount = fuelFeeAmount;
    }

    /**
     * 获取 百公里油耗.
     *
     * @return the 百公里油耗
     */
    public BigDecimal getOhKmFuelConsumption() {
        return ohKmFuelConsumption;
    }

    /**
     * 设置 百公里油耗.
     *
     * @param ohKmFuelConsumption the new 百公里油耗
     */
    public void setOhKmFuelConsumption(BigDecimal ohKmFuelConsumption) {
        this.ohKmFuelConsumption = ohKmFuelConsumption;
    }

    /**
     * 获取 吨百公里油耗.
     *
     * @return the 吨百公里油耗
     */
    public BigDecimal getTohKmFuelConsumption() {
        return tohKmFuelConsumption;
    }

    /**
     * 设置 吨百公里油耗.
     *
     * @param tohKmFuelConsumption the new 吨百公里油耗
     */
    public void setTohKmFuelConsumption(BigDecimal tohKmFuelConsumption) {
        this.tohKmFuelConsumption = tohKmFuelConsumption;
    }

    /**
     * 获取 备注.
     *
     * @return the 备注
     */
    public String getNotes() {
        return notes;
    }

    /**
     * 设置 备注.
     *
     * @param notes the new 备注
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

	/**
	 * 获取 币种.
	 *
	 * @return the 币种
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * 设置 币种.
	 *
	 * @param currencyCode the new 币种
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * 获取 加油时间.
	 *
	 * @param the 加油日期
	 */
	public Date getFuelDate() {
		return fuelDate;
	}

	/**
	 * 设置 加油日期.
	 *
	 * @param currencyCode the new 加油时间
	 */
	@DateFormat(formate="yyyy-MM-dd")
	public void setFuelDate(Date fuelDate) {
		this.fuelDate = fuelDate;
	}
	
    /**
     * 获取 路桥费.
     *
     * @return the 路桥费
     */
	@DateFormat(formate="yyyy-MM-dd")
	public BigDecimal getRoadToll() {
		return roadToll;
	}

	/**
	 * 设置 路桥费.
	 *
	 * @param createDate the new 路桥费
	 */
	public void setRoadToll(BigDecimal roadToll) {
		this.roadToll = roadToll;
	}
	
	
	
}