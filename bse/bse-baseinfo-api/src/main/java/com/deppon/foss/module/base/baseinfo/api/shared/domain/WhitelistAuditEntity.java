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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/WhitelistAuditEntity.java
 * 
 * FILE NAME        	: WhitelistAuditEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;
import java.util.Date;

/**
 * 用来存储交互“外请司机白名单、外请车白名单”的数据库对应实体：SUC-104、SUC-750.
 *
 * @author 100847-foss-GaoPeng
 * @date 2012-10-11 下午4:36:16
 * @since
 * @version
 */
public class WhitelistAuditEntity extends BaseEntity {
	
    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 2960810636310549643L;
    
    /**
     * 司机姓名.
     */
    private String driverName;

    /**
     * 司机电话.
     */
    private String driverPhone;

    /**
     * 司机身份证.
     */
    private String driverIdCard;

    /**
     * 从业资格证.
     */
    private String qualification;

    /**
     * 驾驶证.
     */
    private String driverLicense;

    /**
     * 车牌号.
     */
    private String vehicleNo;

    /**
     * 车辆类型.
     */
    private String vehicleType;

    /**
     * 行驶证.
     */
    private String drivingLicense;

    /**
     * 行驶证到期日期.
     */
    private Date endTimeDrivingLic;

    /**
     * 营运证.
     */
    private String operatingLic;

    /**
     * 营运证到期日期.
     */
    private Date endTimeOperatingLic;

    /**
     * 保险卡.
     */
    private String insureCard;

    /**
     * 车主姓名.
     */
    private String contact;

    /**
     * 车主联系方式.
     */
    private String contactPhone;

    /**
     * 当前申请.
     */
    private String currentApplication;

    /**
     * 白名单状态.
     */
    private String status;

    /**
     * 申请人.
     */
    private String applyUser;
    
    /**
     * 申请人名称（扩展）.
     */
    private String applyUserName;

    /**
     * 申请时间.
     */
    private Date applyTime;

    /**
     * 审核人.
     */
    private String approveUser;

    /**
     * 审核时间.
     */
    private Date approveTime;

    /**
     * 审核备注.
     */
    private String auditNotes;
    
    /**
     * 是否启用.
     */
    private String active;

    /**
     * 白名单类型.
     */
    private String whitelistType;
    
    /**
     * 申请备注.
     */
    private String applyNotes;

    /**
     * 获取 司机姓名.
     *
     * @return  the driverName
     */
    public String getDriverName() {
        return driverName;
    }

    /**
     * 设置 司机姓名.
     *
     * @param driverName the driverName to set
     */
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    /**
     * 获取 司机电话.
     *
     * @return  the driverPhone
     */
    public String getDriverPhone() {
        return driverPhone;
    }
    
    /**
     * 获取 申请人名称（扩展）.
     *
     * @return  the applyUserName
     */
    public String getApplyUserName() {
        return applyUserName;
    }

    
    /**
     * 设置 申请人名称（扩展）.
     *
     * @param applyUserName the applyUserName to set
     */
    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    /**
     * 设置 司机电话.
     *
     * @param driverPhone the driverPhone to set
     */
    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    /**
     * 获取 司机身份证.
     *
     * @return  the driverIdCard
     */
    public String getDriverIdCard() {
        return driverIdCard;
    }

    /**
     * 设置 司机身份证.
     *
     * @param driverIdCard the driverIdCard to set
     */
    public void setDriverIdCard(String driverIdCard) {
        this.driverIdCard = driverIdCard;
    }

    /**
     * 获取 从业资格证.
     *
     * @return  the qualification
     */
    public String getQualification() {
        return qualification;
    }

    /**
     * 设置 从业资格证.
     *
     * @param qualification the qualification to set
     */
    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    /**
     * 获取 驾驶证.
     *
     * @return  the driverLicense
     */
    public String getDriverLicense() {
        return driverLicense;
    }

    /**
     * 设置 驾驶证.
     *
     * @param driverLicense the driverLicense to set
     */
    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }

    /**
     * 获取 车牌号.
     *
     * @return  the vehicleNo
     */
    public String getVehicleNo() {
        return vehicleNo;
    }

    /**
     * 设置 车牌号.
     *
     * @param vehicleNo the vehicleNo to set
     */
    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    /**
     * 获取 车辆类型.
     *
     * @return  the vehicleType
     */
    public String getVehicleType() {
        return vehicleType;
    }

    /**
     * 设置 车辆类型.
     *
     * @param vehicleType the vehicleType to set
     */
    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    /**
     * 获取 行驶证.
     *
     * @return  the drivingLicense
     */
    public String getDrivingLicense() {
        return drivingLicense;
    }

    /**
     * 设置 行驶证.
     *
     * @param drivingLicense the drivingLicense to set
     */
    public void setDrivingLicense(String drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    /**
     * 获取 行驶证到期日期.
     *
     * @return  the endTimeDrivingLic
     */
    public Date getEndTimeDrivingLic() {
        return endTimeDrivingLic;
    }

    /**
     * 设置 行驶证到期日期.
     *
     * @param endTimeDrivingLic the endTimeDrivingLic to set
     */
    public void setEndTimeDrivingLic(Date endTimeDrivingLic) {
        this.endTimeDrivingLic = endTimeDrivingLic;
    }

    /**
     * 获取 营运证.
     *
     * @return  the operatingLic
     */
    public String getOperatingLic() {
        return operatingLic;
    }

    /**
     * 设置 营运证.
     *
     * @param operatingLic the operatingLic to set
     */
    public void setOperatingLic(String operatingLic) {
        this.operatingLic = operatingLic;
    }

    /**
     * 获取 营运证到期日期.
     *
     * @return  the endTimeOperatingLic
     */
    public Date getEndTimeOperatingLic() {
        return endTimeOperatingLic;
    }

    /**
     * 设置 营运证到期日期.
     *
     * @param endTimeOperatingLic the endTimeOperatingLic to set
     */
    public void setEndTimeOperatingLic(Date endTimeOperatingLic) {
        this.endTimeOperatingLic = endTimeOperatingLic;
    }

    /**
     * 获取 保险卡.
     *
     * @return  the insureCard
     */
    public String getInsureCard() {
        return insureCard;
    }

    /**
     * 设置 保险卡.
     *
     * @param insureCard the insureCard to set
     */
    public void setInsureCard(String insureCard) {
        this.insureCard = insureCard;
    }

    /**
     * 获取 车主姓名.
     *
     * @return  the contact
     */
    public String getContact() {
        return contact;
    }

    /**
     * 设置 车主姓名.
     *
     * @param contact the contact to set
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    
    /**
     * 获取 车主联系方式.
     *
     * @return  the contactPhone
     */
    public String getContactPhone() {
        return contactPhone;
    }

    /**
     * 设置 车主联系方式.
     *
     * @param contactPhone the contactPhone to set
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    /**
     * 获取 当前申请.
     *
     * @return  the currentApplication
     */
    public String getCurrentApplication() {
        return currentApplication;
    }

    /**
     * 设置 当前申请.
     *
     * @param currentApplication the currentApplication to set
     */
    public void setCurrentApplication(String currentApplication) {
        this.currentApplication = currentApplication;
    }

    /**
     * 获取 白名单状态.
     *
     * @return  the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置 白名单状态.
     *
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * 获取 申请人.
     *
     * @return  the applyUser
     */
    public String getApplyUser() {
        return applyUser;
    }
    
    /**
     * 设置 申请人.
     *
     * @param applyUser the applyUser to set
     */
    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }
    
    /**
     * 获取 申请时间.
     *
     * @return  the applyTime
     */
    public Date getApplyTime() {
        return applyTime;
    }
    
    /**
     * 设置 申请时间.
     *
     * @param applyTime the applyTime to set
     */
    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }
    
    /**
     * 获取 审核人.
     *
     * @return  the approveUser
     */
    public String getApproveUser() {
        return approveUser;
    }

    /**
     * 设置 审核人.
     *
     * @param approveUser the approveUser to set
     */
    public void setApproveUser(String approveUser) {
        this.approveUser = approveUser;
    }

    /**
     * 获取 审核时间.
     *
     * @return  the approveTime
     */
    public Date getApproveTime() {
        return approveTime;
    }

    /**
     * 设置 审核时间.
     *
     * @param approveTime the approveTime to set
     */
    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    /**
     * 获取 审核备注.
     *
     * @return  the auditNotes
     */
    public String getAuditNotes() {
        return auditNotes;
    }
    
    /**
     * 设置 审核备注.
     *
     * @param auditNotes the auditNotes to set
     */
    public void setAuditNotes(String auditNotes) {
        this.auditNotes = auditNotes;
    }

    /**
     * 获取 是否启用.
     *
     * @return  the active
     */
    public String getActive() {
        return active;
    }

    /**
     * 设置 是否启用.
     *
     * @param active the active to set
     */
    public void setActive(String active) {
        this.active = active;
    }

    
    /**
     * 获取 白名单类型.
     *
     * @return  the whitelistType
     */
    public String getWhitelistType() {
        return whitelistType;
    }

    /**
     * 设置 白名单类型.
     *
     * @param whitelistType the whitelistType to set
     */
    public void setWhitelistType(String whitelistType) {
        this.whitelistType = whitelistType;
    }

    
    /**
     * 获取 申请备注.
     *
     * @return  the applyNotes
     */
    public String getApplyNotes() {
        return applyNotes;
    }
    
    /**
     * 设置 申请备注.
     *
     * @param applyNotes the applyNotes to set
     */
    public void setApplyNotes(String applyNotes) {
        this.applyNotes = applyNotes;
    }
}