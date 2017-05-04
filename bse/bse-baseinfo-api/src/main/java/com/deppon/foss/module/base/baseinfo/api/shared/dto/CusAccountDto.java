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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/CusAccountDto.java
 * 
 * FILE NAME        	: CusAccountDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.math.BigDecimal;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;

public class CusAccountDto extends CustomerEntity{

	/**
	*serialVersionUID
	*/
	private static final long serialVersionUID = -1161012326786442516L;
	//其他支行名称
    private String otherBranchBankName;
    //开户账号
    private String accountNo;
    //开户人姓名
    private String accountName;
    //开户行城市编码
    private String cityCode;
    //开户行省份编码
    private String provCode;
    //开户行编码
    private String bankCode;
    //手机号码
    private String mobilePhone;
    //账号与客户关系
    private String customer;
    //是否默认账号
    private String defaultAccount;
    //支行编码
    private String branchBankCode;
    //备注
    private String notes;
    //是否启用
    private String active;
    //虚拟编码
    private String virtualCode;
    //账户性质 对公；对私两种
    private String accountNature;
    //账户性质 对公；对私两种
    private String accountNatureName;
    //与客户信息是多对一关系
    private CustomerDto customerDto;
    //在CRM中fid
    private BigDecimal crmId; 
    //开户行所在城市名称
    private String cityName;
    //开户行省份名称
    private String provinceName;
    //开户行名称
    private String openingBankName;
    //支行名称
    private String branchBankName;
    //所属客户ID
    private BigDecimal belongCustom;
    //财务联系人名称
    private String financeLinkman;
    //账户用途
    private String accountUse;
	/**
	 *getter
	 */
	public String getOtherBranchBankName() {
		return otherBranchBankName;
	}
	/**
	 *setter
	 */
	public void setOtherBranchBankName(String otherBranchBankName) {
		this.otherBranchBankName = otherBranchBankName;
	}
	/**
	 *getter
	 */
	public String getAccountNo() {
		return accountNo;
	}
	/**
	 *setter
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	/**
	 *getter
	 */
	public String getAccountName() {
		return accountName;
	}
	/**
	 *setter
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	/**
	 *getter
	 */
	public String getCityCode() {
		return cityCode;
	}
	/**
	 *setter
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	/**
	 *getter
	 */
	public String getProvCode() {
		return provCode;
	}
	/**
	 *setter
	 */
	public void setProvCode(String provCode) {
		this.provCode = provCode;
	}
	/**
	 *getter
	 */
	public String getBankCode() {
		return bankCode;
	}
	/**
	 *setter
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	/**
	 *getter
	 */
	public String getMobilePhone() {
		return mobilePhone;
	}
	/**
	 *setter
	 */
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	/**
	 *getter
	 */
	public String getCustomer() {
		return customer;
	}
	/**
	 *setter
	 */
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	/**
	 *getter
	 */
	public String getDefaultAccount() {
		return defaultAccount;
	}
	/**
	 *setter
	 */
	public void setDefaultAccount(String defaultAccount) {
		this.defaultAccount = defaultAccount;
	}
	/**
	 *getter
	 */
	public String getBranchBankCode() {
		return branchBankCode;
	}
	/**
	 *setter
	 */
	public void setBranchBankCode(String branchBankCode) {
		this.branchBankCode = branchBankCode;
	}
	/**
	 *getter
	 */
	public String getNotes() {
		return notes;
	}
	/**
	 *setter
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
	/**
	 *getter
	 */
	@Override
	public String getActive() {
		return active;
	}
	/**
	 *setter
	 */
	@Override
	public void setActive(String active) {
		this.active = active;
	}
	/**
	 *getter
	 */
	@Override
	public String getVirtualCode() {
		return virtualCode;
	}
	/**
	 *setter
	 */
	@Override
	public void setVirtualCode(String virtualCode) {
		this.virtualCode = virtualCode;
	}
	/**
	 *getter
	 */
	public String getAccountNature() {
		return accountNature;
	}
	/**
	 *setter
	 */
	public void setAccountNature(String accountNature) {
		this.accountNature = accountNature;
	}
	/**
	 *getter
	 */
	public CustomerDto getCustomerDto() {
		return customerDto;
	}
	/**
	 *setter
	 */
	public void setCustomerDto(CustomerDto customerDto) {
		this.customerDto = customerDto;
	}
	/**
	 *getter
	 */
	public BigDecimal getCrmId() {
		return crmId;
	}
	/**
	 *setter
	 */
	public void setCrmId(BigDecimal crmId) {
		this.crmId = crmId;
	}
	/**
	 *getter
	 */
	@Override
	public String getCityName() {
		return cityName;
	}
	/**
	 *setter
	 */
	@Override
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	/**
	 *getter
	 */
	public String getProvinceName() {
		return provinceName;
	}
	/**
	 *setter
	 */
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	/**
	 *getter
	 */
	public String getOpeningBankName() {
		return openingBankName;
	}
	/**
	 *setter
	 */
	public void setOpeningBankName(String openingBankName) {
		this.openingBankName = openingBankName;
	}
	/**
	 *getter
	 */
	public String getBranchBankName() {
		return branchBankName;
	}
	/**
	 *setter
	 */
	public void setBranchBankName(String branchBankName) {
		this.branchBankName = branchBankName;
	}
	/**
	 *getter
	 */
	public BigDecimal getBelongCustom() {
		return belongCustom;
	}
	/**
	 *setter
	 */
	public void setBelongCustom(BigDecimal belongCustom) {
		this.belongCustom = belongCustom;
	}
	/**
	 *getter
	 */
	public String getFinanceLinkman() {
		return financeLinkman;
	}
	/**
	 *setter
	 */
	public void setFinanceLinkman(String financeLinkman) {
		this.financeLinkman = financeLinkman;
	}
	/**
	 *getter
	 */
	public String getAccountUse() {
		return accountUse;
	}
	/**
	 *setter
	 */
	public void setAccountUse(String accountUse) {
		this.accountUse = accountUse;
	}
	public String getAccountNatureName() {
		return accountNatureName;
	}
	public void setAccountNatureName(String accountNatureName) {
		this.accountNatureName = accountNatureName;
	}
	
	
}
