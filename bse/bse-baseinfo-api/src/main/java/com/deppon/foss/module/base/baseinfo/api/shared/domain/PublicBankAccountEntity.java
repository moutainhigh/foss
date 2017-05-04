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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/PublicBankAccountEntity.java
 * 
 * FILE NAME        	: PublicBankAccountEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 对公银行账号
 * 
 * @author 087584-foss-lijun
 * @date 2012-10-20 下午2:35:52
 */
public class PublicBankAccountEntity  extends BaseEntity {
	
	/**
	 * 序列ID
	 */
    private static final long serialVersionUID = -3967231355140352109L;

    /**
    *银行账号
    */	
    private String bankAcc;

    /**
    *银行开户名
    */	
    private String bankAccName;

    /**
    *部门标杆编码
    */	
    private String deptCd;

    /**
    *银行编码
    */	
    private String bankCd;

    /**
    *银行名称
    */	
    private String bankName;

    /**
    *支行编码
    */	
    private String subbranchCd;

    /**
    *支行名称
    */	
    private String subbranchName;

    /**
    *省份编码
    */	
    private String provCd;

    /**
    *省份名称
    */	
    private String provName;

    /**
    *城市编码
    */	
    private String cityCd;

    /**
    *城市名称
    */	
    private String cityName;

    /**
    * 账号状态
    */	
    private String accountStatus;

    /**
    * 财务自助ID
    */	
    private String fid;
    
    /**
    * 账号类别
    */	
    private String accSort;
    
    /**
    *是否启用
    */	
    private String active;

	/**
	 * @return bankAcc
	 */
	public String getBankAcc() {
		return bankAcc;
	}

	/**
	 * @param  bankAcc  
	 */
	public void setBankAcc(String bankAcc) {
		this.bankAcc = bankAcc;
	}

	/**
	 * @return bankAccName
	 */
	public String getBankAccName() {
		return bankAccName;
	}

	/**
	 * @param  bankAccName  
	 */
	public void setBankAccName(String bankAccName) {
		this.bankAccName = bankAccName;
	}

	/**
	 * @return deptCd
	 */
	public String getDeptCd() {
		return deptCd;
	}

	/**
	 * @param  deptCd  
	 */
	public void setDeptCd(String deptCd) {
		this.deptCd = deptCd;
	}

	/**
	 * @return bankCd
	 */
	public String getBankCd() {
		return bankCd;
	}

	/**
	 * @param  bankCd  
	 */
	public void setBankCd(String bankCd) {
		this.bankCd = bankCd;
	}

	/**
	 * @return bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param  bankName  
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return subbranchCd
	 */
	public String getSubbranchCd() {
		return subbranchCd;
	}

	/**
	 * @param  subbranchCd  
	 */
	public void setSubbranchCd(String subbranchCd) {
		this.subbranchCd = subbranchCd;
	}

	/**
	 * @return subbranchName
	 */
	public String getSubbranchName() {
		return subbranchName;
	}

	/**
	 * @param  subbranchName  
	 */
	public void setSubbranchName(String subbranchName) {
		this.subbranchName = subbranchName;
	}

	/**
	 * @return provCd
	 */
	public String getProvCd() {
		return provCd;
	}

	/**
	 * @param  provCd  
	 */
	public void setProvCd(String provCd) {
		this.provCd = provCd;
	}

	/**
	 * @return provName
	 */
	public String getProvName() {
		return provName;
	}

	/**
	 * @param  provName  
	 */
	public void setProvName(String provName) {
		this.provName = provName;
	}

	/**
	 * @return cityCd
	 */
	public String getCityCd() {
		return cityCd;
	}

	/**
	 * @param  cityCd  
	 */
	public void setCityCd(String cityCd) {
		this.cityCd = cityCd;
	}

	/**
	 * @return cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param  cityName  
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return accountStatus
	 */
	public String getAccountStatus() {
		return accountStatus;
	}

	/**
	 * @param  accountStatus  
	 */
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	/**
	 * @return fid
	 */
	public String getFid() {
		return fid;
	}

	/**
	 * @param  fid  
	 */
	public void setFid(String fid) {
		this.fid = fid;
	}

	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param  active  
	 */
	public void setActive(String active) {
		this.active = active;
	}

	public String getAccSort() {
		return accSort;
	}

	public void setAccSort(String accSort) {
		this.accSort = accSort;
	}
	
	
}