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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/ContactAddressEntity.java
 * 
 * FILE NAME        	: ContactAddressEntity.java
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
 * 联系人接送货地址实体类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-16 下午4:45:45 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-16 下午4:45:45
 * @since
 * @version
 */
public class ContactAddressEntity extends BaseEntity{
    
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -1843264586647463287L;
    
    /**
     * 接送货地址ID.
     */
    private BigDecimal serviceAddressId;
    
    /**
     * 联系人ID.
     */
    private BigDecimal contact;
    
    /**
     * 地址类型.
     */
    private String addressType;
    
    /**
     * 地址.
     */
    private String contactAddress;
    
    /**
     * 发票要求.
     */
    private String billDemand;
    
    /**
     * 付款方式.
     */
    private String chargeType;
    
    /**
     * 其他要求.
     */
    private String otherDemand;
    
    /**
     * 是否有停车费用.
     */
    private String parkingFee;
    
    /**
     * 是否主地址.
     */
    private String mainAddress;
    
    /**
     * 状态.
     */
    private String status;
    
    /**
     * 偏好起始时间.
     */
    private Date beginTime;
    
    /**
     * 偏好结束时间.
     */
    private Date endTime;
    
    /**
     * 是否送货上楼.
     */
    private String deliveryUpstairs;
    
    /**
     * 是否启用.
     */
    private String active;
    
    /**
     * 在CRM中fid.
     */
    private BigDecimal crmId; 
    
    /**
     * 联系人-地址（偏好地址）多对一客户联系人.
     */
    private ContactEntity contactEntity;
    
    /**
     * 联系人-地址（偏好地址）一对一客户接送货地址.
     */
    private CusAddressEntity addressEntity;
    
    /**
     * 获取 接送货地址ID.
     *
     * @return  the serviceAddressId
     */
    public BigDecimal getServiceAddressId() {
        return serviceAddressId;
    }
    
    /**
     * 设置 接送货地址ID.
     *
     * @param serviceAddressId the serviceAddressId to set
     */
    public void setServiceAddressId(BigDecimal serviceAddressId) {
        this.serviceAddressId = serviceAddressId;
    }
    
    /**
     * 获取 联系人ID.
     *
     * @return  the contact
     */
    public BigDecimal getContact() {
        return contact;
    }
    
    /**
     * 设置 联系人ID.
     *
     * @param contact the contact to set
     */
    public void setContact(BigDecimal contact) {
        this.contact = contact;
    }
    
    /**
     * 获取 地址类型.
     *
     * @return  the addressType
     */
    public String getAddressType() {
        return addressType;
    }
    
    /**
     * 设置 地址类型.
     *
     * @param addressType the addressType to set
     */
    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }
    
    /**
     * 获取 地址.
     *
     * @return  the contactAddress
     */
    public String getContactAddress() {
        return contactAddress;
    }
    
    /**
     * 设置 地址.
     *
     * @param contactAddress the contactAddress to set
     */
    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }
    
    /**
     * 获取 发票要求.
     *
     * @return  the billDemand
     */
    public String getBillDemand() {
        return billDemand;
    }
    
    /**
     * 设置 发票要求.
     *
     * @param billDemand the billDemand to set
     */
    public void setBillDemand(String billDemand) {
        this.billDemand = billDemand;
    }
    
    /**
     * 获取 付款方式.
     *
     * @return  the chargeType
     */
    public String getChargeType() {
        return chargeType;
    }
    
    /**
     * 设置 付款方式.
     *
     * @param chargeType the chargeType to set
     */
    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }
    
    /**
     * 获取 其他要求.
     *
     * @return  the otherDemand
     */
    public String getOtherDemand() {
        return otherDemand;
    }
    
    /**
     * 设置 其他要求.
     *
     * @param otherDemand the otherDemand to set
     */
    public void setOtherDemand(String otherDemand) {
        this.otherDemand = otherDemand;
    }
    
    /**
     * 获取 是否有停车费用.
     *
     * @return  the parkingFee
     */
    public String getParkingFee() {
        return parkingFee;
    }

    
    /**
     * 设置 是否有停车费用.
     *
     * @param parkingFee the parkingFee to set
     */
    public void setParkingFee(String parkingFee) {
        this.parkingFee = parkingFee;
    }

    /**
     * 获取 是否主地址.
     *
     * @return  the mainAddress
     */
    public String getMainAddress() {
        return mainAddress;
    }
    
    /**
     * 设置 是否主地址.
     *
     * @param mainAddress the mainAddress to set
     */
    public void setMainAddress(String mainAddress) {
        this.mainAddress = mainAddress;
    }
    
    /**
     * 获取 状态.
     *
     * @return  the status
     */
    public String getStatus() {
        return status;
    }
    
    /**
     * 设置 状态.
     *
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * 获取 偏好起始时间.
     *
     * @return  the beginTime
     */
    public Date getBeginTime() {
        return beginTime;
    }
    
    /**
     * 设置 偏好起始时间.
     *
     * @param beginTime the beginTime to set
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }
    
    /**
     * 获取 偏好结束时间.
     *
     * @return  the endTime
     */
    public Date getEndTime() {
        return endTime;
    }
    
    /**
     * 设置 偏好结束时间.
     *
     * @param endTime the endTime to set
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
    /**
     * 获取 是否送货上楼.
     *
     * @return  the deliveryUpstairs
     */
    public String getDeliveryUpstairs() {
        return deliveryUpstairs;
    }
    
    /**
     * 设置 是否送货上楼.
     *
     * @param deliveryUpstairs the deliveryUpstairs to set
     */
    public void setDeliveryUpstairs(String deliveryUpstairs) {
        this.deliveryUpstairs = deliveryUpstairs;
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
     * 获取 在CRM中fid.
     *
     * @return  the crmId
     */
    public BigDecimal getCrmId() {
        return crmId;
    }
    
    /**
     * 设置 在CRM中fid.
     *
     * @param crmId the crmId to set
     */
    public void setCrmId(BigDecimal crmId) {
        this.crmId = crmId;
    }
    
    /**
     * 获取 联系人-地址（偏好地址）多对一客户联系人.
     *
     * @return  the contactEntity
     */
    public ContactEntity getContactEntity() {
        return contactEntity;
    }
    
    /**
     * 设置 联系人-地址（偏好地址）多对一客户联系人.
     *
     * @param contactEntity the contactEntity to set
     */
    public void setContactEntity(ContactEntity contactEntity) {
        this.contactEntity = contactEntity;
    }
    
    /**
     * 获取 联系人-地址（偏好地址）一对一客户接送货地址.
     *
     * @return  the addressEntity
     */
    public CusAddressEntity getAddressEntity() {
        return addressEntity;
    }
    
    /**
     * 设置 联系人-地址（偏好地址）一对一客户接送货地址.
     *
     * @param addressEntity the addressEntity to set
     */
    public void setAddressEntity(CusAddressEntity addressEntity) {
        this.addressEntity = addressEntity;
    }
    
    
}
