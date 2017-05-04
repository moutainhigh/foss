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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/PayeeInfoEntity.java
 * 
 * FILE NAME        	: PayeeInfoEntity.java
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
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;


/**
 * 收款方信息实体
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-12-14 上午11:14:21
 * @since
 * @version
 */
public class PayeeInfoEntity extends BaseEntity {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -315742498282492334L;
    
    /**
     * 收款方编码.
     */
    private String payeeNo;
    
    /**
     * 录入人工号.
     */
    private String operatorId;
    
    /**
     * 账户性质, 1-德邦子公司账户; 2-收银员卡; 3-内部员工账户; 4-公司外部账户.
     */
    private String accountType;
    /**
     * 账户性质, 1-德邦子公司账户; 2-收银员卡; 3-内部员工账户; 4-公司外部账户.
     */
    private List<String> accountTypes;
    
    /**
     * 开户人姓名， 可能是单位，可能是个人.
     */
    private String beneficiaryName;
    
    /**
     * 查询条件：是否查询合伙人银行账户，null不查询，Y 只查询合伙人银行账户，N 德邦内部账户和合伙人账号都查询
     */
    private String  isOnlyPartnerAccount;
    public String getIsOnlyPartnerAccount() {
		return isOnlyPartnerAccount;
	}
	public void setIsOnlyPartnerAccount(String isOnlyPartnerAccount) {
		this.isOnlyPartnerAccount = isOnlyPartnerAccount;
	}

	/**
     * 银行帐号.
     */
    private String accountNo;
    
    /**
     * 开户行编码.
     */
    private String accountbankCode;
    
    /**
     * 开户行名称(扩展).
     */
    private String accountbankName;
    
    /**
     * 开户支行编码.
     */
    private String accountbranchbankCode;
    
    /**
     * 开户支行名称(扩展).
     */
    private String accountbranchbankName;
    
    /**
     * 开户行所在城市编码.
     */
    private String accountbankcityCode;
    
    /**
     * 开户行所在城市名称(扩展).
     */
    private String accountbankcityName;
    
    /**
     * 开户行所在省份编码.
     */
    private String accountbankstateCode;
    
    /**
     * 开户行所在省份编码(扩展).
     */
    private String accountbankstateName;
    
    /**
     * 是否有效（Y/N）.
     */
    private String active;
    
    /**
     * 精确查询
     */
    private String exactQuery;
    
    /**
     * 账号类别
     */   
    private String accSort;
    /**
     * 获取精确查询
     * @return
     */
    public String getExactQuery() {
		return exactQuery;
	}
    /**
     * 设置精确查询
     * @return
     */
	public void setExactQuery(String exactQuery) {
		this.exactQuery = exactQuery;
	}

	/**
     * 获取 收款方编码.
     *
     * @return  the payeeNo
     */
    public String getPayeeNo() {
        return payeeNo;
    }
    
    /**
     * 设置 收款方编码.
     *
     * @param payeeNo the payeeNo to set
     */
    public void setPayeeNo(String payeeNo) {
        this.payeeNo = payeeNo;
    }
    
    /**
     * 获取 录入人工号.
     *
     * @return  the operatorId
     */
    public String getOperatorId() {
        return operatorId;
    }
    
    /**
     * 设置 录入人工号.
     *
     * @param operatorId the operatorId to set
     */
    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }
    
    /**
     * 获取 账户性质, 1-德邦子公司账户; 2-收银员卡; 3-内部员工账户; 4-公司外部账户.
     *
     * @return  the accountType
     */
    public String getAccountType() {
        return accountType;
    }
    
    /**
     * 设置 账户性质, 1-德邦子公司账户; 2-收银员卡; 3-内部员工账户; 4-公司外部账户.
     *
     * @param accountType the accountType to set
     */
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    
    /**
     * 获取 开户人姓名， 可能是单位，可能是个人.
     *
     * @return  the beneficiaryName
     */
    public String getBeneficiaryName() {
        return beneficiaryName;
    }
    
    /**
     * 设置 开户人姓名， 可能是单位，可能是个人.
     *
     * @param beneficiaryName the beneficiaryName to set
     */
    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }
    
    /**
     * 获取 银行帐号.
     *
     * @return  the accountNo
     */
    public String getAccountNo() {
        return accountNo;
    }
    
    /**
     * 设置 银行帐号.
     *
     * @param accountNo the accountNo to set
     */
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
    
    /**
     * 获取 开户行编码.
     *
     * @return  the accountbankCode
     */
    public String getAccountbankCode() {
        return accountbankCode;
    }
    
    /**
     * 设置 开户行编码.
     *
     * @param accountbankCode the accountbankCode to set
     */
    public void setAccountbankCode(String accountbankCode) {
        this.accountbankCode = accountbankCode;
    }
    
    /**
     * 获取 开户行名称(扩展).
     *
     * @return  the accountbankName
     */
    public String getAccountbankName() {
        return accountbankName;
    }
    
    /**
     * 设置 开户行名称(扩展).
     *
     * @param accountbankName the accountbankName to set
     */
    public void setAccountbankName(String accountbankName) {
        this.accountbankName = accountbankName;
    }
    
    /**
     * 获取 开户支行编码.
     *
     * @return  the accountbranchbankCode
     */
    public String getAccountbranchbankCode() {
        return accountbranchbankCode;
    }
    
    /**
     * 设置 开户支行编码.
     *
     * @param accountbranchbankCode the accountbranchbankCode to set
     */
    public void setAccountbranchbankCode(String accountbranchbankCode) {
        this.accountbranchbankCode = accountbranchbankCode;
    }
    
    /**
     * 获取 开户支行名称(扩展).
     *
     * @return  the accountbranchbankName
     */
    public String getAccountbranchbankName() {
        return accountbranchbankName;
    }
    
    /**
     * 设置 开户支行名称(扩展).
     *
     * @param accountbranchbankName the accountbranchbankName to set
     */
    public void setAccountbranchbankName(String accountbranchbankName) {
        this.accountbranchbankName = accountbranchbankName;
    }
    
    /**
     * 获取 开户行所在城市编码.
     *
     * @return  the accountbankcityCode
     */
    public String getAccountbankcityCode() {
        return accountbankcityCode;
    }
    
    /**
     * 设置 开户行所在城市编码.
     *
     * @param accountbankcityCode the accountbankcityCode to set
     */
    public void setAccountbankcityCode(String accountbankcityCode) {
        this.accountbankcityCode = accountbankcityCode;
    }
    
    /**
     * 获取 开户行所在城市名称(扩展).
     *
     * @return  the accountbankcityName
     */
    public String getAccountbankcityName() {
        return accountbankcityName;
    }
    
    /**
     * 设置 开户行所在城市名称(扩展).
     *
     * @param accountbankcityName the accountbankcityName to set
     */
    public void setAccountbankcityName(String accountbankcityName) {
        this.accountbankcityName = accountbankcityName;
    }
    
    /**
     * 获取 开户行所在省份编码.
     *
     * @return  the accountbankstateCode
     */
    public String getAccountbankstateCode() {
        return accountbankstateCode;
    }
    
    /**
     * 设置 开户行所在省份编码.
     *
     * @param accountbankstateCode the accountbankstateCode to set
     */
    public void setAccountbankstateCode(String accountbankstateCode) {
        this.accountbankstateCode = accountbankstateCode;
    }
    
    /**
     * 获取 开户行所在省份编码(扩展).
     *
     * @return  the accountbankstateName
     */
    public String getAccountbankstateName() {
        return accountbankstateName;
    }
    
    /**
     * 设置 开户行所在省份编码(扩展).
     *
     * @param accountbankstateName the accountbankstateName to set
     */
    public void setAccountbankstateName(String accountbankstateName) {
        this.accountbankstateName = accountbankstateName;
    }
    
    /**
     * 获取 是否有效（Y/N）.
     *
     * @return  the active
     */
    public String getActive() {
        return active;
    }
    
    /**
     * 设置 是否有效（Y/N）.
     *
     * @param active the active to set
     */
    public void setActive(String active) {
        this.active = active;
    }

	/**
	 * @return accountTypes
	 */
	public List<String> getAccountTypes() {
		return accountTypes;
	}

	/**
	 * @param  accountTypes  
	 */
	public void setAccountTypes(List<String> accountTypes) {
		this.accountTypes = accountTypes;
	}
	
	public String getAccSort() {
		return accSort;
	}
	public void setAccSort(String accSort) {
		this.accSort = accSort;
	}


}