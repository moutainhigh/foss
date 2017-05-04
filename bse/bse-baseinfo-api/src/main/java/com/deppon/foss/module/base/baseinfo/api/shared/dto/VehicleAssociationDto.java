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
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

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
public class VehicleAssociationDto extends VehicleBaseDto implements Serializable{

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
     * 车辆车型（名称）
     */
    private String vehicleLengthName;
    
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
}
