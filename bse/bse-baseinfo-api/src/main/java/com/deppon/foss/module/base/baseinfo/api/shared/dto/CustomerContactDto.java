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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/CustomerContactDto.java
 * 
 * FILE NAME        	: CustomerContactDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 客户以及客户联系人信息Dto
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-6 下午5:10:33 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-6 下午5:10:33
 * @since
 * @version
 */
public class CustomerContactDto implements Serializable {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -8745051242785421655L;
    
    /**
     * 客户ID.
     */
    private String custId;
    
    /**
     * 客户名称.
     */
    private String custName;
    
    /**
     * 客户编码.
     */
    private String custCode;
    
    /**
     * 信用额度.
     */
    private BigDecimal creditAmount;
    
    /**
     * 结算方式.
     */
    private String chargeMode;
    
    /**
     * 联系人名称.
     */
    private String contactName;
    
    /**
     * 联系人ID.
     */
    private String contactId;
    
    /**
     * 联系人编码.
     */
    private String contactCode;
    
    /**
     * 联系人手机.
     */
    private String mobilePhone;
    
    /**
     * 办公电话.
     */
    private String officePhone;
    
    /**
     * 联系人地址.
     */
    private String address;
    
    /**
     * 身份证号.
     */
    private String idCard;
    
    /**
     * 客户所属部门名称
     */
    private String deptName;
    
    /**
     * 获取 客户ID.
     *
     * @return  the custId
     */
    public String getCustId() {
        return custId;
    }
    
    /**
     * 设置 客户ID.
     *
     * @param custId the custId to set
     */
    public void setCustId(String custId) {
        this.custId = custId;
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
     * 获取 信用额度.
     *
     * @return  the creditAmount
     */
    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    
    /**
     * 设置 信用额度.
     *
     * @param creditAmount the creditAmount to set
     */
    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
    }

    /**
     * 获取 结算方式.
     *
     * @return  the chargeMode
     */
    public String getChargeMode() {
        return chargeMode;
    }
    
    /**
     * 设置 结算方式.
     *
     * @param chargeMode the chargeMode to set
     */
    public void setChargeMode(String chargeMode) {
        this.chargeMode = chargeMode;
    }
    
    /**
     * 获取 联系人名称.
     *
     * @return  the contactName
     */
    public String getContactName() {
        return contactName;
    }
    
    /**
     * 设置 联系人名称.
     *
     * @param contactName the contactName to set
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    
    /**
     * 获取 联系人ID.
     *
     * @return  the contactId
     */
    public String getContactId() {
        return contactId;
    }
    
    /**
     * 设置 联系人ID.
     *
     * @param contactId the contactId to set
     */
    public void setContactId(String contactId) {
        this.contactId = contactId;
    }
    
    /**
     * 获取 联系人手机.
     *
     * @return  the mobilePhone
     */
    public String getMobilePhone() {
        return mobilePhone;
    }
    
    /**
     * 设置 联系人手机.
     *
     * @param mobilePhone the mobilePhone to set
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    
    /**
     * 获取 办公电话.
     *
     * @return  the officePhone
     */
    public String getOfficePhone() {
        return officePhone;
    }
    
    /**
     * 设置 办公电话.
     *
     * @param officePhone the officePhone to set
     */
    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }
    
    /**
     * 获取 联系人地址.
     *
     * @return  the address
     */
    public String getAddress() {
        return address;
    }
    
    /**
     * 设置 联系人地址.
     *
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
    
    /**
     * 获取 身份证号.
     *
     * @return  the idCard
     */
    public String getIdCard() {
        return idCard;
    }
    
    /**
     * 设置 身份证号.
     *
     * @param idCard the idCard to set
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

	
	/**
	 * 获取 联系人编码.
	 *
	 * @return the 联系人编码
	 */
	public String getContactCode() {
		return contactCode;
	}

	
	/**
	 * 设置 联系人编码.
	 *
	 * @param contactCode the new 联系人编码
	 */
	public void setContactCode(String contactCode) {
		this.contactCode = contactCode;
	}

	/**
	 * 获得客户所属部门名称
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * 设置客户所属部门名称
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
    
	
}
