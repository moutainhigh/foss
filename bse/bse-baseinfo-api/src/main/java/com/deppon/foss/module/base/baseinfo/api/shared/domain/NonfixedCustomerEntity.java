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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/NonfixedCustomerEntity.java
 * 
 * FILE NAME        	: NonfixedCustomerEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 临欠散客实体类.
 *
 * @author 094463-foss-xieyantao
 * @date 2012-10-16 下午4:12:44
 * @since
 * @version
 */
public class NonfixedCustomerEntity extends BaseEntity {
    
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -5654260507373844927L;
    
    /**
     * 散客CRM_ID.
     */
    private BigInteger crmId;
    
    /**
     * 散客类型.
     */
    private String type;
    
    /**
     * 临欠额度.
     */
    private BigDecimal tempArrears;
    
    /**
     * 是否启用.
     */
    private String active;
    
    /**
     * 客户名称.
     */
    private String custName;
    
    /**
     * 客户编码.
     */
    private String custCode;
    
    /**
     * 会员号.
     */
    private String memberNum;
    
    /**
     * 联系人名称.
     */
    private String linkmanName;
    
    /**
     * 手机.
     */
    private String mobilePhone;
    
    /**
     * 电话.
     */
    private String telephone;
    
    /**
     * 联系人地址.
     */
    private String contactAddress;
    
    /**
     * 所属部门标杆编码.
     */
    private String unifiedCode;
    
    /**
     * 是否财务作废.
     */
    private String isDelete;
    
    /**
     * 获取 所属部门标杆编码.
     *
     * @return  the unifiedCode
     */
    public String getUnifiedCode() {
        return unifiedCode;
    }
    
    /**
     * 设置 所属部门标杆编码.
     *
     * @param unifiedCode the unifiedCode to set
     */
    public void setUnifiedCode(String unifiedCode) {
        this.unifiedCode = unifiedCode;
    }
    
    /**
     * 获取 是否财务作废.
     *
     * @return  the isDelete
     */
    public String getIsDelete() {
        return isDelete;
    }

    
    /**
     * 设置 是否财务作废.
     *
     * @param isDelete the isDelete to set
     */
    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * 获取 散客CRM_ID.
     *
     * @return  the crmId
     */
    public BigInteger getCrmId() {
        return crmId;
    }

    
    /**
     * 设置 散客CRM_ID.
     *
     * @param crmId the crmId to set
     */
    public void setCrmId(BigInteger crmId) {
        this.crmId = crmId;
    }

    /**
     * 获取 散客类型.
     *
     * @return  the type
     */
    public String getType() {
        return type;
    }
    
    /**
     * 设置 散客类型.
     *
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /**
     * 获取 临欠额度.
     *
     * @return  the tempArrears
     */
    public BigDecimal getTempArrears() {
        return tempArrears;
    }

    
    /**
     * 设置 临欠额度.
     *
     * @param tempArrears the tempArrears to set
     */
    public void setTempArrears(BigDecimal tempArrears) {
        this.tempArrears = tempArrears;
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
     * 获取 客户名称.
     *
     * @return  the custName
     */
    public String getCustName() {
        return custName;
    }
    
    /**
     * 设置 客户名称.
     *
     * @param custName the custName to set
     */
    public void setCustName(String custName) {
        this.custName = custName;
    }
    
    /**
     * 获取 客户编码.
     *
     * @return  the custCode
     */
    public String getCustCode() {
        return custCode;
    }
    
    /**
     * 设置 客户编码.
     *
     * @param custCode the custCode to set
     */
    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }
    
    /**
     * 获取 会员号.
     *
     * @return  the memberNum
     */
    public String getMemberNum() {
        return memberNum;
    }
    
    /**
     * 设置 会员号.
     *
     * @param memberNum the memberNum to set
     */
    public void setMemberNum(String memberNum) {
        this.memberNum = memberNum;
    }
    
    /**
     * 获取 联系人名称.
     *
     * @return  the linkmanName
     */
    public String getLinkmanName() {
        return linkmanName;
    }
    
    /**
     * 设置 联系人名称.
     *
     * @param linkmanName the linkmanName to set
     */
    public void setLinkmanName(String linkmanName) {
        this.linkmanName = linkmanName;
    }
    
    /**
     * 获取 手机.
     *
     * @return  the mobilePhone
     */
    public String getMobilePhone() {
        return mobilePhone;
    }
    
    /**
     * 设置 手机.
     *
     * @param mobilePhone the mobilePhone to set
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    
    /**
     * 获取 电话.
     *
     * @return  the telephone
     */
    public String getTelephone() {
        return telephone;
    }
    
    /**
     * 设置 电话.
     *
     * @param telephone the telephone to set
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    /**
     * 获取 联系人地址.
     *
     * @return  the contactAddress
     */
    public String getContactAddress() {
        return contactAddress;
    }
    
    /**
     * 设置 联系人地址.
     *
     * @param contactAddress the contactAddress to set
     */
    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }
}