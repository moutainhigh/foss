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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/shared/domain/DriveArchiveEntity.java
 *  
 *  FILE NAME          :DriveArchiveEntity.java
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

/**
 * 车辆行驶档案实体
 * @author foss-liuxue(for IBM)
 * @date 2012-12-28 下午2:32:56
 */
public class DriveArchiveEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3950575849577729660L;
	/**id*/
	private String id;
	/**车牌号*/
    private String vehicleNo;  
    /**小组*/
    private String groupCode;   
    /**车队*/
    private String transDepartment;   
    /**车辆品牌*/
    private String vehicleBrand;   
    /**货柜号码*/
    private String containerNo;   
    /**车辆型号*/
    private String vehicleTypeLength;   
    /**线路号*/
    private String lineNo;   
    /**线路名称*/
    private String lineName;   
    /**实际发车时间*/
    private Date departureTime;   
    /**目的站*/
    private String arrvRegionName;   
    /**始发站*/
    private String deptRegionName;   
    /**出发公里数*/
    private BigDecimal startKm;   
    /**到达公里数*/
    private BigDecimal arriveKm;   
    /**行驶公里数*/
    private BigDecimal runKm;   
    /**运输体积*/
    private BigDecimal volume;   
    /**司机1工号*/
    private String driver1Code;   
    /**司机1姓名*/
    private String driver1Name;   
    /**司机2工号*/
    private String driver2Code;   
    /**司机2姓名*/
    private String driver2Name;   
    /**运输重量*/
    private BigDecimal weight;   
    /**签单费*/
    private BigDecimal signBillFee;   
    /**司机提成*/
    private BigDecimal driverRoyalty;   
    /**燃油费*/
    private BigDecimal fuelFee;   
    /**路桥费*/
    private BigDecimal tollFee;   
    /**罚款类型*/
    private String fineType;   
    /**付款金额*/
    private BigDecimal payAmount;   
    /**规定发车时间*/
    private Date stipulateDepartureTime;   
    /**规定到达时间*/
    private Date stipulateArriveTime;   
    /**标准时效*/
    private BigDecimal standardPreion;   
    /**是否晚发	*/
    private String isLateDeparture;   
    /**制单人编号*/
    private String archiveUserCode;   
    /**制单人名称*/
    private String archiveUserName;   
    /**实际到达时间*/
    private Date arriveTime;   
    /**实际时效*/
    private BigDecimal preion;   
    /**是否晚到*/
    private String isLateArrive;   
    /**晚发原因*/
    private String lateDepartureReason;   
    /**晚到原因*/
    private String lateArriveReason;   
    /**是否对发*/
    private String isMutual;   
    /**罚款金额*/
    private BigDecimal fineAmount;  
    /**创建时间*/
    private Date createTime;
    
    /**
     * 获取 创建时间.
     *
     * @return the 创建时间
     */
    public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置 创建时间.
	 *
	 * @param createTime the new 创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
     * 配载车次号
     */
    private String vehicleAssembleNo;
    
    /**
     * 班次号
     */
    private String frequencyNo;
    
    /**
     * 加油升数
     */
    private BigDecimal fuelQty;

    /**
     * 加油单价
     */
    private BigDecimal fuelPrice;
    
    /**
     * 百公里油耗
     */
    private BigDecimal onKmFuelConsumption;

    /**
     * 获取 罚款金额.
     *
     * @return the 罚款金额
     */
    public BigDecimal getFineAmount() {
		return fineAmount;
	}

	/**
	 * 获取 配载车次号.
	 *
	 * @return the 配载车次号
	 */
	public String getVehicleAssembleNo() {
		return vehicleAssembleNo;
	}

	/**
	 * 设置 配载车次号.
	 *
	 * @param vehicleAssembleNo the new 配载车次号
	 */
	public void setVehicleAssembleNo(String vehicleAssembleNo) {
		this.vehicleAssembleNo = vehicleAssembleNo;
	}

	/**
	 * 获取 班次号.
	 *
	 * @return the 班次号
	 */
	public String getFrequencyNo() {
		return frequencyNo;
	}

	/**
	 * 设置 班次号.
	 *
	 * @param frequencyNo the new 班次号
	 */
	public void setFrequencyNo(String frequencyNo) {
		this.frequencyNo = frequencyNo;
	}

	/**
	 * 获取 加油升数.
	 *
	 * @return the 加油升数
	 */
	public BigDecimal getFuelQty() {
		return fuelQty;
	}

	/**
	 * 设置 加油升数.
	 *
	 * @param fuelQty the new 加油升数
	 */
	public void setFuelQty(BigDecimal fuelQty) {
		this.fuelQty = fuelQty;
	}

	/**
	 * 获取 加油单价.
	 *
	 * @return the 加油单价
	 */
	public BigDecimal getFuelPrice() {
		return fuelPrice;
	}

	/**
	 * 设置 加油单价.
	 *
	 * @param fuelPrice the new 加油单价
	 */
	public void setFuelPrice(BigDecimal fuelPrice) {
		this.fuelPrice = fuelPrice;
	}

	/**
	 * 获取 百公里油耗.
	 *
	 * @return the 百公里油耗
	 */
	public BigDecimal getOnKmFuelConsumption() {
		return onKmFuelConsumption;
	}

	/**
	 * 设置 百公里油耗.
	 *
	 * @param onKmFuelConsumption the new 百公里油耗
	 */
	public void setOnKmFuelConsumption(BigDecimal onKmFuelConsumption) {
		this.onKmFuelConsumption = onKmFuelConsumption;
	}

	/**
	 * 设置 罚款金额.
	 *
	 * @param fineAmount the new 罚款金额
	 */
	public void setFineAmount(BigDecimal fineAmount) {
		this.fineAmount = fineAmount;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.entity.BaseEntity#getId()
	 */
	public String getId() {
        return id;
    }

    /* (non-Javadoc)
     * @see com.deppon.foss.framework.entity.BaseEntity#setId(java.lang.String)
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
     * 获取 小组.
     *
     * @return the 小组
     */
    public String getGroupCode() {
        return groupCode;
    }

    /**
     * 设置 小组.
     *
     * @param groupCode the new 小组
     */
    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    /**
     * 获取 车队.
     *
     * @return the 车队
     */
    public String getTransDepartment() {
        return transDepartment;
    }

    /**
     * 设置 车队.
     *
     * @param transDepartment the new 车队
     */
    public void setTransDepartment(String transDepartment) {
        this.transDepartment = transDepartment;
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
     * 获取 货柜号码.
     *
     * @return the 货柜号码
     */
    public String getContainerNo() {
        return containerNo;
    }

    /**
     * 设置 货柜号码.
     *
     * @param containerNo the new 货柜号码
     */
    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo;
    }

    /**
     * 获取 车辆型号.
     *
     * @return the 车辆型号
     */
    public String getVehicleTypeLength() {
        return vehicleTypeLength;
    }

    /**
     * 设置 车辆型号.
     *
     * @param vehicleTypeLength the new 车辆型号
     */
    public void setVehicleTypeLength(String vehicleTypeLength) {
        this.vehicleTypeLength = vehicleTypeLength;
    }

    /**
     * 获取 线路号.
     *
     * @return the 线路号
     */
    public String getLineNo() {
        return lineNo;
    }

    /**
     * 设置 线路号.
     *
     * @param lineNo the new 线路号
     */
    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
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
     * 获取 实际发车时间.
     *
     * @return the 实际发车时间
     */
    public Date getDepartureTime() {
        return departureTime;
    }

    /**
     * 设置 实际发车时间.
     *
     * @param departureTime the new 实际发车时间
     */
    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * 获取 目的站.
     *
     * @return the 目的站
     */
    public String getArrvRegionName() {
        return arrvRegionName;
    }

    /**
     * 设置 目的站.
     *
     * @param arrvRegionName the new 目的站
     */
    public void setArrvRegionName(String arrvRegionName) {
        this.arrvRegionName = arrvRegionName;
    }

    /**
     * 获取 始发站.
     *
     * @return the 始发站
     */
    public String getDeptRegionName() {
        return deptRegionName;
    }

    /**
     * 设置 始发站.
     *
     * @param deptRegionName the new 始发站
     */
    public void setDeptRegionName(String deptRegionName) {
        this.deptRegionName = deptRegionName;
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
     * 获取 运输体积.
     *
     * @return the 运输体积
     */
    public BigDecimal getVolume() {
        return volume;
    }

    /**
     * 设置 运输体积.
     *
     * @param volume the new 运输体积
     */
    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    /**
     * 获取 司机1工号.
     *
     * @return the 司机1工号
     */
    public String getDriver1Code() {
        return driver1Code;
    }

    /**
     * 设置 司机1工号.
     *
     * @param driver1Code the new 司机1工号
     */
    public void setDriver1Code(String driver1Code) {
        this.driver1Code = driver1Code;
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
     * 获取 司机2工号.
     *
     * @return the 司机2工号
     */
    public String getDriver2Code() {
        return driver2Code;
    }

    /**
     * 设置 司机2工号.
     *
     * @param driver2Code the new 司机2工号
     */
    public void setDriver2Code(String driver2Code) {
        this.driver2Code = driver2Code;
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
     * 获取 运输重量.
     *
     * @return the 运输重量
     */
    public BigDecimal getWeight() {
        return weight;
    }

    /**
     * 设置 运输重量.
     *
     * @param weight the new 运输重量
     */
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    /**
     * 获取 签单费.
     *
     * @return the 签单费
     */
    public BigDecimal getSignBillFee() {
        return signBillFee;
    }

    /**
     * 设置 签单费.
     *
     * @param signBillFee the new 签单费
     */
    public void setSignBillFee(BigDecimal signBillFee) {
        this.signBillFee = signBillFee;
    }

    /**
     * 获取 司机提成.
     *
     * @return the 司机提成
     */
    public BigDecimal getDriverRoyalty() {
        return driverRoyalty;
    }

    /**
     * 设置 司机提成.
     *
     * @param driverRoyalty the new 司机提成
     */
    public void setDriverRoyalty(BigDecimal driverRoyalty) {
        this.driverRoyalty = driverRoyalty;
    }

    /**
     * 获取 燃油费.
     *
     * @return the 燃油费
     */
    public BigDecimal getFuelFee() {
        return fuelFee;
    }

    /**
     * 设置 燃油费.
     *
     * @param fuelFee the new 燃油费
     */
    public void setFuelFee(BigDecimal fuelFee) {
        this.fuelFee = fuelFee;
    }

    /**
     * 获取 路桥费.
     *
     * @return the 路桥费
     */
    public BigDecimal getTollFee() {
        return tollFee;
    }

    /**
     * 设置 路桥费.
     *
     * @param tollFee the new 路桥费
     */
    public void setTollFee(BigDecimal tollFee) {
        this.tollFee = tollFee;
    }

    /**
     * 获取 罚款类型.
     *
     * @return the 罚款类型
     */
    public String getFineType() {
        return fineType;
    }

    /**
     * 设置 罚款类型.
     *
     * @param fineType the new 罚款类型
     */
    public void setFineType(String fineType) {
        this.fineType = fineType;
    }

    /**
     * 获取 付款金额.
     *
     * @return the 付款金额
     */
    public BigDecimal getPayAmount() {
        return payAmount;
    }

    /**
     * 设置 付款金额.
     *
     * @param payAmount the new 付款金额
     */
    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    /**
     * 获取 规定发车时间.
     *
     * @return the 规定发车时间
     */
    public Date getStipulateDepartureTime() {
        return stipulateDepartureTime;
    }

    /**
     * 设置 规定发车时间.
     *
     * @param stipulateDepartureTime the new 规定发车时间
     */
    public void setStipulateDepartureTime(Date stipulateDepartureTime) {
        this.stipulateDepartureTime = stipulateDepartureTime;
    }

    /**
     * 获取 规定到达时间.
     *
     * @return the 规定到达时间
     */
    public Date getStipulateArriveTime() {
        return stipulateArriveTime;
    }

    /**
     * 设置 规定到达时间.
     *
     * @param stipulateArriveTime the new 规定到达时间
     */
    public void setStipulateArriveTime(Date stipulateArriveTime) {
        this.stipulateArriveTime = stipulateArriveTime;
    }

    /**
     * 获取 标准时效.
     *
     * @return the 标准时效
     */
    public BigDecimal getStandardPreion() {
        return standardPreion;
    }

    /**
     * 设置 标准时效.
     *
     * @param standardPreion the new 标准时效
     */
    public void setStandardPreion(BigDecimal standardPreion) {
        this.standardPreion = standardPreion;
    }

    /**
     * 获取 是否晚发.
     *
     * @return the 是否晚发
     */
    public String getIsLateDeparture() {
        return isLateDeparture;
    }

    /**
     * 设置 是否晚发.
     *
     * @param isLateDeparture the new 是否晚发
     */
    public void setIsLateDeparture(String isLateDeparture) {
        this.isLateDeparture = isLateDeparture;
    }

    /**
     * 获取 制单人编号.
     *
     * @return the 制单人编号
     */
    public String getArchiveUserCode() {
        return archiveUserCode;
    }

    /**
     * 设置 制单人编号.
     *
     * @param archiveUserCode the new 制单人编号
     */
    public void setArchiveUserCode(String archiveUserCode) {
        this.archiveUserCode = archiveUserCode;
    }

    /**
     * 获取 制单人名称.
     *
     * @return the 制单人名称
     */
    public String getArchiveUserName() {
        return archiveUserName;
    }

    /**
     * 设置 制单人名称.
     *
     * @param archiveUserName the new 制单人名称
     */
    public void setArchiveUserName(String archiveUserName) {
        this.archiveUserName = archiveUserName;
    }

    /**
     * 获取 实际到达时间.
     *
     * @return the 实际到达时间
     */
    public Date getArriveTime() {
        return arriveTime;
    }

    /**
     * 设置 实际到达时间.
     *
     * @param arriveTime the new 实际到达时间
     */
    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }

    /**
     * 获取 实际时效.
     *
     * @return the 实际时效
     */
    public BigDecimal getPreion() {
        return preion;
    }

    /**
     * 设置 实际时效.
     *
     * @param preion the new 实际时效
     */
    public void setPreion(BigDecimal preion) {
        this.preion = preion;
    }

    /**
     * 获取 是否晚到.
     *
     * @return the 是否晚到
     */
    public String getIsLateArrive() {
        return isLateArrive;
    }

    /**
     * 设置 是否晚到.
     *
     * @param isLateArrive the new 是否晚到
     */
    public void setIsLateArrive(String isLateArrive) {
        this.isLateArrive = isLateArrive;
    }

    /**
     * 获取 晚发原因.
     *
     * @return the 晚发原因
     */
    public String getLateDepartureReason() {
        return lateDepartureReason;
    }

    /**
     * 设置 晚发原因.
     *
     * @param lateDepartureReason the new 晚发原因
     */
    public void setLateDepartureReason(String lateDepartureReason) {
        this.lateDepartureReason = lateDepartureReason;
    }

    /**
     * 获取 晚到原因.
     *
     * @return the 晚到原因
     */
    public String getLateArriveReason() {
        return lateArriveReason;
    }

    /**
     * 设置 晚到原因.
     *
     * @param lateArriveReason the new 晚到原因
     */
    public void setLateArriveReason(String lateArriveReason) {
        this.lateArriveReason = lateArriveReason;
    }

    /**
     * 获取 是否对发.
     *
     * @return the 是否对发
     */
    public String getIsMutual() {
        return isMutual;
    }

    /**
     * 设置 是否对发.
     *
     * @param isMutual the new 是否对发
     */
    public void setIsMutual(String isMutual) {
        this.isMutual = isMutual;
    }
}