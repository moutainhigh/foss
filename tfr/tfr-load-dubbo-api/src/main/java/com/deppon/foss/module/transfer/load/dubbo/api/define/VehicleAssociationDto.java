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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/VehicleAssociationDto.java
 * 
 * FILE NAME        	: VehicleAssociationDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.dubbo.api.define;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用来交互“车辆（公司、外请车）”的数据实体相关联信息的DTO
 * <p>基本属性包括：车辆直属部门、车辆车型、车辆业务、车辆品牌</p>
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-5 上午11:30:49</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-5 上午11:30:49
 * @since
 * @version
 */
public class VehicleAssociationDto implements Serializable{

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -8141000117198218388L;

    /**
     * 车辆直属部门（或车队小组）名称
     */
    private String vehicleOrganizationName;
    
    /**
     * 直属部门（或车队小组）负责人工号
     */
    private String vehicleOrganizationLeaderCode;
    
    /**
     * 直属部门（或车队小组）负责人姓名
     */
    private String vehicleOrganizationLeaderName;
    
    /**
     * 直属部门（或车队小组）负责人电话
     */
    private String vehicleOrganizationLeaderPhone;
    
    /**
     * 车辆车型（车长值）
     */
    private BigDecimal vehicleLengthValue;
    
    /**
     * 车辆车型（每公里费用）
     */
    private BigDecimal eachKilometersFees;
    
    /**
     * 车辆业务名称（车辆使用类型编码，如：物流班车）
     */
    private String vehicleUsedTypeName;
    
    /**
     * 车辆品牌名称
     */
    private String vehicleBrandName;
    
    /**
     * 所属车队编码
     */
    private String vehicleMotorcadeCode;
    
    /**
     * 所属车队名称
     */
    private String vehicleMotorcadeName;
    
    /**
     * 所属车队负责人工号
     */
    private String vehicleMotorcadeLeaderCode;
    
    /**
     * 所属车队负责人姓名
     */
    private String vehicleMotorcadeLeaderName;
    
    /**
     * 所属车队负责人电话
     */
    private String vehicleMotorcadeLeaderPhone;
    
    /**
     * 停车原因
     */
    private String unavilableReason;
    
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
     * 车辆直属部门（或车队小组）名称
     * 
     * @return  the vehicleOrganizationName
     */
    public String getVehicleOrganizationName() {
        return vehicleOrganizationName;
    }
    
    /**
     * @param vehicleOrganizationName the vehicleOrganizationName to set
     */
    public void setVehicleOrganizationName(String vehicleOrganizationName) {
        this.vehicleOrganizationName = vehicleOrganizationName;
    }

    /**
     * 车辆车型（车型车长）
     * 
     * @return  the vehicleLength
     */
    public BigDecimal getVehicleLengthValue() {
        return vehicleLengthValue;
    }

    /**
     * @param vehicleLength the vehicleLength to set
     */
    public void setVehicleLengthValue(BigDecimal vehicleLengthValue) {
        this.vehicleLengthValue = vehicleLengthValue;
    }
    
    /**
     * 车辆车型（名称）
     * 
     * @return  the vehicleLengthName
     */
    public String getVehicleLengthName() {
        return vehicleLengthName;
    }
    
    /**
     * @param vehicleLengthName the vehicleLengthName to set
     */
    public void setVehicleLengthName(String vehicleLengthName) {
        this.vehicleLengthName = vehicleLengthName;
    }
    
    /**
     * 车辆车型（每公里费用）
     * 
     * @return  the eachKilometersFees
     */
    public BigDecimal getEachKilometersFees() {
        return eachKilometersFees;
    }
    
    /**
     * @param eachKilometersFees the eachKilometersFees to set
     */
    public void setEachKilometersFees(BigDecimal eachKilometersFees) {
        this.eachKilometersFees = eachKilometersFees;
    }

    /**
     * 直属部门（或车队小组）负责人工号
     * 
     * @return  the vehicleOrganizationLeaderCode
     */
    public String getVehicleOrganizationLeaderCode() {
        return vehicleOrganizationLeaderCode;
    }
    
    /**
     * @param vehicleOrganizationLeaderCode the vehicleOrganizationLeaderCode to set
     */
    public void setVehicleOrganizationLeaderCode(String vehicleOrganizationLeaderCode) {
        this.vehicleOrganizationLeaderCode = vehicleOrganizationLeaderCode;
    }

    /**
     * 直属部门（或车队小组）负责人姓名
     * 
     * @return  the vehicleOrganizationLeaderName
     */
    public String getVehicleOrganizationLeaderName() {
        return vehicleOrganizationLeaderName;
    }
    
    /**
     * @param vehicleOrganizationLeaderName the vehicleOrganizationLeaderName to set
     */
    public void setVehicleOrganizationLeaderName(String vehicleOrganizationLeaderName) {
        this.vehicleOrganizationLeaderName = vehicleOrganizationLeaderName;
    }

    /**
     * 直属部门（或车队小组）负责人电话
     * 
     * @return  the vehicleOrganizationLeaderPhone
     */
    public String getVehicleOrganizationLeaderPhone() {
        return vehicleOrganizationLeaderPhone;
    }
    
    /**
     * @param vehicleOrganizationLeaderPhone the vehicleOrganizationLeaderPhone to set
     */
    public void setVehicleOrganizationLeaderPhone(
    	String vehicleOrganizationLeaderPhone) {
        this.vehicleOrganizationLeaderPhone = vehicleOrganizationLeaderPhone;
    }
    
    /**
     * 车辆业务名称（车辆使用类型编码，如：物流班车）
     * 
     * @return  the vehicleUsedTypeName
     */
    public String getVehicleUsedTypeName() {
        return vehicleUsedTypeName;
    }

    /**
     * @param vehicleUsedTypeName the vehicleUsedTypeName to set
     */
    public void setVehicleUsedTypeName(String vehicleUsedTypeName) {
        this.vehicleUsedTypeName = vehicleUsedTypeName;
    }
    
    /**
     * 车辆品牌名称
     * 
     * @return  the vehicleBrandName
     */
    public String getVehicleBrandName() {
        return vehicleBrandName;
    }

    /**
     * @param vehicleBrandName the vehicleBrandName to set
     */
    public void setVehicleBrandName(String vehicleBrandName) {
        this.vehicleBrandName = vehicleBrandName;
    }
    
    /**
     * 所属车队编码
     * 
     * @return  the vehicleMotorcadeCode
     */
    public String getVehicleMotorcadeCode() {
        return vehicleMotorcadeCode;
    }
    
    /**
     * @param vehicleMotorcadeCode the vehicleMotorcadeCode to set
     */
    public void setVehicleMotorcadeCode(String vehicleMotorcadeCode) {
        this.vehicleMotorcadeCode = vehicleMotorcadeCode;
    }
    
    /**
     * 所属车队名称
     * 
     * @return  the vehicleMotorcadeName
     */
    public String getVehicleMotorcadeName() {
        return vehicleMotorcadeName;
    }

    /**
     * @param vehicleMotorcadeName the vehicleMotorcadeName to set
     */
    public void setVehicleMotorcadeName(String vehicleMotorcadeName) {
        this.vehicleMotorcadeName = vehicleMotorcadeName;
    }
    
    /**
     * 所属车队负责人工号
     * 
     * @return  the vehicleMotorcadeLeaderCode
     */
    public String getVehicleMotorcadeLeaderCode() {
        return vehicleMotorcadeLeaderCode;
    }
    
    /**
     * @param vehicleMotorcadeLeaderCode the vehicleMotorcadeLeaderCode to set
     */
    public void setVehicleMotorcadeLeaderCode(String vehicleMotorcadeLeaderCode) {
        this.vehicleMotorcadeLeaderCode = vehicleMotorcadeLeaderCode;
    }
    
    /**
     * 所属车队负责人姓名
     * 
     * @return  the vehicleMotorcadeLeaderName
     */
    public String getVehicleMotorcadeLeaderName() {
        return vehicleMotorcadeLeaderName;
    }
    
    /**
     * @param vehicleMotorcadeLeaderName the vehicleMotorcadeLeaderName to set
     */
    public void setVehicleMotorcadeLeaderName(String vehicleMotorcadeLeaderName) {
        this.vehicleMotorcadeLeaderName = vehicleMotorcadeLeaderName;
    }
    
    /**
     * 所属车队负责人电话
     * 
     * @return  the vehicleMotorcadeLeaderPhone
     */
    public String getVehicleMotorcadeLeaderPhone() {
        return vehicleMotorcadeLeaderPhone;
    }

    /**
     * @param vehicleMotorcadeLeaderPhone the vehicleMotorcadeLeaderPhone to set
     */
    public void setVehicleMotorcadeLeaderPhone(String vehicleMotorcadeLeaderPhone) {
        this.vehicleMotorcadeLeaderPhone = vehicleMotorcadeLeaderPhone;
    }
    
    /**
     * 停车原因
     * 
     * @return  the unavilableReason
     */
    public String getUnavilableReason() {
        return unavilableReason;
    }

    /**
     * 停车原因
     * 
     * @param unavilableReason the unavilableReason to set
     */
    public void setUnavilableReason(String unavilableReason) {
        this.unavilableReason = unavilableReason;
    }

	/**
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
	 * @return the vehicleGps
	 */
	public String getVehicleGps() {
		return vehicleGps;
	}

	/**
	 * @param vehicleGps the vehicleGps to set
	 */
	public void setVehicleGps(String vehicleGps) {
		this.vehicleGps = vehicleGps;
	}

	/**
	 * @return the vehicleGpsNo
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
	 * @return the vehicleContainerCode
	 */
	public String getVehicleContainerCode() {
		return vehicleContainerCode;
	}

	/**
	 * @param vehicleContainerCode the vehicleContainerCode to set
	 */
	public void setVehicleContainerCode(String vehicleContainerCode) {
		this.vehicleContainerCode = vehicleContainerCode;
	}

	/**
	 * @return the vehicleLength
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
	 * @return the vehicleWidth
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
	 * @return the vehicleHeight
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
	 * @return the openVehicle
	 */
	public String getOpenVehicle() {
		return openVehicle;
	}

	/**
	 * @param openVehicle the openVehicle to set
	 */
	public void setOpenVehicle(String openVehicle) {
		this.openVehicle = openVehicle;
	}

	/**
	 * @return the tailBoard
	 */
	public String getTailBoard() {
		return tailBoard;
	}

	/**
	 * @param tailBoard the tailBoard to set
	 */
	public void setTailBoard(String tailBoard) {
		this.tailBoard = tailBoard;
	}

	/**
	 * @return the vehicleOwnershipType
	 */
	public String getVehicleOwnershipType() {
		return vehicleOwnershipType;
	}

	/**
	 * @param vehicleOwnershipType the vehicleOwnershipType to set
	 */
	public void setVehicleOwnershipType(String vehicleOwnershipType) {
		this.vehicleOwnershipType = vehicleOwnershipType;
	}

	/**
	 * @return the vehicleOrganizationCode
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
	 * @return the vehicleUsedTypeCode
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
	 * @return the vehicleBrandCode
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
	 * @return the vehicleLengthCode
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
	 * @return the bargainVehicle
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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the bridge
	 */
	public String getBridge() {
		return bridge;
	}

	/**
	 * @param bridge the bridge to set
	 */
	public void setBridge(String bridge) {
		this.bridge = bridge;
	}

	/**
	 * @return the selfWeight
	 */
	public BigDecimal getSelfWeight() {
		return selfWeight;
	}

	/**
	 * @param selfWeight the selfWeight to set
	 */
	public void setSelfWeight(BigDecimal selfWeight) {
		this.selfWeight = selfWeight;
	}

	/**
	 * @return the railVehicle
	 */
	public String getRailVehicle() {
		return railVehicle;
	}

	/**
	 * @param railVehicle the railVehicle to set
	 */
	public void setRailVehicle(String railVehicle) {
		this.railVehicle = railVehicle;
	}

	/**
	 * @return the unavilableCode
	 */
	public String getUnavilableCode() {
		return unavilableCode;
	}

	/**
	 * @param unavilableCode the unavilableCode to set
	 */
	public void setUnavilableCode(String unavilableCode) {
		this.unavilableCode = unavilableCode;
	}

	/**
	 * @return the vehicleType
	 */
	public String getVehicleType() {
		return vehicleType;
	}

	/**
	 * @param vehicleType the vehicleType to set
	 */
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	/**
	 * @return the consumeFuel
	 */
	public BigDecimal getConsumeFuel() {
		return consumeFuel;
	}

	/**
	 * @param consumeFuel the consumeFuel to set
	 */
	public void setConsumeFuel(BigDecimal consumeFuel) {
		this.consumeFuel = consumeFuel;
	}

	/**
	 * @return the beginTime
	 */
	public Date getBeginTime() {
		return beginTime;
	}

	/**
	 * @param beginTime the beginTime to set
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
