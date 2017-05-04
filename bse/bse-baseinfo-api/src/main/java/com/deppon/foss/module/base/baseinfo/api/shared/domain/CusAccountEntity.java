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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/CusAccountEntity.java
 * 
 * FILE NAME        	: CusAccountEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;

/**
 * 客户开户银行实体类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-16 下午4:31:18 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-16 下午4:31:18
 * @since
 * @version
 */
public class CusAccountEntity extends BaseEntity{
    
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 5014547529742145888L;
    
    /**
     * 其他支行名称.
     */
    private String otherBranchBankName;
    
    /**
     * 开户账号.
     */
    private String accountNo;
    
    /**
     * 开户人姓名.
     */
    private String accountName;
    
    /**
     * 开户行城市编码.
     */
    private String cityCode;
    
    /**
     * 开户行省份编码.
     */
    private String provCode;
    
    /**
     * 开户行编码.
     */
    private String bankCode;
    
    /**
     * 手机号码.
     */
    private String mobilePhone;
    
    /**
     * 账号与客户关系.
     */
    private String customer;
    
    /**
     * 是否默认账号.
     */
    private String defaultAccount;
    
    /**
     * 支行编码.
     */
    private String branchBankCode;
    
    /**
     * 备注.
     */
    private String notes;
    
    /**
     * 是否启用.
     */
    private String active;
    
    /**
     * 虚拟编码.
     */
    private String virtualCode;
    
    /**
     * 账户性质 对公；对私两种.
     */
    private String accountNature;
    
    /**
     * 与客户信息是多对一关系.
     */
    private CustomerDto customerDto;
    
    /**
     * 在CRM中fid.
     */
    private BigDecimal crmId; 
    
    /**
     * 开户行所在城市名称.
     */
    private String cityName;
    
    /**
     * 开户行省份名称.
     */
    private String provinceName;
    
    /**
     * 开户行名称.
     */
    private String openingBankName;
    
    /**
     * 支行名称.
     */
    private String branchBankName;
    
    /**
     * 所属客户ID.
     */
    private BigDecimal belongCustom;
    
    /**
     * 财务联系人名称.
     */
    private String financeLinkman;
    
    /**
     * 账户用途.
     */
    private String accountUse;
    
    /**
     * 获取 其他支行名称.
     *
     * @return  the otherBranchBankName
     */
    public String getOtherBranchBankName() {
        return otherBranchBankName;
    }
    
    /**
     * 设置 其他支行名称.
     *
     * @param otherBranchBankName the otherBranchBankName to set
     */
    public void setOtherBranchBankName(String otherBranchBankName) {
        this.otherBranchBankName = otherBranchBankName;
    }
    
    /**
     * 获取 开户账号.
     *
     * @return  the accountNo
     */
    public String getAccountNo() {
        return accountNo;
    }
    
    /**
     * 设置 开户账号.
     *
     * @param accountNo the accountNo to set
     */
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
    
    /**
     * 获取 开户人姓名.
     *
     * @return  the accountName
     */
    public String getAccountName() {
        return accountName;
    }
    
    /**
     * 设置 开户人姓名.
     *
     * @param accountName the accountName to set
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    
    /**
     * 获取 开户行城市编码.
     *
     * @return  the cityCode
     */
    public String getCityCode() {
        return cityCode;
    }
    
    /**
     * 设置 开户行城市编码.
     *
     * @param cityCode the cityCode to set
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    
    /**
     * 获取 开户行省份编码.
     *
     * @return  the provCode
     */
    public String getProvCode() {
        return provCode;
    }
    
    /**
     * 设置 开户行省份编码.
     *
     * @param provCode the provCode to set
     */
    public void setProvCode(String provCode) {
        this.provCode = provCode;
    }
    
    /**
     * 获取 开户行编码.
     *
     * @return  the bankCode
     */
    public String getBankCode() {
        return bankCode;
    }
    
    /**
     * 设置 开户行编码.
     *
     * @param bankCode the bankCode to set
     */
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
    
    /**
     * 获取 手机号码.
     *
     * @return  the mobilePhone
     */
    public String getMobilePhone() {
        return mobilePhone;
    }
    
    /**
     * 设置 手机号码.
     *
     * @param mobilePhone the mobilePhone to set
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    
    /**
     * 获取 账号与客户关系.
     *
     * @return  the customer
     */
    public String getCustomer() {
        return customer;
    }
    
    /**
     * 设置 账号与客户关系.
     *
     * @param customer the customer to set
     */
    public void setCustomer(String customer) {
        this.customer = customer;
    }
    
    /**
     * 获取 是否默认账号.
     *
     * @return  the defaultAccount
     */
    public String getDefaultAccount() {
        return defaultAccount;
    }
    
    /**
     * 设置 是否默认账号.
     *
     * @param defaultAccount the defaultAccount to set
     */
    public void setDefaultAccount(String defaultAccount) {
        this.defaultAccount = defaultAccount;
    }
    
    /**
     * 获取 支行编码.
     *
     * @return  the branchBankCode
     */
    public String getBranchBankCode() {
        return branchBankCode;
    }
    
    /**
     * 设置 支行编码.
     *
     * @param branchBankCode the branchBankCode to set
     */
    public void setBranchBankCode(String branchBankCode) {
        this.branchBankCode = branchBankCode;
    }
    
    /**
     * 获取 备注.
     *
     * @return  the notes
     */
    public String getNotes() {
        return notes;
    }
    
    /**
     * 设置 备注.
     *
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
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
     * 获取 虚拟编码.
     *
     * @return  the virtualCode
     */
    public String getVirtualCode() {
        return virtualCode;
    }
    
    /**
     * 设置 虚拟编码.
     *
     * @param virtualCode the virtualCode to set
     */
    public void setVirtualCode(String virtualCode) {
        this.virtualCode = virtualCode;
    }
    
    /**
     * 获取 账户性质 对公；对私两种.
     *
     * @return  the accountNature
     */
    public String getAccountNature() {
        return accountNature;
    }
    
    /**
     * 设置 账户性质 对公；对私两种.
     *
     * @param accountNature the accountNature to set
     */
    public void setAccountNature(String accountNature) {
        this.accountNature = accountNature;
    }
    
    /**
     * 获取 与客户信息是多对一关系.
     *
     * @return  the customerDto
     */
    public CustomerDto getCustomerDto() {
        return customerDto;
    }
    
    /**
     * 设置 与客户信息是多对一关系.
     *
     * @param customerDto the customerDto to set
     */
    public void setCustomerDto(CustomerDto customerDto) {
        this.customerDto = customerDto;
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
     * 获取 开户行所在城市名称.
     *
     * @return  the cityName
     */
    public String getCityName() {
        return cityName;
    }
    
    /**
     * 设置 开户行所在城市名称.
     *
     * @param cityName the cityName to set
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    
    /**
     * 获取 开户行省份名称.
     *
     * @return  the provinceName
     */
    public String getProvinceName() {
        return provinceName;
    }
    
    /**
     * 设置 开户行省份名称.
     *
     * @param provinceName the provinceName to set
     */
    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }
    
    /**
     * 获取 开户行名称.
     *
     * @return  the openingBankName
     */
    public String getOpeningBankName() {
        return openingBankName;
    }
    
    /**
     * 设置 开户行名称.
     *
     * @param openingBankName the openingBankName to set
     */
    public void setOpeningBankName(String openingBankName) {
        this.openingBankName = openingBankName;
    }
    
    /**
     * 获取 支行名称.
     *
     * @return  the branchBankName
     */
    public String getBranchBankName() {
        return branchBankName;
    }
    
    /**
     * 设置 支行名称.
     *
     * @param branchBankName the branchBankName to set
     */
    public void setBranchBankName(String branchBankName) {
        this.branchBankName = branchBankName;
    }
    
    /**
     * 获取 所属客户ID.
     *
     * @return  the belongCustom
     */
    public BigDecimal getBelongCustom() {
        return belongCustom;
    }
    
    /**
     * 设置 所属客户ID.
     *
     * @param belongCustom the belongCustom to set
     */
    public void setBelongCustom(BigDecimal belongCustom) {
        this.belongCustom = belongCustom;
    }
    
    /**
     * 获取 财务联系人名称.
     *
     * @return  the financeLinkman
     */
    public String getFinanceLinkman() {
        return financeLinkman;
    }
    
    /**
     * 设置 财务联系人名称.
     *
     * @param financeLinkman the financeLinkman to set
     */
    public void setFinanceLinkman(String financeLinkman) {
        this.financeLinkman = financeLinkman;
    }
    
    /**
     * 获取 账户用途.
     *
     * @return  the accountUse
     */
    public String getAccountUse() {
        return accountUse;
    }
    
    /**
     * 设置 账户用途.
     *
     * @param accountUse the accountUse to set
     */
    public void setAccountUse(String accountUse) {
        this.accountUse = accountUse;
    }
    
    
}
