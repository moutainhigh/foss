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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/LeasedDriverEntity.java
 * 
 * FILE NAME        	: LeasedDriverEntity.java
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
 * 用来存储交互“外请车司机”的数据库对应实体：SUC-211
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-11 下午5:06:45</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-11 下午5:06:45
 * @since
 * @version
 */
public class LeasedDriverEntity extends BaseEntity {
    /**
     *  Serial Version UID
     */
    private static final long serialVersionUID = -6503843582523912511L;

    //司机姓名
    private String driverName;

    //司机住址
    private String address;

    //司机电话
    private String driverPhone;

    //驾驶车辆车牌号
    private String vehicleNo;

    //司机身份证
    private String idCard;

    //亲属姓名
    private String relativeName;

    //亲属电话
    private String relativePhone;

    //驾驶证
    private String driverLicense;

    //从业资格证
    private String qualification;

    //驾照类型
    private String licenseType;

    //驾照签发日期
    private Date beginTime;

    //有效期限
    private BigDecimal expirationDate;

    //备注
    private String notes;

    //状态
    private String status;
    
    //扩展字段 状态 集合
    private List<String> statues;

    //是否启用
    private String active;

    /**
     * @return  the driverName
     */
    public String getDriverName() {
        return driverName;
    }
    
    /**
     * @param driverName the driverName to set
     */
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
    
    /**
     * @return  the address
     */
    public String getAddress() {
        return address;
    }
    
    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
    
    /**
     * @return  the driverPhone
     */
    public String getDriverPhone() {
        return driverPhone;
    }
    
    /**
     * @param driverPhone the driverPhone to set
     */
    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }
    
    /**
     * @return  the vehicleNo
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
     * @return  the idCard
     */
    public String getIdCard() {
        return idCard;
    }
    
    /**
     * @param idCard the idCard to set
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    /**
     * @return  the relativeName
     */
    public String getRelativeName() {
        return relativeName;
    }
    
    /**
     * @param relativeName the relativeName to set
     */
    public void setRelativeName(String relativeName) {
        this.relativeName = relativeName;
    }
    
    /**
     * @return  the relativePhone
     */
    public String getRelativePhone() {
        return relativePhone;
    }
    
    /**
     * @param relativePhone the relativePhone to set
     */
    public void setRelativePhone(String relativePhone) {
        this.relativePhone = relativePhone;
    }
    
    /**
     * @return  the driverLicense
     */
    public String getDriverLicense() {
        return driverLicense;
    }

    /**
     * @param driverLicense the driverLicense to set
     */
    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }
    
    /**
     * @return  the qualification
     */
    public String getQualification() {
        return qualification;
    }
    
    /**
     * @param qualification the qualification to set
     */
    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
    
    /**
     * @return  the licenseType
     */
    public String getLicenseType() {
        return licenseType;
    }
    
    /**
     * @param licenseType the licenseType to set
     */
    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }
    
    /**
     * @return  the beginTime
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
     * @return  the expirationDate
     */
    public BigDecimal getExpirationDate() {
        return expirationDate;
    }
    
    /**
     * @param expirationDate the expirationDate to set
     */
    public void setExpirationDate(BigDecimal expirationDate) {
        this.expirationDate = expirationDate;
    }
    
    /**
     * @return  the notes
     */
    public String getNotes() {
        return notes;
    }
    
    /**
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    /**
     * @return  the status
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
     * @return  the active
     */
    public String getActive() {
        return active;
    }
    
    /**
     * @param active the active to set
     */
    public void setActive(String active) {
        this.active = active;
    }

	
	public List<String> getStatues() {
		return statues;
	}

	
	public void setStatues(List<String> statues) {
		this.statues = statues;
	}
    
}
