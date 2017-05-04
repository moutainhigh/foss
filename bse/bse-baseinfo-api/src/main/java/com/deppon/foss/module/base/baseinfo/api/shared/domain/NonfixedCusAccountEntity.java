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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/NonfixedCusAccountEntity.java
 * 
 * FILE NAME        	: NonfixedCusAccountEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.math.BigInteger;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 临欠散客的开户银行账户信息
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-11-29 上午9:11:15
 * @since
 * @version
 */
public class NonfixedCusAccountEntity extends BaseEntity{
    
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 7851063792750222767L;
    
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
     * 账户性质 对公；对私两种.
     */
    private String accountNature;
    
    /**
     * 在CRM中fid.
     */
    private BigInteger crmId; 
    
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
     * 所属散客CRM_ID.
     */
    private BigInteger noncustCrmId;
    
    /**
     * 获取 所属散客CRM_ID.
     *
     * @return  the noncustCrmId
     */
    public BigInteger getNoncustCrmId() {
        return noncustCrmId;
    }
    
    /**
     * 设置 所属散客CRM_ID.
     *
     * @param noncustCrmId the noncustCrmId to set
     */
    public void setNoncustCrmId(BigInteger noncustCrmId) {
        this.noncustCrmId = noncustCrmId;
    }

    /**
     * 获取 在CRM中fid.
     *
     * @return  the crmId
     */
    public BigInteger getCrmId() {
        return crmId;
    }
    
    /**
     * 设置 在CRM中fid.
     *
     * @param crmId the crmId to set
     */
    public void setCrmId(BigInteger crmId) {
        this.crmId = crmId;
    }

    /**
     * 获取 账户性质 对公；对私两种.
     *
     * @return the 账户性质 对公；对私两种
     */
    public String getAccountNature() {
        return accountNature;
    }
    
    /**
     * 设置 账户性质 对公；对私两种.
     *
     * @param accountNature the new 账户性质 对公；对私两种
     */
    public void setAccountNature(String accountNature) {
        this.accountNature = accountNature;
    }

    /**
     * 获取 是否启用.
     *
     * @return the 是否启用
     */
    public String getActive() {
        return active;
    }
    
    /**
     * 设置 是否启用.
     *
     * @param active the new 是否启用
     */
    public void setActive(String active) {
        this.active = active;
    }

    /**
     * 获取 其他支行名称.
     *
     * @return the 其他支行名称
     */
    public String getOtherBranchBankName() {
        return otherBranchBankName;
    }

    /**
     * 设置 其他支行名称.
     *
     * @param otherBranchBankName the new 其他支行名称
     */
    public void setOtherBranchBankName(String otherBranchBankName) {
        this.otherBranchBankName = otherBranchBankName;
    }

    /**
     * 获取 开户账号.
     *
     * @return the 开户账号
     */
    public String getAccountNo() {
        return accountNo;
    }

    /**
     * 设置 开户账号.
     *
     * @param accountNo the new 开户账号
     */
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    /**
     * 获取 开户人姓名.
     *
     * @return the 开户人姓名
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * 设置 开户人姓名.
     *
     * @param accountName the new 开户人姓名
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * 获取 开户行城市编码.
     *
     * @return the 开户行城市编码
     */
    public String getCityCode() {
        return cityCode;
    }

    /**
     * 设置 开户行城市编码.
     *
     * @param cityCode the new 开户行城市编码
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    /**
     * 获取 开户行省份编码.
     *
     * @return the 开户行省份编码
     */
    public String getProvCode() {
        return provCode;
    }

    /**
     * 设置 开户行省份编码.
     *
     * @param provCode the new 开户行省份编码
     */
    public void setProvCode(String provCode) {
        this.provCode = provCode;
    }

    /**
     * 获取 开户行编码.
     *
     * @return the 开户行编码
     */
    public String getBankCode() {
        return bankCode;
    }

    /**
     * 设置 开户行编码.
     *
     * @param bankCode the new 开户行编码
     */
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    /**
     * 获取 手机号码.
     *
     * @return the 手机号码
     */
    public String getMobilePhone() {
        return mobilePhone;
    }

    /**
     * 设置 手机号码.
     *
     * @param mobilePhone the new 手机号码
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    /**
     * 获取 账号与客户关系.
     *
     * @return the 账号与客户关系
     */
    public String getCustomer() {
        return customer;
    }

    /**
     * 设置 账号与客户关系.
     *
     * @param customer the new 账号与客户关系
     */
    public void setCustomer(String customer) {
        this.customer = customer;
    }

    /**
     * 获取 是否默认账号.
     *
     * @return the 是否默认账号
     */
    public String getDefaultAccount() {
        return defaultAccount;
    }

    /**
     * 设置 是否默认账号.
     *
     * @param defaultAccount the new 是否默认账号
     */
    public void setDefaultAccount(String defaultAccount) {
        this.defaultAccount = defaultAccount;
    }

    /**
     * 获取 支行编码.
     *
     * @return the 支行编码
     */
    public String getBranchBankCode() {
        return branchBankCode;
    }

    /**
     * 设置 支行编码.
     *
     * @param branchBankCode the new 支行编码
     */
    public void setBranchBankCode(String branchBankCode) {
        this.branchBankCode = branchBankCode;
    }

    /**
     * 获取 备注.
     *
     * @return the 备注
     */
    public String getNotes() {
        return notes;
    }

    /**
     * 设置 备注.
     *
     * @param notes the new 备注
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    
    /**
     * 获取 开户行所在城市名称.
     *
     * @return the 开户行所在城市名称
     */
    public String getCityName() {
        return cityName;
    }

    
    /**
     * 设置 开户行所在城市名称.
     *
     * @param cityName the new 开户行所在城市名称
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    
    /**
     * 获取 开户行省份名称.
     *
     * @return the 开户行省份名称
     */
    public String getProvinceName() {
        return provinceName;
    }

    
    /**
     * 设置 开户行省份名称.
     *
     * @param provinceName the new 开户行省份名称
     */
    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    /**
     * 获取 开户行名称.
     *
     * @return the 开户行名称
     */
    public String getOpeningBankName() {
        return openingBankName;
    }

    
    /**
     * 设置 开户行名称.
     *
     * @param openingBankName the new 开户行名称
     */
    public void setOpeningBankName(String openingBankName) {
        this.openingBankName = openingBankName;
    }

    
    /**
     * 获取 支行名称.
     *
     * @return the 支行名称
     */
    public String getBranchBankName() {
        return branchBankName;
    }

    
    /**
     * 设置 支行名称.
     *
     * @param branchBankName the new 支行名称
     */
    public void setBranchBankName(String branchBankName) {
        this.branchBankName = branchBankName;
    }
}