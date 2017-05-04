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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/LeasedTruckEntity.java
 * 
 * FILE NAME        	: LeasedTruckEntity.java
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

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 
 * 用来存储交互“外请车挂车、外请车拖头、外请车厢式车”的数据库对应实体：SUC-608、SUC-103、SUC-44
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-11 下午5:02:50</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-11 下午5:02:50
 * @since
 * @version
 */
public class LeasedTruckEntity  extends BaseEntity  implements Cloneable{
    /**
     *  Serial Version UID
     */
    private static final long serialVersionUID = 3950553061916316780L;
    /**
     * 是否修改净空字段  默认净空没有修改N
     */
     private String isNotUpdateSelfVolume;
    //车牌号
    private String vehicleNo;

    //车架号
    private String vehicleFrameNo;

    //发动机号
    private String engineNo;

    //单双桥
    private String bridge;

    //载重
    private BigDecimal deadLoad;

    //自重
    private BigDecimal selfWeight;

    //出厂日期
    private Date beginTime;

    //报废日期
    private Date endTime;

    //有无高地板
    private String plat;

    //是否有尾板
    private String tailBoard;

    //车长
    private BigDecimal vehicleLength;

    //车宽
    private BigDecimal vehicleWidth;

    //车高
    private BigDecimal vehicleHeight;

    //是否敞篷车
    private String openVehicle;

    //是否高栏车
    private String railVehicle;

    //材质
    private String material;

    //净空
    private BigDecimal selfVolume;

    //是否装GPS
    private String gps;

    //GPS供应商
    private String gpsProvider;

    //是否合同车
    private String bargainVehicle;

    //营运证号
    private String operatingLic;

    //营运证有效开始日期
    private Date beginTimeOperatingLic;

    //营运证有效结束日期
    private Date endTimeOperatingLic;

    //行驶证号
    private String drivingLicense;

    //行驶证有效开始日期
    private Date beginTimeDrivingLic;

    //行驶证有效结束日期
    private Date endTimeDrivingLic;

    //保险卡号
    private String insureCard;

    //保险卡有效开始日期
    private Date beginTimeInsureCard;

    //保险卡有效结束日期
    private Date endTimeInsureCard;
    
    //车主姓名
    private String contact;

    //车主电话
    private String contactPhone;

    //联系地址
    private String contactAddress;

    //GPS设备号
    private String gpsNo;

    //状态
    private String status;

    //是否启用
    private String active;

    //备注
    private String notes;
    
    //车辆类型（挂车/厢式车/拖头）
    private String vehicleType;
    
    //车辆车型编码
    private String vehicleLengthCode;
    //车辆车型名称
    private String vehicleLengthName;

    //合同线路
    private String bargainRoute;
    
    //产地（进口/出口）
    private String producingArea;
    
    //车辆品牌
    private String brand;
    
    //所属外请司机身份证号
    private String leasedDriverIdCard;
    
    //外请司机姓名
    private String leasedDriverName;
    
    //外请司机电话
    private String driverPhone;
    
    //创建人姓名
    private String createUserName;
    //更新人姓名
    private String ModifyUserName;
    
    //创建用户所属部门
    private String createUserDept;
    //创建所属部门名称
    private String createUserDeptName;
    // 修改用户所属部门
    private String modifyUserDept;
    //修改所属部门名称
    private String modifyUserDeptName;
    
    //  部门code
    private String orgCode;
    
    //  车队名称
    private String name;
    
    //  操作人
    private String operatorName;
    //  测量人员
    private String measureEmployeeCode;
    
    //  测量经理
    private String measureManagerCode;
    
    //  测量高级经理
    private String measureSeniorManagerCode;
    //  测量人员名称
    private String measureEmployeeName;
    
    //  测量经理名称
    private String measureManagerName;
    
    //  测量高级经理名称
    private String measureSeniorManagerName;
    
	/**
	 * 车牌号
     * @return  the vehicleNo
     */
    public String getVehicleNo() {
        return vehicleNo;
    }
    
    /**
     * 车牌号
     * @param vehicleNo the vehicleNo to set
     */
    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }
    
    /**车架号
     * @return  the vehicleFrameNo
     */
    public String getVehicleFrameNo() {
        return vehicleFrameNo;
    }
    
    /**
     * 车架号
     * @param vehicleFrameNo the vehicleFrameNo to set
     */
    public void setVehicleFrameNo(String vehicleFrameNo) {
        this.vehicleFrameNo = vehicleFrameNo;
    }
    
    /**
     * 发动机号
     * @return  the engineNo
     */
    public String getEngineNo() {
        return engineNo;
    }
    
    /**
     * 发动机号
     * @param engineNo the engineNo to set
     */
    public void setEngineNo(String engineNo) {
        this.engineNo = engineNo;
    }
    
    /**单双桥
     * @return  the bridge
     */
    public String getBridge() {
        return bridge;
    }
    
    /**
     * 单双桥
     * @param bridge the bridge to set
     */
    public void setBridge(String bridge) {
        this.bridge = bridge;
    }
    
    /**载重
     * @return  the deadLoad
     */
    public BigDecimal getDeadLoad() {
        return deadLoad;
    }
    
    /**载重
     * @param deadLoad the deadLoad to set
     */
    public void setDeadLoad(BigDecimal deadLoad) {
        this.deadLoad = deadLoad;
    }
    
    /**自重
     * @return  the selfWeight
     */
    public BigDecimal getSelfWeight() {
        return selfWeight;
    }
    
    /**自重
     * @param selfWeight the selfWeight to set
     */
    public void setSelfWeight(BigDecimal selfWeight) {
        this.selfWeight = selfWeight;
    }
    
    /**出厂日期
     * @return  the beginTime
     */
    public Date getBeginTime() {
        return beginTime;
    }
    
    /**出厂日期
     * @param beginTime the beginTime to set
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }
    
    /**报废日期
     * @return  the endTime
     */
    public Date getEndTime() {
        return endTime;
    }
    
    /**报废日期
     * @param endTime the endTime to set
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
    /**有无高地板
     * @return  the plat
     */
    public String getPlat() {
        return plat;
    }
    
    /**有无高地板
     * @param plat the plat to set
     */
    public void setPlat(String plat) {
        this.plat = plat;
    }
    
    /**是否有尾板
     * @return  the tailBoard
     */
    public String getTailBoard() {
        return tailBoard;
    }
    
    /**是否有尾板
     * @param tailBoard the tailBoard to set
     */
    public void setTailBoard(String tailBoard) {
        this.tailBoard = tailBoard;
    }
    
    /**车长
     * @return  the vehicleLength
     */
    public BigDecimal getVehicleLength() {
        return vehicleLength;
    }
    
    /**车长
     * @param vehicleLength the vehicleLength to set
     */
    public void setVehicleLength(BigDecimal vehicleLength) {
        this.vehicleLength = vehicleLength;
    }
    
    /**车宽
     * @return  the vehicleWidth
     */
    public BigDecimal getVehicleWidth() {
        return vehicleWidth;
    }
    
    /**车宽
     * @param vehicleWidth the vehicleWidth to set
     */
    public void setVehicleWidth(BigDecimal vehicleWidth) {
        this.vehicleWidth = vehicleWidth;
    }
    
    /**车高
     * @return  the vehicleHeight
     */
    public BigDecimal getVehicleHeight() {
        return vehicleHeight;
    }
    
    /**车高
     * @param vehicleHeight the vehicleHeight to set
     */
    public void setVehicleHeight(BigDecimal vehicleHeight) {
        this.vehicleHeight = vehicleHeight;
    }
    
    /**是否敞篷车
     * @return  the openVehicle
     */
    public String getOpenVehicle() {
        return openVehicle;
    }
    
    /**是否敞篷车
     * @param openVehicle the openVehicle to set
     */
    public void setOpenVehicle(String openVehicle) {
        this.openVehicle = openVehicle;
    }
    
    /**是否高栏车
     * @return  the railVehicle
     */
    public String getRailVehicle() {
        return railVehicle;
    }
    
    /**是否高栏车
     * @param railVehicle the railVehicle to set
     */
    public void setRailVehicle(String railVehicle) {
        this.railVehicle = railVehicle;
    }
    
    /**材质
     * @return  the material
     */
    public String getMaterial() {
        return material;
    }
    
    /**材质
     * @param material the material to set
     */
    public void setMaterial(String material) {
        this.material = material;
    }
    
    /**净空
     * @return  the selfVolume
     */
    public BigDecimal getSelfVolume() {
        return selfVolume;
    }
    
    /**净空
     * @param selfVolume the selfVolume to set
     */
    public void setSelfVolume(BigDecimal selfVolume) {
        this.selfVolume = selfVolume;
    }
    
    /**是否装GPS
     * @return  the gps
     */
    public String getGps() {
        return gps;
    }
    
    /**是否装GPS
     * @param gps the gps to set
     */
    public void setGps(String gps) {
        this.gps = gps;
    }
    
    /**GPS供应商
     * @return  the gpsProvider
     */
    public String getGpsProvider() {
        return gpsProvider;
    }
    
    /**GPS供应商
     * @param gpsProvider the gpsProvider to set
     */
    public void setGpsProvider(String gpsProvider) {
        this.gpsProvider = gpsProvider;
    }
    
    /**是否合同车
     * @return  the bargainVehicle
     */
    public String getBargainVehicle() {
        return bargainVehicle;
    }
    
    /**是否合同车
     * @param bargainVehicle the bargainVehicle to set
     */
    public void setBargainVehicle(String bargainVehicle) {
        this.bargainVehicle = bargainVehicle;
    }
    
    /**营运证号
     * @return  the operatingLic
     */
    public String getOperatingLic() {
        return operatingLic;
    }
    
    /**营运证号
     * @param operatingLic the operatingLic to set
     */
    public void setOperatingLic(String operatingLic) {
        this.operatingLic = operatingLic;
    }
    
    /**营运证有效开始日期
     * @return  the beginTimeOperatingLic
     */
    public Date getBeginTimeOperatingLic() {
        return beginTimeOperatingLic;
    }
    
    /**营运证有效开始日期
     * @param beginTimeOperatingLic the beginTimeOperatingLic to set
     */
    public void setBeginTimeOperatingLic(Date beginTimeOperatingLic) {
        this.beginTimeOperatingLic = beginTimeOperatingLic;
    }
    
    /**营运证有效结束日期
     * @return  the endTimeOperatingLic
     */
    public Date getEndTimeOperatingLic() {
        return endTimeOperatingLic;
    }
    
    /**营运证有效结束日期
     * @param endTimeOperatingLic the endTimeOperatingLic to set
     */
    public void setEndTimeOperatingLic(Date endTimeOperatingLic) {
        this.endTimeOperatingLic = endTimeOperatingLic;
    }
    
    /**
     * //行驶证号
     * @return  the drivingLicense
     */
    public String getDrivingLicense() {
        return drivingLicense;
    }
    
    /**
     * //行驶证号
     * @param drivingLicense the drivingLicense to set
     */
    public void setDrivingLicense(String drivingLicense) {
        this.drivingLicense = drivingLicense;
    }
    
    /**行驶证有效开始日期
     * @return  the beginTimeDrivingLic
     */
    public Date getBeginTimeDrivingLic() {
        return beginTimeDrivingLic;
    }
    
    /**行驶证有效开始日期
     * @param beginTimeDrivingLic the beginTimeDrivingLic to set
     */
    public void setBeginTimeDrivingLic(Date beginTimeDrivingLic) {
        this.beginTimeDrivingLic = beginTimeDrivingLic;
    }
    
    /**行驶证有效结束日期
     * @return  the endTimeDrivingLic
     */
    public Date getEndTimeDrivingLic() {
        return endTimeDrivingLic;
    }
    
    /**行驶证有效结束日期
     * @param endTimeDrivingLic the endTimeDrivingLic to set
     */
    public void setEndTimeDrivingLic(Date endTimeDrivingLic) {
        this.endTimeDrivingLic = endTimeDrivingLic;
    }
    
    /**保险卡号
     * @return  the insureCard
     */
    public String getInsureCard() {
        return insureCard;
    }
    
    /**保险卡号
     * @param insureCard the insureCard to set
     */
    public void setInsureCard(String insureCard) {
        this.insureCard = insureCard;
    }
    
    /**保险卡有效开始日期
     * @return  the beginTimeInsureCard
     */
    public Date getBeginTimeInsureCard() {
        return beginTimeInsureCard;
    }
    
    /**保险卡有效开始日期
     * @param beginTimeInsureCard the beginTimeInsureCard to set
     */
    public void setBeginTimeInsureCard(Date beginTimeInsureCard) {
        this.beginTimeInsureCard = beginTimeInsureCard;
    }
    
    /**保险卡有效结束日期
     * @return  the endTimeInsureCard
     */
    public Date getEndTimeInsureCard() {
        return endTimeInsureCard;
    }
    
    /**保险卡有效结束日期
     * @param endTimeInsureCard the endTimeInsureCard to set
     */
    public void setEndTimeInsureCard(Date endTimeInsureCard) {
        this.endTimeInsureCard = endTimeInsureCard;
    }
    
    /**车主姓名
     * @return  the contact
     */
    public String getContact() {
        return contact;
    }

    /**车主姓名
     * @param contact the contact to set
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**车主电话
     * @return  the contactPhone
     */
    public String getContactPhone() {
        return contactPhone;
    }

    /**车主电话
     * @param contactPhone the contactPhone to set
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    /**联系地址
     * @return  the contactAddress
     */
    public String getContactAddress() {
        return contactAddress;
    }

    /**联系地址
     * @param contactAddress the contactAddress to set
     */
    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    /**GPS设备号
     * @return  the gpsNo
     */
    public String getGpsNo() {
        return gpsNo;
    }

    /**GPS设备号
     * @param gpsNo the gpsNo to set
     */
    public void setGpsNo(String gpsNo) {
        this.gpsNo = gpsNo;
    }

    /**状态
     * @return  the status
     */
    public String getStatus() {
        return status;
    }

    /**状态
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    
    /**是否启用
     * @return  the active
     */
    public String getActive() {
        return active;
    }

    /**是否启用
     * @param active the active to set
     */
    public void setActive(String active) {
        this.active = active;
    }
 
    /**备注
     * @return  the notes
     */
    public String getNotes() {
        return notes;
    }

    /**备注
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**车辆类型（挂车/厢式车/拖头）
     * @return  the vehicleType
     */
    public String getVehicleType() {
        return vehicleType;
    }

    /**车辆类型（挂车/厢式车/拖头）
     * @param vehicleType the vehicleType to set
     */
    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
    
    /**车辆车型编码
     * @return  the vehcleLengthCode
     */
    public String getVehicleLengthCode() {
        return vehicleLengthCode;
    }
    
    /**车辆车型编码
     * @param vehcleLengthCode the vehcleLengthCode to set
     */
    public void setVehicleLengthCode(String vehicleLengthCode) {
        this.vehicleLengthCode = vehicleLengthCode;
    }

    /**
     * /合同线路
     * @return  the bargainRoute
     */
    public String getBargainRoute() {
        return bargainRoute;
    }

    /**
     * /合同线路
     * @param bargainRoute the bargainRoute to set
     */
    public void setBargainRoute(String bargainRoute) {
        this.bargainRoute = bargainRoute;
    }
    
    /**
     * 产地（进口/出口）
     * @return  the producingArea
     */
    public String getProducingArea() {
        return producingArea;
    }
    
    /**产地（进口/出口）
     * @param producingArea the producingArea to set
     */
    public void setProducingArea(String producingArea) {
        this.producingArea = producingArea;
    }

    
    /**车辆品牌
     * @return  the brand
     */
    public String getBrand() {
        return brand;
    }
    
    /**车辆品牌
     * @param brand the brand to set
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**所属外请司机身份证号
     * @return  the driverCard
     */
    public String getLeasedDriverIdCard() {
        return leasedDriverIdCard;
    }
    /**
     * 车辆车型名称
     * @return
     */
    public String getVehicleLengthName() {
		return vehicleLengthName;
	}
    /**
     * 车辆车型名称
     * @param vehicleLengthName
     */
	public void setVehicleLengthName(String vehicleLengthName) {
		this.vehicleLengthName = vehicleLengthName;
	}

	/**所属外请司机身份证号
     * @param driverCard the driverCard to set
     */
    public void setLeasedDriverIdCard(String leasedDriverIdCard) {
        this.leasedDriverIdCard = leasedDriverIdCard;
    }
    
    /**
     * 获取司机姓名
     * @return
     */
	public String getLeasedDriverName() {
		return leasedDriverName;
	}
	
	/**
	 * 设置司机姓名
	 * @param leasedDriverName
	 */
	public void setLeasedDriverName(String leasedDriverName) {
		this.leasedDriverName = leasedDriverName;
	}
	
	/**
	 * 获取司机电话
	 * @return
	 */
	public String getDriverPhone() {
		return driverPhone;
	}
	
	/**
	 * 设置司机电话
	 * @param driverPhone
	 */
	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}

	/***
	 * 创建用户所属部门
	 * @return
	 */
	public String getCreateUserDept() {
		return createUserDept;
	}

	/**
	 * 创建用户所属部门
	 * @param createUserDept
	 */
	public void setCreateUserDept(String createUserDept) {
		this.createUserDept = createUserDept;
	}

	/**
	 * 修改用户所属部门
	 * @return
	 */
	public String getModifyUserDept() {
		return modifyUserDept;
	}

	/***
	 * 修改用户所属部门
	 * @param modifyUserDept
	 */
	public void setModifyUserDept(String modifyUserDept) {
		this.modifyUserDept = modifyUserDept;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getModifyUserName() {
		return ModifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		ModifyUserName = modifyUserName;
	}

	/**
	 * 创建所属部门名称
	 * @return
	 */
	public String getCreateUserDeptName() {
		return createUserDeptName;
	}

	/**
	 * 创建所属部门名称
	 */
	public void setCreateUserDeptName(String createUserDeptName) {
		this.createUserDeptName = createUserDeptName;
	}

	public String getModifyUserDeptName() {
		return modifyUserDeptName;
	}

	public void setModifyUserDeptName(String modifyUserDeptName) {
		this.modifyUserDeptName = modifyUserDeptName;
	}
	/**
	 * 是否修改净空字段  默认净空没有修改N Y修改
	 * @return
	 */
	public String getIsNotUpdateSelfVolume() {
		return isNotUpdateSelfVolume;
	}
	/**
	 * 是否修改净空字段  默认净空没有修改N Y修改
	 * @param isNotUpdateSelfVolume
	 */
	public void setIsNotUpdateSelfVolume(String isNotUpdateSelfVolume) {
		this.isNotUpdateSelfVolume = isNotUpdateSelfVolume;
	}
	
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getMeasureEmployeeCode() {
		return measureEmployeeCode;
	}

	public void setMeasureEmployeeCode(String measureEmployeeCode) {
		this.measureEmployeeCode = measureEmployeeCode;
	}

	public String getMeasureManagerCode() {
		return measureManagerCode;
	}

	public void setMeasureManagerCode(String measureManagerCode) {
		this.measureManagerCode = measureManagerCode;
	}

	public String getMeasureSeniorManagerCode() {
		return measureSeniorManagerCode;
	}

	public void setMeasureSeniorManagerCode(String measureSeniorManagerCode) {
		this.measureSeniorManagerCode = measureSeniorManagerCode;
	}

	public String getMeasureEmployeeName() {
		return measureEmployeeName;
	}

	public void setMeasureEmployeeName(String measureEmployeeName) {
		this.measureEmployeeName = measureEmployeeName;
	}

	public String getMeasureManagerName() {
		return measureManagerName;
	}

	public void setMeasureManagerName(String measureManagerName) {
		this.measureManagerName = measureManagerName;
	}

	public String getMeasureSeniorManagerName() {
		return measureSeniorManagerName;
	}

	public void setMeasureSeniorManagerName(String measureSeniorManagerName) {
		this.measureSeniorManagerName = measureSeniorManagerName;
	}
	
	public Object clone() {
		LeasedTruckEntity o = null;
		try {
			o = (LeasedTruckEntity) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}

}
